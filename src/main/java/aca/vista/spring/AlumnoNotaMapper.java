package aca.vista.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AlumnoNotaMapper implements RowMapper<AlumnoNota>{

	public AlumnoNota mapRow(ResultSet rs, int arg1) throws SQLException {
		AlumnoNota objeto = new AlumnoNota();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setCursoCargaId(rs.getString("CURSO_CARGA_ID"));
		objeto.setEvaluados(rs.getString("EVALUADOS"));
		objeto.setPorcentaje(rs.getString("PORCENTAJE"));
		objeto.setPuntos(rs.getString("PUNTOS"));
		objeto.setExtras(rs.getString("EXTRAS"));		
		objeto.setNota(rs.getString("NOTA"));
		
		return objeto;
	}

}
