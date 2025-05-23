//Beans de la tabla ARCH_UBICACION
package aca.archivo;

import java.sql.*;

public class ArchUbicacion {
    private String ubicacionId;
	private String ubicacionNombre;	

	public ArchUbicacion(){
		ubicacionId 	= "0";
		ubicacionNombre = "-";
	}
	
	public String getUbicacionId() {
		return ubicacionId;
	}

	public void setUbicacionId(String ubicacionId) {
		this.ubicacionId = ubicacionId;
	}

	public String getUbicacionNombre() {
		return ubicacionNombre;
	}

	public void setUbicacionNombre(String ubicacionNombre) {
		this.ubicacionNombre = ubicacionNombre;
	}	
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		ubicacionId	 	 		= rs.getString("UBICACION_ID");
		ubicacionNombre			= rs.getString("UBICACION_NOMBRE");
	}
	
public void mapeaRegId(Connection con, String ubicacionId) throws SQLException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{
			ps = con.prepareStatement("SELECT UBICACION_ID,UBICACION_NOMBRE " +
					"FROM ENOC.ARCH_UBICACION WHERE UBICACION_ID = TO_NUMBER(?,'99')"); 
			ps.setString(1,ubicacionId);
			rs = ps.executeQuery();
			
			if(rs.next()){
				mapeaReg(rs);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchUbicacionUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		try { rs.close(); } catch (Exception ignore) { }
		try { ps.close(); } catch (Exception ignore) { }
		
	}
	
}