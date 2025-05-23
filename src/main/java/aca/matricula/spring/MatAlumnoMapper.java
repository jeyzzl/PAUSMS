package aca.matricula.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class MatAlumnoMapper implements RowMapper<MatAlumno>{
	
	public MatAlumno mapRow(ResultSet rs, int arg1) throws SQLException {
		
		MatAlumno objeto = new MatAlumno();
		objeto.setEventoId(rs.getString("EVENTO_ID"));
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setPlanId(rs.getString("PLAN_ID"));
		objeto.setFecha(rs.getString("FECHA"));		
		objeto.setUsuario(rs.getString("USUARIO"));
		objeto.setModo(rs.getString("MODO"));
		objeto.setEstado(rs.getString("ESTADO"));
		
		return objeto;
	}
}