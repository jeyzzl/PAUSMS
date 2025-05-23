// Beans para la vista Maestro_Grupos

package aca.vista;
import java.sql.*;

public class MaestroGrupo {

	private String maestroCarrera;
	private String codigoPersonal;
	private String carreraId;
	private String numGrupos;
		
	public MaestroGrupo(){
		maestroCarrera	= "";
		codigoPersonal	= "";
		carreraId		= "";
		numGrupos		= "";
	}
	
	public String getMaestroCarrera(){
		return maestroCarrera;
	}
	
	public String getCodigoPersonal(){
		return codigoPersonal;
	}
			
	public String getCarreraId(){
		return carreraId;
	}
	
	public String getNumGrupos(){
		return numGrupos;
	}
		
	public void mapeaReg(ResultSet rs ) throws SQLException{
		maestroCarrera		= rs.getString("MAESTRO_CARRERA");
		codigoPersonal 		= rs.getString("CODIGO_PERSONAL");
		carreraId			= rs.getString("CARRERA_ID");
		numGrupos			= rs.getString("NUM_GRUPOS");
	}
	
	public void mapeaRegId( Connection conn, String codigoPersonal ) throws SQLException{
		ResultSet rs = null;
 		PreparedStatement ps = null; 
 		try{
	 		ps = conn.prepareStatement("SELECT MAESTRO_CARRERA, CODIGO_PERSONAL " +
	 				"CARRERA_ID, NUM_GRUPOS FROM ENOC.MAESTRO_GRUPOS WHERE CODIGO_PERSONAL = ? ");
			ps.setString(1, codigoPersonal);		
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
 		}catch(Exception ex){
			System.out.println("Error - aca.vista.MaestroGrupo|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}	
}