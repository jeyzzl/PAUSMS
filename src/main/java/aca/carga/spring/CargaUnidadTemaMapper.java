package aca.carga.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CargaUnidadTemaMapper implements RowMapper<CargaUnidadTema>{
	
	public CargaUnidadTema mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CargaUnidadTema objeto = new CargaUnidadTema();
		
		objeto.setTemaId(rs.getString("TEMA_ID"));
		objeto.setCursoCargaId(rs.getString("CURSO_CARGA_ID"));
		objeto.setTemaNombre(rs.getString("TEMA_NOMBRE"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setOrden(rs.getString("ORDEN"));
		
		return objeto;
	}
}