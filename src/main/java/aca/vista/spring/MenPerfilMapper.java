package aca.vista.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class MenPerfilMapper implements RowMapper<ModOpcion>{

	public ModOpcion mapRow(ResultSet rs, int arg1) throws SQLException {
		ModOpcion objeto = new ModOpcion();
		
		objeto.setModuloId(rs.getString("MODULO_ID"));
		objeto.setOpcionId(rs.getString("OPCION_ID"));
		objeto.setNombreModulo(rs.getString("NOMBRE_MODULO"));
		objeto.setNombreOpcion(rs.getString("NOMBRE_OPCION"));
		objeto.setUrlModulo(rs.getString("URL_MODULO"));
		objeto.setUrlOpcion(rs.getString("URL_OPCION"));
		objeto.setUsuarios(rs.getString("USUARIOS"));

		return objeto;
	}

}
