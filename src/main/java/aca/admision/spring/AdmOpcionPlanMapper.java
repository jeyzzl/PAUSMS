package aca.admision.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AdmOpcionPlanMapper implements RowMapper<AdmOpcionPlan> {
	@Override
	public AdmOpcionPlan mapRow(ResultSet rs, int arg1) throws SQLException {
		AdmOpcionPlan objeto = new AdmOpcionPlan();
		
		objeto.setOpcionId(rs.getString("OPCION_ID"));
		objeto.setPlanId(rs.getString("PLAN_ID"));

		return objeto;
	}

}
