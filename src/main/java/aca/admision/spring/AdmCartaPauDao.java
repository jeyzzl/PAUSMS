package aca.admision.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AdmCartaPauDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( AdmCartaPau objeto){
		boolean ok = false;
		try{
			String comando = "INSERT INTO SALOMON.ADM_CARTA_PAU(CARTA_ID, FECHA_REGISTRO, FECHA_ORIENTACION, FECHA_APERTURA, FECHA_INICIO, FECHA_CIERRE)"
					+ " VALUES(TO_NUMBER(?,'99'), TO_DATE(?,'DD/MM/YYYY'), TO_DATE(?,'DD/MM/YYYY'), TO_DATE(?,'DD/MM/YYYY'), TO_DATE(?,'DD/MM/YYYY'), TO_DATE(?,'DD/MM/YYYY'))";
			Object[] parametros = new Object[] {objeto.getCartaId(), objeto.getFechaRegistro(), objeto.getFechaOrientacion(), objeto.getFechaApertura(), objeto.getFechaInicio(), objeto.getFechaCierre()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmCartaPauDao|insertReg|:"+ex);
		}		
		return ok;
	}	
	
	public boolean updateReg(AdmCartaPau objeto){
		boolean ok = false;		
		try{
			String comando =  " UPDATE SALOMON.ADM_CARTA_PAU SET FECHA_REGISTRO = TO_DATE(?,'DD/MM/YYYY'), FECHA_ORIENTACION = TO_DATE(?,'DD/MM/YYYY'), FECHA_APERTURA = TO_DATE(?,'DD/MM/YYYY'), "
							+ " FECHA_INICIO = TO_DATE(?,'DD/MM/YYYY'), FECHA_CIERRE = TO_DATE(?,'DD/MM/YYYY')"
							+ " WHERE CARTA_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmCartaPauDao|updateReg|:"+ex);		
		}		
		return ok;
	}	
	
	public boolean deleteReg(String folio){
		boolean ok = false;		
		try{
			String comando = "DELETE FROM SALOMON.ADM_CARTA_PAU WHERE CARTA_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {folio};
			if (enocJdbc.update(comando,parametros)==1){			
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmCartaPauDao|deleteReg|:"+ex);			
		}		
		return ok;
	}
	
	
	public AdmCartaPau mapeaRegId(String folio) {
		AdmCartaPau objeto = new AdmCartaPau();		
		try{
			String comando 	= "SELECT CARTA_ID,TO_CHAR(FECHA_REGISTRO,'DD/MM/YYYY') AS FECHA_REGISTRO,TO_CHAR(FECHA_ORIENTACION,'DD/MM/YYYY') AS FECHA_ORIENTACION, "
							+ "TO_CHAR(FECHA_APERTURA,'DD/MM/YYYY') AS FECHA_APERTURA,TO_CHAR(FECHA_INICIO,'DD/MM/YYYY') AS FECHA_INICIO,TO_CHAR(FECHA_CIERRE,'DD/MM/YYYY') AS FECHA_CIERRE "
							+ "FROM SALOMON.ADM_CARTA_PAU WHERE CARTA_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {folio};
			objeto = enocJdbc.queryForObject(comando, new AdmCartaPauMapper(),parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmCartaPauDao|mapeaRegId|:"+ex);
		}		
		return objeto;
	}
	
	
	public boolean existeReg( String folio){
		boolean ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM SALOMON.ADM_CARTA_PAU WHERE CARTA_ID = TO_NUMBER(?,'99')"; 
			Object[] parametros = new Object[] {folio};	
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){	
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmCartaPauDao|existeReg|:"+ex);
		}	
		return ok;
	}
	
	public List<AdmCartaPau> lisTodos (String orden){
		List<AdmCartaPau> lista = new ArrayList<AdmCartaPau>();
		
		try {
			String comando = "SELECT CARTA_ID, FECHA_REGISTRO, FECHA_ORIENTACION, FECHA_APERTURA, FECHA_INICIO, FECHA_CIERRE FROM SALOMON.ADM_CARTA_PAU "+ orden;			
			lista = enocJdbc.query(comando, new AdmCartaPauMapper());
		}catch(Exception ex) {
			System.out.println("Error - aca.admision.spring.AdmCartaPauDao|lisTodos|:"+ex);
		}
		return lista;
	}

}
