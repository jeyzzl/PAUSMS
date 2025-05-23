package aca.idioma.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class MsgIngMapper implements RowMapper<MsgIng> {
	public MsgIng mapRow(ResultSet rs, int rowNum) throws SQLException {
		MsgIng objeto = new MsgIng();
		
		objeto.setClave(rs.getString("CLAVE"));
		objeto.setValor(rs.getString("VALOR"));		
		
		return objeto;
	}

}
