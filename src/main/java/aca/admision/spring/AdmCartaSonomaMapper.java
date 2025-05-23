package aca.admision.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AdmCartaSonomaMapper implements RowMapper<AdmCartaSonoma> {
	@Override
	public AdmCartaSonoma mapRow(ResultSet rs, int rowNum) throws SQLException {
		AdmCartaSonoma objeto = new AdmCartaSonoma();

		objeto.setCartaId(rs.getString("CARTA_ID"));
		objeto.setFechaFinal(rs.getString("FECHA_FINAL_PAGO"));
		
		return objeto;
	}

}
