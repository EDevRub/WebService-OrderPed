/**
 * Author:  Edunaldo
 * Created: 19-mar-2019
 */
/* drop database bdOrderPedd; */
create database bdOrderPedd;
use bdOrderPedd;

create table cargo(
    id int auto_increment,
    cargo varchar(10),
    primary key(id)
);

create table estado_Pedido(
    id int auto_increment,
    estado varchar(10),
    primary key(id)
);

create table persona(
    rut varchar(12) unique,
    nombre varchar(50),
    id_cargo int,
    primary key(rut),
    foreign key(id_cargo) references cargo(id)
);
delete from persona where rut = '22.222.222-2';
update persona set id_cargo = 3 where rut = '234234234' and id_cargo != 1;
select * from persona;
select persona.rut,persona.nombre,cargo.cargo from persona,cargo where persona.id_cargo = 1 or persona.id_cargo = 2 and persona.id_cargo = cargo.id;

select persona.rut,persona.nombre,cargo.cargo from persona inner join cargo on persona.id_cargo = cargo.id where persona.id_cargo = 1 or persona.id_cargo = 2 order by persona.id_cargo asc;

create table pedido(
    id int auto_increment,
    rut_persona varchar(12),
    nom_persona varchar(50),
    fecha datetime,
    estado int,
    primary key (id),
    foreign key(rut_Persona) references persona(rut),
    foreign key(estado) references estado_Pedido(id)
);

select id from pedido where rut_persona = '33333333-3' order by id desc limit 1;

create table tiempo_estimado(
	id int auto_increment,
    min_estimado int,
    primary key(id)
);

create table historial_Pedidos(
	id int auto_increment,
    num_pedido int,
    rut_persona varchar(12),
    nom_persona varchar(50),
    fecha datetime,
    hora_de_espera int,
    min_de_espera int,
    seg_de_espera int,
    id_tiempo_Estimado int,
    primary key (id),
    foreign key(num_pedido) references pedido(id),
    foreign key(rut_Persona) references persona(rut)
);


select pedido.id,persona.rut,pedido.nom_persona,pedido.fecha,pedido.estado from pedido inner join persona on persona.rut = pedido.rut_persona and pedido.estado between 1 and 2 and pedido.fecha between curdate() and now() order by pedido.id asc;


select * from historial_Pedidos;

select id_tiempo_Estimado from historial_Pedidos where num_pedido = 2;

select count(*) from historial_pedidos where fecha between '2020-07-31 19:20:23' and '2020-09-29 21:48:23' and min_de_espera >= id_tiempo_Estimado;

select tiempo_estimado.min_estimado from historial_Pedidos,tiempo_estimado where historial_Pedidos.id_tiempo_Estimado = tiempo_estimado.id and historial_Pedidos.num_pedido = 2;

select historial_Pedidos.id,historial_Pedidos.num_pedido,historial_Pedidos.rut_persona,historial_Pedidos.nom_persona,historial_Pedidos.fecha,historial_Pedidos.hora_de_espera,historial_Pedidos.min_de_espera,historial_Pedidos.seg_de_espera,tiempo_estimado.min_estimado from historial_Pedidos,tiempo_estimado where historial_Pedidos.id_tiempo_Estimado = tiempo_estimado.id;

select * from historial_pedidos where fecha >= '2020-07-31 19:20:23';
select now();

select estado_Pedido.estado from estado_Pedido,pedido where pedido.estado = estado_Pedido.id and pedido.rut_persona = '7325986-4' limit 1;  

select * from pedido;
select rut_persona from pedido where id = 18;
select nom_persona from pedido where id = 18;

select * from persona;
select nombre from persona where rut = '333-4';
/*select estado_Pedido.estado from estado_Pedido,pedido where pedido.estado = estado_Pedido.id and pedido.rut_persona = '78451296-3' limit 1;*/

select * from persona where rut = '19527116-k' and id_cargo != 3;
select * from pedido where rut_persona = '78451296-3' and date(fecha) = curdate();


select seg_de_espera from historial_pedidos where num_pedido = 1;
SELECT round(avg(seg_de_espera)) FROM historial_pedidos where fecha between '2020-04-20' and '2020-04-24';
select count(*) from historial_pedidos where fecha between '2020-08-01' and '2020-08-03';
select count(*) from historial_pedidos where fecha between '2020-04-20' and '2020-04-24' and seg_de_espera < 30;
select count(*) from historial_pedidos where seg_de_espera >= 30;
select count(*) from historial_pedidos where fecha between '2020-08-01' and concat('2020-08-03 ','23:59:59') and min_de_espera < 10;
select count(*) from historial_pedidos where min_de_espera >= 10;

select * from historial_pedidos where fecha between '2020-8-01' and concat('2020-8-03 ','23:59:59');




/*-----------------------------INSERT--------------------------------*/
insert into cargo values(null,"Jefe");
insert into cargo values(null,"Cajero");
insert into cargo values(null,"Cliente");

insert into estado_Pedido values(null,"En Espera");
insert into estado_Pedido values(null,"Listo");
insert into estado_Pedido values(null,"Entregado");

insert into persona values("19527116-k","Eduardo",1);
insert into persona values("13303295-9","Marcela",2);

insert into tiempo_estimado values(null,10);
/*-----------------------------FIN INSERT--------------------------------*/



