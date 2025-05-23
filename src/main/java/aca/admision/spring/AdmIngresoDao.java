package aca.admision.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AdmIngresoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(AdmIngreso admIngreso) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO SALOMON.ADM_INGRESO "+ 
				"(PERIODO_ID, PERIODO_NOMBRE, ESTADO, FECHA) "+
				"VALUES( TO_NUMBER(?,'999'),? ,?,TO_DATE(?, 'DD/MM/YYYY') )";
			
			Object[] parametros = new Object[] {
					admIngreso.getPeriodoId(), admIngreso.getPeriodoNombre(), admIngreso.getEstado(),admIngreso.getFecha()
 		 	};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}

		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmIngresoDao|insertReg|:"+ex);
		}

		return ok;
	}
	
	public boolean updateReg(AdmIngreso admIngreso) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE SALOMON.ADM_INGRESO " + 
					"SET PERIODO_NOMBRE = ?, ESTADO =? ,FECHA = TO_DATE(?, 'DD/MM/YYYY') " +				
					"WHERE PERIODO_ID = TO_NUMBER(?,'999')";
			
			Object[] parametros = new Object[] {
					admIngreso.getPeriodoNombre(),admIngreso.getEstado(),admIngreso.getFecha(), admIngreso.getPeriodoId()
 		 	};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}

		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmIngresoDao|updateReg|:"+ex);
		}

		return ok;
	}
	
	public boolean deleteReg(String periodoId) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM SALOMON.ADM_INGRESO "+ 
					"WHERE PERIODO_ID = TO_NUMBER(?,'999') ";
			
			Object[] parametros = new Object[] {periodoId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmIngresoDao|deleteReg|:"+ex);
		}
		
		return ok;
	}
	
	public AdmIngreso mapeaRegId(String periodoId) {
		AdmIngreso objeto = new AdmIngreso();
		
		try {
			String comando = "SELECT PERIODO_ID, PERIODO_NOMBRE, ESTADO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA "+
					"FROM SALOMON.ADM_INGRESO "+ 
					"WHERE PERIODO_ID = TO_NUMBER(?,'999') ";
			
			Object[] parametros = new Object[] {periodoId};
			objeto = enocJdbc.queryForObject(comando, new AdmIngresoMapper(), parametros);
			
		} catch (Exception ex) {
			System.out.println("Error - adm.alumno.AdmIngresoDao|mapeaRegId|:"+ex);
		}

		return objeto;
	}
	
	public boolean existeReg(String periodoId) {
		boolean ok 		= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM SALOMON.ADM_INGRESO "+ 
					"WHERE PERIODO_ID = TO_NUMBER(?,'999')";
			
			Object[] parametros = new Object[] {periodoId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmIngresoDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoReg() {
		String maximo		= "1";		
		try{
			String comando = "SELECT COALESCE(MAX(PERIODO_ID)+1,1) AS MAXIMO FROM SALOMON.ADM_INGRESO";		
			maximo = enocJdbc.queryForObject(comando,String.class);			
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmIngresoDao|maximoReg|:"+ex);
		}		
		return maximo;
	}
	
	public List<AdmIngreso> lisTodos(String orden) {
		List<AdmIngreso> lista	= new ArrayList<AdmIngreso>();		
		try{
			String comando = "SELECT PERIODO_ID, PERIODO_NOMBRE, ESTADO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA FROM SALOMON.ADM_INGRESO "+ orden;
			lista = enocJdbc.query(comando, new AdmIngresoMapper());			
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmIngresoDao|lisTodos|:"+ex);
		}
		
		return lista;
	}
public String getNombre( String periodoId ) {
		
		String nombre			= "";		
		try{
			String comando = "SELECT COUNT(*) FROM SALOMON.ADM_INGRESO WHERE PERIODO_ID = TO_NUMBER(?,'999') ";
			Object[] parametros = new Object[] {periodoId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				comando = "SELECT PERIODO_NOMBRE AS NOMBRE FROM SALOMON.ADM_INGRESO WHERE PERIODO_ID = TO_NUMBER(?,'999') ";						
				nombre = enocJdbc.queryForObject(comando, String.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmIngresoDao|getNombre|:"+ex);
		}
		
		return nombre;
	}
	
}
