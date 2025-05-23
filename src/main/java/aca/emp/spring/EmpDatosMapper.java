package aca.emp.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class EmpDatosMapper implements RowMapper<EmpDatos>{
	
	public EmpDatos mapRow(ResultSet rs, int arg1) throws SQLException {
		
		EmpDatos objeto = new EmpDatos();
		
		objeto.setIdEmpleado(rs.getString("ID_EMPLEADO"));
		objeto.setPuesto(rs.getString("PUESTO"));
		objeto.setDepartamento(rs.getString("DEPARTAMENTO"));
		objeto.setStatus(rs.getString("STATUS"));
		objeto.setCotejado(rs.getString("COTEJADO"));
		objeto.setImpreso(rs.getString("IMPRESO"));
		objeto.setId(rs.getString("ID"));
		objeto.setNombre(rs.getString("NOMBRE"));
		objeto.setApellidos(rs.getString("APELLIDOS"));
		objeto.setTipocred(rs.getString("TIPOCRED"));
		
		return objeto;
	}
}