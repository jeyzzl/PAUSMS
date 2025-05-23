package aca.ssoc.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class SsocRequisitoMapper implements RowMapper<SsocRequisito>{
	
	public SsocRequisito mapRow(ResultSet rs, int arg1) throws SQLException {
		
		SsocRequisito objeto = new SsocRequisito();
		
		objeto.setRequisitoId(rs.getString("REQUISITO_ID"));
		objeto.setRequisitoNombre(rs.getString("REQUISITO_NOMBRE"));
		objeto.setOrden(rs.getString("ORDEN"));
		
		return objeto;
	}
}