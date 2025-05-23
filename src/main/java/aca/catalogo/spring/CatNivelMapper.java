package aca.catalogo.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CatNivelMapper implements RowMapper<CatNivel> {
	public CatNivel mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CatNivel objeto = new CatNivel();
		
		objeto.setNivelId(rs.getString("NIVEL_ID"));
		objeto.setNombreNivel(rs.getString("NOMBRE_NIVEL"));
		objeto.setEstado(rs.getString("ESTADO"));
		objeto.setOrden(rs.getString("ORDEN"));
				
		return objeto;
	}
}
