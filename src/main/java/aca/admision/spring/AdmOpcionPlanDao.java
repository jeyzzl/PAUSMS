package aca.admision.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AdmOpcionPlanDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(AdmOpcionPlan objeto) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO SALOMON.ADM_OPCION_PLAN (OPCION_ID, PLAN_ID) VALUES( TO_NUMBER(?,'99'), ?)";
			
			Object[] parametros = new Object[] {
				objeto.getOpcionId(),objeto.getPlanId()
 		 	};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmOpcionPlanDao|insertReg|:"+ex);
		}

		return ok;
	}
	
	public boolean deleteReg(String opcionId, String planId ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM SALOMON.ADM_OPCION_PLAN WHERE OPCION_ID = TO_NUMBER(?,'99') AND PLAN_ID = ?";
			Object[] parametros = new Object[] {opcionId,planId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmOpcionPlanDao|deleteReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean existeReg( String opcionId, String planId) {		
		boolean 		ok 	= false;	
		try{
			String comando = "SELECT COUNT(*) FROM SALOMON.ADM_OPCION_PLAN WHERE OPCION_ID = TO_NUMBER(?,'99') AND PLAN_ID = ?";
			Object[] parametros = new Object[] {opcionId, planId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1 ){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmOpcionPlanDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public List<AdmOpcionPlan> listaPorOpcionId(String opcionId, String orden) {
		List<AdmOpcionPlan> lista	= new ArrayList<AdmOpcionPlan>();		
		try{
			String comando = "SELECT OPCION_ID, PLAN_ID FROM SALOMON.ADM_OPCION_PLAN WHERE OPCION_ID = ?"+ orden;			
			lista = enocJdbc.query(comando, new AdmOpcionPlanMapper(), opcionId);			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmOpcionPlanDao|listaPorOpcionId|:"+ex);
		}		
		return lista;
	}
	
	public HashMap<String, String> mapaTotalPlanes() {		
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT OPCION_ID AS LLAVE, COUNT(PLAN_ID) AS VALOR FROM SALOMON.ADM_OPCION_PLAN WHERE PLAN_ID IN (SELECT PLAN_ID FROM ENOC.MAPA_PLAN WHERE ESTADO IN ('A')) GROUP BY OPCION_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper());	
			for (aca.Mapa map : lista) {
				mapa.put(map.getLlave() , map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmOpcionPlanDao|mapaTotalPlanes:"+ex);
		}
		
		return mapa;
	}
	
}
