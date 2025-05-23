package aca.catalogo.spring;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import aca.alumno.spring.AlumPatrocinador;
import aca.alumno.spring.AlumPatrocinadorMapper;

@Component
public class CatPatrocinadorDao {

	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	
	public boolean insertReg( CatPatrocinador patrocinador) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.CAT_PATROCINADOR(PATROCINADOR_ID, NOMBRE, DETALLES, DIRECCION, TELEFONO, EMAIL, TIPO)"
					+ " VALUES( ?, ?, ?, ?, ?, ?, ? )";
			Object[] parametros = new Object[] {patrocinador.getPatrocinadorId(),patrocinador.getNombrePatrocinador(), patrocinador.getDetalles(), patrocinador.getDireccion(), patrocinador.getTelefono(),
												patrocinador.getEmail(), patrocinador.getTipo()};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatPatrocinadorDao|insertReg|:"+ex);			
		}
		
		return ok;
	}
	
	public boolean updateReg( CatPatrocinador patrocinador ) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.CAT_PATROCINADOR SET NOMBRE = ?, DETALLES = ?, DIRECCION = ?, TELEFONO = ?, EMAIL = ?, TIPO = ? WHERE PATROCINADOR_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {patrocinador.getNombrePatrocinador(),patrocinador.getDetalles(), patrocinador.getDireccion(), patrocinador.getTelefono(), 
												patrocinador.getEmail(), patrocinador.getTipo(), patrocinador.getPatrocinadorId()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatPatrocinadorDao|updateReg|:"+ex);		
		}
		
		return ok;
	}	
	
	public boolean deleteReg( String patrocinadorId ) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.CAT_PATROCINADOR WHERE PATROCINADOR_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {patrocinadorId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatPatrocinadorDao|deleteReg|:"+ex);			
		}
		
		return ok;
	}
	
public CatPatrocinador mapeaRegId(  String patrocinadorId) {
		
		CatPatrocinador patrocinador 	= new CatPatrocinador();		
		try{
			String comando = "SELECT COUNT(PATROCINADOR_ID) FROM ENOC.CAT_PATROCINADOR WHERE PATROCINADOR_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {patrocinadorId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				comando = "SELECT PATROCINADOR_ID, NOMBRE, DETALLES, DIRECCION, TELEFONO, EMAIL, TIPO FROM ENOC.CAT_PATROCINADOR WHERE PATROCINADOR_ID = TO_NUMBER(?,'999')";
				patrocinador = enocJdbc.queryForObject(comando, new CatPatrocinadorMapper(), parametros);
			}						
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatPatrocinadorDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		
		return patrocinador;
	}
	
	public boolean existeReg( String patrocinadorId) {
		boolean 		ok 		= false;	
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_PATROCINADOR WHERE PATROCINADOR_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {patrocinadorId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatPatrocinadorDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoReg() {
		String maximo 			= "01";		
		try{
			String comando = "SELECT COALESCE(MAX(PATROCINADOR_ID)+1,1) AS MAXIMO FROM ENOC.CAT_PATROCINADOR";
			if (enocJdbc.queryForObject(comando, Integer.class) >= 1){
				maximo = enocJdbc.queryForObject(comando, String.class);
				if (maximo.length()==1) maximo = "0"+maximo;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatPatrocinadorDao|maximoReg|:"+ex);
		}
		
		return maximo;		
	}
	
	// Llena el listor con todos los elementos de la tabla	
	public List<CatPatrocinador> lisTodos( String orden ) {
		
		List<CatPatrocinador> lista = new ArrayList<CatPatrocinador>();		
		try{
			String comando = "SELECT PATROCINADOR_ID, NOMBRE, DETALLES, DIRECCION, TELEFONO, EMAIL, TIPO FROM ENOC.CAT_PATROCINADOR "+ orden;				
			lista = enocJdbc.query(comando, new CatPatrocinadorMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatPatrocinadorDao|lisTodos|:"+ex);
		}		
		return lista;
	}
	
	public HashMap<String, CatPatrocinador> mapaCatPatrocinador() {

		HashMap<String, CatPatrocinador> map = new HashMap<String, CatPatrocinador>();
		List<CatPatrocinador> list = new ArrayList<CatPatrocinador>();
		try {
			String comando = "SELECT PATROCINADOR_ID, NOMBRE, DETALLES, DIRECCION, TELEFONO, EMAIL, TIPO FROM ENOC.CAT_PATROCINADOR";
			list = enocJdbc.query(comando, new CatPatrocinadorMapper());
			for (CatPatrocinador alum : list) {
				map.put(alum.getPatrocinadorId(), alum);
				
			}
		} catch (Exception ex) {
			System.out.println("Error - aca.trabajo.spring.CatPatrocinadorDao|mapaCatPatrocinador|:" + ex);
		}
		return map;
	}
	
}

	

