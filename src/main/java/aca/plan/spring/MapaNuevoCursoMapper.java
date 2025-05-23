package aca.plan.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class MapaNuevoCursoMapper implements RowMapper<MapaNuevoCurso>{

	public MapaNuevoCurso mapRow(ResultSet rs, int arg1) throws SQLException {
		MapaNuevoCurso objeto = new MapaNuevoCurso();
		
		objeto.setPlanId(rs.getString("PLAN_ID"));
		objeto.setCursoId(rs.getString("CURSO_ID"));			
		objeto.setVersionId(rs.getString("VERSION_ID"));
		objeto.setClave(rs.getString("CLAVE"));
		objeto.setNombre(rs.getString("NOMBRE"));
		objeto.setCiclo(rs.getString("CICLO"));
		objeto.setFCreada(rs.getString("F_CREADA"));
		objeto.setCodigoMaestro(rs.getString("CODIGO_MAESTRO"));
		objeto.setUbicacion(rs.getString("UBICACION"));
		objeto.setSeriacion(rs.getString("SERIACION"));
		objeto.setHst(rs.getString("HST"));
		objeto.setHsp(rs.getString("HSP"));
		objeto.setThs(rs.getString("THS"));
		objeto.setHt(rs.getString("HT"));
		objeto.setCreditos(rs.getString("CREDITOS"));
		objeto.setDescripcion(rs.getString("DESCRIPCION"));
		objeto.setBibliografia(rs.getString("BIBLIOGRAFIA"));
		objeto.setCompetencia(rs.getString("COMPETENCIA"));
		objeto.setMediosRecursos(rs.getString("MEDIOS_RECURSOS"));
		objeto.setEeDiagnostica(rs.getString("EE_DIAGNOSTICA"));
		objeto.setEeFormativa(rs.getString("EE_FORMATIVA"));
		objeto.setEeSumativa(rs.getString("EE_SUMATIVA"));
		objeto.setEscala(rs.getString("ESCALA"));
		objeto.setEstado(rs.getString("ESTADO"));
		objeto.setHei(rs.getString("HEI"));
		objeto.setHfd(rs.getString("HFD"));
		objeto.setIdioma(rs.getString("IDIOMA"));
		objeto.setHss(rs.getString("HSS"));
		objeto.setHas(rs.getString("HAS"));
		objeto.setProyecto(rs.getString("PROYECTO"));
	
		return objeto;
	}

}
