package aca.archivo.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ArchGruposMapper implements RowMapper<ArchGrupos>{
	@Override
	public ArchGrupos mapRow(ResultSet rs, int arg1) throws SQLException {
		
		ArchGrupos objeto = new ArchGrupos();
		
		objeto.setGrupo(rs.getString("GRUPO"));
		objeto.setIdDocumento(rs.getString("IDDOCUMENTO"));
		objeto.setIdStatus(rs.getString("IDSTATUS"));
		
		return objeto;
	}
}