package aca.admision.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AdmOpcionesDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(AdmOpciones objeto) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO SALOMON.ADM_OPCIONES (OPCION_ID, NOMBRE) VALUES( TO_NUMBER(?,'99'), ?)";
			
			Object[] parametros = new Object[] {
				objeto.getOpcionId(),objeto.getNombre()
 		 	};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmOpcionesDao|insertReg|:"+ex);
		}

		return ok;
	}
	
	public boolean updateReg(AdmOpciones objeto ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE SALOMON.ADM_OPCIONES SET NOMBRE = ? WHERE OPCION_ID = TO_NUMBER(?,'99')";
			
			Object[] parametros = new Object[] {
				objeto.getNombre(),objeto.getOpcionId()
 		 	};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmOpcionesDao|updateReg|:"+ex);
		}

		return ok;
	}
	
	public boolean deleteReg(String opcionId ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM SALOMON.ADM_OPCIONES WHERE OPCION_ID = TO_NUMBER(?,'99')";
			
			Object[] parametros = new Object[] {opcionId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmOpcionesDao|deleteReg|:"+ex);
		}
		
		return ok;
	}
	
	public AdmOpciones mapeaRegId(String opcionId){
		AdmOpciones objeto = new AdmOpciones();		
		try {
			Object[] parametros = new Object[] {opcionId};
				String comando = "SELECT OPCION_ID, NOMBRE FROM SALOMON.ADM_OPCIONES WHERE OPCION_ID = TO_NUMBER(?,'99')";				
				objeto = enocJdbc.queryForObject(comando, new AdmOpcionesMapper(),parametros);				
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmOpcionesDao|mapeaRegId|:"+ex);
		}
		
		return objeto;
	}
	
	public String maxReg() {
		String max = "1";
		try{
			String comando = "SELECT COALESCE((MAX(OPCION_ID)+1),1) AS MAX FROM SALOMON.ADM_OPCIONES";			
			max = enocJdbc.queryForObject(comando,String.class);
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmOpcionesDao|maxReg|:"+ex);
		}		
		return max;
	}

	public boolean existeReg(String opcionId ) {
		boolean 		ok 		= false;		
		try{
			String comando = "SELECT COUNT(*) FROM SALOMON.ADM_OPCIONES WHERE OPCION_ID = TO_NUMBER(?,'99')";			
			Object[] parametros = new Object[] {opcionId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmOpcionesDao|existeReg|:"+ex);
		}		
		return ok;
	}

	
	public List<AdmOpciones> getAll(String orden) {
		List<AdmOpciones> lista	= new ArrayList<AdmOpciones>();		
		try{
			String comando = "SELECT OPCION_ID, NOMBRE FROM SALOMON.ADM_OPCIONES "+ orden;			
			lista = enocJdbc.query(comando, new AdmOpcionesMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmOpcionesDao|getAll|:"+ex);
		}		
		return lista;
	}
	
}
