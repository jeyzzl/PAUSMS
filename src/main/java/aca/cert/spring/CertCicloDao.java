/**
 * 
 */
package aca.cert.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * @author Jose Torres
 *
 */
@Component
public class CertCicloDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( CertCiclo ciclo) {
		boolean ok = false;

		try{
			String comando = "INSERT INTO ENOC.CERT_CICLO"+ 
				"(PLAN_ID, CICLO_ID, TITULO, FST, FSP, CREDITOS)"+
				" VALUES(?, TO_NUMBER(?, '99'), ?, ?, ?, ?)";
			Object[] parametros = new Object[] {ciclo.getPlanId(), ciclo.getCicloId(), ciclo.getTitulo(), ciclo.getFst(), ciclo.getFsp(), ciclo.getCreditos()};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.spring.CertCicloDao|insertReg|:"+ex);			

		}
		
		return ok;
	}	
	
	public boolean updateReg( CertCiclo ciclo) {
		boolean ok = false;

		try{
			String comando = "UPDATE ENOC.CERT_CICLO"+ 
				" SET TITULO = ?," +
				" FST = ?," +
				" FSP = ?," +
				" CREDITOS = ?"+				
				" WHERE PLAN_ID = ?" +
				" AND CICLO_ID = TO_NUMBER(?, '99')";
			Object[] parametros = new Object[] {ciclo.getTitulo(), ciclo.getFst(), ciclo.getFsp(), ciclo.getCreditos(), ciclo.getPlanId(), ciclo.getCicloId()};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.spring.CertCicloDao|updateReg|:"+ex);		

		}
		
		return ok;
	}	
	
	public boolean deleteReg( String planId, String cicloId) {
		boolean ok = false;

		try{
			String comando = "DELETE FROM ENOC.CERT_CICLO"+ 
				" WHERE PLAN_ID = ?" +
				" AND CICLO_ID = TO_NUMBER(?, '99')";
			Object[] parametros = new Object[] {planId, cicloId};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.spring.CertCicloDao|deleteReg|:"+ex);			

		}
		return ok;
	}
	
	public CertCiclo mapeaRegId( String planId, String cicloId) {
		
		CertCiclo ciclo = new CertCiclo();

		try{
			String comando = "SELECT PLAN_ID, CICLO_ID," +
					" TITULO, FST, FSP, CREDITOS" +
					" FROM ENOC.CERT_CICLO" + 
					" WHERE PLAN_ID = ?" +
					" AND CICLO_ID = TO_NUMBER(?, '99')";
			Object[] parametros = new Object[] {planId, cicloId};		
			ciclo = enocJdbc.queryForObject(comando, new CertCicloMapper(),parametros);

		
		}catch(Exception ex){
			System.out.println("Error - aca.cert.spring.CertCicloDao|existeReg|:"+ex);

		}
		return ciclo;
	}
	
	public boolean existeReg( String planId, String cicloId) {
		boolean 			ok 	= false;
		
		try{
			String comando = "SELECT COUNT(TITULO) FROM ENOC.CERT_CICLO"+ 
				" WHERE PLAN_ID = ?" +
				" AND CICLO_ID = TO_NUMBER(?, '99')";
			Object[] parametros = new Object[] {planId, cicloId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.spring.CertCicloDao|existeReg|:"+ex);

		}
		
		return ok;
	}
	
	public String maxReg( String planId) {
		String maximo 			= "";
		
		try{
			String comando = "SELECT COALESCE(MAX(CICLO_ID)+1,1) AS MAXIMO FROM ENOC.CERT_CICLO"+ 
				" WHERE PLAN_ID = ?";
			Object[] parametros = new Object[] {planId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				maximo = enocJdbc.queryForObject(comando, String.class, parametros);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.spring.CertCicloDao|maxReg|:"+ex);

		}
		
		return maximo;
	}	
	
	public ArrayList<CertCiclo> getListPlan( String planId, String orden ) {		
		List<CertCiclo> lista	= new ArrayList<CertCiclo>();
		try{
			String comando = "SELECT PLAN_ID, CICLO_ID, TITULO, FST, FSP, CREDITOS"
					+ " FROM ENOC.CERT_CICLO"
					+ " WHERE PLAN_ID = ? "+ orden;
			lista = enocJdbc.query(comando, new CertCicloMapper(), planId);			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.spring.CertCicloDao|getListPlan|:"+ex);
		}		
		return (ArrayList<CertCiclo>) lista;
	}
}