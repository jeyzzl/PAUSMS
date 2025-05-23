package aca.investiga.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class InvComentarioMapper implements RowMapper<InvComentario> {
	
	public InvComentario mapRow(ResultSet rs, int rowNum) throws SQLException {
		InvComentario objeto = new InvComentario();
		
		objeto.setProyectoId(rs.getString("PROYECTO_ID"));
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setTipo(rs.getString("TIPO"));
		objeto.setComentario(rs.getString("COMENTARIO"));
		
		return objeto;
	}

}
