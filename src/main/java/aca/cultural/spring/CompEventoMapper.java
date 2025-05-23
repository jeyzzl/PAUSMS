package aca.cultural.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CompEventoMapper implements RowMapper<CompEvento> {
	public CompEvento mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CompEvento objeto = new CompEvento();
		
		objeto.setEventoId(rs.getString("EVENTO_ID"));
		objeto.setEventoNombre(rs.getString("EVENTO_NOMBRE"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setDescripcion(rs.getString("DESCRIPCION"));
		objeto.setCapacidad(rs.getString("CAPACIDAD"));
		objeto.setLugar(rs.getString("LUGAR"));
		objeto.setEstado(rs.getString("ESTADO"));

		return objeto;
	}
}