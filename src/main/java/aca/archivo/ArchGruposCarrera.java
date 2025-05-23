//Beans de la tabla ARCH_GRUPOS_CARRERA
package aca.archivo;

import java.sql.*;

public class ArchGruposCarrera {
    private String carrera;
	private String grupos;
			
	public String getCarrera() {
		return carrera;
	}

	public void setCarrera(String carrera) {
		this.carrera = carrera;
	}

	public String getGrupos() {
		return grupos;
	}

	public void setGrupos(String grupos) {
		this.grupos = grupos;
	}

	public ArchGruposCarrera(){
		grupos 	= "";
		carrera = "";
	}
	
		public void mapeaReg(ResultSet rs ) throws SQLException{
		grupos	 	 		= rs.getString("grupos");
		carrera			= rs.getString("carrera");
	}
	
	
	
	public static String getGrupo(Connection conn, String carreraId) throws SQLException{
		String grupos 			= "X";
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(GRUPOS, '0') AS GRUPOS FROM ENOC.ARCH_GRUPOS_CARRERA" + 
					" WHERE CARRERA = ?");
			ps.setString(1, carreraId);
			
			rs= ps.executeQuery();		
			if(rs.next()){
				grupos = rs.getString("GRUPOS");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchGruposCarrera|getGrupo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return grupos;
	}
	
	public void mapeaRegId(Connection con, String carrera) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = con.prepareStatement("SELECT CARRERA, GRUPOS" +
					" FROM ENOC.ARCH_GRUPOS_CARRERA" + 
					" WHERE CARRERA = ?");
			
			ps.setString(1, carrera);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchGruposCarreraUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
	}
}