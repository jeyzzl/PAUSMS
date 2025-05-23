package aca.cert.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CertAlumNotaMapper implements RowMapper<CertAlumNota> {
	public CertAlumNota mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CertAlumNota objeto = new CertAlumNota();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setPlanId(rs.getString("PLAN_ID"));
		objeto.setCicloId(rs.getString("CICLO_ID"));
		objeto.setCursoId(rs.getString("CURSO_ID"));
		objeto.setCurso(rs.getString("CURSO"));
		objeto.setEstado(rs.getString("ESTADO"));
		objeto.setNota(rs.getString("NOTA"));
		objeto.setNotaLetra(rs.getString("NOTA_LETRA"));
		objeto.setOptativa(rs.getString("OPTATIVA"));
		objeto.setPromedia(rs.getString("PROMEDIA"));
	
		return objeto;
	}
}
