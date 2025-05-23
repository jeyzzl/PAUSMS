// Clase Util para la tabla de Carga
package aca.vista.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CargaAcademicaDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public CargaAcademica mapeaRegId(  String cursoCargaId, String cursoId ) {
		CargaAcademica objeto = new CargaAcademica();
		
		try{
			String comando = "SELECT "+
				"CURSO_CARGA_ID, CARGA_ID, "+
				"TO_CHAR(BLOQUE_ID,'99') AS BLOQUE_ID, "+
				"CODIGO_PERSONAL, NOMBRE, CARRERA_ID, GRUPO, "+
				"TO_CHAR(MODALIDAD_ID,'99') AS MODALIDAD_ID, "+
				"ESTADO, "+
				"TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO, "+
				"TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL, "+
				"TO_CHAR(F_ENTREGA,'DD/MM/YYYY') AS F_ENTREGA, "+
				"ORIGEN, PLAN_ID, CURSO_ID, NOMBRE_CURSO, "+
				"TO_CHAR(CICLO,'99') AS CICLO, "+
				"TO_CHAR(CREDITOS,'99.99') AS CREDITOS, "+
				"TO_CHAR(HT,'99') AS HT, "+
				"TO_CHAR(HP,'99') AS HP, "+
				"TO_CHAR(HI,'99') AS HI, "+
				"TO_CHAR(NOTA_APROBATORIA,'99') AS NOTA_APROBATORIA, "+
				"VALEUCAS, NUM_ALUM, SEMANAS, OPTATIVA, HORARIO, GRUPO_HORARIO, HH,SALON,MODO "+
				"FROM ENOC.CARGA_ACADEMICA "+ 
				"WHERE CURSO_CARGA_ID = ? "+
				"AND CURSO_ID = ? ";
			
			Object[] parametros = new Object[] {cursoCargaId, cursoId};
			objeto = enocJdbc.queryForObject(comando, new CargaAcademicaMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.CargaAcademicaDao|mapeaRegId|:"+ex);
		}
		return objeto;
	}
	
	public CargaAcademica mapeaRegId(  String cursoCargaId) {
		CargaAcademica objeto = new CargaAcademica();
		
		try{
			String comando = "SELECT CURSO_CARGA_ID, CARGA_ID, "+
				"TO_CHAR(BLOQUE_ID,'99') AS BLOQUE_ID, "+
				"CODIGO_PERSONAL, NOMBRE, CARRERA_ID, GRUPO, "+
				"TO_CHAR(MODALIDAD_ID,'99') AS MODALIDAD_ID, "+
				"ESTADO, "+
				"TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO, "+
				"TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL, "+
				"TO_CHAR(F_ENTREGA,'DD/MM/YYYY') AS F_ENTREGA, "+
				"ORIGEN, PLAN_ID, CURSO_ID, NOMBRE_CURSO, "+
				"TO_CHAR(CICLO,'99') AS CICLO, "+
				"TO_CHAR(CREDITOS,'99.99') AS CREDITOS, "+
				"TO_CHAR(HT,'99') AS HT, "+
				"TO_CHAR(HP,'99') AS HP, "+
				"TO_CHAR(HI,'99') AS HI, "+
				"TO_CHAR(NOTA_APROBATORIA,'99') AS NOTA_APROBATORIA, "+
				"VALEUCAS, NUM_ALUM, SEMANAS, OPTATIVA, HORARIO, GRUPO_HORARIO, HH, SALON,MODO "+
				"FROM ENOC.CARGA_ACADEMICA "+ 
				"WHERE CURSO_CARGA_ID = ? "+
				"AND ORIGEN = 'O' ";
				
				Object[] parametros = new Object[] {cursoCargaId};
				objeto = enocJdbc.queryForObject(comando, new CargaAcademicaMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.CargaAcademicaDao|mapeaRegId|:"+ex);
		}
		return objeto;
	}
	
	public CargaAcademica mapeaRegId(  String cursoCargaId, String cursoId, String origen) {
		CargaAcademica objeto = new CargaAcademica();
		
		try{
			String comando = "SELECT"
					+ " CURSO_CARGA_ID, CARGA_ID,"
					+ " TO_CHAR(BLOQUE_ID,'99') AS BLOQUE_ID,"
					+ " CODIGO_PERSONAL, NOMBRE, CARRERA_ID, GRUPO,"
					+ " TO_CHAR(MODALIDAD_ID,'99') AS MODALIDAD_ID,"
					+ " ESTADO,"
					+ " TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO,"
					+ " TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL,"
					+ " TO_CHAR(F_ENTREGA,'DD/MM/YYYY') AS F_ENTREGA,"
					+ " ORIGEN, PLAN_ID, CURSO_ID, NOMBRE_CURSO,"
					+ " TO_CHAR(CICLO,'99') AS CICLO,"
					+ " TO_CHAR(CREDITOS,'99.99') AS CREDITOS,"
					+ " TO_CHAR(HT,'99') AS HT,"
					+ " TO_CHAR(HP,'99') AS HP,"
					+ " TO_CHAR(HI,'99') AS HI,"
					+ " TO_CHAR(NOTA_APROBATORIA,'99') AS NOTA_APROBATORIA,"
					+ " VALEUCAS, NUM_ALUM, SEMANAS, OPTATIVA, HORARIO, GRUPO_HORARIO, HH, SALON,MODO"
					+ " FROM ENOC.CARGA_ACADEMICA"
					+ " WHERE CURSO_CARGA_ID = ?"
					+ " AND CURSO_ID = ?"
					+ " AND ORIGEN = ?";
					
					Object[] parametros = new Object[] {cursoCargaId};
					objeto = enocJdbc.queryForObject(comando, new CargaAcademicaMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.CargaAcademicaDao|mapeaRegId|:"+ex);
		}
		return objeto;
	}
	
	public boolean existeReg(String cursoCargaId, String cursoId) {
		boolean 			ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA_ACADEMICA "+ 
				"WHERE CURSO_CARGA_ID = ? AND CURSO_ID = ?";
			
			Object[] parametros = new Object[] {cursoCargaId, cursoId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.CargaAcademicaDao|existeReg|:"+ex);
		}
		return ok;
	}	
	
	public boolean esOfertada( String cargaId, String cursoId, String modalidadId) {
		boolean 			ok 	= false;
		
		try{
			String comando = "SELECT * FROM ENOC.CARGA_ACADEMICA "+ 
				"WHERE CARGA_ID = ?" +
				" AND CURSO_ID = ?" +
				" AND MODALIDAD_ID = TO_NUMBER(?, '999')";
			Object[] parametros = new Object[] {cargaId,cursoId,modalidadId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.CargaAcademicaDao|esOfertada|:"+ex);
		}
		
		return ok;
	}
	
	public String getNombreMateria( String cursoCargaId) {
		String materia			= "";	
		
		try{
			String comando = "SELECT NOMBRE_CURSO FROM ENOC.CARGA_ACADEMICA " + 
					"WHERE CURSO_CARGA_ID = ? " +
					"AND ORIGEN = 'O'";
				Object[] parametros = new Object[] {cursoCargaId};
	 			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
	 				materia  = enocJdbc.queryForObject(comando,String.class,parametros);
				}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.CargaAcademicaDao|getNombreMateria|:"+ex);
		}
		return materia;
	}
	
	public String getOptativa( String cursoCargaId) {
		String materia			= "";
		
		try{
			String comando = "SELECT OPTATIVA FROM ENOC.CARGA_ACADEMICA " + 
					"WHERE CURSO_CARGA_ID = ? " +
					"AND ORIGEN = 'O'";
				Object[] parametros = new Object[] {cursoCargaId};
		 		if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
					materia  = enocJdbc.queryForObject(comando,String.class,parametros);
				}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.CargaAcademicaDao|getOptativa|:"+ex);
		}
		return materia;
	}
	
	public List<CargaAcademica> getMateriasDeFacultad( String cargaId, String facultadId ) {
		List<CargaAcademica> lista	= new ArrayList<CargaAcademica>();
		
		try{
			String comando = " SELECT * FROM ENOC.CARGA_ACADEMICA "
					+ " WHERE CARGA_ID = ? "
					+ " AND ENOC.FACULTAD(CARRERA_ID) = ? "
					+ " AND ORIGEN = 'O' ";
			Object[] parametros = new Object[] {cargaId, facultadId};
			lista = enocJdbc.query(comando, new CargaAcademicaMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.CargaAcademicaDao|getMateriasDeFacultad|:"+ex);
		}
		return lista;
	}
	
	
	public List<CargaAcademica> listMateriasFaltantes( String cargaId, String orden) {
		List<CargaAcademica> lista	= new ArrayList<CargaAcademica>();
		
		try{
			String comando = " SELECT * FROM ENOC.CARGA_ACADEMICA"
					+ " WHERE CARGA_ID = ? AND ORIGEN='O'"
					+ " AND GRUPO_SUM_EVAL(ENOC.CARGA_ACADEMICA.CURSO_CARGA_ID) < 100 " +orden;
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new CargaAcademicaMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.CargaAcademicaDao|listMateriasFaltantes|:"+ex);
		}
		return lista;
	}
		
	public List<CargaAcademica> getListAll( String orden ) {
		List<CargaAcademica> lista		= new ArrayList<CargaAcademica>();
		String comando		= "";
		
		try{
			comando = " SELECT "
					+ " CURSO_CARGA_ID, CARGA_ID, "
					+ " BLOQUE_ID, "
					+ " CODIGO_PERSONAL, NOMBRE, CARRERA_ID, GRUPO, "
					+ " MODALIDAD_ID, "
					+ " ESTADO, "
					+ " TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO, "
					+ " TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL, "
					+ " TO_CHAR(F_ENTREGA,'DD/MM/YYYY') AS F_ENTREGA, "
					+ " ORIGEN, PLAN_ID, CURSO_ID, NOMBRE_CURSO, "
					+ " CICLO, "
					+ " TRIM(TO_CHAR(CREDITOS,'99.9')) AS CREDITOS, "
					+ " HT, "
					+ " HP, "
					+ " HI, "
					+ " TO_CHAR(NOTA_APROBATORIA,'99') AS NOTA_APROBATORIA,"
					+ " VALEUCAS, NUM_ALUM, SEMANAS, OPTATIVA, HORARIO, GRUPO_HORARIO, HH, SALON,MODO "
					+ " FROM ENOC.CARGA_ACADEMICA "+orden; 
			
			lista = enocJdbc.query(comando, new CargaAcademicaMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.CargaAcademicaDao|getListAll|:"+ex);
		}
		return lista;
	}
	
	public List<CargaAcademica> getMatReprobadas( String cargaId, String modalidades ) {
		List<CargaAcademica> lista		= new ArrayList<CargaAcademica>();
		String comando							= "";
		
		try{
			comando = " SELECT * FROM ENOC.CARGA_ACADEMICA WHERE CARGA_ID IN('"+cargaId+"') AND MODALIDAD_ID IN("+modalidades+") AND ORIGEN = 'O' "
					+ " AND CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.KRDX_CURSO_ACT WHERE SUBSTR(CURSO_CARGA_ID,1,6) IN('"+cargaId+"') "
					+ " AND TIPOCAL_ID IN ('2','4'))";
			
			lista = enocJdbc.query(comando, new CargaAcademicaMapper());		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.CargaAcademicaDao|getMatReprobadas|:"+ex);
		}	
		return lista;
	}
	
	public List<CargaAcademica> getListaCarga( String cargaId, String orden ) {		
		List<CargaAcademica> lista	= new ArrayList<CargaAcademica>();
		String comando	= "";
		
		try{
			comando = " SELECT CURSO_CARGA_ID, CARGA_ID, BLOQUE_ID, CODIGO_PERSONAL, NOMBRE, CARRERA_ID, GRUPO, MODALIDAD_ID, ESTADO,"
					+ " TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO,"
					+ " TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL,"
					+ " TO_CHAR(F_ENTREGA,'DD/MM/YYYY') AS F_ENTREGA,"
					+ " ORIGEN, PLAN_ID, CURSO_ID, NOMBRE_CURSO,"
					+ " CICLO, TRIM(TO_CHAR(CREDITOS,'99.9')) AS CREDITOS,"
					+ " HT, HP, HI, NOTA_APROBATORIA, VALEUCAS, NUM_ALUM, SEMANAS, OPTATIVA, HORARIO, GRUPO_HORARIO, HH, SALON,MODO"
					+ " FROM ENOC.CARGA_ACADEMICA"
					+ " WHERE CARGA_ID = ? "+orden;
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new CargaAcademicaMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.CargaAcademicaDao|getListaCarga|:"+ex);
		}		
		return lista;
	}
	
	
	public List<CargaAcademica> lisPorCargayOrigen( String cargaId, String origen, String orden ) { 
		List<CargaAcademica> lista	= new ArrayList<CargaAcademica>();
		try{
			String comando = " SELECT CURSO_CARGA_ID, CARGA_ID, BLOQUE_ID, CODIGO_PERSONAL, NOMBRE, CARRERA_ID, GRUPO, MODALIDAD_ID, ESTADO,"
					+ " TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO,"
					+ " TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL,"
					+ " TO_CHAR(F_ENTREGA,'DD/MM/YYYY') AS F_ENTREGA,"
					+ " ORIGEN, PLAN_ID, CURSO_ID, NOMBRE_CURSO,"
					+ " CICLO, TRIM(TO_CHAR(CREDITOS,'99.9')) AS CREDITOS,"
					+ " HT, HP, HI, NOTA_APROBATORIA, VALEUCAS, NUM_ALUM, SEMANAS, OPTATIVA, HORARIO, GRUPO_HORARIO, HH, SALON,MODO"
					+ " FROM ENOC.CARGA_ACADEMICA"
					+ " WHERE CARGA_ID = ? "
					+ " AND ORIGEN IN ("+origen+") "+orden;
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new CargaAcademicaMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.CargaAcademicaDao|getListaCarga|:"+ex);
		}		
		return lista;
	}
	
	public List<CargaAcademica> getListaCarga( String cargaId, String planId, String modalidades, String orden ) {
		List<CargaAcademica> lista	= new ArrayList<CargaAcademica>();
		try{
			String comando = " SELECT CURSO_CARGA_ID, CARGA_ID,"
					+ " BLOQUE_ID, "
					+ " CODIGO_PERSONAL, NOMBRE, CARRERA_ID, GRUPO,"
					+ " MODALIDAD_ID,"
					+ " ESTADO,"
					+ " TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO,"
					+ " TO_CHAR(F_FINAL,'DD/MM/YYYY') F_FINAL,"
					+ " TO_CHAR(F_ENTREGA,'DD/MM/YYYY') F_ENTREGA,"
					+ " ORIGEN, PLAN_ID, CURSO_ID, NOMBRE_CURSO,"
					+ " CICLO,"
					+ " TRIM(TO_CHAR(CREDITOS,'99.9')) CREDITOS,"
					+ " HT,"
					+ " HP,"
					+ " HI,"
					+ " NOTA_APROBATORIA,"
					+ " VALEUCAS, NUM_ALUM, SEMANAS, OPTATIVA, HORARIO, GRUPO_HORARIO, HH, SALON,MODO"
					+ " FROM ENOC.CARGA_ACADEMICA"
					+ " WHERE CARGA_ID = ?"
					+ " AND PLAN_ID = ?"
					+ " AND MODALIDAD_ID = TO_NUMBER(?,'99') "+ orden;
			Object[] parametros = new Object[] {cargaId,planId, modalidades};
			lista = enocJdbc.query(comando, new CargaAcademicaMapper(), parametros);	
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.CargaAcademicaDao|getListaCarga|:"+ex);
		}		
		return lista;
	}
	
	public List<CargaAcademica> lisCargaEnLinea( String cargaId, String planId, String orden ) {
		List<CargaAcademica> lista	= new ArrayList<CargaAcademica>();
		String comando	= "";
		
		try{
			comando = " SELECT CURSO_CARGA_ID, CARGA_ID,"
					+ " BLOQUE_ID, "
					+ " CODIGO_PERSONAL, NOMBRE, CARRERA_ID, GRUPO,"
					+ " MODALIDAD_ID,"
					+ " ESTADO,"
					+ " TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO,"
					+ " TO_CHAR(F_FINAL,'DD/MM/YYYY') F_FINAL,"
					+ " TO_CHAR(F_ENTREGA,'DD/MM/YYYY') F_ENTREGA,"
					+ " ORIGEN, PLAN_ID, CURSO_ID, NOMBRE_CURSO,"
					+ " CICLO,"
					+ " TRIM(TO_CHAR(CREDITOS,'99.9')) CREDITOS,"
					+ " HT,"
					+ " HP,"
					+ " HI,"
					+ " NOTA_APROBATORIA,"
					+ " VALEUCAS, NUM_ALUM, SEMANAS, OPTATIVA, HORARIO, GRUPO_HORARIO, HH, SALON,MODO"
					+ " FROM ENOC.CARGA_ACADEMICA"
					+ " WHERE CARGA_ID = ?"
					+ " AND PLAN_ID = ?"
					+ " AND MODALIDAD_ID IN (SELECT MODALIDAD_ID FROM CAT_MODALIDAD WHERE ENLINEA = 'S') "+ orden;
			Object[] parametros = new Object[] {cargaId,planId};
			lista = enocJdbc.query(comando, new CargaAcademicaMapper(), parametros);	
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.CargaAcademicaDao|getListaCarga|:"+ex);
		}		
		return lista;
	}
	
	public List<CargaAcademica> getListaCargasyModalidades( String cargas, String modalidades, String tipo, String orden ) {
		List<CargaAcademica> lista	= new ArrayList<CargaAcademica>();
		String comando	= "";
		
		try{
			comando = " SELECT CURSO_CARGA_ID, CARGA_ID, BLOQUE_ID,"
					+ " CODIGO_PERSONAL, NOMBRE, CARRERA_ID, GRUPO, MODALIDAD_ID, ESTADO,"
					+ " TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO,"
					+ " TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL,"
					+ " TO_CHAR(F_ENTREGA,'DD/MM/YYYY') AS F_ENTREGA,"
					+ " ORIGEN, PLAN_ID, CURSO_ID, NOMBRE_CURSO,"
					+ " CICLO,"
					+ " CREDITOS,"
					+ " HT,"
					+ " HP,"
					+ " HI,"
					+ " NOTA_APROBATORIA,"
					+ " VALEUCAS, NUM_ALUM, SEMANAS, OPTATIVA, HORARIO, GRUPO_HORARIO, HH, SALON,MODO"
					+ " FROM ENOC.CARGA_ACADEMICA"
					+ " WHERE CARGA_ID IN ("+cargas+")"
					+ " AND MODALIDAD_ID IN ("+modalidades+")"
					+ " AND ORIGEN = ? "+orden;			
			lista = enocJdbc.query(comando, new CargaAcademicaMapper(),tipo);
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.CargaAcademicaDao|getListaCargasyModalidades|:"+ex);
		}		
		return lista;
	}
	
	public List<CargaAcademica> lisPorCargas( String cargas, String tipo, String orden ) {
		List<CargaAcademica> lista	= new ArrayList<CargaAcademica>();
		String comando	= "";
		
		try{
			comando = " SELECT CURSO_CARGA_ID, CARGA_ID, BLOQUE_ID,"
					+ " CODIGO_PERSONAL, NOMBRE, CARRERA_ID, GRUPO, MODALIDAD_ID, ESTADO,"
					+ " TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO,"
					+ " TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL,"
					+ " TO_CHAR(F_ENTREGA,'DD/MM/YYYY') AS F_ENTREGA,"
					+ " ORIGEN, PLAN_ID, CURSO_ID, NOMBRE_CURSO,"
					+ " CICLO,"
					+ " CREDITOS,"
					+ " HT,"
					+ " HP,"
					+ " HI,"
					+ " NOTA_APROBATORIA,"
					+ " VALEUCAS, NUM_ALUM, SEMANAS, OPTATIVA, HORARIO, GRUPO_HORARIO, HH, SALON,MODO"
					+ " FROM ENOC.CARGA_ACADEMICA"
					+ " WHERE CARGA_ID IN ("+cargas+")"					
					+ " AND ORIGEN = ? "+orden;			
			lista = enocJdbc.query(comando, new CargaAcademicaMapper(),tipo);
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.CargaAcademicaDao|getListaCargasyModalidades|:"+ex);
		}		
		return lista;
	}
	
	// Lista de materias de una facultad 
	public List<CargaAcademica> listaCargaFacultad( String cargaId, String facultadId, String orden ) {
		List<CargaAcademica> lista	= new ArrayList<CargaAcademica>();
		String comando	= "";
		
		try{
			comando = " SELECT CURSO_CARGA_ID, CARGA_ID,"
					+ " BLOQUE_ID,"
					+ " CODIGO_PERSONAL, NOMBRE, CARRERA_ID, GRUPO,"
					+ " MODALIDAD_ID,"
					+ " ESTADO,"
					+ " TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO,"
					+ " TO_CHAR(F_FINAL,'DD/MM/YYYY') F_FINAL,"
					+ " TO_CHAR(F_ENTREGA,'DD/MM/YYYY') F_ENTREGA,"
					+ " ORIGEN, PLAN_ID, CURSO_ID, NOMBRE_CURSO,"
					+ " CICLO,"
					+ " TRIM(TO_CHAR(CREDITOS,'99.9')) CREDITOS,"
					+ " HT,"
					+ " HP,"
					+ " HI,"
					+ " NOTA_APROBATORIA,"
					+ " VALEUCAS, NUM_ALUM, SEMANAS, OPTATIVA, HORARIO, GRUPO_HORARIO, HH, SALON,MODO"
					+ " FROM ENOC.CARGA_ACADEMICA"
					+ " WHERE CARGA_ID = ?"
					+ " AND ENOC.FACULTAD(CARRERA_ID) = '"+facultadId+"'"
				+ " "+orden;
			
			lista = enocJdbc.query(comando, new CargaAcademicaMapper(),cargaId);
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.CargaAcademicaDao|listaCargaFacultad|:"+ex);
		}
		return lista;
	}
	
	public List<CargaAcademica> getListaCarrera( String cargaId, String carreraId, String orden ) {
		List<CargaAcademica> lista		= new ArrayList<CargaAcademica>();
		String comando		= "";
		
		try{
			comando = "SELECT "+
				"CURSO_CARGA_ID, CARGA_ID, "+
				"BLOQUE_ID, "+
				"CODIGO_PERSONAL, NOMBRE, CARRERA_ID, GRUPO, "+
				"MODALIDAD_ID, "+
				"ESTADO, "+
				"TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO, "+
				"TO_CHAR(F_FINAL,'DD/MM/YYYY') F_FINAL, "+
				"TO_CHAR(F_ENTREGA,'DD/MM/YYYY') F_ENTREGA, "+
				"ORIGEN, PLAN_ID, CURSO_ID, NOMBRE_CURSO, "+
				"CICLO, "+
				"TRIM(TO_CHAR(CREDITOS,'99.9')) CREDITOS, "+
				"HT, "+
				"HP, "+
				"HI, "+
				"NOTA_APROBATORIA, "+
				"VALEUCAS, NUM_ALUM, SEMANAS, OPTATIVA, HORARIO, GRUPO_HORARIO, HH, SALON,MODO "+
				"FROM ENOC.CARGA_ACADEMICA "+ 
				"WHERE CARGA_ID = ? "+
				"AND CARRERA_ID = ?"+orden;			
			lista = enocJdbc.query(comando, new CargaAcademicaMapper(),cargaId,carreraId);
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.CargaAcademicaDao|getListaCarrera|:"+ex);
		}
		return lista;
	}	
	
	public List<CargaAcademica> lisMateriasDelPlan( String cargaId, String planId, String orden ) {
		List<CargaAcademica> lista		= new ArrayList<CargaAcademica>();
		
		try{
			String comando = "SELECT "+
				"CURSO_CARGA_ID, CARGA_ID, "+
				"BLOQUE_ID, "+
				"CODIGO_PERSONAL, NOMBRE, CARRERA_ID, GRUPO, "+
				"MODALIDAD_ID, "+
				"ESTADO, "+
				"TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO, "+
				"TO_CHAR(F_FINAL,'DD/MM/YYYY') F_FINAL, "+
				"TO_CHAR(F_ENTREGA,'DD/MM/YYYY') F_ENTREGA, "+
				"ORIGEN, PLAN_ID, CURSO_ID, NOMBRE_CURSO, "+
				"CICLO, "+
				"TRIM(TO_CHAR(CREDITOS,'99.9')) CREDITOS, "+
				"HT, "+
				"HP, "+
				"HI, "+
				"NOTA_APROBATORIA, "+
				"VALEUCAS, NUM_ALUM, SEMANAS, OPTATIVA, HORARIO, GRUPO_HORARIO, HH, SALON,MODO "+
				"FROM ENOC.CARGA_ACADEMICA "+ 
				"WHERE CARGA_ID = ?"+
				"AND PLAN_ID = ? "+orden;
			
			lista = enocJdbc.query(comando, new CargaAcademicaMapper(),cargaId,planId);
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.CargaAcademicaDao|lisMateriasDelPlan|:"+ex);
		}
		return lista;		
	}
	
	public List<CargaAcademica> lisMateriasAlta( String planId, String solicitudId, String orden ) {
		List<CargaAcademica> lista		= new ArrayList<CargaAcademica>();
		
		try{
			String comando = "SELECT "
					+ " CURSO_CARGA_ID, CARGA_ID,"
					+ " BLOQUE_ID, "
					+ " CODIGO_PERSONAL, NOMBRE, CARRERA_ID, GRUPO,"
					+ " MODALIDAD_ID,"
					+ " ESTADO,"
					+ " TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO, "
					+ " TO_CHAR(F_FINAL,'DD/MM/YYYY') F_FINAL, "
					+ " TO_CHAR(F_ENTREGA,'DD/MM/YYYY') F_ENTREGA, "
					+ " ORIGEN, PLAN_ID, CURSO_ID, NOMBRE_CURSO, "
					+ " CICLO, "
					+ " TRIM(TO_CHAR(CREDITOS,'99.9')) CREDITOS, "
					+ " HT, "
					+ " HP, "
					+ " HI, "
					+ " NOTA_APROBATORIA, "
					+ " VALEUCAS, NUM_ALUM, SEMANAS, OPTATIVA, HORARIO, GRUPO_HORARIO, HH, SALON,MODO "
					+ " FROM ENOC.CARGA_ACADEMICA "
					+ " WHERE PLAN_ID = ?"
					+ " AND CURSO_CARGA_ID IN(SELECT CURSO_CARGA_ID FROM ENOC.CAMBIO_MATERIA WHERE SOLICITUD_ID = TO_NUMBER(?,'99999')) "+orden;
			
			Object[] parametros = new Object[] {planId, solicitudId};			
			lista = enocJdbc.query(comando, new CargaAcademicaMapper(),parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.CargaAcademicaDao|lisMateriasAlta|:"+ex);
		}
		return lista;		
	}
	
	public List<CargaAcademica> lisMateriasSimilares( String cargaId, String cursoId, String orden) {
		List<CargaAcademica> lista		= new ArrayList<CargaAcademica>();
		try{
			String comando = "SELECT "+
				"CURSO_CARGA_ID, CARGA_ID, "+
				"BLOQUE_ID, "+
				"CODIGO_PERSONAL, NOMBRE, CARRERA_ID, GRUPO, "+
				"MODALIDAD_ID, "+
				"ESTADO, "+
				"TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO, "+
				"TO_CHAR(F_FINAL,'DD/MM/YYYY') F_FINAL, "+
				"TO_CHAR(F_ENTREGA,'DD/MM/YYYY') F_ENTREGA, "+
				"ORIGEN, PLAN_ID, CURSO_ID, NOMBRE_CURSO, "+
				"CICLO, "+
				"TRIM(TO_CHAR(CREDITOS,'99.9')) CREDITOS, "+
				"HT, "+
				"HP, "+
				"HI, "+
				"NOTA_APROBATORIA, "+
				"VALEUCAS, NUM_ALUM, SEMANAS, OPTATIVA, HORARIO, GRUPO_HORARIO, HH, SALON,MODO "+
				"FROM ENOC.CARGA_ACADEMICA "+ 
				"WHERE CARGA_ID = ? "+
				"AND CURSO_ID = ? "+orden;		
			lista = enocJdbc.query(comando, new CargaAcademicaMapper(), cargaId, cursoId);			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.CargaAcademicaDao|lisMateriasSimilares|:"+ex);
		}
		return lista;		
	}
	
	public List<CargaAcademica> lisMateriasProximas( String planId, String orden) {
		List<CargaAcademica> lista		= new ArrayList<CargaAcademica>();
		try{
			String comando = "SELECT CURSO_CARGA_ID, CARGA_ID, "
				+ " BLOQUE_ID, "
				+ " CODIGO_PERSONAL, NOMBRE, CARRERA_ID, GRUPO, "
				+ " MODALIDAD_ID, "
				+ " ESTADO, "
				+ " TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO, "
				+ " TO_CHAR(F_FINAL,'DD/MM/YYYY') F_FINAL, "
				+ " TO_CHAR(F_ENTREGA,'DD/MM/YYYY') F_ENTREGA, "
				+ " ORIGEN, PLAN_ID, CURSO_ID, NOMBRE_CURSO, "
				+ " CICLO, "
				+ " TRIM(TO_CHAR(CREDITOS,'99.9')) CREDITOS, "
				+ " HT, "
				+ " HP, "
				+ " HI, "
				+ " NOTA_APROBATORIA, "
				+ " VALEUCAS, NUM_ALUM, SEMANAS, OPTATIVA, HORARIO, GRUPO_HORARIO, HH, SALON,MODO "
				+ " FROM ENOC.CARGA_ACADEMICA"
				+ " WHERE PLAN_ID = ? "
				+ " AND (ENOC.CARGA_ACADEMICA.F_INICIO >= SYSDATE OR SYSDATE BETWEEN ENOC.CARGA_ACADEMICA.F_INICIO AND ENOC.CARGA_ACADEMICA.F_FINAL) "+orden;		
			lista = enocJdbc.query(comando, new CargaAcademicaMapper(), planId);			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.CargaAcademicaDao|lisMateriasProximas|:"+ex);
		}
		return lista;		
	}
	
	public List<String> lisCiclosProximos( String planId) {
		List<String> lista		= new ArrayList<String>();
		try{
			String comando = "SELECT DISTINCT(CICLO) FROM ENOC.CARGA_ACADEMICA"
				+ " WHERE PLAN_ID = ? "
				+ " AND (ENOC.CARGA_ACADEMICA.F_INICIO >= SYSDATE OR SYSDATE BETWEEN ENOC.CARGA_ACADEMICA.F_INICIO AND ENOC.CARGA_ACADEMICA.F_FINAL) ORDER BY CICLO";		
			lista = enocJdbc.queryForList(comando, String.class, planId);			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.CargaAcademicaDao|lisCiclosProximos|:"+ex);
		}
		return lista;		
	}
	
	public List<CargaAcademica> lisMateriasDelGrupo( String cargaId, String carreraId, String grupo, String bloqueId, String orden ) {
		List<CargaAcademica> lista		= new ArrayList<CargaAcademica>();
		String comando		= "";
		
		try{
			comando = " SELECT CURSO_CARGA_ID, CARGA_ID, BLOQUE_ID,"
					+ " CODIGO_PERSONAL, NOMBRE, CARRERA_ID, GRUPO,"
					+ " MODALIDAD_ID, ESTADO, "
					+ " TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO,"
					+ " TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL,"
					+ " TO_CHAR(F_ENTREGA,'DD/MM/YYYY') AS F_ENTREGA,"
					+ " ORIGEN, PLAN_ID, CURSO_ID, NOMBRE_CURSO,"
					+ " CICLO, TRIM(TO_CHAR(CREDITOS,'99.9')) CREDITOS,"
					+ " HT, HP, HI, NOTA_APROBATORIA, VALEUCAS, NUM_ALUM,"
					+ " SEMANAS, OPTATIVA, HORARIO, GRUPO_HORARIO, HH, SALON,MODO"
					+ " FROM ENOC.CARGA_ACADEMICA"
					+ " WHERE CARGA_ID = ?"
					+ " AND CARRERA_ID = ? "
					+ " AND (GRUPO = ? OR GRUPO_HORARIO = ?)"
					+ " AND BLOQUE_ID = TO_NUMBER(?,'99') " + orden;
			Object[] parametros = new Object[] {cargaId, carreraId, grupo, grupo, bloqueId};
			lista = enocJdbc.query(comando, new CargaAcademicaMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.CargaAcademicaDao|lisMateriasDelGrupo|:"+ex);
		}
		return lista;		
	}
	
	public List<CargaAcademica> getListaMaestro( String cargaId, String codigoPersonal, String orden ) {
		List<CargaAcademica> lista	= new ArrayList<CargaAcademica>();		
		try{
			String comando = "SELECT "+
				"CURSO_CARGA_ID, CARGA_ID, "+
				"BLOQUE_ID, "+
				"CODIGO_PERSONAL, NOMBRE, CARRERA_ID, GRUPO, "+
				"MODALIDAD_ID, "+
				"ESTADO, "+
				"TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO, "+
				"TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL, "+
				"TO_CHAR(F_ENTREGA,'DD/MM/YYYY') AS F_ENTREGA, "+
				"ORIGEN, PLAN_ID, CURSO_ID, NOMBRE_CURSO, "+
				"CICLO, "+
				"TRIM(TO_CHAR(CREDITOS,'99.99')) AS CREDITOS, "+
				"HT, "+
				"HP, "+
				"HI, "+
				"NOTA_APROBATORIA, "+
				"VALEUCAS, NUM_ALUM, SEMANAS, OPTATIVA, HORARIO, GRUPO_HORARIO, HH, SALON,MODO "+
				"FROM ENOC.CARGA_ACADEMICA "+ 
				"WHERE CARGA_ID = ?"
				+ " AND (CODIGO_PERSONAL = ? OR CODIGO_OTRO = ?)"+
				"AND ORIGEN = 'O' "+orden;		
			lista = enocJdbc.query(comando, new CargaAcademicaMapper(),cargaId,codigoPersonal,codigoPersonal);	
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.CargaAcademicaDao|getListaMaestro|:"+ex);
		}
		return lista;
	}
	
	public List<CargaAcademica> lisPorMaestro( String codigoPersonal, String orden ) {
		List<CargaAcademica> lista	= new ArrayList<CargaAcademica>();	
		try{
			String comando = "SELECT "+
				"CURSO_CARGA_ID, CARGA_ID, "+
				"BLOQUE_ID, "+
				"CODIGO_PERSONAL, NOMBRE, CARRERA_ID, GRUPO, "+
				"MODALIDAD_ID, "+
				"ESTADO, "+
				"TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO, "+
				"TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL, "+
				"TO_CHAR(F_ENTREGA,'DD/MM/YYYY') AS F_ENTREGA, "+
				"ORIGEN, PLAN_ID, CURSO_ID, NOMBRE_CURSO, "+
				"CICLO, "+
				"TRIM(TO_CHAR(CREDITOS,'99.99')) AS CREDITOS, "+
				"HT, "+
				"HP, "+
				"HI, "+
				"NOTA_APROBATORIA, "+
				"VALEUCAS, NUM_ALUM, SEMANAS, OPTATIVA, HORARIO, GRUPO_HORARIO, HH, SALON,MODO "+
				"FROM ENOC.CARGA_ACADEMICA "+ 
				"WHERE CODIGO_PERSONAL = ? "+
				"AND ORIGEN = 'O' "+orden;			
			lista = enocJdbc.query(comando, new CargaAcademicaMapper(), codigoPersonal);			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.CargaAcademicaDao|lisPorMaestro|:"+ex);
		}
		return lista;
	}
	
	public List<CargaAcademica> getListSinAlumno( String cargaId, String orden ) {
		List<CargaAcademica> lista	= new ArrayList<CargaAcademica>();
		try{
			String comando = " SELECT CURSO_CARGA_ID, CARGA_ID, BLOQUE_ID, CODIGO_PERSONAL, NOMBRE, CARRERA_ID, GRUPO,"
					+ " MODALIDAD_ID, ESTADO, TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO, "
					+ " TO_CHAR(F_FINAL,'DD/MM/YYYY') F_FINAL,"
					+ " TO_CHAR(F_ENTREGA,'DD/MM/YYYY') F_ENTREGA,"
					+ " ORIGEN, PLAN_ID, CURSO_ID, NOMBRE_CURSO,"
					+ " CICLO, TRIM(TO_CHAR(CREDITOS,'99.9')) CREDITOS,"
					+ " HT, HP, HI, NOTA_APROBATORIA, VALEUCAS, NUM_ALUM, SEMANAS, OPTATIVA, HORARIO, GRUPO_HORARIO, HH, SALON,MODO"
					+ " FROM ENOC.CARGA_ACADEMICA WHERE CARGA_ID = ? "
					+ " AND ORIGEN = 'O'"
					+ " AND CURSO_CARGA_ID NOT IN"
					+ " 	(SELECT CURSO_CARGA_ID FROM ENOC.KRDX_CURSO_ACT WHERE CURSO_CARGA_ID = CARGA_ACADEMICA.CURSO_CARGA_ID) "+orden;
			
			lista = enocJdbc.query(comando, new CargaAcademicaMapper(),cargaId);
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.CargaAcademicaDao|getListSinAlumno|:"+ex);
		}
		return lista;
	}
	
	public List<CargaAcademica> lisMateriasEnCarga( String cargaId, String orden ){
		List<CargaAcademica> lista	= new ArrayList<CargaAcademica>();
		try{
			String comando = "SELECT CURSO_CARGA_ID, CARGA_ID, BLOQUE_ID, "+ 
				" CODIGO_PERSONAL, NOMBRE, CARRERA_ID, GRUPO, "+
				" MODALIDAD_ID, "+
				" ESTADO, TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO, "+ 
				" TO_CHAR(F_FINAL,'DD/MM/YYYY') F_FINAL, "+
				" TO_CHAR(F_ENTREGA,'DD/MM/YYYY') F_ENTREGA, "+ 
				" ORIGEN, PLAN_ID, CURSO_ID, NOMBRE_CURSO, "+
				" CICLO, TRIM(TO_CHAR(CREDITOS,'99.9')) CREDITOS, "+ 
				" HT, HP, HI, "+
				" NOTA_APROBATORIA," +
				" VALEUCAS, NUM_ALUM, SEMANAS, OPTATIVA, HORARIO, GRUPO_HORARIO, HH, SALON,MODO "+
				" FROM ENOC.CARGA_ACADEMICA WHERE CARGA_ID = ?"+ 
				" AND ORIGEN = 'O' " +orden;			
			lista = enocJdbc.query(comando, new CargaAcademicaMapper(),cargaId);
		
		}catch(Exception ex){
			System.out.println("Error - acavista.spring.CargaAcademicaDao|getListaMaestro|:"+ex);
		}
		return lista;
	}
	
	public List<CargaAcademica> getListaMaestro( String cargaId, String orden ) {
		List<CargaAcademica> lista	= new ArrayList<CargaAcademica>();
		try{
			String comando = "SELECT "+ 
				"CURSO_CARGA_ID, CARGA_ID, BLOQUE_ID, "+ 
				"CODIGO_PERSONAL, NOMBRE, CARRERA_ID, GRUPO, "+
				"MODALIDAD_ID, "+
				"ESTADO, TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO, "+ 
				"TO_CHAR(F_FINAL,'DD/MM/YYYY') F_FINAL, "+
				"TO_CHAR(F_ENTREGA,'DD/MM/YYYY') F_ENTREGA, "+ 
				"ORIGEN, PLAN_ID, CURSO_ID, NOMBRE_CURSO, "+
				"CICLO, TRIM(TO_CHAR(CREDITOS,'99.9')) CREDITOS, "+ 
				"HT, HP, HI, "+
				"NOTA_APROBATORIA," +
				"VALEUCAS, NUM_ALUM, SEMANAS, OPTATIVA, HORARIO, GRUPO_HORARIO, HH, SALON,MODO "+
				"FROM ENOC.CARGA_ACADEMICA WHERE CARGA_ID = ? "+ 
				"AND ORIGEN = 'O' "+
				"AND CURSO_CARGA_ID  IN (SELECT CURSO_CARGA_ID "+ 
					"FROM ENOC.KRDX_CURSO_ACT "+ 
					"WHERE CURSO_CARGA_ID = CARGA_ACADEMICA.CURSO_CARGA_ID)"+orden;
			
			lista = enocJdbc.query(comando, new CargaAcademicaMapper(),cargaId);
		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.CargaAcademicaDao|getListaMaestro|:"+ex);
		}
		return lista;
	}
	
	public List<CargaAcademica> getListaCargaCarrera( String cargaId, String carreraId, String orden ) {
		List<CargaAcademica> lista		= new ArrayList<CargaAcademica>();
		try{
			String comando = "SELECT "+
				"CURSO_CARGA_ID, CARGA_ID, "+
				"BLOQUE_ID, "+
				"CODIGO_PERSONAL, NOMBRE, CARRERA_ID, GRUPO, "+
				"MODALIDAD_ID, "+
				"ESTADO, "+
				"TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO, "+
				"TO_CHAR(F_FINAL,'DD/MM/YYYY') F_FINAL, "+
				"TO_CHAR(F_ENTREGA,'DD/MM/YYYY') F_ENTREGA, "+
				"ORIGEN, PLAN_ID, CURSO_ID, NOMBRE_CURSO, "+
				"CICLO, "+
				"TRIM(TO_CHAR(CREDITOS,'99.9')) CREDITOS, "+
				"HT, "+
				"HP, HI, "+
				"NOTA_APROBATORIA, "+
				"VALEUCAS, NUM_ALUM, SEMANAS, OPTATIVA, HORARIO, GRUPO_HORARIO, HH, SALON,MODO "+
				"FROM ENOC.CARGA_ACADEMICA "+ 
				"WHERE CARGA_ID = ? "+
				"AND CARRERA_ID = ? AND ORIGEN = 'O' " +orden;			
			lista = enocJdbc.query(comando, new CargaAcademicaMapper(),cargaId,carreraId);			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.CargaAcademicaDao|getListaCargaCarrera|:"+ex);
		}
		return lista;
	}
	
	public List<String> getListaCursoIdPorCondicion( String condiciones) {
		List<String> lista = new ArrayList<String>();
		try{
			String comando = " SELECT DISTINCT(A.CURSO_ID) FROM ENOC.CARGA_ACADEMICA A "+condiciones;		
			Object[] parametros = new Object[] {condiciones};	
			lista = enocJdbc.queryForList(comando, String.class, parametros);		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.CargaAcademicaDao|getListaCursoIdPorCondicion|:"+ex);
		}
		return lista;
	}
	
	public List<CargaAcademica> getMateriasPorCarga( String codigoPersonal, String cargaId, String orden) {
		List<CargaAcademica> lista = new ArrayList<CargaAcademica>();		
		try{
			String comando = "SELECT * FROM ENOC.CARGA_ACADEMICA WHERE CODIGO_PERSONAL = ? AND CARGA_ID IN ("+cargaId+") AND ORIGEN = 'O' "+orden;
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.query(comando, new CargaAcademicaMapper(), parametros);		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.CargaAcademicaDao|getMateriasPorCarga|:"+ex);
		}
		return lista;
	}	
	
	public HashMap<String,String> mapMatPlanCarga(String cargaId) {
		HashMap<String,String> mapa		= new HashMap<String, String>();
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT PLAN_ID||CARGA_ID AS LLAVE, COUNT(*) AS VALOR FROM ENOC.CARGA_ACADEMICA"
					+ " WHERE CARGA_ID = ?"
					+ " GROUP BY PLAN_ID||CARGA_ID"; 
			
			lista = enocJdbc.query(comando, new aca.MapaMapper(),new Object[] {cargaId});
			for (aca.Mapa map : lista){			
				mapa.put(map.getLlave(), map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.CargaAcademicaDao|mapMatPlanCarga|:"+ex);
		}
		return mapa;
	}
	
	
	public List<CargaAcademica> lisPorCargaBloqueyOrigen( String cargaId, String origen, String orden ) { 
		List<CargaAcademica> lista	= new ArrayList<CargaAcademica>();
		try{
			String comando = "SELECT CURSO_CARGA_ID, CARGA_ID, BLOQUE_ID, CODIGO_PERSONAL, NOMBRE, CARRERA_ID, GRUPO, MODALIDAD_ID, ESTADO,"
					+ " TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO,"
					+ " TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL,"
					+ " TO_CHAR(F_ENTREGA,'DD/MM/YYYY') AS F_ENTREGA,"
					+ " ORIGEN, PLAN_ID, CURSO_ID, NOMBRE_CURSO,"
					+ " CICLO, TRIM(TO_CHAR(CREDITOS,'99.9')) AS CREDITOS,"
					+ " HT, HP, HI, NOTA_APROBATORIA, VALEUCAS, NUM_ALUM, SEMANAS, OPTATIVA, HORARIO, GRUPO_HORARIO, HH, SALON,MODO"
					+ " FROM ENOC.CARGA_ACADEMICA"
					+ " WHERE CARGA_ID = ?"
					+ " AND BLOQUE_ID = TO_NUMBER(?,'99')"
					+ " AND ORIGEN IN ("+origen+") "+orden;
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new CargaAcademicaMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.CargaAcademicaDao|getListaCarga|:"+ex);
		}		
		return lista;
	}
	
	
	public HashMap<String,String> mapMatPorMaestro( String cargas, String modalidades) {
		HashMap<String,String> mapa		= new HashMap<String, String>();
		String comando	             	= "";	
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();
		
		try{
			comando = " SELECT CODIGO_PERSONAL, COUNT(*) AS TOTAL FROM ENOC.CARGA_ACADEMICA"
					+ " WHERE ORIGEN = 'O'"
					+ " AND CARGA_ID IN ("+cargas+")"
					+ " AND MODALIDAD_ID IN ("+modalidades+")"
					+ " GROUP BY CODIGO_PERSONAL"; 
			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map:lista){			
				mapa.put(map.getLlave(), (String)map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.CargaAcademicaDao|mapMatPorMaestro|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,String> mapCreditosPorMaestro( String cargas) {
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa		= new HashMap<String, String>();
		try{
			String comando = " SELECT CODIGO_PERSONAL AS LLAVE, SUM(CREDITOS) AS TOTAL FROM ENOC.CARGA_ACADEMICA"
					+ " WHERE ORIGEN = 'O'"
					+ " AND CARGA_ID = ?"					
					+ " GROUP BY CODIGO_PERSONAL"; 
			
			lista = enocJdbc.query(comando, new aca.MapaMapper(),cargas);
			for (aca.Mapa map:lista){			
				mapa.put(map.getLlave(), (String)map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.CargaAcademicaDao|mapCreditosPorMaestro|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String,String> mapaMateriasOfrecidas( String cargaId, String modalidadId){
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa		= new HashMap<String, String>();		
		try{
			String comando = "SELECT PLAN_ID AS LLAVE, COUNT(*) AS VALOR FROM ENOC.CARGA_ACADEMICA WHERE CARGA_ID = ? AND MODALIDAD_ID = TO_NUMBER(?,'99') GROUP BY PLAN_ID";
			Object[] parametros = new Object[] {cargaId, modalidadId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map:lista){			
				mapa.put(map.getLlave(), (String)map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.CargaAcademicaDao|mapaMateriasOfertadas|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String,String> mapaMateriasPorCarrera( String cargaId, String origen){
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa		= new HashMap<String, String>();		
		try{
			String comando = "SELECT CARRERA(PLAN_ID) AS LLAVE, COUNT(*) AS VALOR FROM ENOC.CARGA_ACADEMICA WHERE CARGA_ID = ? AND ORIGEN IN ("+origen+") GROUP BY CARRERA(PLAN_ID)";
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map:lista){			
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.CargaAcademicaDao|mapaMateriasPorCarrera|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String,String> mapaEsOfertada( String cargaId, String modalidadId){
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa		= new HashMap<String, String>();		
		try{
			String comando = "SELECT CURSO_ID AS LLAVE, COUNT(*) AS VALOR FROM ENOC.CARGA_ACADEMICA WHERE CARGA_ID = ? AND MODALIDAD_ID = TO_NUMBER(?, '999') GROUP BY CURSO_ID";
			Object[] parametros = new Object[] {cargaId, modalidadId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map:lista){			
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.CargaAcademicaDao|mapaEsOfertada|:"+ex);
		}		
		return mapa;
	}
	
	
	public HashMap<String,CargaAcademica> mapaPorCargaBloqueyOrigen(String cargaId, String bloqueId, String origen){
		HashMap<String,CargaAcademica> mapa		= new HashMap<String, CargaAcademica>();
		List<CargaAcademica> lista	= new ArrayList<CargaAcademica>();		
		try{			 
			String comando = "SELECT CURSO_CARGA_ID, CARGA_ID, BLOQUE_ID, CODIGO_PERSONAL, NOMBRE, CARRERA_ID, GRUPO, MODALIDAD_ID, ESTADO,"
					+ " TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO,"
					+ " TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL,"
					+ " TO_CHAR(F_ENTREGA,'DD/MM/YYYY') AS F_ENTREGA,"
					+ " ORIGEN, PLAN_ID, CURSO_ID, NOMBRE_CURSO,"
					+ " CICLO, TRIM(TO_CHAR(CREDITOS,'99.9')) AS CREDITOS,"
					+ " HT, HP, HI, NOTA_APROBATORIA, VALEUCAS, NUM_ALUM, SEMANAS, OPTATIVA, HORARIO, GRUPO_HORARIO, HH, SALON, MODO"
					+ " FROM ENOC.CARGA_ACADEMICA"
					+ " WHERE CARGA_ID = ?"
					+ " AND BLOQUE_ID = TO_NUMBER(?,'99')"
					+ " AND ORIGEN IN ("+origen+")";			
			lista = enocJdbc.query(comando, new CargaAcademicaMapper(),cargaId, bloqueId);
			for (CargaAcademica carga : lista){
				mapa.put(carga.getCursoCargaId(), carga);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.CargaAcademicaDao|mapaPorCargaBloqueyOrigen|:"+ex);
		}
		return mapa;
	}	
	
	public HashMap<String,String> mapaRequiereSalon( String cargaId){
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa		= new HashMap<String, String>();		
		try{
			String comando = "SELECT PLAN_ID AS LLAVE, COUNT(PLAN_ID) AS VALOR FROM ENOC.CARGA_ACADEMICA WHERE CARGA_ID = ? AND ORIGEN = 'O' AND MODO != 'V' AND SALON = 'S' GROUP BY PLAN_ID";
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map:lista){			
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.CargaAcademicaDao|mapaRequiereSalon|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String,String> mapaMateriasEnPlan( String cargaId, String bloqueId, String planId){
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa		= new HashMap<String, String>();		
		try{
			String comando = "SELECT CURSO_CARGA_ID AS LLAVE, ORIGEN AS VALOR FROM ENOC.CARGA_ACADEMICA WHERE CARGA_ID = ? AND BLOQUE_ID = TO_NUMBER(?,'99') AND PLAN_ID = ?";
			Object[] parametros = new Object[] {cargaId, bloqueId, planId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map:lista){			
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.CargaAcademicaDao|mapaRequiereSalon|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String,String> mapaRequiereHorario( String cargaId){
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa		= new HashMap<String, String>();		
		try{
			String comando = "SELECT PLAN_ID AS LLAVE, COUNT(PLAN_ID) AS VALOR FROM ENOC.CARGA_ACADEMICA WHERE CARGA_ID = ? AND ORIGEN = 'O' AND MODO IN ('P','H','V','R') AND HORARIO = 'S' GROUP BY PLAN_ID";
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map:lista){			
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.CargaAcademicaDao|mapaRequiereHorario|:"+ex);
		}		
		return mapa;
	}
	public HashMap<String,String> mapaGruposPorPlan( String cargaId, String origen){
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa		= new HashMap<String, String>();		
		try{
			String comando = "SELECT PLAN_ID AS LLAVE, COUNT(CURSO_CARGA_ID) AS VALOR FROM ENOC.CARGA_ACADEMICA WHERE CARGA_ID = ? AND ORIGEN IN ("+origen+") GROUP BY PLAN_ID";
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map:lista){			
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.CargaAcademicaDao|mapaMateriasPorCarrera|:"+ex);
		}		
		return mapa;
	}
	public HashMap<String, String> mapMaestrosPorCargaAndPlan( String cargaId) {
		HashMap<String, String> mapaMaestros = new HashMap<String, String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT PLAN_ID AS LLAVE, COUNT(DISTINCT(CURSO_CARGA_ID)) AS VALOR FROM ENOC.CARGA_ACADEMICA"
				+ " WHERE CARGA_ID = ? AND ORIGEN = 'O'"
				+ " AND CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.MAESTROS) GROUP BY PLAN_ID";
			Object[] parametros = new Object[] {cargaId };
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa map : lista) {
				mapaMaestros.put(map.getLlave(), (String)map.getValor());
			}	

		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaAcademicaDao|mapMaestrosPorCargaAndPlan|:"+ex);
		}

		return mapaMaestros;
	}
	public HashMap<String, String> mapHorariosPorPlanAndCarga( String cargaId) {
		HashMap<String, String> mapaHorarios = new HashMap<String, String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();	
		try{
			String comando = " SELECT PLAN_ID AS LLAVE, COALESCE(COUNT(CA.CURSO_CARGA_ID),0) AS VALOR"
					  + " FROM ENOC.CARGA_ACADEMICA CA"
					  + " WHERE CARGA_ID = ?"
					  + " AND ORIGEN = 'O'"					  
					  + " AND (SELECT HORARIO FROM ENOC.MAPA_CURSO WHERE CURSO_ID = CA.CURSO_ID) = 'S'"
					  + " AND (SELECT COALESCE(SUM(HH),0) FROM ENOC.MAPA_CURSO WHERE CURSO_ID = CA.CURSO_ID) <= (SELECT COUNT(CURSO_CARGA_ID) FROM ENOC.CARGA_GRUPO_HORA WHERE CURSO_CARGA_ID = CA.CURSO_CARGA_ID)"
					  + " GROUP BY PLAN_ID";
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa map : lista) {
				mapaHorarios.put(map.getLlave(), (String)map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaAcademicaDao|getTienenHorariosCarga|:"+ex);
		}
		return mapaHorarios;
	}
	public HashMap<String, String> mapaTieneSalones( String cargaId) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT PLAN_ID AS LLAVE, COUNT(CURSO_CARGA_ID) AS VALOR FROM ENOC.CARGA_ACADEMICA"
					+ " WHERE CARGA_ID = ?"
					+ " AND ORIGEN = 'O'"
					+ " AND MODO IN('P','R','H')"					
					+ " AND CURSO_CARGA_ID IN (SELECT DISTINCT(CURSO_CARGA_ID) FROM ENOC.CARGA_GRUPO_HORA WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ?)"
					+ " AND CURSO_CARGA_ID NOT IN (SELECT DISTINCT(CURSO_CARGA_ID) FROM ENOC.CARGA_GRUPO_HORA WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ? AND SALON_ID = '0')"
					+ " GROUP BY PLAN_ID";
			Object[] parametros = new Object[] {cargaId, cargaId, cargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa map : lista) {
				mapa.put(map.getLlave(), (String)map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaAcademicaDao|mapaTieneSalones|:"+ex);
		}
		return mapa;
	}
	public HashMap<String, String> mapCursosPorCiclo(String cargaId){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();	
		try{			
			String comando = "SELECT PLAN_ID||CICLO AS LLAVE, COUNT(CURSO_CARGA_ID) AS VALOR FROM ENOC.CARGA_ACADEMICA WHERE CARGA_ID = ? AND ORIGEN IN('O','E') GROUP BY PLAN_ID,CICLO";
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map:lista){			
				mapa.put(map.getLlave(), (String)map.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.CargaAcademicaDao|mapCursosPorCiclo|:"+ex);
		
		}
		return mapa;
	}
	public HashMap<String, String> mapaGruposModos(String cargaId){
		HashMap<String, String> mapaMaestros = new HashMap<String, String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT PLAN_ID||MODO AS LLAVE, COUNT(CURSO_CARGA_ID) AS VALOR FROM ENOC.CARGA_ACADEMICA WHERE CARGA_ID = ? AND ORIGEN = 'O' GROUP BY PLAN_ID||MODO";
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa map : lista) {
				mapaMaestros.put(map.getLlave(), (String)map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|mapaGruposModos|:"+ex);
		}

		return mapaMaestros;
	}
	public List<CargaAcademica> lisPorCargayPlan( String cargaId, String planId, String orden ) {
		List<CargaAcademica> lista	= new ArrayList<CargaAcademica>();		
		try{
			String comando = " SELECT CURSO_CARGA_ID, CARGA_ID,"
					+ " BLOQUE_ID, "
					+ " CODIGO_PERSONAL, NOMBRE, CARRERA_ID, GRUPO,"
					+ " MODALIDAD_ID,"
					+ " ESTADO,"
					+ " TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO,"
					+ " TO_CHAR(F_FINAL,'DD/MM/YYYY') F_FINAL,"
					+ " TO_CHAR(F_ENTREGA,'DD/MM/YYYY') F_ENTREGA,"
					+ " ORIGEN, PLAN_ID, CURSO_ID, NOMBRE_CURSO,"
					+ " CICLO,"
					+ " TRIM(TO_CHAR(CREDITOS,'99.9')) CREDITOS,"
					+ " HT,"
					+ " HP,"
					+ " HI,"
					+ " NOTA_APROBATORIA,"
					+ " VALEUCAS, NUM_ALUM, SEMANAS, OPTATIVA, HORARIO, GRUPO_HORARIO, HH, SALON,MODO"
					+ " FROM ENOC.CARGA_ACADEMICA"
					+ " WHERE CARGA_ID = ?"
					+ " AND PLAN_ID = ? "+ orden;
			Object[] parametros = new Object[] {cargaId,planId};
			lista = enocJdbc.query(comando, new CargaAcademicaMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.CargaAcademicaDao|lisPorCargayPlan|:"+ex);
		}		
		return lista;
	}
	public HashMap<String, String> mapaMateriasRegistradas(String cargaId, String planId){
		HashMap<String, String> mapaMaestros = new HashMap<String, String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CURSO_ID AS LLAVE, COUNT(CURSO_CARGA_ID) AS VALOR FROM ENOC.CARGA_ACADEMICA WHERE CARGA_ID = ? AND PLAN_ID = ? GROUP BY PLAN_ID, CURSO_ID";
			Object[] parametros = new Object[] {cargaId, planId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa map : lista) {
				mapaMaestros.put(map.getLlave(), (String)map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoDao|mapaMateriasOfrecidas|:"+ex);
		}

		return mapaMaestros;
	}
	
	public HashMap<String, CargaAcademica> mapaPorMateria(String cargaId, String planId, String ciclo){
		HashMap<String, CargaAcademica> mapaMaestros = new HashMap<String, CargaAcademica>();
		List<CargaAcademica> lista 		= new ArrayList<CargaAcademica>();		
		try{
			String comando = "SELECT "
					+ " CURSO_CARGA_ID, CARGA_ID,"
					+ " BLOQUE_ID, "
					+ "	CODIGO_PERSONAL, NOMBRE, CARRERA_ID, GRUPO,"
					+ "	MODALIDAD_ID,"
					+ "	ESTADO,"
					+ "	TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO,"
					+ "	TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL,"
					+ "	TO_CHAR(F_ENTREGA,'DD/MM/YYYY') AS F_ENTREGA,"
					+ "	ORIGEN, PLAN_ID, CURSO_ID, NOMBRE_CURSO,"
					+ "	CICLO,"
					+ "	TRIM(TO_CHAR(CREDITOS,'99.9')) AS CREDITOS,"
					+ "	HT,"
					+ "	HP,"
					+ "	HI,"
					+ "	TO_CHAR(NOTA_APROBATORIA,'99') AS NOTA_APROBATORIA,"
					+ "	VALEUCAS, NUM_ALUM, SEMANAS, OPTATIVA, HORARIO, GRUPO_HORARIO, HH, SALON,MODO FROM ENOC.CARGA_ACADEMICA"
					+ " WHERE CARGA_ID = ? AND PLAN_ID = ? AND CICLO = ?";
			Object[] parametros = new Object[] {cargaId, planId, ciclo};
			lista = enocJdbc.query(comando, new CargaAcademicaMapper(), parametros);
			for(CargaAcademica materia : lista) {
				mapaMaestros.put(materia.getCursoCargaId(), materia);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaAcademicaDao|mapaPorMateria|:"+ex);
		}
		return mapaMaestros;
	}
	
}