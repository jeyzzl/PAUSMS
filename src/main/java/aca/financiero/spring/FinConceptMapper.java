package aca.financiero.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class FinConceptMapper implements RowMapper<FinConcept>{
	
	public FinConcept mapRow(ResultSet rs, int arg1) throws SQLException {
		
		FinConcept objeto = new FinConcept();
		
		objeto.setConcId(rs.getInt("CONC_ID"));
		objeto.setName(rs.getString("NAME"));
		objeto.setUnitCost(rs.getDouble("UNIT_COST"));
		objeto.setType(rs.getString("TYPE"));	
		objeto.setStatus(rs.getString("STATUS"));
		objeto.setCursoClave(rs.getString("CURSO_CLAVE"));
		objeto.setCode(rs.getString("CODE"));
		
		return objeto;
	}
}