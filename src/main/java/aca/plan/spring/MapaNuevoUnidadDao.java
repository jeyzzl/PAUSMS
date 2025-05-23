/**
 * 
 */
package aca.plan.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * @author elifo
 *
 */

@Component
public class MapaNuevoUnidadDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( MapaNuevoUnidad mapaNuevoUnidad){
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.MAPA_NUEVO_UNIDAD"
					+ " (CURSO_ID, UNIDAD_ID, NOMBRE, TIEMPO, TEMAS, ACCION_DOCENTE, ORDEN)"
					+ " VALUES(TO_NUMBER(?, '9999999'), TO_NUMBER(?, '99'), ?, ?, ?, ?, TO_NUMBER(?,'99.99'))";
			Object[] parametros = new Object[] {mapaNuevoUnidad.getCursoId(),mapaNuevoUnidad.getUnidadId(),mapaNuevoUnidad.getNombre(), 
					mapaNuevoUnidad.getTiempo(),mapaNuevoUnidad.getTemas(), mapaNuevoUnidad.getAccionDocente(), mapaNuevoUnidad.getOrden()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	

		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaNuevoUnidadDao|insertReg|:"+ex);	
		
		}
		
		return ok;
	}	
	
	public boolean updateReg( MapaNuevoUnidad mapaNuevoUnidad ){
		boolean ok = false;

		try{
			String comando = "UPDATE ENOC.MAPA_NUEVO_UNIDAD"
					+ " SET NOMBRE = ?,"
					+ " TIEMPO = ?,"
					+ " TEMAS = ?,"
					+ " ACCION_DOCENTE = ?,"
					+ " ORDEN = TO_NUMBER(?,'99.99')"
					+ " WHERE CURSO_ID = TO_NUMBER(?, '9999999')"
					+ " AND UNIDAD_ID = TO_NUMBER(?, '99')";			
			Object[] parametros = new Object[] {mapaNuevoUnidad.getNombre(),mapaNuevoUnidad.getTiempo(),mapaNuevoUnidad.getTemas(), 
					mapaNuevoUnidad.getAccionDocente(),mapaNuevoUnidad.getOrden(), mapaNuevoUnidad.getCursoId(), mapaNuevoUnidad.getUnidadId()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaNuevoUnidadDao|updateReg|:"+ex);		
		
		}
		
		return ok;
	}
	
	public boolean deleteReg( String cursoId, String unidadId ){
		boolean ok = false;
		
		//System.out.println("Borrando la unidad:"+cursoId+":"+unidadId);
		try{
			String comando = "DELETE FROM ENOC.MAPA_NUEVO_UNIDAD" + 
						     " WHERE CURSO_ID = TO_NUMBER(?, '9999999')" +
						     " AND UNIDAD_ID = TO_NUMBER(?, '99')";
			Object[] parametros = new Object[] {cursoId, unidadId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaNuevoUnidadDao|deleteReg|:"+ex);			
		
		}
		return ok;
	}
	
	public boolean deleteRegCurso(String cursoId){
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.MAPA_NUEVO_UNIDAD" + 
						     " WHERE CURSO_ID = TO_NUMBER(?, '9999999')";
			Object[] parametros = new Object[] {cursoId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaNuevoUnidadDao|deleteRegCurso|:"+ex);			
	
		}
		return ok;
	}
	
	public MapaNuevoUnidad mapeaRegId(String cursoId, String unidadId){
		
		MapaNuevoUnidad mapaNuevoUnidad = new MapaNuevoUnidad();
		try{
			String comando = "SELECT CURSO_ID, UNIDAD_ID, NOMBRE, TIEMPO, TEMAS, ACCION_DOCENTE, ORDEN"
					+ " FROM ENOC.MAPA_NUEVO_UNIDAD"
					+ " WHERE CURSO_ID = TO_NUMBER(?, '9999999')"
					+ " AND UNIDAD_ID = TO_NUMBER(?, '99')";
			Object[] parametros = new Object[] {cursoId, unidadId};
			mapaNuevoUnidad = enocJdbc.queryForObject(comando, new MapaNuevoUnidadMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaNuevoUnidadDao|mapeaRegId|:"+ex);
		
		}	
		return mapaNuevoUnidad;
	}
	
	public boolean existeReg( String cursoId, String unidadId){
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT (*) FROM ENOC.MAPA_NUEVO_UNIDAD" + 
					" WHERE CURSO_ID = TO_NUMBER(?, '9999999')" +
					" AND UNIDAD_ID = TO_NUMBER(?, '99')";
			Object[] parametros = new Object[] {cursoId, unidadId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
	
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaNuevoUnidadDao|existeReg|:"+ex);
		
		}
		
		return ok;
	}
	
	public String maximoReg( String cursoId){
		String maximo		 	= "1";
		
		try{
			String comando = "SELECT MAX(UNIDAD_ID)+1 AS MAXIMO FROM ENOC.MAPA_NUEVO_UNIDAD" + 
					" WHERE CURSO_ID = TO_NUMBER(?, '9999999')";
			Object[] parametros = new Object[] {cursoId};
 			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
 				maximo = enocJdbc.queryForObject(comando,String.class,parametros);
			}

		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaNuevoUnidadDao|maximoReg|:"+ex);
	
		}
		
		return maximo;
	}
	
	public String numUnidades( String cursoId){
		String cantidad		 	= "-";
		
		try{
			String comando = "SELECT COUNT(UNIDAD_ID) AS NUM FROM ENOC.MAPA_NUEVO_UNIDAD" + 
							 " WHERE CURSO_ID = TO_NUMBER(?, '9999999')";
			Object[] parametros = new Object[] {cursoId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				cantidad = enocJdbc.queryForObject(comando, String.class, parametros);
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaNuevoUnidadDao|numUnidades|:"+ex);
		
		}
		
		return cantidad;
	}
	
	public String getNombre( String cursoId, String unidadId){
		String cantidad		 	= "-";
		
		try{
			String comando = "SELECT COALESCE(NOMBRE,'0') FROM ENOC.MAPA_NUEVO_UNIDAD"
					+ " WHERE CURSO_ID = TO_NUMBER(?, '9999999') AND UNIDAD_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {cursoId};
			cantidad = enocJdbc.queryForObject(comando,String.class,parametros);	
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaNuevoUnidadDao|getNombre|:"+ex);	
		}
		
		return cantidad;
	}
	
	public List<MapaNuevoUnidad> getListCurso( String cursoId, String orden){
		
		List<MapaNuevoUnidad> lista		= new ArrayList<MapaNuevoUnidad>();
		
		try{
			String comando = " SELECT CURSO_ID, UNIDAD_ID, NOMBRE, TIEMPO, TEMAS, ACCION_DOCENTE, ORDEN"
					+ " FROM ENOC.MAPA_NUEVO_UNIDAD"
					+ " WHERE CURSO_ID = TO_NUMBER( ? , '9999999') "+ orden;
			lista = enocJdbc.query(comando, new MapaNuevoUnidadMapper(),cursoId);
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaNuevoUnidadDao|getListCurso|:"+ex);
		
		}
		
		return lista;
	}
	
	public HashMap<String, String> mapaTotalUnidades( String planId ) {
		List<aca.Mapa> lista 				= new ArrayList<aca.Mapa>();		
		HashMap<String, String> mapa		= new HashMap<String,String>();		
		try{
			String comando = "SELECT CURSO_ID AS LLAVE, COUNT(UNIDAD_ID) AS VALOR FROM ENOC.MAPA_NUEVO_UNIDAD"
					+ " WHERE CURSO_ID IN (SELECT CURSO_ID FROM ENOC.MAPA_NUEVO_CURSO WHERE PLAN_ID = ?)"
					+ " GROUP BY CURSO_ID";	
			lista = enocJdbc.query(comando, new aca.MapaMapper(), planId);
			for ( aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor() );
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaNuevoUnidadDao|mapaTotalUnidades|:"+ex);
		}
		
		return mapa;
	}
}