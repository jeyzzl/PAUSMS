package aca.admision.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AdmCartaSubirMapper implements RowMapper<AdmCartaSubir> {
	@Override
	public AdmCartaSubir mapRow(ResultSet rs, int rowNum) throws SQLException {
		AdmCartaSubir objeto = new AdmCartaSubir();

		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setNombre(rs.getString("NOMBRE"));
		objeto.setCarta(rs.getBytes("CARTA"));
		objeto.setFecha(rs.getString("FECHA"));
		
		return objeto;
	}

}
