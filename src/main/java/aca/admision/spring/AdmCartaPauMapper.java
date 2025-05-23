package aca.admision.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AdmCartaPauMapper implements RowMapper<AdmCartaPau> {
	@Override
	public AdmCartaPau mapRow(ResultSet rs, int rowNum) throws SQLException {
		AdmCartaPau objeto = new AdmCartaPau();

		objeto.setCartaId(rs.getString("CARTA_ID"));
		objeto.setFechaRegistro(rs.getString("FECHA_REGISTRO"));
		objeto.setFechaOrientacion(rs.getString("FECHA_ORIENTACION"));
		objeto.setFechaApertura(rs.getString("FECHA_APERTURA"));
		objeto.setFechaInicio(rs.getString("FECHA_INICIO"));
		objeto.setFechaCierre(rs.getString("FECHA_CIERRE"));
		
		return objeto;
	}

}
