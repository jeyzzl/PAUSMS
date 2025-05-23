package aca.vigilancia.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class VigInfraccionDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(VigInfraccion objeto){
		boolean ok 				= false;
		
		try{
			String comando = "INSERT INTO ENOC.VIG_INFRACCION"+ 
					"(FOLIO, FECHA, AUTO_ID," +
					" TIPO_ID, DESCRIPCION, MULTA )"+
					" VALUES( TO_NUMBER(?, '9999999'), TO_DATE(?,'DD/MM/YYYY'), TO_NUMBER(?, '9999')," +
					" TO_NUMBER(?, '99'), ?, TO_NUMBER(?, '9999.99'))";	
			
			Object[] parametros = new Object[] {
				objeto.getFolio(),objeto.getFecha(),objeto.getAutoId(),objeto.getTipoId(),objeto.getDescripcion(),objeto.getMulta()
			};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.spring.VigAutoDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg(VigInfraccion objeto){ 		
		
		boolean ok 				= false;
		
		try{
			String comando = " UPDATE ENOC.VIG_INFRACCION "+ 
				" SET FECHA = TO_DATE(?,'DD/MM/YYYY'), "+
				" AUTO_ID = TO_NUMBER(?,'9999'), "+ 				
				" TIPO_ID = TO_NUMBER(?,'99'), "+
				" DESCRIPCION = ?, "+
				" MULTA = TO_NUMBER(?,'9999.99'), "+
				" WHERE FOLIO = TO_NUMBER(?, '9999999') ";
			
			Object[] parametros = new Object[] {
				objeto.getFecha(),objeto.getAutoId(),objeto.getTipoId(),objeto.getDescripcion(),objeto.getMulta(),objeto.getFolio()
			};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.spring.VigAutoDao|updateReg|:"+ex);
		}
		
		return ok;
	}
	 	
	public boolean deleteReg(String folio){
		boolean ok = false;
		try{
			String comando = "DELETE FROM ENOC.VIG_INFRACCION"+ 
				" WHERE FOLIO = TO_NUMBER(?, '9999999')  ";
			
			if (enocJdbc.update(comando,folio)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.spring.VigAutoDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public VigInfraccion mapeaRegId(String folio){
		VigInfraccion objeto = new VigInfraccion();
		try{
	 		String comando = "SELECT FOLIO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA , AUTO_ID, TIPO_ID, " +
	 				" DESCRIPCION, MULTA FROM ENOC.VIG_INFRACCION WHERE FOLIO = TO_NUMBER(?,'9999999') "; 

	 		objeto = enocJdbc.queryForObject(comando, new VigInfraccionMapper(), folio);
	 		
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.spring.VigAutoDaoInfraccion|mapeaRegId|:"+ex);
		}
		
		return objeto;
	}
	
	public boolean existeReg(String folio){
		boolean 		ok 		= false;
		
		try{
			String comando = "SELECT FOLIO FROM ENOC.VIG_INFRACCION "+ 
				" WHERE FOLIO = TO_NUMBER(?,'9999999')";

			if (enocJdbc.queryForObject(comando,Integer.class, folio)>=1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.spring.VigAutoDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean existeAutoId(String autoId){
		boolean 		ok 		= false;
		
		try{
			String comando = "SELECT AUTO_ID FROM ENOC.VIG_INFRACCION "+ 
				" WHERE AUTO_ID = TO_NUMBER(?,'9999')";

				if (enocJdbc.queryForObject(comando,Integer.class, autoId)>=1){
					ok = true;
				}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.spring.VigAutoDao|existeAutoId|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoReg(){
		String maximo 			= "1";
		
		try{
			String comando = "SELECT COALESCE(MAX(FOLIO)+1,1) MAXIMO FROM ENOC.VIG_INFRACCION"; 

			maximo = enocJdbc.queryForObject(comando,String.class);
			
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.spring.VigAutoDao|maximoReg|:"+ex);
		}
		
		return maximo;
	}
	
	public String ordenarFecha(String fecha){		
		fecha = fecha.substring(8, 10)+ "/" + fecha.substring(5, 7)+ "/" + fecha.substring(0, 4);	
		return fecha;
	}
	
	public List<VigInfraccion> getListAll(String orden){
		List<VigInfraccion> lis	= new ArrayList<VigInfraccion>();
		
		try{
			String comando = "SELECT FOLIO, FECHA, AUTO_ID, TIPO_ID, DESCRIPCION, MULTA " +
 				" FROM ENOC.VIG_INFRACCION " +orden; 
			
			lis = enocJdbc.query(comando, new VigInfraccionMapper());		
			
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.spring.VigAutoDao|getListAll|:"+ex);
		}			
		
		return lis;
	}
}