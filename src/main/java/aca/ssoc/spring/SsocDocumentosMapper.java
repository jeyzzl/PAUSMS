package aca.ssoc.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class SsocDocumentosMapper implements RowMapper<SsocDocumentos>{
	
	public SsocDocumentos mapRow(ResultSet rs, int arg1) throws SQLException {
		
		SsocDocumentos objeto = new SsocDocumentos();
		
		objeto.setDocumentoId(rs.getString("DOCUMENTO_ID"));
		objeto.setDocumentoNombre(rs.getString("DOCUMENTO_NOMBRE"));
		objeto.setOrden(rs.getString("ORDEN"));
		objeto.setObligatorio(rs.getString("OBLIGATORIO"));		
		objeto.setAcceso(rs.getString("ACCESO"));
		
		return objeto;
	}
}