/*-----------------------------SELECT--------------------------------*/
select * from cargo;
select * from persona;
select * from pedido;
select * from estado_Pedido;

select count(*) from pedido where fecha = curdate();

select fecha from pedido where id = 14;

/*select * from persona where rut = '13303295-9' and id_cargo != 3;*/
/*select id_cargo from persona where rut = '19527116-k';*/

select count(*) from pedido; /*  Total de Pedidos  */
select count(distinct rut_persona) from pedido; /* Cuenta a las personas con distinto rut */

select count(*) from pedido where fecha = curdate(); /* obtiene el total de personas atendidas hoy  --- curdate: rescato solo fecha */
select count(*) from persona where persona.id_cargo = 3; /* Total de clientes  */

select pedido.id,persona.rut,pedido.nom_persona,pedido.fecha,pedido.estado from pedido inner join persona on persona.rut = pedido.rut_persona and pedido.estado between 1 and 2 and pedido.fecha between curdate() and now() order by pedido.id asc;

select * from pedido where estado between 1 and 2;
/*-----------------------------FIN SELECT--------------------------------*/



/*-----------------------------TRIGGER--------------------------------*/
/*create trigger historial_de_pedidos after delete
on pedido
for each row
insert into historial_Pedido(id,rut_persona,nom_persona,fecha)
value(old.id,old.rut_persona,old.nom_persona,old.fecha);*/

/*create trigger creacion_de_personas after insert
on pedido
for each row
insert into persona(rut,nombre,id_cargo)
value(new.rut_persona,new.nom_persona,3);*/
/*-----------------------------TRIGGER--------------------------------*/

select min_estimado from tiempo_estimado order by id desc limit 1;

/*-----------------------------PROCEDIMIENTOS--------------------------------*/
delimiter $$
create procedure agregar_historial_pedido (in p_num_pedido int,in p_rut_persona varchar(12),in p_nom_persona varchar(50),in p_hora_de_espera int,in p_min_de_espera int,in p_seg_de_espera int)
begin
	declare p_tiempo_estimado int;
	set p_tiempo_estimado = (select min_estimado from tiempo_estimado order by id desc limit 1);
	insert into historial_Pedidos(id,num_pedido,rut_persona,nom_persona,fecha,hora_de_espera,min_de_espera,seg_de_espera,id_tiempo_Estimado)
	values(null,p_num_pedido,p_rut_persona,p_nom_persona,now(),p_hora_de_espera,p_min_de_espera,p_seg_de_espera,p_tiempo_estimado);
end$$
delimiter $$


delimiter $$
create procedure agregar_persona (in p_rut varchar(12),in p_nom_persona varchar(50),in p_id_cargo int)
begin
	declare p_existe_rut bit;
	set p_existe_rut = (select count(*) from persona where rut = p_rut);
	if p_existe_rut = 0 then
		select 'no  existe';
		insert into persona(rut,nombre,id_cargo)
		values(p_rut,p_nom_persona,p_id_cargo);
	else
		update persona set id_cargo = p_id_cargo, nombre = p_nom_persona where rut = p_rut and id_cargo != 1;
	end if;

end$$
delimiter $$

delimiter $$
create procedure cambiarEstadoEsperaAListo (in p_id int)
begin
update pedido set estado = 2 where id = p_id;
end$$
delimiter $$

delimiter $$
create procedure cambiarEstadoListoAEntregado (in p_id int)
begin
update pedido set estado = 3 where id = p_id;
end$$
delimiter $$

/*-----------------------------PROCEDIMIENTOS--------------------------------*/

delimiter $$
create procedure horaPedidoIngresado (in p_rut varchar(12))
begin
select date_format(fecha,'%H:%i:%s') as hora from pedido where pedido.rut_persona = p_rut;
end$$
delimiter $$

select date_format(fecha,'%H') as hora from pedido where pedido.rut_persona = '12295035-2' order by id desc limit 0,1;
select date_format(fecha,'%i') as hora from pedido where pedido.rut_persona = '12295035-2' order by id desc limit 0,1;
select date_format(fecha,'%s') as hora from pedido where pedido.rut_persona = '12295035-2' order by id desc limit 0,1;
/*--------------------------------------------------------------------------------------*/
delimiter $$
create procedure agregar_pedido (in p_rut_persona varchar(12),in p_nom_persona varchar(50))
begin
	declare p_existe_rut bit;
	set p_existe_rut = (select count(*) from persona where rut = p_rut_persona);
	if p_existe_rut = 0 then
		select 'no  existe';
		insert into persona values(p_rut_persona,p_nom_persona,3);
                insert into pedido(id,rut_persona,nom_persona,fecha,estado)
		values(null,p_rut_persona,p_nom_persona,now(),1);
	else
		update persona set nombre = p_nom_persona where rut = p_rut_persona;
		insert into pedido(id,rut_persona,nom_persona,fecha,estado)
		values(null,p_rut_persona,p_nom_persona,now(),1);
	end if;
end$$
delimiter $$
/*--------------------------------------------------------------------------------------*/
call agregar_persona("19388074-6","Constanza",3);

call agregar_pedido("7325986-4","Oula");

call cambiarEstadoEsperaAListo(35);

call cambiarEstadoListoAEntregado(35);

call horaPedidoIngresado("18265784-3");


select * from cargo;

/*-----------------------------FIN PROCEDIMIENTOS--------------------------------*/