package aca.admision.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AdmEventoMapper implements RowMapper<AdmEvento>{
	@Override
	public AdmEvento mapRow(ResultSet rs, int arg1) throws SQLException {
		
		AdmEvento objeto = new AdmEvento();
		
		objeto.setEventoId(rs.getString("EVENTO_ID"));
		objeto.setEventoNombre(rs.getString("EVENTO_NOMBRE"));
		objeto.setEstado(rs.getString("ESTADO"));
		objeto.setLugar(rs.getString("LUGAR"));
		objeto.setOrden(rs.getString("ORDEN"));
		objeto.setFecha(rs.getString("FECHA"));
		
		return objeto;
	}
}