package aca;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class EnteroMapper implements RowMapper<Entero> {
	public Entero mapRow(ResultSet rs, int arg1) throws SQLException {
		
		Entero objeto = new Entero();		
		objeto.setValor(rs.getInt("VALOR"));
		
		return objeto;
	}
}
