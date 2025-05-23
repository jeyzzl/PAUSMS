//Clase para la vista MOD_OPCION

package aca.vista.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ModOpcionDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public List<ModOpcion> getListAll (String orden){
		
		List<ModOpcion> lista 				= new ArrayList<ModOpcion>();
		
		try {
			String command = "SELECT (SELECT MENU_ID FROM ENOC.MODULO WHERE MODULO_ID = A.MODULO_ID) AS MENU_ID, MODULO_ID, OPCION_ID, NOMBRE_MODULO, NOMBRE_OPCION, "+
				"URL_MODULO, URL_OPCION, USUARIOS  FROM ENOC.MOD_OPCION A "+ orden; 
			lista = enocJdbc.query(command, new ModOpcionMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.ModOpcionDao|getListAll|:"+ex);
		}		
		
		return lista;
	}
	
	public List<ModOpcion> lisPorUsuario (String codigoPersonal, String orden){
		
		List<ModOpcion> lista 				= new ArrayList<ModOpcion>();		
		try {
			String command = "SELECT MODULO_ID, OPCION_ID, NOMBRE_MODULO, NOMBRE_OPCION, URL_MODULO, URL_OPCION, USUARIOS"
					+ " FROM ENOC.MOD_OPCION"
					+ " WHERE OPCION_ID IN (SELECT OPCION_ID FROM ACCESO_OPCION WHERE CODIGO_PERSONAL = ?)"+ orden; 
			lista = enocJdbc.query(command, new ModOpcionMapper(), codigoPersonal);			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.ModOpcionDao|lisPorUsuario|:"+ex);
		}		
		
		return lista;
	}
	
	
	public List<ModOpcion> getListCat ( String menu_Id, String orden){
		
		List<ModOpcion> lista 				= new ArrayList<ModOpcion>();
		
		try {
			String command = "SELECT (SELECT MENU_ID FROM ENOC.MODULO WHERE MODULO_ID = A.MODULO_ID) AS MENU_ID, MODULO_ID, OPCION_ID, NOMBRE_MODULO, NOMBRE_OPCION, "+
				"URL_MODULO, URL_OPCION, USUARIOS  FROM ENOC.MOD_OPCION A "+ 
				"WHERE MODULO_ID IN (SELECT MODULO_ID FROM ENOC.MODULO WHERE MENU_ID= ? ) "+orden; 
			lista = enocJdbc.query(command, new ModOpcionMapper(), menu_Id);
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.ModOpcionDao|getListAll|:"+ex);
		}		
		
		return lista;
	}	
}