package aca.vista.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AlumnoMapper implements RowMapper<Alumno>{

	public Alumno mapRow(ResultSet rs, int arg1) throws SQLException {
		Alumno objeto = new Alumno();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setNombre(rs.getString("NOMBRE"));
		objeto.setApellidoPaterno(rs.getString("APELLIDO_PATERNO"));
		objeto.setApellidoMaterno(rs.getString("APELLIDO_MATERNO"));
		objeto.setNombreLegal(rs.getString("NOMBRE_LEGAL"));
		objeto.setCotejado(rs.getString("COTEJADO"));
		objeto.setfNacimiento(rs.getString("F_NACIMIENTO"));
		objeto.setSexo(rs.getString("SEXO"));
		objeto.setEstadoCivil(rs.getString("ESTADO_CIVIL"));
		objeto.setReligionId(rs.getString("RELIGION_ID"));
		objeto.setPaisId(rs.getString("PAIS_ID"));
		objeto.setEstadoId(rs.getString("ESTADO_ID"));
		objeto.setCiudadId(rs.getString("CIUDAD_ID"));
		objeto.setNacionalidad(rs.getString("NACIONALIDAD"));
		objeto.setCurp(rs.getString("CURP"));
		objeto.setModalidadId(rs.getString("MODALIDAD_ID"));
		objeto.setClasFin(rs.getString("CLAS_FIN"));
		objeto.setResidenciaId(rs.getString("RESIDENCIA_ID"));
		objeto.setPlanId(rs.getString("PLAN_ID"));
		objeto.setCarreraId(rs.getString("CARRERA_ID"));
		objeto.setCorreo(rs.getString("CORREO"));
		objeto.setTelefono(rs.getString("TELEFONO"));

		return objeto;
	}

}
