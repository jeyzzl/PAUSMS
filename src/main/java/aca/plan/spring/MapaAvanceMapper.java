package aca.plan.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class MapaAvanceMapper implements RowMapper<MapaAvance>{

	public MapaAvance mapRow(ResultSet rs, int arg1) throws SQLException {
		MapaAvance objeto = new MapaAvance();
		
		objeto.setPlanId(rs.getString("PLAN_ID"));
		objeto.setCiclo(rs.getString("CICLO"));
		objeto.setTipocursoId(rs.getString("TIPOCURSO_ID"));
		objeto.setCreditos(rs.getString("CREDITOS"));
		
		return objeto;
	}

}
