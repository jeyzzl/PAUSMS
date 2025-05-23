package aca.vigilancia.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class VigDocAutoDao{	
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(VigDocAuto objeto){
		boolean ok 				= false;		
		try{
			String comando = "INSERT INTO ENOC.VIG_DOCAUTO (AUTO_ID, DOCUMENTO_ID, VIGENCIA, COMENTARIO )"
					+ " VALUES( TO_NUMBER(?, '9999'), TO_NUMBER(?, '99'), TO_DATE(?, 'DD/MM/YYYY'), ?)";
			Object[] parametros = new Object[]{
				objeto.getAutoId(), objeto.getDocumentoId(), objeto.getVigencia(), objeto.getComentario()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.spring.VigDocAutoDao|insertReg|:"+ex);
		}		
		return ok;
	}	
	
	public boolean updateReg(VigDocAuto objeto){
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.VIG_DOCAUTO SET COMENTARIO = ?, VIGENCIA = TO_DATE(?, 'DD/MM/YYYY') WHERE AUTO_ID = TO_NUMBER(?, '9999') AND DOCUMENTO_ID = TO_NUMBER(?, '99')";
			Object[] parametros = new Object[]{
				objeto.getComentario(), objeto.getVigencia(), objeto.getAutoId(), objeto.getDocumentoId()
			};				
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.spring.VigDocAutoDao|updateReg|:"+ex);
		}
		
		return ok;
	}
	 	
	public boolean deleteReg(String autoId, String documentoId){
		boolean ok = false;
		try{
			String comando = "DELETE FROM ENOC.VIG_DOCAUTO WHERE AUTO_ID = TO_NUMBER(?, '9999') AND DOCUMENTO_ID = TO_NUMBER(?, '99')";
			Object[] parametros = new Object[] {autoId,documentoId};
			if (enocJdbc.update(comando, parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.spring.VigDocAutoDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public VigDocAuto mapeaRegId(String autoId, String documentoId) {
		VigDocAuto objeto = new VigDocAuto();
		try{
	 		String comando = "SELECT AUTO_ID, DOCUMENTO_ID, TO_CHAR(VIGENCIA,'DD/MM/YYYY') AS VIGENCIA, COMENTARIO FROM ENOC.VIG_DOCAUTO WHERE AUTO_ID = TO_NUMBER(?, '9999') AND DOCUMENTO_ID = TO_NUMBER(?,'99')"; 
	 		Object[] parametros = new Object[] {autoId, documentoId};
	 		objeto = enocJdbc.queryForObject(comando, new VigDocAutoMapper(), parametros);
	 		
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.spring.VigDocAutoDao|mapeaRegId|:"+ex);
		}
		
		return objeto;
	}
	
	public boolean existeReg(String autoId, String documentoId){
		boolean ok = false;		
		try{
			String comando = "SELECT COUNT(AUTO_ID) FROM ENOC.VIG_DOCAUTO WHERE AUTO_ID = TO_NUMBER(?, '9999') AND DOCUMENTO_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {autoId, documentoId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.spring.VigDocAutoDao|existeReg|:"+ex);
		}		
		return ok;
	}	
	
	public List<VigDocAuto> lisTodos(String orden ){
		
		List<VigDocAuto> lis = new ArrayList<VigDocAuto>();	
		try{
			String comando = "SELECT AUTO_ID, DOCUMENTO_ID, TO_CHAR(VIGENCIA,'DD/MM/YYYY') AS VIGENCIA, COMENTARIO FROM ENOC.VIG_DOCAUTO "+orden;
			lis = enocJdbc.query(comando, new VigDocAutoMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.spring.VigDocAutoDao|lisTodos|:"+ex);
		}		
		return lis;
	}
	
	public HashMap<String,String> mapaTodos(){
		HashMap<String,String> mapa = new  HashMap<String,String>();
		List<aca.Mapa> lis = new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT AUTO_ID||'-'||DOCUMENTO_ID AS LLAVE, TO_CHAR(VIGENCIA,'DD/MM/YYYY') AS VALOR FROM ENOC.VIG_DOCAUTO";
			lis = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa doc : lis) {
				mapa.put(doc.getLlave(), doc.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.spring.VigDocumentoDao|mapaTodos|:"+ex);
		}	
		return mapa;
	}
}