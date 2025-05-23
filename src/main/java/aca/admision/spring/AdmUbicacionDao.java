package aca.admision.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AdmUbicacionDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(AdmUbicacion admUbicacion) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO SALOMON.ADM_UBICACION"+ 
				"(FOLIO, PAIS_ID, ESTADO_ID, CIUDAD_ID, CALLE, NUMERO, CODIGO_POSTAL, TELEFONO, COLONIA) "+
				"VALUES( TO_NUMBER(?,'99999999'), TO_NUMBER(?,'999'),TO_NUMBER(?,'999'), TO_NUMBER(?,'999'), " +
				" ?,?,?,?, ?)";

			Object[] parametros = new Object[] {
				admUbicacion.getFolio(),admUbicacion.getPaisId(),admUbicacion.getEstadoId(),admUbicacion.getCiudadId(),admUbicacion.getCalle(),admUbicacion.getNumero(),
				admUbicacion.getCodigoPostal(),admUbicacion.getTelefono(),admUbicacion.getColonia()
 		 	};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmUbicacionDao|insertReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean updateReg(AdmUbicacion admUbicacion) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE SALOMON.ADM_UBICACION " + 
					" SET PAIS_ID = TO_NUMBER(?,'999'), " +
					" ESTADO_ID = TO_NUMBER(?,'999'), " +
					" CIUDAD_ID = TO_NUMBER(?,'999'), " +
					" CALLE = ?, " +
					" NUMERO = ?, " +
					" CODIGO_POSTAL = ?," +					
					" TELEFONO = ?," +
					" COLONIA = ? " +				
					" WHERE FOLIO = TO_NUMBER(?,'99999999')";
			
			Object[] parametros = new Object[] {
				admUbicacion.getPaisId(),admUbicacion.getEstadoId(),admUbicacion.getCiudadId(),admUbicacion.getCalle(),admUbicacion.getNumero(),
				admUbicacion.getCodigoPostal(),admUbicacion.getTelefono(),admUbicacion.getColonia(),admUbicacion.getFolio()
 		 	};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmUbicacionDao|updateReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean deleteReg(String folio) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM SALOMON.ADM_UBICACION "+ 
					" WHERE FOLIO = TO_NUMBER(?,'99999999')";
			
			Object[] parametros = new Object[] {folio};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmUbicacionDao|deleteReg|:"+ex);
		}
		
		return ok;
	}
	
	public AdmUbicacion mapeaRegId(String folio ) {
		AdmUbicacion objeto = new AdmUbicacion();		
		try {
			String comando = "SELECT COUNT(*) FROM SALOMON.ADM_UBICACION WHERE FOLIO = TO_NUMBER(?,'9999999')";			
			Object[] parametros = new Object[] {folio};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				comando = "SELECT FOLIO, PAIS_ID, ESTADO_ID, CIUDAD_ID, CALLE," +
						" NUMERO, CODIGO_POSTAL, TELEFONO, COLONIA"+
						" FROM SALOMON.ADM_UBICACION "+ 
						" WHERE FOLIO = TO_NUMBER(?,'9999999')";				
				objeto = enocJdbc.queryForObject(comando, new AdmUbicacionMapper(), parametros);			
			}		
		} catch (Exception ex) {
			System.out.println("Error - adm.alumno.AdmUbicacionDao|mapeaRegId|:"+ex);
		}
		
		return objeto;
	}
	
	public boolean existeReg(String folio) {
		boolean 		ok 		= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM SALOMON.ADM_UBICACION "+ 
					" WHERE FOLIO = TO_NUMBER(?,'9999999')";
			
			Object[] parametros = new Object[] {folio};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmUbicacionDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public List<AdmUbicacion> getAll(String orden) {
		List<AdmUbicacion> lista	= new ArrayList<AdmUbicacion>();
		
		try{
			String comando = "SELECT FOLIO, PAIS_ID, ESTADO_ID, CIUDAD_ID, CALLE," +
			" NUMERO, CODIGO_POSTAL, TELEFONO, COLONIA FROM SALOMON.ADM_UBICACION "+ orden; 
			
			lista = enocJdbc.query(comando, new AdmUbicacionMapper());
			
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmUbicacionDao|getAll|:"+ex);
		}
		
		return lista;
	}

}
