/**
 * 
 */
package aca.mentores.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * @author Elifo
 *
 */
@Component
public class MentContactoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(MentContacto contacto){
		boolean ok = true;
		
		try{
			String comando = "INSERT INTO ENOC.MENT_CONTACTO" + 
					" (PERIODO_ID, MENTOR_ID, CONTACTO_ID, CODIGO_PERSONAL," +
					" MOTIVO_ID, MI_ACONSEJADO, FECHA, FECHA_CONTACTO,"+
					" COMENTARIO, TIPO, CARRERA_ID, MOTIVOS)" +
					" VALUES( ?, ?, TO_NUMBER(?, '99999'), ?,"+
					" TO_NUMBER(?, '99'), ?, TO_DATE(?, 'DD/MM/YYYY'), TO_DATE(?, 'DD/MM/YYYY')," +
					" ?, ?, ?, ?)";
			Object[] parametros = new Object[] {
					contacto.getPeriodoId(),contacto.getMentorId(),contacto.getContactoId(),contacto.getCodigoPersonal(),
					contacto.getMotivoId(), contacto.getMiAconsejado(), contacto.getFecha(), contacto.getFechaContacto(), contacto.getComentario(),
					contacto.getTipo(), contacto.getCarreraId(), contacto.getMotivos()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	

		}catch (Exception ex){
			System.out.println("Error - aca.mentores.MentContacto|insertReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean updateReg(MentContacto contacto){
		boolean ok = true;

		try{
			String comando = "UPDATE ENOC.MENT_CONTACTO"
					+ " SET CODIGO_PERSONAL = ?,"
					+ " MOTIVO_ID = TO_NUMBER(?, '99'),"
					+ " MI_ACONSEJADO = ?,"
					+ " FECHA = TO_DATE(?, 'DD/MM/YYYY'),"
					+ " FECHA_CONTACTO = TO_DATE(?, 'DD/MM/YYYY'),"
					+ " COMENTARIO = ?,"
					+ " TIPO = ?,"
					+ " CARRERA_ID = ?, "
					+ " MOTIVOS = ?"
					+ " WHERE PERIODO_ID = ?"					
					+ " AND MENTOR_ID = ?"
					+ " AND CONTACTO_ID = TO_NUMBER(?, '99999')";
			Object[] parametros = new Object[] {
				contacto.getCodigoPersonal(),contacto.getMotivoId(),contacto.getMiAconsejado(),contacto.getFecha(),
				contacto.getFechaContacto(), contacto.getComentario(), contacto.getTipo(), contacto.getCarreraId(),
				contacto.getMotivos(), contacto.getPeriodoId(),contacto.getMentorId(), contacto.getContactoId()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
	
		}catch (Exception ex){
			System.out.println("Error - aca.mentores.MentContacto|updateReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean deleteReg(String periodoId, String mentorId, String contactoId){
		boolean ok = true;
		
		try{
			String comando = "DELETE FROM ENOC.MENT_CONTACTO"+ 
				" WHERE PERIODO_ID = ?" +
				" AND MENTOR_ID = ?" +
				" AND CONTACTO_ID = TO_NUMBER(?, '99999')";
			Object[] parametros = new Object[] {periodoId, mentorId, contactoId};
			if (enocJdbc.update(comando, parametros)==1){
				ok = true;
			}
			
		}catch (Exception ex){
			System.out.println("Error - aca.mentores.MentorContacto|deleteReg|:"+ex);	
		}
		
		return ok;
	}
	
	public MentContacto mapeaRegId(String periodoId, String mentorId, String contactoId){
		
		MentContacto  contacto = new MentContacto();
		
		try{ 
			String comando = "SELECT PERIODO_ID, MENTOR_ID, CONTACTO_ID," +
					" CODIGO_PERSONAL, MOTIVO_ID, MI_ACONSEJADO, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA," +
					" TO_CHAR(FECHA_CONTACTO, 'DD/MM/YYYY') AS FECHA_CONTACTO, COMENTARIO, TIPO, CARRERA_ID, MOTIVOS" +
					" FROM ENOC.MENT_CONTACTO" + 
					" WHERE PERIODO_ID = ?" +
					" AND MENTOR_ID = ?" +
					" AND CONTACTO_ID = TO_NUMBER(?, '99999')";
			Object[] parametros = new Object[] {periodoId, mentorId, contactoId};
			contacto = enocJdbc.queryForObject(comando, new MentContactoMapper(), parametros);
				
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentContacto|mapeaRegId|:"+ex);
		}
		
		return contacto;
	}
	
	public boolean existeReg(String periodoId, String mentorId, String contactoId){
		boolean 		ok 		= false;
		
		try{
			String comando = "SELECT COUNT (*) FROM ENOC.MENT_CONTACTO" + 
					" WHERE PERIODO_ID = ?" +
					" AND MENTOR_ID = ?" +
					" AND CONTACTO_ID = TO_NUMBER(?, '99999')";
			Object[] parametros = new Object[] {periodoId, mentorId, contactoId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentContacto|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean existeReg( String periodoId, String mentorId){
		boolean 		ok 		= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.MENT_CONTACTO" + 
					" WHERE PERIODO_ID = ?" +
					" AND MENTOR_ID = ?";
			Object[] parametros = new Object[] {periodoId, mentorId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentContacto|existeReg2|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoReg( String periodoId, String mentorId ){
		
		String maximo			= "0";
		
		try{
			String comando = "SELECT COALESCE(MAX(CONTACTO_ID)+1,1) AS MAXIMO" +
					" FROM ENOC.MENT_CONTACTO" + 
					" WHERE PERIODO_ID = ?" +
					" AND MENTOR_ID = ?";
			Object[] parametros = new Object[] {periodoId, mentorId};
 			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
 				maximo = enocJdbc.queryForObject(comando,String.class, parametros);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentContacto|maximoReg|:"+ex);
		}
		
		return maximo;
	}
	
	public String getNumEntrevistasCarrera( String carreraId, String periodoId, String fecha){
		
		String cantidad			= "0";
		
		try{
			String comando = "SELECT COUNT(CODIGO_PERSONAL) AS CANTIDAD FROM ENOC.MENT_CONTACTO"
					+ " WHERE PERIODO_ID = ?"
					+ " AND MENTOR_ID IN (SELECT MENTOR_ID FROM ENOC.MENT_CARRERA"
					+ "		WHERE PERIODO_ID = ?"
					+ " 	AND CARRERA_ID = ?)";
			Object[] parametros = new Object[] {carreraId, periodoId, fecha};
 			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
 				cantidad = enocJdbc.queryForObject(comando,String.class, parametros);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentContacto|getNumEntrevistasCarrera|:"+ex);
		}
		
		return cantidad;
	}
	
	public String getNumEntrevistasMentorCarrera( String mentorId, String carreraId, String periodoId){
		
		String cantidad			= "0";
		
		try{
			String comando = "SELECT COUNT(CODIGO_PERSONAL) AS CANTIDAD FROM ENOC.MENT_CONTACTO" + 
					" WHERE PERIODO_ID = ?" +
					" AND MENTOR_ID = ?" +
					" AND CARRERA_ID = ?";
			Object[] parametros = new Object[] {mentorId, carreraId, periodoId};
 			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
 				cantidad = enocJdbc.queryForObject(comando,String.class, parametros);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentContacto|getNumEntrevistasMentorCarrera|:"+ex);
		}
		
		return cantidad;
	}
	
	public String getAlumCarr( String codigo){		
		String carrera	= "00000";		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ALUM_PLAN WHERE CODIGO_PERSONAL = ?  AND ESTADO = '1'";
			Object[] parametros = new Object[] {codigo};
 			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
 				comando = "SELECT ENOC.ALUM_CARRERA_ID(CODIGO_PERSONAL) FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL = ?";
 				carrera = enocJdbc.queryForObject(comando,String.class, parametros);
			}							
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentContacto|getAlumCarr|:"+ex);
		}		
		return carrera;
	}
	
	public List<MentContacto> getListAll( String orden ){
		
		List<MentContacto> lista	= new ArrayList<MentContacto>();		
		try{
			String comando = "SELECT PERIODO_ID, MENTOR_ID, CONTACTO_ID," +
				" CODIGO_PERSONAL, MOTIVO_ID, MI_ACONSEJADO, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA," +
				" TO_CHAR(FECHA_CONTACTO, 'DD/MM/YYYY') AS FECHA_CONTACTO, COMENTARIO, TIPO, CARRERA_ID, MOTIVOS" +
				" FROM ENOC.MENT_CONTACTO "+ orden; 
			lista = enocJdbc.query(comando, new MentContactoMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentContUtil|getListAll|:"+ex);
		}		
		return lista;
	}
	
	public List<MentContacto> getHistorial( String mentorId, String orden ){
		
		List<MentContacto> lista	= new ArrayList<MentContacto>();		
		try{
			String comando = "SELECT PERIODO_ID, MENTOR_ID, CONTACTO_ID," +
				" CODIGO_PERSONAL, MOTIVO_ID, MI_ACONSEJADO, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA," +
				" TO_CHAR(FECHA_CONTACTO, 'DD/MM/YYYY') AS FECHA_CONTACTO, COMENTARIO, TIPO, CARRERA_ID, MOTIVOS" +
				" FROM ENOC.MENT_CONTACTO WHERE MENTOR_ID = ? "+ orden; 
			lista = enocJdbc.query(comando, new MentContactoMapper(), mentorId);			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentContUtil|getHistorial|:"+ex);
		}		
		return lista;
	}
	
	// Lista de entrevistas de un mentor en el periodo
	public List<MentContacto> getListAlumnosEnt( String mentorId, String periodoId, String orden ){
		
		List<MentContacto> lista	= new ArrayList<MentContacto>();
		
		try{
			String comando = " SELECT PERIODO_ID, MENTOR_ID, CONTACTO_ID, CODIGO_PERSONAL, MOTIVO_ID, MI_ACONSEJADO,"
					+ " TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA,"
					+ " TO_CHAR(FECHA_CONTACTO, 'DD/MM/YYYY') AS FECHA_CONTACTO,"
					+ " COMENTARIO, TIPO, CARRERA_ID, MOTIVOS"
					+ " FROM ENOC.MENT_CONTACTO"
					+ " WHERE MENTOR_ID = ?"
					+ " AND PERIODO_ID = ? " + orden; 
			lista = enocJdbc.query(comando, new MentContactoMapper(), mentorId, periodoId);			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentContactoDao|getListAlumnosEnt|:"+ex);
		}
		
		return lista;
	}
	
	// Lista de entrevistas de un mentor en el periodo
	public List<MentContacto> lisEntrevistasAlumno( String mentorId, String codigoAlumno, String periodoId, String orden ){
		
		List<MentContacto> lista	= new ArrayList<MentContacto>();
		
		try{
			String comando = " SELECT PERIODO_ID, MENTOR_ID, CONTACTO_ID, CODIGO_PERSONAL, MOTIVO_ID, MI_ACONSEJADO,"
					+ " TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA,"
					+ " TO_CHAR(FECHA_CONTACTO, 'DD/MM/YYYY') AS FECHA_CONTACTO,"
					+ " COMENTARIO, TIPO, CARRERA_ID, MOTIVOS"
					+ " FROM ENOC.MENT_CONTACTO"
					+ " WHERE MENTOR_ID = ?"
					+ " AND CODIGO_PERSONAL = ?"					
					+ " AND PERIODO_ID = ?" + orden;
			Object[] parametros = new Object[] { mentorId, codigoAlumno, periodoId};
			lista = enocJdbc.query(comando, new MentContactoMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentContactoDao|getListAlumnosEnt|:"+ex);
		}
		
		return lista;
	}
		
	
	public List<MentContacto> getListAlumnosEnt( String mentorId, String periodoId, String fecha, String orden ){
		
		List<MentContacto> lista	= new ArrayList<MentContacto>();
		
		try{
			String comando = " SELECT PERIODO_ID, MENTOR_ID, CONTACTO_ID,"
					+ " CODIGO_PERSONAL, MOTIVO_ID, MI_ACONSEJADO, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA,"
					+ " TO_CHAR(FECHA_CONTACTO, 'DD/MM/YYYY') AS FECHA_CONTACTO, COMENTARIO, TIPO, CARRERA_ID, MOTIVOS"
					+ " FROM ENOC.MENT_CONTACTO"
					+ " WHERE MENTOR_ID = ?"
					+ " AND PERIODO_ID = ? " + orden; 
			lista = enocJdbc.query(comando, new MentContactoMapper(), mentorId, periodoId);		
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentContactoDao|getListAlumnosEnt|:"+ex);
		}
		
		return lista;
	}
	
	public List<MentContacto> getListEntrevistas( String mentorId, String codigoPersonal, String periodoId, String orden ){
		
		List<MentContacto> lista	= new ArrayList<MentContacto>();	
		try{
			String comando = "SELECT PERIODO_ID, MENTOR_ID, CONTACTO_ID," +
					" CODIGO_PERSONAL, MOTIVO_ID, MI_ACONSEJADO, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA," +
					" TO_CHAR(FECHA_CONTACTO, 'DD/MM/YYYY') AS FECHA_CONTACTO, COMENTARIO, TIPO, CARRERA_ID, MOTIVOS" +
					" FROM ENOC.MENT_CONTACTO" + 
					" WHERE MENTOR_ID = ? " +
					" AND CODIGO_PERSONAL = ?" +
					" AND FECHA_CONTACTO BETWEEN (SELECT F_INICIO FROM ENOC.CAT_PERIODO WHERE PERIODO_ID = ?)" + 
					" AND (SELECT F_FINAL FROM ENOC.CAT_PERIODO WHERE PERIODO_ID = ?) "+ orden; 
			lista = enocJdbc.query(comando, new MentContactoMapper(), mentorId, codigoPersonal, periodoId, periodoId );		
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentContactoDao|getListEntrevistas|:"+ex);
		}
		
		return lista;
	}
	
	public List<MentContacto> getListEntCarrera( String mentorId, String carreraId, String periodoId, String orden ){
		
		List<MentContacto> lista	= new ArrayList<MentContacto>();		
		try{
			String comando = "SELECT PERIODO_ID, MENTOR_ID, CONTACTO_ID," +
					" CODIGO_PERSONAL, MOTIVO_ID, MI_ACONSEJADO, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA," +
					" TO_CHAR(FECHA_CONTACTO, 'DD/MM/YYYY') AS FECHA_CONTACTO, COMENTARIO, TIPO, CARRERA_ID, MOTIVOS" +
					" FROM ENOC.MENT_CONTACTO" + 
					" WHERE MENTOR_ID = ?" +
					" AND CARRERA_ID = ?" +
					" AND FECHA_CONTACTO BETWEEN (SELECT F_INICIO FROM ENOC.CAT_PERIODO WHERE PERIODO_ID = ?)" + 
					" AND (SELECT F_FINAL FROM ENOC.CAT_PERIODO WHERE PERIODO_ID = ?) "+ orden; 
			lista = enocJdbc.query(comando, new MentContactoMapper(), mentorId, carreraId, periodoId, periodoId);			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentContactoDao|getListEntCarrera|:"+ex);
		}		
		return lista;
	}
	
	public List<MentContacto> getLista( String idMentor, String orden ){
		
		List<MentContacto> lista	= new ArrayList<MentContacto>();
		
		try{
			String comando = "SELECT PERIODO_ID, MENTOR_ID, CONTACTO_ID," +
					" CODIGO_PERSONAL, MOTIVO_ID, MI_ACONSEJADO, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA," +
					" TO_CHAR(FECHA_CONTACTO, 'DD/MM/YYYY') AS FECHA_CONTACTO, COMENTARIO, TIPO, CARRERA_ID, MOTIVOS" +
					" FROM ENOC.MENT_CONTACTO WHERE MENTOR_ID = ?" + 
					" AND PERIODO_ID IN (SELECT PERIODO_ID FROM ENOC.CAT_PERIODO WHERE TO_DATE(now(),'DD/MM/YYYY')" + 
					" BETWEEN TO_DATE(F_INICIO, 'DD/MM/YYYY')" +
					" AND TO_DATE(F_FINAL, 'DD/MM/YYYY'))"+ orden;
			lista = enocJdbc.query(comando, new MentContactoMapper(), idMentor);
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentContactoDao|getLista|:"+ex);
		}
		
		return lista;
	}
	
	public List<MentContacto> lisContacto(String idMentor, String matricula, String orden){
		
		List<MentContacto> lista	= new ArrayList<MentContacto>();		
		try{
			String comando = "SELECT PERIODO_ID, MENTOR_ID, CONTACTO_ID, CODIGO_PERSONAL, MOTIVO_ID, MI_ACONSEJADO, "
					+ " TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA,"
					+ " TO_CHAR(FECHA_CONTACTO, 'DD/MM/YYYY') AS FECHA_CONTACTO, COMENTARIO, TIPO, CARRERA_ID, MOTIVOS"
					+ " FROM MENTOR_CONTACTO"
					+ " WHERE MENTOR_ID = ?"
					+ " AND MATRICULA = ? "+orden;			
			Object[] parametros = new Object[] {idMentor, matricula};
			lista = enocJdbc.query(comando, new MentContactoMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentContactoDao|lisContacto|:"+ex);
		}
		
		return lista;
	}
	
	public List<MentContacto> getListInscrito( String mentorId, String orden ){
		
		List<MentContacto> lista	= new ArrayList<MentContacto>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, PERIODO_ID, MENTOR_ID, CONTACTO_ID," +
					" MOTIVO_ID, MI_ACONSEJADO," +
					" TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA," +
					" TO_CHAR(FECHA_CONTACTO, 'DD/MM/YYYY') AS FECHA_CONTACTO," +
					" COMENTARIO, TIPO, CARRERA_ID, MOTIVOS" +
					" FROM ENOC.MENT_CONTACTO" + 
					" WHERE  MENTOR_ID = ?" +
					" AND PERIODO_ID IN (SELECT PERIODO_ID FROM ENOC.CAT_PERIODO" + 
					" WHERE TO_DATE(now(), 'DD/MM/YYYY') BETWEEN" +
					" TO_DATE(F_INICIO, 'DD/MM/YYYY') AND TO_DATE(F_FINAL, 'DD/MM/YYYY'))" +
					" AND CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.INSCRITOS)" + orden;
			lista = enocJdbc.query(comando, new MentContactoMapper(), mentorId);
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentContactoDao|getListInscrito|:"+ex);
		}
		
		return lista;
	}
	
	public List<MentContacto> listaEntrevistasPeriodo( String periodo, String orden ){
		
		List<MentContacto> lista	= new ArrayList<MentContacto>();
		try{
			String comando = "SELECT CODIGO_PERSONAL, PERIODO_ID, MENTOR_ID, CONTACTO_ID,"
				+ " MOTIVO_ID, MI_ACONSEJADO, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA,"
				+ " TO_CHAR(FECHA_CONTACTO, 'DD/MM/YYYY') AS FECHA_CONTACTO,"
				+ " COMENTARIO, TIPO, CARRERA_ID, MOTIVOS"
				+ " FROM ENOC.MENT_CONTACTO" 
				+ " WHERE PERIODO_ID = ? " + orden;
			Object[] parametros = new Object[] { periodo };
			lista = enocJdbc.query(comando, new MentContactoMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentContactoDao|listaEntrevistasPeriodo|:"+ex);
		}		
		return lista;
	}

	public List<MentContacto> listaEntrevistasPeriodo( String mentorId, String periodo, String orden ){
		
		List<MentContacto> lista	= new ArrayList<MentContacto>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, PERIODO_ID, MENTOR_ID, CONTACTO_ID," +
					" MOTIVO_ID, MI_ACONSEJADO," +
					" TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA," +
					" TO_CHAR(FECHA_CONTACTO, 'DD/MM/YYYY') AS FECHA_CONTACTO," +
					" COMENTARIO, TIPO, CARRERA_ID, MOTIVOS" +
					" FROM ENOC.MENT_CONTACTO" + 
					" WHERE  MENTOR_ID = ?" +
					" AND PERIODO_ID = ?" + orden;
			Object[] parametros = new Object[] { mentorId, periodo };
			lista = enocJdbc.query(comando, new MentContactoMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentContactoDao|listaEntrevistasPeriodo|:"+ex);
		}		
		return lista;
	}
	
	public List<MentContacto> getListInscrito( String mentorId, String fecha, String orden ){
		
		List<MentContacto> lista	= new ArrayList<MentContacto>();	
		try{
			String comando = "SELECT CODIGO_PERSONAL, PERIODO_ID, MENTOR_ID, CONTACTO_ID," +
					" MOTIVO_ID, MI_ACONSEJADO," +
					" TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA," +
					" TO_CHAR(FECHA_CONTACTO, 'DD/MM/YYYY') AS FECHA_CONTACTO," +
					" COMENTARIO, TIPO, CARRERA_ID, MOTIVOS" +
					" FROM ENOC.MENT_CONTACTO" + 
					" WHERE  MENTOR_ID = ?" +
					" AND PERIODO_ID IN (SELECT PERIODO_ID FROM ENOC.CAT_PERIODO" + 
					" WHERE TO_DATE(now(), 'DD/MM/YYYY') BETWEEN" +
					" TO_DATE(F_INICIO, 'DD/MM/YYYY') AND TO_DATE(F_FINAL, 'DD/MM/YYYY'))" +
					" AND CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.INSCRITOS)" + 
					" AND FECHA_CONTACTO = ?" + orden;
			lista = enocJdbc.query(comando, new MentContactoMapper(), mentorId, fecha);			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentContactoDao|getListInscrito|:"+ex);
		}		
		return lista;
	}
	
	public HashMap<String,String> mapEntrevistas( String periodoId, String fecha){
		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT MENTOR_ID AS LLAVE, COUNT(CONTACTO_ID) AS VALOR"
					+ " FROM ENOC.MENT_CONTACTO "
					+ " WHERE PERIODO_ID = ?"					
					+ " GROUP BY MENTOR_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), periodoId);			
			for (aca.Mapa map: lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentContactoDao|mapEntrevistas|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapEntrevistasCarrera( String periodoId){
		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT MENTOR_ID||CARRERA_ID AS LLAVE, COALESCE(COUNT(CONTACTO_ID),0) AS VALOR FROM ENOC.MENT_CONTACTO WHERE PERIODO_ID = ? GROUP BY MENTOR_ID||CARRERA_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), periodoId);	
			for (aca.Mapa map: lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}				
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentContactoDao|mapEntrevistasCarrera|:"+ex);		
		}		
		return mapa;
	}
	
	public HashMap<String,String> mapEntrevistasPorCarrera( String periodoId){
		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CARRERA_ID AS LLAVE, COALESCE(COUNT(CONTACTO_ID),0) AS VALOR FROM ENOC.MENT_CONTACTO WHERE PERIODO_ID = ? GROUP BY CARRERA_ID";
			Object[] parametros = new Object[] {periodoId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);	
			for (aca.Mapa map: lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentContactoDao|mapEntrevistasCarrera|:"+ex);		
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapEntrevistasPeriodo( String periodoId){
		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT MENTOR_ID AS LLAVE, COALESCE(COUNT(CONTACTO_ID),0) AS VALOR FROM ENOC.MENT_CONTACTO WHERE PERIODO_ID = ? GROUP BY MENTOR_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), periodoId);			
			for (aca.Mapa map: lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentContactoDao|mapEntrevistasPeriodo|:"+ex);
		}		
		return mapa;
	}
	
	public HashMap<String,String> mapEntrevistasMentor( String mentorId, String periodoId){
		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, COUNT(*) AS VALOR  FROM MENT_CONTACTO WHERE MENTOR_ID = ? AND PERIODO_ID = ? GROUP BY CODIGO_PERSONAL";
			Object[] parametros = new Object[] {mentorId, periodoId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);			
			for (aca.Mapa map: lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.spring.MentContactoDao|mapEntrevistasMentor|:"+ex);
		}		
		return mapa;
	}
}