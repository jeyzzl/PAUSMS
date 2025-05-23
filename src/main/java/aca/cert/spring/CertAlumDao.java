package aca.cert.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CertAlumDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( CertAlum cer) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.CERT_ALUM"+ 
				"(CODIGO_PERSONAL, PLAN_ID, AVANCE, NUM_CURSOS, FECHA, EQUIVALENCIA, ESTADO, ENCABEZADO, LINEA)"+
				" VALUES(?, ?, ?, TO_NUMBER(?, '999'), TO_DATE(?, 'DD/MM/YYYY'), ?, ?, ?, ?)";
			Object[] parametros = new Object[] {cer.getCodigoPersonal(),cer.getPlanId(), cer.getAvance(), cer.getNumCursos(), cer.getFecha(), cer.getEquivalencia(),
					cer.getEstado(), cer.getEncabezado(), cer.getLinea()};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}				
		}catch(Exception ex){
			System.out.println("Error - aca.cert.spring.CertAlumDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg( CertAlum cer) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.CERT_ALUM"
				+ " SET AVANCE = ?,"
				+ " NUM_CURSOS = TO_NUMBER(?, '999'),"
				+ " FECHA = TO_DATE(?, 'DD/MM/YYYY'),"
				+ " EQUIVALENCIA = ?,"
				+ " ESTADO = ?,"
				+ " ENCABEZADO = ?,"
				+ " LINEA = ?"
				+ " WHERE CODIGO_PERSONAL = ?"
				+ " AND PLAN_ID = ?";		
			Object[] parametros = new Object[] { cer.getAvance(), cer.getNumCursos(), cer.getFecha(), cer.getEquivalencia(),
					cer.getEstado(), cer.getEncabezado(), cer.getLinea(),cer.getCodigoPersonal(),cer.getPlanId()};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}				
		}catch(Exception ex){
			System.out.println("Error - aca.cert.spring.CertAlumDao|updateReg|:"+ex);		
		}
		
		return ok;
	}
	
	public boolean deleteReg( String codigoPersonal, String planId) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CERT_ALUM"+ 
				" WHERE CODIGO_PERSONAL = ?" +
				" AND PLAN_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, planId};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.spring.CertAlumDao|deleteReg|:"+ex);			
			
		}
		return ok;
	}
	
	public CertAlum mapeaRegId( String codigoPersonal, String planId){
		
		CertAlum cer = new CertAlum();
	
		try{
			String comando = "SELECT CODIGO_PERSONAL, PLAN_ID, AVANCE," +
					" NUM_CURSOS, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, EQUIVALENCIA, ESTADO, ENCABEZADO, LINEA " +
					" FROM ENOC.CERT_ALUM" + 
					" WHERE CODIGO_PERSONAL = ?" +
					" AND PLAN_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, planId};		
			cer = enocJdbc.queryForObject(comando, new CertAlumMapper(),parametros);	
			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.spring.CertAlumDao|mapeaRegId|:"+ex);
			
		}
		
		return cer;
	}
	
	public boolean existeReg( String codigoPersonal, String planId){
		boolean 			ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CERT_ALUM WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, planId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.spring.CertAlumDao|existeReg|:"+ex);
		}
		
		return ok;
	}

}
