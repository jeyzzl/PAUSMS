package aca.financiero.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class FinComentarioMapper implements RowMapper<FinComentario> {

	public FinComentario mapRow(ResultSet rs, int arg1) throws SQLException {
		
		FinComentario objeto = new FinComentario();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setComentario(rs.getString("COMENTARIO"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setUsuario(rs.getString("USUARIO"));
		objeto.setTipo(rs.getString("TIPO"));
		
		return objeto;
	}

}
