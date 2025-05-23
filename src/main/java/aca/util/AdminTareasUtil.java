package aca.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class AdminTareasUtil {
	public ArrayList<AdminTareas> getListAll(Connection conn, String orden ) throws SQLException{
		ArrayList<AdminTareas> list 	= new ArrayList<AdminTareas>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = "SELECT FOLIO, TO_CHAR(F_CREA,'DD/MM/YYYY') AS F_CREA, TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO," +
					" TO_CHAR(F_TERMINADO,'DD/MM/YYYY') AS F_TERMINADO, NOMBRE, DESCRIPCION, " +
					" DESARROLLADOR, CLIENTE, MODULO, ESTADO FROM ENOC.ADMIN_TAREAS  "+orden;		
			
			rs = st.executeQuery(comando);			
			while (rs.next()){
				
				AdminTareas obj = new AdminTareas();				
				obj.mapeaReg(rs);
				list.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.util.AdminTareasUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return list;
	}
}
