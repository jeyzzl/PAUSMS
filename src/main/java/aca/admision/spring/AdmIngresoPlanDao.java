package aca.admision.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AdmIngresoPlanDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	
	public boolean insertReg(AdmIngresoPlan admIngresoPlan) {
		boolean ok = false;
			
		try{
			String comando = "INSERT INTO SALOMON.ADM_INGRESO_PLAN"+ 
				"(PERIODO_ID, MODALIDAD_ID, PLAN_ID) "+
				"VALUES(TO_NUMBER(?,'999'),TO_NUMBER(?,'99'),? )";
				
			Object[] parametros = new Object[] {
					admIngresoPlan.getPeriodoId(),admIngresoPlan.getModalidadId(), admIngresoPlan.getPlanId() };
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmIngresoPlanDao|insertReg|:"+ex);
		}

		return ok;
	}
	
	
	public boolean deleteReg(String periodoId, String modalidadId, String planId){
		boolean ok = false;

		try{
			String comando = "DELETE FROM SALOMON.ADM_INGRESO_PLAN "+ 
					"WHERE PERIODO_ID = TO_NUMBER(?,'999') " +
					"AND MODALIDAD_ID = TO_NUMBER(?,'99') " +
					"AND PLAN_ID = ? ";
			
			Object[] parametros = new Object[] {periodoId,modalidadId,planId};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmIngresoPlanDao|deleteReg|:"+ex);
		}
		
		return ok;
	}
	
	
	
	public AdmIngresoPlan mapeaRegId(String periodoId, String modalidadId, String planId) {
			AdmIngresoPlan objeto = new AdmIngresoPlan();
		
		try {
			String comando = "SELECT PERIODO_ID, MODALIDAD_ID, PLAN_ID  "+
					"FROM SALOMON.ADM_INGRESO_PLAN "+ 
					"WHERE PERIODO_ID  = TO_NUMBER(?,'999') " +
					"AND MODALIDAD_ID = TO_NUMBER(?,'99')" +
					"AND PLAN_ID = ? ";
			
			Object[] parametros = new Object[] {periodoId,modalidadId,planId
					};
			objeto = enocJdbc.queryForObject(comando, new AdmIngresoPlanMapper(), parametros);
			
		} catch (Exception ex) {
			System.out.println("Error - adm.alumno.AdmIngresoPlanDao|mapeaRegId|:"+ex);
		}
		
		return objeto;
	}
	
	public boolean existeReg(String periodoId, String modalidadId, String planId) {
		boolean ok 	= false;

		try{
			String comando = "SELECT COUNT(*) FROM SALOMON.ADM_INGRESO_PLAN "+ 
					"WHERE PERIODO_ID = TO_NUMBER(?,'999') " +
					"AND MODALIDAD_ID = TO_NUMBER(?,'99') " +
					"AND PLAN_ID = ? ";			
			Object[] parametros = new Object[] { periodoId,modalidadId,planId };
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmIngresoPlanDao|mapeaRegId|:"+ex);
		}
		
		return ok;
	}
	
	public HashMap<String,String> mapaCursosPorPeriodo(){
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT PERIODO_ID AS LLAVE, COUNT(PLAN_ID) AS VALOR FROM SALOMON.ADM_INGRESO_PLAN GROUP BY PERIODO_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa map: lista){
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmIngresoPlanDao|mapaCursosPorPeriodo|:"+ex);
		}
		return mapa;
	}
	
	public String maximoReg(String periodoId,String modalidadId) {
		String maximo		= "1";
			
		try{
			String comando = "SELECT COALESCE(MAX(PERIODO_ID)+1,1) AS MAXIMO FROM SALOMON.ADM_INGRESO_PLAN "+ 
						     "WHERE PERIODO_ID = TO_NUMBER(?,'999')"
						    +"MODALIDAD_ID = TO_NUMBER(?,'99')"; 
				
			Object[] parametros = new Object[] {periodoId};
			maximo = enocJdbc.queryForObject(comando,String.class, parametros);
				
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmIngresoPlanDao|maximoReg|:"+ex);
		}
			
		return maximo;
	}
	
	
	public List<AdmIngresoPlan> lisTodos(String periodoId,String modalidadId, String orden) {
		List<AdmIngresoPlan> lista	= new ArrayList<AdmIngresoPlan>();
		
		try{
			String comando = "SELECT PERIODO_ID, MODALIDAD_ID, PLAN_ID " +
					" FROM SALOMON.ADM_INGRESO_PLAN " + 
					" WHERE PERIODO_ID = '"+periodoId+"' " +
					" AND MODALIDAD_ID = '"+modalidadId+"' " +
					orden;
			
			lista = enocJdbc.query(comando, new AdmIngresoPlanMapper());
			
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmIngresoPlanDao|getLista|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String,String> mapaPlanes( String periodoId) {	
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT MODALIDAD_ID AS LLAVE, COUNT(PLAN_ID) AS VALOR FROM SALOMON.ADM_INGRESO_PLAN WHERE PERIODO_ID = ? GROUP BY MODALIDAD_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), periodoId);
			for(aca.Mapa obj : lista){
				mapa.put(obj.getLlave(), obj.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AdmIngresoPlanDaot|mapaPlanes|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String,String> mapaPlanes( String periodoId, String modalidadId) {	
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT PLAN_ID AS LLAVE, PLAN_ID AS VALOR FROM SALOMON.ADM_INGRESO_PLAN WHERE PERIODO_ID = ? AND MODALIDAD_ID = TO_NUMBER(?,'99')";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), periodoId, modalidadId);
			for(aca.Mapa obj : lista){
				mapa.put(obj.getLlave(), obj.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AdmIngresoPlanDaot|mapaPlanes|:"+ex);
		}		
		return mapa;
	}
}
