package aca.vista.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class UsuariosMapper implements RowMapper<Usuarios>{

	public Usuarios mapRow(ResultSet rs, int arg1) throws SQLException {
		Usuarios objeto = new Usuarios();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setNombre(rs.getString("NOMBRE"));
		objeto.setApellidoPaterno(rs.getString("APELLIDO_PATERNO"));
		objeto.setApellidoMaterno(rs.getString("APELLIDO_MATERNO"));
		objeto.setfNacimiento(rs.getString("F_NACIMIENTO"));
		objeto.setGenero(rs.getString("GENERO"));
		objeto.setUsuario(rs.getString("USUARIO"));
		objeto.setClave(rs.getString("CLAVE"));
		objeto.setEstado(rs.getString("ESTADO"));
		
		return objeto;
	}

}
