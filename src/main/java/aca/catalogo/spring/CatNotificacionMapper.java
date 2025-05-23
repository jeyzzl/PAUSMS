package aca.catalogo.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CatNotificacionMapper implements RowMapper<CatNotificacion> {
	public CatNotificacion mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CatNotificacion objeto = new CatNotificacion();
		
		objeto.setNotificacionId(rs.getString("NOTIFICACION_ID"));
		objeto.setNotificacionNombre(rs.getString("NOTIFICACION_NOMBRE"));
				
		return objeto;
	}
}
