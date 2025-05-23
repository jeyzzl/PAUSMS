package aca.mentores.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class MentMotivoMapper implements RowMapper<MentMotivo>{

	public MentMotivo mapRow(ResultSet rs, int arg1) throws SQLException {
		MentMotivo objeto = new MentMotivo();
		
		objeto.setMotivoId(rs.getString("MOTIVO_ID"));
		objeto.setMotivoNombre(rs.getString("MOTIVO_NOMBRE"));
		objeto.setComentario(rs.getString("COMENTARIO"));
		
		return objeto;
	}

}
