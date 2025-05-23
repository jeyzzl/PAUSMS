package aca.rol;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

import aca.rol.RolOpcion;

public class RolOpcionUtil{
	
	public ArrayList<RolOpcion> getListAll(Connection conn, String orden) throws SQLException{
		
		ArrayList<RolOpcion> lis		= new ArrayList<RolOpcion>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT ROL_ID, OPCION_ID FROM ENOC.ROL_OPCION"+orden;	 
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				RolOpcion obj = new RolOpcion();
				obj.mapeaReg(rs);
				lis.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.rol.RolOpcion|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
	
	public HashMap<String, String> getOpciones(Connection conn, String rolId ) throws SQLException{
		
		HashMap<String, String> lisDisciplinaPer = new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = " SELECT ROL_ID, OPCION_ID FROM ENOC.ROL_OPCION"+
					  " WHERE ROL_ID = '"+rolId+"' ";
			
			rs = st.executeQuery(comando);
			while(rs.next()){
				lisDisciplinaPer.put(rs.getString("ROL_ID")+"@@"+rs.getString("OPCION_ID"), rs.getString("OPCION_ID"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.rol.RolOpcion|getOpciones|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisDisciplinaPer;
	}
	
	public HashMap<String, String> mapaOpciones(Connection conn, String rolId ) throws SQLException{
		
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = " SELECT OPCION_ID FROM ENOC.ROL_OPCION WHERE ROL_ID = '"+rolId+"' ";
			
			rs = st.executeQuery(comando);
			while(rs.next()){
				mapa.put(rs.getString("OPCION_ID"), rs.getString("OPCION_ID"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.rol.RolOpcion|mapaOpciones|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return mapa;
	}
	
}