package aca.tit.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class TitInstitucionMapper implements RowMapper<TitInstitucion>{
	
	public TitInstitucion mapRow(ResultSet rs, int arg1) throws SQLException {
		
		TitInstitucion objeto = new TitInstitucion();
		
		objeto.setCveInstitucion(rs.getString("CVEINSTITUCION"));
		objeto.setNombreInstitucion(rs.getString("NOMBREINSTITUCION"));
		objeto.setInstitucion(rs.getString("INSTITUCION"));
		
		return objeto;
	}
}