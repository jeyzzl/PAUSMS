import java.sql.*;
import java.lang.*;
import java.util.*;


public class dependientes { 
	
	private String[] separarNombre(String nombre) throws Exception{
		StringTokenizer tk = new StringTokenizer(nombre," ");
		String aNombre[] = new String[tk.countTokens()];	
		String apellidoP="",apellidoM="";
		String dev[] = new String[2];
		dev[0]="";dev[1]="";
		int i=0;
		boolean salir = false;		
		while (tk.hasMoreTokens()) aNombre[i++] = tk.nextToken().toUpperCase(); //Llenamos el array de nombres y apellidos
		if (i>2){
			//Buscamos apellido materno
			i-=2;		
			if ((aNombre[i].equals("LA")||aNombre[i].equals("LAS")||aNombre[i].equals("LOS")) && aNombre[i-1].equals("DE")){
				apellidoM = aNombre[i-1]+" "+aNombre[i]+" "+aNombre[i+1];				
				i-=3; //Posicionamos i para buscar apellido paterno
			}else if (aNombre[i].equals("DEL") || aNombre[i].equals("DE") || aNombre[i].equals("VDA.")){
				if (aNombre[i-1].equals("VDA.")){
					apellidoM = aNombre[i-1]+aNombre[i]+" "+aNombre[i+1];				
					i-=3; //Posicionamos i para buscar apellido paterno
				}else{
					apellidoM = aNombre[i]+" "+aNombre[i+1];				
					i-=2; //Posicionamos i para buscar apellido paterno
				}
			}else{
				apellidoM = aNombre[i+1];
				i--; //Posicionamos i para buscar apellido paterno
			}
	
			//Buscamos apellido paterno
			if ((aNombre[i].equals("LA")||aNombre[i].equals("LAS")||aNombre[i].equals("LOS")) && aNombre[i-1].equals("DE")){
				apellidoP = aNombre[i-1]+" "+aNombre[i]+" "+aNombre[i+1];				
				i-=2; //Posicionamos i en el nombre
			}else if (aNombre[i].equals("DEL") || aNombre[i].equals("DE") || aNombre[i].equals("VDA.")){
				if (aNombre[i-1].equals("VDA.")){
					apellidoM = aNombre[i-1]+aNombre[i]+" "+aNombre[i+1];				
					i-=2;
				}else{
					apellidoP = aNombre[i]+" "+aNombre[i+1];				
					i--; //Posicionamos i en el nombre
				}
			}else	apellidoP = aNombre[i+1];
			//Creamos el array a devolver	
			int j;
			for (j=0;j<i;j++) dev[0]+=aNombre[j]+" ";
			dev[0]+=aNombre[j];		
			dev[1] = apellidoP+" "+apellidoM;		
		}else{
			dev[0]=aNombre[0];
			dev[1]=aNombre[1];
		}
		return dev;
	}	
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
		dependientes dep = new dependientes();
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
			String relacion = "";
			String bDay = "";
			String folio = "";			
			String Nom[] = new String[2];
			int total=0,insertados=0;
			comando = "select a.claveempleado,a.nombre, a.folio,a.bday,b.nombre "+
								"from nom_depend a, nom_relacion b where a.relacion = b.relacion order by a.claveempleado";		
			rset2= stmt2.executeQuery(comando);									
			stmt.execute("delete from ENOC.emp_dependiente"); 
			while (rset2.next()){ 			
				total++;
				claveEmpleado = rset2.getString(1);			
				nombre = rset2.getString(2);			
				folio = rset2.getString(3);			
				bDay = rset2.getString(4);			
				relacion = rset2.getString(5);					
				//nombre = dep.fixNombre(nombre);
				Nom = dep.separarNombre(nombre);								
				System.out.println(claveEmpleado+" "+nombre+" -> "+Nom[0]+", "+Nom[1]);
				comando = "insert into ENOC.emp_dependiente(id_empleado,id_dependiente,nombre,apellidos,bday,relacion) "+ 
									"values('"+claveEmpleado+"','"+claveEmpleado+"-"+folio+"','"+Nom[0]+"','"+Nom[1]+"',"+
									"to_date('"+bDay+"','yyyy-mm-dd'),'"+relacion+"')";									
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