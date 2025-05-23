package aca.salida.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class SalGrupoMapper implements RowMapper<SalGrupo> {

	public SalGrupo mapRow(ResultSet rs, int arg1) throws SQLException {
		SalGrupo objeto = new SalGrupo();
		
		objeto.setGrupoId(rs.getString("GRUPO_ID"));
		objeto.setGrupoNombre(rs.getString("GRUPO_NOMBRE"));
		objeto.setResponsable(rs.getString("RESPONSABLE"));
		objeto.setCorreo(rs.getString("CORREO"));
		objeto.setUsuarios(rs.getString("USUARIOS"));
		
		return objeto;

	}

}
