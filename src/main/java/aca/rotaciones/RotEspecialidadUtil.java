package aca.rotaciones;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class RotEspecialidadUtil {
	public ArrayList<RotEspecialidad> getListAll(Connection conn, String orden ) throws SQLException{
			
		ArrayList<RotEspecialidad> list	= new ArrayList<RotEspecialidad>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = "SELECT * FROM ENOC.ROT_ESPECIALIDAD "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				RotEspecialidad obj = new RotEspecialidad();
				obj.mapeaReg(rs);
				list.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.RotEspecialidadUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return list;
	}
	
	public ArrayList<RotEspecialidad> getEspNoAsignadas(Connection conn, String hospitalId, String orden ) throws SQLException{
		
		ArrayList<RotEspecialidad> list	= new ArrayList<RotEspecialidad>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = "SELECT * FROM ENOC.ROT_ESPECIALIDAD " +
					" WHERE ESPECIALIDAD_ID NOT IN(SELECT ESPECIALIDAD_ID FROM ENOC.ROT_HOSPITALESPECIALIDAD WHERE HOSPITAL_ID = "+hospitalId+" )"+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				RotEspecialidad obj = new RotEspecialidad();
				obj.mapeaReg(rs);
				list.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.RotEspecialidadUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return list;
	}
	
	public HashMap<String, RotEspecialidad> getMapAll(Connection conn, String orden ) throws SQLException{
		
		HashMap<String, RotEspecialidad> mapa	= new HashMap<String, RotEspecialidad>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = "SELECT * FROM ENOC.ROT_ESPECIALIDAD "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				RotEspecialidad obj = new RotEspecialidad();
				obj.mapeaReg(rs);
				mapa.put(obj.getEspecialidadId(), obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.RotEspecialidadUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return mapa;
	}
}
