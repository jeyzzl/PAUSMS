package aca.baja.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class BajaCursoMapper implements RowMapper<BajaCurso>{
	@Override
	public BajaCurso mapRow(ResultSet rs, int arg1) throws SQLException {
		
		BajaCurso objeto = new BajaCurso();
		
		objeto.setBajaId(rs.getString("BAJA_ID"));
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setCursoCargaId(rs.getString("CURSO_CARGA_ID"));
		objeto.setCursoId(rs.getString("CURSO_ID"));
		objeto.setCreditos(rs.getString("CREDITOS"));
		
		return objeto;
	}
}