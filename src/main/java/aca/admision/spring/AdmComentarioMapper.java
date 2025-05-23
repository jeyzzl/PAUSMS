package aca.admision.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AdmComentarioMapper implements RowMapper<AdmComentario> {
	@Override
	public AdmComentario mapRow(ResultSet rs, int rowNum) throws SQLException {
		AdmComentario objeto = new AdmComentario();
		
		objeto.setFolio((rs.getString("FOLIO")));
		objeto.setComentarioId((rs.getString("COMENTARIO_ID")));
		objeto.setTipo((rs.getString("TIPO")));
		objeto.setUsuario((rs.getString("USUARIO")));
		objeto.setFecha((rs.getString("FECHA")));
		objeto.setComentario((rs.getString("COMENTARIO")));
		objeto.setEstado((rs.getString("ESTADO")));
		
		return objeto;
	}

}
