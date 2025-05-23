package aca.notifica.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class NotiMensajesMapper implements RowMapper<NotiMensajes>{
	
	public NotiMensajes mapRow(ResultSet rs, int arg1) throws SQLException {
		
		NotiMensajes objeto = new NotiMensajes();
		objeto.setId(rs.getString("ID"));
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setTipo(rs.getString("TIPO"));
		objeto.setMensaje(rs.getString("MENSAJE"));
		objeto.setUrl(rs.getString("URL"));
		objeto.setSilenciado(rs.getString("SILENCIADO"));
		objeto.setVisto(rs.getString("VISTO"));
		objeto.setFecha(rs.getString("FECHA"));		
		objeto.setSms(rs.getString("SMS"));
		
		return objeto;
	}
}