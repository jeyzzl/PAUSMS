//Clase Util para la tabla de FORM_CONTESTA_SUB
package aca.form;

import java.sql.*;
import java.util.ArrayList;

public class UtilPreg{
	
	public ArrayList<FormPregunta> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<FormPregunta> lisResp		= new ArrayList<FormPregunta>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT " +
					"FORM_ID, PREGUNTA_ID, NOMBRE, TIPO, " +
					"ORDEN " +
					"FROM FORM_PREGUNTA "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				FormPregunta preg = new FormPregunta();
				preg.mapeaReg(rs);
				lisResp.add(preg);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.form.UtilPreg|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisResp;
	}
	
	public ArrayList<FormPregunta> getLista(Connection conn, String formId, String preguntaId, String orden ) throws SQLException{
		
		ArrayList<FormPregunta> lisResp		= new ArrayList<FormPregunta>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT " +
					"FORM_ID, PREGUNTA_ID, NOMBRE, TIPO, " +
					"ORDEN " +
					"FROM FORM_PREGUNTA " +
					"WHERE FORM_ID = '"+formId+"' " +
					"AND PREGUNTA_ID = '"+preguntaId+"' " + orden;			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				FormPregunta preg = new FormPregunta();
				preg.mapeaReg(rs);
				lisResp.add(preg);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.form.UtilPreg|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisResp;
	}
}