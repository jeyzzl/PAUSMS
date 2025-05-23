package aca.exa.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ExaEgresoMapper implements RowMapper<ExaEgreso>{
	
	public ExaEgreso mapRow(ResultSet rs, int arg1) throws SQLException {
		
		ExaEgreso objeto = new ExaEgreso();
		
		objeto.setEgresoId(rs.getString("EGRESO_ID"));
		objeto.setMatricula(rs.getString("MATRICULA"));
		objeto.setPlanId(rs.getString("PLAN_ID"));
		objeto.setCarreraId(rs.getString("CARRERAID"));
		objeto.setYear(rs.getString("YEAR"));
		objeto.setFechaAct(rs.getString("FECHAACTUALIZACION"));		
		objeto.setEliminado(rs.getString("ELIMINADO"));
		objeto.setIdEgresadoAno(rs.getString("IDEGRESADOANO"));
		
		return objeto;
	}
}