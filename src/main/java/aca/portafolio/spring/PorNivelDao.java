package aca.portafolio.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class PorNivelDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public PorNivel mapeaRegId(String nivelId){
		PorNivel porNivel = new PorNivel();
		
		try{
			String comando = "SELECT NIVEL_ID, NIVEL_NOMBRE, ARCHIVO, DOCUMENTO_ID FROM DANIEL.POR_NIVEL WHERE NIVEL_ID = ?"; 

			Object[] parametros = new Object[] {nivelId};
			porNivel = enocJdbc.queryForObject(comando, new PorNivelMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.spring.PorNivelDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		
		return porNivel;
	}
	
	public boolean existeReg(String nivelId) {
		boolean ok = false;
		
		try{
			String comando = "SELECT * FROM DANIEL.POR_NIVEL WHERE NIVEL_ID = ?"; 

			Object[] parametros = new Object[] {nivelId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.spring.PorNivelDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String getNombre(String nivelId) {
		String nombre = "x";
		
		try{
			String comando = "SELECT NIVEL_NOMBRE FROM DANIEL.POR_NIVEL WHERE NIVEL_ID = ?";

			Object[] parametros = new Object[] {nivelId};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
 				nombre = enocJdbc.queryForObject(comando, String.class, parametros);
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.spring.PorNivelDao|getNombre|:"+ex);
		}
		
		return nombre;
	}
	
	public List<PorNivel> getListAll(String orden) {
		List<PorNivel> lista = new ArrayList<PorNivel>();
	 
		try{
			String comando = "SELECT NIVEL_ID, NIVEL_NOMBRE, ARCHIVO, DOCUMENTO_ID FROM DANIEL.POR_NIVEL "+orden; 

			lista = enocJdbc.query(comando, new PorNivelMapper());
				
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.spring.PorNivelDao|getListAll|:"+ex);
		}
	
		return lista;
	}
	
	public HashMap<String,PorNivel> getMapAll(String orden ) {
		HashMap<String,PorNivel> mapa = new HashMap<String,PorNivel>();
		List<PorNivel> lista		 = new ArrayList<PorNivel>();
		
		try{
			String comando = "SELECT NIVEL_ID, NIVEL_NOMBRE, ARCHIVO, DOCUMENTO_ID FROM DANIEL.POR_NIVEL "+ orden;
			
			lista = enocJdbc.query(comando, new PorNivelMapper());
			for (PorNivel unidad : lista ) {
				mapa.put(unidad.getNivelId(),unidad);
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.spring.PorNivelDao|getMapAll|:"+ex);
		}
		
		return mapa;
	}
	
}
