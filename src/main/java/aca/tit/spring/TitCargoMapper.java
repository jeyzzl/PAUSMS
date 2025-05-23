package aca.tit.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class TitCargoMapper implements RowMapper<TitCargo>{
	public TitCargo mapRow(ResultSet rs, int arg1) throws SQLException {
		
		TitCargo objeto = new TitCargo();
		
		objeto.setCargoId(rs.getString("CARGO_ID"));
		objeto.setCargoNombre(rs.getString("CARGO_NOMBRE"));
		
		return objeto;
	}
}