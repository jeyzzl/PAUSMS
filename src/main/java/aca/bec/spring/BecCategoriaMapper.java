package aca.bec.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class BecCategoriaMapper implements RowMapper<BecCategoria> {
	
	@Override
	public BecCategoria mapRow(ResultSet rs, int arg1) throws SQLException {
		
		BecCategoria objeto = new BecCategoria();
		
		objeto.setCategoriaId(rs.getString("CATEGORIA_ID"));
		objeto.setCategoriaNombre(rs.getString("CATEGORIA_NOMBRE"));
		objeto.setUsuario(rs.getString("USUARIO"));
		objeto.setEstado(rs.getString("ESTADO"));
		objeto.setPdf(rs.getString("PDF"));
		
		
		return objeto;
	}
}
