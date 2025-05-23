package aca.catalogo.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CatEscuelaMapper implements RowMapper<CatEscuela> {
	public CatEscuela mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CatEscuela objeto = new CatEscuela();
		
		objeto.setEscuelaId(rs.getString("ESCUELA_ID"));
		objeto.setNombreEscuela(rs.getString("NOMBRE_ESCUELA"));
		objeto.setPaisId(rs.getString("PAIS_ID"));
		objeto.setEstadoId(rs.getString("ESTADO_ID"));
		objeto.setCiudadId(rs.getString("CIUDAD_ID"));
		
		return objeto;
	}
}
