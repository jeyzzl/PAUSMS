package aca.exa.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ExaCorreoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(ExaCorreo exaCorreo ){
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO "
					+ " ENOC.EXA_CORREO(CORREO_ID, MATRICULA, CORREO, FECHAACTUALIZACION,"
					+ " ELIMINADO, IDCORREO) VALUES( TO_NUMBER(?,'99999999'), ?, ?, TO_DATE(?,'DD/MM/YYYY HH24:MI:SS'), "
					+ " TO_NUMBER(?,'9'), ? )";
			
			Object[] parametros = new Object[]{
				exaCorreo.getCorreoId(),exaCorreo.getMatricula(),exaCorreo.getCorreo(),exaCorreo.getFechaAct(),exaCorreo.getEliminado(),exaCorreo.getIdCorreo()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaCorreoDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean eliminar(String correoId ){
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.EXA_CORREO"+ 
				" SET ELIMINADO = '1' " +
				" WHERE CORREO_ID = TO_NUMBER(?,'99999999')";
			if (enocJdbc.update(comando,correoId)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaCorreoDao|eliminar|:"+ex);		
		}
		
		return ok;
	}	
	
	public ExaCorreo mapeaRegIdCorreo( String correoId){
		ExaCorreo exaCorreo = new ExaCorreo();
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.EXA_CORREO WHERE CORREO_ID = TO_NUMBER(?,'99999999') "; 
			if (enocJdbc.queryForObject(comando,Integer.class, correoId) >= 1){
				comando = "SELECT CORREO_ID, MATRICULA, CORREO, FECHAACTUALIZACION, ELIMINADO, IDCORREO "+
					"FROM ENOC.EXA_CORREO WHERE CORREO_ID = TO_NUMBER(?,'99999999')";
				exaCorreo = enocJdbc.queryForObject(comando, new ExaCorreoMapper(), correoId);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaCorreoDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		return exaCorreo;
	}
	
	public ExaCorreo mapeaRegId( String matricula){
		ExaCorreo exaCorreo = new ExaCorreo();
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.EXA_CORREO WHERE MATRICULA = ?"; 
			if (enocJdbc.queryForObject(comando,Integer.class, matricula) >= 1){
				comando = "SELECT CORREO_ID, MATRICULA, CORREO, FECHAACTUALIZACION, ELIMINADO, IDCORREO "+
					"FROM ENOC.EXA_CORREO WHERE MATRICULA = ?"; 
				exaCorreo = enocJdbc.queryForObject(comando, new ExaCorreoMapper(), matricula);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaCorreoDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		return exaCorreo;
	}
	
	public boolean existeReg(String correoId){
		boolean ok = false;
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.EXA_CORREO WHERE CORREO_ID = TO_NUMBER(?,'99999999') "; 
			if (enocJdbc.queryForObject(comando,Integer.class, correoId) >= 1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaCorreoDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean existeAlumno(String matricula){
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.EXA_CORREO WHERE MATRICULA = ? AND ELIMINADO!=1 "; 
			if (enocJdbc.queryForObject(comando,Integer.class, matricula) >= 1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaCorreoDao|existeAlumno|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoReg(String matricula){
		int maximo 			= 1;		
		try{
			String comando = "SELECT COALESCE(MAX(CORREO_ID),0) AS MAXIMO FROM ENOC.EXA_CORREO WHERE ELIMINADO !=1 AND MATRICULA = ?"; 
			maximo = enocJdbc.queryForObject(comando,Integer.class, matricula);		
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaCorreoDao|maximoReg|:"+ex);
		}
		
		return String.valueOf(maximo);
	}
	
	public String maximoReg(){
		int maximo 			= 1;
		
		try{
			String comando = "SELECT MAX(CORREO_ID)+1 AS MAXIMO FROM ENOC.EXA_CORREO";
			maximo = enocJdbc.queryForObject(comando,Integer.class);	
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaCorreoDao|maximoReg|:"+ex);
		}
		
		return String.valueOf(maximo);
	}
	
	public String getCantidadGenerico(String tabla){
		String res 				= "0";
		
		try{
			String comando = "SELECT COUNT(DISTINCT(MATRICULA)) AS CANTIDAD FROM "+tabla+" WHERE ELIMINADO != 1 "; 
			res = enocJdbc.queryForObject(comando,String.class);		
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaCorreoDao|getCantidadGenerico|:"+ex);
		}
		
		return res;
	}
	
	public List<ExaCorreo> getCorreo(String matricula, String orden){
		List<ExaCorreo> lista		= new ArrayList<ExaCorreo>();		
		try{
			String comando = "SELECT CORREO_ID, MATRICULA, CORREO," +
					" FECHAACTUALIZACION, ELIMINADO, IDCORREO FROM ENOC.EXA_DIRECCION" +
					" WHERE MATRICULA = ? AND ELIMINADO != 1 "+orden;			
			lista = enocJdbc.query(comando, new ExaCorreoMapper(), matricula);			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaCorreoDao|getCorreo|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String,ExaCorreo> getMapCorreo(String orden ){
		HashMap<String,ExaCorreo> mapCorreo = new HashMap<String,ExaCorreo>();
		List<ExaCorreo> lista		= new ArrayList<ExaCorreo>();
		try{
			String comando = "SELECT CORREO_ID, MATRICULA, CORREO,"
					+ " FECHAACTUALIZACION, ELIMINADO, IDCORREO FROM ENOC.EXA_CORREO WHERE ELIMINADO != 1 "+ orden;
			
			lista = enocJdbc.query(comando, new ExaCorreoMapper());
			
			for(ExaCorreo objeto : lista) {
				mapCorreo.put(objeto.getMatricula(), objeto);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.exa.spring.ExaCorreoDao|getMapCorreo|:"+ex);
		}
		
		return mapCorreo;
	}
}
