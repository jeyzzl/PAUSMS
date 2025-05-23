package aca.plan.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class MapaNuevoBiblioUnidadDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( MapaNuevoBiblioUnidad mapaNuevoBiblioUnidad) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.MAPA_NUEVO_BIBLIO_UNIDAD"+ 
				"(CURSO_ID, UNIDAD_ID, BIBLIOGRAFIA_ID, ID," +
				" ESPECIFICACION) "+
				"VALUES(TO_NUMBER(?, '9999999'), TO_NUMBER(?, '99'), TO_NUMBER(?, '999'), TO_NUMBER(?, '99')," +
				" ?)";
			Object[] parametros = new Object[] {mapaNuevoBiblioUnidad.getCursoId(), mapaNuevoBiblioUnidad.getUnidadId(),
			mapaNuevoBiblioUnidad.getBibliografiaId(), mapaNuevoBiblioUnidad.getId(), mapaNuevoBiblioUnidad.getEspecificacion()};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoBiblioUnidadUtil|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg( MapaNuevoBiblioUnidad mapaNuevoBiblioUnidad ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.MAPA_NUEVO_BIBLIO_UNIDAD" + 
					" SET ESPECIFICACION = ?" +
					" WHERE CURSO_ID = TO_NUMBER(?, '9999999')" +
					" AND UNIDAD_ID = TO_NUMBER(?, '99')" +
					" AND BIBLIOGRAFIA_ID = TO_NUMBER(?, '999')" +
					" AND ID = TO_NUMBER(?, '99')";
			Object[] parametros = new Object[] {mapaNuevoBiblioUnidad.getEspecificacion(), mapaNuevoBiblioUnidad.getCursoId(),
			mapaNuevoBiblioUnidad.getUnidadId(), mapaNuevoBiblioUnidad.getBibliografiaId(), mapaNuevoBiblioUnidad.getId()};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoBiblioUnidadUtil|updateReg|:"+ex);		
		}
		return ok;
	}
	
	public boolean deleteReg( MapaNuevoBiblioUnidad mapaNuevoBiblioUnidad ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.MAPA_NUEVO_BIBLIO_UNIDAD" + 
					" WHERE CURSO_ID = TO_NUMBER(?, '9999999')" +
					" AND UNIDAD_ID = TO_NUMBER(?, '99')" +
					" AND BIBLIOGRAFIA_ID = TO_NUMBER(?, '999')" +
					" AND ID = TO_NUMBER(?, '99')";
			Object[] parametros = new Object[] {mapaNuevoBiblioUnidad.getCursoId(), mapaNuevoBiblioUnidad.getUnidadId(),
					mapaNuevoBiblioUnidad.getBibliografiaId(), mapaNuevoBiblioUnidad.getId()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoBiblioUnidadUtil|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public boolean deleteReg( String cursoId, String bibliografiaId) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.MAPA_NUEVO_BIBLIO_UNIDAD" + 
					" WHERE CURSO_ID = TO_NUMBER(?, '9999999')" +
					" AND BIBLIOGRAFIA_ID = TO_NUMBER(?, '999')";
			Object[] parametros = new Object[] {cursoId, bibliografiaId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoBiblioUnidadUtil|deleteReg(cursoId, bibliografiaId)|:"+ex);			
		}
		return ok;
	}
	
	public boolean deleteReg( String cursoId) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.MAPA_NUEVO_BIBLIO_UNIDAD" + 
					" WHERE CURSO_ID = TO_NUMBER(?, '9999999')";
			Object[] parametros = new Object[] {cursoId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoBiblioUnidadUtil|deleteReg(cursoId)|:"+ex);			
		}
		return ok;
	}
	
	public MapaNuevoBiblioUnidad mapeaRegId(  String cursoId, String unidadId, String bibliografiaId, String id) {
		
		MapaNuevoBiblioUnidad objeto = new MapaNuevoBiblioUnidad();
		
		try{
			String comando = "SELECT CURSO_ID, UNIDAD_ID, BIBLIOGRAFIA_ID, ID," +
					" ESPECIFICACION" +
					" FROM ENOC.MAPA_NUEVO_BIBLIO_UNIDAD" + 
					" WHERE CURSO_ID = TO_NUMBER(?, '9999999')" +
					" AND UNIDAD_ID = TO_NUMBER(?, '99')" +
					" AND BIBLIOGRAFIA_ID = TO_NUMBER(?, '999')" +
					" AND ID = TO_NUMBER(?, '99')";
			Object[] parametros = new Object[] {cursoId, unidadId, bibliografiaId, id};
			objeto = enocJdbc.queryForObject(comando, new MapaNuevoBiblioUnidadMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoBiblioUnidadUtil|mapeaRegId|:"+ex);
		}
		return objeto;
	}
	
	public boolean existeReg( MapaNuevoBiblioUnidad mapaNuevoBiblioUnidad) {
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.MAPA_NUEVO_BIBLIO_UNIDAD" + 
					" WHERE CURSO_ID = TO_NUMBER(?, '9999999')" +
					" AND UNIDAD_ID = TO_NUMBER(?, '99')" +
					" AND BIBLIOGRAFIA_ID = TO_NUMBER(?, '999')" +
					" AND ID = TO_NUMBER(?, '99')";
			Object[] parametros = new Object[] {mapaNuevoBiblioUnidad.getCursoId(), mapaNuevoBiblioUnidad.getUnidadId(),
					mapaNuevoBiblioUnidad.getBibliografiaId(), mapaNuevoBiblioUnidad.getId()};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoBiblioUnidadUtil|existeReg|:"+ex);
		}
		return ok;
	}
	
	public String maximoReg( String cursoId, String unidadId, String bibliografiaId ) {
		String maximo = "1";
		
		try{
			String comando = "SELECT MAX(ID)+1 AS MAXIMO FROM ENOC.MAPA_NUEVO_BIBLIO_UNIDAD" + 
					" WHERE CURSO_ID = TO_NUMBER(?, '9999999')" +
					" AND UNIDAD_ID = TO_NUMBER(?, '99')" +
					" AND BIBLIOGRAFIA_ID = TO_NUMBER(?, '999')";
			Object[] parametros = new Object[] {cursoId, unidadId, bibliografiaId};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
 				maximo = enocJdbc.queryForObject(comando, String.class, parametros);
			}

		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoBiblioUnidadUtil|maximoReg|:"+ex);
		}
		return maximo;
	}
	
	public List<MapaNuevoBiblioUnidad> getListCursoUnidad( String cursoId, String unidadId, String orden) {
		
		List<MapaNuevoBiblioUnidad> lista = new ArrayList<MapaNuevoBiblioUnidad>();		
		try{
			String comando = "SELECT CURSO_ID, UNIDAD_ID, BIBLIOGRAFIA_ID, ID," +
					" ESPECIFICACION" +
				" FROM ENOC.MAPA_NUEVO_BIBLIO_UNIDAD" + 
				" WHERE CURSO_ID = TO_NUMBER(?, '9999999')" +
				" AND UNIDAD_ID = TO_NUMBER(?, '99') "+ orden;
			lista = enocJdbc.query(comando, new MapaNuevoBiblioUnidadMapper(), cursoId, unidadId);	
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoBiblioUnidadUtil|getListCursoUnidad|:"+ex);
		}
		return lista;	
	}
}