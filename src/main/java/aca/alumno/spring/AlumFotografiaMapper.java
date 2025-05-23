package aca.alumno.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AlumFotografiaMapper implements RowMapper<AlumFotografia>{
	@Override
	public AlumFotografia mapRow(ResultSet rs, int arg1) throws SQLException {
		
		AlumFotografia objeto = new AlumFotografia();		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setResolucion(rs.getString("RESOLUCION"));
			
		return objeto;
	}
}