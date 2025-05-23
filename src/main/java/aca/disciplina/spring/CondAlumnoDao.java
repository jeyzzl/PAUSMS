package aca.disciplina.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CondAlumnoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( CondAlumno conducta ) {
		boolean ok = false;		
		try{
			String comando = "INSERT INTO " +
				"ENOC.COND_ALUMNO(MATRICULA, PERIODO_ID, FOLIO, IDREPORTE, IDLUGAR, IDJUEZ, " +
				"EMPLEADO, FECHA, CANTIDAD, COMENTARIO, PLAN_ID ) " +
				"VALUES(?,?, TO_NUMBER(?,'999'), TO_NUMBER(?,'999'),TO_NUMBER(?,'999'), " +
				"TO_NUMBER(?,'999'), ?, TO_DATE(?,'DD/MM/YYYY'),TO_NUMBER(?,'999'),?, ? ) ";			
			Object[] parametros = new Object[] {conducta.getMatricula(),conducta.getPeriodoId(),conducta.getFolio(),conducta.getIdReporte(),conducta.getIdLugar(),conducta.getIdJuez(),conducta.getEmpleado(),
					conducta.getFecha(),conducta.getCantidad(),conducta.getComentario(), conducta.getPlanId()
			};
				if (enocJdbc.update(comando,parametros)==1){
					ok = true;
				}	
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.spring.CondAlumnoDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg( CondAlumno conducta ) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.COND_ALUMNO" 
				+ " SET  IDREPORTE= TO_NUMBER(?,999), IDLUGAR= TO_NUMBER(?,999), IDJUEZ= TO_NUMBER(?,999), EMPLEADO= ?,"
				+ " FECHA= TO_DATE(?,'DD/MM/YYYY'), CANTIDAD= TO_NUMBER(?,999), COMENTARIO= ?, PLAN_ID = ?"
				+ " WHERE MATRICULA = ? AND PERIODO_ID = ? AND FOLIO = ?";			
			Object[] parametros = new Object[] {conducta.getIdReporte(),conducta.getIdLugar(),conducta.getIdJuez(),conducta.getEmpleado(),
				conducta.getFecha(),conducta.getCantidad(),conducta.getComentario(), conducta.getPlanId(),
				conducta.getMatricula(),conducta.getPeriodoId(),conducta.getFolio()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.spring.CondAlumnoDao|updateReg|:"+ex);		
		}
		return ok;
	}	
	
	public boolean deleteReg( String matricula, String periodoId, String folio ) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.COND_ALUMNO WHERE MATRICULA = ? AND PERIODO_ID = ? AND FOLIO = ? ";			
			Object[] parametros = new Object[] {matricula,periodoId,folio};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.spring.CondAlumnoDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public CondAlumno mapeaRegId( String matricula, String periodoId, String folio) {
		CondAlumno conducta = new CondAlumno();		
		try{
			String comando = "SELECT MATRICULA, PERIODO_ID, FOLIO, IDREPORTE, IDLUGAR, IDJUEZ, EMPLEADO,"
				+ " TO_CHAR(FECHA, 'DD/MM/YYYY') FECHA, CANTIDAD, COMENTARIO, PLAN_ID"
				+ " FROM ENOC.COND_ALUMNO " 
				+ " WHERE MATRICULA = ? AND FOLIO = ? AND PERIODO_ID = ?";						
			Object[] parametros = new Object[] {matricula,folio,periodoId};
			conducta = enocJdbc.queryForObject(comando, new CondAlumnoMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.spring.CondAlumnoDao|mapeaRegId|:"+ex);
		}
		return conducta;
	}
	
	public boolean existeReg( String matricula, String periodoId, String folio) {
		boolean 		ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.COND_ALUMNO WHERE MATRICULA = ? AND PERIODO_ID = ? AND FOLIO = ? ";		
			Object[] parametros = new Object[] {matricula,periodoId,folio};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.spring.CondAlumnoDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public String maximoReg( String matricula, String periodoId ) {
		String maximo			= "1";		
		try{
			String comando = "SELECT COALESCE((MAX(FOLIO)+1),1) AS MAXIMO FROM ENOC.COND_ALUMNO WHERE MATRICULA = ? AND PERIODO_ID = ? ";
			Object[] parametros = new Object[] {matricula,periodoId};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
 				maximo = enocJdbc.queryForObject(comando, String.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.spring.CondAlumnoDao|maximoReg|:"+ex);
		}
		return maximo;
	}	
	
	public String getCantDisciplina( String matricula, String tipo) {
		String comando		= "";
		String cantidad		= "x";		
		try{
			comando = " SELECT COALESCE(SUM(CANTIDAD),0) AS CANTIDAD FROM ENOC.COND_ALUMNO"
					+ " WHERE MATRICULA = ? AND IDREPORTE IN(SELECT IDREPORTE FROM ENOC.COND_REPORTE WHERE TIPO = ?)";			
			Object[] parametros = new Object[] {matricula, tipo};
			cantidad = enocJdbc.queryForObject(comando,String.class,parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.spring.CondAlumnoDao|getCantDisciplina|:"+ex);
		}
		return cantidad;
	}
	
	public String getDisciplinaPer( String periodo, String matricula, String tipo) {
		String comando		= "";
		String cantidad		= "x";
		
		try{
			comando = "SELECT COALESCE(SUM(A.CANTIDAD),0) AS CANTIDAD " + 
					"FROM ENOC.COND_ALUMNO A, ENOC.COND_REPORTE R WHERE PERIODO_ID = ? " + 
					"AND MATRICULA= ? AND R.IDREPORTE = A.IDREPORTE " +  
					"AND R.TIPO = ? ";				
			Object[] parametros = new Object[] {periodo,matricula,tipo};
			cantidad = enocJdbc.queryForObject(comando,String.class,parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.spring.CondAlumnoDao|getDisciplinaPer|:"+ex);
		}
		return cantidad;
	}
	
	public String getUltimaFecha( String periodo, String matricula, String tipo) {
		String comando			= "";
		Locale loc = new Locale("es","MX");
		java.text.SimpleDateFormat fecha = new java.text.SimpleDateFormat("dd/MM/yyyy",loc);
		String strFecha			= "";		
		try{
			comando = "SELECT MAX(TO_DATE(TO_CHAR(FECHA,'DD/MM/YY'),'DD/MM/YY')) AS FECHA "
					+ " FROM ENOC.COND_ALUMNO WHERE PERIODO_ID = ? " 
					+ " AND MATRICULA = ? AND IDREPORTE IN (SELECT IDREPORTE FROM ENOC.COND_REPORTE WHERE TIPO = ?)";			
			Object[] parametros = new Object[] {periodo, matricula, tipo};
			strFecha = enocJdbc.queryForObject(comando,String.class,parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.spring.CondAlumnoDao|getUltimaFecha|:"+ex);
		}
		return strFecha;
	}	
	
	public String getCantReporte( String matricula, String periodo, String reporte) {
		String comando		= "";
		String cantidad		= "x";		
		try{
			comando = " SELECT COALESCE(SUM(CA.CANTIDAD),0) AS CANTIDAD "
					+ " FROM ENOC.COND_ALUMNO CA, ENOC.COND_REPORTE CR WHERE CA.MATRICULA = ?"
					+ " AND CA.PERIODO_ID = ? AND CR.IDREPORTE = ?"
					+ " AND CA.IDREPORTE = CR.IDREPORTE "; 			
			Object[] parametros = new Object[] {matricula, periodo, reporte};
			cantidad = enocJdbc.queryForObject(comando,String.class,parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.spring.CondAlumnoDao|getCantReporte|:"+ex);
		}
		return cantidad;
	}

	public List<CondAlumno> getListAll( String orden ) {
		List<CondAlumno> lista		= new ArrayList<CondAlumno>();				
		try{
			String comando = "SELECT MATRICULA, PERIODO_ID, FOLIO, IDREPORTE, IDLUGAR, IDJUEZ," +
					"EMPLEADO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, CANTIDAD, COMENTARIO, PLAN_ID " +
					"FROM ENOC.COND_ALUMNO "+orden;			
			lista = enocJdbc.query(comando, new CondAlumnoMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.spring.CondAlumnoDao|getListAll|:"+ex);
		}
		return lista;
	}
	
	public List<CondAlumno> lisAlumno( String codigoAlumno, String orden ) {
		List<CondAlumno> lista 		= new ArrayList<CondAlumno>();
		try{
			String comando = " SELECT MATRICULA, PERIODO_ID, FOLIO, IDREPORTE, IDLUGAR, IDJUEZ,"
					+ " EMPLEADO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, CANTIDAD, COMENTARIO, PLAN_ID"
					+ " FROM ENOC.COND_ALUMNO"
					+ " WHERE MATRICULA = ? "+orden;			
			lista = enocJdbc.query(comando, new CondAlumnoMapper(), codigoAlumno);			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.spring.CondAlumnoDao|getLista|:"+ex);
		}
		return lista;
	}
	
	public List<CondAlumno> getLista( String periodo, String orden ) {
		List<CondAlumno> lista 		= new ArrayList<CondAlumno>();				
		try{
			String comando = "SELECT MATRICULA, PERIODO_ID, FOLIO, IDREPORTE, IDLUGAR, IDJUEZ,"
				+ " EMPLEADO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, CANTIDAD, COMENTARIO, PLAN_ID"
				+ " FROM ENOC.COND_ALUMNO"
				+ " WHERE PERIODO_ID = ?"
				+ " AND MATRICULA IN (SELECT CODIGO_PERSONAL FROM ENOC.INSCRITOS) "+orden;			
			lista = enocJdbc.query(comando, new CondAlumnoMapper(), periodo);			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.spring.CondAlumnoDao|getLista|:"+ex);
		}
		return lista;
	}
	
	public List<String> getListAlumnos( String periodo, String orden ) {
		List<String> lista 		= new ArrayList<String>();		
		try{
			String comando = "SELECT DISTINCT(MATRICULA) AS MATRICULA, ENOC.ALUM_CARRERA_ID(MATRICULA), ENOC.ALUM_NOMBRE(MATRICULA) "
				+ " FROM ENOC.COND_ALUMNO WHERE PERIODO_ID = ? "+orden;
			lista = enocJdbc.queryForList(comando, String.class, periodo);			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.spring.CondAlumnoDao|getListAlumnos|:"+ex);
		}
		return lista;
	}
	
	public List<String> getListAlumnosF( String fechaInicio, String fechaFinal, String orden ) {
		List<String> lista		= new ArrayList<String>();
		try{
			String comando = "SELECT DISTINCT(MATRICULA) AS MATRICULA FROM ENOC.COND_ALUMNO"
				+ " WHERE FECHA BETWEEN TO_DATE(?,'dd/mm/yyyy')"
				+ " AND TO_DATE(?,'dd/mm/yyyy') "+orden;			
			lista = enocJdbc.queryForList(comando, String.class, fechaInicio, fechaFinal);			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.spring.CondAlumnoDao|getListAlumnosF|:"+ex);
		}
		return lista;
	}
	
	public List<CondAlumno> getLista( String periodo, String matricula, String orden ) {
		List<CondAlumno> lista 		= new ArrayList<CondAlumno>();
		try{
			String comando = "SELECT MATRICULA, PERIODO_ID, FOLIO, IDREPORTE, IDLUGAR, IDJUEZ,"
					+ " EMPLEADO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, CANTIDAD, COMENTARIO, PLAN_ID"
					+ " FROM ENOC.COND_ALUMNO"
					+ " WHERE MATRICULA = ?"
					+ " AND PERIODO_ID = ? "+orden;			
			lista = enocJdbc.query(comando, new CondAlumnoMapper(), matricula, periodo);			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.spring.CondAlumnoDao|getLista|:"+ex);
		}
		return lista;
	}
		
	public List<CondAlumno> getList( String matricula, String periodo, String orden ) {
		List<CondAlumno> lista 		= new ArrayList<CondAlumno>();
		try{
			String comando = "SELECT MATRICULA, PERIODO_ID, FOLIO, IDREPORTE, IDLUGAR, IDJUEZ, " +
					"EMPLEADO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, CANTIDAD, COMENTARIO, PLAN_ID " +
					"FROM ENOC.COND_ALUMNO " + 
					"WHERE PERIODO_ID = ? " +
					"AND MATRICULA = ? "+orden;
			Object[] parametros = new Object[] {periodo,matricula};
			lista = enocJdbc.query(comando, new CondAlumnoMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.spring.CondAlumnoDao|getList|:"+ex);
		}
		return lista;
	}
	
	public List<CondAlumno> getListReporte( String periodo, String reporte, String orden ) {
		List<CondAlumno> lista 		= new ArrayList<CondAlumno>();
		try{
			String comando = "SELECT A.MATRICULA, A.PERIODO_ID, A.FOLIO, A.IDREPORTE, A.IDLUGAR, A.IDJUEZ, A.EMPLEADO, " +
					"TO_CHAR(A.FECHA,'DD/MM/YYYY') AS FECHA, A.CANTIDAD, A.COMENTARIO, A.PLAN_ID " +
					"FROM ENOC.COND_ALUMNO A, ENOC.COND_REPORTE R " + 
					"WHERE A.PERIODO_ID = ? " +
					"AND A.MATRICULA IN (SELECT CODIGO_PERSONAL FROM ENOC.INSCRITOS)" +
					"AND A.IDREPORTE = ? " +
					"AND R.IDREPORTE = A.IDREPORTE " +orden;			
			lista = enocJdbc.query(comando, new CondAlumnoMapper(), periodo, reporte);			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.spring.CondAlumnoDao|getListReporte|:"+ex);
		}
		return lista;
	}
	
	public List<CondAlumno> getListCarrera( String periodo, String carreraId, String orden ) {
		List<CondAlumno> lista 		= new ArrayList<CondAlumno>();		
		try{			
			String comando =  "SELECT "+ 
			    "A.MATRICULA, A.PERIODO_ID, A.FOLIO, A.IDREPORTE, A.IDLUGAR, "+ 
			    "A.IDJUEZ, A.EMPLEADO, A.FECHA, A.CANTIDAD, A.COMENTARIO, A.PLAN_ID "+
			    "FROM ENOC.COND_ALUMNO A, ENOC.COND_REPORTE B "+ 
			    "WHERE A.PERIODO_ID = ? "+
			    "AND B.IDREPORTE = A.IDREPORTE "+
			    "AND A.MATRICULA IN "+
			 	"(SELECT CODIGO_PERSONAL FROM ENOC.ALUM_ESTADO "+ 
			 	"WHERE CARGA_ID IN "+
			 	" (SELECT CARGA_ID FROM ENOC.CARGA WHERE TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'),'DD-MM-YY') BETWEEN F_INICIO AND F_FINAL) "+ 
			 	"AND ENOC.ALUM_CARRERA_ID(CODIGO_PERSONAL) = ? ) "+orden;		
			lista = enocJdbc.query(comando, new CondAlumnoMapper(), periodo, carreraId);			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.spring.CondAlumnoDao|getListCarrera|:"+ex);
		}
		return lista;
	}
	
	public List<CondAlumno> getListTipo( String periodo, String matricula, String orden ) {
		List<CondAlumno> lista 		= new ArrayList<CondAlumno>();				
		try{
			String comando = "SELECT A.MATRICULA, A.PERIODO_ID, A.FOLIO, A.IDREPORTE, A.IDLUGAR, " +
					"A.IDJUEZ, A.EMPLEADO, TO_CHAR(A.FECHA,'DD/MM/YYYY') AS FECHA, " +
					"A.CANTIDAD, A.COMENTARIO, A.PLAN_ID " +
					"FROM ENOC.COND_ALUMNO A, ENOC.COND_REPORTE R " + 
					"WHERE A.IDREPORTE = R.IDREPORTE " +
					"AND A.PERIODO_ID = ? " +
					"AND A.MATRICULA = ? " +
					"AND TIPO IN ('C','D') "+orden;			
			lista = enocJdbc.query(comando, new CondAlumnoMapper(), periodo, matricula );			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.spring.CondAlumnoDao|getListTipo|:"+ex);
		}
		return lista;
	}
	
	public List<CondAlumno> getListFechas( String fechaInicio, String fechaFinal, String orden ) {
		List<CondAlumno> lista		= new ArrayList<CondAlumno>();
		try{
			String comando = "SELECT MATRICULA, PERIODO_ID, FOLIO, IDREPORTE, IDLUGAR, IDJUEZ,"
					+ " EMPLEADO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, CANTIDAD, COMENTARIO, PLAN_ID "
					+ " FROM ENOC.COND_ALUMNO"
					+ " WHERE FECHA BETWEEN TO_DATE(?,'dd/mm/yyyy')"
					+ " AND TO_DATE(?,'dd/mm/yyyy') "+orden;			
			lista = enocJdbc.query(comando, new CondAlumnoMapper(), fechaInicio, fechaFinal);			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.spring.CondAlumnoDao|getListFechas|:"+ex);
		}
		return lista;
	}
	
	public HashMap<String, Integer> getMapAlumUnidad( String cargaId,  String periodo, String orden) {
		HashMap<String, Integer> mapa 	= new HashMap<String, Integer>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();	
		try{
			String comando = " SELECT CODIGO_PERSONAL AS LLAVE,"
					+ " (SELECT COALESCE(SUM(A.CANTIDAD * (CASE R.TIPO WHEN 'C' THEN 1 ELSE -1 END)),0)"
					+ " FROM ENOC.COND_ALUMNO A, ENOC.COND_REPORTE R"
					+ " WHERE PERIODO_ID = ?"
					+ " AND MATRICULA= ESTADISTICA.CODIGO_PERSONAL"
					+ " AND R.IDREPORTE = A.IDREPORTE"
					+ "	) AS VALOR"
					+ " FROM ENOC.ESTADISTICA"
					+ " WHERE CARGA_ID IN ('"+cargaId+"')";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), periodo);
			for(aca.Mapa map : lista){
				mapa.put(map.getLlave(), Integer.parseInt(map.getValor()));
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.spring.CondAlumnoDao|getMapDisciplinaPer|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, Integer> getMapDisciplinaPer( String cargaId,  String periodo, String tipo, String orden) {
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		HashMap<String, Integer> mapa 	= new HashMap<String, Integer>();
		try{
			String comando = "SELECT CODIGO_PERSONAL," +
					" (SELECT COALESCE(SUM(A.CANTIDAD),0)" +
						" FROM ENOC.COND_ALUMNO A, ENOC.COND_REPORTE R " +
						" WHERE PERIODO_ID = ?" +
						" AND MATRICULA= ESTADISTICA.CODIGO_PERSONAL" +
						" AND R.IDREPORTE = A.IDREPORTE" +
						" AND R.TIPO = ?)" +
					" AS CANTIDAD" +
					" FROM ENOC.ESTADISTICA" +
					" WHERE CARGA_ID IN ('"+cargaId+"')";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), periodo, tipo);			
			for(aca.Mapa map : lista){
				mapa.put(map.getLlave(), Integer.parseInt(map.getValor()));
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.spring.CondAlumnoDao|getMapDisciplinaPer|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, Integer> getMapUnidadesXCarrera( String fechaIni,  String fechaFin, String cargaId) {
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();
		HashMap<String, Integer> mapa 	= new HashMap<String, Integer>();		
		try{
			String comando = "SELECT COUNT(DISTINCT(MATRICULA)) AS CANTIDAD, ENOC.ALUM_CARRERA_HISTORICA(MATRICULA, ?,1) AS CARRERA"
					+ " FROM ENOC.COND_ALUMNO " 
					+ " WHERE FECHA BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY')" 
					+ " AND IDREPORTE IN (SELECT IDREPORTE FROM ENOC.COND_REPORTE WHERE TIPO = 'D')" 
					+ " GROUP BY ENOC.ALUM_CARRERA_HISTORICA(MATRICULA,?,1)";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), cargaId, fechaIni, fechaFin, cargaId);			
			for(aca.Mapa map : lista){
				mapa.put(map.getLlave(), Integer.parseInt(map.getValor()));
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.spring.CondAlumnoDao|getMapUnidadesXCarrera|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> getElogios(){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa>	lista 		= new ArrayList<aca.Mapa>();		
		try{
			String comando = " SELECT MATRICULA AS LLAVE, SUM(CANTIDAD) AS VALOR FROM ENOC.COND_ALUMNO "+
					  " WHERE MATRICULA IN (SELECT CODIGO_PERSONAL FROM ENOC.INT_ALUMNO) "+
					  " AND IDREPORTE IN (SELECT IDREPORTE FROM ENOC.COND_REPORTE WHERE TIPO = 'C')"+
					  " GROUP BY MATRICULA";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.spring.CondAlumnoDao|getElogios|:"+ex);
		}
		return mapa;
	}

	public HashMap<String, String> getUnidades( ) {
		List<aca.Mapa>	lista 		= new ArrayList<aca.Mapa>();
		HashMap<String, String> mapa = new HashMap<String, String>();
		try{
			String comando = " SELECT MATRICULA AS LLAVE, SUM(CANTIDAD) AS VALOR FROM ENOC.COND_ALUMNO "+
					  " WHERE MATRICULA IN (SELECT CODIGO_PERSONAL FROM ENOC.INT_ALUMNO) "+
					  " AND IDREPORTE IN (SELECT IDREPORTE FROM ENOC.COND_REPORTE WHERE TIPO = 'D')"+
					  " GROUP BY MATRICULA";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.spring.CondAlumnoDao|getUnidades|:"+ex);
		}
		return mapa;
	}

	public List<String> getPeriodosAlumno( String matricula ) {
		List<String> lista 	= new ArrayList<String>();
		try{
			String comando = "SELECT DISTINCT(PERIODO_ID) FROM COND_ALUMNO WHERE MATRICULA = ? GROUP BY PERIODO_ID, TIPO_REPORTE(IDREPORTE) ORDER BY 1";
			Object[] parametros = new Object[] {matricula};
			lista = enocJdbc.queryForList(comando, String.class, parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.spring.CondAlumnoDao|getPeriodosAlumno|:"+ex);
		}
		return lista;
	}
	
	public HashMap<String, String> getEvalDisciplinaria( String matricula ) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa>	lista 		= new ArrayList<aca.Mapa>();
		try{
			String comando = " SELECT PERIODO_ID||TIPO_REPORTE(IDREPORTE) AS LLAVE, COUNT(CANTIDAD) AS VALOR"
					+ " FROM COND_ALUMNO"
					+ "	WHERE MATRICULA = ? GROUP BY PERIODO_ID, TIPO_REPORTE(IDREPORTE)";
			Object[] parametros = new Object[] {matricula};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.spring.CondAlumnoDao|getEvalDisciplinaria|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> condAlumnoDao( String matricula ) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa>	lista 		= new ArrayList<aca.Mapa>();		
		try{
			String comando = " SELECT PERIODO_ID||TIPO_REPORTE(IDREPORTE) AS LLAVE, COUNT(CANTIDAD) AS VALOR"
					+ " FROM COND_ALUMNO"
					+ "	WHERE MATRICULA = ? GROUP BY PERIODO_ID, TIPO_REPORTE(IDREPORTE)";
			Object[] parametros = new Object[] {matricula};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.spring.CondAlumnoDao|getEvalDisciplinaria|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,String> mapaCantidades( String periodo ) {
		HashMap<String, String> mapa 	= new HashMap<String, String>();
		List<aca.Mapa>	lista 			= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT MATRICULA||TIPO_REPORTE(IDREPORTE) AS LLAVE, COALESCE(SUM(CANTIDAD),0) AS VALOR FROM COND_ALUMNO"
					+ " WHERE PERIODO_ID = ?"
					+ " GROUP BY MATRICULA||TIPO_REPORTE(IDREPORTE)";
			Object[] parametros = new Object[] {periodo};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.spring.CondAlumnoDao|mapaCantidades|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,String> mapaMovimientosPorPeriodo( String matricula ) {
		HashMap<String, String> mapa 	= new HashMap<String, String>();
		List<aca.Mapa>	lista 			= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT PERIODO_ID AS LLAVE, COUNT(MATRICULA) AS VALOR FROM ENOC.COND_ALUMNO WHERE MATRICULA = ? GROUP BY PERIODO_ID";
			Object[] parametros = new Object[] {matricula};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.spring.CondAlumnoDao|mapaMovimientosPorPeriodo|:"+ex);
		}
		return mapa;
	}
	

}