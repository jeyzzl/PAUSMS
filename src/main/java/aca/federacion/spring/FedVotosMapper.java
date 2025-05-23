package aca.federacion.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class FedVotosMapper implements RowMapper<FedVotos>{

	@Override
	public FedVotos mapRow(ResultSet rs, int rowNum) throws SQLException {
		FedVotos objeto = new FedVotos();
		
		objeto.setEventoId(rs.getString("EVENTO_ID"));
		objeto.setCandidatoId(rs.getString("CANDIDATO_ID"));
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setVoto(rs.getString("VOTO"));
		
		return objeto;
	}

}
