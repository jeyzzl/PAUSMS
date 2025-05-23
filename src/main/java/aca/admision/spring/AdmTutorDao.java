package aca.admision.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AdmTutorDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(AdmTutor admTutor) throws Exception{
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO SALOMON.ADM_TUTOR"+ 
					" (FOLIO, TUTOR, NOMBRE, CALLE, NUMERO, COLONIA, CODIGOPOSTAL, PAIS_ID, ESTADO_ID, CIUDAD_ID, TELEFONO, ESTADO, CIUDAD) "+
					" VALUES( TO_NUMBER(?,'9999999'), TO_NUMBER(?,'9'), ?, ?, ?, ?, ?, " +
					" TO_NUMBER(?,'999'), TO_NUMBER(?,'999'), TO_NUMBER(?,'999'), ?,?, ? )";		
			Object[] parametros = new Object[] {
				admTutor.getFolio(),admTutor.getTutor(),admTutor.getNombre(),admTutor.getCalle(),admTutor.getNumero(),admTutor.getColonia(),
				admTutor.getCodigoPostal(),admTutor.getPaisId(),admTutor.getEstadoId(),
				admTutor.getCiudadId(),admTutor.getTelefono(),admTutor.getEstado(),admTutor.getCiudad()
 		 	};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - adm.admision.spring.AdmTutorDao|insertReg|:"+ex);
		}

		return ok;
	}
	
	
	public boolean updateReg(AdmTutor admTutor) {
		boolean ok = false;
		
		try{
  		   	String comando = "UPDATE SALOMON.ADM_TUTOR SET"
  		   			+ " TUTOR = TO_NUMBER(?, '9'),"
  		   			+ " NOMBRE = ?,"
  		   			+ " CALLE = ?,"
  		   			+ " NUMERO = ?,"
  		   			+ " COLONIA = ?,"
  		   			+ " CODIGOPOSTAL = ?,"  			
  		   			+ " PAIS_ID = TO_NUMBER(?,'999'),"
  		   			+ " ESTADO_ID = TO_NUMBER(?,'999'),"
  		   			+ " CIUDAD_ID = TO_NUMBER(?,'999'),"
  		   			+ " TELEFONO = ?"  		   			
  		   			+ " ESTADO = ?,"
  		   			+ " CIUDAD = ?,"  		   			
  		   			+ " WHERE FOLIO = TO_NUMBER(?,'9999999')";  		   
	  		 Object[] parametros = new Object[] {
	  				admTutor.getTutor(),admTutor.getNombre(),admTutor.getCalle(),admTutor.getNumero(),
	  				admTutor.getColonia(),admTutor.getCodigoPostal(),admTutor.getPaisId(),admTutor.getEstadoId(),
					admTutor.getCiudadId(),admTutor.getTelefono(),admTutor.getEstado(),admTutor.getCiudad(),admTutor.getFolio()
		 	};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	   
		}catch(Exception ex){
			System.out.println("Error - adm.admision.spring.AdmTutorDao|updateReg|:"+ex);
		}

		return ok;
	}
	
	public boolean deleteReg(String folio) throws Exception{
		boolean ok = false;		
		try{
			String comando = "DELETE FROM SALOMON.ADM_TUTOR WHERE FOLIO = TO_NUMBER(?,'9999999')";			
			Object[] parametros = new Object[] {folio};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - adm.admision.spring.AdmTutorDao|deleteReg|:"+ex);
		}
		return ok;
	}
	
	public AdmTutor mapeaRegId(String folio ) {
		AdmTutor objeto = new AdmTutor();		
		try {
			String comando = "SELECT COUNT(*) FROM SALOMON.ADM_TUTOR WHERE FOLIO = TO_NUMBER(?,'9999999')";
			Object[] parametros = new Object[] {folio};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				comando = "SELECT FOLIO, TUTOR, NOMBRE, CALLE, NUMERO, COLONIA, CODIGOPOSTAL,"
						+ " PAIS_ID, ESTADO_ID, CIUDAD_ID, TELEFONO, ESTADO, CIUDAD " 
						+ " FROM SALOMON.ADM_TUTOR "
						+ " WHERE FOLIO = TO_NUMBER(?,'9999999')";			
				objeto = enocJdbc.queryForObject(comando, new AdmTutorMapper(), parametros);
			}						
		} catch (Exception ex) {
			System.out.println("Error - adm.admision.spring.AdmTutorDao|mapeaRegId|:"+ex);
		}
		
		return objeto;
	}
	
	public boolean existeReg(String folio) {
		boolean ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM SALOMON.ADM_TUTOR WHERE FOLIO = TO_NUMBER(?,'9999999')";
			Object[] parametros = new Object[] {folio};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - adm.admision.spring.AdmTutorDao|existeReg|:"+ex);
		}		
		return ok;
	}
	
	public HashMap<String, AdmTutor> mapaUbicacion(){
		HashMap<String, AdmTutor> mapa = new HashMap<String, AdmTutor>();
		List<AdmTutor> lista = new ArrayList<AdmTutor>();		
		try {
			String comando = "SELECT FOLIO, TUTOR, NOMBRE, CALLE, NUMERO, COLONIA, CODIGOPOSTAL,"
					+ " PAIS_ID, ESTADO_ID, CIUDAD_ID, TELEFONO, ESTADO, CIUDAD"
					+ " FROM SALOMON.ADM_TUTOR ";
			lista = enocJdbc.query(comando, new AdmTutorMapper());
			for (AdmTutor  sol : lista ){
				mapa.put(sol.getFolio(), sol);
			}
		}catch(Exception ex) {
			System.out.println("Error - aca.admision.spring.AdmTutorDao|mapaUbicacion|:"+ex);
		}
		return mapa;
	}
}