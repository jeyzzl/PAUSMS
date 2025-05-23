package aca.edo.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class EdoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( Edo edo) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.EDO(EDO_ID, NOMBRE, F_INICIO, F_FINAL, PERIODO_ID, TIPO, MODALIDAD, ENCABEZADO, TIPO_ENCUESTA, CARGAS, EXCEPTO)" +
				" VALUES(TO_NUMBER(?, '99999'), ?, TO_DATE(?, 'DD/MM/YYYY'), TO_DATE(?, 'DD/MM/YYYY'), ?, ?, ?, ?, ?, ?, ?)";
			Object[] parametros = new Object[] {edo.getEdoId(), edo.getNombre(), edo.getFInicio(),
			edo.getFFinal(), edo.getPeriodoId(), edo.getTipo(), edo.getModalidad(),
			edo.getEncabezado(), edo.getTipoEncuesta(), edo.getCargas(), edo.getExcepto()};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg( Edo edo) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.EDO" + 
				" SET NOMBRE = ?," +
				" F_INICIO = TO_DATE(?, 'DD/MM/YYYY')," +
				" F_FINAL = TO_DATE(?, 'DD/MM/YYYY')," +
				" PERIODO_ID = ?," +
				" TIPO = ?," +
				" MODALIDAD = ?, TIPO_ENCUESTA = ?,"+
				" ENCABEZADO = ?, CARGAS = ?, EXCEPTO = ? " +
				" WHERE EDO_ID = TO_NUMBER(?, '99999')";
			Object[] parametros = new Object[] {edo.getNombre(), edo.getFInicio(), edo.getFFinal(),
			edo.getPeriodoId(), edo.getTipo(), edo.getModalidad(), edo.getTipoEncuesta(), 
			edo.getEncabezado(), edo.getCargas(), edo.getExcepto(), edo.getEdoId()};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoDao|updateReg|:"+ex);		 
		}
		return ok;
	}	
	
	public boolean deleteReg( String edoId) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.EDO"+ 
				" WHERE EDO_ID = TO_NUMBER(?, '99999')";
			Object[] parametros = new Object[] {edoId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public Edo mapeaRegId( String edoId) {
		
		Edo objeto = new Edo();
		
		try{
			String comando = "SELECT EDO_ID, NOMBRE,"
					+ " TO_CHAR(F_INICIO, 'DD/MM/YYYY') AS F_INICIO,"
					+ " TO_CHAR(F_FINAL, 'DD/MM/YYYY') AS F_FINAL,"
					+ " PERIODO_ID, TIPO, MODALIDAD, ENCABEZADO, TIPO_ENCUESTA, CARGAS, EXCEPTO"
					+ " FROM ENOC.EDO WHERE EDO_ID = TO_NUMBER(?, '99999')";
			Object[] parametros = new Object[] {edoId};
			
			objeto = enocJdbc.queryForObject(comando, new EdoMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoDao|mapeaRegId|:"+ex);
		}
		return objeto;
	}
	
	public boolean existeReg( String edoId) {
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.EDO" + 
					" WHERE EDO_ID = TO_NUMBER(?, '99999')";
			Object[] parametros = new Object[] {edoId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public String maximoReg() {
		String maximo = "1";
		
		try{
			String comando = "SELECT COALESCE(MAX(EDO_ID)+1,1) AS MAXIMO FROM ENOC.EDO";
 			if (enocJdbc.queryForObject(comando, Integer.class) >= 1) {
 				maximo = enocJdbc.queryForObject(comando, String.class);
			}
 			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoDao|maximoReg|:"+ex);
		}
		return maximo;
	}
	
	public boolean edoActivo( String tipo) {
		boolean ok = false;
		
		try{
			String comando = "SELECT EDO_ID, NOMBRE," +
					" TO_CHAR(F_INICIO, 'DD/MM/YYYY') AS F_INICIO," +
					" TO_CHAR(F_FINAL, 'DD/MM/YYYY') AS F_FINAL," +
					" PERIODO_ID, TIPO, MODALIDAD, ENCABEZADO, TIPO_ENCUESTA, CARGAS, EXCEPTO " +
					" FROM ENOC.EDO" + 
					" WHERE TIPO = ?" +
					" AND now() BETWEEN F_INICIO AND F_FINAL";
			Object[] parametros = new Object[] {tipo};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoDao|edoActivo|:"+ex);
		}
		return ok;
	}
	
	public String getEdoActual( String tipo, String modalidad) {
		String edoId = "0";
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.EDO WHERE TIPO = ? AND MODALIDAD LIKE '%-'||?||'-%' AND now() BETWEEN F_INICIO AND F_FINAL";
			Object[] parametros = new Object[] {tipo, modalidad};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				comando = "SELECT EDO_ID FROM ENOC.EDO WHERE TIPO = ? AND MODALIDAD LIKE '%-'||?||'-%' AND now() BETWEEN F_INICIO AND F_FINAL";
				edoId = enocJdbc.queryForObject(comando, String.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoDao|getEdoActual|:"+ex);
		}
		return edoId;
	}
	
	public String getEdoId( String tipo) {		
		String edo = "";
		
		try{
			String comando = "SELECT EDO_ID AS EDO, NOMBRE," +
					" TO_CHAR(F_INICIO, 'DD/MM/YYYY') AS F_INICIO," +
					" TO_CHAR(F_FINAL, 'DD/MM/YYYY') AS F_FINAL," +
					" PERIODO_ID, TIPO, MODALIDAD, ENCABEZADO, TIPO_ENCUESTA, CARGAS, EXCEPTO" +
					" FROM ENOC.EDO" + 
					" WHERE TIPO = ?";
			Object[] parametros = new Object[] {tipo};
			edo = enocJdbc.queryForObject(comando, String.class, parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoDao|getEdoId|:"+ex);
		}
		return edo;
	}
	
	public String getEdoNombre( String edoId) {
		String nombre = "";
		
		try{
			String comando = "SELECT NOMBRE FROM ENOC.EDO WHERE EDO_ID = TO_NUMBER(?,'99999')";
			Object[] parametros = new Object[] {edoId};
			nombre = enocJdbc.queryForObject(comando, String.class, parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoDao|getEdoNombre|:"+ex);
		}
		return nombre;
	}
	
	/**
	 * @author etorres
	 * @return Las cargas que su rango de fechas concuerda con la fecha en que se aplico la evaluacion
	 * @param conn Conexion a la base de datos
	 * @param edoId La evaluacion sobre la cual se hara la busqueda
	 * */
	public String getCargasEdo( String edoId) {
		String cargas = "";
		
		try{
			String comando = "SELECT CARGA_ID FROM ENOC.CARGA" + 
					" WHERE (SELECT F_INICIO FROM ENOC.EDO WHERE EDO_ID = TO_NUMBER(?, '99')) BETWEEN F_INICIO AND F_FINAL";
			Object[] parametros = new Object[] {edoId};
			cargas = enocJdbc.queryForObject(comando, String.class, parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoDao|getCargasEdo|:"+ex);
		}
		return cargas;
	}
	
	public String getCargasEvaluadas( String edoId) {
		String cargas = "";
		
		try{
			String comando = "SELECT DISTINCT(CARGA_ID) FROM ENOC.CARGA_GRUPO" + 
					" WHERE CURSO_CARGA_ID IN (SELECT DISTINCT(CURSO_CARGA_ID) FROM ENOC.EDO_ALUMNORESP WHERE EDO_ID = ?)";
			Object[] parametros = new Object[] {edoId};
			cargas= enocJdbc.queryForObject(comando, String.class, parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoDao|getCargasEdo|:"+ex);
		}
		return cargas;
	}
	
	//SELECT DISTINCT(CARGA_ID) FROM ENOC.CARGA_GRUPO WHERE CURSO_CARGA_ID IN (SELECT DISTINCT(CURSO_CARGA_ID) FROM ENOC.EDO_ALUMNORESP WHERE EDO_ID = 11);
	
	/**
	 * @author etorres
	 * @return La cantidad de cursos que estaba impartiendo un profesor cuando se activo la evaluacion
	 * @param conn Conexion a la base de datos
	 * @param edoId La evaluacion en la que se basa la busqueda
	 * @param codigoMaestro El codigo del profesor del que se desea saber la cantidad de cursos impartidos
	 * */
	public String getCursosProf( String edoId, String codigoMaestro) {
		String numCursos = "";
		
		try{
			String comando = "SELECT COUNT(CURSO_CARGA_ID) AS NUM_CURSOS FROM ENOC.CARGA_GRUPO" + 
						" WHERE CODIGO_PERSONAL = ?" +
						" AND CARGA_ID IN ("+getCargasEdo(edoId)+")";
			Object[] parametros = new Object[] {edoId, codigoMaestro};
			numCursos = enocJdbc.queryForObject(comando, String.class, parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoDao|getCursosProf|:"+ex);
		}
		return numCursos;
	}
	
	public List<Edo> getListTipo( String tipo, String orden ) {
		
		List<Edo> lista = new ArrayList<Edo>();	
		try{
			String comando = "SELECT EDO_ID, NOMBRE, TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO, TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL,"
				+ " PERIODO_ID, TIPO, MODALIDAD, ENCABEZADO, TIPO_ENCUESTA, CARGAS, EXCEPTO"
				+ " FROM ENOC.EDO"
				+ " WHERE TIPO = ? "+orden;
			Object[] parametros = new Object[] {tipo};
			lista = enocJdbc.query(comando, new EdoMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoDao|getListTipo|:"+ex);
		}
		return lista;
	}
	
	public List<Edo> getTipo( String orden ) {
		
		List<Edo> lista = new ArrayList<Edo>();
		
		try{
			String comando = "SELECT EDO_ID, NOMBRE," +		
					" TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO,"+
					" TO_CHAR(F_FINAL,'DD/MM/YYYY') F_FINAL,"+
					" PERIODO_ID, TIPO, MODALIDAD, ENCABEZADO, TIPO_ENCUESTA, CARGAS, EXCEPTO "+
					" FROM ENOC.EDO" + 
					" WHERE TIPO != 'E' "+orden;
			lista = enocJdbc.query(comando, new EdoMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoDao|getTipo|:"+ex);
		}
		return lista;
	}
	
	public List<Edo> getListPeriodo( String periodoId, String orden ){		
		List<Edo> lista = new ArrayList<Edo>();		
		try{
			String comando = "SELECT EDO_ID, NOMBRE," +		
					" TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO,"+
					" TO_CHAR(F_FINAL,'DD/MM/YYYY') F_FINAL,"+
					" PERIODO_ID, TIPO, MODALIDAD, ENCABEZADO, TIPO_ENCUESTA, CARGAS, EXCEPTO "+
					" FROM ENOC.EDO" + 
					" WHERE PERIODO_ID = ? "+orden;
			lista = enocJdbc.query(comando, new EdoMapper(), periodoId);			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoDao|getListPeriodo|:"+ex);
		}
		return lista;
	}
	
	public List<Edo> lisEdoActual( String tipo, String orden) {
		
		List<Edo> lista = new ArrayList<Edo>();		
		try{
			String comando = "SELECT EDO_ID, NOMBRE,"
					+ " TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO,"
					+ " TO_CHAR(F_FINAL,'DD/MM/YYYY') F_FINAL,"
					+ " PERIODO_ID, TIPO, MODALIDAD, ENCABEZADO, TIPO_ENCUESTA, CARGAS, EXCEPTO"
					+ " FROM ENOC.EDO"
					+ " WHERE TIPO = ? AND NOW() BETWEEN F_INICIO AND F_FINAL "+orden;
			Object[] parametros = new Object[] {tipo};
			lista = enocJdbc.query(comando, new EdoMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoDao|lisEdoActual|:"+ex);
		}
		return lista;
	}
	
	public HashMap<String,String> mapEdoActual(String tipo) {		
		HashMap<String,String> mapa	= new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT MODALIDAD AS LLAVE, EDO_ID AS VALOR FROM ENOC.EDO WHERE TIPO = ? AND now() BETWEEN F_INICIO AND F_FINAL";		
			Object[] parametros = new Object[] {tipo};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);			
			for (aca.Mapa map: lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoDao|mapEdoActual|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,Edo> mapAllEdo(String orden) {		
		HashMap<String, Edo> mapaEdo = new HashMap<String, Edo>();
		List<Edo> lista	= new ArrayList<Edo>();	
		
		try{
			String comando = "SELECT EDO_ID, NOMBRE,  TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO, TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL,"
					+ " PERIODO_ID, TIPO, MODALIDAD, ENCABEZADO, TIPO_ENCUESTA, CARGAS, EXCEPTO"
					+ " FROM ENOC.EDO "+orden; 
		
			lista = enocJdbc.query(comando,new EdoMapper());
			for(Edo edo : lista){				
				mapaEdo.put(edo.getEdoId(), edo);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoDao|mapEdoActual|:"+ex);
		}
		return mapaEdo;
	}
	
	public HashMap<String,String> mapaEvaluacionesPorPeriodo() {		
		HashMap<String,String> mapa	= new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT PERIODO_ID AS LLAVE, COUNT(EDO_ID) AS VALOR FROM ENOC.EDO GROUP BY PERIODO_ID";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());			
			for (aca.Mapa map: lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoDao|mapaEvaluacionesPorPeriodo|:"+ex);
		}
		return mapa;
	}
}