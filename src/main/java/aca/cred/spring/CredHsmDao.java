package aca.cred.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CredHsmDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( CredHsm hsm){
		boolean ok = false;
	
		try{
			String comando = "INSERT INTO ENOC.CRED_HSM"+ 
				"(CLAVE, NOMBRE, AREA, FONDO, ESTADO) "+
				"VALUES( ?,?,?,?,?)";
			Object[] parametros = new Object[] {hsm.getClave(),hsm.getNombre(),hsm.getArea(), hsm.getFondo(), hsm.getEstado()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	

		}catch(Exception ex){
			System.out.println("Error - aca.cred.credHsm|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg( CredHsm hsm ){
		boolean ok = false;

		try{
			String comando = "UPDATE ENOC.CRED_HSM"+ 
				" SET"+
				" NOMBRE = ?,"+
				" AREA = ?,"+
				" FONDO = ?," +
				" ESTADO = ?"+
				" WHERE CLAVE = ? ";
			Object[] parametros = new Object[] {hsm.getNombre(),hsm.getArea(),hsm.getFondo(), hsm.getEstado(), hsm.getClave()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.cred.credHsm|updateReg|:"+ex);		
		}
		
		return ok;
	}
	
	public boolean deleteReg( String clave ){
		boolean ok = false;
		try{
			String comando = "DELETE FROM ENOC.CRED_HSM "+ 
				"WHERE CLAVE = ? ";
			Object[] parametros = new Object[] {clave};
			if (enocJdbc.update(comando, parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.cred.credHsm|deleteReg|:"+ex);			
		}
		
		return ok;
	}
	
	public CredHsm mapeaRegId(  String clave){
		
		CredHsm hsm = new CredHsm();
		
		try{
			String comando = "SELECT "+
				"CLAVE, NOMBRE, AREA, FONDO, ESTADO " +
				"FROM ENOC.CRED_HSM WHERE CLAVE = ? "; 
			Object[] parametros = new Object[] {clave};
			hsm = enocJdbc.queryForObject(comando, new CredHsmMapper(), parametros);
		
		}catch(Exception ex){
			System.out.println("Error - aca.cred.credHsm|mapeaRegId|:"+ex);
		}
		
		return hsm;
	}
	
	public boolean existeReg( String clave){
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) CLAVE FROM ENOC.CRED_HSM "+ 
				"WHERE CLAVE = ? ";
			Object[] parametros = new Object[] {clave};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.cred.credHsm|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String maxReg( String prefijo){

		String maximo 			= null;
		
		try{
			String comando = "SELECT TO_CHAR(COALESCE(MAX(TO_NUMBER(CLAVE,'9999999')+1),9899001)) AS MAXIMO FROM ENOC.CRED_HSM"+ 
					" WHERE clave like '"+prefijo+"%'";	
			Object[] parametros = new Object[] {prefijo};
 			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
 				maximo = enocJdbc.queryForObject(comando,String.class,parametros);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.cred.credHsm|maxReg|:"+ex);
		}
		
		return maximo;
	}
	
	public String getNombre( String clave){
	
		String nombre			= "X";
		
		try{
			String comando = "SELECT NOMBRE FROM ENOC.CRED_HSM "+ 
				"WHERE CLAVE = ? ";
			Object[] parametros = new Object[] {clave};
 			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
 				nombre = enocJdbc.queryForObject(comando,String.class,parametros);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.cred.credHsm|existeReg|:"+ex);
		}
		
		return nombre;
	}
	
	public List<CredHsm> getListAll( String orden ){
		
		List<CredHsm> lista			= new ArrayList<CredHsm>();
		
		try{
			String comando = "SELECT CLAVE, NOMBRE, AREA, FONDO, ESTADO FROM ENOC.CRED_HSM "+orden; 
			lista = enocJdbc.query(comando, new CredHsmMapper());

		}catch(Exception ex){
			System.out.println("Error - aca.cred.credHsmUtil|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	public List<CredHsm> getListClinica( String clinica, String orden){
		
		List<CredHsm> lista			= new ArrayList<CredHsm>();		
		try{
			String comando = "SELECT CLAVE, NOMBRE, AREA, FONDO, ESTADO FROM ENOC.CRED_HSM WHERE FONDO = ? " +orden; 
			lista = enocJdbc.query(comando, new CredHsmMapper(), clinica);			
		}catch(Exception ex){
			System.out.println("Error - aca.cred.getListClinica|getListAll|:"+ex);
		}		
		return lista;
	}
}