// Bean del Catalogo de Descuentos
package  aca.catalogo;

import java.sql.*;

public class CatDescuento{
	private String descuentoId;	
	private String descuentoNombre;
	private String usuarios;
	
	public CatDescuento(){
		descuentoId 		= "";
		descuentoNombre		= "";
		usuarios			= "";
	}
	
	public String getDescuentoId() {
		return descuentoId;
	}

	public void setDescuentoId(String descuentoId) {
		this.descuentoId = descuentoId;
	}

	public String getDescuentoNombre() {
		return descuentoNombre;
	}

	public void setDescuentoNombre(String descuentoNombre) {
		this.descuentoNombre = descuentoNombre;
	}

	public String getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(String usuarios) {
		this.usuarios = usuarios;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		descuentoId 		= rs.getString("DESCUENTO_ID");
		descuentoNombre 	= rs.getString("DESCUENTO_NOMBRE");
		usuarios 			= rs.getString("USUARIOS");
	}
	
}