package aca.reportes.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


public class AltasBajasMapper implements RowMapper<AltasBajas>{
	
	public AltasBajas mapRow(ResultSet rs, int arg1) throws SQLException {
		
		AltasBajas objeto = new AltasBajas();
		
		objeto.setPlanId(rs.getString("PLAN_ID"));
		objeto.setCarreraId(rs.getString("CARRERA_ID"));
		objeto.setFacultadId(rs.getString("FACULTAD_ID"));
		objeto.setMatricula(rs.getString("MATRICULA"));
		objeto.setNombre(rs.getString("NOMBRE"));
		
		return objeto;
	}
}