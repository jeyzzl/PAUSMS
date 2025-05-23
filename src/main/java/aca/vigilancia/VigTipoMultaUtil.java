package aca.vigilancia;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class VigTipoMultaUtil {
	
	public static ArrayList<VigTipoMulta> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<VigTipoMulta> lis	= new ArrayList<VigTipoMulta>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = "SELECT TIPO_ID, TIPO_NOMBRE, COSTO" +
 				" FROM ENOC.VIG_TIPOMULTA "+orden;			 
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				VigTipoMulta vig = new VigTipoMulta();
				vig.mapeaReg(rs);
				lis.add(vig);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.VigTipoMultaUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}			
		
		return lis;
	}
}