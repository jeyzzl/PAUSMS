package aca.encuesta.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class EncPeriodoResMapper implements RowMapper<EncPeriodoRes> {
	public EncPeriodoRes mapRow(ResultSet rs, int arg1) throws SQLException {
		
		EncPeriodoRes objeto = new EncPeriodoRes();
		
		objeto.setCodigoPersonal(rs.getString("CODIGO_PERSONAL"));
		objeto.setPeriodoId(rs.getString("PERIODO_ID"));
		objeto.setFinAlumno(rs.getString("FIN_ALUMNO"));
		objeto.setFinPor(rs.getString("FIN_POR"));
		objeto.setFinColpor(rs.getString("FIN_COLPOR"));
		objeto.setFinTrabajo(rs.getString("FIN_TRABAJO"));
		objeto.setFinOtro(rs.getString("FIN_OTRO"));
		objeto.setRegresar(rs.getString("REGRESAR"));
		objeto.setPracticas(rs.getString("PRACTICAS"));
		objeto.setInterno(rs.getString("INTERNO"));
		objeto.setExterno(rs.getString("EXTERNO"));
		objeto.setObsSaldo(rs.getString("OBS_SALDO"));
		objeto.setObsFin(rs.getString("OBS_FIN"));
		objeto.setObsMat(rs.getString("OBS_MAT"));
		objeto.setObsDoc(rs.getString("OBS_DOC"));
		objeto.setObsSalud(rs.getString("OBS_SALUD"));
		objeto.setObsAdaptacion(rs.getString("OBS_ADAPTACION"));
		objeto.setObsFamiliar(rs.getString("OBS_FAMILIAR"));
		objeto.setPlanEstudiar(rs.getString("PLAN_ESTUDIAR"));
		objeto.setPlanOtroEst(rs.getString("PLAN_OTRO_EST"));
		objeto.setPlanTrabajo(rs.getString("PLAN_TRABAJO"));
		objeto.setPlanColportar(rs.getString("PLAN_COLPORTAR"));
		objeto.setPlanOtroTrabajo(rs.getString("PLAN_OTRO_TRABAJO"));
		objeto.setPlanDescansar(rs.getString("PLAN_DESCANSAR"));
		objeto.setPlanNinguno(rs.getString("PLAN_NINGUNO"));
		objeto.setOrientacion(rs.getString("ORIENTACION"));
		objeto.setPlanId(rs.getString("PLAN_ID"));
		
		return objeto;
	}
}
