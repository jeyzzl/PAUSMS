package aca.acceso.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AccesoOpcionUnavDao {
/*	
	@Autowired	
	@Qualifier("jdbcUnav")	
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(AccesoOpcion objeto){
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.ACCESO_OPCION(CODIGO_PERSONAL, OPCION_ID, USUARIO) VALUES(?, ?, ?)";
			Object[] parametros = new Object[] {objeto.getCodigoPersonal(), objeto.getOpcionId(), objeto.getUsuario()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.spring.AccesoOpcionDao|insertReg|:"+ex);
		}
		
		return ok;
	}	
	
	public boolean deleteReg(String codigoPersonal, String opcionId){
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.ACCESO_OPCION WHERE CODIGO_PERSONAL = ? AND OPCION_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, opcionId};	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.spring.AccesoOpcionDao|deleteReg|:"+ex);
		}
		
		return ok;
	}
	
	public int deletePorUsuario(String codigoPersonal){
		int borrados = 0;		
		try{
			String comando = "DELETE FROM ENOC.ACCESO_OPCION WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};	
			borrados = enocJdbc.update(comando,parametros);				
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.spring.AccesoOpcionDao|deletePorUsuario|:"+ex);
		}		
		return borrados;
	}
	
	public int deletePorOpcion(String opcionId){
		int borrados = 0;		
		try{
			String comando = "DELETE FROM ENOC.ACCESO_OPCION WHERE OPCION_ID = ?";
			Object[] parametros = new Object[] {opcionId};
			borrados = enocJdbc.update(comando,parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.spring.AccesoOpcionDao|deletePorOpcion|:"+ex);
		}		
		return borrados;
	}
	
	public boolean deleteTodo(){
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.ACCESO_OPCION";				
			if (enocJdbc.update(comando)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.spring.AccesoOpcionDao|deleteTodo|:"+ex);
		}
		
		return ok;
	}
	
	public AccesoOpcion mapeaRegId(String codigoPersonal, String opcionId){		
		AccesoOpcion objeto = new AccesoOpcion();
		try{
			String comando ="SELECT CODIGO_PERSONAL, OPCION_ID, TO_CHAR(FECHA,'YYYY/MM/DD HH24:MI:SS') AS FECHA, USUARIO FROM ENOC.ACCESO_OPCION WHERE CODIGO_PERSONAL = ? AND OPCION_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, opcionId};
			objeto = enocJdbc.queryForObject(comando, new AccesoOpcionMapper(), parametros);			
		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.spring.AccesoOpcionDao|mapeaRegId|:"+ex);
 		}
		return objeto;
	}
	
	public boolean existeReg(String codigoPersonal, String opcionId){	
		boolean ok = false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ACCESO_OPCION WHERE CODIGO_PERSONAL = ? AND OPCION_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, opcionId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.spring.AccesoOpcionDao|existeReg|:"+ex);
		}		
		return ok;
	}
	
	public int alumnosSinPortal(String opcionId){	
		int total = 0;		
		try{
			String comando = "SELECT COUNT(DISTINCT(CODIGO_PERSONAL)) FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL NOT IN (SELECT CODIGO_PERSONAL FROM ENOC.ACCESO_OPCION WHERE OPCION_ID = ?)";
			Object[] parametros = new Object[] {opcionId};
			total 	= enocJdbc.queryForObject(comando, Integer.class, parametros);						
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.spring.AccesoOpcionDao|alumnosSinPortal|:"+ex);
		}		
		return total;
	}
	
	public int maestrosSinPortal(String opcionId){	
		int total = 0;		
		try{
			String comando = "SELECT COUNT(DISTINCT(CODIGO_PERSONAL)) FROM ENOC.CARGA_GRUPO WHERE CODIGO_PERSONAL NOT IN (SELECT CODIGO_PERSONAL FROM ENOC.ACCESO_OPCION WHERE OPCION_ID = ?)";
			Object[] parametros = new Object[] {opcionId};
			total 	= enocJdbc.queryForObject(comando, Integer.class, parametros);						
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.spring.AccesoOpcionDao|maestrosSinPortal|:"+ex);
		}		
		return total;
	}
	
	public int mentorSinPortal(String opcionId){	
		int total = 0;		
		try{
			String comando = "SELECT COUNT(DISTINCT(MENTOR_ID)) FROM ENOC.MENT_ALUMNO WHERE MENTOR_ID NOT IN (SELECT CODIGO_PERSONAL FROM ENOC.ACCESO_OPCION WHERE OPCION_ID = ?)";
			Object[] parametros = new Object[] {opcionId};
			total 	= enocJdbc.queryForObject(comando, Integer.class, parametros);						
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.spring.AccesoOpcionDao|mentorSinPortal|:"+ex);
		}		
		return total;
	}
	
	public int maestrosSinPortafolio(String opcionId){	
		int total = 0;		
		try{
			String comando = "SELECT COUNT(CODIGO_PERSONAL) FROM MAESTROS WHERE CODIGO_PERSONAL NOT IN (SELECT CODIGO_PERSONAL FROM ENOC.ACCESO_OPCION WHERE OPCION_ID = ?)";
			Object[] parametros = new Object[] {opcionId};
			total 	= enocJdbc.queryForObject(comando, Integer.class, parametros);						
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.spring.AccesoOpcionDao|maestrosSinPortafolio|:"+ex);
		}		
		return total;
	}
	
	public boolean existeOpcion(String opcionId){	
		boolean ok = false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ACCESO_OPCION WHERE OPCION_ID = ?";
			Object[] parametros = new Object[] {opcionId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.spring.AccesoOpcionDao|existeOpcion|:"+ex);
		}		
		return ok;
	}
	
	public List<AccesoOpcion> lisPorUsuario( String codigoPersonal, String orden ){
		List<AccesoOpcion> lista = new ArrayList<AccesoOpcion>();
		try{
			String comando = "SELECT CODIGO_PERSONAL, OPCION_ID, TO_CHAR(FECHA,'YYYY/MM/DD HH24:MI:SS') AS FECHA, USUARIO FROM ENOC.ACCESO_OPCION WHERE CODIGO_PERSONAL = ? "+orden;
			lista = enocJdbc.query(comando, new AccesoOpcionMapper(), codigoPersonal);
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.spring.SaiiGrupoDao|lisPorUsuario|:"+ex);
		}
		return lista;
	}	
	
	public List<AccesoOpcion> lisPorOpcion( String opcionId, String orden ){
		List<AccesoOpcion> lista = new ArrayList<AccesoOpcion>();
		try{
			String comando = "SELECT CODIGO_PERSONAL, OPCION_ID, TO_CHAR(FECHA,'YYYY/MM/DD HH24:MI:SS') AS FECHA, USUARIO FROM ENOC.ACCESO_OPCION WHERE OPCION_ID = ? "+orden;
			lista = enocJdbc.query(comando, new AccesoOpcionMapper(), opcionId);
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.spring.AccesoOpcionDao|lisPorOpcion|:"+ex);
		}
		return lista;
	}
	
	public List<String> lisAlumnosSinPortal( String opcionId ){
		List<String> lista = new ArrayList<String>();
		try{
			String comando = "SELECT DISTINCT(CODIGO_PERSONAL) FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL NOT IN (SELECT CODIGO_PERSONAL FROM ENOC.ACCESO_OPCION WHERE OPCION_ID = ?)";
			lista = enocJdbc.queryForList(comando, String.class, opcionId);
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.spring.AccesoOpcionDao|lisAlumnosSinPortal|:"+ex);
		}
		return lista;
	}
	
	public List<String> lisMaestrosSinPortal( String opcionId ){
		List<String> lista = new ArrayList<String>();
		try{
			String comando = "SELECT DISTINCT(CODIGO_PERSONAL) FROM ENOC.CARGA_GRUPO WHERE CODIGO_PERSONAL NOT IN (SELECT CODIGO_PERSONAL FROM ENOC.ACCESO_OPCION WHERE OPCION_ID = ?)";
			lista = enocJdbc.queryForList(comando, String.class, opcionId);
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.spring.AccesoOpcionDao|lisMaestrosSinPortal|:"+ex);
		}
		return lista;
	}
	
	public List<String> lisMentoresSinPortal( String opcionId ){
		List<String> lista = new ArrayList<String>();
		try{
			String comando = "SELECT DISTINCT(MENTOR_ID) FROM ENOC.MENT_ALUMNO WHERE MENTOR_ID NOT IN (SELECT CODIGO_PERSONAL FROM ENOC.ACCESO_OPCION WHERE OPCION_ID = ?)";
			lista = enocJdbc.queryForList(comando, String.class, opcionId);
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.spring.AccesoOpcionDao|lisMentoresSinPortal|:"+ex);
		}
		return lista;
	}
	
	public List<String> lisMaestrosSinPortafolio( String opcionId ){
		List<String> lista = new ArrayList<String>();
		try{
			String comando = "SELECT CODIGO_PERSONAL FROM MAESTROS WHERE CODIGO_PERSONAL NOT IN (SELECT CODIGO_PERSONAL FROM ENOC.ACCESO_OPCION WHERE OPCION_ID = ?)";
			lista = enocJdbc.queryForList(comando, String.class, opcionId);
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.spring.AccesoOpcionDao|lisMaestrosSinPortafolio|:"+ex);
		}
		return lista;
	}
	
	public HashMap<String, String> mapOpcionesPorUsuario(String usuario){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT OPCION_ID AS LLAVE, OPCION_ID AS VALOR FROM ENOC.ACCESO_OPCION WHERE CODIGO_PERSONAL = ?";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), usuario);
			for (aca.Mapa map : lista){				
				mapa.put(map.getLlave(), map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.spring.AccesoOpcionDao|mapOpcionesPorUsuario|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapUsuariosPorOpcion(){
		List<aca.Mapa> lista 		 = new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa = new HashMap<String,String>();		
		try {
			String comando = "SELECT OPCION_ID AS LLAVE, COUNT(CODIGO_PERSONAL) AS VALOR FROM ENOC.ACCESO_OPCION GROUP BY OPCION_ID"; 
			lista = enocJdbc.query(comando, new aca.MapaMapper());			
			for(aca.Mapa opcion : lista) {				
				mapa.put(opcion.getLlave(), opcion.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.spring.AccesoOpcionDao|mapNumUsuarios|:"+ex);
		}		
		return mapa;
	}
	
	public List<String> lisEditores(){
		List<String> lista = new ArrayList<String>();
		List<String> lisEditores = new ArrayList<String>();
		try{
			String comando = "SELECT CODIGO_MAESTRO FROM ENOC.MAPA_NUEVO_CURSO WHERE PLAN_ID IN (SELECT PLAN_ID FROM ENOC.MAPA_NUEVO_PLAN WHERE YEAR = '2017')";
			lista = enocJdbc.queryForList(comando, String.class);			
			for(String dato : lista) {
				
				String[] arreglo = dato.split("-");
				for (String codigo : arreglo){
					if ( codigo.length()==7) {
						if (!lisEditores.contains(codigo)) {
							lisEditores.add(codigo);
						}
					}
				}
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.spring.AccesoOpcionDao|lisEditores|:"+ex);
		}
		return lisEditores;
	}
*/	
}
