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

/**
 * @author Jose Torres 
 *
 */
@Component
public class MapaNuevoActividadDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(MapaNuevoActividad mapaNuevoActividad){
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.MAPA_NUEVO_ACTIVIDAD"+ 
				"(CURSO_ID, UNIDAD_ID, ACTIVIDAD_ID, OBJETIVO," +
				" DESCRIPCION, CRITERIO, TIPO) "+
				"VALUES(TO_NUMBER(?, '9999999'), TO_NUMBER(?, '99'), TO_NUMBER(?, '99'), ?,"+
				" ?, ?, ?)";	
			Object[] parametros = new Object[] {mapaNuevoActividad.getCursoId(),mapaNuevoActividad.getUnidadId(),mapaNuevoActividad.getActividadId(), 
					mapaNuevoActividad.getObjetivo(),mapaNuevoActividad.getDescripcion(), mapaNuevoActividad.getCriterio(), mapaNuevoActividad.getTipo()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoActividadDao|insertReg|:"+ex);			
		
		}		
		return ok;
	}	
	
	public boolean updateReg(MapaNuevoActividad mapaNuevoActividad ){
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.MAPA_NUEVO_ACTIVIDAD" + 
						 	 " SET OBJETIVO = ?, " +
							 " DESCRIPCION = ?, " +
							 " CRITERIO = ?," +
							 " TIPO = ?" +
							 " WHERE CURSO_ID = TO_NUMBER(?, '9999999')" +
							 " AND UNIDAD_ID = TO_NUMBER(?, '99')" +
							 " AND ACTIVIDAD_ID = TO_NUMBER(?, '99')";
			Object[] parametros = new Object[] {mapaNuevoActividad.getObjetivo(),mapaNuevoActividad.getDescripcion(),mapaNuevoActividad.getCriterio(), 
					mapaNuevoActividad.getTipo(), mapaNuevoActividad.getCursoId(), mapaNuevoActividad.getUnidadId(), mapaNuevoActividad.getActividadId()}; 			 			
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoActividadDao|updateReg|:"+ex);		
		
		}		
		return ok;
	}
	
	public boolean deleteReg(String cursoId, String unidadId, String actividadId ){
		boolean ok = false;
	
		try{
			String comando = "DELETE FROM ENOC.MAPA_NUEVO_ACTIVIDAD" + 
						     " WHERE CURSO_ID = TO_NUMBER(?, '9999999')" +
						     " AND UNIDAD_ID = TO_NUMBER(?, '99')" +
						     " AND ACTIVIDAD_ID = TO_NUMBER(?, '99')";
			Object[] parametros = new Object[] {cursoId, unidadId, actividadId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoActividadDao|deleteReg|:"+ex);			
		
		}
		return ok;
	}
	
	public MapaNuevoActividad mapeaRegId( String cursoId, String unidadId, String actividadId){
		
		MapaNuevoActividad mapaNuevoActividad = new MapaNuevoActividad();
		
		try{
			String comando = "SELECT CURSO_ID, UNIDAD_ID, ACTIVIDAD_ID, OBJETIVO," +
					" DESCRIPCION, CRITERIO, TIPO" +
					" FROM ENOC.MAPA_NUEVO_ACTIVIDAD" + 
					" WHERE CURSO_ID = TO_NUMBER(?, '9999999')" +
					" AND UNIDAD_ID = TO_NUMBER(?, '99')" +
				   	" AND ACTIVIDAD_ID = TO_NUMBER(?, '99')";
			Object[] parametros = new Object[] {cursoId, unidadId, actividadId};
			mapaNuevoActividad = enocJdbc.queryForObject(comando, new MapaNuevoActividadMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoActividadDao|mapeaRegId|:"+ex);
		
		}	
		return mapaNuevoActividad;
	}
	
	public boolean existeReg(String cursoId, String unidadId, String actividadId){
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT (*) FROM ENOC.MAPA_NUEVO_ACTIVIDAD" + 
					" WHERE CURSO_ID = TO_NUMBER(?, '9999999')" +
					" AND UNIDAD_ID = TO_NUMBER(?, '99')" +
			   		" AND ACTIVIDAD_ID = TO_NUMBER(?, '99')";
			Object[] parametros = new Object[] {cursoId, unidadId, actividadId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoActividadDao|existeReg|:"+ex);
		
		}		
		return ok;
	}
	
	public String maximoReg(String cursoId, String unidadId){
		String maximo		 	= "1";
	
		try{
			String comando = "SELECT MAX(ACTIVIDAD_ID)+1 AS MAXIMO FROM ENOC.MAPA_NUEVO_ACTIVIDAD" + 
					" WHERE CURSO_ID = TO_NUMBER(?, '9999999')" +
					" AND UNIDAD_ID = TO_NUMBER(?, '99')";
			Object[] parametros = new Object[] {cursoId, unidadId};
 			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
 				maximo = enocJdbc.queryForObject(comando,String.class,parametros);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoActividadDao|maximoReg|:"+ex);
		
		}		
		return maximo;
	}
	
	public List<MapaNuevoActividad> getListUnidad(String cursoId, String unidadId, String orden){
		
		List<MapaNuevoActividad> lista		= new ArrayList<MapaNuevoActividad>();
	
		try{
			String comando = "SELECT CURSO_ID, UNIDAD_ID, ACTIVIDAD_ID, OBJETIVO," +
				" DESCRIPCION, CRITERIO, TIPO" +
				" FROM ENOC.MAPA_NUEVO_ACTIVIDAD" + 
				" WHERE CURSO_ID = TO_NUMBER(?, '9999999')" +
				" AND UNIDAD_ID = TO_NUMBER(?, '99') "+ orden;
			lista = enocJdbc.query(comando, new MapaNuevoActividadMapper(), cursoId, unidadId);
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaNuevoActividadDao|getListPlan|:"+ex);
		
		}
		return lista;	
	}
}