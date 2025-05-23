//Bean de SUBRESPUESTAS DE UNA RESPUESTA
package  aca.form;
import java.sql.*;

public class FormSubRespuesta{
	private String subRespuestaId;
	private String respuestaId;	
	private String preguntaId;
	private String nombre;	
	private String orden;
	
	public FormSubRespuesta(){
		subRespuestaId 	= "";
		respuestaId		= "";		
		preguntaId		= "";
		nombre			= "";		
		orden			= "";
	}

	/**
	 * @return Returns the nombre.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre The nombre to set.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	/**
	 * @return Returns the orden.
	 */
	public String getOrden() {
		return orden;
	}

	/**
	 * @param orden The orden to set.
	 */
	public void setOrden(String orden) {
		this.orden = orden;
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
	 * @param subRespuestaId The subRespuestaId to set.
	 */
	public void setSubRespuestaId(String subRespuestaId) {
		this.subRespuestaId = subRespuestaId;
	}
		
	public boolean insertReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO FORM_SUBRESPUESTA"+
				"(SUBRESPUESTA_ID, RESPUESTA_ID, PREGUNTA_ID, NOMBRE, ORDEN) "+
				"VALUES( ?, ?, ?, ?, ?)");
					
			ps.setString(1, subRespuestaId);
			ps.setString(2, respuestaId);
			ps.setString(3, preguntaId);
			ps.setString(4, nombre);			
			ps.setInt(5, Integer.parseInt(orden));
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.form.FormSubRespuesta|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE FORM_SUBRESPUESTA "+
				"SET "+
				"RESPUESTA_ID = ?, "+
				"PREGUNTA_ID = ?, "+				
				"NOMBRE = ?, "+			
				"ORDEN = ? "+
				"WHERE SUBRESPUESTA_ID = ? ");
			ps.setString(1, respuestaId);			
			ps.setString(2, preguntaId);			
			ps.setString(3, nombre);			
			ps.setInt(4, Integer.parseInt(orden));
			ps.setString(5, respuestaId);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.form.FormSubRespuesta|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean deleteReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM FORM_SUBRESPUESTA "+
				"WHERE SUBRESPUESTA_ID = ? ");
			ps.setString(1, subRespuestaId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.form.FormSubRespuesta|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		subRespuestaId		= rs.getString("SUBRESPUESTA_ID");
		respuestaId 		= rs.getString("RESPUESTA_ID");
		preguntaId			= rs.getString("PREGUNTA_ID");
		nombre 				= rs.getString("NOMBRE");	
		orden				= rs.getString("ORDEN");
	}
	
	public void mapeaRegId( Connection conn, String sRespuestaId ) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{	
			ps = conn.prepareStatement("SELECT "+
				"SUBRESPUESTA_ID, RESPUESTA_ID, PREGUNTA_ID, NOMBRE, ORDEN "+
				"FROM FORM_SUBRESPUESTA WHERE SUBRESPUESTA_ID = ? ");
			ps.setString(1, sRespuestaId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.form.FormSubRespuesta|mapeaRegId|:"+ex);
		}finally{
			if(rs!=null) rs.close();
			if(ps!=null) ps.close();
		}
		
	}
	
	public boolean existeReg(Connection conn) throws SQLException{
		boolean ok 				= false;
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT SUBRESPUESTA_ID FROM FORM_SUBRESPUESTA "+
				"WHERE RESPUESTA_ID = ?");
			ps.setString(1, subRespuestaId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.form.FormSubRespuesta|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
}