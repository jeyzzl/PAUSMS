package aca.vigilancia.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class VigDocumentoMapperCorto implements RowMapper<VigDocumento>{

	public VigDocumento mapRow(ResultSet rs, int arg1) throws SQLException {
		VigDocumento objeto = new VigDocumento();
		
		objeto.setAutoId(rs.getString("AUTO_ID"));
		objeto.setDocumentoId(rs.getString("DOCUMENTO_ID"));		
		objeto.setNombre(rs.getString("NOMBRE"));
		
		return objeto;
	}

}
