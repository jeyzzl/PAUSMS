package aca.kardex.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class KrdxAltaMapper implements RowMapper<KrdxAlta> {
	public KrdxAlta mapRow(ResultSet rs, int arg1) throws SQLException {
		
		KrdxAlta objeto = new KrdxAlta();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setCursoCargaId(rs.getString("CURSO_CARGA_ID"));
		objeto.setCargaId(rs.getString("CARGA_ID"));
		objeto.setBloqueId(rs.getString("BLOQUE_ID"));
		objeto.setCreditos(rs.getString("CREDITOS"));
		objeto.setCursoId(rs.getString("CURSO_ID"));
		objeto.setCarreraId(rs.getString("CARRERA_ID"));
		objeto.setModalidadId(rs.getString("MODALIDAD_ID"));
		objeto.setYear(rs.getString("YEAR"));
		objeto.setTipo(rs.getString("TIPO"));
		
		return objeto;
	}
}