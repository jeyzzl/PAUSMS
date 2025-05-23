package aca.residencia;

import java.sql.*;
import java.util.ArrayList;

public class ComentarioUtil {
	
	public ArrayList<ResComentario> getListAll(Connection conn, String codigoPersonal, String residencia, String orden ) throws SQLException{
		
		ArrayList<ResComentario> lisCom 	= new ArrayList<ResComentario>();
		Statement st 	= conn.createStatement();
		ResultSet rs 	= null;
		String comando	= "";
		
		try{
			comando = "SELECT FOLIO, CODIGO_PERSONAL, RESIDENCIA_ID, COMENTARIO " +
					"FROM ENOC.RES_COMENTARIO WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' " + 
					"AND RESIDENCIA_ID = '"+residencia+"'"+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				ResComentario com = new ResComentario();
				com.mapeaReg(rs);
				lisCom.add(com);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.ComentarioUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCom;
	}
}