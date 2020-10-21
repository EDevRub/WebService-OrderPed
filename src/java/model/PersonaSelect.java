package model;

/**
 *
 * @author Eduardo Rubio
 */
public class PersonaSelect {

    private String rut;
    private String nombre;
    private String cargo;

    public PersonaSelect() {
    }

    public PersonaSelect(String rut, String nombre, String cargo) {
        this.rut = rut;
        this.nombre = nombre;
        this.cargo = cargo;
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

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

}
