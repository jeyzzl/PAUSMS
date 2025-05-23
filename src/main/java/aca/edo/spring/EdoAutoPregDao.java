package aca.edo.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class EdoAutoPregDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( EdoAutoPreg preg) {
		boolean ok = false;
		
		try{
			String comando = ("INSERT INTO" +
				" EDO_AUTOPREG(PREGUNTA_ID, EDO_ID, PREGUNTA, TIPO, ORDEN)" +
				" VALUES(TO_NUMBER(?, '99'), TO_NUMBER(?, '99999'), ?, ?, ?)");
			Object[] parametros = new Object[] {preg.getPreguntaId(), preg.getEdoId(),			
			preg.getPregunta(), preg.getTipo(), preg.getOrden()};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoAutoPregDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg( EdoAutoPreg preg) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.EDO_AUTOPREG" + 
				" SET PREGUNTA = ?," +
				" TIPO = ?," +
				" ORDEN = ?" +
				" WHERE PREGUNTA_ID = TO_NUMBER(?, '99')" +
				" AND EDO_ID = TO_NUMBER(?, '99999')";
			Object[] parametros = new Object[] {preg.getPregunta(), preg.getTipo(),
			preg.getOrden(), preg.getPreguntaId(), preg.getEdoId()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoAutoPregDao|updateReg|:"+ex);		 
		}
		return ok;
	}	
	
	public boolean deleteReg( String preguntaId, String edoId) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.EDO_AUTOPREG"+ 
				" WHERE PREGUNTA_ID = TO_NUMBER(?, '99')" +
				" AND EDO_ID = TO_NUMBER(?, '99999')";
			Object[] parametros = new Object[] {preguntaId, edoId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoAutoPregDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public EdoAutoPreg mapeaRegId(String preguntaId, String edoId) {
		EdoAutoPreg objeto = new EdoAutoPreg();
		
		try{
			String comando = "SELECT PREGUNTA_ID, EDO_ID, PREGUNTA, TIPO, ORDEN" +
					" FROM ENOC.EDO_AUTOPREG" + 
					" WHERE PREGUNTA_ID = TO_NUMBER(?, '99')" +
					" AND EDO_ID = TO_NUMBER(?, '99999')";
			Object[] parametros = new Object[] {preguntaId, edoId};
			objeto = enocJdbc.queryForObject(comando, new EdoAutoPregMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoAutoPregDao|mapeaRegId|:"+ex);
		}
		return objeto;
	}
	
	public boolean existeReg( String preguntaId, String edoId) {
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.EDO_AUTOPREG" + 
					" WHERE PREGUNTA_ID = TO_NUMBER(?, '99')" +
					" AND EDO_ID = TO_NUMBER(?, '99999')";
			Object[] parametros = new Object[] {preguntaId, edoId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoAutoPregDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public String maximoReg( String edoId) {
		String maximo = "1";
		
		try{
			String comando = "SELECT COALESCE(MAX(PREGUNTA_ID)+1,1) AS MAXIMO FROM ENOC.EDO_AUTOPREG" + 
					" WHERE EDO_ID = TO_NUMBER(?, '99999')";
			Object[] parametros = new Object[] {edoId};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
 				maximo = enocJdbc.queryForObject(comando, String.class, parametros);
			}
 			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoAutoPregDao|maximoReg|:"+ex);
		}
		return maximo;
	}
	
	public List<EdoAutoPreg> getListEdo( String edoId, String orden ) {
		
		List<EdoAutoPreg> lista = new ArrayList<EdoAutoPreg>();
		
		try{
			String comando = "SELECT PREGUNTA_ID, EDO_ID, PREGUNTA, TIPO, ORDEN" +
					" FROM ENOC.EDO_AUTOPREG" + 
					" WHERE EDO_ID = "+edoId+" "+orden;
			lista = enocJdbc.query(comando, new EdoAutoPregMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoAutoPregDao|getListEdo|:"+ex);
		}
		return lista;
	}
}