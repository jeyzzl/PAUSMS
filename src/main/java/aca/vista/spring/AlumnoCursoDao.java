// Clase Util para la tabla de Carga
package aca.vista.spring;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import aca.carga.spring.CargaAlumno;
import aca.carga.spring.CargaAlumnoDao;

import java.util.HashMap;

@Component
public class AlumnoCursoDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired
	aca.carga.spring.CargaGrupoDao cargaGrupoDao;	
	
	@Autowired
	aca.kardex.spring.KrdxAlumnoEvalDao krdxAlumnoEvalDao;
	
	@Autowired
	private CargaAlumnoDao cargaAlumnoDao;
	
	public AlumnoCurso mapeaRegId(  String codigoPersonal, String cursoCargaId ) {
		AlumnoCurso alumnoCurso = new AlumnoCurso();
		try{
			String comando = "SELECT "+
				"CODIGO_PERSONAL, "+
				"CARGA_ID, "+
				" BLOQUE_ID, "+
				"CURSO_CARGA_ID, "+
				"CARRERA_ID, "+
				"MODALIDAD_ID, "+
				"PLAN_ID, "+
				"CURSO_ID, "+
				"NOMBRE_CURSO, "+
				"COALESCE(CICLO,'0') AS CICLO, "+
				"COALESCE(TO_CHAR(CREDITOS,'99.99'),'0') AS CREDITOS, "+
				"COALESCE(TO_CHAR(HT,'99'),'0') AS HT, "+
				"COALESCE(TO_CHAR(HP,'99'),'0') AS HP, "+
				"COALESCE(TO_CHAR(NOTA_APROBATORIA,'99'),'0') AS NOTA_APROBATORIA, "+
				"CURSO_ID2, "+
				"NOMBRE_CURSO2, "+
				"COALESCE(TO_CHAR(CREDITOS2,'99.99'),'0') AS CREDITOS2, "+
				"COALESCE(TO_CHAR(HT2,'99'),'0') AS HT2, "+
				"COALESCE(TO_CHAR(HP2,'99'),'0') AS HP2, "+
				"COALESCE(TO_CHAR(NOTA,'999'),'0') AS NOTA, "+
				"TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION, "+
				"TIPOCAL_ID, "+
				"COALESCE(TO_CHAR(NOTA_EXTRA,'999'),'0') AS NOTA_EXTRA, "+
				"TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA, "+
				"CONVALIDACION, "+
				"TITULO, "+
				"TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO, "+
				"GRUPO, "+
				"MAESTRO, "+
				"ESTADO, "+				
				"CORRECCION," +
				"OPTATIVA," +
				"NOTA_CONVA "+
				"FROM ENOC.ALUMNO_CURSO "+
				"WHERE CODIGO_PERSONAL = ? "+
				"AND CURSO_CARGA_ID = ? ";
			
				Object[] parametros = new Object[] {codigoPersonal,cursoCargaId};
				alumnoCurso = enocJdbc.queryForObject(comando, new AlumnoCursoMapper(), parametros);
		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|mapeaRegId|:"+ex);
		}
		return alumnoCurso;
	}
	
	public AlumnoCurso mapeaCursoAprobado(  String codigoPersonal, String cursoId ) {
		AlumnoCurso alumnoCurso = new AlumnoCurso();
		try{
			String comando = "SELECT "+
					"CODIGO_PERSONAL, "+
					"CARGA_ID, "+
					" BLOQUE_ID, "+
					"CURSO_CARGA_ID, "+
					"CARRERA_ID, "+
					"MODALIDAD_ID, "+
					"PLAN_ID, "+
					"CURSO_ID, "+
					"NOMBRE_CURSO, "+
					"COALESCE(CICLO,'0') AS CICLO, "+
					"COALESCE(TO_CHAR(CREDITOS,'99.99'),'0') AS CREDITOS, "+
					"COALESCE(TO_CHAR(HT,'99'),'0') AS HT, "+
					"COALESCE(TO_CHAR(HP,'99'),'0') AS HP, "+
					"COALESCE(TO_CHAR(NOTA_APROBATORIA,'99'),'0') AS NOTA_APROBATORIA, "+
					"CURSO_ID2, "+
					"NOMBRE_CURSO2, "+
					"COALESCE(TO_CHAR(CREDITOS2,'99.99'),'0') AS CREDITOS2, "+
					"COALESCE(TO_CHAR(HT2,'99'),'0') AS HT2, "+
					"COALESCE(TO_CHAR(HP2,'99'),'0') AS HP2, "+
					"COALESCE(TO_CHAR(NOTA,'999'),'0') AS NOTA, "+
					"TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION, "+
					"TIPOCAL_ID, "+
					"COALESCE(TO_CHAR(NOTA_EXTRA,'999'),'0') AS NOTA_EXTRA, "+
					"TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA, "+
					"CONVALIDACION, "+
					"TITULO, "+
					"TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO, "+
					"GRUPO, "+
					"MAESTRO, "+
					"ESTADO, "+					
					"CORRECCION," +
					"OPTATIVA," +
					"NOTA_CONVA "+
					"FROM ENOC.ALUMNO_CURSO "+
					"WHERE CODIGO_PERSONAL = ? "+
					"AND CURSO_ID = ? "+			
					" AND TIPOCAL_ID = '1'";
			
			Object[] parametros = new Object[] {codigoPersonal,cursoId};
			alumnoCurso = enocJdbc.queryForObject(comando, new AlumnoCursoMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|mapeaCursoAprobado|:"+ex);
		}
		return alumnoCurso;
	}
	
	public boolean existeReg( String codigoPersonal, String cursoCargaId) {
		boolean 			ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ALUMNO_CURSO "
					+ " WHERE CODIGO_PERSONAL = ? "
					+ " AND CURSO_CARGA_ID = ? ";
			
				Object[] parametros = new Object[] {codigoPersonal,cursoCargaId};
				if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
					ok = true;
				}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public boolean tieneCurso( String codigoPersonal, String cursoId, String tipoCalId) {
		boolean 			ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ALUMNO_CURSO"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND CURSO_ID = ?"
					+ " AND TIPOCAL_ID = ?";
			
			Object[] parametros = new Object[] {codigoPersonal,cursoId,tipoCalId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|tieneCurso|:"+ex);
		}
		return ok;
	}
	
	public int totMaterias( String codigoPersonal, String cargaId, String bloqueId) {
		int total = 0;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ALUMNO_CURSO"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND CURSO_ID = ?"
					+ " AND BLOQUE_ID = TO_NUMBER(?,'99')";	
			Object[] parametros = new Object[] {codigoPersonal,cargaId,bloqueId};			
			total = enocJdbc.queryForObject(comando, Integer.class,parametros);	
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|totMaterias|:"+ex);
		}
		return total;
	}
	
	public boolean existeCodigoPersonal( String codigoPersonal){
		boolean 			ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = ? ";
			if (enocJdbc.queryForObject(comando,Integer.class,codigoPersonal)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|existeCodigoPersonal|:"+ex);
		}
		return ok;
	}	

	// Considera para el calculo notas AC
	public double promedioAcreditadoEnCarga( String codigoPersonal, String cargaId) {
		double	promedio 		= -1;	
		try{
			
			String comando = "SELECT ENOC.ALUM_PROMEDIO_CARGA_AC(?, ?) FROM DUAL";					
			Object[] parametros = new Object[] {codigoPersonal, cargaId};		 
			promedio = enocJdbc.queryForObject(comando,Float.class,parametros);			
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDaoDao|promedioAcreditadoEnCarga|:"+ex);
		}
		return promedio;
	}
	
	// Obtiene el promedio de un alumno en una carga (Omite componentes y remediales).
	// Considera para el calculo notas AC, NA y RA.
	public double promedioAlumnoCarga( String codigoPersonal, String cargaId) {
		
		List<AlumnoCurso> lista = new ArrayList<AlumnoCurso>();
		double	promedio 		= -1;
		float		creditos	= 0;
		int 	nota			= 0;
		int		notaExtra		= 0;
		int		notaAC			= 0;
		String 	tipocalId		= "";
		String cursoCargaId		= "";
		String estado 			= ""; 
		
		float sumaCreditos		= 0;
		float sumaNotasXCreditos	= 0;
		
		try{
			
			String comando = "SELECT "+
					"CODIGO_PERSONAL, "+
					"CARGA_ID, "+
					" BLOQUE_ID, "+
					"CURSO_CARGA_ID, "+
					"CARRERA_ID, "+
					"MODALIDAD_ID, "+
					"PLAN_ID, "+
					"CURSO_ID, "+
					"NOMBRE_CURSO, "+
					"COALESCE(CICLO,'0') AS CICLO, "+
					"COALESCE(TO_CHAR(CREDITOS,'99.99'),'0') AS CREDITOS, "+
					"COALESCE(TO_CHAR(HT,'99'),'0') AS HT, "+
					"COALESCE(TO_CHAR(HP,'99'),'0') AS HP, "+
					"COALESCE(TO_CHAR(NOTA_APROBATORIA,'99'),'0') AS NOTA_APROBATORIA, "+
					"CURSO_ID2, "+
					"NOMBRE_CURSO2, "+
					"COALESCE(TO_CHAR(CREDITOS2,'99.99'),'0') AS CREDITOS2, "+
					"COALESCE(TO_CHAR(HT2,'99'),'0') AS HT2, "+
					"COALESCE(TO_CHAR(HP2,'99'),'0') AS HP2, "+
					"COALESCE(TO_CHAR(NOTA,'999'),'0') AS NOTA, "+
					"TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION, "+
					"TIPOCAL_ID, "+
					"COALESCE(TO_CHAR(NOTA_EXTRA,'999'),'0') AS NOTA_EXTRA, "+
					"TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA, "+
					"CONVALIDACION, "+
					"TITULO, "+
					"TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO, "+
					"GRUPO, "+
					"MAESTRO, "+
					"ESTADO, "+				
					"CORRECCION," +
					"OPTATIVA," +
					"NOTA_CONVA "+
					"FROM ENOC.ALUMNO_CURSO "+
					"WHERE CODIGO_PERSONAL = ? "+
					"AND CARGA_ID = ? "+
					"AND TIPOCAL_ID IN ('1','2','4','7','I') "+
					"AND CREDITOS > 0";
			Object[] parametros = new Object[] {codigoPersonal, cargaId};
			lista = enocJdbc.query(comando, new AlumnoCursoMapper(), parametros);
			
			for(AlumnoCurso alumCurso : lista){
				
				cursoCargaId	= alumCurso.getCursoCargaId();
				creditos		= Float.valueOf(alumCurso.getCreditos());
				nota 			= Integer.parseInt(alumCurso.getNota());
				notaExtra		= Integer.parseInt(alumCurso.getNotaExtra());				
				tipocalId		= alumCurso.getTipoCalId();
				notaAC 			= Integer.parseInt(alumCurso.getNotaAprobatoria());	
				estado 			= cargaGrupoDao.getEstado(cursoCargaId);
				
				// if la materia no esta cerrada calcula la nota de las estrategias
				if( estado.equals("0") || estado.equals("1") || estado.equals("2")){
					nota = Integer.parseInt(String.valueOf(krdxAlumnoEvalDao.getAlumnoPromedio(cursoCargaId, codigoPersonal)));
					if (nota!=0){
						sumaCreditos += creditos;
						sumaNotasXCreditos	+= nota*creditos;
					}
					
				}else{
					sumaCreditos += creditos;
					if ( nota < notaAC && tipocalId.equals("1") ){
						sumaNotasXCreditos	+= notaExtra*creditos;
					}else if (tipocalId.equals("2") ){
						if (notaExtra!=0)
							sumaNotasXCreditos	+= notaExtra*creditos;
						else 
							sumaNotasXCreditos	+= nota*creditos;
					}else{
						sumaNotasXCreditos	+= nota*creditos;
					}
					
				}				
				
			}
			
			if (sumaCreditos>0 && sumaNotasXCreditos>0){ 
				promedio = (double) sumaNotasXCreditos/sumaCreditos;
			}		
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDaoDao|promedioAlumnoCarga|:"+ex);
		}
		return promedio;
	}
	
	// Obtiene el promedio de un alumno en una carga (Omite componentes y remediales). 
	public double promedioAlumnoCarga( String codigoPersonal, String cargaId, String tipoCal){
		
		List<AlumnoCurso> lista = new ArrayList<AlumnoCurso>();
		double	promedio 		= -1;
		float		creditos	= 0;
		int 	nota			= 0;
		int		notaExtra		= 0;
		int		notaAC			= 0;
		String 	tipocalId		= "";
		String cursoCargaId		= "";
		String estado 			= ""; 
		
		float sumaCreditos		= 0;
		float sumaNotasXCreditos	= 0;
		
		try{	
			
			String comando = "SELECT "+
					"CODIGO_PERSONAL, "+
					"CARGA_ID, "+
					" BLOQUE_ID, "+
					"CURSO_CARGA_ID, "+
					"CARRERA_ID, "+
					"MODALIDAD_ID, "+
					"PLAN_ID, "+
					"CURSO_ID, "+
					"NOMBRE_CURSO, "+
					"COALESCE(CICLO,'0') AS CICLO, "+
					"COALESCE(TO_CHAR(CREDITOS,'99.99'),'0') AS CREDITOS, "+
					"COALESCE(TO_CHAR(HT,'99'),'0') AS HT, "+
					"COALESCE(TO_CHAR(HP,'99'),'0') AS HP, "+
					"COALESCE(TO_CHAR(NOTA_APROBATORIA,'99'),'0') AS NOTA_APROBATORIA, "+
					"CURSO_ID2, "+
					"NOMBRE_CURSO2, "+
					"COALESCE(TO_CHAR(CREDITOS2,'99.99'),'0') AS CREDITOS2, "+
					"COALESCE(TO_CHAR(HT2,'99'),'0') AS HT2, "+
					"COALESCE(TO_CHAR(HP2,'99'),'0') AS HP2, "+
					"COALESCE(TO_CHAR(NOTA,'999'),'0') AS NOTA, "+
					"TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION, "+
					"TIPOCAL_ID, "+
					"COALESCE(TO_CHAR(NOTA_EXTRA,'999'),'0') AS NOTA_EXTRA, "+
					"TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA, "+
					"CONVALIDACION, "+
					"TITULO, "+
					"TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO, "+
					"GRUPO, "+
					"MAESTRO, "+
					"ESTADO, "+				
					"CORRECCION," +
					"OPTATIVA," +
					"NOTA_CONVA "+
					"FROM ENOC.ALUMNO_CURSO "+
					"WHERE CODIGO_PERSONAL = ? "+
					"AND CARGA_ID = ? "+
					"AND TIPOCAL_ID IN ('1','2','4','7','I') "+
					"AND CREDITOS > 0";
			Object[] parametros = new Object[] {codigoPersonal, cargaId};
			lista = enocJdbc.query(comando, new AlumnoCursoMapper(), parametros);
			
			for(AlumnoCurso alumCurso : lista){
				
				cursoCargaId	= alumCurso.getCursoCargaId();
				creditos		= Float.valueOf(alumCurso.getCreditos());
				nota 			= Integer.parseInt(alumCurso.getNota());
				notaExtra		= Integer.parseInt(alumCurso.getNotaExtra());				
				tipocalId		= alumCurso.getTipoCalId();
				notaAC 			= Integer.parseInt(alumCurso.getNotaAprobatoria());
				estado 			= cargaGrupoDao.getEstado(cursoCargaId);
				
				// if la materia no esta cerrada calcula la nota de las estrategias
				if( estado.equals("0") || estado.equals("1") || estado.equals("2")){
					nota = Integer.parseInt(String.valueOf(krdxAlumnoEvalDao.getAlumnoPromedio(cursoCargaId, codigoPersonal)));
					if (nota!=0){
						sumaCreditos += creditos;
						sumaNotasXCreditos	+= nota*creditos;
					}
					
				}else{
					sumaCreditos += creditos;
					if ( nota < notaAC && tipocalId.equals("1") ){
						sumaNotasXCreditos	+= notaExtra*creditos;
					}else if (tipocalId.equals("2") ){
						if (notaExtra!=0)
							sumaNotasXCreditos	+= notaExtra*creditos;
						else 
							sumaNotasXCreditos	+= nota*creditos;
					}else{
						sumaNotasXCreditos	+= nota*creditos;
					}
					
				}				
				
			}
			
			if (sumaCreditos>0 && sumaNotasXCreditos>0){ 
				promedio = (double) sumaNotasXCreditos/sumaCreditos;
			}		
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|promedioAlumnoCarga|:"+ex);
		}
		
		return promedio;
	}
	
