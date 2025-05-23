// Bean del Catalogo Cargas
package  aca.carga;

import java.sql.*;

public class CargaPron{
	private String cursoCargaId;	
	
	public CargaPron(){
		cursoCargaId			= "";		
	}
	
	public String getCursoCargaId() {
		return cursoCargaId;
	}

	public void setCursoCargaId(String cursoCargaId) {
		this.cursoCargaId = cursoCargaId;
	}

	public void mapeaReg(ResultSet rs ) throws SQLException{
		cursoCargaId 		= rs.getString("CURSO_CARGA_ID");	
	}
}