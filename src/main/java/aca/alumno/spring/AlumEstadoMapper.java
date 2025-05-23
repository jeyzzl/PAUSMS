package aca.alumno.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AlumEstadoMapper implements RowMapper<AlumEstado>{
	@Override
	public AlumEstado mapRow(ResultSet rs, int arg1) throws SQLException {
		
		AlumEstado objeto = new AlumEstado();	
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setCargaId(rs.getString("CARGA_ID"));
		objeto.setBloqueId(rs.getString("BLOQUE_ID"));
		objeto.setEstado(rs.getString("ESTADO"));
		objeto.setModalidadId(rs.getString("MODALIDAD_ID"));
		objeto.setTipoalumnoId(rs.getString("TIPOALUMNO_ID"));
		objeto.setFacultadId(rs.getString("FACULTAD_ID"));
		objeto.setCarreraId(rs.getString("CARRERA_ID"));
		objeto.setPlanId(rs.getString("PLAN_ID"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setResidenciaId(rs.getString("RESIDENCIA_ID"));
		objeto.setDormitorio(rs.getString("DORMITORIO"));
		objeto.setCiclo(rs.getString("CICLO"));
		objeto.setGrado(rs.getString("GRADO"));
		objeto.setClasFin(rs.getString("CLAS_FIN"));
		
		return objeto;
	}
}
