package aca.salida;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SalidaAlumnoUtil {

	public ArrayList<SalidaAlumno> getListAll(Connection conn, String salidaId, String orden ) throws SQLException{
		ArrayList<SalidaAlumno> listSalidaGrupo 	= new ArrayList<SalidaAlumno>();
		Statement st 	= conn.createStatement();
		ResultSet rs 	= null;
		String comando	= "";
		
		try{
			comando = "SELECT SALIDA_ID, CODIGO_PERSONAL, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, USUARIO "+			        
					" FROM ENOC.SAL_ALUMNO WHERE SALIDA_ID ='"+salidaId+"' "+orden; 
			
			rs = st.executeQuery(comando);			
			while (rs.next()){
				
				SalidaAlumno grupo = new SalidaAlumno();				
				grupo.mapeaReg(rs);
				listSalidaGrupo.add(grupo);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.salida.SalidaGrupoLista|getListAll|:"+ex);
		}finally{		
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }	
		}
		
		return listSalidaGrupo;
	}
}