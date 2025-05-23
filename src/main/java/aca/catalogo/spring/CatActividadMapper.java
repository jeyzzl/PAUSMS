package aca.catalogo.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CatActividadMapper implements RowMapper<CatActividad> {
	@Override
	public CatActividad mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CatActividad area = new CatActividad();		
		area.setActividadId(rs.getString("ACTIVIDAD_ID"));
		area.setDescripcion(rs.getString("DESCRIPCION"));	
		
		return area;
	}
}
