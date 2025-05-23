package aca.vigilancia.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class VigTipoMultaDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(VigTipoMulta objeto){
		boolean ok 				= false;
		
		try{
			String comando = "INSERT INTO ENOC.VIG_TIPOMULTA"+ 
				"(TIPO_ID, TIPO_NOMBRE, COSTO)"+
				" VALUES( TO_NUMBER(?, '99'), ?, TO_NUMBER(?, '999.99'))";
			
			Object[] parametros = new Object[] {
				objeto.getTipoId(),objeto.getTipoNombre(),objeto.getCosto() 			
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.spring.VigTipoMultaDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg(VigTipoMulta objeto){ 		
		boolean ok 				= false;
		
		try{
			String comando = "UPDATE ENOC.VIG_TIPOMULTA"+ 
				" SET"+		
				" TIPO_NOMBRE = ?,"+
				" COSTO = TO_NUMBER(?, '999.99')"+
				" WHERE TIPO_ID = TO_NUMBER(?, '99')";
			
			Object[] parametros = new Object[] {
				objeto.getTipoNombre(),objeto.getCosto(),objeto.getTipoId()	
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
				
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.spring.VigTipoMultaDao|updateReg|:"+ex);
		}
		
		return ok;
	}
	 	
	public boolean deleteReg(String tipoId){
		boolean ok = false;
		try{
			String comando = "DELETE FROM ENOC.VIG_TIPOMULTA"+ 
				" WHERE TIPO_ID = TO_NUMBER(?, '99')  ";
			if (enocJdbc.update(comando,tipoId)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.spring.VigTipoMultaDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public VigTipoMulta mapeaRegId(String tipoId){
		VigTipoMulta objeto = new VigTipoMulta();
		
		try{
	 		String comando = "SELECT TIPO_ID, TIPO_NOMBRE, COSTO" +
	 			" FROM ENOC.VIG_TIPOMULTA WHERE TIPO_ID = TO_NUMBER(?,'99') "; 

	 		objeto = enocJdbc.queryForObject(comando, new VigTipoMultaMapper(), tipoId);
	 		
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.spring.VigTipoMultaDao|mapeaRegId|:"+ex);
		}
		
		return objeto;
	}
	
	public String maximoReg(){
		String maximo 			= "1";

		try{
			String comando = "SELECT COALESCE(MAX(TIPO_ID)+1,'') AS MAXIMO FROM ENOC.VIG_TIPOMULTA";

			maximo = enocJdbc.queryForObject(comando,String.class);
			
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.spring.VigTipoMultaDao|maximoReg|:"+ex);
		}
		
		return maximo;
	}
	
	public boolean existeReg(String tipoId){
		boolean 			ok 		= false;
		
		try{
			String comando = "SELECT TIPO_ID FROM ENOC.VIG_TIPOMULTA"+ 
				" WHERE TIPO_ID = ?";
			
			if (enocJdbc.queryForObject(comando,Integer.class, tipoId)>=1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.spring.VigTipoMultaDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public List<VigTipoMulta> getListAll(String orden ){
		List<VigTipoMulta> lis	= new ArrayList<VigTipoMulta>();
		
		try{
			String comando = "SELECT TIPO_ID, TIPO_NOMBRE, COSTO" +
 				" FROM ENOC.VIG_TIPOMULTA "+orden;			 

			lis = enocJdbc.query(comando, new VigTipoMultaMapper());		
			
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.spring.VigTipoMultaDao|getListAll|:"+ex);
		}			
		
		return lis;
	}
}