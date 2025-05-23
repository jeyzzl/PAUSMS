/**
 * 
 */
package aca.plan.spring;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class MapaNuevoPlanDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;	
	
	public MapaNuevoPlan mapeaRegId(  String planId, String versionId) {		
		MapaNuevoPlan mapaNuevoPlan = new MapaNuevoPlan();
		try{
			String comando = "SELECT PLAN_ID, CARRERA_ID, NOMBRE, VERSION_ID," +
								" VERSION_NOMBRE, ESTADO, TIPO, HTS, HPS, HFD, HEI, IDIOMA, HSS, HAS, YEAR"+
								" FROM ENOC.MAPA_NUEVO_PLAN" + 
								" WHERE PLAN_ID = ?" +
								" AND VERSION_ID = TO_NUMBER(?, '999')";
			Object[] parametros = new Object[] {planId, versionId};
			mapaNuevoPlan = enocJdbc.queryForObject(comando, new MapaNuevoPlanMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaNuevoPlanDao|mapeaRegId|:"+ex);
		}
		
		return mapaNuevoPlan;
	}
	
	public boolean existeReg( String planId, String versionId) {
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.MAPA_NUEVO_PLAN"+ 
				" WHERE PLAN_ID = ?" +
				" AND VERSION_ID = TO_NUMBER(?, '999')";
			Object[] parametros = new Object[] {planId, versionId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaNuevoPlanDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean deleteReg(String planId, String versionId){
		boolean ok = false;

		try{
			String comando = "DELETE FROM ENOC.MAPA_NUEVO_PLAN"+ 
				" WHERE PLAN_ID = ?" +
				" AND VERSION_ID = TO_NUMBER(?, '999')";
			Object[] parametros = new Object[] {planId, versionId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
	
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaNuevoPlanDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public String maximoReg( String planId) {
		String maximo		 	= "1";
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.MAPA_NUEVO_PLAN WHERE PLAN_ID = ?";
			Object[] parametros = new Object[] {planId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros)>= 1){
				comando = "SELECT MAX(VERSION_ID)+1 FROM ENOC.MAPA_NUEVO_PLAN WHERE PLAN_ID = ?";
				maximo = enocJdbc.queryForObject(comando, String.class, parametros);
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaNuevoPlanDao|maximoReg|:"+ex);
		}		
		return maximo;
	}
	
	public String getMaxVersionPlan( String planId ) {		
		String maximo = "0"; 
		try{
			String comando = "SELECT COALESCE(MAX(VERSION_ID),1) FROM ENOC.MAPA_NUEVO_PLAN WHERE PLAN_ID = ?"; 
			Object[] parametros = new Object[] {planId};
			maximo =  enocJdbc.queryForObject(comando, String.class, parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaNuevoPlanDao|getMaxVersionPlan|:"+ex);
		}		
		return maximo;
	}
	
	public String getHts( String planId ) {	
		String hts 				= "0";		
		try{
			String comando = "SELECT HTS FROM ENOC.MAPA_NUEVO_PLAN WHERE PLAN_ID = ?"; 
			Object[] parametros = new Object[] {planId};
			hts =  enocJdbc.queryForObject(comando, String.class, parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaNuevoPlanDao|getHts|:"+ex);
		}		
		return hts;
	}
	
	public String getHps( String planId ) {		
		String hps 				= "0";		
		try{
			String comando = "SELECT HPS FROM ENOC.MAPA_NUEVO_PLAN WHERE PLAN_ID = ?";
			Object[] parametros = new Object[] {planId};
			hps =  enocJdbc.queryForObject(comando, String.class, parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaNuevoPlanDao|getHps|:"+ex);
		}
		
		return hps;
	}

	public String getHfd( String planId ) {
		
		String hfd 				= "0";		
		try{
			String comando = "SELECT HFD FROM ENOC.MAPA_NUEVO_PLAN WHERE PLAN_ID = ?"; 
			Object[] parametros = new Object[] {planId};
			hfd =  enocJdbc.queryForObject(comando, String.class, parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaNuevoPlanDao|getHfd|:"+ex);
		}		
		return hfd;
	}
	
	public String getHei( String planId ) {	
		
		String hfd 				= "0";		
		try{
			String comando = "SELECT HEI FROM ENOC.MAPA_NUEVO_PLAN WHERE PLAN_ID = ?"; 
			Object[] parametros = new Object[] {planId};
			hfd =  enocJdbc.queryForObject(comando, String.class, parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaNuevoPlanDao|getHei|:"+ex);
		}
		
		return hfd;
	}
	
	public String getIdioma( String planId ) {
		
		String idioma 			= "E";		
		try{
			String comando = "SELECT IDIOMA FROM ENOC.MAPA_NUEVO_PLAN WHERE PLAN_ID = ?"; 
			Object[] parametros = new Object[] {planId};
			idioma =  enocJdbc.queryForObject(comando, String.class, parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaNuevoPlanDao|getIdioma|:"+ex);
		}
		
		return idioma;
	}
	
	public String getHss( String planId ) {
		
		
		String hss 				= "0";		
		try{
			String comando = "SELECT HSS FROM ENOC.MAPA_NUEVO_PLAN WHERE PLAN_ID = ?";
			Object[] parametros = new Object[] {planId};
			hss =  enocJdbc.queryForObject(comando, String.class, parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaNuevoPlanDao|getHss|:"+ex);
		}		
		return hss;
	}
	
	public String getHas( String planId ) {		
		String has 				= "0";		
		try{
			String comando = "SELECT HAS FROM ENOC.MAPA_NUEVO_PLAN WHERE PLAN_ID = ?"; 
			Object[] parametros = new Object[] {planId};
			has =  enocJdbc.queryForObject(comando, String.class, parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaNuevoPlanDao|getHas|:"+ex);
		}
		return has;
	}
	
	public List<MapaNuevoPlan> getListAll( String orden) {
		
		List<MapaNuevoPlan> lista		= new ArrayList<MapaNuevoPlan>();		
		try{
			String comando = "SELECT PLAN_ID, CARRERA_ID, NOMBRE, VERSION_ID," +
					  " VERSION_NOMBRE, ESTADO, TIPO, HTS, HPS, HFD, HEI, IDIOMA, HSS, HAS, YEAR" +
					  " FROM ENOC.MAPA_NUEVO_PLAN "+ orden;
			lista =  enocJdbc.query(comando, new MapaNuevoPlanMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaNuevoPlanDao|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	public List<MapaNuevoPlan> getListPorYear( String year, String orden) {
		
		List<MapaNuevoPlan> lista		= new ArrayList<MapaNuevoPlan>();	
		try{
			String comando = "SELECT PLAN_ID, CARRERA_ID, NOMBRE, VERSION_ID," 
					  + " VERSION_NOMBRE, ESTADO, TIPO, HTS, HPS, HFD, HEI, IDIOMA, HSS, HAS, YEAR"
					  + " FROM ENOC.MAPA_NUEVO_PLAN "
					  + " WHERE YEAR = TO_NUMBER(?, '9999') "+ orden;
			Object[] parametros = new Object[] {year};
			lista =  enocJdbc.query(comando, new MapaNuevoPlanMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaNuevoPlanDao|getListPorYear|:"+ex);
		}
		
		return lista;	
	}
	
	public List<MapaNuevoPlan> getListMaestro( String codigoMaestro, String orden) {
		
		List<MapaNuevoPlan> lista		= new ArrayList<MapaNuevoPlan>();	
		try{
			String comando = "SELECT PLAN_ID, CARRERA_ID, NOMBRE, VERSION_ID, VERSION_NOMBRE, ESTADO, TIPO, HTS, HPS, HFD, HEI, IDIOMA, HSS, HAS, YEAR" +
					  " FROM ENOC.MAPA_NUEVO_PLAN" + 
					  " WHERE (PLAN_ID IN (SELECT DISTINCT(PLAN_ID) FROM ENOC.MAPA_NUEVO_CURSO" + 
					  					" WHERE CODIGO_MAESTRO LIKE '%"+codigoMaestro+"%')" +
					  " OR (SELECT ACCESOS FROM ENOC.ACCESO WHERE CODIGO_PERSONAL = ?) LIKE '%'||CARRERA_ID||'%')" +
					  " AND ESTADO = 'A' "+ orden;			
			lista =  enocJdbc.query(comando, new MapaNuevoPlanMapper(),codigoMaestro);	///		
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaNuevoPlanDao|getListMaestro|:"+ex);
		}
		
		return lista;	
	}
	
	public List<MapaNuevoPlan> getListMaestroPorYear( String codigoMaestro, String orden, String year) {
		
		List<MapaNuevoPlan> lista		= new ArrayList<MapaNuevoPlan>();		
		try{
			String comando = "SELECT PLAN_ID, CARRERA_ID, NOMBRE, VERSION_ID, VERSION_NOMBRE, ESTADO, TIPO, HTS, HPS, HFD, HEI, IDIOMA, HSS, HAS, YEAR" +
					  " FROM ENOC.MAPA_NUEVO_PLAN" + 
					  " WHERE YEAR = TO_NUMBER(?, '9999') AND (PLAN_ID IN (SELECT DISTINCT(PLAN_ID) FROM ENOC.MAPA_NUEVO_CURSO" + 
					  					" WHERE CODIGO_MAESTRO LIKE '%"+codigoMaestro+"%')" +
					  " OR (SELECT ACCESOS FROM ENOC.ACCESO WHERE CODIGO_PERSONAL = ?) LIKE '%'||CARRERA_ID||'%')" + 
					  " AND ESTADO = 'A' "+ orden;
			Object[] parametros = new Object[] {year, codigoMaestro};
			lista =  enocJdbc.query(comando, new MapaNuevoPlanMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaNuevoPlanDao|getListMaestro|:"+ex);
		}
		
		return lista;	
	}
	
	public HashMap<String, MapaNuevoPlan> mapaPlan() {
		
		HashMap<String, MapaNuevoPlan> mapa		= new HashMap<String, MapaNuevoPlan>();
		List<MapaNuevoPlan> lista		= new ArrayList<MapaNuevoPlan>();
		try{
			String comando = "SELECT PLAN_ID, CARRERA_ID, NOMBRE, VERSION_ID, VERSION_NOMBRE, ESTADO, TIPO, HTS, HPS, HFD, HEI, IDIOMA, HSS, HAS, YEAR" +
					  " FROM ENOC.MAPA_NUEVO_PLAN";
			lista =  enocJdbc.query(comando, new MapaNuevoPlanMapper());
			for (MapaNuevoPlan plan : lista) {
				mapa.put(plan.getPlanId(), plan);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaNuevoPlanDao|mapaPlan|:"+ex);
		}
		
		return mapa;	
	}
}