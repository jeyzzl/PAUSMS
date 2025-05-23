package aca.kardex.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class KrdxAlumnoTituloMapper implements RowMapper<KrdxAlumnoTitulo> {
	public KrdxAlumnoTitulo mapRow(ResultSet rs, int arg1) throws SQLException {
		
		KrdxAlumnoTitulo objeto = new KrdxAlumnoTitulo();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setCursoCargaId(rs.getString("CURSO_CARGA_ID"));
		objeto.setCursoId(rs.getString("CURSO_ID"));
		objeto.setCarreraId(rs.getString("CARRERA_ID"));
		objeto.setPresidente(rs.getString("PRESIDENTE"));
		objeto.setSecretario(rs.getString("SECRETARIO"));
		objeto.setMiembro(rs.getString("MIEMBRO"));
		objeto.setComentario(rs.getString("COMENTARIO"));
		objeto.setNota(rs.getString("NOTA"));
		objeto.setUsuario(rs.getString("USUARIO"));
		objeto.setEstado(rs.getString("ESTADO"));
		
		return objeto;
	}
}