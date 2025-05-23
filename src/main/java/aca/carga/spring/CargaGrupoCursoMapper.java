package aca.carga.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CargaGrupoCursoMapper implements RowMapper<CargaGrupoCurso>{
	
	public CargaGrupoCurso mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CargaGrupoCurso objeto = new CargaGrupoCurso();
		
		objeto.setCursoCargaId(rs.getString("CURSO_CARGA_ID"));
		objeto.setCursoId(rs.getString("CURSO_ID"));
		objeto.setOrigen(rs.getString("ORIGEN"));
		objeto.setGrupoHorario(rs.getString("GRUPO_HORARIO"));
	
		return objeto;
	}
}