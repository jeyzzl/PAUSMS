package aca.rotaciones.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class RotProgramacionMapper implements RowMapper<RotProgramacion>{

	public RotProgramacion mapRow(ResultSet rs, int arg1) throws SQLException {
		RotProgramacion objeto = new RotProgramacion();
		
		objeto.setProgramacionId(rs.getString("PROGRAMACION_ID"));
		objeto.setHospitalId(rs.getString("HOSPITAL_ID"));
		objeto.setEspecialidadId(rs.getString("ESPECIALIDAD_ID"));
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setfInicio(rs.getString("F_INICIO"));
		objeto.setfFinal(rs.getString("F_FINAL"));
		objeto.setInscripcion(rs.getString("INSCRIPCION"));
		objeto.setPago(rs.getString("PAGO"));
		objeto.setAnual(rs.getString("ANUAL"));
		objeto.setIntegrada(rs.getString("INTEGRADA"));

		return objeto;
	}
}
