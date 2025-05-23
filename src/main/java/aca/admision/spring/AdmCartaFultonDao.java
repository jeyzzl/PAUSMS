package aca.admision.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AdmCartaFultonDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( AdmCartaFulton objeto){
		boolean ok = false;
		try{
			String comando = "INSERT INTO SALOMON.ADM_CARTA_FULTON(CARTA_ID, FECHA_REGISTRO, FECHA_ORIENTACION, FECHA_INICIO, FECHA_CIERRE, FECHA_ARRIBO)"
					+ " VALUES(TO_NUMBER(?,'99'), TO_DATE(?,'DD/MM/YYYY'), TO_DATE(?,'DD/MM/YYYY'), TO_DATE(?,'DD/MM/YYYY'), TO_DATE(?,'DD/MM/YYYY'), TO_DATE(?,'DD/MM/YYYY'))";
			Object[] parametros = new Object[] {objeto.getCartaId(), objeto.getFechaRegistro(), objeto.getFechaOrientacion(), objeto.getFechaInicio(), objeto.getFechaCierre(), objeto.getFechaArribo()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmCartaFultonDao|insertReg|:"+ex);
		}		
		return ok;
	}
	
	public boolean updateReg(AdmCartaFulton objeto){
		boolean ok = false;		
		try{
			String comando =  " UPDATE SALOMON.ADM_CARTA_FULTON SET FECHA_REGISTRO = TO_DATE(?,'DD/MM/YYYY'), FECHA_ORIENTACION = TO_DATE(?,'DD/MM/YYYY'),"
							+ " FECHA_INICIO = TO_DATE(?,'DD/MM/YYYY'), FECHA_CIERRE = TO_DATE(?,'DD/MM/YYYY'), FECHA_ARRIBO = TO_DATE(?,'DD/MM/YYYY')"
							+ " WHERE CARTA_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {objeto.getFechaRegistro(), objeto.getFechaOrientacion(), objeto.getFechaInicio(), objeto.getFechaCierre(), objeto.getFechaArribo(),objeto.getCartaId()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmCartaFultonDao|updateReg|:"+ex);		
		}		
		return ok;
	}	
	
	public boolean deleteReg(String folio){
		boolean ok = false;		
		try{
			String comando = "DELETE FROM SALOMON.ADM_CARTA_FULTON WHERE CARTA_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {folio};
			if (enocJdbc.update(comando,parametros)==1){			
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmCartaFultonDao|deleteReg|:"+ex);			
		}		
		return ok;
	}
	
	public AdmCartaFulton mapeaRegId(String folio) {
		AdmCartaFulton objeto = new AdmCartaFulton();		
		try{
			String comando 	= "SELECT CARTA_ID,TO_CHAR(FECHA_REGISTRO,'DD/MM/YYYY') AS FECHA_REGISTRO,TO_CHAR(FECHA_ORIENTACION,'DD/MM/YYYY') AS FECHA_ORIENTACION, "
							+ "TO_CHAR(FECHA_INICIO,'DD/MM/YYYY') AS FECHA_INICIO,TO_CHAR(FECHA_CIERRE,'DD/MM/YYYY') AS FECHA_CIERRE, TO_CHAR(FECHA_ARRIBO,'DD/MM/YYYY') AS FECHA_ARRIBO "
							+ "FROM SALOMON.ADM_CARTA_FULTON WHERE CARTA_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {folio};
			objeto = enocJdbc.queryForObject(comando, new AdmCartaFultonMapper(),parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmCartaFultonDao|mapeaRegId|:"+ex);
		}		
		return objeto;
	}
	
	
	public boolean existeReg( String folio){
		boolean ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM SALOMON.ADM_CARTA_FULTON WHERE CARTA_ID = TO_NUMBER(?,'99')"; 
			Object[] parametros = new Object[] {folio};	
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){	
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmCartaFultonDao|existeReg|:"+ex);
		}	
		return ok;
	}
	
	public List<AdmCartaFulton> lisTodos (String orden){
		List<AdmCartaFulton> lista = new ArrayList<AdmCartaFulton>();
		
		try {
			String comando = "SELECT CARTA_ID, FECHA_REGISTRO, FECHA_ORIENTACION, FECHA_APERTURA, FECHA_INICIO, FECHA_CIERRE, FECHA_ARRIBO FROM SALOMON.ADM_CARTA_FULTON "+ orden;			
			lista = enocJdbc.query(comando, new AdmCartaFultonMapper());
		}catch(Exception ex) {
			System.out.println("Error - aca.admision.spring.AdmCartaFultonDao|lisTodos|:"+ex);
		}
		return lista;
	}
	

}
