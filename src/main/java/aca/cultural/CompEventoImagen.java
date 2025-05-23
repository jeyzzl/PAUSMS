// Bean de Alum_Foto
package  aca.cultural;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CompEventoImagen{
	private String eventoId;
	private String imagenId;
	private String descripcion;
	private byte[] imagen;
		
	public CompEventoImagen(){
		eventoId				= "";
		imagenId				= "";
		descripcion				= "";
		imagen 					= null;
	}
	
	public String getEventoId() {
		return eventoId;
	}
	
	public void setEventoId(String eventoId) {
		this.eventoId = eventoId;
	}

	public String getImagenId() {
		return imagenId;
	}

	public void setImagenId(String imagenId) {
		this.imagenId = imagenId;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public byte[] getImagen() {
		return imagen;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

	public void mapeaReg(ResultSet rs ) throws SQLException{
		eventoId 					= rs.getString("EVENTO_ID");
		imagenId					= rs.getString("IMAGEN_ID");
		descripcion					= rs.getString("DESCRIPCION");
		imagen 						= rs.getBytes("IMAGEN");
	}
	
	public void mapeaRegId( Connection conn, String eventoId, String imagenId) throws SQLException{		
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
		ps = conn.prepareStatement("SELECT"+
			" EVENTO_ID, IMAGEN_ID, DESCRIPCION, IMAGEN"+
			" FROM ENOC.COMP_EVENTO_IMAGEN"+ 
			" WHERE EVENTO_ID = ? AND IMAGEN_ID = ? ");
		ps.setString(1, eventoId);
		ps.setString(2, imagenId);
		
		rs = ps.executeQuery();
		if (rs.next()){			
			mapeaReg(rs);
		}
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.CompEventoImagenUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		
	}
	
}