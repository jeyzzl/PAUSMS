package aca.residencia.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ResCandadoMapper implements RowMapper<ResCandado>{
	
	public ResCandado mapRow(ResultSet rs, int arg1) throws SQLException {
		
		ResCandado objeto = new ResCandado();
		
		objeto.setUsuario(rs.getString("USUARIO"));
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setComentario(rs.getString("COMENTARIO"));
		objeto.setEstado(rs.getString("ESTADO"));
		
		return objeto;
	}
}