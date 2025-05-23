package aca.alumno.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AlumPlanMapper implements RowMapper<AlumPlan>{
	@Override
	public AlumPlan mapRow(ResultSet rs, int arg1) throws SQLException {
		
		AlumPlan objeto = new AlumPlan();
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setPlanId(rs.getString("PLAN_ID"));
		objeto.setFInicio(rs.getString("F_INICIO"));
		objeto.setEstado(rs.getString("ESTADO"));
		objeto.setEscuelaId(rs.getString("ESCUELA_ID"));
		objeto.setAvanceId(rs.getString("AVANCE_ID"));
		objeto.setPermiso(rs.getString("PERMISO"));
		objeto.setEvento(rs.getString("EVENTO"));
		objeto.setFGraduacion(rs.getString("F_GRADUACION"));
		objeto.setFEgreso(rs.getString("F_EGRESO"));
		objeto.setGrado(rs.getString("GRADO"));
		objeto.setCiclo(rs.getString("CICLO"));
		objeto.setPrincipal(rs.getString("PRINCIPAL"));
		objeto.setEscala(rs.getString("ESCALA"));
		objeto.setPrimerMatricula(rs.getString("PRIMER_MATRICULA"));
		objeto.setActualizado(rs.getString("ACTUALIZADO"));
		objeto.setCicloSep(rs.getString("CICLO_SEP"));
		objeto.setPromedio(rs.getString("PROMEDIO"));
		objeto.setCreditos(rs.getString("CREDITOS"));
		objeto.setFinalizado(rs.getString("FINALIZADO"));
		objeto.setMayor(rs.getString("MAYOR"));
		objeto.setMenor(rs.getString("MENOR"));
		
		return objeto;
	}
}