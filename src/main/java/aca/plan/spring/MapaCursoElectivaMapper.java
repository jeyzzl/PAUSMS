package aca.plan.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class MapaCursoElectivaMapper implements RowMapper<MapaCursoElectiva>{

	public MapaCursoElectiva mapRow(ResultSet rs, int arg1) throws SQLException {
		
		MapaCursoElectiva objeto = new MapaCursoElectiva();
		
		objeto.setCursoId(rs.getString("CURSO_ID"));
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setCursoElec(rs.getString("CURSO_ELEC"));
		objeto.setCursoNombre(rs.getString("CURSO_NOMBRE"));
		
		return objeto;
	}

}