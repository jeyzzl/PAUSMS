package aca.tit.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class TitPagoMapper implements RowMapper<TitPago>{
	public TitPago mapRow(ResultSet rs, int arg1) throws SQLException {
		
		TitPago objeto = new TitPago();
		
		objeto.setPagoId(rs.getString("PAGO_ID"));
		objeto.setNombrePago(rs.getString("NOMBRE_PAGO"));
		
		return objeto;
	}
}