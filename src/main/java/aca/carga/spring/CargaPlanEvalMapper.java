package aca.carga.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CargaPlanEvalMapper implements RowMapper<CargaPlanEval>{
	
	public CargaPlanEval mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CargaPlanEval objeto = new CargaPlanEval();
		
		objeto.setCursoCargaId(rs.getString("CURSO_CARGA_ID"));
		objeto.setEvaluacionId(rs.getString("EVALUACION_ID"));
		objeto.setEvaluacionNombre(rs.getString("EVALUACION_NOMBRE"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setValor(rs.getString("VALOR"));
		objeto.setActividadId(rs.getString("ACTIVIDAD_ID"));
		
		return objeto;
	}
}