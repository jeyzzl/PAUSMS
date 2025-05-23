package aca.vista;

import java.sql.*;
import java.util.ArrayList;

public class AlumnoBloqueoUtil {
	
	public ArrayList<AlumnoBloqueo> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<AlumnoBloqueo> lista		= new ArrayList<AlumnoBloqueo>();
		Statement st 						= conn.createStatement();
		ResultSet rs 						= null;
		String comando						= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, CARGA_ID, MODALIDAD_ID, FACULTAD_ID, CARRERA_ID FROM ENOC.ALUMNO_BLOQUEO "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AlumnoBloqueo alumnoEficiencia = new AlumnoBloqueo();
				alumnoEficiencia.mapeaReg(rs);
				lista.add(alumnoEficiencia);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoBloqueoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lista;
	}	

}