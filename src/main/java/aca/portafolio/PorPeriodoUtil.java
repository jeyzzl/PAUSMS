package aca.portafolio;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class PorPeriodoUtil {
	
	public ArrayList<PorPeriodo> getListAll(Connection conn, String orden ) throws SQLException{
	
		ArrayList<PorPeriodo> lisPeriodo	= new ArrayList<PorPeriodo>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
	 
		try{
			comando = "SELECT PERIODO_ID, PERIODO_NOMBRE, FECHA_INI, FECHA_FIN, ESTADO "+
					"FROM DANIEL.POR_PERIODO "+orden; 
			rs = st.executeQuery(comando);
			while (rs.next()){
			
				PorPeriodo periodo = new PorPeriodo();
				periodo.mapeaReg(rs);
				lisPeriodo.add(periodo);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.PorPeriodoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
	
		return lisPeriodo;
	}
	
	public HashMap<String,PorPeriodo> getMapAll(Connection conn, String orden ) throws SQLException{
		
		HashMap<String,PorPeriodo> map = new HashMap<String,PorPeriodo>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = "SELECT PERIODO_ID, PERIODO_NOMBRE, FECHA_INI, FECHA_FIN, ESTADO "+
					"FROM DANIEL.POR_PERIODO "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				PorPeriodo obj = new PorPeriodo();
				obj.mapeaReg(rs);
				llave = obj.getPeriodoId();
				map.put(llave, obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.PorPeriodoUtil|getMapAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}	
}