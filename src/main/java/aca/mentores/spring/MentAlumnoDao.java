/**
 * 
 */
package aca.mentores.spring;


import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class MentAlumnoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(MentAlumno ment) {
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.MENT_ALUMNO" + 
					" (PERIODO_ID, MENTOR_ID, CODIGO_PERSONAL, FECHA, STATUS, CARRERA_ID, FECHA_INICIO, FECHA_FINAL, FOLIO, CARGA_ID)" +
					" VALUES( ?, ?, ?, TO_DATE(?, 'DD/MM/YYYY'), ?, ?, TO_DATE(?, 'DD/MM/YYYY'), TO_DATE(?, 'DD/MM/YYYY'), TO_NUMBER(COALESCE(?, '1'),'999'), ?)";
			Object[] parametros = new Object[] {ment.getPeriodoId(), ment.getMentorId(), 
			ment.getCodigoPersonal(), ment.getFecha(), ment.getStatus(), ment.getCarreraId(),
			ment.getFechaInicio(), ment.getFechaFinal(), ment.getFolio(), ment.getCargaId()};			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch (Exception ex){
			System.out.println("Error - aca.mentores.spring.MentAlumnoDao|insertReg|:"+ex);
		}
		return ok;
	}
	
	public boolean updateReg(MentAlumno ment) {
		boolean ok = true;
		try{
			String comando = "UPDATE ENOC.MENT_ALUMNO" + 
					" SET FECHA = TO_DATE(?, 'DD/MM/YYYY')," +
					" STATUS = ?," +
					" CARRERA_ID = ?, FECHA_INICIO = TO_DATE(?, 'DD/MM/YYYY'), FECHA_FINAL = TO_DATE(?, 'DD/MM/YYYY'), CARGA_ID = ? " +
					" WHERE PERIODO_ID = ?" +
					" AND MENTOR_ID = ?" +
					" AND CODIGO_PERSONAL = ?" +
					" AND FOLIO = TO_NUMBER(COALESCE(?, '1'),'999') ";
			
			Object[] parametros = new Object[] {ment.getFecha(), ment.getStatus(), ment.getCarreraId(),
			ment.getFechaInicio(), ment.getFechaFinal(), ment.getCargaId(), ment.getPeriodoId(),ment.getMentorId(),
			ment.getCodigoPersonal(), ment.getFolio()};			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch (Exception ex){
			System.out.println("Error - aca.mentores.spring.MentAlumnoDao|updateReg|:"+ex);
		}
		
		return ok;
	}
		
	public boolean updateFechas(MentAlumno ment) {
		boolean ok = true;		
		try{
			String comando = "UPDATE ENOC.MENT_ALUMNO" + 
					" SET FECHA_INICIO = TO_DATE(?, 'DD/MM/YYYY'), "+
					" FECHA_FINAL = TO_DATE(?, 'DD/MM/YYYY') " +
					" WHERE PERIODO_ID = ?" +
					" AND MENTOR_ID = ?" +
					" AND CODIGO_PERSONAL = ?" +
					" AND FOLIO = TO_NUMBER(COALESCE(?, '1'),'999') ";
			Object[] parametros = new Object[] {ment.getFechaInicio(), ment.getFechaFinal(),
			ment.getPeriodoId(), ment.getMentorId(), ment.getCodigoPersonal(), ment.getFolio()};			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch (Exception ex){
			System.out.println("Error - aca.mentores.spring.MentAlumnoDao|updateFechas|:"+ex);
		}
		return ok;
	}
	
	public boolean deleteReg(String periodoId, String mentorId, String codigoPersonal, String folio){
		boolean ok = true;		
		try{
			String comando = "DELETE FROM ENOC.MENT_ALUMNO"+ 
				" WHERE PERIODO_ID = ? " +
				" AND MENTOR_ID = ? " +
				" AND CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(COALESCE(?, '1'),'999') ";
			Object[] parametros = new Object[] {periodoId, mentorId, codigoPersonal, folio};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch (Exception ex){
			System.out.println("Error - aca.mentores.spring.MentAlumnoDao|deleteReg|:"+ex);
		}
		return ok;
	}
	
	public MentAlumno mapeaRegId(String periodoId, String mentorId, String codigoPersonal, String folio) {
		
		MentAlumno objeto = new MentAlumno();		
		try{ 
			String comando = "SELECT PERIODO_ID, MENTOR_ID," +
					" CODIGO_PERSONAL, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, STATUS, CARRERA_ID, TO_CHAR(FECHA_INICIO, 'DD/MM/YYYY') AS FECHA_INICIO, TO_CHAR(FECHA_FINAL, 'DD/MM/YYYY') AS FECHA_FINAL, FOLIO, CARGA_ID"+
					" FROM ENOC.MENT_ALUMNO" + 
					" WHERE PERIODO_ID = ?" +
					" AND MENTOR_ID = ?" +
					" AND CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(COALESCE(?, '1'),'999')";
			Object[] parametros = new Object[] {periodoId, mentorId, codigoPersonal, folio};
			objeto = enocJdbc.queryForObject(comando, new MentAlumnoMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentAlumnoDao|mapeaRegId|:"+ex);
		}
		return objeto;
	}
	
	public MentAlumno mapeaRegId(String periodoId, String codigoPersonal, String fecha){
		
		MentAlumno objeto = new MentAlumno();		
		try{ 
			String comando = "SELECT COUNT(*) FROM ENOC.MENT_ALUMNO"
					+ " WHERE PERIODO_ID = ?"
					+ " AND CODIGO_PERSONAL = ?"
					+ " AND TO_DATE(?,'DD/MM/YYYY') BETWEEN FECHA_INICIO AND FECHA_FINAL"
					+ " AND STATUS = 'A'";
			Object[] parametros = new Object[] {periodoId, codigoPersonal, fecha};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){	
				comando = "SELECT PERIODO_ID, MENTOR_ID, CODIGO_PERSONAL, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, STATUS, CARRERA_ID, "
						+ " TO_CHAR(FECHA_INICIO, 'DD/MM/YYYY') AS FECHA_INICIO, TO_CHAR(FECHA_FINAL, 'DD/MM/YYYY') AS FECHA_FINAL, FOLIO, CARGA_ID"
						+ " FROM ENOC.MENT_ALUMNO"
						+ " WHERE PERIODO_ID = ?"
						+ " AND CODIGO_PERSONAL = ?"
						+ " AND TO_DATE(?,'DD/MM/YYYY') BETWEEN FECHA_INICIO AND FECHA_FINAL"
						+ " AND STATUS = 'A'";
				objeto = enocJdbc.queryForObject(comando, new MentAlumnoMapper(), parametros);
			}						
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentAlumnoDao|mapeaRegId|:"+ex);
		}
		return objeto;
	}
	
	public boolean existeReg(String periodoId, String mentorId, String codigoPersonal, String folio){
		boolean	ok = false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.MENT_ALUMNO" + 
					" WHERE PERIODO_ID = ?" +
					" AND MENTOR_ID = ?" +
					" AND CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(COALESCE(?, '1'),'999')";
			Object[] parametros = new Object[] {periodoId, mentorId, codigoPersonal, folio};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentAlumnoDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public boolean existeReg(String periodoId, String codigoPersonal, String fecha){
		boolean	ok = false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.MENT_ALUMNO"
				+ " WHERE PERIODO_ID = ?"					
				+ " AND CODIGO_PERSONAL = ?"
				+ " AND TO_DATE(?,'DD/MM/YYYY') BETWEEN FECHA_INICIO AND FECHA_FINAL"
				+ " AND STATUS = 'A'";
			Object[] parametros = new Object[] {periodoId, codigoPersonal, fecha};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentAlumnoDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public boolean fueAconsejadoDelMentor( String codigoAlumno, String mentorId){
		boolean	ok = false;		
		try{
			String comando = "SELECT COUNT(CODIGO_PERSONAL) FROM ENOC.MENT_ALUMNO WHERE CODIGO_PERSONAL = ? AND MENTOR_ID = ?";
			Object[] parametros = new Object[] {codigoAlumno, mentorId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentAlumnoDao|fueAconsejadoDelMentor|:"+ex);
		}
		return ok;
	}
	
	public boolean tieneAlumnosPeriodoCarrera( String mentorId, String periodoId, String carreraId) {
		boolean ok = false;		
		try{
			String comando = "SELECT * FROM ENOC.MENT_ALUMNO" + 
					" WHERE PERIODO_ID = ?" +
					" AND MENTOR_ID = ?" +
					" AND CARRERA_ID = ?" +
					" AND STATUS = 'A'";
			Object[] parametros = new Object[] {periodoId, mentorId, carreraId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentAlumnoDao|tieneAlumnosPeriodoCarrera|:"+ex);
		}
		return ok;
	}
	
	public boolean elAlumnoEstaAsignado( String codigoPersonal, String periodoId) {
		boolean ok = false;		
		try{
			String comando = "SELECT * FROM ENOC.MENT_ALUMNO" + 
					" WHERE PERIODO_ID = ?" +
					" AND CODIGO_PERSONAL = ?" +
					" AND STATUS = 'A'";
			Object[] parametros = new Object[] {periodoId, codigoPersonal};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentAlumnoDao|elAlumnoEstaAsignado|:"+ex);
		}
		return ok;
	}
	
	public String getMentorId( String matricula, String periodoId ) {
		String idMentor	= "x";		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.MENT_ALUMNO"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND PERIODO_ID = ?"
					+ " AND STATUS = 'A'";
			Object[] parametros = new Object[] {matricula, periodoId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1 ){
				comando = "SELECT MENTOR_ID FROM ENOC.MENT_ALUMNO"
						+ " WHERE CODIGO_PERSONAL = ?"
						+ " AND PERIODO_ID = ?"
						+ " AND STATUS = 'A'";
				idMentor = enocJdbc.queryForObject(comando,String.class,parametros);
			}						
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentAlumnoDao|getMentorId|:"+ex);
		}
		return idMentor;
	}
	
	public String getMentorId( String matricula, String periodoId, String fecha){
		
		String idMentor			= "0";
		String fechaMax			= "0";
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.MENT_ALUMNO"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND PERIODO_ID = ?"
					+ " AND TO_DATE(?,'DD/MM/YYYY') BETWEEN FECHA_INICIO AND FECHA_FINAL"
					+ " AND STATUS = 'A'";			
			Object[] parametros = new Object[] {matricula, periodoId, fecha};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1 ){
				comando = "SELECT MAX(TO_CHAR(FECHA,'YYYY/MM/DD')) FROM ENOC.MENT_ALUMNO"
						+ " WHERE CODIGO_PERSONAL = ?"
						+ " AND PERIODO_ID = ?"
						+ " AND TO_DATE(?,'DD/MM/YYYY') BETWEEN FECHA_INICIO AND FECHA_FINAL"
						+ " AND STATUS = 'A'";				
				fechaMax = enocJdbc.queryForObject(comando,String.class,parametros);				
				comando = "SELECT COUNT(*) FROM ENOC.MENT_ALUMNO"
						+ " WHERE CODIGO_PERSONAL = ?"
						+ " AND PERIODO_ID = ?"
						+ " AND TO_DATE(?,'DD/MM/YYYY') BETWEEN FECHA_INICIO AND FECHA_FINAL"
						+ " AND FECHA = TO_DATE(?,'YYYY/MM/DD')"						
						+ " AND STATUS = 'A'";				
				parametros = new Object[] {matricula, periodoId, fecha, fechaMax};
				if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1 ){
					comando = "SELECT MENTOR_ID FROM ENOC.MENT_ALUMNO"
							+ " WHERE CODIGO_PERSONAL = ?"
							+ " AND PERIODO_ID = ?"
							+ " AND TO_DATE(?,'DD/MM/YYYY') BETWEEN FECHA_INICIO AND FECHA_FINAL"
							+ " AND FECHA = TO_DATE(?,'YYYY/MM/DD')"						
							+ " AND STATUS = 'A'";
					idMentor = enocJdbc.queryForObject(comando,String.class,parametros);
				}				
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentAlumnoDao|getMentorId|:"+ex);
		}		
		return idMentor;
	}
	
	public String getNumAlumnosMentor( String periodoId, String mentorId, String fecha) {
		String num = "";		
		try{
			String comando = "SELECT COUNT(CODIGO_PERSONAL) AS NUMALUM FROM ENOC.MENT_ALUMNO" + 
					" WHERE PERIODO_ID = ?" +
					" AND MENTOR_ID = ? AND STATUS = 'A' " +
					" AND TO_DATE(?, 'DD/MM/YYYY') BETWEEN FECHA_INICIO AND FECHA_FINAL  ";
			Object[] parametros = new Object[] {periodoId, mentorId, fecha};
			num = enocJdbc.queryForObject(comando,String.class,parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentAlumnoDao|getNumAlumnosMentor|:"+ex);
		}
		return num;
	}
	
	public String getMentorActual( String matricula, String periodoId, String mentorId ) {
		String idMentor = "";		
		try{
			String comando = "SELECT MENTOR_ID FROM ENOC.MENT_ALUMNO" + 
				" WHERE CODIGO_PERSONAL = ?" +
				" AND PERIODO_ID = ?" +
				" AND STATUS = 'A'";
			Object[] parametros = new Object[] {matricula, periodoId, mentorId};
			idMentor = enocJdbc.queryForObject(comando,String.class,parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentAlumnoDao|getMentorId|:"+ex);
		}
		return idMentor;
	}
	
	public String getMentorPorFecha( String matricula, String fecha ) {
		String mentorId	= "0";		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.MENT_ALUMNO WHERE CODIGO_PERSONAL = ? AND TO_DATE(?,'DD/MM/YYYY') BETWEEN FECHA_INICIO AND FECHA_FINAL AND ROWNUM=1";
			Object[] parametros = new Object[] {matricula, fecha};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1) {
				comando 	= "SELECT MENTOR_ID FROM ENOC.MENT_ALUMNO WHERE CODIGO_PERSONAL = ? AND TO_DATE(?,'DD/MM/YYYY') BETWEEN FECHA_INICIO AND FECHA_FINAL AND ROWNUM=1";
				mentorId 	= enocJdbc.queryForObject(comando,String.class,parametros);
			}						
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentAlumnoDao|getMentorActual|:"+ex);
		}
		return mentorId;
	}
	
	public String getMentorActual( String matricula, String periodoId) {
		String idMentor	= "";		
		try{
			String comando = "SELECT MENTOR_ID FROM ENOC.MENT_ALUMNO" + 
				" WHERE CODIGO_PERSONAL = ?" +
				" AND PERIODO_ID = ?" +
				" AND STATUS = 'A'";
			Object[] parametros = new Object[] {matricula, periodoId};
			idMentor = enocJdbc.queryForObject(comando,String.class,parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentAlumnoDao|getMentorId|:"+ex);
		}
		return idMentor;
	}
	
	public String getMentorActualPorFecha( String matricula, String periodoId, String fecha) {
		String idMentor	= "0";		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.MENT_ALUMNO"  
				+ " WHERE CODIGO_PERSONAL = ?"
				+ " AND PERIODO_ID = ?"
				+ " AND TO_DATE(?,'DD/MM/YYYY') BETWEEN FECHA_INICIO AND FECHA_FINAL"
				+ " AND STATUS = 'A'";			
			Object[] parametros = new Object[] {matricula, periodoId, fecha};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				comando = "SELECT MENTOR_ID FROM ENOC.MENT_ALUMNO"  
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND PERIODO_ID = ?"
					+ " AND TO_DATE(?,'DD/MM/YYYY') BETWEEN FECHA_INICIO AND FECHA_FINAL"
					+ " AND STATUS = 'A'";
				idMentor = enocJdbc.queryForObject(comando,String.class,parametros);
			}						
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentAlumnoDao|getMentorActualPorFecha|:"+ex);
		}
		return idMentor;
	}
	
	
	/* METODOS PARA CHECAR SI CHOCAN LAS FECHAS */	
	public boolean existeFechaBetween( String codigoPersonal, String periodoId, String fecha) {
		boolean ok = false;		
		try{
			String comando = 	"SELECT COUNT(*) FROM MENT_ALUMNO " +
								" WHERE CODIGO_PERSONAL = ? AND PERIODO_ID = ? " +
								" AND TO_DATE(?, 'DD/MM/YYYY') >= FECHA_INICIO" +
								" AND TO_DATE(?, 'DD/MM/YYYY') <= FECHA_FINAL" ;
			Object[] parametros = new Object[] {codigoPersonal, periodoId, fecha, fecha};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentAlumnoDao|existeFechaBetween|:"+ex);
		}
		return ok;
	}
	
	public boolean existeFechaINIBetween( String codigoPersonal, String periodoId, String fechaIni, String fechaFin) {
		boolean ok 				= false;		
		try{
			String comando = 	"SELECT COUNT(*) FROM MENT_ALUMNO " +
										" WHERE CODIGO_PERSONAL = ? AND PERIODO_ID = ? " +
										" AND FECHA_INICIO >= TO_DATE(?, 'DD/MM/YYYY')" +
										" AND FECHA_INICIO <= TO_DATE(?, 'DD/MM/YYYY')" ;
			Object[] parametros = new Object[] {codigoPersonal, periodoId, fechaIni, fechaFin};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentAlumnoDao|existeFechaINIBetween|:"+ex);
		}
		return ok;
	}
	
	public boolean existeFechaFINBetween( String codigoPersonal, String periodoId, String fechaIni, String fechaFin) {
		boolean ok = false;		
		try{
			String comando = 	"SELECT COUNT(*) FROM MENT_ALUMNO " +
										" WHERE CODIGO_PERSONAL = ? AND PERIODO_ID = ?  " +
										" AND FECHA_FINAL >= TO_DATE(?, 'DD/MM/YYYY')" +
										" AND FECHA_FINAL <= TO_DATE(?, 'DD/MM/YYYY')" ;
			Object[] parametros = new Object[] {codigoPersonal, periodoId, fechaIni, fechaFin};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.spring.MentAlumnoDao|existeFechaFINBetween|:"+ex);
		}
		return ok;
	}
	
	public String maximoRegFolio( String periodoId, String codigoPersonal) {
 		String maximo = "1"; 		
 		try{
 			String comando = "SELECT COALESCE(MAX(FOLIO)+1,1) AS MAXIMO FROM ENOC.MENT_ALUMNO"
 					+ " WHERE PERIODO_ID = ? AND CODIGO_PERSONAL = ?";
 			Object[] parametros = new Object[] {periodoId, codigoPersonal};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
 				maximo = enocJdbc.queryForObject(comando, String.class, parametros);
			} 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.mentores.spring.MentAlumnoDao|maximoReg|:"+ex);
 		}
 		return maximo;
 	}
	
	
	public String getNumAlumnosMentor( String periodoId, String mentorId) {
 		String cantidad	= "0"; 		
 		try{
 			String comando = "SELECT COUNT(*) AS CANTIDAD FROM ENOC.MENT_ALUMNO"
 					+ " WHERE MENTOR_ID  = ?"
 					+ " AND PERIODO_ID = ?";
 			Object[] parametros = new Object[] {periodoId, mentorId};
			cantidad = enocJdbc.queryForObject(comando,String.class,parametros);			
 		}catch(Exception ex){
 			System.out.println("Error - aca.mentores.spring.MentAlumnoDao|getNumAlumnosMentor|:"+ex);
 		}
 		return cantidad;
 	}
	
	public List<MentAlumno> getListAll( String orden ) {
		
		List<MentAlumno> lista = new ArrayList<MentAlumno>();		
		try{
			String comando = "SELECT PERIODO_ID, MENTOR_ID, CODIGO_PERSONAL," +
					" TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, STATUS, CARRERA_ID, TO_CHAR(FECHA_INICIO, 'DD/MM/YYYY') AS FECHA_INICIO, TO_CHAR(FECHA_FINAL, 'DD/MM/YYYY') AS FECHA_FINAL, FOLIO, CARGA_ID" +
					" FROM ENOC.MENT_ALUMNO "+ orden;
			lista = enocJdbc.query(comando, new MentAlumnoMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentAlumnoDao|getListAll|:"+ex);
		}
		return lista;
	}
	
	public List<MentAlumno> getListMentorPeriodo( String mentorId, String periodoId, String fecha, String orden ) {
		
		List<MentAlumno> lista = new ArrayList<MentAlumno>();		
		try{
			String comando = "SELECT PERIODO_ID, MENTOR_ID, CODIGO_PERSONAL,"
					+ " TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, STATUS, CARRERA_ID, TO_CHAR(FECHA_INICIO, 'DD/MM/YYYY') AS FECHA_INICIO, TO_CHAR(FECHA_FINAL, 'DD/MM/YYYY') AS FECHA_FINAL, FOLIO, CARGA_ID"
					+ " FROM ENOC.MENT_ALUMNO"
					+ " WHERE MENTOR_ID = ?"					
					+ " AND TO_DATE(?,'DD/MM/YYYY') BETWEEN FECHA_INICIO AND FECHA_FINAL"
					+ " AND PERIODO_ID = ? " +orden;
			lista = enocJdbc.query(comando, new MentAlumnoMapper(), mentorId, fecha, periodoId);			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentAlumnoDao|getListMentorPeriodo|:"+ex);
		}
		return lista;
	}
	
	public List<MentAlumno> getListActivos( String mentorId, String periodoId, String orden ) {
		
		List<MentAlumno> lista = new ArrayList<MentAlumno>();		
		try{
			String comando = "SELECT PERIODO_ID, MENTOR_ID, CODIGO_PERSONAL," +
					" TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, STATUS, CARRERA_ID, TO_CHAR(FECHA_INICIO, 'DD/MM/YYYY') AS FECHA_INICIO, TO_CHAR(FECHA_FINAL, 'DD/MM/YYYY') AS FECHA_FINAL, FOLIO, CARGA_ID" +
					" FROM ENOC.MENT_ALUMNO" + 
					" WHERE MENTOR_ID = ?" +
					" AND PERIODO_ID = ?" +
					" AND STATUS = 'A' AND now() BETWEEN FECHA_INICIO AND FECHA_FINAL " + orden;
			lista = enocJdbc.query(comando, new MentAlumnoMapper(), mentorId, periodoId);			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentAlumnoDao|getListActivos|:"+ex);
		}
		return lista;
	}
	
	public List<MentAlumno> getListActivosEnFecha(String mentorId, String periodoId, String fecha, String orden ){		
		List<MentAlumno> lista		= new ArrayList<MentAlumno>();				
		try{
			String comando = "SELECT PERIODO_ID, MENTOR_ID, CODIGO_PERSONAL," +
					" TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, STATUS, CARRERA_ID, TO_CHAR(FECHA_INICIO, 'DD/MM/YYYY') AS FECHA_INICIO, TO_CHAR(FECHA_FINAL, 'DD/MM/YYYY') AS FECHA_FINAL, FOLIO, CARGA_ID " +
					" FROM ENOC.MENT_ALUMNO" + 
					" WHERE MENTOR_ID = ?" +
					" AND PERIODO_ID = ?" +
					" AND TO_DATE(?,'DD/MM/YYYY') BETWEEN ENOC.MENT_ALUMNO.FECHA_INICIO AND ENOC.MENT_ALUMNO.FECHA_FINAL" +
					" AND STATUS = 'A' " + orden;
			Object[] parametros = new Object[] {mentorId, periodoId, fecha};
			lista = enocJdbc.query(comando, new MentAlumnoMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentAlumnoDao|getListActivosEnFecha|:"+ex);
		}		
		return lista;
	}
	
	public List<MentAlumno> getListMentPeriodo( String periodoId, String orden ) {
			
			List<MentAlumno> lista = new ArrayList<MentAlumno>();			
			try{
				String comando = "SELECT PERIODO_ID, MENTOR_ID, CODIGO_PERSONAL," +
						" TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, STATUS, CARRERA_ID, TO_CHAR(FECHA_INICIO, 'DD/MM/YYYY') AS FECHA_INICIO, TO_CHAR(FECHA_FINAL, 'DD/MM/YYYY') AS FECHA_FINAL, FOLIO, CARGA_ID" +
						" FROM ENOC.MENT_ALUMNO" + 
						" WHERE PERIODO_ID = ?" +
						" AND STATUS = 'A' "+ orden;
				lista = enocJdbc.query(comando, new MentAlumnoMapper(), periodoId);
			}catch(Exception ex){
				System.out.println("Error - aca.mentores.spring.MentAlumnoDao|getListMentPeriodo|:"+ex);
			}
			return lista;
		}
	
	public List<MentAlumno> getListAlumMentor( String periodoId, String mentorId, String facultadId, String orden ) {
		
		List<MentAlumno> lista = new ArrayList<MentAlumno>();		
		try{
			String comando = "SELECT PERIODO_ID, MENTOR_ID, CODIGO_PERSONAL," +
					" TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, STATUS, CARRERA_ID, TO_CHAR(FECHA_INICIO, 'DD/MM/YYYY') AS FECHA_INICIO, TO_CHAR(FECHA_FINAL, 'DD/MM/YYYY') AS FECHA_FINAL, FOLIO, CARGA_ID" +
					" FROM ENOC.MENT_ALUMNO" + 
					" WHERE PERIODO_ID = ?" +
					" AND MENTOR_ID = ?" +
					" AND ENOC.FACULTAD(CARRERA_ID) = ?" +
					" AND STATUS = 'A' "+ orden;
			lista = enocJdbc.query(comando, new MentAlumnoMapper(), periodoId, mentorId, facultadId);			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentAlumnoDao|getListMentPeriodo|:"+ex);
		}
		return lista;
	}
	
	public List<MentAlumno> getListAlumMentor( String periodoId, String mentorId, String orden ) {
		
		List<MentAlumno> lista = new ArrayList<MentAlumno>();		
		try{
			String comando = " SELECT PERIODO_ID, MENTOR_ID, CODIGO_PERSONAL, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA,"
					+ " STATUS, CARRERA_ID, TO_CHAR(FECHA_INICIO, 'DD/MM/YYYY') AS FECHA_INICIO,"
					+ " TO_CHAR(FECHA_FINAL, 'DD/MM/YYYY') AS FECHA_FINAL, FOLIO, CARGA_ID"
					+ " FROM ENOC.MENT_ALUMNO"
					+ " WHERE PERIODO_ID = ?"
					+ " AND MENTOR_ID = ?"
					+ " AND STATUS = 'A' "+ orden;
			lista = enocJdbc.query(comando, new MentAlumnoMapper(), periodoId, mentorId);			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentAlumnoDao|getListMentPeriodo|:"+ex);
		}
		return lista;
	}
	
	// Lista de alumnos aconsejados en un periodo y fecha
	public List<MentAlumno> listAlumMentor( String periodoId, String fecha, String orden ) {
		
		List<MentAlumno> lista = new ArrayList<MentAlumno>();		
		try{
			String comando = " SELECT PERIODO_ID, MENTOR_ID, CODIGO_PERSONAL, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, STATUS, CARRERA_ID,"
					+ " TO_CHAR(FECHA_INICIO, 'DD/MM/YYYY') AS FECHA_INICIO, TO_CHAR(FECHA_FINAL, 'DD/MM/YYYY') AS FECHA_FINAL, FOLIO, CARGA_ID"
					+ " FROM ENOC.MENT_ALUMNO"
					+ " WHERE PERIODO_ID = ?"
					+ " AND STATUS = 'A'"
					+ " AND TO_DATE(?,'DD/MM/YYYY') BETWEEN FECHA_INICIO AND FECHA_FINAL "+ orden;
			lista = enocJdbc.query(comando, new MentAlumnoMapper(), periodoId, fecha);
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentAlumnoDao|listAlumMentor|:"+ex);
		}
		return lista;
	}
	
	public List<MentAlumno> listAlumMentorVigente( String periodoId, String mentorId, String facultadId, String fecha, String orden ) {
		
		List<MentAlumno> lista = new ArrayList<MentAlumno>();		
		try{
			String comando = "SELECT PERIODO_ID, MENTOR_ID, CODIGO_PERSONAL," +
					" TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, STATUS, CARRERA_ID, TO_CHAR(FECHA_INICIO, 'DD/MM/YYYY') AS FECHA_INICIO, TO_CHAR(FECHA_FINAL, 'DD/MM/YYYY') AS FECHA_FINAL, FOLIO, CARGA_ID" +
					" FROM ENOC.MENT_ALUMNO" + 
					" WHERE PERIODO_ID = ?" +
					" AND MENTOR_ID = ?" +
					" AND TO_DATE(?,'DD/MM/YYYY') BETWEEN FECHA_INICIO AND FECHA_FINAL" +
					" AND ENOC.FACULTAD(CARRERA_ID) = ?" +
					" AND STATUS = 'A' "+ orden;
			lista = enocJdbc.query(comando, new MentAlumnoMapper(), periodoId, mentorId, fecha, facultadId);			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentAlumnoDao|getListMentPeriodo|:"+ex);
		}
		return lista;
	}
	
	public List<MentAlumno> getListAlumCoordinador( String periodoId, String codigoId, String orden ) {
		
		List<MentAlumno> lista = new ArrayList<MentAlumno>();		
		try{
			String comando = "	SELECT PERIODO_ID, MENTOR_ID, CODIGO_PERSONAL,TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA,"
					+ " STATUS, CARRERA_ID, TO_CHAR(FECHA_INICIO, 'DD/MM/YYYY') AS FECHA_INICIO, TO_CHAR(FECHA_FINAL, 'DD/MM/YYYY') AS FECHA_FINAL, FOLIO, CARGA_ID"
					+ " FROM ENOC.MENT_ALUMNO"
					+ " WHERE PERIODO_ID = ?"
					+ " AND STATUS = 'A'"
					+ " AND CARRERA_ID"
					+ " IN (SELECT CARRERA_ID"
					+ " FROM ENOC.CAT_CARRERA"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND CAT_CARRERA.CARRERA_ID = ENOC.MENT_ALUMNO.CARRERA_ID) " + orden;
			lista = enocJdbc.query(comando, new MentAlumnoMapper(), periodoId, codigoId);			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentAlumnoDao|getListMentorPeriodo|:"+ex);
		}
		return lista;
	}
	
	public List<MentAlumno> getListAlumFac( String periodoId, String codigoPersonal, String orden ) {
		
		List<MentAlumno> lista = new ArrayList<MentAlumno>();		
		try{
			String comando = " SELECT PERIODO_ID, MENTOR_ID, CODIGO_PERSONAL,TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA," +
					" STATUS, CARRERA_ID, TO_CHAR(FECHA_INICIO, 'DD/MM/YYYY') AS FECHA_INICIO, TO_CHAR(FECHA_FINAL, 'DD/MM/YYYY') AS FECHA_FINAL, FOLIO, CARGA_ID" +
					" FROM ENOC.MENT_ALUMNO" + 
					" WHERE PERIODO_ID = ?" +
					" AND STATUS = 'A' "+ 
					" AND CARRERA_ID " +
					"   IN (SELECT CARRERA_ID FROM ENOC.CAT_CARRERA WHERE FACULTAD_ID IN (SELECT FACULTAD_ID FROM ENOC.CAT_FACULTAD WHERE CODIGO_PERSONAL = ?)) "+ orden;
			lista = enocJdbc.query(comando, new MentAlumnoMapper(), periodoId, codigoPersonal);			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentAlumnoDao|getListAlumTodos|:"+ex);
		}
		return lista;
	}
	
	public List<MentAlumno> getListAlumTodos( String periodoId, String orden ) {
		
		List<MentAlumno> lista = new ArrayList<MentAlumno>();		
		try{
			String comando = " SELECT PERIODO_ID, MENTOR_ID, CODIGO_PERSONAL,TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA," +
				" STATUS, CARRERA_ID, TO_CHAR(FECHA_INICIO, 'DD/MM/YYYY') AS FECHA_INICIO, TO_CHAR(FECHA_FINAL, 'DD/MM/YYYY') AS FECHA_FINAL, FOLIO, CARGA_ID" +
				" FROM ENOC.MENT_ALUMNO" + 
				" WHERE PERIODO_ID = ?" +
				" AND STATUS = 'A' "+ orden;
			lista = enocJdbc.query(comando, new MentAlumnoMapper(), periodoId);			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentAlumnoDao|getListAlumTodos|:"+ex);
		}
		return lista;
	}
	
	public List<String> getListAconsejados( String periodoId, String orden ) {
		
		List<String> lista = new ArrayList<String>();		
		try{
			String comando = " SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL FROM ENOC.MENT_ALUMNO WHERE PERIODO_ID = ? " + orden;
			lista = enocJdbc.queryForList(comando, String.class, periodoId);			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentAlumnoDao|getListAconsejados|:"+ex);
		}
		return lista;
	}
	
	public List<MentAlumno> getListMentores( String periodoId, String orden) {
		
		List<MentAlumno> lista = new ArrayList<MentAlumno>();		
		try{
			String comando = " SELECT PERIODO_ID, MENTOR_ID, CODIGO_PERSONAL,TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, STATUS, CARRERA_ID,"
					+ " TO_CHAR(FECHA_INICIO, 'DD/MM/YYYY') AS FECHA_INICIO, TO_CHAR(FECHA_FINAL, 'DD/MM/YYYY') AS FECHA_FINAL, FOLIO, CARGA_ID"
					+ " FROM ENOC.MENT_ALUMNO"
					+ " WHERE PERIODO_ID = ? "+orden;
			lista = enocJdbc.query(comando, new MentAlumnoMapper(), periodoId);			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentAlumnoDao|getListMentores|:"+ex);
		}
		return lista;
	}
	
	public HashMap<String,MentAlumno> getMapMentorAlumno( String periodoId ) {
		
		HashMap<String,MentAlumno> mapa = new HashMap<String,MentAlumno>();
		List<MentAlumno> lista 		= new ArrayList<MentAlumno>();		
		try{
			String comando = "SELECT PERIODO_ID, MENTOR_ID, CODIGO_PERSONAL,TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, STATUS, CARRERA_ID, TO_CHAR(FECHA_INICIO, 'DD/MM/YYYY') AS FECHA_INICIO, TO_CHAR(FECHA_FINAL, 'DD/MM/YYYY') AS FECHA_FINAL, FOLIO, CARGA_ID"+
					" FROM ENOC.MENT_ALUMNO WHERE PERIODO_ID = ? AND STATUS = 'A'";
			lista = enocJdbc.query(comando,new MentAlumnoMapper(), periodoId);
			for(MentAlumno objeto : lista){				
				mapa.put(objeto.getCodigoPersonal(), objeto);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.MentAlumnoDao|getMapMentorAlumno|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,String> getMapMentor( String periodoId ) {
		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista 		= new ArrayList<aca.Mapa>();		
		try{
			String comando = " SELECT MENTOR_ID, CODIGO_PERSONAL FROM ENOC.MENT_ALUMNO "
					+ " WHERE PERIODO_ID = ?"
					+ " AND STATUS = 'A'";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), periodoId);
			for (aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.MentAlumnoDao|getMapMentor|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> getAlumPorMent( String periodo) {
		
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa>	lista 		= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT MENTOR_ID||ENOC.FACULTAD(CARRERA_ID) AS LLAVE, COALESCE(COUNT(CODIGO_PERSONAL),0) AS VALOR"
					+ " FROM ENOC.MENT_ALUMNO WHERE PERIODO_ID = ? GROUP BY MENTOR_ID, ENOC.FACULTAD(CARRERA_ID)";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), periodo);
			for (aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.MentAlumnoDao|getAlumPorMent|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaAlumPorMentEnCarrera( String periodo) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa>	lista 		= new ArrayList<aca.Mapa>();	
		try{
			String comando = "SELECT MENTOR_ID||CARRERA_ID AS LLAVE, COALESCE(COUNT(CODIGO_PERSONAL),0) AS VALOR"
				+ " FROM ENOC.MENT_ALUMNO"
				+ " WHERE PERIODO_ID = ?"
				+ " AND SYSDATE BETWEEN FECHA_INICIO AND FECHA_FINAL"
				+ " GROUP BY MENTOR_ID, CARRERA_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), periodo);
			for (aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.MentAlumnoDao|getAlumPorMent|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaAlumPorMentActivos( String periodo) {		
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa>	lista 		= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT MENTOR_ID||ENOC.FACULTAD(CARRERA_ID) AS LLAVE, COALESCE(COUNT(CODIGO_PERSONAL),0) AS VALOR"
				+ " FROM ENOC.MENT_ALUMNO"
				+ " WHERE PERIODO_ID = ?"
				+ " AND SYSDATE BETWEEN FECHA_INICIO AND FECHA_FINAL"
				+ " GROUP BY MENTOR_ID, ENOC.FACULTAD(CARRERA_ID)";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), periodo);
			for (aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.MentAlumnoDao|getAlumPorMent|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> getAlumPorMentL( String periodoId) {
		
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista		 = new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT MENTOR_ID AS LLAVE, COUNT(DISTINCT(CODIGO_PERSONAL)) AS VALOR FROM ENOC.MENT_ALUMNO"
					+ " WHERE MENTOR_ID IN (SELECT DISTINCT(MENTOR_ID) FROM ENOC.MENT_CARRERA WHERE PERIODO_ID = ?)"
					+ " AND PERIODO_ID = ?"
					+ " GROUP BY MENTOR_ID";
			Object[] parametros = new Object[] {periodoId, periodoId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.MentAlumnoDao|getAlumPorMentL|:"+ex);
		}
		return mapa;
	}
	
	// Cuenta el numero de aconsejados de un mentor en una fecha determinada
	public HashMap<String, String> getAlumPorMent( String periodoId, String fecha) {
		
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista		 = new ArrayList<aca.Mapa>();		
		try{
			String comando = " SELECT MENTOR_ID||ENOC.FACULTAD(CARRERA_ID) AS LLAVE, COALESCE(COUNT(CODIGO_PERSONAL),0) AS VALOR FROM ENOC.MENT_ALUMNO"
					+ " WHERE PERIODO_ID = ?"
					+ " AND TO_DATE(?,'DD/MM/YYYY') BETWEEN FECHA_INICIO AND FECHA_FINAL GROUP BY MENTOR_ID, ENOC.FACULTAD(CARRERA_ID)";
			Object[] parametros = new Object[] {periodoId, fecha};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.MentAlumnoDao|getAlumPorMent|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,MentAlumno> mapMentorAlumnoVigente( String periodoId ) {
		
		HashMap<String,MentAlumno> mapa = new HashMap<String,MentAlumno>();
		List<MentAlumno> lista 			= new ArrayList<MentAlumno>();		
		try{
			String comando = " SELECT PERIODO_ID, MENTOR_ID, CODIGO_PERSONAL,TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, STATUS, CARRERA_ID, TO_CHAR(FECHA_INICIO, 'DD/MM/YYYY') AS FECHA_INICIO, TO_CHAR(FECHA_FINAL, 'DD/MM/YYYY') AS FECHA_FINAL, FOLIO, CARGA_ID"
					+ " FROM ENOC.MENT_ALUMNO"
					+ " WHERE PERIODO_ID = ?"
					+ " AND TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN FECHA_INICIO AND FECHA_FINAL"
					+ " AND STATUS = 'A'";
			lista = enocJdbc.query(comando,new MentAlumnoMapper(), periodoId);
			for(MentAlumno objeto : lista){				
				mapa.put(objeto.getCodigoPersonal(), objeto);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.MentAlumnoDao|mapMentorAlumnoVigente|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,String> mapMentorAlumno( String periodoId ) {
		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, MENTOR_ID AS VALOR FROM ENOC.MENT_ALUMNO WHERE PERIODO_ID IN("+periodoId+") AND STATUS = 'A'";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.MentAlumnoDao|mapMentorAlumno|:"+ex);
		}
		return mapa;
	}	
	
	public HashMap<String,MentAlumno> mapaMentores( String cargas ) {		
		HashMap<String,MentAlumno> mapa = new HashMap<String,MentAlumno>();
		List<MentAlumno> lista 			= new ArrayList<MentAlumno>();		
		try{
			String comando = "SELECT PERIODO_ID, MENTOR_ID, CODIGO_PERSONAL, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, STATUS, CARRERA_ID, TO_CHAR(FECHA_INICIO, 'DD/MM/YYYY') AS FECHA_INICIO, TO_CHAR(FECHA_FINAL, 'DD/MM/YYYY') AS FECHA_FINAL, FOLIO, CARGA_ID"
					+ " FROM ENOC.MENT_ALUMNO"
					+ " WHERE PERIODO_ID IN (SELECT PERIODO FROM ENOC.CARGA WHERE CARGA_ID IN ("+cargas+"))"
					+ " AND STATUS = 'A'"
					+ " ORDER BY FOLIO DESC";
			lista = enocJdbc.query(comando,new MentAlumnoMapper());
			for(MentAlumno objeto : lista){				
				mapa.put(objeto.getCodigoPersonal(), objeto);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.MentAlumnoDao|mapaMentores|:"+ex);
		}
		return mapa;
	}
}