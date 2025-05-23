package aca.carga.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CargaGrupoAsistenciaMapper implements RowMapper<CargaGrupoAsistencia>{
	
	public CargaGrupoAsistencia mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CargaGrupoAsistencia objeto = new CargaGrupoAsistencia();
		
		objeto.setCargaGrupoId(rs.getString("CURSO_CARGA_ID"));
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setCursoId(rs.getString("CURSO_ID"));
		objeto.setEstado(rs.getString("ESTADO"));
		
		return objeto;
	}
}