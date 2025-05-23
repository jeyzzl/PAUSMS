package aca.cert.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CertRelacionMapper implements RowMapper<CertRelacion> {
	public CertRelacion mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CertRelacion objeto = new CertRelacion();
		
		objeto.setCursoId(rs.getString("CURSO_ID"));
		objeto.setCursoCert(rs.getString("CURSO_CERT"));
		
		return objeto;
	}
}
