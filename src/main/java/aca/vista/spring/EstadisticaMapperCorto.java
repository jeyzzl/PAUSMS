package aca.vista.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class EstadisticaMapperCorto implements RowMapper<Estadistica>{

	public Estadistica mapRow(ResultSet rs, int arg1) throws SQLException {
		Estadistica objeto = new Estadistica();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setCargaId(rs.getString("CARGA_ID"));
		objeto.setBloqueId(rs.getString("BLOQUE_ID"));		
		objeto.setNombre(rs.getString("NOMBRE"));
		objeto.setApellidoPaterno(rs.getString("APELLIDO_PATERNO"));
		objeto.setApellidoMaterno(rs.getString("APELLIDO_MATERNO"));		
		objeto.setFechaNacimiento(rs.getString("F_NACIMIENTO"));
		objeto.setCarreraId(rs.getString("CARRERA_ID"));
		objeto.setReligionId(rs.getString("RELIGION_ID"));
		objeto.setResidenciaId(rs.getString("RESIDENCIA_ID"));
		objeto.setSexo(rs.getString("SEXO"));
		objeto.setPlanId(rs.getString("PLAN_ID"));
		objeto.setFacultadId(rs.getString("FACULTAD_ID"));
		objeto.setModalidadId(rs.getString("MODALIDAD_ID"));
		objeto.setPaisId(rs.getString("PAIS_ID"));
		objeto.setEstadoId(rs.getString("ESTADO_ID"));
		objeto.setCiudadId(rs.getString("CIUDAD_ID"));		
		objeto.setTipoAlumnoId(rs.getString("TIPOALUMNO_ID"));
		objeto.setNacionalidad(rs.getString("NACIONALIDAD"));
		objeto.setFechaInscripcion(rs.getString("F_INSCRIPCION"));
		
		return objeto;
	}

}