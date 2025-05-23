/**
 * 
 */
package aca.mentores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author etorres
 *
 */
public class MentContactoUtil {
	
	public boolean insertReg(Connection conn, MentContacto contacto) throws Exception{
		boolean ok = true;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.MENT_CONTACTO" + 
					" (PERIODO_ID, MENTOR_ID, CONTACTO_ID, CODIGO_PERSONAL," +
					" MOTIVO_ID, MI_ACONSEJADO, FECHA, FECHA_CONTACTO,"+
					" COMENTARIO, TIPO, CARRERA_ID, MOTIVOS)" +
					" VALUES( ?, ?, TO_NUMBER(?, '99999'), ?,"+
					" TO_NUMBER(?, '99'), ?, TO_DATE(?, 'DD/MM/YYYY'), TO_DATE(?, 'DD/MM/YYYY')," +
					" ?, ?, ?, ?)");
			
			ps.setString(1, contacto.getPeriodoId());
			ps.setString(2, contacto.getMentorId());
			ps.setString(3, contacto.getContactoId());
			ps.setString(4, contacto.getCodigoPersonal());
			ps.setString(5, contacto.getMotivoId());
			ps.setString(6, contacto.getMiAconsejado());
			ps.setString(7, contacto.getFecha());
			ps.setString(8, contacto.getFechaContacto());
			ps.setString(9, contacto.getComentario());
			ps.setString(10,contacto.getTipo());
			ps.setString(11, contacto.getCarreraId());
			ps.setString(12, contacto.getMotivos());
		
