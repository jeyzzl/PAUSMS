package aca.vista;

import java.sql.*;
import java.util.ArrayList;

public class FacultadMentorUtil {
	
	public ArrayList<FacultadMentor> getListAll(Connection conn, String orden ) throws SQLException{		
		ArrayList<FacultadMentor> lisMentor	= new ArrayList<FacultadMentor>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
	
		try{
			comando = "SELECT ID_MENTOR, FACULTAD_ID, NOMBRE_FACULTAD FROM FACULTAD_MENTOR "+orden;
		
			rs = st.executeQuery(comando);
			while (rs.next()){
			
				FacultadMentor mentor = new FacultadMentor();
				mentor.mapeaReg(rs);
				lisMentor.add(mentor);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.FacultadMentorUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
	
		return lisMentor;	
	}
}