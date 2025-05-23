/**
 * 
 */
package aca.cert.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * @author Jose Torres
 *
 */
@Component
public class CertPlanDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( CertPlan plan) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.CERT_PLAN"+ 
				"(PLAN_ID, FACULTAD, CARRERA, NUM_CURSOS," +
				" SEMANAS, T_INICIAL, T_FINAL, NOTA," +
				" PIE, CLAVE, FST, FSP," +
				" COMPONENTE, CURSO, RVOE, FECHARETRO, TITULO1, TITULO2, TITULO3, CREDITOS)"+
				" VALUES(?, ?, ?, TO_NUMBER(?, '999')," +
				" TO_NUMBER(?, '99'), ?, ?, ?," +
				" ?, ?, ?, ?," +
				" TO_NUMBER(?, '99'), ?, ?," +
				" TO_DATE(?,'DD/MM/YYYY'), ?, ?, ?, ?)";
			Object[] parametros = new Object[] { plan.getPlanId(), plan.getFacultad(), plan.getCarrera(), plan.getNumCursos(), plan.getSemanas(), plan.gettInicial(),plan.gettFinal(),
					plan.getNota(), plan.getPie(), plan.getClave(), plan.getFst(), plan.getFsp(), plan.getComponente(), plan.getCurso(), plan.getRvoe(), plan.getFechaRetro(),
					plan.getTitulo1(), plan.getTitulo2(), plan.getTitulo3(), plan.getCreditos()};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.CertPlanDao|insertReg|:"+ex);

		}
		
		return ok;
	}	
	
	public boolean updateReg( CertPlan plan) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.CERT_PLAN"+ 
				" SET FACULTAD = ?," +
				" CARRERA = ?," +
				" NUM_CURSOS = TO_NUMBER(?, '999')," +
				" SEMANAS = TO_NUMBER(?, '99')," +
				" T_INICIAL = ?," +
				" T_FINAL = ?," +
				" NOTA = ?," +
				" PIE = ?," +
				" CLAVE = ?," +
				" FST = ?," +
				" FSP = ?," +
				" COMPONENTE = TO_NUMBER(?, '99')," +
				" CURSO = ?," +
				" RVOE = ?," +
				" FECHARETRO = TO_DATE(?,'DD/MM/YYYY'), TITULO1=?, TITULO2=?, TITULO3=?, CREDITOS=? "+
				" WHERE PLAN_ID = ?";
			Object[] parametros = new Object[] { plan.getFacultad(), plan.getCarrera(), plan.getNumCursos(), plan.getSemanas(), plan.gettInicial(), plan.gettFinal(),plan.getNota(),
					plan.getPie(), plan.getClave(), plan.getFst(), plan.getFsp(), plan.getComponente(), plan.getCurso(), plan.getRvoe(), plan.getFechaRetro(), plan.getTitulo1(),
					plan.getTitulo2(), plan.getTitulo3(), plan.getCreditos(), plan.getPlanId()};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.CertPlanDao|updateReg|:"+ex);		

		}
		
		return ok;
	}	
	
	public boolean deleteReg( String planId) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CERT_PLAN"+ 
				" WHERE PLAN_ID = ?";
			Object[] parametros = new Object[] {planId};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.CertPlanDao|deleteReg|:"+ex);			

		}
		return ok;
	}
	
	public CertPlan mapeaRegId( String planId) {
		CertPlan plan = new CertPlan();		
		try{			
			String comando = "SELECT COUNT(*) FROM ENOC.CERT_PLAN WHERE PLAN_ID = ?";
			Object[] parametros = new Object[] {planId};		
			if (enocJdbc.queryForObject(comando, Integer.class,parametros) >= 1) {			
				comando = "SELECT PLAN_ID, FACULTAD, CARRERA, NUM_CURSOS," +
					" SEMANAS, T_INICIAL, T_FINAL, NOTA," +
					" PIE, CLAVE, FST, FSP," +
					" COMPONENTE, CURSO, RVOE, TO_CHAR(FECHARETRO,'DD/MM/YYYY') AS FECHARETRO, TITULO1, TITULO2, TITULO3, CREDITOS" +
					" FROM ENOC.CERT_PLAN" + 
					" WHERE PLAN_ID = ?";						
				plan = enocJdbc.queryForObject(comando, new CertPlanMapper(),parametros);
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.cert.CertPlanDao|mapeaRegId|:"+ex);
			
		}
		
		return plan;
	}
	
	public boolean existeReg( String planId) {
		boolean 			ok 	= false;
		
		try{
			String comando = "SELECT COUNT(FACULTAD) FROM ENOC.CERT_PLAN"+ 
				" WHERE PLAN_ID = ?";
			Object[] parametros = new Object[] {planId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.CertPlanDao|existeReg|:"+ex);

		}
		
		return ok;
	}
	
	
	public ArrayList<CertPlan> getListAll( String facultad, String orden ) {
		
		List<CertPlan> lista	= new ArrayList<CertPlan>();
		try{
			String comando = "SELECT PLAN_ID, FACULTAD, CARRERA, NUM_CURSOS, SEMANAS, T_INICIAL, T_FINAL, NOTA,"
					+ " PIE, CLAVE, FST, FSP, COMPONENTE, CURSO, RVOE, TO_CHAR(FECHARETRO,'DD/MM/YYYY') AS FECHARETRO, TITULO1, TITULO2, TITULO3, CREDITOS"
					+ " FROM ENOC.CERT_PLAN"
					+ " WHERE FACULTAD = ? "+ orden;	 
			lista = enocJdbc.query(comando, new CertPlanMapper(), facultad);			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.CertPlanDao|getListAll|:"+ex);

		}
		
		return (ArrayList<CertPlan>) lista;
	}
	
	public ArrayList<CertPlan> listFacultad( String facultadId, String orden ) {
		
		List<CertPlan> lista	= new ArrayList<CertPlan>();
		try{
			String comando = " SELECT PLAN_ID, FACULTAD, CARRERA, NUM_CURSOS,SEMANAS, T_INICIAL, T_FINAL, NOTA,PIE, CLAVE, FST, FSP,"
					+ " COMPONENTE, CURSO, RVOE, TO_CHAR(FECHARETRO,'DD/MM/YYYY') AS FECHARETRO, TITULO1, TITULO2, TITULO3, CREDITOS"
					+ " FROM ENOC.CERT_PLAN"
					+ " WHERE ENOC.FACULTAD(ENOC.CARRERA(PLAN_ID)) = ? "+ orden;	 
			lista = enocJdbc.query(comando, new CertPlanMapper(), facultadId);			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.CertPlanUtil|listFacultad|:"+ex);
		}		
		return (ArrayList<CertPlan>) lista;
	}
	
	public String getRvoePlan( String planId) {
		String rvoe				= "RVOE";		
		try{
			String comando = "SELECT RVOE FROM ENOC.CERT_PLAN WHERE PLAN_ID = ?";
			Object[] parametros = new Object[] {planId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				rvoe = enocJdbc.queryForObject(comando, String.class, parametros);
			}		
			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.CertPlanDao|existeReg|:"+ex);

		}		
		return rvoe;
	}
	
	public ArrayList<String> getFrases(String parrafo) {
		ArrayList<String> frases = new ArrayList<String>();		
		try{
			String tmp = "";
			int contA = 0;
			int contB = 0;
			
			for(int i = 0; i < parrafo.length(); i++){//Recorre la parrafo completa
				if (i == parrafo.length()-1){
				   tmp += String.valueOf(parrafo.charAt(i));//concatena caracteres(uno por uno)
				   frases.add(tmp);
				}
				tmp += String.valueOf(parrafo.charAt(i));
				
				if(String.valueOf(parrafo.charAt(i)).equals("$")){//verifica cuando se llegue a un $
					frases.add(tmp.substring(0,tmp.length() - 1));//guarda el texto anterior al $ en la lista
					tmp = String.valueOf(parrafo.charAt(i)); //guarda solo el ultimo caracter leido
					
					for(int j = i + 1; j < parrafo.length(); j++){//recorre el texto que hay entre $
						tmp += String.valueOf(parrafo.charAt(j));//
						if(String.valueOf(parrafo.charAt(j)).equals("$")){
							frases.add(tmp);//agrega a la lista el texto entre $
							tmp = "";
							i = j;
							break;
						}
					}
				}
				
				if(String.valueOf(parrafo.charAt(i)).equals("<")){//verifica cuando llegue a un <
					if(contA == 0){
						frases.add(tmp.substring(0,tmp.length() - 1));//guarda el texto anterior al < en la lista
						tmp = String.valueOf(parrafo.charAt(i)); //guarda solo el ultimo caracter leido
					}
					contA++;
				}
				if(String.valueOf(parrafo.charAt(i)).equals(">")){//verifica cuando llegue a un <
					contB++;
					if(contA == 2 && contB == 2){
						frases.add(tmp);
						contA = 0;
						contB = 0;
						tmp = "";
					}
				}
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.CertPlanDao|getFrases|:"+ex);
		}
		
		return frases;
	}
	
	public HashMap<String,String> mapaPlanes() {
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>(); 
		HashMap<String,String> mapa = new HashMap<String,String>();		
		try{
			String comando = "SELECT PLAN_ID AS LLAVE, COUNT(CURSO_ID) AS VALOR FROM CERT_CURSO GROUP BY PLAN_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa objeto : lista) {											
				mapa.put(objeto.getLlave(), objeto.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.spring.CertPlanDao|mapaPlanes|:"+ex);			
		}
		return mapa;
	}
	
}