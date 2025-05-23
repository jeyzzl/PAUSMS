package aca.bitacora.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class BitTramiteRequisitoMapper implements RowMapper<BitTramiteRequisito> {
	public BitTramiteRequisito mapRow(ResultSet rs, int arg1) throws SQLException {
		
		BitTramiteRequisito objeto = new BitTramiteRequisito();
		
		objeto.setTramiteId(rs.getString("TRAMITE_ID"));
		objeto.setRequisitoId(rs.getString("REQUISITO_ID"));
		
		return objeto;
	}
}