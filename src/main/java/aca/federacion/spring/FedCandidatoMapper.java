package aca.federacion.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class FedCandidatoMapper implements RowMapper<FedCandidato> {
	
	@Override
	public FedCandidato mapRow(ResultSet rs, int rowNum) throws SQLException {
		FedCandidato objeto = new FedCandidato();
		
		objeto.setEventoId(rs.getString("EVENTO_ID"));
		objeto.setCandidatoId(rs.getString("CANDIDATO_ID"));
		objeto.setCandidatoNombre(rs.getString("CANDIDATO_NOMBRE"));
		objeto.setCandidatos(rs.getString("CANDIDATOS"));
		objeto.setOrden(rs.getString("ORDEN"));
		
		return objeto;
	}

}
