package aca.rotaciones;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class RotInstitucionUtil {
	public ArrayList<RotInstitucion> getListAll(Connection conn, String orden ) throws SQLException{
			
			ArrayList<RotInstitucion> list	= new ArrayList<RotInstitucion>();
			Statement st 					= conn.createStatement();
			ResultSet rs 					= null;
			String comando					= "";
			
			try{
				comando = "SELECT * FROM ENOC.ROT_INSTITUCION "+orden; 
				
				rs = st.executeQuery(comando);
				while (rs.next()){
					
					RotInstitucion obj = new RotInstitucion();
					obj.mapeaReg(rs);
					list.add(obj);
				}
				
			}catch(Exception ex){
				System.out.println("Error - aca.rotaciones.RotInstitucion|getListAll|:"+ex);
			}finally{
				try { rs.close(); } catch (Exception ignore) { }
				try { st.close(); } catch (Exception ignore) { }
			}
			
			return list;
		}
}
