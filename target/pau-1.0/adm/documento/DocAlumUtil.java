// Clase Util para la tabla de Adm_requisito
package adm.documento;

import java.sql.*;
import java.util.ArrayList;

public class DocAlumUtil{
		
	public ArrayList<AdmDocAlum> getLista(Connection conn, String folio, String orden ) throws SQLException{
		
		ArrayList<AdmDocAlum> lisDocAlum	= new ArrayList<AdmDocAlum>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT FOLIO, DOCUMENTO_ID, ESTADO, USUARIO, COMENTARIO, CARTA"+
				" FROM SALOMON.ADM_DOCALUM" + 
				" WHERE FOLIO = TO_NUMBER('"+folio+"','99999999') "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AdmDocAlum docAlum = new AdmDocAlum();
				docAlum.mapeaReg(rs);
				lisDocAlum.add(docAlum);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.DocAlumUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisDocAlum;
	}
	
	public ArrayList<AdmDocAlum> getListAll(Connection conn, String orden ) throws SQLException{
	
		ArrayList<AdmDocAlum> lisDocAlum	= new ArrayList<AdmDocAlum>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = "SELECT FOLIO, DOCUMENTO_ID, ESTADO, USUARIO, COMENTARIO, CARTA FROM SALOMON.ADM_DOCALUM "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AdmDocAlum docAlum = new AdmDocAlum();
				docAlum.mapeaReg(rs);
				lisDocAlum.add(docAlum);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.RequisitoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisDocAlum;
	}		
}