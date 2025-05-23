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
public class DptoRecFinancieros {

    private int id;
    private int id_actividad;
    private String tipo_recursos;
    private int importe;
    private String fecha_creacion;
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
     * @return the id_actividad
     */
    public int getId_actividad() {
        return id_actividad;
    }

    /**
     * @param id_actividad the id_actividad to set
     */
    
    public void setId_actividad(int id_actividad) {
        this.id_actividad = id_actividad;
    }

    /**
     * @return the tipo_recursos
     */
    public String getTipo_recursos() {
        return tipo_recursos;
    }

    /**
     * @param tipo_recursos the tipo_recursos to set
     */
    public void setTipo_recursos(String tipo_recursos) {
        this.tipo_recursos = tipo_recursos;
    }

    /**
     * @return the importe
     */
    public int getImporte() {
        return importe;
    }

    /**
     * @param importe the importe to set
     */
    public void setImporte(int importe) {
        this.importe = importe;
    }

    /**
     * @return the fecha_creacion
     */
    public String getFecha_creacion() {
        return fecha_creacion;
    }

    /**
     * @param fecha_creacion the fecha_creacion to set
     */
    public void setFecha_creacion(String fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
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
