package aca.por;

import java.util.ArrayList;
import java.util.HashMap;
import java.sql.*;

public class PorRequisitoEmpUtil {
	
	public ArrayList<PorRequisitoEmp> getListAll(Connection conn, String orden) throws SQLException{
		
		ArrayList<PorRequisitoEmp> lista = new ArrayList<PorRequisitoEmp>();
		Statement st	= conn.createStatement();
		ResultSet rs	= null;
		String comando	= "";
		
		try{
			comando = "SELECT PORTAFOLIO_ID, REQUISITO_ID, EMPLEADO_ID FROM ENOC.POR_REQUISITO_EMP " + orden;
			rs = st.executeQuery(comando);
			while (rs.next()){
				PorRequisitoEmp por = new PorRequisitoEmp();
				por.mapeaReg(rs);
				lista.add(por);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.por.PorRequisitoEmpUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lista;
	}

	public HashMap<String, String> mapPorRequisitoEmp(Connection conn) throws SQLException{
		
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st	= conn.createStatement();
		ResultSet rs	= null;
		String comando	= "";
		String llave	= "";
		
		try{
			comando = "SELECT EMPLEADO_ID, COUNT(REQUISITO_ID) AS REQUISITOS FROM ENOC.POR_REQUISITO_EMP GROUP BY EMPLEADO_ID";
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				llave = rs.getString("EMPLEADO_ID");
				mapa.put(llave, rs.getString("REQUISITOS"));
			}
		}catch (Exception ex){
			System.out.println("Error - aca.por.PorRequisitoEmpUtil|mapPorRequisitoEmp|:"+ex);
		}finally{
			if (rs != null) rs.close();
			if (st != null) st.close();
		}
		return mapa;
	}
	
	public ArrayList<String> getListReq(Connection conn) throws SQLException{
		
		ArrayList<String> listReq = new ArrayList<String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
	 
		try{
			comando = "SELECT DISTINCT (CODIGO_PERSONAL) FROM ENOC.POR_REQUISITO_EMP";
			rs = st.executeQuery(comando);
			while (rs.next()){			
				listReq.add(rs.getString("CODIGO_PERSONAL"));
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.por.PorRequisitoEmpUtil|getListReq|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
	
		return listReq;
	}
}
