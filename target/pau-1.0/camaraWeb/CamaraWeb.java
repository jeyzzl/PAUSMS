package  camaraWeb;
import javax.swing.*;
import java.awt.*; 
import java.awt.Component;
import java.util.Date;
import javax.media.*;
import javax.media.control.*;
import javax.media.util.*;
import javax.media.format.*;
import javax.imageio.*;
import java.io.*;
import java.awt.image.RenderedImage;
import java.awt.image.BufferedImage;

class CamaraWeb{
	public Component componente=null;
	public Player p=null;
	public Component video;
	public MediaLocator ml;
        
	
	CamaraWeb(){
		Manager.setHint( Manager.LIGHTWEIGHT_RENDERER, true );
		try{
			ml = new MediaLocator("vfw://0");
			p = Manager.createRealizedPlayer(ml);
			video = p.getVisualComponent();
			
//------------------------- changing to 640x480
            FormatControl fc = (FormatControl)p.getControl("javax.media.control.FormatControl");
            Format[] formats = fc.getSupportedFormats();
            int resolucion = -1;
            for(int i=0; i<formats.length; i++){
                if(formats[i].toString().indexOf("width=640")>0){
                    resolucion = i;
                }
            }
            if(resolucion!=-1){
                fc.setFormat(formats[resolucion]);
            }
            //System.out.println("RESOLUCION:"+resolucion+"\nFORMATO:"+formats[resolucion]);
//--------------------------
			p.start();
                       
	    	if ( video != null ){
	               componente=video;
			}
		}catch(Exception e){
			JOptionPane.showMessageDialog(null,"Error de Comunicacion con la WebCam " + e);
		}
	}
	public Component VerCamara(int x,int y,int alto,int ancho){
		this.componente.setBounds(x,y,alto,ancho);
		return componente;
	}
	
	public int Fotografiar(String Carpeta,Boolean NombreAutomatico,String NombreValoNull){
		Buffer buf=null;
		java.awt.Image img=null;
		File imagenArch;
		String nombre=null;
		String formato = null;
		File CarpetaFotografias;
		
		
		CarpetaFotografias=new File(Carpeta);
		if(CarpetaFotografias.exists()==false){
			CarpetaFotografias.mkdir();
		}
		FrameGrabbingControl fgc = (FrameGrabbingControl)
    	p.getControl("javax.media.control.FrameGrabbingControl");
    	buf = fgc.grabFrame();
    	BufferToImage btoi = new BufferToImage((VideoFormat)buf.getFormat());

    	img = btoi.createImage(buf);

    	if (img != null){
    		if(NombreAutomatico==true){
    			nombre =Carpeta+ "\\0941428.jpg";
    		}else{
    			nombre=Carpeta + "\\"+NombreValoNull+".jpg";
    		}
    			
     			imagenArch = new File(nombre);
         		formato = "JPEG";
				try{
					ImageIO.write((RenderedImage) img,formato,imagenArch);
					return 1;
         		}catch (IOException ioe){
         			return 0;
         		}
         }

         return 0;
	}

}