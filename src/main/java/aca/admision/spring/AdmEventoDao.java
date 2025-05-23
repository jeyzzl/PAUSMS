package aca.admision.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AdmEventoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( AdmEvento objeto){
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO SALOMON.ADM_EVENTO(EVENTO_ID, ESTADO, EVENTO_NOMBRE, LUGAR, ORDEN, FECHA)"
					+ "VALUES(TO_NUMBER(?,'99999'),?,?, ?,TO_NUMBER(?,'999.99'),TO_DATE(?,'YYYY/MM/DD'))";
			Object[] parametros = new Object[] {objeto.getEventoId(), objeto.getEstado(),objeto.getEventoNombre(), objeto.getLugar(), objeto.getOrden(), objeto.getFecha()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmEventoDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg( AdmEvento objeto ) {
		boolean ok = false;		
		try{
			String comando = "UPDATE SALOMON.ADM_EVENTO SET EVENTO_NOMBRE = ?, ESTADO = ?, LUGAR = ?, ORDEN = TO_NUMBER(?,'999.99'), FECHA = TO_DATE(?,'YYYY/MM/DD') WHERE EVENTO_ID = TO_NUMBER(?,'99999') ";
			Object[] parametros = new Object[] {objeto.getEventoNombre(), objeto.getEstado(),objeto.getLugar(), objeto.getOrden(), objeto.getFecha(), objeto.getEventoId()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmEventoDao|updateReg|:"+ex);		
		}
		
		return ok;
	}	
	
	public boolean updateEstado( String eventoId, String estado ) {
		boolean ok = false;		
		try{
			String comando = "UPDATE SALOMON.ADM_EVENTO SET ESTADO = ? WHERE EVENTO_ID = TO_NUMBER(?,'99999')";
			Object[] parametros = new Object[] {estado, eventoId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmEventoDao|updateReg|:"+ex);		
		}
		
		return ok;
	}
	
	public boolean deleteReg( String eventoId) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM SALOMON.ADM_EVENTO WHERE EVENTO_ID = TO_NUMBER(?,'99999')";
			Object[] parametros = new Object[] {eventoId};
			if (enocJdbc.update(comando,parametros)==1){			
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmEventoDao|deleteReg|:"+ex);			
		}		
		return ok;
	}
	
	public AdmEvento mapeaRegId( String eventoId) {
		AdmEvento objeto = new AdmEvento();		
		try{
			String comando = "SELECT EVENTO_ID, ESTADO, EVENTO_NOMBRE, LUGAR, ORDEN, FECHA FROM SALOMON.ADM_EVENTO WHERE EVENTO_ID = TO_NUMBER(?,'99999')";
			Object[] parametros = new Object[] { eventoId };
			objeto = enocJdbc.queryForObject(comando, new AdmEventoMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmEventoDao|mapeaRegId|:"+ex);
		}		
		return objeto;
	}
	
	public boolean existeReg( String eventoId ) {
		boolean 		ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM SALOMON.ADM_EVENTO WHERE EVENTO_ID = TO_NUMBER(?,'99999')"; 
			Object[] parametros = new Object[] {eventoId};	
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){	
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmEventoDao|existeReg|:"+ex);
		}		
		return ok;
	}
	
	public String maximoReg() {
 		String maximo = "1"; 		
 		try{
 			String comando = "SELECT MAX(EVENTO_ID )+1 AS MAXIMO FROM SALOMON.ADM_EVENTO";
 			
 			if (enocJdbc.queryForObject(comando, Integer.class) >= 1) {
 				maximo = enocJdbc.queryForObject(comando, String.class);
			} 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.admision.spring.AdmEventoDao|maximoReg|:"+ex);
 		}
 		return maximo;
 	}
	
	public String getEventoNombre( String eventoId ) {
		String nombre = "-";	
		try{
			String comando = "SELECT COALESCE(EVENTO_NOMBRE,'-') FROM SALOMON.ADM_EVENTO WHERE EVENTO_ID = TO_NUMBER(?,'99999')"; 
			Object[] parametros = new Object[] {eventoId};	
			nombre = enocJdbc.queryForObject(comando,String.class, parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmEventoDao|getEventoNombre|:"+ex);
		}		
		return nombre;
	}
	
	public String getEstado( String eventoId ) {
		String estado = "A";		
		try{
			String comando = "SELECT COALESCE(ESTADO,'-') FROM SALOMON.ADM_EVENTO WHERE EVENTO_ID = TO_NUMBER(?,'99999')"; 
			Object[] parametros = new Object[] {eventoId};	
			estado = enocJdbc.queryForObject(comando,String.class, parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmEventoDao|getEstado|:"+ex);
		}
		
		return estado;
	}
	
	public List<AdmEvento> getListAll (){
		List<AdmEvento> lista = new ArrayList<AdmEvento>();		
		try {
			String comando = "SELECT EVENTO_ID, ESTADO, EVENTO_NOMBRE, LUGAR, ORDEN, TO_CHAR(FECHA,'YYYY/MM/DD') AS FECHA FROM SALOMON.ADM_EVENTO";			
			lista = enocJdbc.query(comando, new AdmEventoMapper());
		}catch(Exception ex) {
			System.out.println("Error - aca.admision.spring.AdmEventoDao|getListAll|:"+ex);
		}
		return lista;
	}	
	
	public List<AdmEvento> lisEstados (String estados, String orden){
		List<AdmEvento> lista = new ArrayList<AdmEvento>();		
		try {
			String comando = "SELECT EVENTO_ID, ESTADO, EVENTO_NOMBRE, LUGAR, ORDEN, TO_CHAR(FECHA,'YYYY/MM/DD') AS FECHA"
					+ " FROM SALOMON.ADM_EVENTO WHERE ESTADO IN ("+estados+") "+orden;			
			lista = enocJdbc.query(comando, new AdmEventoMapper());
		}catch(Exception ex) {
			System.out.println("Error - aca.admision.spring.AdmEventoDao|lisEstados|:"+ex);
		}
		return lista;
	}
}
