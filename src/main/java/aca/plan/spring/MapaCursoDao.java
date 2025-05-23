// Clase Util para la tabla de Mapa_Plan
package aca.plan.spring;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class MapaCursoDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( MapaCurso curso ){
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.MAPA_CURSO" 
				+ "(PLAN_ID, CURSO_ID, NOMBRE_CURSO, CICLO, CREDITOS, HT, HP, HI, F_CREADA, NOTA_APROBATORIA,"
				+ " ESTADO, TIPOCURSO_ID, UNID, ON_LINE, CURSO_CLAVE, OBLIGATORIO, COMPLETO, HH, HFD, HSS, HAS, AREA_ID, LABORATORIO, HORARIO, SALON, ORDEN )"
				+ " VALUES( ?, ?, ?, "
				+ " TO_NUMBER(?,'99'), "
				+ " TO_NUMBER(?,'99.99'),"
				+ " TO_NUMBER(?,'99'),"
				+ " TO_NUMBER(?,'99'),"
				+ " TO_NUMBER(?,'99'),"
				+ " TO_DATE(now(),'DD/MM/YYYY'),"
				+ " TO_NUMBER(?,'999'),"				
				+ " ? , TO_NUMBER(?,'99'), ?, ?, ?, ?, ?,"
				+ " TO_NUMBER(?,'999'),"
				+ " TO_NUMBER(?,'999'),"
				+ " TO_NUMBER(?,'999'),"
			    + " TO_NUMBER(?,'999'),"
			    + " TO_NUMBER(?,'999'), ?, ?, ?, TO_NUMBER(?,'99'))";				
			Object[] parametros = new Object[] {
				curso.getPlanId(), curso.getCursoId(), curso.getNombreCurso(), curso.getCiclo(), curso.getCreditos(), 
				curso.getHt(), curso.getHp(), curso.getHi(), curso.getNotaAprobatoria(),curso.getEstado(), 
				curso.getTipoCursoId(), curso.getUnid(), curso.getOnLine(), curso.getCursoClave(), curso.getObligatorio(), 
				curso.getCompleto(), curso.getHh(),	curso.getHfd(), curso.getHss(), curso.getHas(), 
				curso.getAreaId(), curso.getLaboratorio(),curso.getHorario(),curso.getSalon(), curso.getOrden()
			}; 			
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg( MapaCurso curso ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.MAPA_CURSO "
					+ " SET PLAN_ID = ?, "
					+ " NOMBRE_CURSO = ?, "
					+ " CICLO = TO_NUMBER(?,'99'), "
					+ " CREDITOS = TO_NUMBER(?,'99.99'), "
					+ " HT = TO_NUMBER(?,'99'), "
					+ " HP = TO_NUMBER(?,'99'), "
					+ " HI = TO_NUMBER(?,'99'), "
					+ " NOTA_APROBATORIA = TO_NUMBER(?,'999'), "
					+ " ESTADO = ?,"
					+ " TIPOCURSO_ID = TO_NUMBER(?,'99'), "
					+ " UNID = ?, "
					+ " ON_LINE = ?, "
					+ " CURSO_NUEVO = ?, "
					+ " CURSO_CLAVE = ?, "
					+ " OBLIGATORIO = ?, "
					+ " COMPLETO = ?, "
					+ " HH = ?, "
					+ " HFD = ?, "
					+ " HSS = ?, "
					+ " HAS = ?, "
					+ " HORARIO = ?, "
					+ " SALON = ?, "
					+ " AREA_ID = TO_NUMBER(?,'999'),"
					+ " LABORATORIO = ?,"
					+ " ORDEN = ?"
					+ " WHERE CURSO_ID = ? ";			
			Object[] parametros = new Object[] {
				curso.getPlanId(), curso.getNombreCurso(), curso.getCiclo(), curso.getCreditos(), curso.getHt(), curso.getHp(), curso.getHi(), curso.getNotaAprobatoria(),
				curso.getEstado(), curso.getTipoCursoId(), curso.getUnid(), curso.getOnLine(),curso.getCursoNuevo(), curso.getCursoClave(), curso.getObligatorio(), curso.getCompleto(), curso.getHh(),
				curso.getHfd(), curso.getHss(), curso.getHas(), curso.getHorario(), curso.getSalon(), curso.getAreaId(), curso.getLaboratorio(), curso.getOrden(), curso.getCursoId()
			}; 			
 			if (enocJdbc.update(comando,parametros)==1){
 				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|updateReg|:"+ex);		
		}
		
		return ok;
	}
	
	public boolean updateLaboratorio( String cursoId, String laboratorio ) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.MAPA_CURSO SET LABORATORIO = ? WHERE CURSO_ID = ?";			
			Object[] parametros = new Object[] { laboratorio, cursoId }; 			
 			if (enocJdbc.update(comando,parametros)==1){
 				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|updateLaboratorio|:"+ex);
		}
		
		return ok;
	}
	
	public boolean updateCiclo( String cursoId, String ciclo ) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.MAPA_CURSO SET CICLO = TO_NUMBER(?,'99') WHERE CURSO_ID = ?";			
			Object[] parametros = new Object[] { ciclo, cursoId }; 			
 			if (enocJdbc.update(comando,parametros)==1){
 				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|updateCiclo|:"+ex);
		}
		
		return ok;
	}
	
	public boolean updateHorario( String cursoId, String horario ) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.MAPA_CURSO SET HORARIO = ? WHERE CURSO_ID = ?";			
			Object[] parametros = new Object[] { horario, cursoId }; 			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|updateHorario|:"+ex);
		}
		
		return ok;
	}
	
	public boolean updateSalon( String cursoId, String salon ) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.MAPA_CURSO SET SALON = ? WHERE CURSO_ID = ?";			
			Object[] parametros = new Object[] { salon, cursoId }; 			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|updateSalon|:"+ex);
		}
		
		return ok;
	}
	
	public boolean deleteReg( String cursoId ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.MAPA_CURSO WHERE CURSO_ID = ? ";
			Object[] parametros = new Object[] {
				cursoId
			};
 			
 			if (enocJdbc.update(comando,parametros)==1){
 				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|deleteReg|:"+ex);			
		}
		
		return ok;
	}
	
	public MapaCurso mapeaRegId(  String cursoId) {
		MapaCurso objeto = new MapaCurso();
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.MAPA_CURSO WHERE CURSO_ID = ?";		
			
			if (enocJdbc.queryForObject(comando,Integer.class,cursoId)>=1){
				comando = "SELECT PLAN_ID, CURSO_ID, NOMBRE_CURSO, "
						+ " CICLO, CREDITOS, COALESCE(HT,0) AS HT, COALESCE(HP,0) AS HP, COALESCE(HI,0) AS HI, TO_CHAR(F_CREADA,'DD/MM/YYYY') F_CREADA, "
						+ " NOTA_APROBATORIA, ESTADO, TIPOCURSO_ID, UNID, ON_LINE, CURSO_NUEVO, CURSO_CLAVE, OBLIGATORIO, COMPLETO, HH, HFD, HSS, HAS, HORARIO, AREA_ID, LABORATORIO, HORARIO, SALON, ORDEN "
						+ " FROM ENOC.MAPA_CURSO WHERE CURSO_ID = ?";				
				objeto = enocJdbc.queryForObject(comando, new MapaCursoMapper(), cursoId);				
			}						
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|mapeaRegId|:"+ex);
		}
		
		return objeto;
	}
	
	public boolean existeReg( String cursoId) {
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.MAPA_CURSO WHERE CURSO_ID = ? ";			
			Object[] parametros = new Object[] {cursoId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoReg(String planId) {
		String maximo	= planId+"SUB-001";		
		try{
			String comando = "SELECT COALESCE(MAX(SUBSTR(CURSO_ID,13,3)),'001') AS MAXIMO FROM ENOC.MAPA_CURSO WHERE PLAN_ID = ?";
			Object[] parametros = new Object[] {planId};
			maximo = enocJdbc.queryForObject(comando, String.class, parametros);
			maximo = String.valueOf(Integer.parseInt(maximo)+1);
			if (maximo.length()==1) maximo = "00"+maximo;
			if (maximo.length()==2) maximo = "0"+maximo;			
			maximo = planId+"SUB-"+maximo;
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|maximoReg|:"+ex);
		}		
		return maximo;
	}
	
	// Determina si el plan tiene componentes
	public boolean esPlanDeComponentes( String planId) {
		boolean 	ok 	= false;

		try{
			String comando = "SELECT CURSO_ID FROM ENOC.MAPA_CURSO WHERE PLAN_ID = ? AND TIPOCURSO_ID = 3"; 
			
			Object[] parametros = new Object[] {planId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}

		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|esPlanDeComponentes|:"+ex);
		}
		
		return ok;
	}
	
	public int cuentaMaterias( String planId, String tipoCurso) {
		int nMaterias	= 0;		
		
		try{
			String comando = "SELECT COUNT(*) AS MATERIAS FROM ENOC.MAPA_CURSO "
					+ " WHERE PLAN_ID = ? AND TIPOCURSO_ID = TO_NUMBER(?,'99')";	
			
			Object[] parametros = new Object[] {planId,tipoCurso};
			nMaterias = enocJdbc.queryForObject(comando, Integer.class, parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|cuantaMaterias|:"+ex);
		}
		
		return nMaterias;
	}
	
	public int totalMateriasObligatorias( String planId, String obligatorio) {
		int nMaterias			= 0;		
		
		try{
			String comando = "SELECT COUNT(*) AS MATERIAS FROM ENOC.MAPA_CURSO "
					+ " WHERE PLAN_ID = ? AND OBLIGATORIO = ?";
			
			Object[] parametros = new Object[] {planId,obligatorio};
			nMaterias = enocJdbc.queryForObject(comando, Integer.class, parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|totalMateriasObligatorias|:"+ex);
		}
		
		return nMaterias;
	}
	
	public int totalMateriasObligatoriasDos( String planId, String obligatorio) {
		int nMaterias			= 0;		
		try{
			String comando = "SELECT COUNT(*) AS MATERIAS FROM ENOC.MAPA_CURSO "
					+ " WHERE PLAN_ID = ? AND OBLIGATORIO = ? AND CREDITOS != 0";			
			Object[] parametros = new Object[] {planId,obligatorio};
			nMaterias = enocJdbc.queryForObject(comando, Integer.class, parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|totalMateriasObligatorias|:"+ex);
		}
		
		return nMaterias;
	}

	public String getMateria( String planId, String cursoId ) {
		String nombre = "0000000"; 
		try{
			String comando = "SELECT NOMBRE_CURSO FROM ENOC.MAPA_CURSO WHERE PLAN_ID = ? AND CURSO_ID = ?"; 
			
			Object[] parametros = new Object[] {planId,cursoId};
			nombre = enocJdbc.queryForObject(comando, String.class, parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|getMateria|:"+ex);
		}
		
		return nombre;
	}
	
	public String getCursoNuevo( String cursoId ) {
		String cursoNuevo = "0"; 
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.MAPA_CURSO WHERE CURSO_ID = ?";
			Object[] parametros = new Object[] {cursoId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				comando = "SELECT CURSO_NUEVO FROM ENOC.MAPA_CURSO WHERE CURSO_ID = ?";
				cursoNuevo = enocJdbc.queryForObject(comando, String.class, parametros);
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|getCursoNuevo|:"+ex);
		}
		
		return cursoNuevo;
	}
	
	public String getMateria( String cursoId ) {
		String nombre = "0000000"; 
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.MAPA_CURSO WHERE CURSO_ID = ?";
			Object[] parametros = new Object[] {cursoId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				comando = "SELECT NOMBRE_CURSO FROM ENOC.MAPA_CURSO WHERE CURSO_ID = ?";			
				nombre = enocJdbc.queryForObject(comando, String.class, parametros);			
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|getMateria|:"+ex);
		}		
		return nombre;
	}
	
	public int getNumCredPlanObligatorio( String planId, String obligatorio ) {
		int nCred = 0; 
		try{
			String comando = "SELECT SUM(CREDITOS) AS CREDITOS FROM ENOC.MAPA_CURSO WHERE PLAN_ID = ? AND OBLIGATORIO = ? ";
			
			Object[] parametros = new Object[] {planId,obligatorio};
			nCred = enocJdbc.queryForObject(comando, Integer.class, parametros);

		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|getNumCredPlanObligatorio|:"+ex);
		}
		
		return nCred;
	}
	
	public int getNumCredPlan( String planId ) {
		int nCred = 0; 
		try{
			String comando = "SELECT SUM(CREDITOS) AS CREDITOS FROM ENOC.MAPA_CURSO WHERE PLAN_ID = ?"; 
			
			Object[] parametros = new Object[] {planId};
			nCred = enocJdbc.queryForObject(comando, Integer.class, parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|getNumCredPlan|:"+ex);
		}
		
		return nCred;
	}
	
	public int getNumMateriasCiclo( String planId, int ciclo, int tipo ) {
		int numMat		= 0; 
		try{				
			String comando = "SELECT COUNT(CURSO_ID) AS NUMMAT FROM ENOC.MAPA_CURSO "
					+ " WHERE PLAN_ID = ? AND CICLO = ? AND TIPOCURSO_ID = ? ";
			
			Object[] parametros = new Object[] {planId,ciclo,tipo};
			numMat = enocJdbc.queryForObject(comando, Integer.class, parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|getNumMateriasCiclo|:"+ex);
		}
		
		return numMat;
	}
	
	public String getTipoCurso( String cursoId ) {
		String tipo = "0"; 
		try{
			String comando = "SELECT COUNT(TIPOCURSO_ID) FROM ENOC.MAPA_CURSO WHERE CURSO_ID = ?";
			Object[] parametros = new Object[] {cursoId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				comando = "SELECT TIPOCURSO_ID FROM ENOC.MAPA_CURSO WHERE CURSO_ID = ?";				
				tipo = enocJdbc.queryForObject(comando, String.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|getTipoCurso|:"+ex);
		}
		
		return tipo;
	}
	
	public int getCiclo( String cursoId ) {
		int ciclo = 0; 
		try{
			String comando = "SELECT COUNT(TIPOCURSO_ID) FROM ENOC.MAPA_CURSO WHERE CURSO_ID = ?";
			Object[] parametros = new Object[] {cursoId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				comando = "SELECT CICLO FROM ENOC.MAPA_CURSO WHERE CURSO_ID = ?";			
				ciclo = enocJdbc.queryForObject(comando, Integer.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|getCiclo|:"+ex);
		}
		
		return ciclo;
	}
	
	public float getCreditosObliOpta( String planId, String ciclo) {
		float creditos = 0; 
		try{
			String comando = "SELECT SUM(CREDITOS) NUM_CREDITOS "
					+ " FROM ENOC.MAPA_CURSO WHERE TIPOCURSO_ID IN('1','5','8') AND PLAN_ID = ? AND CICLO = ?";		
		
			Object[] parametros = new Object[] {planId,ciclo};
			creditos = enocJdbc.queryForObject(comando, Integer.class, parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|getCreditosObliOpta|:"+ex);
		}
		
		return creditos;
	}
	
	public float getCreditosCurso( String cursoId ) {
		float creditos = 0; 
		try{
			String comando = "SELECT CREDITOS FROM ENOC.MAPA_CURSO WHERE CURSO_ID = ?";
			
			Object[] parametros = new Object[] {cursoId};
			creditos = enocJdbc.queryForObject(comando, Integer.class, parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|getCreditosCurso|:"+ex);
		}
		
		return creditos;
	}
	
	public float getCreditosElectivos( String planId, String ciclo) {
		float creditos = 0; 
		try{
			String comando = "SELECT SUM(CREDITOS) NUM_CREDITOS "
					+ " FROM ENOC.MAPA_CURSO WHERE TIPOCURSO_ID IN('2','7') "
					+ " AND PLAN_ID = ? AND CICLO = ?";			
			
			Object[] parametros = new Object[] {planId,ciclo};
			creditos = enocJdbc.queryForObject(comando, Float.class, parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|getCreditosElectivos|:"+ex);
		}
		
		return creditos;
	}
	
	public String getFS( String cursoId ) {
		int frecuncia = 0; 
		try{
			String comando = "SELECT SUM(HT+HP) FROM ENOC.MAPA_CURSO WHERE CURSO_ID = ?"; 
			
			Object[] parametros = new Object[] {cursoId};
			frecuncia = enocJdbc.queryForObject(comando, Integer.class, parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|getNumCredPlan|:"+ex);
		}
		
		return String.valueOf(frecuncia);
	}
	
	public String getHH( String cursoId ) {
		int frecuncia = 0; 
		try{
			String comando = "SELECT HH FROM ENOC.MAPA_CURSO WHERE CURSO_ID = ?"; 
			
			Object[] parametros = new Object[] {cursoId};
			frecuncia = enocJdbc.queryForObject(comando, Integer.class, parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|getHH|:"+ex);
		}
		
		return String.valueOf(frecuncia);
	}
	
	public int getCrElec( String planId) {
		int creditos = 0; 
		try{
			String comando = "SELECT SUM(CREDITOS) NUM_CREDITOS "
					+ " FROM ENOC.MAPA_CURSO WHERE TIPOCURSO_ID IN('1','5')"
					+ " AND PLAN_ID = ? ";
			
			Object[] parametros = new Object[] {planId};
			creditos = enocJdbc.queryForObject(comando, Integer.class, parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|getCrElec|:"+ex);
		}
		
		return creditos;
	}
	
	public int getCrObli( String planId) {
		int creditos = 0; 
		try{
			String comando = "SELECT SUM(CREDITOS) NUM_CREDITOS "
					+ " FROM ENOC.MAPA_CURSO WHERE TIPOCURSO_ID IN('2','7') "
					+ " AND PLAN_ID = ?";	
			
			Object[] parametros = new Object[] {planId};
			creditos = enocJdbc.queryForObject(comando, Integer.class, parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|getCrObli|:"+ex);
		}
		
		return creditos;
	}
	
	public String getPlanId( String cursoId ){
		String plan	= "X"; 
		try{
			String comando = "SELECT PLAN_ID FROM ENOC.MAPA_CURSO WHERE CURSO_ID = ?";			
			Object[] parametros = new Object[] {cursoId};
			plan = enocJdbc.queryForObject(comando, String.class, parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|getPlanId|:"+ex);
		}
		
		return plan;
	}	
		
	public List<MapaCurso> getListAll( String orden ) {
		List<MapaCurso> lista	= new ArrayList<MapaCurso>();		
		try{
			String comando = "SELECT PLAN_ID, CURSO_ID, NOMBRE_CURSO, CICLO, CREDITOS, COALESCE(HT,0) AS HT, COALESCE(HP,0) AS HP, COALESCE(HI,0) AS HI,"
					+ " TO_CHAR(F_CREADA,'DD/MM/YYYY') F_CREADA, NOTA_APROBATORIA, ESTADO, TIPOCURSO_ID,"
					+ " UNID, ON_LINE, CURSO_NUEVO, CURSO_CLAVE, OBLIGATORIO, COMPLETO, HH, HFD, HSS, HAS, AREA_ID, LABORATORIO, HORARIO, SALON, ORDEN"
					+ " FROM ENOC.MAPA_CURSO "+orden;			
			lista = enocJdbc.query(comando, new MapaCursoMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|getListAll|:"+ex);
		}		
		return lista;
	}

	public List<MapaCurso> getListaCursos( String orden ) {
		List<MapaCurso> lista	= new ArrayList<MapaCurso>();		
		try{
			String comando = "SELECT * FROM (SELECT PLAN_ID, CURSO_ID, NOMBRE_CURSO, CICLO, CREDITOS,"
					+ " COALESCE(HT,0) AS HT, COALESCE(HP,0) AS HP, COALESCE(HI,0) AS HI, TO_CHAR(F_CREADA,'DD/MM/YYYY') F_CREADA, NOTA_APROBATORIA, ESTADO, TIPOCURSO_ID,"
					+ " UNID, ON_LINE, CURSO_NUEVO, CURSO_CLAVE, OBLIGATORIO, COMPLETO, HH, HFD, HSS, HAS, AREA_ID, LABORATORIO, HORARIO, SALON, ORDEN, ROW_NUMBER() OVER (PARTITION BY CURSO_CLAVE ORDER BY CURSO_ID) AS rn"
					+ " FROM ENOC.MAPA_CURSO) WHERE rn = 1"+orden;			
			lista = enocJdbc.query(comando, new MapaCursoMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|getListaCursos|:"+ex);
		}		
		return lista;
	}
	
	public List<MapaCurso> getLista( String planId, String orden ) {
		List<MapaCurso> lista	= new ArrayList<MapaCurso>();		
		try{
			String comando = "SELECT PLAN_ID, CURSO_ID, NOMBRE_CURSO, CICLO,"
					+ " CREDITOS, COALESCE(HT,0) AS HT, COALESCE(HP,0) AS HP, COALESCE(HI,0) AS HI,"
					+ " TO_CHAR(F_CREADA,'DD/MM/YYYY') F_CREADA,"
					+ " NOTA_APROBATORIA, ESTADO, TIPOCURSO_ID,"
					+ " UNID, ON_LINE, CURSO_NUEVO, CURSO_CLAVE, OBLIGATORIO, COMPLETO, HH, HFD, HSS, HAS, AREA_ID, LABORATORIO, HORARIO, SALON, ORDEN"
					+ " FROM ENOC.MAPA_CURSO "
					+ " WHERE PLAN_ID = ? "+ orden;
			Object[] parametros = new Object[] {planId};	
			lista = enocJdbc.query(comando, new MapaCursoMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|getLista|:"+ex);
		}
		
		return lista;
	}
	
	public List<MapaCurso> lisPorPlanyCarga( String planId, String cargaId, String orden ) {
		List<MapaCurso> lista	= new ArrayList<MapaCurso>();		
		try{
			String comando = "SELECT PLAN_ID, CURSO_ID, NOMBRE_CURSO, CICLO,"
					+ " CREDITOS, COALESCE(HT,0) AS HT, COALESCE(HP,0) AS HP, COALESCE(HI,0) AS HI,"
					+ " TO_CHAR(F_CREADA,'DD/MM/YYYY') F_CREADA,"
					+ " NOTA_APROBATORIA, ESTADO, TIPOCURSO_ID,"
					+ " UNID, ON_LINE, CURSO_NUEVO, CURSO_CLAVE, OBLIGATORIO, COMPLETO, HH, HFD, HSS, HAS, AREA_ID, LABORATORIO, HORARIO, SALON, ORDEN"
					+ " FROM ENOC.MAPA_CURSO "
					+ " WHERE PLAN_ID = ? "
					+ " AND CURSO_ID IN (SELECT DISTINCT(CURSO_ID) FROM CARGA_GRUPO_CURSO WHERE CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM CARGA_GRUPO WHERE CARGA_ID = ?)) "+ orden;
			Object[] parametros = new Object[] {planId, cargaId};	
			lista = enocJdbc.query(comando, new MapaCursoMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|lisPorPlanyCarga|:"+ex);
		}
		
		return lista;
	}

	public List<MapaCurso> getListaMateriasRezagadas( String planId, String codigoPersonal, String tipos, String ciclo, String orden ) {
		List<MapaCurso> lista	= new ArrayList<MapaCurso>();		
		try{
			String comando = "SELECT PLAN_ID, CURSO_ID, NOMBRE_CURSO, CICLO,CREDITOS, COALESCE(HT,0) AS HT, COALESCE(HP,0) AS HP, COALESCE(HI,0) AS HI,TO_CHAR(F_CREADA,'DD/MM/YYYY') F_CREADA,"
					+ " NOTA_APROBATORIA, ESTADO, TIPOCURSO_ID,UNID, ON_LINE, CURSO_NUEVO, CURSO_CLAVE, OBLIGATORIO, COMPLETO, HH, HFD, HSS, HAS, AREA_ID, LABORATORIO, HORARIO, SALON, ORDEN"
					+ " FROM ENOC.MAPA_CURSO"
					+ " WHERE PLAN_ID = ?"
					+ " AND CICLO <= TO_NUMBER(?,'99')"
					+ " AND CURSO_ID NOT IN(SELECT CURSO_ID FROM ALUMNO_CURSO WHERE CODIGO_PERSONAL = ? AND TIPOCAL_ID IN("+tipos+")) " + orden;			
			Object[] parametros = new Object[] {planId, ciclo, codigoPersonal};	
			lista = enocJdbc.query(comando, new MapaCursoMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|getListaMateriasRezagadas|:"+ex);
		}
		
		return lista;
	}
	
	public List<MapaCurso> getMateriasReque( String planId, String cicloId, String orden ) {
		List<MapaCurso> lista	= new ArrayList<MapaCurso>();		
		try{
			String comando = "SELECT PLAN_ID, CURSO_ID, NOMBRE_CURSO, CICLO, "
					+ " CREDITOS, COALESCE(HT,0) AS HT, COALESCE(HP,0) AS HP, COALESCE(HI,0) AS HI,"
					+ " TO_CHAR(F_CREADA,'DD/MM/YYYY') F_CREADA,"
					+ " NOTA_APROBATORIA, ESTADO, TIPOCURSO_ID,"
					+ " UNID, ON_LINE, CURSO_NUEVO, CURSO_CLAVE, OBLIGATORIO, COMPLETO, HH, HFD, HSS, HAS, AREA_ID, LABORATORIO, HORARIO, SALON, ORDEN"
					+ " FROM ENOC.MAPA_CURSO WHERE PLAN_ID = ?"
					+ " AND CICLO = ? " + orden;			
			Object[] parametros = new Object[] {planId,cicloId};	
			lista = enocJdbc.query(comando, new MapaCursoMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|getMateriasReque|:"+ex);
		}
			
		return lista;
	}
	
	public List<MapaCurso> getMatCompleto( String planId, String orden ) {
		List<MapaCurso> lisCurso	= new ArrayList<MapaCurso>();
		
		try{
			String comando = "SELECT PLAN_ID, CURSO_ID, NOMBRE_CURSO, CICLO, " +
					"CREDITOS, COALESCE(HT,0) AS HT, COALESCE(HP,0) AS HP, COALESCE(HI,0) AS HI, "+
					"TO_CHAR(F_CREADA,'DD/MM/YYYY') F_CREADA, "+
					"NOTA_APROBATORIA, ESTADO, TIPOCURSO_ID, " +
					"UNID, ON_LINE, CURSO_NUEVO, CURSO_CLAVE, OBLIGATORIO, COMPLETO, HH, HFD, HSS, HAS, AREA_ID, LABORATORIO, HORARIO, SALON, ORDEN " +
					"FROM ENOC.MAPA_CURSO WHERE COMPLETO = 'S' "+ orden;
			
			lisCurso = enocJdbc.query(comando, new MapaCursoMapper());
					 
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|getMatCompleto|:"+ex);
		}
			
		return lisCurso;
	}
	
	public List<MapaCurso> getMateriasElegibles( String planId, String tipoCurso, String orden ) {
		List<MapaCurso> lisCurso	= new ArrayList<MapaCurso>();
		
		try{
			String comando = "SELECT PLAN_ID, CURSO_ID, NOMBRE_CURSO, CICLO, " +
					" CREDITOS, COALESCE(HT,0) AS HT, COALESCE(HP,0) AS HP, COALESCE(HI,0) AS HI, "+
					" TO_CHAR(F_CREADA,'DD/MM/YYYY') F_CREADA,"+
					" NOTA_APROBATORIA, ESTADO, TIPOCURSO_ID," +
					" UNID, ON_LINE, CURSO_NUEVO, CURSO_CLAVE, OBLIGATORIO, COMPLETO, HH, HFD, HSS, HAS, AREA_ID, LABORATORIO, HORARIO, SALON, ORDEN" +
					" FROM ENOC.MAPA_CURSO" +
					" WHERE PLAN_ID = ? " +
					" AND TIPOCURSO_ID NOT IN ("+tipoCurso+") "+ orden; 
			
			Object[] parametros = new Object[] {planId};	
			lisCurso = enocJdbc.query(comando, new MapaCursoMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|getMateriasElegibles|:"+ex);
		}
		
		return lisCurso;
	}
	
	public List<MapaCurso> getMateriasPlanDeEstudio(String planId, String tipoCurso, String orden ) {
		List<MapaCurso> lisCurso	= new ArrayList<MapaCurso>();
		
		try{
			String comando = "SELECT PLAN_ID, CURSO_ID, NOMBRE_CURSO, CICLO, " +
					" CREDITOS, COALESCE(HT,0) AS HT, COALESCE(HP,0) AS HP, COALESCE(HI,0) AS HI, "+
					" TO_CHAR(F_CREADA,'DD/MM/YYYY') F_CREADA,"+
					" NOTA_APROBATORIA, ESTADO, TIPOCURSO_ID," +
					" UNID, ON_LINE, CURSO_NUEVO, CURSO_CLAVE, OBLIGATORIO, COMPLETO, HH, HFD, HSS, HAS, AREA_ID, LABORATORIO, HORARIO, SALON, ORDEN" +
					" FROM ENOC.MAPA_CURSO" +
					" WHERE PLAN_ID = ? " +
					" AND TIPOCURSO_ID IN ("+tipoCurso+") "+ orden; 
			
			Object[] parametros = new Object[] {planId};	
			lisCurso = enocJdbc.query(comando, new MapaCursoMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|getMateriasPlanDeEstudio|:"+ex);
		}
		
		return lisCurso;
	}
	
	public TreeMap<String,MapaCurso> getTree( String planId, String orden ) {
		TreeMap<String, MapaCurso> treeCurso	= new TreeMap<String,MapaCurso>();
		List<MapaCurso> lista	= new ArrayList<MapaCurso>();		
		try{
			String comando = "SELECT PLAN_ID, CURSO_ID, NOMBRE_CURSO, CICLO," +
					" CREDITOS, COALESCE(HT,0) AS HT, COALESCE(HP,0) AS HP, COALESCE(HI,0) AS HI, "+
					" TO_CHAR(F_CREADA,'DD/MM/YYYY') F_CREADA,"+
					" NOTA_APROBATORIA, ESTADO, TIPOCURSO_ID," +
					" UNID, ON_LINE, CURSO_NUEVO, CURSO_CLAVE, OBLIGATORIO, COMPLETO, HH, HFD, HSS, HAS, AREA_ID, LABORATORIO, HORARIO, SALON, ORDEN " +
					" FROM ENOC.MAPA_CURSO WHERE PLAN_ID = ? "+ orden;			
			lista = enocJdbc.query(comando,new MapaCursoMapper(),planId);
			for(MapaCurso mapa : lista){				
				treeCurso.put(mapa.getCursoId(), mapa);
			}		
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|getTree|:"+ex);
		}
		
		return treeCurso;
	}
	
	public TreeMap<String,MapaCurso> getTree( String planId, String tipo, String orden ) {
		TreeMap<String, MapaCurso> treeCurso	= new TreeMap<String,MapaCurso>();
		List<MapaCurso> lista	= new ArrayList<MapaCurso>();		
		
		try{
			String comando = " SELECT PLAN_ID, CURSO_ID, NOMBRE_CURSO, CICLO," +
					" CREDITOS, COALESCE(HT,0) AS HT, COALESCE(HP,0) AS HP, COALESCE(HI,0) AS HI, "+
					" TO_CHAR(F_CREADA,'DD/MM/YYYY') F_CREADA,"+
					" NOTA_APROBATORIA, ESTADO, TIPOCURSO_ID," +
					" UNID, ON_LINE, CURSO_NUEVO, CURSO_CLAVE, OBLIGATORIO, COMPLETO, HH, HFD, HSS, HAS, AREA_ID, LABORATORIO, HORARIO, SALON, ORDEN " +
					" FROM ENOC.MAPA_CURSO WHERE PLAN_ID = ? " + 
					" AND TIPOCURSO_ID  IN ('"+tipo+"')"+ orden;
			
			lista = enocJdbc.query(comando,new MapaCursoMapper(),planId);
			for(MapaCurso mapa : lista){				
				treeCurso.put(mapa.getCursoId(), mapa);
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|getTree|:"+ex);
		}
		
		return treeCurso;
	}
	
	public List<MapaCurso> getListCarrera( String carreraId, String orden ) {
		List<MapaCurso> lisCurso	= new ArrayList<MapaCurso>();
		
		try{
			String s_comando = "SELECT PLAN_ID, CURSO_ID, NOMBRE_CURSO, CICLO, "+
				"CREDITOS, COALESCE(HT,0) AS HT, COALESCE(HP,0) AS HP, COALESCE(HI,0) AS HI, "+
				"F_CREADA, NOTA_APROBATORIA, ESTADO, TIPOCURSO_ID, " +
				"UNID, ON_LINE, CURSO_NUEVO, CURSO_CLAVE, OBLIGATORIO, COMPLETO, HH, HFD, HSS, HAS, AREA_ID, LABORATORIO, HORARIO, SALON, ORDEN "+
				"FROM ENOC.MAPA_CURSO WHERE PLAN_ID IN "+ 
					"(SELECT PLAN_ID FROM ENOC.MAPA_PLAN WHERE CARRERA_ID = ?) "+ 
				orden;
			
			lisCurso = enocJdbc.query(s_comando, new MapaCursoMapper(),carreraId);
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|getListCarrera|:"+ex);
		}
		
		return lisCurso;
	}
	
	public List<MapaCurso> getListPlan( String planId, String orden ) {
		List<MapaCurso> lisCurso	= new ArrayList<MapaCurso>();
		
		try{
			String s_comando = "SELECT PLAN_ID, CURSO_ID, NOMBRE_CURSO, CICLO, "+
				"CREDITOS, COALESCE(HT,0) AS HT, COALESCE(HP,0) AS HP, COALESCE(HI,0) AS HI, "+
				"F_CREADA, NOTA_APROBATORIA, ESTADO, TIPOCURSO_ID, " +
				"UNID, ON_LINE, CURSO_NUEVO, CURSO_CLAVE, OBLIGATORIO, COMPLETO, HH, HFD, HSS, HAS, AREA_ID, LABORATORIO, HORARIO, SALON, ORDEN "+
				"FROM ENOC.MAPA_CURSO WHERE PLAN_ID = ? "+ orden; 
			
			lisCurso = enocJdbc.query(s_comando, new MapaCursoMapper(),planId);
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|getListPlan|:"+ex);
		}
		
		return lisCurso;
	}
	
	public List<MapaCurso> getListSemestre( String planId, String ciclo, String orden ) {
		List<MapaCurso> lisCurso	= new ArrayList<MapaCurso>();
	
		try{
			String s_comando = "SELECT PLAN_ID, CURSO_ID, NOMBRE_CURSO, CICLO, "+
				"CREDITOS, COALESCE(HT,0) AS HT, COALESCE(HP,0) AS HP, COALESCE(HI,0) AS HI, "+
				"F_CREADA, NOTA_APROBATORIA, ESTADO, TIPOCURSO_ID, " +
				"UNID, ON_LINE, CURSO_NUEVO, CURSO_CLAVE, OBLIGATORIO, COMPLETO, HH, HFD, HSS, HAS, AREA_ID, LABORATORIO, HORARIO, SALON, ORDEN "+
				"FROM ENOC.MAPA_CURSO WHERE PLAN_ID = ? AND CICLO = ? "+ orden; 
			
			Object[] parametros = new Object[] {
				planId,ciclo	
			};
			
			lisCurso = enocJdbc.query(s_comando, new MapaCursoMapper(),parametros);
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|getListSemestre|:"+ex);
		}
	
		return lisCurso;
	}
	
	public List<MapaCurso> getListConvalida( String planId, String codigoPersonal, String orden ) {
		List<MapaCurso> lisCurso	= new ArrayList<MapaCurso>();
		
		try{				
			String comando = " SELECT PLAN_ID, CURSO_ID, NOMBRE_CURSO, CICLO,"+
					" CREDITOS, COALESCE(HT,0) AS HT, COALESCE(HP,0) AS HP, COALESCE(HI,0) AS HI, "+
					" F_CREADA, NOTA_APROBATORIA, ESTADO, TIPOCURSO_ID," +
					" UNID, ON_LINE, CURSO_NUEVO, CURSO_CLAVE, OBLIGATORIO, COMPLETO, HH, HFD, HSS, HAS, AREA_ID, LABORATORIO, HORARIO, SALON, ORDEN "+
					" FROM ENOC.MAPA_CURSO"+ 
					" WHERE PLAN_ID = ?"+ 
					" AND CURSO_ID NOT IN"+
						"(SELECT CURSO_ID FROM ENOC.ALUMNO_CURSO"+ 
						" WHERE CODIGO_PERSONAL = ?"+
					" AND TIPOCAL_ID IN('1','I'))"+
					" AND CURSO_ID NOT IN"+ 
						"(SELECT CURSO_ID FROM ENOC.CONV_SOLICITUD"+
						" WHERE CODIGO_PERSONAL = ?" +
						" AND PROCESO_ID != 'X'" +
						" AND EST_MAT != 'N') "+orden;
			
			Object[] parametros = new Object[] {
				planId,codigoPersonal,codigoPersonal	
			};
			
			lisCurso = enocJdbc.query(comando, new MapaCursoMapper(),parametros);
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|getListConvalida|:"+ex);
		}
	
		return lisCurso;
	}
	
	public List<MapaCurso> getListCursosPendientes( String codigoPersonal, String planId, String orden ) {
		List<MapaCurso> lisCurso	= new ArrayList<MapaCurso>();
		
		try{				
			String comando = " SELECT PLAN_ID, CURSO_ID, NOMBRE_CURSO, CICLO,"+
					" CREDITOS, COALESCE(HT,0) AS HT, COALESCE(HP,0) AS HP, COALESCE(HI,0) AS HI, "+
					" F_CREADA, NOTA_APROBATORIA, ESTADO, TIPOCURSO_ID," +
					" UNID, ON_LINE, CURSO_NUEVO, CURSO_CLAVE, OBLIGATORIO, COMPLETO, HH, HFD, HSS, HAS, AREA_ID, LABORATORIO, HORARIO, SALON, ORDEN "+
					" FROM ENOC.MAPA_CURSO"+ 
					" WHERE PLAN_ID = ?"+					
					" AND CURSO_ID NOT IN"+
						"(SELECT CURSO_ID FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = ? AND TIPOCAL_ID IN('1','I'))"+
					" AND TIPOCURSO_ID IN(1,2,3,4,5,7,8) "+orden;
			
			Object[] parametros = new Object[] {
				planId,codigoPersonal	
			};
			
			lisCurso = enocJdbc.query(comando, new MapaCursoMapper(),parametros);
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|getListCursosPendientes|:"+ex);
		}

		return lisCurso;
	}
	
	public List<MapaCurso> getListCursosPendientessinRemedial( String codigoPersonal, String planId, String orden ) {
		List<MapaCurso> lisCurso	= new ArrayList<MapaCurso>();
	
		try{				
			String comando = " SELECT PLAN_ID, CURSO_ID, NOMBRE_CURSO, CICLO,"+
					" CREDITOS, COALESCE(HT,0) AS HT, COALESCE(HP,0) AS HP, COALESCE(HI,0) AS HI, "+
					" F_CREADA, NOTA_APROBATORIA, ESTADO, TIPOCURSO_ID," +
					" UNID, ON_LINE, CURSO_NUEVO, CURSO_CLAVE, OBLIGATORIO, COMPLETO, HH, HFD, HSS, HAS, AREA_ID, LABORATORIO, HORARIO, SALON, ORDEN "+
					" FROM ENOC.MAPA_CURSO"+ 
					" WHERE PLAN_ID = ?"+					
					" AND CURSO_ID NOT IN"+
						"(SELECT CURSO_ID FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = ? AND TIPOCAL_ID IN('1','I'))"+
					" AND TIPOCURSO_ID IN(1,2,3,5,7,8) "+orden;
			
			Object[] parametros = new Object[] {
				planId,codigoPersonal
			};
		
			lisCurso = enocJdbc.query(comando, new MapaCursoMapper(),parametros);
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|getListCursosPendientessinRemedial|:"+ex);
		}
	
		return lisCurso;
	}
	
	public List<MapaCurso> getCursosNoInscritos( String codigoPersonal, String planId, String tipoCal, String orden ){
		List<MapaCurso> lisCurso	= new ArrayList<MapaCurso>();	
		try{				
			String comando = " SELECT * FROM ENOC.MAPA_CURSO WHERE PLAN_ID = ?"
					+ " AND CURSO_ID NOT IN ( "
					+ "               SELECT CURSO_ID FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = ? AND TIPOCAL_ID IN ("+tipoCal+") ) "+orden;		
			lisCurso = enocJdbc.query(comando, new MapaCursoMapper(), planId, codigoPersonal);			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|getCursosNoInscritos|:"+ex);
		}
		return lisCurso;
	}
	
	public List<MapaCurso> lisCursosSinRegistrar( String codigoPersonal, String planId, String tipoCal, String orden ){
		List<MapaCurso> lisCurso	= new ArrayList<MapaCurso>();	
		try{				
			String comando = " SELECT * FROM ENOC.MAPA_CURSO WHERE PLAN_ID = ?"
				+ " AND CURSO_ID NOT IN ( SELECT CURSO_ID FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = ? AND TIPOCAL_ID IN ("+tipoCal+") )"
				+ " AND CURSO_ID NOT IN ( SELECT CURSO_ID FROM ENOC.CONV_MATERIA WHERE CONVALIDACION_ID IN (SELECT CONVALIDACION_ID FROM CONV_EVENTO WHERE CODIGO_PERSONAL = ?)) "+orden;		
			lisCurso = enocJdbc.query(comando, new MapaCursoMapper(), planId, codigoPersonal, codigoPersonal);			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|getCursosNoInscritos|:"+ex);
		}
		return lisCurso;
	}

	public List<MapaCurso> lisCursosEnCiclo(String codigoPersonal, String planId, String tipoCal, String ciclo, String orden ) {
		List<MapaCurso> lisCurso	= new ArrayList<MapaCurso>();		
		try{				
			String comando = " SELECT * FROM ENOC.MAPA_CURSO WHERE PLAN_ID = ? AND CICLO = ?"
					+ " AND CURSO_ID NOT IN ( "
					+ "               SELECT CURSO_ID FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = ? AND TIPOCAL_ID IN ("+tipoCal+") ) "+orden;			
			lisCurso = enocJdbc.query(comando, new MapaCursoMapper(), planId, ciclo, codigoPersonal);			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|lisCursosEnCiclo|:"+ex);
		}		
		return lisCurso;
	}
	
	public List<MapaCurso> getMateriasFaltantes( String codigoPersonal, String planId, String orden ) {
		List<MapaCurso> lisMateria	= new ArrayList<MapaCurso>();		
		try{				
			String comando = " SELECT * FROM ENOC.MAPA_CURSO WHERE PLAN_ID = ? AND OBLIGATORIO = 'S' "
					+ " AND CURSO_ID NOT IN (SELECT CURSO_ID FROM ENOC.ALUMNO_CURSO "
					+ " WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ? AND TIPOCAL_ID IN ('1','M','I')) "+orden;		
			lisMateria = enocJdbc.query(comando, new MapaCursoMapper(), planId, codigoPersonal, planId); 			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|getMateriasFaltantes|:"+ex);
		}
		return lisMateria;
	}

	public List<MapaCurso> getCursosEnTraspaso( String codigoPersonal, String planId, String orden ) {
		List<MapaCurso> lisMateria	= new ArrayList<MapaCurso>();		
		try{				
			String comando = "SELECT PLAN_ID, CURSO_ID, NOMBRE_CURSO, CICLO,"
					+ " CREDITOS, COALESCE(HT,0) AS HT, COALESCE(HP,0) AS HP, COALESCE(HI,0) AS HI,"
					+ " F_CREADA, NOTA_APROBATORIA, ESTADO, TIPOCURSO_ID,"
					+ " UNID, ON_LINE, CURSO_NUEVO, CURSO_CLAVE, OBLIGATORIO, COMPLETO, HH, HFD, HSS, HAS, AREA_ID, LABORATORIO, HORARIO, SALON, ORDEN "
					+ " FROM ENOC.MAPA_CURSO"
					+ " WHERE CURSO_CLAVE IN (SELECT SUBJECT_CODE FROM KRDX_TRASPASO WHERE ID_NUMBER IN (SELECT CODIGO_SE FROM ALUM_PERSONAL WHERE CODIGO_PERSONAL = ?)) AND PLAN_ID = ?";
			lisMateria = enocJdbc.query(comando, new MapaCursoMapper(), codigoPersonal, planId); 			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|getCursosEnTraspaso|:"+ex);
		}
		return lisMateria;
	}
	
	public HashMap<String, MapaCurso> getMapaCursos( String planId, String orden) {
		HashMap<String, MapaCurso> lisCurso	= new HashMap<String, MapaCurso>();
		List<MapaCurso> lista	= new ArrayList<MapaCurso>();	
		
		try{				
			String comando = " SELECT PLAN_ID, CURSO_ID, NOMBRE_CURSO, CICLO,"+
					" CREDITOS, COALESCE(HT,0) AS HT, COALESCE(HP,0) AS HP, COALESCE(HI,0) AS HI, "+
					" F_CREADA, NOTA_APROBATORIA, ESTADO, TIPOCURSO_ID," +
					" UNID, ON_LINE, CURSO_NUEVO, CURSO_CLAVE, OBLIGATORIO, COMPLETO, HH, HFD, HSS, HAS, AREA_ID, LABORATORIO, HORARIO, SALON, ORDEN "+
					" FROM ENOC.MAPA_CURSO"+ 
					" WHERE PLAN_ID = ? "+orden;
			
			lista = enocJdbc.query(comando,new MapaCursoMapper(), planId);
			for(MapaCurso mapa : lista){				
				lisCurso.put(mapa.getCursoId(), mapa);
			}	
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|getMapaCursos|:"+ex);
		}

		return lisCurso;
	}
	
	public  HashMap<String, MapaCurso> mapaCursosEnCarga( String cargaId) {
		HashMap<String, MapaCurso> lisCurso	= new HashMap<String, MapaCurso>();
		List<MapaCurso> lista	= new ArrayList<MapaCurso>();		
		try{				
			String comando = " SELECT PLAN_ID, CURSO_ID, NOMBRE_CURSO, CICLO,"
					+ " CREDITOS, COALESCE(HT,0) AS HT, COALESCE(HP,0) AS HP, COALESCE(HI,0) AS HI,"
					+ " F_CREADA, NOTA_APROBATORIA, ESTADO, TIPOCURSO_ID,"
					+ " UNID, ON_LINE, CURSO_NUEVO, CURSO_CLAVE, OBLIGATORIO, COMPLETO, HH, HFD, HSS, HAS, AREA_ID, LABORATORIO, HORARIO, SALON, ORDEN "
					+ " FROM ENOC.MAPA_CURSO"
					+ " WHERE CURSO_ID IN (SELECT CURSO_ID FROM ENOC.CARGA_GRUPO_CURSO WHERE CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = ?))";			
			lista = enocJdbc.query(comando,new MapaCursoMapper(), cargaId);
			for(MapaCurso mapa : lista){				
				lisCurso.put(mapa.getCursoId(), mapa);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|mapaCursosEnCarga|:"+ex);
		}
		
		return lisCurso;
	}
	
	public  HashMap<String, MapaCurso> mapaCursosDelAlumno( String codigoPersonal){
		HashMap<String, MapaCurso> lisCurso	= new HashMap<String, MapaCurso>();
		List<MapaCurso> lista	= new ArrayList<MapaCurso>();	
		
		try{				
			String comando = " SELECT PLAN_ID, CURSO_ID, NOMBRE_CURSO, CICLO,"
					+ " CREDITOS, COALESCE(HT,0) AS HT, COALESCE(HP,0) AS HP, COALESCE(HI,0) AS HI,"
					+ " F_CREADA, NOTA_APROBATORIA, ESTADO, TIPOCURSO_ID,"
					+ " UNID, ON_LINE, CURSO_NUEVO, CURSO_CLAVE, OBLIGATORIO, COMPLETO, HH, HFD, HSS, HAS, AREA_ID, LABORATORIO, HORARIO, SALON, ORDEN "
					+ " FROM ENOC.MAPA_CURSO"
					+ " WHERE CURSO_ID IN (SELECT CURSO_ID FROM ENOC.CARGA_GRUPO_CURSO WHERE CURSO_CARGA_ID IN ( SELECT CURSO_CARGA_ID FROM ENOC.KRDX_CURSO_ACT WHERE CODIGO_PERSONAL = ?))";			
			lista = enocJdbc.query(comando,new MapaCursoMapper(), codigoPersonal);
			for(MapaCurso mapa : lista){				
				lisCurso.put(mapa.getCursoId(), mapa);
			}	
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|mapaCursosEnCarga|:"+ex);
		}
		
		return lisCurso;
	}
	
	public HashMap<String, MapaCurso> getAllMapaCursos( String orden) {
		HashMap<String, MapaCurso> lisCurso	= new HashMap<String, MapaCurso>();
		List<MapaCurso> lista	= new ArrayList<MapaCurso>();	
		
		try{
			String comando = " SELECT PLAN_ID, CURSO_ID, NOMBRE_CURSO, CICLO,"+
					" CREDITOS, COALESCE(HT,0) AS HT, COALESCE(HP,0) AS HP, COALESCE(HI,0) AS HI, "+
					" F_CREADA, NOTA_APROBATORIA, ESTADO, TIPOCURSO_ID," +
					" UNID, ON_LINE, CURSO_NUEVO, CURSO_CLAVE, OBLIGATORIO, COMPLETO, HH, HFD, HSS, HAS, AREA_ID, LABORATORIO, HORARIO, SALON, ORDEN "+
					" FROM ENOC.MAPA_CURSO "+orden;
			
			lista = enocJdbc.query(comando,new MapaCursoMapper());
			for(MapaCurso mapa : lista){				
				lisCurso.put(mapa.getCursoId(), mapa);
			}	
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|getAllMapaCursos|:"+ex);
		}
			
		return lisCurso;
	}
	
	public HashMap<String, MapaCurso> mapaCursosPorMaestro( String codigoPersonal){
		HashMap<String, MapaCurso> lisCurso	= new HashMap<String, MapaCurso>();
		List<MapaCurso> lista	= new ArrayList<MapaCurso>();		
		try{
			String comando = " SELECT PLAN_ID, CURSO_ID, NOMBRE_CURSO, CICLO,"
				+ " CREDITOS, COALESCE(HT,0) AS HT, COALESCE(HP,0) AS HP, COALESCE(HI,0) AS HI, "
				+ " F_CREADA, NOTA_APROBATORIA, ESTADO, TIPOCURSO_ID," 
				+ " UNID, ON_LINE, CURSO_NUEVO, CURSO_CLAVE, OBLIGATORIO, COMPLETO, HH, HFD, HSS, HAS, AREA_ID, LABORATORIO, HORARIO, SALON, ORDEN "
				+ " FROM ENOC.MAPA_CURSO"
				+ " WHERE CURSO_ID IN (SELECT CURSO_ID FROM ENOC.CARGA_GRUPO_CURSO WHERE CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO WHERE CODIGO_PERSONAL = ?))";	
			lista = enocJdbc.query(comando,new MapaCursoMapper(), codigoPersonal);
			for(MapaCurso mapa : lista){				
				lisCurso.put(mapa.getCursoId(), mapa);
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|mapaCursosPorMaestro|:"+ex);
		}			
		return lisCurso;
	}
	
	public HashMap<String, MapaCurso> mapaCursosEnGruposImp(){
		HashMap<String, MapaCurso> lisCurso	= new HashMap<String, MapaCurso>();
		List<MapaCurso> lista	= new ArrayList<MapaCurso>();		
		try{
			String comando = " SELECT PLAN_ID, CURSO_ID, NOMBRE_CURSO, CICLO,"
				+ " CREDITOS, COALESCE(HT,0) AS HT, COALESCE(HP,0) AS HP, COALESCE(HI,0) AS HI, "
				+ " F_CREADA, NOTA_APROBATORIA, ESTADO, TIPOCURSO_ID," 
				+ " UNID, ON_LINE, CURSO_NUEVO, CURSO_CLAVE, OBLIGATORIO, COMPLETO, HH, HFD, HSS, HAS, AREA_ID, LABORATORIO, HORARIO, SALON, ORDEN "
				+ " FROM ENOC.MAPA_CURSO"
				+ " WHERE CURSO_ID IN (SELECT CURSO_ID FROM ENOC.CARGA_GRUPO_IMP)";	
			lista = enocJdbc.query(comando,new MapaCursoMapper());
			for(MapaCurso mapa : lista){				
				lisCurso.put(mapa.getCursoId(), mapa);
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|mapaCursosPorMaestro|:"+ex);
		}			
		return lisCurso;
	}
	
	public HashMap<String, MapaCurso> mapaCursoEnCarga( String cargaId) {
		HashMap<String, MapaCurso> lisCurso	= new HashMap<String, MapaCurso>();
		List<MapaCurso> lista	= new ArrayList<MapaCurso>();	
		
		try{
			String comando = " SELECT PLAN_ID, CURSO_ID, NOMBRE_CURSO, CICLO,"
					+ " CREDITOS, COALESCE(HT,0) AS HT, COALESCE(HP,0) AS HP, COALESCE(HI,0) AS HI,"
					+ " F_CREADA, NOTA_APROBATORIA, ESTADO, TIPOCURSO_ID," 
					+ " UNID, ON_LINE, CURSO_NUEVO, CURSO_CLAVE, OBLIGATORIO, COMPLETO, HH, HFD, HSS, HAS, AREA_ID, LABORATORIO, HORARIO, SALON, ORDEN"
					+ " FROM ENOC.MAPA_CURSO "
					+ " WHERE CURSO_ID IN (SELECT DISTINCT(CURSO_ID) FROM CARGA_GRUPO_CURSO WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ?)";
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new MapaCursoMapper(), parametros);
			for(MapaCurso mapa : lista){				
				lisCurso.put(mapa.getCursoId(), mapa);
			}	
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|mapaCursoEnCarga|:"+ex);
		}
			
		return lisCurso;
	}
	
	public HashMap<String, MapaCurso> mapaCursoEnImportado( String codigoAlumno) {
		HashMap<String, MapaCurso> lisCurso	= new HashMap<String, MapaCurso>();
		List<MapaCurso> lista	= new ArrayList<MapaCurso>();		
		try{
			String comando = " SELECT PLAN_ID, CURSO_ID, NOMBRE_CURSO, CICLO,"
					+ " CREDITOS, COALESCE(HT,0) AS HT, COALESCE(HP,0) AS HP, COALESCE(HI,0) AS HI,"
					+ " F_CREADA, NOTA_APROBATORIA, ESTADO, TIPOCURSO_ID," 
					+ " UNID, ON_LINE, CURSO_NUEVO, CURSO_CLAVE, OBLIGATORIO, COMPLETO, HH, HFD, HSS, HAS, AREA_ID, LABORATORIO, HORARIO, SALON, ORDEN"
					+ " FROM ENOC.MAPA_CURSO "
					+ " WHERE CURSO_ID IN (SELECT CURSO_ID FROM ENOC.KRDX_CURSO_IMP WHERE CODIGO_PERSONAL = ?)";
			Object[] parametros = new Object[] {codigoAlumno};
			lista = enocJdbc.query(comando, new MapaCursoMapper(), parametros);
			for(MapaCurso mapa : lista){				
				lisCurso.put(mapa.getCursoId(), mapa);
			}	
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|mapaCursoEnImportado|:"+ex);
		}
			
		return lisCurso;
	}
	
	public HashMap<String, String> getMapaNombresCursosPorCondicion( String condiciones) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT CURSO_ID AS LLAVE, NOMBRE_CURSO AS VALOR FROM ENOC.MAPA_CURSO "+condiciones;
			
			lista = enocJdbc.query(comando, new aca.MapaMapper());	
			for(aca.Mapa curso : lista){				
				mapa.put(curso.getLlave(), curso.getValor());
			}	
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|getMapaNombresCursosPorCondicion|:"+ex);
		}
		
		return mapa;
	}
	
	public String getNombreCurso( String cursoId ) {
		String sNombre			= "x";		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.MAPA_CURSO WHERE CURSO_ID = UPPER(?)";
			if (enocJdbc.queryForObject(comando, Integer.class, cursoId) == 1) {
				comando = "SELECT NOMBRE_CURSO FROM ENOC.MAPA_CURSO WHERE CURSO_ID = UPPER(?)";
				sNombre = enocJdbc.queryForObject(comando, String.class, cursoId);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|getNombreCurso|:"+ex);
		}
		
		return sNombre;
	}	
	
	public String getPlanCurso( String cursoId ) {
		String sNombre			= "x";		
		try{
			String s_comando = "SELECT PLAN_ID FROM ENOC.MAPA_CURSO WHERE CURSO_ID = UPPER(?)";	
			sNombre = enocJdbc.queryForObject(s_comando, String.class, cursoId);			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|getPlanCurso|:"+ex);
		}		
		return sNombre;
	}	
	
	public boolean getMateriaInscrita( String cursoId ) {
		boolean existe = false;		
		
		try{
			String s_comando = "SELECT CURSO_ID FROM ENOC.ALUMNO_CURSO WHERE CURSO_ID = UPPER(?)";
			
			if (enocJdbc.update(s_comando, cursoId)==1){
				existe = true;
			}		
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|getMateriaInscrita|:"+ex);
		}
		
		return existe;
	}
	
	public boolean traspasarMaterias(String planId ) {
		boolean ok = false;		
		
		try{
			String comando = "INSERT INTO MAPA_CURSO(PLAN_ID, CURSO_ID, NOMBRE_CURSO,CICLO,CREDITOS,HT,HP,F_CREADA,NOTA_APROBATORIA,ESTADO,TIPOCURSO_ID,UNID,ON_LINE,"
					+ " CURSO_NUEVO,CURSO_CLAVE,HI,OBLIGATORIO,COMPLETO,HH,HFD,HSS,HAS,HTOT)" 
					+ " SELECT PLAN_ID,PLAN_ID||TRIM(CLAVE),NOMBRE,CICLO,CREDITOS,0,0,F_CREADA,70,1,1,'N','N',CURSO_ID,TRIM(CLAVE),HEI,'S','-',HFD,HFD,HSS,0,HT "
					+ " FROM MAPA_NUEVO_CURSO WHERE PLAN_ID = ? ";
 			
 			if (enocJdbc.update(comando,new Object[] {planId}) >= 1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|traspasarMaterias|:"+ex);
		}
		
		return ok;
	}
	
	public boolean tieneMaterias(String planId) {
		boolean ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.MAPA_NUEVO_PLAN WHERE PLAN_ID = ? "; 
			
			Object[] parametros = new Object[] {planId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|tieneMaterias|:"+ex);
		}
		
		return ok;
	}
	
	public List<MapaCurso> getListMaterias( String cursoClave ) {
		List<MapaCurso> lista	= new ArrayList<MapaCurso>();		
		try{
			String comando = "SELECT * FROM ENOC.MAPA_CURSO WHERE CURSO_CLAVE = ? ORDER BY PLAN_ID ";			
			lista = enocJdbc.query(comando, new MapaCursoMapper(), cursoClave);			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|getListMaterias|:"+ex);
		}		
		return lista;
	}
	
	public List<String> getNotasxCredtios( String periodoId ) {
		List<String> lista	= new ArrayList<String>();		
		try{
			String comando = "SELECT " +
					" SUBSTR(CURSO_CARGA_ID,1,6) AS CARGA_ID || '%' || C.CARRERA_ID AS CARRERA_ID || '%' || SUM( CASE COALESCE(A.NOTA_EXTRA,0) WHEN 0 THEN COALESCE(A.NOTA,0) ELSE COALESCE(A.NOTA_EXTRA,0) END * B.CREDITOS) AS TOTAL " +
					" FROM ENOC.KRDX_CURSO_ACT A, ENOC.MAPA_CURSO B, ENOC.MAPA_PLAN C" +
					" WHERE SUBSTR(CURSO_CARGA_ID,1,4) = ?" +
					" AND A.CURSO_ID = B.CURSO_ID" +
					" AND C.PLAN_ID = B.PLAN_ID" +
					" AND TIPOCAL_ID IN('1','2','4')" +
					" AND B.TIPOCURSO_ID IN (1,2,7)" +
					" GROUP BY SUBSTR(CURSO_CARGA_ID,1,6), C.CARRERA_ID" +
					" ORDER BY 1,2 ";			
			lista = enocJdbc.queryForList(comando, String.class, periodoId);			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|getNotasxCredtios|:"+ex);
		}
		
		return lista;
	}
	
	public List<String> getTotalCredtios( String periodoId ) {
		List<String> lis	= new ArrayList<String>();
		
		try{
			String comando = "SELECT " +
					" SUBSTR(CURSO_CARGA_ID,1,6) AS CARGA_ID, C.CARRERA_ID, SUM(B.CREDITOS) AS TOTAL  " +
					" FROM ENOC.KRDX_CURSO_ACT A, ENOC.MAPA_CURSO B, ENOC.MAPA_PLAN C" +
					" WHERE SUBSTR(CURSO_CARGA_ID,1,4)= ? " +
					" AND A.CURSO_ID = B.CURSO_ID" +
					" AND C.PLAN_ID = B.PLAN_ID" +
					" AND TIPOCAL_ID IN('1','2','4')" +
					" AND B.TIPOCURSO_ID IN (1,2,7)" +
					" GROUP BY SUBSTR(CURSO_CARGA_ID,1,6), C.CARRERA_ID, C.CARRERA_ID" +
					" ORDER BY 1,2 "; 			
			lis = enocJdbc.queryForList(comando, String.class, periodoId);			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|getTotalCredtios|:"+ex);
		}
		
		return lis;
	}
	
	public List<String> getNotasxCredtiosCarga( String cargaId ) {
		List<String> lis	= new ArrayList<String>();
		
		try{
			String comando = "SELECT"
					+ " SUBSTR(CURSO_CARGA_ID,1,6) AS CARGA_ID || '%' || ENOC.FACULTAD(C.CARRERA_ID) AS FACULTAD || '%' || C.CARRERA_ID AS CARRERA_ID || '%' ||"
					+ " SUM( CASE COALESCE(A.NOTA_EXTRA,0) WHEN 0 THEN COALESCE(A.NOTA,0) ELSE COALESCE(A.NOTA_EXTRA,0) END * B.CREDITOS) AS TOTAL"
					+ " FROM ENOC.KRDX_CURSO_ACT A, ENOC.MAPA_CURSO B, ENOC.MAPA_PLAN C"
					+ " WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ?"
					+ " AND A.CURSO_ID = B.CURSO_ID"
					+ " AND C.PLAN_ID = B.PLAN_ID"
					+ " AND TIPOCAL_ID IN('1','2','4')"
					+ " AND B.TIPOCURSO_ID IN (1,2,7)"
					+ " GROUP BY SUBSTR(CURSO_CARGA_ID,1,6), C.CARRERA_ID"
					+ " ORDER BY 2,3 ";		
			lis = enocJdbc.queryForList(comando, String.class, cargaId);			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|getNotasxCredtiosCarga|:"+ex);
		}
		
		return lis;
	}
	
	public List<String> getTotalCredtiosCarga( String cargaId ) {
		List<String> lis	= new ArrayList<String>();
		
		try{
			String comando = "SELECT " +
					" SUBSTR(CURSO_CARGA_ID,1,6) AS CARGA_ID || '%' || ENOC.FACULTAD(C.CARRERA_ID) AS FACULTAD || '%' || C.CARRERA_ID AS CARRERA_ID || '%' || SUM(B.CREDITOS) AS TOTAL  " +
					" FROM ENOC.KRDX_CURSO_ACT A, ENOC.MAPA_CURSO B, ENOC.MAPA_PLAN C" +
					" WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ? " +
					" AND A.CURSO_ID = B.CURSO_ID" +
					" AND C.PLAN_ID = B.PLAN_ID" +
					" AND TIPOCAL_ID IN('1','2','4')" +
					" AND B.TIPOCURSO_ID IN (1,2,7)" +
					" GROUP BY SUBSTR(CURSO_CARGA_ID,1,6), C.CARRERA_ID, C.CARRERA_ID" +
					" ORDER BY 2,3 "; 			
			lis = enocJdbc.queryForList(comando, String.class, cargaId);			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|getTotalCredtiosCarga|:"+ex);
		}
		
		return lis;
	}
	
	/*
	 * Lista de los ciclos de un plan de estudios
	 * */
	public List<String> lisCiclosPlan(  String planId) {
		List<String> ciclos	= new ArrayList<String>();		
		try{
			String comando = "SELECT DISTINCT(CICLO) AS CICLO FROM ENOC.MAPA_CURSO WHERE PLAN_ID = ? ORDER BY 1";			
			Object[] parametros = new Object[] {planId};
			ciclos = enocJdbc.queryForList(comando, String.class, parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|lisCiclosPlan|:"+ex);
		}

		return ciclos;
	}
	
	public List<MapaCurso> lisCursos(String planId, String ciclo, String orden){
		List<MapaCurso> lista = new ArrayList<MapaCurso>();
		try{					
			String comando = "SELECT PLAN_ID, CURSO_ID, NOMBRE_CURSO, CICLO,"
					+ " CREDITOS, COALESCE(HT,0) AS HT, COALESCE(HP,0) AS HP, COALESCE(HI,0) AS HI,"
					+ " F_CREADA, NOTA_APROBATORIA, ESTADO, TIPOCURSO_ID,"
					+ " UNID, ON_LINE, CURSO_NUEVO, CURSO_CLAVE, OBLIGATORIO, COMPLETO, HH, HFD, HSS, HAS, AREA_ID, LABORATORIO, HORARIO, SALON, ORDEN"
					+ " FROM ENOC.MAPA_CURSO WHERE PLAN_ID = ? AND CICLO <= TO_NUMBER(?,'99') "+orden;
			Object[] parametros = new Object[] {planId, ciclo};
			lista = enocJdbc.query(comando, new MapaCursoMapper(), parametros);				 
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|lisCursos|:"+ex);	
		}		
		return lista;
	}
	
	public List<MapaCurso> listaCursosHoras(String codigoPersonal, String year) {
		List<MapaCurso>	lista = new ArrayList<MapaCurso>();
		try{
			String comando = "SELECT PLAN_ID, CURSO_ID, NOMBRE_CURSO, CICLO,"
				+ " CREDITOS, COALESCE(HT,0) AS HT, COALESCE(HP,0) AS HP, COALESCE(HI,0) AS HI,"
				+ " F_CREADA, NOTA_APROBATORIA, ESTADO, TIPOCURSO_ID,"
				+ " UNID, ON_LINE, CURSO_NUEVO, CURSO_CLAVE, OBLIGATORIO, COMPLETO, HH, HFD, HSS, HAS, AREA_ID, LABORATORIO, HORARIO, SALON, ORDEN"
				+ " FROM ENOC.MAPA_CURSO WHERE CURSO_ID IN (SELECT CURSO_ID FROM ENOC.EMP_HORAS WHERE TO_CHAR(FECHA_INI,'YYYY') = ? AND CODIGO_PERSONAL = ?)";			
			lista = enocJdbc.query(comando, new MapaCursoMapper(), year, codigoPersonal);
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|listaCursosHoras|:"+ex);
		}			
		return lista;
	}

	public List<String> lisCursoClaves( String orden ) {
		List<String> llaves	= new ArrayList<String>();		
		try{
			String comando = "SELECT UNIQUE(CURSO_CLAVE) FROM MAPA_CURSO "+orden;			
			llaves = enocJdbc.queryForList(comando, String.class);
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|lisCursoClaves|:"+ex);
		}

		return llaves;
	}
	
	public HashMap<String, String> getCursoTerminadoObligatorio( String codigoPersonal, String planId) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();
	
		try{
			String comando = " SELECT CICLO AS LLAVE, COUNT(CURSO_ID) AS VALOR FROM ENOC.MAPA_CURSO "
					+ " WHERE PLAN_ID = ?"
					+ " AND OBLIGATORIO = 'S'"
					+ " AND CURSO_ID NOT IN "
					+ "		(SELECT CURSO_ID FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ? AND TIPOCAL_ID = '1')"
					+ " GROUP BY CICLO";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), planId, codigoPersonal, planId);
			for(aca.Mapa row : lista) {
				mapa.put(row.getLlave(), (String)row.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|getCursoTerminadoObligatorio|:"+ex);
		}
			
		return mapa;
	}
	
	public HashMap<String, String> mapCursos() {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();
		
		try{
			String comando 	= "SELECT CURSO_ID AS LLAVE, NOMBRE_CURSO AS VALOR FROM ENOC.MAPA_CURSO";
			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa row : lista) {
				mapa.put(row.getLlave(), (String)row.getValor());
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|mapCursos|:"+ex);
		}
			
		return mapa;
	}
	
	public HashMap<String, String> mapCursosPlan() {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();
		
		try{
			String comando 	= "SELECT CURSO_ID AS LLAVE, PLAN_ID AS VALOR FROM ENOC.MAPA_CURSO";
			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa row : lista) {
				mapa.put(row.getLlave(), (String)row.getValor());
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.mapCursosPlan|mapCursos|:"+ex);
		}
			
		return mapa;
	}
	
	public HashMap<String, String> mapCursosConvalida(String codigoPersonal) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();
		
		try{
			String comando 	= "SELECT CURSO_ID AS LLAVE, NOMBRE_CURSO AS VALOR FROM ENOC.MAPA_CURSO"
					+ " WHERE CURSO_ID IN "
					+ "		(SELECT CURSO_ID FROM CONV_MATERIA WHERE CONVALIDACION_ID IN "
					+ "			(SELECT CONVALIDACION_ID FROM CONV_EVENTO WHERE CODIGO_PERSONAL = ?)"
					+ "		)";
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa row : lista) {
				mapa.put(row.getLlave(), (String)row.getValor());
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|mapCursosConvalida|:"+ex);
		}
			
		return mapa;
	}
	
	public HashMap<String, MapaCurso> mapCursosAnalisis( ) {
		HashMap<String, MapaCurso> lisCurso	= new HashMap<String, MapaCurso>();
		List<MapaCurso> lista	= new ArrayList<MapaCurso>();	
		
		try{
			String comando = " SELECT PLAN_ID, CURSO_ID, NOMBRE_CURSO, CICLO,"
					+ " CREDITOS, COALESCE(HT,0) AS HT, COALESCE(HP,0) AS HP, COALESCE(HI,0) AS HI,"
					+ " F_CREADA, NOTA_APROBATORIA, ESTADO, TIPOCURSO_ID,"
					+ " UNID, ON_LINE, CURSO_NUEVO, CURSO_CLAVE, OBLIGATORIO, COMPLETO, HH, HFD, HSS, HAS, AREA_ID, LABORATORIO, HORARIO, SALON, ORDEN"
					+ " FROM ENOC.MAPA_CURSO"
					+ " WHERE CURSO_ID IN (SELECT DISTINCT(CURSO_ID) FROM EST_CCOSTO)";
			
			lista = enocJdbc.query(comando,new MapaCursoMapper());
			for(MapaCurso mapa : lista){				
				lisCurso.put(mapa.getCursoId(), mapa);
			}	
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|mapCursosAnalisis|:"+ex);
		}
			
		return lisCurso;
	}
	
	public HashMap<String, MapaCurso> mapaCursosEnMateria( String cursoCargaId ) {
		HashMap<String, MapaCurso> lisCurso	= new HashMap<String, MapaCurso>();
		List<MapaCurso> lista	= new ArrayList<MapaCurso>();	
		
		try{
			String comando = "SELECT PLAN_ID, CURSO_ID, NOMBRE_CURSO, CICLO,"
					+ " CREDITOS, COALESCE(HT,0) AS HT, COALESCE(HP,0) AS HP, COALESCE(HI,0) AS HI,"
					+ " F_CREADA, NOTA_APROBATORIA, ESTADO, TIPOCURSO_ID,"
					+ " UNID, ON_LINE, CURSO_NUEVO, CURSO_CLAVE, OBLIGATORIO, COMPLETO, HH, HFD, HSS, HAS, AREA_ID, LABORATORIO, HORARIO, SALON, ORDEN"
					+ " FROM ENOC.MAPA_CURSO"
					+ " WHERE CURSO_ID IN (SELECT DISTINCT(CURSO_ID) FROM ENOC.KRDX_CURSO_ACT WHERE CURSO_CARGA_ID = ?)";	
			Object[] parametros = new Object[] {cursoCargaId};
			lista = enocJdbc.query(comando, new MapaCursoMapper(), parametros);
			for(MapaCurso mapa : lista){		
				lisCurso.put(mapa.getCursoId(), mapa);
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|mapCursosEnMateria|:"+ex);
		}
			
		return lisCurso;
	}
	
	public HashMap<String, MapaCurso> mapaCursosEnCarrera( String carreraId ) {
		HashMap<String, MapaCurso> lisCurso	= new HashMap<String, MapaCurso>();
		List<MapaCurso> lista	= new ArrayList<MapaCurso>();	
		
		try{
			String comando = "SELECT PLAN_ID, CURSO_ID, NOMBRE_CURSO, CICLO,"
					+ " CREDITOS, COALESCE(HT,0) AS HT, COALESCE(HP,0) AS HP, COALESCE(HI,0) AS HI,"
					+ " F_CREADA, NOTA_APROBATORIA, ESTADO, TIPOCURSO_ID,"
					+ " UNID, ON_LINE, CURSO_NUEVO, CURSO_CLAVE, OBLIGATORIO, COMPLETO, HH, HFD, HSS, HAS, AREA_ID, LABORATORIO, HORARIO, SALON, ORDEN"
					+ " FROM ENOC.MAPA_CURSO"
					+ " WHERE PLAN_ID IN (SELECT PLAN_ID FROM ENOC.MAPA_PLAN WHERE CARRERA_ID = ?)";	
			Object[] parametros = new Object[] {carreraId};
			lista = enocJdbc.query(comando, new MapaCursoMapper(), parametros);
			for(MapaCurso mapa : lista){		
				lisCurso.put(mapa.getCursoId(), mapa);
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|mapaCursosEnCarrera|:"+ex);
		}
			
		return lisCurso;
	}
	
	public HashMap<String, String> mapCursosEnHoras() {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();
		
		try{
			String comando 			= "SELECT CURSO_ID AS LLAVE, NOMBRE_CURSO AS VALOR FROM ENOC.MAPA_CURSO WHERE CURSO_ID IN (SELECT DISTINCT(CURSO_ID) FROM ENOC.EMP_HORAS)";
			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa row : lista) {
				mapa.put(row.getLlave(), (String)row.getValor());
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|mapCursos|:"+ex);
		}
			
		return mapa;
	}
	
	public HashMap<String, String> mapCursos(String carreraId) {
		HashMap<String, String> mapa 	= new HashMap<String, String>();
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();		
		try{
			String comando 			= "SELECT CURSO_ID AS LLAVE, NOMBRE_CURSO AS VALOR FROM ENOC.MAPA_CURSO "
					+ " WHERE PLAN_ID IN (SELECT PLAN_ID FROM ENOC.MAPA_PLAN WHERE CARRERA_ID = ?)";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), carreraId);
			for(aca.Mapa row : lista) {
				mapa.put(row.getLlave(), (String)row.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|mapCursos|:"+ex);
		}			
		return mapa;
	}
	
	public HashMap<String, String> mapaTipoPorCiclo(String planId) {
		
		HashMap<String, String> mapa 	= new HashMap<String, String>();
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();		
		try{
			String comando 	= "SELECT CICLO||TIPOCURSO_ID AS LLAVE, COUNT(CURSO_ID) AS VALOR FROM ENOC.MAPA_CURSO WHERE PLAN_ID = ? GROUP BY CICLO||TIPOCURSO_ID";
			Object[] parametros = new Object[] {planId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa row : lista) {
				mapa.put(row.getLlave(), (String)row.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|mapaTipoPorCiclo|:"+ex);
		}
			
		return mapa;
	}
	
	public HashMap<String, String> mapCursosHoras(String codigoPersonal) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();		
		try{
			String comando 	= " SELECT CURSO_ID AS LLAVE, NOMBRE_CURSO AS VALOR FROM ENOC.MAPA_CURSO "
							+ " WHERE CURSO_ID IN (SELECT CURSO_ID FROM ENOC.EMP_HORAS WHERE CODIGO_PERSONAL = ?)";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), codigoPersonal);
			for(aca.Mapa row : lista) {
				mapa.put(row.getLlave(), (String)row.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|mapCursosHoras|:"+ex);
		}			
		return mapa;
	}
	
	public HashMap<String, String> mapaCursosPorPlan() {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();		
		try{
			String comando 	= "SELECT PLAN_ID AS LLAVE, COUNT(*) AS VALOR FROM ENOC.MAPA_CURSO GROUP BY PLAN_ID";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa row : lista) {
				mapa.put(row.getLlave(), (String)row.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|mapaCursosPorPlan|:"+ex);
		}			
		return mapa;
	}	
	
	public HashMap<String, String> mapaCursosPorCiclo(String planId){
		HashMap<String, String> mapa 	= new HashMap<String, String>();
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();		
		try{
			String comando 	= "SELECT CICLO AS LLAVE, COUNT(CURSO_ID) AS VALOR FROM ENOC.MAPA_CURSO WHERE PLAN_ID = ? GROUP BY CICLO";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), planId);
			for(aca.Mapa row : lista) {
				mapa.put(row.getLlave(), (String)row.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|mapaCursosPorCiclo|:"+ex);
		}			
		return mapa;
	}
	
	public HashMap<String,MapaCurso> mapaCursosPlan( String planId) {
		HashMap<String, MapaCurso> mapa	= new HashMap<String,MapaCurso>();
		List<MapaCurso> lista			= new ArrayList<MapaCurso>();		
		try{
			String comando = "SELECT PLAN_ID, CURSO_ID, NOMBRE_CURSO, CICLO," +
					" CREDITOS, COALESCE(HT,0) AS HT, COALESCE(HP,0) AS HP, COALESCE(HI,0) AS HI, "+
					" TO_CHAR(F_CREADA,'DD/MM/YYYY') F_CREADA,"+
					" NOTA_APROBATORIA, ESTADO, TIPOCURSO_ID," +
					" UNID, ON_LINE, CURSO_NUEVO, CURSO_CLAVE, OBLIGATORIO, COMPLETO, HH, HFD, HSS, HAS, AREA_ID, LABORATORIO, HORARIO, SALON, ORDEN" +
					" FROM ENOC.MAPA_CURSO WHERE PLAN_ID = ?";			
			lista = enocJdbc.query(comando,new MapaCursoMapper(), planId);
			for(MapaCurso map : lista){				
				mapa.put(map.getCursoId(), map);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|mapaCursosPlan|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String,MapaCurso> mapaCursosDiferidos() {
		HashMap<String, MapaCurso> mapa	= new HashMap<String,MapaCurso>();
		List<MapaCurso> lista			= new ArrayList<MapaCurso>();		
		try{
			String comando = "SELECT PLAN_ID, CURSO_ID, NOMBRE_CURSO, CICLO, CREDITOS, COALESCE(HT,0) AS HT, COALESCE(HP,0) AS HP, COALESCE(HI,0) AS HI,"
					+ " TO_CHAR(F_CREADA,'DD/MM/YYYY') F_CREADA, NOTA_APROBATORIA, ESTADO, TIPOCURSO_ID,"
					+ " UNID, ON_LINE, CURSO_NUEVO, CURSO_CLAVE, OBLIGATORIO, COMPLETO, HH, HFD, HSS, HAS, AREA_ID, LABORATORIO, HORARIO, SALON, ORDEN"
					+ " FROM ENOC.MAPA_CURSO WHERE CURSO_ID IN (SELECT DISTINCT(CURSO_ID) FROM ENOC.KRDX_CURSO_ACT WHERE TIPOCAL_ID IN ('5','6'))";	
			lista = enocJdbc.query(comando,new MapaCursoMapper());
			for(MapaCurso map : lista){				
				mapa.put(map.getCursoId(), map);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|mapaCursosDiferidos|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String, String> mapaCreditosPlan() {
		HashMap<String, String> mapa 	= new HashMap<String, String>();
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT PLAN_ID AS LLAVE, SUM(CREDITOS) AS VALOR FROM ENOC.MAPA_CURSO WHERE OBLIGATORIO = 'S' GROUP BY PLAN_ID";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa row : lista) {
				mapa.put(row.getLlave(), (String)row.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|mapaCreditosPlan|:"+ex);
		}			
		return mapa;
	}
	
	public HashMap<String, String> mapaCreditosEnCurso(String planId, String ciclo) {
		HashMap<String, String> mapa 	= new HashMap<String, String>();
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT TIPOCURSO_ID AS LLAVE, SUM(CREDITOS) AS VALOR FROM ENOC.MAPA_CURSO WHERE PLAN_ID = ? AND CICLO = ? GROUP BY TIPOCURSO_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), planId, ciclo);
			for(aca.Mapa row : lista) {
				mapa.put(row.getLlave(), (String)row.getValor());
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.mapaCreditosEnCurso|mapCursos|:"+ex);
		}
			
		return mapa;
	}
	
	public HashMap<String, String> mapaCursosEnLinea() {
		HashMap<String, String> mapa 	= new HashMap<String, String>();
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT PLAN_ID AS LLAVE, "
					+ " COUNT(ON_LINE) AS VALOR "
					+ " FROM ENOC.MAPA_CURSO "
					+ " WHERE ON_LINE = 'S' "
					+ " GROUP BY PLAN_ID, ON_LINE";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa row : lista) {
				mapa.put(row.getLlave(), (String)row.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|mapaCursosEnLinea|:"+ex);
		}			
		return mapa;
	}
	
	public HashMap<String, String> mapaCursosEnPracticas() {
		HashMap<String, String> mapa 	= new HashMap<String, String>();
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CURSO_ID AS LLAVE, LABORATORIO AS VALOR FROM ENOC.MAPA_CURSO WHERE LABORATORIO = 'S'";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa row : lista) {
				mapa.put(row.getLlave(), (String)row.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|mapaCursosEnPracticas|:"+ex);
		}			
		return mapa;
	}
	
	public HashMap<String, String> mapaPlanEnPracticas() {
		HashMap<String, String> mapa 	= new HashMap<String, String>();
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT PLAN_ID AS LLAVE, COUNT(LABORATORIO) AS VALOR FROM ENOC.MAPA_CURSO WHERE LABORATORIO = 'S' GROUP BY PLAN_ID";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa row : lista) {
				mapa.put(row.getLlave(), (String)row.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|mapaPlanEnPracticas|:"+ex);
		}			
		return mapa;
	}
	
	public HashMap<String, String> mapaPracticasEnCarreras() {
		HashMap<String, String> mapa 	= new HashMap<String, String>();
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CARRERA(PLAN_ID) AS LLAVE, COUNT(LABORATORIO) AS VALOR FROM ENOC.MAPA_CURSO WHERE LABORATORIO = 'S' GROUP BY CARRERA(PLAN_ID)";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa row : lista) {
				mapa.put(row.getLlave(), (String)row.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|mapaPlanEnPracticas|:"+ex);
		}			
		return mapa;
	}
	
	public HashMap<String, String> mapCursosContenido() {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();
		
		try{
			String comando 	= "SELECT PLAN_ID AS LLAVE, COUNT(CURSO_ID) AS VALOR FROM ENOC.MAPA_CURSO WHERE CURSO_NUEVO != 0 GROUP BY PLAN_ID ORDER BY PLAN_ID";
			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa row : lista) {
				mapa.put(row.getLlave(), (String)row.getValor());
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|mapCursosContenido|:"+ex);
		}
			
		return mapa;
	}
	
	public HashMap<String,Boolean> mapaPreCursosAprobados(String matricula, String planId, String cursoId){
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		List<aca.Mapa> lista2 = new ArrayList<aca.Mapa>();
		
		HashMap<String,Boolean> mapa = new HashMap<String,Boolean>();
		
		boolean ok	= false;
		
		try{
			String comando	=	"SELECT COUNT(B.CURSO_ID) AS CANTIDAD " +
					"FROM ENOC.MAPA_CURSO A, ENOC.MAPA_CURSO_PRE B "+ 
					"WHERE A.CURSO_ID = B.CURSO_ID " +
					"AND A.PLAN_ID = ? " +
					"AND A.CURSO_ID = ? "+
					"AND NOT B.CURSO_ID_PRE IN ( "+
					"SELECT CURSO_ID FROM ENOC.ALUMNO_CURSO " +
					"WHERE CODIGO_PERSONAL  = ? "+
					"AND TIPOCAL_ID = '1' "+ 
					" )" +
					"AND NOT B.CURSO_ID_PRE IN( "+
					"SELECT CURSO_ID FROM ENOC.CONV_SOLICITUD " +
					"WHERE CODIGO_PERSONAL  = ? "+
					"AND PROCESO_ID IN ('C','A','T','R') "+
					"AND EST_MAT != 'N'"+				
					" )";	
			if(enocJdbc.queryForObject(comando, Integer.class, planId, cursoId, matricula, matricula) < 1) {
				comando	=	"SELECT B.CURSO_ID AS LLAVE, B.CURSO_ID_PRE AS VALOR " +
					"FROM ENOC.MAPA_CURSO A, ENOC.MAPA_CURSO_PRE B "+ 
					"WHERE A.CURSO_ID = B.CURSO_ID " +
					"AND A.PLAN_ID = ? " +
					"AND A.CURSO_ID = ? "+
					"AND NOT B.CURSO_ID_PRE IN ( "+
						"SELECT CURSO_ID FROM ENOC.ALUMNO_CURSO " +
						"WHERE CODIGO_PERSONAL  = ? "+
						"AND TIPOCAL_ID = '1' "+ 
						" )" +
					"AND NOT B.CURSO_ID_PRE IN( "+
						"SELECT CURSO_ID FROM ENOC.CONV_SOLICITUD " +
						"WHERE CODIGO_PERSONAL  = ? "+
						"AND 	 "+
						"AND EST_MAT != 'N'"+				
						" )";		
			
				lista = enocJdbc.query(comando, new aca.MapaMapper(), planId, cursoId, matricula, matricula );
				
				for(aca.Mapa objeto : lista) {
					
					String comando3 = "SELECT COUNT(B.CURSO_ID)" +
							"FROM ENOC.MAPA_CURSO A, ENOC.MAPA_CURSO_PRE B "+ 
							"WHERE A.CURSO_ID = B.CURSO_ID " +
							"AND A.PLAN_ID = ?" +
							"AND A.CURSO_ID = ? ";				
					boolean entro = false;					
					lista2 = enocJdbc.query(comando, new aca.MapaMapper(), planId);
					
					for(aca.Mapa objeto2 : lista2) {
						Object[] parametros = new Object[]{
							objeto2.getValor()
						};
						if(enocJdbc.queryForObject(comando3,Integer.class,parametros) >= 1) {
							entro = true;
						}
					}
					if(entro == false) {
						ok = true;
					}
					mapa.put(objeto.getLlave(), ok);
				}
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|mapaPreCursosAprobados|:"+ex);
		}
		return mapa;
	}
	
	public void lisPrerrequisitos( String cursoId, List<String> lista ){
		List<String> lisPre = new ArrayList<String>();
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.MAPA_CURSO_PRE WHERE CURSO_ID = ?";
			Object[] parametros = new Object[]{	cursoId	};
			if(enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1) {
				comando = "SELECT CURSO_ID_PRE FROM ENOC.MAPA_CURSO_PRE WHERE CURSO_ID = ?";
				lisPre = enocJdbc.queryForList(comando,String.class,parametros);
				for (String pre : lisPre) {
					if (lista.contains(pre)==false) lista.add(pre);
					lisPrerrequisitos(pre,lista);
				}		
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|lisPrerrequisitos|:"+ex);
		}		
		//return lista;
	}
	
	public HashMap<String, String> mapaPendientesPorCiclo(String codigoPersonal, String planId) {
		HashMap<String, String> mapa 	= new HashMap<String, String>();
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT CICLO AS LLAVE, COUNT(CURSO_ID) AS VALOR FROM ENOC.MAPA_CURSO"
					+ " WHERE PLAN_ID = ? "
					+ " AND TIPOCURSO_ID IN (1,2,3,5,7,8)"
					+ " AND CURSO_ID NOT IN "
					+ " (SELECT CURSO_ID FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ? AND TIPOCAL_ID IN ('1','I'))"
					+ " GROUP BY CICLO";

			Object[] parametros = new Object[]{	
				planId,codigoPersonal,planId	
			};
			
			lista = enocJdbc.query(comando, new aca.MapaMapper(),parametros);
			for(aca.Mapa row : lista) {
				mapa.put(row.getLlave(), row.getValor());
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|mapaPendientesPorCiclo|:"+ex);
		}
			
		return mapa;
	}
	
	public HashMap<String, String> mapaCursos(){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();

		try{
		
			String comando = "SELECT PLAN_ID AS LLAVE, COUNT(CURSO_ID) AS VALOR FROM ENOC.MAPA_CURSO GROUP BY PLAN_ID";				
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			
			for(aca.Mapa row : lista) {
				mapa.put(row.getLlave(), (String)row.getValor());
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|mapaCursos|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaCursosEventoId(String eventoId){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();
		
		try{
			
			String comando = "SELECT CURSO_ID AS LLAVE, NOMBRE_CURSO AS VALOR FROM MAPA_CURSO WHERE PLAN_ID IN (SELECT DISTINCT(PLAN_ID) FROM ALUM_EGRESO WHERE EVENTO_ID = TO_NUMBER(?,'999'))";				
			lista = enocJdbc.query(comando, new aca.MapaMapper(),eventoId);
			
			for(aca.Mapa row : lista) {
				mapa.put(row.getLlave(), (String)row.getValor());
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|mapaCursosEventoId|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaCiclosRegPorFacultad(){
		HashMap<String, String> mapa 	= new HashMap<String, String>();
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();	
		try{
			String comando 	= "SELECT CF.FACULTAD_ID AS LLAVE, COUNT(DISTINCT(MC.PLAN_ID||'-'||MC.CICLO)) AS VALOR FROM ENOC.MAPA_CURSO MC, ENOC.MAPA_PLAN MP, ENOC.CAT_CARRERA CC, ENOC.CAT_FACULTAD CF"
				+ " WHERE MC.PLAN_ID||'-'||MC.CICLO IN (SELECT PLAN_ID||'-'||CICLO FROM ENOC.MAPA_CREDITO)"
				+ " AND MP.PLAN_ID = MC.PLAN_ID"
				+ " AND CC.CARRERA_ID = MP.CARRERA_ID"
				+ " AND CF.FACULTAD_ID = CC.FACULTAD_ID"
				+ " GROUP BY CF.FACULTAD_ID";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa row : lista) {
				mapa.put(row.getLlave(), (String)row.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|mapaCiclosRegPorFacultad|:"+ex);
		}
			
		return mapa;
	}
	
	public HashMap<String, String> mapaCiclosPenPorFacultad(){
		HashMap<String, String> mapa 	= new HashMap<String, String>();
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();	
		try{
			String comando 	= "SELECT CF.FACULTAD_ID AS LLAVE, COUNT(DISTINCT(MC.PLAN_ID||'-'||MC.CICLO)) AS VALOR FROM ENOC.MAPA_CURSO MC, ENOC.MAPA_PLAN MP, ENOC.CAT_CARRERA CC, ENOC.CAT_FACULTAD CF"
				+ " WHERE MC.PLAN_ID||'-'||MC.CICLO NOT IN (SELECT PLAN_ID||'-'||CICLO FROM ENOC.MAPA_CREDITO)"
				+ " AND MP.PLAN_ID = MC.PLAN_ID"
				+ " AND CC.CARRERA_ID = MP.CARRERA_ID"
				+ " AND CF.FACULTAD_ID = CC.FACULTAD_ID"
				+ " GROUP BY CF.FACULTAD_ID";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa row : lista) {
				mapa.put(row.getLlave(), (String)row.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|mapaCiclosRegPorFacultad|:"+ex);
		}
			
		return mapa;
	}
	
	public HashMap<String, String> mapaInscritasPorFacultad() {		
		HashMap<String, String> mapa 	= new HashMap<String, String>();
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();		
		try{
			String comando 	= "SELECT CF.FACULTAD_ID AS LLAVE, COUNT(MC.CURSO_ID) AS VALOR"
				+ " FROM ENOC.MAPA_CURSO MC, ENOC.MAPA_PLAN MP, ENOC.CAT_CARRERA CC, ENOC.CAT_FACULTAD CF"
				+ " WHERE MP.ESTADO IN ('A','V')"
				+ " AND MP.PLAN_ID = MC.PLAN_ID"
				+ " AND CC.CARRERA_ID = MP.CARRERA_ID"
				+ " AND CF.FACULTAD_ID = CC.FACULTAD_ID"
				+ " AND MC.CURSO_ID IN (SELECT CURSO_ID FROM ENOC.CARGA_GRUPO_CURSO WHERE CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO WHERE SYSDATE BETWEEN F_INICIO AND F_FINAL) AND CURSO_CARGA_ID IN (SELECT DISTINCT(CURSO_CARGA_ID) FROM ENOC.KRDX_CURSO_ACT))"
				+ " GROUP BY CF.FACULTAD_ID";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa row : lista) {
				mapa.put(row.getLlave(), (String)row.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|mapaInscritasPorFacultad|:"+ex);
		}
			
		return mapa;
	}

	public HashMap<String, MapaCurso> mapaCursosAlumnoEnTraspaso(String codigoPersonal, String planId) {		
		HashMap<String, MapaCurso> lisCurso	= new HashMap<String,MapaCurso>();
		List<MapaCurso> lista			= new ArrayList<MapaCurso>();		
		try{
			String comando = " SELECT PLAN_ID, CURSO_ID, NOMBRE_CURSO, CICLO,"
			+ " CREDITOS, COALESCE(HT,0) AS HT, COALESCE(HP,0) AS HP, COALESCE(HI,0) AS HI,"
			+ " F_CREADA, NOTA_APROBATORIA, ESTADO, TIPOCURSO_ID,"
			+ " UNID, ON_LINE, CURSO_NUEVO, CURSO_CLAVE, OBLIGATORIO, COMPLETO, HH, HFD, HSS, HAS, AREA_ID, LABORATORIO, HORARIO, SALON, ORDEN "
			+ " FROM ENOC.MAPA_CURSO"
			+ " WHERE CURSO_CLAVE IN (SELECT SUBJECT_CODE FROM KRDX_TRASPASO WHERE ID_NUMBER IN (SELECT CODIGO_SE FROM ALUM_PERSONAL WHERE CODIGO_PERSONAL = ?)) AND PLAN_ID = ?" ;
			lista = enocJdbc.query(comando,new MapaCursoMapper(),codigoPersonal, planId);
			for(MapaCurso mapa : lista){				
				lisCurso.put(mapa.getCursoId()+mapa.getCursoClave(), mapa);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.MapaCursoDao|mapaCursosAlumnoEnTraspaso|:"+ex);
		}
			
		return lisCurso;
	}

}