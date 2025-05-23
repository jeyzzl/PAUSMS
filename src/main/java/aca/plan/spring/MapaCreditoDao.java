// Clase Util para la tabla de Mapa_Plan
package aca.plan.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class MapaCreditoDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( MapaCredito mapaCredito ) {
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.MAPA_CREDITO"+ 
				"(PLAN_ID, CICLO, CREDITOS, OPTATIVOS, TITULO, ESTADO ) "+
				"VALUES( ?, "+
				"TO_NUMBER(?,'99'), "+
				"TO_NUMBER(?,'99.99'), "+
				"TO_NUMBER(?,'99.99'), ?, ?)";
			Object[] parametros = new Object[] {mapaCredito.getPlanId(),mapaCredito.getCiclo(),mapaCredito.getCreditos(),mapaCredito.getOptativos(),mapaCredito.getTitulo(), mapaCredito.getEstado()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCreditoDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg( MapaCredito mapaCredito ){
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.MAPA_CREDITO " + 
				"SET CREDITOS = TO_NUMBER(?,'99.99'), " +
				"OPTATIVOS = TO_NUMBER(?,'99.99'), " +
				"TITULO = ?, " +
				"ESTADO = ? " +
				"WHERE PLAN_ID = ? "+
				"AND CICLO = TO_NUMBER(?,'99')";			
			Object[] parametros = new Object[] {mapaCredito.getCreditos(),mapaCredito.getOptativos(),mapaCredito.getTitulo(), mapaCredito.getEstado(),mapaCredito.getPlanId(),mapaCredito.getCiclo()};				
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCreditoDao|updateReg|:"+ex);		
		}
		return ok;
	}
	
	public boolean deleteReg( String planId, String ciclo ) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.MAPA_CREDITO " + 
					"WHERE PLAN_ID = ? "+
					"AND CICLO = TO_NUMBER(?,'99')";			
			Object[] parametros = new Object[] {planId,ciclo};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCreditoDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public MapaCredito mapeaRegId(  String planId, String ciclo) {
		MapaCredito mapaCredito = new MapaCredito();
		try{
			String comando = "SELECT PLAN_ID, CICLO, "+
					"TO_CHAR(COALESCE(CREDITOS,0),'99.99') AS CREDITOS, TO_CHAR(COALESCE(OPTATIVOS,0),'99.99') AS OPTATIVOS, TITULO, ESTADO "+													
					"FROM ENOC.MAPA_CREDITO WHERE PLAN_ID = ? AND CICLO =  TO_NUMBER(?,'99')";
			
				Object[] parametros = new Object[] {planId,ciclo};
				mapaCredito = enocJdbc.queryForObject(comando, new MapaCreditoMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCreditoDao|mapeaRegId|:"+ex);
		}
		return mapaCredito;		
	}	
	
	public boolean existeReg( String planId, String ciclo) {
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.MAPA_CREDITO WHERE PLAN_ID = ? AND CICLO= TO_NUMBER(?,'99')";			
			Object[] parametros = new Object[] {planId,ciclo};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCreditoDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public float creditosPlan(  String planId) {
		float nCreditos			= 0;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.MAPA_CREDITO WHERE PLAN_ID = ?";			
			Object[] parametros = new Object[] {planId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				comando = "SELECT SUM(CREDITOS+OPTATIVOS) AS CRED FROM ENOC.MAPA_CREDITO WHERE PLAN_ID = ?";				
				nCreditos = enocJdbc.queryForObject(comando, Integer.class, parametros);
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCreditoDao|creditosPlan|:"+ex);
		}
		return nCreditos;
	}
	
	public float creditosObligatorios( String planId) {
		float nCreditos			= 0;
		
		try{
			String comando = "SELECT SUM(CREDITOS) AS CRED FROM ENOC.MAPA_CREDITO WHERE PLAN_ID = ?";			
			Object[] parametros = new Object[] {planId};
			nCreditos = enocJdbc.queryForObject(comando, Float.class, parametros);		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCreditoDao|creditosObligatorios|:"+ex);
		}		
		return nCreditos;
	}
	
	public float creditosElecPlan(  String planId) {
		float nCreditos			= 0;		
		try{
			String comando = "SELECT SUM(OPTATIVOS) AS OPTATIVOS FROM ENOC.MAPA_CREDITO WHERE PLAN_ID = ?";			
			Object[] parametros = new Object[] {planId};
			nCreditos = enocJdbc.queryForObject(comando, Float.class, parametros);		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCreditoDao|creditosElecPlan|:"+ex);
		}
		return nCreditos;
	}
	
	public String getTitulo( String planId, String ciclo) {
		String titulo = "X";
				
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.MAPA_CREDITO WHERE PLAN_ID = ? AND CICLO = TO_NUMBER(?,'99') ";
			Object[] parametros = new Object[] {planId, ciclo};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >=1 ){
				comando = "SELECT COALESCE(TITULO,'X') FROM ENOC.MAPA_CREDITO WHERE PLAN_ID = ? AND CICLO = TO_NUMBER(?,'99') ";
				titulo = enocJdbc.queryForObject(comando, String.class, parametros);
			}
			
			if (titulo.equals("X")){
				switch(Integer.parseInt(ciclo)){
					case 1:  titulo = "PRIMERO"; 		break;
					case 2:  titulo = "SEGUNDO"; 		break;
					case 3:  titulo = "TERCERO"; 		break;
					case 4:  titulo = "CUARTO"; 		break;
					case 5:  titulo = "QUINTO"; 		break;				
					case 6:  titulo = "SEXTO"; 			break;
					case 7:	 titulo = "SEPTIMO";		break;
					case 8:	 titulo = "OCTAVO";			break;
					case 9:	 titulo = "NOVENO";			break;
					case 10: titulo = "DECIMO";			break;
					case 11: titulo = "UNDECIMO";		break;
					case 12: titulo = "DUODECIMO";		break;
					case 13: titulo = "DECIMO TERCERO";	break;
					case 14: titulo = "DECIMO CUARTO";	break;
					case 15: titulo = "DECIMO QUINTO";	break;
					case 16: titulo = "DECIMO SEXTO";	break;
					case 17: titulo = "DECIMO SEPTIMO";	break;
					case 18: titulo = "DECIMO OCTAVO";	break;
					case 19: titulo = "DECIMO NOVENO";	break;
					case 20: titulo = "VIGESIMO";		break;
				}				
			}			
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCreditoDao|getTitulo|:"+ex);
		}		
		return titulo;
	}
		
	public List<MapaCredito> getListAll( String orden ) {
		List<MapaCredito> lista	= new ArrayList<MapaCredito>();
		try{
			String comando = "SELECT PLAN_ID, CICLO, CREDITOS, OPTATIVOS, TITULO, ESTADO FROM ENOC.MAPA_CREDITO "+orden;			
			lista = enocJdbc.query(comando, new MapaCreditoMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCreditoDao|getListAll|:"+ex);
		}		
		return lista;
	}
	
	public List<MapaCredito> getLista( String planId, String orden ) {
		List<MapaCredito> lista	= new ArrayList<MapaCredito>();	
		try{
			String comando = "SELECT PLAN_ID, CICLO, CREDITOS, OPTATIVOS, TITULO, ESTADO FROM ENOC.MAPA_CREDITO WHERE PLAN_ID = ? " +orden;			
			lista = enocJdbc.query(comando, new MapaCreditoMapper(), planId);			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCreditoDao|getLista|:"+ex);
		}		
		return lista;
	}
	
	public List<String> getSemetres( String planId, String orden ) {
		List<String> lista		= new ArrayList<String>();
		String comando	= "";
		
		try{
			comando = "SELECT DISTINCT(CICLO) AS SEMESTRE FROM ENOC.MAPA_CURSO WHERE PLAN_ID = ? " +orden; 
			
			lista = enocJdbc.queryForList(comando,String.class,planId);
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCreditoDao|getSemestres|:"+ex);
		}
		return lista;
	}
	
	public HashMap<String, MapaCredito> mapaCredito( String planId) {
		HashMap<String, MapaCredito> mapa	= new HashMap<String, MapaCredito>();
		List<MapaCredito> lista	= new ArrayList<MapaCredito>();	
		
		try{				
			String comando = "SELECT PLAN_ID, CICLO, CREDITOS, OPTATIVOS, TITULO, ESTADO FROM ENOC.MAPA_CREDITO WHERE PLAN_ID = ?";
			Object[] parametros = new Object[] {planId};
			lista = enocJdbc.query(comando, new MapaCreditoMapper(), parametros);
			for(MapaCredito map : lista){				
				mapa.put(map.getPlanId()+map.getCiclo(), map);
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCreditoDao|mapaCredito|:"+ex);
		}

		return mapa;
	}
	
	public HashMap<String,String> mapaCreditos() {
		HashMap<String,String> mapa	= new HashMap<String, String>();
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();
		
		try{				
			String comando = "SELECT PLAN_ID AS LLAVE, COUNT(*) AS VALOR FROM ENOC.MAPA_CREDITO GROUP BY PLAN_ID";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), map.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCreditoDao|mapaCreditos|:"+ex);
		}

		return mapa;
	}
}