/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package instituto.clases;

public class Alumno {
    private int idAlumno;
    private String nombre;
    private String apellido;
    private String correoElectronico;
    private int idCiclos;

    public Alumno(int idAlumno, String nombre, String apellido, String correoElectronico, int idCiclos) {
        this.idAlumno = idAlumno;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correoElectronico = correoElectronico;
        this.idCiclos = idCiclos;
    }

    public int getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public int getIdCiclos() {
        return idCiclos;
    }

    public void setIdCiclos(int idCiclos) {
        this.idCiclos = idCiclos;
    }
}