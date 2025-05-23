package aca.trabajo.spring;

import java.lang.reflect.Parameter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.microsoft.sqlserver.jdbc.dataclassification.InformationType;

import aca.Mapa;

@Component
public class TrabInformeAlumDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( TrabInformeAlum informeAlum) {
		boolean ok = false;
		try{
			String comando = "INSERT INTO ENOC.TRAB_INFORME_ALUM (MATRICULA, INFORME_ID, DEPT_ID, CAT_ID, FECHA, USUARIO, HORAS, PERIODO_ID, STATUS, HORA_INICIO, HORA_FIN, DESCRIPCION)"
					+ " VALUES( ?, TO_NUMBER(?, '99999'), TO_NUMBER(?, '999'), TO_NUMBER(?, '999'), TO_DATE(?, 'DD/MM/YYYY hh24:mi:ss'), ?, TO_NUMBER(?,'999.99'), TO_NUMBER(?, '999'), ?, TO_DATE(?, 'DD/MM/YYYY hh24:mi:ss'), TO_DATE(?, 'DD/MM/YYYY hh24:mi:ss'), ?)";
			Object[] parametros = new Object[] {informeAlum.getMatricula(), informeAlum.getInformeId(), informeAlum.getDeptId(), informeAlum.getCatId(), informeAlum.getFecha(), informeAlum.getUsuario(), informeAlum.getHoras(), 
												informeAlum.getPeriodoId(), informeAlum.getStatus(), informeAlum.getHoraInicio(), informeAlum.getHoraFin(), informeAlum.getDescripcion()};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.categoria.spring.TrabInformeAlumDao|insertReg|:"+ex);			
		}
		
