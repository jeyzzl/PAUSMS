package aca.vista.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class MaestrosMapper implements RowMapper<Maestros>{

	public Maestros mapRow(ResultSet rs, int arg1) throws SQLException {
		Maestros objeto = new Maestros();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setNombre(rs.getString("NOMBRE"));
		objeto.setApellidoPaterno(rs.getString("APELLIDO_PATERNO"));
		objeto.setApellidoMaterno(rs.getString("APELLIDO_MATERNO"));
		objeto.setfNacimiento(rs.getString("F_NACIMIENTO"));
		objeto.setGenero(rs.getString("GENERO"));
		objeto.setEstadocivil(rs.getString("ESTADOCIVIL"));
		objeto.setTelefono(rs.getString("TELEFONO"));
		objeto.setEmail(rs.getString("EMAIL"));
		objeto.setEstado(rs.getString("ESTADO"));

		return objeto;
	}

}
