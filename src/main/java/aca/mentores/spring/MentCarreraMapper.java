package aca.mentores.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class MentCarreraMapper implements RowMapper<MentCarrera>{

	public MentCarrera mapRow(ResultSet rs, int arg1) throws SQLException {
		MentCarrera objeto = new MentCarrera();
		
		objeto.setCarreraId(rs.getString("CARRERA_ID"));
		objeto.setMentorId(rs.getString("MENTOR_ID"));
		objeto.setPeriodoId(rs.getString("PERIODO_ID"));

		return objeto;
	}

}
