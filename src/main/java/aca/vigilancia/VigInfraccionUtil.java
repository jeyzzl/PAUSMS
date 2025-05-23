package aca.vigilancia;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class VigInfraccionUtil {
	
	public static ArrayList<VigInfraccion> getListAll(Connection conn, String orden) throws SQLException{
		
		ArrayList<VigInfraccion> lis	= new ArrayList<VigInfraccion>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = "SELECT FOLIO, FECHA, AUTO_ID, TIPO_ID, DESCRIPCION, MULTA " +
 				" FROM ENOC.VIG_INFRACCION " +orden; 
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				VigInfraccion vig = new VigInfraccion();
				vig.mapeaReg(rs);
				lis.add(vig);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.VigAutoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}			
		
		return lis;
	}
}