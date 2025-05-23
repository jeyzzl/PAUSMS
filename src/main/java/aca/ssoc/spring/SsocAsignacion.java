/*
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package aca.ssoc.spring;

public class SsocAsignacion {
	
    String codigoPersonal;
    String asignacionId;
    String dependencia;
    String direccion;
    String telefono;
    String responsable;
    String estado;
    String fechaInicio;
    String sector;
    
    public SsocAsignacion() {
    	
    	codigoPersonal 	= "";
    	dependencia		= "";
    	direccion		= "";
    	telefono		= "";
    	responsable		= "";
    	estado			= "";
    	asignacionId	= "";
    	fechaInicio		= "";
    	sector			= "";
    }
    
    
    public String getCodigoPersonal() {
        return codigoPersonal;
    }
    
    public void setCodigoPersonal(String codigoPersonal) {
        this.codigoPersonal = codigoPersonal;
    }
    
    public String getDependencia() {
        return dependencia;
    }
    
    public void setDependencia(String dependencia) {
        this.dependencia = dependencia;
    }
    
    public String getDireccion() {
        return direccion;
    }
    
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public String getFechaInicio() {
        return fechaInicio;
    }
    
    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
    
    public String getAsignacionId() {
        return asignacionId;
    }
    
    public void setAsignacionId(String asignacionId) {
        this.asignacionId = asignacionId;
    }
    
    public String getResponsable() {
        return responsable;
    }
    
    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }
    
    public String getTelefono() {
        return telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
	public String getSector() {
		return sector;
	}
	
	public void setSector(String sector) {
		this.sector = sector;
	}
	
}