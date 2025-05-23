package aca.bitacora.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class BitRequisitoTramiteMapper implements RowMapper<BitRequisitoTramite> {
	
	@Override
	public BitRequisitoTramite mapRow(ResultSet rs, int arg1) throws SQLException {
		
		BitRequisitoTramite objeto = new BitRequisitoTramite();
		
		objeto.setRequisitoId(rs.getString("REQUISITO_ID"));
		objeto.setTramiteId(rs.getString("TRAMITE_ID"));
		
		return objeto;
	}
}