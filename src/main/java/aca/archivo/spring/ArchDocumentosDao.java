//Clase  para la tabla ARCH_DOCUMENTOS
package aca.archivo.spring;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ArchDocumentosDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( ArchDocumentos doc ){
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.ARCH_DOCUMENTOS(IDDOCUMENTO, " + 
					"DESCRIPCION, IMAGEN, ORDEN, MOSTRAR) VALUES(TO_NUMBER(?,'999'),?,?, TO_NUMBER(?,'999.9'),?)";
			Object[] parametros = new Object[] {doc.getIdDocumento(),doc.getDescripcion(),doc.getImagen(),doc.getOrden(),doc.getMostrar()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchDocumentosDao|insertReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean updateReg( ArchDocumentos doc ){
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.ARCH_DOCUMENTOS " + 
					"SET DESCRIPCION = ?, IMAGEN = ?, ORDEN = TO_NUMBER(?, '999.9'), MOSTRAR = ? WHERE IDDOCUMENTO = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {doc.getDescripcion(),doc.getImagen(),doc.getOrden(),doc.getMostrar(),doc.getIdDocumento()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchDocumentosDao|updateReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean deleteReg( String idDocumento ){
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.ARCH_DOCUMENTOS WHERE IDDOCUMENTO = TO_NUMBER(?,'999')"; 
			Object[] parametros = new Object[] {idDocumento};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}

		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchDocumentosDao|deleteReg|:"+ex);
		}
		
		return ok;
	}
	
	public ArchDocumentos mapeaRegId(String IdDocumento){
		
		ArchDocumentos archDoc = new ArchDocumentos();
				
		try{
			String comando = "SELECT IDDOCUMENTO, " +
					"DESCRIPCION, IMAGEN, ORDEN, MOSTRAR " +				
					"FROM ENOC.ARCH_DOCUMENTOS WHERE IDDOCUMENTO = TO_NUMBER(?,'999')"; 
			Object[] parametros = new Object[] {IdDocumento};
			archDoc = enocJdbc.queryForObject(comando, new ArchDocumentosMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchDocumentosDao|mapeaRegId|:"+ex);
		}
		
		return archDoc;
	}
	
	public boolean existeReg( String idDocumento){
		boolean ok 			= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ARCH_DOCUMENTOS WHERE IDDOCUMENTO = TO_NUMBER(?,'999') "; 
			Object[] parametros = new Object[] {idDocumento};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchDocumentosDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoReg(){
		String maximo 			= "1";
		
		try{
			String comando = "SELECT MAX(IDDOCUMENTO)+1 MAXIMO FROM ENOC.ARCH_DOCUMENTOS"; 
			Object[] parametros = new Object[] {};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
 				maximo = enocJdbc.queryForObject(comando, String.class, parametros);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchDocumentosDao|maximoReg|:"+ex);
		}
		
		return maximo;
	}
	
	public String getDescripcion(  String documentoId){
		
		String descripcion		= "-";		
		try{
			String comando = "SELECT COUNT(DESCRIPCION) FROM ENOC.ARCH_DOCUMENTOS WHERE IDDOCUMENTO = TO_NUMBER(?,'999')"; 
			Object[] parametros = new Object[] {documentoId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1){				
				comando = "SELECT DESCRIPCION FROM ENOC.ARCH_DOCUMENTOS WHERE IDDOCUMENTO = TO_NUMBER(?,'999')";
				descripcion = enocJdbc.queryForObject(comando,String.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchDocumentosDao|getDescripcion|:"+ex);
		}
		
		return descripcion;
	} 
	
	public List<ArchDocumentos> getListAll( String orden ){
		
		List<ArchDocumentos> lista 	= new ArrayList<ArchDocumentos>();
		
		try{
			String comando = "SELECT IDDOCUMENTO, DESCRIPCION, IMAGEN, ORDEN, MOSTRAR FROM ENOC.ARCH_DOCUMENTOS "+orden; 
			lista = enocJdbc.query(comando, new ArchDocumentosMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchDocumentosDao|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	public List<ArchDocumentos> getListOne( String matricula, String orden ){
		
		List<ArchDocumentos> lista 	= new ArrayList<ArchDocumentos>();		
		try{
			
			String comando = "SELECT IDDOCUMENTO, DESCRIPCION, IMAGEN, ORDEN, MOSTRAR FROM ENOC.ARCH_DOCUMENTOS WHERE IDDOCUMENTO NOT IN (SELECT IDDOCUMENTO FROM ENOC.ARCH_DOCALUM WHERE MATRICULA = ?) "+orden; 
			lista = enocJdbc.query(comando, new ArchDocumentosMapper(), matricula);		
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchDocumentosDao|getListOne|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String,String> mapaTodos( ){
		
		HashMap<String,String> mapa	= new HashMap<String, String>();
		List<aca.Mapa> lista	    	= new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT IDDOCUMENTO AS LLAVE, DESCRIPCION AS VALOR FROM ENOC.ARCH_DOCUMENTOS";			
			lista = enocJdbc.query(comando,new aca.MapaMapper());
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());					
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchDocumentosDao|mapaTodos|:"+ex);
		}
		
		return mapa;
	}
}