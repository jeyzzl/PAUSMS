package aca.podium.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ExamenAreaMapper implements RowMapper<ExamenArea>{
	
	public ExamenArea mapRow(ResultSet rs, int arg1) throws SQLException {	
		
		ExamenArea objeto = new ExamenArea();
		
		objeto.setId(rs.getInt("ID"));
		objeto.setTermino(rs.getBoolean("TERMINO"));		
		objeto.setActivo(rs.getBoolean("ACTIVO"));		
		objeto.setAreaId(rs.getInt("AREA_ID"));
		objeto.setExamenId(rs.getInt("EXAMEN_ID"));
		
		return objeto;
	}
}