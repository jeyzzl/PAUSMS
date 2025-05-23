package aca.disciplina.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CondAlumnoMapper implements RowMapper<CondAlumno>{
	
	public CondAlumno mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CondAlumno objeto = new CondAlumno();
		
		objeto.setMatricula(rs.getString("MATRICULA"));
		objeto.setPeriodoId(rs.getString("PERIODO_ID"));
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setIdReporte(rs.getString("IDREPORTE"));
		objeto.setIdLugar(rs.getString("IDLUGAR"));
		objeto.setIdJuez(rs.getString("IDJUEZ"));
		objeto.setEmpleado(rs.getString("EMPLEADO"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setCantidad(rs.getString("CANTIDAD"));
		objeto.setComentario(rs.getString("COMENTARIO"));
		objeto.setPlanId(rs.getString("PLAN_ID"));

		return objeto;
	}
}