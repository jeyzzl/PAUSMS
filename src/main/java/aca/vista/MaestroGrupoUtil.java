//Clase para la vista MAESTRO_GRUPOS

package aca.vista;

import java.sql.*;
import java.util.ArrayList;

public class MaestroGrupoUtil {

	public ArrayList<MaestroGrupo> getListAll(Connection conn, String orden ) throws SQLException{
			
		ArrayList<MaestroGrupo> lisMaestro	= new ArrayList<MaestroGrupo>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = "SELECT MAESTRO_CARRERA, CODIGO_PERSONAL, CARRERA_ID, " +
					"NUM_GRUPOS FROM ENOC.MAESTRO_GRUPOS "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				MaestroGrupo maestro = new MaestroGrupo();
				maestro.mapeaReg(rs);
				lisMaestro.add(maestro);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.MaestroGrupoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisMaestro;
	}	
	
	public ArrayList<MaestroGrupo> getLista(Connection conn, String codigoPersonal, String orden ) throws SQLException{
		
		ArrayList<MaestroGrupo> lisMaestro	= new ArrayList<MaestroGrupo>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
	
		try{
			comando = "SELECT MAESTRO_CARRERA, CODIGO_PERSONAL, CARRERA_ID, "+
					"NUM_GRUPOS FROM ENOC.MAESTRO_GRUPOS "+
					"WHERE CODIGO_PERSONAL ='"+codigoPersonal+"' "+orden;
		
			rs = st.executeQuery(comando);
			while (rs.next()){
			
				MaestroGrupo maestro = new MaestroGrupo();
				maestro.mapeaReg(rs);
				lisMaestro.add(maestro);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.vista.MaestroGrupoUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
	
		return lisMaestro;
	}	
}