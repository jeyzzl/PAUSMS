package aca.exp.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ExpTipoMapper implements RowMapper<ExpTipo>{	
	@Override
	public ExpTipo mapRow(ResultSet rs, int arg1) throws SQLException {
		
		ExpTipo objeto = new ExpTipo();		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setTipo(rs.getString("TIPO"));		
		objeto.setEstado(rs.getString("ESTADO"));
		
		return objeto;
	}
}