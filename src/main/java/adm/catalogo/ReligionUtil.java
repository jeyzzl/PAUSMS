// Clase para la tabla de Modulo
package adm.catalogo;

import java.sql.*;
import java.util.ArrayList;

public class ReligionUtil{
		
	public ArrayList<CatReligion> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<CatReligion> lisReligion = new ArrayList<CatReligion>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT RELIGION_ID, NOMBRE_RELIGION FROM ENOC.CAT_RELIGION "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CatReligion religion = new CatReligion();
				religion.mapeaReg(rs);
				lisReligion.add(religion);
			}
			
		}catch(Exception ex){
			System.out.println("Error - adm.catalogo.ReligionUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisReligion;
	}
	public String getNombreReligion(Connection conn, String id ) throws SQLException{
		
		String nombre = "";
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT NOMBRE_RELIGION FROM ENOC.CAT_RELIGION WHERE religion_id='"+id+"'";
			
			rs = st.executeQuery(comando);
			if (rs.next()){
				nombre = rs.getString(1);
			}
			
		}catch(Exception ex){
			System.out.println("Error - adm.catalogo.ReligionUtil|getNombreReligion|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}	
}