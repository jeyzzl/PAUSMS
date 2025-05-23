package aca.exp.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ExpDocumentoMapper implements RowMapper<ExpDocumento>{
	@Override
	public ExpDocumento mapRow(ResultSet rs, int arg1) throws SQLException {
		
		ExpDocumento objeto = new ExpDocumento();
		
		objeto.setDocumentoId(rs.getString("DOCUMENTO_ID"));
		objeto.setDocumentoNombre(rs.getString("DOCUMENTO_NOMBRE"));		
		objeto.setOrden(rs.getString("ORDEN"));		
		objeto.setCorto(rs.getString("CORTO"));
		return objeto;
	}
}