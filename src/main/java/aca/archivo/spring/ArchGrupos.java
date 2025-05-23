//Beans de la tabla ARCH_GRUPOS
package aca.archivo.spring;

import java.sql.*;

public class ArchGrupos {
    private String grupo;
	private String idDocumento;
	private String idStatus;
		
	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public String getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(String idDocumento) {
		this.idDocumento = idDocumento;
	}

	public String getIdStatus() {
		return idStatus;
	}

	public void setIdStatus(String idStatus) {
		this.idStatus = idStatus;
	}

	public ArchGrupos(){
		grupo 		= "";
		idDocumento = "";
		idStatus 	= "";
	}	
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		grupo	 	 		= rs.getString("GRUPO");
		idDocumento			= rs.getString("IDDOCUMENTO");
		idStatus			= rs.getString("IDSTATUS");
	}
	
}