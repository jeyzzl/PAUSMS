package aca.financiero;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class FesParamGralDetUtil {
	public ArrayList<FesParamGralDet> getListAll(Connection conn, String orden) throws SQLException{
		ArrayList<FesParamGralDet> lista 	= new ArrayList<FesParamGralDet>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		try{			
			comando = "SELECT CLAVE, CARGA_ID, CONCEPTO, VALOR FROM NOE.FES_PARAMGRALDET "+orden;
			rs = st.executeQuery(comando);
			while (rs.next()){
				FesParamGralDet acceso = new FesParamGralDet();
				acceso.mapeaReg(rs);
				lista.add(acceso);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FesParamGralDetUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lista;
	}
}