package aca.maestro;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class MaestroRangoFechaMapper implements RowMapper<MaestroRangoFecha> {
	public MaestroRangoFecha mapRow(ResultSet rs, int arg1) throws SQLException {
		
		MaestroRangoFecha objeto = new MaestroRangoFecha();
		
		objeto.setYear(rs.getString("YEAR"));	
		objeto.setFecha(rs.getString("FECHA"));
		
		return objeto;
	}
}
