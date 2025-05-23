// Clase Util para la tabla de Cat_Division
package aca.catalogo.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CatTipoCalDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( CatTipoCal tipocal ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.CAT_TIPOCAL(TIPOCAL_ID, NOMBRE_TIPOCAL, NOMBRE_CORTO)"
					+ " VALUES( ?, ?, ?)";
			Object[] parametros = new Object[] {tipocal.getTipoCalId(),tipocal.getNombreTipoCal(), tipocal.getNombreCorto()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatTipoCalDao|insertReg|:"+ex);
		}
		
		return ok;
	}	
	
	public boolean updateReg( CatTipoCal tipocal ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.CAT_TIPOCAL SET NOMBRE_TIPOCAL = ?, NOMBRE_CORTO = ? WHERE TIPOCAL_ID = ? ";
			Object[] parametros = new Object[] {tipocal.getNombreTipoCal(), tipocal.getNombreCorto(), tipocal.getTipoCalId()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatTipoCalDao|updateReg|:"+ex);		
		}
		
		return ok;
	}
	
	
	public boolean deleteReg( String tipoCalId ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CAT_TIPOCAL WHERE TIPOCAL_ID = ?";
			Object[] parametros = new Object[] {tipoCalId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatTipoCalDao|deleteReg|:"+ex);			
		}
		
		return ok;
	}
	
	public CatTipoCal mapeaRegId(  String tipoCalId) {
		
		CatTipoCal tipoCal = new CatTipoCal();
		
		try{
			String comando = "SELECT TIPOCAL_ID, NOMBRE_TIPOCAL, NOMBRE_CORTO FROM ENOC.CAT_TIPOCAL WHERE TIPOCAL_ID = ?";
			Object[] parametros = new Object[] {tipoCalId};
			tipoCal = enocJdbc.queryForObject(comando, new CatTipoCalMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatTipoCalDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		
		return tipoCal;
	}
	
	public boolean existeReg( String tipoCalId) {
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_TIPOCAL WHERE TIPOCAL_ID = ?";
			Object[] parametros = new Object[] {tipoCalId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatTipoCalDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoReg(){
		String maximo = "1";		
		try{
			String comando = "SELECT COALESCE(MAX(TIPOCAL_ID)+1,1) FROM ENOC.CAT_TIPOCAL";			
			maximo = enocJdbc.queryForObject(comando,String.class);	
			if(maximo.length()==1){
				maximo = "0"+maximo;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatTipoCalDao|maximoReg|:"+ex);
		}
		
		return maximo;
	}
	
	public String getNombreTipoCal( String tipoCal) {
		
		String nombre		= "vacio";
		
		try{
			String comando = "SELECT NOMBRE_TIPOCAL FROM ENOC.CAT_TIPOCAL WHERE TIPOCAL_ID = ?";		
			Object[] parametros = new Object[] {tipoCal};
			nombre = enocJdbc.queryForObject(comando, String.class, parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatTipoCalDao|getNombreTipoCal|:"+ex);
		}
		
		return nombre;
	}
	
	public String getNombreCorto( String tipoCal) {
		
		String nombre		= "vacio";
		
		try{
			String comando = "SELECT NOMBRE_CORTO FROM ENOC.CAT_TIPOCAL WHERE TIPOCAL_ID = ?";					
			Object[] parametros = new Object[] {tipoCal};
			nombre = enocJdbc.queryForObject(comando, String.class, parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatTipoCalDao|getNombreCorto|:"+ex);
		}
		
		return nombre;
	}
		
	public List<CatTipoCal> getListAll( String orden ) {
		
		List<CatTipoCal> lista	= new ArrayList<CatTipoCal>();
		
		try{
			String comando = "SELECT TIPOCAL_ID, NOMBRE_TIPOCAL, NOMBRE_CORTO FROM ENOC.CAT_TIPOCAL "+ orden;			
			lista = enocJdbc.query(comando, new CatTipoCalMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatTipoCalDao|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String,CatTipoCal> getMapAll( String orden ) {
		
		HashMap<String,CatTipoCal> mapa = new HashMap<String,CatTipoCal>();
		List<CatTipoCal> lista			= new ArrayList<CatTipoCal>();
		
		try{
			String comando = "SELECT TIPOCAL_ID, NOMBRE_TIPOCAL, NOMBRE_CORTO FROM ENOC.CAT_TIPOCAL "+ orden;
			lista = enocJdbc.query(comando, new CatTipoCalMapper());
			for (CatTipoCal tipo : lista){
				mapa.put(tipo.getTipoCalId(), tipo);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatTipoCalDao|getMapAll|:"+ex);
		}
		
		return mapa;
	}
	
	public String getNombre( String tipocalId, String opcion ) {
		
		String comando	= "";
		String nombre		= "X";
		
		try{
			if (opcion.equals("1")){
				comando = "SELECT NOMBRE_TIPOCAL AS NOMBRE FROM ENOC.CAT_TIPOCAL WHERE TIPOCAL_ID = ?"; 
			}else{
				comando = "SELECT NOMBRE_CORTO AS NOMBRE FROM ENOC.CAT_TIPOCAL WHERE TIPOCAL_ID = ?"; 
			}
			nombre = enocJdbc.queryForObject(comando, String.class, tipocalId);			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatTipoCalDao|getNombre|:"+ex);
		}
		
		return nombre;
	}
	
	public HashMap<String,String> mapTipoCal() {
		
		HashMap<String,String> mapa 	= new HashMap<String,String>();
		List<CatTipoCal> lista			= new ArrayList<CatTipoCal>();	
		String comando					= "";	 
		
		try{			
			comando = "SELECT TIPOCAL_ID, NOMBRE_TIPOCAL, NOMBRE_CORTO FROM ENOC.CAT_TIPOCAL";				
			lista = enocJdbc.query(comando, new CatTipoCalMapper());
			for (CatTipoCal tipo : lista){
				mapa.put(tipo.getTipoCalId(), tipo.getNombreTipoCal());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatTipoCalDao|mapTipoCal|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapTipoCalCorto( ) {
		
		HashMap<String,String> mapa 	= new HashMap<String,String>();
		List<CatTipoCal> lista			= new ArrayList<CatTipoCal>();	
		String comando					= "";	 
		
		try{			
			comando = "SELECT TIPOCAL_ID, NOMBRE_TIPOCAL, NOMBRE_CORTO FROM ENOC.CAT_TIPOCAL";				
			lista = enocJdbc.query(comando, new CatTipoCalMapper());
			for (CatTipoCal tipo : lista){
				mapa.put(tipo.getTipoCalId(), tipo.getNombreCorto());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatTipoCalDao|mapTipoCalCorto|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapaCalUsadas() {
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT TIPOCAL_ID AS LLAVE, COUNT(TIPOCAL_ID) AS VALOR FROM ENOC.KRDX_CURSO_ACT GROUP BY TIPOCAL_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa obj : lista){
				mapa.put(obj.getLlave(), obj.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatTipoCalDao|mapaCalUsadas|:"+ex);
		}
		
		return mapa;
	}
}