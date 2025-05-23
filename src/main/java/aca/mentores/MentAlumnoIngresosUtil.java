package aca.mentores;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MentAlumnoIngresosUtil {
	public ArrayList<MentAlumnoIngresos> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<MentAlumnoIngresos> lisMentAlumnoIngresos		= new ArrayList<MentAlumnoIngresos>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, CARGA_ID, PROPIOS," +
					" SEMESTRE, COLPORTAJE, BECA" +
					" FROM ENOC.MENT_ALUMNO_INGRESOS "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				MentAlumnoIngresos ingresos = new MentAlumnoIngresos();
				ingresos.mapeaReg(rs);
				lisMentAlumnoIngresos.add(ingresos);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentAlumnoIngresosUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisMentAlumnoIngresos;
	}
	
}