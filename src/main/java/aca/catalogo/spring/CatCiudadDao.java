// Clase Util para la tabla de Cat_Area
package aca.catalogo.spring;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CatCiudadDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( CatCiudad ciudad) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.CAT_CIUDAD"+ 
				"(PAIS_ID, ESTADO_ID, CIUDAD_ID, NOMBRE_CIUDAD) "+
				"VALUES( TO_NUMBER (?,'999'), TO_NUMBER(?,'999'), TO_NUMBER(?,'999'), ?)";
			Object[] parametros = new Object[] {ciudad.getPaisId(),	ciudad.getEstadoId(),ciudad.getCiudadId(),ciudad.getNombreCiudad()};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCiudadDao|insertReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean updateReg( CatCiudad ciudad ) {
		boolean ok = false;		
		
		try{
			String comando = "UPDATE ENOC.CAT_CIUDAD"
					+ " SET NOMBRE_CIUDAD = ?"
					+ " WHERE PAIS_ID = TO_NUMBER(?,'999')"
					+ " AND ESTADO_ID = TO_NUMBER(?,'999')"
					+ " AND CIUDAD_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {ciudad.getNombreCiudad(),ciudad.getPaisId(),ciudad.getEstadoId(),ciudad.getCiudadId()};						
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCiudadDao|updateReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean deleteReg(String paisId, String estadoId, String ciudadId ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CAT_CIUDAD"
					+ " WHERE PAIS_ID = TO_NUMBER(?,'999')"
					+ " AND ESTADO_ID = TO_NUMBER(?,'999')"
					+ " AND CIUDAD_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {paisId, estadoId, ciudadId};						
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCiudadDao|deleteReg|:"+ex);
			ok = false;
		}
		
		return ok;
	}
	
	public CatCiudad mapeaRegId(  String paisId, String estadoId, String ciudadId ) {
		
		CatCiudad ciudad		= new CatCiudad();	
		
		try{
			String comando = "SELECT PAIS_ID, ESTADO_ID, CIUDAD_ID, NOMBRE_CIUDAD "+
				"FROM ENOC.CAT_CIUDAD "+ 
				"WHERE PAIS_ID = TO_NUMBER(?,'999') "+
				"AND ESTADO_ID = TO_NUMBER(?,'999') "+
				"AND CIUDAD_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {paisId, estadoId, ciudadId};
			ciudad = enocJdbc.queryForObject(comando, new CatCiudadMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCiudadDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		
		return ciudad;
	}
	
	public boolean existeReg( String paisId, String estadoId, String ciudadId ) {
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_CIUDAD"
					+ " WHERE PAIS_ID = TO_NUMBER(?,'999')"
					+ " AND ESTADO_ID = TO_NUMBER(?,'999')"
					+ " AND CIUDAD_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {paisId, estadoId, ciudadId};			
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCiudadDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoReg( String paisId, String estadoId) {
		
		String maximo			= "1";
		
		try{
			String comando = "SELECT COALESCE(MAX(CIUDAD_ID)+1,1) AS MAXIMO FROM ENOC.CAT_CIUDAD"
					+ " WHERE PAIS_ID = TO_NUMBER(?,'999') AND ESTADO_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {paisId, estadoId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				maximo = String.valueOf(enocJdbc.queryForObject(comando, Integer.class, parametros));
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCiudadDao|maximoReg|:"+ex);
		}
		
		return maximo;
	}
	
	public String getNombreCiudad( String paisId, String estadoId, String ciudadId) {
		
		String nombre			= "empty";
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_CIUDAD WHERE PAIS_ID = TO_NUMBER(?,'999') AND ESTADO_ID = TO_NUMBER(?,'999') AND CIUDAD_ID = TO_NUMBER(?,'999')";		
			Object[] parametros = new Object[] {paisId, estadoId, ciudadId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				comando = "SELECT NOMBRE_CIUDAD FROM ENOC.CAT_CIUDAD WHERE PAIS_ID = TO_NUMBER(?,'999') AND ESTADO_ID = TO_NUMBER(?,'999') AND CIUDAD_ID = TO_NUMBER(?,'999')";	
				nombre = enocJdbc.queryForObject(comando, String.class, parametros);
			}	
		}catch(Exception ex){		
			System.out.println("Error - aca.catalogo.spring.CatCiudadDao|getNombreCiudad|:"+ex);
		}
		
		return nombre;
	}
	
	public List<CatCiudad> getLista( String paisId, String estadoId, String orden ) {
		
		List<CatCiudad> lista	 	= new ArrayList<CatCiudad>();
		
		try{
			String comando = "SELECT PAIS_ID, ESTADO_ID, CIUDAD_ID, NOMBRE_CIUDAD"
					+ " FROM ENOC.CAT_CIUDAD"
					+ " WHERE PAIS_ID = TO_NUMBER(?,'999')"
					+ " AND ESTADO_ID = TO_NUMBER(?,'999') "+ orden;	
			Object[] parametros = new Object[] { paisId, estadoId };	
			lista = enocJdbc.query(comando, new CatCiudadMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCiudadDao|getLista|:"+ex);
		}
		
		return lista;
	}
	
	public List<CatCiudad> getListAll( String orden ) {
		
		List<CatCiudad> lista = new ArrayList<CatCiudad>();	
		
		try{
			String comando = "SELECT PAIS_ID, ESTADO_ID, CIUDAD_ID, NOMBRE_CIUDAD FROM ENOC.CAT_CIUDAD "+ orden;				
			lista = enocJdbc.query(comando, new CatCiudadMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCiudadDao|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String,CatCiudad> getMapAll( String orden ) {
		
		HashMap<String,CatCiudad> mapa 	= new HashMap<String,CatCiudad>();
		List<CatCiudad> lista 			= new ArrayList<CatCiudad>();
		
		try{
			String comando = "SELECT PAIS_ID, ESTADO_ID, CIUDAD_ID, NOMBRE_CIUDAD FROM ENOC.CAT_CIUDAD "+ orden;		
			lista = enocJdbc.query(comando, new CatCiudadMapper());
			for(CatCiudad ciudad : lista){
				mapa.put(ciudad.getPaisId()+ciudad.getEstadoId()+ciudad.getCiudadId(), ciudad);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCiudadDao|getMapAll|:"+ex);
		}
		
		return mapa;
	}
	
	
	public String getCiudad(Connection Conn,String paisId,String estadoId, String ciudadId) {
		
		String nombre		= "No encontro";
		
		try{
			String comando = "SELECT NOMBRE_CIUDAD FROM ENOC.CAT_CIUDAD WHERE PAIS_ID = TO_NUMBER(?,'999') AND ESTADO_ID = TO_NUMBER(?,'999') AND CIUDAD_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] { paisId, estadoId, ciudadId };
			nombre = enocJdbc.queryForObject(comando, String.class, parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCiudadDao|getCiudad|:"+ex);
		}
		
		return nombre;
	}
	
	public HashMap<String,String> mapaCiudadPorEstado(String paisId, String orden) {
		
		HashMap<String,String> mapa 	= new HashMap<String,String>();
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT ESTADO_ID AS LLAVE, COUNT(CIUDAD_ID) AS VALOR FROM ENOC.CAT_CIUDAD WHERE PAIS_ID = TO_NUMBER(?,'999') "+orden;
			Object[] parametros = new Object[] {paisId};
			lista 	= enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa map : lista){
				mapa.put(map.getLlave(), map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.mapaCiudadPorEstado|mapaCiudadPorEstado|:"+ex);
		}
		
		return mapa;
	}

}