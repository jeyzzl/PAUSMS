package aca.acceso.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AccesoClaveMapper implements RowMapper<AccesoClave>{
	@Override
	public AccesoClave mapRow(ResultSet rs, int arg1) throws SQLException {
		
		AccesoClave objeto = new AccesoClave();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setClave(rs.getString("CLAVE"));
		objeto.setIp(rs.getString("IP"));
		objeto.setFolio(rs.getInt("FOLIO"));
		objeto.setFechaRecupera(rs.getString("FECHA_RECUPERA"));
		
		return objeto;
	}
}