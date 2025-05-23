package aca.cita.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CitaEventoMapper implements RowMapper<CitaEvento>{
	
	public CitaEvento mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CitaEvento objeto = new CitaEvento();
		
		objeto.setEventoId(rs.getString("EVENTO_ID"));
		objeto.setEventoNombre(rs.getString("EVENTO_NOMBRE"));
		objeto.setEstado(rs.getString("ESTADO"));		
		
		return objeto;
	}
}