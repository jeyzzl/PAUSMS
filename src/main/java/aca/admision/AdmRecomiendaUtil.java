package aca.admision;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AdmRecomiendaUtil {
	public ArrayList<AdmRecomienda> getAll(Connection conn, String orden) throws SQLException{
		
		ArrayList<AdmRecomienda> list	= new ArrayList<AdmRecomienda>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT FOLIO, RECOMENDACION_ID, NOMBRE, PUESTO, EMAIL, TELEFONO, ESTADO " +
			" FROM SALOMON.ADM_RECOMIENDA "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				AdmRecomienda recomienda = new AdmRecomienda();
				recomienda.mapeaReg(rs);
				list.add(recomienda);
			}
			
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmRecomiendaUtil|getAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return list;
	}
	
	public ArrayList<AdmRecomienda> getListFolio(Connection conn, String folio, String orden) throws SQLException{
		
		ArrayList<AdmRecomienda> list	= new ArrayList<AdmRecomienda>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT FOLIO, RECOMENDACION_ID, NOMBRE, PUESTO, EMAIL, TELEFONO, ESTADO " +
			" FROM SALOMON.ADM_RECOMIENDA WHERE FOLIO = '"+folio+"' "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				AdmRecomienda recomienda = new AdmRecomienda();
				recomienda.mapeaReg(rs);
				list.add(recomienda);
			}
			
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmRecomiendaUtil|getListFolio|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return list;
	}
}