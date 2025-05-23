package aca.bec.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class BecCompetenciaMapper implements RowMapper<BecCompetencia> {
	
	@Override
	public BecCompetencia mapRow(ResultSet rs, int arg1) throws SQLException {
		
		BecCompetencia objeto = new BecCompetencia();
		
		objeto.setCompetenciaId(rs.getString("COMPETENCIA_ID"));
		objeto.setCompetenciaNombre(rs.getString("COMPETENCIA_NOMBRE"));
		
		return objeto;
	}
}
