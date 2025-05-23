package aca.admision.spring;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AdmPruebaDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(AdmPrueba objeto) {
		boolean ok = false;		
		try{
			String comando = "INSERT INTO SALOMON.ADM_PRUEBA(PRUEBA_ID, NOMBRE, DESCRIPCION, ACCION)"
					+ " VALUES(TO_NUMBER(?,'99'),?, ?, ?)";
			Object[] parametros = new Object[] {objeto.getPruebaId(),objeto.getNombre(),objeto.getDescripcion(), objeto.getAccion()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmPruebaDao|insertReg|:"+ex);
		}
		return ok;
	}

	public boolean updateReg(AdmPrueba objeto) {
		boolean ok = false;		
		try{
			String comando = "UPDATE SALOMON.ADM_PRUEBA"
					+ " SET NOMBRE = ?,"
					+ " DESCRIPCION = ?,"
					+ " ACCION = ?"
					+ " WHERE PRUEBA_ID = TO_NUMBER(?,'99')";			
			Object[] parametros = new Object[] {
					objeto.getNombre(), objeto.getDescripcion(),objeto.getAccion(),objeto.getPruebaId()};			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.admision.AdmPruebaDao|updateReg|:"+ex);		
		}		
		return ok;
	}
	
	public boolean deleteReg(String pruebaId) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM SALOMON.ADM_PRUEBA WHERE PRUEBA_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[]{pruebaId};
			if (enocJdbc.update(comando,parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.AdmPruebaDao|deleteReg|:"+ex);	
		}
		return ok;
	}
	
	public AdmPrueba mapeaRegId(String pruebaId){
		AdmPrueba objeto = new AdmPrueba();		 
		try{
			String comando = "SELECT PRUEBA_ID, NOMBRE, DESCRIPCION, ACCION FROM SALOMON.ADM_PRUEBA WHERE PRUEBA_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {pruebaId};
			objeto = enocJdbc.queryForObject(comando, new AdmPruebaMapper(),parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.AdmPruebaDao|mapeaRegId|:"+ex);			
		}		
		return objeto;
	}
	
	public boolean existeReg(String pruebaId){
		boolean ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM SALOMON.ADM_PRUEBA WHERE PRUEBA_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {pruebaId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.admision.AdmPruebaDao|existeReg|:"+ex);
		}		
		return ok;
	}	

	public List<AdmPrueba> lisTodos(String orden) {
		List<AdmPrueba> lista = new ArrayList<AdmPrueba>();
		try{
			String comando = "SELECT PRUEBA_ID, NOMBRE, DESCRIPCION, ACCION FROM SALOMON.ADM_PRUEBA " + orden;
			lista = enocJdbc.query(comando, new AdmPruebaMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmPruebaDao|lisTodos|:"+ex);
		}		
		return lista;
	}	
	
	public HashMap<String, AdmPrueba> mapaTodos (){
		HashMap<String, AdmPrueba> mapa = new HashMap<String, AdmPrueba>();
		List<AdmPrueba> lista = new ArrayList<AdmPrueba>();		
		try {
			String comando = "SELECT PRUEBA_ID, NOMBRE, DESCRIPCION, ACCION FROM SALOMON.ADM_PRUEBA";
			lista = enocJdbc.query(comando, new AdmPruebaMapper());
			for (AdmPrueba objeto : lista ){
				mapa.put(objeto.getPruebaId(), objeto);
			}
		}catch(Exception ex) {
			System.out.println("Error - aca.admision.spring.AdmPruebaDao|mapaTodos|:"+ex);
		}
		return mapa;
	}

}
