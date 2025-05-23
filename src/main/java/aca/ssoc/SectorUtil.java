//Clase  para la tabla ARCH_DOCSTATUS
package aca.ssoc;

import java.sql.*;
import java.util.ArrayList;

public class SectorUtil {
	public ArrayList<Sector> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<Sector> lisSector 			= new ArrayList<Sector>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = "SELECT SECTOR_ID, SECTOR_NOMBRE FROM ENOC.SSOC_SECTOR "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Sector sector = new Sector();
				sector.mapeaReg(rs);
				lisSector.add(sector);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.SectorUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisSector;
	}
}