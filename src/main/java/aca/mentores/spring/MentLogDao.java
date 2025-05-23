package aca.mentores.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class MentLogDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(MentLog ment) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.MENT_LOG" + 
					" (FOLIO, MENTOR_ID, CODIGO_PERSONAL, FECHA, TAB)" +
					" VALUES(TO_NUMBER(?, '99999'), ?, ?, now(), TO_NUMBER(?, '99'))";
			
			Object[] parametros = new Object[] {ment.getFolio(),ment.getMentorId(),ment.getCodigoPersonal(),ment.getTab()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch (Exception ex){
			System.out.println("Error - aca.mentores.spring.MentLogDao|insertReg|:"+ex);
		}
		return ok;
	}
	
	public boolean updateReg(MentLog ment) {
		boolean ok = true;
		
		try{
			String comando = "UPDATE ENOC.MENT_LOG" + 
					" SET FECHA = TO_DATE(?, 'DD/MM/YYYY HH:MM:SS')." +
					" TAB = TO_NUMBER(?, '99')" +
					" WHERE FOLIO = TO_NUMBER(?, '99999')" +
					" AND MENTOR_ID = ?" +
					" AND CODIGO_PERSONAL = ?";
			
			Object[] parametros = new Object[] {ment.getFecha(),ment.getFolio(),ment.getMentorId(),ment.getCodigoPersonal()}; 			 			
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch (Exception ex){
			System.out.println("Error - aca.mentores.spring.MentLogDao|updateReg|:"+ex);
		}
		return ok;
	}
		
	public boolean deleteReg(String folio, String mentorId, String codigoPersonal){
		boolean ok = true;
		
		try{
			String comando = "DELETE FROM ENOC.MENT_LOG"+ 
				" WHERE FOLIO = TO_NUMBER(?, '99999')" +
				" AND MENTOR_ID = ?" +
				" AND CODIGO_PERSONAL = ?";
			
			Object[] parametros = new Object[] {folio,mentorId,codigoPersonal};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch (Exception ex){
			System.out.println("Error - aca.mentores.spring.MentLogDao|deleteReg|:"+ex);
		}
		return ok;
	}
	
	public MentLog mapeaRegId( String folio, String mentorId, String codigoPersonal) {
		MentLog ment = new MentLog();
		
		try{ 
			String comando = "SELECT FOLIO, MENTOR_ID, CODIGO_PERSONAL," +
					" TO_CHAR(FECHA, 'DD/MM/YYYY HH:MM:SS'), TAB"+
					" FROM ENOC.MENT_LOG" + 
					" WHERE FOLIO = TO_NUMBER(?, '99999')" +
					" AND MENTOR_ID = ?" +
					" AND CODIGO_PERSONAL = ?";
			
					Object[] parametros = new Object[] {folio,mentorId,codigoPersonal};
					ment = enocJdbc.queryForObject(comando, new MentLogMapper(), parametros);
					
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentLogDao|mapeaRegId|:"+ex);
		}
		return ment;
	}
	
	public boolean existeReg(String folio, String mentorId, String codigoPersonal) {
		boolean 		ok 		= false;
				
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.MENT_LOG" + 
					" WHERE FOLIO = ?" +
					" AND MENTOR_ID = ?" +
					" AND CODIGO_PERSONAL = ?";
		
			Object[] parametros = new Object[] {folio,mentorId,codigoPersonal};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentLogDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	
	public String maximoReg( String mentorId, String codigoPersonal) {
		String maximo			= "0";
		
		try{
			String comando = "SELECT COALESCE(MAX(FOLIO)+1,1) AS MAXIMO" +
					" FROM ENOC.MENT_LOG" + 
					" WHERE MENTOR_ID = ?" +
					" AND CODIGO_PERSONAL = ?";
			
			Object[] parametros = new Object[] {mentorId,codigoPersonal};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
 				maximo = enocJdbc.queryForObject(comando, String.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentLogDao|maximoReg|:"+ex);
		}
		return maximo;
	}
	
	public String getNumAlumConsultados( String periodoId, String mentorId) {
		String num			= "0";
		
		try{
			String comando = "SELECT COUNT(DISTINCT(CODIGO_PERSONAL)) AS NUMALUM FROM ENOC.MENT_LOG" + 
					" WHERE MENTOR_ID = ?" +
					" AND FECHA BETWEEN (SELECT F_INICIO FROM ENOC.CAT_PERIODO WHERE PERIODO_ID = ?)" + 
							      " AND (SELECT F_FINAL FROM ENOC.CAT_PERIODO WHERE PERIODO_ID = ?)"; 
			
			Object[] parametros = new Object[] {periodoId,mentorId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				num = enocJdbc.queryForObject(comando, String.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentLogDao|getNumAlumConsultados|:"+ex);
		}
		return num;
	}
	
	public String getConsultasTab( String periodoId, String mentorId, String tab) {
		String num			= "0";
		
		try{
			String comando = "SELECT COUNT(CODIGO_PERSONAL) NUMCONSULTAS FROM ENOC.MENT_LOG" + 
					" WHERE MENTOR_ID = ?" +
					" AND FECHA BETWEEN (SELECT F_INICIO FROM ENOC.CAT_PERIODO WHERE PERIODO_ID = ?)" + 
							      " AND (SELECT F_FINAL FROM ENOC.CAT_PERIODO WHERE PERIODO_ID = ?)" + 
					" AND TAB = TO_NUMBER(?, '99')";
			
			Object[] parametros = new Object[] {periodoId,mentorId,tab};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				num = enocJdbc.queryForObject(comando, String.class, parametros);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentLogDao|getConsultasTab|:"+ex);
		}
		return num;
	}
	
	public String getConsultasAlumnoTab( String periodoId, String mentorId, String codigoPersonal, String tab) {
		String num			= "0";
		
		try{
			String comando = "SELECT COUNT(CODIGO_PERSONAL) NUMCONSULTAS FROM ENOC.MENT_LOG" + 
					" WHERE MENTOR_ID = ?" +
					" AND FECHA BETWEEN (SELECT F_INICIO FROM ENOC.CAT_PERIODO WHERE PERIODO_ID = ?)" + 
							      " AND (SELECT F_FINAL FROM ENOC.CAT_PERIODO WHERE PERIODO_ID = ?)" + 
					" AND TAB = TO_NUMBER(?, '99')" +
					" AND CODIGO_PERSONAL = ?";
				
			Object[] parametros = new Object[] {mentorId,periodoId,codigoPersonal,tab};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				num = enocJdbc.queryForObject(comando, String.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentLogDao|getConsultasTab|:"+ex);
		}
		return num;
	}
	
	public List<String> getListAlumnosMentor( String mentorId, String periodoId, String orden) {
		List<String> lista	= new ArrayList<String>();
			
		try{
			String comando = "SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL FROM ENOC.MENT_LOG" + 
					" WHERE MENTOR_ID = ?" +
					" AND FECHA BETWEEN (SELECT F_INICIO FROM ENOC.CAT_PERIODO WHERE PERIODO_ID = ?)" + 
								  " AND (SELECT F_FINAL FROM ENOC.CAT_PERIODO WHERE PERIODO_ID = ?) "+orden; 
			
			lista = enocJdbc.queryForList(comando, String.class);
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentorLogDao|getListAlumnosMentor|:"+ex);
		}
		return lista;
	}
}