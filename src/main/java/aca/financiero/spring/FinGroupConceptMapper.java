package aca.financiero.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class FinGroupConceptMapper implements RowMapper<FinGroupConcept>{
	
	public FinGroupConcept mapRow(ResultSet rs, int arg1) throws SQLException {
		
		FinGroupConcept objeto = new FinGroupConcept();
		
		objeto.setGroupId(rs.getInt("GROUP_ID"));
		objeto.setConcId(rs.getInt("CONC_ID"));
		objeto.setNoUnits(rs.getInt("NO_UNITS"));
		
		return objeto;
	}
}