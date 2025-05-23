package aca.cert.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CertCicloMapper implements RowMapper<CertCiclo> {
	public CertCiclo mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CertCiclo objeto = new CertCiclo();
		
		objeto.setPlanId(rs.getString("PLAN_ID"));
		objeto.setCicloId(rs.getString("CICLO_ID"));
		objeto.setTitulo(rs.getString("TITULO"));
		objeto.setFst(rs.getString("FST"));
		objeto.setFsp(rs.getString("FSP"));
		objeto.setCreditos(rs.getString("CREDITOS"));
		
		return objeto;
	}
}
