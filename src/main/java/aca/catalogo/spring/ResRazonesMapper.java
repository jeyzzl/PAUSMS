package aca.catalogo.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ResRazonesMapper implements RowMapper<ResRazones> {

	public ResRazones mapRow(ResultSet rs, int rowNum) throws SQLException {
		ResRazones objeto = new ResRazones();
		
		objeto.setRazon(rs.getInt("RAZON"));
		objeto.setDescripcion(rs.getString("DESCRIPCION"));
		
		return objeto;
	}

}
