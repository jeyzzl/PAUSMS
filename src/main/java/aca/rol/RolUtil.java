package aca.rol;

import java.sql.*;
import java.util.ArrayList;
import aca.rol.Rol;

public class RolUtil{
	
	public ArrayList<Rol> getListAll(Connection conn, String orden) throws SQLException{
		
		ArrayList<Rol> lis		= new ArrayList<Rol>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT ROL_ID, ROL_NOMBRE FROM ENOC.ROL "+orden;	 
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Rol obj = new Rol();
				obj.mapeaReg(rs);
				lis.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.rol.Rol|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
}