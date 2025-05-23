package aca.leg.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class LegEstadoMapper implements RowMapper<LegEstado> {

	public LegEstado mapRow(ResultSet rs, int rowNum) throws SQLException {
		LegEstado obejto = new LegEstado();
		
		obejto.setEstadoId(rs.getString("ESTADO_ID"));
		obejto.setEstadoNombre(rs.getString("ESTADO_NOMBRE"));
		
		return obejto;
	}

}
