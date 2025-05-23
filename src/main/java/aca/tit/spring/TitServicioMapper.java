package aca.tit.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class TitServicioMapper implements RowMapper<TitServicio>{
	public TitServicio mapRow(ResultSet rs, int arg1) throws SQLException {
		
		TitServicio objeto = new TitServicio();
		
		objeto.setServicioId(rs.getString("SERVICIO_ID"));
		objeto.setServicioNombre(rs.getString("SERVICIO_NOMBRE"));
		
		return objeto;
	}
}