		return ok;
	}
	
	public boolean updateReg(TrabInformeAlum informeAlum) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.TRAB_INFORME_ALUM SET USUARIO = ?, STATUS = ?, HORAS = TO_NUMBER(?,'999.99'), DESCRIPCION = ? WHERE MATRICULA = ? AND INFORME_ID = TO_NUMBER(?,'99999')";
			Object[] parametros = new Object[] {informeAlum.getUsuario(), informeAlum.getStatus(), informeAlum.getHoras(), informeAlum.getDescripcion(), informeAlum.getMatricula(), informeAlum.getInformeId()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.trabajo.spring.TrabInformeAlumDao|updateReg|:"+ex);		
		}
		
		return ok;
	}	
	
	public boolean updateRegPorInforme(TrabInformeAlum informeAlum) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.TRAB_INFORME_ALUM SET MATRICULA = ?, DEPT_ID = ?, CAT_ID = ?, FECHA = TO_DATE(?, 'DD/MM/YYYY hh24:mi:ss'), USUARIO = ?, HORAS = ?, INFORME_ID = ?, PERIODO_ID = ?, STATUS = ?, DESCRIPCION = ? WHERE MATRICULA = ? AND INFORME_ID = ?";
			Object[] parametros = new Object[] {informeAlum.getMatricula(),informeAlum.getDeptId(), informeAlum.getCatId(),
					informeAlum.getFecha(), informeAlum.getUsuario(), informeAlum.getHoras(), informeAlum.getInformeId(), informeAlum.getPeriodoId(), informeAlum.getDescripcion(), informeAlum.getMatricula(), informeAlum.getInformeId() };
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.trabajo.spring.TrabInformeAlumDao|updateRegPorInforme|:"+ex);		
		}
		
		return ok;
	}	
	
	public boolean deleteReg( String matricula, String informeId ) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.TRAB_INFORME_ALUM WHERE MATRICULA = ? AND INFORME_ID = TO_NUMBER(?,'99999')";
			Object[] parametros = new Object[] {matricula, informeId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.trabajo.spring.TrabInformeAlumDao|deleteReg|:"+ex);			
		}
		
		return ok;
	}

	public boolean updateHoraFin( String matricula, String informeId, String horaFin, String descripcion ) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.TRAB_INFORME_ALUM SET HORA_FIN = TO_DATE(?, 'DD/MM/YYYY hh24:mi:ss'), STATUS = 'F', DESCRIPCION = ? WHERE MATRICULA = ? AND INFORME_ID = ?";
			Object[] parametros = new Object[] {horaFin, descripcion, matricula, informeId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.trabajo.spring.TrabInformeAlumDao|updateHoraFin|:"+ex);			
		}
		
		return ok;
	}
	
	public TrabInformeAlum mapeaRegId(  String matricula, String deptId, String catId, String periodoId, String fecha) {
		
		TrabInformeAlum informeAlum = new TrabInformeAlum();		
		try{
			String comando = "SELECT COUNT(MATRICULA) FROM ENOC.TRAB_INFORME_ALUM WHERE MATRICULA = ? AND DEPT_ID = TO_NUMBER(?, '999') AND CAT_ID = TO_NUMBER(?,'999') AND PERIODO_ID = TO_NUMBER(?,'999') AND TRUNC(FECHA) = TRUNC(COALESCE(TO_DATE(?, 'DD/MM/YYYY hh24:mi:ss', 'NLS_DATE_LANGUAGE=ENGLISH'),TO_DATE(?, 'DD/MM/YYYY', 'NLS_DATE_LANGUAGE=ENGLISH')))";
			Object[] parametros = new Object[] { matricula, deptId, catId, periodoId, fecha, fecha};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				comando = "SELECT * FROM ENOC.TRAB_INFORME_ALUM WHERE MATRICULA = ? AND DEPT_ID = TO_NUMBER(?, '999') AND CAT_ID = TO_NUMBER(?,'999') AND PERIODO_ID = TO_NUMBER(?,'999') AND TRUNC(FECHA) = TRUNC(COALESCE(TO_DATE(?, 'DD/MM/YYYY hh24:mi:ss', 'NLS_DATE_LANGUAGE=ENGLISH'),TO_DATE(?, 'DD/MM/YYYY', 'NLS_DATE_LANGUAGE=ENGLISH')))";
				informeAlum = enocJdbc.queryForObject(comando, new TrabInformeAlumMapper(), parametros);
			}						
		}catch(Exception ex){
			System.out.println("Error - aca.trabajo.spring.TrabInformeAlumDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		
		return informeAlum;
	}

	public TrabInformeAlum mapeaRegId( String matricula, String informeId) {
		
		TrabInformeAlum informeAlum = new TrabInformeAlum();		
		try{
			String comando = "SELECT COUNT(MATRICULA) FROM ENOC.TRAB_INFORME_ALUM WHERE MATRICULA = ? AND INFORME_ID = TO_NUMBER(?,'99999')";
			Object[] parametros = new Object[] { matricula, informeId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				comando = "SELECT * FROM ENOC.TRAB_INFORME_ALUM WHERE MATRICULA = ? AND INFORME_ID = TO_NUMBER(?,'99999')";
				informeAlum = enocJdbc.queryForObject(comando, new TrabInformeAlumMapper(), parametros);
			}						
		}catch(Exception ex){
			System.out.println("Error - aca.trabajo.spring.TrabInformeAlumDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		
		return informeAlum;
	}
	
	public boolean existeReg( String matricula, String deptId, String catId, String periodoId, String fecha ) {
		boolean 		ok 		= false;		
		try{
			String comando = "SELECT COUNT(MATRICULA) FROM ENOC.TRAB_INFORME_ALUM WHERE MATRICULA = ? AND DEPT_ID = TO_NUMBER(?, '999') AND CAT_ID = TO_NUMBER(?,'999') AND PERIODO_ID = TO_NUMBER(?,'999') AND TRUNC(FECHA) = TRUNC(COALESCE(TO_DATE(?, 'DD/MM/YYYY hh24:mi:ss', 'NLS_DATE_LANGUAGE=ENGLISH'),TO_DATE(?, 'DD/MM/YYYY', 'NLS_DATE_LANGUAGE=ENGLISH')))";
			Object[] parametros = new Object[] {matricula, deptId, catId, periodoId, fecha, fecha};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.trabajo.spring.TrabInformeAlumDao|existeReg|:"+ex);
		}
		
		return ok;
	}

	public boolean existeReg( String matricula, String informeId ) {
		boolean 		ok 		= false;		
		try{
			String comando = "SELECT COUNT(MATRICULA) FROM ENOC.TRAB_INFORME_ALUM WHERE MATRICULA = ? AND INFORME_ID = TO_NUMBER(?,'99999')";
			Object[] parametros = new Object[] {matricula, informeId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.trabajo.spring.TrabInformeAlumDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean existeRegPorInforme( String matricula, String informeId ) {
		boolean 		ok 		= false;		
		try{
			String comando = "SELECT COUNT(MATRICULA) FROM ENOC.TRAB_INFORME_ALUM WHERE MATRICULA = ? AND INFORME_ID = ?";
			Object[] parametros = new Object[] {matricula, informeId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.trabajo.spring.TrabInformeAlumDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoReg() {
		String maximo 			= "01";		
		try{
			String comando = "SELECT COALESCE(MAX(INFORME_ID)+1,1) AS MAXIMO FROM ENOC.TRAB_INFORME_ALUM";
			if (enocJdbc.queryForObject(comando, Integer.class) >= 1){
				maximo = enocJdbc.queryForObject(comando, String.class);
				if (maximo.length()==1) maximo = "0"+maximo;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.trabajo.spring.TrabInformeAlumDao|maximoReg|:"+ex);
		}
		
		return maximo;		
	}

	public String getUsuario(String informeId) {
		String usuario 			= "0";		
		try{
			String comando = "SELECT COALESCE(USUARIO,'0') AS USUARIO FROM ENOC.TRAB_INFORME_ALUM WHERE INFORME_ID = TO_NUMBER(?,'99999')";
			if (enocJdbc.queryForObject(comando, Integer.class, informeId) >= 1){
				usuario = enocJdbc.queryForObject(comando, String.class, informeId);
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.trabajo.spring.TrabInformeAlumDao|getUsuario|:"+ex);
		}
		
		return usuario;		
	}
	
	public String AlumHorasAlcanzadas (String codigoPersonal, String orden){
		String horasAlcanzadas = "";
		try {
		String comando = "SELECT COALESCE(SUM(LEAST(HORAS, 8)), 0) FROM TRAB_INFORME_ALUM WHERE MATRICULA = ? AND (STATUS = 'C' OR STATUS = 'X')";
		Object[] parametros = new Object[] {codigoPersonal};
		horasAlcanzadas = enocJdbc.queryForObject(comando, String.class, parametros);
		}catch (Exception ex) {
			System.out.println("Error - aca.alumno.spring.TrabInformeAlumDao|mapaAlumHorasAlcanzadas|:"+ex);
		}
		return horasAlcanzadas;
	}

	public String AlumHorasTotalesAlcanzadas(String codigoPersonal, String orden){
		String horasAlcanzadas = "";
		try {
		String comando = "SELECT COALESCE(SUM(HORAS),0) FROM TRAB_INFORME_ALUM WHERE MATRICULA = ? AND (STATUS = 'C' OR STATUS = 'X')";
		Object[] parametros = new Object[] {codigoPersonal};
		horasAlcanzadas = enocJdbc.queryForObject(comando, String.class, parametros);
		}catch (Exception ex) {
			System.out.println("Error - aca.alumno.spring.TrabInformeAlumDao|mapaAlumHorasAlcanzadas|:"+ex);
		}
		return horasAlcanzadas;
	}
	
	// Llena el listor con todos los elementos de la tabla	
	public List<TrabInformeAlum> lisTodos( String orden ) {
			
		List<TrabInformeAlum> lista = new ArrayList<TrabInformeAlum>();		
		try{
			String comando = "SELECT MATRICULA, INFORME_ID, DEPT_ID, CAT_ID, FECHA, USUARIO, HORAS, PERIODO_ID, STATUS, HORA_INICIO, HORA_FIN, DESCRIPCION FROM ENOC.TRAB_INFORME_ALUM "+ orden;				
			lista = enocJdbc.query(comando, new TrabInformeAlumMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.trabajo.spring.TrabInformeAlumDao|lisTodos|:"+ex);
		}		
		return lista;
	}

	public List<TrabInformeAlum> lisInformes( String deptId, String periodoId, String fecha, String orden ) {
		
		List<TrabInformeAlum> lista = new ArrayList<TrabInformeAlum>();		
		try{
			Object[] parametros = new Object[] {deptId, periodoId, fecha, fecha};
			String comando = "SELECT * FROM TRAB_INFORME_ALUM WHERE DEPT_ID = TO_NUMBER(?, '999') AND PERIODO_ID = TO_NUMBER(?,'999') AND TRUNC(FECHA) = TRUNC(COALESCE(TO_DATE(?, 'DD/MM/YYYY hh24:mi:ss', 'NLS_DATE_LANGUAGE=ENGLISH'),TO_DATE(?, 'DD/MM/YYYY', 'NLS_DATE_LANGUAGE=ENGLISH')))"+ orden;				
			lista = enocJdbc.query(comando, new TrabInformeAlumMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.trabajo.spring.TrabInformeAlumDao|lisInformes|:"+ex);
		}		
		return lista;
	}

	public List<TrabInformeAlum> lisInformes( String deptId, String catId, String periodoId, String fecha, String orden ) {
		
		List<TrabInformeAlum> lista = new ArrayList<TrabInformeAlum>();		
		try{
			Object[] parametros = new Object[] {deptId, catId, periodoId, fecha, fecha};
			String comando = "SELECT * FROM TRAB_INFORME_ALUM WHERE DEPT_ID = TO_NUMBER(?, '999') AND CAT_ID = TO_NUMBER(?,'999') AND PERIODO_ID = TO_NUMBER(?,'999') AND TRUNC(FECHA) = TRUNC(COALESCE(TO_DATE(?, 'DD/MM/YYYY hh24:mi:ss', 'NLS_DATE_LANGUAGE=ENGLISH'),TO_DATE(?, 'DD/MM/YYYY', 'NLS_DATE_LANGUAGE=ENGLISH')))"+ orden;				
			lista = enocJdbc.query(comando, new TrabInformeAlumMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.trabajo.spring.TrabInformeAlumDao|lisInformes|:"+ex);
		}		
		return lista;
	}

	public List<TrabInformeAlum> lisInformesAlumno( String codigoAlumno, String orden ) {
		
		List<TrabInformeAlum> lista = new ArrayList<TrabInformeAlum>();		
		try{
			Object[] parametros = new Object[] {codigoAlumno};
			String comando = "SELECT MATRICULA, INFORME_ID, DEPT_ID, CAT_ID, FECHA, USUARIO, HORAS, PERIODO_ID, STATUS, HORA_INICIO, HORA_FIN, DESCRIPCION FROM TRAB_INFORME_ALUM WHERE MATRICULA = ?"+ orden;				
			lista = enocJdbc.query(comando, new TrabInformeAlumMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.trabajo.spring.TrabInformeAlumDao|lisInformesAlumno|:"+ex);
		}		
		return lista;
	}

	public List<TrabInformeAlum> lisInformesAlumnoPorEstado( String codigoAlumno, String estado, String orden ) {
		
		List<TrabInformeAlum> lista = new ArrayList<TrabInformeAlum>();		
		try{
			Object[] parametros = new Object[] {codigoAlumno, estado};
			String comando = "SELECT * FROM TRAB_INFORME_ALUM WHERE MATRICULA = ? AND STATUS = ?"+ orden;				
			lista = enocJdbc.query(comando, new TrabInformeAlumMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.trabajo.spring.TrabInformeAlumDao|lisInformesAlumno|:"+ex);
		}		
		return lista;
	}
	
	public HashMap<String, String> mapaAlumHoras (String orden){
		
		HashMap<String, String> map		= new HashMap<String,String>();
		List<aca.Mapa> list 				= new ArrayList<aca.Mapa>();
		try {
		String comando = "SELECT MATRICULA AS LLAVE, HORAS AS VALOR FROM TRAB_INFORME_ALUM WHERE STATUS = 'A'";
		list = enocJdbc.query(comando, new aca.MapaMapper());
		for(Mapa alum: list) {
			map.put(alum.getLlave(), alum.getValor());
		}
		}catch (Exception ex) {
			System.out.println("Error - aca.alumno.spring.TrabInformeAlumDao|mapaAlumHoras|:"+ex);
		}
		return map;
		
	}

	public HashMap<String, String> mapaAlumHorasPorInformeyDept (String deptId, String orden){
		
		HashMap<String, String> map		= new HashMap<String,String>();
		List<aca.Mapa> list 				= new ArrayList<aca.Mapa>();
		try {
		String comando = "SELECT MATRICULA AS LLAVE, HORAS AS VALOR FROM TRAB_INFORME_ALUM WHERE DEPT_ID = ? AND STATUS = 'A'";
		Object[] parametros = new Object[] {deptId};
		list = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
		for(Mapa alum: list) {
			map.put(alum.getLlave(), alum.getValor());
		}
		}catch (Exception ex) {
			System.out.println("Error - aca.trabajo.spring.TrabInformeAlumDao|mapaAlumHorasPorInformeyDept|:"+ex);
		}
		return map;
		
	}

	public HashMap<String, String> mapaAlumHorasCompletadasPorDept (String deptId, String orden){
		
		HashMap<String, String> map		= new HashMap<String,String>();
		List<aca.Mapa> list 				= new ArrayList<aca.Mapa>();
		try {
		String comando = "SELECT MATRICULA AS LLAVE, SUM(HORAS) AS VALOR FROM TRAB_INFORME_ALUM WHERE DEPT_ID = ? AND STATUS = 'C' OR STATUS = 'X' GROUP BY MATRICULA";
		Object[] parametros = new Object[] {deptId};
		list = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
		for(Mapa alum: list) {
			map.put(alum.getLlave(), alum.getValor());
		}
		}catch (Exception ex) {
			System.out.println("Error - aca.trabajo.spring.TrabInformeAlumDao|mapaAlumHorasCompletadasPorDept|:"+ex);
		}
		return map;
		
	}
	
	public HashMap<String, String> mapPeriodoyHoras( String matricula) {
		HashMap<String, String> map		= new HashMap<String,String>();
		List<aca.Mapa> list 				= new ArrayList<aca.Mapa>();
		try {
		String comando = "SELECT PERIODO_ID as LLAVE, COALESCE(SUM(LEAST(HORAS, 8)), 0) AS VALOR FROM TRAB_INFORME_ALUM WHERE MATRICULA = ? AND (STATUS = 'C' OR STATUS = 'X') GROUP BY PERIODO_ID";
		Object[] parametros = new Object[] {matricula};
		list = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
		for(Mapa alum: list) {
			map.put(alum.getLlave(), alum.getValor());
		}
		}catch (Exception ex) {
			System.out.println("Error - aca.trabajo.spring.TrabInformeAlumDao|mapHorasByPeriodo|:"+ex);
		}
		return map;
	}

	public HashMap<String, String> mapPeriodoyHorasTotales( String matricula) {
		HashMap<String, String> map		= new HashMap<String,String>();
		List<aca.Mapa> list 				= new ArrayList<aca.Mapa>();
		try {
		String comando = "SELECT PERIODO_ID as LLAVE, SUM(HORAS) AS VALOR FROM TRAB_INFORME_ALUM WHERE MATRICULA = ? AND (STATUS = 'C' OR STATUS = 'X') GROUP BY PERIODO_ID";
		Object[] parametros = new Object[] {matricula};
		list = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
		for(Mapa alum: list) {
			map.put(alum.getLlave(), alum.getValor());
		}
		}catch (Exception ex) {
			System.out.println("Error - aca.trabajo.spring.TrabInformeAlumDao|mapHorasByPeriodo|:"+ex);
		}
		return map;
	}
	
	public HashMap<String, String> mapInformesPorAlumno() {
		HashMap<String, String> map		= new HashMap<String,String>();
		List<aca.Mapa> list 				= new ArrayList<aca.Mapa>();
		try {
		String comando = "SELECT INFORME_ID || '-' || MATRICULA as LLAVE, 1 AS VALOR FROM TRAB_INFORME_ALUM";
		list = enocJdbc.query(comando, new aca.MapaMapper());
		for(Mapa alum: list) {
			map.put(alum.getLlave(), alum.getValor());
		}
		}catch (Exception ex) {
			System.out.println("Error - aca.trabajo.spring.TrabInformeAlumDao|mapInformesPorAlumno|:"+ex);
		}
		return map;
	}

	public HashMap<String,String> mapHorasRegistradasPorPeriodo(String periodoId) {
		HashMap<String,String> map = new HashMap<String,String>();
		List<aca.Mapa> list 		= new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT MATRICULA AS LLAVE, COALESCE(SUM(LEAST(HORAS, 8)), 0) AS VALOR FROM TRAB_INFORME_ALUM WHERE PERIODO_ID = ?  AND (STATUS = 'C' OR STATUS = 'X') GROUP BY MATRICULA, PERIODO_ID";
			list = enocJdbc.query(comando, new aca.MapaMapper(), periodoId);
			for(Mapa alum: list) {
				map.put(alum.getLlave(), alum.getValor());
			}

		}catch(Exception ex){
			System.out.println("Error - aca.trabajo.spring.TrabInformeAlumDao|mapHorasRegistradasPorPeriodo|:"+ex);
		}

		return map;
	}

	public HashMap<String,String> mapHorasTotalesRegistradasPorPeriodo(String periodoId) {
		HashMap<String,String> map = new HashMap<String,String>();
		List<aca.Mapa> list 		= new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT MATRICULA AS LLAVE, SUM(HORAS) AS VALOR FROM TRAB_INFORME_ALUM WHERE PERIODO_ID = ?  AND (STATUS = 'C' OR STATUS = 'X') GROUP BY MATRICULA, PERIODO_ID";
			list = enocJdbc.query(comando, new aca.MapaMapper(), periodoId);
			for(Mapa alum: list) {
				map.put(alum.getLlave(), alum.getValor());
			}

		}catch(Exception ex){
			System.out.println("Error - aca.trabajo.spring.TrabInformeAlumDao|mapHorasRegistradasPorPeriodo|:"+ex);
		}

		return map;
	}
		
	public String horasPorReporte(String matricula, String informeId) {
		String horas 			= "0";		
		try{
			Object[] parametros = new Object[] {matricula, informeId};
			String comando = "WITH date_data AS (" +
								"SELECT " + 
								"TO_TIMESTAMP(TO_CHAR(HORA_INICIO, 'DD/MM/YYYY HH24:MI:SS'), 'DD/MM/YYYY HH24:MI:SS') AS date1, " + 
								"TO_TIMESTAMP(TO_CHAR(HORA_FIN, 'DD/MM/YYYY HH24:MI:SS'), 'DD/MM/YYYY HH24:MI:SS') AS date2 " +
								"FROM ENOC.TRAB_INFORME_ALUM " +
								"WHERE MATRICULA = ? AND INFORME_ID = ? AND STATUS = 'F') " +
								"SELECT ROUND (" +
								"(EXTRACT(DAY FROM (date2 - date1)) * 24) + EXTRACT(HOUR FROM (date2 - date1)) + " +
								"(EXTRACT(MINUTE FROM (date2 - date1)) / 60) + " +
								"(EXTRACT(SECOND FROM (date2 - date1)) / 3600)," +
								"2 ) AS HORAS FROM date_data";
				horas = enocJdbc.queryForObject(comando, String.class, parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.trabajo.spring.TrabInformeAlumDao|horasPorReporte|:"+ex);
		}
			
		return horas;		
	}

	public boolean syncHorasPorReporte(String matricula, String informeId) {
		boolean sync 			= false;		
		try{
			Object[] parametros = new Object[] {matricula, informeId, matricula, informeId};
			String comando = "UPDATE TRAB_INFORME_ALUM SET HORAS = (WITH date_data AS (SELECT " +
								"TO_TIMESTAMP(TO_CHAR(HORA_INICIO, 'DD/MM/YYYY HH24:MI:SS'), 'DD/MM/YYYY HH24:MI:SS') AS date1, " + 
								"TO_TIMESTAMP(TO_CHAR(HORA_FIN, 'DD/MM/YYYY HH24:MI:SS'), 'DD/MM/YYYY HH24:MI:SS') AS date2 " + 
								"FROM ENOC.TRAB_INFORME_ALUM " +
								"WHERE MATRICULA = ? AND INFORME_ID = ? AND STATUS = 'C') " +
								"SELECT ROUND ( " +
								"(EXTRACT(DAY FROM (date2 - date1)) * 24) + EXTRACT(HOUR FROM (date2 - date1)) + " +
								"(EXTRACT(MINUTE FROM (date2 - date1)) / 60) + " +
								"(EXTRACT(SECOND FROM (date2 - date1)) / 3600), 2 ) AS HORAS FROM date_data) " +
								"WHERE MATRICULA = ? AND INFORME_ID= ? AND STATUS = 'C'";
			if (enocJdbc.update(comando,parametros)==1){
				sync = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.trabajo.spring.TrabInformeAlumDao|syncHorasPorReporte|:"+ex);
		}
			
		return sync;		
	}

	public Date minimaSemanaDisponible(String periodoId) {
		Date fecha 			= null;		
		String sFecha 		= "";
		try{
			String comando = "SELECT MIN(FECHA) AS minAvailableDate FROM trab_informe_alum WHERE FECHA IS NOT NULL AND PERIODO_ID = TO_NUMBER(?,'999')";
			sFecha = enocJdbc.queryForObject(comando, String.class, periodoId);

			SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
			fecha = dateFormat.parse(sFecha);
		}catch(Exception ex){
			System.out.println("Error - aca.trabajo.spring.TrabInformeAlumDao|minimaSemanaDisponible|:"+ex);
		}
			
		return fecha;		
	}

	public Date minimaSemanaDisponible() {
		Date fecha 			= null;		
		String sFecha 		= "";
		try{
			String comando = "SELECT MIN(FECHA) AS minAvailableDate FROM trab_informe_alum WHERE FECHA IS NOT NULL";
			sFecha = enocJdbc.queryForObject(comando, String.class);

			SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
			fecha = dateFormat.parse(sFecha);
		}catch(Exception ex){
			System.out.println("Error - aca.trabajo.spring.TrabInformeAlumDao|minimaSemanaDisponible|:"+ex);
		}
			
		return fecha;		
	}

	public HashMap<String, String> mapHorasPorSemana(String periodoId, String orden) {
		HashMap<String, String> map		= new HashMap<String,String>();
		List<aca.Mapa> list 				= new ArrayList<aca.Mapa>();
		try {
		String comando 	= " SELECT MATRICULA || TRUNC(NEXT_DAY(FECHA, 'SUNDAY') - 7) AS LLAVE,"
						+ " LEAST(COALESCE(SUM(HORAS), 0), 8) AS VALOR"
						+ " FROM TRAB_INFORME_ALUM"
						+ " WHERE (STATUS = 'C' OR STATUS = 'X') AND PERIODO_ID = ?"
						+ " GROUP BY MATRICULA, TRUNC(NEXT_DAY(FECHA, 'SUNDAY') - 7)"+orden;
		list = enocJdbc.query(comando, new aca.MapaMapper(), periodoId);
		for(Mapa alum: list) {
			map.put(alum.getLlave(), alum.getValor());
		}
		}catch (Exception ex) {
			System.out.println("Error - aca.trabajo.spring.TrabInformeAlumDao|mapHorasPorSemana|:"+ex);
		}
		return map;
	}

	public HashMap<String, String> mapHorasTotalesPorSemana(String periodoId, String orden) {
		HashMap<String, String> map		= new HashMap<String,String>();
		List<aca.Mapa> list 				= new ArrayList<aca.Mapa>();
		try {
		String comando 	= " SELECT MATRICULA || TRUNC(NEXT_DAY(FECHA, 'SUNDAY') - 7) AS LLAVE,"
						+ " SUM(HORAS) AS VALOR"
						+ " FROM TRAB_INFORME_ALUM"
						+ " WHERE (STATUS = 'C' OR STATUS = 'X') AND PERIODO_ID = ?"
						+ " GROUP BY MATRICULA, TRUNC(NEXT_DAY(FECHA, 'SUNDAY') - 7)"+orden;
		list = enocJdbc.query(comando, new aca.MapaMapper(), periodoId);
		for(Mapa alum: list) {
			map.put(alum.getLlave(), alum.getValor());
		}
		}catch (Exception ex) {
			System.out.println("Error - aca.trabajo.spring.TrabInformeAlumDao|mapHorasPorSemana|:"+ex);
		}
		return map;
	}

	public HashMap<String, String> mapHorasPorMes(String periodoId, String orden) {
		HashMap<String, String> map		= new HashMap<String,String>();
		List<aca.Mapa> list 				= new ArrayList<aca.Mapa>();
		try {
		String comando 	= " WITH WeeklyHours AS ("
						+ " SELECT MATRICULA, TO_CHAR(FECHA, 'YYYY-MM') AS YEAR_MONTH, LEAST(COALESCE(SUM(HORAS), 0), 8) AS WEEKLY_HOURS_CAPPED"
						+ " FROM TRAB_INFORME_ALUM"
						+ " WHERE (STATUS = 'C' OR STATUS = 'X') AND PERIODO_ID = ?"
						+ " GROUP BY MATRICULA, TRUNC(NEXT_DAY(FECHA, 'SUNDAY') - 7), TO_CHAR(FECHA, 'YYYY-MM'))"
						+ " SELECT MATRICULA || '-' || YEAR_MONTH AS LLAVE, SUM(WEEKLY_HOURS_CAPPED) AS VALOR"
						+ " FROM WeeklyHours"
						+ " GROUP BY MATRICULA, YEAR_MONTH ORDER BY MATRICULA, YEAR_MONTH"
						+orden;
		list = enocJdbc.query(comando, new aca.MapaMapper(), periodoId);
		for(Mapa alum: list) {
			map.put(alum.getLlave(), alum.getValor());
		}
		}catch (Exception ex) {
			System.out.println("Error - aca.trabajo.spring.TrabInformeAlumDao|mapHorasPorMes|:"+ex);
		}
		return map;
	}

	public HashMap<String, String> mapHorasTotPorMes(String periodoId, String orden) {
		HashMap<String, String> map		= new HashMap<String,String>();
		List<aca.Mapa> list 				= new ArrayList<aca.Mapa>();
		try {
		String comando 	= " WITH WeeklyHours AS ("
						+ " SELECT MATRICULA, TO_CHAR(FECHA, 'YYYY-MM') AS YEAR_MONTH, COALESCE(SUM(HORAS), 0) AS WEEKLY_HOURS_CAPPED"
						+ " FROM TRAB_INFORME_ALUM"
						+ " WHERE (STATUS = 'C' OR STATUS = 'X') AND PERIODO_ID = ?"
						+ " GROUP BY MATRICULA, TRUNC(NEXT_DAY(FECHA, 'SUNDAY') - 7), TO_CHAR(FECHA, 'YYYY-MM'))"
						+ " SELECT MATRICULA || '-' || YEAR_MONTH AS LLAVE, SUM(WEEKLY_HOURS_CAPPED) AS VALOR"
						+ " FROM WeeklyHours"
						+ " GROUP BY MATRICULA, YEAR_MONTH ORDER BY MATRICULA, YEAR_MONTH"
						+orden;
		list = enocJdbc.query(comando, new aca.MapaMapper(), periodoId);
		for(Mapa alum: list) {
			map.put(alum.getLlave(), alum.getValor());
		}
		}catch (Exception ex) {
			System.out.println("Error - aca.trabajo.spring.TrabInformeAlumDao|mapHorasTotPorMes|:"+ex);
		}
		return map;
	}

    public List<String> semanasDisponibles(Date minimaSemanaDisponible) {
        List<String> availableWeeks = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");

        // Calendar setup
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY); // Start on Monday

        Calendar minCal = Calendar.getInstance();
        minCal.setTime(minimaSemanaDisponible);
        minCal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY); // Align to the nearest Monday

        // Generate the list of available weeks
        while (minCal.before(cal) || minCal.equals(cal)) {
            String weekStart = sdf.format(minCal.getTime()); // Sunday
			weekStart = weekStart.toUpperCase();
            minCal.add(Calendar.DATE, 6); // Move to Saturday
            String weekEnd = sdf.format(minCal.getTime());
			weekEnd = weekEnd.toUpperCase();

            // Store the formatted week range
            availableWeeks.add(weekStart + " to " + weekEnd);

            // Move back to next Monday
            minCal.add(Calendar.DATE, 1);
        }
        return availableWeeks;
    }

	public HashMap<String, String> mapHorasAlumPorSemana(String periodoId, String codigoPersonal, String orden) {
		HashMap<String, String> map		= new HashMap<String,String>();
		List<aca.Mapa> list 				= new ArrayList<aca.Mapa>();
		try {
		Object[] parametros = new Object[] {periodoId, codigoPersonal};
		String comando 	= " SELECT MATRICULA || TRUNC(NEXT_DAY(FECHA, 'SUNDAY') - 7) AS LLAVE,"
						+ " LEAST(COALESCE(SUM(HORAS), 0), 8) AS VALOR"
						+ " FROM TRAB_INFORME_ALUM"
						+ " WHERE (STATUS = 'C' OR STATUS = 'X') AND PERIODO_ID = ? AND MATRICULA = ?"
						+ " GROUP BY MATRICULA, TRUNC(NEXT_DAY(FECHA, 'SUNDAY') - 7)"+orden;
		list = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
		for(Mapa alum: list) {
			map.put(alum.getLlave(), alum.getValor());
		}
		}catch (Exception ex) {
			System.out.println("Error - aca.trabajo.spring.TrabInformeAlumDao|mapHorasAlumPorSemana|:"+ex);
		}
		return map;
	}

	public HashMap<String, String> mapHorasTotAlumPorSemana(String periodoId, String codigoPersonal, String orden) {
		HashMap<String, String> map		= new HashMap<String,String>();
		List<aca.Mapa> list 				= new ArrayList<aca.Mapa>();
		try {
		Object[] parametros = new Object[] {periodoId, codigoPersonal};
		String comando 	= " SELECT MATRICULA || TRUNC(NEXT_DAY(FECHA, 'SUNDAY') - 7) AS LLAVE,"
						+ " COALESCE(SUM(HORAS), 0) AS VALOR"
						+ " FROM TRAB_INFORME_ALUM"
						+ " WHERE (STATUS = 'C' OR STATUS = 'X') AND PERIODO_ID = ? AND MATRICULA = ?"
						+ " GROUP BY MATRICULA, TRUNC(NEXT_DAY(FECHA, 'SUNDAY') - 7)"+orden;
		list = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
		for(Mapa alum: list) {
			map.put(alum.getLlave(), alum.getValor());
		}
		}catch (Exception ex) {
			System.out.println("Error - aca.trabajo.spring.TrabInformeAlumDao|mapHorasTotAlumPorSemana|:"+ex);
		}
		return map;
	}

	public HashMap<String, String> mapDeficitHorasPorSemana(String periodoId, String orden) {
		HashMap<String, String> map		= new HashMap<String,String>();
		List<aca.Mapa> list 				= new ArrayList<aca.Mapa>();
		try {
		String comando 	= " SELECT MATRICULA || TRUNC(NEXT_DAY(FECHA, 'SUNDAY') - 7) AS LLAVE,"
						+ " LEAST(COALESCE(SUM(HORAS), 0), 8) AS VALOR"
						+ " FROM TRAB_INFORME_ALUM"
						+ " WHERE (STATUS = 'C' OR STATUS = 'X') AND PERIODO_ID = ?"
						+ " HAVING LEAST(COALESCE(SUM(HORAS), 0), 8)  < 8"
						+ " GROUP BY MATRICULA, TRUNC(NEXT_DAY(FECHA, 'SUNDAY') - 7)"+orden;
		list = enocJdbc.query(comando, new aca.MapaMapper(), periodoId);
		for(Mapa alum: list) {
			map.put(alum.getLlave(), alum.getValor());
		}
		}catch (Exception ex) {
			System.out.println("Error - aca.trabajo.spring.TrabInformeAlumDao|mapDeficitHorasPorSemana|:"+ex);
		}
		return map;
	}
}
