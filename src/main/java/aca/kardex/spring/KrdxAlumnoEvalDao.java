// Clase para la tabla de Cursos Actuales
package aca.kardex.spring;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class KrdxAlumnoEvalDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( KrdxAlumnoEval alumEval) {
		boolean ok = false;
		
		try{
			if (alumEval.getEvaluacionE42()==null || alumEval.getEvaluacionE42().equals("")) alumEval.getEvaluacionE42().equals("0");
			if (alumEval.getNota().equals("-")) alumEval.getNota().equals("0");
			String comando = "INSERT INTO ENOC.KRDX_ALUMNO_EVAL"+ 
				"(CODIGO_PERSONAL, CURSO_CARGA_ID, EVALUACION_ID, NOTA, EVALUACION_E42 ) "+
				"VALUES( ?, ?, TO_NUMBER(?,'99'), TO_NUMBER(?,'999.99')," +
				"TO_NUMBER(?,'9999999'))";
			Object[] parametros = new Object[] {alumEval.getCodigoPersonal(), alumEval.getCursoCargaId(),
					alumEval.getEvaluacionId(), alumEval.getNota(), alumEval.getEvaluacionE42()};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxAlumnoEvalDao|insertReg|:"+ex);
			//System.out.println("Datos:"+alumEval.getCodigoPersonal()+":"+alumEval.getCursoCargaId()+":"+alumEval.getEvaluacionId()+":"+alumEval.getNota()+":"+alumEval.getEvaluacionE42());
		}
		return ok;
	}	
	
	public boolean updateReg( KrdxAlumnoEval alumEval) {
		boolean ok = false;
		
		try{
			if (alumEval.getEvaluacionE42()==null || alumEval.getEvaluacionE42().equals("null")) alumEval.getEvaluacionE42().equals("0");
			String comando = "UPDATE ENOC.KRDX_ALUMNO_EVAL "+ 
				"SET NOTA = TO_NUMBER(?,'999.99'), " +
				"EVALUACION_E42 = TO_NUMBER(?,'9999999') "+
				"WHERE CODIGO_PERSONAL = ? "+
				"AND CURSO_CARGA_ID = ? "+
				"AND EVALUACION_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {alumEval.getNota(), alumEval.getEvaluacionE42(),
					alumEval.getCodigoPersonal(), alumEval.getCursoCargaId(), alumEval.getEvaluacionId()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxAlumnoEvalDao|updateReg|:"+ex);		
		}
		return ok;
	}
	
	public boolean updatePuntosActividades( String codigoPersonal, String cargaId) {
		boolean ok = false;
		
		try{			
			String comando = "UPDATE ENOC.KRDX_ALUMNO_EVAL KAE"
					+ " SET PUNTOS = (SELECT CASE EVALUACION_TIPO(KAE.CURSO_CARGA_ID, KAE.EVALUACION_ID) WHEN '%' THEN COALESCE(SUM(NOTA*ACTIVIDAD_VALOR(ACTIVIDAD_ID))/100,0) ELSE COALESCE(SUM(NOTA),0) END"
					+ " FROM KRDX_ALUMNO_ACTIV KAA WHERE KAA.CODIGO_PERSONAL = KAE.CODIGO_PERSONAL AND KAA.CURSO_CARGA_ID = KAE.CURSO_CARGA_ID AND KAA.EVALUACION_ID = KAE.EVALUACION_ID)"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND SUBSTR(CURSO_CARGA_ID,1,6) = ?"
					+ " AND CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM KRDX_CURSO_ACT WHERE CODIGO_PERSONAL = ? AND TIPOCAL_ID IN ('M','I'))";
			Object[] parametros = new Object[] {codigoPersonal, cargaId, codigoPersonal};
			if (enocJdbc.update(comando,parametros) >= 1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxAlumnoEvalDao|updatePuntosAlumno|:"+ex);
		}
		return ok;
	}
	
	public boolean updatePuntosSinActividades( String codigoPersonal, String cargaId) {
		boolean ok = false;
		
		try{			
			String comando = "UPDATE KRDX_ALUMNO_EVAL KAE"
					+ " SET PUNTOS = CASE EVALUACION_TIPO(KAE.CURSO_CARGA_ID, KAE.EVALUACION_ID) WHEN '%' THEN COALESCE(NOTA*EVALUACION_VALOR(KAE.CURSO_CARGA_ID,KAE.EVALUACION_ID)/100,0) ELSE COALESCE(NOTA,0) END"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND SUBSTR(CURSO_CARGA_ID,1,6) = ?"
					+ " AND CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM KRDX_CURSO_ACT WHERE CODIGO_PERSONAL = ? AND TIPOCAL_ID IN ('M','I'))"
					+ " AND TOT_ACTIVIDADES = 0"
					+ " AND NOTA > 0";			
			Object[] parametros = new Object[] {codigoPersonal, cargaId, codigoPersonal};
			if (enocJdbc.update(comando,parametros) >= 1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxAlumnoEvalDao|updatePuntosSinActividades|:"+ex);
		}
		return ok;
	}
	
/*	
	public boolean updateNotaEvaluacion( String codigoPersonal, String cargaId) {
		boolean ok = false;
		
		try{			
			String comando = "UPDATE ENOC.KRDX_ALUMNO_EVAL SET NOTA = PUNTOS "
					+ " WHERE CODIGO_PERSONAL = ? AND SUBSTR(CURSO_CARGA_ID,1,6) = ? AND PUNTOS > 0 AND EVALUADAS > 0"
					+ " AND CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM KRDX_CURSO_ACT WHERE CODIGO_PERSONAL = ? AND TIPOCAL_ID IN ('M','I'))";
			Object[] parametros = new Object[] {codigoPersonal, cargaId, codigoPersonal};
			if (enocJdbc.update(comando,parametros) >= 1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxAlumnoEvalDao|updateNotaEvaluacion|:"+ex);
		}
		return ok;
	}
*/	
	public boolean updateRegE42( KrdxAlumnoEval alumEval) {
		boolean ok = false;
		
		try{
			if (alumEval.getEvaluacionE42()!=null && !alumEval.getEvaluacionE42().equals("0")){
				String comando = "UPDATE ENOC.KRDX_ALUMNO_EVAL "+ 
					"SET NOTA = TO_NUMBER(?,'999.99') "+
					"WHERE CODIGO_PERSONAL = ? "+
					"AND EVALUACION_E42 = TO_NUMBER(?,'9999999')";
				Object[] parametros = new Object[] {alumEval.getNota(), alumEval.getCodigoPersonal(), alumEval.getEvaluacionE42()};
				if (enocJdbc.update(comando,parametros)==1){
					ok = true;
				}
			}
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxAlumnoEvalDao|updateRegE42|:"+ex);		
		}
		return ok;
	}
	
	public boolean deleteReg( String codigoPersonal, String cursoCargaId, String evaluacionId) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.KRDX_ALUMNO_EVAL "+ 
				"WHERE CODIGO_PERSONAL = ? "+
				"AND CURSO_CARGA_ID = ? "+
				"AND EVALUACION_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {codigoPersonal, cursoCargaId, evaluacionId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxAlumnoEvalDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public boolean deleteRegE42( String codigoPersonal, String evaluacionE42) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.KRDX_ALUMNO_EVAL "+ 
				"WHERE CODIGO_PERSONAL = ? "+				
				"AND EVALUACION_E42 = TO_NUMBER(?,'9999999')";
			Object[] parametros = new Object[] {codigoPersonal, evaluacionE42};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxAlumnoEvalDao|deleteRegE42|:"+ex);			
		}
		return ok;
	}
	
	public KrdxAlumnoEval mapeaRegId(  String codigoPersonal, String cursoCargaId, String evaluacionId) {
		KrdxAlumnoEval objeto = new KrdxAlumnoEval();
		
		try{ 
			String comando = "SELECT "+
				"CODIGO_PERSONAL, CURSO_CARGA_ID, EVALUACION_ID, NOTA, EVALUACION_E42 "+
				"FROM ENOC.KRDX_ALUMNO_EVAL "+ 
				"WHERE CODIGO_PERSONAL = ? "+
				"AND CURSO_CARGA_ID = ? "+
				"AND EVALUACION_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {codigoPersonal, cursoCargaId, evaluacionId};
			objeto = enocJdbc.queryForObject(comando, new KrdxAlumnoEvalMapper(),parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxAlumnoEvalDao|mapeaRegId|:"+ex);
		}
		return objeto;
	}
	
	public boolean existeReg(String codigoPersonal, String cursoCargaId, String evaluacionId) {
		boolean ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.KRDX_ALUMNO_EVAL "+ 
				"WHERE CODIGO_PERSONAL = ? "+
				"AND CURSO_CARGA_ID = ? "+
				"AND EVALUACION_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {codigoPersonal, cursoCargaId, evaluacionId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxAlumnoEvalDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public boolean existeRegE42(String codigoPersonal, String evaluacionE42) {
		boolean ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.KRDX_ALUMNO_EVAL WHERE CODIGO_PERSONAL = ? AND EVALUACION_E42 = TO_NUMBER(?,'9999999')";
			Object[] parametros = new Object[] {codigoPersonal, evaluacionE42};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxAlumnoEvalDao|existeRegE42|:"+ex);
		}
		return ok;
	}
	
	public String getEvaluacionId( String codigoPersonal, String evaluacionE42 ) {
		String evaluacion = "0";
		
		try{
			String comando = "SELECT EVALUACION_ID FROM ENOC.KRDX_ALUMNO_EVAL "+ 
				"WHERE CODIGO_PERSONAL = ? "+				
				"AND EVALUACION_E42 = TO_NUMBER(?,'9999999')";
			Object[] parametros = new Object[] {codigoPersonal, evaluacionE42};
			evaluacion = enocJdbc.queryForObject(comando,String.class,parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxAlumnoEvalDao|getEvaluacionId|:"+ex);
		}
		return evaluacion;
	}
	
	public String getAlumPromedio(String cursoCargaId, String codigoPersonal ) {
		
		String comando		= "";
		String promedio 	= "";
		double evaluacion 	= 0;		
		try{
			// comando = "SELECT TO_CHAR(FLOOR(ROUND(((SUM(DECODE(TIPO, 'P', NOTA, (VALOR*NOTA/100)))*100)/SUM(VALOR))+0.5,2)),'999.99') AS PROMEDIO "+
			// decode(trunc((SUM(VALOR)-1)/100),0,SUM(VALOR),100) :
			// SELECT ((SUM(DECODE(TIPO, 'P', NOTA, (VALOR*NOTA/100)))*100)/decode(trunc((SUM(VALOR)-1)/100),0,SUM(VALOR),100)) AS PROMEDIO
			// Si la suma de las estrategias es mayor de 100 entonces devolvera 100, de lo contrario devuelve la suma.
			
			comando = " SELECT (COALESCE(SUM(VALOR*NOTA/100),0) /"+
					" COALESCE( CASE WHEN SUM(VALOR * (CASE EVALUADAS WHEN 0 THEN 100 ELSE EVALUADAS END)/100) = 0 THEN 1 ELSE SUM(VALOR * (CASE EVALUADAS WHEN 0 THEN 100 ELSE EVALUADAS END)/100) END,1) *100) AS PROMEDIO"+				 
				" FROM ENOC.ALUMNO_EFICIENCIA "+
				" WHERE CURSO_CARGA_ID = ? "+
				" AND CODIGO_PERSONAL = ?";
			if (enocJdbc.queryForObject(comando, Double.class, cursoCargaId, codigoPersonal) >= 0){
				evaluacion = enocJdbc.queryForObject(comando, Double.class, cursoCargaId, codigoPersonal);
				
				double pextra = 0;
				comando = "SELECT COALESCE(SUM(NOTA),0) AS PEXTRA FROM ENOC.ALUMNO_EVALUACION WHERE CURSO_CARGA_ID = ? "+ 
				"AND CODIGO_PERSONAL = ? AND TIPO = 'E'";
				if (enocJdbc.queryForObject(comando, Integer.class, cursoCargaId, codigoPersonal) >= 0){
					pextra = enocJdbc.queryForObject(comando, Double.class, cursoCargaId, codigoPersonal);
				}			
				evaluacion += pextra;
				if (evaluacion>100)	evaluacion = 100;
				
				comando = "SELECT ROUND("+evaluacion+") FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL = ?";
				if (enocJdbc.queryForObject(comando, Integer.class, codigoPersonal) >= 0){
					promedio = enocJdbc.queryForObject(comando, String.class, codigoPersonal);
				}				
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxAlumnoEval|getAlumnoPromedio|:"+ex);
		}
		
		return promedio;
	}
	
	public boolean existenEvaluaciones( String codigoPersonal, String cursoCargaId ) {
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.KRDX_ALUMNO_EVAL" + 
					" WHERE CODIGO_PERSONAL = ?" +
					" AND CURSO_CARGA_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, cursoCargaId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxAlumnoEvalDao|existenEvaluaciones|:"+ex);
		}
		return ok;
	}
	
	public boolean elimnarEvaluaciones( String codigoPersonal, String cursoCargaId ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.KRDX_ALUMNO_EVAL" + 
					" WHERE CODIGO_PERSONAL = ?" +
					" AND CURSO_CARGA_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, cursoCargaId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxAlumnoEvalDao|elimnarEvaluaciones|:"+ex);
		}
		return ok;
	}
	
	public int getNumEst( String cursoCargaId ) {
		int numReg = 0;
		
		try{
			String comando = " SELECT COALESCE(COUNT(CURSO_CARGA_ID),0) AS NUMREG FROM ENOC.KRDX_ALUMNO_EVAL" + 
					" WHERE CURSO_CARGA_ID = ?";
			Object[] parametros = new Object[] {cursoCargaId};
			if (enocJdbc.queryForObject(comando, Integer.class,parametros) >= 1) {
				numReg = enocJdbc.queryForObject(comando, Integer.class,parametros);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxAlumnoEvalDao|getNumEst|:"+ex);
		}
		return numReg;
	}
	
	public int deleteAlumEval( String cursoCargaId ) {
		int numEval	= 0;		
		try{
			String comando = "SELECT COUNT(CURSO_CARGA_ID) FROM ENOC.KRDX_ALUMNO_EVAL WHERE CURSO_CARGA_ID = ?";
			Object[] parametros = new Object[] {cursoCargaId};
			if (enocJdbc.queryForObject(comando, Integer.class,parametros) >= 1) {
				comando = "DELETE FROM ENOC.KRDX_ALUMNO_EVAL WHERE CURSO_CARGA_ID = ?";
				numEval = enocJdbc.update(comando, parametros);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxAlumnoEvalDao|deleteAlumEval|:"+ex);
		}
		return numEval;
	}
	
	public List<KrdxAlumnoEval> getListAll( String orden ) {
		
		List<KrdxAlumnoEval> lista = new ArrayList<KrdxAlumnoEval>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CURSO_CARGA_ID, EVALUACION_ID, NOTA, EVALUACION_E42 "+
				"FROM ENOC.KRDX_ALUMNO_EVAL "+ orden;
			lista = enocJdbc.query(comando, new KrdxAlumnoEvalMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxAlumnoEvalDao|getListAll|:"+ex);
		}
		return lista;
	}
	
	public TreeMap<String,KrdxAlumnoEval> getMapAll( String orden ) {
		
		TreeMap<String,KrdxAlumnoEval> mapa	= new TreeMap<String,KrdxAlumnoEval>();
		List<KrdxAlumnoEval> lista			= new ArrayList<KrdxAlumnoEval>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CURSO_CARGA_ID, EVALUACION_ID, NOTA, EVALUACION_E42 "+
				"FROM ENOC.KRDX_ALUMNO_EVAL "+ orden;
			lista = enocJdbc.query(comando, new KrdxAlumnoEvalMapper());
			
			for (KrdxAlumnoEval m: lista){			
				mapa.put(m.getCodigoPersonal()+m.getCursoCargaId()+m.getEvaluacionId(), m);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxAlumnoEvalDao|getListAll|:"+ex);
		}
		return mapa;
	}
	
	// Map que obtiene todas las notas de la evaluaciones de una materia	
	public HashMap<String,String> getMapAlumEval( String cursoCargaId ) {
		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa>	lista 		= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL||CURSO_CARGA_ID||EVALUACION_ID AS LLAVE, COALESCE(NOTA,0) AS VALOR"
					+ " FROM ENOC.KRDX_ALUMNO_EVAL WHERE CURSO_CARGA_ID = ?";
			Object[] parametros = new Object[] {cursoCargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(),parametros);
			for (aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxAlumnoEvalDao|getMapAlumEval|:"+ex);
		}
		return mapa;
	}
	
	// Map que obtiene todas las notas de la evaluaciones de una materia	
	public HashMap<String,KrdxAlumnoEval> mapAlumEval( String cursoCargaId ) {
		
		HashMap<String,KrdxAlumnoEval> mapa = new HashMap<String,KrdxAlumnoEval>();
		List<KrdxAlumnoEval> lista 			= new ArrayList<KrdxAlumnoEval>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CURSO_CARGA_ID, EVALUACION_ID, COALESCE(NOTA,0) AS NOTA, EVALUACION_E42 "+
				"FROM ENOC.KRDX_ALUMNO_EVAL WHERE CURSO_CARGA_ID = ?";
			lista = enocJdbc.query(comando,new KrdxAlumnoEvalMapper(), cursoCargaId);
			for(KrdxAlumnoEval objeto : lista){				
				mapa.put(objeto.getCodigoPersonal()+objeto.getCursoCargaId()+objeto.getEvaluacionId(), objeto);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxAlumnoEvalDao|mapAlumEval|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,String> mapAlumnoPuntos(String cursoCargaId, String orden ){
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();
		
		try{
			String comando =
					"SELECT"+
					  " CODIGO_PERSONAL||CURSO_CARGA_ID AS LLAVE,"+
					  " SUM("+    
					    " CASE"+
					      " (SELECT TIPO FROM ENOC.CARGA_GRUPO_EVALUACION WHERE CURSO_CARGA_ID = KAE.CURSO_CARGA_ID AND EVALUACION_ID = KAE.EVALUACION_ID)"+
					    " WHEN '%' THEN COALESCE(NOTA,0)*(SELECT VALOR FROM ENOC.CARGA_GRUPO_EVALUACION WHERE CURSO_CARGA_ID = KAE.CURSO_CARGA_ID AND EVALUACION_ID = KAE.EVALUACION_ID)/100"+
					    " ELSE COALESCE(NOTA,0)"+
					    " END"+
					  " ) AS VALOR"+
					" FROM ENOC.KRDX_ALUMNO_EVAL KAE"+
					" WHERE CURSO_CARGA_ID = ?"+
					" AND (SELECT TIPO FROM ENOC.CARGA_GRUPO_EVALUACION WHERE CURSO_CARGA_ID = KAE.CURSO_CARGA_ID AND EVALUACION_ID = KAE.EVALUACION_ID) IN('%','P')"+
					" GROUP BY CODIGO_PERSONAL, CURSO_CARGA_ID";			
			lista = enocJdbc.query(comando,new aca.MapaMapper(), cursoCargaId);
			for(aca.Mapa objeto : lista){				
				mapa.put(objeto.getLlave(), objeto.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxAlumnoEvalDao|mapAlumnoPuntos|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapAlumnoExtras(String cursoCargaId, String orden ) {
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL||CURSO_CARGA_ID AS LLAVE, SUM(COALESCE(NOTA,0)) AS VALOR " +
					" FROM ENOC.KRDX_ALUMNO_EVAL KAE"+
					" WHERE CURSO_CARGA_ID = ?"+
					" AND (SELECT TIPO FROM ENOC.CARGA_GRUPO_EVALUACION WHERE CURSO_CARGA_ID = KAE.CURSO_CARGA_ID AND EVALUACION_ID = KAE.EVALUACION_ID) = 'E'"+
					" GROUP BY CODIGO_PERSONAL, CURSO_CARGA_ID";
			lista = enocJdbc.query(comando,new aca.MapaMapper(), cursoCargaId);
			for(aca.Mapa objeto : lista){				
				mapa.put(objeto.getLlave(), objeto.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxAlumnoEvalDao|mapAlumnoExtras|:"+ex);
		}
		
		return mapa;
	}
	
	// Map que obtiene todas las notas de la evaluaciones de una materia	
	public HashMap<String,String> mapCuentaEval( String cargaId ) {
		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa>	lista 		= new ArrayList<aca.Mapa>();
		
		try{
			String comando = " SELECT CURSO_CARGA_ID AS LLAVE, COALESCE(COUNT(DISTINCT(EVALUACION_ID)),0) AS VALOR"
					+ " FROM ENOC.KRDX_ALUMNO_EVAL"
					+ " WHERE CURSO_CARGA_ID LIKE '"+cargaId+"%'"
					+ " GROUP BY CURSO_CARGA_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa m : lista){
				mapa.put(m.getLlave(), m.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxAlumnoEvalDao|mapCuentaEval|:"+ex);
		}
		return mapa;
	}

	public HashMap<String,String> mapaExistenEvaluaciones(String codigoPersonal) {
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa>	lista 		= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CURSO_CARGA_ID AS LLAVE, CURSO_CARGA_ID AS VALOR FROM ENOC.KRDX_ALUMNO_EVAL" + 
					" WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.query(comando, new aca.MapaMapper(),parametros);
			for (aca.Mapa m : lista){
				mapa.put(m.getLlave(), m.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxAlumnoEvalDao|mapaExistenEvaluaciones|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,String> mapaPromedios(String cursoCargaId){
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa>	lista 		= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT EVALUACION_ID AS LLAVE, AVG(NOTA) AS VALOR FROM ENOC.KRDX_ALUMNO_EVAL WHERE CURSO_CARGA_ID = ? AND NOTA!=0 GROUP BY EVALUACION_ID";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(),cursoCargaId);
			for (aca.Mapa m : lista){
				mapa.put(m.getLlave(), m.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxAlumnoEvalDao|mapaPromedios|:"+ex);
		}
		return mapa;
	}
	
	public List<KrdxAlumnoEval> getLista( String codigoPersonal, String cursoCargaId, String orden ) {		
		List<KrdxAlumnoEval> lista = new ArrayList<KrdxAlumnoEval>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CURSO_CARGA_ID, EVALUACION_ID, NOTA, EVALUACION_E42 "+
					"FROM ENOC.KRDX_ALUMNO_EVAL "+ 
					"WHERE CODIGO_PERSONAL = ? "+
					"AND CURSO_CARGA_ID = ? "+ orden;
			lista = enocJdbc.query(comando, new KrdxAlumnoEvalMapper(), codigoPersonal, cursoCargaId);			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxAlumnoEvalDao|getLista|:"+ex);
		}
		return lista;
	}
	
	public List<KrdxAlumnoEval> getListaSinNotas( String codigoPersonal, String cursoCargaId, String orden ) {
		
		List<KrdxAlumnoEval> lista = new ArrayList<KrdxAlumnoEval>();
		
		try{
			String comando = "SELECT EV.CURSO_CARGA_ID,EV.EVALUACION_ID, "+
				"(SELECT NOTA FROM ENOC.KRDX_ALUMNO_EVAL " + 
				" WHERE CURSO_CARGA_ID=EV.CURSO_CARGA_ID" + 
				" AND EVALUACION_ID=EV.EVALUACION_ID" +
				" AND CODIGO_PERSONAL = ?" +
				") AS NOTA "+
				"FROM ENOC.CARGA_GRUPO_EVALUACION EV WHERE EV.CURSO_CARGA_ID = ? "+ orden;			
			lista = enocJdbc.query(comando, new KrdxAlumnoEvalMapper(), codigoPersonal, cursoCargaId);
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxAlumnoEvalDao|getListaSinNotas|:"+ex);
		}
		return lista;
	}

	
	public String getAlumnoEvaluacion( String cursoCargaId, String codigoPersonal, String evaluacionId ) {
		String evaluacion = "-";		
		try{
			String comando = "SELECT COALESCE(NOTA,0) AS NOTA FROM ENOC.KRDX_ALUMNO_EVAL" 
				+ " WHERE CURSO_CARGA_ID = ? "
				+ " AND CODIGO_PERSONAL = ? "
				+ " AND EVALUACION_ID = TO_NUMBER(?,'999')";
			evaluacion = enocJdbc.queryForObject(comando,String.class, cursoCargaId, codigoPersonal, evaluacionId);			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxAlumnoEvalDao|getAlumnoEvaluacion|:"+ex);
		}
		return evaluacion;
	}
	
	public String getAlumnoPuntosExtras( String cursoCargaId, String codigoPersonal ) {	
		String evaluacion = "0";
		try{
			String comando = "SELECT COALESCE(SUM(NOTA),0) AS PEXTRA FROM ENOC.ALUMNO_EVALUACION WHERE CURSO_CARGA_ID = ? "+ 
			"AND CODIGO_PERSONAL = ? AND TIPO = 'E'";
			evaluacion = enocJdbc.queryForObject(comando,String.class, cursoCargaId, codigoPersonal);			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxAlumnoEvalDao|getAlumnoPuntosExtras|:"+ex);
		}
		return evaluacion;
	}	
	
	// ESTA FUNCION HACE REFERENCIA A LA VISTA ALUMNO_EVALUACION QUE FUCIONA CARGA_GRUPO_EVALUACION Y KRDX_ALUMNO_EVAL
	public String getAlumnoPuntos( String cursoCargaId, String codigoPersonal ) {
		String evaluacion = "0";		
		try{
			String comando = "SELECT COALESCE(ROUND(SUM(CASE TIPO WHEN 'P' THEN NOTA ELSE (VALOR*NOTA/100) END),2),0) AS PUNTOS"+
				" FROM ENOC.ALUMNO_EVALUACION "+
				" WHERE CURSO_CARGA_ID = ? "+
				" AND CODIGO_PERSONAL = ?  AND TIPO != 'E'";
			evaluacion = enocJdbc.queryForObject(comando,String.class, cursoCargaId, codigoPersonal);			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxAlumnoEvalDao|getAlumnosPuntos|:"+ex);
		}
		return evaluacion;
	}
	
	public String getAlumnoMaximosPuntos( String cursoCargaId, String codigoPersonal ) {
		String evaluacion = "0";		
		try{
			String comando = "SELECT COALESCE(SUM(VALOR* (CASE EVALUADAS WHEN 0 THEN 100 ELSE EVALUADAS END)/100),1)" +
					" FROM ENOC.ALUMNO_EFICIENCIA" +
					" WHERE CURSO_CARGA_ID =  ?"+
					" AND CODIGO_PERSONAL = ?" +
					" AND TIPO != 'E'";
			evaluacion = enocJdbc.queryForObject(comando,String.class, cursoCargaId, codigoPersonal);			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxAlumnoEvalDao|getAlumnoMaximoPuntos|:"+ex);
		}
		return evaluacion;
	}
	
	public String getAlumnoPromedio( String cursoCargaId, String codigoPersonal ) {	
		String promedio 	= "0";
		double evaluacion 	= 0;		
		try{			
			String comando = "SELECT COUNT(*) FROM ENOC.ALUMNO_EVALUACION WHERE CURSO_CARGA_ID = ? AND CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {cursoCargaId, codigoPersonal};
			if (enocJdbc.queryForObject(comando, Integer.class,parametros) >= 1){
				//Si la suma de las estrategias es mayor de 100 entonces devolvera 100, de lo contrario devuelve la suma.
				comando =	"SELECT ((SUM(CASE TIPO WHEN 'P' THEN (SELECT NOTA FROM ENOC.ALUMNO_EVALUACION B " +
												" WHERE B.CURSO_CARGA_ID = A.CURSO_CARGA_ID" +
												" AND A.EVALUACION_ID = B.EVALUACION_ID" +
												" AND B.CODIGO_PERSONAL = ? ) ELSE " +
									" (VALOR*(SELECT NOTA FROM ENOC.ALUMNO_EVALUACION B" +
											" WHERE B.CURSO_CARGA_ID = A.CURSO_CARGA_ID" +
											" AND A.EVALUACION_ID = B.EVALUACION_ID" +
											" AND B.CODIGO_PERSONAL = ?)" +
								"/100) END )*100)/CASE TRUNC((SUM(VALOR)-1)/100) WHEN 0 THEN SUM(VALOR) ELSE 100 END) AS PROMEDIO" +
							" FROM ENOC.CARGA_GRUPO_EVALUACION A" + 
							" WHERE CURSO_CARGA_ID = ?" +
							" AND TIPO != 'E'";			
				if (enocJdbc.queryForObject(comando, Double.class, codigoPersonal, codigoPersonal, cursoCargaId) >= 0){
					evaluacion = enocJdbc.queryForObject(comando, Double.class, codigoPersonal, codigoPersonal, cursoCargaId);
					
					double pextra = 0;
					comando = "SELECT COALESCE(SUM(NOTA),0) AS PEXTRA FROM ENOC.ALUMNO_EVALUACION WHERE CURSO_CARGA_ID = ? AND CODIGO_PERSONAL = ? AND TIPO = 'E'";
					if (enocJdbc.queryForObject(comando, Double.class, cursoCargaId, codigoPersonal) >= 0){
						pextra = enocJdbc.queryForObject(comando, Double.class, cursoCargaId, codigoPersonal);	
					}				
					
					evaluacion += pextra;
					if (evaluacion>100)	evaluacion = 100;
					comando = "SELECT ROUND("+evaluacion+") FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL = ?";
					if (enocJdbc.queryForObject(comando, Integer.class, codigoPersonal) >= 0){
						promedio = enocJdbc.queryForObject(comando, String.class, codigoPersonal);
					}					
				}
			}						
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxAlumnoEvalDao|getAlumnoPromedio|:"+ex);
		}
		
		return promedio;
	}
	
	public String getAlumnoEficiencia( String cursoCargaId, String codigoPersonal ) {
		
		String comando		= "";
		String promedio 	= "";
		double evaluacion 	= 0;
		
		try{
			// SELECT ((SUM(DECODE(TIPO, 'P', NOTA, (VALOR*NOTA/100)))*100)/decode(trunc((SUM(VALOR)-1)/100),0,SUM(VALOR),100)) AS PROMEDIO			
			comando =	"SELECT (COALESCE(" +
							"		SUM( CASE TIPO WHEN '%' THEN VALOR*NOTA/100 ELSE NOTA END),0) / " +
							"		(CASE WHEN COALESCE(SUM(VALOR*(CASE EVALUADAS WHEN 0 THEN 100 ELSE EVALUADAS END)/100),1) = 0 THEN 1"+
							" 		ELSE COALESCE(SUM(VALOR*(CASE EVALUADAS WHEN 0 THEN 100 ELSE EVALUADAS END)/100),1) END) * 100) AS PROMEDIO "+
					" FROM ENOC.ALUMNO_EFICIENCIA" +
					" WHERE CURSO_CARGA_ID = ?" +
					" AND CODIGO_PERSONAL = ?" +
					" AND TIPO IN ('%','P')";
			if (enocJdbc.queryForObject(comando, Double.class, cursoCargaId, codigoPersonal) >= 0){			
				evaluacion = enocJdbc.queryForObject(comando, Double.class, cursoCargaId, codigoPersonal);
				double pextra = 0;				
				comando = "SELECT COALESCE(SUM(NOTA),0) AS PEXTRA FROM ENOC.ALUMNO_EVALUACION WHERE CURSO_CARGA_ID = ? AND CODIGO_PERSONAL = ? AND TIPO = 'E'";
				if (enocJdbc.queryForObject(comando, Double.class, cursoCargaId, codigoPersonal) >= 0){
					pextra = enocJdbc.queryForObject(comando, Double.class);
				}			
				evaluacion += pextra;
				if (evaluacion>100)	evaluacion = 100;
				comando = "SELECT ROUND("+evaluacion+") FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL= ?";
				if (enocJdbc.queryForObject(comando, Integer.class, codigoPersonal) >= 0){
					promedio = enocJdbc.queryForObject(comando, String.class, codigoPersonal);
				}				
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxAlumnoEvalDao|getAlumnoEficiencia|:"+ex);
		}		
		return promedio;
	}
	
	public String getAlumnoNota( String cursoCargaId, String codigoPersonal ) {
		String dev = "";
		try{
			String comando = "SELECT COALESCE(NOTA,0) AS NOTAS FROM ENOC.KRDX_CURSO_ACT	WHERE CURSO_CARGA_ID = ? AND CODIGO_PERSONAL = ? AND TIPO != 'E'";
			Object[] parametros = new Object[] {cursoCargaId, codigoPersonal};
			dev = enocJdbc.queryForObject(comando,String.class,parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxAlumnoEvalDao|getAlumnoNota|:"+ex);
		}
		return dev;
	}	
	
	public String getAlumnoPromedio( String cursoCargaId, String codigoPersonal, String mes ) {
		
		String comando		= "";
		String promedio 	= "";
		double evaluacion 	= 0;
		
		try{
			comando = "SELECT ((SUM(CASE TIPO WHEN 'P' THEN NOTA ELSE (VALOR*NOTA/100) END )*100)/SUM(VALOR)) AS PROMEDIO "+
				"FROM ENOC.ALUMNO_EVALUACION "+
				"WHERE CURSO_CARGA_ID = ? "+
				"AND CODIGO_PERSONAL = ? "+
				"AND TO_CHAR(fecha,'MM') = '" + mes+"' AND TIPO != 'E'";
			if (enocJdbc.queryForObject(comando, Double.class, cursoCargaId, codigoPersonal) >= 0){			
				evaluacion = enocJdbc.queryForObject(comando, Double.class, cursoCargaId, codigoPersonal);			
				double pextra = 0;
				comando = "SELECT SUM(NOTA) AS PEXTRA FROM ENOC.ALUMNO_EVALUACION WHERE CURSO_CARGA_ID = ? "+ 
				"AND CODIGO_PERSONAL = ? AND TIPO = 'E' AND TO_CHAR(fecha,'MM') = '" + mes+"'";
				if (enocJdbc.queryForObject(comando, Double.class, cursoCargaId, codigoPersonal) >= 0){
					pextra = enocJdbc.queryForObject(comando, Double.class, cursoCargaId, codigoPersonal);
				}			
				evaluacion += pextra;
				if (evaluacion>100)	evaluacion = 100;
				comando = "SELECT ROUND("+evaluacion+") FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL= ?";
				if (enocJdbc.queryForObject(comando, Integer.class, codigoPersonal) >= 0){
					promedio = enocJdbc.queryForObject(comando, String.class, codigoPersonal);
				}								
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxAlumnoEvalDao|getAlumnoPromedio|:"+ex);
		}
		
		return promedio;
	}
	
	public List<String> getActaMeses( String cursoCargaId) {
		
		List<String>lista = new ArrayList<String>();
		
		try{
			String comando = "SELECT DISTINCT TO_CHAR(FECHA,'MM') FROM ENOC.ALUMNO_EVALUACION WHERE CURSO_CARGA_ID = ?  AND TIPO != 'E' ORDER BY 1";
			lista = enocJdbc.queryForList(comando, String.class, cursoCargaId);			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxAlumnoEvalDao|getActaMeses|:"+ex);
		}
		return lista;
	}
	
	public String getActaPuntosEvaluados( String cursoCargaId) {
		float suma = 0;
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT DISTINCT EVALUACION_ID AS LLAVE, VALOR FROM ENOC.ALUMNO_EVALUACION WHERE CURSO_CARGA_ID = ? AND TIPO != 'E'";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), cursoCargaId);		
			for (aca.Mapa map : lista){				
				suma += Float.parseFloat((String)map.getValor());	
			}
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxAlumnoEvalDao|getActaPuntosEvaluados|:"+ex);
		}
		return String.valueOf(suma);
	}
}