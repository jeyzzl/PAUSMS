package aca.catalogo.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CatCulturalMapper implements RowMapper<CatCultural> {
	
	@Override
	public CatCultural mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CatCultural objeto = new CatCultural();
		objeto.setCulturalId(rs.getString("CULTURAL_ID"));
		objeto.setNombreCultural(rs.getString("NOMBRE_CULTURAL"));
		objeto.setPrincipal(rs.getString("PRINCIPAL"));
		
		return objeto;
	}
}