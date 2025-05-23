package aca.por.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class PorSalonDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( PorSalon salon ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.POR_SALON "+ 
				"(SALON_ID, SALON_NOMBRE ) "+
				"VALUES( TO_NUMBER(?,'99'), ?)";

			Object[] parametros = new Object[] {salon.getSalonId(), salon.getSalonNombre()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.por.spring.PorSalonDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg( PorSalon salon ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.POR_SALON " + 
				"SET SALON_NOMBRE = ?, " +
				"WHERE SALON_ID = TO_NUMBER(?,'99') ";
			
			Object[] parametros = new Object[] {salon.getSalonNombre(), salon.getSalonId()};				
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.por.spring.PorSalonDao|updateReg|:"+ex);		
		}
		return ok;
	}
	
	public boolean deleteReg( String salonId ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.POR_SALON " + 
					"WHERE SALON_ID = TO_NUMBER(?,'99') ";
			
			Object[] parametros = new Object[] {salonId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.por.spring.PorSalonDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public PorSalon mapeaRegId(  String salonId) {
		PorSalon salon = new PorSalon();
		try{
			String comando = "SELECT SALON_ID, SALON_NOMBRE FROM ENOC.POR_SALON WHERE SALON_ID = TO_NUMBER(?,'99')";
			
				Object[] parametros = new Object[] {salonId};
				salon = enocJdbc.queryForObject(comando, new PorSalonMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.por.spring.PorSalonDao|mapeaRegId|:"+ex);
		}
		return salon;		
	}	
	
	public boolean existeReg( String salonId) {
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.POR_SALON WHERE SALON_ID = TO_NUMBER(?,'99') "; 
			
			Object[] parametros = new Object[] {salonId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.por.spring.PorSalonDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public List<PorSalon> getListAll( String orden ) {
		List<PorSalon> lista	= new ArrayList<PorSalon>();
		String comando	= "";
		
		try{
			comando = "SELECT SALON_ID, SALON_NOMBRE FROM ENOC.POR_SALON "+orden; 
			
			lista = enocJdbc.query(comando, new PorSalonMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.por.spring.PorSalonDao|getListAll|:"+ex);
		}		
		return lista;
	}
	
	public HashMap<String,String> mapaAll( ) {	
		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT SALON_ID AS LLAVE, SALON_NOMBRE AS VALOR FROM POR_SALON";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.por.spring.PorSalonDao|mapaAll|:"+ex);
			ex.printStackTrace();
		}
		
		return mapa;		
	}
}