package model;

import com.mysql.jdbc.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Edunaldo
 */
public class Data {

    private Conexion con;
    private ResultSet rs;
    private String sql;
    private List<Pedido> listaPedidos;
    private List<Pedido> listaPedidosDeHoy;
    private List<Pedido> listaPedidosFiltradosFecha;
    private List<Persona> listaPersonas;
    private List<Historial_Pedidos> listaDeHistorial;
    private List<Historial_Pedidos> listaDeHistorialFiltrados;
    private List<PersonaSelect> ListaDePersonal;

    public Data() {
        try {
            con = new Conexion(
                    "localhost",
                    "root",//nombre User
                    "",//Pass
                    "bdOrderPedd"//nombre  BD
            );
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean ingresar(Persona p) throws SQLException {
        boolean existe = false;
        sql = "select * from persona where rut = '" + p.getRut() + "' and id_cargo != 3;";

        rs = con.ejecutarSelect(sql);

        if (rs.next()) {
            existe = true;
            p.setRut(rs.getString(1));
            p.setNombre(rs.getString(2));
            p.setId_Cargo(rs.getInt(3));
            if (p.getId_Cargo() == 1) {
                System.out.println("JEFE");
            } else if (p.getId_Cargo() == 2) {
                System.out.println("CAJERO");
            }
        }
        con.desconectar();

        return existe;
    }

    public boolean ingresoDeCliente(String rut) throws SQLException {
        boolean existe = false;
        sql = "select * from pedido where rut_persona = '" + rut + "' and date(fecha) = curdate();";

        rs = con.ejecutarSelect(sql);

        if (rs.next()) {
            existe = true;
        }
        con.desconectar();

        return existe;
    }

    public List<Persona> getTotalPersonas() throws SQLException {
        sql = "select * from persona;";

        listaPersonas = new ArrayList<>();
        Persona p;
        rs = con.ejecutarSelect(sql);

        while (rs.next()) {
            p = new Persona();

            p.setRut(rs.getString(1));
            p.setNombre(rs.getString(2));
            p.setId_Cargo(rs.getInt(3));

            listaPersonas.add(p);
        }
        con.desconectar();
        System.out.println(listaPersonas);
        return listaPersonas;
    }

    public List<PersonaSelect> getPersonalDeTrabajo() throws SQLException {
        sql = "select persona.rut,persona.nombre,cargo.cargo from persona inner join cargo on persona.id_cargo = cargo.id where persona.id_cargo = 1 or persona.id_cargo = 2 order by persona.id_cargo asc;";
        ListaDePersonal = new ArrayList<>();
        PersonaSelect p;
        rs = con.ejecutarSelect(sql);

        while (rs.next()) {
            p = new PersonaSelect();

            p.setRut(rs.getString(1));
            p.setNombre(rs.getString(2));
            p.setCargo(rs.getString(3));

            ListaDePersonal.add(p);
        }
        con.desconectar();
        return ListaDePersonal;
    }

    public int getCargoPersona(Persona p) throws SQLException {
        sql = "select id_cargo from persona where rut = '" + p.getRut() + "';";

        int id_Cargo = 0;

        rs = con.ejecutarSelect(sql);

        if (rs.next()) {
            id_Cargo = rs.getInt(1);
        }
        con.desconectar();
        return id_Cargo;
    }

    public List<Pedido> getPedidosTotales() throws SQLException {
        sql = "select * from pedido;";

        listaPedidos = new ArrayList<>();
        Pedido p;
        rs = con.ejecutarSelect(sql);

        while (rs.next()) {
            p = new Pedido();

            p.setId(rs.getInt(1));
            p.setRut_persona(rs.getString(2));
            p.setNom_persona(rs.getString(3));
            p.setFecha(rs.getString(4));
            p.setEstado_pedido(rs.getInt(5));

            listaPedidos.add(p);
        }
        con.desconectar();
        System.out.println(listaPedidos);
        return listaPedidos;
    }

    public int getNumTotalDePersonas() throws SQLException {
        sql = "select count(*) from persona where persona.id_cargo = 3;";

        int numTotalClientes = 0;

        rs = con.ejecutarSelect(sql);

        if (rs.next()) {
            numTotalClientes = rs.getInt(1);
        }
        con.desconectar();
        return numTotalClientes;
    }

    public List<Pedido> getPedidosPorFecha(String fecha) throws SQLException {
        sql = "select pedido.id,persona.rut,pedido.nom_persona,pedido.fecha,pedido.estado from pedido inner join persona on persona.rut = pedido.rut_persona and pedido.fecha = '" + fecha + "';";

        listaPedidosFiltradosFecha = new ArrayList<>();
        Pedido p;
        rs = con.ejecutarSelect(sql);

        while (rs.next()) {
            p = new Pedido();

            p.setId(rs.getInt(1));
            p.setRut_persona(rs.getString(2));
            p.setNom_persona(rs.getString(3));
            p.setFecha(rs.getString(4));
            p.setEstado_pedido(rs.getInt(5));

            listaPedidosFiltradosFecha.add(p);
        }
        con.desconectar();
        System.out.println(listaPedidosFiltradosFecha);
        return listaPedidosFiltradosFecha;
    }

    public void registroPedido(Pedido p) throws SQLException {
        sql = "call agregar_pedido('" + p.getRut_persona() + "','" + p.getNom_persona() + "');";
        //String mensaje = "Pedido Agregado";
        con.ejecutar(sql);
        con.desconectar();
        //System.out.println(listaPedidos);
    }

    public void actualizarEstadoPedidoAListo(int idPedido) throws SQLException {
        sql = "call cambiarEstadoEsperaAListo(" + idPedido + ");";
        con.ejecutar(sql);
        con.desconectar();
    }

    public void actualizarEstadoPedidoAEntregado(int idPedido) throws SQLException {
        sql = "call cambiarEstadoListoAEntregado(" + idPedido + ");";
        con.ejecutar(sql);
        con.desconectar();
    }

    public List<Pedido> getPedidosDeHoy() throws SQLException {
        sql = "select pedido.id,persona.rut,pedido.nom_persona,pedido.fecha,pedido.estado from pedido inner join persona on persona.rut = pedido.rut_persona and pedido.estado between 1 and 2 and pedido.fecha between curdate() and now() order by pedido.id asc;";
        listaPedidosDeHoy = new ArrayList<>();
        Pedido p;
        rs = con.ejecutarSelect(sql);

        while (rs.next()) {
            p = new Pedido();

            p.setId(rs.getInt(1));
            p.setRut_persona(rs.getString(2));
            p.setNom_persona(rs.getString(3));
            p.setFecha(rs.getString(4));
            p.setEstado_pedido(rs.getInt(5));

            listaPedidosDeHoy.add(p);
        }
        con.desconectar();
        return listaPedidosDeHoy;
    }

    public int getNumCantidadPedidosHoy() throws SQLException {
        sql = "select count(*) from pedido where fecha = curdate();";
        int numCantPedidosHoy = 0;
        rs = con.ejecutarSelect(sql);
        if (rs.next()) {
            numCantPedidosHoy = rs.getInt(1);
        }
        con.desconectar();
        return numCantPedidosHoy;
    }

    public int horaRegistroPedido(String rut) throws SQLException {
        sql = "select date_format(hora,'%H') as hora from pedido where pedido.rut_persona = '" + rut + "' order by id desc limit 0,1;";
        int horaPed = 00;

        rs = con.ejecutarSelect(sql);
        if (rs.next()) {
            horaPed = rs.getInt(1);
        }
        con.desconectar();
        return horaPed;
    }

    public int minutoRegistroPedido(String rut) throws SQLException {
        sql = "select date_format(hora,'%i') as hora from pedido where pedido.rut_persona = '" + rut + "' order by id desc limit 0,1;";
        int minPed = 00;

        rs = con.ejecutarSelect(sql);
        if (rs.next()) {
            minPed = rs.getInt(1);
        }
        con.desconectar();
        return minPed;
    }

    public int segundoRegistroPedido(String rut) throws SQLException {
        sql = "select date_format(hora,'%s') as hora from pedido where pedido.rut_persona = '" + rut + "' order by id desc limit 0,1;";
        int segPed = 00;

        rs = con.ejecutarSelect(sql);
        if (rs.next()) {
            segPed = rs.getInt(1);
        }
        con.desconectar();
        return segPed;
    }

    public String getHoraRegistro(int id) throws SQLException {
        sql = "select fecha from pedido where id = " + id + ";";
        String horaRegistro = "";
        rs = con.ejecutarSelect(sql);
        if (rs.next()) {
            horaRegistro = rs.getString(1);
        }
        con.desconectar();
        return horaRegistro;
    }

    public void registrar_Historial_Pedidos(Historial_Pedidos hp) throws SQLException {
        sql = "call agregar_historial_pedido(" + hp.getNum_ped() + ",'" + hp.getRut_persona() + "','" + hp.getNom_persona() + "'," + hp.getHora_de_espera() + "," + hp.getMin_de_espera() + "," + hp.getSeg_de_espera() + ");";
        con.ejecutar(sql);
        con.desconectar();
    }

    public void registro_de_personal(Persona p) throws SQLException {
        sql = "call agregar_persona('" + p.getRut() + "','" + p.getNombre() + "'," + p.getId_Cargo() + ");";
        con.ejecutar(sql);
        con.desconectar();
    }

    public void tiempo_estimado_pedidos(int minutos) throws SQLException {
        sql = "insert into tiempo_estimado values(null,'" + minutos + "');";
        con.ejecutar(sql);
        con.desconectar();
    }

    public List<Historial_Pedidos> getHistorialTotalDePedidos() throws SQLException {
        sql = "select * from historial_pedidos;";
        listaDeHistorial = new ArrayList<>();
        Historial_Pedidos hp;
        rs = con.ejecutarSelect(sql);

        while (rs.next()) {
            hp = new Historial_Pedidos();

            hp.setId(rs.getInt(1));
            hp.setNum_ped(rs.getInt(2));
            hp.setRut_persona(rs.getString(3));
            hp.setNom_persona(rs.getString(4));
            hp.setFecha(rs.getString(5));
            hp.setHora_de_espera(rs.getInt(6));
            hp.setMin_de_espera(rs.getInt(7));
            hp.setSeg_de_espera(rs.getInt(8));

            listaDeHistorial.add(hp);
        }
        con.desconectar();
        return listaDeHistorial;
    }

    public int getSegDeEspera(int numPed) throws SQLException {
        sql = "select seg_de_espera from historial_pedidos where num_pedido = " + numPed + ";";
        int segDeEspera = 0;

        rs = con.ejecutarSelect(sql);

        if (rs.next()) {
            segDeEspera = rs.getInt(1);
        }
        con.desconectar();
        return segDeEspera;

    }

    public int getPromedioDeSegundos() throws SQLException {
        sql = "SELECT round(avg(seg_de_espera)) FROM historial_pedidos;";
        int promedioSegundos = 0;
        rs = con.ejecutarSelect(sql);
        if (rs.next()) {
            promedioSegundos = rs.getInt(1);
        }
        con.desconectar();
        return promedioSegundos;
    }

    public int getMinDeEspera(int numPed) throws SQLException {
        sql = "select min_de_espera from historial_pedidos where num_pedido = " + numPed + ";";
        int minDeEspera = 0;

        rs = con.ejecutarSelect(sql);

        if (rs.next()) {
            minDeEspera = rs.getInt(1);
        }
        con.desconectar();
        return minDeEspera;

    }

    public int getPromedioDeMinutos() throws SQLException {
        sql = "SELECT round(avg(min_de_espera)) FROM historial_pedidos;";
        int promedioMinutos = 0;
        rs = con.ejecutarSelect(sql);
        if (rs.next()) {
            promedioMinutos = rs.getInt(1);
        }
        con.desconectar();
        return promedioMinutos;
    }

    public int getHoraDeEspera(int numPed) throws SQLException {
        sql = "select hora_de_espera from historial_pedidos where num_pedido = " + numPed + ";";
        int horaDeEspera = 0;

        rs = con.ejecutarSelect(sql);

        if (rs.next()) {
            horaDeEspera = rs.getInt(1);
        }
        con.desconectar();
        return horaDeEspera;

    }

    public int getPromedioDeHoras() throws SQLException {
        sql = "SELECT round(avg(hora_de_espera)) FROM historial_pedidos;";
        int promedioHoras = 0;
        rs = con.ejecutarSelect(sql);
        if (rs.next()) {
            promedioHoras = rs.getInt(1);
        }
        con.desconectar();
        return promedioHoras;
    }

    public int getPromedioDeSegundosFiltrados(String fecha1, String fecha2) throws SQLException {
        sql = "SELECT round(avg(seg_de_espera)) FROM historial_pedidos where fecha between '" + fecha1 + "' and '" + fecha2 + "';";
        int promedioSegundos = 0;
        rs = con.ejecutarSelect(sql);
        if (rs.next()) {
            promedioSegundos = rs.getInt(1);
        }
        con.desconectar();
        return promedioSegundos;
    }

    public int getPromedioDeMinutosFiltrados(String fecha1, String fecha2) throws SQLException {
        sql = "SELECT round(avg(min_de_espera)) FROM historial_pedidos where fecha between '" + fecha1 + "' and '" + fecha2 + "';";
        int promedioMin = 0;
        rs = con.ejecutarSelect(sql);
        if (rs.next()) {
            promedioMin = rs.getInt(1);
        }
        con.desconectar();
        return promedioMin;
    }

    public int getPromedioDeHoraFiltrados(String fecha1, String fecha2) throws SQLException {
        sql = "SELECT round(avg(hora_de_espera)) FROM historial_pedidos where fecha between '" + fecha1 + "' and '" + fecha2 + "';";
        int promedioHora = 0;
        rs = con.ejecutarSelect(sql);
        if (rs.next()) {
            promedioHora = rs.getInt(1);
        }
        con.desconectar();
        return promedioHora;
    }

    public int getTotalDePedidos() throws SQLException {
        sql = "select count(*) from historial_pedidos;";
        int totalPed = 0;
        rs = con.ejecutarSelect(sql);
        if (rs.next()) {
            totalPed = rs.getInt(1);
        }
        con.desconectar();
        return totalPed;
    }

    public int getTotalDePedidosFiltrados(String fecha1, String fecha2) throws SQLException {
        sql = "select count(*) from historial_pedidos where fecha between '" + fecha1 + "' and '" + fecha2 + "';";
        int totalPedFiltrado = 0;
        rs = con.ejecutarSelect(sql);
        if (rs.next()) {
            totalPedFiltrado = rs.getInt(1);
        }
        con.desconectar();
        return totalPedFiltrado;
    }

    public List<Historial_Pedidos> getListaHistorialFiltrada(String fecha1, String fecha2) throws SQLException {
        sql = "select * from historial_Pedidos where fecha between '" + fecha1 + "' and '" + fecha2 + "';";
        listaDeHistorialFiltrados = new ArrayList<>();

        Historial_Pedidos hp;
        rs = con.ejecutarSelect(sql);

        while (rs.next()) {
            hp = new Historial_Pedidos();

            hp.setId(rs.getInt(1));
            hp.setNum_ped(rs.getInt(2));
            hp.setRut_persona(rs.getString(3));
            hp.setNom_persona(rs.getString(4));
            hp.setFecha(rs.getString(5));
            hp.setHora_de_espera(rs.getInt(6));
            hp.setMin_de_espera(rs.getInt(7));
            hp.setSeg_de_espera(rs.getInt(8));
            hp.setId_tiempo_Estimado(rs.getInt(9));

            listaDeHistorialFiltrados.add(hp);
        }
        con.desconectar();
        return listaDeHistorialFiltrados;
    }

    public int cantDePedBuenosSEG(int tiempo) throws SQLException {
        sql = "select count(*) from historial_pedidos where seg_de_espera < " + tiempo + ";";
        int pedBuenosSEG = 0;
        rs = con.ejecutarSelect(sql);
        if (rs.next()) {
            pedBuenosSEG = rs.getInt(1);
        }
        con.desconectar();
        return pedBuenosSEG;
    }

    public int cantDePedMalosSEG(int tiempo) throws SQLException {
        sql = "select count(*) from historial_pedidos where seg_de_espera >= " + tiempo + ";";
        int pedMalosSEG = 0;
        rs = con.ejecutarSelect(sql);
        if (rs.next()) {
            pedMalosSEG = rs.getInt(1);
        }
        con.desconectar();
        return pedMalosSEG;
    }

    public int cantDePedBuenosMIN(int tiempo) throws SQLException {
        sql = "select count(*) from historial_pedidos where min_de_espera < " + tiempo + ";";
        int pedBuenosMIN = 0;
        rs = con.ejecutarSelect(sql);
        if (rs.next()) {
            pedBuenosMIN = rs.getInt(1);
        }
        con.desconectar();
        return pedBuenosMIN;
    }

    public int cantDePedMalosMIN(int tiempo) throws SQLException {
        sql = "select count(*) from historial_pedidos where min_de_espera >= " + tiempo + ";";
        int pedMalosMIN = 0;
        rs = con.ejecutarSelect(sql);
        if (rs.next()) {
            pedMalosMIN = rs.getInt(1);
        }
        con.desconectar();
        return pedMalosMIN;
    }

    public int cantDePedBuenosSEGFiltrado(int tiempo, String fecha1, String fecha2) throws SQLException {
        sql = "select count(*) from historial_pedidos where fecha between '" + fecha1 + "' and '" + fecha2 + "' and seg_de_espera < " + tiempo + ";";
        int pedBuenosSEGFiltrado = 0;
        rs = con.ejecutarSelect(sql);
        if (rs.next()) {
            pedBuenosSEGFiltrado = rs.getInt(1);
        }
        con.desconectar();
        return pedBuenosSEGFiltrado;
    }

    public int cantDePedMalosSEGFiltrado(int tiempo, String fecha1, String fecha2) throws SQLException {
        sql = "select count(*) from historial_pedidos where fecha between '" + fecha1 + "' and '" + fecha2 + "' and seg_de_espera >= " + tiempo + ";";
        int pedMalosSEGFiltrado = 0;
        rs = con.ejecutarSelect(sql);
        if (rs.next()) {
            pedMalosSEGFiltrado = rs.getInt(1);
        }
        con.desconectar();
        return pedMalosSEGFiltrado;
    }

    public int cantDePedBuenosMINFiltrado(String fecha1, String fecha2) throws SQLException {
        sql = "select count(*) from historial_pedidos where fecha between '" + fecha1 + "' and '" + fecha2 + "' and min_de_espera < id_tiempo_Estimado;";
        int pedBuenosMINFiltrado = 0;
        rs = con.ejecutarSelect(sql);
        if (rs.next()) {
            pedBuenosMINFiltrado = rs.getInt(1);
        }
        con.desconectar();
        return pedBuenosMINFiltrado;
    }

    public int cantDePedMalosMINFiltrado(String fecha1, String fecha2) throws SQLException {
        sql = "select count(*) from historial_pedidos where fecha between '" + fecha1 + "' and '" + fecha2 + "' and min_de_espera >= id_tiempo_Estimado;";
        int pedMalosMINFiltrado = 0;
        rs = con.ejecutarSelect(sql);
        if (rs.next()) {
            pedMalosMINFiltrado = rs.getInt(1);
        }
        con.desconectar();
        return pedMalosMINFiltrado;
    }

    public String getEstadoPedidoCliente(String rutCliente) throws SQLException {
        sql = "select estado_Pedido.estado from estado_Pedido,pedido where pedido.estado = estado_Pedido.id and pedido.rut_persona = '" + rutCliente + "' limit 1;  ";
        String rutCL = "";
        rs = con.ejecutarSelect(sql);
        if (rs.next()) {
            rutCL = rs.getString(1);
        }
        con.desconectar();
        return rutCL;
    }

    public String getNombreCliente(String rutCliente) throws SQLException {
        sql = "select nombre from persona where rut = '" + rutCliente + "';";
        String nombre = "";
        rs = con.ejecutarSelect(sql);
        if (rs.next()) {
            nombre = rs.getString(1);
        }
        con.desconectar();
        return nombre;

    }

    public int numPedido(String rut) throws SQLException {
        sql = "select id from pedido where rut_persona = '" + rut + "' order by id desc limit 1;";
        int numPedido = 0;
        rs = con.ejecutarSelect(sql);
        if (rs.next()) {
            numPedido = rs.getInt(1);
        }
        con.desconectar();
        return numPedido;
    }

    public String rutClienteID(int numPed) throws SQLException {
        sql = "select rut_persona from pedido where id = " + numPed + ";";
        String rutCliente = "";
        rs = con.ejecutarSelect(sql);
        if (rs.next()) {
            rutCliente = rs.getString(1);
        }
        con.desconectar();
        return rutCliente;
    }

    public String nombreClienteID(int numPed) throws SQLException {
        sql = "select nom_persona from pedido where id = " + numPed + ";";
        String nomCliente = "";
        rs = con.ejecutarSelect(sql);
        if (rs.next()) {
            nomCliente = rs.getString(1);
        }
        con.desconectar();
        return nomCliente;
    }

    public int getTiempoEstimado(int numPed) throws SQLException {
        sql = "select id_tiempo_Estimado from historial_Pedidos where num_pedido = " + numPed + ";";
        int tiempoEstimado = 0;

        rs = con.ejecutarSelect(sql);

        if (rs.next()) {
            tiempoEstimado = rs.getInt(1);
        }
        con.desconectar();
        return tiempoEstimado;

    }

    public int tiempoEstimadoActual() throws SQLException {
        sql = "select min_estimado from tiempo_estimado order by id desc limit 1;";
        int tEstimadoActual = 0;
        rs = con.ejecutarSelect(sql);
        if (rs.next()) {
            tEstimadoActual = rs.getInt(1);
        }
        con.desconectar();
        return tEstimadoActual;
    }

    public void cambiarPersonal(String rut) throws SQLException {
        sql = "update persona set id_cargo = 3 where rut = '" + rut + "' and id_cargo != 1;";
        con.ejecutar(sql);
        con.desconectar();
    }

}
