package aca.admision.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AdmIngresoPlanMapper implements RowMapper<AdmIngresoPlan> {
	
	public AdmIngresoPlan mapRow(ResultSet rs, int arg1) throws SQLException {
		AdmIngresoPlan objeto = new AdmIngresoPlan();
		
		objeto.setPeriodoId(rs.getString("PERIODO_ID"));
		objeto.setModalidadId(rs.getString("MODALIDAD_ID"));
		objeto.setPlanId(rs.getString("PLAN_ID"));
		
		return objeto;
	}

}
