/**
 * 
 */
package aca.vista.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


/**
 * @author general
 *
 */
@Component
public class PlanCicloDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public List<PlanCiclo> getListAll( String orden ){
		
		List<PlanCiclo> lista	= new ArrayList<PlanCiclo>();		
		try{
			String comando = "SELECT PLAN_ID, CICLO, CREDITOS FROM ENOC.PLAN_CICLO "+orden;
			lista = enocJdbc.query(comando, new PlanCicloMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.PlanCilcloDao|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	public List<PlanCiclo> getListCiclosPlan( String planId, String orden ){
		
		   List<PlanCiclo> lista	= new ArrayList<PlanCiclo>();
		   Object[] parametros = new Object[] {planId};
		try{
			String comando = "SELECT PLAN_ID, CICLO, CREDITOS FROM ENOC.PLAN_CICLO WHERE PLAN_ID = ? AND CREDITOS != 0 "+orden;
			lista = enocJdbc.query(comando, new PlanCicloMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.PlanCilcloDao|getListCiclosPlan|:"+ex);
		}
		
		return lista;
	}
	
	public int getNumCiclos( String planId){
		int ciclos = 0;		 
		try{
			String comando = "SELECT COALESCE(MAX(CICLO),0) FROM ENOC.PLAN_CICLO WHERE PLAN_ID = ?";
			Object[] parametros = new Object[] {planId};			
			ciclos = enocJdbc.queryForObject(comando, Integer.class, parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.PlanCicloDao||getNumCiclos:"+ex);			
		}
		
		return ciclos;
	}
}