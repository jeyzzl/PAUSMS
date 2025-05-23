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
public class CatEscuelaDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( CatEscuela escuela ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.CAT_ESCUELA(ESCUELA_ID, NOMBRE_ESCUELA, PAIS_ID, ESTADO_ID, CIUDAD_ID)"
					+ " VALUES( TO_NUMBER(?,'999'), ?, TO_NUMBER(?,'999'), TO_NUMBER(?,'999'), TO_NUMBER(?,'999')) ";
			Object[] parametros = new Object[] {escuela.getEscuelaId(),escuela.getNombreEscuela(),escuela.getPaisId(),escuela.getEstadoId(),escuela.getCiudadId()};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatEscuelaDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg( CatEscuela escuela ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.CAT_ESCUELA "+ 
				"SET NOMBRE_ESCUELA = ?, "+
				"PAIS_ID = TO_NUMBER(?,'999'), "+
				"ESTADO_ID = TO_NUMBER(?,'999'), "+
				"CIUDAD_ID = TO_NUMBER(?,'999') "+
				"WHERE ESCUELA_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {escuela.getNombreEscuela(),escuela.getPaisId(),escuela.getEstadoId(),escuela.getCiudadId(), escuela.getEscuelaId()};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatEscuelaDao|updateReg|:"+ex);		
		}
		
		return ok;
	}
	
	public boolean deleteReg( String escuelaId ) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.CAT_ESCUELA WHERE ESCUELA_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {escuelaId};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatEscuelaDao|deleteReg|:"+ex);			
		}
		
		return ok;
	}
	
	public CatEscuela mapeaRegId(  String escuelaId) {
		
		CatEscuela escuela 		= new CatEscuela();
		
		try{
			String comando = "SELECT ESCUELA_ID, NOMBRE_ESCUELA, PAIS_ID, ESTADO_ID, CIUDAD_ID"
					+ " FROM ENOC.CAT_ESCUELA WHERE ESCUELA_ID = TO_NUMBER(?,'999')"; 
			Object[] parametros = new Object[] {escuelaId};
			escuela = enocJdbc.queryForObject(comando, new CatEscuelaMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatEscuelaDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		
		return escuela;
	}
	
	public boolean existeReg( String escuelaId) {
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_ESCUELA WHERE ESCUELA_ID = TO_NUMBER(?,'999') ";
			Object[] parametros = new Object[] {escuelaId};			
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatEscuelaDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoReg( ) {
		
		String maximo			= "1";
		
		try{
			String comando = "SELECT COALESCE(MAX(ESCUELA_ID)+1,1) AS MAXIMO FROM ENOC.CAT_ESCUELA"; 
			if (enocJdbc.queryForObject(comando, Integer.class) >= 1){
				maximo = enocJdbc.queryForObject(comando, String.class);
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatEscuelaDao|maximoReg|:"+ex);
		}
		
		return maximo;
	}
		
	public List<CatEscuela> getListAll( String orden ) {
		
		List<CatEscuela> lista  = new ArrayList<CatEscuela>();
		try{
			String comando = "SELECT ESCUELA_ID, NOMBRE_ESCUELA, PAIS_ID, ESTADO_ID, CIUDAD_ID FROM ENOC.CAT_ESCUELA "+orden;
			lista = enocJdbc.query(comando, new CatEscuelaMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatEscuelaDao|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String,CatEscuela> getMapAll( String orden ) {
		
		HashMap<String,CatEscuela> mapa = new HashMap<String,CatEscuela>();
		List<CatEscuela> lista  		= new ArrayList<CatEscuela>();
		
		try{
			String comando = "SELECT ESCUELA_ID, NOMBRE_ESCUELA, PAIS_ID, ESTADO_ID, CIUDAD_ID FROM ENOC.CAT_ESCUELA "+ orden;
			lista = enocJdbc.query(comando, new CatEscuelaMapper());
			for (CatEscuela escuela : lista){
				mapa.put(escuela.getEscuelaId(), escuela);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatEscuelaDao|getMapAll|:"+ex);
		}
		
		return mapa;
	}
	
	public String getNombreEscuela( String escuelaId ) {	
		
		String nombre = "";
		
		try{
			String comando = "SELECT NOMBRE_ESCUELA FROM ENOC.CAT_ESCUELA WHERE ESCUELA_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] { escuelaId };
			nombre = enocJdbc.queryForObject(comando, String.class, parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatEscuelaDao|getNombreEscuela|:"+ex);
		}
		
		return nombre;
	}
}