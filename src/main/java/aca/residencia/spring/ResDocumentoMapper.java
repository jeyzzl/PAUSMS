package aca.residencia.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ResDocumentoMapper implements RowMapper<ResDocumento>{
	
	public ResDocumento mapRow(ResultSet rs, int arg1) throws SQLException {
		
		ResDocumento objeto = new ResDocumento();
		
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setDescripcion(rs.getString("DESCRIPCION"));
		objeto.setDocumento(rs.getBytes("DOCUMENTO"));
		objeto.setFolioExpediente(rs.getString("FOLIO_EXPEDIENTE"));
		objeto.setNombre(rs.getString("NOMBRE"));
		
		return objeto;
	}
}