package aca.plan.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class MapaNuevoProductoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(MapaNuevoProducto mapaNuevoProducto){
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.MAPA_NUEVO_PRODUCTO"
					+ " (CURSO_ID, UNIDAD_ID, PRODUCTO_ID, DESCRIPCION, TIPO) "
					+ " VALUES( TO_NUMBER(?, '9999999'),TO_NUMBER(?, '99'),TO_NUMBER(?, '99'), ?, ?)";
			Object[] parametros = new Object[] {mapaNuevoProducto.getCursoId(),mapaNuevoProducto.getUnidadId(),mapaNuevoProducto.getProductoId(), 
					mapaNuevoProducto.getDescripcion(),mapaNuevoProducto.getTipo()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
					
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaNuevoProductoDao|insertReg|:"+ex);			
	
		}
		return ok;
	}	
	
	public boolean updateReg(MapaNuevoProducto mapaNuevoProducto){
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.MAPA_NUEVO_PRODUCTO"
					+ " SET DESCRIPCION = ?,"
					+ " TIPO = ?"
					+ " WHERE CURSO_ID = TO_NUMBER(?,'9999999')"
					+ " AND UNIDAD_ID = TO_NUMBER(?, '99')"
					+ " AND PRODUCTO_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {mapaNuevoProducto.getDescripcion(),mapaNuevoProducto.getTipo(),mapaNuevoProducto.getCursoId(), 
					mapaNuevoProducto.getUnidadId(),mapaNuevoProducto.getProductoId()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaNuevoProductoDao|updateReg|:"+ex);		
		
		}	
		return ok;
	}
	
	public boolean deleteReg(String cursoId, String unidadId, String productoId ){
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.MAPA_NUEVO_PRODUCTO"
					+ " WHERE CURSO_ID = TO_NUMBER(?,'9999999')"
					+ " AND UNIDAD_ID = TO_NUMBER(?, '99')"
					+ " AND PRODUCTO_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {cursoId, unidadId, productoId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaNuevoProductoDao|deleteReg|:"+ex);			
		
		}
		return ok;
	}
	
	public boolean deleteReg(String cursoId ){
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.MAPA_NUEVO_PRODUCTO"
					+ " WHERE CURSO_ID = TO_NUMBER(?,'9999999')";
			Object[] parametros = new Object[] {cursoId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaNuevoProductoDao|deleteReg|:"+ex);			
		
		}
		return ok;
	}
	
	public MapaNuevoProducto mapeaRegId(String cursoId, String unidadId, String productoId){
		
		MapaNuevoProducto mapa 	= new MapaNuevoProducto();
		
		try{
			String comando = "SELECT CURSO_ID, UNIDAD_ID, PRODUCTO_ID,"
					+ " DESCRIPCION, TIPO"
					+ " FROM ENOC.MAPA_NUEVO_PRODUCTO"
					+ " WHERE CURSO_ID = TO_NUMBER(?,'9999999')"
					+ " AND UNIDAD_ID = TO_NUMBER(?, '99')"
					+ " AND PRODUCTO_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {cursoId, unidadId, productoId};
			mapa = enocJdbc.queryForObject(comando, new MapaNuevoProductoMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaNuevoProductoDao|mapeaRegId|:"+ex);
		
		}
		
		return mapa;
	}
	
	public boolean existeReg(String cursoId, String unidadId, String productoId){
		boolean 		ok 		= false;
		
		try{
			String comando = "SELECT COUNT (*) FROM ENOC.MAPA_NUEVO_PRODUCTO " 
					+ " WHERE CURSO_ID = TO_NUMBER(?,'9999999')"
					+ " AND UNIDAD_ID = TO_NUMBER(?, '99')"
					+ " AND PRODUCTO_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {cursoId, unidadId, productoId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaNuevoProductoDao|existeReg|:"+ex);
		
		}
		
		return ok;
	}
	
	public String maximoReg(String cursoId, String unidadId ){
		String maximo		 	= "1";
		
		try{
			String comando = "SELECT COALESCE(MAX(PRODUCTO_ID)+1,1) AS MAXIMO FROM ENOC.MAPA_NUEVO_PRODUCTO "  
					+ " WHERE CURSO_ID = TO_NUMBER(?,'9999999')"
					+ " AND UNIDAD_ID = TO_NUMBER(?, '99')";
			Object[] parametros = new Object[] {cursoId, unidadId};
 			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
 				maximo = enocJdbc.queryForObject(comando,String.class,parametros);
			}

		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaNuevoProductoDao|maximoReg|:"+ex);
		
		}
		return maximo;
	}
	
	// Lista de productos en una unidad
	public List<MapaNuevoProducto> getListUnidad(String cursoId, String unidadId, String orden){
		
		List<MapaNuevoProducto> lista		= new ArrayList<MapaNuevoProducto>();
		
		try{
			String comando = " SELECT CURSO_ID, UNIDAD_ID, PRODUCTO_ID, DESCRIPCION, TIPO"
				+ " FROM ENOC.MAPA_NUEVO_PRODUCTO" 
				+ " WHERE CURSO_ID = TO_NUMBER(?, '9999999')"
				+ " AND UNIDAD_ID = TO_NUMBER(?, '99') "+ orden;
			lista = enocJdbc.query(comando, new MapaNuevoProductoMapper(),cursoId,unidadId); ///
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaNuevoProductoDao|getListUnidad|:"+ex);
		
		}
		
		return lista;	
	}
	
	// Lista de productos en una unidad
	public List<MapaNuevoProducto> lisCurso(String cursoId, String orden){
		
		List<MapaNuevoProducto> lista		= new ArrayList<MapaNuevoProducto>();
		
		try{
			String comando = " SELECT CURSO_ID, UNIDAD_ID, PRODUCTO_ID, DESCRIPCION, TIPO"
				+ " FROM ENOC.MAPA_NUEVO_PRODUCTO" 
				+ " WHERE CURSO_ID = TO_NUMBER( ? , '9999999') "+ orden;
			lista = enocJdbc.query(comando, new MapaNuevoProductoMapper(),cursoId);
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaNuevoProductoDao|lisCurso|:"+ex);
		
		}
		
		return lista;	
	}

}
