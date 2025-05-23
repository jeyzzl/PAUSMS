// Clase para la tabla de Modulo
package aca.catalogo.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CatNivelInicioDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( CatNivelInicio nivel ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.CAT_NIVEL_INICIO(NIVEL_INICIO_ID, NOMBRE_NIVEL, NOMBRE_CORTO )"
					+ " VALUES( TO_NUMBER(?,'999'), ?, ? ) ";
			Object[] parametros = new Object[] {nivel.getNivelInicioId(),nivel.getNombreNivel(),nivel.getNombreCorto()};	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}					
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatNivelInicioDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg( CatNivelInicio nivelInicio ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.CAT_NIVEL_INICIO"
					+ " SET NOMBRE_NIVEL = ?,"
					+ " NOMBRE_CORTO = ?"
					+ " WHERE NIVEL_INICIO_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {nivelInicio.getNombreNivel(),nivelInicio.getNombreCorto(), nivelInicio.getNivelInicioId()};	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatNivelInicioDao|updateReg|:"+ex);
		}
		
		return ok;
	}	
	
	public boolean deleteReg( String nivelInicioId ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CAT_NIVEL_INICIO WHERE NIVEL_INICIO_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {nivelInicioId};	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatNivelInicioDao|deleteReg|:"+ex);
		}
		
		return ok;
	}
	
	public CatNivelInicio mapeaRegId(  String nivelInicioId) {
		
		CatNivelInicio nivelInicio 	= new CatNivelInicio();		
		try{
			String comando = "SELECT NIVEL_INICIO_ID, NOMBRE_NIVEL, NOMBRE_CORTO"
					+ " FROM ENOC.CAT_NIVEL_INICIO WHERE NIVEL_INICIO_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {nivelInicioId};
			nivelInicio = enocJdbc.queryForObject(comando, new CatNivelInicioMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatNivelInicioDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		
		return nivelInicio;
	}
	
	
	public boolean existeReg( String nivelInicioId) {
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_NIVEL_INICIO WHERE NIVEL_INICIO_ID = TO_NUMBER(?,'999')";	
			Object[] parametros = new Object[] {nivelInicioId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatNivelInicioDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoReg() {
		String maximo 			= "1";
		
		try{
			String comando = "SELECT MAX(NIVEL_INICIO_ID)+1 MAXIMO FROM ENOC.CAT_NIVEL_INICIO";
			if (enocJdbc.queryForObject(comando, Integer.class) >= 1){
				maximo = enocJdbc.queryForObject(comando, String.class);
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatNivelInicioDao|maximoReg|:"+ex);
		}
		
		return maximo;
	}
	
	public String getNombreNivel( String nivelInicioId ) {
		
		String nombre			= "empty";
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_NIVEL_INICIO WHERE NIVEL_INICIO_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {nivelInicioId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				comando = "SELECT NOMBRE_NIVEL FROM ENOC.CAT_NIVEL_INICIO WHERE NIVEL_INICIO_ID = TO_NUMBER(?,'999')";			
				nombre = enocJdbc.queryForObject(comando, String.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatNivelInicioDao|getNombreNivel|:"+ex);
		}
		
		return nombre;
	}
	
	public String getNombreCorto( String nivelInicioId ) {
		
		String nombre			= "vacío";
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_NIVEL_INICIO WHERE NIVEL_INICIO_ID = TO_NUMBER(?,'999')";			
			Object[] parametros = new Object[] {nivelInicioId};			
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				comando = "SELECT NOMBRE_CORTO FROM ENOC.CAT_NIVEL_INICIO WHERE NIVEL_INICIO_ID = TO_NUMBER(?,'999')";				
				nombre = enocJdbc.queryForObject(comando, String.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatNivelInicioDao|getNombreCorto|:"+ex);
		}
		
		return nombre;
	}
	
		
	public List<CatNivelInicio> getListAll( String orden ) {
		
		List<CatNivelInicio> lista = new ArrayList<CatNivelInicio>();
		
		try{
			String comando = "SELECT NIVEL_INICIO_ID, NOMBRE_NIVEL, NOMBRE_CORTO FROM ENOC.CAT_NIVEL_INICIO "+ orden;
			lista = enocJdbc.query(comando, new CatNivelInicioMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatNivelInicioDao|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String,CatNivelInicio> getMapAll( String orden ) {
		
		HashMap<String,CatNivelInicio> mapa 	= new HashMap<String,CatNivelInicio>();
		List<CatNivelInicio> lista 			= new ArrayList<CatNivelInicio>();
		
		try{
			String comando = "SELECT NIVEL_INICIO_ID, NOMBRE_NIVEL, NOMBRE_CORTO FROM ENOC.CAT_NIVEL_INICIO "+ orden;
			lista = enocJdbc.query(comando, new CatNivelInicioMapper());
			for(CatNivelInicio nivelInicio : lista){
				mapa.put(nivelInicio.getNivelInicioId(), nivelInicio);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatNivelInicioDao|getMapAll|:"+ex);
		}
		
		return mapa;
	}	
	
	public HashMap<String,String> mapaUsados() {
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT NIVEL_INICIO_ID AS LLAVE, COUNT(CODIGO_PERSONAL) AS VALOR FROM ENOC.ALUM_ACADEMICO GROUP BY NIVEL_INICIO_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa obj : lista){
				mapa.put(obj.getLlave(), obj.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatNivelInicioDao|mapaUsados|:"+ex);
		}
		
		return mapa;
	}
	
}