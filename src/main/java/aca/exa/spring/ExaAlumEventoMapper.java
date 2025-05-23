package aca.exa.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ExaAlumEventoMapper implements RowMapper<ExaAlumEvento>{
	
	public ExaAlumEvento mapRow(ResultSet rs, int arg1) throws SQLException {
		
		ExaAlumEvento objeto = new ExaAlumEvento();
		
		objeto.setAlumEventoId(rs.getString("ALUMEVENTO_ID"));
		objeto.setMatricula(rs.getString("MATRICULA"));
		objeto.setIdEvento(rs.getString("IDEVENTO"));
		objeto.setFechaActualizacion(rs.getString("FECHAACTUALIZACION"));		
		objeto.setEliminado(rs.getString("ELIMINADO"));
		objeto.setIdEventoAsistido(rs.getString("IDEVENTOASISTIDO"));
		
		return objeto;
	}
}