package aca.financiero.spring;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ContMovimientoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public List<ContMovimiento> lisTodos(String orden){
		
		List<ContMovimiento> lista 	= new ArrayList<ContMovimiento>();		
		try{			
			String comando = " SELECT ID_EJERCICIO, ID_LIBRO, ID_CCOSTO, FOLIO, NUMMOVTO, FECHA, DESCRIPCION, IMPORTE, NATURALEZA,"
					+ " REFERENCIA, REFERENCIA2, ID_CTAMAYORM, ID_CCOSTOM, ID_AUXILIARM, STATUS, TIPO_CUENTA, CONCEPTO_ID"
					+ " FROM MATEO.CONT_MOVIMIENTO "+orden;
			
			lista = enocJdbc.query(comando, new ContMovimientoMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.spring.ContMovimientoDao|lisTodos|:"+ex);
		}
		return lista;
	}	
	
	public List<ContMovimiento> lisMovtosAlumno(String codigoPersonal, String libros, String mayor, String year, String naturaleza ){
		
		List<ContMovimiento> lista 	= new ArrayList<ContMovimiento>();		
		try{			
			String comando = " SELECT ID_EJERCICIO, ID_LIBRO, ID_CCOSTO, FOLIO, NUMMOVTO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, DESCRIPCION, IMPORTE, NATURALEZA," 
					+ " REFERENCIA, REFERENCIA2, ID_CTAMAYORM, ID_CCOSTOM, ID_AUXILIARM, STATUS, TIPO_CUENTA, CONCEPTO_ID "
					+ " FROM MATEO.CONT_MOVIMIENTO"
					+ " WHERE ID_AUXILIARM = ?"
					+ " AND ID_CTAMAYORM IN ("+mayor+")"
					+ " AND ID_LIBRO NOT IN("+libros+")"
					+ " AND TO_CHAR(FECHA,'YYYY') IN ("+year+")"
					+ " AND NATURALEZA IN ("+naturaleza+")";
			//Object[] parametros = new Object[] { codigoPersonal, };
			lista = enocJdbc.query(comando, new ContMovimientoMapper(), codigoPersonal);
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.spring.ContMovimientoUtil|lisMovtosAlumno|:"+ex);
		}
		
		return lista;
	}
	
	//
	public double getSaldoAnterior(String matricula, String ejercicioId, String libro, String cuentas ){
		
		double	saldo		= 0;
		
		try{
			String comando = "SELECT COALESCE(SUM(IMPORTE* CASE NATURALEZA WHEN 'C' THEN 1 ELSE -1 END),0)"
					+ " FROM MATEO.CONT_MOVIMIENTO"
					+ " WHERE ID_AUXILIARM = ?"
					+ " AND ID_EJERCICIO = ?"
					+ " AND ID_LIBRO = ?"
					+ " AND ID_CTAMAYORM IN ("+cuentas+")";
			saldo = enocJdbc.queryForObject(comando, Double.class, matricula, ejercicioId, libro);
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.ContMovimiento|getSaldoAnterior|:"+ex);
		}
		
		return saldo;
	}
	
	// Saldo del año completo
	public double getSaldoAnterior(String matricula, String ejercicioId, String cuentas ){		
		double	saldo		= 0;		
		try{
			String comando = "SELECT COALESCE(SUM(IMPORTE* CASE NATURALEZA WHEN 'C' THEN 1 ELSE -1 END),0)"
					+ " FROM MATEO.CONT_MOVIMIENTO"
					+ " WHERE ID_AUXILIARM = ?"
					+ " AND ID_EJERCICIO = ?"					
					+ " AND ID_CTAMAYORM IN ("+cuentas+")";
			saldo = enocJdbc.queryForObject(comando, Double.class, matricula, ejercicioId);
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.spring.ContMovimiento|getSaldoAnterior|:"+ex);
		}		
		return saldo;
	}
	
	public double getSaldo(String matricula) {
	    double saldo = 0;
	    try {
	        String comando = "SELECT" +
	        	" COALESCE(SUM(IMPORTE* CASE NATURALEZA WHEN 'C' THEN 1 ELSE -1 END ),0) AS SALDO" +
	        	" FROM MATEO.CONT_MOVIMIENTO" +
	        	" WHERE ID_AUXILIARM = ?" +
	        	" AND ID_EJERCICIO ='001-'||TO_CHAR(now(),'YYYY')" +
	        	" AND ID_CTAMAYORM IN('1.1.04.01','1.1.04.29','1.1.04.30')" +
	        	" AND TIPO_CUENTA = 'B'";
	        
	        Object[] parametros = new Object[] {matricula};
	        
	        saldo = enocJdbc.queryForObject(comando, Double.class,parametros);
	        
        }catch (Exception ex) {
        	System.out.println("Error - aca.financiero.spring.ContMovimiento|getSaldo|:"+ex);
        }

	    return saldo;
	}
	
	public String getSuma(String porcentaje, String meses, String cuenta, String idEjercicio){
				
		String suma		 	= "";
		String comando 		= "";
		try{
			if(!meses.equals("")){
				comando = "SELECT SUM(IMPORTE)*("+porcentaje+"/100) AS SUMA FROM MATEO.CCP_PRESUPUESTO WHERE ID_EJERCICIO = ? " +
						"AND ID_CTAMAYOR = '1.3.05' AND MES IN ("+meses+")";
			}else{
				comando = "SELECT SUM(IMPORTE)*("+porcentaje+"/100) AS SUMA FROM MATEO.CCP_PRESUPUESTO WHERE ID_EJERCICIO = ? " +
						"AND ID_CTAMAYOR = '"+cuenta+"'";
			}
			suma = enocJdbc.queryForObject(comando, String.class, idEjercicio);
		}catch(Exception e){
			System.out.println("Error - aca.financiero.CCP_PresupuestoUtil|getSuma|:"+e);
		}
		return suma;
	}
	
	
	public String getSumaCcosto(String porcentaje, String meses, String cuenta, String idEjercicio, String ccosto){				
		String suma		 	= "";
		String comando 		= "";
		try{
			if(!meses.equals("")){
				comando = "SELECT COALESCE( SUM(IMPORTE)*("+porcentaje+"/100) ,0) AS SUMA FROM MATEO.CCP_PRESUPUESTO WHERE ID_CCOSTO = ? AND ID_EJERCICIO = ? " +
					"AND ID_CTAMAYOR = ? AND MES IN ("+meses+")";
			}else{
				comando = "SELECT COALESCE( SUM(IMPORTE)*("+porcentaje+"/100) ,0) AS SUMA FROM MATEO.CCP_PRESUPUESTO WHERE ID_CCOSTO = ? AND ID_EJERCICIO = ? " +
					"AND ID_CTAMAYOR = ?";
			}
			suma = enocJdbc.queryForObject(comando, String.class, ccosto, idEjercicio, cuenta);			
		}catch(Exception e){
			System.out.println("Error - aca.financiero.ContMovimientoDao|getSumaCcosto|:"+e);
		}
		return suma;
	}
	
}