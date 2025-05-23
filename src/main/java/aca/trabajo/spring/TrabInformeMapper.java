package aca.trabajo.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class TrabInformeMapper implements RowMapper<TrabInforme>{
	
public TrabInforme mapRow(ResultSet rs, int arg1) throws SQLException {
		
		TrabInforme objeto = new TrabInforme();		
		
		objeto.setInformeId(rs.getString("INFORME_ID"));
		objeto.setNombreInforme(rs.getString("NOMBRE"));	
		objeto.setEstado(rs.getString("ESTADO"));
		objeto.setPeriodoId(rs.getString("PERIODO_ID"));
		
		return objeto;
}
	
	
}
