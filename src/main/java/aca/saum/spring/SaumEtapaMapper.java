package aca.saum.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


public class SaumEtapaMapper implements RowMapper<SaumEtapa>{
	public SaumEtapa mapRow(ResultSet rs, int arg1) throws SQLException {
		
		SaumEtapa objeto = new SaumEtapa();
		
		objeto.setId(rs.getString("ID"));
		objeto.setVersion(rs.getString("VERSION"));
		objeto.setNombre(rs.getString("NOMBRE"));
		objeto.setProcedimiento(rs.getString("PROCEDIMIENTO"));	
		objeto.setRecetaId(rs.getString("RECETA_ID"));
		
		return objeto;
	}
}