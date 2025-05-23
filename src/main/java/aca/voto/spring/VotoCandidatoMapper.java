package aca.voto.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class VotoCandidatoMapper implements RowMapper<VotoCandidato>{

	public VotoCandidato mapRow(ResultSet rs, int arg1) throws SQLException {
		VotoCandidato objeto = new VotoCandidato();
		
		objeto.setEventoId(rs.getString("EVENTO_ID"));
		objeto.setCandidatoId(rs.getString("CANDIDATO_ID"));
		objeto.setCandidatoNombre(rs.getString("CANDIDATO_NOMBRE"));
		objeto.setCandidatos(rs.getString("CANDIDATOS"));
		objeto.setCandidatas(rs.getString("CANDIDATAS"));
		objeto.setFacultades(rs.getString("FACULTADES"));
		objeto.setEstado(rs.getString("ESTADO"));
		objeto.setGanador(rs.getString("GANADOR"));
		objeto.setOrden(rs.getString("ORDEN"));	
		
		return objeto;
	}

}
