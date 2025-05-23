package aca.financiero.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class A3lAsalfldgDao {
	
	// @Autowired
	// @Qualifier("jdbcSunPlus")
	// private JdbcTemplate sunPlusJdbc;	
	
	// public boolean existeReg(String accntCode, String period, String transDatetime, String jrnalNo, String jrnalLine){
	// 	boolean ok 		= false;
		
	// 	try{
	// 		String comando = "SELECT COUNT(*) FROM SunSystemsData.dbo.A3L_A_SALFLDG "
	// 				+ " WHERE ACCNT_CODE = ? AND PERIOD = ? AND TRANS_DATETIME = ? AND JRNAL_NO = ?  AND JRNAL_LINE = ?";
			
	// 		Object[] parametros = new Object[] {accntCode,period,transDatetime,jrnalNo,jrnalLine};
	// 		if (sunPlusJdbc.queryForObject(comando,Integer.class, parametros) >= 1){
	// 			ok = true;
	// 		}
			
	// 	}catch(Exception ex){
	// 		System.out.println("Error - aca.financiero.spring.A3lAsalfldgDao|existeReg|:"+ex);
	// 	}
		
	// 	return ok;
	// }
	
	// public A3lAsalfldg mapeaRegId(String accntCode, String period, String transDatetime, String jrnalNo, String jrnalLine ){
		
	// 	A3lAsalfldg asalfldg = new A3lAsalfldg();
		
	// 	try{
	// 		String comando = "SELECT ACCNT_CODE, PERIOD, CONVERT(VARCHAR(10),TRANS_DATETIME,120) AS TRANS_DATETIME, "
	// 				+ " JRNAL_NO, JRNAL_LINE, AMOUNT, D_C, ALLOCATION, JRNAL_TYPE, JRNAL_SRCE, TREFERENCE, DESCRIPTN, "
	// 				+ " DUE_DATETIME, ASSET_IND, ASSET_CODE, ANAL_T0, ANAL_T1, ANAL_T2, ANAL_T3, ANAL_T4, ANAL_T5, ANAL_T6, "
	// 				+ " ANAL_T7, ANAL_T8, ANAL_T9, POSTING_DATETIME, ALLOC_IN_PROGRESS, VCHR_NUM, ORIGINATED_DATETIME "
	// 				+ " FROM SunSystemsData.dbo.A3L_A_SALFLDG "
	// 				+ " WHERE ACCNT_CODE = ? AND PERIOD = ? AND TRANS_DATETIME = ? AND JRNAL_NO = ? AND JRNAL_LINE = ?";
			
	// 		Object[] parametros = new Object[] {accntCode,period,transDatetime,jrnalNo,jrnalLine};
	// 		asalfldg = sunPlusJdbc.queryForObject(comando, new A3lAsalfldgMapper(), parametros);
			
	// 	}catch(Exception ex){
	// 		System.out.println("Error - aca.financiero.spring.A3lAsalfldgDao|mapeaRegId|:"+ex);
	// 	}
		
	// 	return asalfldg;
	// }
	
	// public List<A3lAsalfldg> lisMovtosAlumno(String accntCode, String codigoAlumno, String year, String orden ){
	// 	List<A3lAsalfldg> lista	= new ArrayList<A3lAsalfldg>();
	// 	int periodoIni 	= Integer.parseInt(year+"000");
	// 	int periodoFin	= Integer.parseInt(year+"999");
	// 	try{
	// 		String comando = "SELECT ACCNT_CODE, PERIOD, CONVERT(VARCHAR(10),TRANS_DATETIME,120) AS TRANS_DATETIME, "
	// 				+ " JRNAL_NO, JRNAL_LINE, AMOUNT, D_C, ALLOCATION, JRNAL_TYPE, JRNAL_SRCE, TREFERENCE, DESCRIPTN, "
	// 				+ " DUE_DATETIME, ASSET_IND, ASSET_CODE, ANAL_T0, ANAL_T1, ANAL_T2, ANAL_T3, ANAL_T4, ANAL_T5, ANAL_T6, "
	// 				+ " ANAL_T7, ANAL_T8, ANAL_T9, POSTING_DATETIME, ALLOC_IN_PROGRESS, VCHR_NUM, ORIGINATED_DATETIME "
	// 				+ " FROM SunSystemsData.dbo.A3L_A_SALFLDG"
	// 				+ " WHERE ACCNT_CODE IN( "+accntCode+" )"
	// 				+ " AND PERIOD >= ?"
	// 				+ " AND PERIOD <= ?"
	// 				+ " AND ANAL_T6 = ? "+orden; 
			
	// 		Object[] parametros = new Object[] {periodoIni,periodoFin, codigoAlumno};			
	// 		lista = sunPlusJdbc.query(comando, new A3lAsalfldgMapper(), parametros);
			
	// 	}catch(Exception ex){
	// 		System.out.println("Error - aca.financiero.spring.A3lAsalfldgDao|lisMovtosAlumno|:"+ex);
	// 	}
		
	// 	return lista;
	// }
	
	// public List<A3lAsalfldg> lisMovtosAlumnoPorCuentas(String accntCode, String codigoAlumno, String year, String cuentas, String orden ){
	// 	List<A3lAsalfldg> lista	= new ArrayList<A3lAsalfldg>();
	// 	int periodoIni 	= Integer.parseInt(year+"000");
	// 	int periodoFin	= Integer.parseInt(year+"999");
	// 	try{
	// 		String comando = "SELECT ACCNT_CODE, PERIOD, CONVERT(VARCHAR(10),TRANS_DATETIME,120) AS TRANS_DATETIME, "
	// 				+ " JRNAL_NO, JRNAL_LINE, AMOUNT, D_C, ALLOCATION, JRNAL_TYPE, JRNAL_SRCE, TREFERENCE, DESCRIPTN, "
	// 				+ " DUE_DATETIME, ASSET_IND, ASSET_CODE, ANAL_T0, ANAL_T1, ANAL_T2, ANAL_T3, ANAL_T4, ANAL_T5, ANAL_T6, "
	// 				+ " ANAL_T7, ANAL_T8, ANAL_T9, POSTING_DATETIME, ALLOC_IN_PROGRESS, VCHR_NUM, ORIGINATED_DATETIME "
	// 				+ " FROM SunSystemsData.dbo.A3L_A_SALFLDG"
	// 				+ " WHERE ACCNT_CODE IN( "+accntCode+" )"
	// 				+ " AND PERIOD >= ?"
	// 				+ " AND PERIOD <= ?"
	// 				+ " AND ANAL_T6 = ?"
	// 				+ " AND ANAL_T5 IN ("+cuentas+") "+orden;
	// 		Object[] parametros = new Object[] {periodoIni,periodoFin, codigoAlumno,};			
	// 		lista = sunPlusJdbc.query(comando, new A3lAsalfldgMapper(), parametros);
			
	// 	}catch(Exception ex){
	// 		System.out.println("Error - aca.financiero.spring.A3lAsalfldgDao|lisMovtosAlumnoPorCuentas|:"+ex);
	// 	}
		
	// 	return lista;
	// }
	
	// public List<String> lisAlumnoCuentas(String accntCode, String codigoAlumno, String year, String orden ){
	// 	List<String> lista	= new ArrayList<String>();
	// 	int periodoIni 	= Integer.parseInt(year+"000");
	// 	int periodoFin	= Integer.parseInt(year+"999");
	// 	try{
	// 		String comando = "SELECT DISTINCT(ANAL_T5) FROM SunSystemsData.dbo.A3L_A_SALFLDG"
	// 				+ " WHERE ACCNT_CODE IN( "+accntCode+" )"
	// 				+ " AND PERIOD >= ?"
	// 				+ " AND PERIOD <= ?"
	// 				+ " AND ANAL_T6 = ? "+orden;			
	// 		Object[] parametros = new Object[] {periodoIni,periodoFin, codigoAlumno};			
	// 		lista = sunPlusJdbc.queryForList(comando, String.class, parametros);			
	// 	}catch(Exception ex){
	// 		System.out.println("Error - aca.financiero.spring.A3lAsalfldgDao|lisAlumnoCuentas|:"+ex);
	// 	}		
	// 	return lista;
	// }

	// public List<String> lisAlumnoCuentasSinFecha(String accntCode, String codigoAlumno, String orden ){
	// 	List<String> lista	= new ArrayList<String>();
	// 	try{
	// 		String comando = "SELECT DISTINCT(ANAL_T5) FROM SunSystemsData.dbo.A3L_A_SALFLDG"
	// 				+ " WHERE ACCNT_CODE IN( "+accntCode+" )"
	// 				+ " AND ANAL_T6 = ? "+orden;			
	// 		Object[] parametros = new Object[] {codigoAlumno};			
	// 		lista = sunPlusJdbc.queryForList(comando, String.class, parametros);			
	// 	}catch(Exception ex){
	// 		System.out.println("Error - aca.financiero.spring.A3lAsalfldgDao|lisAlumnoCuentasSinFecha|:"+ex);
	// 	}		
	// 	return lista;
	// }
	
	// public HashMap<String, String> mapaMovtosPorCuenta(String accntCode, String codigoAlumno, String year){
	// 	HashMap<String, String> mapa = new HashMap<String, String>();
	// 	List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();
	// 	int periodoIni 	= Integer.parseInt(year+"000");
	// 	int periodoFin	= Integer.parseInt(year+"999");
	// 	try{			
	// 		String comando = "SELECT RTRIM(ANAL_T5) AS LLAVE, COUNT(*) AS VALOR FROM SunSystemsData.dbo.A3L_A_SALFLDG"
	// 				+ " WHERE ACCNT_CODE IN( "+accntCode+" )"
	// 				+ " AND PERIOD >= ?"
	// 				+ " AND PERIOD <= ?"
	// 				+ " AND ANAL_T6 = ?"
	// 				+ " GROUP BY ANAL_T5";		
	// 		Object[] parametros = new Object[] {periodoIni,periodoFin, codigoAlumno};
	// 		lista = sunPlusJdbc.query(comando, new aca.MapaMapper(), parametros);			
	// 		for (aca.Mapa map:lista){			
	// 			mapa.put(map.getLlave(), (String)map.getValor());
	// 		}		
	// 	}catch(Exception ex){
	// 		System.out.println("Error - aca.financiero.spring.A3lAsalfldgDao|mapaMovtosPorCuenta|:"+ex);
	// 	}
		
	// 	return mapa;
	// }
	
	
	// public double getSaldoAlumno(String codigoAlumno){
	// 	double saldo 		= 0;		
	// 	try{
	// 		String comando = "SELECT ISNULL(SUM(AMOUNT),0) AS IMPORTE"
	// 				+ " FROM SunSystemsData.dbo.A3L_A_SALFLDG"
	// 				+ " WHERE ANAL_T6= ?";			
	// 		Object[] parametros = new Object[] {codigoAlumno};
	// 		saldo = sunPlusJdbc.queryForObject(comando,Double.class, parametros);			
	// 	}catch(Exception ex){
	// 		System.out.println("Error - aca.financiero.spring.A3lAsalfldgDao|getSaldoAlumno|:"+ex);
	// 	}
		
	// 	return saldo;
	// }
	
	// public double getSaldoAlumno(String codigoAlumno, String cuentas){
	// 	double saldo 		= 0;		
	// 	try{
	// 		String comando = "SELECT ISNULL(SUM(AMOUNT),0) AS IMPORTE FROM SunSystemsData.dbo.A3L_A_SALFLDG"
	// 				+ " WHERE ANAL_T6= ?"
	// 				+ " AND ACCNT_CODE = ?";
	// 		Object[] parametros = new Object[] {codigoAlumno, cuentas};
	// 		saldo = sunPlusJdbc.queryForObject(comando,Double.class, parametros);			
	// 	}catch(Exception ex){
	// 		System.out.println("Error - aca.financiero.spring.A3lAsalfldgDao|getSaldoAlumno|:"+ex);
	// 	}
		
	// 	return saldo;
	// }
	
	// public double getSaldoAnteriorAlumno(String codigoAlumno, String fecha, String cuentas){
	// 	double saldo 		= 0;		
	// 	try{
	// 		String comando = "SELECT ISNULL(SUM(AMOUNT),0) AS IMPORTE FROM SunSystemsData.dbo.A3L_A_SALFLDG"
	// 				+ " WHERE ANAL_T6= ?"					
	// 				+ " AND TRANS_DATETIME < CONVERT(DATETIME,?,103)"
	// 				+ " AND ACCNT_CODE IN("+cuentas+")";			
	// 		Object[] parametros = new Object[] {codigoAlumno, fecha};
	// 		saldo = sunPlusJdbc.queryForObject(comando,Double.class, parametros);			
	// 	}catch(Exception ex){
	// 		System.out.println("Error - aca.financiero.spring.A3lAsalfldgDao|getSaldoAnteriorAlumno|:"+ex);
	// 	}		
	// 	return saldo;
	// }
	
	// public double getSaldoAnteriorPorTipo(String codigoAlumno, String fecha, String cuentas, String detalle){
	// 	double saldo 		= 0;		
	// 	try{
	// 		String comando = "SELECT ISNULL(SUM(AMOUNT),0) AS IMPORTE FROM SunSystemsData.dbo.A3L_A_SALFLDG"
	// 				+ " WHERE ANAL_T6= ?"					
	// 				+ " AND TRANS_DATETIME < CONVERT(DATETIME,?,103)"
	// 				+ " AND ACCNT_CODE IN("+cuentas+")"
	// 				+ " AND ANAL_T5 = ?";			
	// 		Object[] parametros = new Object[] {codigoAlumno, fecha, detalle};
	// 		saldo = sunPlusJdbc.queryForObject(comando,Double.class, parametros);			
	// 	}catch(Exception ex){
	// 		System.out.println("Error - aca.financiero.spring.A3lAsalfldgDao|getSaldoAnteriorPorTipo|:"+ex);
	// 	}		
	// 	return saldo;
	// }

	// public double getSaldoAnteriorPorTipoSinFecha(String codigoAlumno, String cuentas, String detalle){
	// 	double saldo 		= 0;		
	// 	try{
	// 		String comando = "SELECT ISNULL(SUM(AMOUNT),0) AS IMPORTE FROM SunSystemsData.dbo.A3L_A_SALFLDG"
	// 				+ " WHERE ANAL_T6= ?"					
	// 				+ " AND ACCNT_CODE IN("+cuentas+")"
	// 				+ " AND ANAL_T5 = ?";			
	// 		Object[] parametros = new Object[] {codigoAlumno, detalle};
	// 		saldo = sunPlusJdbc.queryForObject(comando,Double.class, parametros);			
	// 	}catch(Exception ex){
	// 		System.out.println("Error - aca.financiero.spring.A3lAsalfldgDao|getSaldoAnteriorPorTipoSinFecha|:"+ex);
	// 	}		
	// 	return saldo;
	// }
}
