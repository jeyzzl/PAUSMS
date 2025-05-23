// Clase para la tabla de Acceso
package aca.afe;

import java.sql.*;
import java.util.ArrayList;

public class ContratoUtil{
		
	public ArrayList<AfeContratoAlumno> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<AfeContratoAlumno> lisAcceso 	= new ArrayList<AfeContratoAlumno>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{			
			comando = "SELECT ID,EJERCICIO_ID, CCOSTO_ID, PUESTO_ID, TURNO,DIAS," +
					" REQUISITOS, EMAIL, MAXIMO_HORAS, CLAVE, STATUS" +
					" FROM NOE.AFE_CCOSTO_PUESTO "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AfeContratoAlumno acceso = new AfeContratoAlumno();
				acceso.mapeaReg(rs);
				lisAcceso.add(acceso);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.afe.PuestoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisAcceso;
	}	
	
}