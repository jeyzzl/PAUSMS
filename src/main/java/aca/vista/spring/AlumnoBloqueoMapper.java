package aca.vista.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AlumnoBloqueoMapper implements RowMapper<AlumnoBloqueo>{

	public AlumnoBloqueo mapRow(ResultSet rs, int arg1) throws SQLException {
		AlumnoBloqueo objeto = new AlumnoBloqueo();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setCargaId(rs.getString("CARGA_ID"));
		objeto.setModalidadId(rs.getString("MODALIDAD_ID"));
		objeto.setFacultadId(rs.getString("FACULTAD_ID"));
		objeto.setCarreraId(rs.getString("CARRERA_ID"));
		
		return objeto;
	}

}
