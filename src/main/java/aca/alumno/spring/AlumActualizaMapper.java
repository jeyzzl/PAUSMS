package aca.alumno.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AlumActualizaMapper implements RowMapper<AlumActualiza>{
	@Override
	public AlumActualiza mapRow(ResultSet rs, int arg1) throws SQLException {
		
		AlumActualiza objeto = new AlumActualiza();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setCodigoEmpleado(rs.getString("CODIGO_EMPLEADO"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setEstado(rs.getString("ESTADO"));
		
		return objeto;
	}
}
