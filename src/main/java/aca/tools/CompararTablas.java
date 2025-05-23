/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package aca.tools;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

/**
 *
 * @author DavidBlanco
 */
import java.io.*;

public class CompararTablas {
    
	static ArrayList<String> tablas = new ArrayList<String>();
	
	public static ArrayList<String> leerDiferentes(ArrayList<String> tablas, ArrayList<String> tablas2){
		for(String t2 : tablas2){
			for(int i=0; i<tablas.size(); i++){
				if(tablas.get(i).equals(t2)){
					tablas.remove(t2);
				}
			}
		}
		return tablas;
    }
	
	public static ArrayList<String> leerIguales(ArrayList<String> tablas, ArrayList<String> tablas2){
		ArrayList<String> tmp = new ArrayList<String>();
		for(String t2 : tablas){
			for(int i=0; i<tablas2.size(); i++){
				if(tablas2.get(i).equals(t2)) tmp.add(tablas2.remove(i));
			}
		}
		System.out.println("Existen en OREB que en RIGEL no: "+tablas2.size());
		System.out.println(tablas2+"\n");
		return tmp;
	}
	
	public static ArrayList<String> cargarTablas(int i){
		Connection conn			= null;
		Statement stmt			= null;
		
		try{				
			//System.out.print("Creando conexion...");
			DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver ());
			conn = DriverManager.getConnection("jdbc:oracle:thin:@172.16.254.20:1521:ora1","enoc","caminacondios");
			stmt = conn.createStatement();
			//System.out.println("OK!");
		}catch (Exception ex){
			ex.printStackTrace();
		}
		try{
			
			ResultSet rs 		= null;
			String comando		= "";
			
			try{
				comando = " SELECT TABLE_NAME FROM SYS.ALL_ALL_TABLES WHERE OWNER = 'ENOC' ORDER BY 1";			
				rs = stmt.executeQuery(comando);
				while (rs.next()){
					tablas.add(rs.getString("TABLE_NAME").trim());
				}
				
			}catch(Exception ex){
				System.out.println("Error:aca.tools.ComparaTablas"+ex);
			}finally{
				try { rs.close(); } catch (Exception ignore) { }
				try { stmt.close(); } catch (Exception ignore) { }
			}				
		}catch(Exception e){
			
		}finally{
			try{
				stmt.close();
				conn.close();
			}catch(Exception e){
				System.out.println(e);
			}
		}
    	
    	
    	
    	ArrayList<String> tablas2 = new ArrayList<String>();
		
		Connection conn2			= null;
		Statement stmt2			= null;
		
		try{				
			//System.out.print("Creando conexion...");
			DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver ());
			conn2 = DriverManager.getConnection("jdbc:oracle:thin:@172.16.7.97:1521:XE","enoc","caminacondios");
			stmt2 = conn2.createStatement();
			//System.out.println("OK!");
		}catch (Exception ex){
			ex.printStackTrace();
		}
		try{
			
			ResultSet rs 		= null;
			String comando		= "";
			
			try{
				comando = " SELECT TABLE_NAME FROM SYS.ALL_ALL_TABLES WHERE OWNER = 'ENOC' ORDER BY 1";			
				rs = stmt2.executeQuery(comando);
				while (rs.next()){
					tablas2.add(rs.getString("TABLE_NAME").trim());
				}
				
			}catch(Exception ex){
				System.out.println("Error:"+ex);
			}finally{
				try { rs.close(); } catch (Exception ignore) { }
				if (stmt2!=null) stmt2.close();
			}				
		}catch(Exception e){
			
		}finally{
			try{
				stmt2.close();
				conn2.close();
			}catch(Exception e){
				System.out.println(e);
			}
		}
		
    	ArrayList<String> tmp = new ArrayList<String>();
    	if(i == 0) tmp = leerDiferentes(tablas, tablas2);
    	else tmp = leerIguales(tablas, tablas2);
    	return tmp;
	}

    public static void main(String[]args)throws FileNotFoundException, IOException{		
    	ArrayList<String> tablas = cargarTablas(0);
		System.out.println(tablas.size());
		
		for(String tabla: tablas){
			System.out.println(tabla);
		}
    }
}