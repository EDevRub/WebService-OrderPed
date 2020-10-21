package model;

import java.sql.Date;


/**
 *
 * @author Eduardo Rubio
 */
public class Historial_Pedidos {
    private int id;
    private int num_ped;
    private String rut_persona;
    private String nom_persona;
    private String fecha;
    private int hora_de_espera;
    private int min_de_espera;
    private int seg_de_espera;
    private int id_tiempo_Estimado;

    public Historial_Pedidos() {
    }

    public Historial_Pedidos(int id, int num_ped, String rut_persona, String nom_persona, String fecha, int hora_de_espera, int min_de_espera, int seg_de_espera, int id_tiempo_Estimado) {
        this.id = id;
        this.num_ped = num_ped;
        this.rut_persona = rut_persona;
        this.nom_persona = nom_persona;
        this.fecha = fecha;
        this.hora_de_espera = hora_de_espera;
        this.min_de_espera = min_de_espera;
        this.seg_de_espera = seg_de_espera;
        this.id_tiempo_Estimado = id_tiempo_Estimado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNum_ped() {
        return num_ped;
    }

    public void setNum_ped(int num_ped) {
        this.num_ped = num_ped;
    }

    public String getRut_persona() {
        return rut_persona;
    }

    public void setRut_persona(String rut_persona) {
        this.rut_persona = rut_persona;
    }

    public String getNom_persona() {
        return nom_persona;
    }

    public void setNom_persona(String nom_persona) {
        this.nom_persona = nom_persona;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getHora_de_espera() {
        return hora_de_espera;
    }

    public void setHora_de_espera(int hora_de_espera) {
        this.hora_de_espera = hora_de_espera;
    }

    public int getMin_de_espera() {
        return min_de_espera;
    }

    public void setMin_de_espera(int min_de_espera) {
        this.min_de_espera = min_de_espera;
    }

    public int getSeg_de_espera() {
        return seg_de_espera;
    }

    public void setSeg_de_espera(int seg_de_espera) {
        this.seg_de_espera = seg_de_espera;
    }

    public int getId_tiempo_Estimado() {
        return id_tiempo_Estimado;
    }

    public void setId_tiempo_Estimado(int id_tiempo_Estimado) {
        this.id_tiempo_Estimado = id_tiempo_Estimado;
    }
    
}
