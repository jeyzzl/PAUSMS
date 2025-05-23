package aca.inscrito.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class InsAlumnoMapper implements RowMapper<InsAlumno> {
	public InsAlumno mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		InsAlumno objeto = new InsAlumno();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setCargaId(rs.getString("CARGA_ID"));		
		objeto.setPlanId(rs.getString("PLAN_ID"));		
		objeto.setCiclo(rs.getString("CICLO"));		
		objeto.setMaterias(rs.getString("MATERIAS"));		

		return objeto;
	}

}
