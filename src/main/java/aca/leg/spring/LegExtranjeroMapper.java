package aca.leg.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class LegExtranjeroMapper implements RowMapper<LegExtranjero> {

	public LegExtranjero mapRow(ResultSet rs, int rowNum) throws SQLException {
		LegExtranjero objeto = new LegExtranjero();
		
		objeto.setCodigo(rs.getString("CODIGO"));
		objeto.setRne(rs.getString("RNE"));
		objeto.setCarrera(rs.getString("CARRERA"));
		objeto.setComentario(rs.getString("COMENTARIO"));	
		
		return objeto;
	}

}
