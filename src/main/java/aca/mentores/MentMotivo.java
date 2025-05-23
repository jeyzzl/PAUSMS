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
public class MentMotivo {
	private String motivoId;
	private String motivoNombre;
	private String comentario;
	
	public MentMotivo(){
		motivoId		= "";
		motivoNombre	= "";
		comentario		= "";
	}
	
	public String getMotivoId() {
		return motivoId;
	}

	public void setMotivoId(String motivoId) {
		this.motivoId = motivoId;
	}

	public String getMotivoNombre() {
		return motivoNombre;
	}

	public void setMotivoNombre(String motivoNombre) {
		this.motivoNombre = motivoNombre;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public boolean insertReg(Connection conn) throws Exception{
		boolean ok = true;
		PreparedStatement ps= null;
		try{
			ps= conn.prepareStatement("INSERT INTO ENOC.MENT_MOTIVO" + 
					" (MOTIVO_ID, MOTIVO_NOMBRE, COMENTARIO)" +
					" VALUES( TO_NUMBER(?, '99'), ?, ?)");
			
			ps.setString(1, motivoId);
			ps.setString(2, motivoNombre);
			ps.setString(3, comentario);
			
			if(ps.executeUpdate() == 1)
				ok = true;
			else
				ok = false;
		}catch (Exception ex){
			System.out.println("Error - aca.mentores.MentMotivo|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn) throws Exception{
		boolean ok = true;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.MENT_MOTIVO"+ 
					" SET MOTIVO_NOMBRE = ?, COMENTARIO = ?" +
					" WHERE MOTIVO_ID = TO_NUMBER(?, '99')");
			
			ps.setString(1, motivoNombre);
			ps.setString(2, comentario);
			ps.setString(3, motivoId);
			
			if(ps.executeUpdate()==1)
				ok = true;
			else
				ok = false;
		}catch (Exception ex){
			System.out.println("Error - aca.mentores.MentMotivo|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn)throws Exception{
		boolean ok = true;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.MENT_MOTIVO"+ 
				" WHERE MOTIVO_ID = TO_NUMBER(?, '99')");
			
			ps.setString(1, motivoId);
			
			if(ps.executeUpdate()==1)
				ok = true;
			else 
				ok = false;
		}catch (Exception ex){
			System.out.println("Error - aca.mentores.MentMotivo|deleteReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs) throws SQLException{
		motivoId		= rs.getString("MOTIVO_ID");
		motivoNombre	= rs.getString("MOTIVO_NOMBRE");
		comentario		= rs.getString("COMENTARIO");
	}
	
	public void mapeaRegId(Connection conn, String idMotivo) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{ 
			ps = conn.prepareStatement("SELECT MOTIVO_ID, MOTIVO_NOMBRE, COMENTARIO"+
					" FROM ENOC.MENT_MOTIVO" + 
					" WHERE MOTIVO_ID = TO_NUMBER(?, '99')");
			ps.setString(1, idMotivo);
			rs = ps.executeQuery();
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentMotivo|mapeaRegId|:"+ex);
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
			ps = conn.prepareStatement("SELECT * FROM ENOC.MENT_MOTIVO" + 
					" WHERE MOTIVO_ID = TO_NUMBER(?, '99')");
			ps.setString(1, motivoId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentMotivo|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String maximoReg(Connection conn ) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String maximo			= "1";
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(MAX(MOTIVO_ID)+1,0) AS MAXIMO FROM ENOC.MENT_MOTIVO "); 
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");	
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentMotivo|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public static String getMotivoNombre(Connection conn, String idMotivo) throws SQLException{
 		
 		ResultSet 		rs		= null;
 		PreparedStatement ps	= null;
 		String nombre			= "";
 		
 		try{
 			ps = conn.prepareStatement("SELECT MOTIVO_NOMBRE FROM ENOC.MENT_MOTIVO " + 
 					"WHERE MOTIVO_ID = TO_NUMBER(?, '99')");
 			ps.setString(1,idMotivo); 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				nombre = rs.getString("MOTIVO_NOMBRE");
 		}catch(Exception ex){
 			System.out.println("Error - aca.mentores.MentMotivo|getMotivo|:"+ex);
 		}finally{
 			try { rs.close(); } catch (Exception ignore) { }
 	 		try { ps.close(); } catch (Exception ignore) { }
 		}		
 		
 		return nombre;
 	}
	
}