// Clase para la tabla Res

package aca.residencia.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Component
public class ResRazonDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( ResRazon razon ){
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.RES_RAZONES"+ 
				"(RAZON, DESCRIPCION) VALUES( TO_NUMBER(?,'999'), ?)";
			Object[] parametros = new Object[] {razon.getRazon(), razon.getDescripcion()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.ResRazon|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg( ResRazon razon ){
		boolean ok = false;

		try{
			String comando = "UPDATE ENOC.RES_RAZONES "+ 
				"SET DESCRIPCION = ? WHERE RAZON= TO_NUMBER(?, '999') ";
			Object[] parametros = new Object[] {razon.getDescripcion(), razon.getRazon()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error-aca.residencia.ResRazon|updateReg|: "+ex);		
		}
		
		return ok;
	}
	
	
	public boolean deleteReg( String razon ){
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.RES_RAZONES "+ 
				"WHERE RAZON = ? ";
			Object[] parametros = new Object[] {razon};
			if (enocJdbc.update(comando, parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.ResRazon|deleteReg|: "+ex);			
		}
		
		return ok;
	}
	
	public ResRazon mapeaRegId( String razon ){
		
		ResRazon raz = new ResRazon();		
		try{
			String comando = "SELECT RAZON, DESCRIPCION FROM ENOC.RES_RAZONES WHERE RAZON = ? "; 
			Object[] parametros = new Object[] {razon};
			raz = enocJdbc.queryForObject(comando, new ResRazonMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.ResRazon|mapeaRegId|:"+ex);

		}
		return raz;
	}
	
	public boolean existeReg( String razon ){
		boolean 			ok 	= false;		
		try{
			String comando = "SELECT COUNT (*) FROM ENOC.RES_RAZONES WHERE RAZON = ?";
			Object[] parametros = new Object[] {razon};
			if (enocJdbc.update(comando, parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error: aca.residencia.ResRazon|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String nombreRazon( String razon){

		String nombre			= "X";		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.RES_RAZONES WHERE RAZON = ?";
			Object[] parametros = new Object[] {razon};
 			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
 				comando = "SELECT DESCRIPCION FROM ENOC.RES_RAZONES WHERE RAZON = ?";
 				nombre = enocJdbc.queryForObject(comando,String.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error: aca.residencia.ResRazon|nombreRazon|:"+ex);
		}
		
		return nombre;
	}
	
	public String maximoReg(){
		
		String maximo 			= "1";
		
		try{
			String comando = "SELECT MAX(RAZON)+1 MAXIMO FROM ENOC.RES_RAZONES"; 
 			if (enocJdbc.queryForObject(comando,Integer.class)>=1){
 				maximo = enocJdbc.queryForObject(comando,String.class);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.ResRazon|maximoReg|:"+ex);
		}
		
		return maximo;
	}
	
	public List<ResRazon> getListAll( String orden ){
		
		List<ResRazon> lista	= new ArrayList<ResRazon>();
		
		try{
			String comando = "SELECT RAZON, DESCRIPCION FROM ENOC.RES_RAZONES "+orden; 
			lista = enocJdbc.query(comando, new ResRazonMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.RazonUtil|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	public List<ResRazon> getLista( String razon, String orden ){
		
		List<ResRazon> lista	= new ArrayList<ResRazon>();
		
		try{
			String comando = "SELECT RAZON, DESCRIPCION FROM ENOC.RES_RAZONES "+ 
				"WHERE RAZON = ? "+orden;
			lista = enocJdbc.query(comando, new ResRazonMapper(),razon);
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.RazonUtil|getLista|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String, String> getMapRazon(){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista	    	= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT RAZON AS LLAVE, DESCRIPCION AS VALOR FROM ENOC.RES_RAZONES ";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());					
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.InternosUtil|mapaEdadInternos|:"+ex);
		}
		
		return mapa;
	}

}