package aca.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
public class CompararColumnasPorTablas {
	public static void main(String[]args){
		
		System.out.println("TABLAS QUE HAY EN RIGEL Y NO EN UNAV:");
		//System.out.println(compararTablas("RIGEL"));
		System.out.println(compararTablas(0));
		System.out.println("TABLAS QUE HAY EN UNAV Y NO EN RIGEL:");
		//System.out.println(compararTablas("OREB"));
		System.out.println(compararTablas(1));
		
	}
	
	public static ArrayList<String> tablasRigel(){
		ArrayList<String> tablas = new ArrayList<String>();
		
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
				System.out.println("Error:aca.tools.ComparaColumnasPorTablas|"+ex);
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
		
		return tablas;
	}

	public static ArrayList<String> tablasOreb(){
		ArrayList<String> tablas = new ArrayList<String>();
		
		Connection conn2			= null;
		Statement stmt2			= null;
		
		try{				
			//System.out.print("Creando conexion...");
			DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver ());
			conn2 = DriverManager.getConnection("jdbc:oracle:thin:@172.16.7.97:1521:xe","enoc","caminacondios");
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
					tablas.add(rs.getString("TABLE_NAME").trim());
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
		return tablas;
	}

	public static ArrayList<String> compararTablas(String BaseDatos){
		ArrayList<String> diferentes = new ArrayList<String>();
		if(BaseDatos.toUpperCase().equals("RIGEL")){//Tablas que hay en rigel y no en UNAV
			for(String str : tablasRigel()){
				System.out.println(str);
				if(!tablasOreb().contains(str)){
					diferentes.add(str);
				}else{
					tablasOreb().remove(str);
				}
			}
		}else if(BaseDatos.toUpperCase().equals("OREB")){//Tablas que hay en UNAV y no en Rigel
			for(String str : tablasOreb()){
				if(!tablasRigel().contains(str)){
					diferentes.add(str);
				}
			}
		}
		return diferentes;
	}
	
	public static ArrayList<String> compararTablas(int numBDatos){
		ArrayList<String> diferentes = new ArrayList<String>();
		if(numBDatos==0){//Tablas que hay en rigel y no en oreb
			for(String str : tablasRigel()){
				System.out.println(str);
				if(!tablasOreb().contains(str)){
					diferentes.add(str);
				}else{
					tablasOreb().remove(str);
				}
			}
		}else if(numBDatos==1){//Tablas que hay en oreb y no en Rigel
			for(String str : tablasOreb()){
				if(!tablasRigel().contains(str)){
					diferentes.add(str);
				}
			}
		}
		return diferentes;
	}
}
