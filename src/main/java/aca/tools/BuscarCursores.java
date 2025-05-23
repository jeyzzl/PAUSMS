/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package aca.tools;

/**
 *
 * @author DavidBlanco
 */
import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class BuscarCursores {
	
    public static boolean leer(String ruta){
      File archivo 			= null;
      FileReader fr 		= null;
      BufferedReader br 	= null;
      boolean resultado 	= false;
      int numPs				= 0;
      int numPs2			= 0;
      int numRs				= 0;
      int numRset			= 0;
      int numSt				= 0;
      int numStmt			= 0;
      try {
         // Se abre el archivo
         // BufferedReader(metodo readLine()).
         archivo = new File (ruta);
         fr = new FileReader (archivo);
         br = new BufferedReader(fr);

         // Lectura del fichero
         String linea;
         while((linea=br.readLine())!=null){
//        	if(linea.indexOf("ResultSet rs")!=-1){
//        		numRs++;
//            }
            if(linea.indexOf("ResultSet rset")!=-1){
            	numRset++;            
            }
//            if(linea.indexOf("PreparedStatement ps2")!=-1){
//                numPs2++;
//            }
//            if(linea.indexOf("Statement st")!=-1){
//            	numSt++;
//            }
            
            if(linea.indexOf("Statement stmt")!=-1){
            	numStmt++;
            }
            
            if(linea.indexOf("rset.close();")!=-1  ){
                numRset--;
            }
            if(linea.indexOf("ps.close();")!=-1  ){
                numPs--;
            }
            if(linea.indexOf("ps2.close();")!=-1  ){
                numPs2--;
            }
            if(linea.indexOf("st.close();")!=-1  ){
                numSt--;
            }
            if(linea.indexOf("stmt.close();")!=-1  ){
                numStmt--;
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
      if(numRset>0 || numStmt>0){
        resultado = true;
      }
      
      return resultado;
    }

    public static void buscarDir (String rutaorig) throws FileNotFoundException{        
        
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
                if( (f.getName().substring(f.getName().length()-4)).equals(".jsp") || (f.getName().substring(f.getName().length()-4)).equals(".java")){
                    if(leer(f.getAbsolutePath())){
                        System.out.println(f.getAbsolutePath());
                    }
                }
            }else{
                buscarDir(f.getAbsolutePath());
            }
        }
    }

    public static void main(String[]args)throws FileNotFoundException, IOException{
    	System.out.println("x.. Inicia busqueda ..!");
        buscarDir("C:\\Trabajo\\workspace\\springacademico");
        System.out.println("x.. Fin de busqueda ..!");
    }
}