package aca.tools;
import java.io.*;
import java.sql.*;

import aca.pg.archivo.ArchResidencia;
import aca.pg.archivo.ArchResidenciaUtil;

public class SubirImagenPos{
	
	public static void main(String args[]){
		File dir				= new File("C:\\Trabajo\\workspace-spring\\docext");
		File listCarpetas[]		= dir.listFiles();		
		Connection conn			= null;
		Statement stmt			= null;
		ArchResidenciaUtil imagenU		= new ArchResidenciaUtil();
		int row = 0;
		
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
			System.out.println("Total fotos: "+listCarpetas.length);
			int num = 0;
			for (int i = 0; i < listCarpetas.length; i++){
				num++;
				File f = listCarpetas[i];				
				if (f.isDirectory()){
					System.out.println(num+":"+f.getName());
					File listFiles[]		= f.listFiles();
					for (File archivo : listFiles){									
						String matricula = archivo.getName();
						String folio = matricula.substring(matricula.indexOf(" ")+1,matricula.indexOf("."));
						matricula = matricula.substring(0,matricula.indexOf(" "));
						//System.out.println("Mat:"+matricula+":"+folio);
						
						ArchResidencia imagen = new ArchResidencia();
						imagen.setCodigoPersonal(matricula);
						imagen.setFolio(folio);					
						
						byte[] buff;
						java.io.FileInputStream instream;
						if(archivo.exists()){
							instream = new FileInputStream(archivo);
							buff = new byte[(int)archivo.length()];
							instream.read(buff);
							imagen.setImagen(buff);
							
							if(imagenU.insertRegByte(conn, imagen)){		
								row++;						
							}else{
								System.err.println("Ocurrió un error al guardar = "+matricula+"-"+folio);
							}	
							
						}else{
							System.err.println("Ocurrio un error al verificar si existe el archivo");
						}
					}					
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