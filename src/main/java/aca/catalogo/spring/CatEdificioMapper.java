package aca.catalogo.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CatEdificioMapper implements RowMapper<CatEdificio> {
	public CatEdificio mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CatEdificio objeto = new CatEdificio();		
		
		objeto.setEdificioId(rs.getString("EDIFICIO_ID"));
		objeto.setNombreEdificio(rs.getString("NOMBRE_EDIFICIO"));	
//		objeto.setUsuarios(rs.getString("USUARIOS"));
		
		return objeto;
	}
}
