package aca.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class mapaCurricular {
	public static ArrayList<String> al = null;
	
	public static ArrayList<String> mapaCurricular(Connection conn, String cursoId)throws SQLException{
		al = new ArrayList<String>();
		buscar(conn, cursoId);
		//System.out.println(al);
		return al;
	}
	
	public static void buscar(Connection conn, String cursoId)throws SQLException{
		String query;
		query="select curso_id_pre from ENOC.mapa_curso_pre where curso_id=?"; 
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement(query);
			ps.setString(1, cursoId);
			rs = ps.executeQuery();
			while(rs.next()){
				al.add(rs.getString(1));
				buscar(conn, rs.getString(1));
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.Materia|getPrerrequisitos|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
			try { rs.close(); } catch (Exception ignore) { }
		}
	}
	
}