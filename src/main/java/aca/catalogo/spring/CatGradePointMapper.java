package aca.catalogo.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CatGradePointMapper implements RowMapper<CatGradePoint> {
	public CatGradePoint mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CatGradePoint objeto = new CatGradePoint();
		
		objeto.setGpId(rs.getString("GP_ID"));
		objeto.setGpNombre(rs.getString("GP_NOMBRE"));		
		objeto.setInicio(rs.getString("INICIO"));
		objeto.setFin(rs.getString("FIN"));
		objeto.setPuntos(rs.getString("PUNTOS"));
		objeto.setTitulo(rs.getString("TITULO"));
		
		return objeto;
	}
}
