package aca.admision.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class AdmFormatoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( AdmFormato formato ) {
		boolean ok = false;
		try{
			String comando = "INSERT INTO SALOMON.ADM_FORMATO (FORMATO_ID, FORMATO_NOMBRE, ARCHIVO)"+
				" VALUES(TO_NUMBER(?,'99') ?, ?)";
			Object[] parametros = new Object[] { formato.getFormatoId(), formato.getFormatoNombre(), formato.getArchivo()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmFormato|insertReg|:"+ex);
		}
		
		return ok;
	}
			
	public boolean updateReg( AdmFormato formato ) {
		boolean ok = false;		
		try{
			String comando = "UPDATE SALOMON.ADM_FORMATO SET FORMATO_NOMBRE = ?, ARCHIVO = ? WHERE FORMATO_ID = TO_NUMBER(?,'99')";					
			Object[] parametros = new Object[] { formato.getFormatoNombre(), formato.getArchivo(), formato.getFormatoId()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmFormato|updateReg|:"+ex);
		}
		return ok;
	}	
	
	public boolean deleteReg( String formatoId) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM SALOMON.ADM_FORMATO WHERE FORMATO_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] { formatoId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmFormato|deleteReg|:"+ex);
			ok = false;
		}		
		return ok;
	}
	
	public AdmFormato mapeaRegId( String formatoId ) {
		
		AdmFormato formato = new AdmFormato();
		
		try{
			String comando = "SELECT * FROM SALOMON.ADM_FORMATO WHERE FORMATO_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] { formatoId};
			formato = enocJdbc.queryForObject(comando, new AdmFormatoMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmFormato|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		
		return formato;
	}
	
	public boolean existeReg(String formatoId) {
		boolean 		ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM SALOMON.ADM_FORMATO WHERE FORMATO_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] { formatoId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmFormato|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public List<AdmFormato> getAll( String orden) {
		List<AdmFormato> lista	= new ArrayList<AdmFormato>();
		try{
			String comando = "SELECT FORMATO_ID, FORMATO_NOMBRE, ARCHIVO FROM SALOMON.ADM_FORMATO "+ orden; 
			lista = enocJdbc.query(comando, new AdmFormatoMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmFormatoUtil|getAll|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String, AdmFormato> mapaFormatos() {
		
		HashMap<String, AdmFormato> mapa = new HashMap<String, AdmFormato>();
		List<AdmFormato> lista	= new ArrayList<AdmFormato>();
		try{
			String comando = "SELECT FORMATO_ID, FORMATO_NOMBRE, ARCHIVO FROM SALOMON.ADM_FORMATO";
			lista = enocJdbc.query(comando, new AdmFormatoMapper());
			for (AdmFormato map : lista) {
				mapa.put(map.getFormatoId(), map);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmFormatoDao|mapaFormatos:"+ex);
		}
		
		return mapa;
	}
	
}