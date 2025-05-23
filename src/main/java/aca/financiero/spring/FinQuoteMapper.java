package aca.financiero.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class FinQuoteMapper implements RowMapper<FinQuote>{
	
	public FinQuote mapRow(ResultSet rs, int arg1) throws SQLException {
		
		FinQuote objeto = new FinQuote();
		
		objeto.setPeriodoId(rs.getString("PERIODO_ID"));
		objeto.setQuoteId(rs.getInt("QUOTE_ID"));
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setStatus(rs.getString("STATUS"));
		objeto.setDescription(rs.getString("DESCRIPTION"));
		objeto.setSemester(rs.getInt("SEMESTER"));
		
		return objeto;
	}
}