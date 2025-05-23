package aca.salida;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class SalidaGrupoUtil {
	public ArrayList<SalidaGrupo> getListAll(Connection conn, String orden ) throws SQLException{
		ArrayList<SalidaGrupo> listSalidaGrupo 	= new ArrayList<SalidaGrupo>();
		Statement st 	= conn.createStatement();
		ResultSet rs 	= null;
		String comando	= "";
		
		try{
			comando = "SELECT GRUPO_ID, GRUPO_NOMBRE, RESPONSABLE, CORREO, USUARIOS"+			        
					" FROM ENOC.SAL_GRUPO "+orden; 
			
			rs = st.executeQuery(comando);			
			while (rs.next()){
				
				SalidaGrupo grupo = new SalidaGrupo();				
				grupo.mapeaReg(rs);
				listSalidaGrupo.add(grupo);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.salida.SalidaGrupoUtil|getListAll|:"+ex);
		}finally{		
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return listSalidaGrupo;
	}
	
	public ArrayList<SalidaGrupo> getListUsuario(Connection conn, String codigoPersonal, String orden ) throws SQLException{
		ArrayList<SalidaGrupo> listSalidaGrupo 	= new ArrayList<SalidaGrupo>();
		Statement st 	= conn.createStatement();
		ResultSet rs 	= null;
		String comando	= "";
		
		try{
			comando = "SELECT GRUPO_ID, GRUPO_NOMBRE, RESPONSABLE, CORREO, USUARIOS"+			        
					" FROM ENOC.SAL_GRUPO WHERE USUARIOS LIKE '%-"+codigoPersonal+"-%' "+orden; 
			
			rs = st.executeQuery(comando);			
			while (rs.next()){
				
				SalidaGrupo grupo = new SalidaGrupo();				
				grupo.mapeaReg(rs);
				listSalidaGrupo.add(grupo);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.salida.SalidaGrupoUtil|getListAll|:"+ex);
		}finally{		
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return listSalidaGrupo;
	}
	
	public static String getGrupoNombre(Connection conn, String grupoId) throws SQLException{
		PreparedStatement ps	= null;		
		ResultSet rs			= null;
		String nombre 			= "-";		
		try{
			ps = conn.prepareStatement("SELECT GRUPO_NOMBRE FROM ENOC.SAL_GRUPO WHERE GRUPO_ID = TO_NUMBER(?,'99')" ); 
			ps.setString(1, grupoId);
			rs = ps.executeQuery();
			if (rs.next())
				nombre = rs.getString("GRUPO_NOMBRE");
			
		}catch(Exception ex){
			System.out.println("Error - aca.salida.SalidaGrupo|getGrupoNombre|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
	
}