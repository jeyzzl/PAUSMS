/**
 * 
 */
package aca.cert.spring;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * @author Jose Torres
 *
 */

@Component
public class CertRelacionDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( CertRelacion rel){
		boolean ok = false;	
		try{
			String comando = "INSERT INTO ENOC.CERT_RELACION"+ 
				"(CURSO_ID, CURSO_CERT)"+
				" VALUES(?, ?)";
			Object[] parametros = new Object[] { rel.getCursoId(), rel.getCursoCert()};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.spring.CertRelacionDao|insertReg|:"+ex);	
		}		
		return ok;
	}
	
	public boolean deleteReg( String cursoCert ){
		boolean ok = false;	
		try{
			String comando = "DELETE FROM ENOC.CERT_RELACION"+ 
				" WHERE CURSO_CERT = ?";
			Object[] parametros = new Object[] {cursoCert};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.spring.CertRelacionDao|deleteReg|:"+ex);		
		}
		return ok;
	}
	
	public CertRelacion mapeaRegId( String cursoCert){
		
		CertRelacion rel = new CertRelacion();		
		try{
			String comando = "SELECT CURSO_ID, CURSO_CERT" +
				" FROM ENOC.CERT_RELACION" + 
				" WHERE CURSO_CERT = ?";
			Object[] parametros = new Object[] {cursoCert};		
			rel = enocJdbc.queryForObject(comando, new CertRelacionMapper(),parametros);		
		}catch(Exception ex){
			System.out.println("Error - aca.cert.spring.CertRelacionDao|mapeaRegId|:"+ex);		
		}
		return rel;
	}
	
	public boolean existeReg( String cursoCert){
		boolean 			ok 	= false;		
		try{
			String comando = "SELECT COUNT(CURSO_ID) FROM ENOC.CERT_RELACION"+ 
				" WHERE CURSO_CERT = ?";
			Object[] parametros = new Object[] {cursoCert};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.spring.CertRelacionDao|existeReg|:"+ex);		
		}		
		return ok;
	}	
	
	public HashMap<String,CertRelacion> mapCertRelacion(List<CertCurso> lisCursos) {
		HashMap<String,CertRelacion> mapa = new HashMap<String,CertRelacion>();
		try{
			
			String comando1 = "SELECT COUNT(CURSO_ID) FROM ENOC.CERT_RELACION WHERE CURSO_CERT = ?";

			for(CertCurso curso : lisCursos) {
				if(enocJdbc.queryForObject(comando1, Integer.class, new Object[] {curso.getCursoId()}) >= 1) {
					String comando = "SELECT CURSO_ID, CURSO_CERT FROM ENOC.CERT_RELACION WHERE CURSO_CERT = ?";
					CertRelacion certRelacion = enocJdbc.queryForObject(comando, new CertRelacionMapper(), new Object[] {curso.getCursoId()});
					mapa.put(curso.getCursoId(), certRelacion);
				}
			}
		}catch(Exception ex){
			System.out.println("Error - aca.cert.spring.CertRelacionDao|mapCertRelacion|:"+ex);			
		}

		return mapa;
	}
	
}