package aca.admision.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AdmCartaSubirDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(AdmCartaSubir objeto){
		boolean ok = false;
		try{
			String comando = "INSERT INTO SALOMON.ADM_CARTA_SUBIR(FOLIO, NOMBRE, CARTA, FECHA)"
					+ " VALUES(TO_NUMBER(?,'99999999'),?,?,TO_DATE(?,'DD/MM/YYYY'))";
			Object[] parametros = new Object[] {objeto.getFolio(), objeto.getNombre(), objeto.getCarta(), objeto.getFecha()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmCartaSubirDao|insertReg|:"+ex);
		}		
		return ok;
	}	
	
	public boolean updateReg(AdmCartaSubir objeto){
		boolean ok = false;		
		try{
			String comando = "UPDATE SALOMON.ADM_CARTA_SUBIR SET CARTA = ?, FECHA = TO_DATE(?,'YYYY/MM/DD'), NOMBRE = ? WHERE FOLIO = TO_NUMBER(?,'99999999')";
			Object[] parametros = new Object[] {objeto.getCarta(), objeto.getFecha(), objeto.getNombre(), objeto.getFolio()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmCartaSubirDao|updateReg|:"+ex);		
		}		
		return ok;
	}	
	
	public boolean deleteReg(String folio){
		boolean ok = false;		
		try{
			String comando = "DELETE FROM SALOMON.ADM_CARTA_SUBIR WHERE FOLIO = TO_NUMBER(?,'99999999')";
			Object[] parametros = new Object[] {folio};
			if (enocJdbc.update(comando,parametros)==1){			
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmCartaSubirDao|deleteReg|:"+ex);			
		}		
		return ok;
	}
	
	public AdmCartaSubir mapeaRegId(String folio) {
		AdmCartaSubir objeto = new AdmCartaSubir();		
		try{
			String comando = "SELECT FOLIO, NOMBRE, CARTA, FECHA FROM SALOMON.ADM_CARTA_SUBIR WHERE FOLIO = TO_NUMBER(?,'99999999')";
			Object[] parametros = new Object[] {folio};
			objeto = enocJdbc.queryForObject(comando, new AdmCartaSubirMapper(),parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmCartaSubirDao|mapeaRegId|:"+ex);
		}		
		return objeto;
	}
	
	public boolean existeReg(String folio){
		boolean ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM SALOMON.ADM_CARTA_SUBIR WHERE FOLIO = TO_NUMBER(?,'99999999')"; 
			Object[] parametros = new Object[] {folio};	
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){	
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmCartaSubirDao|existeReg|:"+ex);
		}	
		return ok;
	}
	
	public List<AdmCartaSubir> lisPorFolio (String folio, String orden){
		List<AdmCartaSubir> lista = new ArrayList<AdmCartaSubir>();
		
		try {
			String comando = "SELECT FOLIO, NOMBRE, CARTA, FECHA FROM SALOMON.ADM_CARTA_SUBIR WHERE FOLIO = TO_NUMBER(?,'99999999') "+ orden;
			Object[] parametros = new Object[] {folio};
			lista = enocJdbc.query(comando, new AdmCartaSubirMapper(),parametros);
		}catch(Exception ex) {
			System.out.println("Error - aca.admision.spring.AdmCartaSubirDao|lisPorFolio|:"+ex);
		}
		return lista;
	}

}
