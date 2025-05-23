package aca.portafolio.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class PorDocumentoMapper implements RowMapper<PorDocumento> {

	public PorDocumento mapRow(ResultSet rs, int rowNum) throws SQLException {
		PorDocumento objeto = new PorDocumento();
		
		objeto.setDocumentoId(rs.getString("DOCUMENTO_ID"));
		objeto.setDocumentoNombre(rs.getString("DOCUMENTO_NOMBRE"));
		objeto.setUsuario(rs.getString("USUARIO"));
		objeto.setArchivo(rs.getString("ARCHIVO"));
		
		return objeto;
	}

}
