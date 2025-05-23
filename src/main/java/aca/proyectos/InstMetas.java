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
public class InstMetas {

    private int id;
    private int id_ciclo;
    private int id_objins;
    private String descripcion;
    private String visible;
    private String fecha_creada;
    private String comentario;

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
     * @return the id_ciclo
     */
    public int getId_ciclo() {
        return id_ciclo;
    }

    /**
     * @param id_ciclo the id_ciclo to set
     */
    public void setId_ciclo(int id_ciclo) {
        this.id_ciclo = id_ciclo;
    }

    /**
     * @return the id_objins
     */
    public int getId_objins() {
        return id_objins;
    }

    /**
     * @param id_objins the id_objins to set
     */
    public void setId_objins(int id_objins) {
        this.id_objins = id_objins;
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
     * @return the visible
     */
    public String getVisible() {
        return visible;
    }

    /**
     * @param visible the visible to set
     */
    public void setVisible(String visible) {
        this.visible = visible;
    }

    /**
     * @return the fecha_creada
     */
    public String getFecha_creada() {
        return fecha_creada;
    }

    /**
     * @param fecha_creada the fecha_creada to set
     */
    public void setFecha_creada(String fecha_creada) {
        this.fecha_creada = fecha_creada;
    }

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
    
    

}
