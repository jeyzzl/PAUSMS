package aca.emp.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class EmpTipoPagoMapper implements RowMapper<EmpTipoPago>{
	
	public EmpTipoPago mapRow(ResultSet rs, int arg1) throws SQLException {
		
		EmpTipoPago objeto = new EmpTipoPago();
		
		objeto.setTipopagoId(rs.getString("TIPOPAGO_ID"));
		objeto.setTipopagoNombre(rs.getString("TIPOPAGO_NOMBRE"));		
		objeto.setCorto(rs.getString("CORTO"));
		
		return objeto;
	}
}