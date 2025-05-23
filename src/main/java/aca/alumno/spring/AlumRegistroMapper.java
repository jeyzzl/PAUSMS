package aca.alumno.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AlumRegistroMapper implements RowMapper<AlumRegistro> {
	@Override
	public AlumRegistro mapRow(ResultSet rs, int rowNum) throws SQLException {
		AlumRegistro objeto = new AlumRegistro();
		
		objeto.setId(rs.getString("ID"));
		objeto.setNombre(rs.getString("NOMBRE"));
		objeto.setPaterno(rs.getString("PATERNO"));
		objeto.setMaterno(rs.getString("MATERNO"));
		objeto.setCorreo(rs.getString("CORREO"));
		objeto.setTelefono(rs.getString("TELEFONO"));
		objeto.setCodigo(rs.getString("CODIGO"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setPlanId(rs.getString("PLAN_ID"));
		
		return objeto;
	}

}
