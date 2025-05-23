// Clase Util para la tabla de FORM_CONTESTA_RESP
package aca.form;

import java.sql.*;
import java.util.ArrayList;

public class UtilContResp{
	
	public ArrayList<FormContestaResp> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<FormContestaResp> lisResp		= new ArrayList<FormContestaResp>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT " +
					"CODIGO_PERSONAL, CARGA_ID, FORM_ID, PREGUNTA_ID, " +
					"RESPUESTA_ID, ABIERTA " +
					"FROM FORM_CONTESTA_RESP "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				FormContestaResp respuesta = new FormContestaResp();
				respuesta.mapeaReg(rs);
				lisResp.add(respuesta);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.form.FormContResp|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisResp;
	}
	
	public ArrayList<FormContestaResp> getLista(Connection conn, String codigoPersonal, String cargaId, String formId, String orden ) throws SQLException{
		
		ArrayList<FormContestaResp> lisResp		= new ArrayList<FormContestaResp>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT " +
					"CODIGO_PERSONAL, CARGA_ID, FORM_ID, PREGUNTA_ID, " +
					"RESPUESTA_ID, ABIERTA" +
					"FROM FORM_CONTESTA_RESP " +
					"WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' " +
					"AND CARGA_ID = '"+cargaId+"' " +
					"AND FORM_ID = '"+formId+"' "+ orden;			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				FormContestaResp respuesta = new FormContestaResp();
				respuesta.mapeaReg(rs);
				lisResp.add(respuesta);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.form.FormContResp|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisResp;
	}
}