package aca.nse.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class NseRespuestaPreguntaDao{
	
	@Autowired		
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;	
	
	public boolean insertReg(NseRespuestaPregunta objeto){
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.NSE_RESPUESTA_PREGUNTA (RESPUESTA_ID, PREGUNTA_ID, ENCUESTA_ID, RESPUESTA, PUNTOS) VALUES(TO_NUMBER(?,'9'),TO_NUMBER(?,'99'),TO_NUMBER(?,'99'),?,TO_NUMBER(?,'999'))";
			Object[] parametros = new Object[] { 
				objeto.getRespuestaId(),objeto.getPreguntaId(),objeto.getEncuestaId(),objeto.getRespuesta(),objeto.getPuntos()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.nse.spring.NseRespuestaPreguntaDao|insertReg|:"+ex);			
	
		}
		return ok;
	}	
	
	public boolean deleteReg(String respuestaId, String encuestaId, String preguntaId){
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.NSE_RESPUESTA_PREGUNTA WHERE RESPUESTA_ID = TO_NUMBER(?,'9') AND ENCUESTA_ID = TO_NUMBER(?,'99') AND PREGUTNA_ID = TO_NUMBER(?,'99')";
 			if (enocJdbc.update(comando, encuestaId)==1){
 				ok = true;
 			}		
		}catch(Exception ex){
			System.out.println("Error - aca.nse.spring.NseRespuestaPreguntaDao|deleteReg|:"+ex);		
		}
		return ok;
	}
	
	public NseRespuestaPregunta mapeaRegId(String respuestaId, String encuestaId, String preguntaI){
		NseRespuestaPregunta objeto = new NseRespuestaPregunta();		
		try{
			String comando = "SELECT RESPUESTA_ID, PREGUNTA_ID, ENCUESTA_ID, RESPUESTA, PUNTOS FROM ENOC.NSE_RESPUESTA_PREGUNTA WHERE RESPUESTA_ID = TO_NUMBER(?,'9') AND ENCUESTA_ID = TO_NUMBER(?,'99') AND PREGUTNA_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] { 
				objeto.getRespuestaId(),objeto.getEncuestaId(),objeto.getPreguntaId()
			};
			
			objeto = enocJdbc.queryForObject(comando, new NseRespuestaPreguntaMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.nse.spring.NseRespuestaPreguntaDao|mapeaRegId|:"+ex);		
		}
		return objeto;
	}	
	
	public boolean existeRegId(String respuestaId, String encuestaId, String preguntaI){
		boolean ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.NSE_RESPUESTA_PREGUNTA WHERE WHERE RESPUESTA_ID = TO_NUMBER(?,'9') AND ENCUESTA_ID = TO_NUMBER(?,'99') AND PREGUTNA_ID = TO_NUMBER(?,'99')";
 			if (enocJdbc.queryForObject(comando, Integer.class, encuestaId) >= 1){
				ok = true;
			} 
		}catch(Exception ex){
			System.out.println("Error - aca.nse.spring.NseRespuestaPreguntaDao|existeRegId|:"+ex);		
		}
		return ok;
	}
	
	public boolean existeEncuestaRegId(String encuestaId){
		boolean ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.NSE_RESPUESTA_PREGUNTA WHERE ENCUESTA_ID = TO_NUMBER(?,'99')";
			if (enocJdbc.queryForObject(comando, Integer.class, encuestaId) >= 1){
				ok = true;
			} 
		}catch(Exception ex){
			System.out.println("Error - aca.nse.spring.NseRespuestaPreguntaDao|existeEncuestaRegId|:"+ex);		
		}
		return ok;
	}
	
	public List<NseRespuestaPregunta> lista(String encuestaId, String orden ){
		
		List<NseRespuestaPregunta> lista	= new ArrayList<NseRespuestaPregunta>();		
		try{
			String comando = "SELECT RESPUESTA_ID, PREGUNTA_ID, ENCUESTA_ID, RESPUESTA, PUNTOS FROM ENOC.NSE_RESPUESTA_PREGUNTA WHERE ENCUESTA_ID = TO_NUMBER(?,'99')"+ orden;
			lista = enocJdbc.query(comando, new NseRespuestaPreguntaMapper(),encuestaId);			
		}catch(Exception ex){
			System.out.println("Error - aca.nse.spring.NseRespuestaPreguntaDao|lista|:"+ex);		
		}
		return lista;
	}	
	
	public HashMap<String,List<NseRespuestaPregunta>> mapaRespuestas(String encuestaId){
		HashMap<String,List<NseRespuestaPregunta>> mapa 	= new HashMap<String,List<NseRespuestaPregunta>>();
		List<NseRespuestaPregunta> lista			= new ArrayList<NseRespuestaPregunta>();		
		List<String> listaPreguntaId				= new ArrayList<String>();		
		try{
			String comando = "SELECT PREGUNTA_ID FROM ENOC.NSE_PREGUNTA WHERE ENCUESTA_ID = TO_NUMBER(?,'99') ORDER BY PREGUNTA_ID";
			listaPreguntaId = enocJdbc.queryForList(comando, String.class,encuestaId);	
			for(String preguntaId : listaPreguntaId) {
				comando = "SELECT RESPUESTA_ID, PREGUNTA_ID, ENCUESTA_ID, RESPUESTA, PUNTOS FROM ENOC.NSE_RESPUESTA_PREGUNTA WHERE ENCUESTA_ID = TO_NUMBER(?,'99') AND PREGUNTA_ID = TO_NUMBER(?,'99')";
				lista = enocJdbc.query(comando, new NseRespuestaPreguntaMapper(),encuestaId,preguntaId);	
				mapa.put(preguntaId, lista);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.nse.spring.NseRespuestaPreguntaDao|mapaRespuestas|:"+ex);		
		}
		return mapa;
	}	
}