package aca.financiero;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class FesCcPagareDetUtil {
	public ArrayList<FesCcPagareDet> getListAll(Connection conn, String orden) throws SQLException{
		ArrayList<FesCcPagareDet> lista 	= new ArrayList<FesCcPagareDet>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		try{			
			comando = "SELECT MATRICULA, CARGA_ID, BLOQUE, FOLIO, FVENCIMIENTO, IMPORTE, STATUS" +
	 				" FROM MATEO.FES_CC_PAGARE_DET "+orden;
			rs = st.executeQuery(comando);
			while (rs.next()){
				FesCcPagareDet acceso = new FesCcPagareDet();
				acceso.mapeaReg(rs);
				lista.add(acceso);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FesCcPagareDetUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lista;
	}
	
	public ArrayList<FesCcPagareDet> getPagares(Connection conn, String codigoPersonal, String cargaId, String bloque, String orden) throws SQLException{
		ArrayList<FesCcPagareDet> lista 	= new ArrayList<FesCcPagareDet>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		try{			
			comando = "SELECT MATRICULA, CARGA_ID, BLOQUE, FOLIO, TO_CHAR(FVENCIMIENTO,'DD/MM/YYYY') AS FVENCIMIENTO, IMPORTE, STATUS" +
					" FROM MATEO.FES_CC_PAGARE_DET " +
					" WHERE MATRICULA = '"+codigoPersonal+"' AND CARGA_ID = '"+cargaId+"' AND BLOQUE = '"+bloque+"' "+orden;
			rs = st.executeQuery(comando);
			while (rs.next()){
				FesCcPagareDet acceso = new FesCcPagareDet();
				acceso.mapeaReg(rs);
				lista.add(acceso);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FesCcPagareDetUtil|getPagares|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lista;
	}
	
	// Map de pagares y prorrogas 
	public static HashMap<String, String> mapPagare(Connection conn, String cargas, String tipo, String estados) throws SQLException{
		
		HashMap<String,String> map		= new HashMap<String,String>();
		Statement st 							= conn.createStatement();
		ResultSet rs		 					= null;
		String comando							= "";
				
		try{
			comando = " SELECT MATRICULA, CARGA_ID, BLOQUE, COALESCE(SUM( IMPORTE),0) AS TOTAL"
					+ " FROM MATEO.FES_CC_PAGARE_DET"
					+ " WHERE MATRICULA||CARGA_ID||BLOQUE IN"										
					+ " (SELECT CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID FROM ENOC.ALUM_ESTADO WHERE CARGA_ID IN ("+cargas+") AND ESTADO IN("+estados+"))"
					+ " AND STATUS IN("+tipo+")"
					+ " GROUP BY MATRICULA, CARGA_ID, BLOQUE";
			rs = st.executeQuery(comando);
			//System.out.println(comando);
			while (rs.next()){				
				map.put(rs.getString("MATRICULA")+rs.getString("CARGA_ID")+rs.getString("BLOQUE"), rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FesCCMovimientoUtil|mapPagare|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
}