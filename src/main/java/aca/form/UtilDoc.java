//Clase Util para la tabla de FORM_CONTESTA_SUB
package aca.form;

import java.sql.*;
import java.util.ArrayList;

public class UtilDoc{
	
	public ArrayList<FormDocumento> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<FormDocumento> lisResp		= new ArrayList<FormDocumento>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT " +
					"FORM_ID, NOMBRE, CARGA_ID " +
					"FROM FORM_DOCUMENTO "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				FormDocumento doc = new FormDocumento();
				doc.mapeaReg(rs);
				lisResp.add(doc);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.form.UtilDoc|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisResp;
	}
	
	public ArrayList<FormDocumento> getLista(Connection conn, String formId, String cargaId, String orden ) throws SQLException{
		
		ArrayList<FormDocumento> lisResp		= new ArrayList<FormDocumento>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT " +
					"FORM_ID, NOMBRE, CARGA_ID " +
					"FROM FORM_DOCUMENTO " +
					"WHERE FORM_ID = '"+formId+"' " +
					"AND CARGA_ID = '"+cargaId+"' " + orden;			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				FormDocumento doc = new FormDocumento();
				doc.mapeaReg(rs);
				lisResp.add(doc);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.form.UtilDoc|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisResp;
	}
}