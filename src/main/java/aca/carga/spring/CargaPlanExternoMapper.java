package aca.carga.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CargaPlanExternoMapper implements RowMapper<CargaPlanExterno>{
	
	public CargaPlanExterno mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CargaPlanExterno objeto = new CargaPlanExterno();
		
		objeto.setCargaId(rs.getString("CARGA_ID"));
		objeto.setPlanId(rs.getString("PLAN_ID"));
		
		return objeto;
	}
}