package aca.saum.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class SaumComidaMapper implements RowMapper<SaumComida>{
	public SaumComida mapRow(ResultSet rs, int arg1) throws SQLException {
		
		SaumComida objeto = new SaumComida();
		
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setRecetaId(rs.getString("RECETA_ID"));
		objeto.setComida(rs.getString("COMIDA"));	
		objeto.setRendimiento(rs.getString("RENDIMIENTO"));
		
		return objeto;
	}
}