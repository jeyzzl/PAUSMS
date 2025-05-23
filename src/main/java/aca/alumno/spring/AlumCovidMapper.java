package aca.alumno.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AlumCovidMapper implements RowMapper<AlumCovid>{
	@Override
	public AlumCovid mapRow(ResultSet rs, int arg1) throws SQLException{
		
		AlumCovid objeto = new AlumCovid();		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setPeriodoId(rs.getString("PERIODO_ID"));		
		objeto.setTipo(rs.getString("TIPO"));		
		objeto.setFechaLlegada(rs.getString("FECHA_LLEGADA"));		
		objeto.setVacuna(rs.getString("VACUNA"));		
		objeto.setFechaVacuna(rs.getString("FECHA_VACUNA"));		
		objeto.setPositivoCovid(rs.getString("POSITIVO_COVID"));		
		objeto.setFechaPositivo(rs.getString("FECHA_POSITIVO"));
		objeto.setSospechoso(rs.getString("SOSPECHOSO"));	
		objeto.setFechaSospechoso(rs.getString("FECHA_SOSPECHOSO"));
		objeto.setAislamiento(rs.getString("AISLAMIENTO"));		
		objeto.setFinAislamiento(rs.getString("FIN_AISLAMIENTO"));	
		objeto.setUsuarioAlta(rs.getString("USUARIO_ALTA"));	
		objeto.setFechaAlta(rs.getString("FECHA_ALTA"));		
		objeto.setComentario(rs.getString("COMENTARIO"));			
		objeto.setValidado(rs.getString("VALIDADO"));
			
		return objeto;
	}
}