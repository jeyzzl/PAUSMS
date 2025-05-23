package aca.vista.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class MenCarreraMapper implements RowMapper<MenCarrera>{

	public MenCarrera mapRow(ResultSet rs, int arg1) throws SQLException {
		MenCarrera objeto = new MenCarrera();
		
		objeto.setPeriodoId(rs.getString("PERIODO_ID"));
		objeto.setFacultadId(rs.getString("FACULTAD_ID"));
		objeto.setFacultadNombre(rs.getString("FACULTAD_NOMBRE"));
		objeto.setCarreraId(rs.getString("CARRERA_ID"));
		objeto.setCarreraNombre(rs.getString("CARRERA_NOMBRE"));
		objeto.setMentorId(rs.getString("MENTOR_ID"));
		objeto.setMentorNombre(rs.getString("MENTOR_NOMBRE"));
		
		return objeto;
	}

}
