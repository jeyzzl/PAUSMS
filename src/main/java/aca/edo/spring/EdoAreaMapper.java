package aca.edo.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class EdoAreaMapper implements RowMapper<EdoArea> {
	public EdoArea mapRow(ResultSet rs, int arg1) throws SQLException {
		
		EdoArea objeto = new EdoArea();
		
		objeto.setAreaId(rs.getString("AREA_ID"));
		objeto.setAreaNombre(rs.getString("AREA_NOMBRE"));
		objeto.setAreaTitulo(rs.getString("AREA_TITULO"));
		
		return objeto;
	}
}