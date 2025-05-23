/*
 * Created on 13/02/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package aca.tit.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Component
public class TitAutorizacionDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;

    
    public boolean insertReg(TitAutorizacion autorizacion) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.TIT_AUTORIZACION (AUTORIZACION_ID, AUTORIZACION_NOMBRE) VALUES( TO_NUMBER(?,'99'), ?) ";
			
			Object[] parametros = new Object[] {autorizacion.getAutorizacionId(), autorizacion.getAutorizacionNombre()};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitAutorizacionDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg(TitAutorizacion autorizacion) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.TIT_AUTORIZACION SET AUTORIZACION_NOMBRE = ? WHERE AUTORIZACION_ID = TO_NUMBER(?,'99')";
			
			Object[] parametros = new Object[] {autorizacion.getAutorizacionNombre(), autorizacion.getAutorizacionId()};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitAutorizacionDao|updateReg|:"+ex);
		}
		return ok;
	}	
	
	public boolean deleteReg(String autorizacionId ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.TIT_AUTORIZACION WHERE AUTORIZACION_ID = TO_NUMBER(?,'99')";
			
			Object[] parametros = new Object[] {autorizacionId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitAutorizacionDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public TitAutorizacion mapeaRegId(  String autorizacionId) {
		TitAutorizacion autorizacion = new TitAutorizacion();
		 
		try{
			String comando = "SELECT AUTORIZACION_ID, AUTORIZACION_NOMBRE FROM ENOC.TIT_AUTORIZACION WHERE AUTORIZACION_ID = TO_NUMBER(?,'99')";
			
			Object[] parametros = new Object[] {autorizacionId};
			autorizacion = enocJdbc.queryForObject(comando, new TitAutorizacionMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitAutorizacionDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		return autorizacion;
		
	}	
	
	public boolean existeReg(String autorizacionId) {
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.TIT_AUTORIZACION WHERE AUTORIZACION_ID = TO_NUMBER(?,'99') "; 
			
			Object[] parametros = new Object[] {autorizacionId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitAutorizacionDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public List<TitAutorizacion> listAll( String orden) {
		List<TitAutorizacion> lista		= new ArrayList<TitAutorizacion>();
		String comando					= "";
		
		try{
			comando = " SELECT AUTORIZACION_ID, AUTORIZACION_NOMBRE FROM ENOC.TIT_AUTORIZACION "+orden;	 
			
			lista = enocJdbc.query(comando, new TitAutorizacionMapper());	
			
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitAutorizacionDao|listAll|:"+ex);
		}
		
		return lista;
	}
	
	public List<TitAutorizacion> lisAutorizacion( String autorizacion, String orden) {
		List<TitAutorizacion> lista		= new ArrayList<TitAutorizacion>();
		
		try{
			String comando = "SELECT AUTORIZACION_ID, AUTORIZACION_NOMBRE FROM ENOC.TIT_AUTORIZACION WHERE AUTORIZACION_ID = TO_NUMBER(?,'99')";
			
			Object[] parametros = new Object[] {autorizacion};
			lista = enocJdbc.query(comando, new TitAutorizacionMapper(), parametros);	
			
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitAutorizacionDao|listCarrera|:"+ex);
		}
		return lista;
	}
	
	public String getNombreAutorizacion(String autorizacionId) {		
		String nombre			= "vacio";
		
		try{
			String comando = "SELECT AUTORIZACION_NOMBRE FROM ENOC.TIT_AUTORIZACION WHERE AUTORIZACION_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {autorizacionId};			
			nombre = enocJdbc.queryForObject(comando, String.class, parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitAutorizacionDao|getNombreAutorizacion|:"+ex);
		}
		
		return nombre;
	}
	
	public HashMap<String, String> mapaAutorizacion() {
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa	= new HashMap<String,String>();	
		
		try{
			String comando	= "SELECT AUTORIZACION_ID AS LLAVE, AUTORIZACION_NOMBRE AS VALOR FROM ENOC.TIT_AUTORIZACION";					
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			
			for (aca.Mapa map : lista){
				mapa.put(map.getLlave(), map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitAutorizacionDao|mapaAutorizacion|:"+ex);
		}
		return mapa;
	}
	
}