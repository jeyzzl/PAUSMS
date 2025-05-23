package aca.vista.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CargaAcademicaCargaMapper implements RowMapper<CargaAcademicaCarga>{

	public CargaAcademicaCarga mapRow(ResultSet rs, int arg1) throws SQLException {
		CargaAcademicaCarga objeto = new CargaAcademicaCarga();
		
		objeto.setPlanId(rs.getString("PLAN_ID"));
		objeto.setCarreraId(rs.getString("CARRERA_ID"));
		objeto.setCiclo(rs.getString("CICLO"));
		objeto.setCursoId(rs.getString("CURSO_ID"));
		objeto.setCursoCargaId(rs.getString("CURSO_CARGA_ID"));
		objeto.setNombreCurso(rs.getString("NOMBRE_CURSO"));
		objeto.setGrupo(rs.getString("GRUPO"));
		objeto.setLetra(rs.getString("LETRA"));
		objeto.setProfesor(rs.getString("PROFESOR"));
		objeto.setCarrera(rs.getString("CARRERA"));
		objeto.setlCarrera(rs.getString("L_CARRERA"));
		objeto.setHoras(rs.getString("HORAS"));
		objeto.setCreditos(rs.getString("CREDITOS"));
		objeto.setEstado(rs.getString("ESTADO"));
		objeto.setModalidad(rs.getString("MODALIDAD"));
		objeto.setBloque(rs.getString("BLOQUE"));
		objeto.setOptativa(rs.getString("OPTATIVA"));
		
		return objeto;
	}

}
