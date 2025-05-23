package aca.idioma.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class MsgEspMapper implements RowMapper<MsgEsp> {
	public MsgEsp mapRow(ResultSet rs, int rowNum) throws SQLException {
		MsgEsp objeto = new MsgEsp();
		
		objeto.setClave(rs.getString("CLAVE"));
		objeto.setValor(rs.getString("VALOR"));		
		
		return objeto;
	}

}
