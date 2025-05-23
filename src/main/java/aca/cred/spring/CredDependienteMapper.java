package aca.cred.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CredDependienteMapper implements RowMapper<CredDependiente> {
	
	public CredDependiente mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CredDependiente objeto = new CredDependiente();
		
		objeto.setIdDependiente(rs.getString("ID_DEPENDIENTE"));
		objeto.setIdEmpleado(rs.getString("ID_EMPLEADO"));
		objeto.setEmpNombre(rs.getString("EMP_NOMBRE"));
		objeto.setEmpApellidos(rs.getString("EMP_APELLIDOS"));
		objeto.setRelacion(rs.getString("RELACION"));
		objeto.setPuesto(rs.getString("PUESTO"));
		objeto.setDepartamento(rs.getString("DEPARTAMENTO"));
		objeto.setCotejado(rs.getString("COTEJADO"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setfActualiza(rs.getString("F_ACTUALIZA"));
		objeto.setDepNombre(rs.getString("DEP_NOMBRE"));
		objeto.setDepApellidos(rs.getString("DEP_APELLIDOS"));
		
		return objeto;
	}

}
