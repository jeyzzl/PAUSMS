//Beans de la tabla ARCH_STATUS
package aca.archivo;

import java.sql.*;

public class ArchStatus {
    private String IdStatus;
	private String Descripcion;
			
	public String getDescripcion() {
		return Descripcion;
	}

	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}

	public String getIdStatus() {
		return IdStatus;
	}

	public void setIdStatus(String idStatus) {
		IdStatus = idStatus;
	}

	public ArchStatus(){
		IdStatus 	= "";
		Descripcion = "";
	}	
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		IdStatus	 	 		= rs.getString("IdStatus");
		Descripcion				= rs.getString("Descripcion");
	}	
	
	public void mapeaRegId(Connection con, String IdStatus) throws SQLException{
 		PreparedStatement ps = null;
 		ResultSet rs = null;		
 		try{
 			ps = con.prepareStatement("SELECT IDSTATUS,DESCRIPCION " +
 					"FROM ENOC.ARCH_STATUS WHERE IDSTATUS = TO_NUMBER(?,'99')"); 
 			ps.setString(1, IdStatus);
 			rs = ps.executeQuery();
 			
 			if(rs.next()){
 				mapeaReg(rs);
 			}
 		
 		}catch(Exception ex){
 			System.out.println("Error - aca.archivo.ArchStatusUtil|mapeaRegId|:"+ex);
 		}finally{
 			try { rs.close(); } catch (Exception ignore) { }
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		try { rs.close(); } catch (Exception ignore) { }
 		try { ps.close(); } catch (Exception ignore) { }
 
 	}
}