package aca.opcion.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class MaestroOpcionDao {
	
	@Autowired	
	@Qualifier("jdbcEnoc")	
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(MaestroOpcion maestro){
		boolean ok = false;
		try{
			String comando = "INSERT INTO ENOC.MAESTRO_OPCION(CODIGO_PERSONAL, VISTA_EVALUAR) VALUES(?, ?)";
			Object[] parametros = new Object[] { maestro.getCodigoPersonal(), maestro.getVistaEvaluar() };
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.MaestroOpcion|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg(MaestroOpcion maestro) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.MAESTRO_OPCION SET VISTA_EVALUAR = ? WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] { maestro.getVistaEvaluar(), maestro.getCodigoPersonal() };
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.MaestroOpcion|updateReg|:"+ex);		 
		}
		
		return ok;
	}	
	
	public boolean deleteReg(String codigoPersonal) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.MAESTRO_OPCION WHERE CODIGO_PERSONAL = ?";		
			Object[] parametros = new Object[] { codigoPersonal };
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.MaestroOpcion|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public MaestroOpcion mapeaRegId(String codigoPersonal){
		MaestroOpcion opcion = new MaestroOpcion();
		try{ 
			String comando = "SELECT COUNT(CODIGO_PERSONAL) FROM ENOC.MAESTRO_OPCION WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] { codigoPersonal };
			if (enocJdbc.queryForObject(comando, Integer.class, parametros)>=1){				
				comando = "SELECT CODIGO_PERSONAL, VISTA_EVALUAR FROM ENOC.MAESTRO_OPCION WHERE CODIGO_PERSONAL = ?";							
				opcion = enocJdbc.queryForObject(comando, new MaestroOpcionMapper(), parametros);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.MaestroOpcion|mapeaRegId|:"+ex);
		}
		
		return opcion;
	}
	
	public boolean existeReg(String codigoPersonal){
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.MAESTRO_OPCION WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] { codigoPersonal };
			if (enocJdbc.queryForObject(comando, Integer.class, parametros)>=1){											
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.opcion.spring.MaestroOpcion|existeReg|:"+ex);
		}
		
		return ok;
	}

}
