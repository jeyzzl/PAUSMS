package aca.admision.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AdmAccesoVoboDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(AdmAccesoVobo objeto){
		boolean ok = false;
		
		Object[] parametros = new Object[] {
			objeto.getCodigoPersonal(),objeto.getCarreraId()
		};

		try{
			String comando = "INSERT INTO SALOMON.ADM_ACCESO_VOBO (CODIGO_PERSONAL, CARRERA_ID)"
				+ " VALUES(?, ?)";
						
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
 			
		}catch(Exception ex){
			System.out.println("Error - adm.admision.spring.AdmAccesoVoboDao|insertReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean deleteReg(String codigoPersonal, String carreraId){
		boolean ok = false;
		
		Object[] parametros = new Object[] {codigoPersonal,carreraId};

		try{
			String comando = "DELETE FROM SALOMON.ADM_ACCESO_VOBO WHERE CODIGO_PERSONAL = ? AND CARRERA_ID = ?";
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - adm.admision.spring.AdmAccesoVoboDao|deleteReg|:"+ex);
		}
		
		return ok;
	}
	
	public AdmAccesoVobo mapeaRegId(String codigoPersonal, String carreraId){
		AdmAccesoVobo objeto = new AdmAccesoVobo();
		
		Object[] parametros = new Object[] {codigoPersonal,carreraId};
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, CARRERA_ID"
					+ " FROM SALOMON.ADM_ACCESO_VOBO WHERE CODIGO_PERSONAL = ? AND CARRERA_ID = ?";
			
			objeto = enocJdbc.queryForObject(comando, new AdmAccesoVoboMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - adm.admision.spring.AdmAccesoVoboDao|deleteReg|:"+ex);
		}
		
		return objeto;
	}
	
	public boolean existeReg(String codigoPersonal, String carreraId){
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT(*) FROM SALOMON.ADM_ACCESO_VOBO WHERE CODIGO_PERSONAL = ? AND CARRERA_ID = ?";
			
			Object[] parametros = new Object[] {codigoPersonal,carreraId};
			
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - adm.admision.spring.AdmAccesoVoboDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public List<AdmAccesoVobo> lista(String carreraId){		
		List<AdmAccesoVobo> lista = new ArrayList<AdmAccesoVobo>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL,CARRERA_ID FROM SALOMON.ADM_ACCESO_VOBO WHERE CARRERA_ID = ?";
			lista = enocJdbc.query(comando, new AdmAccesoVoboMapper(),carreraId);
		
		}catch(Exception ex){
			System.out.println("Error - adm.admision.spring.AdmAccesoVoboDao|lista|:"+ex);
		}
		
		return lista;
	}
	
	public List<String> listAccesoCarrea(String carreraId){
		List<String> lista	= new ArrayList<String>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL FROM SALOMON.ADM_ACCESO_VOBO WHERE CARRERA_ID = ?";	
			Object[] parametros = new Object[] {carreraId};
			lista = enocJdbc.queryForList(comando, String.class, parametros);			
		}catch(Exception ex){
			System.out.println("Error - adm.admision.spring.AdmAccesoVoboDao|listAccesoCarrea|:"+ex);
		}		
		return lista;
	}
	
	public HashMap<String,String> mapAcceCarrera() {
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT CARRERA_ID AS LLAVE, COUNT(CARRERA_ID) AS VALOR FROM SALOMON.ADM_ACCESO_VOBO GROUP BY CARRERA_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - adm.admision.spring.AdmAccesoVoboDao|mapAcceCarrera|:"+ex);
		}
		
		return mapa;
	}

}
