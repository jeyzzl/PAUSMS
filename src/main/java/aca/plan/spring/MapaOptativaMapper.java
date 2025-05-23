package aca.plan.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class MapaOptativaMapper implements RowMapper<MapaOptativa>{

	public MapaOptativa mapRow(ResultSet rs, int arg1) throws SQLException {
		MapaOptativa objeto = new MapaOptativa();
		
		objeto.setCursoId(rs.getString("CURSO_ID"));
		objeto.setOptativaId(rs.getString("OPTATIVA_ID"));		
		
		return objeto;
	}

}
