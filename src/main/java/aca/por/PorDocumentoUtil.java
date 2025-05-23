package aca.por;

import java.sql.*;
import java.util.ArrayList;

public class PorDocumentoUtil {
	
	public ArrayList<PorDocumento> getListAll(Connection conn, String orden ) throws SQLException{
	
		ArrayList<PorDocumento> lista	= new ArrayList<PorDocumento>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
	 
		try{
			comando = "SELECT POR_ID, POR_NOMBRE, ESTADO FROM ENOC.POR_DOCUMENTO "+orden; 
			rs = st.executeQuery(comando);
			while (rs.next()){
			
				PorDocumento por = new PorDocumento();
				por.mapeaReg(rs);
				lista.add(por);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.por.PorDocumentoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
	
		return lista;
	}	
	
	public ArrayList<PorDocumento> listPorEstado(Connection conn, String estado, String orden ) throws SQLException{
		
		ArrayList<PorDocumento> lista	= new ArrayList<PorDocumento>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
	 
		try{
			comando = "SELECT POR_ID, POR_NOMBRE, ESTADO FROM ENOC.POR_DOCUMENTO WHERE ESTADO IN ("+estado+") "+orden; 
			rs = st.executeQuery(comando);
			while (rs.next()){
			
				PorDocumento por = new PorDocumento();
				por.mapeaReg(rs);
				lista.add(por);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.por.PorDocumentoUtil|getListActivos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
	
		return lista;
	}
	
}