package aca.exa.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ExaEventoMapper implements RowMapper<ExaEvento>{
	
	public ExaEvento mapRow(ResultSet rs, int arg1) throws SQLException {
		
		ExaEvento objeto = new ExaEvento();
		
		objeto.setEventoId(rs.getString("EVENTO_ID"));
		objeto.setNombre(rs.getString("NOMBRE"));		
		objeto.setLugar(rs.getString("LUGAR"));
		objeto.setFechaEvento(rs.getString("FECHAEVENTO"));
		objeto.setFechaActualizacion(rs.getString("FECHAACTUALIZACION"));
		objeto.setEliminado(rs.getString("ELIMINADO"));	
		objeto.setIdEvento(rs.getString("IDEVENTO"));
		
		return objeto;
	}
}