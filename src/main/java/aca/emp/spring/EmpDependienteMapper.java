package aca.emp.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class EmpDependienteMapper implements RowMapper<EmpDependiente> {	
	public EmpDependiente mapRow(ResultSet rs, int arg1) throws SQLException {
		
		EmpDependiente objeto = new EmpDependiente();
		
		objeto.setIdEmpleado(rs.getString("ID_EMPLEADO"));
		objeto.setIdDependiente(rs.getString("ID_DEPENDIENTE"));
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setRelacion(rs.getString("RELACION"));
		objeto.setCotejado(rs.getString("COTEJADO"));
		
		return objeto;
	}
}
