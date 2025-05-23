package aca.admision.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AdmCartaMapper implements RowMapper<AdmCarta> {
	@Override
	public AdmCarta mapRow(ResultSet rs, int rowNum) throws SQLException {
		AdmCarta objeto = new AdmCarta();

		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setCondicionId(rs.getString("CONDICION_ID"));
		objeto.setCondicionNombre(rs.getString("CONDICION_NOMBRE"));
		
		return objeto;
	}

}
