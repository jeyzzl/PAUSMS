package aca.por.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class PorSalonMapper implements RowMapper<PorSalon>{

	public PorSalon mapRow(ResultSet rs, int arg1) throws SQLException {
		PorSalon objeto = new PorSalon();
		
		objeto.setSalonId(rs.getString("SALON_ID"));
		objeto.setSalonNombre(rs.getString("SALON_NOMBRE"));	

		return objeto;
	}

}
