package aca.tit.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class TitDocumentosMapper implements RowMapper<TitDocumentos> {

	public TitDocumentos mapRow(ResultSet rs, int rowNum) throws SQLException {
		TitDocumentos objeto = new TitDocumentos();
		
		objeto.setDocumentoId(rs.getString("DOCUMENTO_ID"));
		objeto.setDocumentoNombre(rs.getString("DOCUMENTO_NOMBRE"));		
		objeto.setOrden(rs.getString("ORDEN"));
		objeto.setTipo(rs.getString("TIPO"));
		
		return objeto;
	}

}
