package aca.admision.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AdmIngresoMapper implements RowMapper<AdmIngreso> {
	
	public AdmIngreso mapRow(ResultSet rs, int arg1) throws SQLException {
		AdmIngreso objeto = new AdmIngreso();
		
		objeto.setPeriodoId(rs.getString("PERIODO_ID"));
		objeto.setPeriodoNombre(rs.getString("PERIODO_NOMBRE"));
		objeto.setEstado(rs.getString("ESTADO"));
		objeto.setFecha(rs.getString("FECHA"));
		
		return objeto;
	}

}
