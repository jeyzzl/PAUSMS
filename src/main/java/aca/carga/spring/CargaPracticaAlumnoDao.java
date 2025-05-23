package aca.carga.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CargaPracticaAlumnoDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(CargaPracticaAlumno practica ) {
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.CARGA_PRACTICA_ALUMNO (CODIGO_PERSONAL, FOLIO, CARGA_ID, BLOQUE_ID, FECHA_INI, FECHA_FIN, COMENTARIO, ESTADO)"
					+ " VALUES(?, TO_NUMBER(?,'99'), ?, TO_NUMBER(?,'99'), TO_DATE(?,'DD/MM/YYYY'), TO_DATE(?,'DD/MM/YYYY'), ?, ?)";
			Object[] parametros = new Object[] { 
				practica.getCodigoPersonal(), practica.getFolio(), practica.getCargaId(), practica.getBloqueId(), practica.getFechaIni(), practica.getFechaFin(), practica.getComentario(), practica.getEstado()
			};			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaPracticaAlumoDao|insertReg|:"+ex);
		}		
		return ok;
	}
	
	public boolean updateReg( CargaPracticaAlumno practica ) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.CARGA_PRACTICA_ALUMNO SET FECHA_INI = TO_DATE(?,'DD/MM/YYYY'), FECHA_FIN = TO_DATE(?,'DD/MM/YYYY'), COMENTARIO = ?, ESTADO = ?"
				+ " WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {
				practica.getFechaIni(), practica.getFechaFin(), practica.getComentario(), practica.getEstado(), practica.getCodigoPersonal(), practica.getFolio() 
			};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaPracticaAlumoDao|updateReg|:"+ex); 
		}
		return ok;
	}
	
	public boolean deleteReg( String codigoPersonal, String folio ) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.CARGA_PRACTICA_ALUMNO WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?,'99')";			
			Object[] parametros = new Object[] {codigoPersonal, folio};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaPracticaAlumoDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public boolean deleteTodoReg( String codigoPersonal, String cargaId, String bloqueId) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.CARGA_PRACTICA_ALUMNO WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ? AND BLOQUE_ID = ?";			
			Object[] parametros = new Object[] {codigoPersonal, cargaId, bloqueId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaPracticaAlumoDao|deleteTodoReg|:"+ex);			
		}
		return ok;
	}
	
	public boolean existeReg(String codigoPersonal, String folio ) {
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA_PRACTICA_ALUMNO WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {codigoPersonal, folio};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaPracticaAlumoDao|existeReg|:"+ex);			
		}		
		return ok;
	}
	
	public boolean existeLibre(String codigoPersonal, String cargaId, String estado) {
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA_PRACTICA_ALUMNO WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ? AND ESTADO = ?";
			Object[] parametros = new Object[] {codigoPersonal, cargaId, estado};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaPracticaAlumoDao|existeReg|:"+ex);			
		}		
		return ok;
	}

	public String folio(String codigoPersonal, String cargaId, String bloqueId) {
		String folio = "";
		
		try{
			String comando = "SELECT COALESCE(MAX(FOLIO)+1,1) FROM ENOC.CARGA_PRACTICA_ALUMNO WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ? AND BLOQUE_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {codigoPersonal, cargaId, bloqueId};
			
			folio = enocJdbc.queryForObject(comando,String.class,parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaPracticaAlumoDao|folio|:"+ex);			
		}		
		return folio;
	}
	
	public String maxFolio(String codigoPersonal) {
		String folio = "";		
		try{
			String comando = "SELECT COALESCE(MAX(FOLIO)+1,1) FROM ENOC.CARGA_PRACTICA_ALUMNO WHERE CODIGO_PERSONAL = ?";			
			folio = enocJdbc.queryForObject(comando,String.class,codigoPersonal);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaPracticaAlumoDao|folio|:"+ex);			
		}		
		return folio;
	}
	
	public List<CargaPracticaAlumno> lisPorAlumno( String codigoPersonal, String orden ) {
		List<CargaPracticaAlumno> lista		= new ArrayList<CargaPracticaAlumno>();
		try{
			String comando = "SELECT CODIGO_PERSONAL, FOLIO, CARGA_ID, BLOQUE_ID, TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN,'DD/MM/YYYY') AS FECHA_FIN, COMENTARIO, ESTADO "
					+ " FROM ENOC.CARGA_PRACTICA_ALUMNO WHERE CODIGO_PERSONAL = ?"+ orden;
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.query(comando, new CargaPracticaAlumnoMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaPracticaAlumoDao|lisPorAlumno|:"+ex);
		}
		return lista;
	}
	
	public List<CargaPracticaAlumno> lisPorAlumno( String codigoPersonal, String cargaId, String orden ) {
		List<CargaPracticaAlumno> lista		= new ArrayList<CargaPracticaAlumno>();
		try{
			String comando = "SELECT CODIGO_PERSONAL, FOLIO, CARGA_ID, BLOQUE_ID, TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN,'DD/MM/YYYY') AS FECHA_FIN, COMENTARIO, ESTADO "
					+ " FROM ENOC.CARGA_PRACTICA_ALUMNO WHERE CODIGO_PERSONAL = ?  AND CARGA_ID = ? "+ orden;
			Object[] parametros = new Object[] {codigoPersonal, cargaId};
			lista = enocJdbc.query(comando, new CargaPracticaAlumnoMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaPracticaAlumoDao|lisPorAlumno|:"+ex);
		}
		return lista;
	}	
	
	public List<CargaPracticaAlumno> lisPorAlumnoyEstado( String codigoPersonal, String cargaId, String estado, String orden ) {
		List<CargaPracticaAlumno> lista		= new ArrayList<CargaPracticaAlumno>();
		try{
			String comando = "SELECT CODIGO_PERSONAL, FOLIO, CARGA_ID, BLOQUE_ID, TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN,'DD/MM/YYYY') AS FECHA_FIN, COMENTARIO, ESTADO "
					+ " FROM ENOC.CARGA_PRACTICA_ALUMNO WHERE CODIGO_PERSONAL = ?  AND CARGA_ID = ? AND ESTADO = ?"+ orden;
			Object[] parametros = new Object[] {codigoPersonal, cargaId, estado};
			lista = enocJdbc.query(comando, new CargaPracticaAlumnoMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaPracticaAlumoDao|lisPorAlumno|:"+ex);
		}
		return lista;
	}
	
	public List<CargaPracticaAlumno> lisAlumnoFechas() {
		List<CargaPracticaAlumno> lista		= new ArrayList<CargaPracticaAlumno>();
		try{
			String comando = "SELECT CODIGO_PERSONAL, FOLIO, CARGA_ID, BLOQUE_ID, TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN,'DD/MM/YYYY') AS FECHA_FIN, COMENTARIO, ESTADO FROM ENOC.CARGA_PRACTICA_ALUMNO";
			lista = enocJdbc.query(comando, new CargaPracticaAlumnoMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaPracticaAlumoDao|lisAlumnoFechas|:"+ex);
		}
		return lista;
	}
	
	public List<CargaPracticaAlumno> lisPorCarga( String cargaId, String orden ) {
		List<CargaPracticaAlumno> lista		= new ArrayList<CargaPracticaAlumno>();
		try{
			String comando = "SELECT CODIGO_PERSONAL, FOLIO, CARGA_ID, BLOQUE_ID, TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN,'DD/MM/YYYY') AS FECHA_FIN, COMENTARIO, ESTADO "
					+ " FROM ENOC.CARGA_PRACTICA_ALUMNO WHERE CARGA_ID = ? "+ orden;
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new CargaPracticaAlumnoMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaPracticaAlumoDao|lisPorCarga|:"+ex);
		}
		return lista;
	}
	
	public HashMap<String,CargaPracticaAlumno> mapaPorAlumno( String codigoPersonal ){
		HashMap<String,CargaPracticaAlumno> mapa	= new HashMap<String,CargaPracticaAlumno>();
		List<CargaPracticaAlumno> lista 			= new ArrayList<CargaPracticaAlumno>();				
		try{
			String comando = "SELECT CODIGO_PERSONAL, FOLIO, CARGA_ID, BLOQUE_ID, TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN,'DD/MM/YYYY') AS FECHA_FIN, COMENTARIO, ESTADO FROM ENOC.CARGA_PRACTICA_ALUMNO WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.query(comando, new CargaPracticaAlumnoMapper(),parametros);
			for(CargaPracticaAlumno objeto : lista){		
				mapa.put(objeto.getCodigoPersonal()+objeto.getCargaId()+objeto.getBloqueId(), objeto);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaPracticaAlumoDao|mapaPorAlumno|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,List<aca.Mapa>> mapaFechasPorAlumno(String cargaId){
		HashMap<String,List<aca.Mapa>> mapa	= new HashMap<String,List<aca.Mapa>>();
		
		List<CargaPracticaAlumno> lista = new ArrayList<CargaPracticaAlumno>();		
		List<aca.Mapa> listaFechas 			= new ArrayList<aca.Mapa>();		
		
		String matricula = "";
		aca.Mapa fechas = new aca.Mapa();
		
		try{
			String comando = "SELECT COUNT(CARGA_ID) FROM ENOC.CARGA_PRACTICA_ALUMNO WHERE CARGA_ID = ?";
			Object[] parametros = new Object[] {cargaId};
			
			if(enocJdbc.queryForObject(comando, Integer.class,parametros) >= 1) {
				comando = "SELECT CODIGO_PERSONAL, FOLIO, CARGA_ID, BLOQUE_ID, TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN,'DD/MM/YYYY') AS FECHA_FIN, COMENTARIO, ESTADO"
						+ " FROM ENOC.CARGA_PRACTICA_ALUMNO WHERE CARGA_ID = ? ORDER BY FECHA_INI";
				lista = enocJdbc.query(comando, new CargaPracticaAlumnoMapper(),parametros);
				
				matricula = lista.get(0).getCodigoPersonal();
				
				for(CargaPracticaAlumno objeto : lista){	
					if(matricula.equals(objeto.getCodigoPersonal())) {
						fechas.setLlave(objeto.getFechaIni());
						fechas.setValor(objeto.getFechaFin());
						
						listaFechas.add(fechas);
					}
					if(!matricula.equals(objeto.getCodigoPersonal())) {
						matricula = objeto.getCodigoPersonal();
						
						fechas.setLlave(objeto.getFechaIni());
						fechas.setValor(objeto.getFechaFin());
						
						listaFechas.add(fechas);
					}
					if(!mapa.containsKey(objeto.getCodigoPersonal())) {
						mapa.put(matricula, listaFechas);
					}
				}
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaPracticaAlumoDao|mapaFechasPorAlumno|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,CargaPracticaAlumno> mapaPracticaAlumnoEnCarga(String cargaId){
		HashMap<String,CargaPracticaAlumno> mapa	= new HashMap<String,CargaPracticaAlumno>();
		List<CargaPracticaAlumno> lista 			= new ArrayList<CargaPracticaAlumno>();				
		try{
			String comando = "SELECT CODIGO_PERSONAL, FOLIO, CARGA_ID, BLOQUE_ID, TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN,'DD/MM/YYYY') AS FECHA_FIN, COMENTARIO, ESTADO FROM ENOC.CARGA_PRACTICA_ALUMNO WHERE CARGA_ID = ? ORDER BY ESTADO";
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new CargaPracticaAlumnoMapper(),parametros);
			for(CargaPracticaAlumno objeto : lista){		
				mapa.put(objeto.getCodigoPersonal(), objeto);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaPracticaAlumoDao|mapaPracticaAlumnoEnCarga|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,String> mapaPorAlumnoyEstado( String codigoPersonal, String estado ){
		HashMap<String,String> mapa		= new HashMap<String,String>();
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT CARGA_ID||BLOQUE_ID AS LLAVE, COUNT(ESTADO) AS VALOR FROM ENOC.CARGA_PRACTICA_ALUMNO WHERE CODIGO_PERSONAL = ? AND ESTADO = ? GROUP BY CARGA_ID||BLOQUE_ID";
			Object[] parametros = new Object[] {codigoPersonal, estado};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa map : lista){		
				mapa.put(map.getLlave(), map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaPracticaAlumoDao|mapaPorAlumno|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,String> mapaLibresPorCarga( String cargaId, String estado ){
		HashMap<String,String> mapa		= new HashMap<String,String>();
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT CODIGO_PERSONAL||BLOQUE_ID AS LLAVE, COMENTARIO AS VALOR FROM ENOC.CARGA_PRACTICA_ALUMNO WHERE CARGA_ID = ? AND ESTADO = ?";
			Object[] parametros = new Object[] {cargaId, estado};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa map : lista){		
				mapa.put(map.getLlave(), map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaPracticaAlumoDao|mapaLibresPorCarga|:"+ex);
		}
		return mapa;
	}
}
