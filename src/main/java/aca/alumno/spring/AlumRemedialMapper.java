package aca.alumno.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AlumRemedialMapper implements RowMapper<AlumRemedial> {

	@Override
	public AlumRemedial mapRow(ResultSet rs, int rowNum) throws SQLException {
		AlumRemedial objeto = new AlumRemedial();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setCursoId(rs.getString("CURSO_ID"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setEstado(rs.getString("ESTADO"));
		
		return objeto;
	}

}
