package aca.mentores.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class MentPerfilMapper implements RowMapper<MentPerfil>{

	public MentPerfil mapRow(ResultSet rs, int arg1) throws SQLException {
		MentPerfil objeto = new MentPerfil();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setCargaId(rs.getString("CARGA_ID"));
		objeto.setMentorId(rs.getString("MENTOR_ID"));
		objeto.setFolio(rs.getString("FOLIO"));
		objeto.setFecha(rs.getString("FECHA"));
		objeto.setRelAsdb(rs.getString("REL_ASDB"));
		objeto.setRelAsd(rs.getString("REL_ASD"));
		objeto.setRelNasd(rs.getString("REL_NASD"));
		objeto.setResInt(rs.getString("RES_INT"));
		objeto.setResExt(rs.getString("RES_EXT"));
		objeto.setResTipo(rs.getString("RES_TIPO"));
		objeto.setComComedor(rs.getString("COM_COMEDOR"));
		objeto.setComSnack(rs.getString("COM_SNACK"));
		objeto.setComAsistencia(rs.getString("COM_ASISTENCIA"));
		objeto.setComCasa(rs.getString("COM_CASA"));
		objeto.setComOtro(rs.getString("COM_OTRO"));
		objeto.setCom3(rs.getString("COM_3"));
		objeto.setCom2(rs.getString("COM_2"));
		objeto.setCom1(rs.getString("COM_1"));
		objeto.setDevDiaria(rs.getString("DEV_DIARIA"));
		objeto.setDevSemana(rs.getString("DEV_SEMANA"));
		objeto.setDevMenos(rs.getString("DEV_MENOS"));
		objeto.setIglUm(rs.getString("IGL_UM"));
		objeto.setIglOtra(rs.getString("IGL_OTRA"));
		objeto.setIglNinguna(rs.getString("IGL_NINGUNA"));
		objeto.setProFamiliar(rs.getString("PRO_FAMILIAR"));
		objeto.setProFinanciero(rs.getString("PRO_FINANCIERO"));
		objeto.setProMateria(rs.getString("PRO_MATERIA"));
		objeto.setProPendiente(rs.getString("PRO_PENDIENTE"));
		objeto.setLidEspiritual(rs.getString("LID_ESPIRITUAL"));
		objeto.setLidPosicion(rs.getString("LID_POSICION"));
		objeto.setTraNada(rs.getString("TRA_NADA"));
		objeto.setTraMedio(rs.getString("TRA_MEDIO"));
		objeto.setTraCompleto(rs.getString("TRA_COMPLETO"));
		objeto.setEstDesafios(rs.getString("EST_DESAFIOS"));
		objeto.setEstRelaciones(rs.getString("EST_RELACIONES"));
		objeto.setEstProgreso(rs.getString("EST_PROGRESO"));
		objeto.setEstRegresa(rs.getString("EST_REGRESA"));
		objeto.setRiesgoPersonal(rs.getString("RIESGO_PERSONAL"));
		objeto.setRiesgoIntegracion(rs.getString("RIESGO_INTEGRACION"));
		objeto.setRiesgoRelaciones(rs.getString("RIESGO_RELACIONES"));
		objeto.setRiesgoAcademico(rs.getString("RIESGO_ACADEMICO"));
		objeto.setRiesgoFinanciero(rs.getString("RIESGO_FINANCIERO"));
		objeto.setUsuario(rs.getString("USUARIO"));
				
		return objeto;
	}

}
