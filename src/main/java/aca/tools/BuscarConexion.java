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

public class BuscarConexion {
	
    public static boolean leer(String ruta){
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
            if(linea.indexOf("cierra_enoc.jsp")!=-1  ){ // || linea.indexOf("cierra_enoc2.jsf")!=-1 || linea.indexOf("conEnoc.close()")!=-1
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
      if(num==1){
        resultado=true;
      }
     // System.out.println("Numero de Coincidencias: "+num+" En"+ruta);

      return resultado;
    }

    public static void buscarDir (String rutaorig)throws FileNotFoundException{        
        
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
                if((f.getName().substring(f.getName().length()-4)).equals(".jsp")){
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
        buscarDir("C:\\Trabajo\\workspace-spring\\springacademico");
        System.out.println("x.. Fin de busqueda ..!");
    }
}