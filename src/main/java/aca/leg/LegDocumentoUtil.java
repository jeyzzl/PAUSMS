package aca.leg;

import java.sql.*;
import java.util.ArrayList;

public class LegDocumentoUtil {
	
	public static String descripcion(Connection conn, String idDocumentos) throws SQLException{
 		ResultSet rs			= null;
 		PreparedStatement ps	= null;
 		String desc="";
 		
 		try{
 			ps = conn.prepareStatement("SELECT COALESCE(DESCRIPCION,'VACIO') AS DESCRIPCION FROM ENOC.LEG_DOCUMENTOS WHERE IDDOCUMENTOS = TO_NUMBER(?,'99')");				 
 			ps.setString(1,idDocumentos);
 			rs = ps.executeQuery();
 			if (rs.next()){
				desc = rs.getString("DESCRIPCION");
 			}			
 		}catch(Exception ex){
 			System.out.println("Error - aca.leg.LegDocumentoUtil|descripcion|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return desc;
 	}
	
	public ArrayList<LegDocumento> getLista(Connection conn, String orden) throws SQLException{
		ArrayList<LegDocumento> lisDoc 		= new ArrayList<LegDocumento>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando="SELECT IDDOCUMENTOS, DESCRIPCION, IMAGEN FROM ENOC.LEG_DOCUMENTOS "+orden; 
			rs=st.executeQuery(comando);
			while (rs.next()){
				LegDocumento documento= new LegDocumento();
				documento.mapeaReg(rs);
				lisDoc.add(documento);
			}
		}catch (Exception ex){
			System.out.println("Error - aca.leg.LegDocUtil|getLista|:"+ex);
		}finally{
			if(rs!=null) rs.close();
			if(st!=null) st.close();
		}
		
		return lisDoc;
	}
}