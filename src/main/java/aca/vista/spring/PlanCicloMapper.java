package aca.vista.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class PlanCicloMapper implements RowMapper<PlanCiclo>{

	public PlanCiclo mapRow(ResultSet rs, int arg1) throws SQLException {
		PlanCiclo objeto = new PlanCiclo();
		
		objeto.setPlanId(rs.getString("PLAN_ID"));
		objeto.setCiclo(rs.getString("CICLO"));
		objeto.setCreditos(rs.getString("CREDITOS"));

		return objeto;
	}

}
