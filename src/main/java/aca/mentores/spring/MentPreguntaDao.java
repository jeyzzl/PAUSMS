package aca.mentores.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class MentPreguntaDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(MentPregunta pregunta){
		boolean ok = true;
	
		try{
			String comando = "INSERT INTO ENOC.MENT_PREGUNTA(PREGUNTA_ID,NOMBRE,ENCUESTA_ID) VALUES(TO_NUMBER(?,'99'), ?, TO_NUMBER(?,'99'))";
			Object[] parametros = new Object[] {pregunta.getPreguntaId(),pregunta.getNombre(), pregunta.getEncuestaId()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch (Exception ex){
			System.out.println("Error - aca.mentores.spring.MentPreguntaDao|insertReg|:"+ex);
		
		}
		return ok;
	}
		
	public boolean updateReg(MentPregunta pregunta){
		boolean ok = true;
		
		try{
			String comando = "UPDATE ENOC.MENT_PREGUNTA "+ 
					"SET NOMBRE = ? WHERE PREGUNTA_ID = ? ";
			Object[] parametros = new Object[] {pregunta.getNombre(),pregunta.getPreguntaId()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch (Exception ex){
			System.out.println("Error - aca.mentores.spring.MentPreguntaDao|updateReg|:"+ex);
		}
		
		return ok;
	}
		
	public boolean deleteReg(String preguntaId){
		boolean ok = true;
		
		try{
			String comando = "DELETE FROM ENOC.MENT_PREGUNTA WHERE PREGUNTA_ID = ?";
			Object[] parametros = new Object[] {preguntaId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch (Exception ex){
			System.out.println("Error - aca.mentores.spring.MentPreguntaDao|deleteReg|:"+ex);
		
		}
		return ok;
	}
	
	public MentPregunta mapeaRegId(String preguntaId){
		MentPregunta  pregunta = new MentPregunta();		
		try{ 
			String comando 	= "SELECT COUNT(*) FROM ENOC.MENT_PREGUNTA WHERE PREGUNTA_ID = ?"; 
			Object[] parametros = new Object[] {preguntaId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				comando 	= "SELECT PREGUNTA_ID, NOMBRE FROM ENOC.MENT_PREGUNTA WHERE PREGUNTA_ID = ?";
				pregunta 		= enocJdbc.queryForObject(comando, new MentPreguntaMapper(), parametros);
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentPreguntaDao|mapeaRegId|:"+ex);
		}
		
		return pregunta;
	}
	
	public boolean existeReg(String preguntaId){
		boolean 		ok 		= false;
		
		try{
			String comando = "SELECT COUNT (*) FROM ENOC.MENT_PREGUNTA WHERE PREGUNTA_ID = ?"; 
			Object[] parametros = new Object[] {preguntaId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentPreguntaDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public List<MentPregunta> getListAll( String orden ){
		
		List<MentPregunta> lista		= new ArrayList<MentPregunta>();
		
		try{
			String comando = "SELECT PREGUNTA_ID, NOMBRE, ENCUESTA_ID FROM ENOC.MENT_PREGUNTA "+ orden; 
			lista = enocJdbc.query(comando, new MentPreguntaMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentPreguntaDao|getListAll|:"+ex);
		}
		
		return lista;
	}
}