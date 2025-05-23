package aca.carga.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CargaPracticaDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(CargaPractica practica ) {
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.CARGA_PRACTICA (CURSO_CARGA_ID, FOLIO, FECHA_INI, FECHA_FIN, ESTADO)"
					+ " VALUES(?, TO_NUMBER(?,'99'), TO_DATE(?,'DD/MM/YYYY'), TO_DATE(?,'DD/MM/YYYY'), ?)";
			Object[] parametros = new Object[] {practica.getCursoCargaId(), practica.getFolio(), practica.getFechaIni(), practica.getFechaFin(), practica.getEstado() };			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaPracticaDao|insertReg|:"+ex);
		}		
		return ok;
	}
	
	public boolean updateReg( CargaPractica practica ){
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.CARGA_PRACTICA SET FECHA_INI = TO_DATE(?,'DD/MM/YYYY'), FECHA_FIN = TO_DATE(?,'DD/MM/YYYY'), ESTADO = ?"
				+ " WHERE CURSO_CARGA_ID = ?"
				+ " AND FOLIO = TO_NUMBER(?,'99')";				
			Object[] parametros = new Object[] {practica.getFechaIni(), practica.getFechaFin(), practica.getEstado(), practica.getCursoCargaId(), practica.getFolio() };
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaPracticaDao|updateReg|:"+ex); 
		}
		return ok;
	}
	
	public boolean deleteReg( String cursoCargaId, String folio ) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.CARGA_PRACTICA WHERE CURSO_CARGA_ID = ? AND FOLIO = TO_NUMBER(?,'99')";			
			Object[] parametros = new Object[] {cursoCargaId, folio};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaPracticaDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public boolean existeReg(String cursoCargaId, String folio ) {
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA_PRACTICA WHERE CURSO_CARGA_ID = ? AND FOLIO = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {cursoCargaId, folio};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaPracticaDao|existeReg|:"+ex);			
		}		
		return ok;
	}
	
	public String maximoReg(String cursoCargaId) {
		int maximo = 0;		
		try{
			String comando = "SELECT COALESCE(MAX(FOLIO)+1,1) FROM ENOC.CARGA_PRACTICA WHERE CURSO_CARGA_ID = ?";
			Object[] parametros = new Object[] {cursoCargaId};
			maximo = enocJdbc.queryForObject(comando,Integer.class,parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaPracticaDao|existeReg|:"+ex);			
		}		
		return String.valueOf(maximo);
	}

	public String fechaIni(String codigoAlumno, String cargaId, String bloqueId) {
		String fecha = "";
		
		try{
			String comando = "SELECT MIN(TO_CHAR(FECHA_INI,'DD/MM/YYYY')) AS FECHA_INI FROM CARGA_PRACTICA WHERE CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ALUMNO_CURSO WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ? AND BLOQUE_ID = ?)";
			Object[] parametros = new Object[] {codigoAlumno, cargaId, bloqueId};
			
			fecha = enocJdbc.queryForObject(comando,String.class,parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaPracticaDao|fechaIni|:"+ex);			
		}		
		return fecha;
	}
	
	public String fechaFin(String codigoAlumno, String cargaId, String bloqueId ) {
		String fecha = "";
		
		try{
			String comando = "SELECT MAX(TO_CHAR(FECHA_FIN,'DD/MM/YYYY')) AS FECHA_FIN FROM CARGA_PRACTICA WHERE CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ALUMNO_CURSO WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ? AND BLOQUE_ID = ?)";
			Object[] parametros = new Object[] {codigoAlumno, cargaId, bloqueId};
			
			fecha = enocJdbc.queryForObject(comando,String.class,parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaPracticaDao|fechaFin|:"+ex);			
		}		
		return fecha;
	}
	
	public List<CargaPractica> lisPorGrupo( String cursoCargaId, String orden ) {
		List<CargaPractica> lista		= new ArrayList<CargaPractica>();
		try{
			String comando = "SELECT CURSO_CARGA_ID, FOLIO, TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN,'DD/MM/YYYY') AS FECHA_FIN, ESTADO FROM ENOC.CARGA_PRACTICA WHERE CURSO_CARGA_ID = ? "+ orden;
			Object[] parametros = new Object[] {cursoCargaId};
			lista = enocJdbc.query(comando, new CargaPracticaMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaPracticaDao|lisPorGrupo|:"+ex);
		}
		return lista;
	}
	
	public List<CargaPractica> lisPorCarga( String cargaId, String orden ) {
		List<CargaPractica> lista		= new ArrayList<CargaPractica>();
		try{
			String comando = "SELECT CURSO_CARGA_ID, FOLIO, TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN,'DD/MM/YYYY') AS FECHA_FIN, ESTADO"
					+ " FROM ENOC.CARGA_PRACTICA WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ? "+ orden;
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new CargaPracticaMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaPracticaDao|lisPorGrupo|:"+ex);
		}
		return lista;
	}	
	
	public HashMap<String,CargaPractica> mapaPorCarga( String cargaId ){
		HashMap<String,CargaPractica> mapa		= new HashMap<String,CargaPractica>();
		List<CargaPractica> lista 			= new ArrayList<CargaPractica>();				
		try{
			String comando = "SELECT CURSO_CARGA_ID, FOLIO, TO_CHAR(FECHA_INI,'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN,'DD/MM/YYYY') AS FECHA_FIN, ESTADO FROM ENOC.CARGA_PRACTICA WHERE ENOC.CARGA_DEL_GRUPO(CURSO_CARGA_ID) = ?";
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new CargaPracticaMapper(), parametros);
			for(CargaPractica objeto : lista){		
				mapa.put(objeto.getCursoCargaId()+objeto.getFolio(), objeto);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaPracticaDao|mapaPorCarga|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,String> mapaPorCargaPracticas(String cargaId, String planId){
		HashMap<String,String> mapa	= new HashMap<String,String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();				
		try{
			String comando = "SELECT CURSO_CARGA_ID AS LLAVE, COUNT(CURSO_CARGA_ID) AS VALOR FROM ENOC.CARGA_PRACTICA"
					+ " WHERE CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.CARGA_ACADEMICA WHERE CARGA_ID = '20211B' AND PLAN_ID = 'ISCO2018')"
					+ " GROUP BY CURSO_CARGA_ID";
			
			lista = enocJdbc.query(comando,new aca.MapaMapper());
			
			for(aca.Mapa objeto : lista){		
				mapa.put(objeto.getLlave(), objeto.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaPracticaDao|mapaPorCargaPracticas|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,String> mapaFechasPracticas(String cargaId, String facultadId){
		HashMap<String,String> mapa	= new HashMap<String,String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();				
		try{
			String comando = "SELECT CURSO_CARGA_ID AS LLAVE, (TO_CHAR(FECHA_INI,'DD/MM/YYYY') ||' -'|| TO_CHAR(FECHA_FIN,'DD/MM/YYYY')) AS VALOR"
					+ " FROM ENOC.CARGA_PRACTICA WHERE CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.ALUMNO_CURSO WHERE CARGA_ID = ? AND ENOC.FACULTAD(CARRERA_ID) = ?"
					+ " AND CURSO_ID IN (SELECT CURSO_ID FROM ENOC.MAPA_CURSO WHERE LABORATORIO = 'S'))";
			
			lista = enocJdbc.query(comando,new aca.MapaMapper(),new Object[] {cargaId,facultadId});
			
			for(aca.Mapa objeto : lista){		
				mapa.put(objeto.getLlave(), objeto.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaPracticaDao|mapaFechasPracticas|:"+ex);
		}
		return mapa;
	}
}
