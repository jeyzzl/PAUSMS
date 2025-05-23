package aca.vigilancia.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class VigDocumentoDao{	
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(VigDocumento objeto){
		boolean ok 				= false;		
		try{
			String comando = "INSERT INTO ENOC.VIG_DOCUMENTO (AUTO_ID, DOCUMENTO_ID, HOJA, ARCHIVO, NOMBRE ) "
					+ " VALUES( TO_NUMBER(?, '9999'), TO_NUMBER(?, '99'), TO_NUMBER(?, '99'), ?, ?)";			
			Object[] parametros = new Object[] {
				objeto.getAutoId(), objeto.getDocumentoId(), objeto.getHoja(), objeto.getArchivo(), objeto.getNombre()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.spring.VigDocumentoDao|insertReg|:"+ex);
		}		
		return ok;
	}	
	
	public boolean updateReg(VigDocumento objeto){
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.VIG_DOCUMENTO SET ARCHIVO = ?, NOMBRE = ? WHERE AUTO_ID = TO_NUMBER(?, '9999') AND DOCUMENTO_ID = TO_NUMBER(?, '99') AND HOJA = TO_NUMBER(?, '99')";
			Object[] parametros = new Object[]{
				objeto.getArchivo(), objeto.getNombre(), objeto.getAutoId(), objeto.getDocumentoId(), objeto.getHoja()
			};				
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.spring.VigDocumentoDao|updateReg|:"+ex);
		}
		
		return ok;
	}
	 	
	public boolean deleteReg(String autoId, String documentoId, String hoja){
		boolean ok = false;
		try{
			String comando = "DELETE FROM ENOC.VIG_DOCUMENTO WHERE AUTO_ID = TO_NUMBER(?, '9999') AND DOCUMENTO_ID = TO_NUMBER(?, '99') AND HOJA = TO_NUMBER(?, '99')";
			Object[] parametros = new Object[] {autoId,documentoId, hoja};
			if (enocJdbc.update(comando, parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.spring.VigDocumentoDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public VigDocumento mapeaRegId(String autoId, String documentoId, String hoja) {
		VigDocumento objeto = new VigDocumento();
		try{
	 		String comando = "SELECT AUTO_ID, DOCUMENTO_ID, HOJA, ARCHIVO, NOMBRE FROM ENOC.VIG_DOCUMENTO WHERE AUTO_ID = TO_NUMBER(?, '9999') AND DOCUMENTO_ID = TO_NUMBER(?,'99') AND HOJA = TO_NUMBER(?, '99')"; 
	 		Object[] parametros = new Object[] {autoId, documentoId, hoja};
	 		objeto = enocJdbc.queryForObject(comando, new VigDocumentoMapper(), parametros);	 		
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.spring.VigDocumentoDao|mapeaRegId|:"+ex);
		}
		
		return objeto;
	}
	
	public boolean existeReg(String autoId, String documentoId, String hoja){
		boolean ok = false;		
		try{
			String comando = "SELECT COUNT(AUTO_ID) FROM ENOC.VIG_DOCUMENTO WHERE AUTO_ID = TO_NUMBER(?, '9999') AND DOCUMENTO_ID = TO_NUMBER(?,'99') AND HOJA = TO_NUMBER(?, '99')";
			Object[] parametros = new Object[] {autoId, documentoId, hoja};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.spring.VigDocumentoDao|existeReg|:"+ex);
		}		
		return ok;
	}
	
	public boolean existeReg(String autoId, String documentoId ){
		boolean ok = false;		
		try{
			String comando = "SELECT COUNT(AUTO_ID) FROM ENOC.VIG_DOCUMENTO WHERE AUTO_ID = TO_NUMBER(?, '9999') AND DOCUMENTO_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {autoId, documentoId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.spring.VigDocumentoDao|existeReg|:"+ex);
		}		
		return ok;
	}
	
	public List<VigDocumento> lisTodos(String orden ){
		
		List<VigDocumento> lis = new ArrayList<VigDocumento>();	
		try{
			String comando = "SELECT AUTO_ID, DOCUMENTO_ID, HOJA, NOMBRE FROM ENOC.VIG_DOCUMENTO "+orden;
			lis = enocJdbc.query(comando, new VigDocumentoMapperCorto());			
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.spring.VigDocumentoDao|lisTodos|:"+ex);
		}		
		return lis;
	}
	
	public HashMap<String,String> mapaConImagen(){
		HashMap<String,String> mapa = new  HashMap<String,String>();
		List<aca.Mapa> lis = new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT AUTO_ID||'-'||DOCUMENTO_ID||'-'||HOJA AS LLAVE, NOMBRE AS VALOR FROM ENOC.VIG_DOCUMENTO WHERE ARCHIVO IS NOT NULL";
			lis = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa objeto : lis) {
				mapa.put(objeto.getLlave(), objeto.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.spring.VigDocumentoDao|mapaConImagen|:"+ex);
		}	
		return mapa;
	}
	
	public HashMap<String,String> mapaTotal(){
		HashMap<String,String> mapa = new  HashMap<String,String>();
		List<aca.Mapa> lis = new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT AUTO_ID AS LLAVE, COUNT(DOCUMENTO_ID) AS VALOR FROM ENOC.VIG_DOCUMENTO WHERE ARCHIVO IS NOT NULL GROUP BY AUTO_ID";
			lis = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa doc : lis) {
				mapa.put(doc.getLlave(), doc.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.spring.VigDocumentoDao|mapaTotal|:"+ex);
		}	
		return mapa;
	}

}