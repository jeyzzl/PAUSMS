package aca.carga.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CargaUnidadInstrumentoMapper implements RowMapper<CargaUnidadInstrumento>{
	
	public CargaUnidadInstrumento mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CargaUnidadInstrumento objeto = new CargaUnidadInstrumento();
		
		objeto.setInstrumentoId(rs.getString("INSTRUMENTO_ID"));
		objeto.setCursoCargaId(rs.getString("CURSO_CARGA_ID"));
		objeto.setInstrumentoNombre(rs.getString("INSTRUMENTO_NOMBRE"));
		
		return objeto;
	}
}