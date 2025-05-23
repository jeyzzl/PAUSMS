package aca.emp.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class EmpleadoMapper implements RowMapper<Empleado> {
	public Empleado mapRow(ResultSet rs, int arg1) throws SQLException {
		
		Empleado objeto = new Empleado();
		
		objeto.setId(rs.getString("ID"));
		objeto.setClave(rs.getString("CLAVE"));	
		objeto.setNombre(rs.getString("NOMBRE"));
		objeto.setAppaterno(rs.getString("APPATERNO"));	
		objeto.setApmaterno(rs.getString("APMATERNO"));
		objeto.setFechanacimiento(rs.getString("FECHANACIMIENTO"));
		objeto.setDireccion(rs.getString("DIRECCION"));
		objeto.setGenero(rs.getString("GENERO"));
		objeto.setStatus(rs.getString("STATUS"));
		objeto.setNacionalidad(rs.getString("NACIONALIDAD"));
		
		return objeto;
	}
}
