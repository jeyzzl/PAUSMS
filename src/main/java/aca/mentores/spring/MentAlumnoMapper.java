package aca.mentores.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class MentAlumnoMapper implements RowMapper<MentAlumno> {
	public MentAlumno mapRow(ResultSet rs, int arg1) throws SQLException {
		
		MentAlumno objeto = new MentAlumno();
		
		objeto.setCodigoPersonal(rs.getString("PERIODO_ID"));
		objeto.setMentorId(rs.getString("MENTOR_ID"));
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setStatus(rs.getString("STATUS"));
		objeto.setCarreraId(rs.getString("CARRERA_ID"));
		objeto.setFechaInicio(rs.getString("FECHA_INICIO"));
		objeto.setFechaFinal(rs.getString("FECHA_FINAL"));
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setCargaId(rs.getString("CARGA_ID"));
		
		return objeto;
	}
}