package aca.plan.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class MapaNuevoPlanMapper implements RowMapper<MapaNuevoPlan>{

	public MapaNuevoPlan mapRow(ResultSet rs, int arg1) throws SQLException {
		MapaNuevoPlan objeto = new MapaNuevoPlan();
		
		objeto.setPlanId(rs.getString("PLAN_ID"));
		objeto.setCarreraId(rs.getString("CARRERA_ID"));		
		objeto.setNombre(rs.getString("NOMBRE"));		
		objeto.setVersionId(rs.getString("VERSION_ID"));
		objeto.setVersionNombre(rs.getString("VERSION_NOMBRE"));	
		objeto.setEstado(rs.getString("ESTADO"));
		objeto.setTipo(rs.getString("TIPO"));
		objeto.setHts(rs.getString("HTS"));
		objeto.setHps(rs.getString("HPS"));
		objeto.setHfd(rs.getString("HFD"));
		objeto.setHei(rs.getString("HEI"));
		objeto.setIdioma(rs.getString("IDIOMA"));
		objeto.setHss(rs.getString("HSS"));
		objeto.setHas(rs.getString("HAS"));
		objeto.setYear(rs.getString("YEAR"));
		
		return objeto;
	}

}
