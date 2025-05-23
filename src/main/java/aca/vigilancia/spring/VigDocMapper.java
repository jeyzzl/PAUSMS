package aca.vigilancia.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class VigDocMapper implements RowMapper<VigDoc>{

	public VigDoc mapRow(ResultSet rs, int arg1) throws SQLException{
		VigDoc objeto = new VigDoc();		
		
		objeto.setDocumentoId(rs.getString("DOCUMENTO_ID"));
		objeto.setDocumentoNombre(rs.getString("DOCUMENTO_NOMBRE"));
		objeto.setCorto(rs.getString("CORTO"));
		
		return objeto;
	}

}
