/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package aca.tools;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 *
 * @author DavidBlanco
 */
import java.io.*;

public class CreateTablas {
    
	public static ArrayList<String> tablasUM() throws SQLException{
		ArrayList<String> tablas = new ArrayList<String>();
		
		Connection conn		= null;
		Statement stmt		= null;
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			
			DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver ());
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","enoc","caminacondios");
			stmt = conn.createStatement();
						
			comando = " SELECT TABLE_NAME FROM SYS.ALL_ALL_TABLES WHERE OWNER = 'NOE' ORDER BY 1";			
			rs = stmt.executeQuery(comando);
			while (rs.next()){
				tablas.add(rs.getString("TABLE_NAME").trim());
			}
							
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { stmt.close(); } catch (Exception ignore) { }
			if (conn!=null) conn.close();			
		}
		
		return tablas;
	}
	
	public static HashMap<String,String> tablasUNAV() throws SQLException{
		HashMap<String,String> tablas = new HashMap<String,String>();
		
		Connection conn		= null;
		Statement stmt		= null;
		ResultSet rs 		= null;
		String comando		= "";
		
		try{				
			DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver ());
			conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.8:1521:XE","enoc","caminacondios");
			stmt = conn.createStatement();
			
			comando = " SELECT TABLE_NAME FROM SYS.ALL_ALL_TABLES WHERE OWNER = 'NOE' ORDER BY 1";			
			rs = stmt.executeQuery(comando);
			while (rs.next()){
				tablas.put(rs.getString("TABLE_NAME").trim(),"1");
			}
							
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { stmt.close(); } catch (Exception ignore) { }
			if (conn!=null) conn.close();			
		}
		
		return tablas;
	}

    public static void main(String[]args)throws FileNotFoundException, IOException, SQLException{
    	ArrayList<String> lisTablas = tablasUM();
    	HashMap<String,String> mapTablas = tablasUNAV();
    	for(String tabla: lisTablas){
    		if (!mapTablas.containsKey(tabla)) 
    			System.out.println(tabla); 
		}
    	System.out.println("¡ Finish no more !");
    }
}