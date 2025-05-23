package aca.admision.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AdmCartaFultonMapper implements RowMapper<AdmCartaFulton> {
	@Override
	public AdmCartaFulton mapRow(ResultSet rs, int rowNum) throws SQLException {
		AdmCartaFulton objeto = new AdmCartaFulton();

		objeto.setCartaId(rs.getString("CARTA_ID"));
		objeto.setFechaRegistro(rs.getString("FECHA_REGISTRO"));
		objeto.setFechaOrientacion(rs.getString("FECHA_ORIENTACION"));
		objeto.setFechaInicio(rs.getString("FECHA_INICIO"));
		objeto.setFechaCierre(rs.getString("FECHA_CIERRE"));
		objeto.setFechaArribo(rs.getString("FECHA_ARRIBO"));
		
		return objeto;
	}

}
