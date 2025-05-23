package aca.tit.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class TitActivacionMapper implements RowMapper<TitActivacion> {

	public TitActivacion mapRow(ResultSet rs, int rowNum) throws SQLException {
		TitActivacion objeto = new TitActivacion();
		
		objeto.setInstitucion(rs.getString(""));
		objeto.setInicio(rs.getString(""));
		objeto.setFin(rs.getString(""));
		objeto.setSelloActivacion(rs.getString(""));
		
		return objeto;
	}

}
