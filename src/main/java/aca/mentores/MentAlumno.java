/**
 * 
 */
package aca.mentores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Elifo
 *
 */
public class MentAlumno {
	private String periodoId;
	private String mentorId;
	private String codigoPersonal;
	private String fecha;
	private String status;
	private String carreraId;
	private String fechaInicio;
	private String fechaFinal;
	private String folio;
	
	public MentAlumno(){
		periodoId			= "";
		mentorId			= "";
		codigoPersonal		= "";
		fecha				= "";
		status				= "";
		carreraId			= "";
		fechaInicio			= "";
		fechaFinal			= "";
		folio				= "";
	}

	/**
	 * @return the codigoPersonal
	 */
	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	/**
	 * @param codigoPersonal the codigoPersonal to set
	 */
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	/**
	 * @return the fecha
	 */
	public String getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return the mentorId
	 */
	public String getMentorId() {
		return mentorId;
	}

	/**
	 * @param mentorId the mentorId to set
	 */
	public void setMentorId(String mentorId) {
		this.mentorId = mentorId;
	}

	/**
	 * @return the periodoId
	 */
	public String getPeriodoId() {
		return periodoId;
	}

	/**
	 * @param periodoId the periodoId to set
	 */
	public void setPeriodoId(String periodoId) {
		this.periodoId = periodoId;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	/**
	 * @return the carreraId
	 */
	public String getCarreraId() {
		return carreraId;
	}

	/**
	 * @param carreraId the carreraId to set
	 */
	public void setCarreraId(String carreraId) {
		this.carreraId = carreraId;
	}

	public String getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getFechaFinal() {
		return fechaFinal;
	}

	public void setFechaFinal(String fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public boolean insertReg(Connection conn) throws Exception{
		boolean ok = false;
		PreparedStatement ps= null;
		try{
			ps= conn.prepareStatement("INSERT INTO ENOC.MENT_ALUMNO" + 
					" (PERIODO_ID, MENTOR_ID, CODIGO_PERSONAL, FECHA, STATUS, CARRERA_ID, FECHA_INICIO, FECHA_FINAL, FOLIO)" +
					" VALUES( ?, ?, ?, TO_DATE(?, 'DD/MM/YYYY'), ?, ?, TO_DATE(?, 'DD/MM/YYYY'), TO_DATE(?, 'DD/MM/YYYY'), TO_NUMBER(COALESCE(?, '1'),'999'))");
			
			ps.setString(1, periodoId);
			ps.setString(2, mentorId);
			ps.setString(3, codigoPersonal);
			ps.setString(4, fecha);
			ps.setString(5, status);
			ps.setString(6, carreraId);
			ps.setString(7, fechaInicio);
			ps.setString(8, fechaFinal);
			ps.setString(9, folio);
			
			if(ps.executeUpdate() == 1)
				ok = true;
			else
				ok = false;
		}catch (Exception ex){
			System.out.println("Error - aca.mentores.MentAlumno|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn) throws Exception{
		boolean ok = true;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.MENT_ALUMNO" + 
					" SET FECHA = TO_DATE(?, 'DD/MM/YYYY')," +
					" STATUS = ?," +
					" CARRERA_ID = ?, FECHA_INICIO = TO_DATE(?, 'DD/MM/YYYY'), FECHA_FINAL = TO_DATE(?, 'DD/MM/YYYY') " +
					" WHERE PERIODO_ID = ?" +
					" AND MENTOR_ID = ?" +
					" AND CODIGO_PERSONAL = ?" +
					" AND FOLIO = TO_NUMBER(COALESCE(?, '1'),'999') ");
			
			ps.setString(1, fecha);
			ps.setString(2, status);
			ps.setString(3, carreraId);
			ps.setString(4, fechaInicio);
			ps.setString(5, fechaFinal);
			ps.setString(6, periodoId);
			ps.setString(7, mentorId);
			ps.setString(8, codigoPersonal);
			ps.setString(9, folio);
			
			if(ps.executeUpdate()==1)
				ok = true;
			else
				ok = false;
		}catch (Exception ex){
			System.out.println("Error - aca.mentores.MentAlumno|updateReg|:"+ex);
		}finally{
			ps.close();
		}
		return ok;
	}
		
	public boolean updateFechas(Connection conn) throws Exception{
		boolean ok = true;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.MENT_ALUMNO" + 
					" SET FECHA_INICIO = TO_DATE(?, 'DD/MM/YYYY'), "+
					" FECHA_FINAL = TO_DATE(?, 'DD/MM/YYYY') " +
					" WHERE PERIODO_ID = ?" +
					" AND MENTOR_ID = ?" +
					" AND CODIGO_PERSONAL = ?" +
					" AND FOLIO = TO_NUMBER(COALESCE(?, '1'),'999') ");
			
			ps.setString(1, fechaInicio);
			ps.setString(2, fechaFinal);
			ps.setString(3, periodoId);
			ps.setString(4, mentorId);
			ps.setString(5, codigoPersonal);
			ps.setString(6, folio);
			
			if(ps.executeUpdate()==1)
				ok = true;
			else
				ok = false;
		}catch (Exception ex){
			System.out.println("Error - aca.mentores.MentAlumno|updateFechas|:"+ex);
		}finally{
			ps.close();
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn)throws Exception{
		boolean ok = true;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.MENT_ALUMNO"+ 
				" WHERE PERIODO_ID = '"+periodoId+"' " +
				" AND MENTOR_ID = '"+mentorId+"' " +
				" AND CODIGO_PERSONAL = '"+codigoPersonal+"' AND FOLIO = TO_NUMBER(COALESCE('"+folio+"', '1'),'999') ");
			
			if(ps.executeUpdate()>0)
				ok = true;
			else 
				ok = false;
		}catch (Exception ex){
			System.out.println("Error - aca.mentores.MentAlumno|deleteReg|:"+ex);
		}finally{
			ps.close();
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs) throws SQLException{
		periodoId			= rs.getString("PERIODO_ID");
		mentorId			= rs.getString("MENTOR_ID");
		codigoPersonal		= rs.getString("CODIGO_PERSONAL");
		fecha				= rs.getString("FECHA");
		status				= rs.getString("STATUS");
		carreraId			= rs.getString("CARRERA_ID");
		fechaInicio			= rs.getString("FECHA_INICIO");
		fechaFinal			= rs.getString("FECHA_FINAL");
		folio				= rs.getString("FOLIO");
	}
	
