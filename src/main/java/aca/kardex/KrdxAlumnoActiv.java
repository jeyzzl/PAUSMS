// Bean de la tablas KRDX_CURSO_ACT 
package  aca.kardex;
import java.sql.*;

public class KrdxAlumnoActiv{
	private String codigoPersonal;
	private String cursoCargaId;
	private String actividadId;
	private String nota;
	private String actividadE42;
	
	public KrdxAlumnoActiv(){
		codigoPersonal	= "";
		cursoCargaId	= "";
		actividadId		= "";		
		nota			= "";
		actividadE42	= "";
	}

	public String getActividadId() {
		return actividadId;
	}	

	public void setActividadId(String actividadId) {
		this.actividadId = actividadId;
	}	

	public String getCodigoPersonal() {
		return codigoPersonal;
	}	

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}	

	public String getCursoCargaId() {
		return cursoCargaId;
	}	

	public void setCursoCargaId(String cursoCargaId) {
		this.cursoCargaId = cursoCargaId;
	}	

	public String getNota() {
		return nota;
	}	

	public void setNota(String nota) {
		this.nota = nota;
	}
	
	public String getActividadE42() {
		return actividadE42;
	}

	public void setActividadE42(String actividadE42) {
		this.actividadE42 = actividadE42;
	}
	

	public boolean insertReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{

			if (actividadE42==null || actividadE42.equals("")) actividadE42="0";

			ps = conn.prepareStatement("INSERT INTO ENOC.KRDX_ALUMNO_ACTIV"+ 
				"(CODIGO_PERSONAL, CURSO_CARGA_ID, ACTIVIDAD_ID, NOTA, ACTIVIDAD_E42 ) "+
				"VALUES( ?, ?, TO_NUMBER(?,'9999999'), TO_NUMBER(?,'999')," +
				"TO_NUMBER(?,'9999999'))");
			
			ps.setString(1, codigoPersonal);
			ps.setString(2, cursoCargaId);
			ps.setString(3, actividadId);
			ps.setString(4, nota);
			ps.setString(5, actividadE42);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxAlumnoActiv|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			if (actividadE42==null) actividadE42="0";
			if (actividadE42.trim().equals("")) actividadE42="0";
			ps = conn.prepareStatement("UPDATE ENOC.KRDX_ALUMNO_ACTIV "+ 
				"SET NOTA = TO_NUMBER(?,'999'), " +
				"CURSO_CARGA_ID = ?, " +
				"ACTIVIDAD_E42 = TO_NUMBER(?,'9999999') "+
				"WHERE CODIGO_PERSONAL = ? "+				
				"AND ACTIVIDAD_ID = TO_NUMBER(?,'9999999')");
			
			ps.setString(1, nota);
			ps.setString(2, cursoCargaId);
			ps.setString(3, actividadE42);
			ps.setString(4, codigoPersonal);			
			ps.setString(5, actividadId);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxAlumnoActiv|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateRegE42(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.KRDX_ALUMNO_ACTIV "+ 
				"SET NOTA = TO_NUMBER(?,'999'), " +
				"WHERE CODIGO_PERSONAL = ? "+
				"AND ACTIVIDAD_E42 = TO_NUMBER(?,'9999999')");
			ps.setString(1, nota);
			ps.setString(2, codigoPersonal);
			ps.setString(3, actividadE42);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxAlumnoActiv|updateRegE42|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	public boolean deleteReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.KRDX_ALUMNO_ACTIV "+ 
				"WHERE CODIGO_PERSONAL = ? "+				
				"AND ACTIVIDAD_ID = TO_NUMBER(?,'9999999')");
			ps.setString(1, codigoPersonal);			
			ps.setString(2, actividadId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxAlumnoActiv|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}	
	
	public boolean deleteNotasReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.KRDX_ALUMNO_ACTIV "+ 
				"WHERE ACTIVIDAD_ID = TO_NUMBER(?,'9999999') " +
				"AND CURSO_CARGA_ID = ?");
			ps.setString(1, actividadId);			
			ps.setString(2, cursoCargaId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxAlumnoActiv|deleteNotasReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}	
	
	public boolean deleteRegE42(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.KRDX_ALUMNO_ACTIV "+ 
				"WHERE CODIGO_PERSONAL = ? "+				
				"AND ACTIVIDAD_E42 = TO_NUMBER(?,'9999999')");
			ps.setString(1, codigoPersonal);			
			ps.setString(2, actividadE42);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxAlumnoActiv|deleteRegE42|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		codigoPersonal		= rs.getString("CODIGO_PERSONAL");
		cursoCargaId 		= rs.getString("CURSO_CARGA_ID");
		actividadId			= rs.getString("ACTIVIDAD_ID");
		nota	 			= rs.getString("NOTA");
		actividadE42		= rs.getString("ACTIVIDAD_E42");
	}
	
	public void mapeaRegId( Connection conn, String codigoPersonal, String actividadId) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{ 
			ps = conn.prepareStatement("SELECT "+
				"CODIGO_PERSONAL, CURSO_CARGA_ID, ACTIVIDAD_ID, NOTA, ACTIVIDAD_E42 "+
				"FROM ENOC.KRDX_ALUMNO_ACTIV "+ 
				"WHERE CODIGO_PERSONAL = ? "+			
				"AND ACTIVIDAD_ID = TO_NUMBER(?,'9999999')");
			ps.setString(1, codigoPersonal);		
			ps.setString(2, actividadId);		
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxAlumnoActiv|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
	public boolean existeReg(Connection conn) throws SQLException{
		boolean 			ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.KRDX_ALUMNO_ACTIV "+ 
				"WHERE CODIGO_PERSONAL = ? "+
				"AND ACTIVIDAD_ID = TO_NUMBER(?,'9999999')");
			ps.setString(1, codigoPersonal);			
			ps.setString(2, actividadId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxAlumnoActiv|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean existeRegE42(Connection conn) throws SQLException{
		boolean 			ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.KRDX_ALUMNO_ACTIV "+ 
				"WHERE CODIGO_PERSONAL = ? "+
				"AND ACTIVIDAD_E42 = TO_NUMBER(?,'9999999')");
			ps.setString(1, codigoPersonal);			
			ps.setString(2, actividadE42);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxAlumnoActiv|existeRegE42|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static String getActividadId(Connection conn, String codigoPersonal, String actividadE42) throws SQLException{
		ResultSet 			rs		= null;
		PreparedStatement 	ps		= null;
		String actividad			= "";
		
		try{
			ps = conn.prepareStatement("SELECT ACTIVIDAD_ID FROM ENOC.KRDX_ALUMNO_ACTIV "+ 
				"WHERE CODIGO_PERSONAL = ? "+
				"AND ACTIVIDAD_E42 = TO_NUMBER(?,'9999999')");
			ps.setString(1, codigoPersonal);
			ps.setString(2, actividadE42);
			
			rs = ps.executeQuery();
			if (rs.next())
				actividad = rs.getString("ACTIVIDAD_ID");
			else
				actividad = "0";
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxAlumnoActiv|getActividadId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return actividad;
	}
	
	public static String getNotaActividad(Connection conn, String codigoPersonal, String actividadId) throws SQLException{
		ResultSet 			rs		= null;
		PreparedStatement 	ps		= null;
		String nota			= "";
		
		try{
			ps = conn.prepareStatement("SELECT NOTA FROM ENOC.KRDX_ALUMNO_ACTIV "+ 
				"WHERE CODIGO_PERSONAL = ? "+
				"AND ACTIVIDAD_ID = TO_NUMBER(?,'9999999')");
			ps.setString(1, codigoPersonal);
			ps.setString(2, actividadId);
			
			rs = ps.executeQuery();
			if (rs.next())
				nota = rs.getString("NOTA");
			else
				nota = "0";
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxAlumnoActiv|getNotaActividad|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nota;
	}
	
	public static int getNumActividades(Connection conn, String cursoCargaId) throws SQLException{
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		int numAct				= 0;
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(COUNT(NOTA),0) AS NUMACT FROM ENOC.KRDX_ALUMNO_ACTIV WHERE CURSO_CARGA_ID = ?"); 
			ps.setString(1, cursoCargaId);
			
			rs = ps.executeQuery();
			if (rs.next())
				numAct = rs.getInt("NUMACT");
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxAlumnoActiv|getNumActividades|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return numAct;
	}
	
	public static int deleteAlumAct(Connection conn, String cursoCargaId ) throws SQLException{
		
		PreparedStatement ps	= null;
		int numAct				= 0;
		
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.KRDX_ALUMNO_ACTIV WHERE CURSO_CARGA_ID = ?"); 
			ps.setString(1, cursoCargaId);
			numAct = ps.executeUpdate();
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxAlumnoActiv|deleteAlumAct|:"+ex);
		}finally{			
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return numAct;
	}
}