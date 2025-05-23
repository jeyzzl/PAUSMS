package aca.vista.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class EstadisticaMapper implements RowMapper<Estadistica>{

	public Estadistica mapRow(ResultSet rs, int arg1) throws SQLException {
		Estadistica objeto = new Estadistica();
		
		objeto.setCargaId(rs.getString("CARGA_ID"));
		objeto.setBloqueId(rs.getString("BLOQUE_ID"));
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setNombre(rs.getString("NOMBRE"));
		objeto.setApellidoPaterno(rs.getString("APELLIDO_PATERNO"));
		objeto.setApellidoMaterno(rs.getString("APELLIDO_MATERNO"));
		objeto.setNombreLegal(rs.getString("NOMBRE_LEGAL"));
		objeto.setCotejado(rs.getString("COTEJADO"));
		objeto.setFechaNacimiento(rs.getString("F_NACIMIENTO"));
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
		objeto.setEstado(rs.getString("ESTADO"));
		objeto.setPlanId(rs.getString("PLAN_ID"));
		objeto.setCarreraId(rs.getString("CARRERA_ID"));
		objeto.setFacultadId(rs.getString("FACULTAD_ID"));
		objeto.setFechaInscripcion(rs.getString("F_INSCRIPCION"));
		objeto.setCredAlta(rs.getString("CRED_ALTA"));
		objeto.setCredBaja(rs.getString("CRED_BAJA"));
		objeto.setTipoAlumnoId(rs.getString("TIPOALUMNO_ID"));
		
		return objeto;
	}

}