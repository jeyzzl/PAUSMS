// Clase Util para la tabla de Cat_Area
package aca.catalogo.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CatNivelDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(CatNivel nivel) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.CAT_NIVEL(NIVEL_ID, NOMBRE_NIVEL, ESTADO, ORDEN) "+
				"VALUES( TO_NUMBER(?,'99'), ? , ?, TO_NUMBER(?,'99')) ";
			Object[] parametros = new Object[] {nivel.getNivelId(), nivel.getNombreNivel(), nivel.getEstado(), nivel.getOrden()};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}

		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatNivelDao|insertReg|:"+ex);
			
		}
		
		return ok;
	}	
	
	public boolean updateReg(CatNivel nivel) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.CAT_NIVEL "+ 
				" SET NOMBRE_NIVEL = ?," +
				" ESTADO = ? , " +
				" ORDEN = TO_NUMBER(?,'99') "+
				" WHERE NIVEL_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {nivel.getNombreNivel(), nivel.getEstado(), nivel.getOrden(), nivel.getNivelId()};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatNivelDao|updateReg|:"+ex);		

		}
		
		return ok;
	}	
	
	public boolean deleteReg(String nivelId) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CAT_NIVEL "+ 
				"WHERE NIVEL_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {nivelId};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatNivelDao|deleteReg|:"+ex);			
			
		}
		return ok;
	}
	
	public CatNivel mapeaRegId(String nivelId) {
		
		CatNivel nivel = new CatNivel();
		try{
			String comando = "SELECT NIVEL_ID, NOMBRE_NIVEL, ORDEN, ESTADO "+
				"FROM ENOC.CAT_NIVEL WHERE NIVEL_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {nivelId};		
			nivel = enocJdbc.queryForObject(comando, new CatNivelMapper(),parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatNivelDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
			
		}
		
		return nivel;
	}
	
	public boolean existeReg(String nivelId) {
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_NIVEL WHERE NIVEL_ID = TO_NUMBER(?,'99') ";
			Object[] parametros = new Object[] {nivelId};
			if (enocJdbc.queryForObject(comando, Integer.class,parametros) >= 1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatNivelDao|existeReg|:"+ex);

		}
		
		return ok;
	}
	
	public String maximoReg() {
		String maximo 			= "1";
		
		try{
			String comando = "SELECT COALESCE(MAX(NIVEL_ID)+1,1) AS MAXIMO FROM ENOC.CAT_NIVEL";
			if (enocJdbc.queryForObject(comando, Integer.class) >= 1){
				maximo = enocJdbc.queryForObject(comando, String.class);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatNivelDao|maximoReg|:"+ex);

		}
		
		return maximo;
	}
	
	public String getNivelNombre(String nivelId) {
		
		String nombre			= "X";		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_NIVEL WHERE NIVEL_ID = TO_NUMBER(?,'99')"; 
			Object[] parametros = new Object[] {nivelId};
			if (enocJdbc.queryForObject(comando, Integer.class,parametros) >= 1) {
				comando = "SELECT NOMBRE_NIVEL FROM ENOC.CAT_NIVEL WHERE NIVEL_ID = TO_NUMBER(?,'99')";
				nombre = enocJdbc.queryForObject(comando, String.class,parametros);
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatNivelDao|getNivelNombre|:"+ex);
		}		
		return nombre;
	}
		
	public List<CatNivel> getListAll(String orden ) {
		
		List<CatNivel> lista		= new ArrayList<CatNivel>();	
		
		try{
			String comando = "SELECT NIVEL_ID, NOMBRE_NIVEL, ESTADO, ORDEN FROM ENOC.CAT_NIVEL "+ orden; 
			lista = enocJdbc.query(comando, new CatNivelMapper());
			
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.NivelUtil|getListAll|:"+ex);
	
		}
		
		return lista;
	}
	
	public HashMap<String,CatNivel> getMapAll(String orden ) {
		
		List<CatNivel> lista		= new ArrayList<CatNivel>();
		HashMap<String,CatNivel> mapa = new HashMap<String,CatNivel>();
		
		try{
			String comando = "SELECT NIVEL_ID, NOMBRE_NIVEL, ESTADO, ORDEN FROM ENOC.CAT_NIVEL "+ orden;
			lista = enocJdbc.query(comando, new CatNivelMapper());
			for(CatNivel nivel: lista){
				mapa.put(nivel.getNivelId(), nivel);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.NivelUtil|getMapAll|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String,String> mapaUsados() {
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT NIVEL_ID AS LLAVE, COUNT(NIVEL_ID) AS VALOR FROM ENOC.CAT_CARRERA GROUP BY NIVEL_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa obj : lista){
				mapa.put(obj.getLlave(), obj.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.NivelDao|mapaUsados|:"+ex);
		}
		
		return mapa;
	}

}