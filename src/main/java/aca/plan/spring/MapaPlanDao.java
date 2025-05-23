// Clase Util para la tabla de Mapa_Plan
package aca.plan.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import aca.cert.spring.CertPlan;

@Component
public class MapaPlanDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( MapaPlan plan ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.MAPA_PLAN" 
				+ " (PLAN_ID, CARRERA_ID, NOMBRE_PLAN, F_INICIO, F_FINAL, F_ACTUALIZA, NUM_CURSOS, MINIMO, MAXIMO, CARRERA_SE, RVOE, OFICIAL,"
				+ " ESTADO, ENLINEA, CICLOS, NOTA_EXTRA, GENERAL, PLAN_SE, NOMBRE_PLAN_MUJER, CLAVE_PROFESIONES, PRECIO, RVOE_INICIAL, VERSION_ID, CLAVE_SEMSYS, EXPEDIENTE, CREDITOS, PLAN_ORIGEN, DESCUENTO)"
				+ " VALUES( ?, ?, ?,"
				+ " TO_DATE(?,'DD/MM/YYYY'),"
				+ " TO_DATE(?,'DD/MM/YYYY'),"
				+ " TO_DATE(?,'DD/MM/YYYY'),"
				+ " TO_NUMBER(?,'999'),"
				+ " TO_NUMBER(?,'99'),"
				+ " TO_NUMBER(?,'99'),"
				+ " ?, ?, ?, ?, ?," 
				+ " TO_NUMBER(?,'99'),"				
				+ " TO_NUMBER(?,'99'),"
				+ " ?,?,?,?,?,?,"
				+ " TO_NUMBER(?,'99'), ?, ?, TO_NUMBER(?,'999'), ?, TO_NUMBER(?))";
			
			Object[] parametros = new Object[] {
					plan.getPlanId(),plan.getCarreraId(),plan.getNombrePlan(),plan.getFInicio(),plan.getFFinal(),plan.getFActualiza(),plan.getNumCursos(),
					plan.getMinimo(),plan.getMaximo(),plan.getCarreraSe(),plan.getRvoe(),plan.getOficial(),plan.getEstado(),plan.getEnLinea(),
					plan.getCiclos(),plan.getNotaExtra(),plan.getGeneral(),plan.getPlanSE(),plan.getNombrePlanMujer(),plan.getClaveProfesiones(), 
					plan.getPrecio(), plan.getRvoeInicial(), plan.getVersionId(), plan.getSemsys(), plan.getExpediente(), plan.getCreditos(), plan.getPlanOrigen(), plan.getDescuento()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaPlanDao|insertReg|:"+ex);			
		}		
		return ok;
	}	
	
	public boolean updateReg( MapaPlan plan ) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.MAPA_PLAN"
					+ " SET CARRERA_ID = ?, NOMBRE_PLAN = ?,"
					+ " F_INICIO = TO_DATE(?,'DD/MM/YYYY'),"
					+ " F_FINAL = TO_DATE(?,'DD/MM/YYYY'),"
					+ " F_ACTUALIZA = TO_DATE(?,'DD/MM/YYYY'),"
					+ " NUM_CURSOS = TO_NUMBER(?,'999'),"
					+ " MINIMO = TO_NUMBER(?,'99'),"
					+ " MAXIMO = TO_NUMBER(?,'99'),"
					+ " CARRERA_SE = ?,"
					+ " RVOE = ?,"
					+ " OFICIAL = ?,"
					+ " ESTADO = ?,"
					+ " ENLINEA = ?,"
					+ " CICLOS = TO_NUMBER(?,'99'),"
					+ " NOTA_EXTRA = TO_NUMBER(?,'99'),"
					+ " GENERAL = ?,"
					+ " PLAN_SE = ?,"
					+ " NOMBRE_PLAN_MUJER = ?,"
					+ " CLAVE_PROFESIONES = ?,"
					+ " PRECIO = ?,"
					+ " RVOE_INICIAL = ?,"
					+ " VERSION_ID = TO_NUMBER(?,'99'),"
					+ " CLAVE_SEMSYS = ?,"
					+ " EXPEDIENTE = ?,"
					+ " CREDITOS = TO_NUMBER(?,'999'),"
					+ " PLAN_ORIGEN = ?,"
					+ " DESCUENTO = TO_NUMBER(?)"
					+ " WHERE PLAN_ID = ?";
			
			//System.out.println("entre..");
			Object[] parametros = new Object[] {
					plan.getCarreraId(),plan.getNombrePlan(),plan.getFInicio(),plan.getFFinal(),plan.getFActualiza(),plan.getNumCursos(),plan.getMinimo(),
					plan.getMaximo(),plan.getCarreraSe(),plan.getRvoe(),plan.getOficial(),plan.getEstado(),plan.getEnLinea(),plan.getCiclos(),plan.getNotaExtra(),
					plan.getGeneral(),plan.getPlanSE(),plan.getNombrePlanMujer(),plan.getClaveProfesiones(),plan.getPrecio(),plan.getRvoeInicial(), plan.getVersionId(), 
					plan.getSemsys(), plan.getExpediente(), plan.getCreditos(), plan.getPlanOrigen(), plan.getDescuento(), plan.getPlanId()}; 				
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaPlanDao|updateReg|:"+ex);		
		}
		return ok;
	}
	
	public boolean deleteReg( String planId) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.MAPA_PLAN WHERE PLAN_ID = ? ";			
			Object[] parametros = new Object[] {planId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaPlanDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public MapaPlan mapeaRegId(  String planId ) {
		MapaPlan plan 			= new MapaPlan();
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.MAPA_PLAN WHERE PLAN_ID = ?";
			Object[] parametros = new Object[] {planId};
			if(enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				comando = "SELECT PLAN_ID, CARRERA_ID, NOMBRE_PLAN,"
						+ " TO_CHAR(F_INICIO,'dd/mm/yyyy') F_INICIO, TO_CHAR(F_FINAL,'dd/mm/yyyy') F_FINAL,"
						+ " TO_CHAR(F_ACTUALIZA,'dd/mm/yyyy') F_ACTUALIZA,"
						+ " NUM_CURSOS, MINIMO, MAXIMO, CARRERA_SE, RVOE, OFICIAL, ESTADO, ENLINEA, CICLOS,"
						+ " NOTA_EXTRA, GENERAL, PLAN_SE, NOMBRE_PLAN_MUJER, CLAVE_PROFESIONES, PRECIO, RVOE_INICIAL, VERSION_ID, CLAVE_SEMSYS, EXPEDIENTE, CREDITOS, PLAN_ORIGEN, DESCUENTO"
						+ " FROM ENOC.MAPA_PLAN"
						+ " WHERE PLAN_ID = ? ";					
				plan = enocJdbc.queryForObject(comando, new MapaPlanMapper(), parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaPlanDao|mapeaRegId|:"+ex);
		}
		return plan;
	}
	
	public boolean existeReg( String planId) {
		boolean 		ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.MAPA_PLAN WHERE PLAN_ID = ?";			
			Object[] parametros = new Object[] {planId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaPlanDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	
	public String getNombrePlanUtil( String planId) {
		String nombrePlan 		= "x";		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.MAPA_PLAN WHERE PLAN_ID = ?";						
			Object[] parametros = new Object[] {planId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				comando = "SELECT NOMBRE_PLAN FROM ENOC.MAPA_PLAN WHERE PLAN_ID = ?";
				nombrePlan = enocJdbc.queryForObject(comando, String.class, parametros);
			}						
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaPlanDao|getNombrePlanUtil|:"+ex);
		}
		return nombrePlan;
	}
	
	public int getNumCursos( String planId) {
		int nCursos=0;	
		
		try{
			String comando = "SELECT COUNT(CURSO_ID) AS CURSOS FROM ENOC.MAPA_CURSO WHERE PLAN_ID = ?";
			Object[] parametros = new Object[] {planId};			
			nCursos = enocJdbc.queryForObject(comando, Integer.class, parametros);						
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaPlanDao|getNumCursos|:"+ex);
		}		
		return nCursos;
	}
	
	public String getCarreraId( String planId) {
		String carrera			= "0";		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.MAPA_PLAN WHERE TRIM(PLAN_ID) = ?";
			Object[] parametros = new Object[] {planId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				comando = "SELECT CARRERA_ID FROM ENOC.MAPA_PLAN WHERE TRIM(PLAN_ID) = ?";		
				carrera = enocJdbc.queryForObject(comando, String.class, parametros);
			}							
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaPlanDao|getCarreraId|:"+ex);
		}
		return carrera;
	}	

	public String getDescuento( String planId) {
		String descuento			= "0";		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.MAPA_PLAN WHERE TRIM(PLAN_ID) = ?";
			Object[] parametros = new Object[] {planId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				comando = "SELECT DESCUENTO FROM ENOC.MAPA_PLAN WHERE TRIM(PLAN_ID) = ?";		
				descuento = enocJdbc.queryForObject(comando, String.class, parametros);
			}							
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaPlanDao|getDescuento|:"+ex);
		}
		return descuento;
	}
	
	public String getCarrera( String codigoPersonal, String planId) {
		String comando		= "";
		String nombre		= "x";
		
		try{			
			comando = "SELECT CARRERA_SE FROM ENOC.MAPA_PLAN WHERE PLAN_ID = ?"; 
			
			Object[] parametros = new Object[] {planId};///
			nombre = enocJdbc.queryForObject(comando, String.class, parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaPlanDao|getCarrera|:"+ex);
		}
		return nombre;
	}	
	
	public String getCarreraSe( String planId) {		
		String nombreCarrera	= "x";		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.MAPA_PLAN WHERE PLAN_ID = ?";		
			Object[] parametros = new Object[] {planId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				comando = "SELECT CARRERA_SE FROM ENOC.MAPA_PLAN WHERE PLAN_ID = ?";
				nombreCarrera = enocJdbc.queryForObject(comando, String.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaPlanDao|getCarreraSe|:"+ex);
		}
		return nombreCarrera;
	}
	
	public String getPlanOrigen( String cursoId) {
		String carrera			= "";	
		
		try{
			String comando = "SELECT NOMBRE_PLAN FROM ENOC.MAPA_PLAN WHERE PLAN_ID = " + 
					"(SELECT DISTINCT PLAN_ID FROM ENOC.MAPA_CURSO WHERE CURSO_ID = " + 
					"(SELECT DISTINCT CURSO_ID FROM ENOC.CARGA_GRUPO_CURSO " + 
					"WHERE CURSO_ID = ?))";
			
			Object[] parametros = new Object[] {cursoId};
			carrera = enocJdbc.queryForObject(comando, String.class, parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaPlanDao|getPlanOrigen|:"+ex);
		}
		return carrera;
	}	
	
	public String getPlanDeOrigen( String planId) {
		String origen = "X";
		try {
			String comando = "SELECT COUNT(*) FROM ENOC.MAPA_PLAN WHERE PLAN_ID = ?";	
			if (enocJdbc.queryForObject(comando, Integer.class, planId) >= 1){		
				comando = "SELECT PLAN_ORIGEN FROM ENOC.MAPA_PLAN WHERE PLAN_ID = ?";			
				origen = enocJdbc.queryForObject(comando, String.class, planId);
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaPlanDao|getPlanDeOrigen|:"+ex);
		}
		return origen;
	}
	
	public String getNumPlanes( String carreraId) {
		String carrera			= "";	
		
		try{
			String comando = "SELECT COUNT(*) AS CANTIDAD FROM ENOC.MAPA_PLAN WHERE CARRERA_ID= ? ";
			
			Object[] parametros = new Object[] {carreraId};				
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >=1 ){
				carrera = "1";
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaPlanDao|getNumPlanes|:"+ex);
		}		
		return carrera;
	}
	
	public String getNumPlanes( String carreraId, String estado) {
		String carrera			= "";	
		
		try{
			String comando = "SELECT COUNT(*) AS CANTIDAD FROM ENOC.MAPA_PLAN WHERE CARRERA_ID = ? AND ESTADO != ? ";

			carrera = enocJdbc.queryForObject(comando, String.class,carreraId,estado);			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaPlanDao|getNumPlanes|:"+ex);
		}
		return carrera;
	}
	
	public String getNumPlanesAdmision( String carreraId) {
		String carrera			= "";		
		try{
			String comando = "SELECT COUNT(*) AS CANTIDAD FROM ENOC.MAPA_PLAN WHERE CARRERA_ID = ? AND ESTADO = 'A'";			
			carrera = enocJdbc.queryForObject(comando, String.class, carreraId);			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaPlanDao|getNumPlanes|:"+ex);
		}		
		return carrera;
	}
	
	public int getNumPlanesPorEstado( String estado) {
		int total = 0;		
		try{
			String comando = "SELECT COUNT(*) AS CANTIDAD FROM ENOC.MAPA_PLAN WHERE ESTADO = ?";			
			total = enocJdbc.queryForObject(comando, Integer.class, estado);			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaPlanDao|getNumPlanes|:"+ex);
		}		
		return total;
	}
	
	
	public String getNivelId( String planId) {
		String nivel			= "0";		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.MAPA_PLAN WHERE PLAN_ID = ?";			
			Object[] parametros = new Object[] {planId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				comando = "SELECT NIVEL_ID FROM ENOC.CAT_CARRERA WHERE CARRERA_ID = (SELECT CARRERA_ID FROM ENOC.MAPA_PLAN WHERE PLAN_ID = ?)";			
				nivel = enocJdbc.queryForObject(comando, String.class, parametros);
			}					
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaPlanDao|getNivelId|:"+ex);
		}
		return nivel;
	}
	
	public String getCarreraIdPLAN(String planId) {
		String carreraId = "x";
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.MAPA_PLAN WHERE PLAN_ID = ?";			
			Object[] parametros = new Object[] {planId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				comando = "SELECT CARRERA_ID FROM ENOC.MAPA_PLAN WHERE PLAN_ID = ? ";			
				carreraId = enocJdbc.queryForObject(comando, String.class, parametros);
			}						
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaPlanDao|getCarreraIdPLAN|:"+ex);
		}
		return carreraId;
	}
	
	public String getNotaExtraPlan( String planId){
		String notaExtraPlan 		= "x";			
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.MAPA_PLAN WHERE PLAN_ID = ?";			
			Object[] parametros = new Object[] {planId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				comando = "SELECT NOTA_EXTRA FROM ENOC.MAPA_PLAN WHERE PLAN_ID = ?";			
				notaExtraPlan = enocJdbc.queryForObject(comando, String.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaPlanDao|getNotaExtraPlan|:"+ex);
		}
		return notaExtraPlan;
	}
	
	public String getPrecio(String planId ){		
		String precio			= "N";
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.MAPA_PLAN WHERE PLAN_ID = ?";			
			Object[] parametros = new Object[] {planId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				comando = "SELECT COALESCE(PRECIO,'N') AS PRECIO FROM ENOC.MAPA_PLAN WHERE PLAN_ID = ?";			
				precio = enocJdbc.queryForObject(comando, String.class, parametros);	
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaPlanDao|cotejado|:"+ex);
		}	
		return precio;		
	}
	
	public String getRvoe( String planId) {
		String revoe 		= "x";			
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.MAPA_PLAN WHERE PLAN_ID = ?";			
			Object[] parametros = new Object[] {planId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				comando = "SELECT RVOE FROM ENOC.MAPA_PLAN WHERE PLAN_ID = ?";		
				revoe = enocJdbc.queryForObject(comando, String.class, parametros);	
			}							
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaPlanDao|getRvoe|:"+ex);
		}		
		return revoe;
	}
	
	public String getOficial( String planId) {
		String oficial 		= "x";			
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.MAPA_PLAN WHERE PLAN_ID = ?";			
			Object[] parametros = new Object[] {planId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				comando = "SELECT OFICIAL FROM ENOC.MAPA_PLAN WHERE PLAN_ID = ?";
				oficial = enocJdbc.queryForObject(comando, String.class, parametros);
			}						
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaPlanDao|getOficial|:"+ex);
		}		
		return oficial;
	}
	
	public List<MapaPlan> getListAll( String orden ) {
		List<MapaPlan> lista		= new ArrayList<MapaPlan>();
		String comando		= "";
		
		try{
			comando = "SELECT PLAN_ID, CARRERA_ID, NOMBRE_PLAN, TO_CHAR(F_INICIO,'dd/mm/yyyy') AS F_INICIO,"
					+ " TO_CHAR(F_FINAL,'dd/mm/yyyy') AS F_FINAL,"
					+ " TO_CHAR(F_ACTUALIZA,'dd/mm/yyyy') F_ACTUALIZA, NUM_CURSOS,"
					+ " MINIMO, MAXIMO, CARRERA_SE, RVOE, OFICIAL, ESTADO, ENLINEA, CICLOS,"
					+ " NOTA_EXTRA, GENERAL, PLAN_SE, NOMBRE_PLAN_MUJER, CLAVE_PROFESIONES, PRECIO,"
					+ " RVOE_INICIAL, VERSION_ID, CLAVE_SEMSYS, EXPEDIENTE, CREDITOS, PLAN_ORIGEN, DESCUENTO"
					+ " FROM ENOC.MAPA_PLAN "+ orden;
			
			lista = enocJdbc.query(comando, new MapaPlanMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaPlanDao|getListAll|:"+ex);
		}
		return lista;	
	}
	
	public List<MapaPlan> lisConAcceso( String codigoPersonal, String orden ) {
		List<MapaPlan> lista		= new ArrayList<MapaPlan>();	
		try{
			String comando = "SELECT PLAN_ID, CARRERA_ID, NOMBRE_PLAN, TO_CHAR(F_INICIO,'dd/mm/yyyy') AS F_INICIO,"
					+ " TO_CHAR(F_FINAL,'dd/mm/yyyy') AS F_FINAL,"
					+ " TO_CHAR(F_ACTUALIZA,'dd/mm/yyyy') F_ACTUALIZA, NUM_CURSOS,"
					+ " MINIMO, MAXIMO, CARRERA_SE, RVOE, OFICIAL, ESTADO, ENLINEA, CICLOS,"
					+ " NOTA_EXTRA, GENERAL, PLAN_SE, NOMBRE_PLAN_MUJER, CLAVE_PROFESIONES, PRECIO,"
					+ " RVOE_INICIAL, VERSION_ID, CLAVE_SEMSYS, EXPEDIENTE, CREDITOS, PLAN_ORIGEN, DESCUENTO"
					+ " FROM ENOC.MAPA_PLAN "
					+ " WHERE PLAN_ID IN (SELECT PLAN_ID FROM ENOC.ACCESO_PLAN WHERE CODIGO_PERSONAL = ?)"+ orden;			
			lista = enocJdbc.query(comando, new MapaPlanMapper(), codigoPersonal);			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaPlanDao|lisConAcceso|:"+ex);
		}
		return lista;	
	}
	
	public List<MapaPlan> listPlanesEnCarga(String cargaId, String orden ) {
		List<MapaPlan> lista = new ArrayList<MapaPlan>();
		try{
			String comando = "SELECT PLAN_ID, CARRERA_ID, NOMBRE_PLAN, TO_CHAR(F_INICIO,'dd/mm/yyyy') AS F_INICIO, TO_CHAR(F_FINAL,'dd/mm/yyyy') AS F_FINAL,"
					+ " TO_CHAR(F_ACTUALIZA,'dd/mm/yyyy') F_ACTUALIZA, NUM_CURSOS, MINIMO, MAXIMO, CARRERA_SE, RVOE, OFICIAL, ESTADO, ENLINEA, CICLOS,"
					+ " NOTA_EXTRA, GENERAL, PLAN_SE, NOMBRE_PLAN_MUJER, CLAVE_PROFESIONES, PRECIO, RVOE_INICIAL, VERSION_ID, CLAVE_SEMSYS, EXPEDIENTE, CREDITOS, PLAN_ORIGEN, DESCUENTO FROM ENOC.MAPA_PLAN"
					+ " WHERE PLAN_ID IN(SELECT DISTINCT(PLAN_ID) FROM ENOC.CARGA_ACADEMICA WHERE CARGA_ID = ?) "+ orden;
			
			Object[] parametros = new Object[] {cargaId};
			
			lista = enocJdbc.query(comando, new MapaPlanMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaPlanDao|listPlanesEnCarga|:"+ex);
		}
		return lista;	
	}
	
	public List<MapaPlan> getListRegistrados( String orden ) {
		List<MapaPlan> lista		= new ArrayList<MapaPlan>();
		String comando		= "";
		
		try{
			comando = " SELECT PLAN_ID, CARRERA_ID, NOMBRE_PLAN, TO_CHAR(F_INICIO,'dd/mm/yyyy') AS F_INICIO," 
					+ " TO_CHAR(F_FINAL,'dd/mm/yyyy') AS F_FINAL," 
					+ " TO_CHAR(F_ACTUALIZA,'dd/mm/yyyy') F_ACTUALIZA, NUM_CURSOS,"  
					+ " MINIMO, MAXIMO, CARRERA_SE, RVOE, OFICIAL, ESTADO, ENLINEA, CICLOS," 
					+ " NOTA_EXTRA, GENERAL, PLAN_SE, NOMBRE_PLAN_MUJER, CLAVE_PROFESIONES, PRECIO,"
					+ " RVOE_INICIAL, VERSION_ID, CLAVE_SEMSYS, EXPEDIENTE, CREDITOS, PLAN_ORIGEN, DESCUENTO" 
					+ " FROM ENOC.MAPA_PLAN WHERE PLAN_ID IN(SELECT PLAN_ID FROM CARGA_PLAN_EXTERNO)"+ orden;
			
			lista = enocJdbc.query(comando, new MapaPlanMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaPlanDao|getListRegistrados|:"+ex);
		}
		return lista;	
	}
	
	public List<MapaPlan> getListPlanActivo( String orden ) {
		List<MapaPlan> lista		= new ArrayList<MapaPlan>();
		String comando		= "";
		
		try{
			comando = " SELECT PLAN_ID, CARRERA_ID, NOMBRE_PLAN, TO_CHAR(F_INICIO,'dd/mm/yyyy') AS F_INICIO,"
					+ " TO_CHAR(F_FINAL,'dd/mm/yyyy') AS F_FINAL,"
					+ " TO_CHAR(F_ACTUALIZA,'dd/mm/yyyy') F_ACTUALIZA, NUM_CURSOS,"
					+ " MINIMO, MAXIMO, CARRERA_SE, RVOE, OFICIAL, ESTADO, ENLINEA, CICLOS,"
					+ " NOTA_EXTRA, GENERAL, PLAN_SE, NOMBRE_PLAN_MUJER, CLAVE_PROFESIONES, PRECIO,"
					+ " RVOE_INICIAL, VERSION_ID, CLAVE_SEMSYS, EXPEDIENTE, CREDITOS, PLAN_ORIGEN, DESCUENTO"
					+ " FROM ENOC.MAPA_PLAN WHERE ESTADO IN ('A','V') "+ orden; 
			
			lista = enocJdbc.query(comando, new MapaPlanMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaPlanDao|getListAll|:"+ex);
		}
		return lista;	
	}
	
	public List<MapaPlan> getListPlanActivoEnCarga(String cargaId, String orden) {
		List<MapaPlan> lista		= new ArrayList<MapaPlan>();
		
		try{
			String comando = " SELECT PLAN_ID, CARRERA_ID, NOMBRE_PLAN, TO_CHAR(F_INICIO,'dd/mm/yyyy') AS F_INICIO,"
					+ " TO_CHAR(F_FINAL,'dd/mm/yyyy') AS F_FINAL,"
					+ " TO_CHAR(F_ACTUALIZA,'dd/mm/yyyy') F_ACTUALIZA, NUM_CURSOS,"
					+ " MINIMO, MAXIMO, CARRERA_SE, RVOE, OFICIAL, ESTADO, ENLINEA, CICLOS,"
					+ " NOTA_EXTRA, GENERAL, PLAN_SE, NOMBRE_PLAN_MUJER, CLAVE_PROFESIONES, PRECIO,"
					+ " RVOE_INICIAL, VERSION_ID, CLAVE_SEMSYS, EXPEDIENTE, CREDITOS, PLAN_ORIGEN, DESCUENTO"
					+ " FROM ENOC.MAPA_PLAN WHERE PLAN_ID IN (SELECT DISTINCT(PLAN_ID) AS PLAN_ID FROM CARGA_ACADEMICA WHERE CARGA_ID = ?)"+ orden; 
			
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new MapaPlanMapper(),parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaPlanDao|getListPlanActivoEnCarga|:"+ex);
		}
		return lista;	
	}
	
	public List<MapaPlan> listPlanesDelAlumno( String codigoAlumno, String orden ) {
		List<MapaPlan> lista		= new ArrayList<MapaPlan>();
		
		try{
			String comando = " SELECT PLAN_ID, CARRERA_ID, NOMBRE_PLAN, TO_CHAR(F_INICIO,'dd/mm/yyyy') AS F_INICIO,"
					+ " TO_CHAR(F_FINAL,'dd/mm/yyyy') AS F_FINAL,"
					+ " TO_CHAR(F_ACTUALIZA,'dd/mm/yyyy') F_ACTUALIZA, NUM_CURSOS,"
					+ " MINIMO, MAXIMO, CARRERA_SE, RVOE, OFICIAL, ESTADO, ENLINEA, CICLOS,"
					+ " NOTA_EXTRA, GENERAL, PLAN_SE, NOMBRE_PLAN_MUJER, CLAVE_PROFESIONES, PRECIO,"
					+ " RVOE_INICIAL, VERSION_ID, CLAVE_SEMSYS, EXPEDIENTE, CREDITOS, PLAN_ORIGEN, DESCUENTO"
					+ " FROM ENOC.MAPA_PLAN "
					+ " WHERE PLAN_ID IN (SELECT PLAN_ID FROM ENOC.ALUM_PLAN WHERE CODIGO_PERSONAL = ?) "+ orden; 
			Object[] parametros = new Object[] {codigoAlumno};
			lista = enocJdbc.query(comando, new MapaPlanMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaPlanDao|getListAll|:"+ex);
		}
		return lista;	
	}
	
	public List<MapaPlan> lisPlanesConMaterias( String codigoAlumno, String orden ){
		List<MapaPlan> lista		= new ArrayList<MapaPlan>();		
		try{
			String comando = " SELECT PLAN_ID, CARRERA_ID, NOMBRE_PLAN, TO_CHAR(F_INICIO,'dd/mm/yyyy') AS F_INICIO,"
					+ " TO_CHAR(F_FINAL,'dd/mm/yyyy') AS F_FINAL,"
					+ " TO_CHAR(F_ACTUALIZA,'dd/mm/yyyy') F_ACTUALIZA, NUM_CURSOS,"
					+ " MINIMO, MAXIMO, CARRERA_SE, RVOE, OFICIAL, ESTADO, ENLINEA, CICLOS,"
					+ " NOTA_EXTRA, GENERAL, PLAN_SE, NOMBRE_PLAN_MUJER, CLAVE_PROFESIONES, PRECIO,"
					+ " RVOE_INICIAL, VERSION_ID, CLAVE_SEMSYS, EXPEDIENTE, CREDITOS, PLAN_ORIGEN, DESCUENTO"
					+ " FROM ENOC.MAPA_PLAN "
					+ " WHERE PLAN_ID IN (SELECT DISTINCT(PLAN_ID) FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = ?) "+ orden; 
			Object[] parametros = new Object[] {codigoAlumno};
			lista = enocJdbc.query(comando, new MapaPlanMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaPlanDao|lisPlanesConMaterias|:"+ex);
		}
		return lista;	
	}
	
	public List<MapaPlan> getListPlanes( int year, String orden ) {
		List<MapaPlan> lista		= new ArrayList<MapaPlan>();
		String comando		= "";
				
		try{
			comando = " SELECT PLAN_ID, CARRERA_ID, NOMBRE_PLAN, TO_CHAR(F_INICIO,'dd/mm/yyyy') AS F_INICIO,"
					+ " TO_CHAR(F_FINAL,'dd/mm/yyyy') AS F_FINAL,"
					+ " TO_CHAR(F_ACTUALIZA,'dd/mm/yyyy') F_ACTUALIZA, NUM_CURSOS,"
					+ " MINIMO, MAXIMO, CARRERA_SE, RVOE, OFICIAL, ESTADO, ENLINEA, CICLOS,"
					+ " NOTA_EXTRA, GENERAL, PLAN_SE, NOMBRE_PLAN_MUJER, CLAVE_PROFESIONES, PRECIO,"
					+ " RVOE_INICIAL, VERSION_ID, CLAVE_SEMSYS, EXPEDIENTE, CREDITOS, PLAN_ORIGEN, DESCUENTO"
					+ " FROM ENOC.MAPA_PLAN"
					+ " WHERE ESTADO IN ('A', 'V')"
					+ " AND TO_NUMBER(TO_CHAR(F_INICIO, 'YYYY')) >= "+year+" "+ orden;
			
				lista = enocJdbc.query(comando, new MapaPlanMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaPlanDao|getListAll|:"+ex);
		}
		return lista;	
	}
	
	public List<MapaPlan> getLista( String carreraId, String orden ){
		List<MapaPlan> lista		= new ArrayList<MapaPlan>();
		String comando		= "";
		
		try{
			comando = "SELECT PLAN_ID, CARRERA_ID, NOMBRE_PLAN, F_INICIO, F_FINAL,"
					+ " F_ACTUALIZA, NUM_CURSOS, MINIMO, MAXIMO, CARRERA_SE, RVOE,"
					+ " OFICIAL, ESTADO, ENLINEA, CICLOS, NOTA_EXTRA, GENERAL, PLAN_SE,"
					+ " NOMBRE_PLAN_MUJER, CLAVE_PROFESIONES, PRECIO, RVOE_INICIAL, VERSION_ID, CLAVE_SEMSYS, EXPEDIENTE, CREDITOS, PLAN_ORIGEN, DESCUENTO"
					+ " FROM ENOC.MAPA_PLAN"
					+ " WHERE CARRERA_ID = ?"
					+ " AND ESTADO IN ('A', 'V') "+ orden;
			
			lista = enocJdbc.query(comando, new MapaPlanMapper(),carreraId);
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaPlanDao|getLista|:"+ex);
		}
		return lista;
	}
	
	public List<MapaPlan> getListPlanFacAll( String facultad, String orden ) {
		List<MapaPlan> lista		= new ArrayList<MapaPlan>();
		String comando		= "";
		
		try{
			comando = "SELECT PLAN_ID, CARRERA_ID, NOMBRE_PLAN, TO_CHAR(F_INICIO,'dd/mm/yyyy') AS F_INICIO, TO_CHAR(F_FINAL,'dd/mm/yyyy')F_FINAL,"
					+ " TO_CHAR(F_ACTUALIZA,'dd/mm/yyyy') F_ACTUALIZA, NUM_CURSOS, MINIMO, MAXIMO, CARRERA_SE, RVOE, OFICIAL, ESTADO,"
					+ " ENLINEA, CICLOS, NOTA_EXTRA, GENERAL, PLAN_SE, NOMBRE_PLAN_MUJER, CLAVE_PROFESIONES, PRECIO, RVOE_INICIAL, VERSION_ID, CLAVE_SEMSYS, EXPEDIENTE, CREDITOS, PLAN_ORIGEN, DESCUENTO"
					+ " FROM ENOC.MAPA_PLAN"
					+ " WHERE ENOC.FACULTAD(CARRERA_ID) = ? "
					+ " AND ESTADO IN ('A', 'V','I') "+ orden;
			
			lista = enocJdbc.query(comando, new MapaPlanMapper(),facultad); ///
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaPlanDao|getListPlanFacAll|:"+ex);
		}
		return lista;
	}
	
	public List<MapaPlan> lisPorFacultadyEstado( String facultad, String estados, String orden ) {
		List<MapaPlan> lista		= new ArrayList<MapaPlan>();
		String comando		= "";
		
		try{
			comando = "SELECT PLAN_ID, CARRERA_ID, NOMBRE_PLAN, TO_CHAR(F_INICIO,'dd/mm/yyyy') AS F_INICIO, TO_CHAR(F_FINAL,'dd/mm/yyyy')F_FINAL,"
					+ " TO_CHAR(F_ACTUALIZA,'dd/mm/yyyy') F_ACTUALIZA, NUM_CURSOS, MINIMO, MAXIMO, CARRERA_SE, RVOE, OFICIAL, ESTADO,"
					+ " ENLINEA, CICLOS, NOTA_EXTRA, GENERAL, PLAN_SE, NOMBRE_PLAN_MUJER, CLAVE_PROFESIONES, PRECIO, RVOE_INICIAL, VERSION_ID, CLAVE_SEMSYS, EXPEDIENTE, CREDITOS, PLAN_ORIGEN, DESCUENTO"
					+ " FROM ENOC.MAPA_PLAN"
					+ " WHERE ENOC.FACULTAD(CARRERA_ID) = ? "
					+ " AND ESTADO IN ("+estados+") "+ orden;			
			lista = enocJdbc.query(comando, new MapaPlanMapper(),facultad); ///
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaPlanDao|getListPlanFacAll|:"+ex);
		}
		return lista;
	}
	
	public List<MapaPlan> getListPlanFac( String facultad, String orden ) {
		List<MapaPlan> lista		= new ArrayList<MapaPlan>();
		String comando		= "";
		
		try{
			comando = " SELECT PLAN_ID, CARRERA_ID, NOMBRE_PLAN, TO_CHAR(F_INICIO,'dd/mm/yyyy') AS F_INICIO, TO_CHAR(F_FINAL,'dd/mm/yyyy')F_FINAL,"
					+ " TO_CHAR(F_ACTUALIZA,'dd/mm/yyyy') F_ACTUALIZA, NUM_CURSOS, MINIMO, MAXIMO, CARRERA_SE, RVOE, OFICIAL, ESTADO,"
					+ " ENLINEA, CICLOS, NOTA_EXTRA, GENERAL, PLAN_SE, NOMBRE_PLAN_MUJER, CLAVE_PROFESIONES, PRECIO, RVOE_INICIAL, VERSION_ID, CLAVE_SEMSYS, EXPEDIENTE, CREDITOS, PLAN_ORIGEN, DESCUENTO"
					+ " FROM ENOC.MAPA_PLAN"
					+ " WHERE ENOC.FACULTAD(CARRERA_ID) = ?"
					+ " AND ESTADO IN ('A', 'V') "+ orden;
			
			lista = enocJdbc.query(comando, new MapaPlanMapper(),facultad);
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaPlanDao|getListPlanFac|:"+ex);
		}
		return lista;
	}
	
	public List<MapaPlan> getListPlanFacAdmision( String facultad, String orden ) {
		List<MapaPlan> lista		= new ArrayList<MapaPlan>();
		String comando		= "";
		
		try{
			comando = " SELECT PLAN_ID, CARRERA_ID, NOMBRE_PLAN, TO_CHAR(F_INICIO,'dd/mm/yyyy') AS F_INICIO, TO_CHAR(F_FINAL,'dd/mm/yyyy')F_FINAL,"
					+ " TO_CHAR(F_ACTUALIZA,'dd/mm/yyyy') F_ACTUALIZA, NUM_CURSOS, MINIMO, MAXIMO, CARRERA_SE, RVOE, OFICIAL, ESTADO,"
					+ " ENLINEA, CICLOS, NOTA_EXTRA, GENERAL, PLAN_SE, NOMBRE_PLAN_MUJER, CLAVE_PROFESIONES, PRECIO, RVOE_INICIAL, VERSION_ID, CLAVE_SEMSYS, EXPEDIENTE, CREDITOS, PLAN_ORIGEN, DESCUENTO"
					+ " FROM ENOC.MAPA_PLAN"
					+ " WHERE ENOC.FACULTAD(CARRERA_ID) = ?"
					+ " AND ESTADO IN ('A') "+ orden;
			
			lista = enocJdbc.query(comando, new MapaPlanMapper(),facultad);
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaPlanDao|getListPlanFac|:"+ex);
		}
		return lista;
	}
	
	public List<MapaPlan> lisPlanesEnCarga( String facultadId, String cargaId, String orden ) {
		List<MapaPlan> lista		= new ArrayList<MapaPlan>();		
		try{
			String comando = " SELECT PLAN_ID, CARRERA_ID, NOMBRE_PLAN, TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO, TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL,"
				+ " TO_CHAR(F_ACTUALIZA,'DD/MM/YYYY') AS F_ACTUALIZA, NUM_CURSOS, MINIMO, MAXIMO, CARRERA_SE, RVOE, OFICIAL, ESTADO,"
				+ " ENLINEA, CICLOS, NOTA_EXTRA, GENERAL, PLAN_SE, NOMBRE_PLAN_MUJER, CLAVE_PROFESIONES, PRECIO, RVOE_INICIAL, VERSION_ID, CLAVE_SEMSYS, EXPEDIENTE, CREDITOS, PLAN_ORIGEN, DESCUENTO"
				+ " FROM ENOC.MAPA_PLAN"
				+ " WHERE ENOC.FACULTAD(CARRERA_ID) = ?"
				+ " AND PLAN_ID IN ("
				+ "		SELECT DISTINCT(SUBSTR(CURSO_ID,1,8)) FROM ENOC.CARGA_GRUPO_CURSO WHERE CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = ?)"
				+ "	) "+ orden;
			Object[] parametros = new Object[] {facultadId, cargaId};
			lista = enocJdbc.query(comando, new MapaPlanMapper(), parametros);		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaPlanDao|lisPlanesEnCarga|:"+ex);
		}
		return lista;
	}
	
	public List<MapaPlan> listPlanFac( String facultad, String estados, String orden ) {
		List<MapaPlan> lista		= new ArrayList<MapaPlan>();
		String comando		= "";
		
		try{
			comando = "SELECT PLAN_ID, CARRERA_ID, NOMBRE_PLAN, TO_CHAR(F_INICIO,'dd/mm/yyyy') AS F_INICIO, TO_CHAR(F_FINAL,'dd/mm/yyyy')F_FINAL,"
					+ " TO_CHAR(F_ACTUALIZA,'dd/mm/yyyy') F_ACTUALIZA, NUM_CURSOS, MINIMO, MAXIMO, CARRERA_SE, RVOE, OFICIAL, ESTADO,"
					+ " ENLINEA, CICLOS, NOTA_EXTRA, GENERAL, PLAN_SE, NOMBRE_PLAN_MUJER, CLAVE_PROFESIONES, PRECIO, RVOE_INICIAL, VERSION_ID, CLAVE_SEMSYS, EXPEDIENTE, CREDITOS, PLAN_ORIGEN, DESCUENTO"
					+ " FROM ENOC.MAPA_PLAN"
					+ " WHERE ENOC.FACULTAD(CARRERA_ID) = ? "
					+ " AND ESTADO IN ("+estados+") "+ orden;
			
			lista = enocJdbc.query(comando, new MapaPlanMapper(),facultad);
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaPlanDao|getListPlanFac|:"+ex);
		}
		return lista;
	}
	
	public List<MapaPlan> listPlanes( String estados, String orden ){
		List<MapaPlan> lista		= new ArrayList<MapaPlan>();		
		try{
			String comando = "SELECT PLAN_ID, CARRERA_ID, NOMBRE_PLAN, TO_CHAR(F_INICIO,'dd/mm/yyyy') AS F_INICIO, TO_CHAR(F_FINAL,'dd/mm/yyyy') F_FINAL,"
					+ " TO_CHAR(F_ACTUALIZA,'dd/mm/yyyy') F_ACTUALIZA, NUM_CURSOS, MINIMO, MAXIMO, CARRERA_SE, RVOE, OFICIAL, ESTADO,"
					+ " ENLINEA, CICLOS, NOTA_EXTRA, GENERAL, PLAN_SE, NOMBRE_PLAN_MUJER, CLAVE_PROFESIONES, PRECIO, RVOE_INICIAL, VERSION_ID, CLAVE_SEMSYS, EXPEDIENTE, CREDITOS, PLAN_ORIGEN, DESCUENTO"
					+ " FROM ENOC.MAPA_PLAN"
					+ " WHERE ESTADO IN ("+estados+") "+ orden;			
			lista = enocJdbc.query(comando, new MapaPlanMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaPlanDao|listPlanes|:"+ex);
		}
		return lista;
	}

	public List<MapaPlan> listPlanesRegistro(String orden ){
		List<MapaPlan> lista = new ArrayList<MapaPlan>();		
		try{
			String comando = "SELECT PLAN_ID, CARRERA_ID, NOMBRE_PLAN, TO_CHAR(F_INICIO,'dd/mm/yyyy') AS F_INICIO, TO_CHAR(F_FINAL,'dd/mm/yyyy')F_FINAL,"
					+ " TO_CHAR(F_ACTUALIZA,'dd/mm/yyyy') F_ACTUALIZA, NUM_CURSOS, MINIMO, MAXIMO, CARRERA_SE, RVOE, OFICIAL, ESTADO,"
					+ " ENLINEA, CICLOS, NOTA_EXTRA, GENERAL, PLAN_SE, NOMBRE_PLAN_MUJER, CLAVE_PROFESIONES, PRECIO, RVOE_INICIAL, VERSION_ID, CLAVE_SEMSYS, EXPEDIENTE, CREDITOS, PLAN_ORIGEN, DESCUENTO"
					+ " FROM ENOC.MAPA_PLAN"
					+ " WHERE GENERAL = 'S'"+ orden;			
			lista = enocJdbc.query(comando, new MapaPlanMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaPlanDao|listPlanesRegistro|:"+ex);
		}
		return lista;
	}
	
	public List<MapaPlan> getPlanGeneral( String orden ) {
		List<MapaPlan> lista		= new ArrayList<MapaPlan>();
		String comando		= "";
		
		try{
			comando = " SELECT PLAN_ID, CARRERA_ID, NOMBRE_PLAN, TO_CHAR(F_INICIO,'dd/mm/yyyy') AS F_INICIO, TO_CHAR(F_FINAL,'dd/mm/yyyy')F_FINAL,"
					+ " TO_CHAR(F_ACTUALIZA,'dd/mm/yyyy') F_ACTUALIZA, NUM_CURSOS, MINIMO, MAXIMO, CARRERA_SE, RVOE, OFICIAL, ESTADO,"
					+ " ENLINEA, CICLOS, NOTA_EXTRA, GENERAL, PLAN_SE, NOMBRE_PLAN_MUJER, CLAVE_PROFESIONES, PRECIO, RVOE_INICIAL, VERSION_ID, CLAVE_SEMSYS, EXPEDIENTE, CREDITOS, PLAN_ORIGEN, DESCUENTO"
					+ " FROM ENOC.MAPA_PLAN WHERE GENERAL = 'S' "+ orden;
			
			lista = enocJdbc.query(comando, new MapaPlanMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaPlanDao|getPlanGeneral|:"+ex);
		}
		return lista;
	}
	
	public List<MapaPlan> getListRvoe( String oficial, String estado,String orden ) {
		List<MapaPlan> lista		= new ArrayList<MapaPlan>();
		String comando		= "";
		try{
			comando = "SELECT DISTINCT(RVOE) AS RVOE FROM ENOC.MAPA_PLAN WHERE OFICIAL='"+oficial+"' AND ESTADO != '"+estado+"'"+ orden;
			
			lista = enocJdbc.query(comando, new MapaPlanMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaPlanDao|getListRvoe|:"+ex);
		}
		return lista;
	}

	// Mapa de planes con permiso en Admisiones
	public List<MapaPlan> getPlanesAdmisiones( String orden ) {
		List<MapaPlan> lista		= new ArrayList<MapaPlan>();
		String comando		= "";
		
		try{
			comando = " SELECT PLAN_ID, CARRERA_ID, NOMBRE_PLAN, TO_CHAR(F_INICIO,'dd/mm/yyyy') AS F_INICIO, TO_CHAR(F_FINAL,'dd/mm/yyyy')F_FINAL,"
					+ " TO_CHAR(F_ACTUALIZA,'dd/mm/yyyy') F_ACTUALIZA, NUM_CURSOS, MINIMO, MAXIMO, CARRERA_SE, RVOE, OFICIAL, ESTADO,"
					+ " ENLINEA, CICLOS, NOTA_EXTRA, GENERAL, PLAN_SE, NOMBRE_PLAN_MUJER, CLAVE_PROFESIONES, PRECIO, RVOE_INICIAL, VERSION_ID, CLAVE_SEMSYS, EXPEDIENTE, CREDITOS, PLAN_ORIGEN, DESCUENTO"
					+ " FROM ENOC.MAPA_PLAN WHERE PLAN_ID IN (SELECT DISTINCT(PLAN_ID) FROM SALOMON.ADM_ACADEMICO) "+ orden;
			
			lista = enocJdbc.query(comando, new MapaPlanMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaPlanDao|getPlanesAdmisiones|:"+ex);
		}
		return lista;
	}
	
	//Ayuda
	public HashMap<String, String> mapRvoeAlum( String cargaId, String orden) {
		HashMap<String, String> mapa 	= new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		String comando					= "";
	
		try{
			comando = " SELECT RVOE, COUNT( CODIGO_PERSONAL) AS LLAVE"
					+ " FROM ENOC.ESTADISTICA E INNER JOIN ENOC.MAPA_PLAN M ON E.CARRERA_ID = M.CARRERA_ID AND E.PLAN_ID = M.PLAN_ID"
					+ " WHERE CARGA_ID = ? GROUP BY RVOE"+orden;
				
			lista = enocJdbc.query(comando, new aca.MapaMapper(),cargaId);
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaPlanDao|mapRvoeAlum|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaPlanesEnCarrera( String estados) {
		HashMap<String, String> mapa 	= new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		try{
			String comando = " SELECT CARRERA_ID AS LLAVE, COUNT(*) AS VALOR FROM ENOC.MAPA_PLAN WHERE ESTADO IN ("+estados+") GROUP BY CARRERA_ID";				
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa map : lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaPlanDao|mapaPlanesEnCarrera|:"+ex);
		}
		return mapa;
	}	
	
	public TreeMap<String, MapaPlan> getTreePlan( String orden ) {
		List<MapaPlan> lista	= new ArrayList<MapaPlan>();
		TreeMap<String,MapaPlan> mapa		= new TreeMap<String,MapaPlan>();
		
		
		String comando		= "";
		
		try{
			comando = " SELECT PLAN_ID, CARRERA_ID, NOMBRE_PLAN, TO_CHAR(F_INICIO,'dd/mm/yyyy') AS F_INICIO, TO_CHAR(F_FINAL,'dd/mm/yyyy')F_FINAL,"
					+ " TO_CHAR(F_ACTUALIZA,'dd/mm/yyyy') F_ACTUALIZA, NUM_CURSOS, MINIMO, MAXIMO, CARRERA_SE, RVOE, OFICIAL, ESTADO,"
					+ " ENLINEA, CICLOS, NOTA_EXTRA, GENERAL, PLAN_SE, NOMBRE_PLAN_MUJER, CLAVE_PROFESIONES, PRECIO, RVOE_INICIAL, VERSION_ID, CLAVE_SEMSYS, EXPEDIENTE, CREDITOS, PLAN_ORIGEN, DESCUENTO"
					+ " FROM ENOC.MAPA_PLAN"
					+ " WHERE ESTADO IN ('A', 'V') "+ orden;
			
			lista = enocJdbc.query(comando, new MapaPlanMapper());
			
			for (MapaPlan estado : lista){			
				mapa.put(estado.getPlanId(), estado);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaPlanDao|getTreePlan|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, MapaPlan> mapPlanes( String estados ) {
		List<MapaPlan> lista			= new ArrayList<MapaPlan>();
		HashMap<String,MapaPlan> mapa	= new HashMap<String,MapaPlan>();	
		
		try{
			String comando = " SELECT PLAN_ID, CARRERA_ID, NOMBRE_PLAN, TO_CHAR(F_INICIO,'dd/mm/yyyy') AS F_INICIO, TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL,"
					+ " TO_CHAR(F_ACTUALIZA,'DD/MM/YYYY') AS F_ACTUALIZA, NUM_CURSOS, MINIMO, MAXIMO, CARRERA_SE, RVOE, OFICIAL, ESTADO,"
					+ " ENLINEA, CICLOS, NOTA_EXTRA, GENERAL, PLAN_SE, NOMBRE_PLAN_MUJER, CLAVE_PROFESIONES, PRECIO, RVOE_INICIAL, VERSION_ID, CLAVE_SEMSYS, EXPEDIENTE, CREDITOS, PLAN_ORIGEN, DESCUENTO"
					+ " FROM ENOC.MAPA_PLAN"
					+ " WHERE ESTADO IN ("+estados+")";
			lista = enocJdbc.query(comando, new MapaPlanMapper());
			
			for (MapaPlan plan : lista){
				mapa.put(plan.getPlanId(), plan);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaPlanDao|mapPlanes|:"+ex);
		}
		return mapa;
	}
	
	public List<MapaPlan> lisPlanesEnCarga( String cargaId ) {
		List<MapaPlan> lista			= new ArrayList<MapaPlan>();
		try{
			String comando = " SELECT PLAN_ID, CARRERA_ID, NOMBRE_PLAN, TO_CHAR(F_INICIO,'dd/mm/yyyy') AS F_INICIO, TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL,"
					+ " TO_CHAR(F_ACTUALIZA,'DD/MM/YYYY') AS F_ACTUALIZA, NUM_CURSOS, MINIMO, MAXIMO, CARRERA_SE, RVOE, OFICIAL, ESTADO,"
					+ " ENLINEA, CICLOS, NOTA_EXTRA, GENERAL, PLAN_SE, NOMBRE_PLAN_MUJER, CLAVE_PROFESIONES, PRECIO, RVOE_INICIAL, VERSION_ID, CLAVE_SEMSYS, EXPEDIENTE, CREDITOS, PLAN_ORIGEN, DESCUENTO"
					+ " FROM ENOC.MAPA_PLAN"
					+ " WHERE PLAN_ID IN (SELECT DISTINCT(SUBSTR(CURSO_ID,1,8)) FROM ENOC.CARGA_GRUPO_CURSO WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ?)";
			lista = enocJdbc.query(comando, new MapaPlanMapper(), cargaId);			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaPlanDao|lisPlanesEnCarga|:"+ex);
		}
		return lista;
	}
	
	public String getNombrePlan( String planId ) {
		String nombre			= "x";
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.MAPA_PLAN WHERE PLAN_ID = ?";	
			Object[] parametros = new Object[] {planId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				comando = "SELECT NOMBRE_PLAN FROM ENOC.MAPA_PLAN WHERE PLAN_ID = ?";
				nombre = enocJdbc.queryForObject(comando, String.class, parametros);
			}
				
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaPlanDao|getNombrePlan|:"+ex);
		}
		return nombre;
	}	

	public String getNombreSe( String planId ) {
		String nombre			= "x";
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.MAPA_PLAN WHERE PLAN_ID = ?";	
			Object[] parametros = new Object[] {planId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				comando = "SELECT CARRERA_SE FROM ENOC.MAPA_PLAN WHERE PLAN_ID = ?";
				nombre = enocJdbc.queryForObject(comando, String.class, parametros);
			}
				
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaPlanDao|getNombreSe|:"+ex);
		}
		return nombre;
	}
	
	//Ayuda	
	public HashMap<String, String> mapaCarreraOficial() {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		try{
			String comando = " SELECT PLAN_ID AS LLAVE, CARRERA_SE AS VALOR FROM ENOC.MAPA_PLAN";		
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaPlanDao|mapaCarreraOficial|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapCarreraCultural() {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT PLAN_ID AS LLAVE, CARRERA_ID AS VALOR FROM ENOC.MAPA_PLAN WHERE PLAN_ID IN (SELECT PLAN_ID FROM ENOC.COMP_ASISTENCIA_ALUMNO)";
			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaPlanDao|mapCarreraCultural|:"+ex);
		}
		return mapa;
	}
	 
	public HashMap<String, String> mapCarreraPlan() {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		String comando	= "";
	
		try{
			comando = " SELECT PLAN_ID AS LLAVE, CARRERA_ID AS VALOR FROM ENOC.MAPA_PLAN";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaPlanDao|mapCarreraPlan|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaNombreOficial(){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		try{
			String comando = " SELECT PLAN_ID AS LLAVE, CARRERA_SE AS VALOR FROM ENOC.MAPA_PLAN";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaPlanDao|mapaNombreOficial|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapNombrePlan() {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		String comando	= "";
	
		try{
			comando = " SELECT PLAN_ID AS LLAVE, NOMBRE_PLAN AS VALOR FROM ENOC.MAPA_PLAN";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaPlanDao|mapNombrePlan|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaFacultadDelPlan() {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();	
		try{
			String comando = "SELECT PLAN_ID AS LLAVE, FACULTAD(CARRERA_ID) AS VALOR FROM ENOC.MAPA_PLAN";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaPlanDao|mapaFacultadDelPlan|:"+ex);
		}
		return mapa;
	}
	
	
	public boolean cotejado( String planId ) {
		String cotejado = "";
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.MAPA_PLAN WHERE PLAN_ID = ?";
			if (enocJdbc.queryForObject(comando, Integer.class,planId) >= 1){
				comando = "SELECT COALESCE(COTEJADO,'N') FROM ENOC.MAPA_PLAN WHERE PLAN_ID = ?";
				cotejado = enocJdbc.queryForObject(comando, String.class,planId);
			}				
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaPlanDao|cotejado|:"+ex);
		}
		if(cotejado.equals("S")) 
			return true;
		else 
			return false;
	}	

	public void cambiaCotejado( String planId,String cotejado) {
		String comando			= "";

		try{
			comando = "UPDATE ENOC.MAPA_PLAN SET COTEJADO= ? WHERE PLAN_ID = ?";
			Object[] parametros = new Object[] {cotejado,planId};
			enocJdbc.update(comando,parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaPlanDao|cambiaCotejado|:"+ex);
		}
	}	
	
	public HashMap<String,String> mapCarreraPlan(List<CertPlan> lisPlan) {
		HashMap<String,String> mapa = new HashMap<String,String>();
		try{
			
			for(CertPlan certPlan : lisPlan) {
				
				String comando = "SELECT CARRERA_ID FROM ENOC.MAPA_PLAN WHERE TRIM(PLAN_ID) = ?";
				
				String carrera = enocJdbc.queryForObject(comando, String.class, new Object[] {certPlan.getPlanId()});
				
				mapa.put(certPlan.getPlanId(), carrera);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaPlanDao|mapCarreraPlan|:"+ex);			
		}

		return mapa;
	}
	
	public HashMap<String, String> mapaPlanesPorCarrera(){		
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();		
		try{			
			String comando = "SELECT CARRERA_ID AS LLAVE, COUNT(*) AS VALOR FROM ENOC.MAPA_PLAN GROUP BY CARRERA_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper());		
			for(aca.Mapa map :lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.MapaPlanDao|mapaPlanesPorCarrera|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaPlanesPorFacultad(){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		try{			
			String comando = "SELECT FACULTAD(CARRERA_ID)||ESTADO AS LLAVE, COUNT(PLAN_ID) AS VALOR FROM ENOC.MAPA_PLAN GROUP BY FACULTAD(CARRERA_ID),ESTADO";
			lista = enocJdbc.query(comando, new aca.MapaMapper());		
			for(aca.Mapa map :lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.MapaPlanDao|mapaPlanesPorFacultad|:"+ex);
		}
		
		return mapa;
	}
}