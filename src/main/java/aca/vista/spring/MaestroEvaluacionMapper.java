package aca.vista.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class MaestroEvaluacionMapper implements RowMapper<MaestroEvaluacion>{

	public MaestroEvaluacion mapRow(ResultSet rs, int arg1) throws SQLException {
		MaestroEvaluacion objeto = new MaestroEvaluacion();
		
		objeto.setCursoCargaId(rs.getString("CURSO_CARGA_ID"));
		objeto.setMaestro(rs.getString("MAESTRO"));
		objeto.setEvaluacionId(rs.getString("EVALUACION_ID"));
		objeto.setNombreEvaluacion(rs.getString("NOMBRE_EVALUACION"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setEstragiaId(rs.getString("ESTRATEGIA_ID"));
		objeto.setValor(rs.getString("VALOR"));
		objeto.setTipo(rs.getString("TIPO"));
		objeto.setNumAlumnos(rs.getString("NUM_ALUMNOS"));
		objeto.setNumAct(rs.getString("NUM_ACT"));
		objeto.setNumEval(rs.getString("NUM_EVAL"));
		objeto.setActEval(rs.getString("ACT_EVAL"));
		
		return objeto;
	}

}
