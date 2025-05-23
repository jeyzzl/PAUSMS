// Bean del formulario de reingreso
package  aca.form;

import java.sql.*;

public class FormContestaSub{
	private String codigoPersonal;
	private String cargaId;
	private String formId;
	private String preguntaId;
	private String respuestaId;
	private String subRespuestaId;
	private String abierta;
	
	public FormContestaSub(){
		codigoPersonal	= "";	
		cargaId			= "";
		formId 			= "";
		preguntaId 		= "";
		respuestaId 	= "";
		subRespuestaId 	= "";
		abierta 		= "";
	}
	
	/**
	 * @return Returns the abierta.
	 */
	public String getAbierta() {
		return abierta;
	}
	
	/**
	 * @param abierta The abierta to set.
	 */
	public void setAbierta(String abierta) {
		this.abierta = abierta;
	}
	
	/**
	 * @return Returns the cargaId.
	 */
	public String getCargaId() {
		return cargaId;
	}
	
	/**
	 * @param cargaId The cargaId to set.
	 */
	public void setCargaId(String cargaId) {
		this.cargaId = cargaId;
	}
	
	/**
	 * @return Returns the codigoPersonal.
	 */
	public String getCodigoPersonal() {
		return codigoPersonal;
	}
	
	/**
	 * @param codigoPersonal The codigoPersonal to set.
	 */
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}	

	/**
	 * @return Returns the formId.
	 */
	public String getFormId() {
		return formId;
	}
	
	/**
	 * @param formId The formId to set.
	 */
	public void setFormId(String formId) {
		this.formId = formId;
	}
	
	/**
	 * @return Returns the preguntaId.
	 */
	public String getPreguntaId() {
		return preguntaId;
	}
	
	/**
	 * @param preguntaId The preguntaId to set.
	 */
	public void setPreguntaId(String preguntaId) {
		this.preguntaId = preguntaId;
	}
	
	/**
	 * @return Returns the respuestaId.
	 */
	public String getRespuestaId() {
		return respuestaId;
	}
	
	/**
	 * @param respuestaId The respuestaId to set.
	 */
	public void setRespuestaId(String respuestaId) {
		this.respuestaId = respuestaId;
	}
	

	/**
	 * @return Returns the subRespuestaId.
	 */
	public String getSubRespuestaId() {
		return subRespuestaId;
	}
	
	/**
	 * @param respuestaId The subRespuestaId to set.
	 */
	public void setSubRespuestaId(String subRespuestaId) {
		this.subRespuestaId = subRespuestaId;
	}
	
	public boolean insertReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO "+
				"FORM_CONTESTA_SUB(CODIGO_PERSONAL, CARGA_ID, FORM_ID, PREGUNTA_ID, RESPUESTA_ID, SUBRESPUESTA_ID, ABIERTA) "+
				"VALUES( ?,?,?,?,?,?)");
			ps.setString(1, codigoPersonal);
			ps.setString(2, cargaId);
			ps.setString(3, formId);
			ps.setString(4, preguntaId);
			ps.setString(5, respuestaId);
			ps.setString(5, subRespuestaId);
			ps.setString(6, abierta);
			
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.form.FormContestaSub|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	

	public boolean updateReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE FORM_CONTESTA_SUB "+
				"SET  ABIERTA = ? "+
				"WHERE CODIGO_PERSONAL = ?," );
				
			ps.setString(1, abierta);
			ps.setString(2, codigoPersonal);
	
		
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.form.FormContestaSub|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
				
		return ok;
	}

	
	public boolean deleteReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM FORM_CONTESTA_SUB "+
				"WHERE CODIGO_PERSONAL = ? ");
			ps.setString(1, codigoPersonal);
		
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.form.FormContestaSub|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}

	public void mapeaReg(ResultSet rs ) throws SQLException{
		codigoPersonal 		= rs.getString("CODIGO_PERSONAL");
		cargaId	 			= rs.getString("CARGA_ID");
		formId 	 			= rs.getString("FORM_ID");
		preguntaId	 		= rs.getString("PREGUNTA_ID");
		respuestaId 	 	= rs.getString("RESPUESTA_ID");
		subRespuestaId 	 	= rs.getString("SUBRESPUESTA_ID");
		abierta  	 		= rs.getString("ABIERTA");
	}		


	public void mapeaRegId( Connection conn, String codigoPersonal, String respuestaId) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT CODIGO_PERSONAL, CARGA_ID, FORM_ID, PREGUNTA_ID, ABIERTA "+
				"FROM FORM_CONTESTA_SUB WHERE CODIGO_PERSONAL = ?  SUBRESPUESTA_ID = ? ");
			ps.setString(1,codigoPersonal);
			ps.setString(2,subRespuestaId);
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.form.FormContestaSub|mapeaRegId|:"+ex);
		}finally{
			if(rs!=null) rs.close();
			if(ps!=null) ps.close();
		}
		
	}
	
	public boolean existeReg(Connection conn) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM FORM_CONTESTA_SUB WHERE CODIGO_PERSONAL = ? SUBRESPUESTA_ID = ? ");
			ps.setString(1,codigoPersonal);
			ps.setString(2,subRespuestaId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.form.FormContestaSub|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String maximoReg(Connection conn) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String maximo			= "1";
		
		try{
			ps = conn.prepareStatement("SELECT MAX(CODIGO_PERSONAL)+1 MAXIMO FROM FORM_CONTESTA_SUB"); 
		    ps = conn.prepareStatement("SELECT MAX(SUBRESPUESTA_ID)+1 MAXIMO FROM FORM_CONTESTA_SUB" );			
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");			
			
		}catch(Exception ex){
			System.out.println("Error - aca.form.FormContestaSub|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}

	/*
	public static String getNombrePais(Connection conn, String paisId ) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String nombre			= "vacio";
		
		try{
			ps = conn.prepareStatement("SELECT NOMBRE_PAIS FROM ENOC.CAT_PAIS WHERE PAIS_ID = ?"); 
			ps.setString(1, paisId);
			rs = ps.executeQuery();
			if (rs.next())
				nombre = rs.getString("NOMBRE_PAIS");
			
		}catch(Exception ex){
			System.out.println("Error: "+ex);
		}
		try { rs.close(); } catch (Exception ignore) { }
		try { ps.close(); } catch (Exception ignore) { }
		
		return nombre;
	}

*/
}