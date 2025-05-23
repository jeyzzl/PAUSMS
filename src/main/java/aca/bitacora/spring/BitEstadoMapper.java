package aca.bitacora.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class BitEstadoMapper implements RowMapper<BitEstado> {
	public BitEstado mapRow(ResultSet rs, int arg1) throws SQLException {
		
		BitEstado objeto = new BitEstado();
		
		objeto.setEstado(rs.getString("ESTADO"));
		objeto.setEstado_nombre(rs.getString("ESTADO_NOMBRE"));
		
		return objeto;
	}
}