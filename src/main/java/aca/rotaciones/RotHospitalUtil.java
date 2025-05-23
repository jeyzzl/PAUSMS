package aca.rotaciones;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class RotHospitalUtil {
	public ArrayList<RotHospital> getListAll(Connection conn, String orden ) throws SQLException{
			
		ArrayList<RotHospital> list	= new ArrayList<RotHospital>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = "SELECT * FROM ENOC.ROT_HOSPITAL "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				RotHospital obj = new RotHospital();
				obj.mapeaReg(rs);
				list.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.RotHospitalUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return list;
	}
	
	public ArrayList<RotHospital> getListHospitales(Connection conn, String orden ) throws SQLException{
		
		ArrayList<RotHospital> list	= new ArrayList<RotHospital>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = "SELECT * FROM ENOC.ROT_HOSPITAL WHERE HOSPITAL_ID IN (SELECT HOSPITAL_ID FROM ENOC.ROT_HOSPITALESPECIALIDAD)"+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				RotHospital obj = new RotHospital();
				obj.mapeaReg(rs);
				list.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.RotHospitalUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return list;
	}
}
