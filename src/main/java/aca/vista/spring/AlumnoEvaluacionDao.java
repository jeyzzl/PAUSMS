// Clase para la vista ALUMNO_EVALUACION
package  aca.vista.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Component
public class AlumnoEvaluacionDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public List<AlumnoEvaluacion> getListAll( String orden ){
			
		List<AlumnoEvaluacion> lista	= new ArrayList<AlumnoEvaluacion>();
		
		try{
			String comando = "SELECT "+
				"CURSO_CARGA_ID, "+
				"CODIGO_PERSONAL, "+
				"EVALUACION_ID, "+
				"NOMBRE_EVALUACION, "+
				"ESTRATEGIA_ID, "+
				"TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, "+
				"VALOR, "+
				"TIPO, "+
				"NOTA "+
				"FROM ENOC.ALUMNO_EVALUACION "+ orden;
			lista = enocJdbc.query(comando, new AlumnoEvaluacionMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoEvaluacionDao|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	public List<String> getActaMeses(String cursoCargaId){		 
		List<String> lista = new ArrayList<String>();		
		try{
			String comando = "SELECT DISTINCT TO_CHAR(FECHA,'MM') FROM ENOC.ALUMNO_EVALUACION WHERE CURSO_CARGA_ID = ? AND TIPO != 'E' ORDER BY 1";
			Object[] parametros = new Object[] {cursoCargaId};
			lista = enocJdbc.queryForList(comando, String.class, parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.AlumnoEvaluacionDao|getActaMeses|:"+ex);
		}
		
		return lista;
	}	
	
	public List<AlumnoEvaluacion> getListCurso( String cursoCargaId, String orden ){
		
		List<AlumnoEvaluacion> lista	= new ArrayList<AlumnoEvaluacion>();
		
		try{
			String comando = "SELECT "+
				"CURSO_CARGA_ID, "+
				"CODIGO_PERSONAL, "+
				"EVALUACION_ID, "+
				"NOMBRE_EVALUACION, "+
				"ESTRATEGIA_ID, "+
				"TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, "+
				"VALOR, "+
				"TIPO, "+
				"NOTA "+
				"FROM ENOC.ALUMNO_EVALUACION "+
				"WHERE CURSO_CARGA_ID = ? "+
				" "+ orden;
			lista = enocJdbc.query(comando, new AlumnoEvaluacionMapper(),cursoCargaId);
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoEvaluacionDao|getListCurso|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String, String> mapaSumaValor( String cursoCargaId, String tipo) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, SUM(VALOR) AS VALOR FROM ENOC.ALUMNO_EVALUACION WHERE CURSO_CARGA_ID = ? AND TIPO IN ("+tipo+") GROUP BY CODIGO_PERSONAL";
			Object[] parametros = new Object[] {cursoCargaId};			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.AlumnoEvaluacionDao|mapaSumaValor|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaPuntosAlumno( String codigoPersonal, String cargaId, String tipo) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL||CURSO_CARGA_ID AS LLAVE, COALESCE(ROUND(SUM(CASE TIPO WHEN 'P' THEN NOTA WHEN 'E' THEN NOTA ELSE (VALOR*NOTA/100) END),2),0) AS VALOR"
					+ " FROM ENOC.ALUMNO_EVALUACION"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND SUBSTR(CURSO_CARGA_ID,1,6) = ?"
					+ " AND TIPO IN ("+tipo+") "
					+ " GROUP BY CODIGO_PERSONAL||CURSO_CARGA_ID";
			Object[] parametros = new Object[] {codigoPersonal, cargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.AlumnoEvaluacionDao|mapaPuntosAlumno|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaPuntosAlumno( String cursoCargaId, String tipo) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, COALESCE(ROUND(SUM(CASE TIPO WHEN 'P' THEN NOTA WHEN 'E' THEN NOTA ELSE (VALOR*NOTA/100) END),2),0) AS VALOR"
					+ " FROM ENOC.ALUMNO_EVALUACION"
					+ " WHERE CURSO_CARGA_ID = ?"
					+ " AND TIPO IN ("+tipo+") "
					+ " GROUP BY CODIGO_PERSONAL";
			Object[] parametros = new Object[] {cursoCargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.AlumnoEvaluacionDao|mapaPuntosAlumno|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaPuntosEvaluaciones( String cursoCargaId, String tipo) {
		HashMap<String, String> mapa 	= new HashMap<String, String>();
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL||EVALUACION_ID AS LLAVE, COALESCE(ROUND(CASE TIPO WHEN 'P' THEN NOTA WHEN 'E' THEN NOTA ELSE (VALOR*NOTA/100) END, 2),0) AS VALOR"
					+ " FROM ENOC.ALUMNO_EVALUACION"
					+ " WHERE CURSO_CARGA_ID = ?"
					+ " AND TIPO IN ("+tipo+")";
			Object[] parametros = new Object[] {cursoCargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.AlumnoEvaluacionDao|mapaPuntosEvaluaciones|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaPromedioPorMes( String cursoCargaId) {
		HashMap<String, String> mapa 	= new HashMap<String, String>();
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL||TO_CHAR(FECHA,'MM') AS LLAVE, ((SUM(CASE TIPO WHEN 'P' THEN NOTA WHEN 'E' THEN NOTA ELSE (VALOR*NOTA/100) END )*100)/SUM(VALOR)) AS VALOR"
					+ " FROM ENOC.ALUMNO_EVALUACION"
					+ " WHERE CURSO_CARGA_ID = ?"
					+ " GROUP BY CODIGO_PERSONAL||TO_CHAR(FECHA,'MM')";
			Object[] parametros = new Object[] {cursoCargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.AlumnoEvaluacionDao|mapaPromedioPorMes|:"+ex);
		}
		return mapa;
	}

}