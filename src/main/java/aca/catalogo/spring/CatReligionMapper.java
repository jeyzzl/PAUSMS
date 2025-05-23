package aca.catalogo.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CatReligionMapper implements RowMapper<CatReligion> {
	public CatReligion mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CatReligion objeto = new CatReligion();
		
		objeto.setReligionId(rs.getString("RELIGION_ID"));
		objeto.setNombreReligion(rs.getString("NOMBRE_RELIGION"));
		objeto.setNombreCorto(rs.getString("NOMBRE_CORTO"));
		
		return objeto;
	}
}
