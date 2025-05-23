package aca.residencia;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ResCandadoUtil {

public ArrayList<ResCandado> getListAll(Connection conn, String orden) throws SQLException{
		
		ArrayList<ResCandado> lisColonia = new ArrayList<ResCandado>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, COMENTARIO, USUARIO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, ESTADO FROM ENOC.RES_CANDADO "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				ResCandado colonia = new ResCandado();
				colonia.mapeaReg(rs);
				lisColonia.add(colonia);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.CandadoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisColonia;
	}
}
