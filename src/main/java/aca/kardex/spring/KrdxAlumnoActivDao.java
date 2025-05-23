//Bean del Kardex del Alumno( Formato del Kardex: determina que materias valen para el kardex del alumno).
package  aca.kardex.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class KrdxAlumnoActivDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired
	KrdxAlumnoEvalDao krdxAlumnoEvalDao;
	
	public boolean insertReg( KrdxAlumnoActiv krdxAlum) {
		
		boolean ok = false;
		
		try{

			if (krdxAlum.getActividadE42()==null || krdxAlum.getActividadE42().equals("")) krdxAlum.setActividadE42("0");

			String comando = "INSERT INTO ENOC.KRDX_ALUMNO_ACTIV"+ 
				"(CODIGO_PERSONAL, CURSO_CARGA_ID, ACTIVIDAD_ID, NOTA, ACTIVIDAD_E42 ) "+
				"VALUES( ?, ?, TO_NUMBER(?,'9999999'), TO_NUMBER(?,'999')," +
				"TO_NUMBER(?,'9999999'))";
			
			Object[] parametros = new Object[] {krdxAlum.getCodigoPersonal(),krdxAlum.getCursoCargaId(),krdxAlum.getActividadId(),krdxAlum.getNota(),krdxAlum.getActividadE42()};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxAlumnoActivDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean setPromEstrID(String sCursoCargaId, String evaluacionId) {
		String sTipo				= "";
		
		float fSuma					= 0;
		float fValorEvaluacion		= 0;
		float fNotaEvaluacion		= 0;
		
		boolean ok 					= true;
		
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();
		List<String> matriculas 	= new ArrayList<String>();
		
		try{				
			List<String> lisVal			= new ArrayList<String>();			

			//Query para sacar la matricula de cada alumno que tienen calificacion en alguna actividad
			String comando = "SELECT DISTINCT(CODIGO_PERSONAL) CODIGO_PERSONAL FROM ENOC.KRDX_ALUMNO_ACTIV WHERE CURSO_CARGA_ID = ?";			
			matriculas = enocJdbc.queryForList(comando, String.class, sCursoCargaId);
			
			for(String codigoPersonal : matriculas){			
				//Query para sacar el valor de cada actividad
				comando = " SELECT ACTIVIDAD_ID AS LLAVE, COALESCE(VALOR,0) AS VALOR " +
						   " FROM ENOC.CARGA_GRUPO_ACTIVIDAD" + 
						   " WHERE CURSO_CARGA_ID = ?" +
						   " AND EVALUACION_ID = ? "+
						   " ORDER BY ACTIVIDAD_ID";
				
				lista = enocJdbc.query(comando, new aca.MapaMapper(), sCursoCargaId, evaluacionId);
				
				lisVal.clear();
				//guarda en un listor el id de actividad con su respectivo valor
				for(aca.Mapa objeto : lista){				
					lisVal.add(objeto.getLlave());
					lisVal.add(objeto.getValor());
				}
				
				fSuma = 0;	
				
				comando = "SELECT COALESCE(NOTA,0) AS LLAVE, ACTIVIDAD_ID AS VALOR" +
						" FROM ENOC.KRDX_ALUMNO_ACTIV" + 
						" WHERE ACTIVIDAD_ID IN (SELECT ACTIVIDAD_ID" +
											   " FROM ENOC.CARGA_GRUPO_ACTIVIDAD" + 
											   " WHERE CURSO_CARGA_ID = ?" +
											   " AND EVALUACION_ID = ?)" +
						" AND CODIGO_PERSONAL = ?" +
						" ORDER BY ACTIVIDAD_ID";				
				lista = enocJdbc.query(comando, new aca.MapaMapper(), sCursoCargaId, evaluacionId, codigoPersonal);
				
				for(aca.Mapa objeto : lista){// NECESITO HACER LAS OPERACIONES POR CADA NOTA DE ACTIVIDADA Y COMPROBAR SI ES EL VALOR DE LA NOTA CORRESPONDIENTE		
					String temp = objeto.getValor();
					
					for(int j = 0; j < lisVal.size(); j+=2){
						if(temp.equals((String)lisVal.get(j))){
							fSuma = fSuma + (((Integer.parseInt(objeto.getLlave()))*(Float.parseFloat((String)lisVal.get(j+1))))/100);
						}						
					}
				}
				
				//Query para sacar el valor de la actividad
				comando = "SELECT COALESCE(VALOR,0) AS LLAVE, TIPO AS VALOR" +
						   " FROM ENOC.CARGA_GRUPO_EVALUACION" + 
						   " WHERE CURSO_CARGA_ID = ?" +
						   " AND EVALUACION_ID = ?";				
				lista = enocJdbc.query(comando, new aca.MapaMapper(), sCursoCargaId, evaluacionId);
				
				for(aca.Mapa objeto : lista){				
					fValorEvaluacion = Float.parseFloat(objeto.getLlave());
					sTipo			 = objeto.getValor();
				}
				
				//Se hace la operacion para sacar la nota de cada Estrategia por cada alumno
				if(sTipo.trim().equals("%"))
					fNotaEvaluacion = fSuma;
				else
					fNotaEvaluacion = (fValorEvaluacion * fSuma) / 100;
				
				//fNotaEvaluacion = (fValorEvaluacion * fSuma) / 100;
				
				fNotaEvaluacion = (int)(fNotaEvaluacion +0.5);
				
				
				//Inicializamos BEAN de la tabla para no repetir su contenido
				KrdxAlumnoEval kae = new KrdxAlumnoEval();
				kae.setCodigoPersonal(codigoPersonal);
				kae.setCursoCargaId(sCursoCargaId);
				kae.setEvaluacionId(String.valueOf(evaluacionId));
				if(krdxAlumnoEvalDao.existeReg(codigoPersonal,sCursoCargaId,evaluacionId)){
					krdxAlumnoEvalDao.mapeaRegId(codigoPersonal,sCursoCargaId,evaluacionId);
					kae.setNota(String.valueOf(fNotaEvaluacion));
					if(!krdxAlumnoEvalDao.updateReg(kae))
						ok = false;
				}else{
					kae.setNota(String.valueOf(fNotaEvaluacion));
					if(!krdxAlumnoEvalDao.insertReg(kae))
						ok = false;
				}
			}
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxAlumnoActivUtil|setPromEstrID|:"+ex);
			ok = false;
		}
		
		return ok;
	}
	
	public boolean updateReg( KrdxAlumnoActiv krdxAlum) {
		boolean ok = false;		
		
		try{			
			String comando = "UPDATE ENOC.KRDX_ALUMNO_ACTIV "+ 
				"SET NOTA = TO_NUMBER(?,'999'), " +
				"CURSO_CARGA_ID = ?, " +
				"ACTIVIDAD_E42 = TO_NUMBER(?,'9999999') "+
				"WHERE CODIGO_PERSONAL = ? "+				
				"AND ACTIVIDAD_ID = TO_NUMBER(?,'9999999')";
			
			Object[] parametros = new Object[] {krdxAlum.getNota(),krdxAlum.getCursoCargaId(),krdxAlum.getActividadE42(),krdxAlum.getCodigoPersonal(),krdxAlum.getActividadId()}; 			 			
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxAlumnoActivDao|updateReg|:"+ex);		
		}
		return ok;
	}	
	
	public boolean updateRegE42( KrdxAlumnoActiv krdxAlum) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.KRDX_ALUMNO_ACTIV "+ 
				"SET NOTA = TO_NUMBER(?,'999'), " +
				"WHERE CODIGO_PERSONAL = ? "+
				"AND ACTIVIDAD_E42 = TO_NUMBER(?,'9999999')";
			
			Object[] parametros = new Object[] {krdxAlum.getNota(),krdxAlum.getCodigoPersonal(),krdxAlum.getActividadE42()}; 			 			
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxAlumnoActivDao|updateRegE42|:"+ex);		
		}
		return ok;
	}
	public boolean deleteReg( String codigoPersonal, String actividadId) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.KRDX_ALUMNO_ACTIV "+ 
				"WHERE CODIGO_PERSONAL = ? "+				
				"AND ACTIVIDAD_ID = TO_NUMBER(?,'9999999')";
			
			Object[] parametros = new Object[] {codigoPersonal,actividadId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxAlumnoActivDao|deleteReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean deleteNotasReg( String actividadId, String cursoCargaId) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.KRDX_ALUMNO_ACTIV "+ 
				"WHERE ACTIVIDAD_ID = TO_NUMBER(?,'9999999') " +
				"AND CURSO_CARGA_ID = ?";
			
			Object[] parametros = new Object[] {actividadId,cursoCargaId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxAlumnoActivDao|deleteNotasReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean deleteRegE42( String codigoPersonal, String actividadE42) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.KRDX_ALUMNO_ACTIV "+ 
				"WHERE CODIGO_PERSONAL = ? "+				
				"AND ACTIVIDAD_E42 = TO_NUMBER(?,'9999999')";
			
			Object[] parametros = new Object[] {codigoPersonal,actividadE42};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxAlumnoActivDao|deleteRegE42|:"+ex);			
		}
		return ok;
	}
	
	public KrdxAlumnoActiv mapeaRegId( String codigoPersonal, String actividadId) {
		KrdxAlumnoActiv krdxAlumno = new KrdxAlumnoActiv();
		
		try{ 
			String comando = "SELECT CODIGO_PERSONAL, CURSO_CARGA_ID, ACTIVIDAD_ID, NOTA, ACTIVIDAD_E42, EVALUACION_ID"
					+ " FROM ENOC.KRDX_ALUMNO_ACTIV"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND ACTIVIDAD_ID = TO_NUMBER(?,'9999999')";
				
				Object[] parametros = new Object[] {codigoPersonal, actividadId};
				krdxAlumno = enocJdbc.queryForObject(comando, new KrdxAlumnoActivMapper(),parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxAlumnoActivDao|mapeaRegId|:"+ex);
		}
		return krdxAlumno ;
	}
	
	public boolean existeReg(String codigoPersonal, String actividadId) {
		boolean 			ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.KRDX_ALUMNO_ACTIV "+ 
				"WHERE CODIGO_PERSONAL = ? "+
				"AND ACTIVIDAD_ID = TO_NUMBER(?,'9999999')";
			
			Object[] parametros = new Object[] {codigoPersonal,actividadId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}				
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxAlumnoActivDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public boolean existeRegE42(String codigoPersonal, String actividadE42) {
		boolean 			ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.KRDX_ALUMNO_ACTIV "+ 
				"WHERE CODIGO_PERSONAL = ? "+
				"AND ACTIVIDAD_E42 = TO_NUMBER(?,'9999999')";
			
			Object[] parametros = new Object[] {codigoPersonal,actividadE42};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxAlumnoActivDao|existeRegE42|:"+ex);
		}		
		return ok;
	}
	
	public String getActividadId( String codigoPersonal, String actividadE42) {
		String actividad			= "";
		
		try{
			String comando = "SELECT ACTIVIDAD_ID FROM ENOC.KRDX_ALUMNO_ACTIV "+ 
				"WHERE CODIGO_PERSONAL = ? "+
				"AND ACTIVIDAD_E42 = TO_NUMBER(?,'9999999')";
				
				Object[] parametros = new Object[] {codigoPersonal,actividadE42};
				actividad = enocJdbc.queryForObject(comando, String.class,parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxAlumnoActivDao|getActividadId|:"+ex);
		}
		return actividad;
	}
	
	public String getNotaActividad( String codigoPersonal, String actividadId) {		
		String nota			= "";
		
		try{
			String comando = "SELECT NOTA FROM ENOC.KRDX_ALUMNO_ACTIV "+ 
				"WHERE CODIGO_PERSONAL = ? "+
				"AND ACTIVIDAD_ID = TO_NUMBER(?,'9999999')";
			
				Object[] parametros = new Object[] {codigoPersonal,actividadId};
				nota = enocJdbc.queryForObject(comando, String.class,parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxAlumnoActivDao|getNotaActividad|:"+ex);
		}
		return nota;
	}
	
	public int getNumActividades( String cursoCargaId) {
		int numAct				= 0;		
		try{
			String comando = "SELECT COALESCE(COUNT(NOTA),0) AS NUMACT FROM ENOC.KRDX_ALUMNO_ACTIV WHERE CURSO_CARGA_ID = ?";			
			if (enocJdbc.queryForObject(comando, Integer.class, cursoCargaId) >= 1) {
				numAct = enocJdbc.queryForObject(comando, Integer.class, cursoCargaId);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxAlumnoActivDao|getNumActividades|:"+ex);
		}
		return numAct;
	}
	
	public int deleteAlumAct( String cursoCargaId ) {
		int numAct				= 0;		
		try{
			String comando = "SELECT COUNT(CURSO_CARGA_ID) FROM ENOC.KRDX_ALUMNO_ACTIV WHERE CURSO_CARGA_ID = ?";			
			if (enocJdbc.queryForObject(comando, Integer.class, cursoCargaId) >= 1) {
				comando = "DELETE FROM ENOC.KRDX_ALUMNO_ACTIV WHERE CURSO_CARGA_ID = ?";
				numAct = enocJdbc.update(comando, cursoCargaId);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxAlumnoActivDao|deleteAlumAct|:"+ex);
		}
		return numAct;
	}
	
	public HashMap<String, KrdxAlumnoActiv> mapActividadesMateria( String cursoCargaId) {
		HashMap<String, KrdxAlumnoActiv> map = new HashMap<String, KrdxAlumnoActiv>();
		List<KrdxAlumnoActiv> lista	= new ArrayList<KrdxAlumnoActiv>();
		try{
			String comando = "SELECT CODIGO_PERSONAL, ACTIVIDAD_ID, CURSO_CARGA_ID, NOTA, ACTIVIDAD_E42, EVALUACION_ID"
		    		+" FROM ENOC.KRDX_ALUMNO_ACTIV" 
		    		+" WHERE CURSO_CARGA_ID = ?";
			lista = enocJdbc.query(comando, new KrdxAlumnoActivMapper(), cursoCargaId);
			for (KrdxAlumnoActiv actividad : lista){
				map.put(actividad.getCodigoPersonal()+actividad.getActividadId(), actividad);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxAlumnoActivDao|mapActividadesMateria|:"+ex);
		}
		return map;
	}
	
	public HashMap<String, String> mapNotasMateria( String cursoCargaId) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		try{
			String comando = 	"SELECT CODIGO_PERSONAL+ACTIVIDAD_ID AS LLAVE, COALESCE(NOTA,0) AS VALOR"
					+ " FROM ENOC.KRDX_ALUMNO_ACTIV"
					+ " WHERE CURSO_CARGA_ID = ?";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), cursoCargaId);
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxAlumnoActivDao|mapNotasMateria|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaNumActividadesEvaluadas( String cargaId) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		String comando		= "";
		
		try{
			comando = " SELECT CURSO_CARGA_ID AS LLAVE, COUNT(DISTINCT(ACTIVIDAD_ID)) AS VALOR FROM ENOC.KRDX_ALUMNO_ACTIV"
					+ " WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ?"
					+ " GROUP BY CURSO_CARGA_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), cargaId);
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxAlumnoActivDao|mapaNumActividadesEvaluadas|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String, String> mapaActividadesEvaluadas( String cursoCargaId) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT ACTIVIDAD_ID AS LLAVE, COUNT(*) AS VALOR FROM ENOC.KRDX_ALUMNO_ACTIV WHERE CURSO_CARGA_ID = ? GROUP BY ACTIVIDAD_ID";
			Object[] parametros = new Object[] {cursoCargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(),parametros);
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxAlumnoActivDao|mapaActividadesEvaluadas|:"+ex);
		}		
		return mapa;
	}
	
}