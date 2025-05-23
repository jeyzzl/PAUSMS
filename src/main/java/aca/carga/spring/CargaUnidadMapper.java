package aca.carga.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CargaUnidadMapper implements RowMapper<CargaUnidad>{
	
	public CargaUnidad mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CargaUnidad objeto = new CargaUnidad();
		
		objeto.setUnidadId(rs.getString("UNIDAD_ID"));
		objeto.setCursoCargaId(rs.getString("CURSO_CARGA_ID"));
		objeto.setUnidadNombre(rs.getString("UNIDAD_NOMBRE"));
		objeto.setOrden(rs.getString("ORDEN"));
		
		return objeto;
	}
}