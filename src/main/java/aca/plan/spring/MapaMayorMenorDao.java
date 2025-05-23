// Clase para la tabla de Mapa_Archivo
package aca.plan.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class MapaMayorMenorDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( MapaMayorMenor mapaMayorMenor ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.MAPA_MAYOR_MENOR (FOLIO, PLAN_ID, TIPO, POR_DEFECTO, NOMBRE)"
					+ " VALUES( TO_NUMBER(?,'99'), ?, ?, ?, ? )";

			Object[] parametros = new Object[] { mapaMayorMenor.getFolio(), mapaMayorMenor.getPlanId(), mapaMayorMenor.getTipo(), mapaMayorMenor.getPorDefecto(), mapaMayorMenor.getNombre() };
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaMayorMenorDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg( MapaMayorMenor mapaMayorMenor ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.MAPA_MAYOR_MENOR SET TIPO = ?, POR_DEFECTO = ?, NOMBRE = ? WHERE PLAN_ID = ? AND FOLIO = TO_NUMBER(?,'99')";
			
			Object[] parametros = new Object[] { mapaMayorMenor.getTipo(), mapaMayorMenor.getPorDefecto(), mapaMayorMenor.getNombre(), mapaMayorMenor.getPlanId(),mapaMayorMenor.getFolio()};				
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaMayorMenorDao|updateReg|:"+ex);
		}
		return ok;
	}
	
	public boolean deleteReg( String planId, String folio ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.MAPA_MAYOR_MENOR WHERE PLAN_ID = ? AND FOLIO = TO_NUMBER(?,'99')";
			
			Object[] parametros = new Object[] {planId,folio};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaMayorMenorDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public MapaMayorMenor mapeaRegId(  String planId, String folio) {
		MapaMayorMenor mapaMayorMenor = new MapaMayorMenor();
		try{
			String comando = "SELECT FOLIO, PLAN_ID, TIPO, POR_DEFECTO, NOMBRE FROM ENOC.MAPA_MAYOR_MENOR WHERE PLAN_ID = ? AND FOLIO =  TO_NUMBER(?,'99')";
			
				Object[] parametros = new Object[] {planId,folio};
				mapaMayorMenor = enocJdbc.queryForObject(comando, new MapaMayorMenorMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaMayorMenorDao|mapeaRegId|:"+ex);
		}
		return mapaMayorMenor;		
	}	
	
	public boolean existeReg( String planId, String folio) {
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(PLAN_ID) FROM ENOC.MAPA_MAYOR_MENOR WHERE PLAN_ID = ? AND FOLIO= TO_NUMBER(?,'99')"; 
			
			Object[] parametros = new Object[] {planId,folio};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaMayorMenorDao|existeReg|:"+ex);
		}
		return ok;
	}

	public String maximoReg() {
		
		String maximo			= "1";
		
		try{
			String comando = "SELECT MAX(FOLIO)+1 AS MAXIMO FROM ENOC.MAPA_MAYOR_MENOR";
			// Object[] parametros = new Object[] {};
			if (enocJdbc.queryForObject(comando, Integer.class) >= 1){
				maximo = enocJdbc.queryForObject(comando, String.class);
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaMayorMenorDao|maximoReg|:"+ex);
		}
		
		return maximo;
	}
	
	public List<MapaMayorMenor> getLista( String planId, String orden ) {
		List<MapaMayorMenor> lista	= new ArrayList<MapaMayorMenor>();
		String comando	= "";
		
		try{
			comando = " SELECT FOLIO, PLAN_ID, TIPO, POR_DEFECTO, NOMBRE FROM ENOC.MAPA_MAYOR_MENOR"
					+ " WHERE PLAN_ID = ? " +orden;			
			lista = enocJdbc.query(comando, new MapaMayorMenorMapper(),planId);
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaMayorMenorDao|getLista|:"+ex);
		}		
		return lista;
	}

	public List<MapaMayorMenor> getListaMayores( String planId, String orden ) {
		List<MapaMayorMenor> lista	= new ArrayList<MapaMayorMenor>();
		String comando	= "";
		
		try{
			comando = " SELECT FOLIO, PLAN_ID, TIPO, POR_DEFECTO, NOMBRE FROM ENOC.MAPA_MAYOR_MENOR"
					+ " WHERE PLAN_ID = ? AND TIPO = 'MA' " +orden;			
			lista = enocJdbc.query(comando, new MapaMayorMenorMapper(),planId);
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaMayorMenorDao|getListaMayores|:"+ex);
		}		
		return lista;
	}

	public List<MapaMayorMenor> getListaMenores( String planId, String orden ) {
		List<MapaMayorMenor> lista	= new ArrayList<MapaMayorMenor>();
		String comando	= "";
		
		try{
			comando = " SELECT FOLIO, PLAN_ID, TIPO, POR_DEFECTO, NOMBRE FROM ENOC.MAPA_MAYOR_MENOR"
					+ " WHERE PLAN_ID = ? AND TIPO = 'ME' " +orden;			
			lista = enocJdbc.query(comando, new MapaMayorMenorMapper(),planId);
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaMayorMenorDao|getListaMayores|:"+ex);
		}		
		return lista;
	}

	public MapaMayorMenor getMayorPorDefecto( String planId ) {
		MapaMayorMenor mapaMayorMenor = new MapaMayorMenor();
		try{
			String comando = "SELECT FOLIO, PLAN_ID, TIPO, POR_DEFECTO, NOMBRE FROM ENOC.MAPA_MAYOR_MENOR WHERE PLAN_ID = ? AND TIPO = 'MA' AND POR_DEFECTO = 'S'";
			
				Object[] parametros = new Object[] {planId};
				mapaMayorMenor = enocJdbc.queryForObject(comando, new MapaMayorMenorMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaMayorMenorDao|getMayorPorDefecto|:"+ex);
		}
		return mapaMayorMenor;		
	}

	public MapaMayorMenor getMenorPorDefecto( String planId ) {
		MapaMayorMenor mapaMayorMenor = new MapaMayorMenor();
		try{
			String comando = "SELECT FOLIO, PLAN_ID, TIPO, POR_DEFECTO, NOMBRE FROM ENOC.MAPA_MAYOR_MENOR WHERE PLAN_ID = ? AND TIPO = 'ME' AND POR_DEFECTO = 'S'";
			
				Object[] parametros = new Object[] {planId};
				mapaMayorMenor = enocJdbc.queryForObject(comando, new MapaMayorMenorMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaMayorMenorDao|getMayorPorDefecto|:"+ex);
		}
		return mapaMayorMenor;		
	}

	public HashMap<String, MapaMayorMenor> mapMayores( String orden ) {
		List<MapaMayorMenor> lista			= new ArrayList<MapaMayorMenor>();
		HashMap<String,MapaMayorMenor> mapa	= new HashMap<String,MapaMayorMenor>();	
		
		try{
			String comando = "SELECT FOLIO, PLAN_ID, TIPO, POR_DEFECTO, NOMBRE"
					+ " FROM ENOC.MAPA_MAYOR_MENOR"
					+ " WHERE TIPO = 'MA'";
			lista = enocJdbc.query(comando, new MapaMayorMenorMapper());
			
			for (MapaMayorMenor mayor : lista){
				mapa.put(mayor.getFolio(), mayor);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaMayorMenorDao|mapMayores|:"+ex);
		}
		return mapa;
	}

	
	public HashMap<String, MapaMayorMenor> mapMenores( String orden ) {
		List<MapaMayorMenor> lista			= new ArrayList<MapaMayorMenor>();
		HashMap<String,MapaMayorMenor> mapa	= new HashMap<String,MapaMayorMenor>();	
		
		try{
			String comando = "SELECT FOLIO, PLAN_ID, TIPO, POR_DEFECTO, NOMBRE"
					+ " FROM ENOC.MAPA_MAYOR_MENOR"
					+ " WHERE TIPO = 'ME'";
			lista = enocJdbc.query(comando, new MapaMayorMenorMapper());
			
			for (MapaMayorMenor menor : lista){
				mapa.put(menor.getFolio(), menor);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaMayorMenorDao|mapMenores|:"+ex);
		}
		return mapa;
	}

	public HashMap<String,String> mapaMayoresActivos(String orden ) {
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa		= new HashMap<String,String>();
		try{
			String comando = "SELECT MAYOR AS LLAVE, COUNT(*) AS VALOR FROM ALUM_PLAN WHERE MAYOR IS NOT NULL GROUP BY MAYOR "+orden;
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map:lista){			
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error -  aca.plan.spring.MapaMayorMenorDao|mapaMayoresActivos|:"+ex);
		}
		return mapa;
	}

	public HashMap<String,String> mapaMenoresActivos(String orden) {
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa		= new HashMap<String,String>();
		try{
			String comando = "SELECT MENOR AS LLAVE, COUNT(*) AS VALOR FROM ALUM_PLAN WHERE MENOR IS NOT NULL GROUP BY MENOR "+orden;
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map:lista){			
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error -  aca.plan.spring.MapaMayorMenorDao|mapaMenoresActivos|:"+ex);
		}
		return mapa;
	}

	public HashMap<String, MapaMayorMenor> mapMayoresPorPlan( String planId, String orden ) {
		List<MapaMayorMenor> lista			= new ArrayList<MapaMayorMenor>();
		HashMap<String,MapaMayorMenor> mapa	= new HashMap<String,MapaMayorMenor>();	
		
		try{
			String comando = " SELECT FOLIO, PLAN_ID, TIPO, POR_DEFECTO, NOMBRE"
					+ " FROM ENOC.MAPA_MAYOR_MENOR"
					+ " WHERE TIPO = 'MA' AND PLAN_ID = ?";
			lista = enocJdbc.query(comando, new MapaMayorMenorMapper(), planId);
			
			for (MapaMayorMenor mayor : lista){
				mapa.put(mayor.getFolio(), mayor);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaMayorMenorDao|mapMayoresPorPlan|:"+ex);
		}
		return mapa;
	}

	public HashMap<String, MapaMayorMenor> mapMenoresPorPlan( String planId, String orden ) {
		List<MapaMayorMenor> lista			= new ArrayList<MapaMayorMenor>();
		HashMap<String,MapaMayorMenor> mapa	= new HashMap<String,MapaMayorMenor>();	
		
		try{
			String comando = " SELECT FOLIO, PLAN_ID, TIPO, POR_DEFECTO, NOMBRE"
					+ " FROM ENOC.MAPA_MAYOR_MENOR"
					+ " WHERE TIPO = 'ME' AND PLAN_ID = ?";
			lista = enocJdbc.query(comando, new MapaMayorMenorMapper(), planId);
			
			for (MapaMayorMenor menor : lista){
				mapa.put(menor.getFolio(), menor);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaMayorMenorDao|mapMenoresPorPlan|:"+ex);
		}
		return mapa;
	}

	public String getMenor(String codigoPersonal){
		String nombre		= "";	
		
		try{
			String comando = "SELECT COUNT(*) FROM MAPA_MAYOR_MENOR WHERE FOLIO IN (SELECT MENOR FROM ALUM_PLAN WHERE CODIGO_PERSONAL = ?)";
			Object[] parametros = new Object[] {codigoPersonal};
			if(enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				comando = "SELECT NOMBRE FROM MAPA_MAYOR_MENOR WHERE FOLIO IN (SELECT MENOR FROM ALUM_PLAN WHERE CODIGO_PERSONAL = ?)";
				nombre = enocJdbc.queryForObject(comando, String.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaMayorMenorDao|getMenor|:"+ex);
		}		
		return nombre;
	}

	public String getMayor(String codigoPersonal){
		String nombre		= "";	
		
		try{
			String comando = "SELECT COUNT(*) FROM MAPA_MAYOR_MENOR WHERE FOLIO IN (SELECT MAYOR FROM ALUM_PLAN WHERE CODIGO_PERSONAL = ?)";
			Object[] parametros = new Object[] {codigoPersonal};
			if(enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				comando = "SELECT NOMBRE FROM MAPA_MAYOR_MENOR WHERE FOLIO IN (SELECT MAYOR FROM ALUM_PLAN WHERE CODIGO_PERSONAL = ?)";
				nombre = enocJdbc.queryForObject(comando, String.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaMayorMenorDao|getMayor|:"+ex);
		}		
		return nombre;
	}
	
}