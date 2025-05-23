package aca.carga.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CargaGrupoAsistenciaDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( CargaGrupoAsistencia asistencia ){
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.CARGA_GRUPO_ASISTENCIA (CURSO_CARGA_ID,FOLIO,CODIGO_PERSONAL,CURSO_ID,ESTADO) "+
				"VALUES( ?, TO_NUMBER(?,'999'), ?, ? ,?) ";
			
			Object[] parametros = new Object[] {asistencia.getCargaGrupoId(),asistencia.getFolio(),asistencia.getCodigoPersonal(),asistencia.getCursoId(),asistencia.getEstado()};
 			
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoAsistenciaDao|insertReg|:"+ex);
		}
		return ok;
	}	
	
	public boolean updateReg( CargaGrupoAsistencia asistencia ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.CARGA_GRUPO_ASISTENCIA "+ 
				" SET CURSO_ID = ?, ESTADO = ?"+
				" WHERE CURSO_CARGA_ID = ?" +
				" AND FOLIO = TO_NUMBER(?,'999')" +
				" AND CODIGO_PERSONAL = ?"; 
			
			
			
			Object[] parametros = new Object[] {asistencia.getCursoId(),asistencia.getEstado(),asistencia.getCargaGrupoId(),asistencia.getFolio(),asistencia.getCodigoPersonal()}; 			 			
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoAsistenciaDao|updateReg|:"+ex);		 
		}		
		return ok;
	}	
	
	public boolean deleteReg( String cursoCargaGrupoId, String folio, String codigoPersonal ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CARGA_GRUPO_PROGRAMACION"
					+ " WHERE CURSO_CARGA_ID = ?"
					+ " AND FOLIO = TO_NUMBER(?,'999')"
					+ " AND CODIGO_PERSONAL = ?";
			
			Object[] parametros = new Object[] {cursoCargaGrupoId,folio,codigoPersonal};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoAsistenciaDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public boolean deleteRegistros( String cursoCargaId, String folio) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CARGA_GRUPO_ASISTENCIA"
					+ " WHERE CURSO_CARGA_ID = ?"
					+ " AND FOLIO = TO_NUMBER(?,'999')";
			
			Object[] parametros = new Object[] {cursoCargaId,folio};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoAsistenciaDao|deleteRegistros|:"+ex);			
		}
		return ok;
	}
	
	public CargaGrupoAsistencia mapeaRegId(  String cursoCargaGrupoId, String folio, String codigoPersonal) {
		
		CargaGrupoAsistencia asistencia = new CargaGrupoAsistencia();
		
		
		try{
			String comando = "SELECT CURSO_CARGA_ID, FOLIO, CODIGO_PERSONAL, CURSO_ID, ESTADO FROM ENOC.CARGA_GRUPO_ASISTENCIA" +
					" WHERE CURSO_CARGA_ID = ? AND FOLIO = TO_NUMBER(?,'999') AND CODIGO_PERSONAL = ?"; 
			
			Object[] parametros = new Object[] {codigoPersonal, folio};
			asistencia = enocJdbc.queryForObject(comando, new CargaGrupoAsistenciaMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoAsistenciaDao|mapeaRegId|:"+ex);
		}
		return asistencia;
	}
	
	
	public boolean existeReg( String cursoCargaGrupoId, String folio, String codigoPersonal) {
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA_GRUPO_ASISTENCIA WHERE CURSO_CARGA_ID = ? AND FOLIO = TO_NUMBER(?,'999') AND CODIGO_PERSONAL = ?"; 
			
			Object[] parametros = new Object[] {cursoCargaGrupoId,folio,codigoPersonal};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoAsistenciaDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public String maximoReg( String cursoCargaGrupoId) {
		String maximo 			= "1";		
		
		try{
			String comando = "SELECT COALESCE(MAX(FOLIO)+1,1) AS MAXIMO FROM ENOC.CARGA_GRUPO_ASISTENCIA WHERE CURSO_CARGA_ID = ?"; 
		
			Object[] parametros = new Object[] {cursoCargaGrupoId};
 			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
 				maximo = enocJdbc.queryForObject(comando,String.class,parametros);
			}		
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoAsistenciaDao|maximoReg|:"+ex);
		}
		return maximo;
	}
	
	public String numRegAsistencia( String cursoCargaId, String folio) {
		String total 			= "0";		
		
		try{
			String comando = "SELECT COALESCE(COUNT(CURSO_CARGA_ID),0) AS TOTAL"
					+ " FROM ENOC.CARGA_GRUPO_ASISTENCIA"
					+ " WHERE CURSO_CARGA_ID = ?"
					+ " AND FOLIO = TO_NUMBER(?,'999')"; 
			
			Object[] parametros = new Object[] {cursoCargaId,folio};			
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				total = enocJdbc.queryForObject(comando, String.class, parametros);
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoAsistenciaDao|numRegAsistencia|:"+ex);
		}
		return total;
	}

	public List<CargaGrupoAsistencia> getListAll( String orden ) {
		List<CargaGrupoAsistencia> lisAsistencia	= new ArrayList<CargaGrupoAsistencia>();
		String comando			= "";
		
		try{
			comando = "SELECT CURSO_CARGA_ID, FOLIO, CODIGO_PERSONAL, CURSO_ID, ESTADO"+
					" FROM ENOC.CARGA_GRUPO_ASISTENCIA "+ orden; 
		
			Object[] parametros = new Object[] {orden};	
			lisAsistencia = enocJdbc.query(comando, new CargaGrupoAsistenciaMapper(), parametros);	
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoAsistenciaDao|getListAll|:"+ex);
		}
		return lisAsistencia;
	}
	
	/* Map para verificar la asistencia */
	public HashMap<String, String> mapAsistencia( String cursoCargaId, String folio) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		try{
			String comando = " SELECT CODIGO_PERSONAL AS LLAVE, ESTADO AS VALOR FROM ENOC.CARGA_GRUPO_ASISTENCIA"
					+ " WHERE CURSO_CARGA_ID = ? AND FOLIO = TO_NUMBER(?,'999')";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), cursoCargaId, folio);
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoAsistenciaDao|mapAsistencia|:"+ex);
		}
		return mapa;
	}
	
	/* Map para verificar la asistencia */
	public HashMap<String, String> mapAsistenciaClase( String cursoCargaId) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();			
		try{
			String comando = " SELECT FOLIO||ESTADO AS LLAVE, COUNT(ESTADO) AS VALOR FROM ENOC.CARGA_GRUPO_ASISTENCIA"
					+ " WHERE CURSO_CARGA_ID = ? GROUP BY FOLIO||ESTADO";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), cursoCargaId);
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoAsistenciaDao|mapAsistenciaClase|:"+ex);
		}
		return mapa;
	}
	
	/* Map para el total de asistencias por alumnos*/
	public HashMap<String, String> mapAsistenciaTotal( String cursoCargaId) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();	
		try{
			String comando = " SELECT CODIGO_PERSONAL||ESTADO AS LLAVE, COUNT(CODIGO_PERSONAL) AS VALOR FROM ENOC.CARGA_GRUPO_ASISTENCIA"
					+ " WHERE CURSO_CARGA_ID = ? GROUP BY CODIGO_PERSONAL||ESTADO";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), cursoCargaId);
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoAsistenciaDao|mapAsistenciaTotal|:"+ex);
		}
		return mapa;
	}
	
	public List<CargaGrupoAsistencia> getListReporte( String codigoPersonal, String cursoCargaId, String orden) {
		List<CargaGrupoAsistencia> asistencia = new ArrayList<CargaGrupoAsistencia>();
		try{
			String comando = "SELECT CURSO_CARGA_ID, FOLIO, CODIGO_PERSONAL, CURSO_ID, ESTADO " +
					"FROM ENOC.CARGA_GRUPO_ASISTENCIA " +
					"WHERE CURSO_CARGA_ID = ? AND CODIGO_PERSONAL=? "+orden;		
			Object[] parametros = new Object[] {cursoCargaId, codigoPersonal};	
			asistencia = enocJdbc.query(comando, new CargaGrupoAsistenciaMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoAsistenciaDao|getListMateria|:"+ex);
		}		
		return asistencia;
	}

}
