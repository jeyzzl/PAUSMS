//Clase para la vista MOD_OPCION
package aca.vista;
import java.sql.*;

public class FacultadMentor{
	private String idMentor;
	private String facultadId;
	private String nombreFacultad;	

	public FacultadMentor(){
		idMentor 		="";	
		facultadId 		="";
		nombreFacultad	="";		
	}
	
	/**
	 * @return Returns the facultadId.
	 */
	public String getFacultadId() {
		return facultadId;
	}
	
	/**
	 * @return Returns the idMentor.
	 */
	public String getIdMentor() {
		return idMentor;
	}
	
	/**
	 * @return Returns the nombreFacultad.
	 */
	public String getNombreFacultad() {
		return nombreFacultad;
	}	

	public void mapeaReg(ResultSet rs) throws SQLException{
		idMentor 		= rs.getString("ID_MENTOR");	
		facultadId 		= rs.getString("FACULTAD_ID");
		nombreFacultad	= rs.getString("NOMBRE_FACULTAD");			
	}

	public void mapeaRegId( Connection conn, String idMentor) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT " +
				"ID_MENTOR, FACULTAD_ID, NOMBRE_FACULTAD "+
				"FROM FACULTAD_MENTOR " +
				"WHERE ID_MENTOR = ? ");
			ps.setString(1, idMentor);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.FacultadMentor|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
	public boolean existeReg(Connection conn) throws SQLException{
		boolean  ok				= false;
		ResultSet  rs			= null;		
		PreparedStatement ps 	= null;
		try{
			ps = conn.prepareStatement("SELECT " +
					"ID_MENTOR, FACULTAD_ID, NOMBRE_FACULTAD "+
					"FROM FACULTAD_MENTOR " +
					"WHERE ID_MENTOR = ? ");
			ps.setString(1, idMentor);
			
			rs = ps.executeQuery();
			if(rs.next())
				ok = true;
			else
				ok = false;
			
		}catch (Exception ex){
			System.out.println("Error - aca.vista.FacultadMentor|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}

		return ok;
	}
}