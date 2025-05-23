package aca.log.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class LogPoolMapper implements RowMapper<LogPool> {

	public LogPool mapRow(ResultSet rs, int rowNum) throws SQLException {
		LogPool objeto = new LogPool();
		
		objeto.setId(rs.getString("ID"));		
		objeto.setDato(rs.getString("DATO"));
		objeto.setUrl(rs.getString("URL"));
		
		return objeto;
	}

}
