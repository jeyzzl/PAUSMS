/**
 * 
 */
package aca.edo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Elifo
 *
 */
public class EdoAutoResp {
	private String edoId;
	private String preguntaId;
	private String codigoPersonal;
	private String respuesta;
	
	public EdoAutoResp(){
		edoId			= "";
		preguntaId		= "";
		codigoPersonal	= "";
		respuesta		= "";
	}

	/**
	 * @return the edoId
	 */
	public String getEdoId() {
		return edoId;
	}

	/**
	 * @param edoId the edoId to set
	 */
	public void setEdoId(String edoId) {
		this.edoId = edoId;
	}

	/**
	 * @return the preguntaId
	 */
	public String getPreguntaId() {
		return preguntaId;
	}

	/**
	 * @param preguntaId the preguntaId to set
	 */
	public void setPreguntaId(String preguntaId) {
		this.preguntaId = preguntaId;
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
	 * @return the respuesta
	 */
	public String getRespuesta() {
		return respuesta;
	}

	/**
	 * @param respuesta the respuesta to set
	 */
	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}
	
	public boolean insertReg(Connection conn) throws Exception{
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		try{
			ps = conn.prepareStatement("INSERT INTO" +
				" EDO_AUTORESP(EDO_ID, PREGUNTA_ID, CODIGO_PERSONAL, RESPUESTA)" +
				" VALUES(TO_NUMBER(?, '99999'), TO_NUMBER(?, '99'), ?, ?)");
			
			ps.setString(1, edoId);
			ps.setString(2, preguntaId);			
			ps.setString(3, codigoPersonal);
			ps.setString(4, respuesta);
			
			if(ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;			
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoAutoResp|insertReg|:"+ex);			
		}finally{
			if(ps!=null) ps.close();
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.EDO_AUTORESP" + 
				" SET RESPUESTA = ?" +
				" WHERE EDO_ID = TO_NUMBER(?, '99999')" +
				" AND PREGUNTA_ID = TO_NUMBER(?, '99')" +
				" AND CODIGO_PERSONAL = ?");
			
			ps.setString(1, respuesta);
			ps.setString(2, edoId);
			ps.setString(3, preguntaId);			
			ps.setString(4, codigoPersonal);
						
			if(ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoAutoResp|updateReg|:"+ex);		 
		}finally{
			if(ps!=null) ps.close();
		}
		
		return ok;
	}	
	
	public boolean deleteReg(Connection conn) throws Exception{
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.EDO_AUTORESP"+ 
				" WHERE EDO_ID = TO_NUMBER(?, '99999')" +
				" AND PREGUNTA_ID = TO_NUMBER(?, '99')" +
				" AND CODIGO_PERSONAL = ?");
			
			ps.setString(1, respuesta);
			ps.setString(2, edoId);
			ps.setString(3, preguntaId);			
			ps.setString(4, codigoPersonal);
			
			if(ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoAutoResp|deleteReg|:"+ex);			
		}finally{
			if(ps!=null) ps.close();
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		edoId			= rs.getString("EDO_ID");
		preguntaId		= rs.getString("NOMBRE");
		codigoPersonal	= rs.getString("F_INICIO");
		respuesta		= rs.getString("RESPUESTA");
	}
	
	public void mapeaRegId(Connection conn, String edoId, String preguntaId, String codigoPersonal) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{
			ps = conn.prepareStatement("SELECT EDO_ID, PREGUNTA_ID, CODIGO_PERSONAL, RESPUESTA" +
					" FROM ENOC.EDO_AUTORESP" + 
					" WHERE EDO_ID = TO_NUMBER(?, '99999')" +
					" AND PREGUNTA_ID = TO_NUMBER(?, '99')" +
					" AND CODIGO_PERSONAL = ?");
				
				ps.setString(1, edoId);
				ps.setString(2, preguntaId);			
				ps.setString(3, codigoPersonal);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoAutoResp|mapeaRegId|:"+ex);
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
			ps = conn.prepareStatement("SELECT * FROM ENOC.EDO_AUTORESP" + 
					" WHERE EDO_ID = TO_NUMBER(?, '99999')" +
					" AND PREGUNTA_ID = TO_NUMBER(?, '99')" +
					" AND CODIGO_PERSONAL = ?");
			
			ps.setString(1, respuesta);
			ps.setString(2, edoId);
			ps.setString(3, preguntaId);			
			ps.setString(4, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoAutoResp|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		
		return ok;
	}
	
	public static boolean yaContesto(Connection conn, String edoId, String codigoPersonal) throws SQLException{
		boolean 		ok 		= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.EDO_AUTORESP" + 
					" WHERE EDO_ID = TO_NUMBER(?, '99999')" +
					" AND CODIGO_PERSONAL = ?");
			
			ps.setString(1, edoId);	
			ps.setString(2, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoAutoResp|yaContesto|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		
		return ok;
	}
	
	public static String getPromedio(Connection conn, String edoId, String codigoPersonal) throws SQLException{
		String promedio			= "";
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT TO_CHAR(COALESCE((SUM(RESPUESTA)/COUNT(RESPUESTA)*20), 0), '990.99') AS PROMEDIO FROM ENOC.EDO_AUTORESP A" + 
					" WHERE A.EDO_ID = TO_NUMBER(?, '99999')" +
					" AND A.CODIGO_PERSONAL = ?" +
					" AND A.PREGUNTA_ID IN (SELECT B.PREGUNTA_ID FROM ENOC.EDO_AUTOPREG B" + 
										  " WHERE B.EDO_ID = A.EDO_ID" +
										  " AND B.TIPO = 'O')");
			
			ps.setString(1, edoId);	
			ps.setString(2, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next())
				promedio = rs.getString("PROMEDIO");
			else
				promedio = "0";
			
		}catch(Exception ex){
			System.out.println("Error - aca.edo.EdoAutoResp|getPromedioMaestro|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		
		return promedio;
	}
}