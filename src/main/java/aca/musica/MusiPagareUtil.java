package aca.musica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MusiPagareUtil {

	public ArrayList<MusiPagare> getListAll(Connection conn, String orden) throws SQLException{
		
		ArrayList<MusiPagare> lisPagare	= new ArrayList<MusiPagare>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando =  "SELECT PERIODO_ID, TO_CHAR(PAGARE1,'DD/MM/YYYY') AS PAGARE1," +
					" TO_CHAR(PAGARE2,'DD/MM/YYYY') AS PAGARE2," +
					" TO_CHAR(PAGARE3,'DD/MM/YYYY') AS PAGARE3, COMENTARIO "+
					" FROM ENOC.MUSI_PAGARE "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				MusiPagare mp = new MusiPagare();
				mp.mapeaReg(rs);
				lisPagare.add(mp);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.musica.MusiPagareUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisPagare;
	}
	
	public static String getFechaPagare(Connection conn, String periodoId, String campo) throws SQLException{
 		
 		ResultSet 		rs		= null;
 		PreparedStatement ps	= null;
 		String fecha			= "";
 		
 		try{
 			ps = conn.prepareStatement("SELECT COALESCE(TO_CHAR(?,'DD/MM/YYYY'),'01/01/2000') AS FECHAPAGARE" +
 				"FROM ENOC.MUSI_PAGARE WHERE PERIODO_ID = ?"); 
 			ps.setString(1, campo);
 			ps.setString(2, periodoId);
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				fecha = rs.getString("FECHAPAGARE");
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.MusiPagare|getFechaPagare|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		} 		
 		
 		return fecha;
 	}
	
	public static String getFechasPagare(Connection conn, String periodoId) throws SQLException{
 		
 		ResultSet 		rs		= null;
 		PreparedStatement ps	= null;
 		String fecha			= "";
 		
 		try{
 			ps = conn.prepareStatement("SELECT COALESCE(TO_CHAR(PAGARE1,'DD/MM/YYYY'),'01/01/2000')||','||" +
 					"COALESCE(TO_CHAR(PAGARE2,'DD/MM/YYYY'),'01/01/2000')||','||" +
 					"COALESCE(TO_CHAR(PAGARE3,'DD/MM/YYYY'),'01/01/2000')  AS FECHAPAGARE" +
 				" FROM ENOC.MUSI_PAGARE WHERE PERIODO_ID = ?"); 
 			ps.setString(1, periodoId);
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				fecha = rs.getString("FECHAPAGARE");
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.MusiPagare|getFechasPagare|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		} 		
 		
 		return fecha;
 	}
	
	
}