package aca.matricula.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CambioMateriaMapper implements RowMapper<CambioMateria> {

	public CambioMateria mapRow(ResultSet rs, int rowNum) throws SQLException {
		CambioMateria objeto = new CambioMateria();
		
		objeto.setSolicitudId(rs.getString("SOLICITUD_ID"));
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setCursoCargaId(rs.getString("CURSO_CARGA_ID"));
		objeto.setCursoId(rs.getString("CURSO_ID"));
		objeto.setTipo(rs.getString("TIPO"));
		
		return objeto;
	}

}
