package aca.catalogo.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


public class CatAreaMapper implements RowMapper<CatArea> {
	public CatArea mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CatArea area = new CatArea();
		
		area.setAreaId(rs.getString("AREA_ID"));
		area.setNombreArea(rs.getString("NOMBRE_AREA"));
		area.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		area.setTipoPromedio(rs.getString("TIPO_PROMEDIO"));
		
		return area;
	}
}
