package aca.opcion.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class OpcionDao {
	
	@Autowired	
	@Qualifier("jdbcEnoc")	
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(Opcion opcion){
		boolean ok = false;
		try{
			String comando = "INSERT INTO ENOC.OPCION(CODIGO_PERSONAL, ALERTA_SESION, MENU_CLICK) VALUES(?, ?, ?)";
			Object[] parametros = new Object[] {
				opcion.getCodigoPersonal(),opcion.getAlertaSesion(),opcion.getMenuClick()
			};			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.Opcion|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg(Opcion opcion){
		boolean ok = false;
		try{
			String comando = "UPDATE ENOC.OPCION" + 
				" SET ALERTA_SESION = ?," +
				" MENU_CLICK = ?" +
				" WHERE CODIGO_PERSONAL = ?";			
			Object[] parametros = new Object[] {
				opcion.getAlertaSesion(),opcion.getMenuClick(),opcion.getCodigoPersonal()
			};			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.Opcion|updateReg|:"+ex);		 
		}
		
		return ok;
	}	
	
	public boolean deleteReg(String codigoPersonal){
		boolean ok = false;
		try{
			String comando = "DELETE FROM ENOC.OPCION WHERE CODIGO_PERSONAL = ?";			
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.Opcion|deleteReg|:"+ex);			
		}

		return ok;
	}
	
	public Opcion mapeaRegId(String codigoPersonal){
		Opcion objeto = new Opcion();		
		try{			
			String query = "SELECT COUNT(*) FROM ENOC.OPCION WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[]{codigoPersonal};
			if (enocJdbc.queryForObject(query, Integer.class, parametros) >= 1) {
				query = "SELECT CODIGO_PERSONAL, ALERTA_SESION, MENU_CLICK FROM ENOC.OPCION WHERE CODIGO_PERSONAL = ?";	
				objeto = enocJdbc.queryForObject(query, new OpcionMapper(), parametros);
			}			
		}catch( Exception ex){
			System.out.println("Error: aca.opcion"+ex);
		}
		
		return objeto;
	}
	
	/* Verifica si existe */
	public boolean existeReg(String codigoPersonal){
		boolean ok = false;
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.OPCION WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[]{codigoPersonal};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				ok = true;
			}
		}catch( Exception ex){
			System.out.println("Error: aca.opcion.spring.OpcionDao||existeReg :"+ex);
		}
		
		return ok;		
	}
	
	public String getAlertaSesion(String codigoPersonal){
		String alerta = "N";
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.OPCION WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[]{codigoPersonal};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				comando = "SELECT COALESCE(ALERTA_SESION,'N') FROM ENOC.OPCION WHERE CODIGO_PERSONAL = ?";
				alerta 	= enocJdbc.queryForObject(comando,String.class,parametros); 
			}
		}catch( Exception ex){
			System.out.println("Error: aca.opcion.spring.OpcionDao||tieneAlertaSesion :"+ex);
		}		
		return alerta;
	}
	
}
