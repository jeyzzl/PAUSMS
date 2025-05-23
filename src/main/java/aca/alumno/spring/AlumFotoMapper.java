package aca.alumno.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AlumFotoMapper implements RowMapper<AlumFoto>{
	@Override
	public AlumFoto mapRow(ResultSet rs, int arg1) throws SQLException {
		
		AlumFoto objeto = new AlumFoto();		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setUsuario(rs.getString("USUARIO"));	
		objeto.setFoto(rs.getBytes("FOTO"));	
			
		return objeto;
	}
}