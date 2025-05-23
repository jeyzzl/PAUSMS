package aca.plan.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class MapaArchivoMapper implements RowMapper<MapaArchivo>{

	public MapaArchivo mapRow(ResultSet rs, int arg1) throws SQLException {
		MapaArchivo objeto = new MapaArchivo();
		
		objeto.setPlanId(rs.getString("PLAN_ID"));
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setNombre(rs.getString("NOMBRE"));
		objeto.setArchivo(rs.getBytes("ARCHIVO"));
		
		return objeto;
	}

}
