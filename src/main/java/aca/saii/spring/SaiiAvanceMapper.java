package aca.saii.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class SaiiAvanceMapper implements RowMapper<SaiiAvance>{
	
	public SaiiAvance mapRow(ResultSet rs, int arg1) throws SQLException {
		
		SaiiAvance objeto = new SaiiAvance();
		
		objeto.setGrupoId(rs.getString("GRUPO_ID"));
		objeto.setPorcentaje(rs.getInt("PORCENTAJE"));
				
		return objeto;
	}
}