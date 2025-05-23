//Clase Util para la tabla de FORM_CONTESTA_SUB
package aca.form;

import java.sql.*;
import java.util.ArrayList;

public class UtilResp{
	
	public ArrayList<FormRespuesta> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<FormRespuesta> lisResp		= new ArrayList<FormRespuesta>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT " +
					"REPUESTA_ID, FORM_ID, PREGUNTA_ID, " +
					"NOMBRE, TIPO, ORDEN " +
					"FROM FORM_RESPUESTA "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				FormRespuesta resp = new FormRespuesta();
				resp.mapeaReg(rs);
				lisResp.add(resp);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.form.UtilResp|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisResp;
	}
	
	public ArrayList<FormRespuesta> getLista(Connection conn, String respuestaId, String formId, String preguntaId, String orden ) throws SQLException{
		
		ArrayList<FormRespuesta> lisResp		= new ArrayList<FormRespuesta>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT " +
					"RESPUESTA_ID, FORM_ID, PREGUNTA_ID, " +
					"NOMBRE, TIPO, ORDEN " +
					"FROM FORM_RESPUESTA " +
					"WHERE RESPUESTA_ID = '"+respuestaId+"' " +
					"AND FORM_ID = '"+formId+"' " +
					"AND PREGUNTA_ID = '"+preguntaId+"' "+ orden;			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				FormRespuesta resp = new FormRespuesta();
				resp.mapeaReg(rs);
				lisResp.add(resp);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.form.utilResp|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisResp;
	}
}