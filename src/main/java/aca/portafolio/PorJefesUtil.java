package aca.portafolio;

import java.sql.*;
import java.util.ArrayList;

public class PorJefesUtil {
	
	public ArrayList<PorJefes> getListAll(Connection conn, String orden ) throws SQLException{
	
		ArrayList<PorJefes> lis	= new ArrayList<PorJefes>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
	 
		try{
			comando = "SELECT CODIGO_PERSONAL, EJERCICIO_ID, DEPARTAMENTOS, ESTADO"+
					"FROM DANIEL.POR_NIVEL "+orden; 
			rs = st.executeQuery(comando);
			while (rs.next()){
			
				PorJefes jefes = new PorJefes();
				jefes.mapeaReg(rs);
				lis.add(jefes);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.PorJefes|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
	
		return lis;
	}
	
}