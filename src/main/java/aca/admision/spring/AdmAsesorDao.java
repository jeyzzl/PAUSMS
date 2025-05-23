package aca.admision.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AdmAsesorDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(AdmAsesor admAsesor){
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO SALOMON.ADM_ASESOR"+ 
				" (ASESOR_ID, CORREO, CHAT, TELEFONO, ESTADO)" +
				" VALUES( ?, ?, ?, ?,?)";			
			Object[] parametros = new Object[] {
				admAsesor.getAsesorId(), admAsesor.getCorreo(), admAsesor.getChat(), admAsesor.getTelefono(), admAsesor.getEstado()
 		 	};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmAsesorDao|insertReg|:"+ex);
		}
		return ok;
	}
	
	public boolean updateReg(AdmAsesor admAsesor) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE SALOMON.ADM_ASESOR " + 
					"SET CORREO = ?, CHAT =? ,TELEFONO = ?, ESTADO = ? " +				
					"WHERE ASESOR_ID = ?";
			
			Object[] parametros = new Object[] {
				admAsesor.getCorreo(), admAsesor.getChat(), admAsesor.getTelefono(), admAsesor.getEstado(), admAsesor.getAsesorId()
 		 	};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}

		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmAsesorDao|updateReg|:"+ex);
		}

		return ok;
	}

	public boolean deleteReg(String asesorId){
		boolean ok = false;		
		try{
			String comando = "DELETE FROM SALOMON.ADM_ASESOR WHERE ASESOR_ID = ?";			
			Object[] parametros = new Object[] {asesorId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmAsesorDao|deleteReg|:"+ex);
		}
		return ok;
	}
	
	
	public AdmAsesor mapeaRegId( String asesorId ){
		AdmAsesor admAsesor = new AdmAsesor();
		try{
			String comando = "SELECT COUNT(*) FROM SALOMON.ADM_ASESOR WHERE ASESOR_ID = ?";
			Object[] parametros = new Object[] { asesorId };				
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				comando = "SELECT ASESOR_ID, CORREO, CHAT, TELEFONO, ESTADO FROM SALOMON.ADM_ASESOR WHERE ASESOR_ID = ?";
				admAsesor = enocJdbc.queryForObject(comando, new AdmAsesorMapper(),parametros);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmAsesorDao|mapeaRegId|:"+ex);
		}	
		return admAsesor;
	}
	
	public boolean existeReg(String asesorId ){
		boolean 		ok 		= false;		
		try{
			String comando = "SELECT COUNT(*) FROM SALOMON.ADM_ASESOR  WHERE ASESOR_ID = ?";
			Object[] parametros = new Object[] { asesorId };
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmAsesorDao|existeReg|:"+ex);
		}
		
		return ok;
	}	
	
	public List<AdmAsesor> lisAsesores( ){		
		List<AdmAsesor> lista	= new ArrayList<AdmAsesor>();		
		try{
			String comando = "SELECT ASESOR_ID, CORREO, CHAT, TELEFONO, ESTADO FROM SALOMON.ADM_ASESOR";
			lista = enocJdbc.query(comando,new AdmAsesorMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmSolicitudDao|lisAsesores|:"+ex);
		}
		
		return lista;
	}
	
	public List<AdmAsesor> lisAsesores(String estados ){		
		List<AdmAsesor> lista	= new ArrayList<AdmAsesor>();		
		try{
			String comando = "SELECT ASESOR_ID, CORREO, CHAT, TELEFONO, ESTADO FROM SALOMON.ADM_ASESOR WHERE ESTADO IN ("+estados+")";
			lista = enocJdbc.query(comando,new AdmAsesorMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmSolicitudDao|lisAsesores|:"+ex);
		}
		
		return lista;
	}
	
	public List<String> lisAsesorId(String estados){
		List<String> lista = new ArrayList<String>();
		try {
			String comando = "SELECT ASESOR_ID FROM SALOMON.ADM_ASESOR WHERE ESTADO = ?";
			lista = enocJdbc.queryForList(comando, String.class, estados);
		}catch(Exception ex) {
			System.out.println("Error - aca.admision.spring.AdmSolicitudDao|lisAsesores|:"+ex);
		}
		return lista;
	}
}