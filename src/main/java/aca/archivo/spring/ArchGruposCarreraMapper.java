package aca.archivo.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ArchGruposCarreraMapper implements RowMapper<ArchGruposCarrera>{
	@Override
	public ArchGruposCarrera mapRow(ResultSet rs, int arg1) throws SQLException {
		
		ArchGruposCarrera objeto = new ArchGruposCarrera();
		
		objeto.setCarrera(rs.getString("CARRERA"));
		objeto.setGrupos(rs.getString("GRUPOS"));
		
		return objeto;
	}
}