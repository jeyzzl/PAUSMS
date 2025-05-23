package aca.vista.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class FacultadMentorMapper implements RowMapper<FacultadMentor>{

	public FacultadMentor mapRow(ResultSet rs, int arg1) throws SQLException {
		FacultadMentor objeto = new FacultadMentor();

		objeto.setIdMentor(rs.getString("ID_MENTOR"));
		objeto.setFacultadId(rs.getString("FACULTAD_ID"));
		objeto.setNombreFacultad(rs.getString("NOMBRE_FACULTAD"));
				
		return objeto;
	}

}