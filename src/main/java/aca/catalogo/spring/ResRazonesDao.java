package aca.catalogo.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ResRazonesDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(ResRazones resRazones ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.RES_RAZONES(RAZON, DESCRIPCION)"
					+ " VALUES(TO_NUMBER(?,'99'), ?)";
			Object[] parametros = new Object[] {resRazones.getRazon(),resRazones.getDescripcion()};
			
			if (enocJdbc.update(comando,parametros)==1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.ResRazonesDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg(ResRazones resRazones) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.RES_RAZONES" 
				+" SET DESCRIPCION = ?"
				+" WHERE RAZON = ? ";
			Object[] parametros = new Object[] {resRazones.getDescripcion(),resRazones.getRazon()};
			
			if (enocJdbc.update(comando,parametros)==1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.ResRazonesDao|updateReg|:"+ex);
		}
		
		return ok;
	}	
	
	public boolean deleteReg(String razon) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.RES_RAZONES WHERE RAZON = ? ";
			Object[] parametros = new Object[] {razon};
			
			if (enocJdbc.update(comando,parametros)==1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.ResRazonesDao|deleteReg|: "+ex);
		}
		
		return ok;
	}
	
	public ResRazones mapeaRegId(String razon) {
		ResRazones resRazones = new ResRazones();
		 
		try{
			String comando = "SELECT RAZON, DESCRIPCION FROM ENOC.RES_RAZONES WHERE RAZON = ?";
			Object[] parametros = new Object[] {razon};
			resRazones = enocJdbc.queryForObject(comando, new ResRazonesMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.ResRazonesDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		
		return resRazones;
	}
	
	public boolean existeReg(String razon) {
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.RES_RAZONES WHERE RAZON = ?";
			Object[] parametros = new Object[] {razon};
			
			if (enocJdbc.queryForObject(comando, Integer.class, parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.ResRazonesDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoReg() {
		String maximo = "1";
		
		try{
			String comando = "SELECT COALESCE(MAX(TO_NUMBER(RAZON,'99')+1),1) AS MAXIMO FROM ENOC.RES_RAZONES ";
			Object[] parametros = new Object[] {};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
 				maximo = enocJdbc.queryForObject(comando, String.class, parametros);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.ResRazonesDao|maximoReg|:"+ex);
		}
		
		return maximo;		
	}
	
	public List<ResRazones> getListAll(String orden ) {
		List<ResRazones> lista	= new ArrayList<ResRazones>();
		
		try{
			String comando = "SELECT RAZON, DESCRIPCION FROM ENOC.RES_RAZONES "+ orden;			
			lista = enocJdbc.query(comando, new ResRazonesMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.ResRazonesDao|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String, String> mapaRazones(){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista	    	= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT RAZON AS LLAVE, DESCRIPCION AS VALOR FROM ENOC.RES_RAZONES ";
			lista = enocJdbc.query(comando, new aca.MapaMapper());			
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());					
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.ResRazonesDao|mapaRazones|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapaRazonesUsadas() {
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT RAZON AS LLAVE, COUNT(RAZON) AS VALOR FROM ENOC.RES_DATOS GROUP BY RAZON";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa obj : lista){
				mapa.put(obj.getLlave(), obj.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.ResRazonesDao|mapaRazonesUsadas|:"+ex);
		}
		return mapa;
	}
}
