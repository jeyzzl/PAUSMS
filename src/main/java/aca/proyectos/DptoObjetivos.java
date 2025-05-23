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
public class DptoObjetivos {

    private int id;
    private String id_ccosto;
    private int id_metainst;
    private String descripcion;
    private String fecha_creado;
    private String polo;

    public String getPolo() {
		return polo;
	}

	public void setPolo(String polo) {
		this.polo = polo;
	}

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
     * @return the id_ccosto
     */
    public String getId_ccosto() {
        return id_ccosto;
    }

    /**
     * @param id_ccosto the id_ccosto to set
     */
    public void setId_ccosto(String id_ccosto) {
        this.id_ccosto = id_ccosto;
    }

    /**
     * @return the id_metainst
     */
    public int getId_metainst() {
        return id_metainst;
    }

    /**
     * @param id_metainst the id_metainst to set
     */
    public void setId_metainst(int id_metainst) {
        this.id_metainst = id_metainst;
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

}
