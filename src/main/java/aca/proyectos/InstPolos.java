/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aca.proyectos;

/**
 *
 * @author Daniel
 */
public class InstPolos {

    private int id;
    private String nombre;
    private String descripcion;
    private String motor;
    private String programa;
    private String fecha_creado;
    private String estado;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the motor
     */
    public String getMotor() {
        return motor;
    }

    /**
     * @param motor the motor to set
     */
    public void setMotor(String motor) {
        this.motor = motor;
    }

    /**
     * @return the programa
     */
    public String getPrograma() {
        return programa;
    }

    /**
     * @param programa the programa to set
     */
    public void setPrograma(String programa) {
        this.programa = programa;
    }

    /**
     * @return the fecha_creado
     */
    public String getFecha_creado() {
        return fecha_creado;
    }

    /**
     * @param fecha_creado the fecha_creado to set
     */
    public void setFecha_creado(String fecha_creado) {
        this.fecha_creado = fecha_creado;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

}
