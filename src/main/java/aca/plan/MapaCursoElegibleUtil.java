package aca.plan;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MapaCursoElegibleUtil {
	
	public ArrayList<String> getMaterias(Connection conn, String orden ) throws SQLException{
		
		ArrayList<String> list				= new ArrayList<String>();
		Statement st 						= conn.createStatement();
		ResultSet rs 						= null;
		String comando						= "";
		
		try{
			comando = "SELECT CURSO_ID FROM ENOC.MAPA_CURSO_ELEGIBLE WHERE ESTADO = 'A' "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				list.add(rs.getString("CURSO_ID"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaCursoElegibleUtil|getMaterias|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return list;
	}
	
}
