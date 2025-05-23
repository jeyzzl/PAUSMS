package aca.admision.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AdmImagenMapper implements RowMapper<AdmImagen> {
	@Override
	public AdmImagen mapRow(ResultSet rs, int arg1) throws SQLException {
		AdmImagen objeto = new AdmImagen();
		
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setDocumentoId(rs.getString("DOCUMENTO_ID"));
		objeto.setHoja(rs.getString("HOJA"));
		objeto.setImagen(rs.getBytes("IMAGEN"));
		objeto.setFecha(rs.getString("FECHA"));
		
		return objeto;
	}

}
