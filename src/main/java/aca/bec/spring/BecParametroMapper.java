package aca.bec.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class BecParametroMapper implements RowMapper<BecParametro> {
	
	@Override
	public BecParametro mapRow(ResultSet rs, int arg1) throws SQLException {
		
		BecParametro objeto = new BecParametro();
		
		objeto.setPrepaInicio(rs.getString("PREPA_INICIO"));
		objeto.setPrepaFinal(rs.getString("PREPA_FINAL"));
		objeto.setPregradoInicio(rs.getString("PREGRADO_INICIO"));
		objeto.setPregradoFinal(rs.getString("PREGRADO_FINAL"));
		
		return objeto;
	}
}
