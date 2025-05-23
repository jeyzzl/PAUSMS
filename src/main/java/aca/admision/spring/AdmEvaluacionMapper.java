package aca.admision.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AdmEvaluacionMapper implements RowMapper<AdmEvaluacion> {
	@Override
	public AdmEvaluacion mapRow(ResultSet rs, int rowNum) throws SQLException {
		AdmEvaluacion objeto = new AdmEvaluacion();
		
		objeto.setEvaluacionId(rs.getString("EVALUACION_ID"));
		objeto.setEvaluacionNombre(rs.getString("EVALUACION_NOMBRE"));
		objeto.setAcceso(rs.getString("ACCESO"));
		objeto.setEstado(rs.getString("ESTADO"));
		objeto.setIcono(rs.getString("ICONO"));
		objeto.setPuntos(rs.getString("PUNTOS"));
		
		return objeto;
	}

}
