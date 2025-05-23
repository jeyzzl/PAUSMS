package aca.log.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class LogBecaDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(LogBeca log){
		boolean ok = false;
		try{
			String comando = "INSERT INTO ENOC.LOG_BECA(ID, TABLA, OPERACION, IP, FECHA, USUARIO, DATOS) VALUES(TO_NUMBER(?, '9999999'), ?, ?, ?, TO_DATE(now(), 'DD/MM/YYYY HH:MI:SS'), ?, ?)";
			Object[] parametros = new Object[] {
				log.getId(), log.getTabla(), log.getOperacion(), log.getIp(), log.getUsuario(), log.getDatos()
			};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.log.spring.LogBecaDao|insertReg|:"+ex);
		}
		return ok;
	}
	
	public String maximoReg(){
		String maximo = "1";
		
		try{
			String comando = "SELECT COALESCE(MAX(ID)+1,1) MAXIMO FROM ENOC.LOG_BECA"; 
			maximo = enocJdbc.queryForObject(comando, String.class);
		}catch(Exception ex){
			System.out.println("Error - aca.log.spring.LogBecaDao|maximoReg|:"+ex);
		}
		
		return maximo;		
	}

}
