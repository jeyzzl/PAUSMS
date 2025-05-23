// Clase Util para la tabla de Carga
package aca.carga.spring;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import aca.kardex.spring.KrdxAlumnoEval;
import aca.kardex.spring.KrdxAlumnoEvalMapper;

@Component
public class CargaGrupoEvaluacionDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( CargaGrupoEvaluacion evaluacion ) {
		boolean ok = false;		
		try{
			if (evaluacion.getValor()==null || evaluacion.getValor().equals("null")) evaluacion.setValor("0");
			if (evaluacion.getEvaluacionE42()==null || evaluacion.getEvaluacionE42().equals("null")) 
				evaluacion.setEvaluacionE42("0");
			String comando = "INSERT INTO ENOC.CARGA_GRUPO_EVALUACION"+ 
				"(CURSO_CARGA_ID, EVALUACION_ID, NOMBRE_EVALUACION, FECHA, ESTRATEGIA_ID, VALOR, TIPO, EVALUACION_E42, ENVIAR ) "+
				"VALUES( ?, "+
				"TO_NUMBER(?,'99'), "+
				"?, "+
				"TO_DATE(?,'YYYY/MM/DD HH24:MI:SS'), "+
				"?, "+
				"TO_NUMBER(?,'999.99'), "+
				"?," +
				"TO_NUMBER(?,'9999999'),? )";
			Object[] parametros = new Object[] {evaluacion.getCursoCargaId(), evaluacion.getEvaluacionId(),
				evaluacion.getNombreEvaluacion(), evaluacion.getFecha(), evaluacion.getEstrategiaId(),
				evaluacion.getValor(), evaluacion.getTipo(), evaluacion.getEvaluacionE42(), evaluacion.getEnviar()
			};			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoEvaluacionDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg( CargaGrupoEvaluacion evaluacion ) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.CARGA_GRUPO_EVALUACION"
					+ " SET NOMBRE_EVALUACION = ?,"
					+ " FECHA = TO_DATE(?,'YYYY/MM/DD HH24:MI:SS'),"
					+ " ESTRATEGIA_ID  = ?,"
					+ " VALOR = TO_NUMBER(?,'999.99'),"
					+ " TIPO  = ?,"
					+ " EVALUACION_E42 = TO_NUMBER(?,'9999999'),"
					+ " ENVIAR = ?"
					+ " WHERE CURSO_CARGA_ID = ?"
					+ " AND EVALUACION_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {evaluacion.getNombreEvaluacion(), evaluacion.getFecha(),
				evaluacion.getEstrategiaId(), evaluacion.getValor(), evaluacion.getTipo(), evaluacion.getEvaluacionE42(), evaluacion.getEnviar(),
				evaluacion.getCursoCargaId(), evaluacion.getEvaluacionId()
			};			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoEvaluacionDao|updateReg|:"+ex);		 
		}
		return ok;
	}	
	
	public boolean updateRegE42( CargaGrupoEvaluacion evaluacion ) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.CARGA_GRUPO_EVALUACION "+ 
				"SET NOMBRE_EVALUACION = ?, "+
				"FECHA = TO_DATE(?,'YYYY/MM/DD hh24:MI:SS'), "+
				"ESTRATEGIA_ID  = ?, "+
				"VALOR = TO_NUMBER(?,'999.99'), "+
				"TIPO  = ? " +				
				"WHERE EVALUACION_E42 = TO_NUMBER(?,'9999999')";
			Object[] parametros = new Object[] {evaluacion.getNombreEvaluacion(), evaluacion.getFecha(),
			evaluacion.getEstrategiaId(), evaluacion.getValor(), evaluacion.getTipo(), evaluacion.getEvaluacionE42()};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoEvaluacionDao|updateRegE42|:"+ex);		 
		}
		return ok;
	}
	
	public boolean deleteReg( String cursoCargaId, String evaluacionId ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CARGA_GRUPO_EVALUACION "+ 
				"WHERE CURSO_CARGA_ID = ? AND EVALUACION_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {cursoCargaId, evaluacionId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoEvaluacionDao|delete Reg|:"+ex);			
		}
		return ok;
	}
	
	public boolean deleteTodos( String cursoCargaId) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.CARGA_GRUPO_EVALUACION WHERE CURSO_CARGA_ID = ?";
			Object[] parametros = new Object[] {cursoCargaId};
			if (enocJdbc.update(comando,parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoEvaluacionDao|deleteTodos|:"+ex);			
		}
		return ok;
	}
	
	public boolean deleteRegE42( String evaluacionE42 ) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.CARGA_GRUPO_EVALUACION WHERE EVALUACION_E42 = TO_NUMBER(?,'9999999')";
			Object[] parametros = new Object[] {evaluacionE42};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoEvaluacionDao|deleteRegE42|:"+ex);			
		}
		return ok;
	}
	
	public boolean copiarEstrategias( String ccOrigen, String ccDestino) {
		boolean ok 		= true;		 
		List<CargaGrupoEvaluacion> listor = getLista( ccOrigen, " ORDER BY SUBSTR(FECHA,7,4)||SUBSTR(FECHA,4,5)||SUBSTR(FECHA,1,2), EVALUACION_ID");				
		for (CargaGrupoEvaluacion origen : listor ){			
			CargaGrupoEvaluacion destino = new CargaGrupoEvaluacion();
			destino.setEstrategiaId(origen.getEstrategiaId());
			destino.setCursoCargaId(ccDestino);
			destino.setEvaluacionId(origen.getEvaluacionId());
			destino.setFecha(origen.getFecha());
			destino.setNombreEvaluacion(origen.getNombreEvaluacion());
			destino.setTipo(origen.getTipo());
			destino.setValor(origen.getValor());
			//System.out.println("Valores:"+destino.getCursoCargaId()+":"+destino.getEstrategiaId()+":"+destino.getNombreEvaluacion());
			if (!insertReg(destino)) {			
				ok=false;
			}			
		}
		return ok;
	}
	
	public List<CargaGrupoEvaluacion> getTareas( String codigoPersonal, String cargaId, String fecha) {
		
		List<String> lista 	= new ArrayList<String>();
		List<CargaGrupoEvaluacion> lisTareas	= new ArrayList<CargaGrupoEvaluacion>();		
		try{
			String comando = "SELECT CURSO_CARGA_ID, FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ? ORDER BY NOMBRE_CURSO";
			lista = enocJdbc.queryForList(comando, String.class, codigoPersonal, cargaId);			
			for (String cursoCargaId : lista){
				comando = "SELECT CURSO_CARGA_ID, EVALUACION_ID, NOMBRE_EVALUACION, "+
						"TO_CHAR(FECHA,'YYYY/MM/DD HH24:MI:SS') AS FECHA, ESTRATEGIA_ID, VALOR, " +
						"TIPO, EVALUACION_E42, ENVIAR "+
						"FROM ENOC.CARGA_GRUPO_EVALUACION "+ 
						"WHERE CURSO_CARGA_ID = ? AND FECHA = to_date(?,'dd/mm/yyyy')";
				Object[] parametros = new Object[] {cursoCargaId, fecha};
				lisTareas = enocJdbc.query(comando, new CargaGrupoEvaluacionMapper(), parametros);
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoEvaluacionDao|getTareas|:"+ex);
		}
		
		return lisTareas;
	}
	
	public CargaGrupoEvaluacion mapeaRegId(  String cursoCargaId, String evaluacionId ) {
		
		CargaGrupoEvaluacion objeto = new CargaGrupoEvaluacion();
		
		try{
			String comando = "SELECT "+
				" CURSO_CARGA_ID, EVALUACION_ID, NOMBRE_EVALUACION, "+
				" TO_CHAR(FECHA,'YYYY/MM/DD HH24:MI:SS') AS FECHA, ESTRATEGIA_ID, VALOR, " +
				" TIPO, EVALUACION_E42, ENVIAR "+
				" FROM ENOC.CARGA_GRUPO_EVALUACION "+ 
				" WHERE CURSO_CARGA_ID = ? "+
				" AND EVALUACION_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {cursoCargaId,evaluacionId};
			objeto = enocJdbc.queryForObject(comando, new CargaGrupoEvaluacionMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoEvaluacionDao|mapeaRegId|:"+ex);
		}
		return objeto;
	}
	
	public boolean existeReg( String cursoCargaId, String evaluacionId) {
		boolean	ok = false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA_GRUPO_EVALUACION WHERE CURSO_CARGA_ID = ? AND EVALUACION_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {cursoCargaId,evaluacionId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoEvaluacionDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public boolean existeReg( String cursoCargaId) {
		boolean	ok = false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA_GRUPO_EVALUACION WHERE CURSO_CARGA_ID = ?";
			Object[] parametros = new Object[] {cursoCargaId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoEvaluacionDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public boolean existeRegE42( String evaluacionE42) {
		boolean ok = false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA_GRUPO_EVALUACION "+ 
				"WHERE EVALUACION_E42 = TO_NUMBER(?,'9999999')";
			Object[] parametros = new Object[] {evaluacionE42};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoEvaluacionDao|existeRegE42|:"+ex);
		}
		return ok;
	}
	
	
	public String getNombreEvaluacion( String cursoCargaId, String evaluacionId) {
		String nombre = "-";		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA_GRUPO_EVALUACION WHERE CURSO_CARGA_ID = ? AND EVALUACION_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {cursoCargaId,evaluacionId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				comando = "SELECT NOMBRE_EVALUACION FROM ENOC.CARGA_GRUPO_EVALUACION WHERE CURSO_CARGA_ID = ? AND EVALUACION_ID = TO_NUMBER(?,'99')";
				nombre = enocJdbc.queryForObject(comando, String.class,parametros );
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoEvaluacionDao|getNombreEvaluacion|:"+ex);
		}
		return nombre;
	}	
	
	public String maximoReg( String cursoCargaId) {
		
		String maximo = "1";
		
		try{
			String comando = "SELECT COALESCE(MAX(EVALUACION_ID)+1,1) AS MAXIMO "+
				"FROM ENOC.CARGA_GRUPO_EVALUACION "+ 
				"WHERE CURSO_CARGA_ID = ?";
			Object[] parametros = new Object[] {cursoCargaId};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
 				maximo = enocJdbc.queryForObject(comando, String.class, parametros);
			}
 			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoEvaluacionDao|maximoReg|:"+ex);
		}
		return maximo;
	}
	
	public boolean notasReg( String cursoCargaId, String evaluacionId) {
		
		boolean bOk	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.KRDX_ALUMNO_EVAL"
					+ " WHERE CURSO_CARGA_ID = ?"
					+ " AND EVALUACION_ID = TO_NUMBER(?,'99')"
					+ " AND NOTA!=0";
			Object[] parametros = new Object[] {cursoCargaId, evaluacionId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				bOk = true;
			} 			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoEvaluacionDao|notasReg|:"+ex);
		}
		return bOk;
	}
	
	public HashMap<String,KrdxAlumnoEval> mapNotasEval( String cursoCargaId ) {
		
		HashMap<String,KrdxAlumnoEval> mapa = new HashMap<String,KrdxAlumnoEval>();
		List<KrdxAlumnoEval> lista 			= new ArrayList<KrdxAlumnoEval>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CURSO_CARGA_ID, EVALUACION_ID, COALESCE(NOTA,0) AS NOTA, EVALUACION_E42"
					+ " FROM ENOC.KRDX_ALUMNO_EVAL WHERE CURSO_CARGA_ID = ?";
			lista = enocJdbc.query(comando,new KrdxAlumnoEvalMapper(), cursoCargaId);
			for(KrdxAlumnoEval objeto : lista){				
				mapa.put(objeto.getCursoCargaId()+objeto.getEvaluacionId(), objeto);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoEvaluacionDao|mapNotasEval|:"+ex);
		}
		return mapa;
	}
	
	public boolean deleteNotas( String cursoCargaId, String evaluacionId) {
		boolean bOk	= false;
		
		try{
			String comando = "DELETE FROM ENOC.KRDX_ALUMNO_EVAL WHERE CURSO_CARGA_ID = ? AND EVALUACION_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {cursoCargaId, evaluacionId};
			if (enocJdbc.update(comando,parametros) >= 1){
				bOk = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoEvaluacionDao|deleteNotas|:"+ex);
		}
		return bOk;
	}
	
	public double promedioEstrategia( String cursoCargaId, String evaluacionId) {
		List<String> lista = new ArrayList<String>();
		double promedio=0, alumnos=0;
		
		try{
			String comando = "SELECT NOTA FROM ENOC.KRDX_ALUMNO_EVAL"
					+ " WHERE CURSO_CARGA_ID = ?"
					+ " AND EVALUACION_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {cursoCargaId, evaluacionId};
			lista = enocJdbc.queryForList(comando, String.class, parametros);
			for(String nota : lista){
				alumnos++;
				promedio += Double.valueOf(nota);
			}
			promedio = promedio / alumnos;
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoEvaluacionDao|promedioEstrategia|:"+ex);
		}
		return promedio;
	}
	
	public HashMap<String, Double> mapPromedioEstrategia(String cursoCargaId) {
		
		HashMap<String, Double> mapa = new HashMap<String, Double>();
		List<aca.Mapa>	lista 		 = new ArrayList<aca.Mapa>();	
		try{
			String comando = "SELECT CURSO_CARGA_ID||EVALUACION_ID AS LLAVE, COALESCE(NOTA,0) AS VALOR FROM ENOC.KRDX_ALUMNO_EVAL WHERE CURSO_CARGA_ID = ?";
			Object[] parametros = new Object[] {cursoCargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa m : lista){
				mapa.put(m.getLlave(), Double.parseDouble(m.getValor()));
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoEvaluacionDao|mapPromedioEstrategia|:"+ex);
		}
		return mapa;
	}
	
	public int NumEstVirtual( String cursoCargaId) {
		int cuenta = 0;		
		try{
			String comando = "SELECT COALESCE(COUNT(EVALUACION_ID),0) AS CUENTA"
					+ " FROM ENOC.CARGA_GRUPO_EVALUACION"
					+ " WHERE CURSO_CARGA_ID = ? AND (EVALUACION_E42=0 OR EVALUACION_E42 IS NULL)";
			Object[] parametros = new Object[] {cursoCargaId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				cuenta = enocJdbc.queryForObject(comando, Integer.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoEvaluacionDao|NumEstVirtual|:"+ex);
		}
		return cuenta;
	}
	
	public int NumEstE42( String cursoCargaId) {
		int cuenta = 0;
		
		try{
			String comando = "SELECT COALESCE(COUNT(EVALUACION_ID),0) AS CUENTA FROM ENOC.CARGA_GRUPO_EVALUACION"
					+ " WHERE CURSO_CARGA_ID = ? AND (EVALUACION_E42 != 0 AND EVALUACION_E42 IS NOT NULL)";
			Object[] parametros = new Object[] {cursoCargaId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				cuenta = enocJdbc.queryForObject(comando, Integer.class, parametros);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoEvaluacionDao|NumEstE42|:"+ex);
		}
		return cuenta;
	}
	
	public int NumEstrategias( String cursoCargaId, String Tipo) {
		
		String buscarTipo = "";
		int cuenta 		  = 0;
		
		try{
			if (Tipo.equals("VIRTUAL")) 
				buscarTipo = "AND EVALUACION_E42=0 ";
			else
				buscarTipo = "AND EVALUACION_E42!=0 ";
			
			String comando = "SELECT COALESCE(COUNT(EVALUACION_ID),0) AS CUENTA "+
				"FROM ENOC.CARGA_GRUPO_EVALUACION "+ 
				"WHERE CURSO_CARGA_ID = ? " +buscarTipo;
			Object[] parametros = new Object[] {cursoCargaId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				cuenta = enocJdbc.queryForObject(comando, Integer.class, parametros);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoEvaluacionDao|NumEstrategias|:"+ex);
		}		
		return cuenta;
	}
	
	public int getNumEst( String cursoCargaId, String Tipo) {
		
		String buscarTipo		= "";
		int cuenta				= 0;
		
		try{
			if (Tipo.equals("VIRTUAL")) 
				buscarTipo = "AND EVALUACION_E42=0 ";
			else if (Tipo.equals("E42"))
				buscarTipo = "AND EVALUACION_E42!=0 ";
			else
				buscarTipo = " ";
			
			String comando = "SELECT COALESCE(COUNT(EVALUACION_ID),0) AS CUENTA "+
				"FROM ENOC.CARGA_GRUPO_EVALUACION "+ 
				"WHERE CURSO_CARGA_ID = ? " +buscarTipo;
			Object[] parametros = new Object[] {cursoCargaId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				cuenta = enocJdbc.queryForObject(comando, Integer.class, parametros);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoEvaluacionDao|getNumEst|:"+ex);
		}
		return cuenta;
	}
	
	public String getEvaluacionId( String cursoCargaId, String evaluacionE42 ) {
		
		String evaluacion		= "0";
		
		try{
			String comando = "SELECT EVALUACION_ID FROM ENOC.CARGA_GRUPO_EVALUACION "+ 
				"WHERE CURSO_CARGA_ID = ? "+				
				"AND EVALUACION_E42 = TO_NUMBER(?,'9999999')";
			Object[] parametros = new Object[] {cursoCargaId,evaluacionE42};
			evaluacion = enocJdbc.queryForObject(comando,String.class,parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoEvaluacionDao|getEvaluacionId|:"+ex);
		}
		return evaluacion;
	}
	
	public String getCursoCargaId( String evaluacionE42 ) {
		String evaluacion		= "0";
		
		try{
			String comando = "SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO_EVALUACION "+ 
				"WHERE EVALUACION_E42 = TO_NUMBER(?,'9999999')";
			Object[] parametros = new Object[] {evaluacionE42};
			evaluacion = enocJdbc.queryForObject(comando,String.class,parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoEvaluacionDao|getCursoCargaId|:"+ex);
		}
		return evaluacion;
	}
	
	public boolean getTieneActividades( String cursoCargaId, String evaluacionId ) {
		boolean ok = false;
		
		try{
			String comando = "SELECT CURSO_CARGA_ID||EVALUACION_ID FROM ENOC.CARGA_GRUPO_ACTIVIDAD "+ 
				"WHERE CURSO_CARGA_ID = ? AND EVALUACION_ID = ?";
			Object[] parametros = new Object[] {cursoCargaId, evaluacionId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoEvaluacionDao|getTieneActividades|:"+ex);
		}
		return ok;
	}
	
	public int deleteGpoEval( String cursoCargaId ) {
		
		int numEval	= 0;		
		try{
			String comando = "SELECT COUNT(CURSO_CARGA_ID) FROM ENOC.CARGA_GRUPO_EVALUACION WHERE CURSO_CARGA_ID = ?";
			if (enocJdbc.queryForObject(comando, Integer.class, cursoCargaId) >= 1) {
				comando = "DELETE FROM ENOC.CARGA_GRUPO_EVALUACION WHERE CURSO_CARGA_ID = ?";
				numEval = enocJdbc.update(comando, cursoCargaId);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoEvaluacionDao|deleteGpoEval|:"+ex);
		}
		return numEval;
	}
	
	
	public List<CargaGrupoEvaluacion> getTareas( String codigoPersonal, String cargaId, String fechaI, String fechaF) {
		
		List<CargaGrupoEvaluacion> lista = new ArrayList<CargaGrupoEvaluacion>();
		
		try{
			String comando = "SELECT CURSO_CARGA_ID, EVALUACION_ID, NOMBRE_EVALUACION,"
					+ " TO_CHAR(FECHA,'YYYY/MM/DD HH24:MI:SS') AS FECHA, ESTRATEGIA_ID, VALOR,"
					+ " TIPO, EVALUACION_E42, ENVIAR"
					+ " FROM ENOC.CARGA_GRUPO_EVALUACION"
					+ " WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ?"
					+ " AND FECHA BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY')"
					+ " AND CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.KRDX_CURSO_ACT WHERE CODIGO_PERSONAL = ? AND SUBSTR(CURSO_CARGA_ID,1,6) = ?)"
					+ " ORDER BY FECHA, ENOC.NOMBRE_MATERIA(CURSO_ORIGEN(CURSO_CARGA_ID))";
			Object[] parametros = new Object[] {codigoPersonal, cargaId, fechaI, fechaF};
			lista = enocJdbc.query(comando, new CargaGrupoEvaluacionMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoEvaluacionDao|getTareas|:"+ex);
		}
		return lista;
	}
	
	public List<CargaGrupoEvaluacion> getLista( String cursoCargaId, String orden ){
		
		List<CargaGrupoEvaluacion> lista = new ArrayList<CargaGrupoEvaluacion>();		
		try{
			String comando = "SELECT CURSO_CARGA_ID, EVALUACION_ID, NOMBRE_EVALUACION, TO_CHAR(FECHA,'YYYY/MM/DD HH24:MI:SS') AS FECHA, ESTRATEGIA_ID, VALOR, TIPO, EVALUACION_E42, ENVIAR "+
				" FROM ENOC.CARGA_GRUPO_EVALUACION "+ 
				" WHERE CURSO_CARGA_ID = ? "+orden;
			lista = enocJdbc.query(comando, new CargaGrupoEvaluacionMapper(), cursoCargaId);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoEvaluacionDao|getLista|:"+ex);
		}
		return lista;
	}
	
	public List<CargaGrupoEvaluacion> getListAll( String orden ) {
		
		List<CargaGrupoEvaluacion> lista = new ArrayList<CargaGrupoEvaluacion>();		
		try{
			String comando = "SELECT CURSO_CARGA_ID, EVALUACION_ID, NOMBRE_EVALUACION,"
					+ " TO_CHAR(FECHA,'YYYY/MM/DD HH24:MI:SS') AS FECHA, ESTRATEGIA_ID, VALOR,"
					+ " TIPO, EVALUACION_E42, ENVIAR"
					+ " FROM ENOC.CARGA_GRUPO_EVALUACION "+orden;
			lista = enocJdbc.query(comando, new CargaGrupoEvaluacionMapper());						
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoEvaluacionDao|getListAll|:"+ex);
		}
		return lista;
	}
	public int getNumEstrategias( String cursoCargaId) {
		int num = 0;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA_GRUPO_EVALUACION WHERE CURSO_CARGA_ID = ?";
			if (enocJdbc.queryForObject(comando, Integer.class, cursoCargaId) >= 1) {
				num = enocJdbc.queryForObject(comando, Integer.class, cursoCargaId);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoEvaluacionDao|getNumEstrategias|:"+ex);
		}
		return num;		
	}
	
	public int getNumEstrategiasEvaluadas( String cursoCargaId) {
		int num = 0;		
		try{
			String comando = "SELECT ENOC.NUM_ESTRA_EVAL('"+cursoCargaId+"') FROM DUAL";
			if (enocJdbc.queryForObject(comando, Integer.class) >= 1) {
				num = enocJdbc.queryForObject(comando, Integer.class);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoEvaluacionDao|getNumEstrategiasEvaluadas|:"+ex);
		}
		return num;
		
	}
	
	public int getNumAlumnosBaja( String cursoCargaId) {
		int num = 0;		
		try{
			String comando = "SELECT COUNT(CODIGO_PERSONAL) FROM ENOC.KRDX_CURSO_ACT"
					+ " WHERE CURSO_CARGA_ID = ?"
					+ " AND TIPOCAL_ID IN('3','4')";
			if (enocJdbc.queryForObject(comando, Integer.class, cursoCargaId) >= 1) {
				num = enocJdbc.queryForObject(comando, Integer.class, cursoCargaId);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoEvaluacionDao|getNumAlumnosBaja|:"+ex);
		}
		return num;
		
	}
	
	public String getAvanceEvaluacion( String cursoCargaId ){
		String avance = "0";		
		try{
			
			String comando = "SELECT COUNT(CURSO_CARGA_ID) FROM CARGA_GRUPO_EVALUACION CGE WHERE CURSO_CARGA_ID = ?";
			Object[] parametros = new Object[] {cursoCargaId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) > 0) {
				comando = "SELECT "
						+ " SUM( CASE WHEN ENOC.ACTIVIDAD_POR_EVAL(CGE.CURSO_CARGA_ID,CGE.EVALUACION_ID) >= 1 "
						+ " THEN ROUND(ENOC.ACTIVIDAD_CON_EVAL(CGE.CURSO_CARGA_ID,CGE.EVALUACION_ID) / (ENOC.MATERIA_ALUMNOS(CURSO_CARGA_ID) * ENOC.ACTIVIDAD_POR_EVAL(CGE.CURSO_CARGA_ID,CGE.EVALUACION_ID)) * VALOR,2)"
						+ " ELSE ROUND(ENOC.EVALUACION_CON_NOTA(CGE.CURSO_CARGA_ID,CGE.EVALUACION_ID) / ENOC.MATERIA_ALUMNOS(CURSO_CARGA_ID) * VALOR,2) "
						+ "	END) "
						+ "	FROM CARGA_GRUPO_EVALUACION CGE"
						+ "	WHERE CURSO_CARGA_ID = ?"
						+ " AND ENOC.MATERIA_ALUMNOS(CURSO_CARGA_ID) >= 1";
				avance = enocJdbc.queryForObject(comando, String.class, parametros);
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoEvaluacionDao|getAvanceEvaluacion|:"+ex);
		}
		return avance;		
	}
	
	public List<String> lisMesesPorCarga( String cargaId, String orden) {
		
		List<String> lista		= new ArrayList<String>();				
		try{
			String comando = " SELECT DISTINCT(TO_CHAR(FECHA,'MM')) FROM ENOC.CARGA_GRUPO_EVALUACION"
					+ " WHERE CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = ?) "+ orden;
			lista = enocJdbc.queryForList(comando, String.class, cargaId);
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoEvaluacionDao|lisMesesPorCarga|:"+ex);
		}		
		return lista;
		
	}
	
	public List<String> lisMesesPorCargayCarrera( String cargaId, String carreraId, String orden) {		
		List<String> lista		= new ArrayList<String>();				
		try{
			String comando = " SELECT DISTINCT(TO_CHAR(FECHA,'MM')) FROM ENOC.CARGA_GRUPO_EVALUACION"
					+ " WHERE CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = ? AND CARRERA_ID = ?) "+ orden;
			lista = enocJdbc.queryForList(comando, String.class, cargaId, carreraId);
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoEvaluacionDao|lisMesesPorCargayCarrera|:"+ex);
		}		
		return lista;
		
	}
	
	public List<String> getEstrategiasMes( String cargaId, String orden) {
		
		List<String> lista		= new ArrayList<String>();
		List<String> lisEst	= new ArrayList<String>();		
		try{
			String comando = "SELECT CURSO_CARGA_ID||'@@'||VALOR||'@@'||TO_CHAR(FECHA,'MM')||'@@'||EVALUACION_ID" +
					" FROM ENOC.CARGA_GRUPO_EVALUACION" + 
					" WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ? "+orden;
			lista = enocJdbc.queryForList(comando, String.class, cargaId);
			for(String dato : lista){
				String columnas[] = dato.split("@@");
				lisEst.add(columnas[0]);
				lisEst.add(columnas[1]);
				lisEst.add(columnas[2]);
				lisEst.add(columnas[3]);					
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoEvaluacionDao|getEstrategiasMes|:"+ex);
		}
		
		return lisEst;
		
	}
	
	public List<String> getEstMes( String cargaId, String orden) {		
		List<String> lista 	= new ArrayList<String>();
		List<String> lisEst = new ArrayList<String>();		
		try{
			String comando = "SELECT " 
			  		+ " ENOC.FACULTAD(A.CARRERA_ID)||'@@'||A.CARRERA_ID||'@@'||B.CURSO_CARGA_ID||'@@'||B.VALOR||'@@'||TO_CHAR(B.FECHA,'MM')"
			  		+ " FROM ENOC.CARGA_GRUPO A, ENOC.CARGA_GRUPO_EVALUACION B"
			  		+ " WHERE A.CARGA_ID = ?"
			  		+ " AND B.CURSO_CARGA_ID = A.CURSO_CARGA_ID "+ orden;
			lista = enocJdbc.queryForList(comando, String.class, cargaId);			
			for(String dato : lista){
				String columnas[] = dato.split("@@");
				lisEst.add(columnas[0]);
				lisEst.add(columnas[1]);
				lisEst.add(columnas[2]);
				lisEst.add(columnas[3]);
				lisEst.add(columnas[4]);		
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoEvaluacionDao|getEstMes|:"+ex);
		}		
		return lista;
		
	}	
	
	public HashMap<String, String> mapSumaEsquema( String cargaId) {		
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa>	lista 		 = new ArrayList<aca.Mapa>();	
		try{
			String comando = "SELECT CURSO_CARGA_ID AS LLAVE, COALESCE(SUM(VALOR),0 ) AS VALOR "
					+ " FROM ENOC.CARGA_GRUPO_EVALUACION "
					+ " WHERE SUBSTR(CURSO_CARGA_ID, 1,6) = ?"+
					  " GROUP BY CURSO_CARGA_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), cargaId);
			for (aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoEvaluacionDao|mapSumaEsquema|:"+ex);
		}
		return mapa;
	}

	public HashMap<String, String> mapaNumEvaluciones(String cargaId) {		
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa>	lista 		 = new ArrayList<aca.Mapa>();	
		try{
			String comando = "SELECT CURSO_CARGA_ID AS LLAVE, COUNT(EVALUACION_ID) AS VALOR FROM CARGA_GRUPO_EVALUACION WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ? GROUP BY CURSO_CARGA_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), new Object[] {cargaId});
			for (aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoEvaluacionDao|mapaNumEvalauciones|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaAvanceMaestro(String codigoPersonal, String cargaId) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa>	lista 		 = new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT CURSO_CARGA_ID AS LLAVE,"
					+ " SUM("
					+ " 	CASE WHEN ENOC.ACTIVIDAD_POR_EVAL(CGE.CURSO_CARGA_ID,CGE.EVALUACION_ID) >= 1 "
					+ "		THEN ROUND(ENOC.ACTIVIDAD_CON_EVAL(CGE.CURSO_CARGA_ID,CGE.EVALUACION_ID) / (ENOC.MATERIA_ALUMNOS(CURSO_CARGA_ID) * ENOC.ACTIVIDAD_POR_EVAL(CGE.CURSO_CARGA_ID,CGE.EVALUACION_ID)) * VALOR,2) "
					+ " 	ELSE ROUND(ENOC.EVALUACION_CON_NOTA(CGE.CURSO_CARGA_ID,CGE.EVALUACION_ID) / ENOC.MATERIA_ALUMNOS(CURSO_CARGA_ID) * VALOR,2) "
					+ "		END) "
					+ " AS VALOR"
					+ " FROM CARGA_GRUPO_EVALUACION CGE"
					+ " WHERE CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ?)"
					+ " AND ENOC.MATERIA_ALUMNOS(CURSO_CARGA_ID) >= 1"
					+ " GROUP BY CURSO_CARGA_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), new Object[] {codigoPersonal, cargaId});
			for (aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoEvaluacionDao|mapaAvanceMaestro|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaAvanceCargayFacultad(String cargaId, String facultadId) {		
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa>	lista 		 = new ArrayList<aca.Mapa>();	
		try{
			String comando = "SELECT CURSO_CARGA_ID AS LLAVE,"
					+ " SUM("
					+ " 	CASE WHEN ENOC.ACTIVIDAD_POR_EVAL(CGE.CURSO_CARGA_ID,CGE.EVALUACION_ID) >= 1 "
					+ "		THEN ROUND(ENOC.ACTIVIDAD_CON_EVAL(CGE.CURSO_CARGA_ID,CGE.EVALUACION_ID) / (ENOC.MATERIA_ALUMNOS(CURSO_CARGA_ID) * ENOC.ACTIVIDAD_POR_EVAL(CGE.CURSO_CARGA_ID,CGE.EVALUACION_ID)) * VALOR,2) "
					+ " 	ELSE ROUND(ENOC.EVALUACION_CON_NOTA(CGE.CURSO_CARGA_ID,CGE.EVALUACION_ID) / ENOC.MATERIA_ALUMNOS(CURSO_CARGA_ID) * VALOR,2) "
					+ "		END) "
					+ " AS VALOR"
					+ " FROM CARGA_GRUPO_EVALUACION CGE"
					+ " WHERE CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = ? AND FACULTAD(CARRERA_ID) = ?)"
					+ " AND ENOC.MATERIA_ALUMNOS(CURSO_CARGA_ID) >= 1"
					+ " GROUP BY CURSO_CARGA_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), new Object[] {cargaId, facultadId});
			for (aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoEvaluacionDao|mapaAvanceCargayFacultad|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaAvancePorEvaluacion(String cursoCargaId ) {		
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa>	lista 		 = new ArrayList<aca.Mapa>();	
		try{
			String comando = "SELECT EVALUACION_ID AS LLAVE,"
					+ " SUM("
					+ " 	CASE WHEN ENOC.ACTIVIDAD_POR_EVAL(CGE.CURSO_CARGA_ID,CGE.EVALUACION_ID) >= 1 "
					+ "		THEN ROUND(ENOC.ACTIVIDAD_CON_EVAL(CGE.CURSO_CARGA_ID,CGE.EVALUACION_ID) / (ENOC.MATERIA_ALUMNOS(CURSO_CARGA_ID) * ENOC.ACTIVIDAD_POR_EVAL(CGE.CURSO_CARGA_ID,CGE.EVALUACION_ID)) * VALOR,2) "
					+ " 	ELSE ROUND(ENOC.EVALUACION_CON_NOTA(CGE.CURSO_CARGA_ID,CGE.EVALUACION_ID) / ENOC.MATERIA_ALUMNOS(CURSO_CARGA_ID) * VALOR,2) "
					+ "		END) "
					+ " AS VALOR"
					+ " FROM CARGA_GRUPO_EVALUACION CGE"
					+ " WHERE CURSO_CARGA_ID = ?"
					+ " AND ENOC.MATERIA_ALUMNOS(CURSO_CARGA_ID) >= 1"
					+ " GROUP BY EVALUACION_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), new Object[] {cursoCargaId});
			for (aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoEvaluacionDao|mapaNotasEnEvaluaciones|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaNotasEnEvaluaciones(String cursoCargaId ) {		
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa>	lista 		 = new ArrayList<aca.Mapa>();	
		try{
			String comando = "SELECT EVALUACION_ID AS LLAVE,"
					+ " ENOC.EVALUACION_CON_NOTA(CGE.CURSO_CARGA_ID,CGE.EVALUACION_ID) AS VALOR"					
					+ " FROM CARGA_GRUPO_EVALUACION CGE"
					+ " WHERE CURSO_CARGA_ID = ?"
					+ " AND ENOC.MATERIA_ALUMNOS(CURSO_CARGA_ID) >= 1";					
			lista = enocJdbc.query(comando, new aca.MapaMapper(), new Object[] {cursoCargaId});
			for (aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoEvaluacionDao|mapaNotasEnEvaluaciones|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaNotasActividadesPorEvaluacion(String cursoCargaId ) {		
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa>	lista 		 = new ArrayList<aca.Mapa>();	
		try{
			String comando = "SELECT EVALUACION_ID AS LLAVE,"
					+ " ENOC.ACTIVIDAD_CON_EVAL(CGE.CURSO_CARGA_ID, CGE.EVALUACION_ID) AS VALOR"					
					+ " FROM CARGA_GRUPO_EVALUACION CGE"
					+ " WHERE CURSO_CARGA_ID = ?"
					+ " AND ENOC.MATERIA_ALUMNOS(CURSO_CARGA_ID) >= 1";					
			lista = enocJdbc.query(comando, new aca.MapaMapper(), new Object[] {cursoCargaId});
			for (aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoEvaluacionDao|mapaNotasActividadesPorEvaluacion|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaNotasEnActividades(String cursoCargaId ) {		
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa>	lista 		 = new ArrayList<aca.Mapa>();	
		try{
			String comando = "SELECT ACTIVIDAD_ID AS LLAVE,"
					+ " ENOC.ACTIVIDAD_CON_NOTA(CGA.ACTIVIDAD_ID) AS VALOR"					 
					+ " FROM CARGA_GRUPO_ACTIVIDAD CGA"
					+ " WHERE CURSO_CARGA_ID = ?"
					+ " AND ENOC.MATERIA_ALUMNOS(CURSO_CARGA_ID) >= 1";					
			lista = enocJdbc.query(comando, new aca.MapaMapper(), new Object[] {cursoCargaId});
			for (aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoEvaluacionDao|mapaNotasEnEvaluaciones|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaAvancePorActividades(String cursoCargaId ) {		
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa>	lista 		 = new ArrayList<aca.Mapa>();	
		try{
			String comando = "SELECT ACTIVIDAD_ID AS LLAVE,"
					+ " ROUND( ENOC.ACTIVIDAD_CON_NOTA(CGA.ACTIVIDAD_ID) / ENOC.MATERIA_ALUMNOS(CURSO_CARGA_ID) * VALOR,2) AS VALOR"					 
					+ " FROM CARGA_GRUPO_ACTIVIDAD CGA"
					+ " WHERE CURSO_CARGA_ID = ?"
					+ " AND ENOC.MATERIA_ALUMNOS(CURSO_CARGA_ID) >= 1";					
			lista = enocJdbc.query(comando, new aca.MapaMapper(), new Object[] {cursoCargaId});
			for (aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoEvaluacionDao|mapaAvancePorActividades|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaEvalPendientes(String codigoPersonal, String cargaId ){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa>	lista 		 = new ArrayList<aca.Mapa>();	
		try{
			String comando = "SELECT CURSO_CARGA_ID AS LLAVE, COUNT(*) AS VALOR FROM ENOC.CARGA_GRUPO_EVALUACION CGE"
					+ " WHERE CURSO_CARGA_ID IN ( SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ?)"
					+ " AND FECHA < TO_DATE(TO_CHAR(SYSDATE,'DD/MM/YYYY'),'DD/MM/YYYY')"
					+ " AND ENOC.EVALUACION_CON_NOTA(CGE.CURSO_CARGA_ID,CGE.EVALUACION_ID) <= (ENOC.MATERIA_ALUMNOS(CURSO_CARGA_ID)/2)"
					+ " AND ENOC.MATERIA_ALUMNOS(CURSO_CARGA_ID) >= 1"
					+ " GROUP BY CURSO_CARGA_ID";					
			lista = enocJdbc.query(comando, new aca.MapaMapper(), new Object[] {codigoPersonal, cargaId});
			for (aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoEvaluacionDao|mapaEvalPendientes|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaActPendientes(String codigoPersonal, String cargaId){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa>	lista 		 = new ArrayList<aca.Mapa>();	
		try{
			String comando = "SELECT CURSO_CARGA_ID AS LLAVE, COUNT(ACTIVIDAD_ID) AS VALOR FROM ENOC.CARGA_GRUPO_ACTIVIDAD CGA"
					+ " WHERE CURSO_CARGA_ID IN ( SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ?)"
					+ " AND FECHA < TO_DATE(TO_CHAR(SYSDATE,'DD/MM/YYYY'),'DD/MM/YYYY')"
					+ " AND ENOC.ACTIVIDAD_CON_NOTA(CGA.ACTIVIDAD_ID) <= (ENOC.MATERIA_ALUMNOS(CURSO_CARGA_ID)/2)"
					+ " AND ENOC.MATERIA_ALUMNOS(CURSO_CARGA_ID) >= 1"
					+ " GROUP BY CURSO_CARGA_ID";					
			lista = enocJdbc.query(comando, new aca.MapaMapper(), new Object[] {codigoPersonal, cargaId});
			for (aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoEvaluacionDao|mapaActPendientes|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaEstrategiasPorFacultad( String cargaId) {		
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa>	lista 		 = new ArrayList<aca.Mapa>();	
		try{
			String comando = "SELECT CC.FACULTAD_ID AS LLAVE, COUNT(CG.CARRERA_ID) AS VALOR "
					+ " FROM ENOC.CARGA_GRUPO CG, ENOC.CARGA_GRUPO_EVALUACION CGE, ENOC.CAT_CARRERA CC"
					+ " WHERE CG.CARGA_ID = ?"
					+ " AND CGE.CURSO_CARGA_ID = CG.CURSO_CARGA_ID"
					+ " AND CC.CARRERA_ID = CG.CARRERA_ID"
					+ " GROUP BY CC.FACULTAD_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), cargaId);
			for (aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoEvaluacionDao|mapaEstrategiasPorFacultad|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaEstrategiasPorCarrera( String cargaId) {		
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa>	lista 		 = new ArrayList<aca.Mapa>();	
		try{
			String comando = "SELECT CG.CARRERA_ID AS LLAVE, COUNT(CARRERA_ID) AS VALOR FROM CARGA_GRUPO CG, CARGA_GRUPO_EVALUACION CGE"
					+ " WHERE CG.CARGA_ID = ?"
					+ " AND CGE.CURSO_CARGA_ID = CG.CURSO_CARGA_ID"
					+ " GROUP BY CG.CARRERA_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), cargaId);
			for (aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoEvaluacionDao|mapaEstrategiasPorCarrera|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaEstrategiasPorMesyFacultad( String cargaId) {		
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa>	lista 		 = new ArrayList<aca.Mapa>();	
		try{
			String comando = "SELECT CC.FACULTAD_ID||TO_CHAR(CGE.FECHA,'MM') AS LLAVE, COUNT(CG.CARRERA_ID) AS VALOR"
					+ " FROM ENOC.CARGA_GRUPO CG, ENOC.CARGA_GRUPO_EVALUACION CGE, ENOC.CAT_CARRERA CC"
					+ " WHERE CG.CARGA_ID = ?"
					+ " AND CGE.CURSO_CARGA_ID = CG.CURSO_CARGA_ID"
					+ " AND CC.CARRERA_ID = CG.CARRERA_ID"
					+ " GROUP BY CC.FACULTAD_ID, TO_CHAR(CGE.FECHA,'MM')";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), cargaId);
			for (aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoEvaluacionDao|mapaEstrategiasPorFacultad|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaEstrategiasPorMesyCarrera( String cargaId) {		
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa>	lista 		 = new ArrayList<aca.Mapa>();	
		try{
			String comando = "SELECT CG.CARRERA_ID||TO_CHAR(CGE.FECHA,'MM') AS LLAVE, COUNT(CG.CARRERA_ID) AS VALOR FROM ENOC.CARGA_GRUPO CG, ENOC.CARGA_GRUPO_EVALUACION CGE"
					+ " WHERE CG.CARGA_ID = ?"
					+ " AND CGE.CURSO_CARGA_ID = CG.CURSO_CARGA_ID"
					+ " GROUP BY CG.CARRERA_ID, TO_CHAR(CGE.FECHA,'MM')";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), cargaId);
			for (aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoEvaluacionDao|mapaEstrategiasPorCarrera|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaEstrategiasPorMateria( String cargaId, String carreraId) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa>	lista 		 = new ArrayList<aca.Mapa>();	
		try{
			String comando = "SELECT CG.CURSO_CARGA_ID||TO_CHAR(CGE.FECHA,'MM') AS LLAVE, COUNT(CGE.CURSO_CARGA_ID) AS VALOR FROM ENOC.CARGA_GRUPO CG, ENOC.CARGA_GRUPO_EVALUACION CGE"
					+ " WHERE CG.CARGA_ID = ?"
					+ " AND CG.CARRERA_ID = ?"
					+ " AND CGE.CURSO_CARGA_ID = CG.CURSO_CARGA_ID"
					+ " GROUP BY CG.CURSO_CARGA_ID, TO_CHAR(CGE.FECHA,'MM')";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), cargaId, carreraId);
			for (aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoEvaluacionDao|mapaEstrategiasPorMateria|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaSumaPorMateria( String cargaId, String carreraId) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa>	lista 		 = new ArrayList<aca.Mapa>();	
		try{
			String comando = "SELECT CURSO_CARGA_ID AS LLAVE, SUM(VALOR) AS VALOR FROM ENOC.CARGA_GRUPO_EVALUACION"
					+ " WHERE CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = ? AND CARRERA_ID = ?)"
					+ " GROUP BY CURSO_CARGA_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), cargaId, carreraId);
			for (aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoEvaluacionDao|mapaSumaPorMateria|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaSumaPorMateria( String codigoPersonal) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa>	lista 		 = new ArrayList<aca.Mapa>();	
		try{
			String comando = "SELECT CURSO_CARGA_ID AS LLAVE, SUM(VALOR) AS VALOR FROM ENOC.CARGA_GRUPO_EVALUACION"
					+ " WHERE CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO WHERE CODIGO_PERSONAL = ? OR MAESTRO = ?)"
					+ " GROUP BY CURSO_CARGA_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), codigoPersonal,codigoPersonal);
			for (aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoEvaluacionDao|mapaSumaPorMateria|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaEvaluacionPorMateria(String cargaId, String codigoMaestro) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista		 = new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CURSO_CARGA_ID AS LLAVE, SUM(VALOR) AS VALOR FROM ENOC.CARGA_GRUPO_EVALUACION"
					+ " WHERE CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM CARGA_GRUPO WHERE CARGA_ID = ? AND CODIGO_PERSONAL = ?)"
					+ " GROUP BY CURSO_CARGA_ID";	
			lista = enocJdbc.query(comando, new aca.MapaMapper(), cargaId, codigoMaestro );
			for (aca.Mapa map : lista){
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoEvaluacionDao|mapaEvaluacionPorMateria|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, CargaGrupoEvaluacion> mapaEvaluacionPorMateria(String cursoCargaId) {
		HashMap<String, CargaGrupoEvaluacion> mapa = new HashMap<String, CargaGrupoEvaluacion>();
		List<CargaGrupoEvaluacion> lista		 = new ArrayList<CargaGrupoEvaluacion>();		
		try{
			String comando = "SELECT CURSO_CARGA_ID, EVALUACION_ID, NOMBRE_EVALUACION, "
					+ " TO_CHAR(FECHA,'DD/MM/YYYY HH24:MI:SS') AS FECHA, ESTRATEGIA_ID, VALOR, "
					+ " TIPO, EVALUACION_E42, ENVIAR "
					+" FROM ENOC.CARGA_GRUPO_EVALUACION"
					+ " WHERE CURSO_CARGA_ID = ?";	
			lista = enocJdbc.query(comando, new CargaGrupoEvaluacionMapper(), cursoCargaId);
			for (CargaGrupoEvaluacion objeto : lista){
				mapa.put(objeto.getEvaluacionId(), objeto);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoEvaluacionDao|mapaEvaluacionPorMateria|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaUsadas( ) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa>	lista 		 = new ArrayList<aca.Mapa>();	
		try{
			String comando = "SELECT ESTRATEGIA_ID AS LLAVE, COUNT(ESTRATEGIA_ID) AS VALOR FROM CARGA_GRUPO_EVALUACION GROUP BY ESTRATEGIA_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoEvaluacionDao|mapaUsadas|:"+ex);
		}
		return mapa;
	}
	
}