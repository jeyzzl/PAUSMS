package aca.internado.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class IntAlumnoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;	
	
	public boolean insertReg( IntAlumno alumno ){
		boolean ok = false;				
		try{
			String comando = "INSERT INTO ENOC.INT_ALUMNO(DORMITORIO_ID, CUARTO_ID, CODIGO_PERSONAL, ORDEN, ESTADO, FECHA_INICIO, FECHA_FINAL) VALUES(?,?,?,?,?,?,?)";
			Object[] parametros = new Object[] {
					alumno.getDormitorioId(),alumno.getCuartoId(), alumno.getCodigoPersonal(), alumno.getOrden(), alumno.getEstado(), alumno.getFechaInicio(), alumno.getFechaFinal()
			};
			if (enocJdbc.update(comando,parametros) == 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.IntAlumnoDao|insertReg|:"+ex);
		}
		return ok;
	}
	
	/*	
	public boolean updateReg( IntAlumno alumno ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			String comando = "UPDATE ENOC.INT_ALUMNO SET CUARTO_ID = ? WHERE DORMITORIO_ID = ? AND CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {
				alumno.getCuartoId(), alumno.getDormitorioId(), alumno.getCodigoPersonal()
			};
			if (enocJdbc.update(comando,parametros) == 1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.IntAlumnoDao|updateReg|:"+ex);
		}
		return ok;
	}
*/	
	public boolean deleteReg( String dormitorioId, String cuartoId, String codigoPersonal ){
		boolean ok = false;
		try{
			String comando = "DELETE FROM ENOC.INT_ALUMNO WHERE DORMITORIO_ID = ? AND CUARTO_ID = ? AND CODIGO_PERSONAL = ?"; 
			Object[] parametros = new Object[] {dormitorioId, cuartoId, codigoPersonal };
			if (enocJdbc.update(comando,parametros) == 1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.IntAlumnoDao|deleteReg|:"+ex);
		}
		return ok;
	}

	public boolean eliminarAlumno( String dormitorioId, String cuartoId, String codigoPersonal ){
		boolean ok = false;
		try{
			String comando = "UPDATE ENOC.INT_ALUMNO SET ESTADO = 'I', FECHA_FINAL = TO_DATE(SYSDATE, 'DD-MON-YY') WHERE DORMITORIO_ID = ? AND CUARTO_ID = ? AND CODIGO_PERSONAL = ?"; 
			Object[] parametros = new Object[] {dormitorioId, cuartoId, codigoPersonal };
			if (enocJdbc.update(comando,parametros) == 1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.IntAlumnoDao|eliminarAlumno|:"+ex);
		}
		return ok;
	}

	public boolean borrarAlumnosDormitorio(String dormitorioId){
		boolean ok = false;
		try{
			String comando = "DELETE FROM ENOC.INT_ALUMNO WHERE DORMITORIO_ID = ?"; 
			Object[] parametros = new Object[] {dormitorioId};
			if (enocJdbc.update(comando,parametros) == 1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.IntAlumnoDao|deleteReg|:"+ex);
		}
		return ok;
	}
	
	public IntAlumno mapeaRegId(String dormitorioId, String cuartoId, String codigoPersonal){
		IntAlumno alumno = new IntAlumno();			
		try{ 
			String comando = "SELECT COUNT(*) FROM ENOC.INT_ALUMNO WHERE DORMITORIO_ID = ? AND CUARTO_ID = ? AND CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {dormitorioId, cuartoId, codigoPersonal };
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1) {
				comando = "SELECT * FROM ENOC.INT_ALUMNO WHERE DORMITORIO_ID = ? AND CUARTO_ID = ? AND CODIGO_PERSONAL = ?";
				alumno = enocJdbc.queryForObject(comando,new IntAlumnoMapper(),parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.IntAlumnoDao|mapeaRegId|:"+ex);
		}
		return alumno;
	}
	
	public IntAlumno mapeaRegId(String codigoPersonal){
		IntAlumno alumno = new IntAlumno();			
		try{ 
			String comando = "SELECT COUNT(*) FROM ENOC.INT_ALUMNO WHERE CODIGO_PERSONAL = ? AND ROWNUM = 1";
			Object[] parametros = new Object[] {codigoPersonal };
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1) {
				comando = "SELECT * FROM ENOC.INT_ALUMNO WHERE CODIGO_PERSONAL = ? AND ROWNUM = 1";
				alumno = enocJdbc.queryForObject(comando,new IntAlumnoMapper(),parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.IntAlumnoDao|mapeaRegId|:"+ex);
		}
		return alumno;
	}
	
	public boolean existeReg(String dormitorioId, String cuartoId, String codigoPersonal){
		boolean ok = false;
		try{ 
			String comando = "SELECT COUNT(*) FROM ENOC.INT_ALUMNO WHERE DORMITORIO_ID = ? AND CUARTO_ID = ? AND CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {dormitorioId, cuartoId, codigoPersonal };
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1) {
				ok=true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.IntAlumnoDao|existeReg|:"+ex);
		}
		return ok;
	}
	public boolean existeReg(String codigoPersonal){
		boolean ok = false;
		try{ 
			String comando = "SELECT COUNT(*) FROM ENOC.INT_ALUMNO WHERE CODIGO_PERSONAL = ? AND ESTADO = 'A'";
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1) {
				ok=true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.IntAlumnoDao|existeReg|:"+ex);
		}		
		return ok;
	}
	public boolean existeReg(String codigoPersonal, String dormitorioId){
		boolean ok = false;
		try{ 
			String comando = "SELECT COUNT(*) FROM ENOC.INT_ALUMNO WHERE DORMITORIO_ID = ? AND CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {dormitorioId, codigoPersonal };
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1) {
				ok=true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.IntAlumnoDao|existeReg|:"+ex);
		}		
		return ok;
	}
 	public boolean esInterno( String codigoPersonal){	
 	
 		boolean ok 				= false; 		
 		try{
 			String comando = "SELECT COUNT(CODIGO_PERSONAL) FROM ENOC.ALUM_ACADEMICO WHERE CODIGO_PERSONAL = ? AND RESIDENCIA_ID = 'I'";				 
 			Object[] parametros = new Object[] {codigoPersonal };
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1) {
				ok=true;
			}
 		}catch(Exception ex){
 			System.out.println("Error - aca.internado.spring.IntAlumnoDao|esInterno|:"+ex);
 		}
 		
 		return ok;
 	}
 	
 	public boolean tieneAlumnos( String cuartoId, String dormitorioId){		
 		boolean ok 				= false; 		
 		try{
 			String comando = "SELECT CODIGO_PERSONAL FROM ENOC.INT_ALUMNO WHERE CUARTO_ID = ? AND DORMITORIO_ID = ?";			 
 			Object[] parametros = new Object[] {cuartoId, dormitorioId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1) {
				ok=true;
			} 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.internado.spring.IntAlumnoDao|esInterno|:"+ex);
 		} 		
 		return ok;
 	}
 	
 	public int numAlumRegistrados( String dormitorioId, String estados){ 		
 		int total 		= 0; 		
 		try{
 			String comando = "SELECT COUNT(*) AS TOTAL FROM ENOC.INT_ALUMNO WHERE DORMITORIO_ID = ? AND ESTADO IN ("+estados+")";
 			Object[] parametros = new Object[] {dormitorioId};
 			total = enocJdbc.queryForObject(comando,Integer.class,parametros); 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.internado.spring.IntAlumnoDao|numAlumRegistrados|:"+ex);
 		} 		
 		return total;
 	}
 	
 	public int numAlumRegInscritos( String dormitorioId, String estados){
 		int total 		= 0; 		
 		try{
 			String comando = " SELECT COUNT(*) AS TOTAL FROM ENOC.INT_ALUMNO "
 					+ " WHERE DORMITORIO_ID = ?"
 					+ " AND ESTADO IN ("+estados+")"
 					+ " AND CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.INSCRITOS)";
 			Object[] parametros = new Object[] {dormitorioId};
 			total = enocJdbc.queryForObject(comando,Integer.class,parametros);			
 		}catch(Exception ex){
 			System.out.println("Error - aca.internado.spring.IntAlumnoDao|numAlumRegInscritos|:"+ex);
 		} 		
 		return total;
 	}
 	
 	public List<IntAlumno> lisTodos(){
 		List<IntAlumno> lis = new ArrayList<IntAlumno>();
 		
 		try {
 			String comando = "SELECT DORMITORIO_ID, CUARTO_ID, CODIGO_PERSONAL, ORDEN, ESTADO FROM ENOC.INT_ALUMNO";
 			lis = enocJdbc.query(comando, new IntAlumnoMapper());
 		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.IntAlumnoDao|lisTodos|:"+ex);
		}
 		return lis;
 	}

	public HashMap<String,IntAlumno> mapPorCuartoYOrden(String dormitorioId){
		List<IntAlumno> lista		= new ArrayList<IntAlumno>();
		HashMap<String,IntAlumno> mapa = new HashMap<String,IntAlumno>();		
		try{
			String comando = "SELECT DORMITORIO_ID,CUARTO_ID,CODIGO_PERSONAL,ORDEN,ESTADO,FECHA_INICIO,FECHA_FINAL FROM ENOC.INT_ALUMNO WHERE DORMITORIO_ID = ? AND ESTADO = 'A'";
			lista = enocJdbc.query(comando, new IntAlumnoMapper(), dormitorioId);
			for(IntAlumno objeto: lista){
				mapa.put(objeto.getCuartoId()+"-"+objeto.getOrden(), objeto);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.IntAlumnoDao|mapPorCuartoYOrden|:"+ex);
		}
		
		return mapa;
	}
 	
 	
 	public HashMap<String,Integer> mapRegistradosEnDormitorios(String estado){
		
		HashMap<String,Integer> map	= new HashMap<String,Integer>();
		List<String> lista 				= new ArrayList<String>();
		
		try{
			String comando = " SELECT DISTINCT(DORMITORIO_ID) FROM ENOC.INT_CUARTO";
			lista =	 enocJdbc.queryForList(comando, String.class);
			for (String dormi : lista){	
				int registrados = 0;
				comando = "SELECT COUNT(*) AS TOTAL FROM ENOC.INT_ALUMNO WHERE DORMITORIO_ID = ? AND ESTADO IN ("+estado+")";				
				registrados = enocJdbc.queryForObject(comando,Integer.class, dormi);				
				map.put(dormi, registrados);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.IntAlumnoDao|mapRegistradosEnDormitorios|:"+ex);
		}
		
		return map;
	}
 	
 	public HashMap<String,Integer> mapaOtros(String fechaIni, String fechaFin) {
		
		HashMap<String, Integer> map	= new HashMap<String,Integer>();
		List<String> lista 				= new ArrayList<String>();
		
		try{
			String comando = " SELECT DISTINCT(DORMITORIO_ID) FROM ENOC.INT_CUARTO";
			lista =	 enocJdbc.queryForList(comando, String.class);
			for (String dormi : lista){	
				int otros = 0;
				comando = "SELECT COUNT(*) FROM ENOC.INT_ALUMNO WHERE DORMITORIO_ID = ? AND ESTADO = 'A'"
						+ "	AND CODIGO_PERSONAL NOT IN(SELECT CODIGO_PERSONAL FROM ENOC.INSCRITOS WHERE RESIDENCIA_ID = 'I'"
						+ " AND DORMITORIO = ? AND F_INSCRIPCION BETWEEN TO_DATE(?,'DD/MM/YYYY')"
						+ "	AND TO_DATE(?,'DD/MM/YYYY'))";				
				otros = enocJdbc.queryForObject(comando,Integer.class, dormi, dormi, fechaIni, fechaFin);				
				map.put(dormi, otros);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.IntAlumnoDao|mapaOtros|:"+ex);
		}
		return map;
	}
 	
 	public HashMap<String,String> mapOcupados(String dormitorioId) {
		HashMap<String,String> mapa		= new HashMap<String,String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT CUARTO_ID AS LLAVE, COUNT(*) AS VALOR FROM INT_ALUMNO WHERE DORMITORIO_ID = ? AND ESTADO = 'A' GROUP BY CUARTO_ID";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), dormitorioId);			
			for(aca.Mapa objeto : lista) {
				mapa.put(objeto.getLlave(), objeto.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.IntAlumnoDao|mapOcupados|:"+ex);
		}
		return mapa;
	}
 	
 	public HashMap<String,String> mapOcupadosPorPasillo(String dormitorioId) {
		HashMap<String,String> mapa		= new HashMap<String,String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT IC.PASILLO AS LLAVE, COUNT(CODIGO_PERSONAL) AS VALOR FROM ENOC.INT_ALUMNO IA, ENOC.INT_CUARTO IC"
					+ " WHERE IA.DORMITORIO_ID = ?"
					+ " AND IC.DORMITORIO_ID = IA.DORMITORIO_ID"
					+ " AND IC.CUARTO_ID = IA.CUARTO_ID"
					+ " GROUP BY IC.PASILLO";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), dormitorioId);			
			for(aca.Mapa objeto : lista) {
				mapa.put(objeto.getLlave(), objeto.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.IntAlumnoDao|mapOcupadosPorPasillo|:"+ex);
		}
		return mapa;
	}
 	
 	public HashMap<String,String> mapInscritosPorCuarto(String dormitorioId) {
		HashMap<String,String> mapa		= new HashMap<String,String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT CUARTO_ID AS LLAVE, COUNT(CODIGO_PERSONAL) AS VALOR FROM INT_ALUMNO "
					+ " WHERE DORMITORIO_ID = ? AND ESTADO = 'A'"
					+ " AND CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.INSCRITOS)"
					+ " GROUP BY CUARTO_ID";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), dormitorioId);			
			for(aca.Mapa objeto : lista) {
				mapa.put(objeto.getLlave(), objeto.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.IntAlumnoDao|mapInscritosPorCuarto|:"+ex);
		}
		return mapa;
	}
 	
 	public HashMap<String,String> mapExiste(String dormitorioId) {
 		HashMap<String,String> mapa		= new HashMap<String,String>();
 		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
 		try{
 			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, CODIGO_PERSONAL AS VALOR FROM INT_ALUMNO WHERE DORMITORIO_ID = "+dormitorioId;
 			
 			lista = enocJdbc.query(comando, new aca.MapaMapper());	
 			
 			for(aca.Mapa objeto : lista) {
 				mapa.put(objeto.getLlave(), objeto.getValor());
 			}
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.spring.IntAlumnoDao|mapExiste|:"+ex);
 		}
 		return mapa;
 	}
 	
 	public HashMap<String,String> mapaTieneAlumnos() {
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();
		HashMap<String,String> mapa = new HashMap<String,String>();		
		try{
			String comando = "SELECT CUARTO_ID||DORMITORIO_ID AS LLAVE, CODIGO_PERSONAL AS VALOR FROM ENOC.INT_ALUMNO WHERE ESTADO = 'A'";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa map: lista){
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.IntAlumnoDao|mapaTieneAlumnos|:"+ex);
		}
		
		return mapa;
	}
 	
 	public HashMap<String,IntAlumno> mapaInternos(String dormitorioId) {
		List<IntAlumno> lista		= new ArrayList<IntAlumno>();
		HashMap<String,IntAlumno> mapa = new HashMap<String,IntAlumno>();		
		try{
			String comando = "SELECT DORMITORIO_ID,CUARTO_ID,CODIGO_PERSONAL,FECHA_INICIO,FECHA_FINAL,ESTADO,ORDEN,FECHA_INICIO,FECHA_FINAL FROM ENOC.INT_ALUMNO WHERE DORMITORIO_ID = ?";
			lista = enocJdbc.query(comando, new IntAlumnoMapper(), dormitorioId);
			for(IntAlumno objeto: lista){
				mapa.put(objeto.getCodigoPersonal(), objeto);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.IntAlumnoDao|mapaInternos|:"+ex);
		}
		
		return mapa;
	}
 	
	public HashMap<String, IntAlumno> mapaIntAlumno(){
		List<IntAlumno> lista				= new ArrayList<IntAlumno>();
		HashMap<String, IntAlumno> mapa 	= new HashMap<String, IntAlumno>();
		try {
			String comando = "SELECT DORMITORIO_ID, CUARTO_ID, CODIGO_PERSONAL, FECHA_INICIO, FECHA_FINAL,"
					+ " ESTADO, ORDEN"
					+ " FROM ENOC.INT_ALUMNO"
					+ " WHERE CODIGO_PERSONAL IN"
					+ " (SELECT CODIGO_PERSONAL FROM ENOC.INSCRITOS)";
			lista = enocJdbc.query(comando,new IntAlumnoMapper());
			for(IntAlumno objeto : lista){				
				mapa.put(objeto.getCodigoPersonal(), objeto);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.spring.IntDormitorioDao|mapaIntDormitorio|:"+ex);
		}		
		return mapa;
	}
}