//Clase para la tabla de Modulo
package aca.graduacion.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CarreraOrdenDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;	
	
	public boolean insertReg( CarreraOrden orden ) {
 		boolean ok = false;
 		
 		try{
 			String comando = "INSERT INTO ENOC.CARRERA_ORDEN"+ 
 				"(CARRERA_ID, ORDEN)"+
 				"VALUES( ?, TO_NUMBER(?,'999.99'))";
 			Object[] parametros = new Object[] {orden.getCarreraId(), orden.getOrden()};	
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
 		}catch(Exception ex){
 			System.out.println("Error - aca.graduacion.spring.CarreraOrdenDao|insertReg|:"+ex);			
 		}
 		return ok;
 	}	
 	
 	public boolean updateReg( CarreraOrden orden ) {
 		boolean ok = false;
 		
 		try{
 			String comando = "UPDATE ENOC.CARRERA_ORDEN SET ORDEN = TO_NUMBER(?,'999.99') WHERE CARRERA_ID = ?";
 			Object[] parametros = new Object[] {orden.getOrden(), orden.getCarreraId()}; 			
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
 		}catch(Exception ex){
 			System.out.println("Error - aca.graduacion.spring.CarreraOrdenDao|updateReg|:"+ex);		
 		}
 		return ok;
 	}
 	 	
 	public boolean deleteReg( String carreraId) {
 		boolean ok = false;
 		
 		try{
 			String comando = "DELETE FROM ENOC.CARRERA_ORDEN WHERE CARRERA_ID = ?";
 			Object[] parametros = new Object[] {carreraId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
 		}catch(Exception ex){
 			System.out.println("Error - aca.graduacion.spring.CarreraOrdenDao|deleteReg|:"+ex);			
 		}
 		return ok;
 	}
 	
 	public CarreraOrden mapeaRegId(  String carreraId ) {
 		
 		CarreraOrden objeto = new CarreraOrden();
 		
 		try{
	 		String comando = "SELECT CARRERA_ID, ORDEN FROM ENOC.CARRERA_ORDEN WHERE CARRERA_ID = ?";
	 		Object[] parametros = new Object[] {carreraId};
			objeto = enocJdbc.queryForObject(comando, new CarreraOrdenMapper(),parametros);
			
 		}catch(Exception ex){
			System.out.println("Error - aca.graduacion.spring.CarreraOrdenDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
 		return objeto;
 	}
	
 	public boolean existeReg( String carreraId) {
 		boolean ok = false;
 		
 		try{
 			String comando = "SELECT COUNT(CARRERA_ID) FROM ENOC.CARRERA_ORDEN WHERE CARRERA_ID = ?";
 			Object[] parametros = new Object[] {carreraId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
			
 		}catch(Exception ex){
 			System.out.println("Error - aca.graduacion.spring.CarreraOrdenDao|existeReg|:"+ex);
 		}
 		return ok;
 	}
 	
 	public String getOrden( String carreraId) {
 		String orden = "0";
 		
 		try{
 			String comando = "SELECT COUNT(CARRERA_ID) FROM ENOC.CARRERA_ORDEN WHERE CARRERA_ID = ?";
 			Object[] parametros = new Object[] {carreraId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				comando = "SELECT ORDEN FROM ENOC.CARRERA_ORDEN WHERE CARRERA_ID = ?";
				orden = enocJdbc.queryForObject(comando,String.class,parametros);
			}
			
 		}catch(Exception ex){
 			System.out.println("Error - aca.graduacion.spring.CarreraOrdenDao|existeReg|:"+ex);
 		}
 		return orden;
 	}	
	
 	public HashMap<String, String> mapaOrden (){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		
		try {
			String comando = "SELECT CARRERA_ID AS LLAVE, ORDEN AS VALOR FROM ENOC.CARRERA_ORDEN";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista ){
				mapa.put(map.getLlave(), map.getValor());
			}
		}catch(Exception ex) {
			System.out.println("Error - aca.graduacion.spring.CarreraOrdenDao|mapaOrden|:"+ex);
		}
		return mapa;
	}
}