/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package instituto.clases;

public class Ciclo {
    private int idCiclos;
    private String nombre;
    private int anio;
    private String descripcion;

    public Ciclo(int idCiclos, String nombre, int anio, String descripcion) {
        this.idCiclos = idCiclos;
        this.nombre = nombre;
        this.anio = anio;
        this.descripcion = descripcion;
    }

    public int getIdCiclos() {
        return idCiclos;
    }

    public void setIdCiclos(int idCiclos) {
        this.idCiclos = idCiclos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
