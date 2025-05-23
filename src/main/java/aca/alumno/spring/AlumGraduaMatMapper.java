package aca.alumno.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AlumGraduaMatMapper implements RowMapper<AlumGraduaMat>{
	@Override
	public AlumGraduaMat mapRow(ResultSet rs, int arg1) throws SQLException {
		
		AlumGraduaMat objeto = new AlumGraduaMat();		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setCursoId(rs.getString("CURSO_ID"));
		objeto.setProgramada(rs.getString("PROGRAMADA"));
		objeto.setComentario(rs.getString("COMENTARIO"));
	
		return objeto;
	}
}