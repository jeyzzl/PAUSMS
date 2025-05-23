package aca.admision.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AdmPadresDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(AdmPadres objeto){
		boolean ok = false;

		Object[] parametros = new Object[] {
			objeto.getFolio(), objeto.getPadreNombre(),objeto.getPadreApellido(),objeto.getPadreReligion(),objeto.getPadreNacionalidad(),objeto.getPadreOcupacion(),
			objeto.getMadreNombre(),objeto.getMadreApellido(),objeto.getMadreReligion(),objeto.getMadreNacionalidad(),objeto.getMadreOcupacion()
		};
		
		try{
			String comando = "INSERT INTO SALOMON.ADM_PADRES"
					+ " (FOLIO, PADRE_NOMBRE, PADRE_APELLIDO, PADRE_RELIGION, PADRE_NACIONALIDAD, PADRE_OCUPACION, MADRE_NOMBRE, MADRE_APELLIDO, MADRE_RELIGION,"
					+ " MADRE_NACIONALIDAD, MADRE_OCUPACION )"
					+ " VALUES( TO_NUMBER(?,'9999999'), ?, ?, TO_NUMBER(?,'99'), TO_NUMBER(?,'999'), ?, ?, ?, TO_NUMBER(?,'99'), TO_NUMBER(?,'999'), ? )";
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - adm.admision.spring.AdmPadresDao|insertReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean updateReg(AdmPadres objeto){
		boolean ok = false;
		
		try{
			String comando = "UPDATE SALOMON.ADM_PADRES "
					+ " SET PADRE_NOMBRE = ?, "
					+ " PADRE_APELLIDO = ?, "
					+ " PADRE_RELIGION = TO_NUMBER(?,'99'), "
					+ " PADRE_NACIONALIDAD = TO_NUMBER(?,'999'), "
					+ " PADRE_OCUPACION = ?, "
					+ " MADRE_NOMBRE = ?, "
					+ " MADRE_APELLIDO = ?, "
					+ " MADRE_RELIGION = TO_NUMBER(?,'99'), "
					+ " MADRE_NACIONALIDAD = TO_NUMBER(?,'999'), "
					+ " MADRE_OCUPACION = ? "
					+ " WHERE FOLIO = TO_NUMBER(?,'9999999')";
			
			Object[] parametros = new Object[] {
				objeto.getPadreNombre(),objeto.getPadreApellido(),objeto.getPadreReligion(),objeto.getPadreNacionalidad(),objeto.getPadreOcupacion(),
				objeto.getMadreNombre(),objeto.getMadreApellido(),objeto.getMadreReligion(),objeto.getMadreNacionalidad(),objeto.getMadreOcupacion(),
				objeto.getFolio()
			};
				
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - adm.admision.spring.AdmPadresDao|updateReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean deleteReg(String folio){
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM SALOMON.ADM_PADRES WHERE FOLIO = TO_NUMBER(?,'9999999')";
			
			if (enocJdbc.update(comando,new Object[] {folio})==1){			
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - adm.admision.spring.AdmPadresDao|deleteReg|:"+ex);
			ok = false;
		}
		
		return ok;
	}
	
	public AdmPadres mapeaRegId(String folio){
		AdmPadres objeto = new AdmPadres();		
		try{
			String comando = "SELECT COUNT(*) FROM SALOMON.ADM_PADRES WHERE FOLIO = TO_NUMBER(?,'9999999')";
			if (enocJdbc.queryForObject(comando,Integer.class, new Object[] {folio})>=1){
				comando = "SELECT FOLIO, PADRE_NOMBRE, PADRE_APELLIDO, PADRE_RELIGION, PADRE_NACIONALIDAD,"
						+ " PADRE_OCUPACION, MADRE_NOMBRE, MADRE_APELLIDO, MADRE_RELIGION, MADRE_NACIONALIDAD, MADRE_OCUPACION "
						+ " FROM SALOMON.ADM_PADRES WHERE FOLIO = TO_NUMBER(?,'9999999')";				
				objeto = enocJdbc.queryForObject(comando, new AdmPadresMapper(), new Object[] {folio});
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmPadresDao|mapeaRegId|:"+ex);
		}
		
		return objeto;
	}
	
	public boolean existeReg(String folio){
		boolean ok = false;		
		try{
			String comando = "SELECT COUNT(*) FROM SALOMON.ADM_PADRES WHERE FOLIO = TO_NUMBER(?,'9999999')";
			if (enocJdbc.queryForObject(comando,Integer.class, new Object[] {folio})>=1){	
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmEvaluacionDao|existeReg|:"+ex);
		}
		
		return ok;
	}

}
