//Clase  para la tabla Materias_Insc
package aca.cred.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CredEventoDao{	
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( CredEvento evento ){

		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.CRED_EVENTO(EVENTO_ID, EVENTO_NOMBRE, CODIGO_INICIAL) " +
				"VALUES(TO_NUMBER(?,'999'),?,?) ";
			Object[] parametros = new Object[] {evento.getEventoId(),evento.getEventoNombre(),evento.getCodigoInicial()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.cred.credEvento|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg( CredEvento evento ){
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.CRED_EVENTO " + 
				"SET  EVENTO_NOMBRE= ?, CODIGO_INICIAL=? " +
				"WHERE EVENTO_ID= TO_NUMBER(?,999) ";
			Object[] parametros = new Object[] {evento.getEventoNombre(),evento.getCodigoInicial(),evento.getEventoId()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.cred.credEvento|updateReg|:"+ex);		
		}
		
		return ok;
	}	
	
	public boolean deleteReg( String eventoId ){
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CRED_EVENTO "+ 
				"WHERE EVENTO_ID = TO_NUMBER(?,999)  ";
			Object[] parametros = new Object[] {eventoId};
			if (enocJdbc.update(comando, parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.cred.credEvento|deleteReg|:"+ex);			
		}
		
		return ok;
	}
	
	public CredEvento mapeaRegId( String eventoId){		
		
		CredEvento evento = new CredEvento();		
		
		try{
			String comando = "SELECT EVENTO_ID, EVENTO_NOMBRE, CODIGO_INICIAL FROM ENOC.CRED_EVENTO " +					
					"WHERE EVENTO_ID = TO_NUMBER(?,999) ";		
			Object[] parametros = new Object[] {eventoId};
			evento = enocJdbc.queryForObject(comando, new CredEventoMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.cred.credEvento|mapeaRegId|:"+ex);
		}
		
		return evento;
	}
	
	public boolean existeReg( String eventoId){
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CRED_EVENTO WHERE EVENTO_ID = TO_NUMBER(?,999)  "; 
			Object[] parametros = new Object[] {eventoId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.cred.credEvento|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoReg( String eventoId){

		String maximo			= "1";
		
		try{
			String comando = "SELECT TO_CHAR((MAX(TO_NUMBER(CODIGO_PERSONAL,'9999999')+1)) AS MAXIMO FROM ENOC.CRED_EVENTO WHERE EVENTO_ID = TO_NUMBER(?,999) ";
			Object[] parametros = new Object[] {eventoId};
 			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
 				maximo = enocJdbc.queryForObject(comando,String.class,parametros);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.cred.credEvento|maximoReg|:"+ex);
		}
		
		return maximo;
	}	
	
	public List<CredEvento> getListAll( String orden){
		
		List<CredEvento> lista		= new ArrayList<CredEvento>();

		try{
			String comando = "SELECT EVENTO_ID, EVENTO_NOMBRE, CODIGO_INICIAL" +
					" FROM ENOC.CRED_EVENTO "+orden;	 
			lista = enocJdbc.query(comando, new CredEventoMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.cred.credEvento|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	public List<CredEvento> getListEvento( String eventoId){
		
		List<CredEvento> lista		= new ArrayList<CredEvento>();
		
		try{
			String comando = "SELECT EVENTO_ID, EVENTO_NOMBRE, CODIGO_INICIAL" +
					" FROM ENOC.CRED_EVENTO WHERE EVENTO_ID = "+eventoId;	
			lista = enocJdbc.query(comando, new CredEventoMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.cred.credEvento|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String,CredEvento> getMapAll( String orden ){
		
		HashMap<String,CredEvento> mapa = new HashMap<String,CredEvento>();
		List<CredEvento> lista		= new ArrayList<CredEvento>();
		
		try{
			String comando = "SELECT EVENTO_ID, EVENTO_NOMBRE, CODIGO_INICIAL"
					+ " FROM ENOC.CRED_EVENTO "+ orden;
			lista = enocJdbc.query(comando, new CredEventoMapper());
			for(CredEvento evento  : lista){				
				mapa.put(evento.getEventoId(), evento);					
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.cred.credEvento|getMapAll|:"+ex);
		}
		
		return mapa;
	}
	
}