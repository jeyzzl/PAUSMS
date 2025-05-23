package aca.investiga.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class InvReferenteDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( InvReferente ref ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.INV_REFERENTE(CODIGO_ID, CARRERA_ID) "
					+ " VALUES(?, ?)"; 
			Object[] parametros = new Object[] { ref.getCodigoId(),	ref.getCarreraId() };
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;				
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.spring.InvReferente|insertReg|:"+ex);
		}
		return ok;
	}
	
	public boolean deleteReg( InvReferente ref ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.INV_REFERENTE WHERE CODIGO_ID = ? AND CARRERA_ID = ? ";
			Object[] parametros = new Object[] { ref.getCodigoId(),	ref.getCarreraId() };
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;				
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.spring.InvReferente|deleteReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean existeReg( String codigoId, String carreraId) {
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.INV_REFRENTE WHERE CODIGO_ID = ? AND CARRERA_ID = ? ";
			Object[] parametros = new Object[] { codigoId,	carreraId };		
			if(enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.spring.InvReferente|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String getCarrera( String codigoId ){
		
		String nombre			= "1";
		
		try{
			String comando = "SELECT COUNT(CARRERA_ID) FROM ENOC.INV_REFERENTE WHERE CODIGO_ID = ?";
			Object[] parametros = new Object[] { codigoId };		
			if(enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				comando = "SELECT CARRERA_ID FROM ENOC.INV_REFERENTE WHERE CODIGO_ID = ?";
				nombre = enocJdbc.queryForObject(comando,String.class,parametros);
			}				
			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.spring.InvReferente|getCarrera|:"+ex);
		}
		
		return nombre;
	}
	

	public List<InvReferente> listAll( String orden ) {
		List<InvReferente> lista	= new ArrayList<InvReferente>();
		
		String comando	= "";		
		try{
			comando = "SELECT CODIGO_ID, CARRERA_ID FROM ENOC.INV_REFERENTE "+orden; 
			lista = enocJdbc.query(comando, new InvReferenteMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.spring.InvReferenteUtil|listAll|:"+ex);
		}
		
		return lista;
	}	
}