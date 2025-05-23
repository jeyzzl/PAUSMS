package aca.financiero;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class ContConceptoUtil {
	
	public HashMap<String, ContConcepto> getMapContConcepto(Connection conn) throws SQLException{
		HashMap<String, ContConcepto> mapCcosto = new HashMap<String, ContConcepto>();
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;		 
		String comando	= "";
		
		try{			
			comando = "SELECT ID, VERSION, DESCRIPCION, STATUS, NOMBRE, TAGS FROM MATEO.CONT_CONCEPTO";
			
			rs = st.executeQuery(comando);
			while(rs.next()){
				ContConcepto contConcepto = new ContConcepto();
				contConcepto.mapeaReg(rs);
				mapCcosto.put(contConcepto.getId(), contConcepto);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.ContCeptoUtil|getMapContConcepto|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}		
		return mapCcosto;
	}

}
