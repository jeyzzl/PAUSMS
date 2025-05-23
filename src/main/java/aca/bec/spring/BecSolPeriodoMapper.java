package aca.bec.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class BecSolPeriodoMapper implements RowMapper<BecSolPeriodo> {
	
	@Override
	public BecSolPeriodo mapRow(ResultSet rs, int arg1) throws SQLException {
		
		BecSolPeriodo objeto = new BecSolPeriodo();
		
		objeto.setPeriodoId(rs.getString("PERIODO_ID"));
		objeto.setPeriodoNombre(rs.getString("PERIODO_NOMBRE"));
		objeto.setFecha(rs.getString("FECHA"));
		
		return objeto;
	}
}
