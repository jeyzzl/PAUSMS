package aca.exp.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ExpDocumentoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( ExpDocumento doc ){
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.EXP_DOCUMENTO(DOCUMENTO_ID, DOCUMENTO_NOMBRE, ORDEN, CORTO) VALUES(TO_NUMBER(?,'99'),?,TO_NUMBER(?,'99'), ?)";
			Object[] parametros = new Object[] {doc.getDocumentoId(),doc.getDocumentoNombre(),doc.getOrden(), doc.getCorto()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.exp.spring.ExpDocumentoDao|insertReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean updateReg( ExpDocumento doc ){
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.EXP_DOCUMENTO SET DOCUMENTO_NOMBRE = ?, ORDEN = TO_NUMBER(?, '99'), CORTO = ? "
					+ " WHERE DOCUMENTO_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {doc.getDocumentoNombre(),doc.getOrden(), doc.getCorto(), doc.getDocumentoId()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.exp.spring.ExpDocumentoDao|updateReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean deleteReg( String idDocumento ){
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.EXP_DOCUMENTO WHERE DOCUMENTO_ID = TO_NUMBER(?,'99')"; 
			Object[] parametros = new Object[] {idDocumento};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.exp.spring.ExpDocumentoDao|deleteReg|:"+ex);
		}		
		return ok;
	}
	
	public ExpDocumento mapeaRegId(String documentoId){		
		ExpDocumento archDoc = new ExpDocumento();				
		try{
			String comando = "SELECT DOCUMENTO_ID, DOCUMENTO_NOMBRE, ORDEN, CORTO FROM ENOC.EXP_DOCUMENTO"
					+ " WHERE DOCUMENTO_ID = TO_NUMBER(?,'99')"; 
			Object[] parametros = new Object[] {documentoId};
			archDoc = enocJdbc.queryForObject(comando, new ExpDocumentoMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.exp.spring.ExpDocumentoDao|mapeaRegId|:"+ex);
		}		
		return archDoc;
	}
	
	public boolean existeReg( String documentoId){
		boolean ok 			= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.EXP_DOCUMENTO WHERE DOCUMENTO_ID = TO_NUMBER(?,'99') "; 
			Object[] parametros = new Object[] {documentoId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.exp.spring.ExpDocumentoDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoReg(){
		String maximo 			= "1";		
		try{
			String comando = "SELECT COALESCE(MAX(DOCUMENTO_ID)+1,1) MAXIMO FROM ENOC.EXP_DOCUMENTO"; 
			Object[] parametros = new Object[] {};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
 				maximo = enocJdbc.queryForObject(comando, String.class, parametros);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.exp.spring.ExpDocumentoDao|maximoReg|:"+ex);
		}
		
		return maximo;
	}
	
	public String getDocumentoNombre(  String documentoId){
		
		String descripcion		= "-";		
		try{
			String comando = "SELECT COUNT(DOCUMENTO_NOMBRE) FROM ENOC.EXP_DOCUMENTO WHERE DOCUMENTO_ID = TO_NUMBER(?,'99')"; 
			Object[] parametros = new Object[] {documentoId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1){				
				comando = "SELECT DOCUMENTO_NOMBRE FROM ENOC.EXP_DOCUMENTO WHERE DOCUMENTO_ID = TO_NUMBER(?,'99')";
				descripcion = enocJdbc.queryForObject(comando,String.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.exp.spring.ExpDocumentoDao|getDocumentoNombre|:"+ex);
		}
		
		return descripcion;
	} 
	
	public List<ExpDocumento> lisTodos( String orden ){		
		List<ExpDocumento> lista 	= new ArrayList<ExpDocumento>();		
		try{
			String comando = "SELECT DOCUMENTO_ID, DOCUMENTO_NOMBRE, ORDEN, CORTO FROM ENOC.EXP_DOCUMENTO "+orden; 
			lista = enocJdbc.query(comando, new ExpDocumentoMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.exp.spring.ExpDocumentoDao|getListAll|:"+ex);
		}		
		return lista;
	}
	
	public List<ExpDocumento> lisPorEmpleado(String codigoEmpleado, String orden ){		
		List<ExpDocumento> lista 	= new ArrayList<ExpDocumento>();		
		try{
			String comando = "SELECT DOCUMENTO_ID, DOCUMENTO_NOMBRE, ORDEN, CORTO FROM ENOC.EXP_DOCUMENTO"
					+ " WHERE DOCUMENTO_ID IN (SELECT DISTINCT(DOCUMENTO_ID) FROM EXP_EMPLEADO WHERE CODIGO_PERSONAL = ?) "+orden; 
			lista = enocJdbc.query(comando, new ExpDocumentoMapper(), codigoEmpleado);			
		}catch(Exception ex){
			System.out.println("Error - aca.exp.spring.ExpDocumentoDao|lisPorEmpleado|:"+ex);
		}		
		return lista;
	}
	
	public HashMap<String,String> mapaTodos( ){
		
		HashMap<String,String> mapa	= new HashMap<String, String>();
		List<aca.Mapa> lista	    	= new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT DOCUMENTO_ID AS LLAVE, DOCUMENTO_NOMBRE AS VALOR FROM ENOC.EXP_DOCUMENTO";
			lista = enocJdbc.query(comando,new aca.MapaMapper());
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());					
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.exp.spring.ExpDocumentoDao|mapaTodos|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String,String> mapaCorto( ){		
		HashMap<String,String> mapa	= new HashMap<String, String>();
		List<aca.Mapa> lista	    	= new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT DOCUMENTO_ID AS LLAVE, CORTO AS VALOR FROM ENOC.EXP_DOCUMENTO";
			lista = enocJdbc.query(comando,new aca.MapaMapper());
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());					
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.exp.spring.ExpDocumentoDao|mapaCorto|:"+ex);
		}		
		return mapa;
	}
}