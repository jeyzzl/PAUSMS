package aca.rotaciones.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class RotEspecialidadMapper implements RowMapper<RotEspecialidad>{

	public RotEspecialidad mapRow(ResultSet rs, int arg1) throws SQLException {
		RotEspecialidad objeto = new RotEspecialidad();
		
		objeto.setEspecialidadId(rs.getString("ESPECIALIDAD_ID"));
		objeto.setEspecialidadNombre(rs.getString("ESPECIALIDAD_NOMBRE"));
		objeto.setCursoId(rs.getString("CURSO_ID"));
		objeto.setSemanas(rs.getString("SEMANAS"));
		objeto.setPlanId(rs.getString("PLAN_ID"));

		return objeto;
	}

}
