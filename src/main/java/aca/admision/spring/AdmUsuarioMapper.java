package aca.admision.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AdmUsuarioMapper implements RowMapper<AdmUsuario> {

	public AdmUsuario mapRow(ResultSet rs, int arg1) throws SQLException {
		AdmUsuario objeto = new AdmUsuario();
		
		objeto.setUsuarioId(rs.getString("USUARIO_ID"));
		objeto.setNombre(rs.getString("NOMBRE"));
		objeto.setApellidoPaterno(rs.getString("APELLIDO_PATERNO"));
		objeto.setApellidoMaterno(rs.getString("APELLIDO_MATERNO"));
		objeto.setFechaNac(rs.getString("FECHA_NAC"));
		objeto.setEmail(rs.getString("EMAIL"));
		objeto.setCuenta(rs.getString("CUENTA"));
		objeto.setClave(rs.getString("CLAVE"));
		objeto.setMatricula(rs.getString("MATRICULA"));
		objeto.setTelefono(rs.getString("TELEFONO"));
		objeto.setPaisId(rs.getString("PAIS_ID"));
		objeto.setEstadoId(rs.getString("ESTADO_ID"));
		objeto.setCiudadId(rs.getString("CIUDAD_ID"));
		objeto.setGenero(rs.getString("GENERO"));
		objeto.setEstadoCivil(rs.getString("ESTADOCIVIL"));
		objeto.setReligionId(rs.getString("RELIGION_ID"));
		objeto.setFecha(rs.getString("FECHA"));	
		objeto.setEstado(rs.getString("ESTADO"));	
		objeto.setCodigo(rs.getString("CODIGO"));
		objeto.setNacionalidad(rs.getString("NACIONALIDAD"));
		
		return objeto;
	}

}