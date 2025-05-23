package aca.mentores.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class MentRespuestaDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(MentRespuesta respuesta){
		boolean ok = true;
	
		try{
			String comando = "INSERT INTO ENOC.MENT_RESPUESTA(CODIGO_PERSONAL,PREGUNTA_ID,IMPORTANCIA,SATISFACCION,ENCUESTA_ID) VALUES(?,TO_NUMBER(?,'9999999'), TO_NUMBER(?,'99'), TO_NUMBER(?,'99'), TO_NUMBER(?,'9999'))";
			Object[] parametros = new Object[] {respuesta.getCodigoPersonal(),respuesta.getPreguntaId(),respuesta.getImportancia(), respuesta.getSatisfaccion(), respuesta.getEncuestaId()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch (Exception ex){
			System.out.println("Error - aca.mentores.spring.MentRespuestaDao|insertReg|:"+ex);
		
		}
		return ok;
	}
		
	public MentRespuesta mapeaRegId(String codigoPersonal, String preguntaId){
		MentRespuesta  respuesta = new MentRespuesta();		
		try{ 
			String comando 	= "SELECT COUNT(*) FROM ENOC.MENT_RESPUESTA WHERE CODIGO_PERSONAL = ? AND PREGUNTA_ID = TO_NUMBER(?,'9999999')"; 
			Object[] parametros = new Object[] {codigoPersonal,preguntaId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				comando 	= "SELECT CODIGO_PERSONAL, PREGUNTA_ID, IMPORTANCIA, SATISFACCION,ENCUESTA_ID FROM ENOC.MENT_RESPUESTA WHERE CODIGO_PERSONAL = ? AND PREGUNTA_ID = TO_NUMBER(?,'9999999')";
				respuesta 	= enocJdbc.queryForObject(comando, new MentRespuestaMapper(), parametros);
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentRespuestaDao|mapeaRegId|:"+ex);
		}
		
		return respuesta;
	}
	
	public List<MentRespuesta> getListAll( String orden ){
		
		List<MentRespuesta> lista		= new ArrayList<MentRespuesta>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, PREGUNTA_ID, IMPORTANCIA, SATISFACCION, ENCUESTA_ID FROM ENOC.MENT_RESPUESTA "+ orden; 
			lista = enocJdbc.query(comando, new MentRespuestaMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentRespuestaDao|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	public boolean contestoEncuesta(String codigoPersonal, String encuestaId){
		boolean ok = false;
	
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.MENT_RESPUESTA WHERE CODIGO_PERSONAL = ? AND ENCUESTA_ID = TO_NUMBER(?,'9999')";
			Object[] parametros = new Object[] {codigoPersonal,encuestaId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				ok = true;
			}	
			
		}catch (Exception ex){
			System.out.println("Error - aca.mentores.spring.MentRespuestaDao|contestoEncuesta|:"+ex);
		
		}
		return ok;
	}
}