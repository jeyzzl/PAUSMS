package aca.tools;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

public class CompararFotos{
	public static void main(String [] agg) throws IOException{		
		File[] archivosAca = new File("C://Users//Elier//Desktop//fotosaca").listFiles();
		File[] tmpVir = new File("C://Users//Elier//Desktop//fotosvir").listFiles();
				
		ArrayList<File> actuales = new ArrayList();
		ArrayList<String> tmpactuales = new ArrayList();

		System.out.println("Comparando Fotos Mas Nuevas...");
		for(int i=0; i<archivosAca.length; i++){
			boolean esta = false;
			for(int j=0; j<tmpVir.length; j++){				
				if(tmpVir[j].getName().equals(archivosAca[i].getName())){
					esta = true;
					if(archivosAca[i].lastModified() > tmpVir[j].lastModified()){
						actuales.add(archivosAca[i]);
					}
					else{
						actuales.add(tmpVir[j]);
					}
				}
			}
			if(!esta) actuales.add(archivosAca[i]);				
		}
		
		for(File x : actuales){
			tmpactuales.add(x.getName());
		}
		
		for(File x : tmpVir){
			if(!tmpactuales.contains(x.getName())){
				actuales.add(x);
			}
		}
		
		System.out.println("Copiando Fotos...");		
		System.out.println("Fotos a Copiar: "+actuales.size());
		
		for(File x : actuales){
		    FileInputStream fis = new FileInputStream(x.getPath());
		    FileOutputStream fos = new FileOutputStream("C://Users//Elier//Desktop//actuales//"+x.getName());
		    FileChannel canalFuente = fis.getChannel();  
		    FileChannel canalDestino = fos.getChannel();  
		    canalFuente.transferTo(0, canalFuente.size(), canalDestino);  
		    fis.close();  
		    fos.close();
		}		
	}
}