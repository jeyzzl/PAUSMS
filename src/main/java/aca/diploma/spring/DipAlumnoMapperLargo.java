package aca.diploma.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class DipAlumnoMapperLargo implements RowMapper<DipAlumno> {
	public DipAlumno mapRow(ResultSet rs, int arg1) throws SQLException {
		
		DipAlumno objeto = new DipAlumno();
		
		objeto.setDiplomaId(rs.getString("DIPLOMA_ID"));
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setNombre(rs.getString("NOMBRE"));
		objeto.setImagenQr(rs.getBytes("IMAGEN_QR"));
		
		return objeto;
	}
}