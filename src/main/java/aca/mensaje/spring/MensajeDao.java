/**
 * 
 */
package aca.mensaje.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * @author Etorres
 *
 */
@Component
public class MensajeDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( Mensaje mensaje) {
		
 		boolean ok 				= false; 		
 		try{
 			String comando = "INSERT INTO ENOC.MENSAJE(CODIGO_PERSONAL, MENSAJE1, MENSAJE2) VALUES(?,?,?)";
 			Object[] parametros = new Object[] {mensaje.getCodigoPersonal(), mensaje.getMensaje1(), mensaje.getMensaje2()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			} 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.mensaje.spring.MensajeDao|insertReg|:"+ex);			
 		}
 		
 		return ok;
 	}
	
	public boolean updateReg( Mensaje mensaje ) {
		boolean ok = true;
		
		try{
			String comando = "UPDATE ENOC.MENSAJE SET MENSAJE1 = ?, MENSAJE2 = ? WHERE CODIGO_PERSONAL = ? ";
			Object[] parametros = new Object[] {mensaje.getMensaje1(), mensaje.getMensaje2(), mensaje.getCodigoPersonal()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch (Exception ex){
			System.out.println("Error - aca.mensaje.spring.MensajeDao|updateReg|:"+ex);
		}
		return ok;
	}
	
	public boolean deleteReg(String codigoPersonal) {
		
		boolean ok 				= false;
		try{
			String comando = "DELETE FROM ENOC.MENSAJE WHERE CODIGO_PERSONAL = ? ";
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.mensaje.spring.MensajeDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public Mensaje mapeaRegId( String codigoPersonal){
		Mensaje mensaje = new Mensaje();		
		try{
			String comando = "SELECT COUNT(CODIGO_PERSONAL) FROM ENOC.MENSAJE WHERE CODIGO_PERSONAL = ?";
			if (enocJdbc.queryForObject(comando, Integer.class, codigoPersonal)>=1){
				comando = "SELECT CODIGO_PERSONAL, MENSAJE1, MENSAJE2 FROM ENOC.MENSAJE WHERE CODIGO_PERSONAL = ?";
				mensaje = enocJdbc.queryForObject(comando, new MensajeMapper(), codigoPersonal);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.mensaje.spring.MensajeDao|mapeaRegId|:"+ex);
		}
		return mensaje;
	}
 	
 	public boolean existeReg(String codigoPersonal) {
 		
 		boolean 		ok 		= false;
 		
 		try{
 			String comando = "SELECT COUNT(CODIGO_PERSONAL) FROM ENOC.MENSAJE WHERE CODIGO_PERSONAL = ? ";
 			Object[] parametros = new Object[] {codigoPersonal}; 
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros)>0){
 				ok = true;
 			}
 		}catch(Exception ex){
 			System.out.println("Error - aca.mensaje.spring.MensajeDao|existeReg|:"+ex);
 		}
 		
 		return ok;
 	} 	
}