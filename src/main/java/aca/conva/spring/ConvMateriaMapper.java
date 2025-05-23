package aca.conva.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ConvMateriaMapper implements RowMapper<ConvMateria> {
	public ConvMateria mapRow(ResultSet rs, int arg1) throws SQLException {
		
		ConvMateria materia = new ConvMateria();

		materia.setConvalidacionId(rs.getString("CONVALIDACION_ID"));
		materia.setCursoId(rs.getString("CURSO_ID"));
		materia.setFecha(rs.getString("FECHA"));
		materia.setTipo(rs.getString("TIPO"));
		materia.setEstado(rs.getString("ESTADO"));
		materia.setMateria_O(rs.getString("MATERIA_O"));
		materia.setCreditos_O(rs.getString("CREDITOS_O"));
		materia.setNota_O(rs.getString("NOTA_O"));
		materia.setfNota(rs.getString("FECHA_NOTA"));
		materia.setFolio(rs.getString("FOLIO"));
		return materia;
	}
}
