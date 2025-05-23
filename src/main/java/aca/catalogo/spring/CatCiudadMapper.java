package aca.catalogo.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CatCiudadMapper implements RowMapper<CatCiudad> {
	public CatCiudad mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CatCiudad ciudad = new CatCiudad();
		
		ciudad.setPaisId(rs.getString("PAIS_ID"));
		ciudad.setEstadoId(rs.getString("ESTADO_ID"));
		ciudad.setCiudadId(rs.getString("CIUDAD_ID"));
		ciudad.setNombreCiudad(rs.getString("NOMBRE_CIUDAD"));	
		
		return ciudad;
	}
}
