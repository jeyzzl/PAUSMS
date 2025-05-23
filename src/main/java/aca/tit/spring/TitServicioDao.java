package aca.tit.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class TitServicioDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(TitServicio servicio ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.TIT_SERVICIO (SERVICIO_ID, SERVICIO_NOMBRE) VALUES( TO_NUMBER(?,'99'), ? ) ";
			
			Object[] parametros = new Object[] {servicio.getServicioId(),servicio.getServicioNombre()};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitServicioDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg(TitServicio servicio) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.TIT_SERVICIO SET SERVICIO_NOMBRE = ? WHERE ID = TO_NUMBER(?,'99')";
			
			Object[] parametros = new Object[] {servicio.getServicioNombre(),servicio.getServicioId()};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitServicioDao|updateReg|:"+ex);		
		}
		return ok;
	}	
	
	public boolean deleteReg(String servicioId ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.TIT_SERVICIO WHERE SERVICIO_ID = TO_NUMBER(?,'99')";
			
			Object[] parametros = new Object[] {servicioId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitServicioDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public TitServicio mapeaRegId(  String servicioId) {
		TitServicio servicio = new TitServicio();
		 
		try{
			String comando = "SELECT SERVICIO_ID, SERVICIO_NOMBRE FROM ENOC.TIT_SERVICIO WHERE SERVICIO_ID = TO_NUMBER(?,'99')";
			
			Object[] parametros = new Object[] {servicioId};
			servicio = enocJdbc.queryForObject(comando, new TitServicioMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitServicioDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		return servicio;
		
	}	
	
	public boolean existeReg(String ServicioId) {
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.TIT_SERVICIO WHERE SERVICIO_ID = TO_NUMBER(?,'99') "; 
			
			Object[] parametros = new Object[] {ServicioId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitServicioDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public String getNombreFundamento( String fundamentoId) {		
		String nombre			= "vacio";
		
		try{
			String comando = "SELECT SERVICIO_NOMBRE FROM ENOC.TIT_SERVICIO WHERE SERVICIO_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {fundamentoId};			
			nombre = enocJdbc.queryForObject(comando, String.class, parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitServicioDao|getNombreFundamento|:"+ex);
		}
		
		return nombre;
	}
	
	public List<TitServicio> listAll( String orden) {
		List<TitServicio> lista		= new ArrayList<TitServicio>();
		String comando		= "";
		
		try{
			comando = " SELECT SERVICIO_ID, SERVICIO_NOMBRE FROM ENOC.TIT_SERVICIO "+orden;	 
			
			lista = enocJdbc.query(comando, new TitServicioMapper());	
			
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitServicioDao|listAll|:"+ex);
		}
		return lista;
	}
	
	public HashMap<String, String> mapaServicios() {
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa	= new HashMap<String,String>();	
		
		try{
			String comando	= "SELECT SERVICIO_ID AS LLAVE, SERVICIO_NOMBRE AS VALOR FROM ENOC.TIT_SERVICIO";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			
			for (aca.Mapa map : lista){
				mapa.put(map.getLlave(), map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitServicioDao|mapaServicios|:"+ex);
		}
		return mapa;
	}
	
}