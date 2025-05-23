package aca.plan.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class MapaMayorMenorMapper implements RowMapper<MapaMayorMenor>{

	public MapaMayorMenor mapRow(ResultSet rs, int arg1) throws SQLException {
		MapaMayorMenor objeto = new MapaMayorMenor();
		
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setPlanId(rs.getString("PLAN_ID"));
		objeto.setTipo(rs.getString("TIPO"));
		objeto.setPorDefecto(rs.getString("POR_DEFECTO"));
		objeto.setNombre(rs.getString("NOMBRE"));
		
		return objeto;
	}

}
