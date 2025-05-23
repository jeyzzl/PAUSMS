package aca.catalogo.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CatInstrumentoMapper implements RowMapper<CatInstrumento> {
	public CatInstrumento mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CatInstrumento objeto = new CatInstrumento();
		
		objeto.setInstrumentoId(rs.getString("INSTRUMENTO_ID"));
		objeto.setDescripcion(rs.getString("DESCRIPCION"));
				
		return objeto;
	}
}
