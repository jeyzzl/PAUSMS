package aca.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
/**
 *
 * @author Jose Torres
 */

public class CompararMenu {	

    public static void main(String[]args){
    	// Variables de conexion a UM
		Connection conn			= null;
		Statement stmt			= null;
		ResultSet rs 			= null;
		
		
		// Variables de conexion a NAvojoa
		Connection conn2		= null;
		Statement stmt2			= null;
		ResultSet rs2 			= null;
		
		String comando		= "";
		int iguales 		= 0;
		
		try{				
			System.out.println("Creando conexion UM");
			DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver ());
			conn = DriverManager.getConnection("jdbc:oracle:thin:@172.16.254.20:1521:ora1","enoc","caminacondios");
			stmt = conn.createStatement();
			
			System.out.println("Creando conexion Navojoa");
			DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver ());
			conn2 = DriverManager.getConnection("jdbc:oracle:thin:@148.235.6.71:1521:XE","enoc","caminacondios");
			stmt2 = conn2.createStatement();
			
			comando = " SELECT MODULO_ID, OPCION_ID, NOMBRE_OPCION, URL, ICONO, ORDEN FROM ENOC.MODULO_OPCION ORDER BY 1";
			rs = stmt.executeQuery(comando);
			while (rs.next()){				
				String opcion = rs.getString("OPCION_ID");
				System.out.println("Buscando..."+opcion);
				comando = "SELECT MODULO_ID FROM ENOC.MODULO_OPCION WHERE OPCION_ID = '"+opcion+"'";
				rs2 = stmt2.executeQuery(comando);
				if (!rs2.next()){
					System.out.println("encontre nuevo:"+opcion+":"+rs.getString("NOMBRE_OPCION"));
					comando = "INSERT INTO MODULO_OPCION(MODULO_ID, OPCION_ID, NOMBRE_OPCION, URL, ICONO, ORDEN)" +
							"VALUES('"+rs.getString("MODULO_ID")+"','"+opcion+"','"+rs.getString("NOMBRE_OPCION")+"','"+rs.getString("URL")+"','"+rs.getString("ICONO")+"','"+rs.getString("ORDEN")+"')";				
					if (stmt2.executeUpdate(comando)==1){
						System.out.println("Inserto el renglon"+opcion);
					}else{
						System.out.println("Error:aca.tools.ComparaMenu|"+opcion);
					}
				}else{
					iguales++;
				}
			}
			System.out.println("Iguales:"+iguales);
			//System.out.println("OK!");
		}catch (Exception ex){
			ex.printStackTrace();
		}
    }
}