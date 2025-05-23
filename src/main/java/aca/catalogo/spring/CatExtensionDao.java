// Clase Util para la tabla de Cat_Division
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
public class CatExtensionDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( CatExtension extension ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.CAT_EXTENSION(EXTENSION_ID, NOMBRE_EXTENSION, REFERENTE, DIRECCION, COLONIA, COD_POSTAL,TELEFONO, FAX, EMAIL)"
					+ " VALUES( TO_NUMBER(?,'999'), ?, ?, ?, ?, ?, ?, ?, ?) ";
			Object[] parametros = new Object[] {extension.getExtensionId(), extension.getNombreExtension(), extension.getReferente(), extension.getDireccion(), 
					extension.getColonia(), extension.getCodPostal(), extension.getTelefono(), extension.getFax(), extension.getEmail()};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatExtensionDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg( CatExtension extension ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.CAT_EXTENSION"
				+ " SET NOMBRE_EXTENSION = ?,"
				+ " REFERENTE = ?,"
				+ " DIRECCION = ?,"
				+ " COLONIA = ?,"
				+ " COD_POSTAL = ?,"
				+ " TELEFONO = ?,"
				+ " FAX = ?,"
				+ " EMAIL = ?"
				+ " WHERE EXTENSION_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {extension.getNombreExtension(), extension.getReferente(), extension.getDireccion(), extension.getColonia(), 
					extension.getCodPostal(), extension.getTelefono(), extension.getFax(), extension.getEmail(), extension.getExtensionId()};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatExtensionDao|updateReg|:"+ex);		
		}
		
		return ok;
	}
	
	public boolean deleteReg( String extensionId ) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.CAT_EXTENSION WHERE EXTENSION_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {extensionId};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatExtensionDao|deleteReg|:"+ex);			
		}
		
		return ok;
	}
	
	public CatExtension mapeaRegId(  String extensionId) {
		
		CatExtension extension 		= new CatExtension();
		
		try{
			String comando = "SELECT EXTENSION_ID, NOMBRE_EXTENSION, REFERENTE, DIRECCION, COLONIA, COD_POSTAL,TELEFONO, FAX, EMAIL"
					+ " FROM ENOC.CAT_EXTENSION "
					+ " WHERE EXTENSION_ID = TO_NUMBER(?,'999')"; 
			Object[] parametros = new Object[] {extensionId};
			extension = enocJdbc.queryForObject(comando, new CatExtensionMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatExtensionDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		
		return extension;
	}
	
	public boolean existeReg( String extensionId) {
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_EXTENSION WHERE EXTENSION_ID = TO_NUMBER(?,'999') ";
			Object[] parametros = new Object[] {extensionId};			
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatExtensionDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoReg( ) {
		
		String maximo			= "1";
		
		try{
			String comando = "SELECT COALESCE(MAX(EXTENSION_ID)+1,1) AS MAXIMO FROM ENOC.CAT_EXTENSION"; 
			if (enocJdbc.queryForObject(comando, Integer.class) >= 1){
				maximo = enocJdbc.queryForObject(comando, String.class);
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatExtensionDao|maximoReg|:"+ex);
		}
		
		return maximo;
	}
		
	public List<CatExtension> getListAll( String orden ) {
		
		List<CatExtension> lista  = new ArrayList<CatExtension>();
		try{
			String comando = "SELECT EXTENSION_ID, NOMBRE_EXTENSION, REFERENTE, DIRECCION, COLONIA, COD_POSTAL,TELEFONO, FAX, EMAIL FROM ENOC.CAT_EXTENSION "+orden;
			lista = enocJdbc.query(comando, new CatExtensionMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatExtensionDao|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String,CatExtension> getMapAll( String orden ) {
		
		HashMap<String,CatExtension> mapa = new HashMap<String,CatExtension>();
		List<CatExtension> lista  		= new ArrayList<CatExtension>();
		
		try{
			String comando = "SELECT EXTENSION_ID, NOMBRE_EXTENSION, REFERENTE, DIRECCION, COLONIA, COD_POSTAL,TELEFONO, FAX, EMAIL FROM ENOC.CAT_EXTENSION "+ orden;
			lista = enocJdbc.query(comando, new CatExtensionMapper());
			for (CatExtension extension : lista){
				mapa.put(extension.getExtensionId(), extension);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatExtensionDao|getMapAll|:"+ex);
		}
		
		return mapa;
	}
	
	public String getNombreEscuela( String extensionId ) {	
		
		String nombre = "";
		
		try{
			String comando = "SELECT NOMBRE_EXTENSION FROM ENOC.CAT_EXTENSION WHERE EXTENSION_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] { extensionId };
			nombre = enocJdbc.queryForObject(comando, String.class, parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatExtensionDao|getNombreEscuela|:"+ex);
		}
		
		return nombre;
	}
}