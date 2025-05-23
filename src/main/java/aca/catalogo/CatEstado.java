// Bean del Catalogo de Estados
package  aca.catalogo;

import java.sql.*;

public class CatEstado{
	private String paisId;
	private String estadoId;
	private String nombreEstado;
	private String corto;
	
	public CatEstado(){
		paisId 			= "";
		estadoId 		= "";
		nombreEstado	= "";
		corto 			= "-";
	}
	
	public String getPaisId(){
		return paisId;
	}
	
	public void setPaisId( String paisId){
		this.paisId = paisId;
	}
	
	public String getEstadoId(){
		return estadoId;
	}
	
	public void setEstadoId( String estadoId){
		this.estadoId = estadoId;
	}
	
	public String getNombreEstado(){
		return nombreEstado;
	}
	
	public void setNombreEstado( String nombreEstado){
		this.nombreEstado = nombreEstado;
	}
	
	public String getCorto() {
		return corto;
	}

	public void setCorto(String corto) {
		this.corto = corto;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		paisId 			= rs.getString("PAIS_ID");
		estadoId	 	= rs.getString("ESTADO_ID");
		nombreEstado 	= rs.getString("NOMBRE_ESTADO");
		corto 			= rs.getString("CORTO");
	}

}