package aca.plan.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class MapaCursoPreMapper implements RowMapper<MapaCursoPre>{

	public MapaCursoPre mapRow(ResultSet rs, int arg1) throws SQLException {
		MapaCursoPre objeto = new MapaCursoPre();
		
		objeto.setCursoId(rs.getString("CURSO_ID"));
		objeto.setCursoIdPre(rs.getString("CURSO_ID_PRE"));
		
		return objeto;
	}

}
