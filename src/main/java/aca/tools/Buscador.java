/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package aca.tools;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

/**
 *
 * @author DavidBlanco
 */
import java.io.*;

public class Buscador {
	
	public static void leer(String ruta, ArrayList<String> tablas){
		for(String tabla : tablas){
			for(int cont=0; cont<3; cont++){
			      File archivo = null;
			      FileReader fr = null;
			      BufferedReader br = null;
			      ArrayList<String> lista = new ArrayList<String>();
			      
			      try {
			         // Se abre el archivo
			         // BufferedReader(metodo readLine()).
			         archivo = new File (ruta);
			         fr = new FileReader (archivo);
			         br = new BufferedReader(fr);
			
			         // Lectura del fichero
			         String linea;
			         while((linea=br.readLine())!=null){
			     		String palabra = "";
			     		if(cont==0)palabra = "FROM";
			     		else if(cont == 1) palabra = "UPDATE";
			     		else palabra = "INTO";
			     		String palMin = palabra.toLowerCase();
			     		
			     		//String tabla = "ACCESO";
			     		
			     		String tabMin =	tabla.toLowerCase();
			     		
			     		if((linea.contains(palabra)||linea.contains(palMin)) && (linea.contains(tabla)||linea.contains(tabMin))){
			     			String [] arr = linea.split(" ");
			     			for(int i=0; i<arr.length; i++){	
			     				if(arr[i].contains(palabra) || arr[i].contains(palMin)){
			     					String tmpPal = "";
			     					if(arr[i].length()>=palabra.length()){
		     							tmpPal = arr[i].substring(arr[i].length()-palabra.length());
		     						}
			     					if(tmpPal.equals(palabra) || tmpPal.equals(palMin)){
				     					int j =i+1;
				     					while(arr[j].equals(" ")) j++;
				     					i = j;
				     					if(arr[i].contains(tabla) || arr[i].contains(tabMin)){
				     						String tmp = arr[i].substring(0, tabla.length());
				     						if(tmp.equals(tabla) || tmp.equals(tabMin)){
				     							arr[i] = "SALOMON."+arr[i];
				    			    			if(cont==0)System.out.print(tabla+", ");
				     						}
				         				}
			     					}
			     					
			     					
			     				}
			     			}
			     			String txt = "";
			     			for(String str: arr){
			     				txt+=(str+" ");
			     			}
			     			linea=txt;
			     		}
			     		lista.add(linea);
			         }
			
			      }
			      catch(Exception e){
			         e.printStackTrace();
			      }finally{
			         // En el finally cerramos el fichero
			         try{
			            if( null != fr ){
			               fr.close();
			            }
			         }catch (Exception e2){
			            e2.printStackTrace();
			         }
			      }
			     // 
			      try {
						//Escritura
						java.io.BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(ruta));
						for(String str: lista){
							bufferedWriter.append(str);
							bufferedWriter.flush();
							bufferedWriter.newLine();
						}
			
					} catch (IOException e) {						
						e.printStackTrace();
					}
	    	}
		
		}System.out.println();		
		
    }
	
    public static void buscarDir (String rutaorig, ArrayList<String> tablas)throws FileNotFoundException{        
        
        if(rutaorig==null ){
            throw new IllegalArgumentException("Argumentos no validos para hacer esta operacion");
        }

        File origen = new File(rutaorig);

        if(!origen.isDirectory()){
            throw new FileNotFoundException("No se puede realizar la copia, Directorio Oirgen no existente");
        }
        String[]listaOrigen = origen.list();

        for(String cad: listaOrigen){
            File f = new File(origen.getAbsolutePath()+"/"+cad);
            if(f.isFile()){
                if((f.getName().substring(f.getName().length()-4)).equals(".jsp") || (f.getName().substring(f.getName().length()-5)).equals(".java")){
                	System.out.println("TABLA: "+f.getName()+" DIR: "+f.getAbsolutePath());
                	leer(f.getAbsolutePath(), tablas);
                }
            }else{
                buscarDir(f.getAbsolutePath(),tablas);
            }
        }
    }

    public static void main(String[]args)throws FileNotFoundException, IOException{
        
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
				comando = " SELECT TABLE_NAME FROM SYS.ALL_ALL_TABLES WHERE OWNER = 'SALOMON' ORDER BY 1";			
				rs = stmt.executeQuery(comando);
				while (rs.next()){		
					tablas.add(rs.getString("TABLE_NAME").trim());
				}
				
			}catch(Exception ex){
				System.out.println("Error:aca.tools.Buscador"+ex);
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
    	System.out.println(tablas);
    	
    	buscarDir("C:/eclipse/workspace/admision", tablas);
    	/*
    	try {
			//Escritura
    		String ruta = "c:/escritura/prueba.java";
			java.io.BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(ruta));
			bufferedWriter.append("Esto es la linea 1");
			bufferedWriter.flush();
			bufferedWriter.newLine();
			bufferedWriter.append("Esto es la linea 4");
			bufferedWriter.flush();
 
		} catch (IOException e) {			
			e.printStackTrace();
		}
		*/
		
		

    }
}