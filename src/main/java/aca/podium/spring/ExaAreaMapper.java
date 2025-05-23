package aca.podium.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ExaAreaMapper implements RowMapper<ExaArea>{
	
	public ExaArea mapRow(ResultSet rs, int arg1) throws SQLException {	
		
		ExaArea objeto = new ExaArea();
		
		objeto.setId(rs.getInt("ID"));
		objeto.setNombre(rs.getString("NOMBRE"));
		objeto.setOrden(rs.getInt("ORDEN"));
		objeto.setTiempoPre(rs.getInt("TIEMPO_PRE"));		
		objeto.setTiempoPos(rs.getInt("TIEMPO_POS"));		
		objeto.setMinimoPre(rs.getInt("MINIMO_PRE"));
		objeto.setMinimoPos(rs.getInt("MINIMO_POS"));
		objeto.setPuntosPre(rs.getFloat("PUNTOS_PRE"));
		objeto.setPuntosPos(rs.getFloat("PUNTOS_POS"));
		objeto.setFacilPre(rs.getInt("FACIL_PRE"));
		objeto.setFacilPos(rs.getInt("FACIL_POS"));
		objeto.setMedioPre(rs.getInt("MEDIO_PRE"));
		objeto.setMedioPos(rs.getInt("MEDIO_POS"));
		objeto.setDificilPre(rs.getInt("DIFICIL_PRE"));
		objeto.setDificilPos(rs.getInt("DIFICIL_POS"));
		
		return objeto;
	}
}