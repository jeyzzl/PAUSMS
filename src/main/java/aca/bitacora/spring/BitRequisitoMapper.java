package aca.bitacora.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class BitRequisitoMapper implements RowMapper<BitRequisito> {
	
	@Override
	public BitRequisito mapRow(ResultSet rs, int arg1) throws SQLException {
		
		BitRequisito objeto = new BitRequisito();
		
		objeto.setRequisitoId(rs.getString("REQUISITO_ID"));
		objeto.setNombre(rs.getString("NOMBRE"));
		objeto.setTipo(rs.getString("TIPO"));
		objeto.setComentario(rs.getString("COMENTARIO"));
		
		return objeto;
	}
}