package aca.log.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class LogProcesoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(LogProceso log){
		boolean ok = false;

		try{
			String comando = "INSERT INTO ENOC.LOG_PROCESO(FOLIO, CODIGO_PERSONAL, MODULO, FECHA, EVENTO) VALUES(TO_NUMBER(?,'9999999'), ?, ?, now(), ?)";				
			Object[] parametros = new Object[] {
				log.getFolio(),log.getCodigoPersonal(),log.getModulo(),log.getEvento()
			};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.log.spring.LogProceso|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public int maximoReg() {
		int maximo = 1;
		
		try{
			String comando = "SELECT COALESCE(MAX(FOLIO+1),1) FROM ENOC.LOG_PROCESO";		
			if (enocJdbc.queryForObject(comando,Integer.class)>=1){
				maximo = enocJdbc.queryForObject(comando,Integer.class);
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.log.spring.LogProceso|existeReg|:"+ex);
		}
		
		return maximo;
	}

}
