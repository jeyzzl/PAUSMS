package aca.baja.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class BajaPasoMapper implements RowMapper<BajaPaso>{
	@Override
	public BajaPaso mapRow(ResultSet rs, int arg1) throws SQLException {
		
		BajaPaso objeto = new BajaPaso();
		
		objeto.setPasoId(rs.getString("PASO_ID"));
		objeto.setPasoNombre(rs.getString("PASO_NOMBRE"));
		
		return objeto;
	}
}