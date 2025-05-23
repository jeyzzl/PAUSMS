package aca.mensaje.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class MensajeMapper implements RowMapper<Mensaje>{

	public Mensaje mapRow(ResultSet rs, int arg1) throws SQLException {
		Mensaje objeto = new Mensaje();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setMensaje1(rs.getString("MENSAJE1"));
		objeto.setMensaje2(rs.getString("MENSAJE2"));

		return objeto;
	}

}
