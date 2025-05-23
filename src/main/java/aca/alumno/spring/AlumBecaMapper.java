package aca.alumno.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AlumBecaMapper implements RowMapper<AlumBeca>{
	@Override
	public AlumBeca mapRow(ResultSet rs, int arg1) throws SQLException {
		
		AlumBeca objeto = new AlumBeca();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setBeca(rs.getString("BECA"));
		
		return objeto;
	}
}
