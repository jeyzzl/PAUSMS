package aca.baja.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class BajaAlumpasoMapper implements RowMapper<BajaAlumpaso>{
	@Override
	public BajaAlumpaso mapRow(ResultSet rs, int arg1) throws SQLException {
		
		BajaAlumpaso objeto = new BajaAlumpaso();
		
		objeto.setBajaId(rs.getString("BAJA_ID"));
		objeto.setPasoId(rs.getString("PASO_ID"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setEstado(rs.getString("ESTADO"));

		return objeto;
	}
}