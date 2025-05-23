package aca.trabajo.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class TrabCategoriaDao {

	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( TrabCategoria categoria) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.TRAB_CATEGORIA(CAT_ID, NOMBRE, ESTADO, DEPT_ID)"
					+ " VALUES( ?, ?, ?, ? )";
			Object[] parametros = new Object[] {categoria.getCategoriaId(),categoria.getNombreCategoria(), categoria.getEstado(),
					categoria.getDeptId()};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.categoria.spring.TrabCategoriaDao|insertReg|:"+ex);			
		}
		
		return ok;
	}
	
	public boolean updateReg( TrabCategoria categoria) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.TRAB_CATEGORIA SET NOMBRE = ?, ESTADO = ? WHERE CAT_ID = TO_NUMBER(?,'999') AND DEPT_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {categoria.getNombreCategoria(),categoria.getEstado(), categoria.getCategoriaId(), categoria.getDeptId()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.trabajo.spring.TrabCategoriaDao|updateReg|:"+ex);		
		}
		
		return ok;
	}	
	
	public boolean deleteReg( String categoriaId ) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.TRAB_CATEGORIA WHERE CAT_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {categoriaId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.trabajo.spring.TrabCategoriaDao|deleteReg|:"+ex);			
		}
		
		return ok;
	}
	
	public TrabCategoria mapeaRegId(  String categoriaId ) {
		
		TrabCategoria categoria = new TrabCategoria();		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.TRAB_CATEGORIA WHERE CAT_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] { categoriaId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				comando = "SELECT CAT_ID, NOMBRE, ESTADO, DEPT_ID FROM ENOC.TRAB_CATEGORIA WHERE CAT_ID = TO_NUMBER(?,'999')";
				categoria = enocJdbc.queryForObject(comando, new TrabCategoriaMapper(), parametros);
			}						
		}catch(Exception ex){
			System.out.println("Error - aca.trabajo.spring.TrabCategoriaDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		
		return categoria;
	}
	
	public boolean existeReg( String categoriaId ) {
		boolean 		ok 		= false;		
		try{
			String comando = "SELECT COUNT(CAT_ID) FROM ENOC.TRAB_CATEGORIA WHERE CAT_ID = TO_NUMBER(?, '999') ";
			Object[] parametros = new Object[] {categoriaId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.trabajo.spring.TrabCategoriaDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoReg() {
		String maximo 			= "1";		
		try{
			String comando = "SELECT COALESCE(MAX(CAT_ID)+1,1) AS MAXIMO FROM ENOC.TRAB_CATEGORIA";
			if (enocJdbc.queryForObject(comando, Integer.class) >= 1){
				maximo = enocJdbc.queryForObject(comando, String.class);
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.trabajo.spring.TrabCategoriaDao|maximoReg|:"+ex);
		}
		
		return maximo;		
	}
	
	// Llena el listor con todos los elementos de la tabla	
		public List<TrabCategoria> lisTodos( String orden ) {
			
			List<TrabCategoria> lista = new ArrayList<TrabCategoria>();		
			try{
				String comando = "SELECT CAT_ID, NOMBRE, ESTADO, DEPT_ID FROM ENOC.TRAB_CATEGORIA "+ orden;				
				lista = enocJdbc.query(comando, new TrabCategoriaMapper());
			}catch(Exception ex){
				System.out.println("Error - aca.trabajo.spring.TrabCategoriaDao|lisTodos|:"+ex);
			}		
			return lista;
		}
		
	public List<TrabCategoria> lisPorDepartamento (String deptId, String orden){
		List<TrabCategoria> lista = new ArrayList<TrabCategoria>();
		
		try {
			String comando = "SELECT CAT_ID, NOMBRE, ESTADO, DEPT_ID FROM ENOC.TRAB_CATEGORIA WHERE DEPT_ID = TO_NUMBER(?, '999') " + orden;
			Object[] parametros = new Object[] {deptId};
			lista = enocJdbc.query(comando, new TrabCategoriaMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.trabajo.spring.TrabCategoriaDao|lisPorDepartamento|:"+ex);
		}		
		
		return lista;
	}
	
	public HashMap<String,String> mapaTotalCategorias(){		
		HashMap<String,String> mapa 	= new HashMap<String,String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT DEPT_ID AS LLAVE, COUNT(CAT_ID) AS VALOR FROM ENOC.TRAB_CATEGORIA GROUP BY DEPT_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper());	
			for ( aca.Mapa objeto : lista){
				 mapa.put(objeto.getLlave(), objeto.getValor());
			}				
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.TrabCategoriaDao|mapaTotalCategorias|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String,String> mapaCategoriaNombre(){		
		HashMap<String,String> mapa 	= new HashMap<String,String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CAT_ID AS LLAVE, NOMBRE AS VALOR FROM ENOC.TRAB_CATEGORIA";
			lista = enocJdbc.query(comando, new aca.MapaMapper());	
			for ( aca.Mapa objeto : lista){
				 mapa.put(objeto.getLlave(), objeto.getValor());
			}				
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.TrabCategoriaDao|mapaTotalCategorias|:"+ex);
		}		
		return mapa;
	}
	
}
