package aca.podium.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AspiranteMapper implements RowMapper<Aspirante> {

	public Aspirante mapRow(ResultSet rs, int rowNum) throws SQLException {
		Aspirante objeto = new Aspirante();
		
		objeto.setId(rs.getInt("ID"));
		objeto.setFolio(rs.getInt("FOLIO"));
		objeto.setGrado(rs.getString("GRADO"));
		
		return objeto;
	}

}
