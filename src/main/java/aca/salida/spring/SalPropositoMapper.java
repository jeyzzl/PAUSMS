package aca.salida.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class SalPropositoMapper implements RowMapper<SalProposito>{
	
	public SalProposito mapRow(ResultSet rs, int arg1) throws SQLException {
		
		SalProposito objeto = new SalProposito();
		
		objeto.setPropositoId(rs.getString("PROPOSITO_ID"));
		objeto.setPropositoNombre(rs.getString("PROPOSITO_NOMBRE"));
				
		return objeto;
	}
}