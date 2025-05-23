package aca.plan.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class MapaNuevoUnidadMapper implements RowMapper<MapaNuevoUnidad>{

	public MapaNuevoUnidad mapRow(ResultSet rs, int arg1) throws SQLException {
		MapaNuevoUnidad objeto = new MapaNuevoUnidad();
		
		objeto.setCursoId(rs.getString("CURSO_ID"));
		objeto.setUnidadId(rs.getString("UNIDAD_ID"));
		objeto.setNombre(rs.getString("NOMBRE"));
		objeto.setTiempo(rs.getString("TIEMPO"));
		objeto.setTemas(rs.getString("TEMAS"));
		objeto.setAccionDocente(rs.getString("ACCION_DOCENTE"));
		objeto.setOrden(rs.getString("ORDEN"));
		
		return objeto;
	}

}
