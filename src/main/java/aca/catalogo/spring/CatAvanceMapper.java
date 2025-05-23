package aca.catalogo.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CatAvanceMapper implements RowMapper<CatAvance> {
	public CatAvance mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CatAvance avance = new CatAvance();
		
		avance.setAvanceId(rs.getString("AVANCE_ID"));
		avance.setNombreAvance(rs.getString("NOMBRE_AVANCE"));	
		
		return avance;
	}
}
