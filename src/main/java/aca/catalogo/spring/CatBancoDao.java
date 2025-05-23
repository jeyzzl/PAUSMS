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
public class CatBancoDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( CatBanco banco) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.CAT_BANCO"+ 
				"(BANCO_ID, NOMBRE, NOMBRE_CORTO, PAIS_ID, SWIFT) "+
				"VALUES( TO_NUMBER (?,'999'), ?, ?, TO_NUMBER(?,'999'), ?)";
			Object[] parametros = new Object[] {banco.getBancoId(), banco.getNombre(), banco.getNombreCorto(), banco.getPaisId(), banco.getSwift()};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatBancoDao|insertReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean updateReg( CatBanco banco ) {
		boolean ok = false;		
		
		try{
			String comando = "UPDATE ENOC.CAT_BANCO"
					+ " SET NOMBRE = ?, NOMBRE_CORTO = ?, PAIS_ID = TO_NUMBER (?,'999'), SWIFT = ?"
					+ " WHERE BANCO_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {banco.getNombre(), banco.getNombreCorto(), banco.getPaisId(), banco.getSwift(), banco.getBancoId()};						
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatBancoDao|updateReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean deleteReg(String bancoId) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CAT_BANCO"
					+ " WHERE BANCO_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {bancoId};						
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatBancoDao|deleteReg|:"+ex);
			ok = false;
		}
		
		return ok;
	}
	
	public CatBanco mapeaRegId( String bancoId ) {
		
		CatBanco banco		= new CatBanco();	
		
		try{
			String comando = "SELECT BANCO_ID, NOMBRE, NOMBRE_CORTO, PAIS_ID, SWIFT FROM ENOC.CAT_BANCO "+ 
				"WHERE BANCO_ID = ?";
			Object[] parametros = new Object[] {bancoId};
			banco = enocJdbc.queryForObject(comando, new CatBancoMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatBancoDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		
		return banco;
	}
	
	// public boolean existeReg( String bancoId ) {
	// 	boolean 		ok 	= false;
		
	// 	try{
	// 		String comando = "SELECT COUNT(*) FROM ENOC.CAT_BANCO WHERE BANCO_ID = TO_NUMBER(?,'999')";
	// 		Object[] parametros = new Object[] {bancoId};			
	// 		if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
	// 			ok = true;
	// 		}
	// 	}catch(Exception ex){
	// 		System.out.println("Error - aca.catalogo.spring.CatBancoDao|existeReg|:"+ex);
	// 	}
		
	// 	return ok;
	// }

	public boolean existeReg(String bancoId) {
		boolean ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_BANCO WHERE BANCO_ID = ? ";
			Object[] parametros = new Object[] {bancoId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatBancoDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoReg() {
		
		String maximo			= "0";
		
		try{
			String comando = "SELECT COALESCE(MAX(BANCO_ID)+1,1) AS MAXIMO FROM ENOC.BANCO_ID";
			if (enocJdbc.queryForObject(comando, Integer.class) >= 1){
				maximo = String.valueOf(enocJdbc.queryForObject(comando, Integer.class));
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatBancoDao|maximoReg|:"+ex);
		}
		
		return maximo;
	}
	
	public String getNombreBanco( String bancoId) {
		
		String nombre			= "empty";
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_BANCO WHERE BANCO_ID = TO_NUMBER(?,'999')";		
			Object[] parametros = new Object[] {bancoId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				comando = "SELECT NOMBRE FROM ENOC.CAT_BANCO WHERE BANCO_ID = TO_NUMBER(?,'999')";	
				nombre = enocJdbc.queryForObject(comando, String.class, parametros);
			}	
		}catch(Exception ex){		
			System.out.println("Error - aca.catalogo.spring.CatBancoDao|getNombreBanco|:"+ex);
		}
		
		return nombre;
	}
	
	public List<CatBanco> getListAll( String orden ) {
		
		List<CatBanco> lista = new ArrayList<CatBanco>();	
		
		try{
			String comando = "SELECT BANCO_ID, NOMBRE, NOMBRE_CORTO, PAIS_ID, SWIFT FROM ENOC.CAT_BANCO "+ orden;				
			lista = enocJdbc.query(comando, new CatBancoMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatBancoDao|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String,CatBanco> getMapAll( String orden ) {
		
		HashMap<String,CatBanco> mapa 	= new HashMap<String,CatBanco>();
		List<CatBanco> lista 			= new ArrayList<CatBanco>();
		
		try{
			String comando = "SELECT BANCO_ID, NOMBRE, NOMBRE_CORTO, PAIS_ID, SWIFT FROM ENOC.CAT_BANCO"+ orden;		
			lista = enocJdbc.query(comando, new CatBancoMapper());
			for(CatBanco banco : lista){
				mapa.put(banco.getBancoId(), banco);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatBancoDao|getMapAll|:"+ex);
		}
		
		return mapa;
	}

	public HashMap<String,String> mapaUsados() {
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT BANCO_ID AS LLAVE, COUNT(CODIGO_PERSONAL) AS VALOR FROM ENOC.ALUM_BANCO GROUP BY BANCO_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa obj : lista){
				mapa.put(obj.getLlave(), obj.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatBancoDao|mapaUsados|:"+ex);
		}
		
		return mapa;
	}

}