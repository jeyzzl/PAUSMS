package aca.voto.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class VotoAlumnoMapper implements RowMapper<VotoAlumno>{

	public VotoAlumno mapRow(ResultSet rs, int arg1) throws SQLException {
		VotoAlumno objeto = new VotoAlumno();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setEventoId(rs.getString("EVENTO_ID"));
		objeto.setCandidatoId(rs.getString("CANDIDATO_ID"));
		objeto.setVoto(rs.getString("VOTO"));
		objeto.setFecha(rs.getString("FECHA"));
		
		return objeto;
	}

}
