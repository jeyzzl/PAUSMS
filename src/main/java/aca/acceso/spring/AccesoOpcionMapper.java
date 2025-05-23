package aca.acceso.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AccesoOpcionMapper implements RowMapper<AccesoOpcion>{
	@Override
	public AccesoOpcion mapRow(ResultSet rs, int arg1) throws SQLException {
		
		AccesoOpcion objeto = new AccesoOpcion();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setOpcionId(rs.getString("OPCION_ID"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setUsuario(rs.getString("USUARIO"));
		
		return objeto;
	}
}