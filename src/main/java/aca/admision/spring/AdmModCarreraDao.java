package aca.admision.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
@Component
public class AdmModCarreraDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( AdmModCarrera modCarrera) {
		boolean ok = false;		
		try{
			String comando ="INSERT INTO SALOMON.ADM_MODCARRERA (MODALIDAD_ID, CARRERA_ID) VALUES(TO_NUMBER(?,'99'), ?)";
			Object[] parametros = new Object[] { modCarrera.getModalidadId(), modCarrera.getCarreraId() };			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmModCarrera|insertReg|:"+ex);
		}
		return ok;
	}
	
	public boolean deleteReg( String modalidadId, String carreraId) {
		boolean ok = false;		
		try{
			String comando ="DELETE FROM SALOMON.ADM_MODCARRERA WHERE MODALIDAD_ID = TO_NUMBER(?,'99') AND CARRERA_ID = ?";
			Object[] parametros = new Object[] { modalidadId, carreraId };			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmModCarrera|deleteReg|:"+ex);			
		}
				
		return ok;
	}
	
	public AdmModCarrera mapeaRegId( String modalidadId, String carreraId ) {
		AdmModCarrera modCarrera = new AdmModCarrera();
		try{
			String comando ="SELECT MODALIDAD_ID, CARRERA_ID FROM SALOMON.ADM_MODCARRERA WHERE MODALIDAD_ID = TO_NUMBER(?,'99') AND CARRERA_ID = ?";		
			Object[] parametros = new Object[] { modalidadId, carreraId };			
			modCarrera = enocJdbc.queryForObject(comando, new AdmModCarreraMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmModCarrera|mapeaRegId|:"+ex);			
		}		
		return modCarrera;
	}
	
	public boolean existeReg( String modalidadId, String carreraId ){
		boolean 		ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM SALOMON.ADM_MODCARRERA WHERE MODALIDAD_ID = TO_NUMBER(?,'99') AND CARRERA_ID = ?";
			Object[] parametros = new Object[] { modalidadId, carreraId };
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmModCarrera|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public List<AdmModCarrera> getAll( String orden) {
		
		List<AdmModCarrera> lista	= new ArrayList<AdmModCarrera>();
		
		try{
			String comando = "SELECT MODALIDAD_ID, CARRERA_ID FROM SALOMON.ADM_MODCARRERA " + orden;			
			lista = enocJdbc.query(comando, new AdmModCarreraMapper());	
			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmModCarrera|getAll|:"+ex);
		}
		
		return lista;
	}
	
	public List<AdmModCarrera> lisPorModalidad( String modalidadId, String orden) {
		
		List<AdmModCarrera> lista	= new ArrayList<AdmModCarrera>();		
		try{
			String comando = "SELECT MODALIDAD_ID, CARRERA_ID FROM SALOMON.ADM_MODCARRERA WHERE MODALIDAD_ID = TO_NUMBER(?,'99') " + orden;
			Object[] parametros = new Object[] { modalidadId };
			lista = enocJdbc.query(comando, new AdmModCarreraMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmModCarrera|getAll|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String, String> mapaModCarrera() {
		
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT MODALIDAD_ID||CARRERA_ID AS LLAVE, COUNT(*) AS VALOR FROM SALOMON.ADM_MODCARRERA GROUP BY MODALIDAD_ID||CARRERA_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper());	
			for (aca.Mapa map : lista) {
				mapa.put(map.getLlave() , map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmModCarreraDao|mapaModCarrera:"+ex);
		}
		
		return mapa;
	}
}