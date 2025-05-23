package aca.pron.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class PronBiblioMapper implements RowMapper<PronBiblio> {

	public PronBiblio mapRow(ResultSet rs, int rowNum) throws SQLException {
		PronBiblio objeto = new PronBiblio();

		objeto.setCursoCargaId(rs.getString("CURSO_CARGA_ID"));
		objeto.setBiblioId(rs.getString("BIBLIO_ID"));
		objeto.setBiblioNombre(rs.getString("BIBLIO_NOMBRE"));
		objeto.setOrden(rs.getString("ORDEN"));
		return objeto;
	}

}
