package aca.residencia.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ResComentarioMapper implements RowMapper<ResComentario>{
	
	public ResComentario mapRow(ResultSet rs, int arg1) throws SQLException {
		
		ResComentario objeto = new ResComentario();
		
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setResidenciaId(rs.getString("RESIDENCIA_ID"));
		objeto.setComentario(rs.getString("COMENTARIO"));
		objeto.setComentarioB(rs.getString("COMENTARIO_B"));
		
		return objeto;
	}
}