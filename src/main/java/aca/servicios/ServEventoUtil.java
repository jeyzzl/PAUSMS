package aca.servicios;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ServEventoUtil {
	
	public ArrayList<ServEvento> getListAll(Connection conn, String orden ) throws SQLException{
	
		ArrayList<ServEvento> lisEvento	= new ArrayList<ServEvento>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
	 
		try{
			comando = "SELECT" +
					" EVENTO_ID, EVENTO_NOMBRE," +
					" TO_CHAR(FECHA_INICIO,'DD/MM/YYYY') AS FECHA_INICIO," +
					" TO_CHAR(FECHA_FINAL,'DD/MM/YYYY') AS FECHA_FINAL," +
					" USUARIO" +
					" FROM ENOC.SERV_EVENTO "+orden; 
			rs = st.executeQuery(comando);
			while (rs.next()){
			
				ServEvento evento = new ServEvento();
				evento.mapeaReg(rs);
				lisEvento.add(evento);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.servicios.ServEventoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
	
		return lisEvento;
	}
	
	public HashMap<String,ServEvento> getMapAll(Connection conn, String orden ) throws SQLException{
		
		HashMap<String,ServEvento> map = new HashMap<String,ServEvento>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = "SELECT " +
					" EVENTO_ID, EVENTO_NOMBRE," +
					" TO_CHAR(FECHA_INICIO,'DD/MM/YYYY') AS FECHA_INICIO," +
					" TO_CHAR(FECHA_FINAL,'DD/MM/YYYY') AS FECHA_FINAL," +
					" USUARIO" +					
					" FROM ENOC.SERV_EVENTO "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				ServEvento obj = new ServEvento();
				obj.mapeaReg(rs);
				llave = obj.getEventoId();
				map.put(llave, obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.servicios.ServEventoUtil|getMapAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}	
}