package aca.rotaciones;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class RotHospitalEspecialidadUtil {
	public ArrayList<RotHospitalEspecialidad> getListAll(Connection conn, String orden ) throws SQLException{
			
			ArrayList<RotHospitalEspecialidad> list	= new ArrayList<RotHospitalEspecialidad>();
			Statement st 				= conn.createStatement();
			ResultSet rs 				= null;
			String comando				= "";
			
			try{
				comando = "SELECT * FROM ENOC.ROT_HOSPITALESPECIALIDAD "+orden; 
				
				rs = st.executeQuery(comando);
				while (rs.next()){
					
					RotHospitalEspecialidad obj = new RotHospitalEspecialidad();
					obj.mapeaReg(rs);
					list.add(obj);
				}
				
			}catch(Exception ex){
				System.out.println("Error - aca.rotaciones.RotHospitalEspecialidadUtil|getListAll|:"+ex);
			}finally{
				try { rs.close(); } catch (Exception ignore) { }
				try { st.close(); } catch (Exception ignore) { }
			}
			
			return list;
	}
	
	public ArrayList<RotHospitalEspecialidad> getListHosp(Connection conn, String hospitalId, String orden ) throws SQLException{
		
		ArrayList<RotHospitalEspecialidad> list	= new ArrayList<RotHospitalEspecialidad>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = "SELECT * FROM ENOC.ROT_HOSPITALESPECIALIDAD WHERE HOSPITAL_ID = "+hospitalId+" "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				RotHospitalEspecialidad obj = new RotHospitalEspecialidad();
				obj.mapeaReg(rs);
				list.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.RotHospitalEspecialidadUtil|getListHosp|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return list;
	}
	
	public ArrayList<RotHospitalEspecialidad> getListHospActivas(Connection conn, String hospitalId, String orden ) throws SQLException{
		
		ArrayList<RotHospitalEspecialidad> list	= new ArrayList<RotHospitalEspecialidad>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = "SELECT * FROM ENOC.ROT_HOSPITALESPECIALIDAD WHERE HOSPITAL_ID = "+hospitalId+"" +
					"  AND ESTADO = 'A' "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				RotHospitalEspecialidad obj = new RotHospitalEspecialidad();
				obj.mapeaReg(rs);
				list.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.RotHospitalEspecialidadUtil|getListHosp|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return list;
	}
}
