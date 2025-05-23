package aca.catalogo.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CatNivelInicioMapper implements RowMapper<CatNivelInicio> {
	public CatNivelInicio mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CatNivelInicio objeto = new CatNivelInicio();
		
		objeto.setNivelInicioId(rs.getString("NIVEL_INICIO_ID"));
		objeto.setNombreNivel(rs.getString("NOMBRE_NIVEL"));
		objeto.setNombreCorto(rs.getString("NOMBRE_CORTO"));
		
		return objeto;
	}
}
