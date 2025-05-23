// Clase Util para la tabla de Carga
package aca.carga.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CargaPermisoPlanDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( CargaPermisoPlan permiso ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.CARGA_PERMISO_PLAN(CARGA_ID, PLAN_ID, CARRERA_ID, RECUPERACION, USUARIO, FECHA ) VALUES( ?, ?, ?, ?, ?, SYSDATE )";			
			Object[] parametros = new Object[] {permiso.getCargaId(), permiso.getPlanId(), permiso.getCarreraId(), permiso.getRecuperacion(), permiso.getUsuario()};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaPermisoPlanDao|insertReg|:"+ex);			
		}		
		return ok;
	}
	
	public boolean deleteReg( String cargaId, String planId ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CARGA_PERMISO_PLAN WHERE CARGA_ID = ? AND PLAN_ID = ?";
			
			Object[] parametros = new Object[] {cargaId, planId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaPermisoPlanDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public CargaPermisoPlan mapeaRegId(  String cargaId, String planId ) {
		CargaPermisoPlan permiso = new CargaPermisoPlan();
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA_PERMISO_PLAN"
					+ " WHERE CARGA_ID = ?"
					+ " AND PLAN_ID = ?";
			
			Object[] parametros = new Object[] {cargaId, planId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				comando = "SELECT CARGA_ID, PLAN_ID, CARRERA_ID, RECUPERACION, USUARIO, FECHA"
					+ " FROM ENOC.CARGA_PERMISO_PLAN"
					+ " WHERE CARGA_ID = ?"
					+ " AND PLAN_ID = ?";
			
				permiso = enocJdbc.queryForObject(comando, new CargaPermisoPlanMapper(), parametros);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaPermisoPlanDao|mapeaRegId|:"+ex);
		}
		return permiso;
	}
	
	public boolean existeReg( String cargaId, String planId) {
		boolean 			ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA_PERMISO_PLAN"
					+ " WHERE CARGA_ID = ?"
					+ " AND PLAN_ID = ?";
			
			Object[] parametros = new Object[] {cargaId, planId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaPermisoPlanDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public boolean esCarreraRecuperacion( String cargaId, String planId, String carreraId) {
		boolean 			ok 		= false;
		
		try{
			String comando = "SELECT RECUPERACION FROM ENOC.CARGA_PERMISO_PLAN "+ 
				"WHERE CARGA_ID = ? "+
				"AND PLAN_ID = ?" +
				"AND CARRERA_ID = ?";
			
			Object[] parametros = new Object[] {cargaId, planId, carreraId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaPermisoPlanDao|esCarreraRecuperacion|:"+ex);
		}		
		return ok;
	}
	
	public boolean carreraPermitida( String cargaId, String planId, String carreraId) {
		boolean 			ok 		= false;
		
		try{
			String comando = "SELECT CARRERA_ID FROM ENOC.CARGA_PERMISO_PLAN "+ 
				"WHERE CARGA_ID = ? "+
				"AND PLAN_ID = ? "+
				"AND CARRERA_ID = ?";
			
			Object[] parametros = new Object[] {cargaId, planId, carreraId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaPermisoPlanDao|carreraPermitida|:"+ex);
		}
		return ok;
	}
	
	public boolean planPermitido( String cargaId, String planId) {
		boolean 			ok 		= false;		
		try{
			String comando = "SELECT COUNT(PLAN_ID) FROM ENOC.CARGA_PERMISO_PLAN WHERE CARGA_ID = ? AND PLAN_ID = ?";
			Object[] parametros = new Object[] {cargaId, planId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaPermisoPlanDao|planPermitido|:"+ex);
		}
		return ok;
	}
	
	public List<CargaPermisoPlan> getListAll( String orden ) {
		List<CargaPermisoPlan> lista		= new ArrayList<CargaPermisoPlan>();
		String comando		= "";
		
		try{
			comando = "SELECT CARGA_ID, PLAN_ID, CARRERA_ID, RECUPERACION, USUARIO, FECHA FROM ENOC.CARGA_PERMISO_PLAN "+ orden; 
			
			lista = enocJdbc.query(comando, new CargaPermisoPlanMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaPermisoPlanDao|getListAll|:"+ex);
		}
		return lista;
	}
	
	public List<CargaPermisoPlan> lisPorCarga( String cargaId, String orden ) {
		List<CargaPermisoPlan> lista		= new ArrayList<CargaPermisoPlan>();	
		try{
			String comando = "SELECT CARGA_ID, PLAN_ID, CARRERA_ID, RECUPERACION, USUARIO, FECHA FROM ENOC.CARGA_PERMISO_PLAN WHERE CARGA_ID = ? "+ orden;
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new CargaPermisoPlanMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error- aca.carga.spring.CargaPermisoPlanDao|lisPorCarga|:"+ex);
		}
		
		return lista;
	}
	
	public List<CargaPermisoPlan> getListaSinPermiso( String cargaId, String orden ) {
		List<CargaPermisoPlan> lista		= new ArrayList<CargaPermisoPlan>();	
		try{
			String comando = "SELECT '"+cargaId+"' AS carga_Id, CARRERA_ID, 'N' AS RECUPERACION, '0' AS USUARIO"
				+ " FROM ENOC.CAT_CARRERA " 
				+ " WHERE CARRERA_ID NOT IN "
				+ "	(SELECT CARRERA_ID FROM ENOC.CARGA_PERMISO_PLAN WHERE carga_Id = ?) "+orden;			
			lista = enocJdbc.query(comando, new CargaPermisoPlanMapper(), cargaId);			
		}catch(Exception ex){
			System.out.println("Error- aca.carga.spring.CargaPermisoPlanDao|getListaSinPermiso|:"+ex);
		}
		return lista;
	}
	
	public HashMap<String, String> mapaPermisos(String cargaId){
		HashMap<String, String> mapa 	= new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();		
		try{			
			String comando = "SELECT PLAN_ID AS LLAVE, USUARIO AS VALOR FROM ENOC.CARGA_PERMISO_PLAN WHERE CARGA_ID = ?";
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa map :lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaPermisoPlanDao|mapaPermisos|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaPlanesPorCarrera(String cargaId){
		HashMap<String, String> mapa 	= new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();		
		try{			
			String comando = "SELECT ENOC.FACULTAD(CARRERA_ID) AS LLAVE, COUNT(PLAN_ID) AS VALOR "
					+ " FROM ENOC.CARGA_PERMISO_PLAN"
					+ " WHERE CARGA_ID = ?"
					+ " GROUP BY FACULTAD(CARRERA_ID)";
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa map :lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaPermisoPlanDao|mapaPlanesPorCarrera|:"+ex);
		}
		
		return mapa;
	}
	
}