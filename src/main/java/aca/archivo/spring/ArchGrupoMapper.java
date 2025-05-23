package aca.archivo.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ArchGrupoMapper implements RowMapper<ArchGrupo>{
	@Override
	public ArchGrupo mapRow(ResultSet rs, int arg1) throws SQLException {
		
		ArchGrupo objeto = new ArchGrupo();
		
		objeto.setGrupoId(rs.getString("GRUPO_ID"));
		objeto.setGrupoNombre(rs.getString("GRUPO_NOMBRE"));
		objeto.setComentario(rs.getString("COMENTARIO"));
		
		return objeto;
	}
}