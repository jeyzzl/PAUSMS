package aca.portafolio.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class PorNivelMapper implements RowMapper<PorNivel> {

	public PorNivel mapRow(ResultSet rs, int rowNum) throws SQLException {
		PorNivel objeto = new PorNivel();
		
		objeto.setNivelId(rs.getString("NIVEL_ID"));
		objeto.setNivelNombre(rs.getString("NIVEL_NOMBRE"));
		objeto.setArchivo(rs.getString("ARCHIVO"));
		objeto.setDocumento_id(rs.getString("DOCUMENTO_ID"));
		
		return objeto;
	}

}