//	Obtiene el promedio de un alumno en el plan de estudios (Omite componentes y remediales).
//  Incluye solo materias aprobadas.. 	
	public double promedioAlumno( String codigoPersonal, String planId) {	
		
		List<AlumnoCurso> lista = new ArrayList<AlumnoCurso>();	
		double	promedio 		= 0;
		float	creditos		= 0;
		int 	nota			= 0;
		int		notaExtra		= 0;
		int		notaAC			= 0;
		
		float sumaCreditos		= 0;
		int sumaNotasXCreditos	= 0;
		
		try{
			
			String comando = "SELECT "+
					"CODIGO_PERSONAL, "+
					"CARGA_ID, "+
					" BLOQUE_ID, "+
					"CURSO_CARGA_ID, "+
					"CARRERA_ID, "+
					"MODALIDAD_ID, "+
					"PLAN_ID, "+
					"CURSO_ID, "+
					"NOMBRE_CURSO, "+
					"COALESCE(CICLO,'0') AS CICLO, "+
					"COALESCE(TO_CHAR(CREDITOS,'99.99'),'0') AS CREDITOS, "+
					"COALESCE(TO_CHAR(HT,'99'),'0') AS HT, "+
					"COALESCE(TO_CHAR(HP,'99'),'0') AS HP, "+
					"COALESCE(TO_CHAR(NOTA_APROBATORIA,'99'),'0') AS NOTA_APROBATORIA, "+
					"CURSO_ID2, "+
					"NOMBRE_CURSO2, "+
					"COALESCE(TO_CHAR(CREDITOS2,'99.99'),'0') AS CREDITOS2, "+
					"COALESCE(TO_CHAR(HT2,'99'),'0') AS HT2, "+
					"COALESCE(TO_CHAR(HP2,'99'),'0') AS HP2, "+
					"COALESCE(TO_CHAR(NOTA,'999'),'0') AS NOTA, "+
					"TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION, "+
					"TIPOCAL_ID, "+
					"COALESCE(TO_CHAR(NOTA_EXTRA,'999'),'0') AS NOTA_EXTRA, "+
					"TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA, "+
					"CONVALIDACION, "+
					"TITULO, "+
					"TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO, "+
					"GRUPO, "+
					"MAESTRO, "+
					"ESTADO, "+				
					"CORRECCION," +
					"OPTATIVA," +
					"NOTA_CONVA "+
					"FROM ENOC.ALUMNO_CURSO "+
					"WHERE CODIGO_PERSONAL = ? "+
					"AND PLAN_ID = ? "+
					"AND ENOC.TIPOCURSO_ID(CURSO_ID) IN (1,2,7) "+
					"AND TIPOCAL_ID = '1' "+
					"AND CONVALIDACION IN ('N','I') "+
					"AND CREDITOS > 0";		
			Object[] parametros = new Object[] {codigoPersonal, planId};
			lista = enocJdbc.query(comando, new AlumnoCursoMapper(), parametros);
			
			for(AlumnoCurso alumCurso : lista){			
				
				creditos		= Float.valueOf(alumCurso.getCreditos());
				nota 			= Integer.parseInt(alumCurso.getNota());
				notaExtra		= Integer.parseInt(alumCurso.getNotaExtra());				
				notaAC 			= Integer.parseInt(alumCurso.getNotaAprobatoria());			
				
				sumaCreditos += creditos;
				
				if ( nota < notaAC ){					 
					sumaNotasXCreditos	+= notaExtra*creditos;
				}else{
					sumaNotasXCreditos	+= nota*creditos;
				}	
			}
			if (sumaCreditos>0 && sumaNotasXCreditos>0){ 
				promedio = (double) sumaNotasXCreditos/sumaCreditos;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|promedioAlumno|:"+ex); 
		}
		return promedio;
	}

	public float gpaAlumno( String codigoPersonal, String planId) {	
		
		List<AlumnoCurso> lista = new ArrayList<AlumnoCurso>();	
		float sumaCreditos 			= 0;
		float sumaCreditosPorNota 	= 0;
		String gradePointValue 		= "";

		float	promedio 		= 0;
		
		HashMap<String,String> mapaGradePoint = mapaGradePoint(codigoPersonal, planId);
	
		try{
			
			String comando = "SELECT *"+
					"FROM ENOC.ALUMNO_CURSO "+
					"WHERE CODIGO_PERSONAL = ? "+
					"AND PLAN_ID = ? "+
					"AND ENOC.TIPOCURSO_ID(CURSO_ID) IN (1,2,7) "+
					"AND TIPOCAL_ID = '1' "+
					"AND CONVALIDACION IN ('N','I') "+
					"AND CREDITOS > 0";		
			Object[] parametros = new Object[] {codigoPersonal, planId};
			lista = enocJdbc.query(comando, new AlumnoCursoMapper(), parametros);
			
			for(AlumnoCurso alumCurso : lista){					
				if (mapaGradePoint.containsKey(alumCurso.getNota().trim())){
					gradePointValue 	=  mapaGradePoint.get(alumCurso.getNota().trim()).split(";")[1];
				}else{
					gradePointValue		= "0";
				}
				float notaGPA 			= Float.parseFloat(gradePointValue);
				float extra 			= Float.parseFloat(alumCurso.getNotaExtra());
				float extraGPA 			= extra * (float) 0.04;
				float creditos 			= Float.parseFloat(alumCurso.getCreditos());
				float creditosPorNota 	= extra==0?(notaGPA*creditos):extraGPA*creditos;		
				
				sumaCreditos+=creditos;
				sumaCreditosPorNota+=creditosPorNota;	
			}
			if (sumaCreditos>0 && sumaCreditosPorNota>0){ 
				promedio = (float) sumaCreditosPorNota/sumaCreditos;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|gpaAlumno|:"+ex); 
		}
		return promedio;
	}
	
	public float creditosEnPlan(  String codigoPersonal, String planId, String tipoCalId) {
		float nCreditos			= 0;
		
		try{
			String comando = "SELECT "+
				" COALESCE(SUM(CREDITOS),0) AS CREDITOS FROM ENOC.ALUMNO_CURSO "+
				"WHERE CODIGO_PERSONAL = ? "+
				"AND PLAN_ID = ? "+
				"AND TIPOCAL_ID = '1'";
			
			Object[] parametros = new Object[] {planId};
			nCreditos = enocJdbc.queryForObject(comando, Integer.class, parametros);
		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|creditosAprobados|:"+ex);
		}
		return nCreditos;
	}
	
	public float creditosAprobados(  String codigoPersonal, String planId) {
		float nCreditos			= 0;
		
		try{
			String comando = "SELECT COALESCE(SUM(CREDITOS),0) AS CREDITOS FROM ENOC.ALUMNO_CURSO"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND PLAN_ID = ?"
					+ " AND TIPOCAL_ID = '1'";			
			Object[] parametros = new Object[] {codigoPersonal, planId};
			nCreditos = enocJdbc.queryForObject(comando, Integer.class, parametros);
		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|creditosAprobados|:"+ex);
		}
		return nCreditos;
	}	
	
	public int materiasAlumno(  String codigoPersonal, String planId, String tipoCurso) {
		int nMaterias			= 0;
		
		try{
			String comando = "SELECT COUNT(*) AS MATERIAS " +
				"FROM ENOC.ALUMNO_CURSO "+
				"WHERE CODIGO_PERSONAL = ? "+
				"AND PLAN_ID = ? "+
				"AND ENOC.TIPOCURSO_ID(CURSO_ID) = TO_NUMBER(?,'99') "+
				"AND TIPOCAL_ID = '1'";
			
			Object[] parametros = new Object[] {codigoPersonal,planId,tipoCurso};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				nMaterias = enocJdbc.queryForObject(comando, Integer.class, parametros);
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|materiasAlumno|:"+ex);
		}
		return nMaterias;
	}
	
	public int matAlumReprobadas(  String codigoPersonal, String planId, String tipoCurso) {
		int nMaterias			= 0;
		
		try{
			String comando = "SELECT COUNT(*) AS MATERIAS " +
				"FROM ENOC.ALUMNO_CURSO "+
				"WHERE CODIGO_PERSONAL = ? "+
				"AND PLAN_ID = ? "+
				"AND ENOC.TIPOCURSO_ID(CURSO_ID) = TO_NUMBER(?,'99') "+
				"AND TIPOCAL_ID IN ('2','4')";
			
			Object[] parametros = new Object[] {codigoPersonal,planId,tipoCurso};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				nMaterias = enocJdbc.queryForObject(comando, Integer.class, parametros);
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|materiasAlumno|:"+ex);
		}
		return nMaterias;
	}
	
	public int obtenTipoCurso(  String cursoId) {		
		int tipoCurso			= 0;
		
		try{
			String comando = "select tipocurso_id from ENOC.mapa_curso where curso_id=?"; 
		
			Object[] parametros = new Object[] {cursoId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				tipoCurso = enocJdbc.queryForObject(comando, Integer.class, parametros);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|obtenTipoCurso|:"+ex);
		}
		return tipoCurso;
	}
	
	public HashMap<String,String> mapTipoCurso(String planId) {		
		HashMap<String,String> mapa	= new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT CURSO_ID AS LLAVE, TIPOCURSO_ID AS VALOR FROM ENOC.MAPA_CURSO WHERE PLAN_ID = ?"; 
		
			Object[] parametros = new Object[] {planId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			
			for (aca.Mapa map: lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|mapTipoCurso|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,String> mapTipoCursoAlumno(String codigoPersonal) {		
		HashMap<String,String> mapa	= new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT CURSO_ID AS LLAVE, TIPOCURSO_ID AS VALOR FROM ENOC.MAPA_CURSO WHERE PLAN_ID IN (SELECT PLAN_ID FROM ENOC.ALUM_PLAN WHERE CODIGO_PERSONAL = ?)";
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			
			for (aca.Mapa map: lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|mapTipoCurso|:"+ex);
		}
		return mapa;
	}
	
	public boolean yaLlevoLaMateria(  String codigoPersonal, String cursoId) {
		boolean ok				= false;
		
		try{
			String comando = "SELECT COUNT(CODIGO_PERSONAL) FROM ENOC.ALUMNO_CURSO" +
								" WHERE CODIGO_PERSONAL = ?" +
								" AND CURSO_ID = ?" +
								" AND TIPOCAL_ID IN ('2','3','4','5','6')";
			Object[] parametros = new Object[] {codigoPersonal,cursoId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {					
				ok = true;
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|yaLlevoLaMateria|:"+ex);
		}
		return ok;
	}
	
	public boolean esMateriaAc(  String codigoPersonal, String cursoId) {
		boolean ok				= false;		
		try{
			String comando = "SELECT COUNT(CODIGO_PERSONAL) FROM ENOC.ALUMNO_CURSO" +
										" WHERE CODIGO_PERSONAL = ?" +
										" AND CURSO_ID = ?" +
										" AND TIPOCAL_ID IN ('1')";
			Object[] parametros = new Object[] {codigoPersonal,cursoId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {					
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|esMateriaAc|:"+ex);
		}
		return ok;
	}
	
	public float numCredOptaCiclo(  String codigoPersonal, String planId, String ciclo) {
		float creditos			= 0;		
		try{
			String comando = "SELECT COALESCE(SUM(CREDITOS),0) AS CRED FROM ENOC.ALUMNO_CURSO"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND PLAN_ID = ?"
					+ " AND CICLO = ?"
					+ " AND TIPOCAL_ID IN ('I','1')"
					+ " AND ENOC.TIPOCURSO_ID(CURSO_ID) IN (2,7)";
			Object[] parametros = new Object[] {codigoPersonal, planId, ciclo};
			creditos = enocJdbc.queryForObject(comando, Integer.class, parametros);		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|numCredOptaCiclo|:"+ex);
		}
		return creditos;
	}
	
	public float getCreditosPorTipoCurso(  String codigoPersonal, String planId, String tiposCal, String tiposCurso) {
		float nCreditos			= 0;
		
		try{
			String comando = "SELECT COALESCE(SUM(CREDITOS),0)"
					+ " FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = ?"
					+ " AND TRIM(PLAN_ID) = ? AND TIPOCAL_ID IN ("+tiposCal+")"
					+ " AND CURSO_ID IN (SELECT CURSO_ID FROM ENOC.MAPA_CURSO WHERE TRIM(PLAN_ID) = ? AND TIPOCURSO_ID IN ("+tiposCurso+"))";			
					Object[] parametros = new Object[] {planId};
					nCreditos = enocJdbc.queryForObject(comando, Integer.class, parametros);		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|getCreditosPorTipoCurso|:"+ex);
		}
		return nCreditos;
	}
	
	public float getCrObliAC(  String codigoPersonal, String planId) {
		float nCreditos			= 0;
		
		try{
			String comando = "SELECT COALESCE(SUM(CREDITOS),0) FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = ?"
					+ " AND PLAN_ID = ? AND TIPOCAL_ID = '1'"
					+ " AND CURSO_ID IN (SELECT CURSO_ID FROM ENOC.MAPA_CURSO WHERE PLAN_ID = ? AND TIPOCURSO_ID IN (1,5,8))";			
					Object[] parametros = new Object[] {codigoPersonal,planId,planId};
					nCreditos = enocJdbc.queryForObject(comando, Float.class, parametros);
		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|getCrObliAC|:"+ex);
		}
		return nCreditos;
	}
	
	public float getCrElecAC(  String codigoPersonal, String planId) {
		float nCreditos			= 0;
		
		try{
			String comando = "SELECT COALESCE(SUM(CREDITOS),0)"
					+ " FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = ?"
					+ " AND PLAN_ID = ? AND TIPOCAL_ID = '1'"
					+ " AND CURSO_ID IN (SELECT CURSO_ID FROM ENOC.MAPA_CURSO WHERE PLAN_ID = ? AND TIPOCURSO_ID IN (2,7))";			
			Object[] parametros = new Object[] {codigoPersonal,planId,planId};
			nCreditos = enocJdbc.queryForObject(comando, Float.class, parametros);
		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|getCrElecAC|:"+ex);
		}
		return nCreditos;
	}
	
	public float getCreditosActuales(  String codigoPersonal, String planId) {
		float nCreditos			= 0;
		
		try{
			String comando = "SELECT "
					+ " COALESCE(SUM(CREDITOS),0) AS CREDITOS FROM ENOC.ALUMNO_CURSO"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND PLAN_ID = ? AND TIPOCAL_ID = 'I'";

				Object[] parametros = new Object[] {planId};
				nCreditos = enocJdbc.queryForObject(comando, Integer.class, parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|getCreditosActuales|:"+ex);
		}		
		return nCreditos;
	}
	
	public float getCrElecActuales(  String codigoPersonal, String planId) {
		float nCreditos			= 0;
		
		try{
			String comando = "SELECT COALESCE(SUM(CREDITOS),0) AS CREDITOS"
					+ " FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = ? "
					+ " AND PLAN_ID = ? AND TIPOCAL_ID = 'I' "
					+ " AND CURSO_ID IN (SELECT CURSO_ID FROM ENOC.MAPA_CURSO "
					+ " WHERE PLAN_ID = ? AND TIPOCURSO_ID IN (2,7))";

			Object[] parametros = new Object[] {planId};
			nCreditos = enocJdbc.queryForObject(comando, Integer.class, parametros);		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|getCrElecActuales|:"+ex);
		}
		return nCreditos;
	}
		
	public float getCrObliActuales(  String codigoPersonal, String planId) {
		float nCreditos			= 0;
		
		try{
			String comando = "SELECT " +
					" COALESCE(SUM(CREDITOS),0) AS CREDITOS " +
					"FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = ? " +
					"AND PLAN_ID = ? AND TIPOCAL_ID = 'I' " +
					"AND CURSO_ID IN (SELECT CURSO_ID FROM ENOC.MAPA_CURSO " + 
					"WHERE PLAN_ID = ? AND TIPOCURSO_ID IN (1,5))";

					Object[] parametros = new Object[] {planId};
					nCreditos = enocJdbc.queryForObject(comando, Integer.class, parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|getCrObliActuales|:"+ex);
		}
		return nCreditos;
	}
	
	public int getComponentesAct(  String codigoPersonal, String planId) {
		int nCreditos			= 0;
		try{
			String comando = "SELECT " +
					"COUNT(CURSO_ID)AS COMPONENTES " +
					"FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = ? " +
					"AND PLAN_ID = ? AND TIPOCAL_ID = 'I' " +
					"AND CURSO_ID IN (SELECT CURSO_ID FROM ENOC.MAPA_CURSO " + 
					"WHERE PLAN_ID = ? AND TIPOCURSO_ID = '3')";
			
			Object[] parametros = new Object[] {codigoPersonal,planId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				nCreditos = enocJdbc.queryForObject(comando, Integer.class, parametros);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|getComponentesAct|:"+ex);
		}
		return nCreditos;
	}
	
	public float creditosDelCiclo(  String codigoPersonal, String cargaId) {
		float creditos			= 0;
		
		try{
			String comando = "SELECT COALESCE(SUM(CREDITOS),0) AS CREDITOS FROM ENOC.ALUMNO_CURSO" +
					" WHERE CODIGO_PERSONAL = ?" +
					" AND CARGA_ID LIKE ?||'%'" +
					" AND TIPOCAL_ID IN ('1', '5', '6', 'I', 'M')";
			
			Object[] parametros = new Object[] {codigoPersonal,cargaId};
			creditos = enocJdbc.queryForObject(comando, Float.class, parametros);
		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|terminoCiclo|:"+ex);
		}
		return creditos;
	}

	public float getCreditos(String codigoPersonal, String cargaId, String tipoCal) {
		float creditos = 0;
		
		try{
			String comando = "SELECT COALESCE(SUM(CREDITOS),0) FROM ALUMNO_CURSO WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ? AND TIPOCAL_ID IN (?)";			
			Object[] parametros = new Object[] {codigoPersonal,cargaId,tipoCal};
			creditos = enocJdbc.queryForObject(comando, Float.class, parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|getCreditos|:"+ex);
		}
		return creditos;
	}
	
	public boolean terminoCiclo(  String codigoPersonal, String cargaId, float creditos) {		
		boolean ok				= false;
		
		try{
			String comando = "SELECT SUM(CREDITOS) AS CREDITOS FROM ENOC.ALUMNO_CURSO" +
					" WHERE CODIGO_PERSONAL = ?" +
					" AND CARGA_ID LIKE ?||'%'" +
					" AND TIPOCAL_ID IN ('1', '5', '6', 'I', 'M')";
			
			Object[] parametros = new Object[] {codigoPersonal,cargaId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=creditos){
				ok = true;
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|terminoCiclo|:"+ex);
		}
		return ok;
	}
	
	public float getCreditosPermitidosXPeriodo( String codigoPersonal, String cargaId) {		
		float creditos			= -1;
		
		try{
			String comando = "SELECT COUNT(DISTINCT(CARGA_ID)) * CASE ENOC.ALUMNO_FAC_ID(?) WHEN '107' THEN 59 ELSE 44 END AS CREDITOS"
					+ " FROM ENOC.ALUMNO_CURSO"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND CARGA_ID LIKE ?||'%'"
					+ " AND TIPOCAL_ID IN ('1', '5', '6', 'I', 'M')"
					+ " AND CARGA_ID IN (SELECT CARGA_ID FROM ENOC.CARGA WHERE TIPOCARGA = 'O')"; 
			
			Object[] parametros = new Object[] {codigoPersonal,cargaId};
			creditos = enocJdbc.queryForObject(comando, Integer.class, parametros);
		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|getCreditosPermitidosXPeriodo|:"+ex);
		}
		return creditos;
	}
	
	public int getNumExtras( String codigoPersonal, String planId) {
		int numExtras			= 0;
		
		try{
			String comando = "SELECT COUNT(CODIGO_PERSONAL) AS EXTRAS FROM ENOC.ALUMNO_CURSO" +
					" WHERE CODIGO_PERSONAL = ?" +
					" AND PLAN_ID = ?" +
					" AND (NOTA BETWEEN NOTA_APROBATORIA-10 AND NOTA_APROBATORIA-1)"+
					" AND NOTA_EXTRA > 0";
			Object[] parametros = new Object[] {codigoPersonal,planId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				numExtras = enocJdbc.queryForObject(comando, Integer.class, parametros);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|getNumExtras|:"+ex);
		}
		return numExtras;
	}
	
	public int numMatEnPlan( String codigoPersonal, String planId, String tipocalId) {
		int total		= 0;
		
		try{
			String comando = "SELECT COUNT(CODIGO_PERSONAL) AS TOTAL FROM ENOC.ALUMNO_CURSO"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND PLAN_ID = ?"
					+ " AND TIPOCAL_ID IN (?)";
				Object[] parametros = new Object[] {codigoPersonal,planId};
				if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
					total = enocJdbc.queryForObject(comando, Integer.class, parametros);
				}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|numMatEnPlan|:"+ex);
		}
		return total;
	}
	
	public String getUltimaCarga( String codigoPersonal) {
		String carga			= "xxxxxx";
		
		try{
			String comando = "SELECT COALESCE(MAX(REPLACE(CARGA_ID,'2002-1','0102A')),'000000') AS CARGA" +
					" FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = ?";
			
			Object[] parametros = new Object[] {codigoPersonal};
 			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
 				carga  = enocJdbc.queryForObject(comando,String.class,parametros);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|getUltimaCarga|:"+ex);
		}
		return carga;
	}
	
	public String getUltimaCargaEstado( String codigoPersonal) {
		String carga			= "xxxxxx";
		
		try{
			String comando = "SELECT COALESCE(MAX(REPLACE(CARGA_ID,'2002-1','0102A')),'000000') AS CARGA" +
					" FROM ENOC.ALUM_ESTADO WHERE CODIGO_PERSONAL = ?";
					
			Object[] parametros = new Object[] {codigoPersonal};
 			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
 				carga  = enocJdbc.queryForObject(comando,String.class,parametros);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|getUltimaCarga|:"+ex);
		}
		return carga;
	}
	
	public int getNumMatTipoCal( String codigoPersonal, String cargaId, String tipoCal) {
		String comando 		= "";		
		int numMat			= 0;		
		
		try{
			comando = "SELECT COUNT(CODIGO_PERSONAL) AS NUMMAT " +
			"FROM ENOC.ALUMNO_CURSO "+
			"WHERE CODIGO_PERSONAL = ? "+
			"AND CARGA_ID = ? "+
			"AND TIPOCAL_ID IN("+tipoCal+")";			
			Object[] parametros = new Object[] {codigoPersonal,cargaId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				numMat = enocJdbc.queryForObject(comando, Integer.class, parametros);
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|getNumMatTipoCal|:"+ex);
		}
		return numMat;
	}	
	
	public int getNumMatPendientes(  String codigoPersonal, String planId, String ciclo) {
		String comando 		= "";		
		int numMat			= 0;		
		
		try{
			comando = "SELECT COUNT(CURSO_ID) AS NUMMAT FROM ENOC.MAPA_CURSO" + 
					" WHERE PLAN_ID = ?"+ 
					" AND CICLO = ?"+
					" AND TIPOCURSO_ID IN (1,2,3,5,7,8)"+ 
					" AND CURSO_ID NOT IN " +
					"	(SELECT CURSO_ID FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ? AND TIPOCAL_ID IN ('1','I'))";			
			Object[] parametros = new Object[] {planId,ciclo, codigoPersonal, planId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				numMat = enocJdbc.queryForObject(comando, Integer.class, parametros);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|getNumMatPendientes|:"+ex);
		}
		return numMat;
	}
	
	public int getNumReprobadas(  String codigoPersonal, String cargaId, String carreraId) {
		String comando 		= "";		
		int num			= 0;		
		
		try{
			comando = "SELECT COUNT(CURSO_CARGA_ID) AS NUM FROM ENOC.KRDX_CURSO_ACT" +
					" WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ?" +
					" AND ENOC.CARRERA(ENOC.CURSO_PLAN(CURSO_ID)) = ?  " +
					" AND TIPOCAL_ID IN ('2','4') " +
					" AND ENOC.TIPOCURSO_ID(CURSO_ID) NOT IN(3,4,5,6)" +
					" AND CODIGO_PERSONAL = ?";			
			Object[] parametros = new Object[] {cargaId,carreraId, codigoPersonal};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				num = enocJdbc.queryForObject(comando, Integer.class, parametros);
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|getNumReprobadas|:"+ex);
		}
		return num;
	}
	
	public int totMatPorAlumno( String codigoPersonal, String cargaId, String carrera, String tipoCal) {
		String comando 		= "";		
		int num			= 0;		
		
		try{
			comando = "SELECT COUNT(CURSO_CARGA_ID) AS TOTAL FROM ENOC.KRDX_CURSO_ACT" +
					" WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ?" +
					" AND ENOC.CARRERA(ENOC.CURSO_PLAN(CURSO_ID)) = ?  " +
					" AND TIPOCAL_ID IN ("+tipoCal+")" +
					" AND ENOC.TIPOCURSO_ID(CURSO_ID) NOT IN(3,4,5,6)" +
					" AND CODIGO_PERSONAL = ?";
			
			Object[] parametros = new Object[] {cargaId,carrera, codigoPersonal};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				num = enocJdbc.queryForObject(comando, Integer.class, parametros);
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|totMatPorAlumno|:"+ex);
		}
		return num;
	}
	
	public int getTotalMat(  String cargaId, String carrera) {
		String comando 		= "";		
		int num				= 0;		
		
		try{
			comando = " SELECT COUNT(*) AS NUMMAT" +
					" FROM ENOC.KRDX_CURSO_ACT " +
					" WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ? " +
					" AND ENOC.CARRERA(ENOC.CURSO_PLAN(CURSO_ID)) = ? " +
					" AND ENOC.TIPOCURSO_ID(CURSO_ID) NOT IN(3,4,5,6) ";			
			Object[] parametros = new Object[] {cargaId,carrera};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				num = enocJdbc.queryForObject(comando, Integer.class, parametros);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|getTotalMat|:"+ex);
		}
		return num;
	}
	
	public int getTotalReprobadas( String cargaId, String carrera) {
		String comando 		= "";		
		int num				= 0;		
		
		try{
			comando = " SELECT COUNT(*) AS NUMMAT " +
					" FROM ENOC.KRDX_CURSO_ACT " +
					" WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ? " +
					" AND ENOC.CARRERA(ENOC.CURSO_PLAN(CURSO_ID)) = ? " +
					" AND TIPOCAL_ID IN ('2','4')" +
					" AND ENOC.TIPOCURSO_ID(CURSO_ID) NOT IN(3,4,5,6) ";			
			Object[] parametros = new Object[] {cargaId,carrera};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				num = enocJdbc.queryForObject(comando, Integer.class, parametros);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|getTotalReprobadas|:"+ex);
		}
		return num;
	}
	
	public String getNumCreditos( String codigoPersonal, String planId, String cargasNoVigentes) {
		String creditos			= "";
		
		try{
			String comando = "SELECT COALESCE( SUM(CREDITOS) , 0 ) AS CREDITOS FROM ENOC.ALUMNO_CURSO "
					+ " WHERE CODIGO_PERSONAL = ? "
					+ " AND PLAN_ID = ? "
					+ " AND TIPOCAL_ID IN('I','1') "+cargasNoVigentes;			
			Object[] parametros = new Object[] {codigoPersonal, planId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
	 			creditos  = enocJdbc.queryForObject(comando,String.class,parametros);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|getNumCreditos|:"+ex);
		}
		return creditos;
	}
	
	public String getNotaCurso( String codigoPersonal, String cursoId, String tipocal, String orden) {
		String nota				= "0";
		
		try{
			String comando = "SELECT COALESCE(NOTA,0) AS NOTA FROM ENOC.ALUMNO_CURSO"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND CURSO_ID = ?"
					+ " AND TIPOCAL_ID = ?"
					+ " AND ROWNUM = 1";
			
			Object[] parametros = new Object[] {codigoPersonal, cursoId, tipocal};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
	 			nota  = enocJdbc.queryForObject(comando,String.class,parametros);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|getNotaCurso|:"+ex);
		}
		return nota;
	}
	
	public HashMap<String, String> mapCalificacionesInscritos( String cargaId) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();
		
		try{
			String comando = " SELECT CODIGO_PERSONAL,PLAN_ID,CICLO,"
									 + " SUM( (CASE COALESCE(NOTA_EXTRA,0) WHEN 0 THEN NOTA ELSE NOTA_EXTRA END) * CREDITOS )/SUM(CREDITOS) AS CALIFICACION"
									 + " FROM ENOC.ALUMNO_CURSO"
									 + " WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_ESTADO WHERE CARGA_ID = ? AND ESTADO = 'I')"
									 + " AND TIPOCAL_ID = '1'"									 
									 + " AND CREDITOS > 0"
									 + " AND CONVALIDACION IN ('N','I')"
									 + " GROUP BY CODIGO_PERSONAL,PLAN_ID,CICLO";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map:lista){			
				mapa.put(map.getLlave(), (String)map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|mapCalificacionesInscritos|:"+ex);
		}
		return mapa;
	}
	
	public List<String> getListaAlumReprobon( String cargaId, String incidencias, String orden) {		
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		List<String> lisAlumno = new ArrayList<String>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL,CODIGO_PERSONAL||'&&'||CURSO_ID AS LLAVE, COUNT(*) AS VALOR FROM ENOC.ALUMNO_CURSO"
					+ " WHERE TIPOCAL_ID IN ('2','4')"
					+ " AND CODIGO_PERSONAL||PLAN_ID IN (SELECT CODIGO_PERSONAL||PLAN_ID FROM ENOC.ALUM_ESTADO WHERE CARGA_ID IN ('"+cargaId+"'))"
					+ " GROUP BY CODIGO_PERSONAL,CODIGO_PERSONAL||'&&'||CURSO_ID"
					+ " HAVING COUNT(*) > TO_NUMBER(?,'9') "+orden;
			Object[] parametros = new Object[] {incidencias};
			lista = enocJdbc.query(comando,new aca.MapaMapper(), parametros);				
			for(aca.Mapa map : lista){				
				lisAlumno.add(map.getLlave());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|getListaAlumReprobon|:"+ex);
		}
		return lisAlumno;
	}
	
	public HashMap<String, String> mapAlumReprobon( String cargaId, String incidencias) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL||CURSO_ID AS LLAVE, COUNT(*) AS VALOR FROM ENOC.ALUMNO_CURSO"
					+ " WHERE TIPOCAL_ID IN ('2','4')"					
					+ " AND CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_ESTADO WHERE CARGA_ID IN ('"+cargaId+"'))"
					+ " GROUP BY CODIGO_PERSONAL||CURSO_ID"
					+ " HAVING COUNT(*) > TO_NUMBER(?,'9') ";
			Object[] parametros = new Object[] {incidencias};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map:lista){			
				mapa.put(map.getLlave(), (String)map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|mapAlumReprobon|:"+ex);
		}
		return mapa;
	}
	
	public int getListesTipo( String codigoPersonal, String cursoCargaId, String tipoCalId) {
		int lises		=0;
		String comando	= "";

		try{
			comando = "select count(*) FROM ENOC.ALUMNO_CURSO "+
					  "where codigo_personal = ? "+
					  "and curso_carga_id= ? "+
		 			  "and tipocal_id= ? ";
			Object[] parametros = new Object[] {codigoPersonal,cursoCargaId,tipoCalId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				lises = enocJdbc.queryForObject(comando, Integer.class, parametros);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|getListesTipo|:"+ex);
		}
		return lises;
	}
	
	public int[] getArrayComponente( String codigoPersonal, String planId, String tipoComp) {
		String comando		= "";
		List<String>  lista = new ArrayList<String>(); 
		int comp[] 			= {0,0,0,0,0,0,0,0,0,0,0,0,0,0};
				
		try{
			comando = "SELECT NOMBRE_CURSO FROM ENOC.ALUMNO_CURSO"+
					  " WHERE CODIGO_PERSONAL = ?"+
					  " AND PLAN_ID= ?" +
					  " AND TIPOCAL_ID = '1'";
			if (tipoComp.equals("SC")){
				comando += " AND SUBSTR(CURSO_ID,9,4) IN ('COSM','COSC')";
			}else if (tipoComp.equals("AF")){
				comando += " AND SUBSTR(CURSO_ID,9,4) IN ('COAF','COSA')";
			}else if (tipoComp.equals("TM")){
				comando += " AND SUBSTR(CURSO_ID,9,4) IN ('COTM','COTR')";
			}else if (tipoComp.equals("LC")){
				comando += " AND SUBSTR(CURSO_ID,9,4) IN ('CLOC','COLC')";
			}			
			
			lista = enocJdbc.queryForList(comando, String.class, codigoPersonal, planId);
			for (String nombreCurso: lista){
				
				if (nombreCurso.trim().indexOf(" I") != -1){ comp[0]=1; }
				if (nombreCurso.trim().indexOf(" II") != -1){ comp[1]=1; }
				if (nombreCurso.trim().indexOf(" III") != -1 ){ comp[2]=1; }
				if (nombreCurso.trim().indexOf(" IV") != -1 ){ comp[3]=1; }
				if (nombreCurso.trim().indexOf(" V") != -1 ){ comp[4]=1; }
				if (nombreCurso.trim().indexOf(" VI") != -1 ){ comp[5]=1; }
				if (nombreCurso.trim().indexOf(" VII") != -1 ){ comp[6]=1; }
				if (nombreCurso.trim().indexOf(" VIII") != -1 ){ comp[7]=1; }
				if (nombreCurso.trim().indexOf(" IX") != -1 ){ comp[8]=1; }
				if (nombreCurso.trim().indexOf(" X") != -1 ){ comp[9]=1; }
				if (nombreCurso.trim().indexOf(" XI") != -1 ){ comp[10]=1; }
				if (nombreCurso.trim().indexOf(" XII") != -1 ){ comp[11]=1; }
				if (nombreCurso.trim().indexOf(" XIII") != -1 ){ comp[12]=1; }
				if (nombreCurso.trim().indexOf(" XIV") != -1 ){ comp[13]=1; }
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|getListesTipo|:"+ex);
		}
		return comp;
	}
	
	public List<AlumnoCurso> getListTipo( String codigoPersonal, String planId, String tipoCursoId) {
		List<AlumnoCurso> lista		= new ArrayList<AlumnoCurso>();
		String comando					= "";
		
		try{
			comando ="SELECT " +
				"CODIGO_PERSONAL, " +
				"CARGA_ID, " +
				" BLOQUE_ID, " +
				"CURSO_CARGA_ID, " +
				"CARRERA_ID, " +
				"MODALIDAD_ID, " +
				"PLAN_ID, " +
				"CURSO_ID, " +
				"NOMBRE_CURSO, " +
				"CICLO AS CICLO, " +
				"TO_CHAR(CREDITOS,'99.99') AS CREDITOS, " +
				"HT, " +
				"HP, " +
				"TO_CHAR(NOTA_APROBATORIA,'999') AS NOTA_APROBATORIA, " +
				"CURSO_ID2, " +
				"NOMBRE_CURSO2, " +
				"TO_CHAR(CREDITOS2,'99.99') AS CREDITOS2, " +
				"TO_CHAR(HT2,'99') AS HT2, " +
				"TO_CHAR(HP2,'99') AS HP2, " +
				"TO_CHAR(NOTA,'999') AS NOTA, " +
				"TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION, " +
				"TIPOCAL_ID, " +
				"TO_CHAR(NOTA_EXTRA,'999') AS NOTA_EXTRA, " +
				"TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA, " +
				"CONVALIDACION, " +
				"TITULO, " +
				"TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO, " +
				"GRUPO, " +
				"MAESTRO, " +
				"ESTADO, " +				
				"CORRECCION, " +
				"OPTATIVA," +
				"NOTA_CONVA "+
				"FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = ? " +
				"AND PLAN_ID= ? AND CURSO_ID IN (SELECT CURSO_ID " +
					"FROM ENOC.MAPA_CURSO WHERE TIPOCURSO_ID = ?) " + 
				"AND TIPOCAL_ID NOT IN ('1','M','I')";			
			lista = enocJdbc.query(comando, new AlumnoCursoMapper(), codigoPersonal, planId, tipoCursoId);			
		}catch(Exception ex){			
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|getListTipo|:"+ex);
		}		
		return lista;
	}
	
	public List<AlumnoCurso> getMatAproPorPlan( String codigoPersonal, String planId, String orden ) {
		List<AlumnoCurso> lista		= new ArrayList<AlumnoCurso>();		
		try{
			String comando ="SELECT " +
				"CODIGO_PERSONAL, " +
				"CARGA_ID, " +
				" BLOQUE_ID, " +
				"CURSO_CARGA_ID, " +
				"CARRERA_ID, " +
				"MODALIDAD_ID, " +
				"PLAN_ID, " +
				"CURSO_ID, " +
				"NOMBRE_CURSO, " +
				"CICLO AS CICLO, " +
				"CREDITOS, " +
				"HT, " +
				"HP, " +
				"NOTA_APROBATORIA, " +
				"CURSO_ID2, " +
				"NOMBRE_CURSO2, " +
				"CREDITOS2," +
				"HT2, " +
				"HP2, " +
				"NOTA, " +
				"TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION, " +
				"TIPOCAL_ID, " +
				"NOTA_EXTRA, " +
				"TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA, " +
				"CONVALIDACION, " +
				"TITULO, " +
				"TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO, " +
				"GRUPO, " +
				"MAESTRO, " +
				"ESTADO, " +				
				"CORRECCION, " +
				"OPTATIVA," +
				"NOTA_CONVA "+
				"FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = ? " +
				"AND PLAN_ID= ? "+
				"AND TIPOCAL_ID = '1' "+orden;			
			lista = enocJdbc.query(comando, new AlumnoCursoMapper(), codigoPersonal, planId);
			
		}catch(Exception ex){	
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|getMatAproPorPlan|:"+ex);
		}	
		return lista;
	}
	
	public List<String> getPlanAalumnoPorTipo( String codigoPersonal, String tipoCal) {
		List<String> lista		= new ArrayList<String>();
		try{
			String comando = " SELECT DISTINCT(PLAN_ID) AS PLAN "
				    + " FROM ENOC.ALUMNO_CURSO "
				    + " WHERE CODIGO_PERSONAL = ? "
				    + " AND TIPOCAL_ID = ?"; 
			lista = enocJdbc.queryForList(comando, String.class, codigoPersonal, tipoCal);	
		}catch(Exception ex){			
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|getPlanAalumnoPorTipo|:"+ex);
		}
		return lista;
	}
	
	public AlumnoCurso getCursoPorIdyAlumno(String codigoPersonal, String cursoId, String tipos) {
		AlumnoCurso curso = new AlumnoCurso();		
		try{
			String comando = " SELECT COUNT(*) FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = ? AND CURSO_ID = ? AND TIPOCAL_ID IN ("+tipos+")";			
			if (enocJdbc.queryForObject(comando,Integer.class, codigoPersonal, cursoId) >= 1){
				comando = " SELECT * FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = ? AND CURSO_ID = ? AND TIPOCAL_ID IN ("+tipos+")";
				curso = enocJdbc.queryForObject(comando, new AlumnoCursoMapper(), codigoPersonal, cursoId);				
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|getCursoPorIdyAlumno|:"+ex);
		}
		return curso;
	}
	
	public boolean existeCursoPorIdyAlumno(String codigoPersonal, String cursoId, String tipos) {
		boolean ok = false;		
		try{
			String comando = " SELECT COUNT(*) FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = ? AND CURSO_ID = ? AND TIPOCAL_ID IN ("+tipos+")";			
			if (enocJdbc.queryForObject(comando,Integer.class, codigoPersonal, cursoId) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|existeCursoPorIdyAlumno|:"+ex);
		}
		return ok;
	}
	
	public int getNumAlumnos( String cursoCargaId ) {
		int num=0;
		String comando	= "";

		try{
			comando = "SELECT COUNT(CODIGO_PERSONAL) AS NUM FROM ENOC.ALUMNO_CURSO" +
			  " WHERE CURSO_CARGA_ID = ?" +
			  " AND TIPOCAL_ID != 'M'";
			Object[] parametros = new Object[] {cursoCargaId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				num = enocJdbc.queryForObject(comando, Integer.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|getNumAlumnos|:"+ex);
		}
		return num;
	}
	
	public String getPromedioCiclo( String codigoPersonal, String ciclo ) {
		String promedio		= "";
		String comando	= "";
		
		try{
			comando = "SELECT TO_CHAR( ROUND(SUM(NOTA*CREDITOS) / CASE SUM(CREDITOS) WHEN 0 THEN 1 ELSE SUM(CREDITOS) END,2), '999.99' ) AS PONDERADO "+
					  "FROM ENOC.ALUMNO_CURSO " +
					  "WHERE CODIGO_PERSONAL = ? "+
					  "AND CICLO = TO_NUMBER(?,'99') "+
					  "AND TIPOCAL_ID = '1'";			
			Object[] parametros = new Object[] {codigoPersonal, ciclo};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
	 			promedio  = enocJdbc.queryForObject(comando,String.class,parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error getNumAlumnos: "+ex);
		}
		return promedio;
	}
	
	// Este metodo no esta recomendado para utilizarlo en algxxxn reporte de consulta a
	// menos que se considere y este conciente del tiempo requerido para este proceso.
	public List<AlumnoCurso> getListAll( String orden ) {
		List<AlumnoCurso> lista		= new ArrayList<AlumnoCurso>();
		String comando	= "";
		
		try{
			comando = "SELECT "+
				"CODIGO_PERSONAL, "+
				"CARGA_ID, "+
				" BLOQUE_ID, "+
				"CURSO_CARGA_ID, "+
				"CARRERA_ID, "+
				"MODALIDAD_ID, "+
				"PLAN_ID, "+
				"CURSO_ID, "+
				"NOMBRE_CURSO, "+
				"CICLO AS CICLO, "+
				"TO_CHAR(CREDITOS,'99.99') AS CREDITOS, "+
				"HT, "+
				"HP, "+
				"TO_CHAR(NOTA_APROBATORIA,'999') AS NOTA_APROBATORIA, "+
				"CURSO_ID2, "+
				"NOMBRE_CURSO2, "+
				"TO_CHAR(CREDITOS2,'99.99') AS CREDITOS2, "+
				"TO_CHAR(HT2,'99') AS HT2, "+
				"TO_CHAR(HP2,'99') AS HP2, "+
				"NOTA, "+
				"TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION, "+
				"TIPOCAL_ID, "+
				"COALESCE(NOTA_EXTRA,0) AS NOTA_EXTRA, "+
				"TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA, "+
				"CONVALIDACION, "+
				"TITULO, "+
				"TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO, "+
				"GRUPO, "+
				"MAESTRO, "+
				"ESTADO, "+				
				"CORRECCION, " +
				"OPTATIVA," +
				"NOTA_CONVA "+
				"FROM ENOC.ALUMNO_CURSO "+ orden;
			
			lista = enocJdbc.query(comando, new AlumnoCursoMapper());	
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|getListAll|:"+ex);
		}		
		return lista;
	}
	
	public List<AlumnoCurso> getListCarga( String cargaId, String orden ) {
		List<AlumnoCurso> lista		= new ArrayList<AlumnoCurso>();
		String comando	= "";
		
		try{
			comando = "SELECT "+
				"CODIGO_PERSONAL, "+
				"CARGA_ID, "+
				" BLOQUE_ID, "+
				"CURSO_CARGA_ID, "+
				"CARRERA_ID, "+
				"MODALIDAD_ID, "+
				"PLAN_ID, "+
				"CURSO_ID, "+
				"NOMBRE_CURSO, "+
				"CICLO AS CICLO, "+
				"TO_CHAR(CREDITOS,'99.99') AS CREDITOS, "+
				"HT, "+
				"HP, "+
				"TO_CHAR(NOTA_APROBATORIA,'999') AS NOTA_APROBATORIA, "+
				"CURSO_ID2, "+
				"NOMBRE_CURSO2, "+
				"TO_CHAR(CREDITOS2,'99.99') AS CREDITOS2, "+
				"TO_CHAR(HT2,'99') AS HT2, "+
				"TO_CHAR(HP2,'99') AS HP2, "+
				"TO_CHAR(NOTA,'999') AS NOTA, "+
				"TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION, "+
				"TIPOCAL_ID, "+
				"COALESCE(NOTA_EXTRA,0) AS NOTA_EXTRA, "+
				"TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA, "+
				"CONVALIDACION, "+
				"TITULO, "+
				"TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO, "+
				"GRUPO, "+
				"MAESTRO, "+
				"ESTADO, "+				
				"CORRECCION, " +
				"OPTATIVA," +
				"NOTA_CONVA "+
				"FROM ENOC.ALUMNO_CURSO " +
				"WHERE CARGA_ID = ? " + orden;			
			lista = enocJdbc.query(comando, new AlumnoCursoMapper(), cargaId);			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|getListCarga|:"+ex);
		}
		return lista;
	}
	
	public List<AlumnoCurso> getListCargayCarrera( String cargaId, String carreraId, String orden ) {
		List<AlumnoCurso> lista		= new ArrayList<AlumnoCurso>();
		String comando	= "";
		
		try{
			comando = "SELECT "+
				"CODIGO_PERSONAL, "+
				"CARGA_ID, "+
				" BLOQUE_ID, "+
				"CURSO_CARGA_ID, "+
				"CARRERA_ID, "+
				"MODALIDAD_ID, "+
				"PLAN_ID, "+
				"CURSO_ID, "+
				"NOMBRE_CURSO, "+
				"CICLO AS CICLO, "+
				"TO_CHAR(CREDITOS,'99.99') AS CREDITOS, "+
				"HT, "+
				"HP, "+
				"TO_CHAR(NOTA_APROBATORIA,'999') AS NOTA_APROBATORIA, "+
				"CURSO_ID2, "+
				"NOMBRE_CURSO2, "+
				"TO_CHAR(CREDITOS2,'99.99') AS CREDITOS2, "+
				"TO_CHAR(HT2,'99') AS HT2, "+
				"TO_CHAR(HP2,'99') AS HP2, "+
				"TO_CHAR(NOTA,'999') AS NOTA, "+
				"TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION, "+
				"TIPOCAL_ID, "+
				"COALESCE(NOTA_EXTRA,0) AS NOTA_EXTRA, "+
				"TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA, "+
				"CONVALIDACION, "+
				"TITULO, "+
				"TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO, "+
				"GRUPO, "+
				"MAESTRO, "+
				"ESTADO, "+				
				"CORRECCION, " +
				"OPTATIVA," +
				"NOTA_CONVA "+
				"FROM ENOC.ALUMNO_CURSO " +
				"WHERE CARGA_ID = ? "+
				"AND CARRERA_ID = ? "+ orden;			
			lista = enocJdbc.query(comando, new AlumnoCursoMapper(), cargaId, carreraId);			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|getListCarga|:"+ex);
		}		
		return lista;
	}	
	
	public List<AlumnoCurso> lisPorCargayCarrera( String cargaId, String carreraId, String orden ) {
		List<AlumnoCurso> lista		= new ArrayList<AlumnoCurso>();
		String comando	= "";		
		try{
			comando = "SELECT "+
				"CODIGO_PERSONAL, "+
				"CARGA_ID, "+
				" BLOQUE_ID, "+
				"CURSO_CARGA_ID, "+
				"CARRERA_ID, "+
				"MODALIDAD_ID, "+
				"PLAN_ID, "+
				"CURSO_ID, "+
				"NOMBRE_CURSO, "+
				"CICLO AS CICLO, "+
				"TO_CHAR(CREDITOS,'99.99') AS CREDITOS, "+
				"HT, "+
				"HP, "+
				"TO_CHAR(NOTA_APROBATORIA,'999') AS NOTA_APROBATORIA, "+
				"CURSO_ID2, "+
				"NOMBRE_CURSO2, "+
				"TO_CHAR(CREDITOS2,'99.99') AS CREDITOS2, "+
				"TO_CHAR(HT2,'99') AS HT2, "+
				"TO_CHAR(HP2,'99') AS HP2, "+
				"TO_CHAR(NOTA,'999') AS NOTA, "+
				"TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION, "+
				"TIPOCAL_ID, "+
				"COALESCE(NOTA_EXTRA,0) AS NOTA_EXTRA, "+
				"TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA, "+
				"CONVALIDACION, "+
				"TITULO, "+
				"TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO, "+
				"GRUPO, "+
				"MAESTRO, "+
				"ESTADO, "+				
				"CORRECCION, " +
				"OPTATIVA," +
				"NOTA_CONVA "+
				"FROM ENOC.ALUMNO_CURSO " +
				"WHERE CARGA_ID = ? "+
				"AND CARRERA_ID = ? "
				+ " AND TIPOCAL_ID != 'M' "+ orden;			
			lista = enocJdbc.query(comando, new AlumnoCursoMapper(), cargaId, carreraId);			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|getListCarga|:"+ex);
		}		
		return lista;
	}
	
	public List<AlumnoCurso> getListCargaconExtra( String cargaId, String orden ) {
		List<AlumnoCurso> lista		= new ArrayList<AlumnoCurso>();
		String comando	= "";
		
		try{
			comando = "SELECT " +
					" CODIGO_PERSONAL," +
					" CARGA_ID," +
					"  BLOQUE_ID," +
					" CURSO_CARGA_ID, " +
					" CARRERA_ID," +
					" MODALIDAD_ID, " +
					" PLAN_ID, " +
					" CURSO_ID, " +
					" NOMBRE_CURSO," +
					" CICLO AS CICLO, " +
					" TO_CHAR(CREDITOS,'99.99') AS CREDITOS, " +
					" HT, " +
					" HP," +
					" TO_CHAR(NOTA_APROBATORIA,'999') AS NOTA_APROBATORIA, " +
					" CURSO_ID2, " +
					" NOMBRE_CURSO2, " +
					" TO_CHAR(CREDITOS2,'99.99') AS CREDITOS2, " +
					" TO_CHAR(HT2,'99') AS HT2," +
					" TO_CHAR(HP2,'99') AS HP2, " +
					" TO_CHAR(NOTA,'999') AS NOTA," +
					" TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION, " +
					" TIPOCAL_ID, " +
					" COALESCE(NOTA_EXTRA,0) AS NOTA_EXTRA, " +
					" TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA, " +
					" CONVALIDACION," +
					" TITULO," +
					" TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO," +
					" GRUPO," +
					" MAESTRO," +
					" ESTADO, " +					
					" CORRECCION, " +
					" OPTATIVA, " +
					" NOTA_CONVA " +
					" FROM ENOC.ALUMNO_CURSO " +
					" WHERE CARGA_ID = ?" +
					" AND NOTA_EXTRA > 0 " + orden;			
			lista = enocJdbc.query(comando, new AlumnoCursoMapper(), cargaId);
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|getListCarga|:"+ex);
		}		
		return lista;
	}
	
	public List<String> getListCargaconExtraP( String cargaId, String orden ) {
		List<String> lista		= new ArrayList<String>();
		try{
			String comando = "SELECT " +
					" CODIGO_PERSONAL," +
					" (SELECT NOMBRE_CARRERA FROM ENOC.CAT_CARRERA WHERE CARRERA_ID = ALUMNO_CURSO.CARRERA_ID) AS CARRERA," +
					" NOMBRE_CURSO," +
					" COALESCE(NOTA_EXTRA,0) AS NOTA_EXTRA, " +
					" TO_CHAR(NOTA,'999') AS NOTA," +
					" FROM ENOC.ALUMNO_CURSO " +
					" WHERE CARGA_ID = ?" +
					" AND NOTA_EXTRA > 0 " + orden;			
			lista = enocJdbc.queryForList(comando, String.class, cargaId);			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|getListCarga|:"+ex);
		}
		return lista;
	}
	
	public List<AlumnoCurso> getListAlumno( String codigoPersonal, String orden ) {
		List<AlumnoCurso> lista		= new ArrayList<AlumnoCurso>();
		try{
			String comando = "SELECT "+
				"CODIGO_PERSONAL, "+
				"CARGA_ID, "+
				"BLOQUE_ID, "+
				"CURSO_CARGA_ID, "+
				"CARRERA_ID, "+
				"MODALIDAD_ID, "+
				"PLAN_ID, "+
				"CURSO_ID, "+
				"NOMBRE_CURSO, "+
				"CICLO, "+
				"COALESCE(TRIM(TO_CHAR(CREDITOS,'99.99')),'0') AS CREDITOS, "+
				"HT, "+
				"HP, "+
				"TO_CHAR(NOTA_APROBATORIA,'999') AS NOTA_APROBATORIA, "+
				"CURSO_ID2, "+
				"NOMBRE_CURSO2, "+
				"TO_CHAR(CREDITOS2,'99.99') AS CREDITOS2, "+
				"TO_CHAR(HT2,'99') AS HT2, "+
				"TO_CHAR(HP2,'99') AS HP2, "+
				"COALESCE(NOTA,0) AS NOTA, "+
				"COALESCE(NOTA_EXTRA,0) AS NOTA_EXTRA, "+
				"TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION, "+
				"TIPOCAL_ID, "+
				"TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA, "+
				"CONVALIDACION, "+
				"TITULO, "+
				"TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO, "+
				"GRUPO, "+
				"MAESTRO, "+
				"ESTADO, "+				
				"CORRECCION, " +
				"OPTATIVA," +
				"NOTA_CONVA "+
				"FROM ENOC.ALUMNO_CURSO "+
				"WHERE CODIGO_PERSONAL = ? "+orden;			
			lista = enocJdbc.query(comando, new AlumnoCursoMapper(), codigoPersonal);		
		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|getListAlumno|:"+ex);
		}
		return lista;
	}
	
	public List<AlumnoCurso> lisPorAlumnoAndPlan( String codigoPersonal, String planId, String orden) {
		List<AlumnoCurso> lista		= new ArrayList<AlumnoCurso>();
		try{
			String comando = "SELECT "
				+ " CODIGO_PERSONAL, "
				+ " CARGA_ID, "
				+ " BLOQUE_ID, "
				+ " CURSO_CARGA_ID, "
				+ " CARRERA_ID, "
				+ " MODALIDAD_ID, "
				+ " PLAN_ID, "
				+ " CURSO_ID, "
				+ " NOMBRE_CURSO, "
				+ " CICLO, "
				+ " COALESCE(TRIM(TO_CHAR(CREDITOS,'99.99')),'0') AS CREDITOS, "
				+ " HT, "
				+ " HP, "
				+ " TO_CHAR(NOTA_APROBATORIA,'999') AS NOTA_APROBATORIA, "
				+ " CURSO_ID2, "
				+ " NOMBRE_CURSO2, "
				+ " TO_CHAR(CREDITOS2,'99.99') AS CREDITOS2, "
				+ " TO_CHAR(HT2,'99') AS HT2, "
				+ " TO_CHAR(HP2,'99') AS HP2, "
				+ " COALESCE(NOTA,0) AS NOTA, "
				+ " COALESCE(NOTA_EXTRA,0) AS NOTA_EXTRA,"
				+ " TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION,"
				+ " TIPOCAL_ID, "
				+ " TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA, "
				+ " CONVALIDACION, "
				+ " TITULO, "
				+ " TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO, "
				+ " GRUPO, "
				+ " MAESTRO, "
				+ " ESTADO, "				
				+ " CORRECCION, "
				+ " OPTATIVA,"
				+ " NOTA_CONVA "
				+ " FROM ENOC.ALUMNO_CURSO "
				+ " WHERE CODIGO_PERSONAL = ?"
				+ " AND PLAN_ID = ? "+orden;
			lista = enocJdbc.query(comando, new AlumnoCursoMapper(), codigoPersonal, planId);
		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|lisPorAlumnoAndPlan|:"+ex);
		}
		return lista;
	}
	
	public List<AlumnoCurso> lisNotasPorAlumnoAndPlan( String codigoPersonal, String planId, String orden ) {
		List<AlumnoCurso> lista		= new ArrayList<AlumnoCurso>();
		try{
			String comando = "SELECT "+
				" CODIGO_PERSONAL, "+
				" CARGA_ID, "+
				" BLOQUE_ID, "+
				" CURSO_CARGA_ID, "+
				" CARRERA_ID, "+
				" MODALIDAD_ID, "+
				" PLAN_ID, "+
				" CURSO_ID, "+
				" NOMBRE_CURSO, "+
				" CICLO, "+
				" COALESCE(TRIM(TO_CHAR(CREDITOS,'99.99')),'0') AS CREDITOS, "+
				" HT, "+
				" HP, "+
				" TO_CHAR(NOTA_APROBATORIA,'999') AS NOTA_APROBATORIA, "+
				" CURSO_ID2, "+
				" NOMBRE_CURSO2, "+
				" TO_CHAR(CREDITOS2,'99.99') AS CREDITOS2, "+
				" TO_CHAR(HT2,'99') AS HT2, "+
				" TO_CHAR(HP2,'99') AS HP2, "+
				" COALESCE(NOTA,0) AS NOTA, "+
				" COALESCE(NOTA_EXTRA,0) AS NOTA_EXTRA, "+
				" TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION, "+
				" TIPOCAL_ID, "+
				" TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA, "+
				" CONVALIDACION, "+
				" TITULO, "+
				" TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO, "+
				" GRUPO, "+
				" MAESTRO, "+
				" ESTADO, "+				
				" CORRECCION, " +
				" OPTATIVA," +
				" NOTA_CONVA "+
				" FROM ENOC.ALUMNO_CURSO "+
				" WHERE CODIGO_PERSONAL = ? "
				+ " AND PLAN_ID = ? "+orden;			
			lista = enocJdbc.query(comando, new AlumnoCursoMapper(), codigoPersonal, planId);		
		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|lisNotasPorAlumnoAndPlan|:"+ex);
		}
		return lista;
	}
	public List<AlumnoCurso> getListCurso( String cursoCargaId, String orden ) {
		List<AlumnoCurso> lista			= new ArrayList<AlumnoCurso>();
		String comando			= "";
		
		try{
			comando = "SELECT "+
				"CODIGO_PERSONAL, "+
				"CARGA_ID, "+
				"BLOQUE_ID, "+
				"CURSO_CARGA_ID, "+
				"CARRERA_ID, "+
				"MODALIDAD_ID, "+
				"PLAN_ID, "+
				"CURSO_ID, "+
				"NOMBRE_CURSO, "+
				"CICLO, "+
				"TO_CHAR(CREDITOS,'99.99') AS CREDITOS, "+
				"HT, "+
				"HP, "+
				"NOTA_APROBATORIA, "+
				"CURSO_ID2, "+
				"NOMBRE_CURSO2, "+
				"TO_CHAR(CREDITOS2,'99.99') AS CREDITOS2, "+
				"TO_CHAR(HT2,'99') AS HT2, "+
				"TO_CHAR(HP2,'99') AS HP2, "+
				"COALESCE(NOTA,0) AS NOTA, "+
				"TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION, "+
				"TIPOCAL_ID, "+
				"COALESCE(NOTA_EXTRA,0) AS NOTA_EXTRA, "+
				"TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA, "+
				"CONVALIDACION, "+
				"TITULO, "+
				"TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO, "+
				"GRUPO, "+
				"MAESTRO, "+
				"ESTADO, "+				
				"CORRECCION, " +
				"OPTATIVA," +
				"NOTA_CONVA "+
				"FROM ENOC.ALUMNO_CURSO "+
				"WHERE CURSO_CARGA_ID = ? "+orden;			
			lista = enocJdbc.query(comando, new AlumnoCursoMapper(), cursoCargaId);			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|getListCurso|:"+ex);
		}
		return lista;
	}
	
	public List<AlumnoCurso> getListTraspaso( String cargaId, String orden ) {
		List<AlumnoCurso> lista			= new ArrayList<AlumnoCurso>();
		String comando			= "";
		
		try{
			comando = "SELECT "+
				"CODIGO_PERSONAL, "+
				"CARGA_ID, "+
				"BLOQUE_ID, "+
				"CURSO_CARGA_ID, "+
				"CARRERA_ID, "+
				"MODALIDAD_ID, "+
				"PLAN_ID, "+
				"CURSO_ID, "+
				"NOMBRE_CURSO, "+
				"CICLO, "+
				"TO_CHAR(CREDITOS,'99.99') AS CREDITOS, "+
				"HT, "+
				"HP, "+
				"NOTA_APROBATORIA, "+
				"CURSO_ID2, "+
				"NOMBRE_CURSO2, "+
				"TO_CHAR(CREDITOS2,'99.99') AS CREDITOS2, "+
				"TO_CHAR(HT2,'99') AS HT2, "+
				"TO_CHAR(HP2,'99') AS HP2, "+
				"COALESCE(NOTA,0) AS NOTA, "+
				"TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION, "+
				"TIPOCAL_ID, "+
				"COALESCE(NOTA_EXTRA,0) AS NOTA_EXTRA, "+
				"TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA, "+
				"CONVALIDACION, "+
				"TITULO, "+
				"TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO, "+
				"GRUPO, "+
				"MAESTRO, "+
				"ESTADO, "+				
				"CORRECCION, " +
				"OPTATIVA," +
				"NOTA_CONVA "+
				"FROM ENOC.ALUMNO_CURSO "+
				"WHERE CARGA_ID = ?"+orden;			
			lista = enocJdbc.query(comando, new AlumnoCursoMapper(), cargaId);		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|getListTraspaso|:"+ex);
		}
		return lista;
	}
	
	public List<AlumnoCurso> getListAlumnoCarga(  String codigoPersonal, String cargaId, String orden ) {
		List<AlumnoCurso> lista		= new ArrayList<AlumnoCurso>();
		try{
			String comando = "SELECT "+
				"CODIGO_PERSONAL, "+
				"CARGA_ID, "+
				" BLOQUE_ID, "+
				"CURSO_CARGA_ID, "+
				"CARRERA_ID, "+
				"MODALIDAD_ID, "+
				"PLAN_ID, "+
				"CURSO_ID, "+
				"NOMBRE_CURSO, "+
				"CICLO AS CICLO, "+
				"TO_CHAR(CREDITOS,'99.99') AS CREDITOS, "+
				"HT, "+
				"HP, "+
				"TO_CHAR(NOTA_APROBATORIA,'999') AS NOTA_APROBATORIA, "+
				"CURSO_ID2, "+
				"NOMBRE_CURSO2, "+
				"TO_CHAR(CREDITOS2,'99.99') AS CREDITOS2, "+
				"TO_CHAR(HT2,'99') AS HT2, "+
				"TO_CHAR(HP2,'99') AS HP2, "+
				"COALESCE(NOTA,0) AS NOTA, "+
				"TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION, "+
				"TIPOCAL_ID, "+
				"COALESCE(NOTA_EXTRA,0) AS NOTA_EXTRA, "+
				"COALESCE(TO_CHAR(F_EXTRA,'DD/MM/YYYY'),'') AS F_EXTRA, "+
				"CONVALIDACION, "+
				"TITULO, "+
				"TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO, "+
				"GRUPO, "+
				"MAESTRO, "+
				"ESTADO, "+				
				"CORRECCION, " +
				"OPTATIVA," +
				"NOTA_CONVA "+
				"FROM ENOC.ALUMNO_CURSO "+
				"WHERE CODIGO_PERSONAL = ? "+
				"AND CARGA_ID = ? "+orden;
		
			lista = enocJdbc.query(comando, new AlumnoCursoMapper(), codigoPersonal, cargaId);
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|getListAlumnoCarga|:"+ex);
		}
		return lista;
	}
	
	public List<AlumnoCurso> lisMateriasDelAlumno(  String codigoPersonal, String cargaId, String bloqueId, String orden ) {
		List<AlumnoCurso> lista		= new ArrayList<AlumnoCurso>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, CURSO_CARGA_ID, CARRERA_ID, MODALIDAD_ID,"
				+ " PLAN_ID, CURSO_ID, NOMBRE_CURSO, CICLO AS CICLO, TO_CHAR(CREDITOS,'99.99') AS CREDITOS, HT, HP,"
				+ " TO_CHAR(NOTA_APROBATORIA,'999') AS NOTA_APROBATORIA, CURSO_ID2, NOMBRE_CURSO2,"
				+ " TO_CHAR(CREDITOS2,'99.99') AS CREDITOS2,"
				+ " TO_CHAR(HT2,'99') AS HT2,"
				+ " TO_CHAR(HP2,'99') AS HP2,"
				+ " COALESCE(NOTA,0) AS NOTA,"
				+ " TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION,"
				+ " TIPOCAL_ID, COALESCE(NOTA_EXTRA,0) AS NOTA_EXTRA,"
				+ " COALESCE(TO_CHAR(F_EXTRA,'DD/MM/YYYY'),'') AS F_EXTRA,"
				+ " CONVALIDACION, TITULO,"
				+ " TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO,"
				+ " GRUPO,"
				+ " MAESTRO,"
				+ " ESTADO,"
				+ " CORRECCION,"
				+ " OPTATIVA,"
				+ " NOTA_CONVA"
				+ " FROM ENOC.ALUMNO_CURSO"
				+ " WHERE CODIGO_PERSONAL = ?"				
				+ " AND CARGA_ID = ? "
				+ " AND BLOQUE_ID = TO_NUMBER(?,'99') "+orden;
			lista = enocJdbc.query(comando, new AlumnoCursoMapper(), codigoPersonal,cargaId, bloqueId);			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|lisMateriasDelAlumno|:"+ex);
		}
		return lista;
	}
	
	public List<AlumnoCurso> getListAlumnoCarga(  String codigoPersonal, String cargaId, String tipos, String orden ) {
		List<AlumnoCurso> lista		= new ArrayList<AlumnoCurso>();
		try{
			String comando = "SELECT "+
				"CODIGO_PERSONAL, "+
				"CARGA_ID, "+
				" BLOQUE_ID, "+
				"CURSO_CARGA_ID, "+
				"CARRERA_ID, "+
				"MODALIDAD_ID, "+
				"PLAN_ID, "+
				"CURSO_ID, "+
				"NOMBRE_CURSO, "+
				"CICLO AS CICLO, "+
				"TO_CHAR(CREDITOS,'99.99') AS CREDITOS, "+
				"HT, "+
				"HP, "+
				"TO_CHAR(NOTA_APROBATORIA,'999') AS NOTA_APROBATORIA, "+
				"CURSO_ID2, "+
				"NOMBRE_CURSO2, "+
				"TO_CHAR(CREDITOS2,'99.99') AS CREDITOS2, "+
				"TO_CHAR(HT2,'99') AS HT2, "+
				"TO_CHAR(HP2,'99') AS HP2, "+
				"COALESCE(NOTA,0) AS NOTA, "+
				"TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION, "+
				"TIPOCAL_ID, "+
				"COALESCE(NOTA_EXTRA,0) AS NOTA_EXTRA, "+
				"COALESCE(TO_CHAR(F_EXTRA,'DD/MM/YYYY'),'') AS F_EXTRA, "+
				"CONVALIDACION, "+
				"TITULO, "+
				"TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO, "+
				"GRUPO, "+
				"MAESTRO, "+
				"ESTADO, "+
				"CORRECCION, " +
				"OPTATIVA," +
				"NOTA_CONVA "+
				"FROM ENOC.ALUMNO_CURSO "+
				"WHERE CODIGO_PERSONAL = ? "+
				"AND CARGA_ID = ?"
				+ " AND TIPOCAL_ID NOT IN ("+tipos+") "+orden;		
			lista = enocJdbc.query(comando, new AlumnoCursoMapper(), codigoPersonal, cargaId);
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|getListAlumnoCarga|:"+ex);
		}
		return lista;
	}
	
	public List<AlumnoCurso> getListAlumnoCargaReprobados(  String codigoPersonal, String cargaId, String orden ) {
		List<AlumnoCurso> lista		= new ArrayList<AlumnoCurso>();
		String comando		= "";
		
		try{
			comando = " SELECT CODIGO_PERSONAL, CARGA_ID,  BLOQUE_ID, CURSO_CARGA_ID, CARRERA_ID, MODALIDAD_ID, PLAN_ID, "
					+ " CURSO_ID,NOMBRE_CURSO, CICLO AS CICLO, TO_CHAR(CREDITOS,'99.99') AS CREDITOS, HT, HP, "
					+ " TO_CHAR(NOTA_APROBATORIA,'999') AS NOTA_APROBATORIA, CURSO_ID2, NOMBRE_CURSO2, TO_CHAR(CREDITOS2,'99.99') AS CREDITOS2, TO_CHAR(HT2,'99') AS HT2, "
					+ " TO_CHAR(HP2,'99') AS HP2, NOTA, TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION, TIPOCAL_ID, COALESCE(TO_CHAR(NOTA_EXTRA,'999'),'0') AS NOTA_EXTRA, "
					+ " COALESCE(TO_CHAR(F_EXTRA,'DD/MM/YYYY'),'') AS F_EXTRA, CONVALIDACION, TITULO, TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO, GRUPO, MAESTRO, ESTADO, "
					+ " CORRECCION, OPTATIVA,NOTA_CONVA FROM ENOC.ALUMNO_CURSO  "
					+ " WHERE CODIGO_PERSONAL = ? AND CARGA_ID IN ("+cargaId+") AND NOTA < NOTA_APROBATORIA AND TIPOCAL_ID IN('2','4') ";			
			lista = enocJdbc.query(comando, new AlumnoCursoMapper(), codigoPersonal);		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|getListAlumnoCargaReprobados|:"+ex);
		}
		return lista;
	}

	public List<AlumnoCurso> getListMateriasAlumnoPorCarga(String codigoPersonal, String cargaId) {
		List<AlumnoCurso> lista = new ArrayList<AlumnoCurso>();
		String comando = "";
		
		try{
			comando = " SELECT CODIGO_PERSONAL, CARGA_ID,  BLOQUE_ID, CURSO_CARGA_ID, CARRERA_ID, MODALIDAD_ID, PLAN_ID, "
					+ " CURSO_ID,NOMBRE_CURSO, CICLO, COALESCE(CREDITOS,0) AS CREDITOS, HT, HP, "
					+ " NOTA_APROBATORIA, CURSO_ID2, NOMBRE_CURSO2, COALESCE(CREDITOS2,0) AS CREDITOS2, HT2, "
					+ " TO_CHAR(HP2,'99') AS HP2, NOTA, TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION, TIPOCAL_ID, COALESCE(TO_CHAR(NOTA_EXTRA,'999'),'0') AS NOTA_EXTRA, "
					+ " COALESCE(TO_CHAR(F_EXTRA,'DD/MM/YYYY'),'') AS F_EXTRA, CONVALIDACION, TITULO, TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO, GRUPO, MAESTRO, ESTADO, "
					+ " CORRECCION, OPTATIVA,NOTA_CONVA "
					+ " FROM ENOC.ALUMNO_CURSO"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND CARGA_ID= ? ";
			Object[] parametros = new Object[] {codigoPersonal, cargaId};
			lista = enocJdbc.query(comando, new AlumnoCursoMapper(), parametros);		
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|getListMateriasAlumnoPorCarga|:"+ex);
		}
		return lista;
	}
	
	public List<AlumnoCurso> lisAlumnosPorMateria(String fecha, String materia, String estados, String orden){
		List<AlumnoCurso> lista = new ArrayList<AlumnoCurso>();				
		try{
			String comando = " SELECT CODIGO_PERSONAL, CARGA_ID,  BLOQUE_ID, CURSO_CARGA_ID, CARRERA_ID, MODALIDAD_ID, PLAN_ID, "
					+ " CURSO_ID,NOMBRE_CURSO, CICLO, COALESCE(CREDITOS,0) AS CREDITOS, HT, HP, "
					+ " NOTA_APROBATORIA, CURSO_ID2, NOMBRE_CURSO2, COALESCE(CREDITOS2,0) AS CREDITOS2, HT2, "
					+ " TO_CHAR(HP2,'99') AS HP2, NOTA, TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION, TIPOCAL_ID, COALESCE(TO_CHAR(NOTA_EXTRA,'999'),'0') AS NOTA_EXTRA, "
					+ " COALESCE(TO_CHAR(F_EXTRA,'DD/MM/YYYY'),'') AS F_EXTRA, CONVALIDACION, TITULO, TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO, GRUPO, MAESTRO, ESTADO, "
					+ " CORRECCION, OPTATIVA,NOTA_CONVA "
					+ " FROM ENOC.ALUMNO_CURSO"
					+ " WHERE CARGA_ID IN (SELECT CARGA_ID FROM ENOC.CARGA WHERE TO_DATE(?,'DD/MM/YYYY') BETWEEN F_INICIO AND F_FINAL) AND CURSO_ID LIKE '%"+materia+"%'"
					+ " AND TIPOCAL_ID IN ("+estados+") "+orden;
			Object[] parametros = new Object[] {fecha};
			lista = enocJdbc.query(comando, new AlumnoCursoMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|lisAlumnosPorMateria|:"+ex);
		}
		return lista;
	}
	
	public List<AlumnoCurso> getListAlumnoCargaBloque(  String codigoPersonal, String cargaId, String bloqueId, String orden ) {
		List<AlumnoCurso> lista		= new ArrayList<AlumnoCurso>();
		String comando		= "";
		
		try{
			comando = "SELECT "+
				"CODIGO_PERSONAL, "+
				"CARGA_ID, "+
				" BLOQUE_ID, "+
				"CURSO_CARGA_ID, "+
				"CARRERA_ID, "+
				"MODALIDAD_ID, "+
				"PLAN_ID, "+
				"CURSO_ID, "+
				"NOMBRE_CURSO, "+
				"CICLO AS CICLO, "+
				"TO_CHAR(CREDITOS,'99.99') AS CREDITOS, "+
				"HT, "+
				"HP, "+
				"TO_CHAR(NOTA_APROBATORIA,'999') AS NOTA_APROBATORIA, "+
				"CURSO_ID2, "+
				"NOMBRE_CURSO2, "+
				"TO_CHAR(CREDITOS2,'99.99') AS CREDITOS2, "+
				"TO_CHAR(HT2,'99') AS HT2, "+
				"TO_CHAR(HP2,'99') AS HP2, "+
				"NOTA, "+
				"TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION, "+
				"TIPOCAL_ID, "+
				"COALESCE(TO_CHAR(NOTA_EXTRA,'999'),'0') AS NOTA_EXTRA, "+
				"COALESCE(TO_CHAR(F_EXTRA,'DD/MM/YYYY'),'') AS F_EXTRA, "+
				"CONVALIDACION, "+
				"TITULO, "+
				"TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO, "+
				"GRUPO, "+
				"MAESTRO, "+
				"ESTADO, "+				
				"CORRECCION, " +
				"OPTATIVA," +
				"NOTA_CONVA "+
				"FROM ENOC.ALUMNO_CURSO "+
				"WHERE CODIGO_PERSONAL = ? "+
				"AND CARGA_ID = ? AND BLOQUE_ID = TO_NUMBER(?,'99') "+orden;
			lista = enocJdbc.query(comando, new AlumnoCursoMapper(), codigoPersonal, cargaId, bloqueId);			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|getListAlumnoCargaBloque|:"+ex);
		}
		return lista;
	}
	
	public List<AlumnoCurso> getListAlumnoCargaPlan(String codigoPersonal, String cargaId, String planId, String orden ) {
		List<AlumnoCurso> lista	= new ArrayList<AlumnoCurso>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL,CARGA_ID, BLOQUE_ID,CURSO_CARGA_ID,CARRERA_ID,MODALIDAD_ID,"
					+ " PLAN_ID,CURSO_ID,NOMBRE_CURSO,CICLO AS CICLO,TO_CHAR(CREDITOS,'99.99') AS CREDITOS,HT,HP,"
					+ " TO_CHAR(NOTA_APROBATORIA,'999') AS NOTA_APROBATORIA,CURSO_ID2,NOMBRE_CURSO2,TO_CHAR(CREDITOS2,'99.99') AS CREDITOS2,TO_CHAR(HT2,'99') AS HT2,"
					+ " TO_CHAR(HP2,'99') AS HP2,NOTA,TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION,TIPOCAL_ID,COALESCE(TO_CHAR(NOTA_EXTRA,'999'),'0') AS NOTA_EXTRA,"
					+ " COALESCE(TO_CHAR(F_EXTRA,'DD/MM/YYYY'),'') AS F_EXTRA,CONVALIDACION,TITULO,TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO,GRUPO,MAESTRO,ESTADO,"
					+ " CORRECCION,OPTATIVA,NOTA_CONVA"
					+ " FROM ENOC.ALUMNO_CURSO"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND CARGA_ID = ?"
					+ " AND PLAN_ID = ? "+orden;
			lista = enocJdbc.query(comando, new AlumnoCursoMapper(), codigoPersonal, cargaId, planId);
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|getListAlumnoCargaPlan|:"+ex);
		}
		return lista;
	}
	
	public List<AlumnoCurso> getComponentesAlumno(  String codigoPersonal, String planId, String orden ) {
		List<AlumnoCurso> lista		= new ArrayList<AlumnoCurso>();
		try{
			String comando = "SELECT "+
				"CODIGO_PERSONAL, "+
				"CARGA_ID, "+
				" BLOQUE_ID, "+
				"CURSO_CARGA_ID, "+
				"CARRERA_ID, "+
				"MODALIDAD_ID, "+
				"PLAN_ID, "+
				"CURSO_ID, "+
				"NOMBRE_CURSO, "+
				"CICLO AS CICLO, "+
				"TO_CHAR(CREDITOS,'99.99') AS CREDITOS, "+
				"HT, "+
				"HP, "+
				"TO_CHAR(NOTA_APROBATORIA,'999') AS NOTA_APROBATORIA, "+
				"CURSO_ID2, "+
				"NOMBRE_CURSO2, "+
				"TO_CHAR(CREDITOS2,'99.99') AS CREDITOS2, "+
				"TO_CHAR(HT2,'99') AS HT2, "+
				"TO_CHAR(HP2,'99') AS HP2, "+
				"NOTA, "+
				"TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION, "+
				"TIPOCAL_ID, "+
				"COALESCE(TO_CHAR(NOTA_EXTRA,'999'),'0') AS NOTA_EXTRA, "+
				"COALESCE(TO_CHAR(F_EXTRA,'DD/MM/YYYY'),'') AS F_EXTRA, "+
				"CONVALIDACION, "+
				"TITULO, "+
				"TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO, "+
				"GRUPO, "+
				"MAESTRO, "+
				"ESTADO, "+				
				"CORRECCION, " +
				"OPTATIVA," +
				"NOTA_CONVA "+
				"FROM ENOC.ALUMNO_CURSO "+
				"WHERE CODIGO_PERSONAL = ? "+
				"AND PLAN_ID = ? "+
				"AND ENOC.TIPOCURSO_ID(CURSO_ID) = 3 AND TIPOCAL_ID = '1' "+orden;		
			lista = enocJdbc.query(comando, new AlumnoCursoMapper(), codigoPersonal, planId);			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|getComponentesAlumno|:"+ex);
		}
		return lista;
	}
	
	public HashMap<String,String> mapaNotaMateriasAlumno(String codigoPersonal, String planId) {
		HashMap<String,String> mapa		= new HashMap<String,String>();
		List<AlumnoCurso> lista			= new ArrayList<AlumnoCurso>();		
		try{
			String comando =  "SELECT "+
					"CODIGO_PERSONAL, "+
					"CARGA_ID, "+
					" BLOQUE_ID, "+
					"CURSO_CARGA_ID, "+
					"CARRERA_ID, "+
					"MODALIDAD_ID, "+
					"PLAN_ID, "+
					"CURSO_ID, "+
					"NOMBRE_CURSO, "+
					"CICLO AS CICLO, "+
					"TO_CHAR(CREDITOS,'99.99') AS CREDITOS, "+
					"HT, "+
					"HP, "+
					"TO_CHAR(NOTA_APROBATORIA,'999') AS NOTA_APROBATORIA, "+
					"CURSO_ID2, "+
					"NOMBRE_CURSO2, "+
					"TO_CHAR(CREDITOS2,'99.99') AS CREDITOS2, "+
					"TO_CHAR(HT2,'99') AS HT2, "+
					"TO_CHAR(HP2,'99') AS HP2, "+
					"LTRIM(NOTA) AS NOTA, "+
					"F_EVALUACION, "+
					"TIPOCAL_ID, "+
					"LTRIM(COALESCE(TO_CHAR(NOTA_EXTRA,'999'),'0')) AS NOTA_EXTRA, "+
					"COALESCE(TO_CHAR(F_EXTRA,'DD/MM/YYYY'),'') AS F_EXTRA, "+
					"CONVALIDACION, "+
					"TITULO, "+
					"TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO, "+
					"GRUPO, "+
					"MAESTRO, "+
					"ESTADO, "+				
					"CORRECCION, " +
					"OPTATIVA," +
					"NOTA_CONVA "
					+ " FROM ENOC.ALUMNO_CURSO "
					+ " WHERE CODIGO_PERSONAL = ?"
							+ " AND PLAN_ID = ?"
							+ " AND CURSO_ID IN(SELECT CURSO_ID"
								+ " FROM ENOC.MAPA_CURSO"
								+ " WHERE PLAN_ID = ?"
								+ " AND TIPOCURSO_ID IN(1,2,5,7,8))"
					+ " ORDER BY CICLO, CURSO_ID, TO_DATE(TO_CHAR(F_EVALUACION,'DD/MM/YYYY'),'DD/MM/YYYY') ASC";		
			lista = enocJdbc.query(comando, new AlumnoCursoMapper(), codigoPersonal, planId, planId);
			for (AlumnoCurso curso : lista){	
				String nota = "0";
				if(curso.getNotaExtra().equals("0")) {
					nota = curso.getNota();
				}else {
					nota = curso.getNotaExtra()+"*";
				}
				
				if(curso.getTipoCalId().equals("3")) {
					nota = "B";
				}
				mapa.put(curso.getNombreCurso(), nota);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|mapaNotaMateriasAlumno|:"+ex);
		}
		return mapa;
	}

	public TreeMap<String,String> getMapAlumPromCarga( String carga ) {
		TreeMap<String,String> treeProm	= new TreeMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();
		String comando					= "";
				
		try{
			comando = "SELECT DISTINCT(A.CODIGO_PERSONAL) AS LLAVE,"+
			" ENOC.ALUM_PROMEDIO_CARGA(A.CODIGO_PERSONAL,A.CARGA_ID, A.PLAN_ID) AS VALOR"+
			" FROM ENOC.ALUM_ESTADO A"+ 
			" WHERE CARGA_ID = ?"+
			" AND ESTADO = 'I'";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), carga);
						
			for (aca.Mapa map:lista){				
				treeProm.put( map.getLlave(), (String)map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoAlumnoRespUtil|getMapAlumPromCarga|:"+ex);
		}
		return treeProm;
	}
	
	
	public List<AlumnoCurso> getListAlumnosCarga(  String codigoPersonal, String cargaId, String orden ) {
		List<AlumnoCurso> lista		= new ArrayList<AlumnoCurso>();
		try{
			String comando = "SELECT "+
				"CODIGO_PERSONAL, "+
				"CARGA_ID, "+
				" BLOQUE_ID, "+
				"CURSO_CARGA_ID, "+
				"CARRERA_ID, "+
				"MODALIDAD_ID, "+
				"PLAN_ID, "+
				"CURSO_ID, "+
				"NOMBRE_CURSO, "+
				"CICLO AS CICLO, "+
				"TO_CHAR(CREDITOS,'99.99') AS CREDITOS, "+
				"HT, "+
				"HP, "+
				"TO_CHAR(NOTA_APROBATORIA,'99') AS NOTA_APROBATORIA, "+
				"CURSO_ID2, "+
				"NOMBRE_CURSO2, "+
				"TO_CHAR(CREDITOS2,'99.99') AS CREDITOS2, "+
				"TO_CHAR(HT2,'99') AS HT2, "+
				"TO_CHAR(HP2,'99') AS HP2, "+
				"NOTA, "+
				"TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION, "+
				"TIPOCAL_ID, "+
				"COALESCE(TO_CHAR(NOTA_EXTRA,'999'),'0') AS NOTA_EXTRA, "+
				"COALESCE(TO_CHAR(F_EXTRA,'DD/MM/YYYY'),'') AS F_EXTRA, "+
				"CONVALIDACION, "+
				"TITULO, "+
				"TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO, "+
				"GRUPO, "+
				"MAESTRO, "+
				"ESTADO, "+				
				"CORRECCION, " +
				"OPTATIVA," +
				"NOTA_CONVA "+
				"FROM ENOC.ALUMNO_CURSO "+
				"WHERE CODIGO_PERSONAL = ? "+
				"AND CARGA_ID = ? "+orden;			
			lista = enocJdbc.query(comando, new AlumnoCursoMapper(), codigoPersonal, cargaId);			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|getListAlumnoCarga|:"+ex);
		}
		return lista;
	}
	
	public List<AlumnoCurso> lisAlumnosCargayCarrera(  String cargaId, String carreraId, String tipoCal, String orden ) {
		List<AlumnoCurso> lista		= new ArrayList<AlumnoCurso>();
		String comando		= "";
		
		try{
			comando = "SELECT "+
				" DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL, "+
				" CARGA_ID, "+
				"  BLOQUE_ID, "+
				" CURSO_CARGA_ID, "+
				" CARRERA_ID, "+
				" MODALIDAD_ID, "+
				" PLAN_ID, "+
				" CURSO_ID, "+
				" NOMBRE_CURSO, "+
				" CICLO AS CICLO, "+
				" TO_CHAR(CREDITOS,'99.99') AS CREDITOS, "+
				" HT, "+
				" HP, "+
				" TO_CHAR(NOTA_APROBATORIA,'99') AS NOTA_APROBATORIA, "+
				" CURSO_ID2, "+
				" NOMBRE_CURSO2, "+
				" TO_CHAR(CREDITOS2,'99.99') AS CREDITOS2, "+
				" TO_CHAR(HT2,'99') AS HT2, "+
				" TO_CHAR(HP2,'99') AS HP2, "+
				" NOTA, "+
				" TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION, "+
				" TIPOCAL_ID, "+
				" COALESCE(TO_CHAR(NOTA_EXTRA,'999'),'0') AS NOTA_EXTRA, "+
				" COALESCE(TO_CHAR(F_EXTRA,'DD/MM/YYYY'),'') AS F_EXTRA, "+
				" CONVALIDACION, "+
				" TITULO, "+
				" TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO, "+
				" GRUPO, "+
				" MAESTRO, "+
				" ESTADO, "+				
				" CORRECCION, " +
				" OPTATIVA," +
				" NOTA_CONVA "+
				" FROM ENOC.ALUMNO_CURSO "+
				" WHERE CURSO_CARGA_ID LIKE '"+cargaId+"%' "+
				" AND CARRERA_ID = ? "+
				" AND TIPOCAL_ID IN ("+tipoCal+") "+orden;
			
			lista = enocJdbc.query(comando, new AlumnoCursoMapper(), carreraId);
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|getComponentesAlumno|:"+ex);
		}
		return lista;
	}
	
	public List<AlumnoCurso> lisAlumnosEnComponentes(  String cargaId, String tipos, String areas, String orden ) {
		List<AlumnoCurso> lista		= new ArrayList<AlumnoCurso>();
		String comando		= "";
		
		try{
			comando = "SELECT "+
				" DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL, "+
				" CARGA_ID, "+
				"  BLOQUE_ID, "+
				" CURSO_CARGA_ID, "+
				" CARRERA_ID, "+
				" MODALIDAD_ID, "+
				" PLAN_ID, "+
				" CURSO_ID, "+
				" NOMBRE_CURSO, "+
				" CICLO AS CICLO, "+
				" TO_CHAR(CREDITOS,'99.99') AS CREDITOS, "+
				" HT, "+
				" HP, "+
				" TO_CHAR(NOTA_APROBATORIA,'99') AS NOTA_APROBATORIA, "+
				" CURSO_ID2, "+
				" NOMBRE_CURSO2, "+
				" TO_CHAR(CREDITOS2,'99.99') AS CREDITOS2, "+
				" TO_CHAR(HT2,'99') AS HT2, "+
				" TO_CHAR(HP2,'99') AS HP2, "+
				" NOTA, "+
				" TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION, "+
				" TIPOCAL_ID, "+
				" COALESCE(TO_CHAR(NOTA_EXTRA,'999'),'0') AS NOTA_EXTRA, "+
				" COALESCE(TO_CHAR(F_EXTRA,'DD/MM/YYYY'),'') AS F_EXTRA, "+
				" CONVALIDACION, "+
				" TITULO, "+
				" TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO, "+
				" GRUPO, "+
				" MAESTRO, "+
				" ESTADO, "+				
				" CORRECCION, " +
				" OPTATIVA," +
				" NOTA_CONVA "+
				" FROM ENOC.ALUMNO_CURSO "+
				" WHERE CARGA_ID = ?"+
				" AND TIPOCAL_ID IN ("+tipos+")"+
				" AND CURSO_ID IN (SELECT CURSO_ID FROM ENOC.MAPA_CURSO WHERE AREA_ID IN ("+areas+")) "+orden; 
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new AlumnoCursoMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|getComponentesAlumno|:"+ex);
		}
		return lista;
	}
	
	public List<AlumnoCurso> lisAlumnosEnAptitud(  String cargaId, String materias, String orden ) {
		List<AlumnoCurso> lista		= new ArrayList<AlumnoCurso>();
		try{
			String comando = "SELECT "+
				" DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL, "+
				" CARGA_ID, "+
				"  BLOQUE_ID, "+
				" CURSO_CARGA_ID, "+
				" CARRERA_ID, "+
				" MODALIDAD_ID, "+
				" PLAN_ID, "+
				" CURSO_ID, "+
				" NOMBRE_CURSO, "+
				" CICLO AS CICLO, "+
				" TO_CHAR(CREDITOS,'99.99') AS CREDITOS, "+
				" HT, "+
				" HP, "+
				" TO_CHAR(NOTA_APROBATORIA,'99') AS NOTA_APROBATORIA, "+
				" CURSO_ID2, "+
				" NOMBRE_CURSO2, "+
				" TO_CHAR(CREDITOS2,'99.99') AS CREDITOS2, "+
				" TO_CHAR(HT2,'99') AS HT2, "+
				" TO_CHAR(HP2,'99') AS HP2, "+
				" NOTA, "+
				" TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION, "+
				" TIPOCAL_ID, "+
				" COALESCE(TO_CHAR(NOTA_EXTRA,'999'),'0') AS NOTA_EXTRA, "+
				" COALESCE(TO_CHAR(F_EXTRA,'DD/MM/YYYY'),'') AS F_EXTRA, "+
				" CONVALIDACION, "+
				" TITULO, "+
				" TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO, "+
				" GRUPO, "+
				" MAESTRO, "+
				" ESTADO, "+				
				" CORRECCION, " +
				" OPTATIVA," +
				" NOTA_CONVA "+
				" FROM ENOC.ALUMNO_CURSO "+
				" WHERE CARGA_ID = ?"+
				" AND CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.CARGA_ALUMNO WHERE CARGA_ID = ?)"+
				" AND SUBSTR(CURSO_ID,9,7) IN ("+materias+") "+orden; 
			Object[] parametros = new Object[] {cargaId, cargaId};
			lista = enocJdbc.query(comando, new AlumnoCursoMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|lisAlumnosEnAptitud|:"+ex);
		}
		return lista;
	}
	
	public List<AlumnoCurso> lisAlumnosEnPracticas(String cargaId, String bloques, String facultadId, String orden ) {
		List<AlumnoCurso> lista		= new ArrayList<AlumnoCurso>();
		try{
			String comando = "SELECT "+
				" DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL, "+
				" CARGA_ID, "+
				"  BLOQUE_ID, "+
				" CURSO_CARGA_ID, "+
				" CARRERA_ID, "+
				" MODALIDAD_ID, "+
				" PLAN_ID, "+
				" CURSO_ID, "+
				" NOMBRE_CURSO, "+
				" CICLO AS CICLO, "+
				" CREDITOS, "+
				" HT, "+
				" HP, "+
				" TO_CHAR(NOTA_APROBATORIA,'99') AS NOTA_APROBATORIA, "+
				" CURSO_ID2, "+
				" NOMBRE_CURSO2, "+
				" TO_CHAR(CREDITOS2,'99.99') AS CREDITOS2, "+
				" TO_CHAR(HT2,'99') AS HT2, "+
				" TO_CHAR(HP2,'99') AS HP2, "+
				" NOTA, "+
				" TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION, "+
				" TIPOCAL_ID, "+
				" COALESCE(TO_CHAR(NOTA_EXTRA,'999'),'0') AS NOTA_EXTRA, "+
				" COALESCE(TO_CHAR(F_EXTRA,'DD/MM/YYYY'),'') AS F_EXTRA, "+
				" CONVALIDACION, "+
				" TITULO, "+
				" TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO, "+
				" GRUPO, "+
				" MAESTRO, "+
				" ESTADO, "+				
				" CORRECCION, " +
				" OPTATIVA," +
				" NOTA_CONVA "+
				" FROM ENOC.ALUMNO_CURSO "+
				" WHERE CARGA_ID = ?"+
				" AND BLOQUE_ID IN("+bloques+") "+
				" AND ENOC.FACULTAD(CARRERA_ID) = ?"+
				" AND CURSO_ID IN (SELECT CURSO_ID FROM ENOC.MAPA_CURSO WHERE LABORATORIO = 'S') "+orden; 
			Object[] parametros = new Object[] {cargaId, facultadId};
			lista = enocJdbc.query(comando, new AlumnoCursoMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|lisAlumnosEnPracticas|:"+ex);
		}
		return lista;
	}
	
	public List<AlumnoCurso> lisAlumnosEnPracticas(String cargaId, String orden ) {
		List<AlumnoCurso> lista		= new ArrayList<AlumnoCurso>();
		try{
			String comando = "SELECT "+
					" DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL, "+
					" CARGA_ID, "+
					" BLOQUE_ID, "+
					" CURSO_CARGA_ID, "+
					" CARRERA_ID, "+
					" MODALIDAD_ID, "+
					" PLAN_ID, "+
					" CURSO_ID, "+
					" NOMBRE_CURSO, "+
					" CICLO AS CICLO, "+
					" CREDITOS, "+
					" HT, "+
					" HP, "+
					" TO_CHAR(NOTA_APROBATORIA,'99') AS NOTA_APROBATORIA, "+
					" CURSO_ID2, "+
					" NOMBRE_CURSO2, "+
					" TO_CHAR(CREDITOS2,'99.99') AS CREDITOS2, "+
					" TO_CHAR(HT2,'99') AS HT2, "+
					" TO_CHAR(HP2,'99') AS HP2, "+
					" NOTA, "+
					" TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION, "+
					" TIPOCAL_ID, "+
					" COALESCE(TO_CHAR(NOTA_EXTRA,'999'),'0') AS NOTA_EXTRA, "+
					" COALESCE(TO_CHAR(F_EXTRA,'DD/MM/YYYY'),'') AS F_EXTRA, "+
					" CONVALIDACION, "+
					" TITULO, "+
					" TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO, "+
					" GRUPO, "+
					" MAESTRO, "+
					" ESTADO, "+				
					" CORRECCION, " +
					" OPTATIVA," +
					" NOTA_CONVA "+
					" FROM ENOC.ALUMNO_CURSO "+
					" WHERE CARGA_ID = ?"+
					" AND CURSO_ID IN (SELECT CURSO_ID FROM ENOC.MAPA_CURSO WHERE LABORATORIO = 'S') "+orden; 
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new AlumnoCursoMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|lisAlumnosEnPracticas|:"+ex);
		}
		return lista;
	}
	
	public List<AlumnoCurso> lisAptitudSinGrupo(String cargaId, String clave, String orden ) {
		List<AlumnoCurso> lista		= new ArrayList<AlumnoCurso>();
		try{
			String comando = "SELECT "+
					" DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL, "+
					" CARGA_ID, "+
					"  BLOQUE_ID, "+
					" CURSO_CARGA_ID, "+
					" CARRERA_ID, "+
					" MODALIDAD_ID, "+
					" PLAN_ID, "+
					" CURSO_ID, "+
					" NOMBRE_CURSO, "+
					" CICLO AS CICLO, "+
					" TO_CHAR(CREDITOS,'99.99') AS CREDITOS, "+
					" HT, "+
					" HP, "+
					" TO_CHAR(NOTA_APROBATORIA,'99') AS NOTA_APROBATORIA, "+
					" CURSO_ID2, "+
					" NOMBRE_CURSO2, "+
					" TO_CHAR(CREDITOS2,'99.99') AS CREDITOS2, "+
					" TO_CHAR(HT2,'99') AS HT2, "+
					" TO_CHAR(HP2,'99') AS HP2, "+
					" NOTA, "+
					" TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION, "+
					" TIPOCAL_ID, "+
					" COALESCE(TO_CHAR(NOTA_EXTRA,'999'),'0') AS NOTA_EXTRA, "+
					" COALESCE(TO_CHAR(F_EXTRA,'DD/MM/YYYY'),'') AS F_EXTRA, "+
					" CONVALIDACION, "+
					" TITULO, "+
					" TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO, "+
					" GRUPO, "+
					" MAESTRO, "+
					" ESTADO, "+				
					" CORRECCION, " +
					" OPTATIVA," +
					" NOTA_CONVA "+
					" FROM ENOC.ALUMNO_CURSO "+
					" WHERE CARGA_ID = ?"+
					" AND SUBSTR(CURSO_ID,9,7) = ?"+
					" AND TIPOCAL_ID IN ('I','1','2','4','5','6')"+
					" AND CODIGO_PERSONAL||CURSO_CARGA_ID NOT IN (SELECT CODIGO_PERSONAL||CURSO_CARGA_ID FROM ENOC.APFISICA_ALUMNO WHERE ESTADO = 'A') "+orden; 
			Object[] parametros = new Object[] {cargaId, clave};
			lista = enocJdbc.query(comando, new AlumnoCursoMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|lisAptitudSinGrupo|:"+ex);
		}
		return lista;
	}
	
	public List<AlumnoCurso> getReprobadosCargas(  String cursoCargaId, String carreraId, String orden ) {
		List<AlumnoCurso> lista		= new ArrayList<AlumnoCurso>();
		String comando		= "";
		
		try{
			comando = "SELECT "+
				" DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL, "+
				" CARGA_ID, "+
				"  BLOQUE_ID, "+
				" CURSO_CARGA_ID, "+
				" CARRERA_ID, "+
				" MODALIDAD_ID, "+
				" PLAN_ID, "+
				" CURSO_ID, "+
				" NOMBRE_CURSO, "+
				" CICLO AS CICLO, "+
				" TO_CHAR(CREDITOS,'99.99') AS CREDITOS, "+
				" HT, "+
				" HP, "+
				" TO_CHAR(NOTA_APROBATORIA,'99') AS NOTA_APROBATORIA, "+
				" CURSO_ID2, "+
				" NOMBRE_CURSO2, "+
				" TO_CHAR(CREDITOS2,'99.99') AS CREDITOS2, "+
				" TO_CHAR(HT2,'99') AS HT2, "+
				" TO_CHAR(HP2,'99') AS HP2, "+
				" NOTA, "+
				" TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION, "+
				" TIPOCAL_ID, "+
				" COALESCE(TO_CHAR(NOTA_EXTRA,'999'),'0') AS NOTA_EXTRA, "+
				" COALESCE(TO_CHAR(F_EXTRA,'DD/MM/YYYY'),'') AS F_EXTRA, "+
				" CONVALIDACION, "+
				" TITULO, "+
				" TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO, "+
				" GRUPO, "+
				" MAESTRO, "+
				" ESTADO, "+				
				" CORRECCION, " +
				" OPTATIVA," +
				" NOTA_CONVA "+
				" FROM ENOC.ALUMNO_CURSO "+
				" WHERE CURSO_CARGA_ID LIKE '"+cursoCargaId+"%' "+
				" AND CARRERA_ID = ? "+
				" AND ENOC.TIPOCURSO_ID(CURSO_ID) NOT IN (3,4,5) AND TIPOCAL_ID IN ('2','4') "+orden;
			
			lista = enocJdbc.query(comando, new AlumnoCursoMapper(), carreraId);
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|getReprobadosCargas|:"+ex);
		}
		return lista;
	}
	
	public int numReprobadosxCarga(String cursoCargaId, String carreraId) {
		int lises=0;
		try{
			String comando = " SELECT COUNT (DISTINCT(CODIGO_PERSONAL)) FROM ENOC.ALUMNO_CURSO " +
					" WHERE CURSO_CARGA_ID LIKE '"+cursoCargaId+"%' " +
					" AND CARRERA_ID = ? " +
					" AND TIPOCAL_ID IN ('2','4') " +
					" AND ENOC.TIPOCURSO_ID(CURSO_ID) NOT IN(3,4,5)";	
			Object[] parametros = new Object[] {carreraId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				lises = enocJdbc.queryForObject(comando, Integer.class, parametros);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|numReprobadosxCarga|:"+ex);
		}
		return lises;
	}
	
	public int getNumMaterias(String codigoPersonal, String planId, String tipos) {		
		String comando		= "";
		int total 			= 0;
		
		try{
			comando = " SELECT COUNT(DISTINCT(CODIGO_PERSONAL)) FROM ENOC.ALUMNO_CURSO"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND PLAN_ID = ?"
					+ " AND TIPOCAL_ID IN ("+tipos+")";
			Object[] parametros = new Object[] {codigoPersonal,planId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				total = enocJdbc.queryForObject(comando, Integer.class, parametros);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|getNumMaterias|:"+ex);
		}
		return total;
	}
	
	public HashMap<String,AlumnoCurso> mapaNotas( String cargaId, String carreraId ) {			
		HashMap<String,AlumnoCurso> mapa	= new HashMap<String,AlumnoCurso>();
		List<AlumnoCurso> lista		= new ArrayList<AlumnoCurso>();		
		try{
			String comando = "SELECT "
				+ " DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL,"
				+ " CARGA_ID,"
				+ "  BLOQUE_ID,"
				+ " CURSO_CARGA_ID,"
				+ " CARRERA_ID,"
				+ " MODALIDAD_ID,"
				+ " PLAN_ID,"
				+ " CURSO_ID,"
				+ " NOMBRE_CURSO,"
				+ " CICLO AS CICLO,"
				+ " TO_CHAR(CREDITOS,'99.99') AS CREDITOS,"
				+ " HT,"
				+ " HP,"
				+ " TO_CHAR(NOTA_APROBATORIA,'99') AS NOTA_APROBATORIA,"
				+ " CURSO_ID2,"
				+ " NOMBRE_CURSO2,"
				+ " TO_CHAR(CREDITOS2,'99.99') AS CREDITOS2,"
				+ " TO_CHAR(HT2,'99') AS HT2,"
				+ " TO_CHAR(HP2,'99') AS HP2,"
				+ " NOTA,"
				+ " TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION,"
				+ " TIPOCAL_ID,"
				+ " COALESCE(TO_CHAR(NOTA_EXTRA,'999'),'0') AS NOTA_EXTRA,"
				+ " COALESCE(TO_CHAR(F_EXTRA,'DD/MM/YYYY'),'') AS F_EXTRA,"
				+ " CONVALIDACION,"
				+ " TITULO,"
				+ " TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO,"
				+ " GRUPO,"
				+ " MAESTRO,"
				+ " ESTADO,"
				+ " CORRECCION,"
				+ " OPTATIVA,"
				+ " NOTA_CONVA"
				+ " FROM ENOC.ALUMNO_CURSO"
				+ " WHERE CARGA_ID = ?"
				+ " AND CARRERA_ID = ?";
			Object[] parametros = new Object[] {cargaId, carreraId};
			lista = enocJdbc.query(comando, new AlumnoCursoMapper(), parametros);			
			for (AlumnoCurso alumnoCurso : lista){				
				mapa.put( alumnoCurso.getCodigoPersonal()+alumnoCurso.getCursoId(), alumnoCurso);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|mapaNotas|:"+ex);
		}
		return mapa;
	}
/*	
	public List<ArrayList<String>> getListaRemediales( String orden) {
		List<ArrayList<String>> lisCurso = new ArrayList<ArrayList<String>>();
		String comando	= "";
		
		try{
			comando = "	SELECT CODIGO_PERSONAL," +
					"	(SELECT CURSO_CLAVE FROM ENOC.MAPA_CURSO WHERE CURSO_ID = A.CURSO_ID) AS CURSO_CLAVE," +
					"	(SELECT NOMBRE_TIPOCAL FROM ENOC.CAT_TIPOCAL WHERE TIPOCAL_ID = A.TIPOCAL_ID) AS NOMBRE_TIPOCAL," +
					"	(SELECT PLAN_ID FROM ENOC.MAPA_CURSO WHERE CURSO_ID = A.CURSO_ID) AS PLAN_ID" +
					"	FROM ENOC.KRDX_CURSO_ACT A WHERE A.CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.INSCRITOS)" +
					"	UNION" +
					"	SELECT CODIGO_PERSONAL," +
					"	(SELECT CURSO_CLAVE FROM ENOC.MAPA_CURSO WHERE CURSO_ID = B.CURSO_ID) AS CURSO_CLAVE," +
					"	(SELECT NOMBRE_TIPOCAL FROM ENOC.CAT_TIPOCAL WHERE TIPOCAL_ID = B.TIPOCAL_ID) AS NOMBRE_TIPOCAL," +
					"	(SELECT PLAN_ID FROM ENOC.MAPA_CURSO WHERE CURSO_ID = B.CURSO_ID) AS PLAN_ID" +
					"	FROM ENOC.KRDX_CURSO_IMP B" +
					"	WHERE B.CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.INSCRITOS) "+ orden;
			
			rs = st.executeQuery(comando);
			while(rs.next()){
				ArrayList<String> arr = new ArrayList<String>();
				arr.add(rs.getString("CODIGO_PERSONAL"));
				arr.add(rs.getString("CURSO_CLAVE"));
				arr.add(rs.getString("NOMBRE_TIPOCAL"));
				arr.add(rs.getString("PLAN_ID"));
				lisCurso.add(arr);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|getListaRemediales|:"+ex);
		}
		return lisCurso;
	}
*/	
	
	public List<String> getListaAlumPlan( String planId, String orden) {
		List<String> lista 		= new ArrayList<String>();
		List<aca.Mapa> lista2	= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT"
					+ " DISTINCT(CODIGO_PERSONAL) AS LLAVE,"
					+ " ENOC.ALUM_APELLIDO(CODIGO_PERSONAL) AS VALOR"
					+ " FROM ENOC.KRDX_CURSO_ACT KCA"
					+ " WHERE (SELECT PLAN_ID FROM ENOC.MAPA_CURSO WHERE CURSO_ID = KCA.CURSO_ID) = ?"
					+ " UNION SELECT DISTINCT(CODIGO_PERSONAL) AS LLAVE,"
					+ " ENOC.ALUM_APELLIDO(CODIGO_PERSONAL) AS VALOR"
					+ " FROM ENOC.KRDX_CURSO_IMP KCI"
					+ " WHERE (SELECT PLAN_ID FROM ENOC.MAPA_CURSO WHERE CURSO_ID = KCI.CURSO_ID) = ? "+ orden;			
			lista2 = enocJdbc.query(comando, new aca.MapaMapper(), planId, planId);			
			for (aca.Mapa map : lista2){		
				lista.add(map.getLlave());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|getListaAlumPlan|:"+ex);
		}
		return lista;
	}
	
	public List<String> getListaAlumPlan(String cargaId, String planId, String orden) {
		List<String> lista = new ArrayList<String>();
		List<aca.Mapa> lista2	= new ArrayList<aca.Mapa>();		
		try{
			String comando = "	SELECT"
					+ " DISTINCT(CODIGO_PERSONAL) AS LLAVE,"
					+ " ENOC.ALUM_APELLIDO(CODIGO_PERSONAL) AS VALOR"
					+ " FROM ENOC.KRDX_CURSO_ACT KCA"
					+ " WHERE (SELECT CARGA_ID FROM ENOC.CARGA_GRUPO WHERE CURSO_CARGA_ID = KCA.CURSO_CARGA_ID) = ?"
					+ " AND (SELECT PLAN_ID FROM ENOC.MAPA_CURSO WHERE CURSO_ID = KCA.CURSO_ID) = ? " +orden;			
			lista2 = enocJdbc.query(comando, new aca.MapaMapper(), cargaId, planId);			
			for (aca.Mapa map : lista2){		
				lista.add(map.getLlave());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|getListaAlumPlan|:"+ex);
		}
		return lista;
	}

	public List<AlumnoCurso> lisAlumnoPlanCarga(String codigoPersonal, String planId, String cargaId, String orden) {
		List<AlumnoCurso> lista = new ArrayList<AlumnoCurso>();		
		try{
			String comando = "SELECT "
				+ " CODIGO_PERSONAL,"
				+ " CARGA_ID,"
				+ " BLOQUE_ID,"
				+ " CURSO_CARGA_ID,"
				+ " CARRERA_ID,"
				+ " MODALIDAD_ID,"
				+ " PLAN_ID,"
				+ " CURSO_ID,"
				+ " NOMBRE_CURSO,"
				+ " COALESCE(CICLO,0) AS CICLO,"
				+ " COALESCE(CREDITOS,0) AS CREDITOS,"
				+ " COALESCE(HT,0) AS HT,"
				+ " COALESCE(HP,0) AS HP,"
				+ " COALESCE(NOTA_APROBATORIA,0) AS NOTA_APROBATORIA,"
				+ " CURSO_ID2,"
				+ " NOMBRE_CURSO2,"
				+ " COALESCE(CREDITOS2,0) AS CREDITOS2,"
				+ " COALESCE(HT2,0) AS HT2,"
				+ " COALESCE(HP2,0) AS HP2,"
				+ " COALESCE(NOTA,0) AS NOTA,"
				+ " TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION,"
				+ " TIPOCAL_ID,"
				+ " COALESCE(NOTA_EXTRA,0) AS NOTA_EXTRA,"
				+ " TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA,"
				+ " CONVALIDACION,"
				+ " TITULO,"
				+ " TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO,"
				+ " GRUPO,"
				+ " MAESTRO,"
				+ " ESTADO,"
				+ " CORRECCION,"
				+ " OPTATIVA,"
				+ " NOTA_CONVA"
				+ " FROM ENOC.ALUMNO_CURSO"
				+ " WHERE CODIGO_PERSONAL = ?"
				+ " AND PLAN_ID = ?"
				+ " AND CARGA_ID = ? "+orden;			
			Object[] parametros = new Object[] {codigoPersonal, planId, cargaId};	
			lista = enocJdbc.query(comando, new AlumnoCursoMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|lisAlumnoPlanCarga|:"+ex);
		}
		return lista;
	}
	
	public List<AlumnoCurso> lisAlumnosEnMateria(String cursoId, String orden) {
		List<AlumnoCurso> lista = new ArrayList<AlumnoCurso>();		
		try{
			String comando = "SELECT "
				+ " CODIGO_PERSONAL,"
				+ " CARGA_ID,"
				+ " BLOQUE_ID,"
				+ " CURSO_CARGA_ID,"
				+ " CARRERA_ID,"
				+ " MODALIDAD_ID,"
				+ " PLAN_ID,"
				+ " CURSO_ID,"
				+ " NOMBRE_CURSO,"
				+ " COALESCE(CICLO,0) AS CICLO,"
				+ " COALESCE(CREDITOS,0) AS CREDITOS,"
				+ " COALESCE(HT,0) AS HT,"
				+ " COALESCE(HP,0) AS HP,"
				+ " COALESCE(NOTA_APROBATORIA,0) AS NOTA_APROBATORIA,"
				+ " CURSO_ID2,"
				+ " NOMBRE_CURSO2,"
				+ " COALESCE(CREDITOS2,0) AS CREDITOS2,"
				+ " COALESCE(HT2,0) AS HT2,"
				+ " COALESCE(HP2,0) AS HP2,"
				+ " COALESCE(NOTA,0) AS NOTA,"
				+ " TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION,"
				+ " TIPOCAL_ID,"
				+ " COALESCE(NOTA_EXTRA,0) AS NOTA_EXTRA,"
				+ " TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA,"
				+ " CONVALIDACION,"
				+ " TITULO,"
				+ " TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO,"
				+ " GRUPO,"
				+ " MAESTRO,"
				+ " ESTADO,"
				+ " CORRECCION,"
				+ " OPTATIVA,"
				+ " NOTA_CONVA"
				+ " FROM ENOC.ALUMNO_CURSO"
				+ " WHERE CURSO_ID = ? "+orden;			
			Object[] parametros = new Object[] { cursoId};	
			lista = enocJdbc.query(comando, new AlumnoCursoMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|lisAlumnosEnMateria|:"+ex);
		}
		return lista;
	}
	
	public List<AlumnoCurso> lisMateriasPorCargayBloque(String codigoPersonal, String cargaId, String bloqueId, String tipos, String orden) {
		List<AlumnoCurso> lista = new ArrayList<AlumnoCurso>();		
		try{
			String comando = "SELECT "
				+ " CODIGO_PERSONAL,"
				+ " CARGA_ID,"
				+ " BLOQUE_ID,"
				+ " CURSO_CARGA_ID,"
				+ " CARRERA_ID,"
				+ " MODALIDAD_ID,"
				+ " PLAN_ID,"
				+ " CURSO_ID,"
				+ " NOMBRE_CURSO,"
				+ " COALESCE(CICLO,0) AS CICLO,"
				+ " COALESCE(CREDITOS,0) AS CREDITOS,"
				+ " COALESCE(HT,0) AS HT,"
				+ " COALESCE(HP,0) AS HP,"
				+ " COALESCE(NOTA_APROBATORIA,0) AS NOTA_APROBATORIA,"
				+ " CURSO_ID2,"
				+ " NOMBRE_CURSO2,"
				+ " COALESCE(CREDITOS2,0) AS CREDITOS2,"
				+ " COALESCE(HT2,0) AS HT2,"
				+ " COALESCE(HP2,0) AS HP2,"
				+ " COALESCE(NOTA,0) AS NOTA,"
				+ " TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION,"
				+ " TIPOCAL_ID,"
				+ " COALESCE(NOTA_EXTRA,0) AS NOTA_EXTRA,"
				+ " TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA,"
				+ " CONVALIDACION,"
				+ " TITULO,"
				+ " TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO,"
				+ " GRUPO,"
				+ " MAESTRO,"
				+ " ESTADO,"
				+ " CORRECCION,"
				+ " OPTATIVA,"
				+ " NOTA_CONVA"
				+ " FROM ENOC.ALUMNO_CURSO"
				+ " WHERE CODIGO_PERSONAL = ?"				
				+ " AND CARGA_ID = ?"
				+ " AND BLOQUE_ID = TO_NUMBER(?,'99')"
				+ " AND TIPOCAL_ID IN ("+tipos+") "+orden;			
			Object[] parametros = new Object[] {codigoPersonal, cargaId, bloqueId};			
			lista = enocJdbc.query(comando, new AlumnoCursoMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|lisMateriasPorCargayBloque|:"+ex);
		}
		return lista;
	}
	
	public List<AlumnoCurso> lisMateriasBaja(String codigoPersonal, String solicitudId, String orden) {
		List<AlumnoCurso> lista = new ArrayList<AlumnoCurso>();		
		try{
			String comando = "SELECT "
					+ " CODIGO_PERSONAL,"
					+ " CARGA_ID,"
					+ " BLOQUE_ID,"
					+ " CURSO_CARGA_ID,"
					+ " CARRERA_ID,"
					+ " MODALIDAD_ID,"
					+ " PLAN_ID,"
					+ " CURSO_ID,"
					+ " NOMBRE_CURSO,"
					+ " COALESCE(CICLO,0) AS CICLO,"
					+ " COALESCE(CREDITOS,0) AS CREDITOS,"
					+ " COALESCE(HT,0) AS HT,"
					+ " COALESCE(HP,0) AS HP,"
					+ " COALESCE(NOTA_APROBATORIA,0) AS NOTA_APROBATORIA,"
					+ " CURSO_ID2,"
					+ " NOMBRE_CURSO2,"
					+ " COALESCE(CREDITOS2,0) AS CREDITOS2,"
					+ " COALESCE(HT2,0) AS HT2,"
					+ " COALESCE(HP2,0) AS HP2,"
					+ " COALESCE(NOTA,0) AS NOTA,"
					+ " TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION,"
					+ " TIPOCAL_ID,"
					+ " COALESCE(NOTA_EXTRA,0) AS NOTA_EXTRA,"
					+ " TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA,"
					+ " CONVALIDACION,"
					+ " TITULO,"
					+ " TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO,"
					+ " GRUPO,"
					+ " MAESTRO,"
					+ " ESTADO,"
					+ " CORRECCION,"
					+ " OPTATIVA,"
					+ " NOTA_CONVA"
					+ " FROM ENOC.ALUMNO_CURSO"
					+ " WHERE CODIGO_PERSONAL = ?"				
					+ " AND CURSO_CARGA_ID IN(SELECT CURSO_CARGA_ID FROM ENOC.CAMBIO_MATERIA WHERE SOLICITUD_ID = TO_NUMBER(?,'99999') AND TIPO = 'B') "+orden;			
			Object[] parametros = new Object[] {codigoPersonal, solicitudId};			
			lista = enocJdbc.query(comando, new AlumnoCursoMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|lisMateriasBaja|:"+ex);
		}
		return lista;
	}
	
	public String getMaestrosAlumno( String codigoPersonal, String cargaId){
		List<String> lista = new ArrayList<String>();
		String maestros	= "'*'";		
		try{
			String comando = "	SELECT DISTINCT(MAESTRO) FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, cargaId};
			lista = enocJdbc.queryForList(comando, String.class, parametros);
			for (String codigo : lista) {
				maestros += ",'"+codigo+"'"; 
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|getMaestrosAlumno|:"+ex);
		}
		return maestros;
	}
	
	public HashMap<String,String> getMapCreditos( String planId, String codigoPersonal ) {		
		HashMap<String,String> mapa		= new HashMap<String,String>();
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();
		String comando					= "";
				
		try{
			comando = " SELECT CICLO AS LLAVE, SUM(CREDITOS) AS VALOR"
					+ " FROM ENOC.ALUMNO_CURSO "
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND PLAN_ID = ?"
					+ " AND TIPOCAL_ID = '1'"
					+ " GROUP BY PLAN_ID, CICLO ";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), codigoPersonal, planId);
			for (aca.Mapa map:lista){			
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|getMapCreditos|:"+ex);
		}
		return mapa;
	}
	
	/*  */
	public HashMap<String,String> mapCredPorCarga( String codigoPersonal, String tipoCalId) {
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa		= new HashMap<String,String>();
		String comando					= "";
				
		try{
			comando = " SELECT CARGA_ID AS LLAVE, BLOQUE_ID AS LLAVE, SUM(CREDITOS) AS VALOR FROM ENOC.ALUMNO_CURSO "
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND TIPOCAL_ID IN ("+tipoCalId+")"
					+ " GROUP BY CARGA_ID, BLOQUE_ID";	
			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), codigoPersonal);
			for (aca.Mapa map:lista){			
				mapa.put(map.getLlave(), (String)map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|getMapCreditos|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,AlumnoCurso> mapaCursosAcreditados( String codigoPersonal, String planId, String tipo) {
		List<AlumnoCurso> lista	= new ArrayList<AlumnoCurso>();
		HashMap<String,AlumnoCurso> mapa		= new HashMap<String,AlumnoCurso>();
		String comando					= "";
		
		try{
			comando = " SELECT CODIGO_PERSONAL,"
					+ " CARGA_ID, "
					+ "  BLOQUE_ID,"
					+ " CURSO_CARGA_ID,"
					+ " CARRERA_ID,"
					+ " MODALIDAD_ID,"
					+ " PLAN_ID,"
					+ " CURSO_ID,"
					+ " NOMBRE_CURSO,"
					+ " CICLO AS CICLO,"
					+ " TO_CHAR(CREDITOS,'99.99') AS CREDITOS,"
					+ " HT,"
					+ " HP,"
					+ " TO_CHAR(NOTA_APROBATORIA,'999') AS NOTA_APROBATORIA,"
					+ " CURSO_ID2,"
					+ " NOMBRE_CURSO2,"
					+ " TO_CHAR(CREDITOS2,'99.99') AS CREDITOS2,"
					+ " TO_CHAR(HT2,'99') AS HT2,"
					+ " TO_CHAR(HP2,'99') AS HP2,"
					+ " NOTA,"
					+ " TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION,"
					+ " TIPOCAL_ID,"
					+ " COALESCE(TO_CHAR(NOTA_EXTRA,'999'),'0') AS NOTA_EXTRA,"
					+ " COALESCE(TO_CHAR(F_EXTRA,'DD/MM/YYYY'),'') AS F_EXTRA,"
					+ " CONVALIDACION,"
					+ " TITULO,"
					+ " TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO,"
					+ " GRUPO,"
					+ " MAESTRO,"
					+ " ESTADO,"
					+ " CORRECCION,"
					+ " OPTATIVA,"
					+ " NOTA_CONVA"
					+ " FROM ENOC.ALUMNO_CURSO"
					+ " WHERE CODIGO_PERSONAL = ? "
					+ " AND PLAN_ID = ? "
					+ " AND TIPOCAL_ID = ?";
			
			Object[] parametros = new Object[] {codigoPersonal, planId, tipo};
			lista = enocJdbc.query(comando, new AlumnoCursoMapper(), parametros);
			for (AlumnoCurso alumnoCurso : lista){			
				mapa.put(alumnoCurso.getCursoId(), alumnoCurso);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|mapaCursosAcreditados|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,AlumnoCurso> mapaCursosDelAlumno( String codigoPersonal, String planId) {
		List<AlumnoCurso> lista				= new ArrayList<AlumnoCurso>();
		HashMap<String,AlumnoCurso> mapa	= new HashMap<String,AlumnoCurso>();
		String comando						= "";		
		try{
			comando = " SELECT CODIGO_PERSONAL,"
					+ " CARGA_ID, "
					+ "  BLOQUE_ID,"
					+ " CURSO_CARGA_ID,"
					+ " CARRERA_ID,"
					+ " MODALIDAD_ID,"
					+ " PLAN_ID,"
					+ " CURSO_ID,"
					+ " NOMBRE_CURSO,"
					+ " CICLO AS CICLO,"
					+ " TO_CHAR(CREDITOS,'99.99') AS CREDITOS,"
					+ " HT,"
					+ " HP,"
					+ " TO_CHAR(NOTA_APROBATORIA,'999') AS NOTA_APROBATORIA,"
					+ " CURSO_ID2,"
					+ " NOMBRE_CURSO2,"
					+ " TO_CHAR(CREDITOS2,'99.99') AS CREDITOS2,"
					+ " TO_CHAR(HT2,'99') AS HT2,"
					+ " TO_CHAR(HP2,'99') AS HP2,"
					+ " NOTA,"
					+ " TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION,"
					+ " TIPOCAL_ID,"
					+ " COALESCE(TO_CHAR(NOTA_EXTRA,'999'),'0') AS NOTA_EXTRA,"
					+ " COALESCE(TO_CHAR(F_EXTRA,'DD/MM/YYYY'),'') AS F_EXTRA,"
					+ " CONVALIDACION,"
					+ " TITULO,"
					+ " TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO,"
					+ " GRUPO,"
					+ " MAESTRO,"
					+ " ESTADO,"
					+ " CORRECCION,"
					+ " OPTATIVA,"
					+ " NOTA_CONVA"
					+ " FROM ENOC.ALUMNO_CURSO"
					+ " WHERE CODIGO_PERSONAL = ? "
					+ " AND PLAN_ID = ?";
			
			Object[] parametros = new Object[] {codigoPersonal, planId};
			lista = enocJdbc.query(comando, new AlumnoCursoMapper(), parametros);
			for (AlumnoCurso alumnoCurso : lista){			
				mapa.put(alumnoCurso.getCursoCargaId(), alumnoCurso);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|mapaCursosDelAlumno|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,AlumnoCurso> mapaCursosAlumno( String codigoPersonal, String planId) {
		List<AlumnoCurso> lista				= new ArrayList<AlumnoCurso>();
		HashMap<String,AlumnoCurso> mapa	= new HashMap<String,AlumnoCurso>();
		String comando						= "";		
		try{
			comando = " SELECT CODIGO_PERSONAL,"
					+ " CARGA_ID, "
					+ " BLOQUE_ID,"
					+ " CURSO_CARGA_ID,"
					+ " CARRERA_ID,"
					+ " MODALIDAD_ID,"
					+ " PLAN_ID,"
					+ " CURSO_ID,"
					+ " NOMBRE_CURSO,"
					+ " CICLO AS CICLO,"
					+ " TO_CHAR(CREDITOS,'99.99') AS CREDITOS,"
					+ " HT,"
					+ " HP,"
					+ " TO_CHAR(NOTA_APROBATORIA,'999') AS NOTA_APROBATORIA,"
					+ " CURSO_ID2,"
					+ " NOMBRE_CURSO2,"
					+ " TO_CHAR(CREDITOS2,'99.99') AS CREDITOS2,"
					+ " TO_CHAR(HT2,'99') AS HT2,"
					+ " TO_CHAR(HP2,'99') AS HP2,"
					+ " NOTA,"
					+ " TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION,"
					+ " TIPOCAL_ID,"
					+ " COALESCE(TO_CHAR(NOTA_EXTRA,'999'),'0') AS NOTA_EXTRA,"
					+ " COALESCE(TO_CHAR(F_EXTRA,'DD/MM/YYYY'),'') AS F_EXTRA,"
					+ " CONVALIDACION,"
					+ " TITULO,"
					+ " TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO,"
					+ " GRUPO,"
					+ " MAESTRO,"
					+ " ESTADO,"
					+ " CORRECCION,"
					+ " OPTATIVA,"
					+ " NOTA_CONVA"
					+ " FROM ENOC.ALUMNO_CURSO"
					+ " WHERE CODIGO_PERSONAL = ? "
					+ " AND PLAN_ID = ?";
			
			Object[] parametros = new Object[] {codigoPersonal, planId};
			lista = enocJdbc.query(comando, new AlumnoCursoMapper(), parametros);
			for (AlumnoCurso alumnoCurso : lista){			
				mapa.put(alumnoCurso.getCursoId(), alumnoCurso);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|mapaCursosAlumno|:"+ex);
		}
		return mapa;
	}
	
	// 
	public HashMap<String,String> mapAlumPromCarga( String carga ) {
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa	= new HashMap<String,String>();
		try{
			String comando = "SELECT DISTINCT(A.CODIGO_PERSONAL) AS LLAVE,"+
			" ENOC.ALUM_PROMEDIO_CARGA(A.CODIGO_PERSONAL,A.CARGA_ID, A.PLAN_ID) AS VALOR"+
			" FROM ENOC.ALUM_ESTADO A"+ 
			" WHERE CARGA_ID = ?"+
			" AND ESTADO = 'I'";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), carga);
			for (aca.Mapa map:lista){			
				mapa.put(map.getLlave(), (String)map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|mapAlumPromCarga|:"+ex);
		}
		return mapa;
	}	
	
	public HashMap<String,String> mapAlumPorMaestro( String cargaId, String estado) {
		HashMap<String,String> mapa	= new HashMap<String,String>();
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();		
		try{
			String comando = " SELECT MAESTRO AS LLAVE, COUNT(CODIGO_PERSONAL) AS VALOR "
					+ " FROM ENOC.ALUMNO_CURSO"
					+ " WHERE CARGA_ID = ?"
					+ " AND TIPOCAL_ID IN ("+estado+")"
					+ " GROUP BY MAESTRO";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), cargaId);
			for (aca.Mapa map:lista){			
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|mapAlumPorMaestro|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,String> mapAlumPorMaestro( String cargas, String modalidades, String estados) {
		HashMap<String,String> mapa	= new HashMap<String,String>();
		String comando					= "";
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();
		
		try{
			comando = " SELECT MAESTRO AS LLAVE, COUNT(CODIGO_PERSONAL) AS VALOR FROM ENOC.ALUMNO_CURSO"
					+ " WHERE CARGA_ID IN("+cargas+")"
					+ " AND MODALIDAD_ID IN("+modalidades+")"
					+ " AND TIPOCAL_ID IN ("+estados+")"
					+ " GROUP BY MAESTRO";

			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map:lista){			
				mapa.put(map.getLlave(), (String)map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|mapAlumPorMaestro|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,String> mapAlumPorMaestroyMateria( String cargas, String modalidades, String estados) {
		HashMap<String,String> mapa	= new HashMap<String,String>();
		String comando					= "";
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();
		
		try{
			comando = " SELECT MAESTRO AS LLAVE, CURSO_CARGA_ID AS LLAVE, COUNT(CODIGO_PERSONAL) AS VALOR FROM ENOC.ALUMNO_CURSO"
					+ " WHERE CARGA_ID IN("+cargas+")"
					+ " AND MODALIDAD_ID IN("+modalidades+")"
					+ " AND TIPOCAL_ID IN ("+estados+")"
					+ " GROUP BY MAESTRO, CURSO_CARGA_ID";

			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map:lista){			
				mapa.put(map.getLlave(), (String)map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|mapAlumPorMaestro|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,String> mapAlumPorMateria( String cargaId, String estado) {
		HashMap<String,String> mapa	= new HashMap<String,String>();
		String comando					= "";
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();
		
		try{
			comando = " SELECT CURSO_CARGA_ID AS LLAVE, COUNT(CODIGO_PERSONAL) AS VALOR "
					+ " FROM ENOC.ALUMNO_CURSO"
					+ " WHERE CARGA_ID = ?"
					+ " AND TIPOCAL_ID IN ("+estado+")"
					+ " GROUP BY CURSO_CARGA_ID";

			lista = enocJdbc.query(comando, new aca.MapaMapper(), cargaId);
			for (aca.Mapa map:lista){			
				mapa.put(map.getLlave(), (String)map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|mapAlumPorMateria|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,String> mapCreditosPorMaestro( String cargas, String modalidades, String estados) {
		HashMap<String,String> mapa	= new HashMap<String,String>();
		String comando					= "";
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();
		
		try{
			comando = " SELECT MAESTRO, ENOC.CARRERA_NIVEL(CARRERA_ID) AS LLAVE, SUM(CREDITOS) AS VALOR FROM ENOC.ALUMNO_CURSO"
					+ " WHERE CARGA_ID IN ("+cargas+")"
					+ " AND MODALIDAD_ID IN ("+modalidades+")"
					+ " AND TIPOCAL_ID IN ("+estados+")"
					+ " GROUP BY MAESTRO, ENOC.CARRERA_NIVEL(CARRERA_ID)";

			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map:lista){			
				mapa.put(map.getLlave(), (String)map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|mapCreditosPorMaestro|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,String> mapaCredPorTipo( String codigoPersonal, String planId, String estados) {
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa		= new HashMap<String,String>();
		String comando					= "";
				
		try{
			comando = " SELECT ENOC.TIPOCURSO_ID(CURSO_ID) AS LLAVE, SUM(CREDITOS) AS VALOR FROM ENOC.ALUMNO_CURSO"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND PLAN_ID IN (?)"
					+ " AND TIPOCAL_ID IN ("+estados+")"
					+ " GROUP BY ENOC.TIPOCURSO_ID(CURSO_ID)";	
			Object[] parametros = new Object[] {codigoPersonal, planId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map:lista){			
				mapa.put(map.getLlave(), (String)map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|mapaCredPorTipo|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,String> mapaMaximaCarga( String mentorId, String periodoId) {
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa		= new HashMap<String,String>();
		String comando					= "";
				
		try{
			comando = " SELECT CODIGO_PERSONAL AS LLAVE, COALESCE(MAX(REPLACE(CARGA_ID,'2002-1','0102A')),'000000') AS VALOR FROM ENOC.ALUMNO_CURSO"
					+ " WHERE CODIGO_PERSONAL IN (SELECT DISTINCT(CODIGO_PERSONAL) FROM ENOC.MENT_ALUMNO WHERE MENTOR_ID = ? AND PERIODO_ID = ?)"
					+ " GROUP BY CODIGO_PERSONAL";	
			Object[] parametros = new Object[] {mentorId, periodoId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map:lista){			
				mapa.put(map.getLlave(), (String)map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|mapaMaximaCarga|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,String> mapaMateriasPorCarga( String codigoAlumno) {
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa		= new HashMap<String,String>();
		try{
			String comando = "SELECT CARGA_ID AS LLAVE, COUNT(*) AS VALOR FROM ALUMNO_CURSO WHERE CODIGO_PERSONAL = ? GROUP BY CARGA_ID";	
			Object[] parametros = new Object[] {codigoAlumno};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map:lista){			
				mapa.put(map.getLlave(), (String)map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|mapaMateriasPorCarga|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,String> mapaMateriasPorCargayBloque( String codigoAlumno) {
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa		= new HashMap<String,String>();
		try{
			String comando = "SELECT CARGA_ID||BLOQUE_ID AS LLAVE, COUNT(*) AS VALOR FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = ? GROUP BY CARGA_ID,BLOQUE_ID";	
			Object[] parametros = new Object[] {codigoAlumno};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map:lista){		
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|mapaMateriasPorCargayBloque|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,String> mapaMateriasPendientes( String codigoAlumno, String planId ) {
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa		= new HashMap<String,String>();
		try{
			String comando = "SELECT CICLO AS LLAVE, COUNT(CURSO_ID) AS VALOR FROM ENOC.MAPA_CURSO"
					+ "	WHERE PLAN_ID = ? "
					+ "	AND TIPOCURSO_ID IN (1,2,3,5,7,8)"
					+ "	AND CURSO_ID NOT IN"
					+ "	(SELECT CURSO_ID FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ? AND TIPOCAL_ID IN ('1','I'))"
					+ "	GROUP BY CICLO";	
			Object[] parametros = new Object[] {planId, codigoAlumno, planId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : lista){		
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|mapaMateriasPendientes|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,String> mapaAlumnoPorCarga(String cargaId) {
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa		= new HashMap<String,String>();
		try{
			String comando = "SELECT CARGA_ID AS LLAVE,COUNT(CARGA_ID) AS VALOR FROM ENOC.ALUMNO_CURSO WHERE CARGA_ID = ? GROUP BY CARGA_ID";	
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map:lista){		
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|mapaAlumnoPorCarga|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,String> mapaCursosAprobados(String codigoPersonal) {
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa		= new HashMap<String,String>();
		try{
			String comando = "SELECT CURSO_ID AS LLAVE, COUNT(*) AS VALOR FROM ALUMNO_CURSO WHERE CODIGO_PERSONAL = ? AND TIPOCAL_ID = '1' GROUP BY CURSO_ID";
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map:lista){		
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|mapaCursosAprobados|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,String> mapaMateriasEnCarga(String cargaId){
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa		= new HashMap<String,String>();
		try{
			String comando = "SELECT CODIGO_PERSONAL||BLOQUE_ID AS LLAVE, COUNT(*) AS VALOR FROM ENOC.ALUMNO_CURSO WHERE CARGA_ID = ? GROUP BY CODIGO_PERSONAL||BLOQUE_ID";	
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map:lista){		
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|mapaMateriasEnCarga|:"+ex);
		}
		return mapa;
	}

	public HashMap<String,String> mapaMateriasCierreAlumno(){
		List<CargaAlumno> listaCarga 	= cargaAlumnoDao.lisCargasEnLinea(" ORDER BY ALUM_APELLIDO(CODIGO_PERSONAL)");
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa		= new HashMap<String,String>();
		try{
			String comando = "SELECT CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID AS LLAVE, COUNT(CODIGO_PERSONAL) AS VALOR FROM ALUMNO_CURSO"
					+ " WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ? AND BLOQUE_ID = ?"
					+ " GROUP BY CODIGO_PERSONAL,CARGA_ID,BLOQUE_ID";	
			
			for (CargaAlumno carga : listaCarga){		
				Object[] parametros = new Object[] {carga.getCodigoPersonal(),carga.getCargaId(),carga.getBloqueId()};
				lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
				for (aca.Mapa map : lista){		
					mapa.put(map.getLlave(), map.getValor());
				}
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|mapaMateriasCierreAlumno|:"+ex);
		}
		return mapa;
	}
	
	public List<AlumnoCurso> listaMateriasEnCargayBloque(String codigoAlumno, String cargaId, String bloqueId, String order) {
		List<AlumnoCurso> lista				= new ArrayList<AlumnoCurso>();		
		try{
			String comando = " SELECT CODIGO_PERSONAL,"
					+ " CARGA_ID, "
					+ "  BLOQUE_ID,"
					+ " CURSO_CARGA_ID,"
					+ " CARRERA_ID,"
					+ " MODALIDAD_ID,"
					+ " PLAN_ID,"
					+ " CURSO_ID,"
					+ " NOMBRE_CURSO,"
					+ " CICLO AS CICLO,"
					+ " TO_CHAR(CREDITOS,'99.99') AS CREDITOS,"
					+ " HT,"
					+ " HP,"
					+ " TO_CHAR(NOTA_APROBATORIA,'999') AS NOTA_APROBATORIA,"
					+ " CURSO_ID2,"
					+ " NOMBRE_CURSO2,"
					+ " TO_CHAR(CREDITOS2,'99.99') AS CREDITOS2,"
					+ " TO_CHAR(HT2,'99') AS HT2,"
					+ " TO_CHAR(HP2,'99') AS HP2,"
					+ " NOTA,"
					+ " TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION,"
					+ " TIPOCAL_ID,"
					+ " COALESCE(TO_CHAR(NOTA_EXTRA,'999'),'0') AS NOTA_EXTRA,"
					+ " COALESCE(TO_CHAR(F_EXTRA,'DD/MM/YYYY'),'') AS F_EXTRA,"
					+ " CONVALIDACION,"
					+ " TITULO,"
					+ " TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO,"
					+ " GRUPO,"
					+ " MAESTRO,"
					+ " ESTADO,"
					+ " CORRECCION,"
					+ " OPTATIVA,"
					+ " NOTA_CONVA"
					+ " FROM ENOC.ALUMNO_CURSO"
					+ " WHERE CODIGO_PERSONAL = ? "
					+ " AND CARGA_ID = ? "
					+ " AND BLOQUE_ID = TO_NUMBER(?,'99') "+ order;			
			Object[] parametros = new Object[] {codigoAlumno,cargaId,bloqueId};
			lista = enocJdbc.query(comando, new AlumnoCursoMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDaoDao|listaMateriasEnCargayBloque|:"+ex);
		}
		return lista;
	}
	
	public List<AlumnoCurso> listaReprobadosPorMateria(String cursoId, String order) {
		List<AlumnoCurso> lista				= new ArrayList<AlumnoCurso>();		
		try{
			String comando = " SELECT CODIGO_PERSONAL, CARGA_ID, "
					+ " BLOQUE_ID,"
					+ " CURSO_CARGA_ID,"
					+ " CARRERA_ID,"
					+ " MODALIDAD_ID,"
					+ " PLAN_ID,"
					+ " CURSO_ID,"
					+ " NOMBRE_CURSO,"
					+ " CICLO AS CICLO,"
					+ " CREDITOS,"
					+ " HT,"
					+ " HP,"
					+ " NOTA_APROBATORIA,"
					+ " CURSO_ID2,"
					+ " NOMBRE_CURSO2,"
					+ " CREDITOS2,"
					+ " HT2,"
					+ " HP2,"
					+ " NOTA,"
					+ " TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION,"
					+ " TIPOCAL_ID,"
					+ " NOTA_EXTRA,"
					+ " COALESCE(TO_CHAR(F_EXTRA,'DD/MM/YYYY'),'') AS F_EXTRA,"
					+ " CONVALIDACION,"
					+ " TITULO,"
					+ " TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO,"
					+ " GRUPO,"
					+ " MAESTRO,"
					+ " ESTADO,"
					+ " CORRECCION,"
					+ " OPTATIVA,"
					+ " NOTA_CONVA"
					+ " FROM ENOC.ALUMNO_CURSO"
					+ " WHERE CURSO_ID = ? "
					+ " AND TIPOCAL_ID IN ('2','4')"+ order;			
			Object[] parametros = new Object[] {cursoId};
			lista = enocJdbc.query(comando, new AlumnoCursoMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDaoDao|listaReprobadosPorMateria|:"+ex);
		}
		return lista;
	}
	
	public HashMap<String,String> mapaMateriasPorAlumno( String planId, String estados ) {
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa		= new HashMap<String,String>();
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, COUNT(CODIGO_PERSONAL) AS VALOR FROM ENOC.ALUMNO_CURSO"
					+ " WHERE PLAN_ID = ?"
					+ " AND TIPOCAL_ID IN ("+estados+")"
					+ " AND CODIGO_PERSONAL IN"
					+ " 	(SELECT CODIGO_PERSONAL FROM ENOC.ALUM_PLAN WHERE PLAN_ID = ? AND PROMEDIO > 0)"
					+ " GROUP BY CODIGO_PERSONAL";	
			Object[] parametros = new Object[] {planId, planId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map:lista){			
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|mapaMateriasPorAlumno|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,String> mapaCreditosAlumno(String planId, String codigoPersonal ) {
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa		= new HashMap<String,String>();
		try{
			String comando = "SELECT CICLO||ENOC.TIPOCURSO_ID(CURSO_ID) AS LLAVE, SUM(CREDITOS) AS VALOR"
					+ " FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ? AND TIPOCAL_ID = '1'"
					+ " GROUP BY CICLO||ENOC.TIPOCURSO_ID(CURSO_ID)";	
			Object[] parametros = new Object[] {codigoPersonal, planId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map:lista){			
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|mapaCreditosAlumno|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,String> mapaMateriasPorAlumno(String codigoPersonal ) {
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa		= new HashMap<String,String>();
		try{
			String comando = "SELECT PLAN_ID AS LLAVE, COUNT(CODIGO_PERSONAL) AS VALOR FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = ? GROUP BY PLAN_ID";
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map:lista){			
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|mapaMateriasPorAlumno|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,String> mapaMateriasConAlumnos(String cargaId ) {
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa		= new HashMap<String,String>();
		try{
			String comando = "SELECT CARRERA_ID AS LLAVE, COUNT(DISTINCT(CURSO_CARGA_ID)) AS VALOR FROM ALUMNO_CURSO WHERE CARGA_ID = ? GROUP BY CARRERA_ID";
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map:lista){			
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|mapaMateriasConAlumnos|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,String> mapaAlumnosPorCarrera(String cargaId, String tipos){
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa		= new HashMap<String,String>();
		try{
			String comando = "SELECT CARRERA_ID AS LLAVE, COUNT(CODIGO_PERSONAL) AS VALOR FROM ENOC.ALUMNO_CURSO WHERE CARGA_ID = ? AND TIPOCAL_ID IN ("+tipos+") GROUP BY CARRERA_ID";
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map:lista){			
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|mapaMateriasConAlumnos|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,String> mapaMateriasEnPracticas(String cargaId, String bloques ) {
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa		= new HashMap<String,String>();
		try{
			String comando = "SELECT ENOC.FACULTAD(CARRERA_ID) AS LLAVE, COUNT(CODIGO_PERSONAL) AS VALOR FROM ENOC.ALUMNO_CURSO"
					+ " WHERE CARGA_ID = ? "
					+ " AND BLOQUE_ID IN ("+bloques+")"
					+ " AND CURSO_ID IN (SELECT CURSO_ID FROM ENOC.MAPA_CURSO WHERE LABORATORIO = 'S')"
					+ " GROUP BY ENOC.FACULTAD(CARRERA_ID)";
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map:lista){			
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|mapaMateriasPorAlumno|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,String> mapaCreditosPorAlumno(String codigoPersonal, String tipos ){
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa		= new HashMap<String,String>();
		try{
			String comando = "SELECT PLAN_ID AS LLAVE, SUM(CREDITOS) AS VALOR FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = ? AND TIPOCAL_ID IN ("+tipos+") GROUP BY PLAN_ID";
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map:lista){			
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|mapaCreditosPorAlumno|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,String> mapaRemediales(String codigoPersonal){
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa		= new HashMap<String,String>();
		try{
			String comando = "SELECT CURSO_ID AS LLAVE, NOTA AS VALOR "
					+ " FROM ALUMNO_CURSO "
					+ " WHERE CODIGO_PERSONAL = ? "
					+ " AND CURSO_ID IN ('REMPOD20REMM102','REMPOD20REME101','REMPOD20REMI103') "
					+ " AND TIPOCAL_ID IN ('1','I')";
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map:lista){			
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|mapaRemediales|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,AlumnoCurso> mapTieneCurso(String codigoPersonal) {
		List<AlumnoCurso> lista				= new ArrayList<AlumnoCurso>();
		HashMap<String,AlumnoCurso> mapa 	= new HashMap<String,AlumnoCurso>();
		try{
			String comando = "SELECT CODIGO_PERSONAL,CARGA_ID,BLOQUE_ID,CURSO_CARGA_ID,CARRERA_ID,MODALIDAD_ID,PLAN_ID,CURSO_ID,NOMBRE_CURSO,CICLO,CREDITOS,HT,HP,NOTA_APROBATORIA,CURSO_ID2,NOMBRE_CURSO2,CREDITOS2,"
				+ " HT2,HP2, NOTA,TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION,TIPOCAL_ID,TO_CHAR(NOTA_EXTRA,'999') AS NOTA_EXTRA,TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA,CONVALIDACION,TITULO,"
				+ " TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO,GRUPO,MAESTRO,ESTADO,CORRECCION,OPTATIVA,NOTA_CONVA FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.query(comando, new AlumnoCursoMapper(), parametros);
			for (AlumnoCurso map : lista){			
				mapa.put(map.getCodigoPersonal()+map.getCursoId()+map.getTipoCalId(), map);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|mapTieneCurso|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,String> mapMateriaAc(String codigoPersonal) {
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa		= new HashMap<String,String>();
		try{
			String comando = "SELECT CURSO_ID AS LLAVE, CODIGO_PERSONAL AS VALOR FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = ? AND TIPOCAL_ID IN('1')";
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : lista){			
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|mapMateriaAc|:"+ex);
		}
		return mapa;
	}
	
	public int bajaTotal(String matricula,String carga) {
		List<String> lista	= new ArrayList<String>();
		int bajas 				= 0;
		try{
			String comando	=	"SELECT	CURSO_CARGA_ID FROM ENOC.ALUMNO_CURSO"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND CARGA_ID = ?"
					+ " AND TIPOCAL_ID IN('M','I','1') "
					+ " ORDER BY CICLO, NOMBRE_CURSO";
			Object[] parametros = new Object[] {matricula, carga};
			lista = enocJdbc.queryForList(comando, String.class, parametros);		
			for(String  cursoCargaId : lista) {
				Object[] parametrosUpdate = new Object[] {matricula, cursoCargaId};
				comando = "UPDATE ENOC.KRDX_CURSO_ACT SET TIPOCAL_ID ='3', TIPO='B' WHERE CODIGO_PERSONAL = ? AND CURSO_CARGA_ID = ?";
				if (enocJdbc.update(comando,parametrosUpdate) == 1){
					bajas++;
				}	
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|bajaTotal|:"+ex);
		}
		return bajas;
	}
	
	public void bajaTotalCoordinador(String matricula,String carga) {
		int grabados 				= 0;
		List<AlumnoCurso> lista		= new ArrayList<AlumnoCurso>();
		String cursoCargaId 		= "";
		try{		
			String comando	= " SELECT CODIGO_PERSONAL,"
				+ " CARGA_ID, "
				+ "  BLOQUE_ID,"
				+ " CURSO_CARGA_ID,"
				+ " CARRERA_ID,"
				+ " MODALIDAD_ID,"
				+ " PLAN_ID,"
				+ " CURSO_ID,"
				+ " NOMBRE_CURSO,"
				+ " CICLO AS CICLO,"
				+ " TO_CHAR(CREDITOS,'99.99') AS CREDITOS,"
				+ " HT,"
				+ " HP,"
				+ " TO_CHAR(NOTA_APROBATORIA,'999') AS NOTA_APROBATORIA,"
				+ " CURSO_ID2,"
				+ " NOMBRE_CURSO2,"
				+ " TO_CHAR(CREDITOS2,'99.99') AS CREDITOS2,"
				+ " TO_CHAR(HT2,'99') AS HT2,"
				+ " TO_CHAR(HP2,'99') AS HP2,"
				+ " NOTA,"
				+ " TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION,"
				+ " TIPOCAL_ID,"
				+ " COALESCE(TO_CHAR(NOTA_EXTRA,'999'),'0') AS NOTA_EXTRA,"
				+ " COALESCE(TO_CHAR(F_EXTRA,'DD/MM/YYYY'),'') AS F_EXTRA,"
				+ " CONVALIDACION,"
				+ " TITULO,"
				+ " TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO,"
				+ " GRUPO,"
				+ " MAESTRO,"
				+ " ESTADO,"
				+ " CORRECCION,"
				+ " OPTATIVA,"
				+ " NOTA_CONVA"				
				+ " FROM ENOC.ALUMNO_CURSO"
				+ " WHERE CODIGO_PERSONAL = ?"
				+ " AND CARGA_ID = ?"
				+ " AND TIPOCAL_ID IN('M','I','1')"
				+ " ORDER BY CICLO, NOMBRE_CURSO";			
			lista = enocJdbc.query(comando, new AlumnoCursoMapper(), matricula, carga);
			
			for(AlumnoCurso curso : lista) {
				cursoCargaId = curso.getCursoCargaId();				
				comando = "UPDATE ENOC.KRDX_CURSO_ACT SET TIPO = 'B' WHERE CODIGO_PERSONAL = ? AND CURSO_CARGA_ID = ?";
				if (enocJdbc.update(comando, matricula, cursoCargaId) == 1) {
					grabados++;
				}
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|bajaTotalCoordinador|:"+ex);
		}
	}
	
	public HashMap<String,String> mapaReprobadosPorMateria(String planId) {
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa		= new HashMap<String,String>();
		try{
			String comando = "SELECT CURSO_ID AS LLAVE, COUNT(CODIGO_PERSONAL) AS VALOR FROM ENOC.ALUMNO_CURSO WHERE PLAN_ID = ? AND TIPOCAL_ID IN ('2','4') GROUP BY CURSO_ID";
			Object[] parametros = new Object[] {planId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : lista){			
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|mapaReprobadosPorMateria|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,String> mapaPromedioPorMateria(String planId) {
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa		= new HashMap<String,String>();
		try{
			String comando = "SELECT CURSO_ID AS LLAVE, ROUND(AVG(CASE NOTA_EXTRA WHEN 0 THEN NOTA ELSE NOTA_EXTRA END),2) AS VALOR"
					+ " FROM ALUMNO_CURSO "
					+ " WHERE PLAN_ID = ? AND TIPOCAL_ID NOT IN ('M','I')"
					+ " GROUP BY CURSO_ID";
			Object[] parametros = new Object[] {planId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : lista){			
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|mapaPromedioPorMateria|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,String> mapaAlumnosPorMateria(String cargas, String cursos, String sexo) {
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa		= new HashMap<String,String>();
		try{
			String comando = "SELECT SUBSTR(CURSO_ID,9,7)||TIPOCAL_ID AS LLAVE, COUNT(AC.CODIGO_PERSONAL) AS VALOR FROM ENOC.ALUMNO_CURSO AC, ENOC.ALUM_PERSONAL AP"
					+ " WHERE CARGA_ID IN("+cargas+") AND SUBSTR(CURSO_ID,9,7) IN ("+cursos+")"
					+ " AND AP.CODIGO_PERSONAL = AC.CODIGO_PERSONAL"
					+ " AND AP.SEXO = ?"
					+ " GROUP BY SUBSTR(CURSO_ID,9,7)||TIPOCAL_ID";
			Object[] parametros = new Object[] {sexo};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : lista){			
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|mapaAlumnosPorMateria|:"+ex);
		}
		return mapa;
	}	
	
	public HashMap<String,String> mapaGradePoint(String codigoAlumno, String planId){
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa		= new HashMap<String,String>();
		try{
			String comando = "SELECT DISTINCT AC.NOTA AS LLAVE, COALESCE((SELECT COALESCE(GP.GP_NOMBRE || ';' || GP.PUNTOS, 'X;0') FROM ENOC.CAT_GRADEPOINT GP WHERE AC.NOTA BETWEEN GP.INICIO AND GP.FIN), 'F;0') AS VALOR"
					+ " FROM ALUMNO_CURSO AC"
					+ " WHERE AC.CODIGO_PERSONAL = ?"
					+ " AND PLAN_ID = ?";
			Object[] parametros = new Object[] {codigoAlumno,planId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : lista){			
				mapa.put(map.getLlave(), map.getValor());
			}			
			// comando = "SELECT DISTINCT(NOTA_EXTRA) AS LLAVE,(SELECT COALESCE(GP_NOMBRE,'X')||';'||COALESCE(PUNTOS,0) FROM ENOC.CAT_GRADEPOINT WHERE AC.NOTA BETWEEN INICIO AND FIN) AS VALOR"
			// 		+ " FROM ALUMNO_CURSO AC"
			// 		+ " WHERE CODIGO_PERSONAL = ?"
			// 		+ " AND PLAN_ID = ?";			
			// lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			// for (aca.Mapa map : lista){			
			// 	mapa.put(map.getLlave(), map.getValor());
			// }
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|mapaGradePoint|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,String> mapaGradePoint(String codigoAlumno){
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa		= new HashMap<String,String>();
		try{
			String comando = "SELECT DISTINCT AC.NOTA AS LLAVE, COALESCE((SELECT COALESCE(GP.GP_NOMBRE || ';' || GP.PUNTOS, 'X;0') FROM ENOC.CAT_GRADEPOINT GP WHERE AC.NOTA BETWEEN GP.INICIO AND GP.FIN), 'F;0') AS VALOR"
					+ " FROM ALUMNO_CURSO AC"
					+ " WHERE AC.CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoAlumno};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : lista){			
				mapa.put(map.getLlave(), map.getValor());
			}			
			comando = "SELECT DISTINCT AC.NOTA_EXTRA AS LLAVE, COALESCE((SELECT COALESCE(GP.GP_NOMBRE || ';' || GP.PUNTOS, 'X;0') FROM ENOC.CAT_GRADEPOINT GP WHERE AC.NOTA BETWEEN GP.INICIO AND GP.FIN), 'F;0') AS VALOR"
					+ " FROM ALUMNO_CURSO AC"
					+ " WHERE AC.CODIGO_PERSONAL = ?";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : lista){			
				mapa.put(map.getLlave(), map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|mapaGradePoint|:"+ex);
		}
		return mapa;
	}	

	public HashMap<String,String> mapaSumCreditosPorGPA(String planId, String tipoCal) {
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa		= new HashMap<String,String>();
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, SUM(CREDITOS * (SELECT COALESCE(GP.PUNTOS, 0) FROM ENOC.CAT_GRADEPOINT GP WHERE NOTA BETWEEN GP.INICIO AND GP.FIN)) AS VALOR"
					+ " FROM ENOC.ALUMNO_CURSO"
					+ " WHERE PLAN_ID = ? AND TIPOCAL_ID IN ("+tipoCal+")"
					+ " AND (SELECT TIPOCURSO_ID FROM ENOC.MAPA_CURSO WHERE CURSO_ID = ALUMNO_CURSO.CURSO_ID) IN ('1', '2', '7','9')"
					+ " AND CONVALIDACION IN ('N', 'I')"
					+ " AND ((NOTA != 0 OR CONVALIDACION = 'S' OR CREDITOS > 0) OR NOTA_EXTRA != 0)"
					+ " GROUP BY CODIGO_PERSONAL";
			Object[] parametros = new Object[] {planId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : lista){			
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|mapaSumCreditosPorGPA|:"+ex);
		}
		return mapa;
	}

	public HashMap<String,String> mapaSumCreditosPorPlan(String planId) {
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa		= new HashMap<String,String>();
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, SUM(CREDITOS) AS VALOR"
					+ " FROM ENOC.ALUMNO_CURSO"
					+ " WHERE PLAN_ID = ? AND TIPOCAL_ID = '1'"
					+ " AND CONVALIDACION IN ('N', 'I')"
					+ " AND ((NOTA != 0 OR CONVALIDACION = 'S' OR CREDITOS > 0) OR NOTA_EXTRA != 0)"
					+ " GROUP BY CODIGO_PERSONAL";
			Object[] parametros = new Object[] {planId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : lista){			
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoCursoDao|mapaSumCreditosPorPlan|:"+ex);
		}
		return mapa;
	}
	
}