package aca.graduacion.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CarreraOrdenMapper implements RowMapper<CarreraOrden> {
	public CarreraOrden mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CarreraOrden objeto = new CarreraOrden();
		
		objeto.setCarreraId(rs.getString("CARRERA_ID"));
		objeto.setOrden(rs.getString("ORDEN"));		
		
		return objeto;
	}
}