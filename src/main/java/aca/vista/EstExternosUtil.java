//Clase para la vista MOD_OPCION

package aca.vista;

import java.sql.*;
import java.util.ArrayList;

public class EstExternosUtil{
	
	public ArrayList<EstExternos> getListAll (Connection conn, String orden) throws SQLException{
		
		ArrayList<EstExternos> lisExternos 			= new ArrayList<EstExternos>();
		Statement st	 			= conn.createStatement();
		ResultSet rs				= null;
		String s_command			= "";
		
		try {
			s_command = "SELECT FACULTAD_ID, CARRERA_ID, CODIGO_PERSONAL, RAZON, FECHA "+
				"FROM ENOC.ESTEXTERNOS "+ orden;
			rs = st.executeQuery (s_command);
			while (rs.next()){
				
				EstExternos externos = new EstExternos();
				externos.mapeaReg(rs);
				lisExternos.add(externos);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstExternosUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisExternos;
	}
	
	public ArrayList<EstExternos> getListAllPorFecha(Connection conn, String orden, String fechaIni, String fechaFin) throws SQLException{
		
		ArrayList<EstExternos> lisExternos 			= new ArrayList<EstExternos>();
		Statement st	 			= conn.createStatement();
		ResultSet rs				= null;
		String s_command			= "";
		
		try {
			s_command = "SELECT FACULTAD_ID, CARRERA_ID, CODIGO_PERSONAL, RAZON, FECHA "+
				"FROM ENOC.ESTEXTERNOS WHERE FECHA BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFin+"','DD/MM/YYYY') "+ orden;
			rs = st.executeQuery (s_command);
			while (rs.next()){
				
				EstExternos externos = new EstExternos();
				externos.mapeaReg(rs);
				lisExternos.add(externos);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.EstExternosUtil|getListAllPorFecha|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisExternos;
	}
}