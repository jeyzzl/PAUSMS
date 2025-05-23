package aca.alumno.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AlumImagenMapper implements RowMapper<AlumImagen>{
	@Override
	public AlumImagen mapRow(ResultSet rs, int arg1) throws SQLException {
		
		AlumImagen objeto = new AlumImagen();		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setConsentimiento(rs.getString("CONSENTIMIENTO"));
		objeto.setTipo(rs.getString("TIPO"));
		objeto.setFecha(rs.getString("FECHA"));
		
		return objeto;
	}
}