package aca.alumno.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AlumFamiliaMapper implements RowMapper<AlumFamilia>{
	@Override
	public AlumFamilia mapRow(ResultSet rs, int arg1) throws SQLException {
		
		AlumFamilia objeto = new AlumFamilia();		
		objeto.setFamiliaId(rs.getString("FAMILIA_ID"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setEstado(rs.getString("ESTADO"));
	
		return objeto;
	}
}