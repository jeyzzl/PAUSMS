package aca.well;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class WellDisposicionUtil {
	
	
	public ArrayList<WellDisposicion> getlistAll(Connection con, String orden) throws SQLException{
		
		ResultSet rs 					= null;
		PreparedStatement ps			= null;
		String comando					= "";
		ArrayList<WellDisposicion> lis 	= new ArrayList<WellDisposicion>();

		try{
			
			comando = "SELECT CODIGO_PERSONAL, CARDIACO, DOLOR, DOLOR_MES, VERTIGO, OSEO, PRESION, RAZON"
					+ " FROM ENOC.WELL_DISPOSICION "+orden;
			
			ps = con.prepareStatement(comando);		
			
			rs = ps.executeQuery();
			while (rs.next()){
				
				WellDisposicion obj = new WellDisposicion();				
				obj.mapeaReg(rs);
				lis.add(obj);		
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.well.WellDisposicionUtil|getlistAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
	
	public HashMap<String, WellDisposicion> getMapaAll(Connection conn) throws SQLException{
		HashMap<String, WellDisposicion> mapDisposicion	= new HashMap<String, WellDisposicion>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			
			comando = "SELECT CODIGO_PERSONAL, CARDIACO, DOLOR, COLOR_MES, VERTIGO, OSEO, PRESION, RAZON"
					+ " FROM ENOC.WELLNESS_DISPOSICION ";
			rs = st.executeQuery(comando);
			while (rs.next()){				
				WellDisposicion obj = new WellDisposicion();
				obj.mapeaReg(rs);
				mapDisposicion.put(obj.getCodigoPersonal(), obj);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoUtil|getMapaAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapDisposicion;
	}
	

}