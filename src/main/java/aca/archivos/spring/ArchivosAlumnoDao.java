/**
 * 
 */
package aca.archivos.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ArchivosAlumnoDao {
	
	@Autowired
	@Qualifier("jdbcArchivo")
	private JdbcTemplate archivoJdbc;	
	
	public boolean insertReg(ArchivosAlumno archivo){		
		boolean ok 				= false;
		try{
			String comando = "INSERT INTO PORTAL.ARCHIVOS_ALUMNO(ARCHIVO_ID, FOLIO, CODIGO_PERSONAL, FECHA, NOMBRE, COMENTARIO, TAMANO, STATUS, ARCHIVO, ARCHIVO_NUEVO, ACTIVIDAD_ID, EVALUACION_ID)" +
				" VALUES(?, TO_NUMBER(?, '9999999999'), ?, now(), ?, ?, TO_NUMBER(?, '9999999999'), ?, ?, ?,TO_NUMBER(?, '9999999'), TO_NUMBER(?, '99'))";	
			
			Object[] parametros = new Object[]{
			 archivo.getArchivoId(), archivo.getFolio(), archivo.getCodigoPersonal(), archivo.getNombre(),archivo.getComentario(), archivo.getTamano(),
			 archivo.getStatus(), archivo.getArchivo(), archivo.getArchivoNuevo(), archivo.getActividadId(), archivo.getEvaluacionId()
			};
			if (archivoJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.archivos.spring.ArchivosAlumnoDao|insertReg|:"+ex);			
		}		
		return ok;
	}
	
	public boolean updateStatus(String codigoPersonal, String archivoId, String folio, String status ){
		boolean ok = false;		
		try{
			String comando = "UPDATE PORTAL.ARCHIVOS_ALUMNO"
					+ " SET STATUS = ?"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND ARCHIVO_ID = ?"
					+ " AND FOLIO = TO_NUMBER(?, '99999')";
			Object[] parametros = new Object[]{	status, codigoPersonal, archivoId, folio };
			if (archivoJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.archivos.spring.ArchivosAlumnoDao|updateStatus|:"+ex);		
		}		
		return ok;
	}
	
	public boolean updateEvaluacion(String codigoPersonal, String archivoId, String folio, String evaluacionId ){
		boolean ok = false;		
		try{
			String comando = "UPDATE PORTAL.ARCHIVOS_ALUMNO"
					+ " SET EVALUACION_ID = TO_NUMBER(?,'99')"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND ARCHIVO_ID = ?"
					+ " AND FOLIO = TO_NUMBER(?, '99999')";
			Object[] parametros = new Object[]{	evaluacionId, codigoPersonal, archivoId, folio };
			if (archivoJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.archivos.spring.ArchivosAlumnoDao|updateEvaluacion|:"+ex);		
		}		
		return ok;
	}
	
	public ArchivosAlumno mapeaRegId( String codigoPersonal, String archivoId, String folio){
		ArchivosAlumno  archivo = new ArchivosAlumno(); 
		try{ 
			String comando ="SELECT COUNT(*) FROM PORTAL.ARCHIVOS_ALUMNO "
				+ " WHERE CODIGO_PERSONAL = ?"
				+ " AND ARCHIVO_ID = ?"
				+ " AND FOLIO = TO_NUMBER(?, '999999')";
			Object[] parametros = new Object[]{	codigoPersonal, archivoId, folio };		
			if (archivoJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				comando = " SELECT ARCHIVO_ID, FOLIO, CODIGO_PERSONAL, FECHA,"
					    + " NOMBRE, COMENTARIO, TAMANO, STATUS, ARCHIVO, ARCHIVO_NUEVO, ACTIVIDAD_ID, EVALUACION_ID"
					    + " FROM PORTAL.ARCHIVOS_ALUMNO"
						+ " WHERE CODIGO_PERSONAL = ?"
						+ " AND ARCHIVO_ID = ?"
						+ " AND FOLIO = TO_NUMBER(?, '999999')";
				archivo = archivoJdbc.queryForObject(comando, new ArchivosAlumnoMapper(), parametros);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivos.spring.ArchivosAlumnoDao|mapeaRegId|:"+ex);
		}
		return archivo;
	}
	
	public boolean deleteReg(String codigoPersonal, String archivoId, String folio ){
		boolean ok = false;		
		try{			
		    String comando ="DELETE FROM PORTAL.ARCHIVOS_ALUMNO"
		    		+ " WHERE CODIGO_PERSONAL = ?"
		    		+ " AND ARCHIVO_ID = ?"
		    		+ " AND FOLIO = TO_NUMBER(?, '9999999999')";
		    Object[] parametros = new Object[]{	codigoPersonal, archivoId, folio };			
		    if (archivoJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivos.spring.ArchivosAlumnoDao|deleteReg(OID)|:"+ex);
		}
		return ok;
	}

	public boolean existeReg(String codigoPersonal, String archivoId, String folio ){
		boolean ok = false;		
		try{			
			String comando ="SELECT COUNT(*) FROM PORTAL.ARCHIVOS_ALUMNO "
					+ " WHERE CODIGO_PERSONAL = ? AND ARCHIVO_ID = ? AND FOLIO = TO_NUMBER(?, '9999999999')";
			Object[] parametros = new Object[]{	codigoPersonal, archivoId, folio };			
			if (archivoJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivos.spring.ArchivosAlumnoDao|existeReg|:"+ex);
		}
		return ok;
	}

	public String folio(String codigoPersonal, String archivoId){
		String folio = "0";		
		try{			
			String comando ="SELECT COALESCE(MAX(FOLIO)+1,1) AS FOLIO FROM PORTAL.ARCHIVOS_ALUMNO "
					+ " WHERE CODIGO_PERSONAL = ? AND ARCHIVO_ID = ?";
			
			Object[] parametros = new Object[]{	codigoPersonal, archivoId };			
			
			folio = archivoJdbc.queryForObject(comando,String.class, parametros);

			}catch(Exception ex){
			System.out.println("Error - aca.archivos.spring.ArchivosAlumnoDao|folio|:"+ex);
		}
		return folio;
	}
	
	public List<ArchivosAlumno> lisTodos( String orden){
		List<ArchivosAlumno> lista	= new ArrayList<ArchivosAlumno>();		
		try{
			String comando ="SELECT ARCHIVO_ID, FOLIO, CODIGO_PERSONAL, TO_CHAR(FECHA, 'DD/MM/YYYY HH24:MI:SS') AS FECHA," +
		    		" NOMBRE, COMENTARIO, TAMANO, STATUS, ARCHIVO, ACTIVIDAD_ID, EVALUACION_ID" +
		    		" FROM PORTAL.ARCHIVOS_ALUMNO "+orden;					
			lista = archivoJdbc.query(comando, new ArchivosAlumnoMapperCorto());	
		}
		catch(Exception e){
			System.out.println("Error - aca.archivos.spring.ArchivosAlumnoDao|lisTodos|:"+e);
		}
		
		return lista;
	}
	
	public List<ArchivosAlumno> lisAlumnoCurso( String cursoCargaId, String codigoPersonal, String orden){	
		
		List<ArchivosAlumno> lista	= new ArrayList<ArchivosAlumno>();		
		try{
			String comando ="SELECT ARCHIVO_ID, FOLIO, CODIGO_PERSONAL, TO_CHAR(FECHA, 'DD/MM/YYYY HH24:MI:SS') AS FECHA," 
		    		+ " NOMBRE, COMENTARIO, TAMANO, STATUS, ARCHIVO, ARCHIVO_NUEVO, ACTIVIDAD_ID, EVALUACION_ID" 
		    		+ " FROM PORTAL.ARCHIVOS_ALUMNO" 
					+ " WHERE SUBSTR(ARCHIVO_ID,1,11) = ?"
					+ " AND CODIGO_PERSONAL = ? "+orden;
			Object[] parametros = new Object[]{	cursoCargaId, codigoPersonal};		
			lista = archivoJdbc.query(comando, new ArchivosAlumnoMapper(), parametros);
		}
		catch(Exception e){
			System.out.println("Error - aca.archivos.spring.ArchivosAlumnoDao|lisAlumnoCurso|:"+e);
		}		
		return lista;
	}
	
	public List<ArchivosAlumno> lisAlumnoEstrategia( String codigoPersonal, String archivoId, String orden){	
		
		List<ArchivosAlumno> lista	= new ArrayList<ArchivosAlumno>();		
		try{
			String comando ="SELECT ARCHIVO_ID, FOLIO, CODIGO_PERSONAL, TO_CHAR(FECHA, 'DD/MM/YYYY HH24:MI:SS') AS FECHA," +
		    		" NOMBRE, COMENTARIO, TAMANO, STATUS, ARCHIVO, ARCHIVO_NUEVO, ACTIVIDAD_ID, EVALUACION_ID" +
		    		" FROM PORTAL.ARCHIVOS_ALUMNO" + 
					" WHERE CODIGO_PERSONAL = ?" +
					" AND ARCHIVO_ID = ? "+orden;
			Object[] parametros = new Object[]{	codigoPersonal, archivoId };		
			lista = archivoJdbc.query(comando, new ArchivosAlumnoMapper(), parametros);			
		}
		catch(Exception e){
			System.out.println("Error - aca.archivos.spring.ArchivosAlumnoDao|lisAlumnoEstrategia|:"+e);
		}
		
		return lista;
	}
	
	public List<ArchivosAlumno> lisPorEstrategia( String archivoId, String orden){
		List<ArchivosAlumno> lista	= new ArrayList<ArchivosAlumno>();		
		try{
			String comando ="SELECT ARCHIVO_ID, FOLIO, CODIGO_PERSONAL, TO_CHAR(FECHA, 'DD/MM/YYYY HH24:MI:SS') AS FECHA," +
		    		" NOMBRE, COMENTARIO, TAMANO, STATUS, ARCHIVO, ARCHIVO_NUEVO, ACTIVIDAD_ID, EVALUACION_ID" +
		    		" FROM PORTAL.ARCHIVOS_ALUMNO" + 
					" WHERE ARCHIVO_ID = ? "+orden;
			Object[] parametros = new Object[]{	archivoId };		
			lista = archivoJdbc.query(comando, new ArchivosAlumnoMapper(), parametros);	
		}
		catch(Exception e){
			System.out.println("Error - aca.archivos.spring.ArchivosAlumnoDao|lisPorEstrategia|:"+e);
		}
		
		return lista;
	}
	
	public List<ArchivosAlumno> lisPorEvaluacion(String evaluacionId, String orden){
		List<ArchivosAlumno> lista	= new ArrayList<ArchivosAlumno>();		
		try{
			String comando ="SELECT ARCHIVO_ID, FOLIO, CODIGO_PERSONAL, TO_CHAR(FECHA, 'DD/MM/YYYY HH24:MI:SS') AS FECHA,"
		    		+ " NOMBRE, COMENTARIO, TAMANO, STATUS, ARCHIVO, ARCHIVO_NUEVO, ACTIVIDAD_ID, EVALUACION_ID"
		    		+ " FROM PORTAL.ARCHIVOS_ALUMNO"
					+ " WHERE EVALUACION_ID = TO_NUMBER(?,'99')"
					+ " AND ACTIVIDAD_ID = 0 "+orden;
			Object[] parametros = new Object[]{ evaluacionId };		
			lista = archivoJdbc.query(comando, new ArchivosAlumnoMapper(), parametros);	
		}
		catch(Exception e){
			System.out.println("Error - aca.archivos.spring.ArchivosAlumnoDao|lisPorEvaluacion|:"+e);
		}
		
		return lista;
	}
	
	public List<ArchivosAlumno> lisPorActividad( String actividadId, String orden){
		List<ArchivosAlumno> lista	= new ArrayList<ArchivosAlumno>();		
		try{
			String comando ="SELECT ARCHIVO_ID, FOLIO, CODIGO_PERSONAL, TO_CHAR(FECHA, 'DD/MM/YYYY HH24:MI:SS') AS FECHA," +
		    		" NOMBRE, COMENTARIO, TAMANO, STATUS, ARCHIVO, ARCHIVO_NUEVO, ACTIVIDAD_ID, EVALUACION_ID" +
		    		" FROM PORTAL.ARCHIVOS_ALUMNO" + 
					" WHERE ACTIVIDAD_ID = TO_NUMBER(?,'9999999') "+orden;
			Object[] parametros = new Object[]{	actividadId };
			lista = archivoJdbc.query(comando, new ArchivosAlumnoMapper(), parametros);	
		}
		catch(Exception e){
			System.out.println("Error - aca.archivos.spring.ArchivosAlumnoDao|lisPorEstrategia|:"+e);
		}
		
		return lista;
	}
	
	public List<ArchivosAlumno> lisPorAlumno( String codigoPersonal, String orden){
		List<ArchivosAlumno> lista = new ArrayList<ArchivosAlumno>();
		try {
			String comando ="SELECT ARCHIVO_ID, FOLIO, CODIGO_PERSONAL, TO_CHAR(FECHA, 'DD/MM/YYYY HH24:MI:SS') AS FECHA," +
		    		" NOMBRE, COMENTARIO, TAMANO, STATUS, ARCHIVO, ARCHIVO_NUEVO, ACTIVIDAD_ID, EVALUACION_ID" +
		    		" FROM PORTAL.ARCHIVOS_ALUMNO" + 
					" WHERE CODIGO_PERSONAL = ? "+orden;
			Object[] parametros = new Object[] { codigoPersonal };
			lista = archivoJdbc.query(comando, new ArchivosAlumnoMapper(), parametros);
		}catch(Exception e){
			System.out.println("Error - aca.archivos.spring.ArchivosAlumnoDao|lisPorAlumno|:"+e);
		}
		return lista;
	}
	
	public HashMap<String, String> mapaArchivosPorEvaluacion(String cursoCargaId) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 		 = new ArrayList<aca.Mapa>();
		try{
			String comando = " SELECT ARCHIVO_ID AS LLAVE, COUNT(*) AS VALOR FROM PORTAL.ARCHIVOS_ALUMNO WHERE SUBSTR(ARCHIVO_ID,1,11) = ? GROUP BY ARCHIVO_ID";
			Object[] parametros = new Object[]{	cursoCargaId };
			lista = archivoJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivos.spring.ArchivosAlumnoDao|mapaArchivosPorEvaluacion|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaArchivosPorAlumno(String cursoCargaId) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 		 = new ArrayList<aca.Mapa>();
		try{
			String comando = " SELECT CODIGO_PERSONAL AS LLAVE, COUNT(*) AS VALOR FROM PORTAL.ARCHIVOS_ALUMNO WHERE SUBSTR(ARCHIVO_ID,1,11) = ? GROUP BY CODIGO_PERSONAL";
			Object[] parametros = new Object[]{	cursoCargaId };
			lista = archivoJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivos.spring.ArchivosAlumnoDao|mapaArchivosPorAlumno|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaArchivosDelAlumno(String codigoPersonal) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 		 = new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT ACTIVIDAD_ID AS LLAVE, STATUS AS VALOR FROM PORTAL.ARCHIVOS_ALUMNO WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[]{	codigoPersonal };
			lista = archivoJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivos.spring.ArchivosAlumnoDao|mapaArchivosDelAlumno|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaArchivosPorMateria(String cursoCargaId) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 		 = new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT ACTIVIDAD_ID||STATUS AS LLAVE, COUNT(STATUS) AS VALOR FROM PORTAL.ARCHIVOS_ALUMNO WHERE ARCHIVO_ID = ? GROUP BY ACTIVIDAD_ID, STATUS";
			Object[] parametros = new Object[]{	cursoCargaId };
			lista = archivoJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivos.spring.ArchivosAlumnoDao|mapaArchivosPorMateria|:"+ex);
		}
		return mapa;
	}
}