package aca.plan.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class MapaNuevoActividadMapper implements RowMapper<MapaNuevoActividad>{

	public MapaNuevoActividad mapRow(ResultSet rs, int arg1) throws SQLException {
		MapaNuevoActividad objeto = new MapaNuevoActividad();
		
		objeto.setCursoId(rs.getString("CURSO_ID"));
		objeto.setUnidadId(rs.getString("UNIDAD_ID"));
		objeto.setActividadId(rs.getString("ACTIVIDAD_ID"));
		objeto.setObjetivo(rs.getString("OBJETIVO"));
		objeto.setDescripcion(rs.getString("DESCRIPCION"));
		objeto.setCriterio(rs.getString("CRITERIO"));
		objeto.setTipo(rs.getString("TIPO"));

		return objeto;
	}

}
