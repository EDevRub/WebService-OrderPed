package model;

import java.util.Date;

/**
 *
 * @author Edunaldo
 */
public class Pedido {

    private int id;
    private String rut_persona;
    private String nom_persona;
    private String fecha;
    private int estado_pedido;
    private String estado;

    public Pedido() {
    }

    public Pedido(int id, String rut_persona, String nom_persona, String fecha, int estado_pedido, String estado) {
        this.id = id;
        this.rut_persona = rut_persona;
        this.nom_persona = nom_persona;
        this.fecha = fecha;
        this.estado_pedido = estado_pedido;
        this.estado = estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEstado() {
        if (estado_pedido == 1) {
            estado = "En Espera";
        } else if (estado_pedido == 2) {
            estado = "Listo";
        } else if (estado_pedido == 3) {
            estado = "Entregado";
        }
        return estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getEstado_pedido() {
        return estado_pedido;
    }

    public void setEstado_pedido(int estado_pedido) {
        this.estado_pedido = estado_pedido;
    }

    @Override
    public String toString() {
        return "Pedido{" + "id=" + id + ", rut_persona=" + rut_persona + ", nom_persona=" + nom_persona + ", fecha=" + fecha + ", estado_pedido=" + estado_pedido + '}';
    }

}
