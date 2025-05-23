package aca.admision.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AdmDirectoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(AdmDirecto objeto) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO SALOMON.ADM_DIRECTO"
					+ " (FOLIO, MATRICULA, PLAN_ID, RECIENTE, TETRA, REC_PREPA, REC_VRE, ENVIO_SOL, PREPA_VALIDO, VRE_VALIDO, NOMBRE_PREPA, NOMBRE_VRE)"
					+ " VALUES(TO_NUMBER(?,'99999999'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			
			Object[] parametros = new Object[] {
				objeto.getFolio(),objeto.getMatricula(),objeto.getPlanId(),objeto.getReciente(),objeto.getTetra(),objeto.getRecPrepa(),objeto.getRecVre(),
				objeto.getEnvioSol(),objeto.getPrepaValido(),objeto.getVreValido(),objeto.getNombrePrepa(),objeto.getNombreVre()
 		 	};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmDirectoDao|insertReg|:"+ex);
		}

		return ok;
	}
	
	public boolean updateReg(AdmDirecto objeto) {
		boolean ok = false;		
		try{
			String comando = "UPDATE SALOMON.ADM_DIRECTO"
					+ " SET MATRICULA = ?,"
					+ " PLAN_ID = ?,"
					+ " RECIENTE = ?,"
					+ " TETRA = ?,"
					+ " REC_PREPA = ?,"
					+ " REC_VRE = ?,"
					+ " ENVIO_SOL = ?,"
					+ " PREPA_VALIDO = ?,"
					+ " VRE_VALIDO = ?,"
					+ " NOMBRE_PREPA = ?,"
					+ " NOMBRE_VRE = ?"
					+ " WHERE FOLIO = TO_NUMBER(?,'99999999')";			
			Object[] parametros = new Object[] {
				objeto.getMatricula(),objeto.getPlanId(),objeto.getReciente(),objeto.getTetra(),objeto.getRecPrepa(),objeto.getRecVre(),
				objeto.getEnvioSol(),objeto.getPrepaValido(),objeto.getVreValido(),objeto.getNombrePrepa(),objeto.getNombreVre(),objeto.getFolio()
 		 	};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmDirectoDao|updateReg|:"+ex);
		}

		return ok;
	}
	
	public boolean enviarSolicitud(String folio) {
		boolean ok = false;		
		try{
			String comando = "UPDATE SALOMON.ADM_DIRECTO SET ENVIO_SOL = 'S' WHERE FOLIO = TO_NUMBER(?,'99999999')";			
			if (enocJdbc.update(comando,new Object[] {folio})==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmDirectoDao|enviarSolicitud|:"+ex);
		}
		
		return ok;
	}
	
	public boolean deleteReg(String folio) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM SALOMON.ADM_DIRECTO WHERE FOLIO = TO_NUMBER(?,'99999999')";
			Object[] parametros = new Object[] {folio};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmDirectoDao|deleteReg|:"+ex);
		}		
		return ok;
	}
	
	public AdmDirecto mapeaRegId(String folio) {
		AdmDirecto objeto = new AdmDirecto();		
		try {
			String comando = "SELECT COUNT(*) FROM SALOMON.ADM_DIRECTO WHERE FOLIO = TO_NUMBER(?,'9999999')";
			Object[] parametros = new Object[] {folio};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				comando = "SELECT FOLIO, MATRICULA, PLAN_ID, RECIENTE, TETRA, REC_PREPA, REC_VRE, ENVIO_SOL, PREPA_VALIDO, VRE_VALIDO, NOMBRE_PREPA, NOMBRE_VRE"
					+ " FROM SALOMON.ADM_DIRECTO WHERE FOLIO = TO_NUMBER(?,'9999999')";				
				objeto = enocJdbc.queryForObject(comando, new AdmDirectoMapper(),parametros);
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmDirectoDao|mapeaRegId|:"+ex);
		}
		
		return objeto;
	}
	
	public boolean existeReg(String folio){
		boolean ok = false;		
		try{
			String comando = "SELECT COUNT(*) FROM SALOMON.ADM_DIRECTO WHERE FOLIO = TO_NUMBER(?,'9999999')";			
			Object[] parametros = new Object[] {folio};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmDirectoDao|existeReg|:"+ex);
		}
		
		return ok;
	}

	public String promedio(String matricula, String planId) {
		String promedio = "";
		
		try{
			String comando = "SELECT PROMEDIO FROM ENOC.ALUM_PLAN WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ?";
			
			Object[] parametros = new Object[] {matricula, planId};
			promedio = enocJdbc.queryForObject(comando,String.class,parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmDirectoDao|promedio|:"+ex);
		}
		
		return promedio;
	}

	public String nombreMatricula(String matricula) {
		String nombre = "";
		
		try{
			String comando = "SELECT NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL = ?";
			
			Object[] parametros = new Object[] {matricula};
			nombre = enocJdbc.queryForObject(comando,String.class,parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmDirectoDao|nombreMatricula|:"+ex);
		}
		
		return nombre;
	}
}
