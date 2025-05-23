package aca.alumno.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AlumVacacionMapper implements RowMapper<AlumVacacion>{
	@Override
	public AlumVacacion mapRow(ResultSet rs, int arg1) throws SQLException {
		
		AlumVacacion objeto = new AlumVacacion();		
		objeto.setNivelId(rs.getString("NIVEL_ID"));
		objeto.setModalidadId(rs.getString("MODALIDAD_ID"));
		objeto.setfExamen(rs.getString("F_EXAMEN"));
		objeto.setfInicio(rs.getString("F_INICIO"));	
		objeto.setfFinal(rs.getString("F_FINAL"));	
			
		return objeto;
	}
}