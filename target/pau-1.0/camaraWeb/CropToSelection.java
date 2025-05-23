package  camaraWeb;
import java.awt.*;
import javax.swing.*;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.BufferedImage;
import java.io.*;
import java.awt.image.RenderedImage;
import javax.imageio.*;

public class CropToSelection extends JFrame{

    String url = "";
    String nombreArchivo = "";
    Image img;
    boolean recortado = false;

    
    public CropToSelection(int x, int y, int w, int h, String url, String nombreArchivo){
        this.url = url;
        this.nombreArchivo = nombreArchivo;

        ImageIcon icon = new ImageIcon(url+"\\"+nombreArchivo);

        if(icon!=null){
            img = icon.getImage();

            CropImageFilter cif = new CropImageFilter(x, y, w, h);
            icon = new ImageIcon( createImage(new FilteredImageSource( img.getSource(), cif )));

            BufferedImage bimg = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_RGB);
            bimg.getGraphics().drawImage(icon.getImage(), 0, 0, null);

            try{
                ImageIO.write((RenderedImage) bimg , "JPEG", new File(url+"\\"+nombreArchivo));
                recortado = true;
            }catch (IOException ioe){
                System.out.println("Error al recortar la imagen");
            }
       }else{
           System.out.println("No Se Encuentra La Direccion Especificada");
       }
    }

    public boolean getRecortado(){
        return recortado;
    }
}