package aca.alumno.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AlumAsesorMapper implements RowMapper<AlumAsesor>{
	@Override
	public AlumAsesor mapRow(ResultSet rs, int arg1) throws SQLException {
		
		AlumAsesor objeto = new AlumAsesor();		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setPlanId(rs.getString("PLAN_ID"));
		objeto.setAsesorId(rs.getString("ASESOR_ID"));
		
		return objeto;
	}
}