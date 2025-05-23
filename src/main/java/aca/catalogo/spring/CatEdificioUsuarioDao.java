// Clase Util para la tabla de Cat_Edificios
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
public class CatEdificioUsuarioDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( CatEdificioUsuario edificio ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.CAT_EDIFICIO_USUARIO(EDIFICIO_ID, CODIGO_PERSONAL)"
					+ " VALUES( ?, ?)";
			Object[] parametros = new Object[] {edificio.getEdificioId(), edificio.getCodigo_personal()};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatEdificioUsuarioDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean deleteReg( String edificioId, String codigoPersonal ) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.CAT_EDIFICIO_USUARIO WHERE EDIFICIO_ID = ? AND CODIGO_PERSONAL = ? ";
			Object[] parametros = new Object[] {edificioId, codigoPersonal};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatEdificioUsuarioDao|deleteReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public CatEdificioUsuario mapeaRegId( String edificioId, String codigoPersonal) {		
		CatEdificioUsuario edificio 	= new CatEdificioUsuario();		
		try{
			String comando 		= "SELECT COUNT(*) FROM ENOC.CAT_EDIFICIO_USUARIO WHERE EDIFICIO_ID = ? AND CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {edificioId, codigoPersonal};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				comando 	= "SELECT * FROM ENOC.CAT_EDIFICIO_USUARIO WHERE EDIFICIO_ID = ?";
				edificio 	= enocJdbc.queryForObject(comando, new CatEdificioUsuarioMapper(), parametros);
			}						
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatEdificioUsuarioDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}		
		return edificio;
	}
	
	public boolean existeReg( String edificioId, String codigoPersonal) {
		boolean 		ok 		= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_EDIFICIO_USUARIO WHERE EDIFICIO_ID = ? AND CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {edificioId, codigoPersonal};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatEdificioUsuarioDao|existeReg|:"+ex);
		}		
		return ok;
	}
	
	public String maximoReg(Connection conn) {
		String maximo 			= "1";		
		try{
			String comando = "SELECT COALESCE(MAX(EDIFICIO_ID)+1,1) AS MAXIMO FROM ENOC.CAT_EDIFICIO_USUARIO";
			if (enocJdbc.queryForObject(comando, Integer.class) >= 1){
				maximo = enocJdbc.queryForObject(comando, String.class);
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatEdificiosUsuarioDao|maximoReg|:"+ex);
		}
		
		return maximo;		
	}
	
	// Llena el listor con todos los elementos de la tabla	
	public List<CatEdificioUsuario> lisPorEdificio( String edificioId, String orden ){
		List<CatEdificioUsuario> lista = new ArrayList<CatEdificioUsuario>();		
		try{
			String comando = "SELECT * FROM ENOC.CAT_EDIFICIO_USUARIO WHERE EDIFICIO_ID = ? "+ orden;
			lista = enocJdbc.query(comando, new CatEdificioUsuarioMapper(), edificioId);
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatEdificioUsuarioDao|lisPorEdificio|:"+ex);
		}
		
		return lista;
	}
	
	public List<String> lisUsuarios( String edificioId, String orden ){
		List<String> lista = new ArrayList<String>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL FROM ENOC.CAT_EDIFICIO_USUARIO WHERE EDIFICIO_ID = ? "+ orden;
			lista = enocJdbc.queryForList(comando, String.class, edificioId);
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatEdificioUsuarioDao|lisUsuarios|:"+ex);
		}
		
		return lista;
	}
	
	public List<String> lisEdificiosPorUsuario( String codigoPersonal, String orden ){
		List<String> lista = new ArrayList<String>();		
		try{
			String comando = "SELECT EDIFICIO_ID FROM ENOC.CAT_EDIFICIO_USUARIO WHERE CODIGO_PERSONAL = ? "+ orden;
			lista = enocJdbc.queryForList(comando, String.class, codigoPersonal);
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatEdificioUsuarioDao|lisEdificiosPorUsuario|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String, String> mapaPorEdificio() {
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();
		HashMap<String, String> mapa	= new HashMap<String, String>();				
		try{
			String comando = "SELECT EDIFICIO_ID AS LLAVE, COUNT(CODIGO_PERSONAL) AS VALOR FROM ENOC.CAT_EDIFICIO_USUARIO GROUP BY EDIFICIO_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa objeto: lista){
				mapa.put(objeto.getLlave(), objeto.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.GrupoUtil|mapaPorEdificio|:"+ex);
		}		
		return mapa;
	}
}