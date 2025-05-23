package aca.admision.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AdmEvaluacionNotaMapper implements RowMapper<AdmEvaluacionNota> {
	@Override
	public AdmEvaluacionNota mapRow(ResultSet rs, int rowNum) throws SQLException {
		AdmEvaluacionNota objeto = new AdmEvaluacionNota();
		
		objeto.setEvaluacionId(rs.getString("EVALUACION_ID"));
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setNota(rs.getString("NOTA"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setUsuario(rs.getString("USUARIO"));
		objeto.setComentario(rs.getString("COMENTARIO"));
		
		return objeto;
	}

}
