//Clase Util para la tabla de FORM_CONTESTA_SUB
package aca.form;

import java.sql.*;
import java.util.ArrayList;

public class UtilSubResp{
	
	public ArrayList<FormSubRespuesta> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<FormSubRespuesta> lisResp		= new ArrayList<FormSubRespuesta>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT " +
					"SUBRESPUESTA_ID, RESPUESTA_ID, PREGUNTA_ID, NOMBRE, " +
					"ORDEN " +
					"FROM FORM_SUBRESPUESTA "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				FormSubRespuesta subRespuesta = new FormSubRespuesta();
				subRespuesta.mapeaReg(rs);
				lisResp.add(subRespuesta);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.form.UtilSubResp|getListAll|:"+ex);
		}
		
		try { rs.close(); } catch (Exception ignore) { }
		try { st.close(); } catch (Exception ignore) { }		
		
		return lisResp;
	}
	
	public ArrayList<FormSubRespuesta> getLista(Connection conn, String subRespuestaId, String respuestaId, String preguntaId, String orden ) throws SQLException{
		
		ArrayList<FormSubRespuesta> lisResp		= new ArrayList<FormSubRespuesta>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT " +
					"SUBRESPUESTA_ID, RESPUESTA_ID, PREGUNTA_ID, NOMBRE, " +
					"ORDEN " +
					"FROM FORM_SUBRESPUESTA " +
					"WHERE SUBRESPUESTA_ID = '"+subRespuestaId+"' " +
					"AND RESPUESTA_ID = '"+respuestaId+"' " +
					"AND PREGUNTA_ID = '"+preguntaId+"' "+ orden;			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				FormSubRespuesta subRespuesta = new FormSubRespuesta();
				subRespuesta.mapeaReg(rs);
				lisResp.add(subRespuesta);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.form.UtilSubResp|getLista|:"+ex);
		}
		
		try { rs.close(); } catch (Exception ignore) { }
		try { st.close(); } catch (Exception ignore) { }		
		
		return lisResp;
	}
	
}