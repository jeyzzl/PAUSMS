// Clase para la tabla Cat_Avance
package aca.catalogo.spring;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CatAvanceDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( CatAvance avance ) {
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.CAT_AVANCE(AVANCE_ID, NOMBRE_AVANCE ) VALUES( TO_NUMBER(?,'99'), ? )";
			Object[] parametros = new Object[] {avance.getAvanceId(),avance.getNombreAvance()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatAvanceDao|insertReg|:"+ex);			
		}		
		return ok;
	}
	
	public boolean updateReg( CatAvance avance ) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.CAT_AVANCE SET NOMBRE_AVANCE = ? WHERE AVANCE_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {avance.getNombreAvance(), avance.getAvanceId()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatAvanceDao|updateReg|:"+ex);		
		}		
		return ok;
	}
	
	public boolean deleteReg( String avanceId ) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.CAT_AVANCE WHERE AVANCE_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {avanceId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatAvanceDao|deleteReg|:"+ex);			
		}		
		return ok;
	}
	
	public CatAvance mapeaRegId(  String avanceId ) {		
		CatAvance avance 		= new CatAvance();		
		try{
			String comando = "SELECT COUNT(AVANCE_ID) FROM ENOC.CAT_AVANCE WHERE AVANCE_ID = TO_NUMBER(?,'99') "; 
			Object[] parametros = new Object[] {avanceId};
			if(enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				comando = "SELECT AVANCE_ID, NOMBRE_AVANCE FROM ENOC.CAT_AVANCE WHERE AVANCE_ID = TO_NUMBER(?,'99')";			
				avance = enocJdbc.queryForObject(comando, new CatAvanceMapper(), parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatAvanceDao|mapeaRegId|:"+ex);			
		}		
		return avance;
	}
	
	public boolean existeReg( String avanceId ) {
		boolean 		ok 	= false;		
		try{
			String comando = "SELECT COUNT(AVANCE_ID) FROM ENOC.CAT_AVANCE WHERE AVANCE_ID = TO_NUMBER(?,'99') "; 
			Object[] parametros = new Object[] {avanceId};
			if(enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatAvanceDao|existeReg|:"+ex);
		}		
		return ok;
	}
	
	public String maximoReg(Connection conn) {
		String maximo 			= "1";		
		
		try{
			String comando = "SELECT COALESCE(MAX(AVANCE_ID)+1,1) AS MAXIMO FROM ENOC.CAT_AVANCE";
			if(enocJdbc.queryForObject(comando,Integer.class) >= 1){
				maximo = String.valueOf(enocJdbc.queryForObject(comando,String.class));
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatAvanceDao|maximoReg|:"+ex);
		}
		
		return maximo;
	}
	
	public String getNombreAvance( String avanceId){
		String nombre 			= "1";		
		try{
			String comando = "SELECT NOMBRE_AVANCE FROM ENOC.CAT_AVANCE WHERE AVANCE_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {avanceId};
			nombre = enocJdbc.queryForObject(comando, String.class, parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatAvanceDao|getNombreAvance|:"+ex);
		}		
		return nombre;
	}	
		
	public List<CatAvance> getListAll( String orden ) {
		
		List<CatAvance> lista	= new ArrayList<CatAvance>();		
		try{
			String comando = "SELECT AVANCE_ID, NOMBRE_AVANCE FROM ENOC.CAT_AVANCE "+ orden;			
			lista = enocJdbc.query(comando, new CatAvanceMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatAvanceDao|getListAll|:"+ex);
		}		
		return lista;
	}
	
	public HashMap<String,CatAvance> getMapAll( String orden ) {
		
		HashMap<String,CatAvance> mapa = new HashMap<String,CatAvance>();
		List<CatAvance> lista	= new ArrayList<CatAvance>();		
		try{
			String comando = "SELECT AVANCE_ID, NOMBRE_AVANCE FROM ENOC.CAT_AVANCE "+ orden;
			lista = enocJdbc.query(comando,new CatAvanceMapper());
			for(CatAvance avance : lista){				
				mapa.put(avance.getAvanceId(), avance );
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatAvanceDao|getMapAll|:"+ex);
		}		
		return mapa;
	}
}