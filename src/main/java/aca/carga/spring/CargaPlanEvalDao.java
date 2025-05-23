package aca.carga.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CargaPlanEvalDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( CargaPlanEval plan ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO" +
				" ENOC.CARGA_PLAN_EVAL(CURSO_CARGA_ID, EVALUACION_ID, EVALUACION_NOMBRE, FECHA, VALOR, ACTIVIDAD_ID)" +
				" VALUES( ?, TO_NUMBER(?,'99'), ?, TO_DATE(?,'DD/MM/YYYY'), TO_NUMBER(?,'99.99'),?)";
			
				Object[] parametros = new Object[] {plan.getCursoCargaId(),plan.getEvaluacionId(),plan.getEvaluacionNombre(),plan.getFecha(),plan.getValor(),plan.getActividadId()};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaPlanEvalDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg( CargaPlanEval plan ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.CARGA_PLAN_EVAL "+ 
				" SET EVALUACION_NOMBRE = ?, " +
				" FECHA = TO_DATE(?,'DD/MM/YYYY'), " +
				" VALOR = TO_NUMBER(?,'99.99')," +
				" ACTIVIDAD_ID = ?" +
				" WHERE CURSO_CARGA_ID = ? " +
				" AND EVALUACION_ID = TO_NUMBER(?,'99')";		
			
			Object[] parametros = new Object[] {plan.getEvaluacionNombre(),plan.getFecha(),plan.getValor(),plan.getActividadId(),plan.getCursoCargaId(),plan.getEvaluacionId()}; 			 			
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaPlanEvalDao|updateReg|:"+ex);		 
		}
		return ok;
	}	
	
	public boolean updateActividad( CargaPlanEval plan ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.CARGA_PLAN_EVAL"+ 
				" SET EVALUACION_NOMBRE = ?,"+
				" FECHA = TO_DATE(?,'DD/MM/YYYY'),"+
				" VALOR = TO_NUMBER(?,'99.99')"+
				" WHERE CURSO_CARGA_ID = ?" +
				" AND ACTIVIDAD_ID = TO_NUMBER(?,'99')";
						
			Object[] parametros = new Object[] {plan.getEvaluacionNombre(),plan.getFecha(),plan.getValor(),plan.getCursoCargaId(),plan.getActividadId()}; 			 			
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaPlanEvalDao|updateActividad|:"+ex); 
		}
		return ok;
	}
	
	public boolean deleteReg( String cursoCargaId, String evaluacionId) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CARGA_PLAN_EVAL "+ 
				" WHERE CURSO_CARGA_ID = ?" +
				" AND EVALUACION_ID = TO_NUMBER(?,'99') ";
			
			Object[] parametros = new Object[] {cursoCargaId,evaluacionId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaPlanEvalDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public boolean deleteEvaluaciones( String cursoCargaId ) {		
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CARGA_PLAN_EVAL "+ 
				" WHERE CURSO_CARGA_ID = ?" +
				" AND ACTIVIDAD_ID NOT IN ('00','77') ";
			Object[] parametros = new Object[] {cursoCargaId};
			if (enocJdbc.update(comando,parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaPlanEvalDao|deleteEvaluaciones|:"+ex);
		}
		return ok;
	}
	
	public CargaPlanEval mapeaRegId(  String cursoCargaId, String evaluacionId) {
		CargaPlanEval plan = new CargaPlanEval();
		
		try{
			String comando = "SELECT CURSO_CARGA_ID, EVALUACION_ID," +
					" EVALUACION_NOMBRE, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, VALOR, ACTIVIDAD_ID " +
					" FROM ENOC.CARGA_PLAN_EVAL " + 
					" WHERE CURSO_CARGA_ID = ? AND EVALUACION_ID = ?";
			Object[] parametros = new Object[] {cursoCargaId,evaluacionId};
			plan = enocJdbc.queryForObject(comando, new CargaPlanEvalMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaPlanEvalDao|mapeaRegId|:"+ex);
		}
		return plan;
	}
	
	public boolean existeReg( String cursoCargaId, String evaluacionId) {
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA_PLAN_EVAL " + 
					" WHERE CURSO_CARGA_ID = ?  AND EVALUACION_ID = TO_NUMBER(?,'99')";

			Object[] parametros = new Object[] {cursoCargaId,evaluacionId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}				
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaPlanEvalDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public boolean existeActividad( String cursoCargaId, String actividadId) {
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA_PLAN_EVAL " + 
					" WHERE CURSO_CARGA_ID = ?  AND ACTIVIDAD_ID = ?";
			
			Object[] parametros = new Object[] {cursoCargaId,actividadId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}				
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaPlanEvalDao|existeActividad|:"+ex);
		}
		return ok;
	}
	
	public String maximoReg( String cursoCargaId) {
		String maximo			= "1";
		
		try{
			String comando = "SELECT MAX(EVALUACION_ID)+1 AS MAXIMO "+
				" FROM ENOC.CARGA_PLAN_EVAL WHERE CURSO_CARGA_ID = ? "; 
			
			Object[] parametros = new Object[] {cursoCargaId};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
 				maximo = enocJdbc.queryForObject(comando, String.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaPlanEvalDao|maximoReg|:"+ex);
		}		
		return maximo;
	}
	
	public double getSumEvaluaciones( String cursoCargaId) {
		double suma				= 0;
		
		try{
			String comando = "SELECT COALESCE(SUM(VALOR),0) AS SUMA FROM ENOC.CARGA_PLAN_EVAL" + 
					" WHERE CURSO_CARGA_ID = ?";
			
			Object[] parametros = new Object[] {cursoCargaId};
			if (enocJdbc.queryForObject(comando, Double.class,parametros) >= 1){		        
				suma = enocJdbc.queryForObject(comando, Double.class,parametros);
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaPlanEvalDao|getSumEvaluaciones|:"+ex);
		}
		return suma;
	}
	
	public int getNumEval( String cursoCargaId) {
		int totalEval		    = 0;
		
		try{
			String comando = "SELECT COUNT(EVALUACION_ID) AS EVALUACION FROM ENOC.CARGA_PLAN_EVAL " + 
					" WHERE CURSO_CARGA_ID = ?";
		
			if (enocJdbc.queryForObject(comando, Integer.class) >= 1) {
				totalEval = enocJdbc.queryForObject(comando, Integer.class);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaPlanEvalDao|getNumEval|:"+ex);
		}
		return totalEval;
	}	
	
	public List<CargaPlanEval> getListAll( String orden ) {
		List<CargaPlanEval> lista = new ArrayList<CargaPlanEval>();
		String comando	= "";
		
		try{
			comando = "SELECT CURSO_CARGA_ID, EVALUACION_ID, EVALUACION_NOMBRE, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, VALOR, ACTIVIDAD_ID " +
					" FROM ENOC.CARGA_PLAN_EVAL "+ orden; 
			
			lista = enocJdbc.query(comando, new CargaPlanEvalMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaPlanEvalDao|getListAll|:"+ex);
		}
		return lista;
	}
	
	public List<CargaPlanEval> getListEvaluaciones( String cursoCargaId, String orden ) {
		List<CargaPlanEval> lista = new ArrayList<CargaPlanEval>();
		try{
			String comando = "SELECT CURSO_CARGA_ID, EVALUACION_ID, EVALUACION_NOMBRE, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, VALOR, ACTIVIDAD_ID" +
					" FROM ENOC.CARGA_PLAN_EVAL" + 
					" WHERE CURSO_CARGA_ID = ? "+ orden;			
			lista = enocJdbc.query(comando, new CargaPlanEvalMapper(), cursoCargaId);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaPlanEvalDao|getListAll|:"+ex);
		}
		return lista;
	}

}