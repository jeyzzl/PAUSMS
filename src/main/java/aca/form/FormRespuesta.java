//Bean de datos academicos del alumno
package  aca.form;
import java.sql.*;

public class FormRespuesta{
	private String respuestaId;
	private String formId;
	private String preguntaId;
	private String nombre;
	private String tipo;
	private String orden;
	
	public FormRespuesta(){
		respuestaId		= "";
		formId			= "";
		preguntaId		= "";
		nombre			= "";
		tipo			= "";
		orden			= "";
	}
	
	
	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getOrden() {
		return orden;
	}

	public void setOrden(String orden) {
		this.orden = orden;
	}

	public String getPreguntaId() {
		return preguntaId;
	}

	public void setPreguntaId(String preguntaId) {
		this.preguntaId = preguntaId;
	}

	public String getRespuestaId() {
		return respuestaId;
	}

	public void setRespuestaId(String respuestaId) {
		this.respuestaId = respuestaId;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public boolean insertReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO FORM_RESPUESTA"+
				"(RESPUESTA_ID, FORM_ID, PREGUNTA_ID, NOMBRE, TIPO, ORDEN) "+
				"VALUES( ?, ?, ?, ?, ?, ?)");
					
			ps.setString(1, respuestaId);
			ps.setString(2, formId);
			ps.setString(3, preguntaId);
			ps.setString(4, nombre);
			ps.setString(5, tipo);
			ps.setInt(6, Integer.parseInt(orden));
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.form.FormRespuesta|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE FORM_RESPUESTA "+
				"SET "+				
				"FORM_ID = ?, "+
				"PREGUNTA_ID = ?, "+
				"NOMBRE = ?, "+
				"TIPO = ?, "+
				"ORDEN = ? "+
				"WHERE RESPUESTA_ID = ? ");			
			ps.setString(1, formId);
			ps.setString(2, preguntaId);
			ps.setString(3, nombre);
			ps.setString(4, tipo);
			ps.setInt(5, Integer.parseInt(orden));
			ps.setString(6, respuestaId);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.form.FormRespuesta|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean deleteReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM FORM_RESPUESTA "+
				"WHERE RESPUESTA_ID = ? ");
			ps.setString(1, respuestaId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.form.FormRespuesta|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		respuestaId			= rs.getString("RESPUESTA_ID");
		formId 				= rs.getString("FORM_ID");
		preguntaId			= rs.getString("PREGUNTA_ID");
		nombre 				= rs.getString("NOMBRE");
		tipo	 			= rs.getString("TIPO");
		orden				= rs.getString("ORDEN");
	}
	
	public void mapeaRegId( Connection conn, String respuestaId ) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try{
		ps = conn.prepareStatement("SELECT "+
			"RESPUESTA_ID, FORM_ID, PREGUNTA_ID, NOMBRE, TIPO, ORDEN "+
			"FROM FORM_RESPUESTA WHERE RESPUESTA_ID = ? ");
		ps.setString(1, respuestaId);
		
		rs = ps.executeQuery();
		if (rs.next()){
			mapeaReg(rs);
		}
		
		}catch(Exception ex){
			System.out.println("Error - aca.form.FormPregunta|mapeaRegId|:"+ex);
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
			ps = conn.prepareStatement("SELECT RESPUESTA_ID FROM FORM_RESPUESTA "+
				"WHERE RESPUESTA_ID = ?");
			ps.setString(1, respuestaId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.form.FormRespuesta|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
}