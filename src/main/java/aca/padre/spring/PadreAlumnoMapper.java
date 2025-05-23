package aca.padre.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class PadreAlumnoMapper implements RowMapper<PadreAlumno> {
	public PadreAlumno mapRow(ResultSet rs, int arg1) throws SQLException {
		PadreAlumno objeto = new PadreAlumno();
		
		objeto.setPadreId(rs.getString("PADRE_ID"));
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL")); 		
		objeto.setFechaAlta(rs.getString("FECHA_ALTA"));
		objeto.setFechaAutoriza(rs.getString("FECHA_AUTORIZA"));
		objeto.setEstado(rs.getString("ESTADO")); 	
		return objeto;
	}
}
