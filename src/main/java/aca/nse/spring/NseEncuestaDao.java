package aca.nse.spring;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class NseEncuestaDao{
	
	@Autowired		
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;	
	
	public boolean insertReg(NseEncuesta objeto){
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.NSE_ENCUESTA (ENCUESTA_ID, ENCUESTA_NOMBRE, FECHA_INI, FECHA_FIN) VALUES(TO_NUMBER(?,'99'), ?, TO_DATE(?,'DD/MM/YYYY HH:MI:SS'), TO_DATE(?,'DD/MM/YYYY HH:MI:SS'))";
			Object[] parametros = new Object[] { 
				objeto.getEncuestaId(),objeto.getEncuestaNombre(),objeto.getFechaIni(),objeto.getFechaFin()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.nse.spring.NseEncuestaDao|insertReg|:"+ex);			
	
		}
		return ok;
	}	
	
	public boolean deleteReg(String encuestaId){
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.NSE_ENCUESTA WHERE ENCUESTA_ID = TO_NUMBER(?,'99')";
 			if (enocJdbc.update(comando, encuestaId)==1){
 				ok = true;
 			}		
		}catch(Exception ex){
			System.out.println("Error - aca.nse.spring.NseEncuestaDao|deleteReg|:"+ex);		
		}
		return ok;
	}
	
	public NseEncuesta mapeaRegId(String  encuestaId){
		NseEncuesta objeto = new NseEncuesta();		
		try{
			String comando = "SELECT ENCUESTA_ID, ENCUESTA_NOMBRE, FECHA_INI, FECHA_FIN FROM ENOC.NSE_ENCUESTA WHERE ENCUESTA_ID = TO_NUMBER(?,'99')";
			objeto = enocJdbc.queryForObject(comando, new NseEncuestaMapper(), encuestaId);			
		}catch(Exception ex){
			System.out.println("Error - aca.nse.spring.NseEncuestaDao|mapeaRegId|:"+ex);		
		}
		return objeto;
	}	
	
	public int getEncuestaActiva(){
		int encuestaId = 0;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.NSE_ENCUESTA WHERE SYSDATE BETWEEN FECHA_INI AND FECHA_FIN";
			if (enocJdbc.queryForObject(comando, Integer.class) >= 1){
				comando = "SELECT ENCUESTA_ID FROM ENOC.NSE_ENCUESTA WHERE SYSDATE BETWEEN FECHA_INI AND FECHA_FIN";
				encuestaId = enocJdbc.queryForObject(comando, Integer.class);
			}					
		}catch(Exception ex){
			System.out.println("Error - aca.nse.spring.NseEncuestaDao|getEncuestaActiva|:"+ex);
		}
		return encuestaId;
	}
	
	public NseEncuesta encuestaActiva(){
		NseEncuesta objeto = new NseEncuesta();		
		try{
			String comando = "SELECT ENCUESTA_ID, ENCUESTA_NOMBRE, FECHA_INI, FECHA_FIN FROM ENOC.NSE_ENCUESTA WHERE SYSDATE BETWEEN FECHA_INI AND FECHA_FIN";
			objeto = enocJdbc.queryForObject(comando, new NseEncuestaMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.nse.spring.NseEncuestaDao|encuestaActiva|:"+ex);		
		}
		return objeto;
	}	
	
	public boolean existeRegId(String encuestaId){
		boolean 			ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.NSE_ENCUESTA WHERE ENCUESTA_ID = TO_NUMBER(?,'99')";
 			if (enocJdbc.queryForObject(comando, Integer.class, encuestaId) >= 1){
				ok = true;
			} 
		}catch(Exception ex){
			System.out.println("Error - aca.nse.spring.NseEncuestaDao|existeRegId|:"+ex);		
		}
		return ok;
	}
	
	public String maximoReg() {
		String maximo = "1";		
		
		try{
			String comando = "SELECT COALESCE(MAX(ENCUESTA_ID)+1,1) AS MAXIMO FROM ENOC.NSE_ENCUESTA";
			if(enocJdbc.queryForObject(comando,Integer.class) >= 1){
				maximo = String.valueOf(enocJdbc.queryForObject(comando,String.class));
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.nse.spring.NseEncuestaDao|maximoReg|:"+ex);	
		}
		
		return maximo;
	}
	
	public List<NseEncuesta> lista(String orden ){
		
		List<NseEncuesta> lista	= new ArrayList<NseEncuesta>();		
		try{
			String comando = "SELECT ENCUESTA_ID, ENCUESTA_NOMBRE, TO_CHAR (FECHA_INI, 'DD/MM/YYYY HH:MM:SS') AS FECHA_INI, TO_CHAR (FECHA_FIN, 'DD/MM/YYYY HH:MM:SS') AS FECHA_FIN FROM ENOC.NSE_ENCUESTA "+ orden;
			lista = enocJdbc.query(comando, new NseEncuestaMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.nse.spring.NseEncuestaDao|lista|:"+ex);		
		}
		return lista;
	}	
}