package aca.plan.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class MapaNuevoBiblioUnidadMapper implements RowMapper<MapaNuevoBiblioUnidad>{

	public MapaNuevoBiblioUnidad mapRow(ResultSet rs, int arg1) throws SQLException {
		MapaNuevoBiblioUnidad objeto = new MapaNuevoBiblioUnidad();
		
		objeto.setCursoId(rs.getString("CURSO_ID"));
		objeto.setUnidadId(rs.getString("UNIDAD_ID"));
		objeto.setBibliografiaId(rs.getString("BIBLIOGRAFIA_ID"));
		objeto.setId(rs.getString("ID"));
		objeto.setEspecificacion(rs.getString("ESPECIFICACION"));
		
		return objeto;
	}

}