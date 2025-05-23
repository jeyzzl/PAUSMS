package aca.pg.archivo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AlumFotoMapperCorto implements RowMapper<AlumFoto>{
	
	public AlumFoto mapRow(ResultSet rs, int arg1) throws SQLException {
		
		AlumFoto objeto = new AlumFoto();		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setTipo(rs.getString("TIPO"));
		objeto.setFecha(rs.getString("FECHA"));		
		objeto.setUsuario(rs.getString("USUARIO"));
		
		return objeto;
	}
}