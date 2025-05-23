package aca.vigilancia.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class VigTipoMultaMapper implements RowMapper<VigTipoMulta>{

	public VigTipoMulta mapRow(ResultSet rs, int arg1) throws SQLException {
		VigTipoMulta objeto = new VigTipoMulta();
		
		objeto.setTipoId(rs.getString("TIPO_ID"));
		objeto.setTipoNombre(rs.getString("TIPO_NOMBRE"));
		objeto.setCosto(rs.getString("COSTO"));
		
		return objeto;
	}

}
