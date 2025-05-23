//Clase Util para la tabla de Actividad
package aca.carga.spring;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CargaGrupoActividadDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( CargaGrupoActividad actividad ) {	
		boolean ok = false;		
		try{			
			actividad.setActividadId( maximoReg());
			String comando = "INSERT INTO ENOC.CARGA_GRUPO_ACTIVIDAD(" + 
				" ACTIVIDAD_ID, CURSO_CARGA_ID, EVALUACION_ID, NOMBRE, VALOR, FECHA, ACTIVIDAD_E42, AGENDADA_E42, ENVIAR)"+
				" VALUES(TO_NUMBER(?,'9999999')," +
				" ?," +
				" TO_NUMBER(?,'99')," +
				" ?," +
				" TO_NUMBER(?,'999.99')," +
				" TO_DATE(?,'DD/MM/YYYY HH24:MI:SS')," +
				" TO_NUMBER(?,'9999999'),?,?)";
			Object[] parametros = new Object[] {actividad.getActividadId(), actividad.getCursoCargaId(), actividad.getEvaluacionId(),
				actividad.getNombre(), actividad.getValor(), actividad.getFecha(), actividad.getActividadE42(), actividad.getAgendadaE42(),
				actividad.getEnviar() 
			};			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoActividadDao|insertReg|:"+ex);			
		}		
		return ok;
	}	
	
	public boolean updateReg( CargaGrupoActividad actividad ) {
		boolean ok = false;		
		
		try{			
			String comando = "UPDATE ENOC.CARGA_GRUPO_ACTIVIDAD" 
				+ " SET CURSO_CARGA_ID = ?,"
				+ " EVALUACION_ID = TO_NUMBER(?,'99'),"
				+ " NOMBRE = ?,"
				+ " VALOR = TO_NUMBER(?,'999.99'),"
				+ " FECHA = TO_DATE(?,'DD/MM/YYYY HH24:MI:SS')," 
				+ " ACTIVIDAD_E42 = TO_NUMBER(?,'9999999'),"
				+ " AGENDADA_E42 = ?,"
				+ " ENVIAR = ?"
				+ " WHERE ACTIVIDAD_ID = TO_NUMBER(?,'9999999')";
			Object[] parametros = new Object[] {actividad.getCursoCargaId(), actividad.getEvaluacionId(), actividad.getNombre(),
				actividad.getValor(), actividad.getFecha(), actividad.getActividadE42(), actividad.getAgendadaE42(), actividad.getEnviar(), 
				actividad.getActividadId()
			};			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoActividadDao|updateReg|:"+ex);		 
		}
		return ok;
	}
	
	public boolean updateRegE42( CargaGrupoActividad actividad ) {
		boolean ok = false;		
		try{		
			String comando	= " UPDATE ENOC.CARGA_GRUPO_ACTIVIDAD"				 
							+ " SET NOMBRE = ?,"
							+ " VALOR  = ?,"
							+ " FECHA = TO_DATE(?,'DD/MM/YYYY'),"
							+ " WHERE ACTIVIDAD_E42 = ?";
			if (enocJdbc.update(comando, actividad.getNombre(), actividad.getValor(), actividad.getFecha(), actividad.getActividadE42())==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoActividadDao|updateRegE42|:"+ex);		 
		}
		return ok;
	}
	
	public boolean deleteReg( String actividadId ) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.CARGA_GRUPO_ACTIVIDAD WHERE ACTIVIDAD_ID = ? ";
			Object[] parametros = new Object[] {actividadId};
			if (enocJdbc.update(comando,parametros) == 1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoActividadDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public boolean deleteTodos( String cursoCargaId ) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.CARGA_GRUPO_ACTIVIDAD WHERE CURSO_CARGA_ID = ?";
			Object[] parametros = new Object[] {cursoCargaId};
			if (enocJdbc.update(comando,parametros) >= 1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoActividadDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public boolean deleteRegE42( String actividadE42 ) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.CARGA_GRUPO_ACTIVIDAD WHERE ACTIVIDAD_E42 = ? ";
			Object[] parametros = new Object[] {actividadE42};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoActividadDao|deleteRegE42|:"+ex);			
		}
		return ok;
	}
	
	public CargaGrupoActividad mapeaRegId(  String actividadId) {
		
		CargaGrupoActividad objeto = new CargaGrupoActividad();		
		try{
			String comando = "SELECT ACTIVIDAD_ID, CURSO_CARGA_ID, EVALUACION_ID,"
				+ " NOMBRE, VALOR, TO_CHAR(FECHA,'DD/MM/YYYY HH24:MI:SS') AS FECHA, ACTIVIDAD_E42, AGENDADA_E42, ENVIAR"
				+ " FROM ENOC.CARGA_GRUPO_ACTIVIDAD"
				+ " WHERE ACTIVIDAD_ID = TO_NUMBER(?,'9999999')";
			Object[] parametros = new Object[] {actividadId};
			objeto = enocJdbc.queryForObject(comando, new CargaGrupoActividadMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoActividadDao|mapeaRegId|:"+ex);
		}
		return objeto;
	}
	
	public CargaGrupoActividad mapeaRegIdE42(  String actividadE42) {
		
		CargaGrupoActividad objeto = new CargaGrupoActividad();		
		try{
			String comando = "SELECT"+
				" ACTIVIDAD_ID, CURSO_CARGA_ID, EVALUACION_ID,"+
				" NOMBRE, VALOR, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, ACTIVIDAD_E42, AGENDADA_E42, ENVIAR"+
				" FROM ENOC.CARGA_GRUPO_ACTIVIDAD"+	 
				" WHERE ACTIVIDAD_E42 = ?";
			Object[] parametros = new Object[] {actividadE42};
			objeto = enocJdbc.queryForObject(comando, new CargaGrupoActividadMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoActividadDao|mapeaRegIdE42|:"+ex);
		}
		return objeto;
	}
	
	public boolean existeReg( String actividadId, String cursoCargaId) {
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT(ACTIVIDAD_ID) FROM ENOC.CARGA_GRUPO_ACTIVIDAD"+ 
				" WHERE ACTIVIDAD_ID = ? AND CURSO_CARGA_ID = ?";
			Object[] parametros = new Object[] {actividadId,cursoCargaId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoActividadDao|existeReg|:"+ex);
		}		
		return ok;
	}
	
	public boolean existeReg( String cursoCargaId) {
		boolean ok = false;		
		try{
			String comando = "SELECT COUNT(ACTIVIDAD_ID) FROM ENOC.CARGA_GRUPO_ACTIVIDAD"
					+ " WHERE CURSO_CARGA_ID = ?";
			Object[] parametros = new Object[] {cursoCargaId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoActividadDao|existeReg|:"+ex);
		}		
		return ok;
	}
	
	public boolean existeRegId( String actividadId) {
		boolean ok = false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA_GRUPO_ACTIVIDAD WHERE ACTIVIDAD_ID = TO_NUMBER(?,'9999999')";
			Object[] parametros = new Object[] {actividadId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1 ){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoActividadDao|existeRegId|:"+ex);
		}
		return ok;
	}
	
	public boolean existeRegIdE42( String actividadE42) {
		boolean ok = false;
		
		try{
			String comando = "SELECT ACTIVIDAD_E42 FROM ENOC.CARGA_GRUPO_ACTIVIDAD"+ 
				" WHERE ACTIVIDAD_E42 = ?";
			Object[] parametros = new Object[] {actividadE42};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoActividadDao|existeRegIdE42|:"+ex);
		}
		return ok;
	}
	
	public String maximoReg() {
		String maximo = "1";		
		try{
			String comando = "SELECT COALESCE(MAX(ACTIVIDAD_ID)+1,1) AS MAXIMO"+
				" FROM ENOC.CARGA_GRUPO_ACTIVIDAD ";
 			if (enocJdbc.queryForObject(comando, Integer.class) >= 1) {
 				maximo = enocJdbc.queryForObject(comando, String.class);
			} 			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoActividadDao|maximotReg|:"+ex);
		}
		return maximo;
	}
	
	public int getNumAct( String cursoCargaId) {
		int numAct = 0;		
		try{
			String comando = "SELECT COALESCE(COUNT(ACTIVIDAD_ID),0) AS NUMACT FROM ENOC.CARGA_GRUPO_ACTIVIDAD"
					+ " WHERE CURSO_CARGA_ID = ?";
			if (enocJdbc.queryForObject(comando, Integer.class, cursoCargaId) >= 1) {
				numAct = enocJdbc.queryForObject(comando, Integer.class, cursoCargaId);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoActividadDao|getNumAct|:"+ex);
		}
		return numAct;
	}
	
	public int deleteGpoAct( String cursoCargaId ) {
		int numAct = 0;		
		try{
			String comando = "SELECT COUNT(CURSO_CARGA_ID) FROM ENOC.CARGA_GRUPO_ACTIVIDAD WHERE CURSO_CARGA_ID = ?";
			Object[] parametros = new Object[] {cursoCargaId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				comando = "DELETE FROM ENOC.CARGA_GRUPO_ACTIVIDAD WHERE CURSO_CARGA_ID = ?";
				numAct = enocJdbc.update(comando, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoActividadDao|deleteGpoAct|:"+ex);
		}
		return numAct;
	}
		
	public List<CargaGrupoActividad> getListAll( String orden ) {		
		List<CargaGrupoActividad> lista	= new ArrayList<CargaGrupoActividad>();		
		try{
			String comando = "SELECT ACTIVIDAD_ID, CURSO_CARGA_ID, EVALUACION_ID,"+
					" NOMBRE, VALOR, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, ACTIVIDAD_E42, AGENDADA_E42, ENVIAR"+
					" FROM ENOC.CARGA_GRUPO_ACTIVIDAD "+orden;
			lista = enocJdbc.query(comando, new CargaGrupoActividadMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoActividadDao|getListAll|:"+ex);
		}
		return lista;
	}
	
	public List<CargaGrupoActividad> getListCurso( String cursoCargaId, String orden) {
		
		List<CargaGrupoActividad> lista	= new ArrayList<CargaGrupoActividad>();		
		try{
			String comando = "SELECT ACTIVIDAD_ID, CURSO_CARGA_ID, EVALUACION_ID,"+
					" NOMBRE, VALOR, TO_CHAR(FECHA,'DD/MM/YYYY HH24:MI:SS') AS FECHA, ACTIVIDAD_E42, AGENDADA_E42, ENVIAR"+
					" FROM ENOC.CARGA_GRUPO_ACTIVIDAD" + 
					" WHERE CURSO_CARGA_ID = ? "+orden;
			lista = enocJdbc.query(comando, new CargaGrupoActividadMapper(), cursoCargaId);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoActividadDao|getListCurso|:"+ex);
		}
		return lista;
	}
	
	public List<CargaGrupoActividad> getListCargaEvaluacion( String cursoCarga, String evaluacion ) {
		
		List<CargaGrupoActividad> lista = new ArrayList<CargaGrupoActividad>();
		
		try{
			String comando = "SELECT ACTIVIDAD_ID, CURSO_CARGA_ID, EVALUACION_ID,"+
				" NOMBRE, VALOR, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, ACTIVIDAD_E42, AGENDADA_E42, ENVIAR"+
				" FROM ENOC.CARGA_GRUPO_ACTIVIDAD"+ 
				" WHERE CURSO_CARGA_ID = ?" +
				" AND EVALUACION_ID = ?"+
				" ORDER BY FECHA, ACTIVIDAD_ID";
			lista = enocJdbc.query(comando, new CargaGrupoActividadMapper(), cursoCarga, evaluacion);
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoActividadDao|getListCargaEvaluacion|:"+ex);
		}
		return lista;
	}
	
	public List<CargaGrupoActividad> getListAllCargaEvaluacion( String cursoCarga ) {
		
		List<CargaGrupoActividad> lista = new ArrayList<CargaGrupoActividad>();
		
		try{
			String comando = "SELECT ACTIVIDAD_ID, CURSO_CARGA_ID, EVALUACION_ID,"+
				" NOMBRE, VALOR, TO_CHAR(FECHA,'YYYY/MM/DD') AS FECHA, ACTIVIDAD_E42, AGENDADA_E42, ENVIAR"+
				" FROM ENOC.CARGA_GRUPO_ACTIVIDAD"+ 
				" WHERE CURSO_CARGA_ID = ?" +
				" ORDER BY FECHA, ACTIVIDAD_ID";
			lista = enocJdbc.query(comando, new CargaGrupoActividadMapper(), cursoCarga);
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoActividadDao|getListAllCargaEvaluacion|:"+ex);
		}
		return lista;
	}
	
	public boolean evalTieneActiv( String cursoCargaId, String evaluacionId ) {
		
		boolean ok = false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA_GRUPO_ACTIVIDAD"+ 
				" WHERE CURSO_CARGA_ID = ?" +
				" AND EVALUACION_ID = ?";
			if (enocJdbc.queryForObject(comando, Integer.class, cursoCargaId, evaluacionId) >= 1) {
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoActividadDao|evalTieneActiv|:"+ex);
		}
		return ok;
	}
	
	public String getNombre( String actividadId ) {
		
		String nombre = "x";		
		try{
			String comando = "SELECT NOMBRE FROM ENOC.CARGA_GRUPO_ACTIVIDAD WHERE ACTIVIDAD_ID = "+actividadId;
			nombre = enocJdbc.queryForObject(comando,String.class);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoActividadDao|getNombre|:"+ex);
		}
		return nombre;
	}
	
	public String getEvaluacion( String actividadId ) {
		
		String evaluacion = "0";		
		try{
			String comando = "SELECT EVALUACION_ID FROM ENOC.CARGA_GRUPO_ACTIVIDAD WHERE ACTIVIDAD_ID = "+actividadId;
			evaluacion 	= enocJdbc.queryForObject(comando,String.class);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoActividadDao|getEvaluacion|:"+ex);
		}
		return evaluacion;
	}
	
	public HashMap<String, String> getActividadesMes( String cargaId, String orden) {		
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT CURSO_CARGA_ID||'/'||EVALUACION_ID AS LLAVE, COUNT(ACTIVIDAD_ID) AS VALOR" +
					" FROM ENOC.CARGA_GRUPO_ACTIVIDAD WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ?" +
					" GROUP BY EVALUACION_ID, CURSO_CARGA_ID "+orden;
			lista = enocJdbc.query(comando, new aca.MapaMapper(), cargaId);			
			for(aca.Mapa map : lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoActividadDao|getActividadesMes|:"+ex);
		}
		return mapa;		
	}
	
	// Cuenta las actividades agendades por materia
	public HashMap<String, String> mapActividadesAgendadas( String cargaId, String agendada ) {		
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 		 = new ArrayList<aca.Mapa>();		
		try{
			String comando = " SELECT CURSO_CARGA_ID AS LLAVE, COUNT(ACTIVIDAD_ID) AS VALOR"
					+ " FROM ENOC.CARGA_GRUPO_ACTIVIDAD"
					+ " WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ?"
					+ " AND AGENDADA_E42 = ?"					
					+ " GROUP BY CURSO_CARGA_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), cargaId, agendada);
			for(aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoActividadDao|mapActividadesAgendadas|:"+ex);
		}
		return mapa;		
	}
	
	// Cuenta las actividades agendades por materia
	public HashMap<String, String> mapaActividadesPorEvaluacion( String cursoCargaId ) {		
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 		 = new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT EVALUACION_ID AS LLAVE, COUNT(ACTIVIDAD_ID) AS VALOR"
					+ " FROM ENOC.CARGA_GRUPO_ACTIVIDAD"
					+ " WHERE CURSO_CARGA_ID = ?"
					+ " GROUP BY EVALUACION_ID";
			Object[] parametros = new Object[] {cursoCargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoActividadDao|mapaActividadesPorEvaluacion|:"+ex);
		}
		return mapa;		
	}
	
	// Mapa de las actividades que existen en una materia
	public HashMap<String, String> mapaActividadesEnMateria( String cursoCargaId ) {		
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 		 = new ArrayList<aca.Mapa>();		
		try{
			String comando = " SELECT ACTIVIDAD_ID AS LLAVE, ACTIVIDAD_ID AS VALOR"
					+ " FROM ENOC.CARGA_GRUPO_ACTIVIDAD"
					+ " WHERE CURSO_CARGA_ID = ?";
			Object[] parametros = new Object[] {cursoCargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoActividadDao|mapaActividadesEnMateria|:"+ex);
		}
		return mapa;		
	}
	
	public HashMap<String, CargaGrupoActividad> mapaActividadesPorMateria( String cursoCargaId ){
		HashMap<String, CargaGrupoActividad> mapa = new HashMap<String, CargaGrupoActividad>();
		List<CargaGrupoActividad> lista 		 = new ArrayList<CargaGrupoActividad>();		
		try{
			String comando = " SELECT ACTIVIDAD_ID, CURSO_CARGA_ID, EVALUACION_ID,"
				+ " NOMBRE, VALOR, TO_CHAR(FECHA,'DD/MM/YYYY HH24:MI:SS') AS FECHA, ACTIVIDAD_E42, AGENDADA_E42, ENVIAR"
				+ " FROM ENOC.CARGA_GRUPO_ACTIVIDAD"
				+ " WHERE CURSO_CARGA_ID = ?";
			Object[] parametros = new Object[] {cursoCargaId};
			lista = enocJdbc.query(comando, new CargaGrupoActividadMapper(), parametros);
			for(CargaGrupoActividad objeto : lista){
				mapa.put(objeto.getActividadId(), objeto);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoActividadDao|mapaActividadesEnMateria|:"+ex);
		}
		return mapa;		
	}
	
	// Mapa de las actividades que existen en una materia
	public HashMap<String, String> mapaActividadesPorMateria( String cargaId, String carreraId ) {		
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 		 = new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CURSO_CARGA_ID AS LLAVE, COUNT(ACTIVIDAD_ID) AS VALOR"
					+ " FROM ENOC.CARGA_GRUPO_ACTIVIDAD"
					+ " WHERE CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM CARGA_GRUPO WHERE CARGA_ID = ? AND CARRERA_ID = ?)"
					+ " GROUP BY CURSO_CARGA_ID";
			Object[] parametros = new Object[] {cargaId, carreraId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoActividadDao|mapaActividadesPorMateria|:"+ex);
		}
		return mapa;		
	}
	
	public HashMap<String, String> mapaMesesPorCargas(){		
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 		 = new ArrayList<aca.Mapa>();
		try{
			String comando = " SELECT CG.CARGA_ID||TO_CHAR(CGA.FECHA,'MM') AS LLAVE, COUNT(CGA.CURSO_CARGA_ID) AS VALOR" 
				+ " FROM ENOC.CARGA_GRUPO CG, CARGA_GRUPO_ACTIVIDAD CGA" 
				+ " WHERE CGA.CURSO_CARGA_ID = CG.CURSO_CARGA_ID" 
				+ " GROUP BY CG.CARGA_ID,TO_CHAR(CGA.FECHA,'MM')";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoActividadDao|mapaMesesPorCargas|:"+ex);
		}		
		return mapa;	
	}
}