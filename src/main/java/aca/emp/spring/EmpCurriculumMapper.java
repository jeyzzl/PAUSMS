package aca.emp.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class EmpCurriculumMapper implements RowMapper<EmpCurriculum> {
	public EmpCurriculum mapRow(ResultSet rs, int arg1) throws SQLException {
		
		EmpCurriculum objeto = new EmpCurriculum();
		
		objeto.setIdEmpleado(rs.getString("ID_EMPLEADO"));
		objeto.setLugarNac(rs.getString("LUGAR_NAC"));	
		objeto.setTProfesional(rs.getString("T_PROFESIONAL"));
		objeto.setGPosgrado(rs.getString("G_POSGRADO"));	
		objeto.setTUniversitario(rs.getString("T_UNIVERSITARIO"));
		objeto.setRespActual(rs.getString("RESP_ACTUAL"));
		objeto.setRespAnterior(rs.getString("RESP_ANTERIOR"));
		objeto.setExpDocente(rs.getString("EXP_DOCENTE"));
		objeto.setFNacimiento(rs.getString("F_NACIMIENTO"));
		objeto.setNacionalidad(rs.getString("NACIONALIDAD"));
		objeto.setNivelId(rs.getString("NIVEL_ID"));
		objeto.setRevisado(rs.getString("REVISADO"));
		objeto.settDoctorado(rs.getString("T_DOCTORADO"));
		objeto.setFechaLic(rs.getString("FECHA_LIC"));
		objeto.setFechaMae(rs.getString("FECHA_MAE"));
		objeto.setFechaDoc(rs.getString("FECHA_DOC"));
		objeto.setInstLic(rs.getString("INST_LIC"));
		objeto.setInstMae(rs.getString("INST_MAE"));
		objeto.setInstDoc(rs.getString("INST_DOC"));
		
		return objeto;
	}
}
