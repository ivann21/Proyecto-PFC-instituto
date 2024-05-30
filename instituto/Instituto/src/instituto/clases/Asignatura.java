/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package instituto.clases;

public class Asignatura {
    private int idAsignatura;
    private String nombre;
    private String descripcion;
    private int idCiclos;

    public Asignatura(int idAsignatura, String nombre, String descripcion, int idCiclos) {
        this.idAsignatura = idAsignatura;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.idCiclos = idCiclos;
    }

    public int getIdAsignatura() {
        return idAsignatura;
    }

    public void setIdAsignatura(int idAsignatura) {
        this.idAsignatura = idAsignatura;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIdCiclos() {
        return idCiclos;
    }

    public void setIdCiclos(int idCiclos) {
        this.idCiclos = idCiclos;
    }

   
}