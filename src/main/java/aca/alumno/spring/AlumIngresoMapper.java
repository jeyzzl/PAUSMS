package aca.alumno.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AlumIngresoMapper implements RowMapper<AlumIngreso>{
	@Override
	public AlumIngreso mapRow(ResultSet rs, int arg1) throws SQLException {
		
		AlumIngreso objeto = new AlumIngreso();		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setPlanId(rs.getString("PLAN_ID"));
		objeto.setCargaId(rs.getString("CARGA_ID"));
		objeto.setCarreraId(rs.getString("CARRERA_ID"));
		objeto.setNewUm(rs.getString("NEWUM"));
		objeto.setNewPlan(rs.getString("NEWPLAN"));
		
		return objeto;
	}
}