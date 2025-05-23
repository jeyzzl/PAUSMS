package aca.alumno.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AlumColorMapper implements RowMapper<AlumColor>{
	@Override
	public AlumColor mapRow(ResultSet rs, int arg1) throws SQLException {
		
		AlumColor objeto = new AlumColor();		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setColor(rs.getString("COLOR"));
		objeto.setMenu(rs.getString("MENU"));
		objeto.setReloj(rs.getString("RELOJ"));	
		objeto.setColorReloj(rs.getString("COLOR_RELOJ"));	
			
		return objeto;
	}
}