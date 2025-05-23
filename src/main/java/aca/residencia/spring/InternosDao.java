//Beans para la tabla INTERNOS
package aca.residencia.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class InternosDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public List<String> getListEdadInterno( String dormitorio, String orden ) {
		
		List<String> lista	= new ArrayList<String>();
		try{
			String comando = "SELECT DISTINCT(ENOC.ALUM_EDAD(CODIGO_PERSONAL)) AS EDAD FROM ENOC.INSCRITOS"+
			" WHERE RESIDENCIA_ID = 'I' " +
			" AND DORMITORIO IN("+dormitorio+") "+ orden;
			lista = enocJdbc.queryForList(comando, String.class);
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.spring.InternosDao|getListEdadInterno|:"+ex);
		}
		
		return lista;
	}
	
	public List<String> getListEdadInternoModalidad( String modalidades, String dormitorio, String fechaIni, String fechaFin, String orden ) {
		
		List<String> lista	= new ArrayList<String>();
		try{
			String comando = "SELECT DISTINCT(ENOC.ALUM_EDAD(CODIGO_PERSONAL)) AS EDAD FROM ENOC.INSCRITOS"+
			" WHERE RESIDENCIA_ID = 'I' AND MODALIDAD_ID IN ("+modalidades+") " +
			" AND DORMITORIO IN("+dormitorio+") AND F_INSCRIPCION BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE (?,'DD/MM/YYYY') "+ orden;	
			
			Object[] parametros = new Object[] {
				fechaIni,fechaFin
			};
			
			lista = enocJdbc.queryForList(comando, String.class,parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.spring.InternosDao|getListEdadInternoModalidad|:"+ex);
		}	

		return lista;
	}
	
	public List<String> getListNacionInterno( String dormitorio, String orden) {
		
		List<String> lista	= new ArrayList<String>();

		try{
			String comando = "SELECT DISTINCT(NACIONALIDAD) AS NACION FROM ENOC.INSCRITOS"+
			" WHERE RESIDENCIA_ID = 'I' " +
			" AND DORMITORIO IN("+dormitorio+") "+orden;
			lista = enocJdbc.queryForList(comando, String.class);
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.spring.InternosDao|getListNacionInterno|:"+ex);
		}			

		return lista;
	}
	
	public List<String> getListNacionInternoModalidad( String modalidades, String dormitorio, String fechaIni, String fechaFin, String orden) {
		
		List<String> lista	= new ArrayList<String>();
		

		try{
			String comando = " SELECT DISTINCT(NACIONALIDAD) AS NACION FROM ENOC.INSCRITOS"
					+ " WHERE RESIDENCIA_ID = 'I' AND MODALIDAD_ID IN ("+modalidades+") AND DORMITORIO IN("+dormitorio+")"
					+ " AND F_INSCRIPCION BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE (?,'DD/MM/YYYY') "+orden;
		
			Object[] parametros = new Object[] {
				fechaIni,fechaFin
			};
			
			lista = enocJdbc.queryForList(comando, String.class,parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.spring.InternosDao|getListNacionInternoModalidad|:"+ex);
		}			

		return lista;
	}
	
	public List<String> getListReligionInterno( String dormitorio, String orden) {
		
		List<String> lista	= new ArrayList<String>();
		
		try{
			String comando = "SELECT DISTINCT(RELIGION_ID) AS RELIGION FROM ENOC.INSCRITOS"+
			" WHERE RESIDENCIA_ID = 'I' " +
			" AND DORMITORIO IN("+dormitorio+") "+orden;

			lista = enocJdbc.queryForList(comando, String.class);

		}catch(Exception ex){
			System.out.println("Error - aca.residencia.spring.InternosDao|getListReligionInterno|:"+ex);
		}	

		return lista;
	}
	
	public List<String> getListReligionInternoModalidad( String modalidades, String dormitorio, String fechaIni, String fechaFin, String orden) {
		
		List<String> lista	= new ArrayList<String>();
		
		try{
			String comando = "SELECT DISTINCT(RELIGION_ID) AS RELIGION FROM ENOC.INSCRITOS"+
			" WHERE RESIDENCIA_ID = 'I' AND MODALIDAD_ID IN ("+modalidades+") " +
			" AND DORMITORIO IN("+dormitorio+") AND F_INSCRIPCION BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE (?,'DD/MM/YYYY') "+orden;

			Object[] parametros = new Object[] {
				fechaIni,fechaFin
			};
			
			lista = enocJdbc.queryForList(comando, String.class,parametros);

		}catch(Exception ex){
			System.out.println("Error - aca.residencia.spring.InternosDao|getListReligionInternoModalidad|:"+ex);
		}
		return lista;
	}	
	
	public List<String> getListTipoAlumnoInterno( String dormitorio, String orden) {
		
		List<String> lista	= new ArrayList<String>();
		

		try{
			String comando = "SELECT DISTINCT(TIPOALUMNO_ID) AS TIPO FROM ENOC.INSCRITOS"+
			" WHERE RESIDENCIA_ID = 'I' " +
			" AND DORMITORIO IN("+dormitorio+") "+orden;
			lista = enocJdbc.queryForList(comando, String.class);


		}catch(Exception ex){
			System.out.println("Error - aca.residencia.spring.InternosDao|getListTipoAlumnoInterno|:"+ex);
		}		

		return lista;
	}
	
	public List<String> getListTipoAlumnoInternoModalidad( String modalidades, String dormitorio, String fechaIni, String fechaFin, String orden) {
		
		List<String> lista	= new ArrayList<String>();
		

		try{
			String comando = "SELECT DISTINCT(TIPOALUMNO_ID) AS TIPO FROM ENOC.INSCRITOS"+
			" WHERE RESIDENCIA_ID = 'I' AND MODALIDAD_ID IN ("+modalidades+") " +
			" AND DORMITORIO IN("+dormitorio+") AND F_INSCRIPCION BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE (?,'DD/MM/YYYY') "+orden;

			Object[] parametros = new Object[] {
				fechaIni,fechaFin
			};
			
			lista = enocJdbc.queryForList(comando, String.class,parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.spring.InternosDao|getListTipoAlumnoInternoModalidad|:"+ex);
		}		

		return lista;
	}
	
	
	public HashMap<String, String> mapaEdadInterno( String dormitorio) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT ENOC.ALUM_EDAD(CODIGO_PERSONAL) AS LLAVE, COUNT(CODIGO_PERSONAL) AS VALOR FROM ENOC.INSCRITOS"+
				" WHERE RESIDENCIA_ID = 'I'"+
				" AND DORMITORIO IN ("+dormitorio+")"+
				" GROUP BY ENOC.ALUM_EDAD(CODIGO_PERSONAL)";		
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.spring.InternosDao|mapaEdadInternos|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaEdadInternoModalidad( String modalidades, String dormitorio) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT ENOC.ALUM_EDAD(CODIGO_PERSONAL) AS LLAVE, COUNT(CODIGO_PERSONAL) AS VALOR FROM ENOC.INSCRITOS"+
				" WHERE RESIDENCIA_ID = 'I'"+
				" AND DORMITORIO IN ("+dormitorio+") AND MODALIDAD_ID IN ("+modalidades+")"+
				" GROUP BY ENOC.ALUM_EDAD(CODIGO_PERSONAL)";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista){
				mapa.put(map.getLlave(), map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.spring.InternosDao|mapaEdadInternos|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaNacionInterno( String dormitorio) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT NACIONALIDAD, COUNT(CODIGO_PERSONAL) AS TOTAL FROM ENOC.INSCRITOS"+
				" WHERE RESIDENCIA_ID = 'I'"+
				" AND DORMITORIO IN ("+dormitorio+")"+
				" GROUP BY NACIONALIDAD";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista){
				mapa.put(map.getLlave(), map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.spring.InternosDao|mapaNacionInterno|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaNacionInternoModalidad( String modalidades, String dormitorio) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT NACIONALIDAD AS LLAVE, COUNT(CODIGO_PERSONAL) AS VALOR FROM ENOC.INSCRITOS"+
				" WHERE RESIDENCIA_ID = 'I'"+
				" AND DORMITORIO IN ("+dormitorio+") AND MODALIDAD_ID IN ("+modalidades+") "+
				" GROUP BY NACIONALIDAD";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista){
				mapa.put(map.getLlave(), map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.spring.InternosDao|mapaNacionInterno|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaReligionInterno( String dormitorio) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT RELIGION_ID, COUNT(CODIGO_PERSONAL) AS TOTAL FROM ENOC.INSCRITOS"+
				" WHERE RESIDENCIA_ID = 'I'"+
				" AND DORMITORIO IN ("+dormitorio+")"+
				" GROUP BY RELIGION_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista){
				mapa.put(map.getLlave(), map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.spring.InternosDao|mapaReligionInterno|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaReligionInternoModalidad( String modalidades, String dormitorio) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT RELIGION_ID AS LLAVE, COUNT(CODIGO_PERSONAL) AS VALOR FROM ENOC.INSCRITOS"+
				" WHERE RESIDENCIA_ID = 'I'"+
				" AND DORMITORIO IN ("+dormitorio+") AND MODALIDAD_ID IN ("+modalidades+")"+
				" GROUP BY RELIGION_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista){
				mapa.put(map.getLlave(), map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.spring.InternosDao|mapaReligionInterno|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaTipoAlumnoInterno( String dormitorio) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT TIPOALUMNO_ID, COUNT(CODIGO_PERSONAL) AS TOTAL FROM ENOC.INSCRITOS"+
				" WHERE RESIDENCIA_ID = 'I'"+
				" AND DORMITORIO IN ("+dormitorio+")"+
				" GROUP BY TIPOALUMNO_ID";
					
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista){
				mapa.put(map.getLlave(), map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.spring.InternosDao|mapaTipoAlumnoInterno|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaTipoAlumnoInternoModalidad( String modalidades, String dormitorio) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT TIPOALUMNO_ID AS LLAVE, COUNT(CODIGO_PERSONAL) AS VALOR FROM ENOC.INSCRITOS"+
				" WHERE RESIDENCIA_ID = 'I'"+
				" AND DORMITORIO IN ("+dormitorio+") AND MODALIDAD_ID IN ("+modalidades+") "+
				" GROUP BY TIPOALUMNO_ID";
					
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista){
				mapa.put(map.getLlave(), map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.spring.InternosDao|mapaTipoAlumnoInterno|:"+ex);
		}
		return mapa;
	}
	
	public String getMexicanosUM( String dormitorio) {		
		int total		= 0;
		
		try{
			String comando = "SELECT COUNT(NACIONALIDAD) FROM ENOC.INSCRITOS " +
					"WHERE RESIDENCIA_ID = 'I' " +
					" AND NACIONALIDAD= '91'" +
					" AND MODALIDAD_ID IN (1,2,3,4)" +
					" AND DORMITORIO = ?";	
			total = enocJdbc.queryForObject(comando,Integer.class,dormitorio);
		}catch(Exception ex){
			System.out.println("Error - aca.vista.Inscritos|getMexicanosUM|:"+ex);
		}
		
		return String.valueOf(total);
	}
	
	public String getASDUM( String dormitorio) {
		String asd		= "x";
		
		try{
			String comando = "SELECT COUNT(RELIGION_ID) RELIGION FROM ENOC.INSCRITOS " +
					"WHERE RESIDENCIA_ID = 'I' " +
					" AND RELIGION_ID= '1'" +
					" AND MODALIDAD_ID IN (1,2,3,4)" +
					" AND DORMITORIO = ?"; 
			asd = enocJdbc.queryForObject(comando,String.class,dormitorio);
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.Inscritos|getASDUM|:"+ex);
		}
		
		return asd;
	}
	
	public String getTipoAlumUM( String tipo, String dormitorio) {
			
		
		String 	tipoAlum	= "1";
		
		try{
			String comando = "SELECT COUNT(I.CODIGO_PERSONAL)CODIGO " +
			"FROM ENOC.INSCRITOS I, ENOC.ALUM_ACADEMICO AC " + 
			"WHERE I.CODIGO_PERSONAL = AC.CODIGO_PERSONAL " +
			"AND I.RESIDENCIA_ID = 'I' AND I.DORMITORIO = ? " +
			"AND AC.TIPO_ALUMNO = ? " +
			" AND I.MODALIDAD_ID IN (1,2,3,4)"; 
		
			Object[] parametros = new Object[] {
				dormitorio,	tipo
			};
		
			tipoAlum = enocJdbc.queryForObject(comando,String.class,parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.res.InternosUtil|getTipoAlumUM|:"+ex);
		}
		
		return tipoAlum;
	}	

}