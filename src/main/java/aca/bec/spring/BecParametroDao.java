/**
 * 
 */
package aca.bec.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class BecParametroDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( BecParametro becParametro) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.BEC_PARAMETRO(PREPA_INICIO, PREPA_FINAL, PREGRADO_INICIO, PREGRADO_FINAL)" +
				" VALUES( TO_DATE(?, 'DD/MM/YYYY'), TO_DATE(?, 'DD/MM/YYYY'), TO_DATE(?, 'DD/MM/YYYY'), TO_DATE(?, 'DD/MM/YYYY'))";
					
			Object[] parametros = new Object[] {becParametro.getPrepaInicio(),becParametro.getPrepaFinal(),
					becParametro.getPregradoInicio(),becParametro.getPrepaFinal()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecParametroDao|insertReg|:"+ex);			
		}		
		return ok;
	}	
	
	public boolean updateReg( BecParametro becParametro) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.BEC_PARAMETRO"+ 
				" SET PREPA_INICIO = TO_DATE(?, 'DD/MM/YYYY')," +
				" PREPA_FINAL = TO_DATE(?, 'DD/MM/YYYY'), "+
				" PREGRADO_INICIO = TO_DATE(?, 'DD/MM/YYYY'), "+
				" PREGRADO_FINAL = TO_DATE(?, 'DD/MM/YYYY')";
			
			Object[] parametros = new Object[] {becParametro.getPrepaInicio(),becParametro.getPrepaFinal(),
					becParametro.getPregradoInicio(),becParametro.getPregradoFinal()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecParametroDao|updateReg|:"+ex);		
		}
		return ok;
	}
	
	public BecParametro mapeaRegId( ){
		BecParametro becParametro = new BecParametro();
 		try{
	 		String comando 	= "SELECT TO_CHAR(PREPA_INICIO,'DD/MM/YYYY') AS PREPA_INICIO,"
	 				+ " TO_CHAR(PREPA_FINAL,'DD/MM/YYYY') AS PREPA_FINAL,"
	 				+ " TO_CHAR(PREGRADO_INICIO,'DD/MM/YYYY') AS PREGRADO_INICIO, "
	 				+ " TO_CHAR(PREGRADO_FINAL,'DD/MM/YYYY') AS PREGRADO_FINAL"
	 				+ " FROM ENOC.BEC_PARAMETRO";
	 		becParametro 	= enocJdbc.queryForObject(comando, new BecParametroMapper());
 		}catch(Exception ex){
 			System.out.println("Error - aca.bec.spring.BecParametroDao|mapeaRegId|:"+ex);
 		} 		
 		return becParametro;
 	}
	
	public HashMap<Integer, String> getMapFechas(){
		HashMap<Integer, String> map 	= new HashMap<Integer, String>();
		List<BecParametro> lista 			= new ArrayList<BecParametro>();
		try{			
			String comando = "SELECT TO_CHAR(PREPA_INICIO, 'DD/MM/YYYY') AS PREPA_INICIO,TO_CHAR(PREPA_FINAL, 'DD/MM/YYYY') AS PREPA_FINAL," +
					"TO_CHAR(PREGRADO_INICIO, 'DD/MM/YYYY') AS PREGRADO_INICIO, TO_CHAR(PREGRADO_FINAL, 'DD/MM/YYYY') AS PREGRADO_FINAL " +
					"FROM ENOC.BEC_PARAMETRO";
			lista 	= enocJdbc.query(comando, new BecParametroMapper());
			for (BecParametro par : lista) {
				map.put(1, par.getPrepaInicio());
				map.put(2, par.getPrepaFinal());
				map.put(3, par.getPregradoInicio());
				map.put(4, par.getPregradoFinal());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecParametroDao|getMapFechas|:"+ex);
		}		
		return map;
	}
	
}