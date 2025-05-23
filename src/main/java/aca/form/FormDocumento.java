//Bean de datos academicos del alumno
package  aca.form;
import java.sql.*;

public class FormDocumento{
	private String formId;
	private String nombre;
	private String cargaId;
	
	public FormDocumento(){
		formId		= "";
		nombre		= "";
		cargaId		= "";
	}
	
	
	public String getCargaId() {
		return cargaId;
	}
	
	public void setCargaId(String cargaId) {
		this.cargaId = cargaId;
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

	public boolean insertReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO FORM_DOCUMENTO"+
				"(FORM_ID, NOMBRE, CARGA_ID) "+
				"VALUES( ?, ?, ?)");
					
			ps.setString(1, formId);
			ps.setString(2, nombre);
			ps.setString(3, cargaId);
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.form.FormDocumento|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE FORM_DOCUMENTO "+
				"SET "+				
				"NOMBRE = ?, "+
				"CARGA_ID = ? "+
				"WHERE FORM_ID = ? ");			
			ps.setString(1, nombre);
			ps.setString(2, cargaId);
			ps.setString(3, formId);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.form.FormDocumento|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean deleteReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM FORM_DOCUMENTO "+
				"WHERE FORM_ID = ? ");
			ps.setString(1, formId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.form.FormDocumento|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		formId 				= rs.getString("FORM_ID");
		nombre 				= rs.getString("NOMBRE");
		cargaId 			= rs.getString("CARGA_ID");
	}
	
	public void mapeaRegId( Connection conn, String sFormId ) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{	
			ps = conn.prepareStatement("SELECT "+
				"FORM_ID, NOMBRE, CARGA_ID "+
				"FROM FORM_DOCUMENTO WHERE FORM_ID = ? ");
			ps.setString(1, sFormId);
			
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
			ps = conn.prepareStatement("SELECT FORM_ID FROM FORM_DOCUMENTO "+
				"WHERE FORM_ID = ?");
			ps.setString(1, formId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.form.FormDocumento|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
}