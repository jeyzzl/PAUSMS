package aca.carga.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CargaUnidadActividadMapper implements RowMapper<CargaUnidadActividad>{
	
	public CargaUnidadActividad mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CargaUnidadActividad objeto = new CargaUnidadActividad();
		
		objeto.setCursoCargaId(rs.getString("CURSO_CARGA_ID"));
		objeto.setActividadId(rs.getString("ACTIVIDAD_ID"));
		objeto.setActividadNombre(rs.getString("ACTIVIDAD_NOMBRE"));
		objeto.setComentario(rs.getString("COMENTARIO"));
		objeto.setValor(rs.getString("VALOR"));
		objeto.setOrden(rs.getString("ORDEN"));
		
		return objeto;
	}
}