package aca.archivo.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ArchGrupoDocumentoMapper implements RowMapper<ArchGrupoDocumento>{
	@Override
	public ArchGrupoDocumento mapRow(ResultSet rs, int arg1) throws SQLException {
		
		ArchGrupoDocumento objeto = new ArchGrupoDocumento();
		
		objeto.setGrupoId(rs.getString("GRUPO_ID"));
		objeto.setDocumentoId(rs.getString("DOCUMENTO_ID"));
		
		return objeto;
	}
}