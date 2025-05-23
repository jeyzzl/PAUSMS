package aca.padre.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class PadrePersonalMapper implements RowMapper<PadrePersonal>{
	public PadrePersonal mapRow(ResultSet rs, int arg1) throws SQLException {
		PadrePersonal objeto = new PadrePersonal();
		
		objeto.setPadreId(rs.getString("PADRE_ID"));
		objeto.setNombre(rs.getString("NOMBRE"));
		objeto.setPaterno(rs.getString("PATERNO"));
		objeto.setMaterno(rs.getString("MATERNO"));
		objeto.setCorreo(rs.getString("CORREO"));
		objeto.setTelefono(rs.getString("TELEFONO"));
		objeto.setTipo(rs.getString("TIPO"));
 		
 		return objeto;
	}

}
