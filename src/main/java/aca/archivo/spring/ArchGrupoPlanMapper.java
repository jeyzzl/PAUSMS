package aca.archivo.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ArchGrupoPlanMapper implements RowMapper<ArchGrupoPlan>{
	@Override
	public ArchGrupoPlan mapRow(ResultSet rs, int arg1) throws SQLException{
		
		ArchGrupoPlan objeto = new ArchGrupoPlan();
		objeto.setGrupoId(rs.getString("GRUPO_ID"));
		objeto.setPlanId(rs.getString("PLAN_ID"));
		
		return objeto;
	}
}