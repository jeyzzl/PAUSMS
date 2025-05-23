package aca.tools;
import java.io.*;
import java.sql.*;

import aca.pg.archivo.AlumFoto;
import aca.pg.archivo.AlumFotoUtil;

public class SubirFotoPos{
	
	public static void main(String args[]){		
		File dir				= new File("C:\\Trabajo\\FotoEmpleado");
		File listFiles[]		= dir.listFiles();
		boolean error 			= false;
		Connection conn			= null;
		Statement stmt			= null;
		AlumFotoUtil afu		= new AlumFotoUtil();
		int row = 0;
		try{				
			//System.out.print("Creando conexion...");
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://172.16.251.11:5432/archivo","postgres","jete17");
			stmt = conn.createStatement();
			//System.out.println("OK!");
		}catch (Exception ex){ex.printStackTrace();};
		try{
			System.out.println("Total fotos: "+listFiles.length);
			for (int i = 0; i < listFiles.length; i++){
				File f = listFiles[i];
				
				//System.out.print(i + "  ");
				String matricula = listFiles[i].getName();
				matricula = matricula.substring(0,matricula.indexOf("."));
				String tipo = "O";
				error = false;
				if (matricula.length() > 7){
					if (matricula.contains("-")){
						tipo = matricula.substring(matricula.indexOf("-")+1, matricula.length());
						matricula = matricula.substring(0,matricula.indexOf("-"));						
						if (tipo.length() > 1) tipo = "X";
					}else{
						error = true;
						//System.out.println("Error:"+matricula);
					}
				}
				
				if (i % 1000 == 0 && i > 0){
					System.out.println(i+":"+matricula+":"+tipo);
				}
				
				AlumFoto af = new AlumFoto();
				af.setCodigoPersonal(matricula);
				af.setTipo(tipo);
				af.setFecha(aca.util.Fecha.getHoy());
				af.setUsuario("9800308");
				if (afu.existeReg(conn, matricula, tipo)==false){
					byte[] buff = null;
					java.io.FileInputStream instream;
					if(f.exists()){
						instream = new FileInputStream(f);
						buff = new byte[(int)f.length()];
						instream.read(buff);
						af.setFoto(buff);
						
						if(afu.insertRegByte(conn, af)){
							row++;						
						}else{
							System.err.println("  Ocurrix un error al guardar = "+matricula);
						}
						instream.close();
					}else{
						System.err.println("Ocurrio un error al verificar si existe el archivo");
					}
				}else{
					System.out.println("Ya existe este archivo:"+matricula+":"+tipo);
				}
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
		System.err.println(" Inserto:"+row);
	}	
}