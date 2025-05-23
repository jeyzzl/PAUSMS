package aca.musica.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class MusiInstitucionMapper implements RowMapper<MusiInstitucion> {
	public MusiInstitucion mapRow(ResultSet rs, int arg1) throws SQLException {
		
		MusiInstitucion objeto = new MusiInstitucion();
		
		objeto.setInstitucionId(rs.getString("INSTITUCION_ID"));
		objeto.setInstitucionNombre(rs.getString("INSTITUCION_NOMBRE"));		
		
		return objeto;
	}
}
