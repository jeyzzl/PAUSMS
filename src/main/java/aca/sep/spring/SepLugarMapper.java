package aca.sep.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class SepLugarMapper implements RowMapper<SepLugar>{
	public SepLugar mapRow(ResultSet rs, int arg1) throws SQLException {
		
		SepLugar objeto = new SepLugar();
		
		objeto.setLugarId(rs.getString("LUGAR_ID"));
		objeto.setLugarNombre(rs.getString("LUGAR_NOMBRE"));
		objeto.setOrden(rs.getString("ORDEN"));
		
		return objeto;
	}
}