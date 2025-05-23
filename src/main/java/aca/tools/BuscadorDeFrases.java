package aca.tools;

/**
 *
 * @author Jose Torres
 */
import java.io.*;

public class BuscadorDeFrases {
	
    public static boolean leer(String ruta){
      File archivo = null;
      FileReader fr = null;
      BufferedReader br = null;
      int num = 0;
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
            if(linea.indexOf("idioma.jsp") != -1){
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
      if(num == 0){
        resultado = true;
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
            throw new FileNotFoundException("No se puede realizar la copia, Directorio Origen no existente");
        }
        String[]listaOrigen = origen.list();

        for(String archivo: listaOrigen){
            File f = new File(origen.getAbsolutePath()+"/"+archivo);
            if(f.isFile()){
                if((f.getName().substring(f.getName().length()-4)).equals(".jsp") && !f.getName().equals("id.jsp")){
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
        buscarDir("C:/Trabajo/workspace/academico");
    }
}