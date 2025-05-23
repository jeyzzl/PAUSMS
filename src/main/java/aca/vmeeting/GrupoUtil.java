/**
 * 
 */
package aca.vmeeting;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author elifo
 *
 */
public class GrupoUtil {
	public static ArrayList<Grupo> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<Grupo> list	= new ArrayList<Grupo>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		
		try{
			comando = "SELECT ID, NOMBRE" +
 				" FROM GRUPO "+orden;			 
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Grupo grupo = new Grupo();
				grupo.mapeaReg(rs);
				list.add(grupo);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vmeeting.GrupoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}			
		
		return list;
	}
}
