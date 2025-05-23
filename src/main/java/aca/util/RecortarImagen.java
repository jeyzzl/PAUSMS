package aca.util;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class RecortarImagen {
	
	public static boolean getSubImagen( String archivo){
		boolean ok = false;
		
		try{
			// Archivo de la foto del alumno sin recortar
			File foto = new File(archivo);
			
			//Imagen orignal
		    BufferedImage bufferedImage = ImageIO.read(foto);
		    
		    int width = bufferedImage.getWidth();
		    int height = bufferedImage.getHeight();
		    
		    // nuevas dimensiones de la foto
		    int x1 = 140, y1 = 0, x2 = 360, y2 = 480;
		    // Redimensionar la foto (Pendiente)
		    
		    // borra la foto actual
		    foto.delete();

		    //Imagen revisada y modificada
			BufferedImage newBufferedImage = bufferedImage.getSubimage(x1, y1, x2, y2);
			
			// Graba el archivo de la nueva fotografia 
			if (ImageIO.write(newBufferedImage, "jpg", foto)){
				ok = true;
			}
			
		}catch(Exception e){
			System.out.println("Error al recortar la foto: "+e);
		}		
		return ok;
	}
	
}