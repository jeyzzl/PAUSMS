// Bean del Catalogo de Niveles
package  aca.catalogo;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CatNivel{
	private String nivelId;
	private String nombreNivel;
	private String orden;
	private String estado;
		
	public CatNivel(){
		nivelId		= "";
		nombreNivel	= "";
		orden		= "";
		estado		= "";
	}
	
	public String getOrden() {
		return orden;
	}

	public void setOrden(String orden) {
		this.orden = orden;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getNivelId(){
		return nivelId;
	}
	
	public void setNivelId( String nivelId){
		this.nivelId = nivelId;
	}	
	
	public String getNombreNivel(){
		return nombreNivel;
	}
	
	public void setNombreNivel( String nombreNivel){
		this.nombreNivel = nombreNivel;
	}		
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		nivelId 		= rs.getString("NIVEL_ID");
		nombreNivel 	= rs.getString("NOMBRE_NIVEL");	
		estado	 		= rs.getString("ESTADO");
		orden		 	= rs.getString("ORDEN");	
	}
	
}