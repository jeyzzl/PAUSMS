// Bean del Catalogo de Edificios
package  aca.catalogo;

import java.sql.*;

public class CatEdificio{
	private String edificioId;	
	private String nombreEdificio;
	private String usuarios;
	
	public CatEdificio(){
		edificioId 		= "";		
		nombreEdificio	= "";
		usuarios		= "";
	}
	
	public String getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(String usuarios) {
		this.usuarios = usuarios;
	}

	public String getEdificioId(){
		return edificioId;
	}
	
	public void setEdificioId( String edificioId){
		this.edificioId = edificioId;
	}
	
	public String getNombreEdificio(){
		return nombreEdificio;
	}
	
	public void setNombreEdificio( String nombreEdificio){
		this.nombreEdificio = nombreEdificio;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		edificioId 			= rs.getString("EDIFICIO_ID");
		nombreEdificio 		= rs.getString("NOMBRE_EDIFICIO");
		usuarios 			= rs.getString("USUARIOS");
	}
	
}