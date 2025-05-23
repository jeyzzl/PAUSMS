package aca.vista.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class MaestroGrupoMapper implements RowMapper<MaestroGrupo>{

	public MaestroGrupo mapRow(ResultSet rs, int arg1) throws SQLException {
		MaestroGrupo objeto = new MaestroGrupo();
		
		objeto.setMaestroCarrera(rs.getString("MAESTRO_CARRERA"));
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setCarreraId(rs.getString("CARRERA_ID"));
		objeto.setNumGrupos(rs.getString("NUM_GRUPOS"));
		
		return objeto;
	}

}
