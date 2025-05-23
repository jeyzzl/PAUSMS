package aca.salida;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SalidaConsejeroUtil {
	public ArrayList<SalidaConsejero> getListAll(Connection conn, String salidaId, String orden ) throws SQLException{
		ArrayList<SalidaConsejero> listSalidaConsejero 	= new ArrayList<SalidaConsejero>();
		Statement st 	= conn.createStatement();
		ResultSet rs 	= null;
		String comando	= "";
		
		try{
			comando = "SELECT SALIDA_ID , FOLIO, CONSEJERO, TRABAJO, CLAVE"+			        
					" FROM ENOC.SAL_CONSEJERO WHERE SALIDA_ID = '"+salidaId+"' "+orden; 
			
			rs = st.executeQuery(comando);			
			while (rs.next()){
				
				SalidaConsejero consejero = new SalidaConsejero();				
				consejero.mapeaReg(rs);
				listSalidaConsejero.add(consejero);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.salida.SalidaConsejeroUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }	
		}	
		
		return listSalidaConsejero;
	}
	

}