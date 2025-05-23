package aca.cert.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CertCursoMapper implements RowMapper<CertCurso> {
	public CertCurso mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CertCurso objeto = new CertCurso();
		
		objeto.setCursoId(rs.getString("CURSO_ID"));
		objeto.setPlanId(rs.getString("PLAN_ID"));
		objeto.setClave(rs.getString("CLAVE"));
		objeto.setCicloId(rs.getString("CICLO_ID"));
		objeto.setCursoNombre(rs.getString("CURSO_NOMBRE"));
		objeto.setFst(rs.getString("FST"));
		objeto.setFsp(rs.getString("FSP"));
		objeto.setCreditos(rs.getString("CREDITOS"));
		objeto.setOrden(rs.getString("ORDEN"));
		objeto.setTipoCursoId(rs.getString("TIPOCURSO_ID"));
		objeto.setCreditos2(rs.getString("CREDITOS2"));
		
		return objeto;
	}
}
