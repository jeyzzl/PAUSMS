// Clase Util para la tabla de Mapa_Plan
package aca.plan.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class MapaVersionDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( MapaVersion version ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.MAPA_VERSION (VERSION_ID, VERSION_NOMBRE)"
					+ " VALUES( TO_NUMBER(?,'99'), ?)";

			Object[] parametros = new Object[] {version.getVersionId(),version.getVersionNombre()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaVersionDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg( MapaVersion version ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.MAPA_VERSION SET VERSION_NOMBRE = ? WHERE VERSION_ID = TO_NUMBER(?,'99')";			
			Object[] parametros = new Object[] {version.getVersionNombre(),version.getVersionId()}; 				
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaVersionDao|updateReg|:"+ex);		
		}
		return ok;
	}
	
	public boolean deleteReg( String planId, String ciclo ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.MAPA_VERSION WHERE VERSION_ID = TO_NUMBER(?,'99')";			
			Object[] parametros = new Object[] {planId,ciclo};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaVersionDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public MapaVersion mapeaRegId(  String versionId) {
		MapaVersion version = new MapaVersion();
		try{
			String comando = "SELECT VERSION_ID, VERSION_NOMBRE FROM ENOC.MAPA_VERSION WHERE VERSION_ID = TO_NUMBER(?,'99')";			
			Object[] parametros = new Object[] {versionId};
			version = enocJdbc.queryForObject(comando, new MapaVersionMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaVersionDao|mapeaRegId|:"+ex);
		}
		return version;		
	}	
	
	public boolean existeReg( String planId, String ciclo) {
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.MAPA_VERSION WHERE VERSION_ID = TO_NUMBER(?,'99')"; 
			
			Object[] parametros = new Object[] {planId,ciclo};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaVersionDao|existeReg|:"+ex);
		}
		return ok;
	}	
	
	public List<MapaVersion> LisTodos( String orden ) {
		List<MapaVersion> lista	= new ArrayList<MapaVersion>();
		try{
			String comando = "SELECT VERSION_ID, VERSION_NOMBRE FROM ENOC.MAPA_VERSION "+orden;	
			lista = enocJdbc.query(comando, new MapaVersionMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaVersionDao|LisTodos|:"+ex);
		}		
		return lista;
	}	
	
	public HashMap<String, String> mapaVersiones() {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT VERSION_ID AS LLAVE, VERSION_NOMBRE AS VALOR FROM ENOC.MAPA_VERSION";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaVersionDao|mapaVersion|:"+ex);
		}
		return mapa;
	}
}