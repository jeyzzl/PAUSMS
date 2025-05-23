/**
 * 
 */
package aca.mentores;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author Elifo
 *
 */
public class MentAccesoUtil {
	public ArrayList<MentAcceso> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<MentAcceso> lisAcceso		= new ArrayList<MentAcceso>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, ACCESO FROM ENOC.MENT_ACCESO "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				MentAcceso acceso = new MentAcceso();
				acceso.mapeaReg(rs);
				lisAcceso.add(acceso);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentAccesoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisAcceso;
	}

	public ArrayList<MentAcceso> getLista(Connection conn, String codigoPersonal, String orden ) throws SQLException{
		
		ArrayList<MentAcceso> lisAcceso	= new ArrayList<MentAcceso>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, ACCESO FROM ENOC.MENT_ACCESO "+ 
						"WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				MentAcceso acceso = new MentAcceso();
				acceso.mapeaReg(rs);
				lisAcceso.add(acceso);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentAccesoUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisAcceso;
	}
}