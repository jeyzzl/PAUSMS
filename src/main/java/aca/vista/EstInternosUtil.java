// Clase para la vista ESTINTERNOS

package aca.vista;

import java.sql.*;
import java.util.ArrayList;

public class EstInternosUtil{
	
	public ArrayList<EstInternos> getListAll (Connection conn, String orden) throws SQLException{
		
		ArrayList<EstInternos> lisInternos 			= new ArrayList<EstInternos>();
		Statement st	 			= conn.createStatement();
		ResultSet rs				= null;
		String s_command			= "";
		
		try {
			s_command = "SELECT FACULTAD_ID, CARRERA_ID, CODIGO_PERSONAL, DORMITORIO "+
				"FROM ENOC.ESTINTERNOS "+ orden;
			rs = st.executeQuery (s_command);
			while (rs.next()){
				
				EstInternos internos = new EstInternos();
				internos.mapeaReg(rs);
				lisInternos.add(internos);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstInternosUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}	
		
		return lisInternos;
	}
	
	public ArrayList<EstInternos> getListAllUM (Connection conn, String orden) throws SQLException{
		
		ArrayList<EstInternos> lisInternos 			= new ArrayList<EstInternos>();
		Statement st	 			= conn.createStatement();
		ResultSet rs				= null;
		String s_command			= "";
		
		try {
			s_command = "SELECT E.FACULTAD_ID AS FACULTAD_ID," +
					" E.CARRERA_ID AS CARRERA_ID," +
					" E.CODIGO_PERSONAL AS CODIGO_PERSONAL," +
					" E.DORMITORIO AS DORMITORIO "+
				"FROM ENOC.ESTINTERNOS E, ENOC.INSCRITOS I" +
				" WHERE E.CODIGO_PERSONAL = I.CODIGO_PERSONAL" +
				" AND I.MODALIDAD_ID IN (1,2,3,4) "+ orden;
			rs = st.executeQuery (s_command);
			while (rs.next()){
				
				EstInternos internos = new EstInternos();
				internos.mapeaReg(rs);
				lisInternos.add(internos);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstInternosUtil|getListAllUM|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisInternos;
	}
}