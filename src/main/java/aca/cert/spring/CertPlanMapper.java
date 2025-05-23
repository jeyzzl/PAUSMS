package aca.cert.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CertPlanMapper implements RowMapper<CertPlan> {
	public CertPlan mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CertPlan objeto = new CertPlan();
		
		objeto.setPlanId(rs.getString("PLAN_ID"));
		objeto.setFacultad(rs.getString("FACULTAD"));
		objeto.setCarrera(rs.getString("CARRERA"));
		objeto.setNumCursos(rs.getString("NUM_CURSOS"));
		objeto.setSemanas(rs.getString("SEMANAS"));
		objeto.settInicial(rs.getString("T_INICIAL"));
		objeto.settFinal(rs.getString("T_FINAL"));
		objeto.setNota(rs.getString("NOTA"));
		objeto.setPie(rs.getString("PIE"));
		objeto.setClave(rs.getString("CLAVE"));
		objeto.setFst(rs.getString("FST"));
		objeto.setFsp(rs.getString("FSP"));
		objeto.setComponente(rs.getString("COMPONENTE"));
		objeto.setCurso(rs.getString("CURSO"));
		objeto.setRvoe(rs.getString("RVOE"));
		objeto.setFechaRetro(rs.getString("FECHARETRO"));
		objeto.setTitulo1(rs.getString("TITULO1"));
		objeto.setTitulo2(rs.getString("TITULO2"));
		objeto.setTitulo3(rs.getString("TITULO3"));
		objeto.setCreditos(rs.getString("CREDITOS"));
		
		return objeto;
	}
}
