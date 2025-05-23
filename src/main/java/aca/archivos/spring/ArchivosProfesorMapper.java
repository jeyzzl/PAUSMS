package aca.archivos.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ArchivosProfesorMapper implements RowMapper<ArchivosProfesor>{
	@Override
	public ArchivosProfesor mapRow(ResultSet rs, int rowNum) throws SQLException {
		ArchivosProfesor objeto = new ArchivosProfesor();
		
		objeto.setArchivoId(rs.getString("ARCHIVO_ID"));
		objeto.setFolio(rs.getInt("FOLIO"));
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setNombre(rs.getString("NOMBRE"));
		objeto.setComentario(rs.getString("COMENTARIO"));
		objeto.setAutorizacion(rs.getString("AUTORIZACION"));
		objeto.setTamano(rs.getLong("TAMANO"));
		objeto.setOid(rs.getLong("ARCHIVO"));
		objeto.setArchivoNuevo(rs.getBytes("ARCHIVO_NUEVO"));

		return objeto;
	}
	
}