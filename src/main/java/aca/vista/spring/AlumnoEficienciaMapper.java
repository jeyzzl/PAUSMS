package aca.vista.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AlumnoEficienciaMapper implements RowMapper<AlumnoEficiencia>{

	public AlumnoEficiencia mapRow(ResultSet rs, int arg1) throws SQLException {
		AlumnoEficiencia objeto = new AlumnoEficiencia();
		
		objeto.setCursoCargaId(rs.getString("CURSO_CARGA_ID"));
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setEvaluacionId(rs.getString("EVALUACION_ID"));
		objeto.setTipo(rs.getString("TIPO"));
		objeto.setValor(rs.getString("VALOR"));
		objeto.setNota(rs.getString("NOTA"));
		objeto.setEvaluadas(rs.getString("EVALUADAS"));
		objeto.setPuntos(rs.getString("PUNTOS"));
		objeto.setTotActividades(rs.getString("TOT_ACTIVIDADES"));
		
		return objeto;
	}

}
