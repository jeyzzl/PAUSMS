package aca.catalogo.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CatRegionMapper implements RowMapper<CatRegion> {
	@Override
	public CatRegion mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CatRegion objeto = new CatRegion();
		objeto.setRegionId(rs.getString("REGION_ID"));
		objeto.setCulturalId(rs.getString("CULTURAL_ID"));
		objeto.setNombreRegion(rs.getString("NOMBRE_REGION"));
		
		return objeto;
	}
}