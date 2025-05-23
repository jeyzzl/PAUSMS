package aca.nse.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class NsePreguntaDao{
	
	@Autowired		
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;	
	
	public boolean insertReg(NsePregunta objeto){
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.NSE_PREGUNTA (PREGUNTA_ID, ENCUESTA_ID, PREGUNTA) VALUES(?,?,?)";
			Object[] parametros = new Object[] { 
				objeto.getPreguntaId(),objeto.getEncuestaId(),objeto.getPregunta()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.nse.spring.NsePreguntaDao|insertReg|:"+ex);			
	
		}
		return ok;
	}	
	
	public boolean deleteReg(String encuestaId, String preguntaId){
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.NSE_PREGUNTA WHERE ENCUESTA_ID = TO_NUMBER(?,'99') AND PREGUTNA_ID = TO_NUMBER(?,'99')";
 			if (enocJdbc.update(comando, encuestaId)==1){
 				ok = true;
 			}		
		}catch(Exception ex){
			System.out.println("Error - aca.nse.spring.NsePreguntaDao|deleteReg|:"+ex);		
		}
		return ok;
	}
	
	public NsePregunta mapeaRegId(String encuestaId, String preguntaId){
		NsePregunta objeto = new NsePregunta();		
		try{
			String comando = "SELECT PREGUNTA_ID, ENCUESTA_ID, PREGUNTA FROM ENOC.NSE_PREGUNTA WHERE ENCUESTA_ID = TO_NUMBER(?,'99') AND PREGUTNA_ID = TO_NUMBER(?,'99')";
			objeto = enocJdbc.queryForObject(comando, new NsePreguntaMapper(), encuestaId);			
		}catch(Exception ex){
			System.out.println("Error - aca.nse.spring.NsePreguntaDao|mapeaRegId|:"+ex);		
		}
		return objeto;
	}	
	
	public boolean existeRegId(String encuestaId, String preguntaId){
		boolean ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.NSE_PREGUNTA WHERE ENCUESTA_ID = TO_NUMBER(?,'99') AND PREGUTNA_ID = TO_NUMBER(?,'99')";
 			if (enocJdbc.queryForObject(comando, Integer.class, encuestaId) >= 1){
				ok = true;
			} 
		}catch(Exception ex){
			System.out.println("Error - aca.nse.spring.NsePreguntaDao|existeRegId|:"+ex);		
		}
		return ok;
	}
	
	public List<NsePregunta> lista(String encuestaId, String orden ){
		
		List<NsePregunta> lista	= new ArrayList<NsePregunta>();		
		try{
			String comando = "SELECT PREGUNTA_ID, ENCUESTA_ID, PREGUNTA FROM ENOC.NSE_PREGUNTA WHERE ENCUESTA_ID = TO_NUMBER(?,'99')"+ orden;
			lista = enocJdbc.query(comando, new NsePreguntaMapper(),encuestaId);			
		}catch(Exception ex){
			System.out.println("Error - aca.nse.spring.NsePreguntaDao|lista|:"+ex);		
		}
		return lista;
	}	
}