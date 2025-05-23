package aca.financiero.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class FinQuoteConceptMapper implements RowMapper<FinQuoteConcept>{
	
	public FinQuoteConcept mapRow(ResultSet rs, int arg1) throws SQLException {
		
		FinQuoteConcept objeto = new FinQuoteConcept();
		
		objeto.setQuoteId(rs.getInt("QUOTE_ID"));
		objeto.setConcId(rs.getInt("CONC_ID"));
		objeto.setNoUnits(rs.getInt("NO_UNITS"));
		objeto.setAmount(rs.getDouble("AMOUNT"));
		objeto.setDiscountAmt(rs.getDouble("DISCOUNT_AMT"));

		return objeto;
	}
}