package aca.emp.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class EmpDocumentoMapper implements RowMapper<EmpDocumento>{
	
	public EmpDocumento mapRow(ResultSet rs, int arg1) throws SQLException {
		
		EmpDocumento objeto = new EmpDocumento();
		
		objeto.setDocumentoId(rs.getString("DOCUMENTO_ID"));
		objeto.setDocumentoNombre(rs.getString("DOCUMENTO_NOMBRE"));
		objeto.setOrden(rs.getString("ORDEN"));
		
		return objeto;
	}
}