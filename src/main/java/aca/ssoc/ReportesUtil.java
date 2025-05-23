/**
 * 
 */
package aca.ssoc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


/**
 * @author Elifo
 *
 */
public class ReportesUtil {
	public ArrayList<String> getListAlum(Connection conn, String orden ) throws SQLException{
		
		ArrayList<String> lisAlum			= new ArrayList<String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT" +
					" DISTINCT(A.CODIGO_PERSONAL||A.PLAN_ID)," +
					" A.CODIGO_PERSONAL," +
					" ENOC.ALUM_APELLIDO(A.CODIGO_PERSONAL) AS NOMBRE," +
					" ALUM_FNAC(A.CODIGO_PERSONAL) AS FECHA_NAC," +
					" ENOC.NOMBRE_CARRERA(ENOC.CARRERA(A.PLAN_ID)) AS CARRERA," +
					" SSOC_BUSCADOC(A.CODIGO_PERSONAL,23) AS LIBERACION," +
					" SSOC_BUSCADOC(A.CODIGO_PERSONAL,22) AS TERMINACION," +
					" SSOC_HORAS(A.CODIGO_PERSONAL,PLAN_ID) AS HORAS," +
					" ALUM_INSCRITO(A.CODIGO_PERSONAL) AS INSCRITO," +
					" SSOC_MAXFECHA(A.CODIGO_PERSONAL) AS ULTIMO_REPORTE" +
					" FROM ENOC.SSOC_DOCALUM A "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				lisAlum.add(rs.getString("CODIGO_PERSONAL"));
				lisAlum.add(rs.getString("NOMBRE"));
				lisAlum.add(rs.getString("FECHA_NAC"));
				lisAlum.add(rs.getString("CARRERA"));
				lisAlum.add(rs.getString("LIBERACION"));
				lisAlum.add(rs.getString("TERMINACION"));
				lisAlum.add(rs.getString("HORAS"));
				lisAlum.add(rs.getString("INSCRITO"));
				lisAlum.add(rs.getString("ULTIMO_REPORTE"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.ReportesUtil|getListAlum|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisAlum;
	}	
}