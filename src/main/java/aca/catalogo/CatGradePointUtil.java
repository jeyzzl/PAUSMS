//Clase  para la tabla Materias_Insc
package aca.catalogo;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class CatGradePointUtil{
	
	public ArrayList<CatGradePoint> getListAll(Connection conn, String orden) throws SQLException{
		
		ArrayList<CatGradePoint> lista		= new ArrayList<CatGradePoint>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT GP_ID, GP_NOMBRE, INICIO, FIN, PUNTOS, TITULO FROM ENOC.CAT_GRADEPOINT "+orden;	 
			rs = st.executeQuery(comando);
			while (rs.next()){
				CatGradePoint carreraU = new CatGradePoint();
				carreraU.mapeaReg(rs);
				lista.add(carreraU);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatGradePointUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lista;
	}
	
	public HashMap<String,CatGradePoint> getMapAll(Connection conn, String orden ) throws SQLException{
		
		HashMap<String,CatGradePoint> map = new HashMap<String,CatGradePoint>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = "SELECT GP_ID, GP_NOMBRE, INICIO, FIN, PUNTOS, TITULO FROM ENOC.CAT_GRADEPOINT "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				CatGradePoint obj = new CatGradePoint();
				obj.mapeaReg(rs);
				llave = obj.getGpId();
				map.put(llave, obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatGradePointUtil|getMapAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}	
	
}