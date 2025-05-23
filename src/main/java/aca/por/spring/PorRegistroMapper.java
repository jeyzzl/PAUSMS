package aca.por.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class PorRegistroMapper implements RowMapper<PorRegistro>{

	public PorRegistro mapRow(ResultSet rs, int arg1) throws SQLException {
		PorRegistro objeto = new PorRegistro();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setEquipoId(rs.getString("EQUIPO_ID"));	
		objeto.setEstado(rs.getString("ESTADO"));

		return objeto;
	}

}
