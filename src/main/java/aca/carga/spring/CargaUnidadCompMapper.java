package aca.carga.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CargaUnidadCompMapper implements RowMapper<CargaUnidadComp>{
	
	public CargaUnidadComp mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CargaUnidadComp objeto = new CargaUnidadComp();
		
		objeto.setCursoCargaId(rs.getString("CURSO_CARGA_ID"));
		objeto.setUnidadId(rs.getString("UNIDAD_ID"));
		objeto.setCompetenciaId(rs.getString("COMPETENCIA_ID"));
		
		return objeto;
	}
}