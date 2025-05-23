// Clase para la tabla de Candado
package aca.candado.spring;

import java.sql.*;

public class Candado{	
	private String candadoId;
	private String tipoId;
	private String nombreCandado;		
	
	// Constructor
	public Candado(){		
		candadoId		= "";
		tipoId 			= "";
		nombreCandado	= "";		
	}
	
	public String getCandadoId() {
		return candadoId;
	}

	public void setCandadoId(String candadoId) {
		this.candadoId = candadoId;
	}

	public String getNombreCandado() {
		return nombreCandado;
	}

	public void setNombreCandado(String nombreCandado) {
		this.nombreCandado = nombreCandado;
	}

	public void mapeaReg(ResultSet rs ) throws SQLException{
		candadoId  		= rs.getString("CANDADO_ID");
		nombreCandado	= rs.getString("NOMBRE_CANDADO");				
	}

	public String getTipoId() {
		return tipoId;
	}
	public void setTipoId(String tipoId) {
		this.tipoId = tipoId;
	}
}