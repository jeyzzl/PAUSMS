package aca.kardex.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class KrdxAlumnoActivMapper implements RowMapper<KrdxAlumnoActiv> {
	public KrdxAlumnoActiv mapRow(ResultSet rs, int arg1) throws SQLException {
		
		KrdxAlumnoActiv objeto = new KrdxAlumnoActiv();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setCursoCargaId(rs.getString("CURSO_CARGA_ID"));
		objeto.setActividadId(rs.getString("ACTIVIDAD_ID"));
		objeto.setNota(rs.getString("NOTA"));
		objeto.setActividadE42(rs.getString("ACTIVIDAD_E42"));
		objeto.setEvaluacionId(rs.getString("EVALUACION_ID"));
		
		return objeto;
	}
}