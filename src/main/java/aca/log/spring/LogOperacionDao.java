package aca.log.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class LogOperacionDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(LogOperacion log){
		boolean ok = false;

		try{
			String comando = "INSERT INTO ENOC.LOG_OPERACION(TABLA, OPERACION, IP, FECHA, USUARIO, DATOS)"
					+ " VALUES(?, ?, ?, now(), ?, ?)";				
			Object[] parametros = new Object[] {
				log.getTabla(), log.getOperacion(), log.getIp(), log.getUsuario(), log.getDatos()
			};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.log.spring.LogOperacionDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public List<LogOperacion> lisPorDatos(String matricula, String orden){		
		List<LogOperacion> lista	= new ArrayList<LogOperacion>();		
		try{
			String comando = " SELECT DATOS, IP, OPERACION, TABLA, USUARIO, TO_CHAR(FECHA, 'YYYY/MM/DD') AS FECHA"
					+ " FROM ENOC.LOG_OPERACION "
					+ " WHERE TABLA = 'RES_DATOS' AND DATOS LIKE '%"+matricula+"%' "+orden;		
			lista = enocJdbc.query(comando, new LogOperacionMapper());	
		}catch(Exception ex){
			System.out.println("Error - aca.log.spring.LogOperacion|lisPorDatos|:"+ex);
		}		
		return lista;
	}

}
