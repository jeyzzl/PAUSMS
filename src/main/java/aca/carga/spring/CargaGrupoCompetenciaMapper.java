package aca.carga.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CargaGrupoCompetenciaMapper implements RowMapper<CargaGrupoCompetencia>{
	
	public CargaGrupoCompetencia mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CargaGrupoCompetencia objeto = new CargaGrupoCompetencia();
		
		objeto.setCursoCargaId(rs.getString("CURSO_CARGA_ID"));
		objeto.setCompetenciaId(rs.getString("COMPETENCIA_ID"));
		objeto.setDescripcion(rs.getString("DESCRIPCION"));
		
		return objeto;
	}
}