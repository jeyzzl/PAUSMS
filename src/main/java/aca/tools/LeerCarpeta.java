/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package aca.tools;

/**
 *
 * @author DavidBlanco
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class LeerCarpeta {
	
    public static void buscarDir (String rutaorig)throws FileNotFoundException{    	
		
        if(rutaorig==null ){
            throw new IllegalArgumentException("Argumentos no validos para hacer esta operacion");
        }

        File origen = new File(rutaorig);
        if(!origen.isDirectory()){
            throw new FileNotFoundException("No se puede realizar la copia, Directorio Oirgen no existente");
        }
        String[]listaOrigen = origen.list();     
        
        int row=0;
        for(String cad: listaOrigen){
            File f = new File(origen.getAbsolutePath()+"/"+cad);
            row++;
            String tipo = f.isFile()?"File":"Carpeta";
            System.out.println(row+":"+f.getName()+":"+tipo);
            
//            if(f.isFile()){
//                if((f.getName().substring(f.getName().length()-4)).equals(".jsp")){                	
//                }
//            }else{
//                buscarDir(f.getAbsolutePath());
//            }
        }        
    }

    public static void main(String[]args)throws FileNotFoundException, IOException{
        //buscarDir("C:/Trabajo/SpringBoot/springactivos/src/main/webapp");
    	buscarDir("C:/Trabajo/workspace/academico");
    }
}