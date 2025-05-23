package aca.saii.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class SaiiGrupoMapper implements RowMapper<SaiiGrupo>{
	
	public SaiiGrupo mapRow(ResultSet rs, int arg1) throws SQLException {
		
		SaiiGrupo objeto = new SaiiGrupo();
		
		objeto.setGrupoId(rs.getString("GRUPO_ID"));
		objeto.setPeriodoId(rs.getString("PERIODO_ID"));
		objeto.setPlanId(rs.getString("PLAN_ID"));
				
		return objeto;
	}
}