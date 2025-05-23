package aca.vista.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AlumnoEvaluacionMapper implements RowMapper<AlumnoEvaluacion>{

	public AlumnoEvaluacion mapRow(ResultSet rs, int arg1) throws SQLException {
		AlumnoEvaluacion objeto = new AlumnoEvaluacion();
		
		objeto.setCodigoPersonal(rs.getString("CURSO_CARGA_ID"));
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setEvaluacionId(rs.getString("EVALUACION_ID"));
		objeto.setNombreEvaluacion(rs.getString("NOMBRE_EVALUACION"));
		objeto.setEstrategiaId(rs.getString("ESTRATEGIA_ID"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setValor(rs.getString("VALOR"));
		objeto.setTipo(rs.getString("TIPO"));
		objeto.setNota(rs.getString("NOTA"));

		return objeto;
	}

}
