package aca.graduacion.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AlumEventoMapper implements RowMapper<AlumEvento> {
	public AlumEvento mapRow(ResultSet rs, int arg1) throws SQLException {
		
		AlumEvento objeto = new AlumEvento();
		
		objeto.setEventoId(rs.getString("EVENTO_ID"));
		objeto.setEventoNombre(rs.getString("EVENTO_NOMBRE"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setEstado(rs.getString("ESTADO"));
		objeto.setArchivo(rs.getString("ARCHIVO"));
		
		return objeto;
	}
}