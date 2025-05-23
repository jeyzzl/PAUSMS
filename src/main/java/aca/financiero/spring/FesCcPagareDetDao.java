package aca.financiero.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class FesCcPagareDetDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean existeReg(String id){
		boolean ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM MATEO.FES_CC_PAGARE_DET WHERE ID = ? ";
			Object[] parametros = new Object[] {id};	
			if (enocJdbc.queryForObject(comando, Integer.class,parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FesCcPagareDetDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public FesCcPagareDet mapeaRegId(String id) {
		FesCcPagareDet pagareDet = new FesCcPagareDet();
 		
 		try{
	 		String comando = "SELECT MATRICULA,CARGA_ID,BLOQUE,FOLIO,FVENCIMIENTO,IMPORTE,STATUS,ID,CCOBRO_ID " +	 					
	 				" FROM MATEO.FES_CC_PAGARE_DET WHERE IDO = ?";
	 		Object[] parametros = new Object[] {id};
	 		pagareDet = enocJdbc.queryForObject(comando, new FesCcPagareDetMapper(),parametros);	
	 		
 		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.FesCcPagareDetDao|mapeaRegId|:"+ex);
		}
 		
 		return pagareDet;
 	}
	
	public double getPagareNoVencido(String matricula) {
		double saldoPagare = 0;
		
		try{		
			String comando = "SELECT COALESCE(SUM(IMPORTE),0) FROM MATEO.FES_CC_PAGARE_DET" +
				" WHERE MATRICULA = ?"+			
				" AND TO_DATE(TO_CHAR(FVENCIMIENTO,'DD-MM-YY'),'DD-MM-YY') >= TO_DATE(TO_CHAR(now(),'DD-MM-YY'),'DD-MM-YY')" +
				" AND STATUS = 'A'";
			saldoPagare = enocJdbc.queryForObject(comando,Double.class, matricula);			
		}catch(Exception ex){
 			System.out.println("Error - aca.financiero.spring.FesCcPagareDetDao|getPagareNoVencido|:"+ex);
 		}
 		
		return saldoPagare;
	}

}
