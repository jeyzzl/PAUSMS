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
public class CatEdificioDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( CatEdificio edificio ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.CAT_EDIFICIO(EDIFICIO_ID, NOMBRE_EDIFICIO)"
					+ " VALUES( ?, ? )";
			Object[] parametros = new Object[] {edificio.getEdificioId(),edificio.getNombreEdificio()};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatEdificioDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg( CatEdificio edificio ) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.CAT_EDIFICIO SET NOMBRE_EDIFICIO = ?  WHERE EDIFICIO_ID = ?";
			Object[] parametros = new Object[] {edificio.getNombreEdificio(),edificio.getEdificioId()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatEdificioDao|updateReg|:"+ex);		
		}
		
		return ok;
	}	
	
	public boolean deleteReg( String edificioId ) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.CAT_EDIFICIO WHERE EDIFICIO_ID = ?";
			Object[] parametros = new Object[] {edificioId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatEdificioDao|deleteReg|:"+ex);			
		}
		
		return ok;
	}
	
	public CatEdificio mapeaRegId(  String edificioId) {
		
		CatEdificio edificio 	= new CatEdificio();		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_EDIFICIO WHERE EDIFICIO_ID = ? ";
			Object[] parametros = new Object[] {edificioId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				comando = "SELECT * FROM ENOC.CAT_EDIFICIO WHERE EDIFICIO_ID = ?";
				edificio = enocJdbc.queryForObject(comando, new CatEdificioMapper(), parametros);
			}						
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatEdificioDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		
		return edificio;
	}
	
	public boolean existeReg( String edificioId) {
		boolean 		ok 		= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_EDIFICIO WHERE EDIFICIO_ID = ? ";
			Object[] parametros = new Object[] {edificioId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatEdificioDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoReg() {
		String maximo 			= "01";		
		try{
			String comando = "SELECT COALESCE(MAX(EDIFICIO_ID)+1,1) AS MAXIMO FROM ENOC.CAT_EDIFICIO";
			if (enocJdbc.queryForObject(comando, Integer.class) >= 1){
				maximo = enocJdbc.queryForObject(comando, String.class);
				if (maximo.length()==1) maximo = "0"+maximo;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatEdificioDao|maximoReg|:"+ex);
		}
		
		return maximo;		
	}
	
	// Llena el listor con todos los elementos de la tabla	
	public List<CatEdificio> lisTodos( String orden ) {
		
		List<CatEdificio> lista = new ArrayList<CatEdificio>();		
		try{
			String comando = "SELECT EDIFICIO_ID, NOMBRE_EDIFICIO FROM ENOC.CAT_EDIFICIO "+ orden;				
			lista = enocJdbc.query(comando, new CatEdificioMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatEdificioDao|lisTodos|:"+ex);
		}		
		return lista;
	}	
		
	public List<CatEdificio> lisEnHorario( String cargaId, String codigoPersonal, String orden ) {
		
		List<CatEdificio> lista = new ArrayList<CatEdificio>();		
		try{
			String comando = "SELECT EDIFICIO_ID, NOMBRE_EDIFICIO FROM ENOC.CAT_EDIFICIO"
					+ " WHERE EDIFICIO_ID IN (SELECT DISTINCT(EDIFICIO_ID) FROM CAT_SALON WHERE SALON_ID IN (SELECT DISTINCT(SALON_ID) FROM ENOC.CARGA_GRUPO_HORA WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ? AND SALON_ID != '0'))"
					+ " AND EDIFICIO_ID IN (SELECT EDIFICIO_ID FROM ENOC.CAT_EDIFICIO_USUARIO WHERE CODIGO_PERSONAL = ?)"+ orden;		
			lista = enocJdbc.query(comando, new CatEdificioMapper(), cargaId, codigoPersonal);
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatEdificioDao|lisEnHorario|:"+ex);
		}
		
		return lista;
	}
	
	public List<CatEdificio> lisEnHorarioyPlan( String cargaId, String planId, String codigoPersonal, String orden ) {
		
		List<CatEdificio> lista = new ArrayList<CatEdificio>();		
		try{
			String comando = "SELECT EDIFICIO_ID, NOMBRE_EDIFICIO FROM ENOC.CAT_EDIFICIO "
					+ " WHERE EDIFICIO_ID IN ("
					+ " SELECT DISTINCT(SUBSTR(SALON_ID,1,2)) FROM ENOC.CARGA_GRUPO_HORA"
					+ " WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ?"
					+ " AND SALON_ID != '0'"
					+ " AND CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.CARGA_ACADEMICA WHERE CARGA_ID = ? AND PLAN_ID = ? )"
					+ ")"
					+ " AND EDIFICIO_ID IN (SELECT EDIFICIO_ID FROM ENOC.CAT_EDIFICIO_USUARIO WHERE CODIGO_PERSONAL = ?)"+ orden;	
			lista = enocJdbc.query(comando, new CatEdificioMapper(), cargaId, cargaId, planId, codigoPersonal);
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatEdificioDao|lisEnHorario|:"+ex);
		}
		
		return lista;
	}
	
	public List<CatEdificio> lisEdificiosPorUsuario(String codigoPersonal, String orden) {
		
		List<CatEdificio> lista = new ArrayList<CatEdificio>();		
		try{
			String comando = "SELECT EDIFICIO_ID, NOMBRE_EDIFICIO FROM CAT_EDIFICIO WHERE EDIFICIO_ID IN (SELECT EDIFICIO_ID FROM ENOC.CAT_EDIFICIO_USUARIO WHERE CODIGO_PERSONAL = ?) "+ orden;
			lista = enocJdbc.query(comando, new CatEdificioMapper(), codigoPersonal);
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatEdificioDao|lisEdificiosPorUsuario|:"+ex);
		}		
		return lista;
	}
	
	public HashMap<String,CatEdificio> mapaEdificios() {
		
		HashMap<String,CatEdificio> mapa 	= new HashMap<String,CatEdificio>();
		List<CatEdificio> lista 			= new ArrayList<CatEdificio>();		
		try{
			String comando = "SELECT EDIFICIO_ID, NOMBRE_EDIFICIO FROM CAT_EDIFICIO";
			lista = enocJdbc.query(comando, new CatEdificioMapper());
			for (CatEdificio edificio: lista){
				mapa.put(edificio.getEdificioId(), edificio);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatEdificioDao|mapaEdificios|:"+ex);
		}
		
		return mapa;
	}	
}