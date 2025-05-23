package aca.conva.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ConvHistorialMapper implements RowMapper<ConvHistorial> {
	public ConvHistorial mapRow(ResultSet rs, int arg1) throws SQLException {
		
		ConvHistorial historial = new ConvHistorial();
		
		historial.setConvalidacionId(rs.getString("CONVALIDACION_ID"));
		historial.setFolio(rs.getString("FOLIO"));
		historial.setFecha(rs.getString("FECHA"));
		historial.setUsuario(rs.getString("USUARIO"));
		historial.setEstado(rs.getString("ESTADO"));
		return historial;
	}
}
