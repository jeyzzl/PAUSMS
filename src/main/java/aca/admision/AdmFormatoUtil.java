package aca.admision;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AdmFormatoUtil {
	
	public ArrayList<AdmFormato> getAll(Connection conn, String orden) throws SQLException{
		ArrayList<AdmFormato> lisFormatos	= new ArrayList<AdmFormato>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT FORMATO_ID, FORMATO_NOMBRE, ARCHIVO" +
			" FROM SALOMON.ADM_FORMATO "+ orden; 
			
			rs = st.executeQuery(comando);
			while(rs.next()){
				AdmFormato formato = new AdmFormato();
				formato.mapeaReg(rs);
				lisFormatos.add(formato);
			}
			
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmFormatoUtil|getAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lisFormatos;
	}
}