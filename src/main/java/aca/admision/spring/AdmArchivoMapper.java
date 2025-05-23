package aca.admision.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AdmArchivoMapper implements RowMapper<AdmArchivo> {
	@Override
	public AdmArchivo mapRow(ResultSet rs, int arg1) throws SQLException {
		AdmArchivo objeto = new AdmArchivo();
		
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setDocumentoId(rs.getString("DOCUMENTO_ID"));
		objeto.setArchivo(rs.getBytes("ARCHIVO"));
		objeto.setNombre(rs.getString("NOMBRE"));
		objeto.setFecha(rs.getString("FECHA"));
		
		return objeto;
	}
}
