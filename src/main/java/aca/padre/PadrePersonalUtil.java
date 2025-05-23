//BEANS DE LA TABLA ALUM_PADRE

package aca.padre;

import java.sql.*;
import java.util.ArrayList;

public class PadrePersonalUtil {	
	
	public ArrayList<PadrePersonal> getAll(Connection conn, String orden ) throws SQLException{
	
		ArrayList<PadrePersonal> lisPadre			= new ArrayList<PadrePersonal>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
			
		try{
			comando = "SELECT * FROM ENOC.PADRE_PERSONAL "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
					
				PadrePersonal padre = new PadrePersonal();
				padre.mapeaReg(rs);
				lisPadre.add(padre);
			}
				
		}catch(Exception ex){
			System.out.println("Error - aca.padre.PadrePersonalUtil|getAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
			
		return lisPadre;
	} 	
	
}