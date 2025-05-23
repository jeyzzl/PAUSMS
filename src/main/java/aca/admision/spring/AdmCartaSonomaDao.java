package aca.admision.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AdmCartaSonomaDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( AdmCartaSonoma objeto){
		boolean ok = false;
		try{
			String comando = "INSERT INTO SALOMON.ADM_CARTA_SONOMA(CARTA_ID, FECHA_FINAL_PAGO)"
					+ " VALUES(TO_NUMBER(?,'99'),TO_DATE(?,'DD/MM/YYYY'))";
			Object[] parametros = new Object[] {objeto.getCartaId(), objeto.getFechaFinal()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmCartaSonomaDao|insertReg|:"+ex);
		}		
		return ok;
	}	
	
	public boolean updateReg(AdmCartaSonoma objeto){
		boolean ok = false;		
		try{
			String comando =  " UPDATE SALOMON.ADM_CARTA_SONOMA SET FECHA_FINAL_PAGO = TO_DATE(?,'DD/MM/YYYY')"
							+ " WHERE CARTA_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {objeto.getFechaFinal(), objeto.getCartaId()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmCartaSonomaDao|updateReg|:"+ex);		
		}		
		return ok;
	}	
	
	public boolean deleteReg(String folio){
		boolean ok = false;		
		try{
			String comando = "DELETE FROM SALOMON.ADM_CARTA_SONOMA WHERE CARTA_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {folio};
			if (enocJdbc.update(comando,parametros)==1){			
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmCartaSonomaDao|deleteReg|:"+ex);			
		}		
		return ok;
	}
	
	
	public AdmCartaSonoma mapeaRegId(String folio) {
		AdmCartaSonoma objeto = new AdmCartaSonoma();		
		try{
			String comando = "SELECT CARTA_ID, TO_CHAR(FECHA_FINAL_PAGO,'DD/MM/YYYY') AS FECHA_FINAL_PAGO FROM SALOMON.ADM_CARTA_SONOMA WHERE CARTA_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {folio};
			objeto = enocJdbc.queryForObject(comando, new AdmCartaSonomaMapper(),parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmCartaSonomaDao|mapeaRegId|:"+ex);
		}		
		return objeto;
	}
	
	
	public boolean existeReg( String folio){
		boolean ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM SALOMON.ADM_CARTA_SONOMA WHERE CARTA_ID = TO_NUMBER(?,'99')"; 
			Object[] parametros = new Object[] {folio};	
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){	
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmCartaSonomaDao|existeReg|:"+ex);
		}	
		return ok;
	}
	
	public List<AdmCartaSonoma> lisTodos (String orden){
		List<AdmCartaSonoma> lista = new ArrayList<AdmCartaSonoma>();
		
		try {
			String comando = "SELECT CARTA_ID, FECHA_FINAL_PAGO FROM SALOMON.ADM_CARTA_SONOMA "+ orden;			
			lista = enocJdbc.query(comando, new AdmCartaSonomaMapper());
		}catch(Exception ex) {
			System.out.println("Error - aca.admision.spring.AdmCartaSonomaDao|lisTodos|:"+ex);
		}
		return lista;
	}
	

}
