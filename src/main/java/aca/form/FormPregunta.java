//Bean de datos academicos del alumno
package  aca.form;
import java.sql.*;

public class FormPregunta{
	private String formId;
	private String preguntaId;
	private String nombre;
	private String tipo;
	private String orden;
	
	public FormPregunta(){
		formId		= "";
		preguntaId	= "";
		nombre		= "";
		tipo		= "";
		orden		= "";
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

	public String getPreguntaId() {
		return preguntaId;
	}	

	public void setPreguntaId(String preguntaId) {
		this.preguntaId = preguntaId;
	}	

	public String getTipo() {
		return tipo;
	}	

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public String getOrden() {
		return orden;
	}	

	public void setOrden(String orden) {
		this.orden = orden;
	}
	
	public boolean insertReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO FORM_PREGUNTA"+
				"(FORM_ID, PREGUNTA_ID, NOMBRE, TIPO, ORDEN) "+
				"VALUES( ?, ?, ?, ?, ?)");
					
			ps.setString(1, formId);
			ps.setString(2, preguntaId);
			ps.setString(3, nombre);
			ps.setString(4, tipo);
			ps.setInt(5, Integer.parseInt(orden));
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.form.FormPregunta|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE FORM_PREGUNTA "+
				"SET "+
				"FORM_ID = ?, "+				
				"NOMBRE = ?, "+
				"TIPO = ?, "+
				"ORDEN = ? "+
				"WHERE PREGUNTA_ID = ? ");
			ps.setString(1, formId);			
			ps.setString(2, nombre);
			ps.setString(3, tipo);
			ps.setInt(4, Integer.parseInt(orden));
			ps.setString(5, preguntaId);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.form.FormPregunta|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean deleteReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM FORM_PREGUNTA "+
				"WHERE PREGUNTA_ID = ? ");
			ps.setString(1, preguntaId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.form.FormPregunta|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		formId 				= rs.getString("FORM_ID");
		preguntaId			= rs.getString("PREGUNTA_ID");
		nombre 				= rs.getString("NOMBRE");
		tipo	 			= rs.getString("TIPO");
		orden				= rs.getString("ORDEN");
	}
	
	public void mapeaRegId( Connection conn, String preguntaId ) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT "+
				"FORM_ID, PREGUNTA_ID, NOMBRE, TIPO, ORDEN "+
				"FROM FORM_PREGUNTA WHERE PREGUNTA_ID = ? ");
			ps.setString(1, preguntaId);
			
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
			ps = conn.prepareStatement("SELECT PREGUNTA_ID FROM FORM_PREGUNTA "+
				"WHERE PREGUNTA_ID = ?");
			ps.setString(1, preguntaId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.form.FormPregunta|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
}