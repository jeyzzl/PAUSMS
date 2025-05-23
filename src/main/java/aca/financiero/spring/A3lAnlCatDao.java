package aca.financiero.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class A3lAnlCatDao {
	
	// @Autowired
	// @Qualifier("jdbcSunPlus")
	// private JdbcTemplate sunPlusJdbc;
	
	//Metodos
	
	// public boolean existeReg( String anlCatId) {
	// 	boolean ok = false;
		
	// 	try{
	// 		String comando = "SELECT COUNT(*) FROM SunSystemsData.dbo.A3L_ANL_CAT WHERE ANL_CAT_ID = ?";
	// 		Object[] parametros = new Object[] {anlCatId};
	// 		if (sunPlusJdbc.queryForObject(comando, Integer.class, parametros)>=1){
	// 			ok = true;
	// 		}
			
	// 	}catch(Exception ex){
	// 		System.out.println("Error - aca.financiero.spring.A3lAnlCatDao|existeReg|:"+ex);
	// 	}
		
	// 	return ok;
	// }
	
	// public A3lAnlCat mapeaRegId(String anlCatId) {
		
	// 	A3lAnlCat anlCat = new A3lAnlCat();
		
	// 	try{
	// 		String comando = "SELECT ANL_CAT_ID, UPDATE_COUNT, LAST_CHANGE_USER_ID,"
	// 				+ " LAST_CHANGE_DATETIME, STATUS, LOOKUP, USEABLE_ANL_ENT_ID,"
	// 				+ "S_HEAD, DESCR, DAG_CODE, AMEND_CODE, VALIDATE_IND, LNGTH, LINKED, ANL_CODE_FORM "
	// 				+ " FROM SunSystemsData.dbo.A3L_ANL_CAT WHERE ANL_CAT_ID = ? "; 
	// 		Object[] parametros = new Object[] {anlCatId};
	// 		anlCat = sunPlusJdbc.queryForObject(comando, new A3lAnlCatMapper(), parametros);
			
	// 	}catch(Exception ex){
	// 		System.out.println("Error - aca.financiero.spring.A3lAnlCatDao|mapeaRegId|:"+ex);
	// 	}
		
	// 	return anlCat;
	// }
	
	// public ArrayList<A3lAnlCat> getListAll( String orden) {
		
	// 	List<A3lAnlCat> lista = new ArrayList<A3lAnlCat>();
		
	// 	try{
	// 		String comando = "SELECT ANL_CAT_ID, UPDATE_COUNT, LAST_CHANGE_USER_ID,"
	// 				+ " LAST_CHANGE_DATETIME, STATUS, LOOKUP, USEABLE_ANL_ENT_ID,"
	// 				+ " S_HEAD, DESCR, DAG_CODE, AMEND_CODE, VALIDATE_IND, LNGTH,"
	// 				+ " LINKED, ANL_CODE_FORM FROM SunSystemsData.dbo.A3L_ANL_CAT "+orden;
	// 		lista = sunPlusJdbc.query(comando, new A3lAnlCatMapper());
	// 	}catch(Exception ex){
	// 		System.out.println("Error - aca.financiero.spring.A3lAnlCatDao|getListAll|:"+ex);
	// 	}
		
	// 	return (ArrayList<A3lAnlCat>)lista;
	// }
	
	/*
	public HashMap<String, String> getAccesoDepto( String ejercicioId) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<A3lAnlCat> lista 	= new ArrayList<A3lAnlCat>();
		StringBuffer usuarios	= new StringBuffer();
		String depto 			= "x";
		int row 				= 0;
		try{
			String comando = "SELECT SELECT CODIGO_PERSONAL, ID_EJERCICIO, ID_CCOSTO, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, USUARIO"
					+ " FROM ENOC.BEC_ACCESO WHERE ID_EJERCICIO = ? ORDER BY ID_CCOSTO";
			Object[] parametros = new Object[] {ejercicioId};
			lista = enocJdbc.query(comando, parametros, new A3lAnlCatMapper());
			for( A3lAnlCat acceso : lista){
				if ( !depto.equals(acceso.getIdCcosto()) && !depto.equals("x")){
					// grabar elemento en el mapa
					mapa.put(depto, usuarios.toString());						
					row = 0;
					usuarios.delete(0,usuarios.length());					
				}
				if (row>0) usuarios.append(",");
				usuarios.append(acceso.getCodigoPersonal());
				depto = acceso.getIdCcosto();					
				row++;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.BecAccesoDao|getAccesoDepto|:"+ex);
		}
		
		return mapa;
	}
	*/
		
}
