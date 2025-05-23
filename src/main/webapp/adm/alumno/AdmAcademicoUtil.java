package adm.alumno;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AdmAcademicoUtil {
	public ArrayList<AdmAcademico> getAll(Connection conn, String orden) throws SQLException{
		
		ArrayList<AdmAcademico> lisUbicacion	= new ArrayList<AdmAcademico>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT FOLIO, MODALIDAD_ID, NIVEL_ID,CARRERA_ID, FECHA " +
			" FROM SALOMON.ADM_ACADEMICO "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				AdmAcademico ubicacion = new AdmAcademico();
				ubicacion.mapeaReg(rs);
				lisUbicacion.add(ubicacion);
			}
			
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmUbicacionUtil|getAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisUbicacion;
	}
}