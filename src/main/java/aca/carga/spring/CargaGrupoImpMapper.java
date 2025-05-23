package aca.carga.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CargaGrupoImpMapper implements RowMapper<CargaGrupoImp>{
	
	public CargaGrupoImp mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CargaGrupoImp objeto = new CargaGrupoImp();
		
		objeto.setGrupoId(rs.getString("GRUPO_ID"));
		objeto.setCursoId(rs.getString("CURSO_ID"));
		objeto.setMaestro(rs.getString("MAESTRO"));
		objeto.setAlumnos(rs.getString("ALUMNOS"));
		objeto.setCursoCargaId(rs.getString("CURSO_CARGA_ID"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setGrupo(rs.getString("GRUPO"));
		
		return objeto;
	}
}