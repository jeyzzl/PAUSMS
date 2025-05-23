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
 * @author JoseTorres
 */

public class DeleteColumnas {
	
    // Llena columnas de la base de datos UNAV
	public static ArrayList<String> columnasUNAV() throws SQLException{
		ArrayList<String> columnas = new ArrayList<String>();
		
		Connection conn		= null;
		Statement stmt		= null;
		ResultSet rs 		= null;
		String comando		= "";
		
		
		try{
			
			DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver ());
			conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.8:1521:XE","enoc","caminacondios");
			stmt = conn.createStatement();
						
			comando = "SELECT TABLE_NAME, COLUMN_NAME, DATA_TYPE, DATA_LENGTH, COALESCE(DATA_PRECISION,0) AS DATA_PRECISION, NULLABLE" +
					" FROM SYS.ALL_TAB_COLUMNS WHERE OWNER = 'MATEO' ORDER BY OWNER,1,2";			
			rs = stmt.executeQuery(comando);
			while (rs.next()){
				
				columnas.add(rs.getString("TABLE_NAME")+","+rs.getString("COLUMN_NAME"));
			}
							
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { stmt.close(); } catch (Exception ignore) { }
			if (conn!=null) conn.close();
		}
		
		return columnas;
	}
	
	//Llena HashSet con columnas de la base de datos de UM  
	public static HashSet<String> columnasUM() throws SQLException{
		HashSet<String> columnas = new HashSet<String>();
		
		Connection conn		= null;
		Statement stmt		= null;
		ResultSet rs 		= null;
		String comando		= "";
		
		try{				
			DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver ());
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","enoc","caminacondios");
			stmt = conn.createStatement();
			
			comando = " SELECT TABLE_NAME, COLUMN_NAME FROM SYS.ALL_TAB_COLUMNS WHERE OWNER = 'MATEO'";	
			rs = stmt.executeQuery(comando);
			while (rs.next()){
				columnas.add(rs.getString("TABLE_NAME")+rs.getString("COLUMN_NAME"));
			}
							
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { stmt.close(); } catch (Exception ignore) { }
			if (conn!=null) conn.close();			
		}
		
		return columnas;
	}

	// Busca columnas de UNAV que no existan en UM y crea el script para borrarlas.
    public static void main(String[]args) throws SQLException{
    	
    	ArrayList<String> lisColumnas 	= columnasUNAV(); System.out.println(lisColumnas.size());
    	HashSet<String> setColumnas 	= columnasUM(); System.out.println(setColumnas.size());
    	String borrarColumna= "";
    	for(String columna: lisColumnas){
    		
			String[] arregloCol = columna.split(",");
			if (arregloCol!=null)
				if (!setColumnas.contains(arregloCol[0].toString()+arregloCol[1].toString()) ){

					borrarColumna = "ALTER TABLE MATEO."+arregloCol[0]+" DROP COLUMN "+arregloCol[1]+";";
					System.out.println(borrarColumna);
				}	
		}
		    
    }
}