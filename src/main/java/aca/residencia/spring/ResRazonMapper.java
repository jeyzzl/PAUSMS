package aca.residencia.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ResRazonMapper implements RowMapper<ResRazon>{
	
	public ResRazon mapRow(ResultSet rs, int arg1) throws SQLException {
		
		ResRazon objeto = new ResRazon();
		
		objeto.setRazon(rs.getString("RAZON"));
		objeto.setDescripcion(rs.getString("DESCRIPCION"));
		
		return objeto;
	}
}