	public void mapeaRegId(Connection conn, String periodoId, String mentorId, String codigoPersonal, String folio) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{ 
			ps = conn.prepareStatement("SELECT PERIODO_ID, MENTOR_ID," +
					" CODIGO_PERSONAL, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, STATUS, CARRERA_ID, TO_CHAR(FECHA_INICIO, 'DD/MM/YYYY') AS FECHA_INICIO, TO_CHAR(FECHA_FINAL, 'DD/MM/YYYY') AS FECHA_FINAL, FOLIO"+
					" FROM ENOC.MENT_ALUMNO" + 
					" WHERE PERIODO_ID = ?" +
					" AND MENTOR_ID = ?" +
					" AND CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(COALESCE(?, '1'),'999') ");
			
			ps.setString(1, periodoId);
			ps.setString(2, mentorId);
			ps.setString(3, codigoPersonal);
			ps.setString(4, folio);
			
			rs = ps.executeQuery();
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentAlumno|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
	public void mapeaRegAlumnoAsignadoPeriodo(Connection conn, String codigoPersonal, String periodoId) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{ 
			ps = conn.prepareStatement("SELECT PERIODO_ID, MENTOR_ID," +
					" CODIGO_PERSONAL, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, STATUS, CARRERA_ID, FECHA_INICIO, FECHA_FINAL, FOLIO"+
					" FROM ENOC.MENT_ALUMNO" + 
					" WHERE PERIODO_ID = ?" +
					" AND CODIGO_PERSONAL = ?" +
					" AND STATUS = 'A'");
			
			ps.setString(1, periodoId);
			ps.setString(2, codigoPersonal);
			
			rs = ps.executeQuery();
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentAlumno|mapeaRegAlumnoAsignadoPeriodo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
	public boolean existeReg(Connection conn) throws SQLException{
		boolean 		ok 		= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.MENT_ALUMNO" + 
					" WHERE PERIODO_ID = ?" +
					" AND MENTOR_ID = ?" +
					" AND CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(COALESCE(?, '1'),'999')");
		
		ps.setString(1, periodoId);
		ps.setString(2, mentorId);
		ps.setString(3, codigoPersonal);	
		ps.setString(4, folio);	
						
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentAlumno|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static boolean tieneAlumnosPeriodoCarrera(Connection conn, String mentorId, String periodoId, String carreraId) throws SQLException{
		boolean 		ok 		= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.MENT_ALUMNO" + 
					" WHERE PERIODO_ID = ?" +
					" AND MENTOR_ID = ?" +
					" AND CARRERA_ID = ?" +
					" AND STATUS = 'A'");
		
		ps.setString(1, periodoId);
		ps.setString(2, mentorId);
		ps.setString(3, carreraId);
						
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentAlumno|tieneAlumnosPeriodo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static boolean elAlumnoEstaAsignado(Connection conn, String codigoPersonal, String periodoId) throws SQLException{
		boolean 		ok 		= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.MENT_ALUMNO" + 
					" WHERE PERIODO_ID = ?" +
					" AND CODIGO_PERSONAL = ?" +
					" AND STATUS = 'A'");
		
		ps.setString(1, periodoId);
		ps.setString(2, codigoPersonal);
						
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentAlumno|elAlumnoEstaAsignado|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static String getMentorId( Connection conn, String Matricula, String periodoId ) throws SQLException{
		PreparedStatement ps	= null;
		ResultSet rs			= null;
		String idMentor			= "x";
		
		try{
			ps	= conn.prepareStatement("SELECT MENTOR_ID" +
				" FROM ENOC.MENT_ALUMNO" + 
				" WHERE CODIGO_PERSONAL = ?" +
				" AND PERIODO_ID = ?" +
				" AND STATUS = 'A'");
			ps.setString(1, Matricula);
			ps.setString(2, periodoId);
			
			rs = ps.executeQuery();
			if (rs.next()) idMentor = rs.getString(1);	
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentAlumno|getMentorId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return idMentor;
	}
	
	public static String getMentorId( Connection conn, String Matricula, String periodoId, String fecha) throws SQLException{
		PreparedStatement ps	= null;
		ResultSet rs			= null;
		String idMentor			= "x";
		
		try{
			ps	= conn.prepareStatement("SELECT MENTOR_ID FROM ENOC.MENT_ALUMNO"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND PERIODO_ID = ?"
					+ " AND TO_DATE('"+fecha+"','DD/MM/YYYY') BETWEEN FECHA_INICIO AND FECHA_FINAL"
					+ " AND STATUS = 'A'");
			ps.setString(1, Matricula);
			ps.setString(2, periodoId);
			
			rs = ps.executeQuery();
			if (rs.next()) idMentor = rs.getString(1);	
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentAlumno|getMentorId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return idMentor;
	}
	
	public static String getNumAlumnosMentor( Connection conn, String periodoId, String mentorId, String fecha) throws SQLException{
		PreparedStatement ps	= null;
		ResultSet rs			= null;
		String num				= "";
		
		try{
			ps	= conn.prepareStatement("SELECT COUNT(CODIGO_PERSONAL) AS NUMALUM FROM ENOC.MENT_ALUMNO" + 
					" WHERE PERIODO_ID = ?" +
					" AND MENTOR_ID = ? AND STATUS = 'A' " +
					" AND TO_DATE(?, 'DD/MM/YYYY') BETWEEN FECHA_INICIO AND FECHA_FINAL  ");
			ps.setString(1, periodoId);
			ps.setString(2, mentorId);
			ps.setString(3, fecha);
			
			rs = ps.executeQuery();
			if (rs.next())
				num = rs.getString("NUMALUM");	
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentAlumno|getNumAlumnosMentor|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return num;
	}
	
	public static String getMentorActual( Connection conn, String Matricula, String periodoId, String mentorId ) throws SQLException{
		PreparedStatement ps	= null;
		ResultSet rs			= null;
		String idMentor			= "";
		
		try{
			ps	= conn.prepareStatement("SELECT MENTOR_ID" +
				" FROM ENOC.MENT_ALUMNO" + 
				" WHERE CODIGO_PERSONAL = ?" +
				" AND PERIODO_ID = ?" +
				" AND STATUS = 'A'");
			ps.setString(1, Matricula);
			ps.setString(2, periodoId);
			
			rs = ps.executeQuery();
			if (rs.next()) idMentor = rs.getString(1);	
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentAlumno|getMentorId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return idMentor;
	}
	
	public static String getMentorActual( Connection conn, String Matricula, String periodoId) throws SQLException{
		PreparedStatement ps	= null;
		ResultSet rs			= null;
		String idMentor			= "";
		
		try{
			ps	= conn.prepareStatement("SELECT MENTOR_ID" +
				" FROM ENOC.MENT_ALUMNO" + 
				" WHERE CODIGO_PERSONAL = ?" +
				" AND PERIODO_ID = ?" +
				" AND STATUS = 'A'");
			ps.setString(1, Matricula);
			ps.setString(2, periodoId);
			
			rs = ps.executeQuery();
			if (rs.next()) idMentor = rs.getString(1);	
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentAlumno|getMentorId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return idMentor;
	}
	
	
	/* METODOS PARA CHECAR SI CHOCAN LAS FECHAS */ 
	
	public boolean existeFechaBetween(Connection conn, String codigoPersonal, String periodoId, String fecha) throws SQLException{
		boolean ok 				= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement(	"SELECT * FROM MENT_ALUMNO " +
										" WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' AND PERIODO_ID = '"+periodoId+"' " +
										" AND TO_DATE('"+fecha+"', 'DD/MM/YYYY') >= FECHA_INICIO" +
										" AND TO_DATE('"+fecha+"', 'DD/MM/YYYY') <= FECHA_FINAL" ); 
			
			rs= ps.executeQuery();		
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentAlumno|existeFechaBetween|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean existeFechaINIBetween(Connection conn, String codigoPersonal, String periodoId, String fechaIni, String fechaFin) throws SQLException{
		boolean ok 				= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement(	"SELECT * FROM MENT_ALUMNO " +
										" WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' AND PERIODO_ID = '"+periodoId+"' " +
										" AND FECHA_INICIO >= TO_DATE('"+fechaIni+"', 'DD/MM/YYYY')" +
										" AND FECHA_INICIO <= TO_DATE('"+fechaFin+"', 'DD/MM/YYYY')" ); 
			
			rs= ps.executeQuery();		
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentAlumno|existeFechaBetween|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean existeFechaFINBetween(Connection conn, String codigoPersonal, String periodoId, String fechaIni, String fechaFin) throws SQLException{
		boolean ok 				= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement(	"SELECT * FROM MENT_ALUMNO " +
										" WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' AND PERIODO_ID = '"+periodoId+"'  " +
										" AND FECHA_FINAL >= TO_DATE('"+fechaIni+"', 'DD/MM/YYYY')" +
										" AND FECHA_FINAL <= TO_DATE('"+fechaFin+"', 'DD/MM/YYYY')" ); 
			
			rs= ps.executeQuery();		
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentAlumno|existeFechaBetween|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String maximoRegFolio(Connection conn, String periodoId, String codigoPersonal) throws SQLException{
 		String 		maximo 		= "1";
 		ResultSet 		rs		= null;
 		PreparedStatement ps	= null;
 		
 		try{
 			ps = conn.prepareStatement("SELECT MAX( FOLIO )+1 AS MAXIMO FROM ENOC.MENT_ALUMNO"+ 
 				" WHERE PERIODO_ID = ? AND CODIGO_PERSONAL = ? ");
 			
 			ps.setString(1, periodoId);
 			ps.setString(2, codigoPersonal);
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				maximo = rs.getString("MAXIMO");
 			if(maximo == null)
 				maximo = "1";
 		}catch(Exception ex){
 			System.out.println("Error - aca.mentores.MentAlumno|maximoReg|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return maximo;
 	}
	
	
	public static String getNumAlumnosMentor(Connection conn, String periodoId, String mentorId) throws SQLException{
 		String 		cantidad	= "0";
 		ResultSet 		rs		= null;
 		PreparedStatement ps	= null;
 		
 		try{
 			ps = conn.prepareStatement("SELECT COUNT(*) AS CANTIDAD FROM ENOC.MENT_ALUMNO"
 					+ " WHERE MENTOR_ID  = '"+mentorId+"'"
 					+ " AND PERIODO_ID = '"+periodoId+"' ");
 			
 			rs = ps.executeQuery();
 			if (rs.next()){
 				cantidad = rs.getString("CANTIDAD");
 			}
 				
 		}catch(Exception ex){
 			System.out.println("Error - aca.mentores.MentAlumno|getNumAlumnosMentor|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return cantidad;
 	}
	
}