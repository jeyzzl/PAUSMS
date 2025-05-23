// Clase para la tabla de Alum_Plan
package aca.alumno.spring;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Order(2)
@Component
public class AlumPlanDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( AlumPlan alumPlan ) {
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.ALUM_PLAN"
					+ " (CODIGO_PERSONAL, PLAN_ID,  F_INICIO, ESTADO, ESCUELA_ID, AVANCE_ID,"
					+ " PERMISO, EVENTO, F_GRADUACION, F_EGRESO, GRADO, CICLO, PRINCIPAL, ESCALA,"
					+ " PRIMER_MATRICULA, ACTUALIZADO, CICLO_SEP, PROMEDIO, MAYOR, MENOR)"
					+ " VALUES( ?, ?,"
					+ " TO_DATE(?,'DD/MM/YYYY'),"
					+ " ?, TO_NUMBER( ?, '999'),"
					+ " TO_NUMBER( ?, '99'),"
					+ " ?,?, TO_DATE(?, 'DD/MM/YYYY'),"
					+ " TO_DATE(?, 'DD/MM/YYYY'),"
					+ " TO_NUMBER( ?, '99'),"
					+ " TO_NUMBER( ?, '99'),?,"
					+ " TO_NUMBER(?, '999'),"
					+ " TO_DATE(?, 'DD/MM/YYYY'), ?,"
					+ " TO_NUMBER( ?, '99'),"
					+ " TO_NUMBER( ?, '999.99'),"
					+ " TO_NUMBER( ?, '99'),"
					+ " TO_NUMBER( ?, '99')"
					+ " )";
			Object[] parametros = new Object[] {
				alumPlan.getCodigoPersonal(),alumPlan.getPlanId(),alumPlan.getFInicio(),
				alumPlan.getEstado(),alumPlan.getEscuelaId(),alumPlan.getAvanceId(),
				alumPlan.getPermiso(),alumPlan.getEvento(),alumPlan.getFGraduacion(),
				alumPlan.getFEgreso(),alumPlan.getGrado(),alumPlan.getCiclo(),
				alumPlan.getPrincipal(),alumPlan.getEscala(),alumPlan.getPrimerMatricula(),
				alumPlan.getActualizado(),alumPlan.getCicloSep(), alumPlan.getPromedio(), alumPlan.getMayor(), alumPlan.getMenor()
 		 	};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg( AlumPlan alumPlan ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.ALUM_PLAN"
					+ " SET F_INICIO = TO_DATE(?,'DD/MM/YYYY'),"
					+ " ESTADO = ?,"
					+ " ESCUELA_ID = TO_NUMBER(?,'999'),"
					+ " AVANCE_ID = TO_NUMBER(?,'999'),"
					+ " PERMISO = ?,"
					+ " EVENTO = ?,"
					+ " F_GRADUACION = TO_DATE(?, 'DD/MM/YYYY'),"
					+ " F_EGRESO = TO_DATE(?, 'DD/MM/YYYY'),"
					+ " GRADO = TO_NUMBER(?, '99'),"
					+ " CICLO = TO_NUMBER(?, '99'),"
					+ " PRINCIPAL = ?,"
					+ " ESCALA = TO_NUMBER(?, '999'),"
					+ " PRIMER_MATRICULA = TO_DATE(?, 'DD/MM/YYYY'),"
					+ " ACTUALIZADO = ?,"
					+ " CICLO_SEP = TO_NUMBER(?,'99'),"
					+ " PROMEDIO = TO_NUMBER(?,'999.99'),"
					+ " FINALIZADO = ?,"
					+ " MAYOR = ?,"
					+ " MENOR = ?"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND PLAN_ID = ?";			
			Object[] parametros = new Object[] {
				alumPlan.getFInicio(),alumPlan.getEstado(),alumPlan.getEscuelaId(),
				alumPlan.getAvanceId(),alumPlan.getPermiso(),alumPlan.getEvento(),
				alumPlan.getFGraduacion(),alumPlan.getFEgreso(),alumPlan.getGrado(),
				alumPlan.getCiclo(),alumPlan.getPrincipal(),alumPlan.getEscala(),
				alumPlan.getPrimerMatricula(),alumPlan.getActualizado(),alumPlan.getCicloSep(), 
				alumPlan.getPromedio(), alumPlan.getFinalizado(), alumPlan.getMayor(), alumPlan.getMenor(), alumPlan.getCodigoPersonal(),alumPlan.getPlanId()					
	 		};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|updateReg|:"+ex);		
		}		
		return ok;
	}
	
	public boolean updatePrincipal( String principal, String codigoPersonal, String planId ) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.ALUM_PLAN SET PRINCIPAL = ?"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND PLAN_ID = ?";	
			Object[] parametros = new Object[] {principal,codigoPersonal,planId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|updatePrincipal|:"+ex);
		}		
		return ok;
	}
	
	public boolean updateEstado( String estado, String codigoPersonal, String planId ) {
		boolean ok = false;
		try{
			String comando = "UPDATE ENOC.ALUM_PLAN SET ESTADO = ? WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ?";
			Object[] parametros = new Object[] {estado,codigoPersonal,planId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|updateEstado|:"+ex);
		}		
		return ok;
	}
	
	public boolean updateEstado( String estado, String codigoPersonal ) {
		boolean ok = false;
		try{
			String comando = "UPDATE ENOC.ALUM_PLAN SET ESTADO = ? WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {estado,codigoPersonal};
			if (enocJdbc.update(comando,parametros) >= 1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|updateEstado|:"+ex);
		}		
		return ok;
	}
	
	public boolean updateEstadoPrincipal( String estado, String principal, String codigoPersonal, String planId ) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.ALUM_PLAN SET ESTADO = ?, PRINCIPAL = ?"+
				" WHERE CODIGO_PERSONAL = ?"+
				" AND PLAN_ID = ?";
			Object[] parametros = new Object[] {estado, principal, codigoPersonal, planId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|updateEstadoPrincipal|:"+ex);
		}
		
		return ok;
	}
	
	public boolean updateGrado( String codigoPersonal, String planId, int grado) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.ALUM_PLAN "+ 
				"SET GRADO = ? "+								
				"WHERE CODIGO_PERSONAL = ? "+
				"AND PLAN_ID = ?";
			Object[] parametros = new Object[] {grado, codigoPersonal, planId};
			if (enocJdbc.update(comando,parametros) >= 1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|updateGrado|:"+ex);
		}
		
		return ok;
	}
	
	public boolean updatePermiso( String codigoPersonal, String planId, String permiso) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.ALUM_PLAN SET PERMISO = ? WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ?";
			Object[] parametros = new Object[] {permiso, codigoPersonal, planId};
			if (enocJdbc.update(comando,parametros) >= 1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|updateGrado|:"+ex);
		}
		
		return ok;
	}
	
	public boolean updateEvento(String codigoPersonal, String planId, String evento) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.ALUM_PLAN SET EVENTO = ? WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ?";
			Object[] parametros = new Object[] {evento, codigoPersonal, planId};
			if (enocJdbc.update(comando,parametros) >= 1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|updateEvento|:"+ex);
		}
		
		return ok;
	}
	
	public boolean updateCicloSep( String codigoPersonal, String planId, String cicloSep) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.ALUM_PLAN"
					+ " SET CICLO_SEP = TO_NUMBER(?,'99')"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND PLAN_ID = ?";
			Object[] parametros = new Object[] {cicloSep, codigoPersonal, planId};
			if (enocJdbc.update(comando,parametros) >= 1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|updateCicloSep|:"+ex);
		}
		
		return ok;
	}
	
	public boolean updatePrincipal( String codigoPersonal) {
		
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.ALUM_PLAN SET PRINCIPAL = '0' WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.update(comando,parametros) >= 1 ){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|updatePrincipal|:"+ex);
		}
		
		return ok;
	}
	
	public int updatePrimerMatricula() {
		int rows = 0;		
		try{
			String comando = "UPDATE ALUM_PLAN SET PRIMER_MATRICULA = TO_DATE(ALUM_INGRESO_PLAN(CODIGO_PERSONAL,PLAN_ID),'DD/MM/YYYY'), ACTUALIZADO = 'S'"
					+ " WHERE ALUM_INGRESO_PLAN(CODIGO_PERSONAL,PLAN_ID) != '01/01/2000'";
			rows = enocJdbc.update(comando);			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|updatePrimerMatricula|:"+ex);
		}		
		return rows;
	}
	
	public boolean updateFechaGraduacion( String fGraduacion, String codigoPersonal, String planId ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.ALUM_PLAN "+ 
				" SET F_GRADUACION = TO_DATE(?,'DD/MM/YYYY') "+
				" WHERE CODIGO_PERSONAL = ? "+
				" AND PLAN_ID = ?";
			Object[] parametros = new Object[] {fGraduacion, codigoPersonal, planId};
			if (enocJdbc.update(comando,parametros) >= 1 ){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|updateFechaGraduacion|:"+ex);		
		}
		
		return ok;
	}	
	
	public boolean updateFinalizado( String finalizado, String codigoPersonal, String planId ) {
		boolean ok = false;
		try{
			String comando = "UPDATE ENOC.ALUM_PLAN SET FINALIZADO = ? WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ?";
			Object[] parametros = new Object[] {finalizado,codigoPersonal,planId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|updateFinalizado|:"+ex);
		}		
		return ok;
	}
	
	public boolean deleteReg( String codigoPersonal, String planId ) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.ALUM_PLAN "+ 
				"WHERE CODIGO_PERSONAL = ? "+
				"AND PLAN_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, planId};
			if (enocJdbc.update(comando,parametros) >= 1 ){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|deleteReg|:"+ex);
		}
		
		return ok;
	}
	
	public AlumPlan mapeaRegId( String codigoPersonal, String planId ) {
		
		AlumPlan alumPlan = new AlumPlan();
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ALUM_PLAN WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, planId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				comando = "SELECT CODIGO_PERSONAL, PLAN_ID, TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO,"
						+ " ESTADO, ESCUELA_ID, AVANCE_ID, PERMISO, EVENTO,"
						+ " TO_CHAR(F_GRADUACION,'DD/MM/YYYY') AS F_GRADUACION,"
						+ " TO_CHAR(F_EGRESO, 'DD/MM/YYYY') AS F_EGRESO,"
						+ " GRADO, CICLO, PRINCIPAL, ESCALA, TO_CHAR(PRIMER_MATRICULA, 'DD/MM/YYYY') AS PRIMER_MATRICULA,"
						+ " ACTUALIZADO, CICLO_SEP, PROMEDIO, CREDITOS, FINALIZADO, MAYOR, MENOR"
						+ " FROM ENOC.ALUM_PLAN"
						+ " WHERE CODIGO_PERSONAL = ?"
						+ " AND PLAN_ID = ?";
				alumPlan = enocJdbc.queryForObject(comando, new AlumPlanMapper(), parametros);
			}
						
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|mapeaRegId|:"+ex);
		}
		
		return alumPlan;
	}
	
	public AlumPlan mapeaRegId(  String codigoPersonal) {
		
		AlumPlan plan = new AlumPlan();		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ALUM_PLAN WHERE CODIGO_PERSONAL = ? AND ESTADO = '1'";
			Object[] parametros = new Object[] {codigoPersonal};			
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				comando = " SELECT CODIGO_PERSONAL, PLAN_ID, TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO," 
						+ " ESTADO, ESCUELA_ID, AVANCE_ID, PERMISO, EVENTO,"  
						+ " TO_CHAR(F_GRADUACION,'DD/MM/YYYY') AS F_GRADUACION,"  
						+ " TO_CHAR(F_EGRESO, 'DD/MM/YYYY') AS F_EGRESO,"
						+ " GRADO, CICLO, PRINCIPAL, ESCALA, TO_CHAR(PRIMER_MATRICULA, 'DD/MM/YYYY') AS PRIMER_MATRICULA," 
						+ " ACTUALIZADO, CICLO_SEP, PROMEDIO, CREDITOS, FINALIZADO, MAYOR, MENOR" 
						+ " FROM ENOC.ALUM_PLAN WHERE CODIGO_PERSONAL=? AND ESTADO='1'";
				plan = enocJdbc.queryForObject(comando, new AlumPlanMapper(), parametros);	
			}						
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|mapeaRegId|:"+ex);
		}		
		return plan;
	}
	
	public AlumPlan mapeaRegIdE(  String codigoPersonal, String planId) {		
		AlumPlan plan = new AlumPlan();		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ALUM_PLAN WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, planId};			
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				comando = "SELECT * FROM ENOC.ALUM_PLAN WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ?";			
				plan = enocJdbc.queryForObject(comando, new AlumPlanMapper(), parametros);
			}						
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|mapeaRegIdE|:"+ex);
		}		
		return plan;
	}

	public boolean existeReg( String codigoPersonal, String planId) {
		boolean 		ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ALUM_PLAN WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, planId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1 ){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public int tienePlan( String codigoPersonal) {
		int total 				=  0;		
		try{
			String comando = "SELECT COUNT(*) AS TOTAL FROM ENOC.ALUM_PLAN WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
			total = enocJdbc.queryForObject(comando,Integer.class, parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|tienePlan|:"+ex);
		}
		
		return total;
	}
	
	public boolean tienePlanActivo( String codigoPersonal) {
		boolean tiene = false;		
		try{
			String comando = "SELECT COUNT(*) AS TOTAL FROM ENOC.ALUM_PLAN WHERE CODIGO_PERSONAL = ? AND ESTADO = '1'";
			Object[] parametros = new Object[] {codigoPersonal};
			if(enocJdbc.queryForObject(comando,Integer.class, parametros)>=1) {
				tiene = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|tienePlanActivo|:"+ex);
		}		
		return tiene;
	}
	
	public boolean existeCodigoPersonal( String codigoPersonal) {
		boolean 		ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ALUM_PLAN WHERE CODIGO_PERSONAL = ? ";
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1 ){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|existeCodigoPersonal|:"+ex);
		}
		
		return ok;
	}
	
	public List<String> getPlanesAlumno(  String codigoPersonal) {
		
		List<String> lista = new ArrayList<String>();
		try{
			String comando = "SELECT TRIM(PLAN_ID) AS PLAN_ID FROM ENOC.ALUM_PLAN WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.queryForList(comando, String.class, parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|getPlanesAlumno|:"+ex);
		}
		
		return lista;
	}
	
	public String getAvanceId(  String codigoPersonal, String planId){		
		String avanceId 		= "0";		
		try{			
			String comando = "SELECT COUNT(*) FROM ENOC.ALUM_PLAN WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, planId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1 ){				
				comando = "SELECT COALESCE(AVANCE_ID,0) FROM ENOC.ALUM_PLAN WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ?";
				avanceId = enocJdbc.queryForObject(comando,String.class, parametros);								
			}						
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|getAvanceId|:"+ex);
		}		
		return avanceId;
	}
	
	public String getPlanActual(  String codigoPersonal){		
		String plan 	= "0";
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ALUM_PLAN WHERE CODIGO_PERSONAL = ? AND ESTADO='1'";
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1) {				
				comando = "SELECT PLAN_ID FROM ENOC.ALUM_PLAN WHERE CODIGO_PERSONAL = ? AND ESTADO='1'";
				plan = enocJdbc.queryForObject(comando,String.class, parametros);
			}						
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|getPlanActual|:"+ex);
		}
		return plan;
	}
	
	public String getPermiso( String codigoPersonal, String planId ){
		
		String permiso 		= "";	
		try{
			String comando = "SELECT PERMISO FROM ENOC.ALUM_PLAN  WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, planId};			
			permiso = enocJdbc.queryForObject(comando,String.class, parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|getPermiso|:"+ex);
		}
		
		return permiso;
	}

	public String getPromedio(String codigoPersonal, String planId ){
		String promeido	= "";	
		try{
			String comando = "SELECT ALUM_PROMEDIO(?,?) FROM DUAL";
			Object[] parametros = new Object[] {codigoPersonal, planId};			
			promeido = enocJdbc.queryForObject(comando,String.class, parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|getPromedio|:"+ex);
		}
		
		return promeido;
	}
	
	public String getEvento(  String codigoPersonal, String planId){
	
		String evento 			= "";	
		try{
			String comando = "SELECT EVENTO FROM ENOC.ALUM_PLAN"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND PLAN_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, planId};
			evento = enocJdbc.queryForObject(comando,String.class, parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|getEvento|:"+ex);
		}
		
		return evento;
	}	
	
	public String getCarreraId( String codigoPersonal) {
		
		String carreraId		= "x";		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ALUM_PLAN A, ENOC.MAPA_PLAN B "
					+ " WHERE A.CODIGO_PERSONAL = ?"
					+ " AND A.ESTADO = '1'"
					+ " AND B.PLAN_ID = A.PLAN_ID";
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1) {
				comando = "SELECT B.CARRERA_ID FROM ENOC.ALUM_PLAN A, ENOC.MAPA_PLAN B "
						+ " WHERE A.CODIGO_PERSONAL = ?"
						+ " AND A.ESTADO = '1'"
						+ " AND B.PLAN_ID = A.PLAN_ID";
				carreraId = enocJdbc.queryForObject(comando,String.class, parametros);
			}						
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|getCarreraId|:"+ex);
		}
		
		return carreraId;
	}
	
	public String getCarreraDelPlan( String planId) {
	
		String carreraId		= "x";
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.MAPA_PLAN WHERE PLAN_ID = ?";
			Object[] parametros = new Object[] {planId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1) {
				comando = "SELECT CARRERA_ID FROM ENOC.MAPA_PLAN WHERE PLAN_ID = ?";
				carreraId = enocJdbc.queryForObject(comando,String.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|getCarreraIdPLAN|:"+ex);
		}
		
		return carreraId;
	}
	
	public String getCarreraNivel( String carreraId) {
		String nivelId		= "x";
		try{
			String comando = "SELECT NIVEL_ID FROM ENOC.CAT_CARRERA WHERE CARRERA_ID = ?";
			if (enocJdbc.queryForObject(comando,Integer.class, carreraId) >= 1) {
				comando = "SELECT NIVEL_ID FROM ENOC.CAT_CARRERA WHERE CARRERA_ID = ?";
				nivelId = enocJdbc.queryForObject(comando,String.class, carreraId);
			}					
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|getCarreraNivel|:"+ex);
		}
		
		return nivelId;
	}
	
	public String getFechaInicio(  String codigoPersonal, String planId) {		
		
		String fecha 			= "";		
		try{
			String comando = "SELECT TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO FROM ENOC.ALUM_PLAN"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND PLAN_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, planId};
			fecha = enocJdbc.queryForObject(comando,String.class, parametros);		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|getFechaInicio|:"+ex);
		}
		
		return fecha;
	}
	
	public boolean actualizaCicloAlumno( String codigoPersonal) {
		
		boolean ok = false;				
		try{
			String comando = "UPDATE ENOC.ALUM_PLAN"+ 
				" SET CICLO = ALUM_PLAN_CICLO(CODIGO_PERSONAL,PLAN_ID)"+
				" WHERE CODIGO_PERSONAL = ? " +
				" AND ESTADO = '1'";
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|actualizaCicloAlumno|:"+ex);
		}
		
		return ok;
	}
	
	public boolean actualizaCicloAlumnoPostgrado( String codigoPersonal) {
		
		boolean ok = false;				
		try{
			String comando = "UPDATE ENOC.ALUM_PLAN"+
				" SET CICLO = ALUM_PLAN_CICLO_POSTGRADO(CODIGO_PERSONAL,PLAN_ID)"+
				" WHERE CODIGO_PERSONAL = ?" + 
				" AND ESTADO = '1'";
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|actualizaCicloAlumnoPostgrado|:"+ex);
		}
		
		return ok;
	}
	
	public boolean actualizaCiclo( String cargas) {
		
		boolean ok = false;				
		try{
			String comando = "UPDATE ENOC.ALUM_PLAN"+ 
				" SET CICLO = ENOC.ALUM_PLAN_CICLO(CODIGO_PERSONAL,PLAN_ID)"+
				" WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ESTADISTICA WHERE CARGA_ID IN ("+cargas+"))" + 
				" AND ENOC.NIVEL_PLAN(PLAN_ID) IN (1,2,5)";			
			if (enocJdbc.update(comando)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|actualizaCiclo|:"+ex);
		}
		
		return ok;
	}
	
	public boolean actualizaCicloPostgrado( String cargas) {
		
		boolean ok = false;				
		try{
			String comando = "UPDATE ENOC.ALUM_PLAN"+			 
				" SET CICLO = ENOC.ALUM_PLAN_CICLO_POSTGRADO(CODIGO_PERSONAL,PLAN_ID)"+
				" WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ESTADISTICA WHERE CARGA_ID IN ("+cargas+"))" + 
				" AND ENOC.NIVEL_PLAN(PLAN_ID) IN (3,4)";				
			if (enocJdbc.update(comando)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|actualizaCicloPostgrado|:"+ex);
		}
		
		return ok;
	}
	
	public boolean actualizaGradoAlumno( String codigoPersonal) {
		
		AlumPlan alumPlan 	= new AlumPlan();
		boolean ok 			= false;		
		try{
			int grado 		= 0;			
			int error		= 0;
			
			String comando = " SELECT COUNT(*) FROM ENOC.ALUM_PLAN"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND ESTADO = '1'";
			if (enocJdbc.queryForObject(comando, Integer.class,codigoPersonal) >= 1){			
				comando = " SELECT CODIGO_PERSONAL, PLAN_ID, F_INICIO, ENOC.FACULTAD(ENOC.CARRERA(PLAN_ID)) AS ESTADO, CICLO, ESCUELA_ID, AVANCE_ID,"
						+ " PERMISO, EVENTO, F_GRADUACION, F_EGRESO, GRADO, CICLO, PRINCIPAL, ESCALA,"
						+ " PRIMER_MATRICULA, ACTUALIZADO, CICLO_SEP, PROMEDIO, CREDITOS, FINALIZADO, MAYOR, MENOR"
						+ " FROM ENOC.ALUM_PLAN"
						+ " WHERE CODIGO_PERSONAL = ? "
						+ " AND ESTADO = '1'";
				alumPlan = enocJdbc.queryForObject(comando, new AlumPlanMapper(),codigoPersonal); ///
				
				int ciclo = Integer.parseInt(alumPlan.getCiclo());
				
				// Se está guardando la facultad en el campo del estado
				if (alumPlan.getEstado().equals("107")){
					if (ciclo >= 1 && ciclo <= 3) {
						grado = 1;
					}else if (ciclo >= 4 && ciclo <=6){
						grado = 2;
					}else{
						grado = 0;
					}
				}else{
					if (ciclo==1 || ciclo ==2) {
						grado = 1;
					}else if (ciclo==3 || ciclo ==4){
						grado = 2;
					}else if (ciclo==5 || ciclo ==6){
						grado = 3;
					}else if (ciclo==7 || ciclo ==8){
						grado = 4;
					}else if (ciclo==9 || ciclo ==10){
						grado = 5;
					}else if (ciclo==11 || ciclo ==12){
						grado = 6;
					}else{
						grado = 0;
					}					
				}				
				if (updateGrado(alumPlan.getCodigoPersonal(),alumPlan.getPlanId(), grado) == false){				
					error++;
				}					
			}			
			if (error==0){
				ok=true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|actualizaGradoAlumno|:"+ex);		
		}
		
		return ok;
	}
	
	public boolean actualizaGrado( String cargas) {
		
		List<AlumPlan> lista 	= new ArrayList<AlumPlan>();		
		boolean ok 				= false;
		
		try{
			int grado 		= 0;			
			int error		= 0;			 
			String comando = " SELECT CODIGO_PERSONAL, PLAN_ID, F_INICIO, ENOC.FACULTAD(ENOC.CARRERA(PLAN_ID)) AS ESTADO, CICLO, ESCUELA_ID, AVANCE_ID,"
					+ " PERMISO, EVENTO, F_GRADUACION, F_EGRESO, GRADO, CICLO, PRINCIPAL, ESCALA,"
					+ " PRIMER_MATRICULA, ACTUALIZADO, CICLO_SEP, PROMEDIO, CREDITOS, FINALIZADO, MAYOR, MENOR"
					+ " FROM ENOC.ALUM_PLAN"
					+ " WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ESTADISTICA WHERE CARGA_ID IN ("+cargas+"))";					
					
			lista = enocJdbc.query(comando, new AlumPlanMapper());
			
			for (AlumPlan alumPlan : lista){
				
				int ciclo = Integer.parseInt(alumPlan.getCiclo());
				
				if (alumPlan.getEstado().equals("107")){
					if ( ciclo >= 1 && ciclo <= 3) {
						grado = 1;
					}else if ( ciclo >=4 && ciclo <=6){
						grado = 2;
					}else{
						grado = 0;
					}
				}else{
					if (ciclo == 1 || ciclo == 2) {
						grado = 1;
					}else if (ciclo == 3 || ciclo ==4){
						grado = 2;
					}else if (ciclo == 5 || ciclo ==6){
						grado = 3;
					}else if (ciclo == 7 || ciclo == 8){
						grado = 4;
					}else if (ciclo ==9 || ciclo == 10){
						grado = 5;
					}else if (ciclo == 11 || ciclo == 12){
						grado = 6;
					}else{
						grado = 0;
					}					
				}				
				if (updateGrado(alumPlan.getCodigoPersonal(),alumPlan.getPlanId(), grado)==false){					
					error++;
				}					
			}			
			if (error==0){
				ok=true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|actualizaGrado|:"+ex);		
		}
		
		return ok;
	}
	
	public String getSem( String codigoPersonal, String planId) {		
		String	sem			= "0"; 
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ALUM_PLAN WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, planId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				comando = "SELECT CICLO FROM ENOC.ALUM_PLAN WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ?";
				sem= enocJdbc.queryForObject(comando, String.class, parametros);
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|getSem|:"+ex);
		}		
		return sem;
	}
	
	public String getCicloSep( String codigoPersonal, String planId) {
		
		String	ciclo			= "0"; 
		try{
			String comando = "SELECT CICLO_SEP FROM ENOC.ALUM_PLAN WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, planId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ciclo= enocJdbc.queryForObject(comando, String.class, parametros);
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|getCicloSep|:"+ex);
		}
		
		return ciclo;
	}
	
	public String getGrado( String codigoPersonal, String planId) {
		
		String	grado				= "0"; 
		try{
			String comando = "SELECT GRADO FROM ENOC.ALUM_PLAN WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, planId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				grado= enocJdbc.queryForObject(comando, String.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|getGrado|:"+ex);
		}
		
		return grado;
	}
	
	public String getAlumPlanCiclo( String codigoPersonal, String planId) {
		
		String	ciclo			= ""; 
		try{
			String comando = "SELECT ALUM_PLAN_CICLO(CODIGO_PERSONAL, PLAN_ID) AS CICLO FROM ENOC.ALUM_PLAN "
					+ " WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, planId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ciclo= enocJdbc.queryForObject(comando, String.class, parametros);
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|getAlumPlanCiclo|:"+ex);
		}
		
		return ciclo;
	}
	
	public String getAlumPromCreditos( String codigoPersonal, String planId) {
		
		String	promedio		= "0"; 
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ALUM_PLAN WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, planId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				comando 	= "SELECT ENOC.ALUM_PROMEDIO_CREDITOS(CODIGO_PERSONAL, PLAN_ID) AS PROMEDIO FROM ENOC.ALUM_PLAN WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ?";
				promedio	= enocJdbc.queryForObject(comando, String.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|getAlumPromCreditos|:"+ex);
		}		
		return promedio;
	}
	
	public String getEscuelaOrigen( String codigoPersonal, String planId) {
		
		String escuela			= "x";
		
		try{
			String comando = " SELECT ESCUELA_ID FROM ENOC.ALUM_PLAN WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ? ";
			Object[] parametros = new Object[] {codigoPersonal, planId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				escuela = enocJdbc.queryForObject(comando, String.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|getEscuelaOrigen|:"+ex);
		}
		
		return escuela;
	}
	
	public int getContPrimerIngreso( String year, String carreraId) {
		
		int total			= 0;
		
		try{
			String comando = " SELECT COUNT(CODIGO_PERSONAL) AS TOTAL FROM ALUM_PLAN WHERE TO_CHAR(PRIMER_MATRICULA,'YYYY') = ? " +
					" AND ENOC.CARRERA(PLAN_ID)= ? AND PRIMER_MATRICULA IS NOT NULL" +
					" AND (SELECT MODALIDAD_ID FROM ENOC.ALUM_ACADEMICO WHERE CODIGO_PERSONAL = ALUM_PLAN.CODIGO_PERSONAL)=1 ";			
			if (enocJdbc.queryForObject(comando, Integer.class,year,carreraId) >= 1){
				total = enocJdbc.queryForObject(comando, Integer.class,year,carreraId);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|getContPrimerIngreso|:"+ex);
		}
		
		return total;
	}
	
	public String getFInicio( String codigoPersonal, String planId) {
		
		String 	fecha			= "X";
		
		try{
			String comando = "SELECT COALESCE(MIN(F_EVALUACION),NULL) AS INICIO FROM ENOC.ALUMNO_CURSO"
					+ " WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ? AND CONVALIDACION = 'N'";
			if (enocJdbc.queryForObject(comando, Integer.class,codigoPersonal,planId) >= 1){
				fecha = enocJdbc.queryForObject(comando, String.class,codigoPersonal,planId);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|getFInicio|:"+ex);
		}
		
		return fecha;
	}
	
	public String getPrimerMatricula( String codigoPersonal, String planId) {
	
		String 	fecha			= "X";
		
		try{
			String comando = "SELECT COALESCE(MIN(FECHA),NULL) AS INSCRIPCION " +
					" FROM MATEO.FES_CCOBRO WHERE MATRICULA = ? AND PLAN_ID = ? AND INSCRITO = 'S' ";
			if (enocJdbc.queryForObject(comando, Integer.class,codigoPersonal,planId) >= 1){
				fecha = enocJdbc.queryForObject(comando, String.class,codigoPersonal,planId);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|getPrimerMatricula|:"+ex);
		}
		
		return fecha;
	}
	
	public String getNumMaterias( String codigoPersonal, String planId) {
		
		String 	mat			= "X";
		
		try{
			
			String comando = "SELECT COUNT(CODIGO_PERSONAL) AS CONT FROM ENOC.ALUMNO_CURSO"
					+ " WHERE CODIGO_PERSONAL = ? "
					+ " AND PLAN_ID =  ? "
					+ " AND CONVALIDACION = 'N'";
			if (enocJdbc.queryForObject(comando, Integer.class,codigoPersonal,planId) >= 1){
				mat = enocJdbc.queryForObject(comando, String.class,codigoPersonal,planId);
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|getNumMaterias|:"+ex);
		}
		
		return mat;
	}
		
	public List<AlumPlan> getListAll( String orden ) {
		
		List<AlumPlan> lista	= new ArrayList<AlumPlan>();
		
		try{
			String comando = " SELECT CODIGO_PERSONAL, PLAN_ID,"
					+ " TO_CHAR(F_INICIO,'dd/mm/yyyy') F_INICIO,"
					+ " ESTADO, ESCUELA_ID, AVANCE_ID, PERMISO, EVENTO,"
					+ " TO_CHAR(F_GRADUACION,'DD/MM/YYYY') AS F_GRADUACION,"
					+ " TO_CHAR(F_EGRESO,'DD/MM/YYYY') AS F_EGRESO, GRADO, CICLO,"
					+ " PRINCIPAL, ESCALA, PRIMER_MATRICULA, ACTUALIZADO, CICLO_SEP, PROMEDIO, CREDITOS, FINALIZADO, MAYOR, MENOR"
					+ " FROM ENOC.ALUM_PLAN "+ orden;
			lista = enocJdbc.query(comando, new AlumPlanMapper()); 
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	public List<String> getLisPorMatriculayNivel( String codigoPersonal, String nivelId ) {
		
		List<String> lista	= new ArrayList<String>();		
		try{
			String comando = " SELECT DISTINCT ENOC.CARRERA(PLAN_ID) AS CARRERA FROM ENOC.ALUM_PLAN "
					+ " WHERE CODIGO_PERSONAL = ?" 
					+ " AND ENOC.NIVEL_PLAN(PLAN_ID) = ? "; 
			lista = enocJdbc.queryForList(comando,String.class,codigoPersonal,nivelId);			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|getLisPorMatriculayNivel|:"+ex);
		}
		
		return lista;
	}
	
	public List<AlumPlan> getLista( String codigoPersonal, String orden ) {
		
		List<AlumPlan> lista	= new ArrayList<AlumPlan>();
		try{
			String comando = "SELECT CODIGO_PERSONAL, " +
					"PLAN_ID," +
					"TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO," +
					"ESTADO, ESCUELA_ID, AVANCE_ID, PERMISO, EVENTO," +
					"TO_CHAR(F_GRADUACION,'DD/MM/YYYY') AS F_GRADUACION," +
					"TO_CHAR(F_EGRESO,'DD/MM/YYYY') AS F_EGRESO, GRADO, CICLO," +
					"PRINCIPAL, ESCALA, TO_CHAR(PRIMER_MATRICULA,'DD/MM/YYYY') AS PRIMER_MATRICULA, ACTUALIZADO, CICLO_SEP, PROMEDIO, CREDITOS, FINALIZADO, MAYOR, MENOR " +
					"FROM ENOC.ALUM_PLAN " + 
					"WHERE CODIGO_PERSONAL = ? "+ orden;
			lista = enocJdbc.query(comando, new AlumPlanMapper(), codigoPersonal);
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|getLista|:"+ex);
		}
		
		return lista;
	}
	
	public List<AlumPlan> lisPlanesAlumno( String codigoPersonal, String orden ) {
		
		List<AlumPlan> lista	= new ArrayList<AlumPlan>();
		try{
			String comando = "SELECT CODIGO_PERSONAL, PLAN_ID, TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO,"
					+ " ESTADO, ESCUELA_ID, AVANCE_ID, PERMISO, EVENTO, TO_CHAR(F_GRADUACION,'DD/MM/YYYY') AS F_GRADUACION,"
					+ " TO_CHAR(F_EGRESO,'DD/MM/YYYY') AS F_EGRESO, GRADO, CICLO, PRINCIPAL, ESCALA,"
					+ " TO_CHAR(PRIMER_MATRICULA,'DD/MM/YYYY') AS PRIMER_MATRICULA, ACTUALIZADO, CICLO_SEP, PROMEDIO, CREDITOS, FINALIZADO, MAYOR, MENOR"
					+ " FROM ENOC.ALUM_PLAN"
					+ " WHERE CODIGO_PERSONAL = ? "+ orden;
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.query(comando, new AlumPlanMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|getLista|:"+ex);
		}
		
		return lista;
	}
	
	public List<String> lisCarrerasPorAlumno( String codigoPersonal) {
		List<String> lista	= new ArrayList<String>();
		try{
			String comando = "SELECT DISTINCT(ENOC.CARRERA(PLAN_ID)) FROM ENOC.ALUM_PLAN WHERE CODIGO_PERSONAL = ? ORDER BY 1";
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.queryForList(comando, String.class, parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|lisCarrerasPorAlumno|:"+ex);
		}		
		return lista;
	}
	
	public List<AlumPlan> lisSinTitulo( String codigoPersonal, String orden ) {
		
		List<AlumPlan> lista	= new ArrayList<AlumPlan>();
		try{
			String comando = "SELECT CODIGO_PERSONAL,"
					+ " PLAN_ID,"
					+ " TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO,"
					+ " ESTADO, ESCUELA_ID, AVANCE_ID, PERMISO, EVENTO,"
					+ " TO_CHAR(F_GRADUACION,'DD/MM/YYYY') AS F_GRADUACION,"
					+ " TO_CHAR(F_EGRESO,'DD/MM/YYYY') AS F_EGRESO, GRADO, CICLO,"
					+ " PRINCIPAL, ESCALA, TO_CHAR(PRIMER_MATRICULA,'DD/MM/YYYY') AS PRIMER_MATRICULA,"
					+ " ACTUALIZADO, CICLO_SEP, PROMEDIO, CREDITOS, FINALIZADO, MAYOR, MENOR"
					+ " FROM ENOC.ALUM_PLAN"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND PLAN_ID NOT IN (SELECT PLAN_ID FROM ENOC.TIT_ALUMNO WHERE CODIGO_PERSONAL = ? )"
					+ " AND PLAN_ID IN (SELECT PLAN_ID FROM ENOC.MAPA_PLAN WHERE OFICIAL = 'S')"
					+ orden;
			lista = enocJdbc.query(comando, new AlumPlanMapper(),codigoPersonal,codigoPersonal);
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|lisSinTitulo|:"+ex);
		}
		
		return lista;
	}
	
	public List<AlumPlan> getListaAlumnosPlan( String planId, String orden ) {
		
		List<AlumPlan> lista	= new ArrayList<AlumPlan>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, PLAN_ID," +
					" TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO," +
					" ESTADO, ESCUELA_ID, AVANCE_ID, PERMISO, EVENTO," +
					" TO_CHAR(F_GRADUACION,'DD/MM/YYYY') AS F_GRADUACION," +
					" TO_CHAR(F_EGRESO,'DD/MM/YYYY') AS F_EGRESO, GRADO, CICLO," +
					" PRINCIPAL, ESCALA, TO_CHAR(PRIMER_MATRICULA,'DD/MM/YYYY') AS PRIMER_MATRICULA, ACTUALIZADO, CICLO_SEP, PROMEDIO, CREDITOS, FINALIZADO, MAYOR, MENOR" +
					" FROM ENOC.ALUM_PLAN " +
					" WHERE PLAN_ID = ?" +
					" AND CODIGO_PERSONAL||PLAN_ID IN" +
					"	(SELECT MATRICULA||PLAN_ID FROM MATEO.FES_CCOBRO WHERE MATRICULA = ALUM_PLAN.CODIGO_PERSONAL AND INSCRITO = 'S') "+ orden;
			Object[] parametros = new Object[] {planId};
			lista = enocJdbc.query(comando, new AlumPlanMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|getListaAlumnosPlan|:"+ex);
		}
		
		return lista;
	}
	
	public List<aca.Mapa> lisPlanesPorNivelYDocumentos( String nivelId, String documentos, String orden ){
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();	
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, PLAN_ID AS VALOR FROM ENOC.ALUM_PLAN"
					+ " WHERE CODIGO_PERSONAL IN ( SELECT MATRICULA FROM ENOC.ARCH_DOCALUM WHERE IDDOCUMENTO IN ("+documentos+"))"
					+ " AND PLAN_ID IN (SELECT PLAN_ID FROM ENOC.MAPA_PLAN WHERE CARRERA_ID IN (SELECT CARRERA_ID FROM ENOC.CAT_CARRERA WHERE NIVEL_ID = TO_NUMBER(?,'99'))) "+ orden;
			Object[] parametros = new Object[] {nivelId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|getListaAlumnosPlan|:"+ex);
		}
		
		return lista;
	}
	
	public List<AlumPlan> listaAlumnosCarga(String cargaId, String orden ) {
		
		List<AlumPlan> lista	= new ArrayList<AlumPlan>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, PLAN_ID, TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO, ESTADO, ESCUELA_ID, AVANCE_ID, PERMISO, EVENTO, TO_CHAR(F_GRADUACION,'DD/MM/YYYY') AS F_GRADUACION,"
					+ " TO_CHAR(F_EGRESO,'DD/MM/YYYY') AS F_EGRESO, GRADO, CICLO, PRINCIPAL, ESCALA, TO_CHAR(PRIMER_MATRICULA,'DD/MM/YYYY') AS PRIMER_MATRICULA, ACTUALIZADO, CICLO_SEP, PROMEDIO, CREDITOS, FINALIZADO,"
					+ " MAYOR, MENOR"
					+ " FROM ENOC.ALUM_PLAN WHERE CODIGO_PERSONAL||PLAN_ID IN (SELECT CODIGO_PERSONAL||PLAN_ID FROM ENOC.ESTADISTICA  WHERE CARGA_ID = ?)"+ orden;
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new AlumPlanMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|listaAlumnosCarga|:"+ex);
		}
		
		return lista;
	}
	
	public List<AlumPlan> getListaAlumnosYear( String year, String orden ) {
		
		List<AlumPlan> lista	= new ArrayList<AlumPlan>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, PLAN_ID, TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO, ESTADO, ESCUELA_ID, AVANCE_ID, PERMISO, EVENTO, TO_CHAR(F_GRADUACION,'DD/MM/YYYY') AS F_GRADUACION,"
					+ " TO_CHAR(F_EGRESO,'DD/MM/YYYY') AS F_EGRESO, GRADO, CICLO, PRINCIPAL, ESCALA, PRIMER_MATRICULA, ACTUALIZADO, CICLO_SEP, PROMEDIO, FINALIZADO, MAYOR, MENOR FROM ENOC.ALUM_PLAN A"
					+ " WHERE A.F_INICIO BETWEEN TO_DATE('01/01/"+year+"','DD/MM/YYYY') AND TO_DATE('31/12/"+year+"','DD/MM/YYYY')"
					+ " OR (SELECT TO_CHAR(COALESCE(MAX(FECHA),PRIMER_MATRICULA),'DD/MM/YYYY') FROM MATEO.FES_CCOBRO WHERE MATRICULA = A.CODIGO_PERSONAL AND PLAN_ID = A.PLAN_ID AND INSCRITO = 'S') BETWEEN TO_DATE('01/01/"+year+"','DD/MM/YYYY') AND TO_DATE('31/12/"+year+"','DD/MM/YYYY')"
					+ " AND A.CODIGO_PERSONAL||A.PLAN_ID = (SELECT DISTINCT(B.CODIGO_PERSONAL||B.PLAN_ID) FROM ENOC.ALUMNO_CURSO B WHERE (B.NOTA > 0 OR 'S' IN (SELECT C.INSCRITO FROM MATEO.FES_CCOBRO C"
					+ " WHERE C.MATRICULA = A.CODIGO_PERSONAL AND C.PLAN_ID = A.PLAN_ID)) AND A.CODIGO_PERSONAL = B.CODIGO_PERSONAL AND A.PLAN_ID = B.PLAN_ID)"+ orden;
			lista = enocJdbc.query(comando, new AlumPlanMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|getListaAlumnosYear|:"+ex);
		}
		
		return lista;
	}
	
	public List<AlumPlan> lisAlumnosPromedios( String planId, String orden ) {
		
		List<AlumPlan> lista	= new ArrayList<AlumPlan>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, PLAN_ID," +
					" TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO," +
					" ESTADO, ESCUELA_ID, AVANCE_ID, PERMISO, EVENTO," +
					" TO_CHAR(F_GRADUACION,'DD/MM/YYYY') AS F_GRADUACION," +
					" TO_CHAR(F_EGRESO,'DD/MM/YYYY') AS F_EGRESO, GRADO, CICLO," +
					" PRINCIPAL, ESCALA, TO_CHAR(PRIMER_MATRICULA,'DD/MM/YYYY') AS PRIMER_MATRICULA, ACTUALIZADO, CICLO_SEP, PROMEDIO, CREDITOS, FINALIZADO, MAYOR, MENOR " +
					" FROM ENOC.ALUM_PLAN " +
					" WHERE PLAN_ID = ? AND ESTADO = '1'" +
					" AND PROMEDIO > 0 "+ orden;			
			Object[] parametros = new Object[] {planId};
			lista = enocJdbc.query(comando, new AlumPlanMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|getListaAlumnosInscritos|:"+ex);
		}
		
		return lista;
	}
	
	public List<AlumPlan> getListYear( String year, String orden ) {
		
		List<AlumPlan> lista	= new ArrayList<AlumPlan>();		
		String comando		= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, PLAN_ID,	TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO," +
					" ESTADO, ESCUELA_ID, AVANCE_ID, PERMISO, EVENTO," +
					" TO_CHAR(F_GRADUACION,'DD/MM/YYYY') AS F_GRADUACION, TO_CHAR(F_EGRESO,'DD/MM/YYYY') AS F_EGRESO, " +
					" GRADO, CICLO, PRINCIPAL, ESCALA, PRIMER_MATRICULA, ACTUALIZADO, CICLO_SEP, PROMEDIO, CREDITOS, FINALIZADO, MAYOR, MENOR" +
					" FROM ENOC.ALUM_PLAN A" + 
					" WHERE A.F_INICIO BETWEEN TO_DATE('01/07/"+year+"','DD/MM/YYYY') AND TO_DATE('30/06/"+(Integer.parseInt(year)+1)+"','DD/MM/YYYY')" +
					" AND A.CODIGO_PERSONAL||A.PLAN_ID = (SELECT DISTINCT(B.CODIGO_PERSONAL||B.PLAN_ID) FROM ENOC.ALUMNO_CURSO B" +
														" WHERE (B.NOTA > 0 OR 'S' IN (SELECT C.INSCRITO FROM MATEO.FES_CCOBRO C" +
																					 " WHERE C.MATRICULA = A.CODIGO_PERSONAL" +
																					 " AND C.PLAN_ID = A.PLAN_ID))" +
														" AND A.CODIGO_PERSONAL = B.CODIGO_PERSONAL" +
														" AND A.PLAN_ID = B.PLAN_ID) "+ orden;
			lista = enocJdbc.query(comando, new AlumPlanMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|getListYear|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String, String> mapAlumnosPlanYear(String year ) {
		List<aca.Mapa> list 				= new ArrayList<aca.Mapa>();
		HashMap<String, String> mapa	= new HashMap<String,String>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, APELLIDO_PATERNO||' '||APELLIDO_MATERNO||' '||NOMBRE AS VAlOR"
					+ " FROM ENOC.ALUM_PERSONAL AP"
					+ " WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_PLAN A"
					+ " WHERE A.F_INICIO BETWEEN TO_DATE('01/01/"+year+"','DD/MM/YYYY') AND TO_DATE('31/12/"+year+"','DD/MM/YYYY')"
					+ " OR (SELECT TO_CHAR(COALESCE(MAX(FECHA),PRIMER_MATRICULA),'DD/MM/YYYY') FROM MATEO.FES_CCOBRO WHERE MATRICULA = A.CODIGO_PERSONAL AND PLAN_ID = A.PLAN_ID AND INSCRITO = 'S') "
					+ " BETWEEN TO_DATE('01/01/"+year+"','DD/MM/YYYY') AND TO_DATE('31/12/"+year+"','DD/MM/YYYY')"
					+ " AND A.CODIGO_PERSONAL||A.PLAN_ID = (SELECT DISTINCT(B.CODIGO_PERSONAL||B.PLAN_ID) FROM ENOC.ALUMNO_CURSO B WHERE (B.NOTA > 0 OR 'S' IN (SELECT C.INSCRITO FROM MATEO.FES_CCOBRO C"
					+ " WHERE C.MATRICULA = A.CODIGO_PERSONAL AND C.PLAN_ID = A.PLAN_ID)) AND A.CODIGO_PERSONAL = B.CODIGO_PERSONAL AND A.PLAN_ID = B.PLAN_ID))";
			list = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa tipo : list ) {
				mapa.put(tipo.getLlave(), (String)tipo.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|mapAlumnosPlanYear|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String,AlumPlan> getMapInscritos( String orden ) {
		
		HashMap<String,AlumPlan> mapa = new HashMap<String,AlumPlan>();
		List<AlumPlan> lista	= new ArrayList<AlumPlan>();
		String comando				= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL,"+
				" PLAN_ID,"+
				" TO_CHAR(F_INICIO,'dd/mm/yyyy') F_INICIO,"+
				" ESTADO,"+
				" ESCUELA_ID,"+
				" AVANCE_ID,"+
				" PERMISO,"+
				" EVENTO,"+
				" TO_CHAR(F_GRADUACION,'DD/MM/YYYY') AS F_GRADUACION," +
				" TO_CHAR(F_EGRESO,'DD/MM/YYYY') AS F_EGRESO, GRADO, CICLO," +
				" PRINCIPAL, ESCALA, PRIMER_MATRICULA, ACTUALIZADO, CICLO_SEP, PROMEDIO, CREDITOS, FINALIZADO, MAYOR, MENOR"+
				" FROM ENOC.ALUM_PLAN" +
				" WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.INSCRITOS)" +
				" AND ESTADO = '1' "+ orden;
			lista = enocJdbc.query(comando, new AlumPlanMapper());			
			for(AlumPlan plan : lista){				
				mapa.put(plan.getCodigoPersonal()+plan.getPlanId(), plan);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|getMapInscritos|:"+ex);
		}
		
		return mapa;
	}

	public HashMap<String,AlumPlan> getMapAlumPlan( String orden ) {
		
		HashMap<String,AlumPlan> mapa = new HashMap<String,AlumPlan>();
		List<AlumPlan> lista	= new ArrayList<AlumPlan>();
		String comando				= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL,"+
				" PLAN_ID,"+
				" TO_CHAR(F_INICIO,'dd/mm/yyyy') F_INICIO,"+
				" ESTADO,"+
				" ESCUELA_ID,"+
				" AVANCE_ID,"+
				" PERMISO,"+
				" EVENTO,"+
				" TO_CHAR(F_GRADUACION,'DD/MM/YYYY') AS F_GRADUACION," +
				" TO_CHAR(F_EGRESO,'DD/MM/YYYY') AS F_EGRESO, GRADO, CICLO," +
				" PRINCIPAL, ESCALA, PRIMER_MATRICULA, ACTUALIZADO, CICLO_SEP, PROMEDIO, CREDITOS, FINALIZADO, MAYOR, MENOR"+
				" FROM ENOC.ALUM_PLAN" + orden;
			lista = enocJdbc.query(comando, new AlumPlanMapper());			
			for(AlumPlan plan : lista){				
				mapa.put(plan.getCodigoPersonal()+plan.getPlanId(), plan);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|getMapAlumPlan|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,AlumPlan> getMapPorCarga( String cargaId, String orden ) {
		
		HashMap<String,AlumPlan> mapa = new HashMap<String,AlumPlan>();
		List<AlumPlan> lista	= new ArrayList<AlumPlan>();
		String comando				= "";		
		
		try{
			comando = "SELECT CODIGO_PERSONAL," +
					" PLAN_ID," +
					" TO_CHAR(F_INICIO,'dd/mm/yyyy') AS F_INICIO," +
					" ESTADO," +
					" ESCUELA_ID," +
					" AVANCE_ID," +
					" PERMISO," +
					" EVENTO," +
					" TO_CHAR(F_GRADUACION,'DD/MM/YYYY') AS F_GRADUACION," +
					" TO_CHAR(F_EGRESO,'DD/MM/YYYY') AS F_EGRESO, GRADO, CICLO," +
					" PRINCIPAL, ESCALA, PRIMER_MATRICULA, ACTUALIZADO, CICLO_SEP, PROMEDIO, CREDITOS, FINALIZADO, MAYOR, MENOR" +
					" FROM ENOC.ALUM_PLAN" +
					" WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.CARGA_ALUMNO WHERE CARGA_ID = ? ) "+ orden;
			lista = enocJdbc.query(comando, new AlumPlanMapper(),cargaId);			
			for(AlumPlan plan : lista){				
				mapa.put(plan.getCodigoPersonal()+plan.getPlanId(), plan);
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|getMapPorCarga|:"+ex);
		}
		
		return mapa;
	}

	public HashMap<String,AlumPlan> mapaPlanPorCarga( String cargaId, String orden ) {
		
		HashMap<String,AlumPlan> mapa = new HashMap<String,AlumPlan>();
		List<AlumPlan> lista	= new ArrayList<AlumPlan>();
		String comando				= "";		
		
		try{
			comando = "SELECT CODIGO_PERSONAL," +
					" PLAN_ID," +
					" TO_CHAR(F_INICIO,'dd/mm/yyyy') AS F_INICIO," +
					" ESTADO," +
					" ESCUELA_ID," +
					" AVANCE_ID," +
					" PERMISO," +
					" EVENTO," +
					" TO_CHAR(F_GRADUACION,'DD/MM/YYYY') AS F_GRADUACION," +
					" TO_CHAR(F_EGRESO,'DD/MM/YYYY') AS F_EGRESO, GRADO, CICLO," +
					" PRINCIPAL, ESCALA, PRIMER_MATRICULA, ACTUALIZADO, CICLO_SEP, PROMEDIO, CREDITOS, FINALIZADO, MAYOR, MENOR" +
					" FROM ENOC.ALUM_PLAN" +
					" WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.CARGA_ALUMNO WHERE CARGA_ID = ? ) "+ orden;
			lista = enocJdbc.query(comando, new AlumPlanMapper(),cargaId);			
			for(AlumPlan plan : lista){				
				mapa.put(plan.getCodigoPersonal(), plan);
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|mapaPlanPorCarga|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,AlumPlan> mapaPorMateria( String cursoCargaId ) {
		
		HashMap<String,AlumPlan> mapa 	= new HashMap<String,AlumPlan>();
		List<AlumPlan> lista			= new ArrayList<AlumPlan>();
		try{
			String comando = "SELECT CODIGO_PERSONAL," +
					" PLAN_ID," +
					" TO_CHAR(F_INICIO,'dd/mm/yyyy') AS F_INICIO," +
					" ESTADO," +
					" ESCUELA_ID," +
					" AVANCE_ID," +
					" PERMISO," +
					" EVENTO," +
					" TO_CHAR(F_GRADUACION,'DD/MM/YYYY') AS F_GRADUACION," +
					" TO_CHAR(F_EGRESO,'DD/MM/YYYY') AS F_EGRESO, GRADO, CICLO," +
					" PRINCIPAL, ESCALA, PRIMER_MATRICULA, ACTUALIZADO, CICLO_SEP, PROMEDIO, CREDITOS, FINALIZADO, MAYOR, MENOR" +
					" FROM ENOC.ALUM_PLAN" +
					" WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.KRDX_CURSO_ACT WHERE CURSO_CARGA_ID = ?)";
			Object[] parametros = new Object[] {cursoCargaId};
			lista = enocJdbc.query(comando, new AlumPlanMapper(), parametros);			
			for(AlumPlan plan : lista){				
				mapa.put(plan.getCodigoPersonal()+plan.getPlanId(), plan);
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|mapaPorMateria|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapAlumCicloSepCarga( String cargaId ) {
		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();
		
		try{
			String comando = " SELECT CODIGO_PERSONAL||PLAN_ID AS LLAVE, CICLO_SEP AS VALOR" 
					+ " FROM ENOC.ALUM_PLAN"
					+ " WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_ESTADO WHERE CARGA_ID IN("+cargaId+") )";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|mapAlumCicloSepCarga|:"+ex);
		}
		
		return mapa;
	}

	public int getMaximoCicloCargas( String cargas) {
		
		int maximo 		= 0;
		String comando	= "";
		try{
			comando = "SELECT COALESCE(MAX(CICLO),0) AS MAXIMO FROM ENOC.INSCRITOS A, ENOC.ALUM_PLAN B"
					+ " WHERE B.CODIGO_PERSONAL = A.CODIGO_PERSONAL"
					+ " AND A.CARGA_ID IN ("+cargas+")";
			if (enocJdbc.queryForObject(comando, Integer.class) >= 1){
				maximo = enocJdbc.queryForObject(comando, Integer.class);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|getMaximoCiclo|:"+ex);
		}
		
		return maximo;
	}
	
	public HashMap<String,AlumPlan> mapAlumPlanActivo( String cargas ) {
		
		HashMap<String,AlumPlan> mapa 	= new HashMap<String,AlumPlan>();
		List<AlumPlan> lista			= new ArrayList<AlumPlan>();
		String comando					= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, " +
					" PLAN_ID," +
					" TO_CHAR(F_INICIO,'dd/mm/yyyy') F_INICIO," +
					" ESTADO," +
					" ESCUELA_ID," +
					" AVANCE_ID," +
					" PERMISO," +
					" EVENTO," +
					" TO_CHAR(F_GRADUACION,'DD/MM/YYYY') AS F_GRADUACION," +
					" TO_CHAR(F_EGRESO,'DD/MM/YYYY') AS F_EGRESO, GRADO, CICLO," +
					" PRINCIPAL, ESCALA, PRIMER_MATRICULA, ACTUALIZADO, CICLO_SEP, PROMEDIO, CREDITOS, FINALIZADO, MAYOR, MENOR" +
					" FROM ENOC.ALUM_PLAN" +
					" WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_ESTADO WHERE CARGA_ID IN ("+cargas+") AND ESTADO = 'I')" +
					" AND ESTADO = '1' ";
			lista = enocJdbc.query(comando, new AlumPlanMapper());
			for(AlumPlan plan : lista){				
				mapa.put(plan.getCodigoPersonal(), plan);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|mapAlumPlanActivo|:"+ex);
		}
		
		return mapa;
	}

	public HashMap<String,AlumPlan> mapAlumPlanActivo() {
		
		HashMap<String,AlumPlan> mapa 	= new HashMap<String,AlumPlan>();
		List<AlumPlan> lista			= new ArrayList<AlumPlan>();
		String comando					= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, " +
					" PLAN_ID," +
					" TO_CHAR(F_INICIO,'dd/mm/yyyy') F_INICIO," +
					" ESTADO," +
					" ESCUELA_ID," +
					" AVANCE_ID," +
					" PERMISO," +
					" EVENTO," +
					" TO_CHAR(F_GRADUACION,'DD/MM/YYYY') AS F_GRADUACION," +
					" TO_CHAR(F_EGRESO,'DD/MM/YYYY') AS F_EGRESO, GRADO, CICLO," +
					" PRINCIPAL, ESCALA, PRIMER_MATRICULA, ACTUALIZADO, CICLO_SEP, PROMEDIO, CREDITOS, FINALIZADO, MAYOR, MENOR" +
					" FROM ENOC.ALUM_PLAN" +
					" WHERE ESTADO = '1' ";
			lista = enocJdbc.query(comando, new AlumPlanMapper());
			for(AlumPlan plan : lista){				
				mapa.put(plan.getCodigoPersonal(), plan);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|mapAlumPlanActivo|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,AlumPlan> mapAlumEnPlan( String planId, String estado ) {
		
		HashMap<String,AlumPlan> mapa = new HashMap<String,AlumPlan>();
		List<AlumPlan> lista		= new ArrayList<AlumPlan>();
		String comando				= "";
		
		
		try{
			comando = "SELECT CODIGO_PERSONAL, " +
					" PLAN_ID," +
					" TO_CHAR(F_INICIO,'dd/mm/yyyy') F_INICIO," +
					" ESTADO," +
					" ESCUELA_ID," +
					" AVANCE_ID," +
					" PERMISO," +
					" EVENTO," +
					" TO_CHAR(F_GRADUACION,'DD/MM/YYYY') AS F_GRADUACION," +
					" TO_CHAR(F_EGRESO,'DD/MM/YYYY') AS F_EGRESO, GRADO, CICLO," +
					" PRINCIPAL, ESCALA, PRIMER_MATRICULA, ACTUALIZADO, CICLO_SEP, PROMEDIO, CREDITOS, FINALIZADO, MAYOR, MENOR" +
					" FROM ENOC.ALUM_PLAN" +
					" WHERE PLAN_ID = ? " +
					" AND ESTADO IN("+estado+")";
			lista = enocJdbc.query(comando, new AlumPlanMapper(),planId);
			for(AlumPlan plan : lista){				
				mapa.put(plan.getCodigoPersonal(), plan);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|mapAlumEnPlan|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,AlumPlan> mapAlumIngreso( String matricula ) {
		
		HashMap<String,AlumPlan> mapa = new HashMap<String,AlumPlan>();
		List<AlumPlan> lista		= new ArrayList<AlumPlan>();	
		String comando				= "";		
		try{
			comando = "SELECT CODIGO_PERSONAL, " +
					" PLAN_ID," +
					" TO_CHAR(F_INICIO,'dd/mm/yyyy') F_INICIO," +
					" ESTADO," +
					" ESCUELA_ID," +
					" AVANCE_ID," +
					" PERMISO," +
					" EVENTO," +
					" TO_CHAR(F_GRADUACION,'DD/MM/YYYY') AS F_GRADUACION," +
					" TO_CHAR(F_EGRESO,'DD/MM/YYYY') AS F_EGRESO, GRADO, CICLO," +
					" PRINCIPAL, ESCALA, PRIMER_MATRICULA, ACTUALIZADO, CICLO_SEP, PROMEDIO, CREDITOS, FINALIZADO, MAYOR " +
					" FROM ENOC.ALUM_PLAN" +
					" WHERE CODIGO_PERSONAL LIKE '"+matricula+"%'" +
					" AND ESTADO = '1' ";
			lista = enocJdbc.query(comando, new AlumPlanMapper());
			for(AlumPlan plan : lista){				
				mapa.put(plan.getCodigoPersonal(), plan);
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|mapAlumIngreso|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapAlumPlanCreditos( String planId ) {
		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();
		String comando				= "";		
		
		try{
			comando = "SELECT" +
					" CODIGO_PERSONAL AS LLAVE, ALUM_CREDITOS_PLAN(CODIGO_PERSONAL, PLAN_ID) AS VALOR" +
					" FROM ENOC.ALUM_PLAN" +
					" WHERE PLAN_ID =  ? " +
					" AND CODIGO_PERSONAL||PLAN_ID IN" +
					"  (SELECT MATRICULA||PLAN_ID FROM MATEO.FES_CCOBRO WHERE MATRICULA = ALUM_PLAN.CODIGO_PERSONAL AND INSCRITO = 'S')";
			lista = enocJdbc.query(comando, new aca.MapaMapper(),planId);
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|mapAlumPlanActivo|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapAlumCargaCreditos( String cargaId ) {
		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();
		String comando				= "";		
		
		try{
			comando = "SELECT" +
					" CODIGO_PERSONAL AS LLAVE, ALUM_CREDITOS_PLAN(CODIGO_PERSONAL, PLAN_ID) AS VALOR" +
					" FROM ENOC.ALUM_PLAN" +
					" WHERE CODIGO_PERSONAL||PLAN_ID IN" +
					"  (SELECT CODIGO_PERSONAL||PLAN_ID FROM ENOC.ESTADISTICA WHERE CARGA_ID = ?)";
			lista = enocJdbc.query(comando, new aca.MapaMapper(),cargaId);
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|mapAlumCargaCreditos|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapAlumPlanCreditosYear(String year) {
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE,  ALUM_CREDITOS_PLAN(CODIGO_PERSONAL, PLAN_ID) AS VALOR FROM ENOC.ALUM_PLAN A"
					+ " WHERE A.F_INICIO BETWEEN TO_DATE('01/01/"+year+"','DD/MM/YYYY') AND TO_DATE('30/06/"+year+"','DD/MM/YYYY')"
					+ " OR (SELECT TO_CHAR(COALESCE(MAX(FECHA),PRIMER_MATRICULA),'DD/MM/YYYY') FROM MATEO.FES_CCOBRO WHERE MATRICULA = A.CODIGO_PERSONAL AND PLAN_ID = A.PLAN_ID AND INSCRITO = 'S')"
					+ " BETWEEN TO_DATE('03/07/"+year+"','DD/MM/YYYY') AND TO_DATE('03/07/"+year+"','DD/MM/YYYY')"
					+ " AND A.CODIGO_PERSONAL||A.PLAN_ID = (SELECT DISTINCT(B.CODIGO_PERSONAL||B.PLAN_ID) FROM ENOC.ALUMNO_CURSO B WHERE (B.NOTA > 0 OR 'S' IN (SELECT C.INSCRITO FROM MATEO.FES_CCOBRO C"
					+ " WHERE C.MATRICULA = A.CODIGO_PERSONAL AND C.PLAN_ID = A.PLAN_ID)) AND A.CODIGO_PERSONAL = B.CODIGO_PERSONAL AND A.PLAN_ID = B.PLAN_ID)";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|mapAlumPlanCreditosYear|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapAlumUltimaInsc( String planId ) {
		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();
		String comando				= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL AS LLAVE, " +
					" (SELECT TO_CHAR(COALESCE(MAX(FECHA),PRIMER_MATRICULA),'DD/MM/YYYY') FROM MATEO.FES_CCOBRO WHERE MATRICULA = AP.CODIGO_PERSONAL AND PLAN_ID = AP.PLAN_ID AND INSCRITO = 'S') AS VALOR" +
					" FROM ENOC.ALUM_PLAN AP" +
					" WHERE PLAN_ID = ? " +
					" AND CODIGO_PERSONAL||PLAN_ID IN" +
					"  (SELECT MATRICULA||PLAN_ID FROM MATEO.FES_CCOBRO WHERE MATRICULA = AP.CODIGO_PERSONAL AND INSCRITO = 'S')";
			lista = enocJdbc.query(comando, new aca.MapaMapper(),planId);
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|mapAlumUltimaInsc|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapAlumCargaUltimaInsc( String cargaId ) {
		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();
		String comando				= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL AS LLAVE, " +
					" (SELECT TO_CHAR(COALESCE(MAX(FECHA),PRIMER_MATRICULA),'DD/MM/YYYY') FROM MATEO.FES_CCOBRO WHERE MATRICULA = AP.CODIGO_PERSONAL AND PLAN_ID = AP.PLAN_ID AND INSCRITO = 'S') AS VALOR" +
					" FROM ENOC.ALUM_PLAN AP" +
					" WHERE CODIGO_PERSONAL||PLAN_ID IN" +
					"  (SELECT CODIGO_PERSONAL||PLAN_ID FROM ENOC.ESTADISTICA WHERE CARGA_ID = ?)";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), cargaId);
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|mapAlumCargaUltimaInsc|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapAlumUltimaInscYear(String year) {
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, (SELECT TO_CHAR(COALESCE(MAX(FECHA),PRIMER_MATRICULA),'DD/MM/YYYY') FROM MATEO.FES_CCOBRO"
					+ " WHERE MATRICULA = A.CODIGO_PERSONAL AND PLAN_ID = A.PLAN_ID AND INSCRITO = 'S') AS VALOR FROM ENOC.ALUM_PLAN A"
					+ " WHERE A.F_INICIO BETWEEN TO_DATE('01/01/"+year+"','DD/MM/YYYY') AND TO_DATE('30/06/"+year+"','DD/MM/YYYY')"
					+ " OR (SELECT TO_CHAR(COALESCE(MAX(FECHA),PRIMER_MATRICULA),'DD/MM/YYYY') FROM MATEO.FES_CCOBRO WHERE MATRICULA = A.CODIGO_PERSONAL AND PLAN_ID = A.PLAN_ID AND INSCRITO = 'S')"
					+ " BETWEEN TO_DATE('03/07/"+year+"','DD/MM/YYYY') AND TO_DATE('03/07/"+year+"','DD/MM/YYYY')"
					+ " AND A.CODIGO_PERSONAL||A.PLAN_ID = (SELECT DISTINCT(B.CODIGO_PERSONAL||B.PLAN_ID) FROM ENOC.ALUMNO_CURSO B WHERE (B.NOTA > 0 OR 'S' IN (SELECT C.INSCRITO FROM MATEO.FES_CCOBRO C"
					+ " WHERE C.MATRICULA = A.CODIGO_PERSONAL AND C.PLAN_ID = A.PLAN_ID)) AND A.CODIGO_PERSONAL = B.CODIGO_PERSONAL AND A.PLAN_ID = B.PLAN_ID)";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|mapAlumUltimaInscYear|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapaAlumnosSalida( String salidaId ) {		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, CARRERA(PLAN_ID) AS VALOR FROM ENOC.ALUM_PLAN"
					+ " WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.SAL_ALUMNO WHERE SALIDA_ID = TO_NUMBER(?,'99999'))"
					+ " AND ESTADO = '1'";
			Object[] parametros = new Object[] {salidaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|mapaAlumnosSalida|:"+ex);
		}		
		return mapa;
	}	
	
	public HashMap<String, AlumPlan> mapaGraduandos( String eventoId ) {		
		HashMap<String,AlumPlan> mapa 	= new HashMap<String,AlumPlan>();
		List<AlumPlan> lista			= new ArrayList<AlumPlan>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, " +
					" PLAN_ID," +
					" TO_CHAR(F_INICIO,'dd/mm/yyyy') AS F_INICIO," +
					" ESTADO," +
					" ESCUELA_ID," +
					" AVANCE_ID," +
					" PERMISO," +
					" EVENTO," +
					" TO_CHAR(F_GRADUACION,'DD/MM/YYYY') AS F_GRADUACION," +
					" TO_CHAR(F_EGRESO,'DD/MM/YYYY') AS F_EGRESO, GRADO, CICLO," +
					" PRINCIPAL, ESCALA, PRIMER_MATRICULA, ACTUALIZADO, CICLO_SEP, PROMEDIO, CREDITOS, FINALIZADO, MAYOR, MENOR" +
					" FROM ENOC.ALUM_PLAN" +
					" WHERE CODIGO_PERSONAL||PLAN_ID IN (SELECT CODIGO_PERSONAL||PLAN_ID FROM ENOC.ALUM_EGRESO WHERE EVENTO_ID = TO_NUMBER(?,'99999'))";
			Object[] parametros = new Object[] {eventoId};
			lista = enocJdbc.query(comando, new AlumPlanMapper(), parametros);
			for(AlumPlan plan : lista){			
				mapa.put(plan.getCodigoPersonal()+plan.getPlanId(), plan);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|eventoId|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String,String> mapaPlanDelInterno( String dormitorioId ) {
		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();
		try{
			String comando = " SELECT CODIGO_PERSONAL AS LLAVE, PLAN_ID AS VALOR FROM ENOC.ALUM_PLAN "
					+ " WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.INT_ALUMNO WHERE DORMITORIO_ID = ?)"
					+ " AND ESTADO = '1'";
			lista = enocJdbc.query(comando, new aca.MapaMapper(),dormitorioId);
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|mapaPlanDelInterno|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapPlanActualEnCovid( String periodoId ) {
		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();
		try{
			String comando = " SELECT CODIGO_PERSONAL AS LLAVE, PLAN_ID AS VALOR FROM ENOC.ALUM_PLAN "
					+ " WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.COVID WHERE PERIODO_ID = ?)"
					+ " AND ESTADO = '1'";
			Object[] parametros = new Object[] {periodoId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|mapPlanActualEnCovid|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,AlumPlan> mapCicloActual( String cargas ) {
		
		HashMap<String,AlumPlan> mapa = new HashMap<String,AlumPlan>();
		List<AlumPlan> lista		= new ArrayList<AlumPlan>();				
		try{
			String comando = "SELECT CODIGO_PERSONAL, " +
					" PLAN_ID," +
					" TO_CHAR(F_INICIO,'dd/mm/yyyy') F_INICIO," +
					" ESTADO," +
					" ESCUELA_ID," +
					" AVANCE_ID," +
					" PERMISO," +
					" EVENTO," +
					" TO_CHAR(F_GRADUACION,'DD/MM/YYYY') AS F_GRADUACION," +
					" TO_CHAR(F_EGRESO,'DD/MM/YYYY') AS F_EGRESO, GRADO, CICLO," +
					" PRINCIPAL, ESCALA, PRIMER_MATRICULA, ACTUALIZADO, CICLO_SEP, PROMEDIO, CREDITOS, FINALIZADO, MAYOR, MENOR " +
					" FROM ENOC.ALUM_PLAN" +
					" WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ESTADISTICA WHERE CARGA_ID IN ("+cargas+"))";				
			lista = enocJdbc.query(comando, new AlumPlanMapper());
			for(AlumPlan plan : lista){				
				mapa.put(plan.getCodigoPersonal()+plan.getPlanId(), plan);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|mapAlumPlanActual|:"+ex);
		}		
		return mapa;
	}
	
	public List<String> listAlumPlanEstudiado( String codigoPersonal) {
		
		List<String> lista 	= new ArrayList<String>();
	
		String comando				= "";		
		try{
			comando = " SELECT PLAN_ID FROM ENOC.ALUM_PLAN WHERE CODIGO_PERSONAL = ? AND PRINCIPAL!= '1'";
			lista = enocJdbc.queryForList(comando, String.class,codigoPersonal);			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|listAlumPlanEstudiado|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String,AlumPlan> mapaPlanesEnCargas( String cargas ) {		
		HashMap<String,AlumPlan> mapa = new HashMap<String,AlumPlan>();
		List<AlumPlan> lista		= new ArrayList<AlumPlan>();	
		String comando				= "";		
		try{
			comando = "SELECT CODIGO_PERSONAL, " +
					" PLAN_ID," +
					" TO_CHAR(F_INICIO,'dd/mm/yyyy') F_INICIO," +
					" ESTADO," +
					" ESCUELA_ID," +
					" AVANCE_ID," +
					" PERMISO," +
					" EVENTO," +
					" TO_CHAR(F_GRADUACION,'DD/MM/YYYY') AS F_GRADUACION," +
					" TO_CHAR(F_EGRESO,'DD/MM/YYYY') AS F_EGRESO, GRADO, CICLO," +
					" PRINCIPAL, ESCALA, PRIMER_MATRICULA, ACTUALIZADO, CICLO_SEP, PROMEDIO, CREDITOS, FINALIZADO, MAYOR, MENOR " +
					" FROM ENOC.ALUM_PLAN" +
					" WHERE CODIGO_PERSONAL||PLAN_ID IN (SELECT CODIGO_PERSONAL||PLAN_ID FROM ENOC.ESTADISTICA WHERE CARGA_ID IN ("+cargas+"))";
			lista = enocJdbc.query(comando, new AlumPlanMapper());
			for(AlumPlan plan : lista){				
				mapa.put(plan.getCodigoPersonal()+plan.getPlanId(), plan);
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|mapaPlanesEnCargas|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,AlumPlan> mapaPlanesEnGraduacion( String eventoId ) {		
		HashMap<String,AlumPlan> mapa = new HashMap<String,AlumPlan>();
		List<AlumPlan> lista		= new ArrayList<AlumPlan>();	
		String comando				= "";		
		try{
			comando = "SELECT CODIGO_PERSONAL, " +
					" PLAN_ID," +
					" TO_CHAR(F_INICIO,'dd/mm/yyyy') AS F_INICIO," +
					" ESTADO," +
					" ESCUELA_ID," +
					" AVANCE_ID," +
					" PERMISO," +
					" EVENTO," +
					" TO_CHAR(F_GRADUACION,'DD/MM/YYYY') AS F_GRADUACION," +
					" TO_CHAR(F_EGRESO,'DD/MM/YYYY') AS F_EGRESO, GRADO, CICLO," +
					" PRINCIPAL, ESCALA, PRIMER_MATRICULA, ACTUALIZADO, CICLO_SEP, PROMEDIO, CREDITOS, FINALIZADO, MAYOR, MENOR " +
					" FROM ENOC.ALUM_PLAN" +
					" WHERE CODIGO_PERSONAL||PLAN_ID IN (SELECT CODIGO_PERSONAL||PLAN_ID FROM ENOC.ALUM_EGRESO WHERE EVENTO_ID = TO_NUMBER(?,'999'))";
			Object[] parametros = new Object[] {eventoId};
			lista = enocJdbc.query(comando, new AlumPlanMapper(), parametros);
			for(AlumPlan plan : lista){				
				mapa.put(plan.getCodigoPersonal()+plan.getPlanId(), plan);
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|mapaPlanesEnGraduacion|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapaCarrerasConCandado( ){	
		HashMap<String,String> mapa 	= new HashMap<String,String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();			
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, ENOC.CARRERA(PLAN_ID) AS VALOR FROM ENOC.ALUM_PLAN"
					+ " WHERE ESTADO = '1'"
					+ " AND CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM CAND_ALUMNO WHERE ESTADO = 'A')";
			//Object[] parametros = new Object[] {eventoId};
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), map.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|mapaCarrerasConCandado|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapaPromediosPlanes( ) {		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();			
		try{
			String comando = "SELECT PLAN_ID AS LLAVE, ROUND(AVG(PROMEDIO),2) AS VALOR FROM ENOC.ALUM_PLAN"
					+ " WHERE PROMEDIO > 0 "
					+ " GROUP BY PLAN_ID";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), map.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|mapaPromediosFacultades|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapaPromediosCarreras( ) {		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();			
		try{
			String comando = "SELECT CARRERA(PLAN_ID) AS LLAVE, ROUND(AVG(PROMEDIO),2) AS VALOR FROM ENOC.ALUM_PLAN"
					+ " WHERE PROMEDIO > 0 "
					+ " GROUP BY CARRERA(PLAN_ID)";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), map.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|mapaPromediosFacultades|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapaPromediosFacultades( ) {		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();			
		try{
			String comando = "SELECT FACULTAD(CARRERA(PLAN_ID)) AS LLAVE, ROUND(AVG(PROMEDIO),2) AS VALOR FROM ENOC.ALUM_PLAN"
					+ " WHERE PROMEDIO > 0 "
					+ " GROUP BY FACULTAD(CARRERA(PLAN_ID))";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), map.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|mapaPromediosFacultades|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapaPlanesCovid( String periodoId ){
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();			
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, PLAN_ID AS VALOR FROM ENOC.ALUM_PLAN"
					+ " WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_COVID WHERE PERIODO_ID = ?)"
					+ " AND ESTADO = '1'";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), periodoId);
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), map.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|mapaPlanesCovid|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapaPlanesConDocumentos( String periodoId ){
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();			
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, PLAN_ID AS VALOR FROM ENOC.ALUM_PLAN"
					+ " WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ALERTA_DOCALUM WHERE PERIODO_ID = ?)"
					+ " AND ESTADO = '1'";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), periodoId);
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), map.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|mapaPlanesCovid|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapaAlumnosEnPlan( ) {		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();			
		try{
			String comando = "SELECT PLAN_ID AS LLAVE, COUNT(CODIGO_PERSONAL) AS VALOR FROM ENOC.ALUM_PLAN"
					+ " WHERE PROMEDIO > 0 "
					+ " GROUP BY PLAN_ID";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), map.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|mapaAlumnosEnPlan|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapaCreditosAcreditados( String cargas) {
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa		= new HashMap<String,String>();
		try{
			String comando = "SELECT CODIGO_PERSONAL||PLAN_ID AS LLAVE, CREDITOS AS VALOR FROM ENOC.ALUM_PLAN" 
					+ " WHERE CODIGO_PERSONAL||PLAN_ID IN" 
					+ " (SELECT DISTINCT(CODIGO_PERSONAL||PLAN_ID) FROM ENOC.ALUM_ESTADO WHERE CARGA_ID IN ("+cargas+") AND ESTADO = 'I')";	
			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map:lista){			
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|mapaCreditosAcreditados|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,String> mapaPrimerMatricula(String cargaId ) {
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL||PLAN_ID AS LLAVE, PRIMER_MATRICULA AS VALOR FROM ALUM_PLAN WHERE CODIGO_PERSONAL||PLAN_ID IN (SELECT CODIGO_PERSONAL||PLAN_ID FROM ESTADISTICA WHERE CARGA_ID = ?)";			
			
			lista = enocJdbc.query(comando, new aca.MapaMapper(),cargaId);
			for (aca.Mapa map:lista){			
				mapa.put(map.getLlave(), (String)map.getValor());
			}		
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|mapaPrimerMatricula|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapaSemestreAlumno(String periodoId, String estado) {
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, CICLO AS VALOR FROM ENOC.ALUM_PLAN WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.MENT_ALUMNO WHERE PERIODO_ID = "+periodoId+") AND ESTADO = ? ";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(),estado);
			for (aca.Mapa map:lista){			
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|mapaSemestreAlumno|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String,String> mapaAtuendos(String eventoId) {
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT DISTINCT(CODIGO_PERSONAL) AS LLAVE, EVENTO AS VALOR FROM ENOC.ALUM_PLAN WHERE ESTADO = 1 AND CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_EGRESO WHERE EVENTO_ID = ?)";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(),eventoId);
			for (aca.Mapa map:lista){			
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|mapaAtuendos|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String,String> mapaPlanesActivos() {
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, PLAN_ID AS VALOR FROM ENOC.ALUM_PLAN WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.INSCRITOS) AND ESTADO = '1'";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map:lista){			
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|mapaPlanesActivos|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String,String> mapaPlanesActivosTodos() {
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, PLAN_ID AS VALOR FROM ENOC.ALUM_PLAN WHERE ESTADO = '1'";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map:lista){			
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|mapaPlanesActivosTodos|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String, AlumPlan> mapaAlumnosEnDisciplina( String periodoId ) {
		HashMap<String,AlumPlan> mapa 	= new HashMap<String,AlumPlan>();
		List<AlumPlan> lista			= new ArrayList<AlumPlan>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, PLAN_ID,"
					+ " TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO,"
					+ " ESTADO,"
					+ " ESCUELA_ID,"
					+ " AVANCE_ID,"
					+ " PERMISO,"
					+ " EVENTO,"
					+ " TO_CHAR(F_GRADUACION,'DD/MM/YYYY') AS F_GRADUACION,"
					+ " TO_CHAR(F_EGRESO,'DD/MM/YYYY') AS F_EGRESO, GRADO, CICLO,"
					+ " PRINCIPAL, ESCALA, PRIMER_MATRICULA, ACTUALIZADO, CICLO_SEP, PROMEDIO, CREDITOS, FINALIZADO, MAYOR, MENOR"
					+ " FROM ENOC.ALUM_PLAN"
					+ " WHERE ESTADO = '1'"
					+ " AND CODIGO_PERSONAL IN (SELECT MATRICULA FROM ENOC.COND_ALUMNO WHERE PERIODO_ID = ?)";
			Object[] parametros = new Object[] {periodoId};
			lista = enocJdbc.query(comando, new AlumPlanMapper(), parametros);
			for(AlumPlan plan : lista){			
				mapa.put(plan.getCodigoPersonal(), plan);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|eventoId|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String, AlumPlan> mapaAlumnosConMentor( String periodoId ) {
		HashMap<String,AlumPlan> mapa 	= new HashMap<String,AlumPlan>();
		List<AlumPlan> lista			= new ArrayList<AlumPlan>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, PLAN_ID,"
					+ " TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO,"
					+ " ESTADO,"
					+ " ESCUELA_ID,"
					+ " AVANCE_ID,"
					+ " PERMISO,"
					+ " EVENTO,"
					+ " TO_CHAR(F_GRADUACION,'DD/MM/YYYY') AS F_GRADUACION,"
					+ " TO_CHAR(F_EGRESO,'DD/MM/YYYY') AS F_EGRESO, GRADO, CICLO,"
					+ " PRINCIPAL, ESCALA, PRIMER_MATRICULA, ACTUALIZADO, CICLO_SEP, PROMEDIO, CREDITOS, FINALIZADO, MAYOR, MENOR"
					+ " FROM ENOC.ALUM_PLAN"
					+ " WHERE ESTADO = '1'"
					+ " AND CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.MENT_ALUMNO WHERE PERIODO_ID = ?)";
			Object[] parametros = new Object[] {periodoId};
			lista = enocJdbc.query(comando, new AlumPlanMapper(), parametros);
			for(AlumPlan plan : lista){			
				mapa.put(plan.getCodigoPersonal(), plan);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|mapaAlumnosConMentor|:"+ex);
		}		
		return mapa;
	}
	
	public boolean editarCiclo(String fecha) {
		boolean ok =  false;
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT CODIGO_PERSONAL||PLAN_UM AS LLAVE, CICLO AS VALOR FROM SEP_ALUMNO WHERE FECHA = TO_DATE(?,'DD/MM/YYYY')";			
			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), fecha);
			for (aca.Mapa map : lista){			
				comando  = "UPDATE ALUM_PLAN SET CICLO_SEP = TO_NUMBER(?,'99') WHERE CODIGO_PERSONAL||PLAN_ID = ? ";
				if (enocJdbc.update(comando, map.getValor(), map.getLlave())==1) {
					ok = true;
				}
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|editarCiclo|:"+ex);
		}
		
		return ok;
	}
	
}