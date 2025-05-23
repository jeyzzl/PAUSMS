package aca.calcula.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CalConceptoMapper implements RowMapper<CalConcepto> {
	public CalConcepto mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CalConcepto objeto = new CalConcepto();
		
		objeto.setConceptoId(rs.getString("CONCEPTO_ID"));
		objeto.setConceptoNombre(rs.getString("CONCEPTO_NOMBRE"));
		objeto.setTipo(rs.getString("TIPO"));
				
		return objeto;
	}
}
