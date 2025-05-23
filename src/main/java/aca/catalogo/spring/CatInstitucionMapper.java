package aca.catalogo.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CatInstitucionMapper implements RowMapper<CatInstitucion> {
	public CatInstitucion mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CatInstitucion objeto = new CatInstitucion();
		
		objeto.setInstitucionId(rs.getString("INSTITUCION_ID"));
		objeto.setNombreInstitucion(rs.getString("NOMBRE_INSTITUCION"));
				
		return objeto;
	}
}
