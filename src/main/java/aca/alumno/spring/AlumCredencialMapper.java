package aca.alumno.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AlumCredencialMapper implements RowMapper<AlumCredencial>{
	@Override
	public AlumCredencial mapRow(ResultSet rs, int arg1) throws SQLException {
		
		AlumCredencial objeto = new AlumCredencial();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setNombres(rs.getString("NOMBRES"));
		objeto.setApellidos(rs.getString("APELLIDOS"));
		objeto.setCarrera(rs.getString("CARRERA"));
		objeto.setCotejado(rs.getString("COTEJADO"));
		objeto.setPeriodo1(rs.getString("PERIODO1"));
		objeto.setPeriodo2(rs.getString("PERIODO2"));
		objeto.setPeriodo3(rs.getString("PERIODO3"));
		
		return objeto;
	}
}
