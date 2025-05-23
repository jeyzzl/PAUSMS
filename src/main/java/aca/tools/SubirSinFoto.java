package aca.tools;
import java.io.*;
import java.sql.*;

import aca.pg.archivo.AlumFoto;
import aca.pg.archivo.AlumFotoUtil;

public class SubirSinFoto{
	
	public static void main(String args[]){
		File archivo			= new File("C:\\Trabajo\\SinFoto\\nofoto2.png");
				
		Connection conn			= null;
		Statement stmt			= null;
		AlumFoto foto			= new AlumFoto();
		AlumFotoUtil fotoU		= new AlumFotoUtil();
		
		try{				
			//System.out.print("Creando conexion...");
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://172.16.251.11:5432/archivo","postgres","jete17");
			stmt = conn.createStatement();
			//System.out.println("OK!");
		}catch (Exception ex){
			ex.printStackTrace();
		}
		
		try{						
			byte[] buff;
			java.io.FileInputStream instream;
			if(archivo.exists()){
				instream = new FileInputStream(archivo);
				buff = new byte[(int)archivo.length()];
				instream.read(buff);
				foto.setCodigoPersonal("nofoto");
				foto.setTipo("O");
				foto.setFecha(aca.util.Fecha.getHoy());
				foto.setUsuario("9800308");
				foto.setFoto(buff);				
				
				if(fotoU.insertRegByte(conn,foto)) {
					System.out.println("Si se grabo...");						
				}else{
					System.err.println("Ocurrió un error al guardar nofoto");
				}	
				
			}else{
				System.err.println("Ocurrio un error al verificar si existe el archivo");
			}		
		}catch(Exception e){
			e.printStackTrace();			
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