package aca.kardex.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class KrdxAlumnoEvalMapperCorto implements RowMapper<KrdxAlumnoEval> {
	public KrdxAlumnoEval mapRow(ResultSet rs, int arg1) throws SQLException {
		
		KrdxAlumnoEval objeto = new KrdxAlumnoEval();
		
		objeto.setCursoCargaId(rs.getString("CURSO_CARGA_ID"));
		objeto.setEvaluacionId(rs.getString("EVALUACION_ID"));
		objeto.setNota(rs.getString("NOTA"));
		
		return objeto;
	}
}