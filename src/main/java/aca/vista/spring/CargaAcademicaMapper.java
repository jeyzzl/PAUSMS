package aca.vista.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CargaAcademicaMapper implements RowMapper<CargaAcademica>{

	public CargaAcademica mapRow(ResultSet rs, int arg1) throws SQLException {
		CargaAcademica objeto = new CargaAcademica();

		objeto.setCursoCargaId(rs.getString("CURSO_CARGA_ID"));
		objeto.setCargaId(rs.getString("CARGA_ID"));
		objeto.setBloqueId(rs.getString("BLOQUE_ID"));
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setNombre(rs.getString("NOMBRE"));
		objeto.setCarreraId(rs.getString("CARRERA_ID"));
		objeto.setGrupo(rs.getString("GRUPO"));
		objeto.setModalidadId(rs.getString("MODALIDAD_ID"));
		objeto.setEstado(rs.getString("ESTADO"));
		objeto.setfInicio(rs.getString("F_INICIO"));
		objeto.setfFinal(rs.getString("F_FINAL"));
		objeto.setfEntrega(rs.getString("F_ENTREGA"));
		objeto.setOrigen(rs.getString("ORIGEN"));
		objeto.setPlanId(rs.getString("PLAN_ID"));
		objeto.setCursoId(rs.getString("CURSO_ID"));
		objeto.setNombreCurso(rs.getString("NOMBRE_CURSO"));
		objeto.setCiclo(rs.getString("CICLO"));
		objeto.setCreditos(rs.getString("CREDITOS"));
		objeto.setHt(rs.getString("HT"));
		objeto.setHp(rs.getString("HP"));
		objeto.setHi(rs.getString("HI"));
		objeto.setNotaAprobatoria(rs.getString("NOTA_APROBATORIA"));
		objeto.setValeucas(rs.getString("VALEUCAS"));
		objeto.setNumAlum(rs.getString("NUM_ALUM"));
		objeto.setSemanas(rs.getString("SEMANAS"));
		objeto.setOptativa(rs.getString("OPTATIVA"));
		objeto.setHorario(rs.getString("HORARIO"));
		objeto.setGrupoHorario(rs.getString("GRUPO_HORARIO"));
		objeto.setHh(rs.getString("HH"));
		objeto.setSalon(rs.getString("SALON"));
		objeto.setModo(rs.getString("MODO"));
		
		return objeto;
	}

}
