package aca.cert.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CertAlumMapper implements RowMapper<CertAlum> {
	public CertAlum mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CertAlum objeto = new CertAlum();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setPlanId(rs.getString("PLAN_ID"));
		objeto.setAvance(rs.getString("AVANCE"));
		objeto.setNumCursos(rs.getString("NUM_CURSOS"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setEquivalencia(rs.getString("EQUIVALENCIA"));
		objeto.setEstado(rs.getString("ESTADO"));
		objeto.setEncabezado(rs.getString("ENCABEZADO"));
		objeto.setLinea(rs.getString("LINEA"));
		
		return objeto;
	}
}
