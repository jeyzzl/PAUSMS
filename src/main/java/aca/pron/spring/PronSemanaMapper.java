package aca.pron.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class PronSemanaMapper implements RowMapper<PronSemana> {

	public PronSemana mapRow(ResultSet rs, int rowNum) throws SQLException {
		PronSemana objeto = new PronSemana();
		
		objeto.setCursoCargaId(rs.getString("CURSO_CARGA_ID"));
		objeto.setUnidadId(rs.getString("UNIDAD_ID"));
		objeto.setSemanaId(rs.getString("SEMANA_ID"));
		objeto.setSemanaNombre(rs.getString("SEMANA_NOMBRE"));
		objeto.setContenido(rs.getString("CONTENIDO"));
		objeto.setActividades(rs.getString("ACTIVIDADES"));
		objeto.setEvidencias(rs.getString("EVIDENCIAS"));
		objeto.setOrden(rs.getString("ORDEN"));
		
		return objeto;
	}

}
