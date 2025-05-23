package aca.tools;
import java.io.*;
import java.sql.*;

import aca.alumno.AlumFoto;

public class SubirFoto{
	public static void main(String args[]){
		String errores = "";
		File dir				= new File("/home/josetorres/fotos");
		File listFiles[]		= dir.listFiles();
		
		Connection conn			= null;
		Statement stmt			= null;
		
		try{				
			//System.out.print("Creando conexion...");
			DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver ());
			conn = DriverManager.getConnection("jdbc:oracle:thin:@172.16.254.20:1521:ora1","enoc","caminacondios");
			stmt = conn.createStatement();
			//System.out.println("OK!");
		}catch (Exception ex){ex.printStackTrace();};
		try{
			System.out.println("Total fotos: "+listFiles.length);
			for (int i = 0; i < listFiles.length; i++){
				File f = listFiles[i];
				if (i%100==0){
					conn.close();
					DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver ());
     				conn = DriverManager.getConnection("jdbc:oracle:thin:@172.16.254.20:1521:ora1","enoc","caminacondios");
					stmt = conn.createStatement();
					//System.out.println("100");
				}
				//System.out.print(i + "  ");
				String matricula = listFiles[i].getName();
				matricula = matricula.substring(0,matricula.indexOf("."));
				//System.out.print(matricula + "   " + listFiles[i].getName());
				String folio = "0";
				if(matricula.length() > 7){
					folio = matricula.substring(8);
					matricula = matricula.substring(0, 7);
					//System.out.print("Dependiente # = "+matricula);
				}else if(matricula.length() == 6)
					System.out.println(matricula);
				AlumFoto af = new AlumFoto();
				af.setCodigoPersonal(matricula);
				af.setFecha(aca.util.Fecha.getHoy());
				af.setFolio(folio);
				af.setUsuario("9800308");
				
				byte[] buff;
				java.io.FileInputStream instream;
				if(f.exists()){
					instream = new FileInputStream(f);
					buff = new byte[(int)f.length()];
					instream.read(buff);
					af.setFoto(buff);
					System.out.println("Grabando: "+af.getCodigoPersonal());
/*					
					BLOB blob = BLOB.createTemporary(conn, true, BLOB.DURATION_SESSION);					

					OutputStream blobOS = blob.getBinaryOutputStream();
					blobOS.write(buff);
					blobOS.flush();
					af.setFoto(blob);
*/					
					if(af.insertRegByte(conn)){
					
						//System.out.println("  Se guardó!!!");
					}else
						System.err.println("  Ocurrix un error al guardar = "+matricula+" folio = "+folio);
						
				}else{
					System.err.println("Ocurrix un error al verificar si existe el archivo");
				}
			}	
			
		}catch(Exception e){
			e.printStackTrace();
			errores += e;
		}finally{
			try{
				stmt.close();
				conn.close();
			}catch(Exception e){
				System.out.println(e);
			}
		}
	}	
}