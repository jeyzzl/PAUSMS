package aca.acceso.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AccesoPrivacidadDao {
	
	@Autowired	
	@Qualifier("jdbcEnoc")	
	private JdbcTemplate enocJdbc;
	

	public boolean insertReg( AccesoPrivacidad privacidad){
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.ACCESO_PRIVACIDAD(CODIGO_PERSONAL, FECHA)"
					+ " VALUES(?,now())";
			Object[] parametros = new Object[] {privacidad.getCodigoPersonal()};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.spring.AccesoPrivacidadDao|insertReg|:"+ex);
		}
		
		return ok;
	}
	
	/* Verifica si existe */
	public boolean existeReg(String codigoPersonal){
		boolean ok = false;
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ACCESO_PRIVACIDAD WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[]{codigoPersonal};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1){
				ok = true;
			}
		}catch( Exception ex){
			System.out.println("Error: aca.acceso.spring.AccesoPrivacidadDao||existeReg :"+ex);
		}
		
		return ok;		
	}
	
	public AccesoPrivacidad mapeaRegId(String codigoPersonal){
		AccesoPrivacidad privacidad = new AccesoPrivacidad();
		
		try{			
			String query = "SELECT CODIGO_PERSONAL, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA FROM ENOC.ACCESO_PRIVACIDAD WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[]{codigoPersonal};
			privacidad = enocJdbc.queryForObject(query, new AccesoPrivacidadMapper(), parametros);
		}catch( Exception ex){			
			System.out.println("Error: aca.acceso.spring.AccesoPrivacidadDao||mapeaRegId :"+ex);
		}
		
		return privacidad;
	}

}
