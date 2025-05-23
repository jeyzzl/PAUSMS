package aca.admision.spring;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AdmDocumentoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(AdmDocumento admDocumento) {
		boolean ok = false;		
		try{
			String comando = "INSERT INTO SALOMON.ADM_DOCUMENTO(DOCUMENTO_ID, DOCUMENTO_NOMBRE, TIPO, COMENTARIO, ORIGINAL, ORDEN, FORMATO_ID, CORTO)"
				+ " VALUES( TO_NUMBER(?,'999'), ?, ?, ?, ?, TO_NUMBER(?,'99'), TO_NUMBER(?, '99'), ?)";			
			Object[] parametros = new Object[] {
				admDocumento.getDocumentoId(),admDocumento.getDocumentoNombre(),admDocumento.getTipo(),admDocumento.getComentario(),admDocumento.getOriginal(),
				admDocumento.getOrden(),admDocumento.getFormatoId(),admDocumento.getCorto()
			};			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}				
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmDocumento|insertReg|:"+ex);			
		}

		return ok;
	}

	public boolean updateReg(AdmDocumento admDocumento) {
		boolean ok = false;		
		try{
			String comando = "UPDATE SALOMON.ADM_DOCUMENTO"
					+ " SET DOCUMENTO_NOMBRE = ?,"
					+ " TIPO = ?,"
					+ " COMENTARIO = ?,"
					+ " ORIGINAL = ?,"
					+ " ORDEN = TO_NUMBER(?,'99'),"
					+ " FORMATO_ID = TO_NUMBER(?, '99'),"
					+ " CORTO = ?"
					+ " WHERE DOCUMENTO_ID = TO_NUMBER(?,'999')";			
			Object[] parametros = new Object[] {
				admDocumento.getDocumentoNombre(),admDocumento.getTipo(),admDocumento.getComentario(),admDocumento.getOriginal(),
				admDocumento.getOrden(),admDocumento.getFormatoId(),admDocumento.getCorto(),admDocumento.getDocumentoId()
			};			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.admision.AdmDocumento|updateReg|:"+ex);		
		}		
		return ok;
	}
	
	public boolean deleteReg(String documentoId) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM SALOMON.ADM_DOCUMENTO WHERE DOCUMENTO_ID = TO_NUMBER(?,'99')";			
			Object[] parametros = new Object[]{documentoId};
			if (enocJdbc.update(comando,parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.AdmDocumento|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public AdmDocumento mapeaRegId(String documentoId){
		AdmDocumento objeto = new AdmDocumento();		 
		try{
			String comando = "SELECT DOCUMENTO_ID, DOCUMENTO_NOMBRE, TIPO, COMENTARIO, ORIGINAL, ORDEN, FORMATO_ID, CORTO"
					+ " FROM SALOMON.ADM_DOCUMENTO WHERE DOCUMENTO_ID = TO_NUMBER(?,'99')";			
			Object[] parametros = new Object[] {documentoId};
			objeto = enocJdbc.queryForObject(comando, new AdmDocumentoMapper(),parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.AdmDocumento|mapeaRegId|:"+ex);			
		}		
		return objeto;
	}
	
	public boolean existeReg(String documentoId){
		boolean ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM SALOMON.ADM_DOCUMENTO WHERE DOCUMENTO_ID = TO_NUMBER(?,'99')";			
			Object[] parametros = new Object[] {documentoId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.admision.AdmDocumento|existeReg|:"+ex);
		}		
		return ok;
	}
	
	public String maximoReg(){
		String maximo = "1";		
		try{
			String comando = "SELECT COALESCE(MAX(DOCUMENTO_ID)+1,1) FROM SALOMON.ADM_DOCUMENTO";			
			maximo = enocJdbc.queryForObject(comando,String.class);			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.AdmDocumento|maximoReg|:"+ex);
		}
		
		return maximo;
	}
	
	public String getNombreDocumento(String documentoId ){
		String nombre = "-";		
		try{
			String comando = "SELECT COUNT(*) FROM SALOMON.ADM_DOCUMENTO WHERE DOCUMENTO_ID = ?";
			Object[] parametros = new Object[] {documentoId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1) {
				comando = "SELECT DOCUMENTO_NOMBRE FROM SALOMON.ADM_DOCUMENTO WHERE DOCUMENTO_ID = ?";
				nombre = enocJdbc.queryForObject(comando,String.class,parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.AdmDocumento|getNombreDocumento|:"+ex);
		}
		
		return nombre;
	}
	
	public String getTipo(String documentoId ) throws SQLException{
		String tipo	= "9";		
		try{
			String comando = "SELECT TIPO FROM SALOMON.ADM_DOCUMENTO WHERE DOCUMENTO_ID = ?";
			Object[] parametros = new Object[] {documentoId};
			tipo = enocJdbc.queryForObject(comando,String.class,parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.AdmDocumento|getTipo|:"+ex);
		}		
		return tipo;
	}
	
	public List<AdmDocumento> getAll(String orden) {
		List<AdmDocumento> lista	= new ArrayList<AdmDocumento>();		
		try{
			String comando = "SELECT DOCUMENTO_ID, DOCUMENTO_NOMBRE, TIPO, COMENTARIO, ORIGINAL, ORDEN, FORMATO_ID, CORTO FROM SALOMON.ADM_DOCUMENTO " + orden;			
			lista = enocJdbc.query(comando, new AdmDocumentoMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmDocumentoDao|getAll|:"+ex);
		}		
		return lista;
	}
	
	public HashMap<String, AdmDocumento> mapaDocumentos() {
		
		HashMap<String, AdmDocumento> mapa = new HashMap<String, AdmDocumento>();
		List<AdmDocumento> lista	= new ArrayList<AdmDocumento>();
		try{
			String comando = "SELECT DOCUMENTO_ID, DOCUMENTO_NOMBRE, TIPO, COMENTARIO, ORIGINAL, ORDEN, FORMATO_ID, CORTO FROM SALOMON.ADM_DOCUMENTO";			
			lista = enocJdbc.query(comando, new AdmDocumentoMapper());
			for (AdmDocumento map : lista) {
				mapa.put(map.getDocumentoId(), map);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.admision.spring.AdmRequisitoDao|mapaDocumentos:"+ex);
		}
		
		return mapa;
	}

}
