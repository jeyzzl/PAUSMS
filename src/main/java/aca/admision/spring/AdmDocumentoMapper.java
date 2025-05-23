package aca.admision.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AdmDocumentoMapper implements RowMapper<AdmDocumento> {

	@Override
	public AdmDocumento mapRow(ResultSet rs, int arg1) throws SQLException {
		AdmDocumento objeto = new AdmDocumento();
		
		objeto.setDocumentoId(rs.getString("DOCUMENTO_ID"));
		objeto.setDocumentoNombre(rs.getString("DOCUMENTO_NOMBRE"));
		objeto.setTipo(rs.getString("TIPO"));
		objeto.setComentario(rs.getString("COMENTARIO"));
		objeto.setOriginal(rs.getString("ORIGINAL"));
		objeto.setOrden(rs.getString("ORDEN"));
		objeto.setFormatoId( rs.getString("FORMATO_ID"));
		objeto.setCorto( rs.getString("CORTO"));
		
		return objeto;
	}

}
