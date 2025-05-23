package aca.area.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AreaMapper implements RowMapper<Area>{
	
	@Override
	public Area mapRow(ResultSet rs, int arg1) throws SQLException {		
		Area objeto = new Area();
		objeto.setAreaId(rs.getString("AREA_ID"));
		objeto.setAreaNombre(rs.getString("AREA_NOMBRE"));
		objeto.setResponsable(rs.getString("RESPONSABLE"));
		
		return objeto;
	}
}