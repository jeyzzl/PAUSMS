package aca.catalogo.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CatNotificacionDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;

	public List<CatNotificacion> getListAll(String orden){
		
		List<CatNotificacion> lista = new ArrayList<CatNotificacion>();
		
		try{
			String comando = "SELECT NOTIFICACION_ID, NOTIFICACION_NOMBRE FROM ENOC.CAT_NOTIFICACION "+ orden; 
			lista = enocJdbc.query(comando, new CatNotificacionMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatNotificacionUtil|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String,CatNotificacion> getMapAll(String orden){
		
		HashMap<String,CatNotificacion> mapa = new HashMap<String,CatNotificacion>();
		List<CatNotificacion> lista = new ArrayList<CatNotificacion>();
		
		try{
			String comando = "SELECT NOTIFICACION_ID, NOTIFICACION_NOMBRE FROM ENOC.CAT_NOTIFICACION  "+ orden;
			lista = enocJdbc.query(comando, new CatNotificacionMapper());
			
			
			
			
			
			
			for(CatNotificacion objeto:lista){
				mapa.put(objeto.getNotificacionId(), objeto);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatNotificacionUtil|getMapAll|:"+ex);
		}
		
		return mapa;
	}
}