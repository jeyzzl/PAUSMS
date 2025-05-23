package aca.vigilancia;

import java.sql.*;
import java.util.ArrayList;

public class VigAutoUtil{		
	
	public static ArrayList<VigAuto> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<VigAuto> lis	= new ArrayList<VigAuto>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		
		try{
			comando = "SELECT AUTO_ID, PLACAS, SERIE, ENGOMADO, ALUMNO," +
 				" EMPLEADO, OTRO, COLOR, MODELO, MARCA, POLIZA " +
 				" FROM ENOC.VIG_AUTO "+orden;			 
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				VigAuto vig = new VigAuto();
				vig.mapeaReg(rs);
				lis.add(vig);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.VigAutoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}			
		
		return lis;
	}
	
	
	public ArrayList<VigAuto> getLista(Connection conn, String placas, String orden ) throws SQLException{
		
		ArrayList<VigAuto> lisUsuarios	= new ArrayList<VigAuto>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT AUTO_ID, PLACAS, SERIE, ENGOMADO, " +
				" ALUMNO, EMPLEADO, OTRO, COLOR, MODELO, MARCA, POLIZA " +
				" FROM ENOC.VIG_AUTO WHERE PLACAS LIKE UPPER('%"+placas+"%') "+ 
				" OR ENGOMADO LIKE UPPER('%"+placas+"%') "+
				" OR MARCA LIKE UPPER('%"+placas+"%')" +
				" OR COLOR LIKE UPPER('%"+placas+"%')" +orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				VigAuto usuario = new VigAuto();
				usuario.mapeaReg(rs);
				lisUsuarios.add(usuario);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.VigAutoUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisUsuarios;
	}

}