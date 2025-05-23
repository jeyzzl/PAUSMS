// Bean del Catalogo de Grupos
package  aca.carga.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CargaGrupoBorraDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( CargaGrupoBorra borra ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.CARGA_GRUPO_BORRA"+ 
				" (CURSO_CARGA_ID, FOLIO, FECHA, USUARIO, IP, NUMEST,NUMACT)"+				 
				" VALUES( ?, TO_NUMBER(?,'99'), TO_DATE(?,'DD/MM/YYYY'), ?, ?," +
				" TO_NUMBER(?,'99'), TO_NUMBER(?,'999'))";		
			
			Object[] parametros = new Object[] {borra.getCursoCargaId(),borra.getFolio(),borra.getFecha(),borra.getUsuario(),borra.getIp(),borra.getNumEst(),borra.getNumAct()};
 			
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}				
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoBorraDao|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg( CargaGrupoBorra borra ) {
		boolean ok 				= false;
		
		try{
			String comando = "UPDATE ENOC.CARGA_GRUPO_BORRA"+ 
				" SET FECHA = TO_DATE(?,'DD/MM/YYYY')," +
				" USUARIO = ?," +
				" IP = ?," +
				" NUMEST = TO_NUMBER(?,'99')," +
				" NUMACT = TO_NUMBER(?,'999')"+				
				" WHERE CURSO_CARGA_ID = ? " +
				" AND FOLIO = TO_NUMBER(?,'99')";	
			
			Object[] parametros = new Object[] {borra.getFecha(),borra.getUsuario(),borra.getIp(),borra.getNumEst(),borra.getNumAct(),borra.getCursoCargaId(),borra.getFolio()}; 			 			
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
			
						
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoBorraDao|updateReg|:"+ex);		 
		}
		return ok;
	}	
		
	public boolean deleteReg( String cursoCargaId, String folio ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CARGA_GRUPO_BORRA"+ 
				" WHERE CURSO_CARGA_ID = ? " +
				" AND FOLIO = TO_NUMBER(?,'99')";
			
			Object[] parametros = new Object[] {cursoCargaId,folio};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoBorraDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public CargaGrupoBorra mapeaRegId(  String cursoCargaId, String folio ) {
		CargaGrupoBorra borra = new CargaGrupoBorra();		
		
		try{
			String comando = "SELECT"+
				" CURSO_CARGA_ID,"+
				" FOLIO,"+
				" TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA,"+
				" USUARIO,"+
				" IP,"+
				" NUMEST,"+
				" NUMACT"+			
				" FROM ENOC.CARGA_GRUPO_BORRA " + 
				" WHERE CURSO_CARGA_ID = ? " +
				" AND FOLIO = TO_NUMBER(?,'99')";
				
				Object[] parametros = new Object[] {cursoCargaId,folio};
				borra = enocJdbc.queryForObject(comando, new CargaGrupoBorraMapper(), parametros);
				
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoBorraDao|mapeaRegId|:"+ex);
		}		
		return borra;
	}
	
	public boolean existeReg( String cursoCargaId, String folio) {
		boolean 			ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA_GRUPO"+ 
				" WHERE CURSO_CARGA_ID = ?" +
				" AND FOLIO = TO_NUMBER(?,'99')";
			
			Object[] parametros = new Object[] {cursoCargaId,folio};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoBorraDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public String maximoReg( String cursoCargaId) {
		String maximo 			= "1";		
		try{
			String comando = "SELECT COALESCE(MAX(FOLIO)+1,1) AS MAXIMO FROM ENOC.CARGA_GRUPO_BORRA WHERE CURSO_CARGA_ID = ?";			
			Object[] parametros = new Object[] {cursoCargaId};
 			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
 				maximo = enocJdbc.queryForObject(comando,String.class,parametros);
			} 			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoBorraDao|maximoReg|:"+ex);
		}
		return maximo;
	}
	
}