package aca.encuesta.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Component
public class EncPeriodoResDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(EncPeriodoRes objeto) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO "
					+ " ENOC.ENC_PERIODO_RES(CODIGO_PERSONAL,PERIODO_ID,FIN_ALUMNO,FIN_POR,FIN_COLPOR,FIN_TRABAJO,FIN_OTRO,REGRESAR,PRACTICAS,INTERNO,EXTERNO,OBS_SALDO,OBS_FIN,"
					+ "OBS_MAT,OBS_DOC,OBS_SALUD,OBS_ADAPTACION,OBS_FAMILIAR,PLAN_ESTUDIAR,PLAN_OTRO_EST,PLAN_TRABAJO,PLAN_COLPORTAR,PLAN_OTRO_TRABAJO,PLAN_DESCANSAR,PLAN_NINGUNO,"
					+ "ORIENTACION,PLAN_ID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			
			Object[] parametros = new Object[] {
				objeto.getCodigoPersonal(),objeto.getPeriodoId(),objeto.getFinAlumno(),objeto.getFinPor(),objeto.getFinColpor(),objeto.getFinTrabajo(),objeto.getFinOtro(),
				objeto.getRegresar(),objeto.getPracticas(),objeto.getInterno(),objeto.getExterno(),objeto.getObsSaldo(),objeto.getObsFin(),objeto.getObsMat(),objeto.getObsDoc(),
				objeto.getObsSalud(),objeto.getObsAdaptacion(),objeto.getObsFamiliar(),objeto.getPlanEstudiar(),objeto.getPlanOtroEst(),objeto.getPlanTrabajo(),objeto.getPlanColportar(),
				objeto.getPlanOtroTrabajo(),objeto.getPlanDescansar(),objeto.getPlanNinguno(),objeto.getOrientacion(),objeto.getPlanId()
			};
			
			if (enocJdbc.update(comando,parametros)==1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.encuesta.spring.EncPeriodoResDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	

	public EncPeriodoRes mapeaRegId(String codigoPersonal, String periodoId) {
		EncPeriodoRes objeto = new EncPeriodoRes();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL,PERIODO_ID,FIN_ALUMNO,FIN_POR,FIN_COLPOR,FIN_TRABAJO,FIN_OTRO,REGRESAR,PRACTICAS,INTERNO,EXTERNO,OBS_SALDO,OBS_FIN,OBS_MAT,"
					+ " OBS_DOC,OBS_SALUD,OBS_ADAPTACION,OBS_FAMILIAR,PLAN_ESTUDIAR,PLAN_OTRO_EST,PLAN_TRABAJO,PLAN_COLPORTAR,PLAN_OTRO_TRABAJO,PLAN_DESCANSAR,PLAN_NINGUNO,ORIENTACION,PLAN_ID"
					+ " FROM ENOC.ENC_PERIODO_RES WHERE CODIGO_PERSONAL = ? AND PERIODO_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal,periodoId};
			objeto = enocJdbc.queryForObject(comando, new EncPeriodoResMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.encuesta.spring.EncPeriodoResDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		
		return objeto;
	}
	
	public boolean existeReg(String codigoPersonal, String periodoId) {
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ENC_PERIODO_RES WHERE CODIGO_PERSONAL = ? AND PERIODO_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal,periodoId};
			
			if (enocJdbc.queryForObject(comando, Integer.class, parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.encuesta.spring.EncPeriodoResDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public List<EncPeriodoRes> getListAll( String orden ) {
		List<EncPeriodoRes> lista = new ArrayList<EncPeriodoRes>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL,PERIODO_ID,FIN_ALUMNO,FIN_POR,FIN_COLPOR,FIN_TRABAJO,FIN_OTRO,REGRESAR,PRACTICAS,INTERNO,EXTERNO,OBS_SALDO,OBS_FIN,OBS_MAT,"
					+ " OBS_DOC,OBS_SALUD,OBS_ADAPTACION,OBS_FAMILIAR,PLAN_ESTUDIAR,PLAN_OTRO_EST,PLAN_TRABAJO,PLAN_COLPORTAR,PLAN_OTRO_TRABAJO,PLAN_DESCANSAR,PLAN_NINGUNO,ORIENTACION,PLAN_ID"
					+ " FROM ENOC.ENC_PERIODO_RES "+orden;
			
			lista = enocJdbc.query(comando, new EncPeriodoResMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.encuesta.spring.EncPeriodoResDao|getListAll|:"+ex);
		}
		
		return lista;
	}
	
}