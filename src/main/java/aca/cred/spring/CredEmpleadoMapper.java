package aca.cred.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CredEmpleadoMapper implements RowMapper<CredEmpleado> {
	public CredEmpleado mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CredEmpleado objeto = new CredEmpleado();
		
		objeto.setId(rs.getString("ID"));
		objeto.setClave(rs.getString("CLAVE"));
		objeto.setNombre(rs.getString("NOMBRE"));
		objeto.setApellidos(rs.getString("APELLIDOS"));
		objeto.setPuesto(rs.getString("PUESTO"));
		objeto.setDepartamento(rs.getString("DEPARTAMENTO"));
		objeto.setStatus(rs.getString("STATUS"));
		objeto.setCotejado(rs.getString("COTEJADO"));
		objeto.setImprimir(rs.getString("IMPRIMIR"));
		objeto.setTipoCred(rs.getString("TIPOCRED"));
		
		return objeto;
	}
}