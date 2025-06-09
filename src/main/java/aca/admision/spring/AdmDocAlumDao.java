package aca.admision.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AdmDocAlumDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(AdmDocAlum admDocAlum){
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO SALOMON.ADM_DOCALUM(FOLIO, DOCUMENTO_ID, ESTADO, USUARIO, COMENTARIO, CARTA) "+
				"VALUES( TO_NUMBER(?,'99999999'), TO_NUMBER(?,'99'), ?, ?, ?, 'S')";
			
			Object[] parametros = new Object[] {
				admDocAlum.getFolio(),admDocAlum.getDocumentoId(),admDocAlum.getEstado(),admDocAlum.getUsuario(),admDocAlum.getComentario()
			};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmDocAlumDao|insertReg|:"+ex);
		}

		return ok;
	}
			
	public boolean updateReg(AdmDocAlum admDocAlum) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE SALOMON.ADM_DOCALUM SET ESTADO = ?, USUARIO = ?, COMENTARIO = ?, CARTA = ?"
					+ " WHERE FOLIO = TO_NUMBER(?,'99999999') AND DOCUMENTO_ID = TO_NUMBER(?,'99')";
			
			Object[] parametros = new Object[] {
				admDocAlum.getEstado(),admDocAlum.getUsuario(),admDocAlum.getComentario(),admDocAlum.getCarta(),admDocAlum.getFolio(),admDocAlum.getDocumentoId(),
			};			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmDocAlumDao|updateReg|:"+ex);	
		}

		return ok;
	}	
	
	public boolean updateAutoriza(String estado, String usuario, String folio ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE SALOMON.ADM_DOCALUM "+
				"SET ESTADO = ?, USUARIO = ? "+
				"WHERE FOLIO = TO_NUMBER(?,'99999999')";
			
			Object[] parametros = new Object[]{estado,usuario,folio};
			if (enocJdbc.update(comando,parametros) >= 1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmDocAlumDao|updateAutoriza|:"+ex);
		}

		return ok;
	}
	
	public boolean updateComentario(String folio, String documentoId, String comentario) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE SALOMON.ADM_DOCALUM" + 
					" SET COMENTARIO = ? WHERE FOLIO = TO_NUMBER(?,'9999999') AND DOCUMENTO_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {comentario, folio, documentoId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - adm.admision.spring.AdmDocAlumDao|updateComentario|:"+ex);
		}

		return ok;
	}
	
	public boolean deleteReg(String folio, String documentoId) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM SALOMON.ADM_DOCALUM "+
				"WHERE FOLIO = TO_NUMBER(?,'99999999') AND DOCUMENTO_ID = TO_NUMBER(?,'99')";
			
			Object[] parametros = new Object[]{folio,documentoId};
			if (enocJdbc.update(comando,parametros) >= 1){
				ok = true;
			}

		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmDocAlumDao|deleteReg|:"+ex);
		}

		return ok;
	}
	
	public boolean deleteRegFolio(String folio) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM SALOMON.ADM_DOCALUM "+
					"WHERE FOLIO = TO_NUMBER(?,'99999999')";
			
			Object[] parametros = new Object[]{folio};
			if (enocJdbc.update(comando,parametros) >= 1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmDocAlumDao|deleteRegFolio|:"+ex);
		}
		
		return ok;
	}
	
	public AdmDocAlum mapeaRegId(String folio, String documentoId ){
		AdmDocAlum objeto = new AdmDocAlum();
		 
		try{
			String comando = "SELECT FOLIO, DOCUMENTO_ID, ESTADO, USUARIO, COMENTARIO, CARTA "+
				"FROM SALOMON.ADM_DOCALUM WHERE FOLIO = TO_NUMBER(?,'99999999') AND DOCUMENTO_ID = TO_NUMBER(?, '99')";
			
			Object[] parametros = new Object[] {folio,documentoId};
			objeto = enocJdbc.queryForObject(comando, new AdmDocAlumMapper(),parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmDocAlum|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		return objeto;
	}
	
	public boolean existeReg(String folio, String documentoId ) {
		boolean ok 	= false;		
		try{
			String comando = "SELECT COUNT(FOLIO) FROM SALOMON.ADM_DOCALUM WHERE FOLIO = TO_NUMBER(?,'99999999') AND DOCUMENTO_ID = TO_NUMBER(?,'99')";			
			Object[] parametros = new Object[] {folio,documentoId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmDocAlumDao|existeReg|:"+ex);
		}
		
		return ok;
	}	
	
	public boolean existeRegFolio(String folio) {
		boolean ok 	= false;
		
		try{
			String comando = "SELECT COUNT(FOLIO) FROM SALOMON.ADM_DOCALUM WHERE FOLIO = TO_NUMBER(?,'99999999')";
			
			Object[] parametros = new Object[] {folio};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmDocAlumDao|existeRegFolio|:"+ex);
		}
		
		return ok;
	}
	
	public boolean tieneDocs(String folio) {
		boolean ok 	= false;
		
		try{
			String comando = "SELECT COUNT(FOLIO) FROM SALOMON.ADM_DOCALUM WHERE FOLIO = TO_NUMBER(?,'99999999')";
			
			Object[] parametros = new Object[] {folio};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmDocAlumDao|tieneDocs|:"+ex);
		}
		
		return ok;
	}
	
	public int numDoctosExtras(String folio, String carreraId) {
		int total = 0;		
		try{
			String comando = "SELECT COUNT(FOLIO) FROM SALOMON.ADM_DOCALUM"
					+ " WHERE FOLIO = TO_NUMBER(?,'99999999')"
					+ " AND DOCUMENTO_ID NOT IN (SELECT DOCUMENTO_ID FROM SALOMON.ADM_REQUISITO WHERE CARRERA_ID = ?)";			
			Object[] parametros = new Object[] {folio, carreraId};			
			total = enocJdbc.queryForObject(comando,Integer.class,parametros);						
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmDocAlumDao|numDoctosExtras|:"+ex);
		}		
		return total;
	}
	
	public int numDocs(String folio) {
		int numero = 0;		
		try{
			String comando = "SELECT COUNT(FOLIO) AS FOLIO FROM SALOMON.ADM_DOCALUM WHERE FOLIO = TO_NUMBER(?,'99999999') AND ESTADO = 'E'";			
			Object[] parametros = new Object[] {folio};
			numero = enocJdbc.queryForObject(comando,Integer.class,parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmDocAlumDao|numDocs|:"+ex);
		}		
		return numero;
	}
	
	public int cantidadDocs(String folio) {
		int numero = 0;		
		try{
			String comando = "SELECT COUNT(FOLIO) AS FOLIO FROM SALOMON.ADM_DOCALUM WHERE FOLIO = TO_NUMBER(?,'99999999')";			
			Object[] parametros = new Object[] {folio};
			numero = enocJdbc.queryForObject(comando,Integer.class,parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmDocAlumDao|cantidadDocs|:"+ex);
		}		
		return numero;
	}
	
	public List<AdmDocAlum> lisTodos( String orden ){
		
		List<AdmDocAlum> lista	= new ArrayList<AdmDocAlum>();	
		try{
			String comando = "SELECT FOLIO, DOCUMENTO_ID, ESTADO, USUARIO, COMENTARIO, CARTA FROM SALOMON.ADM_DOCALUM "+ orden;
			lista = enocJdbc.query(comando, new AdmDocAlumMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmDocAlumDao|lisTodos|:"+ex);
		}		
		return lista;
	}
	
	public List<AdmDocAlum> lisPorFolio( String folio, String orden ){
		
		List<AdmDocAlum> lista	= new ArrayList<AdmDocAlum>();	
		try{
			String comando = "SELECT FOLIO, DOCUMENTO_ID, ESTADO, USUARIO, COMENTARIO, CARTA FROM SALOMON.ADM_DOCALUM WHERE FOLIO = TO_NUMBER(?,'99999999') "+ orden;
			Object[] parametros = new Object[] {folio};
			lista = enocJdbc.query(comando, new AdmDocAlumMapper(),parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmDocAlumDao|lisPorFolio|:"+ex);
		}		
		return lista;
	}
	
	public List<AdmDocAlum> lisDocumentosExtras( String folio, String carreraId, String orden ){
		
		List<AdmDocAlum> lista	= new ArrayList<AdmDocAlum>();	
		try{
			String comando = "SELECT FOLIO, DOCUMENTO_ID, ESTADO, USUARIO, COMENTARIO, CARTA FROM SALOMON.ADM_DOCALUM"
					+ " WHERE FOLIO = TO_NUMBER(?,'99999999')"
					+ " AND DOCUMENTO_ID NOT IN (SELECT DOCUMENTO_ID FROM SALOMON.ADM_REQUISITO WHERE CARRERA_ID = ?)"+ orden;
			Object[] parametros = new Object[] {folio, carreraId};
			lista = enocJdbc.query(comando, new AdmDocAlumMapper(),parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmDocAlumDao|lisDocumentosExtras|:"+ex);
		}		
		return lista;
	}
	
	public HashMap<String, AdmDocAlum> mapaDocumentosAlumno(String folio) {
		
		HashMap<String, AdmDocAlum> mapa = new HashMap<String, AdmDocAlum>();
		List<AdmDocAlum> lista	= new ArrayList<AdmDocAlum>();
		try{
			String comando = "SELECT FOLIO, DOCUMENTO_ID, ESTADO, USUARIO, COMENTARIO, CARTA FROM SALOMON.ADM_DOCALUM WHERE FOLIO = TO_NUMBER(?,'99999999')";	
			Object[] parametros = new Object[] {folio};
			lista = enocJdbc.query(comando, new AdmDocAlumMapper(),parametros);
			for (AdmDocAlum map : lista) {
				mapa.put(map.getDocumentoId(), map);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmDocAlumDao|mapaDocumentosAlumno:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapaDocumentosPendientes() {
		
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT FOLIO AS LLAVE, COUNT(FOLIO) AS VALOR FROM SALOMON.ADM_DOCALUM WHERE ESTADO = 'E' GROUP BY FOLIO";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmDocAlumDao|mapaDocumentosAlumno:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String,String> mapaDocumentosEstado() {
		
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT FOLIO AS LLAVE, COUNT(DOCUMENTO_ID) AS VALOR FROM SALOMON.ADM_DOCALUM WHERE ESTADO = 'E' GROUP BY FOLIO";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmDocAlumDao|mapaDocumentosEstado:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String,String> mapaDocumentosRevisado() {
	
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT FOLIO AS LLAVE, COUNT(DOCUMENTO_ID) AS VALOR FROM SALOMON.ADM_DOCALUM GROUP BY FOLIO";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmDocAlumDao|mapaDocumentosRevisado:"+ex);
		}		
		return mapa;
	}

	public HashMap<String,String> mapaDocPorFolio() {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT FOLIO AS LLAVE, COUNT(FOLIO) AS VALOR FROM SALOMON.ADM_DOCALUM WHERE ESTADO = 'E' GROUP BY FOLIO ORDER BY FOLIO";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmDocAlumDao|mapaDocPorFolio:"+ex);
		}		
		return mapa;
	}

}
