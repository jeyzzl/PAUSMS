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
public class AlumnoEficienciaDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public List<AlumnoEficiencia> getListCurso( String cursoCargaId, String orden ){		
		List<AlumnoEficiencia> lista	= new ArrayList<AlumnoEficiencia>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CURSO_CARGA_ID, EVALUACION_ID, TIPO, VALOR, NOTA, EVALUADAS, PUNTOS, TOT_ACTIVIDADES"
					+ " FROM ENOC.ALUMNO_EFICIENCIA"
					+ " WHERE CURSO_CARGA_ID = ? " + orden;
			lista = enocJdbc.query(comando, new AlumnoEficienciaMapper(),cursoCargaId);			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoEficienciaDao|getListCurso|:"+ex);
		}		
		return lista;
	}
	
	public HashMap<String,AlumnoEficiencia> mapaMateria( String cursoCargaId ) {
		List<AlumnoEficiencia> lista			= new ArrayList<AlumnoEficiencia>();
		HashMap<String,AlumnoEficiencia> mapa	= new HashMap<String, AlumnoEficiencia>();
		try{
			String comando = " SELECT CODIGO_PERSONAL, CURSO_CARGA_ID, EVALUACION_ID, TIPO, VALOR, NOTA, EVALUADAS, PUNTOS, TOT_ACTIVIDADES"
					+ " FROM ENOC.ALUMNO_EFICIENCIA"
					+ " WHERE CURSO_CARGA_ID = ?";
			Object[] parametros = new Object[] {cursoCargaId};
			lista = enocJdbc.query(comando, new AlumnoEficienciaMapper(), parametros);
			for (AlumnoEficiencia eficiencia : lista){			
				mapa.put(eficiencia.getCodigoPersonal()+eficiencia.getEvaluacionId(), eficiencia);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoEficienciaDao|mapaEficienciaMateria|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String,String> mapaAvanceEvaluacion( String codigoPersonal ) {
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa		= new HashMap<String, String>();
		try{
			String comando = "SELECT CURSO_CARGA_ID AS LLAVE, SUM((EVALUADAS*VALOR)/100) AS VALOR FROM ENOC.ALUMNO_EFICIENCIA WHERE CODIGO_PERSONAL = ? GROUP BY CURSO_CARGA_ID";
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map:lista){			
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoEficienciaDao|mapaAvanceEvaluacion|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String,String> mapaPuntosEvaluados( String codigoPersonal ) {
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa		= new HashMap<String, String>();
		try{
			String comando = " SELECT CURSO_CARGA_ID AS LLAVE, SUM(VALOR*EVALUADAS/100) AS VALOR FROM ALUMNO_EFICIENCIA WHERE CODIGO_PERSONAL = ? GROUP BY CURSO_CARGA_ID";
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map:lista){			
				mapa.put(map.getLlave(), (String)map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoEficienciaDao|mapaPuntosEvaluados|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String,String> mapaPuntosEvaluados( String cargaId, String carreraId ) {
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa		= new HashMap<String, String>();
		try{
			String comando = " SELECT CODIGO_PERSONAL||CURSO_CARGA_ID AS LLAVE, SUM(VALOR*EVALUADAS/100) AS VALOR FROM ALUMNO_EFICIENCIA "
					+ " WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_ESTADO WHERE CARGA_ID = ? AND CARRERA_ID = ?) "
					+ " GROUP BY CODIGO_PERSONAL, CURSO_CARGA_ID";
			Object[] parametros = new Object[] {cargaId, carreraId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map:lista){			
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoEficienciaDao|mapaPuntosEvaluados|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String,String> mapaPuntosGanados( String codigoPersonal ) {
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa		= new HashMap<String, String>();
		try{
			String comando = " SELECT CURSO_CARGA_ID AS LLAVE,  SUM(CASE TOT_ACTIVIDADES WHEN 0 THEN PUNTOS ELSE (VALOR*EVALUADAS/100)*PUNTOS/EVALUADAS END) AS VALOR FROM ENOC.ALUMNO_EFICIENCIA WHERE CODIGO_PERSONAL = ? GROUP BY CURSO_CARGA_ID";
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map:lista){			
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoEficienciaDao|mapaPuntosGanados|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String, String> mapPuntosEvaluadosMateria(String cursoCargaId){
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();
		HashMap<String, String> mapa = new HashMap<String, String>();
		try{
			String comando = " SELECT CODIGO_PERSONAL AS LLAVE, COALESCE(SUM(VALOR*(EVALUADAS/100)),0) AS VALOR"
					+ " FROM ENOC.ALUMNO_EFICIENCIA"
					+ " WHERE CURSO_CARGA_ID = ? GROUP BY CODIGO_PERSONAL";	
			Object[] parametros = new Object[] {cursoCargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map:lista){			
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoEficienciaDao|mapPuntosEvaluados|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapAlumnoNotas(String codigoPersonal){
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();
		HashMap<String, String> mapa = new HashMap<String, String>();
		try{
			String comando = "SELECT CURSO_CARGA_ID AS LLAVE, ROUND(SUM(VALOR*NOTA/100)/SUM(VALOR*EVALUADAS/100)*100,2) AS VALOR"
					+ " FROM ENOC.ALUMNO_EFICIENCIA WHERE CODIGO_PERSONAL = ? GROUP BY CURSO_CARGA_ID";
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map:lista){			
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoEficienciaDao|mapPuntosEvaluados|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String, String> mapaPuntosAlumno(String cargaId, String carreraId){
		
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();
		HashMap<String, String> mapa = new HashMap<String, String>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL||CURSO_CARGA_ID AS LLAVE, COALESCE(SUM(VALOR*(NOTA/100)),0) AS VALOR"
					+ " FROM ENOC.ALUMNO_EFICIENCIA "
					+ " WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_ESTADO WHERE CARGA_ID = ? AND CARRERA_ID = ?)"
					+ " GROUP BY CURSO_CARGA_ID, CODIGO_PERSONAL";
			Object[] parametros = new Object[] {cargaId, carreraId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map:lista){			
				mapa.put(map.getLlave(), (String)map.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.AlumnoEficienciaDao|mapPuntosAlumno|:"+ex);
		}
		return mapa;
	}
	
}