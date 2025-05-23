package aca.mentores.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class MentContactoMapper implements RowMapper<MentContacto>{

	public MentContacto mapRow(ResultSet rs, int arg1) throws SQLException {
		MentContacto objeto = new MentContacto();
		
		objeto.setPeriodoId(rs.getString("PERIODO_ID"));
		objeto.setMentorId(rs.getString("MENTOR_ID"));
		objeto.setContactoId(rs.getString("CONTACTO_ID"));
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setMotivoId(rs.getString("MOTIVO_ID"));
		objeto.setMiAconsejado(rs.getString("MI_ACONSEJADO"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setFechaContacto(rs.getString("FECHA_CONTACTO"));
		objeto.setComentario(rs.getString("COMENTARIO"));
		objeto.setTipo(rs.getString("TIPO"));
		objeto.setCarreraId(rs.getString("CARRERA_ID"));
		objeto.setMotivos(rs.getString("MOTIVOS"));
		
		return objeto;
	}

}
