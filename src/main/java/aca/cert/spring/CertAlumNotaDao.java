package aca.cert.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanMapper;

/**
 * @author Jose Torres
 *
 */
@Component
public class CertAlumNotaDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( CertAlumNota cerNota) {
		boolean ok = false;
		
		try{			
			String comando = "INSERT INTO ENOC.CERT_ALUM_NOTA"+ 
				"(CODIGO_PERSONAL, FOLIO, PLAN_ID, CICLO_ID, CURSO_ID," +
				" CURSO, ESTADO, NOTA, NOTA_LETRA, OPTATIVA,PROMEDIA)"+
				" VALUES(?, TO_NUMBER(?, '999'), ?, ?, ?," +
				" ?, ?, ?, ?, ?,? )";
			Object[] parametros = new Object[] {cerNota.getCodigoPersonal(),cerNota.getFolio(), cerNota.getPlanId(), cerNota.getCicloId(), cerNota.getCursoId(), cerNota.getCurso(),
					cerNota.getEstado(), cerNota.getNota(), cerNota.getNotaLetra(), cerNota.getOptativa(), cerNota.getPromedia()};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.spring.CertAlumNotaDao|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg( CertAlumNota cerNota) {
		
		boolean ok = false;
		
		try{			
			String comando = "UPDATE ENOC.CERT_ALUM_NOTA"+ 
				" SET PLAN_ID = ?," +
				" CICLO_ID = ?," +
				" CURSO_ID = ?," +
				" CURSO = ?," +
				" ESTADO = ?," +
				" NOTA = ?,"+
				" NOTA_LETRA = ?," +
				" OPTATIVA = ? ," +
				" PROMEDIA = ? "+
				" WHERE CODIGO_PERSONAL = ?" +
				" AND FOLIO = TO_NUMBER(?, '999')";	
			Object[] parametros = new Object[] { cerNota.getPlanId(), cerNota.getCicloId(), cerNota.getCursoId(), cerNota.getCurso(), cerNota.getEstado(), cerNota.getNota(),cerNota.getNotaLetra(),
					cerNota.getOptativa(), cerNota.getPromedia(), cerNota.getCodigoPersonal(), cerNota.getFolio()};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.spring.CertAlumNotaDao|updateReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean deleteReg( String codigoPersonal, String folio) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CERT_ALUM_NOTA"+ 
				" WHERE CODIGO_PERSONAL = ?" +
				" AND FOLIO = TO_NUMBER(?, '999')";
			Object[] parametros = new Object[] {codigoPersonal, folio};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.spring.CertAlumNotaDao|deleteReg|:"+ex);			
			
		}
		return ok;
	}
	
	public boolean deleteNotas( String codigoPersonal, String planId) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CERT_ALUM_NOTA"+ 
				" WHERE CODIGO_PERSONAL = ?" +
				" AND PLAN_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, planId};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.spring.CertAlumNotaDao|deleteNotas|:"+ex);			

		}
		return ok;
	}
	
	public CertAlumNota mapeaRegId( String codigoPersonal, String cursoId) {
		
		CertAlumNota cerNota = new CertAlumNota();

		try{
			String comando = "SELECT CODIGO_PERSONAL, FOLIO, PLAN_ID, CICLO_ID, CURSO_ID," +
					" CURSO, ESTADO, NOTA, NOTA_LETRA, OPTATIVA, PROMEDIA" +
					" FROM ENOC.CERT_ALUM_NOTA" + 
					" WHERE CODIGO_PERSONAL = ?" +
					" AND CURSO_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, cursoId};		
			cerNota = enocJdbc.queryForObject(comando, new CertAlumNotaMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.spring.CertAlumNotaDao|mapeaRegId|:"+ex);
		}
		
		return cerNota;
	}
	
	public boolean existeReg( String codigoPersonal, String cursoId) {
		boolean 			ok 	= false;
		
		try{
			String comando = "SELECT COUNT(CODIGO_PERSONAL) FROM ENOC.CERT_ALUM_NOTA"+ 
				" WHERE CODIGO_PERSONAL = ?" +
				" AND CURSO_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, cursoId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.spring.CertAlumNotaDao|existeReg|:"+ex);	
		}
		
		return ok;
	}
	
	public int getNumAlumNotas( String codigoPersonal, String planId) {

		int numNotas 			= 0;
		
		try{
			String comando = "SELECT COALESCE(COUNT(CODIGO_PERSONAL),0) AS NUM_NOTAS FROM ENOC.CERT_ALUM_NOTA"+ 
				" WHERE CODIGO_PERSONAL = ?" +
				" AND PLAN_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, planId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				numNotas = enocJdbc.queryForObject(comando, Integer.class, parametros);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.spring.CertAlumNotaDao|existeReg|:"+ex);	
		}
		
		return numNotas;
	}
	
	public boolean cerrarNotas( String codigoPersonal, String planId) {
		
		boolean ok 				= false;
		
		try{
			String comando = "UPDATE ENOC.CERT_ALUM_NOTA SET ESTADO = 'C'"+
				" WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ?";
			Object[] parametros = new Object[] { codigoPersonal, planId};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.spring.CertAlumNotaDao|cerrarNotas|:"+ex);

		}
		
		return ok;
	}
	
	public boolean abrirNotas( String codigoPersonal, String planId) {
			
		boolean ok 				= false;
		
		try{
			String comando = "UPDATE ENOC.CERT_ALUM_NOTA SET ESTADO = 'A'"+ 
				" WHERE CODIGO_PERSONAL = ?"+
				" AND PLAN_ID = ?";		
			Object[] parametros = new Object[] { codigoPersonal, planId};		
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.spring.CertAlumNotaDao|abrirNotas|:"+ex);

		}
		
		return ok;
	}
	
	public String maxReg( String codigoPersonal) {
		
		String maximo 			= "1";
		
		try{
			String comando = "SELECT COALESCE(MAX(FOLIO)+1,1) AS MAXIMO FROM ENOC.CERT_ALUM_NOTA"+ 
				" WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				maximo = enocJdbc.queryForObject(comando, String.class, parametros);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.spring.CertAlumNotaDao|maxReg|:"+ex);

		}
		
		return maximo;
	}
	
	public int getNumMatCiclo( String codigoPersonal, String planId, String ciclo) {
		
		int numMat				= 0;
		
		try{
			String comando = "SELECT COUNT(CODIGO_PERSONAL) AS NUMMAT FROM ENOC.CERT_ALUM_NOTA"+ 
				" WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ? AND CICLO_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {codigoPersonal, planId, ciclo};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				numMat = enocJdbc.queryForObject(comando, Integer.class, parametros);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.spring.CertAlumNotaDao|getNumMatCiclo|:"+ex);
	
		}
		
		return numMat;
	}
	
	public HashMap<String, CertAlumNota> mapCertAlumNotas(String codigoPersonal) {
		List<CertAlumNota> lista			= new ArrayList<CertAlumNota>();
		HashMap<String,CertAlumNota> mapa	= new HashMap<String,CertAlumNota>();	
		
		try{
			String comando = " SELECT CODIGO_PERSONAL, FOLIO, PLAN_ID, CICLO_ID, CURSO_ID,CURSO, ESTADO, NOTA, NOTA_LETRA, OPTATIVA, PROMEDIA"
					+ " FROM ENOC.CERT_ALUM_NOTA WHERE CODIGO_PERSONAL = ?";
			lista = enocJdbc.query(comando, new CertAlumNotaMapper(), codigoPersonal);
			
			for (CertAlumNota alumno : lista){
				mapa.put(alumno.getCodigoPersonal()+alumno.getCursoId(), alumno);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.cert.spring.CertAlumNotaDao|mapCertAlumNotas|:"+ex);
		}
		return mapa;
	}
	
}