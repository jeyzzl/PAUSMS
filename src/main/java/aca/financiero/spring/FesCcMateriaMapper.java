package aca.financiero.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class FesCcMateriaMapper implements RowMapper<FesCcMateria>{

	public FesCcMateria mapRow(ResultSet rs, int rowNum) throws SQLException {
		FesCcMateria objeto = new FesCcMateria();
		
		objeto.setMatricula(rs.getString("MATRICULA"));
		objeto.setCargaId(rs.getString("CARGA_ID"));		
		objeto.setBloque(rs.getString("BLOQUE"));
		objeto.setCursoCargaId(rs.getString("CURSO_CARGA_ID"));
		objeto.setCursoId(rs.getString("CURSO_ID"));
		objeto.setCostoCredito(rs.getString("COSTO_CREDITO"));
		objeto.setCostoCurso(rs.getString("COSTO_CURSO"));
		
		return objeto;
	}

}