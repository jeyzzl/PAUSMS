package aca.carga.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CargaArchivoMapper implements RowMapper<CargaArchivo>{
	
	public CargaArchivo mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CargaArchivo objeto = new CargaArchivo();
		
		objeto.setCursoCargaId(rs.getString("CURSO_CARGA_ID"));
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setNombre(rs.getString("NOMBRE"));
		objeto.setEvaluacionId(rs.getString("EVALUCION_ID"));
		objeto.setActividadId(rs.getString("ACTIVIDAD_ID"));
		objeto.setUsuarioOrigen(rs.getString("USUARIO_ORIGEN"));
		objeto.setUsuarioDestino(rs.getString("USUARIO_DESTINO"));
		objeto.setComentario(rs.getString("COMENTARIO"));
		objeto.setRuta(rs.getString("RUTA"));
		objeto.setEstado(rs.getString("ESTADO"));
		
		return objeto;
	}
}