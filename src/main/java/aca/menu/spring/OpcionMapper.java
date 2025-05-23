package aca.menu.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class OpcionMapper implements RowMapper<Opcion>{

	public Opcion mapRow(ResultSet rs, int arg1) throws SQLException {
		Opcion objeto = new Opcion();
		
		objeto.setModuloId(rs.getString("MODULO_ID"));
		objeto.setOpcionId(rs.getString("OPCION_ID"));
		objeto.setNombreOpcion(rs.getString("NOMBRE_OPCION"));
		objeto.setUrl(rs.getString("URL"));
		objeto.setIcono(rs.getString("ICONO"));
		objeto.setOrden(rs.getString("ORDEN"));
		objeto.setUsuarios(rs.getString("USUARIOS"));
		objeto.setCarpeta(rs.getString("CARPETA"));

		return objeto;
	}

}
