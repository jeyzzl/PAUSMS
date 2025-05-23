//Beans de la tabla ARCH_DOCUMENTOS

package aca.archivo;
import java.sql.*;

public class ArchDocumentos {
	private String idDocumento;
	private String descripcion;
	private String imagen;
	private String orden;

	public ArchDocumentos(){
		idDocumento		= "";
		descripcion		= "";
		imagen			= "";
		orden			= "";
	}
	
	public String getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(String idDocumento) {
		this.idDocumento = idDocumento;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getOrden() {
		return orden;
	}

	public void setOrden(String orden) {
		this.orden = orden;
	}

	public void mapeaReg(ResultSet rs ) throws SQLException{
		idDocumento  		= rs.getString("IdDocumento");
		descripcion			= rs.getString("Descripcion");
		imagen				= rs.getString("Imagen");
		orden				= rs.getString("Orden");
	}

	
	public void mapeaRegId(Connection con, String IdDocumento) throws SQLException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{
			ps = con.prepareStatement("SELECT IDDOCUMENTO, " +
					"DESCRIPCION, IMAGEN, ORDEN " +				
					"FROM ENOC.ARCH_DOCUMENTOS WHERE IDDOCUMENTO = TO_NUMBER(?,'999')"); 
			ps.setString(1,IdDocumento);
			rs = ps.executeQuery();
			
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchDocumentos|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
	}
}		