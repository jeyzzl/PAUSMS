package aca.log.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class LogPoolDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(LogPool log){
		boolean ok = false;

		try{
			String comando = "INSERT INTO ENOC.LOG_POOL(ID, DATO, URL) VALUES(TO_NUMBER(?,'9999999'), ?, ?)";				
			Object[] parametros = new Object[] {
				log.getId(),log.getDato(),log.getUrl()
			};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.log.spring.LogPool|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg(LogPool log){
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.LOG_POOL SET DATO = ?, URL = ? WHERE ID = TO_NUMBER(?,'9999999')";
			
			Object[] parametros = new Object[] {
				log.getDato(), log.getUrl(), log.getId()
			}; 	
			
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
 			
		}catch(Exception ex){
			System.out.println("Error - aca.log.spring.LogPool|updateReg|:"+ex);		
		}
		
		return ok;
	}
	
	public boolean deleteReg(String grupo, String id ){
		boolean ok = false;
		try{
			String comando = "DELETE FROM ENOC.LOG_POOL WHERE ID = TO_NUMBER(?,'9999999')";			
			Object[] parametros = new Object[] {id};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.log.spring.LogPool|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public LogPool mapeaRegId(String id) {
		LogPool logPool = new LogPool();
		try{
			String comando = "SELECT ID, DATO, URL FROM ENOC.LOG_POOL WHERE ID = TO_NUMBER(?,'9999999')";
			Object[] parametros = new Object[] {id};
			logPool = enocJdbc.queryForObject(comando, new LogPoolMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.log.spring.LogPool|mapeaRegId|:"+ex);
		}
		return logPool;
	}
	
	public boolean existeReg(String id) {
		boolean ok 	= false;
		
		try{
			String comando = "SELECT * FROM ENOC.LOG_POOL WHERE ID = TO_NUMBER(?,'99')";

			Object[] parametros = new Object[] {id};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.log.spring.LogPool|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public int maximoReg() {
		int maximo = 1;
		
		try{
			String comando = "SELECT COALESCE(MAX(ID+1),1) FROM ENOC.LOG_POOL";		
			if (enocJdbc.queryForObject(comando,Integer.class)>=1){
				maximo = enocJdbc.queryForObject(comando,Integer.class);
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.log.spring.LogPool|existeReg|:"+ex);
		}
		
		return maximo;
	}
}
