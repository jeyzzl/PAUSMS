package aca.archivo.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ArchDocumentosMapper implements RowMapper<ArchDocumentos>{
	@Override
	public ArchDocumentos mapRow(ResultSet rs, int arg1) throws SQLException {
		
		ArchDocumentos objeto = new ArchDocumentos();
		
		objeto.setIdDocumento(rs.getString("IDDOCUMENTO"));
		objeto.setDescripcion(rs.getString("DESCRIPCION"));
		objeto.setImagen(rs.getString("IMAGEN"));
		objeto.setOrden(rs.getString("ORDEN"));
		objeto.setMostrar(rs.getString("MOSTRAR"));
		
		return objeto;
	}
}