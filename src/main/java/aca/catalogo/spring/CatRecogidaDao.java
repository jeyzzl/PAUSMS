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
public class CatRecogidaDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( CatRecogida recogida ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.CAT_RECOGIDA(RECOGIDA_ID, NOMBRE, NOMBRE_CORTO )"
					+ " VALUES( TO_NUMBER(?,'999'), ?, ? ) ";
			Object[] parametros = new Object[] {recogida.getRecogidaId(),recogida.getNombre(),recogida.getNombreCorto()};	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}					
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatRecogidaDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg( CatRecogida recogida ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.CAT_RECOGIDA"
					+ " SET NOMBRE = ?,"
					+ " NOMBRE_CORTO = ?"
					+ " WHERE RECOGIDA_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {recogida.getNombre(),recogida.getNombreCorto(), recogida.getRecogidaId()};	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatRecogidaDao|updateReg|:"+ex);
		}
		
		return ok;
	}	
	
	public boolean deleteReg( String recogidaId ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CAT_RECOGIDA WHERE RECOGIDA_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {recogidaId};	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatRecogidaDao|deleteReg|:"+ex);
		}
		
		return ok;
	}
	
	public CatRecogida mapeaRegId(  String recogidaId) {
		
		CatRecogida recogida 	= new CatRecogida();		
		try{
			String comando = "SELECT RECOGIDA_ID, NOMBRE, NOMBRE_CORTO"
					+ " FROM ENOC.CAT_RECOGIDA WHERE RECOGIDA_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {recogidaId};
			recogida = enocJdbc.queryForObject(comando, new CatRecogidaMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatRecogidaDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		
		return recogida;
	}
	
	
	public boolean existeReg( String recogidaId) {
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_RECOGIDA WHERE RECOGIDA_ID = TO_NUMBER(?,'999')";	
			Object[] parametros = new Object[] {recogidaId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatRecogidaDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoReg() {
		String maximo 			= "1";
		
		try{
			String comando = "SELECT COALESCE(MAX(RECOGIDA_ID)+1,1) MAXIMO FROM ENOC.CAT_RECOGIDA";
			if (enocJdbc.queryForObject(comando, Integer.class) >= 1){
				maximo = enocJdbc.queryForObject(comando, String.class);
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatRecogidaDao|maximoReg|:"+ex);
		}
		
		return maximo;
	}
	
	public String getNombre( String recogidaId ) {
		
		String nombre			= "empty";
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_RECOGIDA WHERE RECOGIDA_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {recogidaId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				comando = "SELECT NOMBRE FROM ENOC.CAT_RECOGIDA WHERE RECOGIDA_ID = TO_NUMBER(?,'999')";			
				nombre = enocJdbc.queryForObject(comando, String.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatRecogidaDao|getNombre|:"+ex);
		}
		
		return nombre;
	}
	
	public String getNombreCorto( String recogidaId ) {
		
		String nombre			= "empty";
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_RECOGIDA WHERE RECOGIDA_ID = TO_NUMBER(?,'999')";			
			Object[] parametros = new Object[] {recogidaId};			
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				comando = "SELECT NOMBRE_CORTO FROM ENOC.CAT_RECOGIDA WHERE RECOGIDA_ID = TO_NUMBER(?,'999')";				
				nombre = enocJdbc.queryForObject(comando, String.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatRecogidaDao|getNombreCorto|:"+ex);
		}
		
		return nombre;
	}
	
		
	public List<CatRecogida> getListAll( String orden ) {
		
		List<CatRecogida> lista = new ArrayList<CatRecogida>();
		
		try{
			String comando = "SELECT RECOGIDA_ID, NOMBRE, NOMBRE_CORTO FROM ENOC.CAT_RECOGIDA "+ orden;
			lista = enocJdbc.query(comando, new CatRecogidaMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatRecogidaDao|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String,CatRecogida> getMapAll( String orden ) {
		
		HashMap<String,CatRecogida> mapa 	= new HashMap<String,CatRecogida>();
		List<CatRecogida> lista 			= new ArrayList<CatRecogida>();
		
		try{
			String comando = "SELECT RECOGIDA_ID, NOMBRE, NOMBRE_CORTO FROM ENOC.CAT_RECOGIDA "+ orden;
			lista = enocJdbc.query(comando, new CatRecogidaMapper());
			for(CatRecogida recogida : lista){
				mapa.put(recogida.getRecogidaId(), recogida);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatRecogidaDao|getMapAll|:"+ex);
		}
		
		return mapa;
	}	
	
	public HashMap<String,String> mapaUsados() {
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT RECOGIDA_ID AS LLAVE, COUNT(CODIGO_PERSONAL) AS VALOR FROM ENOC.ALUM_UBICACION GROUP BY RECOGIDA_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa obj : lista){
				mapa.put(obj.getLlave(), obj.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatRecogidaDao|mapaUsados|:"+ex);
		}
		
		return mapa;
	}
	
}