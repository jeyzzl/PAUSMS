// Clase para la tabla de Modulo
package aca.menu.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class ModuloJspDao{
	
	@Autowired	
	@Qualifier("jdbcEnoc")	
	private JdbcTemplate enocJdbc;
	
	public boolean existeReg(String rutaCorta){
		boolean ok = false;
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.MODULO_JSP WHERE RUTA_CORTA = ?";
			Object[] parametros = new Object[]{rutaCorta};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				ok = true;
			}
		}catch( Exception ex){
			System.out.println("Error: aca.menu.JspUtil|existeReg|"+ex);
		}
		
		return ok;
		
	}	
	
	public ModuloJsp traePorRutaCorta(String rutaCorta){
		ModuloJsp jsp = new ModuloJsp();
		
		try{			
			String query = "SELECT ID, RUTA, ENOC, ATLAS, RUTA_CORTA FROM ENOC.MODULO_JSP WHERE RUTA_CORTA = ?";
			Object[] parametros = new Object[]{rutaCorta};
			jsp = enocJdbc.queryForObject(query, new ModuloJspMapper(), parametros);
		}catch( Exception ex){
			System.out.println("Error: aca.menu.JspUtil|traePorRutaCorta|"+ex);
		}
		
		return jsp;
	}
	
	public ArrayList<ModuloJsp> lisTodos(String orden){		
		List<ModuloJsp> lista = new ArrayList<ModuloJsp>();
		
		try{			
			String query = "SELECT ID, RUTA, ENOC, ATLAS, RUTA_CORTA FROM ENOC.MODULO_JSP "+orden;
			lista = enocJdbc.query(query, new ModuloJspMapper());
		}catch( Exception ex){
			System.out.println("Error: aca.menu.JspUtil|lisTodos|"+ex);
		}
		
		return (ArrayList<ModuloJsp>)lista;
	}
	
	public HashMap<String,ModuloJsp> mapTodos(){
		HashMap<String,ModuloJsp> mapa = new HashMap<String,ModuloJsp>();
		List<ModuloJsp> lista = new ArrayList<ModuloJsp>();		
		try{
			String comando = "SELECT ID, RUTA, ENOC, ATLAS, RUTA_CORTA FROM ENOC.MODULO_JSP";
			lista = enocJdbc.query(comando,new ModuloJspMapper());			
			for(ModuloJsp jsp : lista){				
				//mapa.put(Integer.valueOf(religion.getReligionId()), religion);
				mapa.put(jsp.getRutaCorta(), jsp);
			}
		}catch( Exception ex){
			System.out.println("Error:aca.menu.JspUtil|lisTodos|"+ex);
		}
		return mapa;
	}
	
}