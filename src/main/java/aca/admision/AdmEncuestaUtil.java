package aca.admision;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AdmEncuestaUtil {
	public ArrayList<AdmEncuesta> getAll(Connection conn, String orden) throws SQLException{
		
		ArrayList<AdmEncuesta> list	= new ArrayList<AdmEncuesta>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT FOLIO, RECOMENDACION_ID, R1, R2, R3, R4, R5, R6, R7, R8, R9, R10,R11,R12,R13, " +
			" TIEMPO, CONOCER, RELACION, CONDUCTA, OPINION, CENSURA, ADICIONAL, OTRA FROM SALOMON.ADM_ENCUESTA "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				AdmEncuesta recomienda = new AdmEncuesta();
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
}