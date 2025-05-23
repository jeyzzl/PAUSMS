package aca.tit.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class TitAutorizacionMapper implements RowMapper<TitAutorizacion>{
	
	public TitAutorizacion mapRow(ResultSet rs, int arg1) throws SQLException {
		
		TitAutorizacion objeto = new TitAutorizacion();
		
		objeto.setAutorizacionId(rs.getString("AUTORIZACION_ID"));
		objeto.setAutorizacionNombre(rs.getString("AUTORIZACION_NOMBRE"));
		
		return objeto;
	}
}