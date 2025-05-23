package aca.leg.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class LegDocumentoMapper implements RowMapper<LegDocumento> {

	public LegDocumento mapRow(ResultSet rs, int rowNum) throws SQLException {
		LegDocumento objeto = new LegDocumento();
		
		objeto.setIdDocumentos(rs.getString("IDDOCUMENTOS"));
		objeto.setDescripcion(rs.getString("DESCRIPCION"));
		objeto.setImagen(rs.getString("IMAGEN"));
		
		return objeto;
	}
	
}
