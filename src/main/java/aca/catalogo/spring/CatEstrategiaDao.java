// Clase Util para la tabla de Cat_Division
package aca.catalogo.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CatEstrategiaDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( CatEstrategia estrategia ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.CAT_ESTRATEGIA (ESTRATEGIA_ID, NOMBRE_ESTRATEGIA) VALUES( ?, ? )";
			Object[] parametros = new Object[] {estrategia.getEstrategiaId(), estrategia.getNombreEstrategia()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatEstrategiaDao|insertReg|:"+ex);			
		}
		
		return ok;
	}
	
	public boolean updateReg( CatEstrategia estrategia ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.CAT_ESTRATEGIA SET NOMBRE_ESTRATEGIA = ? WHERE ESTRATEGIA_ID = ?";
			Object[] parametros = new Object[] {estrategia.getNombreEstrategia(), estrategia.getEstrategiaId()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatEstrategiaDao|updateReg|:"+ex);		
		}
		
		return ok;
	}
	
	public boolean deleteReg( String estrategiaId ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CAT_ESTRATEGIA WHERE ESTRATEGIA_ID = ?";
			Object[] parametros = new Object[] {estrategiaId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatEstrategiaDao|deleteReg|:"+ex);			
		}
		
		return ok;
	}
	
	public CatEstrategia mapeaRegId(  String estrategiaId) {
		
		CatEstrategia estrategia 	= new CatEstrategia();
		
		try{
			String comando = "SELECT ESTRATEGIA_ID, NOMBRE_ESTRATEGIA FROM ENOC.CAT_ESTRATEGIA WHERE ESTRATEGIA_ID = ?"; 
			Object[] parametros = new Object[] {estrategiaId};
			estrategia = enocJdbc.queryForObject(comando, new CatEstrategiaMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatEstrategiaDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		
		return estrategia;
	}
	
	public boolean existeReg( String estrategiaId) {
		boolean ok 				= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_ESTRATEGIA WHERE ESTRATEGIA_ID = ? ";
			Object[] parametros = new Object[] {estrategiaId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatEstrategiaDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoReg(){
		String maximo = "1";		
		try{
			String comando = "SELECT COALESCE(MAX(ESTRATEGIA_ID)+1,1) FROM ENOC.CAT_ESTRATEGIA";			
			maximo = enocJdbc.queryForObject(comando,String.class);	
			if(maximo.length()==1){
				maximo = "0"+maximo;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatEstrategiaDao|maximoReg|:"+ex);
		}
		
		return maximo;
	}
	
	public String getNombreEstrategia( String estrategiaId) {		
		String nombre = "";		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_ESTRATEGIA WHERE ESTRATEGIA_ID = ? ";
			Object[] parametros = new Object[] {estrategiaId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				comando = "SELECT NOMBRE_ESTRATEGIA FROM ENOC.CAT_ESTRATEGIA WHERE ESTRATEGIA_ID = ?";							
				nombre = enocJdbc.queryForObject(comando, String.class, parametros);
			}						
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatEstrategiaDao|getNombreEstrategia|:"+ex);
		}
		
		return nombre;
	}
		
	public List<CatEstrategia> getListAll( String orden ) {
		
		List<CatEstrategia> lista	= new ArrayList<CatEstrategia>();
		
		try{
			String comando = "SELECT ESTRATEGIA_ID, NOMBRE_ESTRATEGIA FROM ENOC.CAT_ESTRATEGIA "+orden;
			lista = enocJdbc.query(comando, new CatEstrategiaMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatEstrategiaDao|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String,CatEstrategia> getMapAll( String orden ) {
		
		HashMap<String,CatEstrategia> mapa 	= new HashMap<String,CatEstrategia>();
		List<CatEstrategia> lista			= new ArrayList<CatEstrategia>();
		
		try{
			String comando = "SELECT ESTRATEGIA_ID, NOMBRE_ESTRATEGIA FROM ENOC.CAT_ESTRATEGIA "+ orden;
			lista = enocJdbc.query(comando, new CatEstrategiaMapper());	
			for ( CatEstrategia estado : lista){
				 mapa.put(estado.getEstrategiaId(), estado);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatEstrategiaDao|getMapAll|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> getMapNombre( String orden ) {
		
		HashMap<String, String> mapa 	= new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT ESTRATEGIA_ID AS LLAVE, NOMBRE_ESTRATEGIA AS VALOR FROM ENOC.CAT_ESTRATEGIA "+ orden;
			lista = enocJdbc.query(comando, new aca.MapaMapper());	
			for (aca.Mapa map : lista){
				 mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatEstrategiaDao|getMapNombre|:"+ex);
		}
		
		return mapa;
	}
	
	public String getNombre( String estrategiaId ) {	
		
		String nombre			= "x";
		
		try{			 
			String comando = "SELECT ESTRATEGIA_ID, NOMBRE_ESTRATEGIA FROM ENOC.CAT_ESTRATEGIA WHERE ESTRATEGIA_ID = ?";		
			Object[] parametros = new Object[] {estrategiaId};			
			nombre = enocJdbc.queryForObject(comando, String.class, parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatEstrategiaDao|getNombre|:"+ex);
		}
		
		return nombre;
	}
}