package aca.catalogo.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CatBancoMapper implements RowMapper<CatBanco> {
	public CatBanco mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CatBanco objeto = new CatBanco();
		
		objeto.setBancoId(rs.getString("BANCO_ID"));
		objeto.setNombre(rs.getString("NOMBRE"));
		objeto.setNombreCorto(rs.getString("NOMBRE_CORTO"));
		objeto.setPaisId(rs.getString("PAIS_ID"));
		objeto.setSwift(rs.getString("SWIFT"));
		
		return objeto;
	}
}
