package aca.admision.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AdmSaludDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(AdmSalud objeto){
		boolean ok = false;
		
		Object[] parametros = new Object[] {
			objeto.getFolio(),objeto.getEnfermedad(),objeto.getEnfermedadDato(),objeto.getImpedimento(),objeto.getImpedimentoDato()
		};

		try{
			if (objeto.getEnfermedad().equals("N")) objeto.setEnfermedadDato("-");
			if (objeto.getImpedimento().equals("N")) objeto.setImpedimentoDato("-");
			
			String comando = "INSERT INTO SALOMON.ADM_SALUD (FOLIO, ENFERMEDAD, ENFERMEDAD_DATO, IMPEDIMENTO, IMPEDIMENTO_DATO)"
				+ " VALUES( TO_NUMBER(?,'9999999'), ?, ?, ?)";
						
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
 			
		}catch(Exception ex){
			System.out.println("Error - adm.admision.spring.AdmSaludDao|insertReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean updateReg(AdmSalud objeto){
		boolean ok = false;
		
		Object[] parametros = new Object[] {
			objeto.getEnfermedad(),objeto.getEnfermedadDato(),objeto.getImpedimento(),objeto.getImpedimentoDato(),objeto.getFolio()
		};
		
		try{
			if (objeto.getEnfermedad().equals("N")) objeto.setEnfermedadDato("-");
			if (objeto.getImpedimento().equals("N")) objeto.setImpedimentoDato("-");
			
			String comando = "UPDATE SALOMON.ADM_SALUD "
					+ " SET ENFERMEDAD = ?, "
					+ " ENFERMEDAD_DATO = ?, "
					+ " IMPEDIMENTO = ?, "
					+ " IMPEDIMENTO_DATO = ? "
					+ " WHERE FOLIO = TO_NUMBER(?,'9999999')";
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - adm.admision.spring.AdmSaludDao|updateReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean deleteReg(String folio){
		boolean ok = false;
		
		Object[] parametros = new Object[] {folio};

		try{
			String comando = "DELETE FROM SALOMON.ADM_SALUD WHERE FOLIO = TO_NUMBER(?,'9999999')";
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - adm.admision.spring.AdmSaludDao|deleteReg|:"+ex);
		}
		
		return ok;
	}
	
	public AdmSalud mapeaRegId(String folio){
		AdmSalud objeto = new AdmSalud();				
		try{
			String comando = "SELECT COUNT(*) FROM SALOMON.ADM_SALUD WHERE FOLIO = TO_NUMBER(?,'9999999')";			
			Object[] parametros = new Object[] {folio};			
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				comando = "SELECT FOLIO, ENFERMEDAD, ENFERMEDAD_DATO, IMPEDIMENTO, IMPEDIMENTO_DATO"
						+ " FROM SALOMON.ADM_SALUD WHERE FOLIO = TO_NUMBER(?,'9999999')";				
				objeto = enocJdbc.queryForObject(comando, new AdmSaludMapper(), parametros);
			}		
		}catch(Exception ex){
			System.out.println("Error - adm.admision.spring.AdmSaludDao|deleteReg|:"+ex);
		}
		
		return objeto;
	}
	
	public boolean existeReg(String folio){
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT(*) FROM SALOMON.ADM_SALUD WHERE FOLIO = TO_NUMBER(?,'9999999')";			
			Object[] parametros = new Object[] {folio};			
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - adm.admision.spring.AdmSaludDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public HashMap<String, AdmSalud> mapaTodos (){
		HashMap<String, AdmSalud> mapa = new HashMap<String, AdmSalud>();
		List<AdmSalud> lista = new ArrayList<AdmSalud>();		
		try {
			String comando = "SELECT FOLIO, ENFERMEDAD, ENFERMEDAD_DATO, IMPEDIMENTO, IMPEDIMENTO_DATO FROM SALOMON.ADM_SALUD";
			lista = enocJdbc.query(comando, new AdmSaludMapper());
			for (AdmSalud aca : lista ){
				mapa.put(aca.getFolio(), aca);
			}
		}catch(Exception ex) {
			System.out.println("Error - aca.admision.spring.AdmSaludDao|mapaTodos|:"+ex);
		}
		return mapa;
	}

}
