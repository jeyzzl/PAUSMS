package aca.plan.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class MapaCursoMapper implements RowMapper<MapaCurso>{

	public MapaCurso mapRow(ResultSet rs, int arg1) throws SQLException {
		MapaCurso objeto = new MapaCurso();
		
		objeto.setPlanId(rs.getString("PLAN_ID"));
		objeto.setCursoId(rs.getString("CURSO_ID"));
		objeto.setNombreCurso(rs.getString("NOMBRE_CURSO"));
		objeto.setCiclo(rs.getString("CICLO"));
		objeto.setCreditos(rs.getString("CREDITOS"));
		objeto.setHt(rs.getString("HT"));
		objeto.setHp(rs.getString("HP"));
		objeto.setHi(rs.getString("HI"));
		objeto.setfCreada(rs.getString("F_CREADA"));
		objeto.setNotaAprobatoria(rs.getString("NOTA_APROBATORIA"));
		objeto.setEstado(rs.getString("ESTADO"));
		objeto.setTipoCursoId(rs.getString("TIPOCURSO_ID"));	
		objeto.setUnid(rs.getString("UNID"));
		objeto.setOnLine(rs.getString("ON_LINE"));
		objeto.setCursoNuevo(rs.getString("CURSO_NUEVO"));
		objeto.setCursoClave(rs.getString("CURSO_CLAVE"));
		objeto.setObligatorio(rs.getString("OBLIGATORIO"));
		objeto.setCompleto(rs.getString("COMPLETO"));
		objeto.setHh(rs.getString("HH"));
		objeto.setHfd(rs.getString("HFD"));
		objeto.setHss(rs.getString("HSS"));
		objeto.setHas(rs.getString("HAS"));
		objeto.setAreaId(rs.getString("AREA_ID"));
		objeto.setLaboratorio(rs.getString("LABORATORIO"));
		objeto.setHorario(rs.getString("HORARIO"));
		objeto.setSalon(rs.getString("SALON"));
		objeto.setOrden(rs.getString("ORDEN"));
		
		return objeto;
	}

}
