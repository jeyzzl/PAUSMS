// Clase para la tabla de Modulo
package aca.catalogo.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.github.jaiimageio.impl.plugins.tiff.TIFFAttrInfo;

@Component
public class CatAcomodoDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( CatAcomodo acomodo ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.CAT_ACOMODO(ACOMODO_ID, NOMBRE, NOMBRE_CORTO, TIPO)"
					+ " VALUES( TO_NUMBER(?), ?, ?, ?) ";
			Object[] parametros = new Object[] {acomodo.getAcomodoId(),acomodo.getNombre(),acomodo.getNombreCorto(),acomodo.getTipo()};	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}					
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatAcomodoDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg( CatAcomodo acomodo ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.CAT_ACOMODO"
					+ " SET NOMBRE = ?,"
					+ " NOMBRE_CORTO = ?,"
					+ " TIPO = ?"
					+ " WHERE ACOMODO_ID = TO_NUMBER(?)";
			Object[] parametros = new Object[] {acomodo.getNombre(),acomodo.getNombreCorto(),acomodo.getTipo(), acomodo.getAcomodoId()};	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatAcomodoDao|updateReg|:"+ex);
		}
		
		return ok;
	}	
	
	public boolean deleteReg( String acomodoId ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CAT_ACOMODO WHERE ACOMODO_ID = TO_NUMBER(?)";
			Object[] parametros = new Object[] {acomodoId};	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatAcomodoDao|deleteReg|:"+ex);
		}
		
		return ok;
	}
	
	public CatAcomodo mapeaRegId(  String acomodoId) {
		
		CatAcomodo acomodo 	= new CatAcomodo();		
		try{
			String comando = "SELECT ACOMODO_ID, NOMBRE, NOMBRE_CORTO, TIPO"
					+ " FROM ENOC.CAT_ACOMODO WHERE ACOMODO_ID = TO_NUMBER(?)";
			Object[] parametros = new Object[] {acomodoId};
			acomodo = enocJdbc.queryForObject(comando, new CatAcomodoMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatAcomodoDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		
		return acomodo;
	}
	
	
	public boolean existeReg( String acomodoId) {
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_ACOMODO WHERE ACOMODO_ID = TO_NUMBER(?)";	
			Object[] parametros = new Object[] {acomodoId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatAcomodoDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoReg() {
		String maximo 			= "1";
		
		try{
			String comando = "SELECT COALESCE(MAX(ACOMODO_ID)+1,1) MAXIMO FROM ENOC.CAT_ACOMODO";
			if (enocJdbc.queryForObject(comando, Integer.class) >= 1){
				maximo = enocJdbc.queryForObject(comando, String.class);
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatAcomodoDao|maximoReg|:"+ex);
		}
		
		return maximo;
	}
	
	public String getNombre( String acomodoId ) {
		
		String nombre			= "empty";
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_ACOMODO WHERE ACOMODO_ID = TO_NUMBER(?)";
			Object[] parametros = new Object[] {acomodoId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				comando = "SELECT NOMBRE FROM ENOC.CAT_ACOMODO WHERE ACOMODO_ID = TO_NUMBER(?)";			
				nombre = enocJdbc.queryForObject(comando, String.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatAcomodoDao|getNombre|:"+ex);
		}
		
		return nombre;
	}
	
	public String getNombreCorto( String acomodoId ) {
		
		String nombre			= "empty";
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_ACOMODO WHERE ACOMODO_ID = TO_NUMBER(?)";			
			Object[] parametros = new Object[] {acomodoId};			
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				comando = "SELECT NOMBRE_CORTO FROM ENOC.CAT_ACOMODO WHERE ACOMODO_ID = TO_NUMBER(?)";				
				nombre = enocJdbc.queryForObject(comando, String.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatAcomodoDao|getNombreCorto|:"+ex);
		}
		
		return nombre;
	}
	
		
	public List<CatAcomodo> getListAll( String orden ) {
		
		List<CatAcomodo> lista = new ArrayList<CatAcomodo>();
		
		try{
			String comando = "SELECT ACOMODO_ID, NOMBRE, NOMBRE_CORTO, TIPO FROM ENOC.CAT_ACOMODO "+ orden;
			lista = enocJdbc.query(comando, new CatAcomodoMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatAcomodoDao|getListAll|:"+ex);
		}
		
		return lista;
	}

	public List<CatAcomodo> getListTipo( String tipo ) {
		
		List<CatAcomodo> lista = new ArrayList<CatAcomodo>();
		
		try{
			String comando = "SELECT ACOMODO_ID, NOMBRE, NOMBRE_CORTO, TIPO FROM ENOC.CAT_ACOMODO WHERE TIPO = ?";
			lista = enocJdbc.query(comando, new CatAcomodoMapper(), tipo);
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatAcomodoDao|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String,CatAcomodo> getMapAll( String orden ) {
		
		HashMap<String,CatAcomodo> mapa 	= new HashMap<String,CatAcomodo>();
		List<CatAcomodo> lista 			= new ArrayList<CatAcomodo>();
		
		try{
			String comando = "SELECT ACOMODO_ID, NOMBRE, NOMBRE_CORTO, TIPO FROM ENOC.CAT_ACOMODO "+ orden;
			lista = enocJdbc.query(comando, new CatAcomodoMapper());
			for(CatAcomodo acomodo : lista){
				mapa.put(acomodo.getAcomodoId(), acomodo);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatAcomodoDao|getMapAll|:"+ex);
		}
		
		return mapa;
	}	
	
	public HashMap<String,String> mapaUsados() {
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT ACOMODO_ID AS LLAVE, COUNT(CODIGO_PERSONAL) AS VALOR FROM ENOC.ALUM_UBICACION GROUP BY ACOMODO_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa obj : lista){
				mapa.put(obj.getLlave(), obj.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatAcomodoDao|mapaUsados|:"+ex);
		}
		
		return mapa;
	}
	
}