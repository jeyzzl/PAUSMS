// Clase para la tabla de CandPermiso
package aca.candado.spring;

import java.sql.*;

public class CandPermiso{	
	private String tipoId;
	private String codioPersonal;
	private String usAlta;
	private String fechaAlta;
	
	// Constructor
	public CandPermiso(){		
		tipoId			= "";
		codioPersonal	= "";	
		usAlta			= "";
		fechaAlta		= "";
	}

	public String getTipoId() {
		return tipoId;
	}
	public void setTipoId(String tipoId) {
		this.tipoId = tipoId;
	}
	
	public String getCodioPersonal() {
		return codioPersonal;
	}
	public void setCodioPersonal(String codioPersonal) {
		this.codioPersonal = codioPersonal;
	}
	public String getUsAlta() {
		return usAlta;
	}
	public void setUsAlta(String usAlta) {
		this.usAlta = usAlta;
	}

	public String getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(String fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	
	
	
}