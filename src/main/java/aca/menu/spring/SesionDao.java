/**
 * 
 */
package aca.menu.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * @author Etorres
 *
 */

@Component
public class SesionDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( Sesion sesion ){
		
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.MODULO_SESION"+ 
				"(SESION_ID, CODIGO_PERSONAL, F_INICIO, F_FINAL, IP, FINALIZO ) "+
				"VALUES( ?, ?, "+
				"now(), "+
				"now(), "+
				" ?, 'N' )";
			Object[] parametros = new Object[] {sesion.getSesionId(), sesion.getCodigoPersonal(), sesion.getIp()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.menu.spring.Sesion|insertReg|:"+ex);			
		}
		
		return ok;
	}		
	
	public boolean update( String sesionId ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.MODULO_SESION SET F_FINAL = now() WHERE SESION_ID = ?";
			Object[] parametros = new Object[] {sesionId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.menu.spring.Sesion|updateReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean updateReg( String sesionId) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.MODULO_SESION SET F_FINAL = now() WHERE SESION_ID = ? ";				
			Object[] parametros = new Object[] {sesionId};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.menu.spring.Sesion|updateReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean updateRegFinalizo( String sesionId ) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.MODULO_SESION SET FINALIZO = 'S' WHERE SESION_ID = ? ";
			Object[] parametros = new Object[] {sesionId};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.menu.spring.Sesion|updateRegFinalizo|:"+ex);		
		}
		
		return ok;
	}
	
	public boolean deleteReg( String sesionId ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.MODULO_SESION WHERE SESION_ID = ?";
			Object[] parametros = new Object[] {sesionId};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.menu.spring.Sesion|deleteReg|:"+ex);			
		}
		
		return ok;
	}
	
	public Sesion mapeaRegId( String sesionId ) {
		Sesion sesion = new Sesion();	
		try{ 
			String comando = "SELECT SESION_ID, CODIGO_PERSONAL, F_INICIO, F_FINAL, IP, FINALIZO"
					+ " FROM ENOC.MODULO_SESION WHERE SESION_ID = ? "; 
			Object[] parametros = new Object[] {sesionId};
			sesion = enocJdbc.queryForObject(comando, new SesionMapper(), parametros);		
			
		}catch(Exception ex){
			System.out.println("Error - aca.menu.spring.Sesion|mapeaRegId|:"+ex);
		}
		
		return sesion;
	}
	
	public boolean existeReg(String sesionId) {
		boolean 		ok 	= false;				
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.MODULO_SESION WHERE SESION_ID = ?";
			Object[] parametros = new Object[] {sesionId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.menu.spring.SesionDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String validaSesionUnica(String sesionId, String codigoPersonal, String ipCliente) {		
		List<Sesion> lista		= new ArrayList<Sesion>();
		
		String respuesta = "milisegundosEspera = 0.5 * 60 * 1000; muestraMensajeSesion('";
		boolean entro = false;	
		
		try{
			String comando = "SELECT SESION_ID, CODIGO_PERSONAL, TO_CHAR(F_INICIO,'YYYY/MM/DD HH24:MI:SS') AS F_INICIO, TO_CHAR(F_FINAL,'YYYY/MM/DD HH24:MI:SS') AS F_FINAL, IP, FINALIZO FROM ENOC.MODULO_SESION" + 
					" WHERE SESION_ID != ?" +
					" AND CODIGO_PERSONAL = ?" +					
					" AND (TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'),'DD/MM/YYYY')-F_FINAL)*24*60 <= 1" +
					" AND FINALIZO = 'N'" +
					" ORDER BY F_FINAL DESC";
			Object[] parametros = new Object[] {sesionId, codigoPersonal};
			lista = enocJdbc.query(comando, new SesionMapper(), parametros);	
			
			for( Sesion sesion : lista){
				String ip 			= sesion.getIp();
				String fechaInicio 	= sesion.getFInicio();  
				if((ip.trim().substring(0, 3).equals("172") || ip.trim().substring(0, 3).equals("10.")) && !ip.trim().equals(ipCliente.trim())){
					respuesta += "Sesiones registradas con tu usuario: "+ip+"-"+fechaInicio+"<br/>";
					entro = true;
				}
			}
			respuesta += "<br><b>Si no reconoces estos accesos te recomendamos que cambies tu contrase&ntilde;a...</b><br/>"+
						 "O puedes <a href=\"#\" onclick=\"expulsaOtrasSesiones();\">expulsar las otras sesiones</a>');";
			if(!entro)
				respuesta = "milisegundosEspera = 2 * 60 * 1000; ocultaMensajeSesion();";
			comando = "SELECT SESION_ID, CODIGO_PERSONAL, F_INICIO, F_FINAL, IP, FINALIZO FROM ENOC.MODULO_SESION" + 
					" WHERE SESION_ID = ?" +
					" AND CODIGO_PERSONAL = ?" +
					" ORDER BY F_FINAL DESC";
			parametros = new Object[] {sesionId, codigoPersonal};
			lista = enocJdbc.query(comando, new SesionMapper(), parametros);
			for( Sesion sesion : lista){
				if(sesion.getFinalizo().equals("E")){
					respuesta += "alert('Ah sido expulsado de la sesion');";
					respuesta += "document.location = 'salir';";
				}
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.menu.spring.SesionDao|validaSesionUnica|:"+ex);
		}
		
		return respuesta;
	}
	
	public String expulsaOtrasSesiones( String sesionId, String codigoPersonal) {
		String respuesta		= "";		
		try{
			String comando = "UPDATE ENOC.MODULO_SESION" + 
					" SET FINALIZO = ?" +
					" WHERE CODIGO_PERSONAL = ?" +
					" AND SESION_ID != ?" +
					" AND (TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'),'DD/MM/YYYY')-F_FINAL)*24*60 <= 2" +
					" AND FINALIZO = 'N'";
			Object[] parametros = new Object[] {"E", codigoPersonal, sesionId};		
			if (enocJdbc.update(comando,parametros)==1){			
				respuesta += "alert('La orden de expulsion ha sido enviada. En 2 minutos o menos el mensaje de otras sesiones debe DESAPARECER');";
			}else{
				respuesta += "alert('Ocurrio un ERROR. Intentelo de nuevo.\nSi el error persiste, contacte a sistemas')";
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.menu.spring.SesionDao|expulsaOtrasSesiones|:"+ex);
		}
		
		return respuesta;
	}
	
	public List<Sesion> getListUsuarioSemana( String codigoPersonal, String orden ) {
		
		List<Sesion> lista	= new ArrayList<Sesion>();
		try{
			String comando = " SELECT SESION_ID, CODIGO_PERSONAL,"
					+ " TO_CHAR(F_INICIO, 'DD/MM/YYYY HH:MI:SS AM') AS F_INICIO,"
					+ " TO_CHAR(F_FINAL, 'DD/MM/YYYY HH:MI:SS AM') AS F_FINAL, IP, FINALIZO"
					+ " FROM ENOC.MODULO_SESION A"
					+ " WHERE A.CODIGO_PERSONAL = ?"
					+ " AND A.F_INICIO > FECHA_DIAS(TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'),'DD/MM/YYYY'),'RESTA',7) "+ orden;
			lista = enocJdbc.query(comando, new SesionMapper(), codigoPersonal);			
		}catch(Exception ex){
			System.out.println("Error - aca.menu.spring.SesionDao|getListUsuarioSemana|:"+ex);
		}
		
		return lista;
	}
}