			if(ps.executeUpdate() == 1)
				ok = true;
			else
				ok = false;
		}catch (Exception ex){
			System.out.println("Error - aca.mentores.MentContacto|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn, MentContacto contacto) throws Exception{
		boolean ok = true;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.MENT_CONTACTO"
					+ " SET CODIGO_PERSONAL = ?,"
					+ " MOTIVO_ID = TO_NUMBER(?, '99'),"
					+ " MI_ACONSEJADO = ?,"
					+ " FECHA = TO_DATE(?, 'DD/MM/YYYY'),"
					+ " FECHA_CONTACTO = TO_DATE(?, 'DD/MM/YYYY'),"
					+ " COMENTARIO = ?,"
					+ " TIPO = ?,"
					+ " CARRERA_ID = ?,"
					+ " MOTIVOS = ?"
					+ " WHERE PERIODO_ID = ?"
					+ " AND MENTOR_ID = ?"
					+ " AND CONTACTO_ID = TO_NUMBER(?, '99999')");
			
			ps.setString(1, contacto.getCodigoPersonal());
			ps.setString(2, contacto.getMotivoId());
			ps.setString(3, contacto.getMiAconsejado());
			ps.setString(4, contacto.getFecha());
			ps.setString(5, contacto.getFechaContacto());
			ps.setString(6, contacto.getComentario());
			ps.setString(7,contacto.getTipo());
			ps.setString(8, contacto.getCarreraId());
			ps.setString(9, contacto.getMotivos());
			ps.setString(10, contacto.getPeriodoId());
			ps.setString(11, contacto.getMentorId());
			ps.setString(12, contacto.getContactoId());
			
			if(ps.executeUpdate()==1)
				ok = true;
			else
				ok = false;
		}catch (Exception ex){
			System.out.println("Error - aca.mentores.MentContacto|updateReg|:"+ex);
		}finally{
			ps.close();
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String periodoId, String mentorId, String contactoId)throws Exception{
		boolean ok = true;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.MENT_CONTACTO"+ 
				" WHERE PERIODO_ID = ?" +
				" AND MENTOR_ID = ?" +
				" AND CONTACTO_ID = TO_NUMBER(?, '99999')");
			
			ps.setString(1, periodoId);
			ps.setString(2, mentorId);
			ps.setString(3, contactoId);
			
			if(ps.executeUpdate()==1)
				ok = true;
			else 
				ok = false;
		}catch (Exception ex){
			System.out.println("Error - aca.mentores.MentorContacto|deleteReg|:"+ex);
		}finally{
			ps.close();
		}
		return ok;
	}
	
	public boolean existeReg(Connection conn, String periodoId, String mentorId, String contactoId ) throws SQLException{
		boolean 		ok 		= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.MENT_CONTACTO" + 
					" WHERE PERIODO_ID = ?" +
					" AND MENTOR_ID = ?" +
					" AND CONTACTO_ID = TO_NUMBER(?, '99999')");
			
			ps.setString(1, periodoId);
			ps.setString(2, mentorId);
			ps.setString(3, contactoId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentContacto|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean existeReg(Connection conn, String periodoId, String mentorId) throws SQLException{
		boolean 		ok 		= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.MENT_CONTACTO" + 
					" WHERE PERIODO_ID = ?" +
					" AND MENTOR_ID = ?");
			
			ps.setString(1, periodoId);
			ps.setString(2, mentorId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentContacto|existeReg2|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String maximoReg(Connection conn, String periodoId, String mentorId ) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String maximo			= "0";
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(MAX(CONTACTO_ID)+1,1) AS MAXIMO" +
					" FROM ENOC.MENT_CONTACTO" + 
					" WHERE PERIODO_ID = ?" +
					" AND MENTOR_ID = ?");
			
			ps.setString(1, periodoId);
			ps.setString(2, mentorId);
			
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");	
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentContacto|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public static String getAlumCarr(Connection conn, String codigo) throws SQLException{
		
		Statement st	= conn.createStatement();	
		ResultSet rs	= null;
		String comando	= "";
		String carrera	= "00000";			
		
		try{
			comando = "SELECT ENOC.ALUM_CARRERA_ID(CODIGO_PERSONAL) FROM ENOC.ALUM_PERSONAL " + 
			"WHERE CODIGO_PERSONAL = '"+codigo+"' ";
							
			rs = st.executeQuery(comando);
			if (rs.next())
				carrera = rs.getString(1);		
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentContacto|getAlumCarr|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return carrera;
	}
	
	
	public static String getNumEntrevistasMentorCarrera(Connection conn, String mentorId, String carreraId, String periodoId) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String cantidad			= "0";
		
		try{
			ps = conn.prepareStatement("SELECT COUNT(CODIGO_PERSONAL) AS CANTIDAD FROM ENOC.MENT_CONTACTO" + 
					" WHERE PERIODO_ID = ?" +
					" AND MENTOR_ID = ?" +
					" AND CARRERA_ID = ?");
			
			ps.setString(1, periodoId);
			ps.setString(2, mentorId);
			ps.setString(3, carreraId);
			
			rs = ps.executeQuery();
			if (rs.next())
				cantidad = rs.getString("CANTIDAD");	
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentContacto|getNumEntrevistasMentorCarrera|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return cantidad;
	}
	
	public static String getNumEntrevistasCarrera(Connection conn, String carreraId, String periodoId, String fecha) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String cantidad			= "0";
		
		try{
			ps = conn.prepareStatement("SELECT COUNT(CODIGO_PERSONAL) AS CANTIDAD FROM ENOC.MENT_CONTACTO"
					+ " WHERE PERIODO_ID = ?"
					+ " AND MENTOR_ID IN (SELECT MENTOR_ID FROM ENOC.MENT_CARRERA"
					+ "		WHERE PERIODO_ID = ?"
					+ " 	AND CARRERA_ID = ?)");
			
			ps.setString(1, periodoId);
			ps.setString(2, periodoId);
			ps.setString(3, carreraId);
			
			rs = ps.executeQuery();
			if (rs.next())
				cantidad = rs.getString("CANTIDAD");	
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentContacto|getNumEntrevistasCarrera|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return cantidad;
	}

	public ArrayList<MentContacto> getListAll(Connection conn, String orden ) throws SQLException{		
		
		ArrayList<MentContacto> lisMentContacto	= new ArrayList<MentContacto>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando = "SELECT PERIODO_ID, MENTOR_ID, CONTACTO_ID," +
				" CODIGO_PERSONAL, MOTIVO_ID, MI_ACONSEJADO, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA," +
				" TO_CHAR(FECHA_CONTACTO, 'DD/MM/YYYY') AS FECHA_CONTACTO, COMENTARIO, TIPO, CARRERA_ID, MOTIVOS" +
				" FROM ENOC.MENT_CONTACTO "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				MentContacto mentCont = new MentContacto();
				mentCont.mapeaReg(rs);
				lisMentContacto.add(mentCont);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentContUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisMentContacto;
	}
	
	public ArrayList<MentContacto> getHistorial(Connection conn, String mentorId, String orden ) throws SQLException{
		
		ArrayList<MentContacto> lisMenContacto	= new ArrayList<MentContacto>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT PERIODO_ID, MENTOR_ID, CONTACTO_ID," +
				" CODIGO_PERSONAL, MOTIVO_ID, MI_ACONSEJADO, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA," +
				" TO_CHAR(FECHA_CONTACTO, 'DD/MM/YYYY') AS FECHA_CONTACTO, COMENTARIO, TIPO, CARRERA_ID, MOTIVOS" +
				" FROM ENOC.MENT_CONTACTO WHERE MENTOR_ID = '"+mentorId+"' "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				MentContacto mentor = new MentContacto();
				mentor.mapeaReg(rs);
				lisMenContacto.add(mentor);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentContUtil|getHistorial|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisMenContacto;
	}
	
	// Lista de entrevistas de un mentor en el periodo
	public ArrayList<MentContacto> getListAlumnosEnt(Connection conn, String mentorId, String periodoId, String orden ) throws SQLException{
		
		ArrayList<MentContacto> lisMenContacto	= new ArrayList<MentContacto>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = " SELECT PERIODO_ID, MENTOR_ID, CONTACTO_ID, CODIGO_PERSONAL, MOTIVO_ID, MI_ACONSEJADO,"
					+ " TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA,"
					+ " TO_CHAR(FECHA_CONTACTO, 'DD/MM/YYYY') AS FECHA_CONTACTO,"
					+ " COMENTARIO, TIPO, CARRERA_ID, MOTIVOS"
					+ " FROM ENOC.MENT_CONTACTO"
					+ " WHERE MENTOR_ID = '"+mentorId+"'"
					+ " AND PERIODO_ID = '"+periodoId+"' " + orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				MentContacto alumno = new MentContacto();
				alumno.mapeaReg(rs);
				lisMenContacto.add(alumno);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentContactoUtil|getListAlumnosEnt|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisMenContacto;
	}
	
	public ArrayList<MentContacto> getListAlumnosEnt(Connection conn, String mentorId, String periodoId, String fecha, String orden ) throws SQLException{
		
		ArrayList<MentContacto> lisMenContacto	= new ArrayList<MentContacto>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = " SELECT PERIODO_ID, MENTOR_ID, CONTACTO_ID,"
					+ " CODIGO_PERSONAL, MOTIVO_ID, MI_ACONSEJADO, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA,"
					+ " TO_CHAR(FECHA_CONTACTO, 'DD/MM/YYYY') AS FECHA_CONTACTO, COMENTARIO, TIPO, CARRERA_ID, MOTIVOS"
					+ " FROM ENOC.MENT_CONTACTO"
					+ " WHERE MENTOR_ID = '"+mentorId+"'"
					+ " AND PERIODO_ID = '"+periodoId+"' " + orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				MentContacto alumno = new MentContacto();
				alumno.mapeaReg(rs);
				lisMenContacto.add(alumno);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentContactoUtil|getListAlumnosEnt|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisMenContacto;
	}
	
	public ArrayList<MentContacto> getListEntrevistas(Connection conn, String mentorId, String codigoPersonal, String periodoId, String orden ) throws SQLException{
		
		ArrayList<MentContacto> lisMenContacto	= new ArrayList<MentContacto>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT PERIODO_ID, MENTOR_ID, CONTACTO_ID," +
					" CODIGO_PERSONAL, MOTIVO_ID, MI_ACONSEJADO, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA," +
					" TO_CHAR(FECHA_CONTACTO, 'DD/MM/YYYY') AS FECHA_CONTACTO, COMENTARIO, TIPO, CARRERA_ID, MOTIVOS" +
					" FROM ENOC.MENT_CONTACTO" + 
					" WHERE MENTOR_ID = '"+mentorId+"' " +
					" AND CODIGO_PERSONAL = '"+codigoPersonal+"'" +
					" AND FECHA_CONTACTO BETWEEN (SELECT F_INICIO FROM ENOC.CAT_PERIODO WHERE PERIODO_ID = '"+periodoId+"')" + 
					" AND (SELECT F_FINAL FROM ENOC.CAT_PERIODO WHERE PERIODO_ID = '"+periodoId+"') "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				MentContacto alumno = new MentContacto();
				alumno.mapeaReg(rs);
				lisMenContacto.add(alumno);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentContactoUtil|getListEntrevistas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisMenContacto;
	}
	
	public ArrayList<MentContacto> getListEntCarrera(Connection conn, String mentorId, String carreraId, String periodoId, String orden ) throws SQLException{
		
		ArrayList<MentContacto> lisMenContacto	= new ArrayList<MentContacto>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT PERIODO_ID, MENTOR_ID, CONTACTO_ID," +
					" CODIGO_PERSONAL, MOTIVO_ID, MI_ACONSEJADO, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA," +
					" TO_CHAR(FECHA_CONTACTO, 'DD/MM/YYYY') AS FECHA_CONTACTO, COMENTARIO, TIPO, CARRERA_ID, MOTIVOS" +
					" FROM ENOC.MENT_CONTACTO" + 
					" WHERE MENTOR_ID = '"+mentorId+"' " +
					" AND CARRERA_ID = '"+carreraId+"'" +
					" AND FECHA_CONTACTO BETWEEN (SELECT F_INICIO FROM ENOC.CAT_PERIODO WHERE PERIODO_ID = '"+periodoId+"')" + 
					" AND (SELECT F_FINAL FROM ENOC.CAT_PERIODO WHERE PERIODO_ID = '"+periodoId+"') "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				MentContacto alumno = new MentContacto();
				alumno.mapeaReg(rs);
				lisMenContacto.add(alumno);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentContactoUtil|getListEntCarrera|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisMenContacto;
	}
	
	public ArrayList<MentContacto> getLista(Connection conn, String idMentor, String orden ) throws SQLException{
		
		ArrayList<MentContacto> lisMenContacto	= new ArrayList<MentContacto>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT PERIODO_ID, MENTOR_ID, CONTACTO_ID," +
					" CODIGO_PERSONAL, MOTIVO_ID, MI_ACONSEJADO, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA," +
					" TO_CHAR(FECHA_CONTACTO, 'DD/MM/YYYY') AS FECHA_CONTACTO, COMENTARIO, TIPO, CARRERA_ID, MOTIVOS" +
					" FROM ENOC.MENT_CONTACTO WHERE MENTOR_ID = '"+idMentor+"'" + 
					" AND PERIODO_ID IN "
					+ "		(SELECT PERIODO_ID FROM ENOC.CAT_PERIODO WHERE TO_DATE(now(),'DD/MM/YYYY')"
					+ " 	BETWEEN TO_DATE(F_INICIO, 'DD/MM/YYYY')"
					+ "		AND TO_DATE(F_FINAL, 'DD/MM/YYYY')) "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				MentContacto mentor = new MentContacto();
				mentor.mapeaReg(rs);
				lisMenContacto.add(mentor);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentContactoUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisMenContacto;
	}
	
	public ArrayList<MentContacto> getListInscrito(Connection conn, String mentorId, String orden ) throws SQLException{
		
		ArrayList<MentContacto> lisMenContacto	= new ArrayList<MentContacto>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";		
		
		try{
			comando = "SELECT CODIGO_PERSONAL, PERIODO_ID, MENTOR_ID, CONTACTO_ID," +
					" MOTIVO_ID, MI_ACONSEJADO," +
					" TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA," +
					" TO_CHAR(FECHA_CONTACTO, 'DD/MM/YYYY') AS FECHA_CONTACTO," +
					" COMENTARIO, TIPO, CARRERA_ID, MOTIVOS" +
					" FROM ENOC.MENT_CONTACTO" + 
					" WHERE  MENTOR_ID = '"+mentorId+"'" +
					" AND PERIODO_ID IN (SELECT PERIODO_ID FROM ENOC.CAT_PERIODO" + 
					" WHERE TO_DATE(now(), 'DD/MM/YYYY') BETWEEN" +
					" TO_DATE(F_INICIO, 'DD/MM/YYYY') AND TO_DATE(F_FINAL, 'DD/MM/YYYY'))" +
					" AND CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.INSCRITOS)" + orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
			
					MentContacto alumno = new MentContacto();
					alumno.mapeaReg(rs);
					lisMenContacto.add(alumno);
				
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentContactoUtil|getListInscrito|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisMenContacto;
	}
	
	
	public ArrayList<MentContacto> listaEntrevistasPeriodo(Connection conn, String mentorId, String periodo, String orden ) throws SQLException{
		
		ArrayList<MentContacto> lisMenContacto	= new ArrayList<MentContacto>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";		
		
		try{
			comando = "SELECT CODIGO_PERSONAL, PERIODO_ID, MENTOR_ID, CONTACTO_ID," +
					" MOTIVO_ID, MI_ACONSEJADO," +
					" TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA," +
					" TO_CHAR(FECHA_CONTACTO, 'DD/MM/YYYY') AS FECHA_CONTACTO," +
					" COMENTARIO, TIPO, CARRERA_ID, MOTIVOS" +
					" FROM ENOC.MENT_CONTACTO" + 
					" WHERE  MENTOR_ID = '"+mentorId+"'" +
					" AND PERIODO_ID ='"+periodo+"' " + orden;
			 
			rs = st.executeQuery(comando);
			while (rs.next()){
			
					MentContacto alumno = new MentContacto();
					alumno.mapeaReg(rs);
					lisMenContacto.add(alumno);
				
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentContactoUtil|listaEntrevistasPeriodo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisMenContacto;
	}
	
	public ArrayList<MentContacto> getListInscrito(Connection conn, String mentorId, String fecha, String orden ) throws SQLException{
		
		ArrayList<MentContacto> lisMenContacto	= new ArrayList<MentContacto>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";		
		
		try{
			comando = "SELECT CODIGO_PERSONAL, PERIODO_ID, MENTOR_ID, CONTACTO_ID," +
					" MOTIVO_ID, MI_ACONSEJADO," +
					" TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA," +
					" TO_CHAR(FECHA_CONTACTO, 'DD/MM/YYYY') AS FECHA_CONTACTO," +
					" COMENTARIO, TIPO, CARRERA_ID, MOTIVOS" +
					" FROM ENOC.MENT_CONTACTO" + 
					" WHERE  MENTOR_ID = '"+mentorId+"'" +
					" AND PERIODO_ID IN (SELECT PERIODO_ID FROM ENOC.CAT_PERIODO" + 
					" WHERE TO_DATE(now(), 'DD/MM/YYYY') BETWEEN" +
					" TO_DATE(F_INICIO, 'DD/MM/YYYY') AND TO_DATE(F_FINAL, 'DD/MM/YYYY'))" +
					" AND CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.INSCRITOS)" + 
					" AND FECHA_CONTACTO = '"+fecha+"'" + 
					orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
			
					MentContacto alumno = new MentContacto();
					alumno.mapeaReg(rs);
					lisMenContacto.add(alumno);
				
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentContactoUtil|getListInscrito|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisMenContacto;
	}
	
	public HashMap<String,String> mapEntrevistas(Connection conn, String periodoId, String fecha) throws SQLException{
		
		HashMap<String,String> mapEntrevista = new HashMap<String,String>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = "SELECT MENTOR_ID, COUNT(CONTACTO_ID) AS TOTAL "
					+ " FROM ENOC.MENT_CONTACTO "
					+ " WHERE PERIODO_ID = '"+periodoId+"'"					
					+ " GROUP BY MENTOR_ID";
			rs = st.executeQuery(comando);
			while (rs.next()){				
				mapEntrevista.put(rs.getString("MENTOR_ID"), rs.getString("TOTAL") );
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentContactoUtil|mapEntrevistas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return mapEntrevista;
	}
	
	public HashMap<String,String> mapEntrevistasCarrera(Connection conn, String periodoId) throws SQLException{
		
		HashMap<String,String> mapEntrevista = new HashMap<String,String>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = "SELECT MENTOR_ID, CARRERA_ID, COALESCE(COUNT(CONTACTO_ID),0) AS TOTAL FROM ENOC.MENT_CONTACTO WHERE PERIODO_ID = '"+periodoId+"' GROUP BY MENTOR_ID, CARRERA_ID";
			rs = st.executeQuery(comando);
			while (rs.next()){				
				mapEntrevista.put(rs.getString("MENTOR_ID")+rs.getString("CARRERA_ID"), rs.getString("TOTAL") );
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentContactoUtil|mapEntrevistasCarrera|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return mapEntrevista;
	}
	
	public HashMap<String,String> mapEntrevistasPeriodo(Connection conn, String periodoId) throws SQLException{
		
		HashMap<String,String> mapEntrevista = new HashMap<String,String>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = "SELECT MENTOR_ID, COALESCE(COUNT(CONTACTO_ID),0) AS TOTAL FROM ENOC.MENT_CONTACTO WHERE PERIODO_ID = '"+periodoId+"' GROUP BY MENTOR_ID";
			rs = st.executeQuery(comando);
			while (rs.next()){				
				mapEntrevista.put(rs.getString("MENTOR_ID"), rs.getString("TOTAL") );
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentContactoUtil|mapEntrevistasPeriodo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return mapEntrevista;
	}
	
	public HashMap<String,String> mapEntrevistasAlumno(Connection conn, String fechaIni, String fechaFin) throws SQLException{
		
		HashMap<String,String> mapEntrevista = new HashMap<String,String>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = " SELECT CODIGO_PERSONAL, COUNT(*) AS TOTAL"
					+ " FROM ENOC.MENT_CONTACTO "
					+ " WHERE FECHA_CONTACTO BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFin+"','DD/MM/YYYY') "
					+ " GROUP BY CODIGO_PERSONAL";
			rs = st.executeQuery(comando);
			while (rs.next()){				
				mapEntrevista.put(rs.getString("CODIGO_PERSONAL"), rs.getString("TOTAL") );
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentContactoUtil|mapEntrevistasAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return mapEntrevista;
	}
	
	public HashMap<String,String> mapEntDelAlumnoPorMentor(Connection conn, String fechaIni, String fechaFin) throws SQLException{
		
		HashMap<String,String> mapEntrevista = new HashMap<String,String>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = " SELECT CODIGO_PERSONAL, MENTOR_ID, COUNT(*) AS TOTAL"
					+ " FROM ENOC.MENT_CONTACTO "
					+ " WHERE FECHA_CONTACTO BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFin+"','DD/MM/YYYY') "
					+ " GROUP BY CODIGO_PERSONAL, MENTOR_ID";
			rs = st.executeQuery(comando);
			while (rs.next()){				
				mapEntrevista.put(rs.getString("CODIGO_PERSONAL")+rs.getString("MENTOR_ID"), rs.getString("TOTAL") );
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentContactoUtil|mapEntDelAlumnoPorMentor|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return mapEntrevista;
	}

}