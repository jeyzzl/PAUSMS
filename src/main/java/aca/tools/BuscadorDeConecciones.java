/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package aca.tools;

/**
 *
 * @author DavidBlanco
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class BuscadorDeConecciones {
	
    public static boolean leer(String ruta, int valor){
      File archivo = null;
      FileReader fr = null;
      BufferedReader br = null;
      int num=0;
      boolean resultado = false;

      try {
         // Se abre el archivo
         // BufferedReader(metodo readLine()).
         archivo = new File (ruta);
         fr = new FileReader (archivo);
         br = new BufferedReader(fr);

         // Lectura del fichero
         String linea;
         while((linea=br.readLine())!=null){
            if(linea.indexOf("con_enoc.jsp")!=-1){
                num++;
            }
            if(linea.indexOf("cierra_enoc.jsp")!=-1 || linea.indexOf("conEnoc.close()")!=-1){
                num++;
            }
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
      if(num==valor){
        resultado=true;
      }
     // System.out.println("Numero de Coincidencias: "+num+" En"+ruta);

      return resultado;
    }

    public static void buscarDir (Connection conEnoc, String rutaorig)throws FileNotFoundException{
    	
		PreparedStatement ps	= null;
		
        if(rutaorig==null ){
            throw new IllegalArgumentException("Argumentos no validos para hacer esta operacion");
        }

        File origen = new File(rutaorig);

        if(!origen.isDirectory()){
            throw new FileNotFoundException("No se puede realizar la copia, Directorio Oirgen no existente");
        }
        String[]listaOrigen = origen.list();       
        
        int row 		= 0;
        int grabado 	= 0;
        int error 		= 0;
        try{
        	
			ps = conEnoc.prepareStatement("INSERT INTO ENOC.MODULO_JSP(RUTA,ENOC,ATLAS) VALUES(?,?,?)");
            
            for(String cad: listaOrigen){
                File f = new File(origen.getAbsolutePath()+"/"+cad);
                if(f.isFile()){
                    if((f.getName().substring(f.getName().length()-4)).equals(".jsp")){
                    	// 1=Archivos sin cerrar o abrir conexion 2=archivos que abren y cierran conexion 
                        if(leer(f.getAbsolutePath(),2)){
                        	row++;                        	
                        	ps.setString(1, f.getAbsolutePath());
                        	ps.setString(2, "S");
                        	ps.setString(3, "N");
                        	if (ps.executeUpdate()==1) {
                        		grabado++;
                        		conEnoc.commit();
                        	}else {
                        		error++;
                        	}
                        }
                    }
                }else{
                    buscarDir(conEnoc, f.getAbsolutePath());
                }
            }
            
            try { ps.close(); } catch (Exception ignore) { }			
			
		}catch (Exception ex){
			ex.printStackTrace();
		}   
        System.out.println("Rows:"+row+" Inserts:"+grabado+" errores:"+error);
    }

    public static void main(String[]args)throws FileNotFoundException, IOException{
    	Connection conEnoc			= null;
    	try{        	
        	DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver ());
			conEnoc = DriverManager.getConnection("jdbc:oracle:thin:@172.16.254.20:1521:ora1","enoc","caminacondios");
			
			//buscarDir("C:/Trabajo/SpringBoot/springactivos/src/main/webapp");
	    	buscarDir(conEnoc, "C:/Trabajo/workspace/academico");
	    	if (conEnoc!=null) conEnoc.close();
    	}catch(Exception ex) {
    		ex.printStackTrace();
    	}
    }
}