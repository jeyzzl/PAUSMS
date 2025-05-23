package aca.well;
import java.sql.*;
import java.util.ArrayList;

public class WellAlimentacionUtil {
	
	
	public ArrayList<WellAlimentacion> getlistAll(Connection con, String orden) throws SQLException{
		
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		String comando			= "";
		ArrayList<WellAlimentacion> lis 				= new ArrayList<WellAlimentacion>();

		try{
			
			comando = "SELECT VERDURAS, FRUTAS, CEREALES, AZUCARES, LEGUMINOSAS, PRODUCTOS_ORIGEN, CARNES_VEGETARIANAS, ACEITES, NUECES, ALIMENTOS, CODIGO_PERSONAL "+
	 			" FROM ENOC.WELLNESS_ALIMENTACION"+orden;
			
			ps = con.prepareStatement(comando);		
			
			rs = ps.executeQuery();
			while (rs.next()){
				
				WellAlimentacion alimentacion = new WellAlimentacion();				
				alimentacion.mapeaReg(rs);
				lis.add(alimentacion);		
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.wellness.WellAlimentacionUtil|getlistAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}

}