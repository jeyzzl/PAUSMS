package aca.edo.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class EdoAlumnoRespDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired
	EdoDao edoDao;
	
	public boolean insertReg( EdoAlumnoResp resp) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.EDO_ALUMNORESP(EDO_ID, PREGUNTA_ID, CODIGO_PERSONAL, CURSO_CARGA_ID, CODIGO_MAESTRO, RESPUESTA)" +
				" VALUES(TO_NUMBER(?, '99999'), TO_NUMBER(?, '99'), ?, ?, ?, ?)";
			Object[] parametros = new Object[] {resp.getEdoId(), resp.getPreguntaId(),
			resp.getCodigoPersonal(), resp.getCursoCargaId(), resp.getCodigoMaestro(), resp.getRespuesta()};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoAlumnoRespDao|insertReg|:"+ex);
		}
		return ok;
	}	
	
	public boolean updateReg( EdoAlumnoResp resp) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.EDO_ALUMNORESP" + 
				" SET RESPUESTA = ?" +
				" WHERE EDO_ID = TO_NUMBER(?, '99999')" +
				" AND PREGUNTA_ID = TO_NUMBER(?, '99')" +
				" AND CODIGO_PERSONAL = ?" +
				" AND CURSO_CARGA_ID = ?" +
				" AND CODIGO_MAESTRO = ?";
			Object[] parametros = new Object[] {resp.getRespuesta(), resp.getEdoId(),
			resp.getPreguntaId(), resp.getCodigoPersonal(), resp.getCursoCargaId(), resp.getCodigoMaestro()};
						
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoAlumnoRespDao|updateReg|:"+ex);		 
		}
		return ok;
	}	
	
	public boolean deleteReg( String respuesta, String edoId, String preguntaId, String codigoPersonal, String cursoCargaId, String codigoMaestro) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.EDO_ALUMNORESP"+ 
				" WHERE EDO_ID = TO_NUMBER(?, '99999')" +
				" AND PREGUNTA_ID = TO_NUMBER(?, '99')" +
				" AND CODIGO_PERSONAL = ?" +
				" AND CURSO_CARGA_ID = ?" +
				" AND CODIGO_MAESTRO = ?";
			Object[] parametros = new Object[] {respuesta, edoId, preguntaId, codigoPersonal, cursoCargaId, codigoMaestro};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoAlumnoRespDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public EdoAlumnoResp mapeaRegId(String edoId, String preguntaId, String codigoPersonal, String cursoCargaId, String codigoMaestro) {
		EdoAlumnoResp objeto = new EdoAlumnoResp();
		
		try{
			String comando = "SELECT EDO_ID, PREGUNTA_ID, CODIGO_PERSONAL, CURSO_CARGA_ID, CODIGO_MAESTRO, RESPUESTA" +
					" FROM ENOC.EDO_ALUMNORESP" + 
					" WHERE EDO_ID = TO_NUMBER(?, '99999')" +
					" AND PREGUNTA_ID = TO_NUMBER(?, '99')" +
					" AND CODIGO_PERSONAL = ?" +
					" AND CURSO_CARGA_ID = ?" +
					" AND CODIGO_MAESTRO = ?";
			Object[] parametros = new Object[] {edoId, preguntaId, codigoPersonal, cursoCargaId, codigoMaestro};
			objeto = enocJdbc.queryForObject(comando, new EdoAlumnoRespMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoAlumnoRespDao|mapeaRegId|:"+ex);
		}
		return objeto;
	}
	
	public boolean existeReg( String edoId, String preguntaId, String codigoPersonal, String cursoCargaId ){
		boolean ok = false;		
		try{
			String comando = "SELECT COUNT(EDO_ID) FROM ENOC.EDO_ALUMNORESP" + 
						" WHERE EDO_ID = TO_NUMBER(?, '99999')" +
						" AND PREGUNTA_ID = TO_NUMBER(?, '99')" +
						" AND CODIGO_PERSONAL = ?" +
						" AND CURSO_CARGA_ID = ?";
			Object[] parametros = new Object[] {edoId, preguntaId, codigoPersonal, cursoCargaId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoAlumnoRespDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public boolean existeReg(String edoId, String preguntaId, String codigoPersonal, String cursoCargaId, String codigoMaestro) {
		boolean ok = false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.EDO_ALUMNORESP" + 
						" WHERE EDO_ID = TO_NUMBER(?, '99999')" +
						" AND PREGUNTA_ID = TO_NUMBER(?, '99')" +
						" AND CODIGO_PERSONAL = ?" +
						" AND CURSO_CARGA_ID = ?" +
						" AND CODIGO_MAESTRO = ?";
			Object[] parametros = new Object[] {edoId, preguntaId, codigoPersonal, cursoCargaId, codigoMaestro};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoAlumnoRespDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public boolean yaContesto( String edoId, String codigoPersonal, String cursoCargaId) {
		boolean	ok = false;
		
		try{
			String comando = "SELECT * FROM ENOC.EDO_ALUMNORESP" + 
						" WHERE EDO_ID = TO_NUMBER(?, '99999')" +
						" AND CODIGO_PERSONAL = ?" +
						" AND CURSO_CARGA_ID = ?";
			Object[] parametros = new Object[] {edoId, codigoPersonal, cursoCargaId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoAlumnoRespDao|yaContesto|:"+ex);
		}
		return ok;
	}
	
	public String getPromedioMaestro( String edoId, String codigoMaestro) {		
		String promedio	= "";
		
		try{
			String comando = "SELECT TO_CHAR(COALESCE((SUM(RESPUESTA)/COUNT(RESPUESTA)*20), 0), '990.99') AS PROMEDIO FROM ENOC.EDO_ALUMNORESP A" + 
						" WHERE A.EDO_ID = TO_NUMBER(?, '99999')" +
						" AND A.CODIGO_MAESTRO = ?" +
						" AND A.PREGUNTA_ID IN (SELECT B.PREGUNTA_ID FROM ENOC.EDO_ALUMNOPREG B" + 
						" WHERE B.EDO_ID = A.EDO_ID" +
						" AND B.TIPO = 'O')";
			Object[] parametros = new Object[] {edoId, codigoMaestro};
			promedio = enocJdbc.queryForObject(comando,String.class,parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoAlumnoRespDao|getPromedioMaestro|:"+ex);
		}
		return promedio;
	}
	
	public String getPromedioEvaluacion( String edoId) {
		String promedio	= "";
		
		try{
			String comando = "SELECT TO_CHAR(COALESCE(((SUM(RESPUESTA)/COUNT(RESPUESTA))*20), 0), '990.99') AS PROMEDIO FROM ENOC.EDO_ALUMNORESP A" + 
						" WHERE A.EDO_ID = TO_NUMBER(?, '99999')" +					
						" AND A.PREGUNTA_ID IN (SELECT B.PREGUNTA_ID FROM ENOC.EDO_ALUMNOPREG B" + 
						" WHERE B.EDO_ID = A.EDO_ID" +
						" AND B.TIPO = 'O')";
			Object[] parametros = new Object[] {edoId};
			promedio = enocJdbc.queryForObject(comando,String.class,parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoAlumnoRespDao|getPromedioEvaluacion|:"+ex);
		}
		return promedio;
	}
	
	// CAST(AVG(RESPUESTA)*20 AS NUMERIC(10,2)) AS PROMEDIO
	public String getPromedioMateria( String cursoCargaId, String edoId) {
		String promedio	= "";
		
		try{
			String comando = "SELECT COALESCE(CAST(AVG(TO_NUMBER(RESPUESTA,'99'))*20 AS NUMERIC(10,2)),0) AS PROMEDIO FROM ENOC.EDO_ALUMNORESP A"
						+ " WHERE A.CURSO_CARGA_ID = ?"
						+ " AND EDO_ID = TO_NUMBER(?,'99')"
						+ " AND A.PREGUNTA_ID IN "
						+ "		(SELECT B.PREGUNTA_ID FROM ENOC.EDO_ALUMNOPREG B WHERE B.EDO_ID = A.EDO_ID AND SECCION = 'B' AND B.TIPO = 'O')";
			Object[] parametros = new Object[] {cursoCargaId, edoId};
			promedio = enocJdbc.queryForObject(comando,String.class,parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoAlumnoRespDao|getPromedioMateria|:"+ex);
		}
		return promedio;
	}
	
	public HashMap<String,String> mapaPromedioMaterias( String cargaId, String codigoPersonal){
		
		HashMap<String, String> mapa  = new HashMap<String, String>();
		List<aca.Mapa> 			lista = new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CURSO_CARGA_ID AS LLAVE, COALESCE(CAST(AVG(TO_NUMBER(RESPUESTA,'99'))*20 AS NUMERIC(10,2)),0) AS VALOR FROM ENOC.EDO_ALUMNORESP A"
						+ " WHERE SUBSTR(A.CURSO_CARGA_ID,1,6) = ?"						
						+ " AND A.PREGUNTA_ID IN "
						+ "		(SELECT B.PREGUNTA_ID FROM ENOC.EDO_ALUMNOPREG B WHERE B.EDO_ID = A.EDO_ID AND SECCION = 'B' AND B.TIPO = 'O')"
						+ " AND A.CODIGO_MAESTRO = ?"
						+ " GROUP BY A.CURSO_CARGA_ID";
			Object[] parametros = new Object[] {cargaId, codigoPersonal};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoAlumnoRespDao|mapaPromedioMaterias|::"+cargaId+"::"+codigoPersonal+"::"+ex);
		}
		return mapa;
	}	
	
	public HashMap<String,String> mapaContestaron( String cargaId){
		
		HashMap<String, String> mapa  = new HashMap<String, String>();
		List<aca.Mapa> 			lista = new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CURSO_CARGA_ID AS LLAVE, COUNT(DISTINCT(CODIGO_PERSONAL)) AS VALOR FROM ENOC.EDO_ALUMNORESP WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ? GROUP BY CURSO_CARGA_ID";
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoAlumnoRespDao|mapaContestaron|:"+ex);
		}
		return mapa;
	}
	
	
	/**
	 * @author Etorres
	 * @return El numero de alumnos que faltan por contestar el insctrumento de evaluacion
	 * @param conn Conexion a la base de datos
	 * @param cursoCargaId El codigo de la materia
	 **/
	public String getAlumFaltantesMateria( String cursoCargaId) {
		String numAlum = "";
		
		try{
			String comando = "SELECT COUNT(CODIGO_PERSONAL) AS NUM_ALUM FROM ENOC.KRDX_CURSO_ACT" + 
						" WHERE CURSO_CARGA_ID = ?" +
						" AND CODIGO_PERSONAL NOT IN (SELECT DISTINCT(CODIGO_PERSONAL) FROM ENOC.EDO_ALUMNORESP" + 
						" WHERE CURSO_CARGA_ID = ?)";
			Object[] parametros = new Object[] {cursoCargaId, cursoCargaId};
			numAlum = enocJdbc.queryForObject(comando,String.class,parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoAlumnoRespDao|getAlumFaltantesMateria|:"+ex);
		}
		return numAlum;
	}	
	
	/**
	 * @author Elifo
	 * @return El numero de alumnos que han evaluado la materia
	 * @param conn Conexion a la base de datos
	 * @param cursoCargaId El cursoCargaId de la materia a buscar
	 **/
	public String getNumAlumEval( String cursoCargaId) {
		String numAlum = "";
		
		try{
			String comando = "SELECT COUNT(DISTINCT(CODIGO_PERSONAL)) AS NUM_ALUM FROM ENOC.EDO_ALUMNORESP" + 
						" WHERE CURSO_CARGA_ID = ?";
			Object[] parametros = new Object[] {cursoCargaId};
			numAlum = enocJdbc.queryForObject(comando,String.class,parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoAlumnoRespDao|getNumAlumEval|:"+ex);
		}
		return numAlum;
	}
	
	/**
	 * @author Elifo
	 * @return El número de alumnos que han evaluado al profesor
	 * @param conn conexión a la base de datos
	 * @param edoId el edoId en el cual se buscaran los alumnos
	 * @param codigoMaestro El código del maestro para el cual se buscarán los alumnos
	 * */
	public String getAlumEvalProf( String edoId, String codigoMaestro) {
		String numAlum = "";
		
		try{
			String comando = "SELECT COALESCE(COUNT(DISTINCT(CODIGO_PERSONAL)),0) AS NUM_ALUM FROM ENOC.EDO_ALUMNORESP" + 
						" WHERE EDO_ID = ?" +
						" AND CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO" + 
						" WHERE CODIGO_PERSONAL = ?)";
			Object[] parametros = new Object[] {edoId, codigoMaestro};
			numAlum = enocJdbc.queryForObject(comando,String.class,parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoAlumnoRespDao|getAlumEvalProf|:"+ex);
		}
		return numAlum;
	}
	
	public String getAlumEvalProf( String cursoCargaId){
		int numAlum = 0;		
		try{
			String comando = "SELECT COALESCE(COUNT(DISTINCT(CODIGO_PERSONAL)),0) FROM ENOC.EDO_ALUMNORESP"
					+ " WHERE CURSO_CARGA_ID = ?";			
			numAlum = enocJdbc.queryForObject(comando,Integer.class,cursoCargaId);			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoAlumnoRespDao|getAlumEvalProf|:"+ex);
		}
		return String.valueOf(numAlum);
	}
	
	/**
	 * @author Elifo
	 * @return El numero de alumnos correspondientes al profesor dado que faltan por contestar la evaluacion dada
	 * @param conn Conexion a la base de datos
	 * @param edoId El edoId de la evaluacion a buscar
	 * @param codigoMaestro El codigo del profesor del que se desea el resultado
	 * */
	public String getAlumFaltantesProf( String edoId, String codigoMaestro) {
		String numAlum			= "0";
		
		try{
			String comando = ("SELECT COALESCE(COUNT(CODIGO_PERSONAL),0) AS NUM_ALUM FROM ENOC.KRDX_CURSO_ACT A" + 
						" WHERE A.CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO B" + 
						" WHERE B.CODIGO_PERSONAL = ?" +
						" AND B.CARGA_ID IN ("+edoDao.getCargasEdo(edoId)+"))" +
						" AND A.CODIGO_PERSONAL NOT IN (SELECT DISTINCT(CODIGO_PERSONAL) FROM ENOC.EDO_ALUMNORESP" + 
						" WHERE EDO_ID = ?" +
						" AND CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO" + 
						" WHERE CODIGO_PERSONAL = ?" +
						" AND CARGA_ID IN ("+edoDao.getCargasEdo(edoId)+")))");
			Object[] parametros = new Object[] {codigoMaestro, edoId, codigoMaestro};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){		
				numAlum = enocJdbc.queryForObject(comando,String.class,parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoAlumnoRespDao|getAlumFaltantesProf|:"+ex);
		}
		
		return numAlum;
	}
	
	
	public String getAlumFaltantesProf( String cursoCargaId) {
		String numAlum			= "0";		
		try{
			String comando = "SELECT COALESCE(COUNT(CODIGO_PERSONAL),0) FROM ENOC.KRDX_CURSO_ACT A"
					+ " WHERE A.CURSO_CARGA_ID = ?"
					+ " AND A.CODIGO_PERSONAL NOT IN (SELECT DISTINCT(CODIGO_PERSONAL) FROM ENOC.EDO_ALUMNORESP WHERE CURSO_CARGA_ID = ?)";			
			if (enocJdbc.queryForObject(comando,Integer.class,cursoCargaId, cursoCargaId) >= 1){		
				numAlum = enocJdbc.queryForObject(comando,String.class,cursoCargaId, cursoCargaId);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoAlumnoRespDao|getAlumFaltantesProf|:"+ex);
		}
		
		return numAlum;
	}
	
	
	/**
	 * @author Elifo
	 * @return El promedio de la pregunta dada del profesor dado
	 * @param conn Conexion a la base de datos
	 * @param edoId La evaluacion de la cual se desea el promedio de la pregunta dada
	 * @param preguntaId La pregunta de la cual se desea el promedio
	 * @param codigoMaestro El codigo del maestro del cual se desea el promedio de la pregunta dada
	 * */
	public String getPromedioPregunta( String edoId, String preguntaId, String codigoMaestro) {
		String promedio = "0";
		
		try{
			String comando = "SELECT TO_CHAR(COALESCE(((SUM(RESPUESTA)/COUNT(RESPUESTA))*20), 0), '990.99') AS PROMEDIO FROM ENOC.EDO_ALUMNORESP" + 
						" WHERE EDO_ID = TO_NUMBER(?, '99999')" +
						" AND PREGUNTA_ID = TO_NUMBER(?, '99999')" +
						" AND CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO" + 
						" WHERE CODIGO_PERSONAL = ?)";
			Object[] parametros = new Object[] {edoId, preguntaId, codigoMaestro};
			promedio = enocJdbc.queryForObject(comando,String.class,parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoAlumnoRespDao|getPromedioPregunta|:"+ex);
		}
		return promedio;
	}
	
	/**
	 * @author Elifo
	 * @return El valor minimo con el cual se califico la pregunta dada de la evaluacion dada del maestro dado
	 * @param conn Conexion a la base de datos
	 * @param edoId La evaluacion de la que se desea el minimo de la pregunta dada
	 * @param preguntaId La pregunta de la cual se desea el minimo
	 * @param codigoMaestro El codigo del maestro del cual se desea el minimo
	 * */
	public String getMinPregunta( String edoId, String preguntaId, String codigoMaestro) {		
		String minimo = "";
		
		try{
			String comando = "SELECT COALESCE(MIN(RESPUESTA), '--') AS MINIMO FROM ENOC.EDO_ALUMNORESP" + 
						" WHERE EDO_ID = TO_NUMBER(?, '99')" +
						" AND PREGUNTA_ID = TO_NUMBER(?, '99')" +
						" AND CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO" + 
						" WHERE CODIGO_PERSONAL = ?)";
			Object[] parametros = new Object[] {edoId, preguntaId, codigoMaestro};
			minimo = enocJdbc.queryForObject(comando,String.class,parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoAlumnoRespDao|getMinPregunta|:"+ex);
		}
		return minimo;
	}
	
	/**
	 * @author Elifo
	 * @return El valor maximo con el cual se califico la pregunta dada de la evaluacion dada del maestro dado
	 * @param conn Conexion a la base de datos
	 * @param edoId La evaluacion de la que se desea el minimo de la pregunta dada
	 * @param preguntaId La pregunta de la cual se desea el minimo
	 * @param codigoMaestro El codigo del maestro del cual se desea el minimo
	 * */
	public String getMaxPregunta( String edoId, String preguntaId, String codigoMaestro) {		
		String maximo = "";
		
		try{
			String comando = "SELECT COALESCE(MAX(RESPUESTA), '--') AS MAXIMO FROM ENOC.EDO_ALUMNORESP" + 
						" WHERE EDO_ID = TO_NUMBER(?, '99')" +
						" AND PREGUNTA_ID = TO_NUMBER(?, '99')" +
						" AND CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO" + 
						" WHERE CODIGO_PERSONAL = ?)";
			Object[] parametros = new Object[] {edoId, preguntaId, codigoMaestro};
			maximo = enocJdbc.queryForObject(comando,String.class,parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoAlumnoRespDao|getMaxPregunta|:"+ex);
		}
		return maximo;
	}
	
	/**
	 * @author Elifo
	 * @return El valor minimo con el cual se califico la pregunta dada de la evaluacion dada del maestro dado
	 * @param conn Conexion a la base de datos
	 * @param edoId La evaluacion de la que se desea el minimo de la pregunta dada
	 * @param preguntaId La pregunta de la cual se desea el minimo
	 * @param codigoMaestro El codigo del maestro del cual se desea el minimo
	 * */
	public String getMinPreguntaMateria( String edoId, String preguntaId, String cursoCargaId) {		
		String minimo = "";
		
		try{
			String comando = "SELECT COALESCE(MIN(RESPUESTA), '--') AS MINIMO FROM ENOC.EDO_ALUMNORESP" + 
						" WHERE EDO_ID = TO_NUMBER(?, '99')" +
						" AND PREGUNTA_ID = TO_NUMBER(?, '99')" +
						" AND CURSO_CARGA_ID = ?";
			Object[] parametros = new Object[] {edoId, preguntaId, cursoCargaId};
			minimo = enocJdbc.queryForObject(comando,String.class,parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoAlumnoRespDao|getMinPreguntaMateria|:"+ex);
		}
		return minimo;
	}
	
	/**
	 * @author Elifo
	 * @return El valor maximo con el cual se califico la pregunta dada de la evaluacion dada del maestro dado
	 * @param conn Conexion a la base de datos
	 * @param edoId La evaluacion de la que se desea el minimo de la pregunta dada
	 * @param preguntaId La pregunta de la cual se desea el minimo
	 * @param codigoMaestro El codigo del maestro del cual se desea el minimo
	 * */
	public String getMaxPreguntaMateria( String edoId, String preguntaId, String cursoCargaId) {		
		String maximo = "";
		
		try{
			String comando = "SELECT COALESCE(MAX(RESPUESTA), '--') AS MAXIMO FROM ENOC.EDO_ALUMNORESP" + 
						" WHERE EDO_ID = TO_NUMBER(?, '99')" +
						" AND PREGUNTA_ID = TO_NUMBER(?, '99')" +
						" AND CURSO_CARGA_ID = ?";
			Object[] parametros = new Object[] {edoId, preguntaId, cursoCargaId};
			maximo = enocJdbc.queryForObject(comando,String.class,parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoAlumnoRespDao|getMaxPreguntaMateria|:"+ex);
		}
		return maximo;
	}
	
	/**
	 * @author Elifo
	 * @return El numero de alumnos que han contestado la evaluacion dada
	 * @param conn Conexion a la base de datos
	 * @param edoId El edoId en el cual se buscaran los alumnos
	 * */
	public String getAlumEvalEdo( String edoId) {
		String numAlum = "";
		
		try{
			String comando = "SELECT COUNT(DISTINCT(CODIGO_PERSONAL)) AS NUM_ALUM FROM ENOC.EDO_ALUMNORESP" + 
						" WHERE EDO_ID = ?";
			Object[] parametros = new Object[] {edoId};
			numAlum = enocJdbc.queryForObject(comando,String.class,parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoAlumnoRespDao|getAlumEvalEdo|:"+ex);
		}
		return numAlum;
	}
	
	/**
	 * @author Elifo
	 * @return El numero de encuestas/materia contestadas
	 * @param conn Conexion a la base de datos
	 * @param edoId El edoId para el cual se desea el resultado
	 * */
	public String getNumEvalEdo( String edoId) {
		String numEval = "";
		
		try{
			String comando = "SELECT COUNT(DISTINCT(CODIGO_PERSONAL||CURSO_CARGA_ID)) AS NUM_EVAL FROM ENOC.EDO_ALUMNORESP" +
						" WHERE EDO_ID = TO_NUMBER(?, '99')";
			Object[] parametros = new Object[] {edoId};
			numEval = enocJdbc.queryForObject(comando,String.class,parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoAlumnoRespDao|getNumEvalEdo|:"+ex);
		}
		return numEval;
	}
	
	/**
	 * @author Elifo
	 * @return El promedio de la pregunta dada de la evaluacion dada
	 * @param conn Conexion a la base de datos
	 * @param edoId La evaluacion de la cual se desea el promedio de la pregunta dada
	 * @param preguntaId La pregunta de la cual se desea el promedio
	 * */
	public String getPromedioPregunta( String edoId, String preguntaId) {		
		String promedio = "";
		
		try{
			String comando = "SELECT TO_CHAR(COALESCE(((SUM(RESPUESTA)/COUNT(RESPUESTA))*20), 0), '990.99') AS PROMEDIO FROM ENOC.EDO_ALUMNORESP" + 
						" WHERE EDO_ID = TO_NUMBER(?, '99')" +
						" AND PREGUNTA_ID = TO_NUMBER(?, '99')"+
						" AND RESPUESTA > 0";
			Object[] parametros = new Object[] {edoId, preguntaId};
			promedio = enocJdbc.queryForObject(comando,String.class,parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoAlumnoRespDao|getPromedioPregunta|:"+ex);
		}
		return promedio;
	}
	
	/**
	 * @author Elifo
	 * @return El numero de respuestas con un valor dado
	 * @param conn Conexion a la base de datos
	 * @param edoId El edoId para el cual se desea el resultado
	 * @param preguntaId El numero de pregunta par la cual se desea el resultado
	 * @param repuestaVal El valor de la respuesta que se busca
	 * */
	public String getNumRespuestasPreg( String edoId, String preguntaId, String repuestaVal) {
		String numResp = "";
		
		try{
			String comando = "SELECT COUNT(*) AS NUM_RESPUESTAS FROM ENOC.EDO_ALUMNORESP" + 
						" WHERE EDO_ID = TO_NUMBER(?, '99')" +
						" AND PREGUNTA_ID = TO_NUMBER(?, '99')" +
						" AND RESPUESTA = ?";
			Object[] parametros = new Object[] {edoId, preguntaId, repuestaVal};
			numResp = enocJdbc.queryForObject(comando,String.class,parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoAlumnoRespDao|getNumRespuestasPreg|:"+ex);
		}
		return numResp;
	}
	
	/**
	 * @author Elifo
	 * @return La cantidad de cursos que lleva el alumno en las cargas habiles para la evaluacion dada
	 * @param conn Conexion a la base de datos
	 * @param edoId La evaluacion en la que se desea buscar
	 * @param codigoPersonal El codigo del alumno del que se desean saber el numero de cursos
	 * */
	public String getNumCursos( String edoId, String codigoPersonal) {
		String numMat			= "0";	
		
		try{
			String comando = "SELECT COUNT(*) AS NUM_MAT FROM ENOC.KRDX_CURSO_ACT" + 
					" WHERE CODIGO_PERSONAL = ?" +
					" AND SUBSTR(CURSO_CARGA_ID, 1, 6) IN ("+edoDao.getCargasEdo(edoId)+")";
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>= 1){
				numMat = enocJdbc.queryForObject(comando,String.class,parametros);
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoAlumnoRespDao|getNumCursos|:"+ex);
		}
		
		return numMat;
	}
	
	/**
	 * @author Elifo
	 * @return La cantidad de cursos que ha evaluado(contestado) el alumno en la evaluacion dada
	 * @param conn Conexion a la base de datos
	 * @param edoId La evaluacion en la que se desea buscar
	 * @param codigoPersonal El codigo del alumno del que se desean saber el numero de cursos evaluados
	 * */
	public String getNumCursosEvaluados( String edoId, String codigoPersonal) {
		String numMat = "";
		
		try{
			String comando = "SELECT COUNT(DISTINCT(CURSO_CARGA_ID)) AS NUM_MAT FROM ENOC.EDO_ALUMNORESP" + 
					" WHERE CODIGO_PERSONAL = ?" +
					" AND EDO_ID = TO_NUMBER(?, '99')";
			Object[] parametros = new Object[] {edoId, codigoPersonal};
			numMat = enocJdbc.queryForObject(comando,String.class,parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoAlumnoRespDao|getNumCursosEvaluados|:"+ex);
		}
		return numMat;
	}
	
	public String getPromedioPreguntaPorMateria( String edoId, String preguntaId, String CursoCargaId) {
		String promedio	= "0";
		
		try{
			String comando = "SELECT TO_CHAR( COALESCE(SUM(RESPUESTA*20),0) / COALESCE(COUNT(RESPUESTA),1), '999.99') AS PROMEDIO FROM ENOC.EDO_ALUMNORESP"
					+ " WHERE EDO_ID = TO_NUMBER(?,'99999')"
					+ " AND PREGUNTA_ID = TO_NUMBER(?, '99999')"
					+ " AND CURSO_CARGA_ID = ?"
					+ " AND RESPUESTA IN('0','1','2','3','4','5')";
			Object[] parametros = new Object[] {edoId, preguntaId, CursoCargaId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				promedio = enocJdbc.queryForObject(comando, String.class, parametros);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoAlumnoRespDao|getPromedioPreguntaPorMateria|:"+ex);
		}
		return promedio;
	}
	
	/**
	 * @author Etorres
	 * @return El numero de alumnos que han evaluado la materia
	 * @param conn Conexion a la base de datos
	 * @param cursoCargaId El codigo de la materia para el cual se buscaron los alumnos
	 * */
	public String getAlumEvalMateria( String cursoCargaId) {
		String numAlum = "";
		
		try{
			String comando = "SELECT COALESCE(COUNT(DISTINCT(CODIGO_PERSONAL)),0) AS NUM_ALUM FROM ENOC.EDO_ALUMNORESP" + 
						  " WHERE CURSO_CARGA_ID = ?";
			Object[] parametros = new Object[] {cursoCargaId};
			numAlum = enocJdbc.queryForObject(comando,String.class,parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoAlumnoRespDao|getAlumEvalMateria|:"+ex);
		}
		return numAlum;
	}	
	
	/**
	 * @author Etorres
	 * @return Regresa el número de EdoId con que se evaluo la materia 
	 * @param conn Conexion a la base de datos
	 * @param cursoCargaId El codigo de la materia
	 * */
	public String getEdoId( String cursoCargaId) {
		
		String edoId = "0";		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.EDO_ALUMNORESP WHERE CURSO_CARGA_ID = ?";			
			if (enocJdbc.queryForObject(comando, Integer.class, cursoCargaId) >= 1) {
				comando = "SELECT DISTINCT(EDO_ID) FROM ENOC.EDO_ALUMNORESP WHERE CURSO_CARGA_ID = ?";
				edoId = enocJdbc.queryForObject(comando, String.class, cursoCargaId);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoAlumnoRespDao|getEdoId|:"+ex);
		}
		return edoId;
	}
	
	public List<EdoAlumnoResp> getListRespuestas( String cursoCargaId, String preguntaId, String orden ) {
		
		List<EdoAlumnoResp> lista = new ArrayList<EdoAlumnoResp>();		
		try{
			String comando = "SELECT EDO_ID, PREGUNTA_ID, CODIGO_PERSONAL, CURSO_CARGA_ID, CODIGO_MAESTRO, RESPUESTA " +
					"FROM ENOC.EDO_ALUMNORESP WHERE CURSO_CARGA_ID = ? AND PREGUNTA_ID = ? "+orden;
			lista = enocJdbc.query(comando, new EdoAlumnoRespMapper(), cursoCargaId, preguntaId);			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoAlumnoRespDao|getListRespuestas|:"+ex);
		}
		return lista;
	}
	
	public List<EdoAlumnoResp> lisRespuestas( String cursoCargaId, String orden ){
		
		List<EdoAlumnoResp> lista = new ArrayList<EdoAlumnoResp>();		
		try{
			String comando = " SELECT EDO_ID, PREGUNTA_ID, CODIGO_PERSONAL, CURSO_CARGA_ID, CODIGO_MAESTRO, RESPUESTA"
							+" FROM ENOC.EDO_ALUMNORESP WHERE CURSO_CARGA_ID = ? "+orden;
			Object[] parametros = new Object[] {cursoCargaId};
			lista = enocJdbc.query(comando, new EdoAlumnoRespMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoAlumnoRespDao|lisRespuestas|:"+ex);
		}
		return lista;
	}
	
	public TreeMap<String,String> getMapCursos( String evaluacionId, String cargas ) {
		
		TreeMap<String,String> treeMaestro	= new TreeMap<String,String>();
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();		
		String comando						= "";
				
		try{
			comando = "SELECT"+
			" DISTINCT(CA.CODIGO_PERSONAL) AS LLAVE,"+			
			" ("+
			"   SELECT COUNT(CURSO_CARGA_ID) FROM ENOC.CARGA_GRUPO B"+ 
			"   WHERE B.CODIGO_PERSONAL = CA.CODIGO_PERSONAL"+
			"   AND B.CARGA_ID IN ("+cargas+")"+
			" ) AS VALOR"+					  		
			" FROM ENOC.CARGA_GRUPO CA"+ 
			" WHERE CA.CARGA_ID IN ("+cargas+")";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista){
				treeMaestro.put( map.getLlave(), (String)map.getValor());			
			}		
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoAlumnoRespDao|getMapCursos|:"+ex);
		}
		
		return treeMaestro;
	}
	
	public List<String> getCursos( String cargas, String evaluacionId, String orden ) {
		
		List<String> lista = new ArrayList<String>();
		
		try{
			String comando = "SELECT"+
			" CODIGO_PERSONAL, CURSO_CARGA_ID "+											  		
			" FROM ENOC.CARGA_GRUPO "+ 
			" WHERE CARGA_ID IN ("+cargas+") AND CODIGO_PERSONAL IN (SELECT CODIGO_MAESTRO FROM ENOC.EDO_ALUMNORESP WHERE EDO_ID = "+evaluacionId+") "+orden;
			lista = enocJdbc.queryForList(comando, String.class);
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoAlumnoRespDao|getCursos|:"+ex);
		}
		return lista;
	}
	
	public TreeMap<String,String> getMapAlumnos( String evaluacionId, String cargas ) {
		
		TreeMap<String,String> treeMaestro	= new TreeMap<String,String>();
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();
		String comando					= "";		
		
		try{
			comando = "SELECT"+
			" DISTINCT(CA.CODIGO_PERSONAL) AS LLAVE,"+			
			" ("+
			"   SELECT COALESCE(COUNT(CODIGO_PERSONAL),0) AS NUM_ALUM FROM ENOC.KRDX_CURSO_ACT A"+ 
			"   WHERE CURSO_CARGA_ID IN "+
			"   (SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO B WHERE B.CODIGO_PERSONAL = CA.CODIGO_PERSONAL"+ 
			"		AND B.CARGA_ID IN ("+cargas+") )"+
			" ) AS VALOR"+								  		
			" FROM ENOC.CARGA_GRUPO CA"+ 
			" WHERE CA.CARGA_ID IN ("+cargas+")";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista){
				treeMaestro.put( map.getLlave(), (String)map.getValor());	
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoAlumnoRespDao|getMapAlumnos|:"+ex);
		}
		
		return treeMaestro;
	}
	
	public TreeMap<String,String> getMapAlumnosMateria( String evaluacionId, String cargas ) {
		
		TreeMap<String,String> treeMaestro	= new TreeMap<String,String>();
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();
		String comando					= "";		
		
		try{
			comando = "SELECT"+
			" DISTINCT(CA.CURSO_CARGA_ID) AS LLAVE,"+			
			" ("+
			"   SELECT COALESCE(COUNT(CODIGO_PERSONAL),0) AS NUM_ALUM FROM ENOC.KRDX_CURSO_ACT A"+ 
			"   WHERE CURSO_CARGA_ID = CA.CURSO_CARGA_ID "+
			" ) AS VALOR"+								  		
			" FROM ENOC.CARGA_GRUPO CA"+ 
			" WHERE CA.CARGA_ID IN ("+cargas+")";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista){
				treeMaestro.put( map.getLlave(), (String)map.getValor());	
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoAlumnoRespDao|getMapAlumnos|:"+ex);
		}
		
		return treeMaestro;
	}
	
	public TreeMap<String,String> getMapEvaluados( String evaluacionId, String cargas ) {
		
		TreeMap<String,String> treeMaestro	= new TreeMap<String,String>();		
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();		
		String comando					= "";		
		
		try{
			comando = "SELECT"+
			" DISTINCT(CA.CODIGO_PERSONAL) AS LLAVE,"+			
			" ("+
			"   SELECT COUNT(DISTINCT(CODIGO_PERSONAL||CURSO_CARGA_ID)) FROM ENOC.EDO_ALUMNORESP"+ 
			"   WHERE EDO_ID = TO_NUMBER(?,'99999') " +
			"	AND CODIGO_MAESTRO = CA.CODIGO_PERSONAL "+
			" ) AS VALOR "+											  		
			" FROM ENOC.CARGA_GRUPO CA"+ 
			" WHERE CA.CARGA_ID IN ("+cargas+")";	
			lista = enocJdbc.query(comando, new aca.MapaMapper(), evaluacionId);
			for (aca.Mapa map : lista){
				treeMaestro.put( map.getLlave(), (String)map.getValor());	
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoAlumnoRespDao|getMapEvaluados|:"+ex);
		}
		
		return treeMaestro;
	}	
	
	public TreeMap<String,String> getMapEvaluadosMaterial( String evaluacionId, String cargas ) {
		
		TreeMap<String,String> treeMaestro	= new TreeMap<String,String>();
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();
		String comando					= "";		
		
		try{
			comando = "SELECT"+
			" DISTINCT(CA.CURSO_CARGA_ID) AS LLAVE,"+			
			" ("+
			"   SELECT COUNT(DISTINCT(CODIGO_PERSONAL||CURSO_CARGA_ID)) FROM ENOC.EDO_ALUMNORESP"+ 
			"   WHERE EDO_ID = TO_NUMBER(?,'99999') " +
			"	AND CURSO_CARGA_ID = CA.CURSO_CARGA_ID "+
			" ) AS VALOR "+											  		
			" FROM ENOC.CARGA_GRUPO CA"+ 
			" WHERE CA.CARGA_ID IN ("+cargas+")";	
			lista = enocJdbc.query(comando, new aca.MapaMapper(), evaluacionId);
			for (aca.Mapa map : lista){
				treeMaestro.put( map.getLlave(), (String)map.getValor());	
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoAlumnoRespDao|getMapEvaluados|:"+ex);
		}
		
		return treeMaestro;
	}
	
	public TreeMap<String,String> getMapPromedioMateria( String evaluacionId, String cargas ) {
		
		TreeMap<String,String> treeMaestro	= new TreeMap<String,String>();
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();
		String comando					= "";		
		
		try{
			comando = "SELECT"+
			" CA.CURSO_CARGA_ID AS LLAVE, "+			
			" ( "+
			"   SELECT TO_CHAR(COALESCE((SUM(RESPUESTA)/COUNT(RESPUESTA)*20), 0), '990.99') AS PROMEDIO FROM ENOC.EDO_ALUMNORESP A"+ 
			"   WHERE A.EDO_ID = TO_NUMBER(?, '99999')"+
			"   AND A.CURSO_CARGA_ID = CA.CURSO_CARGA_ID"+
			"   AND A.PREGUNTA_ID IN (SELECT B.PREGUNTA_ID FROM ENOC.EDO_ALUMNOPREG B"+ 
			"   WHERE B.EDO_ID = A.EDO_ID"+
			"   AND B.TIPO = 'O')"+
			" ) AS VALOR "+							  		
			" FROM ENOC.CARGA_GRUPO CA"+ 
			" WHERE CA.CARGA_ID IN ("+cargas+")";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), evaluacionId);
			for (aca.Mapa map : lista){
				treeMaestro.put( map.getLlave(), (String)map.getValor());	
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoAlumnoRespDao|getMapPromedio|:"+ex);
		}
		
		return treeMaestro;
	}
	
	public TreeMap<String,String> getMapPromedio( String evaluacionId, String cargas ) {
		
		TreeMap<String,String> treeMaestro	= new TreeMap<String,String>();
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();
		String comando					= "";		
		
		try{
			comando = "SELECT"
					+ " CURSO_CARGA_MAESTRO(CURSO_CARGA_ID) AS LLAVE, TO_CHAR(COALESCE(SUM(RESPUESTA)/COALESCE(COUNT(RESPUESTA),0) * 20,0),'990.99') AS VALOR"
					+ " FROM ENOC.EDO_ALUMNORESP"
					+ " WHERE EDO_ID = "+evaluacionId+""
					+ " AND CURSO_CARGA_ID = ANY(SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO WHERE CARGA_ID IN ("+cargas+"))"
					+ " AND PREGUNTA_ID = ANY(SELECT PREGUNTA_ID FROM ENOC.EDO_ALUMNOPREG WHERE EDO_ID = "+evaluacionId+" AND TIPO = 'O')"
					+ " GROUP BY CURSO_CARGA_MAESTRO(CURSO_CARGA_ID)";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista){
				treeMaestro.put( map.getLlave(), (String)map.getValor());	
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoAlumnoRespDao|getMapPromedio|:"+ex);
		}
		
		return treeMaestro;
	}
	
	public HashMap<String,String> getPromedio( String cargas) {
			
			HashMap<String, String> mapa  = new HashMap<String, String>();
			List<aca.Mapa> 			lista = new ArrayList<aca.Mapa>();
			
		try{
			String comando = " SELECT CODIGO_MAESTRO||PREGUNTA_ID AS LLAVE, TRIM(TO_CHAR( SUM(COALESCE(TO_NUMBER(RESPUESTA),0))/COUNT(PREGUNTA_ID) ,'99.99')) AS VALOR "+
					  " FROM ENOC.EDO_ALUMNORESP WHERE CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO WHERE CARGA_ID IN ("+cargas+")) "+
					  " AND PREGUNTA_ID IN (SELECT PREGUNTA_ID FROM ENOC.EDO_ALUMNOPREG WHERE EDO_ID = ENOC.EDO_ALUMNORESP.EDO_ID AND TIPO='O') "+
					  " GROUP BY CODIGO_MAESTRO, PREGUNTA_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoAlumnoRespDao|getPromedio|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,String> mapaContestadas(String codigoPersonal) {
		
		HashMap<String, String> mapa  = new HashMap<String, String>();
		List<aca.Mapa> 			lista = new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CURSO_CARGA_ID AS LLAVE, COUNT(*) AS VALOR FROM ENOC.EDO_ALUMNORESP WHERE CODIGO_PERSONAL = ? GROUP BY CURSO_CARGA_ID";
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoAlumnoRespDao|mapaContestadas|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapaPreguntasContestadas(String edoId) {
		
		HashMap<String, String> mapa  = new HashMap<String, String>();
		List<aca.Mapa> 			lista = new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT PREGUNTA_ID AS LLAVE, COUNT(*) AS VALOR FROM ENOC.EDO_ALUMNORESP WHERE EDO_ID = TO_NUMBER(?,'99999') GROUP BY PREGUNTA_ID";
			Object[] parametros = new Object[] {edoId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoAlumnoRespDao|mapaPreguntasContestadas|:"+ex);
		}
		
		return mapa;
	}
	
	
	public HashMap<String,String> mapaEdoDeMateria(String cargaId) {
		
		HashMap<String, String> mapa  = new HashMap<String, String>();
		List<aca.Mapa> 			lista = new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT DISTINCT(CURSO_CARGA_ID) AS LLAVE, EDO_ID AS VALOR FROM EDO_ALUMNORESP WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ?";
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoAlumnoRespDao|mapaEdoDeMateria|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapaPromedioPreguntas(String edoId, String codigoPersonal){
		
		HashMap<String, String> mapa  = new HashMap<String, String>();
		List<aca.Mapa> 			lista = new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT PREGUNTA_ID AS LLAVE, TO_CHAR(COALESCE(((SUM(RESPUESTA)/COUNT(RESPUESTA))*20), 0), '990.99') AS VALOR FROM ENOC.EDO_ALUMNORESP"
					+ " WHERE EDO_ID = TO_NUMBER(?,'99999')"
					+ " AND CODIGO_MAESTRO = ?"					
					+ " AND RESPUESTA IN ('1','2','3','4','5')"
					+ " GROUP BY PREGUNTA_ID";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), edoId, codigoPersonal);
			for (aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoAlumnoRespDao|mapaPromedioPreguntas|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapaMinimoPreguntas(String edoId, String codigoPersonal){		
		HashMap<String, String> mapa  = new HashMap<String, String>();
		List<aca.Mapa> 			lista = new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT PREGUNTA_ID AS LLAVE, COALESCE(MIN(RESPUESTA), '--') AS VALOR FROM ENOC.EDO_ALUMNORESP"
					+ " WHERE EDO_ID = TO_NUMBER(?,'99999')"
					+ " AND CODIGO_MAESTRO = ?"
					+ " AND RESPUESTA IN ('1','2','3','4','5')"				
					+ " GROUP BY PREGUNTA_ID";	
			lista = enocJdbc.query(comando, new aca.MapaMapper(), edoId, codigoPersonal);
			for (aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoAlumnoRespDao|mapaMinimoPreguntas|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String,String> mapaMaximoPreguntas(String edoId, String codigoPersonal){		
		HashMap<String, String> mapa  = new HashMap<String, String>();
		List<aca.Mapa> 			lista = new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT PREGUNTA_ID AS LLAVE, COALESCE(MAX(RESPUESTA), '--') AS VALOR FROM ENOC.EDO_ALUMNORESP"
					+ " WHERE EDO_ID = TO_NUMBER(?,'99999')"
					+ " AND CODIGO_MAESTRO = ?"
					+ " AND RESPUESTA IN ('1','2','3','4','5')"
					+ " GROUP BY PREGUNTA_ID";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), edoId, codigoPersonal);
			for (aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoAlumnoRespDao|mapaMaximoPreguntas|:"+ex);
		}		
		return mapa;
	}
	
	public List<EdoAlumnoResp> listaPreguntasCursoId(String cursoCargaId){		
		List<EdoAlumnoResp> lista 	= new ArrayList<EdoAlumnoResp>();		
		try{
			String comando = "SELECT EDO_ID, PREGUNTA_ID, CODIGO_PERSONAL, CURSO_CARGA_ID, CODIGO_MAESTRO, RESPUESTA"
					+ " FROM ENOC.EDO_ALUMNORESP WHERE CURSO_CARGA_ID = ?";			
			lista = enocJdbc.query(comando, new EdoAlumnoRespMapper(), cursoCargaId);
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoAlumnoRespDao|listaPreguntasCursoId|:"+ex);
		}		
		return lista;
	}
	
}