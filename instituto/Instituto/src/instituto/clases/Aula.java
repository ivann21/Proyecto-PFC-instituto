/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package instituto.clases;

public class Aula {
    private int idAula;
    private String nombre;
    private String ubicacion;
    private int capacidadAula;
    private boolean pizarra;
    private boolean ordenadores;

    public Aula(int idAula, String nombre, String ubicacion, int capacidadAula, boolean pizarra, boolean ordenadores) {
        this.idAula = idAula;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.capacidadAula = capacidadAula;
        this.pizarra = pizarra;
        this.ordenadores = ordenadores;
    }

    public int getIdAula() {
        return idAula;
    }

    public void setIdAula(int idAula) {
        this.idAula = idAula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public int getCapacidadAula() {
        return capacidadAula;
    }

    public void setCapacidadAula(int capacidadAula) {
        this.capacidadAula = capacidadAula;
    }

    public boolean isPizarra() {
        return pizarra;
    }

    public void setPizarra(boolean pizarra) {
        this.pizarra = pizarra;
    }

    public boolean isOrdenadores() {
        return ordenadores;
    }

    public void setOrdenadores(boolean ordenadores) {
        this.ordenadores = ordenadores;
    }

    
}