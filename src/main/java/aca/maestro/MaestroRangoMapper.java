package aca.maestro;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class MaestroRangoMapper implements RowMapper<MaestroRango> {
	public MaestroRango mapRow(ResultSet rs, int arg1) throws SQLException {
		
		MaestroRango objeto = new MaestroRango();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setYear(rs.getString("YEAR"));	
		objeto.setArea(rs.getString("AREA"));	
		objeto.setRangoAnterior(rs.getString("RANGO_ANTERIOR"));	
		objeto.setRangoRecomendado(rs.getString("RANGO_RECOMENDADO"));	
		objeto.setRespuesta(rs.getString("RESPUESTA"));	
		objeto.setNombre(rs.getString("NOMBRE"));	
		
		return objeto;
	}
}
