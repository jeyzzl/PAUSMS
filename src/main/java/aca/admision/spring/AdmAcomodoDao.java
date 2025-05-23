package aca.admision.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AdmAcomodoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(AdmAcomodo admAcomodo) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO SALOMON.ADM_ACOMODO"+ 
				"(ACOMODO_ID, ACOMODO_NOMBRE, ACOMODO_TIPO) "+
				"VALUES(?, ?, ?)";
			
			Object[] parametros = new Object[] {
				admAcomodo.getAcomodoId(), admAcomodo.getAcomodoId(), admAcomodo.getAcomodoTipo()
 		 	};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}

		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmAcomodoDao|insertReg|:"+ex);
		}

		return ok;
	}
	
	public boolean deleteReg(String acomodoId) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM SALOMON.ADM_ACOMODO"+ 
				" WHERE ACOMODO_ID = ?";
			
			Object[] parametros = new Object[] {acomodoId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmAcomodoDao|deleteReg|:"+ex);
		}
				
		return ok;
	}
	
	public AdmAcomodo mapeaRegId(String acomodoId) {
		AdmAcomodo objeto = new AdmAcomodo();
		
		try {
			String comando = "SELECT ACOMODO_ID, ACOMODO_NOMBRE, ACOMODO_TIPO"+
					" FROM SALOMON.ADM_ACOMODO" + 
					" WHERE ACOMODO_ID = ?";
			
			Object[] parametros = new Object[] {acomodoId};
			objeto = enocJdbc.queryForObject(comando, new AdmAcomodoMapper(), parametros);
			
		} catch (Exception ex) {
			System.out.println("Error - aca.admision.spring.AdmAcomodoDao|mapeaRegId|:"+ex);
		}
		
		return objeto;
	}
	
	public boolean existeReg(String acomodoId) {
		boolean ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM SALOMON.ADM_ACOMODO "+ 
				"WHERE FOLIO = TO_NUMBER(?,'9999999')";
			
			Object[] parametros = new Object[] {acomodoId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmAcomodoDao|existeReg|:"+ex);
		}

		return ok;
	}

	public  String getNombreAcomodo(String acomodoId) {
		String nombre			= "empty";
		
		try{
			String comando = "SELECT COUNT(*) FROM SALOMON.ADM_ACOMODO WHERE ACOMODO_ID = ?"; 
			Object[] parametros = new Object[] {acomodoId};
			if(enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				comando = "SELECT ACOMODO_NOMBRE FROM SALOMON.ADM_ACOMODO WHERE ACOMODO_ID = ?";
				nombre = enocJdbc.queryForObject(comando, String.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmAcomodoDao|getNombreAcomodo|:"+ex);
		}
		return nombre;
	}

	public HashMap<String,AdmAcomodo> mapaAcomodoPorTipo(String tipo) {
		
		HashMap<String,AdmAcomodo> mapa	= new HashMap<String,AdmAcomodo>();
		List<AdmAcomodo> lista 			= new ArrayList<AdmAcomodo>();
		
		try{
			String comando = "SELECT "+
					" ACOMODO_ID, ACOMODO_NOMBRE, ACOMODO_TIPO"+
					" FROM SALOMON.ADM_ACOMODO"+
					" WHERE ACOMODO_TIPO = ?"; 			
			lista = enocJdbc.query(comando,new AdmAcomodoMapper(), tipo);
			for(AdmAcomodo objeto : lista){				
				mapa.put(objeto.getAcomodoId(), objeto);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmAcomodoDao|mapaAcomodo|:"+ex);
		}
		
		return mapa;
	}

	public List<AdmAcomodo> getListAcomodosPorTipo( String tipo ) {
		
		List<AdmAcomodo> lisAcomodo	= new ArrayList<AdmAcomodo>();
		String comando	= "";
		
		try{
			comando = " SELECT ACOMODO_ID, ACOMODO_NOMBRE, ACOMODO_TIPO"
					+ " FROM SALOMON.ADM_ACOMODO" 
					+ " WHERE ACOMODO_TIPO = ?"; 
			
			lisAcomodo = enocJdbc.query(comando, new AdmAcomodoMapper(), tipo);	
			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmAcomodoDao|getListAcomodosPorTipo|:"+ex);
		}
		return lisAcomodo;
	}
	

}
