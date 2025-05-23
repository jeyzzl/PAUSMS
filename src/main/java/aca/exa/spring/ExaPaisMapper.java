package aca.exa.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ExaPaisMapper implements RowMapper<ExaPais>{
	
	public ExaPais mapRow(ResultSet rs, int arg1) throws SQLException{
		
		ExaPais objeto = new ExaPais();	
		
		objeto.setPaisId(rs.getString("PAIS_ID"));
		objeto.setPaisNombre(rs.getString("PAIS_NOMBRE"));
		
		return objeto;	
	}
}