package aca.admision.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AdmPasosMapper implements RowMapper<AdmPasos> {
	@Override
	public AdmPasos mapRow(ResultSet rs, int rowNum) throws SQLException {
		AdmPasos objeto = new AdmPasos();
		
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setUsuario(rs.getString("USUARIO"));
		objeto.setPasos(rs.getString("PASOS"));
		objeto.setEstado(rs.getString("ESTADO"));
		objeto.setComentario(rs.getString("COMENTARIO"));
		
		return objeto;
	}

}
