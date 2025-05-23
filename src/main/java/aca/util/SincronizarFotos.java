package aca.util;
import java.io.*;

public class SincronizarFotos {
	
	public static void main(String[] args){
		File DirVir = new File ("https://virtual-um.um.edu.mx/academico/WEB-INF/fotos");
		File DirAca = new File ("http://academico.um.edu.mx/academico/portales/alumno");
		
		System.out.println("Dir:"+DirAca.getPath());
		System.out.println("Dir:"+DirAca.length());
/*		
		String[] fotosVir = DirVir.list();
		String[] fotosAca = DirAca.list();
		String archivo = "";
		String archivo2 = "";
		boolean existe = false;
		System.out.println("Listas:"+fotosVir.length+":"+fotosAca.length);

	
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
*/
		
		
	}

}