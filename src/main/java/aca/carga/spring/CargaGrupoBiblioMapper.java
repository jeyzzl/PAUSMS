package aca.carga.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CargaGrupoBiblioMapper implements RowMapper<CargaGrupoBiblio>{
	
	public CargaGrupoBiblio mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CargaGrupoBiblio objeto = new CargaGrupoBiblio();
		
		objeto.setCursoCargaId(rs.getString("CURSO_CARGA_ID"));
		objeto.setBiblioId(rs.getString("BIBLIO_ID"));
		objeto.setBibliografia(rs.getString("BIBLIOGRAFIA"));
		objeto.setOrden(rs.getString("ORDEN"));
	
		return objeto;
	}
}