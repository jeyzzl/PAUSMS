package aca.admision.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AdmDocAlumMapper implements RowMapper<AdmDocAlum> {

	@Override
	public AdmDocAlum mapRow(ResultSet rs, int arg1) throws SQLException {
		AdmDocAlum objeto = new AdmDocAlum();
		
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setDocumentoId(rs.getString("DOCUMENTO_ID"));
		objeto.setEstado(rs.getString("ESTADO"));
		objeto.setUsuario(rs.getString("USUARIO"));
		objeto.setComentario(rs.getString("COMENTARIO"));
		objeto.setCarta(rs.getString("CARTA"));
		
		return objeto;
	}

}
