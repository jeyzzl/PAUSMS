package aca.cred.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CredPersonalDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( CredPersonal per ){
		boolean ok = false;
	
		try{
			String comando = "INSERT INTO ENOC.CRED_PERSONAL"+ 
				"(EVENTO_ID, CODIGO_PERSONAL, NOMBRE) "+
				"VALUES( ?,?,?)";
			Object[] parametros = new Object[] {per.getEventoId(),per.getCodigoPersonal(),per.getNombre()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.cred.CredPersonal|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg( CredPersonal per ){
		boolean ok = false;
	
		try{
			String comando = "UPDATE ENOC.CRED_PERSONAL"+ 
				" SET"+
				" NOMBRE = ? "+
				" WHERE EVENTO_ID = ? AND CODIGO_PERSONAL = ? ";
			Object[] parametros = new Object[] {per.getNombre(),per.getEventoId(),per.getCodigoPersonal()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.cred.CredPersonal|updateReg|:"+ex);		
		}
		
		return ok;
	}
	
	public boolean deleteReg( String eventoId, String codigoPersonal ){
		boolean ok = false;

		try{
			String comando = "DELETE FROM ENOC.CRED_PERSONAL "+ 
				"WHERE EVENTO_ID = ? AND CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {eventoId, codigoPersonal};
			if (enocJdbc.update(comando, parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.cred.CredPersonal|deleteReg|:"+ex);			
		}
		
		return ok;
	}
	
	public CredPersonal mapeaRegId(String eventoId, String codigoPersonal){
		
		CredPersonal per = new CredPersonal();
		
		try{
			String comando = "SELECT "+
				"EVENTO_ID, CODIGO_PERSONAL, NOMBRE " +
				"FROM ENOC.CRED_PERSONAL WHERE EVENTO_ID = TO_NUMBER(?,999) AND CODIGO_PERSONAL = ?"; 
			Object[] parametros = new Object[] {eventoId, codigoPersonal};
			per = enocJdbc.queryForObject(comando, new CredPersonalMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.cred.CredPersonal|mapeaRegId|:"+ex);
		}
		
		return per;
	}
	
	public CredPersonal mapeaRegCodigoPersonal(  String codigoPersonal, String eventoId){
		
		CredPersonal per = new CredPersonal();
		try{
			String comando = "SELECT EVENTO_ID, CODIGO_PERSONAL, NOMBRE FROM ENOC.CRED_PERSONAL WHERE CODIGO_PERSONAL = ? AND EVENTO_ID = ?"; 
			Object[] parametros = new Object[] {codigoPersonal, eventoId};
			per = enocJdbc.queryForObject(comando, new CredPersonalMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.cred.CredPersonal|mapeaRegCodigoPersonal|:"+ex);
		}
		
		return per;
	}
	
	public boolean existeReg( CredPersonal per, String eventoId, String codigoPersonal){
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) EVENTO_ID, CODIGO_PERSONAL, NOMBRE FROM ENOC.CRED_PERSONAL"
					+ " WHERE EVENTO_ID = ? AND CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {eventoId, codigoPersonal};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.cred.CredPersonal|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String maxReg( String eventoId){

		String maximo 			= null;
		String comando			= "";
		
		try{
			comando = " SELECT COALESCE(MAX(TO_NUMBER(CODIGO_PERSONAL))+1,0) AS MAXIMO FROM ENOC.CRED_PERSONAL WHERE EVENTO_ID = ?";{
			comando = "SELECT CODIGO_INICIAL FROM ENOC.CRED_EVENTO WHERE EVENTO_ID = ?";{
			Object[] parametros = new Object[] {eventoId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				maximo = enocJdbc.queryForObject(comando,String.class,parametros);
			}
						
			}
				
			}				
			
		}catch(Exception ex){
			System.out.println("Error - aca.cred.CredPersonal|maxReg|:"+ex);
		}
		
		return maximo;
	}
	
	public String getNombre( String eventoId, String codigoPersonal){
		
		String nombre			= "X";
		
		try{
			String comando = "SELECT NOMBRE FROM ENOC.CRED_PERSONAL "+ 
				"WHERE EVENTO_ID = ? AND CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {eventoId, codigoPersonal};
 			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
 				nombre = enocJdbc.queryForObject(comando,String.class,parametros);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.cred.CredPersonal|getNombre|:"+ex);
		}
		
		return nombre;
	}
	public String getNombre( String codigoPersonal){
		String nombre			= "X";		
		try{
			String comando = "SELECT NOMBRE FROM ENOC.CRED_PERSONAL "+ 
				"WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
 			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
 				nombre = enocJdbc.queryForObject(comando,String.class,parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.cred.CredPersonal|getNombre|:"+ex);
		}
		
		return nombre;
	}
	
	public List<CredPersonal> getListAll( String orden ){
		
		List<CredPersonal> lista		= new ArrayList<CredPersonal>();
		
		try{
			String comando = "SELECT NOMBRE, EVENTO_ID, CODIGO_PERSONAL FROM ENOC.CRED_PERSONAL "+orden; 
			lista = enocJdbc.query(comando, new CredPersonalMapper());

		}catch(Exception ex){
			System.out.println("Error - aca.cred.CredPersonalUtil|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	public List<CredPersonal> getListNombre( String eventoId){		
		List<CredPersonal> lista       = new ArrayList<CredPersonal>();		
		try{
			String comando = "SELECT NOMBRE, EVENTO_ID, CODIGO_PERSONAL FROM ENOC.CRED_PERSONAL WHERE EVENTO_ID = ?"; 
			lista = enocJdbc.query(comando, new CredPersonalMapper(), eventoId);			
		}catch(Exception ex){
			System.out.println("Error - aca.cred.CredPersonalUtil|getListNombre|:"+ex);
		}		
		return lista;
	}
}
