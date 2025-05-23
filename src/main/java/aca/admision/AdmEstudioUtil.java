package aca.admision;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AdmEstudioUtil {
	public ArrayList<AdmEstudio> getAll(Connection conn, String orden) throws SQLException{
		
		ArrayList<AdmEstudio> lisEstudio	= new ArrayList<AdmEstudio>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT FOLIO, ID, TITULO, INSTITUCION, PAIS_ID, ESTADO_ID, CIUDAD_ID, COMPLETO " +
			" FROM SALOMON.ADM_ESTUDIO "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				AdmEstudio estudio = new AdmEstudio();
				estudio.mapeaReg(rs);
				lisEstudio.add(estudio);
			}
			
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmEstudioUtil|getAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEstudio;
	}
	
	public ArrayList<AdmEstudio> getListAFolio(Connection conn, String folio, String orden) throws SQLException{
		
		ArrayList<AdmEstudio> lisEstudio	= new ArrayList<AdmEstudio>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT FOLIO, ID, TITULO, INSTITUCION, PAIS_ID, ESTADO_ID, CIUDAD_ID, COMPLETO " +
			" FROM SALOMON.ADM_ESTUDIO WHERE FOLIO = '"+folio+"'"+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				AdmEstudio estudio = new AdmEstudio();
				estudio.mapeaReg(rs);
				lisEstudio.add(estudio);
			}
			
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmEstudioUtil|getListAFolio|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEstudio;
	}
}