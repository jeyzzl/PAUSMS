package aca.financiero.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CcpPresupuestoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public String getSuma(String porcentaje, String meses, String cuenta, String idEjercicio){				
		String suma		= "";
		String comando 	= "";
		try{
			if(!meses.equals("")){
				comando = "SELECT SUM(IMPORTE)*("+porcentaje+"/100) AS SUMA FROM MATEO.CCP_PRESUPUESTO WHERE ID_EJERCICIO = ?" +
						"AND ID_CTAMAYOR = '1.3.05' AND MES IN ("+meses+")";
				suma = enocJdbc.queryForObject(comando, String.class, idEjercicio);
			}else{
				comando = "SELECT SUM(IMPORTE)*("+porcentaje+"/100) AS SUMA FROM MATEO.CCP_PRESUPUESTO WHERE ID_EJERCICIO = ? " +
						"AND ID_CTAMAYOR = ?";
				suma = enocJdbc.queryForObject(comando, String.class, idEjercicio, cuenta);
			}			
		}catch(Exception e){
			System.out.println("Error - aca.financiero.spring.CcpPresupuestoDao|getSuma|:"+e);
		}
		return suma;
	}
	
	public String getSumaCcosto(String porcentaje, String meses, String cuenta, String idEjercicio, String ccosto){				
		String suma		= "";
		String comando 	= "";
		try{
			if(!meses.equals("")){
				comando = "SELECT COALESCE( SUM(IMPORTE)*("+porcentaje+"/100) ,0) AS SUMA FROM MATEO.CCP_PRESUPUESTO WHERE ID_CCOSTO = ? AND ID_EJERCICIO = ? " +
						"AND ID_CTAMAYOR = '"+cuenta+"' AND MES IN ("+meses+")";
				suma = enocJdbc.queryForObject(comando, String.class, ccosto, idEjercicio);		
			}else{
				comando = "SELECT COALESCE( SUM(IMPORTE)*("+porcentaje+"/100) ,0) AS SUMA FROM MATEO.CCP_PRESUPUESTO WHERE ID_CCOSTO = '"+ccosto+"' AND ID_EJERCICIO = '"+idEjercicio+"' " +
						"AND ID_CTAMAYOR = '"+cuenta+"'";
				suma = enocJdbc.queryForObject(comando, String.class, ccosto, idEjercicio, cuenta);
			}			
		}catch(Exception e){
			System.out.println("Error - aca.financiero.spring.CcpPresupuestoDao|getSumaCcosto|:"+e);
		}
		return suma;
	}
}
