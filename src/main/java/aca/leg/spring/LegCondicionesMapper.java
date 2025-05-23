package aca.leg.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class LegCondicionesMapper implements RowMapper<LegCondiciones> {

	public LegCondiciones mapRow(ResultSet rs, int rowNum) throws SQLException {
		LegCondiciones objeto = new LegCondiciones();
		
		objeto.setGrupo(rs.getString("GRUPO"));		
		objeto.setIdDocumento(rs.getString("IDDOCUMENTO"));
		objeto.setValidaFecha(rs.getString("VALIDA_FECHA"));
		
		return objeto;
	}

}
