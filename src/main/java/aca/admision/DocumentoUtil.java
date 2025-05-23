// Clase para la tabla del Catalogo de Documentos de Admision
package aca.admision;

import java.sql.*;
import java.util.ArrayList;

public class DocumentoUtil{
		
	public ArrayList<AdmDocumento> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<AdmDocumento> lisDocumento = new ArrayList<AdmDocumento>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT DOCUMENTO_ID, DOCUMENTO_NOMBRE, TIPO, COMENTARIO, ORIGINAL, ORDEN, FORMATO_ID" +
					" FROM SALOMON.ADM_DOCUMENTO "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AdmDocumento doc = new AdmDocumento();
				doc.mapeaReg(rs);
				lisDocumento.add(doc);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.DocumentoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisDocumento;
	}	
	
	public ArrayList<AdmDocumento> getListNewDoc(Connection conn, String nivelId, String orden ) throws SQLException{
		
		ArrayList<AdmDocumento> lisDocumento = new ArrayList<AdmDocumento>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT DOCUMENTO_ID, DOCUMENTO_NOMBRE, TIPO, COMENTARIO, ORIGINAL, ORDEN, FORMATO_ID " +
					" FROM SALOMON.ADM_DOCUMENTO " +
					" WHERE DOCUMENTO_ID NOT IN " +
							" (SELECT DOCUMENTO_ID FROM SALOMON.ADM_REQUISITO" +
							" WHERE NIVEL_ID = "+nivelId+")" + orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AdmDocumento doc = new AdmDocumento();
				doc.mapeaReg(rs);
				lisDocumento.add(doc);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.DocumentoUtil|getListNewDoc|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisDocumento;
	}
}