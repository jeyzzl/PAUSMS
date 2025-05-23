package aca.vigilancia.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class VigDocAutoMapper implements RowMapper<VigDocAuto>{

	public VigDocAuto mapRow(ResultSet rs, int arg1) throws SQLException {
		VigDocAuto objeto = new VigDocAuto();
		
		objeto.setAutoId(rs.getString("AUTO_ID"));
		objeto.setDocumentoId(rs.getString("DOCUMENTO_ID"));
		objeto.setVigencia(rs.getString("VIGENCIA"));
		objeto.setComentario(rs.getString("COMENTARIO"));
		
		return objeto;
	}

}
