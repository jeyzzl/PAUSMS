package aca.saii.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SaiiAlumnoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public String maximoReg(){		
		int maximo = 0;
		try{
			String comando = "SELECT COALESCE(MAX(FOLIO),0)+1 FROM ENOC.SAII_ALUMNO";	
 			maximo 	= enocJdbc.queryForObject(comando, Integer.class);						
		}catch(Exception ex){
			System.out.println("Error - aca.saii.spring.SaiiAlumnoDao|maximoReg|:"+ex);
			ex.printStackTrace();
		}		
		return String.valueOf(maximo);
	}
		
	public SaiiAlumno mapeaRegId( String folio){
		
		SaiiAlumno sep = new SaiiAlumno();
		try{
			String comando = "SELECT FOLIO, PLANTEL, PLAN_SEP, CICLO, CURP, NOMBRE, PATERNO, MATERNO, FECHA, CODIGO_PERSONAL, PLAN_UM, GENERO, EDAD, GRADO," + 
					" PAIS_ID, ESTADO_ID, PREPA_LUGAR, USADO FROM ENOC.SAII_ALUMNO WHERE FOLIO = TO_NUMBER(?,'9999999')"; 
			Object[] parametros = new Object[] {folio};
 			sep = enocJdbc.queryForObject(comando, new SaiiAlumnoMapper(), parametros);			
						
		}catch(Exception ex){
			System.out.println("Error - aca.saii.spring.SaiiAlumnoDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		
		return sep;
	}
	
	public boolean insertReg(SaiiAlumno alumno ) {
		boolean ok = false;

		try{
			String comando = "INSERT INTO ENOC.SAII_ALUMNO"
					+ " (FOLIO, PLANTEL, PLAN_SEP, CICLO, CURP, NOMBRE, PATERNO, MATERNO,"
					+ " FECHA, CODIGO_PERSONAL, PLAN_UM, GENERO, EDAD, GRADO, "
					+ " PAIS_ID, ESTADO_ID, PREPA_LUGAR, USADO) "
					+ " VALUES( TO_NUMBER(?,'9999999'),?, ?, TO_NUMBER(?,'99'), ?, ?, ?, ?, "
					+ " TO_DATE(?, 'DD/MM/YYYY'), ?, ?, ?, TO_NUMBER(?,'9999'), TO_NUMBER(?,'99'), "
					+ " TO_NUMBER(?,'999'), TO_NUMBER(?,'999'), TO_NUMBER(?,'99'), ?) ";
			
			Object[] parametros = new Object[] {
				alumno.getFolio(), alumno.getPlantel(), alumno.getPlanSep(), alumno.getCiclo(), alumno.getCurp(), alumno.getNombre(), alumno.getPaterno(), alumno.getMaterno(), 
				alumno.getFecha(), alumno.getCodigoPersonal(), alumno.getPlanUm(), alumno.getGenero(), alumno.getEdad(), alumno.getGrado(),
				alumno.getPaisId(), alumno.getEstadoId(), alumno.getPrepaLugar(), alumno.getUsado()
			};			
			if (enocJdbc.update(comando,parametros) >=1 ){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.saii.spring.SaiiAlumnoDao|insertReg|:"+ex);			
		}
		return ok;
	}	

	public boolean deleteReg(String folio) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.SAII_ALUMNO WHERE FOLIO = TO_NUMBER(?,'9999999')";
			Object[] parametros = new Object[] {folio};			
			if (enocJdbc.update(comando,parametros) >=1 ){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.saii.spring.SaiiAlumnoDao|deleteReg|:"+ex);			
		}
		return ok;
	}

	public boolean existeReg(String folio) {
		boolean ok = false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.SAII_ALUMNO WHERE FOLIO = TO_NUMBER(?,'9999999')";
			Object[] parametros = new Object[] {folio};			
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1 ){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.saii.spring.SaiiAlumnoDao|existeReg|:"+ex);			
		}
		return ok;
	}
	
	public boolean deleteRegPorFecha(String fecha) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.SAII_ALUMNO WHERE FECHA = TO_DATE(?, 'DD/MM/YYYY')";
			Object[] parametros = new Object[] {fecha};
			if (enocJdbc.update(comando,parametros) >=1 ){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.saii.spring.SaiiAlumnoDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public boolean updateReg(SaiiAlumno alumno ) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.SAII_ALUMNO"
				+ " SET PLANTEL = ?,"
				+ " PLAN_SEP = ?,"
				+ " CICLO = TO_NUMBER(?,'99'),"
				+ " NOMBRE = ?,"
				+ " PATERNO = ?,"
				+ " MATERNO = ?,"
				+ " PLAN_UM = ?,"
				+ " GENERO = ?,"
				+ " EDAD = TO_NUMBER(?,'9999'),"
				+ " GRADO = TO_NUMBER(?,'99'),"
				+ " PAIS_ID = TO_NUMBER(?,'999'),"
				+ " ESTADO_ID = TO_NUMBER(?,'999'),"
				+ " PREPA_LUGAR = TO_NUMBER(?,'99'),"
				+ " USADO = ?,"
				+ " CURP = ?,"
				+ " CODIGO_PERSONAL = ?,"
				+ " FECHA = TO_DATE(?, 'DD/MM/YYYY')"
				+ " WHERE FOLIO = TO_NUMBER(?,'9999999')";
			
			Object[] parametros = new Object[] {alumno.getPlantel(), alumno.getPlanSep(), alumno.getCiclo(), alumno.getNombre(), alumno.getPaterno(),
					alumno.getMaterno(), alumno.getPlanUm(), alumno.getGenero(), alumno.getEdad(), alumno.getGrado(), alumno.getPaisId(),alumno.getEstadoId(),
					alumno.getPrepaLugar(), alumno.getUsado(), alumno.getCurp(), alumno.getCodigoPersonal(), alumno.getFecha(),alumno.getFolio()};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.saii.spring.SaiiAlumnoDao|updateReg|:"+ex);		
		}
		return ok;
	}
	
	public List<String> listPlanes( String fecha, String orden ) {
		List<String> lista = new ArrayList<String>();
		try{
			String comando = "SELECT DISTINCT(PLAN_UM) FROM SAII_ALUMNO WHERE TO_CHAR(FECHA,'DD/MM/YYYY') = ? "+orden;
			Object[] parametros = new Object[] {fecha};
			lista = enocJdbc.queryForList(comando, String.class, parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.saii.spring.SaiiAlumnoDao|listPlanes|:"+ex);
		}
		return lista;
	}
	
	public List<SaiiAlumno> listAlumno(String codigoAlumno ) {
		List<SaiiAlumno> lista = new ArrayList<SaiiAlumno>();
		try{
			String comando = "SELECT * FROM SAII_ALUMNO WHERE CODIGO_PERSONAL = ? ORDER BY FECHA DESC";
			Object[] parametros = new Object[] {codigoAlumno};
			lista = enocJdbc.query(comando, new SaiiAlumnoMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.saii.spring.SaiiAlumnoDao|listAlumno|:"+ex);
		}
		return lista;
	}

	public List<SaiiAlumno> lisPorFecha(String fecha, String orden ) {
		List<SaiiAlumno> lista = new ArrayList<SaiiAlumno>();
		try{
			String comando = "SELECT * FROM SAII_ALUMNO WHERE FECHA = TO_DATE(?,'YYYY/MM/DD') " + orden;
			Object[] parametros = new Object[] {fecha};
			lista = enocJdbc.query(comando, new SaiiAlumnoMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.saii.spring.SaiiAlumnoDao|listAlumno|:"+ex);
		}
		return lista;
	}
	
	public List<SaiiAlumno> lisAlumnosPlanGrupo(String fecha, String grupoId) {
		List<SaiiAlumno> lista = new ArrayList<SaiiAlumno>();
		try{
			String comando = "SELECT * FROM ENOC.SAII_ALUMNO WHERE TO_CHAR(FECHA,'DD/MM/YYYY')= ? AND PLAN_UM IN (SELECT PLAN_ID FROM ENOC.SAII_GRUPO WHERE GRUPO_ID = TO_NUMBER(?,'9999999')) ORDER BY PLAN_UM";
			Object[] parametros = new Object[] {fecha, grupoId};
			lista = enocJdbc.query(comando, new SaiiAlumnoMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.saii.spring.SaiiAlumnoDao|lisAlumnosPlanGrupo|:"+ex);
		}
		return lista;
	}

	public List<aca.Mapa> listFechaSubio() {
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT TO_CHAR(FECHA,'YYYY/MM/DD') AS LLAVE, COUNT(*) AS VALOR FROM SAII_ALUMNO GROUP BY FECHA ORDER BY FECHA DESC";
			lista = enocJdbc.query(comando,  new aca.MapaMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.saii.spring.SaiiAlumnoDao|listFechaSubio|:"+ex);
		}
		return lista;
	}
	
	public HashMap<String,String> mapaGenero(String fecha) {
		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT GENERO AS LLAVE, COUNT(*) AS VALOR FROM ENOC.SAII_ALUMNO WHERE TO_CHAR(FECHA,'DD/MM/YYYY') = ? GROUP BY GENERO";
			Object[] parametros = new Object[] {fecha};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.saii.spring.SaiiAlumnoDao|mapaGenero|:"+ex);
		}
		
		return mapa;
	}
	
	public int getTotal( String fecha){
		
		int total = 0;
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.SAII_ALUMNO WHERE TO_CHAR(FECHA,'DD/MM/YYYY') = ?"; 
			Object[] parametros = new Object[] {fecha};			
 			total = enocJdbc.queryForObject(comando, Integer.class, parametros);						
		}catch(Exception ex){
			System.out.println("Error - aca.saii.spring.SaiiAlumnoDao|getTotal|:"+ex);
			ex.printStackTrace();
		}		
		return total;
	}
	
	public int getPorRangos( String fecha, int rangoIni, int rangoFin){		
		int total = 0;
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.SAII_ALUMNO WHERE WHERE TO_CHAR(FECHA,'DD/MM/YYYY') = ? AND EDAD >= ? AND EDAD <= ?"; 
			Object[] parametros = new Object[] {fecha, rangoIni, rangoFin};			
 			total = enocJdbc.queryForObject(comando, Integer.class, parametros);						
		}catch(Exception ex){
			System.out.println("Error - aca.saii.spring.SaiiAlumnoDao|getPorRangos|:"+ex);
			ex.printStackTrace();
		}		
		return total;
	}
	
	//Total de Alumnos de Primer Ingreso
	
	public int getByIngreso_genero(String fecha, String periodoId, String genero,  String grado){		
		int total = 0;
		try{
			String comando = "SELECT COUNT(*) FROM SAII_ALUMNO WHERE TO_CHAR(FECHA, 'DD/MM/YYYY') = ? AND PLAN_UM = ? AND GENERO = ? AND GRADO = ?"; 
			Object[] parametros = new Object[] {fecha, periodoId, genero, grado};			
 			total = enocJdbc.queryForObject(comando, Integer.class, parametros);						
		}catch(Exception ex){
			System.out.println("Error - aca.saii.spring.SaiiAlumnoDao|getByIngreso_genero|:"+ex);
			ex.printStackTrace();
		}		
		return total;
	}
	
	public int getByIngreso_generoPorGrupo(String fecha, String grupoId, String genero,  String grado){		
		int total = 0;
		try{
			String comando = "SELECT COUNT(*) FROM SAII_ALUMNO WHERE TO_CHAR(FECHA, 'DD/MM/YYYY') = ? AND PLAN_UM IN (SELECT PLAN_ID FROM ENOC.SAII_GRUPO WHERE GRUPO_ID = ?) AND GENERO = ? AND GRADO = ?"; 
			Object[] parametros = new Object[] {fecha, grupoId, genero, grado};			
			total = enocJdbc.queryForObject(comando, Integer.class, parametros);						
		}catch(Exception ex){
			System.out.println("Error - aca.saii.spring.SaiiAlumnoDao|getByIngreso_generoPorGrupo|:"+ex);
			ex.printStackTrace();
		}		
		return total;
	}

	public int getByIngreso_Edad( String fecha, String periodoId, int min, int max){		
		int total = 0;
		try{
			String comando = "SELECT COUNT(*) FROM SAII_ALUMNO WHERE TO_CHAR(FECHA, 'DD/MM/YYYY') = ? AND PLAN_UM = ? AND EDAD BETWEEN ? AND ? AND GRADO = 1"; 
			Object[] parametros = new Object[] {fecha, periodoId, min, max};			
 			total = enocJdbc.queryForObject(comando, Integer.class, parametros);						
		}catch(Exception ex){
			System.out.println("Error - aca.saii.spring.SaiiAlumnoDao|getByIngreso_Edad|:"+ex);
			ex.printStackTrace();
		}		
		return total;
	}

	public int getByIngreso_EdadPorGrupo( String fecha, String grupoId, int min, int max){		
		int total = 0;
		try{
			String comando = "SELECT COUNT(*) FROM SAII_ALUMNO WHERE TO_CHAR(FECHA, 'DD/MM/YYYY') = ? AND PLAN_UM IN (SELECT PLAN_ID FROM ENOC.SAII_GRUPO WHERE GRUPO_ID = ?) AND EDAD BETWEEN ? AND ? AND GRADO = 1"; 
			Object[] parametros = new Object[] {fecha, grupoId, min, max};			
			total = enocJdbc.queryForObject(comando, Integer.class, parametros);						
		}catch(Exception ex){
			System.out.println("Error - aca.saii.spring.SaiiAlumnoDao|getByIngreso_EdadPorGrupo|:"+ex);
			ex.printStackTrace();
		}		
		return total;
	}
	
	public int getPrimerIngresoExtranjeros( String fecha, String periodoId){
		
		int total = 0;
		try{
			String comando = "SELECT COUNT(*) FROM SAII_ALUMNO WHERE TO_CHAR(FECHA, 'DD/MM/YYYY') = ? AND PLAN_UM = ? AND PAIS_ID <> 91 AND GRADO = 1 "; 
			Object[] parametros = new Object[] {fecha, periodoId};			
 			total = enocJdbc.queryForObject(comando, Integer.class, parametros);
 			
		}catch(Exception ex){
			System.out.println("Error - aca.saii.spring.SaiiAlumnoDao|getPrimerIngresoExtranjeros|:"+ex);
			ex.printStackTrace();
		}
		
		return total;
	}
	
	public int getPrimerIngresoExtranjerosPorGrupo( String fecha, String grupoId){
		
		int total = 0;
		try{
			String comando = "SELECT COUNT(*) FROM SAII_ALUMNO WHERE TO_CHAR(FECHA, 'DD/MM/YYYY') = ? AND PLAN_UM IN (SELECT PLAN_ID FROM ENOC.SAII_GRUPO WHERE GRUPO_ID = ?) AND PAIS_ID <> 91 AND GRADO = 1 "; 
			Object[] parametros = new Object[] {fecha, grupoId};			
			total = enocJdbc.queryForObject(comando, Integer.class, parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.saii.spring.SaiiAlumnoDao|getPrimerIngresoExtranjerosPorGrupo|:"+ex);
			ex.printStackTrace();
		}
		
		return total;
	}
	
	//Total de Alumnos de Reingreso
	
	public int getByReIngreso_genero( String fecha, String periodoId, String genero ){
		
		int total = 0;
		try{
			String comando = "SELECT COUNT(*) FROM SAII_ALUMNO WHERE TO_CHAR(FECHA, 'DD/MM/YYYY') = ? AND PLAN_UM = ? AND GENERO = ? AND GRADO <> 1"; 
			Object[] parametros = new Object[] {fecha, periodoId, genero};			
 			total = enocJdbc.queryForObject(comando, Integer.class, parametros);						
		}catch(Exception ex){
			System.out.println("Error - aca.saii.spring.SaiiAlumnoDao|getByReIngreso_genero|:"+ex);
			ex.printStackTrace();
		}
		
		return total;
	}

	public int getByReIngreso_generoPorGrupo( String fecha, String grupoId, String genero ){
		
		int total = 0;
		try{
			String comando = "SELECT COUNT(*) FROM SAII_ALUMNO WHERE TO_CHAR(FECHA, 'DD/MM/YYYY') = ? AND PLAN_UM IN (SELECT PLAN_ID FROM ENOC.SAII_GRUPO WHERE GRUPO_ID = ?) AND GENERO = ? AND GRADO <> 1"; 
			Object[] parametros = new Object[] {fecha, grupoId, genero};			
			total = enocJdbc.queryForObject(comando, Integer.class, parametros);						
		}catch(Exception ex){
			System.out.println("Error - aca.saii.spring.SaiiAlumnoDao|getByReIngreso_generoPorGrupo|:"+ex);
			ex.printStackTrace();
		}
		
		return total;
	}
	
	public int getByReIngreso_Edad( String fecha, String periodoId, int min, int max){
		
		int total = 0;
		try{
			String comando = "SELECT COUNT(*) FROM SAII_ALUMNO WHERE TO_CHAR(FECHA, 'DD/MM/YYYY') = ? AND PLAN_UM = ? AND EDAD BETWEEN ? AND ? AND GRADO <> 1"; 
			Object[] parametros = new Object[] {fecha, periodoId, min, max};			
 			total = enocJdbc.queryForObject(comando, Integer.class, parametros);						
		}catch(Exception ex){
			System.out.println("Error - aca.saii.spring.SaiiAlumnoDao|getByReIngreso_Edad|:"+ex);
			ex.printStackTrace();
		}
		
		return total;
	}
	
	public int getByReIngreso_EdadPorGrupo( String fecha, String grupoId, int min, int max){
		
		int total = 0;
		try{
			String comando = "SELECT COUNT(*) FROM SAII_ALUMNO WHERE TO_CHAR(FECHA, 'DD/MM/YYYY') = ? AND PLAN_UM IN (SELECT PLAN_ID FROM ENOC.SAII_GRUPO WHERE GRUPO_ID = ?) AND EDAD BETWEEN ? AND ? AND GRADO <> 1"; 
			Object[] parametros = new Object[] {fecha, grupoId, min, max};			
			total = enocJdbc.queryForObject(comando, Integer.class, parametros);						
		}catch(Exception ex){
			System.out.println("Error - aca.saii.spring.SaiiAlumnoDao|getByReIngreso_EdadPorGrupo|:"+ex);
			ex.printStackTrace();
		}
		
		return total;
	}
	
	public int getReIngresoExtranjeros( String fecha, String periodoId){
		
		int total = 0;
		try{
			String comando = "SELECT COUNT(*) FROM SAII_ALUMNO WHERE TO_CHAR(FECHA, 'DD/MM/YYYY') = ? AND PLAN_UM = ? AND PAIS_ID <> 91 AND GRADO <> 1 "; 
			Object[] parametros = new Object[] {fecha, periodoId};			
 			total = enocJdbc.queryForObject(comando, Integer.class, parametros);
 			
		}catch(Exception ex){
			System.out.println("Error - aca.saii.spring.SaiiAlumnoDao|getReIngresoExtranjeros|:"+ex);
			ex.printStackTrace();
		}
		
		return total;
	}
	
	public int getReIngresoExtranjerosPorGrupo(String fecha, String grupoId){
		
		int total = 0;
		try{
			String comando = "SELECT COUNT(*) FROM SAII_ALUMNO WHERE TO_CHAR(FECHA, 'DD/MM/YYYY') = ? AND PLAN_UM IN (SELECT PLAN_ID FROM ENOC.SAII_GRUPO WHERE GRUPO_ID = ?) AND PAIS_ID <> 91 AND GRADO <> 1 "; 
			Object[] parametros = new Object[] {fecha, grupoId};			
			total = enocJdbc.queryForObject(comando, Integer.class, parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.saii.spring.SaiiAlumnoDao|getReIngresoExtranjerosPorGrupo|:"+ex);
			ex.printStackTrace();
		}
		
		return total;
	}
	
	//Total de Alumnos
	public int getByTotal_genero( String fecha, String periodoId, String genero ){
		
		int total = 0;
		try{
			String comando = "SELECT COUNT(*) FROM SAII_ALUMNO WHERE TO_CHAR(FECHA, 'DD/MM/YYYY') = ? AND PLAN_UM = ? AND GENERO = ?"; 
			Object[] parametros = new Object[] {fecha, periodoId, genero};			
 			total = enocJdbc.queryForObject(comando, Integer.class, parametros);						
		}catch(Exception ex){
			System.out.println("Error - aca.saii.spring.SaiiAlumnoDao|getByTotal_genero|:"+ex);
			ex.printStackTrace();
		}
		
		return total;
	}

	public int getByTotal_generoPorGrupo( String fecha, String grupoId, String genero ){
		
		int total = 0;
		try{
			String comando = "SELECT COUNT(*) FROM SAII_ALUMNO WHERE TO_CHAR(FECHA, 'DD/MM/YYYY') = ? AND PLAN_UM IN (SELECT PLAN_ID FROM ENOC.SAII_GRUPO WHERE GRUPO_ID = ?) AND GENERO = ?"; 
			Object[] parametros = new Object[] {fecha, grupoId, genero};			
			total = enocJdbc.queryForObject(comando, Integer.class, parametros);						
		}catch(Exception ex){
			System.out.println("Error - aca.saii.spring.SaiiAlumnoDao|getByTotal_generoPorGrupo|:"+ex);
			ex.printStackTrace();
		}
		
		return total;
	}
	
	public int getByTotal_Edad( String fecha, String periodoId, int min, int max){
		
		int total = 0;
		try{
			String comando = "SELECT COUNT(*) FROM SAII_ALUMNO WHERE TO_CHAR(FECHA, 'DD/MM/YYYY') = ? AND PLAN_UM = ? AND EDAD BETWEEN ? AND ?"; 
			Object[] parametros = new Object[] {fecha, periodoId, min, max};			
 			total = enocJdbc.queryForObject(comando, Integer.class, parametros);						
		}catch(Exception ex){
			System.out.println("Error - aca.saii.spring.SaiiAlumnoDao|getByTotal_Edad|:"+ex);
			ex.printStackTrace();
		}
		
		return total;
	}

	public int getByTotal_EdadPorGrupo( String fecha, String grupoId, int min, int max){
		int total = 0;
		try{
			String comando = "SELECT COUNT(*) FROM SAII_ALUMNO WHERE TO_CHAR(FECHA, 'DD/MM/YYYY') = ? AND PLAN_UM IN (SELECT PLAN_ID FROM ENOC.SAII_GRUPO WHERE GRUPO_ID = ?) AND EDAD BETWEEN ? AND ?"; 
			Object[] parametros = new Object[] {fecha, grupoId, min, max};			
			total = enocJdbc.queryForObject(comando, Integer.class, parametros);						
		}catch(Exception ex){
			System.out.println("Error - aca.saii.spring.SaiiAlumnoDao|getByTotal_EdadPorGrupo|:"+ex);
			ex.printStackTrace();
		}
		
		return total;
	}
	
	public int getTotalExtranjeros( String fecha, String periodoId){
		
		int total = 0;
		try{
			String comando = "SELECT COUNT(*) FROM SAII_ALUMNO WHERE TO_CHAR(FECHA, 'DD/MM/YYYY') = ? AND PLAN_UM = ? AND PAIS_ID <> 91"; 
			Object[] parametros = new Object[] {fecha, periodoId};			
 			total = enocJdbc.queryForObject(comando, Integer.class, parametros);
 			
		}catch(Exception ex){
			System.out.println("Error - aca.saii.spring.SaiiAlumnoDao|getTotalExtranjeros|:"+ex);
			ex.printStackTrace();
		}
		
		return total;
	}

	public int getTotalExtranjerosPorGrupo( String fecha, String grupoId){
		int total = 0;
		try{
			String comando = "SELECT COUNT(*) FROM SAII_ALUMNO WHERE TO_CHAR(FECHA, 'DD/MM/YYYY') = ? AND PLAN_UM IN (SELECT PLAN_ID FROM ENOC.SAII_GRUPO WHERE GRUPO_ID = ?) AND PAIS_ID <> 91"; 
			Object[] parametros = new Object[] {fecha, grupoId};			
			total = enocJdbc.queryForObject(comando, Integer.class, parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.saii.spring.SaiiAlumnoDao|getTotalExtranjerosPorGrupo|:"+ex);
			ex.printStackTrace();
		}
		
		return total;
	}
	
	//Alumnos Ultimo Año
	
	public int getUltimoAñoAlumnosPlan( String fecha){
		
		int total = 0;
		try{
			String comando = "SELECT COUNT(*) FROM SAII_ALUMNO WHERE TO_CHAR(FECHA,'DD/MM/YYYY') = ?" + 
					"AND GRADO = (SELECT ULTIMO_GRADO FROM SAII_PLAN WHERE PLAN_ID = SAII_ALUMNO.PLAN_UM)"; 
			Object[] parametros = new Object[] {fecha};			
 			total = enocJdbc.queryForObject(comando, Integer.class, parametros);
 			
		}catch(Exception ex){
			System.out.println("Error - aca.saii.spring.SaiiAlumnoDao|getUltimoAñoAlumnosPlan|:"+ex);
			ex.printStackTrace();
		}
		
		return total;
	}

	public int getByUltimoAño_genero( String fecha, String periodoId, String genero ){
		
		int total = 0;
		try{
			String comando = "SELECT COUNT(*) FROM SAII_ALUMNO WHERE TO_CHAR(FECHA,'DD/MM/YYYY') = ?" + 
					"AND GRADO = (SELECT ULTIMO_GRADO FROM SAII_PLAN WHERE PLAN_ID = SAII_ALUMNO.PLAN_UM) AND PLAN_UM = ? AND GENERO = ?"; 
			Object[] parametros = new Object[] {fecha, periodoId, genero};			
 			total = enocJdbc.queryForObject(comando, Integer.class, parametros);						
		}catch(Exception ex){
			System.out.println("Error - aca.saii.spring.SaiiAlumnoDao|getByUltimoAño_genero|:"+ex);
			ex.printStackTrace();
		}
		
		return total;
	}

	public int getByUltimoAño_generoPorGrupo( String fecha, String grupoId, String genero ){
		
		int total = 0;
		try{
			String comando = "SELECT COUNT(*) FROM SAII_ALUMNO WHERE TO_CHAR(FECHA,'DD/MM/YYYY') = ?" + 
					"AND GRADO = (SELECT ULTIMO_GRADO FROM SAII_PLAN WHERE PLAN_ID = SAII_ALUMNO.PLAN_UM) AND PLAN_UM IN (SELECT PLAN_ID FROM ENOC.SAII_GRUPO WHERE GRUPO_ID = ?) AND GENERO = ?"; 
			Object[] parametros = new Object[] {fecha, grupoId, genero};			
			total = enocJdbc.queryForObject(comando, Integer.class, parametros);						
		}catch(Exception ex){
			System.out.println("Error - aca.saii.spring.SaiiAlumnoDao|getByUltimoAño_generoPorGrupo|:"+ex);
			ex.printStackTrace();
		}
		
		return total;
	}
	
	public int getByUltimoAño_Edad( String fecha, String periodoId, int min, int max){
			 	
		int total = 0;
		try{
			String comando = "SELECT COUNT(*) FROM SAII_ALUMNO WHERE TO_CHAR(FECHA,'DD/MM/YYYY') = ?" + 
					"AND GRADO = (SELECT ULTIMO_GRADO FROM SAII_PLAN WHERE PLAN_ID = SAII_ALUMNO.PLAN_UM) AND PLAN_UM = ? AND EDAD BETWEEN ? AND ?"; 
			Object[] parametros = new Object[] {fecha, periodoId, min, max};			
 			total = enocJdbc.queryForObject(comando, Integer.class, parametros);						
		}catch(Exception ex){
			System.out.println("Error - aca.saii.spring.SaiiAlumnoDao|getByUltimoAño_Edad|:"+ex);
			ex.printStackTrace();
		}
		
		return total;
	}
	
	public int getByUltimoAño_EdadPorGrupo( String fecha, String grupoId, int min, int max){
		
		int total = 0;
		try{
			String comando = "SELECT COUNT(*) FROM SAII_ALUMNO WHERE TO_CHAR(FECHA,'DD/MM/YYYY') = ?" + 
					"AND GRADO = (SELECT ULTIMO_GRADO FROM SAII_PLAN WHERE PLAN_ID = SAII_ALUMNO.PLAN_UM) AND PLAN_UM IN (SELECT PLAN_ID FROM ENOC.SAII_GRUPO WHERE GRUPO_ID = ?) AND EDAD BETWEEN ? AND ?"; 
			Object[] parametros = new Object[] {fecha, grupoId, min, max};			
			total = enocJdbc.queryForObject(comando, Integer.class, parametros);						
		}catch(Exception ex){
			System.out.println("Error - aca.saii.spring.SaiiAlumnoDao|getByUltimoAño_EdadPorGrupo|:"+ex);
			ex.printStackTrace();
		}
		
		return total;
	}
	
	public int getTotalReprobados(String periodoId, String fecha, String genero){
		
		int total = 0;
		try{
			String comando = "SELECT COUNT(CODIGO_PERSONAL) FROM SAII_ALUMNO"
					+ " WHERE TO_CHAR(FECHA,'DD/MM/YYYY') = ? AND PLAN_UM = ?"
					+ " AND CODIGO_PERSONAL IN (SELECT DISTINCT(CODIGO_PERSONAL) FROM KRDX_CURSO_ACT WHERE SUBSTR(CURSO_CARGA_ID,1,6)"
					+ " IN (SELECT CARGA_ID FROM CARGA WHERE TO_DATE(?, 'DD/MM/YYYY') BETWEEN F_INICIO AND F_FINAL) AND TIPOCAL_ID = '2') AND GENERO = ?"; 
			Object[] parametros = new Object[] {fecha, periodoId, fecha, genero};			
 			total = enocJdbc.queryForObject(comando, Integer.class, parametros);
 			
		}catch(Exception ex){
			System.out.println("Error - aca.saii.spring.SaiiAlumnoDao|getTotalReprobadosMasc|:"+ex);
			ex.printStackTrace();
		}
		
		return total;
	}
	
	public int getTotalReprobadosPorGrupo(String grupoId, String fecha, String genero){
		
		int total = 0;
		try{
			String comando = "SELECT COUNT(CODIGO_PERSONAL) FROM SAII_ALUMNO"
					+ " WHERE TO_CHAR(FECHA,'DD/MM/YYYY') = ? AND PLAN_UM IN (SELECT PLAN_ID FROM ENOC.SAII_GRUPO WHERE GRUPO_ID = ?)"
					+ " AND CODIGO_PERSONAL IN (SELECT DISTINCT(CODIGO_PERSONAL) FROM KRDX_CURSO_ACT WHERE SUBSTR(CURSO_CARGA_ID,1,6)"
					+ " IN (SELECT CARGA_ID FROM CARGA WHERE TO_DATE(?, 'DD/MM/YYYY') BETWEEN F_INICIO AND F_FINAL) AND TIPOCAL_ID = '2') AND GENERO = ?";
			Object[] parametros = new Object[] {fecha, grupoId, fecha, genero};
			total = enocJdbc.queryForObject(comando, Integer.class, parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.saii.spring.SaiiAlumnoDao|getTotalReprobadosPorGrupo|:"+fecha+":"+ex);
			ex.printStackTrace();
		}
		
		return total;
	}
	
public float getPromedioG(String grupoId, String fecha, String sexo){
		
		float total = 0;
		try{
			String comando = "SELECT AVG(promedio) AS PromResultante"
					+ " FROM("
					+ " SELECT COALESCE(AVG(NOTA),0) AS promedio"
					+ " FROM ALUMNO_CURSO A"
					+ " INNER JOIN ALUM_PERSONAL B"
					+ " ON A.CODIGO_PERSONAL = B.CODIGO_PERSONAL"
					+ " WHERE F_EVALUACION >= TO_DATE(?, 'DD/MM/YYYY') AND TIPOCAL_ID = '1' AND NOTA >= 70 AND B.SEXO = ? AND PLAN_ID IN (SELECT PLAN_ID FROM ENOC.SAII_GRUPO WHERE GRUPO_ID = ?)"
					+ " union"
					+ " SELECT COALESCE(AVG(NOTA_EXTRA),0) AS promedio"
					+ " FROM ALUMNO_CURSO C"
					+ " INNER JOIN ALUM_PERSONAL D"
					+ " ON C.CODIGO_PERSONAL = D.CODIGO_PERSONAL"
					+ " WHERE F_EVALUACION >= TO_DATE(?, 'DD/MM/YYYY') AND TIPOCAL_ID = '1' AND NOTA_EXTRA >= 70 AND D.SEXO = ? AND PLAN_ID IN (SELECT PLAN_ID FROM ENOC.SAII_GRUPO WHERE GRUPO_ID = ?))";
			Object[] parametros = new Object[] {fecha, sexo, grupoId, fecha, sexo, grupoId};
			total = enocJdbc.queryForObject(comando, Float.class, parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.saii.spring.SaiiAlumnoDao|getPromedioG|:"+ex);
			ex.printStackTrace();
		}
		
		return total;
	}
	
	public HashMap<String,String> mapaBecados(String fecha) {
			
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();
			
		try{
			String comando = "SELECT GENERO AS LLAVE, COUNT(*) AS VALOR "
					+ "FROM ENOC.SAII_ALUMNO s INNER JOIN BEC_PUESTO_ALUMNO b "
					+ "ON s.CODIGO_PERSONAL = b.CODIGO_PERSONAL "
					+ "WHERE TO_CHAR(FECHA,'DD/MM/YYYY') = ? AND TO_DATE(?, 'DD/MM/YYYY') "
					+ "BETWEEN b.FECHA_INI AND b.FECHA_FIN GROUP BY s.genero;";
			Object[] parametros = new Object[] {fecha};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.saii.spring.SaiiAlumnoDao|mapaBecados|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapaReprobados(String fecha){
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT GENERO AS LLAVE, COUNT(CODIGO_PERSONAL) AS VALOR FROM ENOC.SAII_ALUMNO WHERE TO_CHAR(FECHA,'DD/MM/YYYY') = ? "
					+ " AND CODIGO_PERSONAL IN(SELECT DISTINCT(CODIGO_PERSONAL) FROM KRDX_CURSO_ACT WHERE SUBSTR(CURSO_CARGA_ID,1,6) IN "
					+ "(SELECT CARGA_ID FROM CARGA WHERE TO_DATE( ? ,'DD/MM/YYYY') BETWEEN F_INICIO AND F_FINAL)"
					+ " AND TIPOCAL_ID = '2') GROUP BY GENERO";
			Object[] parametros = new Object[] {fecha};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.saii.spring.SaiiAlumnoDao|mapaReprobados|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapaPromedio(String fecha, String genero){
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT SUM(CREDITOS(CURSO_ID)* CASE COALESCE(NOTA_EXTRA, 0) WHEN 0 THEN NOTA ELSE NOTA_EXTRA END)/SUM(CREDITOS(CURSO_ID)) "
					+ "FROM KRDX_CURSO_ACT WHERE CODIGO_PERSONAL||SUBSTR(CURSO_ID,1,8) IN (SELECT CODIGO_PERSONAL||PLAN_UM FROM SAII_ALUMNO "
					+ "WHERE TO_CHAR(FECHA, 'DD/MM/YYYY') = ?) AND SUBSTR(CURSO_CARGA_ID,1,6) IN (SELECT CARGA_ID FROM CARGA WHERE TO_DATE(?, 'DD/MM/YYYY') "
					+ "BETWEEN F_INICIO AND F_FINAL) AND TIPOCAL_ID = '1' AND ENOC.ALUM_GENERO(CODIGO_PERSONAL)=?";
			Object[] parametros = new Object[] {fecha, genero};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.saii.spring.SaiiAlumnoDao|mapaPromedio|:"+ex);
		}
		
		return mapa;
	}	
	
	public HashMap<String,String> mapaAlumnosPorPlan(String fecha ){
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT PLAN_UM AS LLAVE, COUNT(*) AS VALOR FROM ENOC.SAII_ALUMNO"
					+ " WHERE FECHA = TO_DATE(?,'DD/MM/YYYY') GROUP BY PLAN_UM";
			Object[] parametros = new Object[] {fecha};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.saii.spring.SaiiAlumnoDao|mapaAlumnosPorPlan|:"+ex);
		}
		
		return mapa;
	}
	
}
