package aca.pg.archivos.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class PosArchivosProfesorMapper implements RowMapper<PosArchivosProfesor>{
	
	public PosArchivosProfesor mapRow(ResultSet rs, int arg1) throws SQLException {
		
		PosArchivosProfesor objeto = new PosArchivosProfesor();		 
		objeto.setArchivoId(rs.getString("ARCHIVO_ID"));
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setNombre(rs.getString("NOMBRE"));
		objeto.setComentario(rs.getString("COMENTARIO"));
		objeto.setAutorizacion(rs.getString("AUTORIZACION"));
		objeto.setTamano(rs.getString("TAMANO"));		
		objeto.setArchivo(rs.getInt("ARCHIVO"));
		
		return objeto;
	}
}