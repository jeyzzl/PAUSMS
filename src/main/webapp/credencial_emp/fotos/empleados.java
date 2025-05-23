import java.sql.*;
import java.lang.*;
import java.util.*;

public class empleados { 
	private String fixNombre(String nombre){
		nombre = nombre.replace('x','A');
		nombre = nombre.replace('x','E');
		nombre = nombre.replace('x','I'); 
		nombre = nombre.replace('x','O'); 
		nombre = nombre.replace('x','U'); 
		return nombre;
	}
	
	public static void main(String[] args) throws Exception {
		Connection	conn = null;		
		Connection	conn2= null;
		empleados e = new empleados();
		String comando	= "";
		
		try{		
			// Conexion a Oracle	
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@172.16.254.14:1521:ora1", "enoc", "caminacondios");
			Statement 	stmt = conn.createStatement();
			ResultSet	rset = null; 
			System.out.println("CONEXION ORACLE");
			
			//Conexion a SqlBase
			Class.forName("jdbc.gupta.sqlbase.SqlbaseDriver");
			conn2 = DriverManager.getConnection("jdbc:sqlbase://172.16.254.15/UMFINANC", "admin", "islu3bin");			
			Statement 	stmt2= conn2.createStatement();
			ResultSet	rset2= null;
			System.out.println("CONEXION SQLBASE");			
			
			String claveEmpleado = "";
			String nombre = "";
			String apellidos = "";
			int total=0,insertados=0;
			comando = "select * from nom_personales where status = 'A'";		
			rset2= stmt2.executeQuery(comando);									
			stmt.execute("delete from ENOC.emp_datos"); 
			while (rset2.next()){ 			
				total++;
				claveEmpleado = rset2.getString("CLAVEEMPLEADO");			
				nombre = rset2.getString("NOMBRE");			
				apellidos = rset2.getString("APELLIDOS");			
				nombre = e.fixNombre(nombre);				
				apellidos = e.fixNombre(apellidos);
				System.out.println(claveEmpleado+" "+nombre+" "+apellidos);
				comando = "insert into ENOC.emp_datos(id_empleado,nombre,apellidos) "+ 
									"values('"+claveEmpleado+"','"+nombre+"','"+apellidos+"')";									
				if (stmt.executeUpdate(comando)==1){
					insertados++;
				}else{
					System.out.println("Error al insertar");
				}
			}			
			try { stmt.close(); } catch (Exception ignore) { }
			try { stmt2.close(); } catch (Exception ignore) { }
			try { rset.close(); } catch (Exception ignore) { }
			try { rset2.close(); } catch (Exception ignore) { }		
			System.out.println("END PROGRAM Total emp.:"+total+" Insertados: "+insertados);
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try { conn.close(); } catch (Exception ignore) { }
			try { conn2.close(); } catch (Exception ignore) { }
		}
	}
}