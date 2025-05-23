package aca.emp.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class EmpContactoMapper implements RowMapper<EmpContacto> {
	public EmpContacto mapRow(ResultSet rs, int arg1) throws SQLException {
		
		EmpContacto objeto = new EmpContacto();		
		objeto.setEmpleadoId(rs.getString("EMPLEADO_ID"));
		objeto.setTelefono(rs.getString("TELEFONO"));
		objeto.setCorreo(rs.getString("CORREO"));
		
		return objeto;
	}
}
