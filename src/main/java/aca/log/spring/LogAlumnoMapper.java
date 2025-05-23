package aca.log.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class LogAlumnoMapper implements RowMapper<LogAlumno> {

	public LogAlumno mapRow(ResultSet rs, int rowNum) throws SQLException {
		LogAlumno objeto = new LogAlumno();
		
		objeto.setId(rs.getString("ID"));		
		objeto.setTabla(rs.getString("TABLA"));
		objeto.setOperacion(rs.getString("OPERACION"));
		objeto.setIp(rs.getString("IP"));
		objeto.setFecha(rs.getString("FECHA"));		
		objeto.setUsuario(rs.getString("USUARIO"));
		objeto.setDatos(rs.getString("DATOS"));
		
		return objeto;
	}

}
