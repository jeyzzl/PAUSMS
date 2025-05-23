//Beans de la tabla ARCH_GRUPOS
package aca.archivo;

import java.sql.*;

public class ArchGrupos {
    private String grupo;
	private String idDocumento;
	private String idStatus;
		
	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public String getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(String idDocumento) {
		this.idDocumento = idDocumento;
	}

	public String getIdStatus() {
		return idStatus;
	}

	public void setIdStatus(String idStatus) {
		this.idStatus = idStatus;
	}

	public ArchGrupos(){
		grupo 		= "";
		idDocumento = "";
		idStatus 	= "";
	}	
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		grupo	 	 		= rs.getString("GRUPO");
		idDocumento			= rs.getString("IDDOCUMENTO");
		idStatus			= rs.getString("IDSTATUS");
	}
	
	public void mapeaRegId(Connection con, String grupo, String idDocumento, String idStatus) throws SQLException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{
			ps = con.prepareStatement("SELECT GRUPO, IDDOCUMENTO " +
					"IDSTATUS, FROM ENOC.ARCH_GRUPOS"); 
			ps.setString(1,grupo);
			ps.setString(2,idDocumento);
			ps.setString(3,idStatus);
			rs = ps.executeQuery();
			
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchGruposUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
}