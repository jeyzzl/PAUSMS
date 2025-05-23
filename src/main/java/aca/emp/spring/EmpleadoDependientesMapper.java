package aca.emp.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class EmpleadoDependientesMapper implements RowMapper<EmpleadoDependientes> {	
	public EmpleadoDependientes mapRow(ResultSet rs, int arg1) throws SQLException {
		
		EmpleadoDependientes objeto = new EmpleadoDependientes();
		
		objeto.setId(rs.getString("ID"));
		objeto.setEmpleadoId(rs.getString("EMPLEADO_ID"));	
		objeto.setNombre(rs.getString("NOMBRE"));
		objeto.setBday(rs.getString("BDAY"));	
		objeto.setRelacionId(rs.getString("RELACION_ID"));
		objeto.setMatricula(rs.getString("MATRICULA"));
		
		return objeto;
	}
}
