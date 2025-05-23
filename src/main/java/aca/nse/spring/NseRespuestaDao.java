package aca.nse.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class NseRespuestaDao{
	
	@Autowired		
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;	
	
	public boolean insertReg(NseRespuesta objeto){
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.NSE_RESPUESTA (PREGUNTA_ID, CODIGO_PERSONAL, RESPUESTA, PUNTOS, ENCUESTA_ID) VALUES(TO_NUMBER(?,'99'),?,?,?,TO_NUMBER(?,'99'))";
			Object[] parametros = new Object[] { 
				objeto.getPreguntaId(),objeto.getCodigoPersonal(),objeto.getRespuesta(),objeto.getPuntos(),objeto.getEncuestaId()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.nse.spring.NseRespuestaDao|insertReg|:"+ex);			
	
		}
		return ok;
	}
	
	public boolean updateReg(NseRespuesta objeto){
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.NSE_RESPUESTA SET RESPUESTA = ?, PUNTOS = ? WHERE ENCUESTA_ID = TO_NUMBER(?,'99') AND CODIGO_PERSONAL = ? AND PREGUNTA_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] { 
				objeto.getRespuesta(),objeto.getPuntos(), objeto.getEncuestaId(), objeto.getCodigoPersonal(), objeto.getPreguntaId()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.nse.spring.NseRespuestaDao|updateReg|:"+ex);	
	
		}
		return ok;
	}	
	
	public boolean deleteReg(String preguntaId, String codigoPersonal, String encuestaId){
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.NSE_RESPUESTA WHERE CODIGO_PERSONAL = ? AND PREGUNTA_ID = TO_NUMBER(?,'99') AND ENCUESTA_ID = TO_NUMBER(?,'99')";
 			if (enocJdbc.update(comando, preguntaId)==1){
 				ok = true;
 			}		
		}catch(Exception ex){
			System.out.println("Error - aca.nse.spring.NseRespuestaDao|deleteReg|:"+ex);		
		}
		return ok;
	}
	
	public NseRespuesta mapeaRegId(String preguntaId, String codigoPersonal, String encuestaId){
		NseRespuesta objeto = new NseRespuesta();		
		try{
			String comando = "SELECT RESPUESTA_ID, CODIGO_PERSONAL, RESPUESTA FROM ENOC.NSE_RESPUESTA WHERE CODIGO_PERSONAL = ? AND PREGUNTA_ID = TO_NUMBER(?,'99') AND ENCUESTA_ID = TO_NUMBER(?,'99')";
			objeto = enocJdbc.queryForObject(comando, new NseRespuestaMapper(), preguntaId);			
		}catch(Exception ex){
			System.out.println("Error - aca.nse.spring.NseRespuestaDao|mapeaRegId|:"+ex);		
		}
		return objeto;
	}	
	
	public boolean existeRegId(String encuestaId, String preguntaId, String codigoPersonal ){
		boolean ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.NSE_RESPUESTA WHERE ENCUESTA_ID = TO_NUMBER(?,'99') AND CODIGO_PERSONAL = ? AND PREGUNTA_ID = TO_NUMBER(?,'99')";
 			if (enocJdbc.queryForObject(comando, Integer.class, encuestaId, codigoPersonal, preguntaId) >= 1){
				ok = true;
			} 
		}catch(Exception ex){
			System.out.println("Error - aca.nse.spring.NseRespuestaDao|existeRegId|:"+ex);		
		}
		return ok;
	}
	
	public boolean tieneRespuestas(String codigoPersonal, String encuestaId){
		boolean ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.NSE_RESPUESTA WHERE CODIGO_PERSONAL = ? AND ENCUESTA_ID = TO_NUMBER(?,'99')";
			if (enocJdbc.queryForObject(comando, Integer.class, codigoPersonal,encuestaId) >= 1){
				ok = true;
			} 
		}catch(Exception ex){
			System.out.println("Error - aca.nse.spring.NseRespuestaDao|tieneRespuestas|:"+ex);		
		}
		return ok;
	}
	
	public int getNumRespuestas(String codigoPersonal, String encuestaId){
		int numero = 0;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.NSE_RESPUESTA WHERE CODIGO_PERSONAL = ? AND ENCUESTA_ID = TO_NUMBER(?,'99')";
			if (enocJdbc.queryForObject(comando, Integer.class, codigoPersonal,encuestaId) >= 1){
				numero = enocJdbc.queryForObject(comando, Integer.class, codigoPersonal,encuestaId);
			} 
		}catch(Exception ex){
			System.out.println("Error - aca.nse.spring.NseRespuestaDao|getNumRespuestas|:"+ex);
		}
		return numero;
	}
	
	public boolean encuestaPendiente(String codigoPersonal){
		boolean ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.NSE_RESPUESTA WHERE CODIGO_PERSONAL = ? AND"
					+ " ENCUESTA_ID IN (SELECT ENCUESTA_ID FROM ENOC.NSE_ENCUESTA WHERE SYSDATE BETWEEN FECHA_INI AND FECHA_FIN)";
			if (enocJdbc.queryForObject(comando, Integer.class, codigoPersonal) >= 1){
				ok = true;
			} 
		}catch(Exception ex){
			System.out.println("Error - aca.nse.spring.NseRespuestaDao|encuestaPendiente|:"+ex);		
		}
		return ok;
	}
	
	public List<NseRespuesta> lista(String orden ){
		
		List<NseRespuesta> lista	= new ArrayList<NseRespuesta>();		
		try{
			String comando = "SELECT RESPUESTA_ID, CODIGO_PERSONAL, RESPUESTA, PUNTOS FROM ENOC.NSE_RESPUESTA "+ orden;
			lista = enocJdbc.query(comando, new NseRespuestaMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.nse.spring.NseRespuestaDao|lista|:"+ex);		
		}
		return lista;
	}	
	
	public HashMap<String,String> mapaRespuestas(String codigoPersonal, String encuestaId){
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<NseRespuesta> lista	= new ArrayList<NseRespuesta>();		
		try{
			String comando = "SELECT PREGUNTA_ID, CODIGO_PERSONAL, RESPUESTA, PUNTOS,ENCUESTA_ID FROM ENOC.NSE_RESPUESTA WHERE CODIGO_PERSONAL = ? AND ENCUESTA_ID = TO_NUMBER(?,'99')";
			lista = enocJdbc.query(comando, new NseRespuestaMapper(),codigoPersonal,encuestaId);		
			for(NseRespuesta respuesta : lista) {
				mapa.put(respuesta.getPreguntaId(), respuesta.getRespuesta());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.nse.spring.NseRespuestaDao|mapaRespuestas|:"+ex);		
		}
		return mapa;
	}	
}