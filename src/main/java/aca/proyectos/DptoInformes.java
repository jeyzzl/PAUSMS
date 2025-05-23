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
public class DptoInformes {

    private int id;
    private int id_actividad;
    private String fecha_creado;
    private String tipo_informe;
    private String detalle;
    private int porcentaje_avance;
    private String clave_reviso;
    private String fecha_revisado;
    private String estado;
    private String clave;

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
     * @return the tipo_informe
     */
    public String getTipo_informe() {
        return tipo_informe;
    }

    /**
     * @param tipo_informe the tipo_informe to set
     */
    public void setTipo_informe(String tipo_informe) {
        this.tipo_informe = tipo_informe;
    }

    /**
     * @return the detalle
     */
    public String getDetalle() {
        return detalle;
    }

    /**
     * @param detalle the detalle to set
     */
    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    /**
     * @return the porcentaje_avance
     */
    public int getPorcentaje_avance() {
        return porcentaje_avance;
    }

    /**
     * @param porcentaje_avance the porcentaje_avance to set
     */
    public void setPorcentaje_avance(int porcentaje_avance) {
        this.porcentaje_avance = porcentaje_avance;
    }

    /**
     * @return the clave_reviso
     */
    public String getClave_reviso() {
        return clave_reviso;
    }

    /**
     * @param clave_reviso the clave_reviso to set
     */
    public void setClave_reviso(String clave_reviso) {
        this.clave_reviso = clave_reviso;
    }

    /**
     * @return the fecha_revisado
     */
    public String getFecha_revisado() {
        return fecha_revisado;
    }

    /**
     * @param fecha_revisado the fecha_revisado to set
     */
    public void setFecha_revisado(String fecha_revisado) {
        this.fecha_revisado = fecha_revisado;
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

    /**
     * @return the clave
     */
    public String getClave() {
        return clave;
    }

    /**
     * @param clave the clave to set
     */
    public void setClave(String clave) {
        this.clave = clave;
    }

}
