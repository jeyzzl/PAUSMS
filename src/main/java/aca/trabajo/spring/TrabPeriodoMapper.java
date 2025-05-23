package aca.trabajo.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class TrabPeriodoMapper implements RowMapper<TrabPeriodo>{
	
public TrabPeriodo mapRow(ResultSet rs, int arg1) throws SQLException {
		
		TrabPeriodo objeto = new TrabPeriodo();		
		
		objeto.setPeriodoId(rs.getString("PERIODO_ID"));
		objeto.setNombrePeriodo(rs.getString("NOMBRE"));	
		objeto.setEstado(rs.getString("ESTADO"));
		objeto.setFechaIni(rs.getString("FECHA_INI"));
		objeto.setFechaFin(rs.getString("FECHA_FIN"));
		
		return objeto;
}
	
	
}
