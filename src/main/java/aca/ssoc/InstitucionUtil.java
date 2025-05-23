//Clase  para la tabla SSOC_INSTITUCION
package aca.ssoc;

import java.sql.*;
import java.util.ArrayList;

public class InstitucionUtil {
	public ArrayList<Institucion> getListAll(Connection conn, String Sector_Id, String orden ) throws SQLException{
		ArrayList<Institucion> lisInstitucion 			= new ArrayList<Institucion>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = "SELECT INSTITUCION_ID, " +
					"INSTITUCION_NOMBRE, SECTOR_ID, " +
					"DIRECCION, TELEFONO FROM ENOC.SSOC_INSTITUCION " + 
					"WHERE SECTOR_ID = '"+Sector_Id+"'"+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				Institucion Institucion = new Institucion();				
				Institucion.mapeaReg(rs);
				lisInstitucion.add(Institucion);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.InstitucionUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisInstitucion;
	}
}