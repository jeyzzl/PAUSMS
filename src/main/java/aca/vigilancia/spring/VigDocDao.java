package aca.vigilancia.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class VigDocDao{	
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(VigDoc objeto){
		boolean ok 				= false;		
		try{
			String comando = "INSERT INTO ENOC.VIG_DOC (DOCUMENTO_ID, DOCUMENTO_NOMBRE, CORTO) VALUES( TO_NUMBER(?, '99'), ?, ?)";			
			Object[] parametros = new Object[]{				objeto.getDocumentoId(),objeto.getDocumentoNombre() 			
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}				
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.spring.VigDocDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg(VigDoc objeto){ 		
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.VIG_DOC SET DOCUMENTO_NOMBRE = ?, CORTO = ? WHERE DOCUMENTO_ID = TO_NUMBER(?, '99')";			
			Object[] parametros = new Object[] { objeto.getDocumentoNombre(), objeto.getCorto(), objeto.getDocumentoId() };				
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.spring.VigDocDao|updateReg|:"+ex);
		}
		
		return ok;
	}
	 	
	public boolean deleteReg(String autoId){
		boolean ok = false;
		try{
			String comando = "DELETE FROM ENOC.VIG_DOC WHERE DOCUMENTO_ID = TO_NUMBER(?, '99')";
			if (enocJdbc.update(comando,autoId)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.spring.VigDocDao|deleteReg|:"+ex);
		}
		return ok;
	}
	
	public VigDoc mapeaRegId(String autoId) {
		VigDoc objeto = new VigDoc();
		try{
	 		String comando = "SELECT DOCUMENTO_ID, DOCUMENTO_NOMBRE, CORTO FROM ENOC.VIG_DOC WHERE DOCUMENTO_ID = TO_NUMBER(?,'99')";
	 		objeto = enocJdbc.queryForObject(comando, new VigDocMapper(), autoId);	 		
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.spring.VigDocDao|mapeaRegId|:"+ex);
		}		
		return objeto;
	}
	
	public boolean existeReg(String autoId){
		boolean ok = false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.VIG_DOC WHERE DOCUMENTO_ID = TO_NUMBER(?,'99')";
			if (enocJdbc.queryForObject(comando,Integer.class, autoId)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.spring.VigDocDao|existeReg|:"+ex);
		}		
		return ok;
	}
	
	public String getDocumentoNombre(String documentoId){
		String nombre = "-";		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.VIG_DOC WHERE DOCUMENTO_ID = TO_NUMBER(?,'99')";
			if (enocJdbc.queryForObject(comando,Integer.class, documentoId)>=1){
				comando = "SELECT DOCUMENTO_NOMBRE FROM ENOC.VIG_DOC WHERE DOCUMENTO_ID = TO_NUMBER(?,'99')";
				nombre 	= enocJdbc.queryForObject(comando,String.class, documentoId);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.spring.VigDocDao|getDocumentoNombre|:"+ex);
		}		
		return nombre;
	}
	
	public String maximoReg(){
		String maximo = "1";		
		try{
			String comando = "SELECT COALESCE(MAX(DOCUMENTO_ID)+1,1) FROM ENOC.VIG_DOC";
			maximo = enocJdbc.queryForObject(comando,String.class);			
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.spring.VigDocDao|maximoReg|:"+ex);
		}		
		return maximo;
	}
	
	public List<VigDoc> lisTodos(String orden ){
		
		List<VigDoc> lis = new ArrayList<VigDoc>();		
		try{
			String comando = "SELECT DOCUMENTO_ID, DOCUMENTO_NOMBRE, CORTO FROM ENOC.VIG_DOC "+orden;
			lis = enocJdbc.query(comando, new VigDocMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.spring.VigDocDao|lisTodos|:"+ex);
		}			
		
		return lis;
	}

}