package aca.musica.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class MusiHorarioAlumnoMapper implements RowMapper<MusiHorarioAlumno> {

	public MusiHorarioAlumno mapRow(ResultSet rs, int rowNum) throws SQLException {
		MusiHorarioAlumno objeto = new MusiHorarioAlumno();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setCursoCargaId(rs.getString("CURSO_CARGA_ID"));
		
		return objeto;
	}

}
