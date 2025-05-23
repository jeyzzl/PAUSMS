package aca.financiero.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class FinGroupMapper implements RowMapper<FinGroup>{
	
	public FinGroup mapRow(ResultSet rs, int arg1) throws SQLException {
		
		FinGroup objeto = new FinGroup();
		
		objeto.setGroupId(rs.getInt("GROUP_ID"));
		objeto.setName(rs.getString("NAME"));
		objeto.setDescription(rs.getString("DESCRIPTION"));
		
		return objeto;
	}
}