// Bean del Catalogo de Religiones
package aca.catalogo;

import java.sql.*;

public class CatEstrategia{
	private String estrategiaId;	
	private String nombreEstrategia;
	
	public CatEstrategia(){
		estrategiaId 		= "";		
		nombreEstrategia	= "";
	}
	
	public String getEstrategiaId(){
		return estrategiaId;
	}
	
	public void setEstrategiaId( String estrategiaId){
		this.estrategiaId = estrategiaId;
	}
	
	public String getNombreEstrategia(){
		return nombreEstrategia;
	}
	
	public void setNombreEstrategia( String nombreEstrategia){
		this.nombreEstrategia = nombreEstrategia;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		estrategiaId 		= rs.getString("ESTRATEGIA_ID");
		nombreEstrategia 	= rs.getString("NOMBRE_ESTRATEGIA");		
	}

}