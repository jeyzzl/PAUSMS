package aca.vigilancia.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class VigIngresoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(VigIngreso objeto){
		
		boolean ok 				= false;
		
		try{
			String comando = "INSERT INTO ENOC.VIG_INGRESO"+ 
					"(FOLIO, FECHA, CODIGO_PERSONAL," +
					" RESIDENCIA_ID, DORMITORIO, TIPO )"+
					" VALUES( TO_NUMBER(?, '99999'), TO_DATE(?,'DD/MM/YYYY HH24:MI:SS'), ?," +
					"  ?, ?, ? )";		
			
			Object[] parametros = new Object[] {
				objeto.getFolio(),objeto.getFecha(),objeto.getCodigoId(),objeto.getResId(),objeto.getDormi(),objeto.getTipo()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.spring.VigIngresDaoo|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg(VigIngreso objeto){ 		
		boolean ok 				= false;
		
		try{
			String comando = " UPDATE ENOC.VIG_INGRESO " + 
				" SET FECHA = TO_DATE(?,'DD/MM/YYYY'), " +
				" RESIDENCIA_ID = ? , " + 				
				" DORMI = ? , " + 				
				" TIPO = ? " +
				" WHERE FOLIO = TO_NUMBER(?, '99999') " +
				" AND CODIGO_PERSONAL = ? ";
			
			Object[] parametros = new Object[] {
				objeto.getFecha(),objeto.getResId(),objeto.getDormi(),objeto.getTipo(),objeto.getFolio(),objeto.getCodigoId()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.spring.VigIngresDaoo|updateReg|:"+ex);
		}
		
		return ok;
	}
	 	
	public boolean deleteReg(String folio){
		boolean ok = false;
		try{
			String comando = "DELETE FROM ENOC.VIG_INGRESO"+ 
				" WHERE FOLIO = TO_NUMBER(?, '99999') " +
				" AND CODIGO_PERSONAL = ? ";

			if (enocJdbc.update(comando,folio)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.spring.VigIngresDaoo|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public VigIngreso mapeaRegId(String folio, String codigoId){
		VigIngreso objeto = new VigIngreso();
		
		try{
	 		String comando = "SELECT FOLIO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA , RESIDENCIA_ID, CODIGO_PERSONAL, " +
	 				" DORMITORIO, TIPO, FROM ENOC.VIG_INGRESO WHERE FOLIO = TO_NUMBER(?,'99999') AND CODIGO_PERSONAL = ? ";
	 		
	 		objeto = enocJdbc.queryForObject(comando, new VigIngresoMapper(), folio,codigoId);
	 		
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.spring.VigIngresDaoo|mapeaRegId|:"+ex);
		}
		
		return objeto;
	}
	
	public boolean existeReg(String folio){
		boolean 		ok 		= false;
		
		try{
			String comando = "SELECT FOLIO FROM ENOC.VIG_INGRESO "+ 
				" WHERE FOLIO = TO_NUMBER(?,'99999')" +
				" AND CODIGO_PERSONAL = ? ";

			if (enocJdbc.queryForObject(comando,Integer.class, folio)>=1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.spring.VigIngresDaoo|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoReg(String codigoPersonal) {
		String maximo 			= "1";
		
		try{
			String comando = "SELECT COALESCE(MAX(FOLIO)+1,1) MAXIMO FROM ENOC.VIG_INGRESO WHERE CODIGO_PERSONAL = ? "; 

			maximo = enocJdbc.queryForObject(comando,String.class,codigoPersonal);
			
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.spring.VigIngresDaoo|maximoReg|:"+ex);
		}
		
		return maximo;
	}
	
	public String ordenarFecha(String fecha){		
		fecha = fecha.substring(8, 10)+ "/" + fecha.substring(5, 7)+ "/" + fecha.substring(0, 4);	
		return fecha;
	}
	
	public List<VigIngreso> getListAll(String orden){
		List<VigIngreso> lis		= new ArrayList<VigIngreso>();
		
		try{
			String comando = "SELECT FOLIO, TO_CHAR(FECHA, 'DD/MM/YYY HH:MI:SS AM') AS FECHA, CODIGO_PERSONAL, RESIDENCIA_ID, DORMITORIO, TIPO " +
 				" FROM ENOC.VIG_INGRESO " +orden; 

			lis = enocJdbc.query(comando, new VigIngresoMapper());		
			
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.spring.VigIngresDaoo|getListAll|:"+ex);
		}			
		
		return lis;
	}
}