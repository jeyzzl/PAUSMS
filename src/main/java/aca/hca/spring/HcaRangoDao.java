/**
 * 
 */
package aca.hca.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class HcaRangoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( HcaRango hcaRango) {
		boolean ok 				= false;
		try{
			String comando = "INSERT INTO ENOC.HCA_RANGO(NIVEL_ID, MODALIDAD_ID, RANGO_ID, RANGO_INI, RANGO_FIN, VALOR)" +
				" VALUES(TO_NUMBER(?, '99'), " +
				" TO_NUMBER(?, '99')," +
				" TO_NUMBER(?, '99')," +
				" TO_NUMBER(?, '99')," +
				" TO_NUMBER(?, '99')," +
				" TO_NUMBER(?, '999.99'))";
			Object[] parametros = new Object[] {hcaRango.getNivelId(), hcaRango.getModalidadId(),hcaRango.getRangoId(),hcaRango.getRangoIni(),hcaRango.getRangoFin(),hcaRango.getValor()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.hca.spring.HcaRangoDao|insertReg|:"+ex);
		}
		return ok;
	}	
	
	public boolean updateReg( HcaRango hcaRango) {
		boolean ok = false;
		try{
			String comando = "UPDATE ENOC.HCA_RANGO" + 
				" SET RANGO_INI = TO_NUMBER(?,'99')," +
				" RANGO_FIN = TO_NUMBER(?,'99')," +
				" VALOR = TO_NUMBER(?,'999.99')" +
				" WHERE NIVEL_ID = TO_NUMBER(?, '99')" +
				" AND MODALIDAD_ID = TO_NUMBER(?, '99')" +
				" AND RANGO_ID = TO_NUMBER(?, '99')";
			Object[] parametros = new Object[] {hcaRango.getRangoIni(), hcaRango.getRangoFin(), hcaRango.getValor(), hcaRango.getNivelId(), hcaRango.getModalidadId(), hcaRango.getRangoId()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.hca.spring.HcaRangoDao|updateReg|:"+ex);		
		}
		return ok;
	}	
	
	public boolean deleteReg( String nivelId, String modalidadId, String rangoId ) {
		boolean ok = false;
		try{
			String comando = "DELETE FROM ENOC.HCA_RANGO WHERE NIVEL_ID = TO_NUMBER(?, '99') AND MODALIDAD_ID = TO_NUMBER(?,'99') AND RANGO_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {nivelId,modalidadId,rangoId};			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.hca.spring.HcaRangoDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public HcaRango mapeaRegId( String nivelId, String modalidadId, String rangoId) {
		HcaRango hcaRango = new HcaRango();
		try{
			String comando = "SELECT VALOR, RANGO_INI, RANGO_FIN, RANGO_ID, MODALIDAD_ID, NIVEL_ID FROM ENOC.HCA_RANGO"
					+ " WHERE NIVEL_ID = TO_NUMBER(?, '99')"
					+ " AND MODALIDAD_ID = TO_NUMBER(?, '99')"
					+ " AND RANGO_ID = TO_NUMBER(?, '99')";
			hcaRango = enocJdbc.queryForObject(comando, new HcaRangoMapper(),nivelId,modalidadId,rangoId);			
		}catch(Exception ex){
			System.out.println("Error - aca.hca.spring.HcaRangoDao|mapeaRegId|:"+ex);
		}
		return hcaRango;
	}
	
	public boolean existeReg( String nivelId, String modalidadId, String rangoId) {
		boolean 		ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.HCA_RANGO WHERE NIVEL_ID = TO_NUMBER(?, '99') AND MODALIDAD_ID = TO_NUMBER(?, '99') AND RANGO_ID = TO_NUMBER(?, '99')";
			if (enocJdbc.queryForObject(comando,Integer.class,nivelId,modalidadId,rangoId)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.hca.spring.HcaRangoDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public String maximoReg( String nivelId, String modalidadId) {		
		String maximo			= "1";		
		try{
			String comando = "SELECT COALESCE(MAX(RANGO_ID)+1,1) AS MAXIMO FROM ENOC.HCA_RANGO WHERE NIVEL_ID = TO_NUMBER(?, '99') AND MODALIDAD_ID = TO_NUMBER(?, '99')";
			maximo = enocJdbc.queryForObject(comando,String.class,nivelId,modalidadId);			
		}catch(Exception ex){
			System.out.println("Error - aca.hca.spring.HcaRangoDao|maximoReg|:"+ex);
		}
		return maximo;
	}
	
	public String getValor( String nivelId, String modalidadId, String numAlumnos) {		
		String valor			= "0";		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.HCA_RANGO"
				+ " WHERE NIVEL_ID = TO_NUMBER(?, '99')"
				+ " AND MODALIDAD_ID = TO_NUMBER(?, '99')"
				+ " AND TO_NUMBER(?, '999') BETWEEN RANGO_INI AND RANGO_FIN";
			if (enocJdbc.queryForObject(comando,Integer.class,nivelId,modalidadId,numAlumnos)>=1){
				comando = "SELECT VALOR FROM ENOC.HCA_RANGO"
						+ " WHERE NIVEL_ID = TO_NUMBER(?, '99')"
						+ " AND MODALIDAD_ID = TO_NUMBER(?, '99')"
						+ " AND TO_NUMBER(?, '999') BETWEEN RANGO_INI AND RANGO_FIN";
				valor = enocJdbc.queryForObject(comando,String.class,nivelId,modalidadId,numAlumnos);
			}				
		}catch(Exception ex){
			System.out.println("Error - aca.hca.spring.HcaRangoDao|getValor|:"+ex);
		}
		return valor;
	}
	
	public List<HcaRango> lisTodos( String orden ) {
		
		List<HcaRango> lista	= new ArrayList<HcaRango>();		
		try{
			String comando = "SELECT * FROM ENOC.HCA_RANGO " +orden;	
			lista = enocJdbc.query(comando, new HcaRangoMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.hca.spring.HcaRangoDao|lisTodos|:"+ex);
		}		
		return lista;
	}
}