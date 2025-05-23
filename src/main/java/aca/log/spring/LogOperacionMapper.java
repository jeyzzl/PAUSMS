package aca.log.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class LogOperacionMapper implements RowMapper<LogOperacion> {

	public LogOperacion mapRow(ResultSet rs, int rowNum) throws SQLException {
		LogOperacion objeto = new LogOperacion();
		
		objeto.setTabla(rs.getString("TABLA"));		
		objeto.setOperacion(rs.getString("OPERACION"));
		objeto.setIp(rs.getString("IP"));
		objeto.setFecha(rs.getString("FECHA"));		
		objeto.setUsuario(rs.getString("USUARIO"));
		objeto.setDatos(rs.getString("DATOS"));
		
		return objeto;
	}

}
