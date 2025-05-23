package aca.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

import javax.imageio.ImageIO;
import org.imgscalr.Scalr;

public class RedimencionarImagen {
	
	public static byte[] redimencionarArchivo(File icon, int ancho, int alto) {
        try {
           BufferedImage imagenOriginal = ImageIO.read(icon);

           //imagenOriginal= Scalr.resize(imagenOriginal, Scalr.Method.QUALITY, Scalr.Mode.FIT_EXACT, ancho, alto);
           //To save with original ratio uncomment next line and comment the above.
           imagenOriginal= Scalr.resize(imagenOriginal, ancho, alto);
           ByteArrayOutputStream baos = new ByteArrayOutputStream();
           ImageIO.write(imagenOriginal, "jpg", baos);
           baos.flush();
           byte[] imageReducida = baos.toByteArray();
           baos.close();
           return imageReducida;
        } catch (Exception e) {
            return null;
        }
    }
	
	public static byte[] redimencionarBytes(byte[] fotoNormal, int ancho, int alto) {
        try {
        	InputStream fotoInput = new ByteArrayInputStream(fotoNormal);
        	BufferedImage imagenOriginal = ImageIO.read(fotoInput);

           //imagenOriginal= Scalr.resize(imagenOriginal, Scalr.Method.QUALITY, Scalr.Mode.FIT_EXACT, ancho, alto);
           //To save with original ratio uncomment next line and comment the above.
           imagenOriginal= Scalr.resize(imagenOriginal, ancho, alto);
           ByteArrayOutputStream baos = new ByteArrayOutputStream();
           ImageIO.write(imagenOriginal, "jpg", baos);
           baos.flush();
           byte[] imageReducida = baos.toByteArray();
           baos.close();
           return imageReducida;
        } catch (Exception e) {
            return null;
        }
    }
	
}