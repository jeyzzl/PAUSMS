package adm.alumno;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AdmUbicacionUtil {
	public ArrayList<AdmUbicacion> getAll(Connection conn, String orden) throws SQLException{
			
			ArrayList<AdmUbicacion> lisUbicacion	= new ArrayList<AdmUbicacion>();
			Statement st 		= conn.createStatement();
			ResultSet rs 		= null;
			String comando		= "";
			
			try{
				comando = "SELECT FOLIO, PAIS_ID, ESTADO_ID, CIUDAD_ID, CALLE," +
				" NUMERO, CODIGO_POSTAL, TELEFONO, COLONIA FROM SALOMON.ADM_UBICACION "+ orden; 
				
				rs = st.executeQuery(comando);
				while (rs.next()){
					AdmUbicacion ubicacion = new AdmUbicacion();
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