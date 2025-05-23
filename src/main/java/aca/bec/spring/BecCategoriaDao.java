package aca.bec.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class BecCategoriaDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( BecCategoria becCategoria) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.BEC_CATEGORIA"+ 
				"(CATEGORIA_ID, CATEGORIA_NOMBRE, USUARIO, ESTADO, PDF)"+
				" VALUES( TO_NUMBER(?,'999'), ?, ?, ?, ?)";
			Object[] parametros = new Object[] {becCategoria.getCategoriaId(), becCategoria.getCategoriaNombre(),becCategoria.getUsuario(),becCategoria.getEstado(), becCategoria.getPdf()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecCategoriaDao|insertReg|:"+ex);	
		}
		
		return ok;
	}	
	
	public boolean updateReg( BecCategoria becCategoria) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.BEC_CATEGORIA"+ 
				" SET CATEGORIA_NOMBRE = ?, USUARIO = ?, ESTADO = ?, PDF = ?"+				
				" WHERE CATEGORIA_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {becCategoria.getCategoriaNombre(),becCategoria.getUsuario(), becCategoria.getEstado(), becCategoria.getPdf(), becCategoria.getCategoriaId()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecCategoriaDao|updateReg|:"+ex);		
		}
		
		return ok;
	}
		
	public boolean deleteReg( String categoriaId) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.BEC_CATEGORIA WHERE CATEGORIA_ID = TO_NUMBER(?,'999')";	
			Object[] parametros = new Object[] {categoriaId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecCategoriaDao|deleteReg|:"+ex);			
		}
		
		return ok;
	}
	
	public BecCategoria mapeaRegId( String categoriaId){
		
		BecCategoria becCategoria = new BecCategoria();
		
		try{
			String comando = "SELECT  * FROM ENOC.BEC_CATEGORIA WHERE CATEGORIA_ID = TO_NUMBER(?,'999')"; 
			Object[] parametros = new Object[] {categoriaId};
			becCategoria = enocJdbc.queryForObject(comando, new BecCategoriaMapper(),parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecCategoriaDao|mapeaRegId|:"+ex);
		}
		 
		return becCategoria;
	}
	
	public boolean existeReg( String categoriaId) {
		
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.BEC_CATEGORIA WHERE CATEGORIA_ID = TO_NUMBER(?,'999') ";
			Object[] parametros = new Object[] {categoriaId};
			if (enocJdbc.queryForObject(comando, Integer.class,parametros) >= 1) {
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecCategoriaDao|existeReg|:"+ex);
		}
		
		return ok;
	}
		
	public String maximo() {
		
		String  maximo 		= "";
		
		try{
			String comando = "SELECT COALESCE(MAX(CATEGORIA_ID+1), 1) AS MAXIMO FROM ENOC.BEC_CATEGORIA";			
			if (enocJdbc.queryForObject(comando, Integer.class) >= 1) {
				maximo = enocJdbc.queryForObject(comando, String.class);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecCategoriaDao|maximo|:"+ex);
		}
		
		return maximo;
	}
	
	public String getNombreCategoria( String categoriaId) {
		
		String nombre	 		= "";
		
		try{
			String comando = "SELECT CATEGORIA_NOMBRE FROM ENOC.BEC_CATEGORIA WHERE CATEGORIA_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {categoriaId};
			nombre = enocJdbc.queryForObject(comando, String.class,parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecCategoriaDao|getNombreCategoria|:"+ex);
		}
		
		return nombre;
	}
	
	public boolean getTienePuesto( String categoriaId) {
		
		boolean ok 				= false; 
		
		try{
			String comando = "SELECT CATEGORIA_NOMBRE FROM ENOC.BEC_CATEGORIA WHERE CATEGORIA_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {categoriaId};
			if (enocJdbc.queryForObject(comando, Integer.class,parametros) >= 1) {
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecCategoriaDao|getTienePuesto|:"+ex);
		}
		
		return ok;
	}
	
	public List<BecCategoria> getListAll( String orden) {
			
		List<BecCategoria> lista 		= new ArrayList<BecCategoria>();
		
		try{
			String comando = "SELECT * FROM ENOC.BEC_CATEGORIA "+orden;		
			lista = enocJdbc.query(comando, new BecCategoriaMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecCategoriaDao|getListAll|:"+ex);
		}
		
		return 	lista;
	}
	
	public List<BecCategoria> getListActivos( String orden) {
		
		List<BecCategoria> lista 		= new ArrayList<BecCategoria>();
		
		try{
			String comando = "SELECT * FROM ENOC.BEC_CATEGORIA WHERE ESTADO = 'A' " +orden;		
			lista = enocJdbc.query(comando, new BecCategoriaMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecCategoriaDao|getListAll|:"+ex);
		}
		
		return 	lista;
	}
	
	
	public HashMap<String, String> getMapCategorias( ){
		
		HashMap<String, String> mapa 	= new HashMap<String, String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();
		
		try{			
			String comando = "SELECT CATEGORIA_ID AS LLAVE, CATEGORIA_NOMBRE AS VALOR FROM ENOC.BEC_CATEGORIA ";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa map : lista ){
				mapa.put(map.getLlave(), (String)map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecCategoriaDao|getMapCategorias|:"+ex);
		}
		
		return mapa;		
	}
	
	public HashMap<String, BecCategoria> mapaCategorias( ){		
		HashMap<String, BecCategoria> mapa 	= new HashMap<String, BecCategoria>();
		List<BecCategoria> lista 		= new ArrayList<BecCategoria>();		
		try{			
			String comando = "SELECT CATEGORIA_ID, CATEGORIA_NOMBRE, USUARIO, ESTADO, PDF FROM ENOC.BEC_CATEGORIA";
			lista = enocJdbc.query(comando, new BecCategoriaMapper());
			for(BecCategoria categoria : lista ){
				mapa.put(categoria.getCategoriaId(), categoria);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecCategoriaDao|mapaCategorias|:"+ex);
		}
		
		return mapa;		
	}
	
}
