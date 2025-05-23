package aca.edo.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class EdoAlumnoPregDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( EdoAlumnoPreg edo) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.EDO_ALUMNOPREG(PREGUNTA_ID, EDO_ID, PREGUNTA, TIPO, ORDEN, AREA_ID, SECCION)" +
				" VALUES(TO_NUMBER(?, '99'), TO_NUMBER(?, '99999'), ?, ?,?, TO_NUMBER(?, '99'),?)";
			Object[] parametros = new Object[] {edo.getPreguntaId(), edo.getEdoId(),
			edo.getPregunta(), edo.getTipo(), edo.getOrden(), edo.getAreaId(), edo.getSeccion()};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoAlumnoPregDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg( EdoAlumnoPreg preg) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.EDO_ALUMNOPREG" + 
				" SET PREGUNTA = ?," +
				" TIPO = ?," +
				" ORDEN = ?," +
				" AREA_ID = ?,"+
				" SECCION = ?"+
				" WHERE PREGUNTA_ID = TO_NUMBER(?, '99')" +
				" AND EDO_ID = TO_NUMBER(?, '99999')";
			Object[] parametros = new Object[] {preg.getPregunta(), preg.getTipo(),
			preg.getOrden(), preg.getAreaId(), preg.getSeccion(), preg.getPreguntaId(), preg.getEdoId()};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoAlumnoPregDao|updateReg|:"+ex);		 
		}
		return ok;
	}	
	
	public boolean deleteReg( String preguntaId, String edoId) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.EDO_ALUMNOPREG "+ 
				" WHERE PREGUNTA_ID = TO_NUMBER(?, '99')" +
				" AND EDO_ID = TO_NUMBER(?, '99999')";
			Object[] parametros = new Object[] {preguntaId, edoId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoAlumnoPregDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public EdoAlumnoPreg mapeaRegId( String preguntaId, String edoId) {
		EdoAlumnoPreg objeto = new EdoAlumnoPreg();
		
		try{
			String comando = "SELECT PREGUNTA_ID, EDO_ID, PREGUNTA, TIPO, ORDEN, AREA_ID, SECCION"
					+ " FROM ENOC.EDO_ALUMNOPREG"
					+ " WHERE PREGUNTA_ID = TO_NUMBER(?, '99')"
					+ " AND EDO_ID = TO_NUMBER(?, '99999')";
			Object[] parametros = new Object[] {preguntaId, edoId};
			objeto = enocJdbc.queryForObject(comando, new EdoAlumnoPregMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoAlumnoPregDao|mapeaRegId|:"+ex);
		}
		return objeto;
	}
	
	public boolean existeReg( String preguntaId, String edoId) {
		boolean	ok = false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.EDO_ALUMNOPREG" + 
					" WHERE PREGUNTA_ID = TO_NUMBER(?, '99')" +
					" AND EDO_ID = TO_NUMBER(?, '99999')";
			Object[] parametros = new Object[] {preguntaId, edoId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoAlumnoPregDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public String maximoReg( String edoId) {
		String maximo = "1";
		
		try{
			String comando = "SELECT COALESCE(MAX(PREGUNTA_ID)+1,1) AS MAXIMO FROM ENOC.EDO_ALUMNOPREG" + 
					" WHERE EDO_ID = TO_NUMBER(?, '99999')";
			Object[] parametros = new Object[] {edoId};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
 				maximo = enocJdbc.queryForObject(comando, String.class, parametros);
			}
 			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoAlumnoPregDao|maximoReg|:"+ex);
		}
		return maximo;
	}
	
	public int getNumPreguntas( String edoId, String tipo) {
		int total = 0;
		
		try{
			String comando = "SELECT COALESCE(COUNT(PREGUNTA_ID),0) AS TOTAL FROM ENOC.EDO_ALUMNOPREG WHERE EDO_ID = TO_NUMBER(?, '99999') AND TIPO = ?";
			Object[] parametros = new Object[] {edoId, tipo};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				total = enocJdbc.queryForObject(comando, Integer.class, parametros);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoAlumnoPregDao|getNumPreguntas|:"+ex);
		}
		return total;
	}
	
	public List<EdoAlumnoPreg> getListEdo( String edoId, String orden ) {
		
		List<EdoAlumnoPreg> lista = new ArrayList<EdoAlumnoPreg>();
		
		try{
			String comando = "SELECT PREGUNTA_ID, EDO_ID, PREGUNTA, TIPO, ORDEN, AREA_ID, SECCION" +
					" FROM ENOC.EDO_ALUMNOPREG" + 
					" WHERE EDO_ID = "+edoId+" "+orden;
			lista = enocJdbc.query(comando, new EdoAlumnoPregMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoAlumnoPregDao|getListEdo|:"+ex);
		}
		return lista;
	}
	
	public List<EdoAlumnoPreg> getListComentarios( String cursoCargaId, String tipo, String orden ) {		
		List<EdoAlumnoPreg> lista = new ArrayList<EdoAlumnoPreg>();		
		try{
			String comando = "SELECT PREGUNTA_ID, EDO_ID, PREGUNTA, TIPO, ORDEN, AREA_ID, SECCION" +
					" FROM ENOC.EDO_ALUMNOPREG " + 
					" WHERE EDO_ID = (SELECT DISTINCT(EDO_ID) FROM ENOC.EDO_ALUMNORESP WHERE CURSO_CARGA_ID = ?) " + 
					" AND TIPO = ? "+orden;
			lista = enocJdbc.query(comando, new EdoAlumnoPregMapper(), cursoCargaId, tipo);			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoAlumnoPregDao|getListComentarios|:"+ex);
		}
		return lista;
	}
	
	public List<EdoAlumnoPreg> getListFortalezas( String cursoCargaId, String orden ) {
		
		List<EdoAlumnoPreg> lista = new ArrayList<EdoAlumnoPreg>();
		
		try{
			String comando = "SELECT EDO_ID, PREGUNTA_ID, CODIGO_PERSONAL, CURSO_CARGA_ID, CODIGO_MAESTRO, RESPUESTA" +
					" FROM ENOC.EDO_ALUMNORESP WHERE CURSO_CARGA_ID = "+cursoCargaId+" AND PREGUNTA_ID = '19' "+orden;
			lista = enocJdbc.query(comando, new EdoAlumnoPregMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.spring.EdoAlumnoPregDao|getListFortalezas|:"+ex);
		}
		return lista;
	}
}