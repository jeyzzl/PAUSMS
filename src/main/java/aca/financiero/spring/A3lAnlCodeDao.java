package aca.financiero.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class A3lAnlCodeDao {
	
	// @Autowired
	// @Qualifier("jdbcSunPlus")
	// private JdbcTemplate sunPlusJdbc;
	
	// //Metodos
	
	// public boolean existeReg( String anlCatId, String anlCode) {
	// 	boolean ok = false;
		
	// 	try{
	// 		String comando = "SELECT COUNT(*) FROM SunSystemsData.dbo.A3L_ANL_CODE WHERE ANL_CAT_ID = ? AND ANL_CODE = ? ";
	// 		Object[] parametros = new Object[] {anlCatId, anlCode};
	// 		if (sunPlusJdbc.queryForObject(comando, Integer.class, parametros)>=1){
	// 			ok = true;
	// 		}
			
	// 	}catch(Exception ex){
	// 		System.out.println("Error - aca.financiero.spring.A3lAnlCodeDao|existeReg|:"+ex);
	// 	}
		
	// 	return ok;
	// }
	
	// public A3lAnlCode mapeaRegId( String anlCatId, String anlCode) {
		
	// 	A3lAnlCode a3lAnlCode = new A3lAnlCode();
		
	// 	try{
	// 		String comando = "SELECT ANL_CAT_ID, ANL_CODE, UPDATE_COUNT, LAST_CHANGE_USER_ID, LAST_CHANGE_DATETIME, STATUS, LOOKUP, NAME, DAG_CODE, BDGT_CHECK, BDGT_STOP, "
	// 				+ "PROHIBIT_POSTING, NAVIGATION_OPTION, COMBINED_BGDT_CHECK" +
	// 				" FROM SunSystemsData.dbo.A3L_ANL_CODE WHERE ANL_CAT_ID = ? AND ANL_CODE = ? "; 
	// 		Object[] parametros = new Object[] {anlCatId, anlCode};
	// 		a3lAnlCode = sunPlusJdbc.queryForObject(comando, new A3lAnlCodeMapper(), parametros);
			
	// 	}catch(Exception ex){
	// 		System.out.println("Error - aca.financiero.spring.A3lAnlCodeDao|mapeaRegId|:"+ex);
	// 	}
		
	// 	return a3lAnlCode;
	// }
	
	// public ArrayList<A3lAnlCode> getListAll( String orden) {
		
	// 	List<A3lAnlCode> lista = new ArrayList<A3lAnlCode>();
		
	// 	try{
	// 		String comando = "SELECT ANL_CAT_ID, ANL_CODE, UPDATE_COUNT, LAST_CHANGE_USER_ID, LAST_CHANGE_DATETIME, STATUS, LOOKUP, NAME, DAG_CODE, BDGT_CHECK, BDGT_STOP, PROHIBIT_POSTING, NAVIGATION_OPTION, COMBINED_BGDT_CHECK "
	// 				+ " FROM SunSystemsData.dbo.A3L_ANL_CODE"+orden;	
	// 		lista = sunPlusJdbc.query(comando, new A3lAnlCodeMapper());
	// 	}catch(Exception ex){
	// 		System.out.println("Error - aca.financiero.spring.A3lAnlCodeDao|getListAll|:"+ex);
	// 	}
		
	// 	return (ArrayList<A3lAnlCode>)lista;
	// }
	
	// // Mapa de nombres de los codigos en las dimensiones
	// public HashMap<String,String> mapaCodeDim( String dimension ){
		
	// 	HashMap<String,String> mapa	= new HashMap<String, String>();
	// 	List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
	// 	String comando	             		= "";	
		
	// 	try{			
	// 		comando = "SELECT ANL_CODE AS LLAVE, NAME AS VALOR FROM SunSystemsData.dbo.A3L_ANL_CODE WHERE ANL_CAT_ID = ?";
	// 		Object[] parametros = new Object[] {dimension};
	// 		lista = sunPlusJdbc.query(comando, new aca.MapaMapper(), parametros);
			
	// 		for (aca.Mapa map: lista){				
	// 			mapa.put(map.getLlave(), (String)map.getValor());
	// 		}
			
	// 	}catch(Exception ex){
	// 		System.out.println("Error - aca.financiero.spring.A3lAnlCodeDao|mapaCodeDim|:"+ex);
	// 	}
		
	// 	return mapa;
	// }
		
}
