package aca.archivos.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ArchivosAlumnoMapper implements RowMapper<ArchivosAlumno>{
	@Override
	public ArchivosAlumno mapRow(ResultSet rs, int arg1) throws SQLException {
		
		ArchivosAlumno objeto = new ArchivosAlumno();		 
		objeto.setArchivoId(rs.getString("ARCHIVO_ID"));
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setNombre(rs.getString("NOMBRE"));
		objeto.setComentario(rs.getString("COMENTARIO"));
		objeto.setTamano(rs.getString("TAMANO"));
		objeto.setStatus(rs.getString("STATUS"));
		objeto.setArchivo(rs.getInt("ARCHIVO"));
		objeto.setArchivoNuevo(rs.getBytes("ARCHIVO_NUEVO"));	
		objeto.setActividadId(rs.getString("ACTIVIDAD_ID"));	
		objeto.setEvaluacionId(rs.getString("EVALUACION_ID"));
		
		return objeto;
	}
}