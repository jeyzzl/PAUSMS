package aca.acceso.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AccesoPlanDao {
	
	@Autowired	
	@Qualifier("jdbcEnoc")	
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(AccesoPlan objeto){
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.ACCESO_PLAN(CODIGO_PERSONAL, PLAN_ID, CARRERA_ID, FECHA, USUARIO) VALUES(?, ?, ?, SYSDATE, ?)";
			Object[] parametros = new Object[] {objeto.getCodigoPersonal(), objeto.getPlanId(), objeto.getCarreraId(), objeto.getUsuario()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.spring.AccesoPlanDao|insertReg|:"+ex);
		}		
		return ok;
	}	
	
	public boolean deleteReg(String codigoPersonal, String planId){
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.ACCESO_PLAN WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, planId};	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.spring.AccesoPlanDao|deleteReg|:"+ex);
		}
		
		return ok;
	}
	
	public int deletePorUsuario(String codigoPersonal){
		int borrados = 0;		
		try{
			String comando = "DELETE FROM ENOC.ACCESO_PLAN WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};	
			borrados = enocJdbc.update(comando,parametros);				
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.spring.AccesoPlanDao|deletePorUsuario|:"+ex);
		}		
		return borrados;
	}	
	
	public AccesoPlan mapeaRegId(String codigoPersonal, String planId){		
		AccesoPlan objeto = new AccesoPlan();
		try{
			String comando ="SELECT CODIGO_PERSONAL, PLAN_ID, CARRERA_ID, TO_CHAR(FECHA,'YYYY/MM/DD HH24:MI:SS') AS FECHA, USUARIO FROM ENOC.ACCESO_PLAN WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, planId};
			objeto = enocJdbc.queryForObject(comando, new AccesoPlanMapper(), parametros);			
		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.spring.AccesoPlanDao|mapeaRegId|:"+ex);
 		}
		return objeto;
	}
	
	public AccesoPlan mapeaRegId(String codigoPersonal){		
		AccesoPlan objeto = new AccesoPlan();
		try{
			String comando ="SELECT CODIGO_PERSONAL, PLAN_ID, CARRERA_ID, TO_CHAR(FECHA,'YYYY/MM/DD HH24:MI:SS') AS FECHA, USUARIO FROM ENOC.ACCESO_PLAN WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
			objeto = enocJdbc.queryForObject(comando, new AccesoPlanMapper(), parametros);			
		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.spring.AccesoPlanDao|mapeaRegId|:"+ex);
 		}
		return objeto;
	}
	
	public boolean existeReg(String codigoPersonal, String planId){	
		boolean ok = false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ACCESO_PLAN WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, planId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.spring.AccesoPlanDao|existeReg|:"+ex);
		}		
		return ok;
	}
	
	public List<String> lisPlanesPorUsuario(String codigoPersonal){
		List<String> lista = new ArrayList<String>();
		try{
				String comando = "SELECT UNIQUE(PLAN_ID) FROM ACCESO_PLAN WHERE CODIGO_PERSONAL =?";
				lista = enocJdbc.queryForList(comando, String.class, codigoPersonal);		
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.spring.AccesoDao|liPlanesPorUsuario|:"+ex);
		}		
		return lista;
	}
	
	public List<String> lisPorUsuario( String codigoPersonal, String orden ){
		List<String> lista = new ArrayList<String>();
		try{
			String comando = "SELECT PLAN_ID FROM ENOC.ACCESO_PLAN WHERE CODIGO_PERSONAL = ? "+orden;
			lista = enocJdbc.queryForList(comando,String.class, codigoPersonal);
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.spring.SaiiGrupoDao|lisPorUsuario|:"+ex);
		}
		return lista;
	}	
	
	public List<AccesoPlan> lisPorPlan( String planId, String orden ){
		List<AccesoPlan> lista = new ArrayList<AccesoPlan>();
		try{
			String comando = "SELECT CODIGO_PERSONAL, PLAN_ID, CARRERA_ID, TO_CHAR(FECHA,'YYYY/MM/DD HH24:MI:SS') AS FECHA, USUARIO FROM ENOC.ACCESO_PLAN WHERE PLAN_ID = ? "+orden;
			lista = enocJdbc.query(comando, new AccesoPlanMapper(), planId);
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.spring.AccesoPlanDao|lisPorOpcion|:"+ex);
		}
		return lista;
	}
	
	public List<String> lisAlumnosSinPortal( String planId ){
		List<String> lista = new ArrayList<String>();
		try{
			String comando = "SELECT DISTINCT(CODIGO_PERSONAL) FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL NOT IN (SELECT CODIGO_PERSONAL FROM ENOC.ACCESO_PLAN WHERE PLAN_ID = ?)";
			lista = enocJdbc.queryForList(comando, String.class, planId);
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.spring.AccesoPlanDao|lisAlumnosSinPortal|:"+ex);
		}
		return lista;
	}
	
	public HashMap<String, String> mapPlanesPorUsuario(String codigoPersonal){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT PLAN_ID AS LLAVE, PLAN_ID AS VALOR FROM ENOC.ACCESO_PLAN WHERE CODIGO_PERSONAL = ?";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), codigoPersonal);
			for (aca.Mapa map : lista){				
				mapa.put(map.getLlave(), map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.spring.AccesoPlanDao|mapOpcionesPorUsuario|:"+ex);
		}
		
		return mapa;
	}	
}
