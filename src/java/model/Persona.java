package model;

public class Persona {
    
    private String rut;
    private String nombre;
    private int id_Cargo;

    public Persona() {
    }

    public Persona(String rut, String nombre, int id_Cargo) {
        this.rut = rut;
        this.nombre = nombre;
        this.id_Cargo = id_Cargo;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId_Cargo() {
        return id_Cargo;
    }

    public void setId_Cargo(int id_Cargo) {
        this.id_Cargo = id_Cargo;
    }

    @Override
    public String toString() {
        return "Persona{" + "rut=" + rut + ", nombre=" + nombre + ", id_Cargo=" + id_Cargo + '}';
    }
    
}
