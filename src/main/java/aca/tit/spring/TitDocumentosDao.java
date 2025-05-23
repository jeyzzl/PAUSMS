package aca.tit.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class TitDocumentosDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public TitDocumentos mapeaRegId(String documentoId) {
		TitDocumentos objeto = new TitDocumentos();		 
		try{
			String comando = "SELECT DOCUMENTO_ID, DOCUMENTO_NOMBRE, ORDEN, TIPO FROM ENOC.TIT_DOCUMENTOS WHERE DOCUMENTO_ID = TO_NUMBER(?,'99')";			
			Object[] parametros = new Object[] {documentoId};
			objeto = enocJdbc.queryForObject(comando, new TitDocumentosMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitDocumentosDao|mapeaRegId|:"+ex);		
		}
		return objeto;		
	}
	
	public boolean existeReg(String nombreDocumento,  String digestion, String longitud) {
		boolean 		ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.TIT_DOCUMENTOS WHERE FOLIO = TO_NUMBER(?,'9999999') "; 
			
			Object[] parametros = new Object[] {nombreDocumento,digestion,longitud};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitDocumentosDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public List<TitDocumentos> lisTodos(String orden) {
		List<TitDocumentos> lista		= new ArrayList<TitDocumentos>();
		String comando		= "";		
		try{
			comando = "SELECT DOCUMENTO_ID, DOCUMENTO_NOMBRE, ORDEN, TIPO FROM ENOC.TIT_DOCUMENTOS "+orden;
			lista = enocJdbc.query(comando, new TitDocumentosMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.saum.spring.TitDocumentosDao|lisTodos|:"+ex);
		}
		return lista;
	}
	
	public String maximoReg() {
		String maximo 	= "1";		
		try{
			String comando = "SELECT COALESCE(MAX(FOLIO)+1, 1) AS MAXIMO FROM ENOC.TIT_DOCUMENTOS";			
			Object[] parametros = new Object[] {};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
 				maximo = enocJdbc.queryForObject(comando, String.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitDocumentosDao|maximoReg|:"+ex);
		}		
		return maximo;
	}
	
	public HashMap<String, TitDocumentos> mapaTodos( ) {
		List<TitDocumentos> lista			= new ArrayList<TitDocumentos>();
		HashMap<String,TitDocumentos> mapa	= new HashMap<String,TitDocumentos>();
		try{
			String comando	= "SELECT DOCUMENTO_ID, DOCUMENTO_NOMBRE, ORDEN, TIPO FROM ENOC.TIT_DOCUMENTOS FROM ENOC.TIT_ANTECEDENTE";
			lista = enocJdbc.query(comando, new TitDocumentosMapper());			
			for (TitDocumentos ant : lista){
				mapa.put(ant.getDocumentoId(), ant);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.tit.spring.TitDocumentosDao|mapaTodos|:"+ex);
		}
		return mapa;
	}
}
