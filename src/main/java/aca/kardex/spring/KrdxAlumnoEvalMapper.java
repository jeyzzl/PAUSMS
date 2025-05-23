package aca.kardex.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class KrdxAlumnoEvalMapper implements RowMapper<KrdxAlumnoEval> {
	public KrdxAlumnoEval mapRow(ResultSet rs, int arg1) throws SQLException {
		
		KrdxAlumnoEval objeto = new KrdxAlumnoEval();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setCursoCargaId(rs.getString("CURSO_CARGA_ID"));
		objeto.setEvaluacionId(rs.getString("EVALUACION_ID"));
		objeto.setNota(rs.getString("NOTA"));
		objeto.setEvaluacionE42(rs.getString("EVALUACION_E42"));
		
		return objeto;
	}
}