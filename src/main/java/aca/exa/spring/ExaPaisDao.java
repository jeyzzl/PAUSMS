// Clase Util para la tabla de Cat_Division
package aca.exa.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ExaPaisDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( ExaPais exaPais ) {
		boolean ok = true;
		try{
			String comando = "INSERT INTO ENOC.EXA_PAIS(PAIS_ID, PAIS_NOMBRE, NACIONALIDAD, INTERAMERICA)"
				+ " VALUES( TO_NUMBER (?,'999'), ?, ?, ? )";
			Object[] parametros = new Object[]{ 
				exaPais.getPaisId(), exaPais.getPaisNombre() 
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaPaisDao|insertReg|:"+ex);			
		}		
		return ok;
	}	

	public boolean updateReg( ExaPais exaPais ) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.EXA_PAIS SET PAIS_NOMBRE = ? WHERE PAIS_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[]{ 
				exaPais.getPaisNombre(), exaPais.getPaisId() 
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaPaisDao|updateReg|:"+ex);		
		}				
		return ok;
	}
	
	public boolean deleteReg( String paisId ){
		boolean ok = false;
		try{
			String comando = "DELETE FROM ENOC.EXA_PAIS WHERE PAIS_ID = TO_NUMBER(?,'999')";
			if (enocJdbc.update(comando,paisId)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaPaisDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public ExaPais mapeaRegId(  String paisId) {
		ExaPais exaPais = new ExaPais();		 
		try{
			String comando = "SELECT PAIS_ID, PAIS_NOMBRE FROM ENOC.EXA_PAIS WHERE PAIS_ID = TO_NUMBER(?,'999')"; 
			exaPais 	= enocJdbc.queryForObject(comando, new ExaPaisMapper(), paisId);
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaPaisDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		return exaPais;
	}
	
	public boolean existeReg( String paisId){
		boolean 		ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.EXA_PAIS WHERE PAIS_ID = TO_NUMBER(?,'999')";
			if (enocJdbc.queryForObject(comando,Integer.class, paisId) >= 1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaPaisDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoReg() {
		
		int maximo			= 1;
		try{
			String comando = "SELECT MAX(PAIS_ID)+1 MAXIMO FROM ENOC.EXA_PAIS";
			maximo = enocJdbc.queryForObject(comando,Integer.class);			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaPaisDao|maximoReg|:"+ex);
		}		
		return String.valueOf(maximo);
	}
	
	public String getNombrePais( String paisId ) {	
		
		String nombre			= "vacío";		
		try{
			String comando = "SELECT PAIS_NOMBRE FROM ENOC.EXA_PAIS WHERE PAIS_ID = ?";
			nombre 	= enocJdbc.queryForObject(comando,String.class, paisId);			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaPaisDao|getNombrePais|:"+ex);
		}		
		return nombre;
	}
		
	public List<ExaPais> getListAll( String orden ) {		
		List<ExaPais> lista		= new ArrayList<ExaPais>();
		try{
			String comando = "SELECT PAIS_ID, PAIS_NOMBRE FROM ENOC.EXA_PAIS "+ orden;
			lista = enocJdbc.query(comando, new ExaPaisMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaPaisDao|getListAll|:"+ex);
		}		
		return lista;
	}
	
	public List<ExaPais> getListPaisesDistintos( String orden) {
		List<ExaPais> lista		= new ArrayList<ExaPais>();
		try{
			String comando = "SELECT DISTINCT(A.PAIS_NOMBRE) AS PAIS_NOMBRE, A.PAIS_ID FROM ENOC.EXA_PAIS A "+ orden;	
			lista = enocJdbc.query(comando, new ExaPaisMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaPaisDao|getListPaisesDistintos|:"+ex);
		}		
		return lista;
	}
	
	public HashMap<String,ExaPais> getMapAll( String orden ) {
		
		List<ExaPais> lista		= new ArrayList<ExaPais>();
		HashMap<String,ExaPais> mapPais = new HashMap<String,ExaPais>();	
		try{
			String comando = "SELECT PAIS_ID, PAIS_NOMBRE FROM ENOC.EXA_PAIS "+ orden;		
			lista = enocJdbc.query(comando, new ExaPaisMapper());
			for (ExaPais pais : lista) {
				mapPais.put(pais.getPaisId(), pais);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaPaisDao|getMapAll|:"+ex);
		}		
		return mapPais;
	}
}