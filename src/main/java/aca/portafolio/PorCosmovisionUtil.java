package aca.portafolio;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PorCosmovisionUtil {
	public ArrayList<PorCosmovision> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<PorCosmovision> lis	= new ArrayList<PorCosmovision>();
		Statement st 						= conn.createStatement();
		ResultSet rs 						= null;
		String comando						= "";
	 
		try{
			comando = "SELECT * "+
					"FROM DANIEL.POR_COSMOVISION "+orden; 
			rs = st.executeQuery(comando);
			while (rs.next()){
				PorCosmovision obj = new PorCosmovision();
				obj.mapeaReg(rs);
				lis.add(obj);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.PorCosmovisionUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
	
		return lis;
	}
}
