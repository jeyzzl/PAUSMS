package aca.catalogo.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CatEjeMapper implements RowMapper<CatEje> {
	public CatEje mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CatEje objeto = new CatEje();
		
		objeto.setEjeId(rs.getString("EJE_ID"));
		objeto.setEjeNombre(rs.getString("EJE_NOMBRE"));	
		objeto.setDescripcion(rs.getString("DESCRIPCION"));
		objeto.setNivelId(rs.getString("NIVEL_ID"));
		objeto.setOrden(rs.getString("ORDEN"));
		
		return objeto;
	}
}
