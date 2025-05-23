/*
 * Created on 13/02/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package aca.ssoc.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SsocDocumentosDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
    public boolean guardaDocumento( SsocDocumentos documento) {
		String comando = "";
    	boolean ok = false;
    	try{
    	    comando="INSERT INTO ENOC.SSOC_DOCUMENTOS(DOCUMENTO_ID,DOCUMENTO_NOMBRE,ORDEN, OBLIGATORIO, ACCESO)"
    	    		+ " VALUES(TO_NUMBER(?,'99'),?,TO_NUMBER(?,'99'),?, ?)";
    	    Object[] parametros = new Object[]{documento.getDocumentoId(), documento.getDocumentoNombre(),
			documento.getOrden(), documento.getObligatorio(), documento.getAcceso()};
    	    if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
    	}catch(Exception ex){
    		System.out.println("Error - aca.ssoc.spring.SsocDocumentosDao|guardaDocumento|:"+ex);
    		ok=false;
    	}
    	return ok;
    }   

    public boolean modificaDocumento( SsocDocumentos documento) {
        String comando = "";
        boolean ok = false;
        
    	try{
    	    comando	= " UPDATE ENOC.SSOC_DOCUMENTOS"
    	    		+ " SET DOCUMENTO_NOMBRE = ?, ORDEN = TO_NUMBER(?,'99'),OBLIGATORIO = ?, ACCESO = ? "
    	    		+ " WHERE DOCUMENTO_ID = TO_NUMBER(?,'99')";
    	    Object[] parametros = new Object[]{ documento.getDocumentoNombre(),
    				documento.getOrden(), documento.getObligatorio(), documento.getAcceso(), documento.getDocumentoId()};    	    
    	    if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
    	}catch(Exception ex){
    		System.out.println("Error - aca.ssoc.spring.SsocDocumentosDao|modificaDocumento|:"+ex);
    	}
    	return ok;
    }

    public boolean eliminaDocumento( String documentoId) {
        String comando = "";
        boolean ok = false;
        
    	try{
    	    comando="DELETE FROM ENOC.SSOC_DOCUMENTOS WHERE DOCUMENTO_ID = TO_NUMBER(?,'99')";
    	    Object[] parametros = new Object[]{documentoId};
    	    if (enocJdbc.update(comando,parametros)==1){
    	    	ok = true;
    		}
    	}catch(Exception ex){
    		System.out.println("Error - aca.ssoc.spring.SsocDocumentosDao|eliminaDocumento|:"+ex);
    	}
    	return ok;
    }
    
    public SsocDocumentos mapeRegId(String documentoId) {
        String comando = "";
        SsocDocumentos objeto = new SsocDocumentos();        
    	try{
    	    comando="SELECT DOCUMENTO_ID, DOCUMENTO_NOMBRE, ORDEN, OBLIGATORIO, ACCESO FROM ENOC.SSOC_DOCUMENTOS "+ 
    	    		"WHERE DOCUMENTO_ID = TO_NUMBER(?,'99')";
    	    Object[] parametros = new Object[]{documentoId};
    	    objeto = enocJdbc.queryForObject(comando, new SsocDocumentosMapper(), parametros);
    	    
    	}catch(Exception ex){
    		System.out.println("Error - aca.ssoc.spring.SsocDocumentosDao|mapeRegId|:"+ex);
    	}
    	return objeto;
    }
    
    public boolean existeReg( String documentoId) {
		String comando = "";
    	boolean ok	   = false;    	
    	try{
    	    comando=" SELECT COUNT(*) FROM ENOC.SSOC_DOCUMENTOS" + 
    	    		" WHERE DOCUMENTO_ID = TO_NUMBER(?,'99')";
    	    Object[] parametros = new Object[] {documentoId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}
    	}catch(Exception ex){
    		System.out.println("Error - aca.ssoc.spring.SsocDocumentosDao|existeReg|:"+ex);
    	}
    	return ok;
    }
    
    public String maximoReg( ){		
		int maximo				= 1;		
		try{
			String comando = "SELECT COALESCE(MAX(DOCUMENTO_ID)+1,1) AS MAXIMO FROM ENOC.SSOC_DOCUMENTOS";			
			maximo = enocJdbc.queryForObject(comando,Integer.class);			
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.spring.SsocDocumentosDao|maximoReg|:"+ex);
		}
		
		return String.valueOf(maximo);
	}
    
    public int totalPorTipo( String tipo ){		
		int maximo				= 0;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.SSOC_DOCUMENTOS WHERE OBLIGATORIO = ?";			
			maximo = enocJdbc.queryForObject(comando,Integer.class, tipo);			
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.spring.SsocDocumentosDao|totalPorTipo|:"+ex);
		}		
		return maximo;
	}
    
    public List<SsocDocumentos> lisDocumentos(String orden) {
        List<SsocDocumentos> lista = new ArrayList<SsocDocumentos>();
        String comando = "";        
    	try{
    	    comando="SELECT DOCUMENTO_ID, DOCUMENTO_NOMBRE, ORDEN, OBLIGATORIO, ACCESO FROM ENOC.SSOC_DOCUMENTOS "+orden;
    	    lista = enocJdbc.query(comando, new SsocDocumentosMapper());    	    
    	}catch(Exception ex){
    		System.out.println("Error - aca.ssoc.spring.SsocDocumentosDao|lisDocumentos|:"+ex);
    	}
    	return lista;
    }
    
    public HashMap<String,SsocDocumentos> mapaTodos() {	
		HashMap<String,SsocDocumentos> mapa = new HashMap<String,SsocDocumentos>();
		List<SsocDocumentos> lista		= new ArrayList<SsocDocumentos>();		
		try{
			String comando = "SELECT DOCUMENTO_ID, DOCUMENTO_NOMBRE, ORDEN, OBLIGATORIO, ACCESO FROM ENOC.SSOC_DOCUMENTOS";
			lista = enocJdbc.query(comando, new SsocDocumentosMapper());
			for (SsocDocumentos documento : lista ) {
				mapa.put(documento.getDocumentoId(), documento);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.spring.SsocDocumentosDao|mapaDocumentos|:"+ex);
			ex.printStackTrace();
		}		
		return mapa;		
	}
    
    public HashMap<String,String> mapaDocumentos() {	
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT DOCUMENTO_ID AS LLAVE, DOCUMENTO_NOMBRE AS VALOR FROM ENOC.SSOC_DOCUMENTOS";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.spring.SsocDocumentosDao|mapaDocumentos|:"+ex);
			ex.printStackTrace();
		}		
		return mapa;		
	}
    
    public HashMap<String,String> mapaDocUso() {	
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT DOCUMENTO_ID AS LLAVE, COUNT(CODIGO_PERSONAL) AS VALOR FROM ENOC.SSOC_DOCALUM GROUP BY DOCUMENTO_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.spring.SsocDocumentosDao|mapaDocUso|:"+ex);
			ex.printStackTrace();
		}		
		return mapa;		
	}
	
}