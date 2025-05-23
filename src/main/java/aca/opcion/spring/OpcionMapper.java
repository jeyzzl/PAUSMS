package aca.opcion.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class OpcionMapper implements RowMapper<Opcion>{
	
	public Opcion mapRow(ResultSet rs, int arg1) throws SQLException {
		
		Opcion objeto = new Opcion();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setAlertaSesion(rs.getString("ALERTA_SESION"));
		objeto.setMenuClick(rs.getString("MENU_CLICK"));			
		
		return objeto;
	}
}