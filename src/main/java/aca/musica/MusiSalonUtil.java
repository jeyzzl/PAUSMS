package aca.musica;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MusiSalonUtil {

	public ArrayList<MusiSalon> getListAll(Connection conn, String orden) throws SQLException{
		
		ArrayList<MusiSalon> lisSalon	= new ArrayList<MusiSalon>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT SALON_ID, SALON_NOMBRE, CUPO FROM ENOC.MUSI_SALON " +orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				MusiSalon mi = new MusiSalon();
				mi.mapeaReg(rs);
				lisSalon.add(mi);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.musica.MusiInstrumentoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisSalon;
}
}