// Clase Util para la tabla de FORM_CONTESTA_SUB
package aca.form;

import java.sql.*;
import java.util.ArrayList;

public class UtilContSub{
	
	public ArrayList<FormContestaSub> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<FormContestaSub> lisResp		= new ArrayList<FormContestaSub>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT " +
					"CODIGO_PERSONAL, CARGA_ID, FORM_ID, PREGUNTA_ID, " +
					"RESPUESTA_ID, SUBRESPUESTA_ID, ABIERTA " +
					"FROM FORM_CONTESTA_SUB "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				FormContestaSub subRespuesta = new FormContestaSub();
				subRespuesta.mapeaReg(rs);
				lisResp.add(subRespuesta);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.form.UtilContSub|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisResp;
	}
	
	public ArrayList<FormContestaSub> getLista(Connection conn, String codigoPersonal, String cargaId, String formId, String orden ) throws SQLException{
		
		ArrayList<FormContestaSub> lisResp		= new ArrayList<FormContestaSub>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT " +
					"CODIGO_PERSONAL, CARGA_ID, FORM_ID, PREGUNTA_ID, " +
					"RESPUESTA_ID, SUBRESPUESTA_ID, ABIERTA " +
					"FROM FORM_CONTESTA_SUB " +
					"WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' " +
					"AND CARGA_ID = '"+cargaId+"' " +
					"AND FORM_ID = '"+formId+"' "+ orden;			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				FormContestaSub subRespuesta = new FormContestaSub();
				subRespuesta.mapeaReg(rs);
				lisResp.add(subRespuesta);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.form.UtilContSub|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisResp;
	}
}