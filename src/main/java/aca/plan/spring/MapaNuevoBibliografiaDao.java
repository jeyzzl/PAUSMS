/**
 * 
 */
package aca.plan.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class MapaNuevoBibliografiaDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( MapaNuevoBibliografia mapaNuevoBibliografia) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.MAPA_NUEVO_BIBLIOGRAFIA"+ 
				"(CURSO_ID, BIBLIOGRAFIA_ID, BIBLIOGRAFIA, TIPO," +
				" REFERENCIA)"+
				" VALUES(TO_NUMBER(?, '9999999'), TO_NUMBER(?, '999'), ?, ?," +
				" ?)";
			Object[] parametros = new Object[] {mapaNuevoBibliografia.getCursoId(), mapaNuevoBibliografia.getBibliografiaId(),
			mapaNuevoBibliografia.getBibliografia(), mapaNuevoBibliografia.getTipo(), mapaNuevoBibliografia.getReferencia()};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoBibliografiaUtil|insertReg|:"+ex);			
		}		
		return ok;
	}	
	
	public boolean updateReg( MapaNuevoBibliografia mapaNuevoBibliografia ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.MAPA_NUEVO_BIBLIOGRAFIA" + 
					" SET BIBLIOGRAFIA = ?," +
					" TIPO = ?," +
					" REFERENCIA = ?" +
					" WHERE CURSO_ID = TO_NUMBER(?, '9999999')" +
					" AND BIBLIOGRAFIA_ID = TO_NUMBER(?, '999')";
			Object[] parametros = new Object[] {mapaNuevoBibliografia.getBibliografia(), mapaNuevoBibliografia.getTipo(),
			mapaNuevoBibliografia.getReferencia(), mapaNuevoBibliografia.getCursoId(), mapaNuevoBibliografia.getBibliografiaId()};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoBibliografiaUtil|updateReg|:"+ex);		
		}
		return ok;
	}
	
	public boolean deleteReg( String cursoId, String bibliografiaId ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.MAPA_NUEVO_BIBLIOGRAFIA" + 
					" WHERE CURSO_ID = TO_NUMBER(?, '9999999')" +
					" AND BIBLIOGRAFIA_ID = TO_NUMBER(?, '999')";
			Object[] parametros = new Object[] {cursoId, bibliografiaId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoBibliografiaUtil|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public boolean deleteRegCurso( String cursoId) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.MAPA_NUEVO_BIBLIOGRAFIA" + 
					" WHERE CURSO_ID = TO_NUMBER(?, '9999999')";
			Object[] parametros = new Object[] {cursoId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoBibliografiaUtil|deleteRegCurso|:"+ex);			
		}
		return ok;
	}
	
	public MapaNuevoBibliografia mapeaRegId(  String cursoId, String bibliografiaId) {
		
		MapaNuevoBibliografia objeto = new MapaNuevoBibliografia();
		
		try{
			String comando = "SELECT CURSO_ID, BIBLIOGRAFIA_ID, BIBLIOGRAFIA, TIPO," +
					" REFERENCIA" +
					" FROM ENOC.MAPA_NUEVO_BIBLIOGRAFIA" + 
					" WHERE CURSO_ID = TO_NUMBER(?, '9999999')" +
					" AND BIBLIOGRAFIA_ID = TO_NUMBER(?, '999')";
			Object[] parametros = new Object[] {cursoId, bibliografiaId};
			objeto = enocJdbc.queryForObject(comando, new MapaNuevoBibliografiaMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoBibliografiaUtil|mapeaRegId|:"+ex);
		}
		return objeto;
	}
	
	public boolean existeReg( String cursoId, String bibliografiaId) {
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.MAPA_NUEVO_BIBLIOGRAFIA" + 
					" WHERE CURSO_ID = TO_NUMBER(?, '9999999')" +
					" AND BIBLIOGRAFIA_ID = TO_NUMBER(?, '999')";
			Object[] parametros = new Object[] {cursoId, bibliografiaId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}

		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoBibliografiaUtil|existeReg|:"+ex);
		}
		return ok;
	}
	
	public String maximoReg( String cursoId) {
		String maximo = "1";
		
		try{
			String comando = "SELECT MAX(BIBLIOGRAFIA_ID)+1 AS MAXIMO FROM ENOC.MAPA_NUEVO_BIBLIOGRAFIA" + 
					" WHERE CURSO_ID = TO_NUMBER(?, '9999999')";
			Object[] parametros = new Object[] {cursoId};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
 				maximo = enocJdbc.queryForObject(comando, String.class, parametros);
			}
 			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoBibliografiaUtil|maximoReg|:"+ex);
		}
		return maximo;
	}
	
	public List<MapaNuevoBibliografia> getListCurso( String cursoId, String orden) {
		
		List<MapaNuevoBibliografia> lista = new ArrayList<MapaNuevoBibliografia>();
		
		try{
			String comando = "SELECT CURSO_ID, BIBLIOGRAFIA_ID, BIBLIOGRAFIA, TIPO," +
					" REFERENCIA" +
					" FROM ENOC.MAPA_NUEVO_BIBLIOGRAFIA" + 
					" WHERE CURSO_ID = TO_NUMBER(?, '9999999') "+ orden;
			lista = enocJdbc.query(comando, new MapaNuevoBibliografiaMapper(), cursoId);			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoBibliografiaUtil|getListCurso|:"+ex);
		}
		return lista;	
	}
}