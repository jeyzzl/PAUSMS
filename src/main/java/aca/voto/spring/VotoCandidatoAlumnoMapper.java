package aca.voto.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class VotoCandidatoAlumnoMapper implements RowMapper<VotoCandidatoAlumno>{

	public VotoCandidatoAlumno mapRow(ResultSet rs, int arg1) throws SQLException {
		VotoCandidatoAlumno objeto = new VotoCandidatoAlumno();
		
		objeto.setEventoId(rs.getString("EVENTO_ID"));
		objeto.setCandidatoId(rs.getString("CANDIDATO_ID"));
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setFacultadId(rs.getString("FACULTAD_ID"));
		objeto.setOrden(rs.getString("ORDEN"));
		
		return objeto;
	}

}
