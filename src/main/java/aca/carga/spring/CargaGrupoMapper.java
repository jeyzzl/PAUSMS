package aca.carga.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CargaGrupoMapper implements RowMapper<CargaGrupo>{

	public CargaGrupo mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CargaGrupo objeto = new CargaGrupo();
		
		objeto.setCursoCargaId(rs.getString("CURSO_CARGA_ID"));
		objeto.setCargaId(rs.getString("CARGA_ID"));
		objeto.setBloqueId(rs.getString("BLOQUE_ID"));
		objeto.setCarreraId(rs.getString("CARRERA_ID"));
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setGrupo(rs.getString("GRUPO"));
		objeto.setModalidadId(rs.getString("MODALIDAD_ID"));
		objeto.setfInicio(rs.getString("F_INICIO"));
		objeto.setfFinal(rs.getString("F_FINAL"));
		objeto.setfEntrega(rs.getString("F_ENTREGA"));
		objeto.setRestriccion(rs.getString("RESTRICCION"));
		objeto.setEstado(rs.getString("ESTADO"));
		objeto.setHorario(rs.getString("HORARIO"));
		objeto.setfEvaluacion(rs.getString("F_EVALUACION"));
		objeto.setValeucas(rs.getString("VALEUCAS"));
		objeto.setNumAlum(rs.getString("NUM_ALUM"));
		objeto.setSemanas(rs.getString("SEMANAS"));
		objeto.setOptativa(rs.getString("OPTATIVA"));
		objeto.setComentario(rs.getString("COMENTARIO"));
		objeto.setCodigoOtro(rs.getString("CODIGO_OTRO"));
		objeto.setPrecio(rs.getString("PRECIO"));
		objeto.setModo(rs.getString("MODO"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setUsuario(rs.getString("USUARIO"));
		
		return objeto;
	}

}
