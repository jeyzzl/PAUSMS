package aca.musica.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class MusiAlumnoMapper implements RowMapper<MusiAlumno> {
	
	@Override
	public MusiAlumno mapRow(ResultSet rs, int rowNum) throws SQLException {
		MusiAlumno objeto = new MusiAlumno();
		
		objeto.setCodigoId(rs.getString("CODIGO_ID"));
		objeto.setNombre(rs.getString("NOMBRE"));
		objeto.setApellidoPaterno(rs.getString("APELLIDO_PATERNO"));
		objeto.setApellidoMaterno(rs.getString("APELLIDO_MATERNO"));
		objeto.setFechaNac(rs.getString("FECHA_NAC"));
		objeto.setInstitucionId(rs.getString("INSTITUCION_ID"));
		objeto.setSeguro(rs.getString("SEGURO"));
		objeto.setTutor(rs.getString("TUTOR"));
		objeto.setTelefono(rs.getString("TELEFONO"));
		objeto.setCelular(rs.getString("CELULAR"));
		objeto.setEmail(rs.getString("EMAIL"));
		objeto.setCodigoUM(rs.getString("CODIGO_UM"));
		objeto.setComentario(rs.getString("COMENTARIO"));
		objeto.setEmpleado(rs.getString("EMPLEADO"));
		objeto.setReligionId(rs.getString("RELIGION_ID"));
		objeto.setCiudadId(rs.getString("CIUDAD_ID"));
		objeto.setEstadoId(rs.getString("ESTADO_ID"));
		objeto.setPaisId(rs.getString("PAIS_ID"));
		objeto.setNacionalidad(rs.getString("NACIONALIDAD"));
		objeto.setTelTrabajo(rs.getString("TEL_TRABAJO"));
		objeto.setEstado(rs.getString("ESTADO"));
		objeto.setGenero(rs.getString("GENERO"));
		
		return objeto;
	}
	

}
