package aca.catalogo.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CatEdificioUsuarioMapper implements RowMapper<CatEdificioUsuario> {
	public CatEdificioUsuario mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CatEdificioUsuario objeto = new CatEdificioUsuario();
		
		objeto.setEdificioId(rs.getString("EDIFICIO_ID"));
		objeto.setCodigo_personal(rs.getString("CODIGO_PERSONAL"));		
		
		return objeto;
	}
}
