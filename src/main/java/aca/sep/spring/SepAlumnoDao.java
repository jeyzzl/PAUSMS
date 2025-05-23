package aca.sep.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SepAlumnoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public String maximoReg(){		
		int maximo = 0;
		try{
			String comando = "SELECT COALESCE(MAX(FOLIO),0)+1 FROM ENOC.SEP_ALUMNO";	
 			maximo 	= enocJdbc.queryForObject(comando, Integer.class);						
		}catch(Exception ex){
			System.out.println("Error - aca.sep.spring.SepAlumnoDao|maximoReg|:"+ex);
			ex.printStackTrace();
		}		
		return String.valueOf(maximo);
	}
		
	public SepAlumno mapeaRegId( String folio){
		
		SepAlumno sep = new SepAlumno();
		try{
			String comando = "SELECT FOLIO, PLANTEL, PLAN_SEP, CICLO, CURP, NOMBRE, PATERNO, MATERNO, FECHA, CODIGO_PERSONAL, PLAN_UM, GENERO, EDAD, GRADO," + 
					" PAIS_ID, ESTADO_ID, PREPA_LUGAR, USADO FROM ENOC.SEP_ALUMNO WHERE FOLIO = TO_NUMBER(?,'9999999')"; 
			Object[] parametros = new Object[] {folio};
 			sep = enocJdbc.queryForObject(comando,new SepAlumnoMapper(), parametros);			
						
		}catch(Exception ex){
			System.out.println("Error - aca.sep.spring.SepAlumnoDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		
		return sep;
	}
	
	public boolean insertReg(SepAlumno alumno ) {
		boolean ok = false;

		try{
			String comando = "INSERT INTO ENOC.SEP_ALUMNO"
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
			System.out.println("Error - aca.sep.spring.SepAlumnoDao|insertReg|:"+ex);			
		}
		return ok;
	}	

	public boolean deleteReg(String folio) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.SEP_ALUMNO WHERE FOLIO = TO_NUMBER(?,'9999999')";
			Object[] parametros = new Object[] {folio};			
			if (enocJdbc.update(comando,parametros) >=1 ){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.sep.spring.SepAlumnoDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public boolean deleteRegPorFecha(String fecha) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.SEP_ALUMNO WHERE FECHA = TO_DATE(?, 'DD/MM/YYYY')";
			Object[] parametros = new Object[] {fecha};
			if (enocJdbc.update(comando,parametros) >=1 ){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.sep.spring.SepAlumnoDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public boolean updateReg(SepAlumno alumno ) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.SEP_ALUMNO"
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
			System.out.println("Error - aca.sep.spring.SepAlumnoDao|updateReg|:"+ex);		
		}
		return ok;
	}
	
	public List<String> listPlanes( String fecha, String orden ) {
		List<String> lista = new ArrayList<String>();
		try{
			String comando = "SELECT DISTINCT(PLAN_UM) FROM SEP_ALUMNO WHERE TO_CHAR(FECHA,'DD/MM/YYYY') = ? "+orden;
			Object[] parametros = new Object[] {fecha};
			lista = enocJdbc.queryForList(comando, String.class, parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.sep.spring.SepAlumnoDao|listPlanes|:"+ex);
		}
		return lista;
	}
	
	public List<SepAlumno> listAlumno(String codigoAlumno ) {
		List<SepAlumno> lista = new ArrayList<SepAlumno>();
		try{
			String comando = "SELECT * FROM SEP_ALUMNO WHERE CODIGO_PERSONAL = ? ORDER BY FECHA DESC";
			Object[] parametros = new Object[] {codigoAlumno};
			lista = enocJdbc.query(comando, new SepAlumnoMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.sep.spring.SepAlumnoDao|listAlumno|:"+ex);
		}
		return lista;
	}

	public List<SepAlumno> lisPorFecha(String fecha, String orden ) {
		List<SepAlumno> lista = new ArrayList<SepAlumno>();
		try{
			String comando = "SELECT * FROM SEP_ALUMNO WHERE FECHA = TO_DATE(?,'YYYY/MM/DD') " + orden;
			Object[] parametros = new Object[] {fecha};
			lista = enocJdbc.query(comando, new SepAlumnoMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.sep.spring.SepAlumnoDao|listAlumno|:"+ex);
		}
		return lista;
	}
	
	public List<SepAlumno> lisAlumnosPlanGrupo(String fecha, String grupoId) {
		List<SepAlumno> lista = new ArrayList<SepAlumno>();
		try{
			String comando = "SELECT * FROM ENOC.SEP_ALUMNO WHERE TO_CHAR(FECHA,'DD/MM/YYYY')= ? AND PLAN_UM IN (SELECT PLAN_ID FROM ENOC.SAII_GRUPO WHERE GRUPO_ID = TO_NUMBER(?,'9999999'))";
			Object[] parametros = new Object[] {fecha, grupoId};
			lista = enocJdbc.query(comando, new SepAlumnoMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.sep.spring.SepAlumnoDao|lisAlumnosPlanGrupo|:"+ex);
		}
		return lista;
	}
	
	public List<aca.Mapa> listFechaSubio() {
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT TO_CHAR(FECHA,'YYYY/MM/DD') AS LLAVE, COUNT(*) AS VALOR FROM SEP_ALUMNO GROUP BY FECHA ORDER BY FECHA DESC";
			lista = enocJdbc.query(comando,  new aca.MapaMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.sep.spring.SepAlumnoDao|listFechaSubio|:"+ex);
		}
		return lista;
	}
	
	public HashMap<String,String> mapaGenero(String fecha) {
		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT GENERO AS LLAVE, COUNT(*) AS VALOR FROM ENOC.SEP_ALUMNO WHERE TO_CHAR(FECHA,'DD/MM/YYYY') = ? GROUP BY GENERO";
			Object[] parametros = new Object[] {fecha};
			lista = enocJdbc.query(comando,  new aca.MapaMapper(), parametros);
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.sep.spring.SepAlumnoDao|mapaGenero|:"+ex);
		}
		
		return mapa;
	}
	
	public int getTotal( String fecha){
		
		int total = 0;
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.SEP_ALUMNO WHERE TO_CHAR(FECHA,'DD/MM/YYYY') = ?"; 
			Object[] parametros = new Object[] {fecha};			
 			total = enocJdbc.queryForObject(comando, Integer.class, parametros);						
		}catch(Exception ex){
			System.out.println("Error - aca.sep.spring.SepAlumnoDao|getTotal|:"+ex);
			ex.printStackTrace();
		}		
		return total;
	}
	
	public int getPorRangos( String fecha, int rangoIni, int rangoFin){		
		int total = 0;
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.SEP_ALUMNO WHERE WHERE TO_CHAR(FECHA,'DD/MM/YYYY') = ? AND EDAD >= ? AND EDAD <= ?"; 
			Object[] parametros = new Object[] {fecha, rangoIni, rangoFin};			
 			total = enocJdbc.queryForObject(comando, Integer.class, parametros);						
		}catch(Exception ex){
			System.out.println("Error - aca.sep.spring.SepAlumnoDao|getPorRangos|:"+ex);
			ex.printStackTrace();
		}		
		return total;
	}
	
	//Total de Alumnos de Primer Ingreso
	
	public int getByIngreso_genero(String fecha, String periodoId, String genero,  String grado){		
		int total = 0;
		try{
			String comando = "SELECT COUNT(*) FROM SEP_ALUMNO WHERE TO_CHAR(FECHA, 'DD/MM/YYYY') = ? AND PLAN_UM = ? AND GENERO = ? AND GRADO = ?"; 
			Object[] parametros = new Object[] {fecha, periodoId, genero, grado};			
 			total = enocJdbc.queryForObject(comando, Integer.class, parametros);						
		}catch(Exception ex){
			System.out.println("Error - aca.sep.spring.SepAlumnoDao|getByIngreso_genero|:"+ex);
			ex.printStackTrace();
		}		
		return total;
	}
	
	public int getByIngreso_generoPorGrupo(String fecha, String grupoId, String genero,  String grado){		
		int total = 0;
		try{
			String comando = "SELECT COUNT(*) FROM SEP_ALUMNO WHERE TO_CHAR(FECHA, 'DD/MM/YYYY') = ? AND PLAN_UM IN (SELECT PLAN_ID FROM ENOC.SAII_GRUPO WHERE GRUPO_ID = ?) AND GENERO = ? AND GRADO = ?"; 
			Object[] parametros = new Object[] {fecha, grupoId, genero, grado};			
			total = enocJdbc.queryForObject(comando,Integer.class, parametros);						
		}catch(Exception ex){
			System.out.println("Error - aca.sep.spring.SepAlumnoDao|getByIngreso_generoPorGrupo|:"+ex);
			ex.printStackTrace();
		}		
		return total;
	}

	public int getByIngreso_Edad( String fecha, String periodoId, int min, int max){		
		int total = 0;
		try{
			String comando = "SELECT COUNT(*) FROM SEP_ALUMNO WHERE TO_CHAR(FECHA, 'DD/MM/YYYY') = ? AND PLAN_UM = ? AND EDAD BETWEEN ? AND ? AND GRADO = 1"; 
			Object[] parametros = new Object[] {fecha, periodoId, min, max};			
 			total = enocJdbc.queryForObject(comando, Integer.class, parametros);						
		}catch(Exception ex){
			System.out.println("Error - aca.sep.spring.SepAlumnoDao|getByIngreso_Edad|:"+ex);
			ex.printStackTrace();
		}		
		return total;
	}

	public int getByIngreso_EdadPorGrupo( String fecha, String grupoId, int min, int max){		
		int total = 0;
		try{
			String comando = "SELECT COUNT(*) FROM SEP_ALUMNO WHERE TO_CHAR(FECHA, 'DD/MM/YYYY') = ? AND PLAN_UM IN (SELECT PLAN_ID FROM ENOC.SAII_GRUPO WHERE GRUPO_ID = ?) AND EDAD BETWEEN ? AND ? AND GRADO = 1"; 
			Object[] parametros = new Object[] {fecha, grupoId, min, max};			
			total = enocJdbc.queryForObject(comando, Integer.class, parametros);						
		}catch(Exception ex){
			System.out.println("Error - aca.sep.spring.SepAlumnoDao|getByIngreso_EdadPorGrupo|:"+ex);
			ex.printStackTrace();
		}		
		return total;
	}
	
	public int getPrimerIngresoExtranjeros( String fecha, String periodoId){
		
		int total = 0;
		try{
			String comando = "SELECT COUNT(*) FROM SEP_ALUMNO WHERE TO_CHAR(FECHA, 'DD/MM/YYYY') = ? AND PLAN_UM = ? AND PAIS_ID <> 91 AND GRADO = 1 "; 
			Object[] parametros = new Object[] {fecha, periodoId};			
 			total = enocJdbc.queryForObject(comando, Integer.class, parametros);
 			
		}catch(Exception ex){
			System.out.println("Error - aca.sep.spring.SepAlumnoDao|getPrimerIngresoExtranjeros|:"+ex);
			ex.printStackTrace();
		}
		
		return total;
	}
	
	public int getPrimerIngresoExtranjerosPorGrupo( String fecha, String grupoId){
		
		int total = 0;
		try{
			String comando = "SELECT COUNT(*) FROM SEP_ALUMNO WHERE TO_CHAR(FECHA, 'DD/MM/YYYY') = ? AND PLAN_UM IN (SELECT PLAN_ID FROM ENOC.SAII_GRUPO WHERE GRUPO_ID = ?) AND PAIS_ID <> 91 AND GRADO = 1 "; 
			Object[] parametros = new Object[] {fecha, grupoId};			
			total = enocJdbc.queryForObject(comando, Integer.class, parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.sep.spring.SepAlumnoDao|getPrimerIngresoExtranjerosPorGrupo|:"+ex);
			ex.printStackTrace();
		}
		
		return total;
	}
	
	//Total de Alumnos de Reingreso
	
	public int getByReIngreso_genero( String fecha, String periodoId, String genero ){
		
		int total = 0;
		try{
			String comando = "SELECT COUNT(*) FROM SEP_ALUMNO WHERE TO_CHAR(FECHA, 'DD/MM/YYYY') = ? AND PLAN_UM = ? AND GENERO = ? AND GRADO <> 1"; 
			Object[] parametros = new Object[] {fecha, periodoId, genero};			
 			total = enocJdbc.queryForObject(comando, Integer.class, parametros);						
		}catch(Exception ex){
			System.out.println("Error - aca.sep.spring.SepAlumnoDao|getByReIngreso_genero|:"+ex);
			ex.printStackTrace();
		}
		
		return total;
	}

	public int getByReIngreso_generoPorGrupo( String fecha, String grupoId, String genero ){
		
		int total = 0;
		try{
			String comando = "SELECT COUNT(*) FROM SEP_ALUMNO WHERE TO_CHAR(FECHA, 'DD/MM/YYYY') = ? AND PLAN_UM IN (SELECT PLAN_ID FROM ENOC.SAII_GRUPO WHERE GRUPO_ID = ?) AND GENERO = ? AND GRADO <> 1"; 
			Object[] parametros = new Object[] {fecha, grupoId, genero};			
			total = enocJdbc.queryForObject(comando, Integer.class, parametros);						
		}catch(Exception ex){
			System.out.println("Error - aca.sep.spring.SepAlumnoDao|getByReIngreso_generoPorGrupo|:"+ex);
			ex.printStackTrace();
		}
		
		return total;
	}
	
	public int getByReIngreso_Edad( String fecha, String periodoId, int min, int max){
		
		int total = 0;
		try{
			String comando = "SELECT COUNT(*) FROM SEP_ALUMNO WHERE TO_CHAR(FECHA, 'DD/MM/YYYY') = ? AND PLAN_UM = ? AND EDAD BETWEEN ? AND ? AND GRADO <> 1"; 
			Object[] parametros = new Object[] {fecha, periodoId, min, max};			
 			total = enocJdbc.queryForObject(comando, Integer.class, parametros);						
		}catch(Exception ex){
			System.out.println("Error - aca.sep.spring.SepAlumnoDao|getByReIngreso_Edad|:"+ex);
			ex.printStackTrace();
		}
		
		return total;
	}
	
	public int getByReIngreso_EdadPorGrupo( String fecha, String grupoId, int min, int max){
		
		int total = 0;
		try{
			String comando = "SELECT COUNT(*) FROM SEP_ALUMNO WHERE TO_CHAR(FECHA, 'DD/MM/YYYY') = ? AND PLAN_UM IN (SELECT PLAN_ID FROM ENOC.SAII_GRUPO WHERE GRUPO_ID = ?) AND EDAD BETWEEN ? AND ? AND GRADO <> 1"; 
			Object[] parametros = new Object[] {fecha, grupoId, min, max};			
			total = enocJdbc.queryForObject(comando, Integer.class, parametros);						
		}catch(Exception ex){
			System.out.println("Error - aca.sep.spring.SepAlumnoDao|getByReIngreso_EdadPorGrupo|:"+ex);
			ex.printStackTrace();
		}
		
		return total;
	}
	
	public int getReIngresoExtranjeros( String fecha, String periodoId){
		
		int total = 0;
		try{
			String comando = "SELECT COUNT(*) FROM SEP_ALUMNO WHERE TO_CHAR(FECHA, 'DD/MM/YYYY') = ? AND PLAN_UM = ? AND PAIS_ID <> 91 AND GRADO <> 1 "; 
			Object[] parametros = new Object[] {fecha, periodoId};			
 			total = enocJdbc.queryForObject(comando, Integer.class, parametros);
 			
		}catch(Exception ex){
			System.out.println("Error - aca.sep.spring.SepAlumnoDao|getReIngresoExtranjeros|:"+ex);
			ex.printStackTrace();
		}
		
		return total;
	}
	
	public int getReIngresoExtranjerosPorGrupo(String fecha, String grupoId){
		
		int total = 0;
		try{
			String comando = "SELECT COUNT(*) FROM SEP_ALUMNO WHERE TO_CHAR(FECHA, 'DD/MM/YYYY') = ? AND PLAN_UM IN (SELECT PLAN_ID FROM ENOC.SAII_GRUPO WHERE GRUPO_ID = ?) AND PAIS_ID <> 91 AND GRADO <> 1 "; 
			Object[] parametros = new Object[] {fecha, grupoId};			
			total = enocJdbc.queryForObject(comando, Integer.class, parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.sep.spring.SepAlumnoDao|getReIngresoExtranjerosPorGrupo|:"+ex);
			ex.printStackTrace();
		}
		
		return total;
	}
	
	//Total de Alumnos
	public int getByTotal_genero( String fecha, String periodoId, String genero ){
		
		int total = 0;
		try{
			String comando = "SELECT COUNT(*) FROM SEP_ALUMNO WHERE TO_CHAR(FECHA, 'DD/MM/YYYY') = ? AND PLAN_UM = ? AND GENERO = ?"; 
			Object[] parametros = new Object[] {fecha, periodoId, genero};			
 			total = enocJdbc.queryForObject(comando, Integer.class, parametros);						
		}catch(Exception ex){
			System.out.println("Error - aca.sep.spring.SepAlumnoDao|getByTotal_genero|:"+ex);
			ex.printStackTrace();
		}
		
		return total;
	}

	public int getByTotal_generoPorGrupo( String fecha, String grupoId, String genero ){
		
		int total = 0;
		try{
			String comando = "SELECT COUNT(*) FROM SEP_ALUMNO WHERE TO_CHAR(FECHA, 'DD/MM/YYYY') = ? AND PLAN_UM IN (SELECT PLAN_ID FROM ENOC.SAII_GRUPO WHERE GRUPO_ID = ?) AND GENERO = ?"; 
			Object[] parametros = new Object[] {fecha, grupoId, genero};			
			total = enocJdbc.queryForObject(comando, Integer.class, parametros);						
		}catch(Exception ex){
			System.out.println("Error - aca.sep.spring.SepAlumnoDao|getByTotal_generoPorGrupo|:"+ex);
			ex.printStackTrace();
		}
		
		return total;
	}
	
	public int getByTotal_Edad( String fecha, String periodoId, int min, int max){
		
		int total = 0;
		try{
			String comando = "SELECT COUNT(*) FROM SEP_ALUMNO WHERE TO_CHAR(FECHA, 'DD/MM/YYYY') = ? AND PLAN_UM = ? AND EDAD BETWEEN ? AND ?"; 
			Object[] parametros = new Object[] {fecha, periodoId, min, max};			
 			total = enocJdbc.queryForObject(comando, Integer.class, parametros);						
		}catch(Exception ex){
			System.out.println("Error - aca.sep.spring.SepAlumnoDao|getByTotal_Edad|:"+ex);
			ex.printStackTrace();
		}
		
		return total;
	}

	public int getByTotal_EdadPorGrupo( String fecha, String grupoId, int min, int max){
		int total = 0;
		try{
			String comando = "SELECT COUNT(*) FROM SEP_ALUMNO WHERE TO_CHAR(FECHA, 'DD/MM/YYYY') = ? AND PLAN_UM IN (SELECT PLAN_ID FROM ENOC.SAII_GRUPO WHERE GRUPO_ID = ?) AND EDAD BETWEEN ? AND ?"; 
			Object[] parametros = new Object[] {fecha, grupoId, min, max};			
			total = enocJdbc.queryForObject(comando, Integer.class, parametros);						
		}catch(Exception ex){
			System.out.println("Error - aca.sep.spring.SepAlumnoDao|getByTotal_EdadPorGrupo|:"+ex);
			ex.printStackTrace();
		}
		
		return total;
	}
	
	public int getTotalExtranjeros( String fecha, String periodoId){
		
		int total = 0;
		try{
			String comando = "SELECT COUNT(*) FROM SEP_ALUMNO WHERE TO_CHAR(FECHA, 'DD/MM/YYYY') = ? AND PLAN_UM = ? AND PAIS_ID <> 91"; 
			Object[] parametros = new Object[] {fecha, periodoId};			
 			total = enocJdbc.queryForObject(comando, Integer.class, parametros);
 			
		}catch(Exception ex){
			System.out.println("Error - aca.sep.spring.SepAlumnoDao|getTotalExtranjeros|:"+ex);
			ex.printStackTrace();
		}
		
		return total;
	}

	public int getTotalExtranjerosPorGrupo( String fecha, String grupoId){
		int total = 0;
		try{
			String comando = "SELECT COUNT(*) FROM SEP_ALUMNO WHERE TO_CHAR(FECHA, 'DD/MM/YYYY') = ? AND PLAN_UM IN (SELECT PLAN_ID FROM ENOC.SAII_GRUPO WHERE GRUPO_ID = ?) AND PAIS_ID <> 91"; 
			Object[] parametros = new Object[] {fecha, grupoId};			
			total = enocJdbc.queryForObject(comando, Integer.class, parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.sep.spring.SepAlumnoDao|getTotalExtranjerosPorGrupo|:"+ex);
			ex.printStackTrace();
		}
		
		return total;
	}
	
	//Alumnos Ultimo Año
	
	public int getUltimoAñoAlumnosPlan( String fecha){
		
		int total = 0;
		try{
			String comando = "SELECT COUNT(*) FROM SEP_ALUMNO WHERE TO_CHAR(FECHA,'DD/MM/YYYY') = ?" + 
					"AND GRADO = (SELECT ULTIMO_GRADO FROM SAII_PLAN WHERE PLAN_ID = SEP_ALUMNO.PLAN_UM)"; 
			Object[] parametros = new Object[] {fecha};			
 			total = enocJdbc.queryForObject(comando, Integer.class, parametros);
 			
		}catch(Exception ex){
			System.out.println("Error - aca.sep.spring.SepAlumnoDao|getUltimoAñoAlumnosPlan|:"+ex);
			ex.printStackTrace();
		}
		
		return total;
	}

	public int getByUltimoAño_genero( String fecha, String periodoId, String genero ){
		
		int total = 0;
		try{
			String comando = "SELECT COUNT(*) FROM SEP_ALUMNO WHERE TO_CHAR(FECHA,'DD/MM/YYYY') = ?" + 
					"AND GRADO = (SELECT ULTIMO_GRADO FROM SAII_PLAN WHERE PLAN_ID = SEP_ALUMNO.PLAN_UM) AND PLAN_UM = ? AND GENERO = ?"; 
			Object[] parametros = new Object[] {fecha, periodoId, genero};			
 			total = enocJdbc.queryForObject(comando, Integer.class, parametros);						
		}catch(Exception ex){
			System.out.println("Error - aca.sep.spring.SepAlumnoDao|getByUltimoAño_genero|:"+ex);
			ex.printStackTrace();
		}
		
		return total;
	}

	public int getByUltimoAño_generoPorGrupo( String fecha, String grupoId, String genero ){
		
		int total = 0;
		try{
			String comando = "SELECT COUNT(*) FROM SEP_ALUMNO WHERE TO_CHAR(FECHA,'DD/MM/YYYY') = ?" + 
					"AND GRADO = (SELECT ULTIMO_GRADO FROM SAII_PLAN WHERE PLAN_ID = SEP_ALUMNO.PLAN_UM) AND PLAN_UM IN (SELECT PLAN_ID FROM ENOC.SAII_GRUPO WHERE GRUPO_ID = ?) AND GENERO = ?"; 
			Object[] parametros = new Object[] {fecha, grupoId, genero};			
			total = enocJdbc.queryForObject(comando, Integer.class, parametros);						
		}catch(Exception ex){
			System.out.println("Error - aca.sep.spring.SepAlumnoDao|getByUltimoAño_generoPorGrupo|:"+ex);
			ex.printStackTrace();
		}
		
		return total;
	}
	
	public int getByUltimoAño_Edad( String fecha, String periodoId, int min, int max){
			 	
		int total = 0;
		try{
			String comando = "SELECT COUNT(*) FROM SEP_ALUMNO WHERE TO_CHAR(FECHA,'DD/MM/YYYY') = ?" + 
					"AND GRADO = (SELECT ULTIMO_GRADO FROM SAII_PLAN WHERE PLAN_ID = SEP_ALUMNO.PLAN_UM) AND PLAN_UM = ? AND EDAD BETWEEN ? AND ?"; 
			Object[] parametros = new Object[] {fecha, periodoId, min, max};			
 			total = enocJdbc.queryForObject(comando, Integer.class, parametros);						
		}catch(Exception ex){
			System.out.println("Error - aca.sep.spring.SepAlumnoDao|getByUltimoAño_Edad|:"+ex);
			ex.printStackTrace();
		}
		
		return total;
	}
	
	public int getByUltimoAño_EdadPorGrupo( String fecha, String grupoId, int min, int max){
		
		int total = 0;
		try{
			String comando = "SELECT COUNT(*) FROM SEP_ALUMNO WHERE TO_CHAR(FECHA,'DD/MM/YYYY') = ?" + 
					"AND GRADO = (SELECT ULTIMO_GRADO FROM SAII_PLAN WHERE PLAN_ID = SEP_ALUMNO.PLAN_UM) AND PLAN_UM IN (SELECT PLAN_ID FROM ENOC.SAII_GRUPO WHERE GRUPO_ID = ?) AND EDAD BETWEEN ? AND ?"; 
			Object[] parametros = new Object[] {fecha, grupoId, min, max};			
			total = enocJdbc.queryForObject(comando, Integer.class, parametros);						
		}catch(Exception ex){
			System.out.println("Error - aca.sep.spring.SepAlumnoDao|getByUltimoAño_EdadPorGrupo|:"+ex);
			ex.printStackTrace();
		}
		
		return total;
	}
	
	public int getTotalReprobados(String periodoId, String fecha, String genero){
		
		int total = 0;
		try{
			String comando = "SELECT COUNT(CODIGO_PERSONAL) FROM SEP_ALUMNO WHERE TO_CHAR(FECHA,'DD/MM/YYYY') = ? AND PLAN_UM = ?" + 
					"AND CODIGO_PERSONAL IN (SELECT DISTINCT(CODIGO_PERSONAL) FROM KRDX_CURSO_ACT WHERE SUBSTR(CURSO_CARGA_ID,1,6) " + 
					"IN (SELECT CARGA_ID FROM CARGA WHERE TO_DATE('19/09/2016','DD/MM/YYYY') BETWEEN F_INICIO AND F_FINAL) AND TIPOCAL_ID = '2') AND GENERO = ?"; 
			Object[] parametros = new Object[] {fecha, periodoId, genero};			
 			total = enocJdbc.queryForObject(comando, Integer.class, parametros);
 			
		}catch(Exception ex){
			System.out.println("Error - aca.sep.spring.SepAlumnoDao|getTotalReprobadosMasc|:"+ex);
			ex.printStackTrace();
		}
		
		return total;
	}
	
	public int getTotalReprobadosPorGrupo(String grupoId, String fecha, String genero){
		
		int total = 0;
		try{
			String comando = "SELECT COUNT(CODIGO_PERSONAL) FROM SEP_ALUMNO WHERE TO_CHAR(FECHA,'DD/MM/YYYY') = ? AND PLAN_UM IN (SELECT PLAN_ID FROM ENOC.SAII_GRUPO WHERE GRUPO_ID = ?)" + 
					"AND CODIGO_PERSONAL IN (SELECT DISTINCT(CODIGO_PERSONAL) FROM KRDX_CURSO_ACT WHERE SUBSTR(CURSO_CARGA_ID,1,6) " + 
					"IN (SELECT CARGA_ID FROM CARGA WHERE TO_DATE('19/09/2016','DD/MM/YYYY') BETWEEN F_INICIO AND F_FINAL) AND TIPOCAL_ID = '2') AND GENERO = ?"; 
			Object[] parametros = new Object[] {fecha, grupoId, genero};			
			total = enocJdbc.queryForObject(comando, Integer.class, parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.sep.spring.SepAlumnoDao|getTotalReprobadosPorGrupo|:"+ex);
			ex.printStackTrace();
		}
		
		return total;
	}


	/*public float getPromedio(String planId, String fecha, String genero){
		
		float total = 0;
		try{
			System.out.println(fecha + " " + planId);
			String comando = "SELECT SUM(CREDITOS(CURSO_ID)* CASE COALESCE(NOTA_EXTRA,0) WHEN 0 THEN NOTA ELSE NOTA_EXTRA END) /SUM(CREDITOS(CURSO_ID))" + 
					"FROM KRDX_CURSO_ACT WHERE CODIGO_PERSONAL||SUBSTR(CURSO_ID,1,8) IN (SELECT CODIGO_PERSONAL||PLAN_UM FROM SEP_ALUMNO " + 
					"WHERE TO_CHAR(FECHA,'DD/MM/YYYY') = ?  AND PLAN_UM = ?)AND SUBSTR(CURSO_CARGA_ID,1,6) " + 
					"IN (SELECT CARGA_ID FROM CARGA WHERE TO_DATE(?,'DD/MM/YYYY') BETWEEN F_INICIO AND F_FINAL)" + 
					"AND TIPOCAL_ID = '1' AND ENOC.ALUM_GENERO(CODIGO_PERSONAL) = ? ";
			Object[] parametros = new Object[] {fecha, planId, fecha, genero};
 			total = enocJdbc.queryForObject(comando, parametros, Float.class);
 			System.out.println(total);
 			
		}catch(Exception ex){
			System.out.println("Error - aca.sep.spring.SepAlumnoDao|getPromedioMasc|:"+ex);
			ex.printStackTrace();
		}
		
		return total;
	}*/
	
	public float getPromedio(String periodoId, String fecha){
		
		float total = 0;
		try{
			String comando = "SELECT SUM(CREDITOS(CURSO_ID)* CASE COALESCE(NOTA_EXTRA,0) WHEN 0 THEN NOTA ELSE NOTA_EXTRA END) /SUM(CREDITOS(CURSO_ID))" + 
					"FROM KRDX_CURSO_ACT WHERE CODIGO_PERSONAL||SUBSTR(CURSO_ID,1,8) IN (SELECT CODIGO_PERSONAL||PLAN_UM FROM SEP_ALUMNO " + 
					"WHERE TO_CHAR(FECHA,'DD/MM/YYYY') = ? AND PLAN_UM = ?)AND SUBSTR(CURSO_CARGA_ID,1,6) " + 
					"IN (SELECT CARGA_ID FROM CARGA WHERE TO_DATE(?,'DD/MM/YYYY') BETWEEN F_INICIO AND F_FINAL)" + 
					"AND TIPOCAL_ID = '1'"; 
			Object[] parametros = new Object[] {fecha, periodoId, fecha};			
 			total = enocJdbc.queryForObject(comando, Float.class, parametros);
 			System.out.println(total);
		}catch(Exception ex){
			System.out.println("Error - aca.sep.spring.SepAlumnoDao|getPromedio|:"+ex);
			ex.printStackTrace();
		}
		
		return total;
	}
		
	public float getPromedioPorGrupo(String grupoId, String fecha){
		
		float total = 0;
		try{
			String comando = "SELECT SUM(CREDITOS(CURSO_ID)* CASE COALESCE(NOTA_EXTRA,0) WHEN 0 THEN NOTA ELSE NOTA_EXTRA END) /SUM(CREDITOS(CURSO_ID))" + 
					"FROM KRDX_CURSO_ACT WHERE CODIGO_PERSONAL||SUBSTR(CURSO_ID,1,8) IN (SELECT CODIGO_PERSONAL||PLAN_UM FROM SEP_ALUMNO " + 
					"WHERE TO_CHAR(FECHA,'DD/MM/YYYY') = ? AND PLAN_UM IN (SELECT PLAN_ID FROM ENOC.SAII_GRUPO WHERE GRUPO_ID = ?))AND SUBSTR(CURSO_CARGA_ID,1,6) " + 
					"IN (SELECT CARGA_ID FROM CARGA WHERE TO_DATE(?,'DD/MM/YYYY') BETWEEN F_INICIO AND F_FINAL)" + 
					"AND TIPOCAL_ID = '1'"; 
			Object[] parametros = new Object[] {fecha, grupoId, fecha};			
			total = enocJdbc.queryForObject(comando, Float.class, parametros);
			System.out.println(total);
		}catch(Exception ex){
			System.out.println("Error - aca.sep.spring.SepAlumnoDao|getPromedioPorGrupo|:"+ex);
			ex.printStackTrace();
		}
		
		return total;
	}
	
	public HashMap<String,String> mapaBecados(String fecha) {
			
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();
			
		try{
			String comando = "SELECT GENERO AS LLAVE, COUNT(*) AS VALOR "
					+ "FROM ENOC.SEP_ALUMNO s INNER JOIN BEC_PUESTO_ALUMNO b "
					+ "ON s.CODIGO_PERSONAL = b.CODIGO_PERSONAL "
					+ "WHERE TO_CHAR(FECHA,'DD/MM/YYYY') = ? AND TO_DATE(?, 'DD/MM/YYYY') "
					+ "BETWEEN b.FECHA_INI AND b.FECHA_FIN GROUP BY s.genero;";
			Object[] parametros = new Object[] {fecha};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.sep.spring.SepAlumnoDao|mapaBecados|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapaReprobados(String fecha){
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT GENERO AS LLAVE, COUNT(CODIGO_PERSONAL) AS VALOR FROM ENOC.SEP_ALUMNO WHERE TO_CHAR(FECHA,'DD/MM/YYYY') = ? "
					+ " AND CODIGO_PERSONAL IN(SELECT DISTINCT(CODIGO_PERSONAL) FROM KRDX_CURSO_ACT WHERE SUBSTR(CURSO_CARGA_ID,1,6) IN "
					+ "(SELECT CARGA_ID FROM CARGA WHERE TO_DATE( ? ,'DD/MM/YYYY') BETWEEN F_INICIO AND F_FINAL)"
					+ " AND TIPOCAL_ID = '2') GROUP BY GENERO";
			Object[] parametros = new Object[] {fecha};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.sep.spring.SepAlumnoDao|mapaReprobados|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapaPromedio(String fecha, String genero){
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT SUM(CREDITOS(CURSO_ID)* CASE COALESCE(NOTA_EXTRA, 0) WHEN 0 THEN NOTA ELSE NOTA_EXTRA END)/SUM(CREDITOS(CURSO_ID)) "
					+ "FROM KRDX_CURSO_ACT WHERE CODIGO_PERSONAL||SUBSTR(CURSO_ID,1,8) IN (SELECT CODIGO_PERSONAL||PLAN_UM FROM SEP_ALUMNO "
					+ "WHERE TO_CHAR(FECHA, 'DD/MM/YYYY') = ?) AND SUBSTR(CURSO_CARGA_ID,1,6) IN (SELECT CARGA_ID FROM CARGA WHERE TO_DATE(?, 'DD/MM/YYYY') "
					+ "BETWEEN F_INICIO AND F_FINAL) AND TIPOCAL_ID = '1' AND ENOC.ALUM_GENERO(CODIGO_PERSONAL)=?";
			Object[] parametros = new Object[] {fecha, genero};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.sep.spring.SepAlumnoDao|mapaPromedio|:"+ex);
		}
		
		return mapa;
	}	
	
	public HashMap<String,String> mapaAlumnosPorPlan(String fecha ){
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT PLAN_UM AS LLAVE, COUNT(*) AS VALOR FROM ENOC.SEP_ALUMNO"
					+ " WHERE FECHA = TO_DATE(?,'DD/MM/YYYY') GROUP BY PLAN_UM";
			Object[] parametros = new Object[] {fecha};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.sep.spring.SepAlumnoDao|mapaAlumnosPorPlan|:"+ex);
		}
		
		return mapa;
	}

	public HashMap<String,String> mapaAlumnosUltimoPlan(String cargaId){
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();	
		String plan = "";
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, MAX(FECHA) AS VALOR FROM SEP_ALUMNO WHERE CODIGO_PERSONAL IN(SELECT CODIGO_PERSONAL FROM ENOC.ESTADISTICA WHERE CARGA_ID IN ("+cargaId+")) GROUP BY CODIGO_PERSONAL";
			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista ) {
				
				String fecha = map.getValor().substring(0, 10);
				String[] arryfecha = fecha.split("-");
				fecha = arryfecha[2]+"/"+arryfecha[1]+"/"+arryfecha[0];
				
				comando = "SELECT COUNT(PLAN_UM) FROM SEP_ALUMNO WHERE CODIGO_PERSONAL = ? AND FECHA = ?";///

				if(enocJdbc.queryForObject(comando, Integer.class,map.getLlave(),fecha) >= 1) {
					comando = "SELECT PLAN_UM FROM SEP_ALUMNO WHERE CODIGO_PERSONAL = ? AND FECHA = ?";
					plan = enocJdbc.queryForObject(comando, String.class, map.getLlave(), fecha);
					mapa.put(map.getLlave(), plan);
				}
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.sep.spring.SepAlumnoDao|mapaAlumnosUltimoPlan|:"+ex);
		}
		
		return mapa;
	}

	public HashMap<String,SepAlumno> mapaAlumnosCompletosUltimoPlan(String cargaId){
		HashMap<String,SepAlumno> mapa = new HashMap<String,SepAlumno>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();	
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, MAX(FECHA) AS VALOR FROM SEP_ALUMNO WHERE CODIGO_PERSONAL IN(SELECT CODIGO_PERSONAL FROM ENOC.ESTADISTICA WHERE CARGA_ID IN ("+cargaId+")) GROUP BY CODIGO_PERSONAL";
			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista ) {
				
				String fecha = map.getValor().substring(0, 10);
				String[] arryfecha = fecha.split("-");
				fecha = arryfecha[2]+"/"+arryfecha[1]+"/"+arryfecha[0];
				
				comando = "SELECT COUNT(PLAN_UM) FROM SEP_ALUMNO WHERE CODIGO_PERSONAL = ? AND FECHA = ?";
				
				if(enocJdbc.queryForObject(comando, Integer.class,map.getLlave(),fecha) >= 1) {
					SepAlumno objeto = new SepAlumno();
					comando = "SELECT PLANTEL,PLAN_SEP,CICLO,CURP,NOMBRE,PATERNO,MATERNO,FECHA,CODIGO_PERSONAL,PLAN_UM,GENERO,EDAD,GRADO,PAIS_ID,ESTADO_ID,PREPA_LUGAR,"
							+ " USADO, FOLIO FROM SEP_ALUMNO WHERE CODIGO_PERSONAL = ? AND FECHA = ?";
					objeto = enocJdbc.queryForObject(comando, new SepAlumnoMapper(), map.getLlave(), fecha);
					mapa.put(objeto.getCodigoPersonal(), objeto);
				}
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.sep.spring.SepAlumnoDao|mapaAlumnosCompletosUltimoPlan|:"+ex);
		}
		
		return mapa;
	}
	
	public List<String> ultimasDosFechasSepAlumno() {
		List<String> lista = new ArrayList<String>();
		try{
			String comando = "SELECT DISTINCT(TO_CHAR(FECHA,'DD/MM/YYYY')) FROM ENOC.SEP_ALUMNO ORDER BY FECHA DESC FETCH FIRST 2 ROWS ONLY";			
			lista = enocJdbc.queryForList(comando, String.class);
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.AlumPlanDao|ultimasDosFechasSepAlumno|:"+ex);
		}
		
		return lista;
	}
	
}
