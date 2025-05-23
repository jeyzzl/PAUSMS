// Clase para traer los privilegios del usuario
package aca.conecta;
import java.sql.*;
import java.io.*;

public class Conectar{	
	
	// Coneccion para Oracle con el usuario Enoc ( Incluye Rigel y Oreb )
	public Connection conEnoc( String servidor) throws SQLException{
		Connection conn = null;
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@172.16.254.25:1521:ora12", "enoc", "caminacondios");
			/*
			Context initContext = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			DataSource ds = (DataSource)envContext.lookup("jdbc/academico");
			conn = ds.getConnection();
			*/			
		}catch(Exception e) {
			System.out.println("De plano no me pude conectar jajaja...");
			e.printStackTrace();
		}
		
		return conn;
	}
	
	// Coneccion para Oracle con el usuario Enoc
	public Connection conEnoc() throws SQLException{
		Connection conn = null;
		try{			
			//conn = enoc.getConnection();			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@172.16.254.25:1521:ora12", "enoc", "caminacondios");
			System.out.println("Conexion directa enoc");		
		}catch(Exception e) {
			System.out.println("De plano no me pude conectar jajaja...");
			e.printStackTrace();
		}		
		return conn;
	}
	
	// Desconectar Oracle usuario Enoc
	public void desEnoc(Connection Conn) throws SQLException{
		try{			
			Conn.close();
		}catch(Exception ex){
			System.out.println("Error - aca.conectar.Conectar|desEnoc|:"+ex);
		}
	}
	
	// Desconectar Oracle usuario Enoc (para antes de response.sendRedirect)
	public static boolean cierraEnoc(Connection Conn) throws SQLException{
		boolean ok = false;
		try{			
			Conn.close();
			ok = true;			
		}catch(Exception ex){
			System.out.println("Error - aca.conectar.Conectar|cierraEnoc|:"+ex);
		}
		return ok;
	}
	
	
	// Coneccion para Postgresql
	public Connection conPostgres(){
		Connection Conn = null;		
		try{			
			Class.forName("org.postgresql.Driver");
			Conn = DriverManager.getConnection(Conectar.coneccion(),Conectar.usuario(),Conectar.password());
			System.out.println("Conexion directa archivo");
		}catch(Exception e){
			System.out.println("No es posible conectarse... Error - aca.conectar.Conectar|conPostgres|:");
			e.printStackTrace();
		}		
		return Conn;
	}	
	
	// Coneccion para Postgresql
	public Connection conPostgresDirecta(){
		Connection Conn = null;		
		try{			
			Class.forName("org.postgresql.Driver");
			Conn = DriverManager.getConnection(Conectar.coneccion(),Conectar.usuario(),Conectar.password());
		}catch(Exception e){
			System.out.println("No es posible conectarse... Error - aca.conectar.Conectar|conPostgres|:");
			e.printStackTrace();
		}		
		return Conn;
	}
	
	// Coneccion para Postgresql
	public Connection conPostgresAdm(){
		Connection Conn = null;		
		try{
			Class.forName("org.postgresql.Driver");
			Conn = DriverManager.getConnection("jdbc:postgresql://172.16.254.25:5432/admision","postgres","jete17");
			System.out.println("Conexion directa admision");
		}catch(Exception e){
			System.out.println("No es posible conectarse... Error - aca.conectar.Conectar|conPostgres|:");
			e.printStackTrace();
		}		
		return Conn;
	}
	
	// Desconectar Postgres
	public void desPostgres(Connection conn){
		try{			
			if (conn != null) conn.close();
		}catch(Exception ex){
			System.out.println("Error - aca.conectar.Conectar|desPostgres|:"+ex);
		}
	}
	
	public boolean setSchema(Connection conn, String schema ) throws Exception{
		boolean ok = false;
		try{
			PreparedStatement ps = conn.prepareStatement("ALTER SESSION "+
				"SET CURRENT_SCHEMA = ?");
			ps.setString(1, schema);			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
			
			try { ps.close(); } catch (Exception ignore) { }
			
		}catch(Exception ex){
			System.out.println("Error - aca.conectar.conectar|setSchema|:"+ex);
		}
		
		return ok;
	}
	
	//Datos de la coneccion a postgres 
	public static String coneccion(){
		// "jdbc:postgresql://172.16.197.99:5432/archivo";
		return "jdbc:postgresql://127.0.0.1:5432/archivo";
	}
	public static String usuario(){
		return "postgres";
	}
	public static String password(){
		return "ptstann69";
	}
	
	// Coneccion para Postgresql
	public Connection conPostgresVMeeting(){
		Connection Conn = null;
		try{			
			Class.forName("org.postgresql.Driver");
			Conn = DriverManager.getConnection("jdbc:postgresql://172.16.254.20:5432/vmeeting","postgres","absbfg");
			System.out.println("Conexion Vmeeting");						
		}catch(Exception ex){
			System.out.println("Error - aca.conectar.Conectar|conPostgresVMeeting|:"+ex);
			try{
				//System.out.println("ACA postgres dir(fallo pool)...");
				Class.forName("org.postgresql.Driver");
				Conn = DriverManager.getConnection("jdbc:postgresql://172.16.254.20:5432/vmeeting","postgres","absbfg");
			}catch(Exception e){
				System.out.println("No es posible conectarse... Error - aca.conectar.Conectar|conPostgresVMeeting|:");
				e.printStackTrace();
			}
		}
		return Conn;
	}
	
	// Coneccion para Postgresql
	public Connection conPostgresRadius(){
		Connection Conn = null;
		try{			
			Class.forName("org.postgresql.Driver");
			Conn = DriverManager.getConnection("jdbc:postgresql://172.16.254.20:5432/radius","tomcat","tomcat00");
			System.out.println("Conexion Radius");						
		}catch(Exception ex){
			System.out.println("Error - aca.conectar.Conectar|conPostgresRadius|:"+ex);
			try{
				//System.out.println("ACA postgres dir(fallo pool)...");
				Class.forName("org.postgresql.Driver");
				Conn = DriverManager.getConnection("jdbc:postgresql://172.16.254.20:5432/radius","tomcat","tomcat00");
			}catch(Exception e){
				System.out.println("No es posible conectarse... Error - aca.conectar.Conectar|conPostgresRadius|:");
				e.printStackTrace();
			}
		}
		return Conn;
	}
	
	public static int cleanConexiones(Connection conn, String schema ) throws Exception{
		PreparedStatement ps 	= null;
		PreparedStatement ps2 	= null;
		ResultSet rs 			= null;
		int numConexiones		= 0;		
		try{
			ps = conn.prepareStatement("SELECT SID, SERIAL# FROM SYS.V_$SESSION WHERE STATUS = 'INACTIVE' AND SCHEMANAME=?");
			ps.setString(1, schema);			
			rs = ps.executeQuery();
			while (rs.next()){
				ps2 = conn.prepareStatement("ALTER SYSTEM KILL SESSION '"+rs.getString("SID")+","+rs.getString("SERIAL#")+"'");
				if (ps2.executeUpdate()==1){
					numConexiones++;
				}
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.conectar.conectar|cleanConexiones|:"+ex);	
		}finally{			
			if (rs!=null) 	rs.close();
			if (ps!=null) 	ps.close();
			if (ps2!=null) 	ps2.close();			
		}		
		return numConexiones;
	}
	
	public static String getStringFromInputStream(InputStream is) throws Exception{
		String line		 	     	= "";
		StringBuilder strBuilder	= new StringBuilder();
		boolean encontro 			= false;
				
		try{
			BufferedReader bufferReader = new BufferedReader(new InputStreamReader(is,"ISO-8859-1"));
			line 		= bufferReader.readLine();
			
			while(line!=null){
				encontro = true;
				strBuilder.append(line); strBuilder.append("\n");
				line = bufferReader.readLine();		
			}
		}catch(Exception e){
			System.out.println(e);
		}
		
		if (!encontro)  strBuilder.append("-");		
		
		return strBuilder.toString();
	}
	
}