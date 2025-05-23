package aca.edo.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class EdoMaestroPregDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( EdoMaestroPreg mpreg) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO" +
				" EDO_MAESTROPREG(EDO_ID, PREGUNTA_ID, PREGUNTA, TIPO, ORDEN, AREA_ID, " +
				" COMENTARIO1, COMENTARIO2, COMENTARIO3, COMENTARIO4)" +
				" VALUES(TO_NUMBER(?, '99'), TO_NUMBER(?, '99999'), ?, ?, TO_NUMBER(?, '99'), " +
				" TO_NUMBER(?, '99'), ?, ?, ?, ?)";
			Object[] parametros = new Object[] {mpreg.getEdoId(), mpreg.getPreguntaId(),
			mpreg.getPregunta(), mpreg.getTipo(), mpreg.getOrden(), mpreg.getAreaId(),
			mpreg.getComentario1(), mpreg.getComentario2(), mpreg.getComentario3(), mpreg.getComentario4()};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoMaestroPregDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	
	public boolean updateReg( EdoMaestroPreg mpreg) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.EDO_MAESTROPREG" + 
				" SET PREGUNTA = ?," +
				" TIPO = ?," +
				" ORDEN = ?" +
				" AREA_ID = TO_NUMBER(?, '99')" +
				" COMENTARIO1 = ? " +
				" COMENTARIO2 = ? " +
				" COMENTARIO3 = ? " +
				" COMENTARIO4 = ? " +
				" WHERE PREGUNTA_ID = TO_NUMBER(?, '99')" +
				" AND EDO_ID = TO_NUMBER(?, '99999')";
			Object[] parametros = new Object[] {mpreg.getPregunta(), mpreg.getTipo(),
			mpreg.getOrden(), mpreg.getAreaId(), mpreg.getComentario1(), mpreg.getComentario2(),
			mpreg.getComentario3(), mpreg.getComentario4(), mpreg.getPreguntaId(), mpreg.getEdoId()};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoMaestroPregDao|updateReg|:"+ex);		 
		}
		return ok;
	}	
	
	public boolean deleteReg( String preguntaId, String edoId) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.EDO_MAESTROPREG "+ 
				" WHERE PREGUNTA_ID = TO_NUMBER(?, '99')" +
				" AND EDO_ID = TO_NUMBER(?, '99999')";
			Object[] parametros = new Object[] {preguntaId, edoId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoMaestroPregDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public EdoMaestroPreg mapeaRegId( String preguntaId, String edoId) {
		EdoMaestroPreg objeto = new EdoMaestroPreg();
		
		try{
			String comando = "SELECT PREGUNTA_ID, EDO_ID, PREGUNTA, TIPO, ORDEN, AREA_ID," +
					"COMENTARIO1, COMENTARIO2, COMENTARIO3, COMENTARIO4" +
					" FROM ENOC.EDO_MAESTROPREG" + 
					" WHERE PREGUNTA_ID = TO_NUMBER(?, '99')" +
					" AND EDO_ID = TO_NUMBER(?, '99999')";
			Object[] parametros = new Object[] {preguntaId, edoId};
			objeto = enocJdbc.queryForObject(comando, new EdoMaestroPregMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoMaestroPregDao|mapeaRegId|:"+ex);
		}
		return objeto;
	}
	
	public boolean existeReg( String preguntaId, String edoId) {
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.EDO_MAESTROPREG" + 
					" WHERE PREGUNTA_ID = TO_NUMBER(?, '99')" +
					" AND EDO_ID = TO_NUMBER(?, '99999')";
			Object[] parametros = new Object[] {preguntaId, edoId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoMaestroPregDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public String maximoReg( String edoId) {
		String maximo = "1";
		
		try{
			String comando = "SELECT COALESCE(MAX(PREGUNTA_ID)+1,1) AS MAXIMO FROM ENOC.EDO_MAESTROPREG" + 
					" WHERE EDO_ID = TO_NUMBER(?, '99999')";
			Object[] parametros = new Object[] {edoId};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
 				maximo = enocJdbc.queryForObject(comando, String.class, parametros);
			}
 			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoMaestroPregDao|maximoReg|:"+ex);
		}
		return maximo;
	}
	
	public int getNumPreguntas( String edoId, String tipo) {
		int total = 0;
		
		try{
			String comando = "SELECT COALESCE(COUNT(PREGUNTA_ID),0) AS TOTAL FROM EDO_MAESTROPREG WHERE EDO_ID = TO_NUMBER(?, '99999') AND TIPO = ?";
			if (enocJdbc.queryForObject(comando, Integer.class) >= 1) {
				total = enocJdbc.queryForObject(comando, Integer.class);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoMaestroPregDao|getNumPreguntas|:"+ex);
		}
		return total;
	}
	
	public List<EdoMaestroPreg> getListPreg ( String edoId, String orden ) {
		
		List<EdoMaestroPreg> lista = new ArrayList<EdoMaestroPreg>();		
		try{
			String comando = "SELECT EDO_ID, PREGUNTA_ID, PREGUNTA, TIPO, ORDEN, AREA_ID, " +
				" COMENTARIO1, COMENTARIO2, COMENTARIO3, COMENTARIO4" +
				" FROM ENOC.EDO_MAESTROPREG" + 
				" WHERE EDO_ID = ? "+orden;
			lista = enocJdbc.query(comando, new EdoMaestroPregMapper(), edoId);			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoMaestroPregDao|getListEdo|:"+ex);
		}
		return lista;
	}
	
	public List<EdoMaestroPreg> getListPorArea ( String edoId, String areaId, String orden ) {
		
		List<EdoMaestroPreg> lista = new ArrayList<EdoMaestroPreg>();		
		try{
			String comando = "SELECT EDO_ID, PREGUNTA_ID, PREGUNTA, TIPO, ORDEN, AREA_ID, " +
					" COMENTARIO1, COMENTARIO2, COMENTARIO3, COMENTARIO4" +
					" FROM ENOC.EDO_MAESTROPREG" + 
					" WHERE EDO_ID = ? AND AREA_ID = ? "+orden;
			lista = enocJdbc.query(comando, new EdoMaestroPregMapper(), edoId, areaId);			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoMaestroPregDao|getListPorArea|:"+ex);
		}
		return lista;
	}
	
	public List<EdoMaestroPreg> getListNoContestadas ( String edoId, String areaId, String codigoPersonal, String maestroId,  String orden ) {
		
		List<EdoMaestroPreg> lista = new ArrayList<EdoMaestroPreg>();		
		try{
			String comando = "SELECT EDO_ID, PREGUNTA_ID, PREGUNTA, TIPO, ORDEN, AREA_ID," +
					" COMENTARIO1, COMENTARIO2, COMENTARIO3, COMENTARIO4" +
					" FROM ENOC.EDO_MAESTROPREG" +
					" WHERE EDO_ID = ? AND AREA_ID = ? " +
					" AND EDO_ID||PREGUNTA_ID " +
					" NOT IN ( " +
					"	SELECT EDO_ID||PREGUNTA_ID " +
					" 	FROM EDO_MAESTRORESP " +
					"	WHERE EDO_ID = ? " +
					"	AND CODIGO_PERSONAL = ?"  +
					"	AND MAESTRO = ? " +
					" ) "+orden;
			lista = enocJdbc.query(comando, new EdoMaestroPregMapper(), edoId, areaId, edoId, codigoPersonal, maestroId );			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoMaestroPregDao|getListNoContestadas|:"+ex);
		}
		return lista;
	}

	public List<EdoArea> getListAreas ( String edoId, String orden ) {
		List<EdoArea> lista 		= new ArrayList<EdoArea>();	
		try{
			String comando = " SELECT * FROM ENOC.EDO_AREA WHERE AREA_ID IN (SELECT AREA_ID FROM EDO_MAESTROPREG WHERE EDO_ID = ?) "+orden;
			lista = enocJdbc.query(comando, new EdoAreaMapper(), edoId);			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoMaestroPregDao|getListAreas|:"+ex);
		}		
		return lista;
	}
}
