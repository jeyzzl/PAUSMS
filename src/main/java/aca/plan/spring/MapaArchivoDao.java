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
public class MapaArchivoDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( MapaArchivo mapaArchivo ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.MAPA_ARCHIVO (PLAN_ID, FOLIO, NOMBRE, ARCHIVO )"
					+ " VALUES( ?, TO_NUMBER(?,'99'), ?, ? )";

			Object[] parametros = new Object[] {mapaArchivo.getPlanId(), mapaArchivo.getFolio(), mapaArchivo.getNombre(), mapaArchivo.getArchivo()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaArchivoDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg( MapaArchivo mapaArchivo ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.MAPA_ARCHIVO SET ARCHIVO = ?, NOMBRE = ? WHERE PLAN_ID = ? AND FOLIO = TO_NUMBER(?,'99')";
			
			Object[] parametros = new Object[] { mapaArchivo.getArchivo(), mapaArchivo.getNombre(), mapaArchivo.getPlanId(),mapaArchivo.getFolio()};				
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaArchivoDao|updateReg|:"+ex);
		}
		return ok;
	}
	
	public boolean deleteReg( String planId, String folio ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.MAPA_ARCHIVO WHERE PLAN_ID = ? AND FOLIO = TO_NUMBER(?,'99')";
			
			Object[] parametros = new Object[] {planId,folio};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaArchivoDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public MapaArchivo mapeaRegId(  String planId, String folio) {
		MapaArchivo mapaArchivo = new MapaArchivo();
		try{
			String comando = "SELECT PLAN_ID, FOLIO, NOMBRE, ARCHIVO FROM ENOC.MAPA_ARCHIVO WHERE PLAN_ID = ? AND FOLIO =  TO_NUMBER(?,'99')";
			
				Object[] parametros = new Object[] {planId,folio};
				mapaArchivo = enocJdbc.queryForObject(comando, new MapaArchivoMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaArchivoDao|mapeaRegId|:"+ex);
		}
		return mapaArchivo;		
	}	
	
	public boolean existeReg( String planId, String folio) {
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(PLAN_ID) FROM ENOC.MAPA_ARCHIVO WHERE PLAN_ID = ? AND FOLIO= TO_NUMBER(?,'99')"; 
			
			Object[] parametros = new Object[] {planId,folio};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaArchivoDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public List<MapaArchivo> getLista( String planId, String orden ) {
		List<MapaArchivo> lista	= new ArrayList<MapaArchivo>();
		String comando	= "";
		
		try{
			comando = " SELECT PLAN_ID, FOLIO, NOMBRE, ARCHIVO FROM ENOC.MAPA_ARCHIVO"
					+ " WHERE PLAN_ID = ? " +orden;			
			lista = enocJdbc.query(comando, new MapaArchivoMapper(),planId);
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaArchivoDao|getLista|:"+ex);
		}		
		return lista;
	}
	
	public HashMap<String, String> mapaArchivos() {
		
		List<aca.Mapa> list 			= new ArrayList<aca.Mapa>();
		HashMap<String, String> mapa	= new HashMap<String,String>();
		String comando = "";
		
		try{			
			comando = "SELECT PLAN_ID||FOLIO AS LLAVE, PLAN_ID||FOLIO AS VALOR FROM ENOC.MAPA_ARCHIVO WHERE ARCHIVO IS NOT NULL";
			list = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa obj : list){
				mapa.put(obj.getLlave(), obj.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaArchivoDao|mapaArchivos|:"+ex);
		}
		return mapa;
	}
	
}