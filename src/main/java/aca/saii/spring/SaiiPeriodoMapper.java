package aca.saii.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class SaiiPeriodoMapper implements RowMapper<SaiiPeriodo>{
	
	public SaiiPeriodo mapRow(ResultSet rs, int arg1) throws SQLException {
		
		SaiiPeriodo objeto = new SaiiPeriodo();
		
		objeto.setPeriodoId(rs.getString("PERIODO_ID"));
		objeto.setPeriodoNombre(rs.getString("PERIODO_NOMBRE"));
		objeto.setEstado(rs.getString("ESTADO"));
		objeto.setFecha(rs.getString("FECHA"));
				
		return objeto;
	}
}