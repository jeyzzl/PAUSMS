package aca.tools;
import java.io.*;

public class Fotos {
	
	public static void main(String[] args){
		File DirVir = new File ("c:\\fotos\\fotosvir");
		File DirAca = new File ("c:\\fotos\\fotosaca");
		
		String[] fotosVir = DirVir.list(); 
		String[] fotosAca = DirAca.list();
		String archivo = "";
		String archivo2 = "";
		boolean existe = false;
		
		for (int i = 0; i < fotosAca.length; i++){
			archivo = fotosAca[i];
			existe = false;
			for (int j = 0; j<fotosVir.length; j++){
				archivo2 = fotosVir[j]; 
				if (archivo.equals(archivo2)) existe = true;
			}
			
			if (existe == false){
				System.out.println(archivo);
			}
		}
		
	}

}