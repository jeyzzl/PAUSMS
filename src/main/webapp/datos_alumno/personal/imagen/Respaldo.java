
package imagen;

import java.applet.Applet;
import java.awt.Button;
import java.awt.Color;
import java.awt.Event;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.color.ICC_ColorSpace;
import java.awt.color.ICC_Profile;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;

public class Respaldo extends Applet
{
    Image imgCredencial,imgFoto;
    String matricula,nombre,apellidos,carrera;
    FontMetrics fm;
    BufferedImage bs;
    Button credBoton;
	boolean recrear=true;
	ByteArrayOutputStream comandos;
    public void init() {
    	credBoton = new Button("   Imprimir credencial   ");
    	add(credBoton);
    	imgCredencial = getImage(getDocumentBase(),"http://localhost/enoc/imagenes/credencial1.jpg");
    	matricula="1000097";
    	nombre = "CARLOS";
    	apellidos = "GUZMAN GARCIA";
    	carrera = "INGENIERIA EN SISTEMAS COMPUTACIONALES";
    	imgFoto = getImage(getDocumentBase(),"http://localhost/enoc/imagenes/"+matricula+".jpg");
    	setSize(1035,650);
    	bs = new BufferedImage(1029, 645, BufferedImage.TYPE_INT_RGB);
    	comandos = new ByteArrayOutputStream();
    }
    public boolean action(Event x, Object y) {
 
	   	AffineTransform afLeft = AffineTransform.getRotateInstance(Math.toRadians(270)); 
		afLeft.translate(bs.getWidth() * -1,0); 
		AffineTransformOp lOp = new AffineTransformOp(afLeft,null); 
		BufferedImage bs2 = lOp.filter(bs, null); 	

		BufferedImage bcmyk = new BufferedImage(645, 1029, BufferedImage.TYPE_INT_RGB);
		bcmyk.getRaster().setSamples(0,0,645, 1029,0,convertirCMY(bs2.getRaster().getSamples(0,0,645, 1029,0,(int[])null)));
		bcmyk.getRaster().setSamples(0,0,645, 1029,1,convertirCMY(bs2.getRaster().getSamples(0,0,645, 1029,1,(int[])null)));
		bcmyk.getRaster().setSamples(0,0,645, 1029,2,convertirCMY(bs2.getRaster().getSamples(0,0,645, 1029,2,(int[])null)));

		byte C[] = new byte[1029*645];
		byte M[] = new byte[1029*645];
		byte Y[] = new byte[1029*645];

		transformar31(C,bcmyk.getRaster().getSamples(0,0,645, 1029,0,(int[])null));
		transformar31(M,bcmyk.getRaster().getSamples(0,0,645, 1029,1,(int[])null));
		transformar31(Y,bcmyk.getRaster().getSamples(0,0,645, 1029,2,(int[])null));

		byte Ccomp[] = comprimir(C);
		byte Mcomp[] = comprimir(M);
		byte Ycomp[] = comprimir(Y);

		FileOutputStream fo=null;
		comandos.reset();
		agregarComando("SXY 1");
		agregarComando("+RIB 0");
		agregarComando("+$L 0 5");
		agregarComando("+$L 1 5");
		agregarComando("+$L 2 5");
		agregarComando("+$L 3 5");
		agregarComando("+$C 0 5");
		agregarComando("+$C 1 7");
		agregarComando("+$C 2 7");
		agregarComando("+$C 3 7");
		agregarComando("+C 5");
		agregarComando("+CV 3");
		agregarComando("$F");
		agregarGS("0",Ycomp);
		agregarGS("1",Mcomp);
		agregarGS("2",Ccomp);
		agregarComando("L 0 0 1023 639 0");
		agregarComando("vL 0 0 1023 639 1");
		agregarComando("M 1 [IS 0[IS 1[IS 2[IV 31");
		try {
			File f = new File("Y:\\UmOK.prn");
			fo = new FileOutputStream(f);
			fo.write(comandos.toByteArray());
			fo.flush();
			fo.close();
		} catch (IOException e2) { e2.printStackTrace(); }
		
		byte Cd[] = descomprimir(Ccomp);
		byte Md[] = descomprimir(Mcomp);
		byte Yd[] = descomprimir(Ycomp);
		
		int Ci[] = normalizar(Cd);
		int Mi[] = normalizar(Md);
		int Yi[] = normalizar(Yd);

		bs2.getRaster().setSamples(0,0,645, 1029,0,Ci);
		bs2.getRaster().setSamples(0,0,645, 1029,1,Mi);
		bs2.getRaster().setSamples(0,0,645, 1029,2,Yi);
		
	   	afLeft = AffineTransform.getRotateInstance(Math.toRadians(270)); 
		afLeft.translate(bs2.getWidth() * -1,0); 
		lOp = new AffineTransformOp(afLeft,null); 
		bs = lOp.filter(bs2, null); 	
		
		recrear = false;
		repaint();
		return true;
    }
    public void paint(Graphics g) {
    	if (recrear){
	    	Graphics2D gs = bs.createGraphics();
	    	gs.drawImage(imgCredencial, 0, 0,1029, 645,this);
	    	gs.drawImage(imgFoto, 50,240,272,360,this);
	    	gs.setColor(Color.WHITE);
	    	gs.setFont(new Font("Gill Sans MT",Font.PLAIN,29));
	    	gs.drawString("CREDENCIAL DE ESTUDIANTE",50,216);
	    	gs.drawString("Nombre",350,283);
	    	gs.drawString("Carrera",350,404);
	    	gs.drawString("Matrxcula",350,598);
	    	gs.setFont(new Font("Gill Sans MT",Font.PLAIN,38));
	    	gs.drawString(nombre.toUpperCase(),360,323);
	    	gs.drawString(apellidos.toUpperCase(),360,371);
	        ArrayList lineas = separarCarrera(carrera, 30);
	        for (int i=0;i<lineas.size();i++){
	        	gs.drawString(((String)lineas.get(i)).toUpperCase(),360,444+(i*41));
	        }
	        gs.setFont(new Font("Gill Sans MT",Font.PLAIN,46));
	        gs.drawString(matricula,480,605);
    	}
        g.drawImage(bs,0,0,this);
        
	    File f = new File("c:\\FotosCredencial\\"+matricula+".jpg");
		try { ImageIO.write(bs, "jpeg", f);
		} catch (IOException e) {e.printStackTrace();}
	}
    public int[] normalizar(byte a[]) {
    	int b[] = new int[a.length];
    	for (int i=0;i<a.length;i++) 
    		b[i]=255-(a[i]*255/31);
    	return b;
    }
    public byte[] descomprimir(byte a[]) {
		int iguales=0,data;
		ByteArrayOutputStream ba = new ByteArrayOutputStream();
		for (int i=0;i<a.length;i++){ 
			if ((a[i] & 0x80) == 128){
				iguales = a[i] & 0x7F;
				data=a[++i];
				for (int k=0;k<iguales;k++)
					ba.write(data);
			}else ba.write(a[i]);
		}
		return ba.toByteArray();    	
    }
    public byte[] comprimir(byte a[]) {
    	int inicio=0,iguales,diferentes,tamano=0;
    	byte nbyte[] = new byte[2];
    	ByteArrayOutputStream ba = new ByteArrayOutputStream();
    	while (inicio<a.length){
    		iguales = buscaIguales(inicio,a);
    		if (iguales>1 || inicio==0){
    			if (iguales>127) iguales=127;
	    		nbyte[0] = (byte) (iguales|0x80);
	    		nbyte[1] = a[inicio];
	    		try {ba.write(nbyte);} catch (IOException e) {e.printStackTrace();}
	    		inicio+=iguales;
    		}else{
    			diferentes = buscaDiferentes(inicio,a);
    			if (diferentes==0)diferentes=1;
				for (int i=0;i<diferentes;i++)
					ba.write(a[inicio+i]);
		    	inicio+=diferentes;
    		}
    	}
    	return ba.toByteArray();
    }
	public int buscaDiferentes(int inicio,byte a[]){
		int pbyte=a[inicio],i;
		for (i=inicio+1;i<a.length;i++) 
			if (pbyte!=a[i]) pbyte=a[i];
			else break;
		return i-inicio-1;
	}
	public int buscaIguales(int inicio,byte a[]){
		int pbyte=a[inicio],i;
		for (i=inicio+1;i<a.length;i++) if (a[i]!=pbyte) break;
		return i-inicio;
	}
    public void transformar31(byte a[],int b[]){
    	for (int i=0;i<b.length;i++){ 
    		a[i]=(byte)(b[i]*31/255);
    	}
    }
    public void copiarArray(byte a[],int b[]){
    	for (int i=0;i<b.length;i++) 
    		a[i]=(byte)b[i];
    }
    public int[] copiarArray(byte a[]){
    	int b[] = new int[a.length];
    	for (int i=0;i<a.length;i++) 
    		b[i]=a[i];
    	return b;
    }
    public int[] convertirCMY(int a[]){
    	int b[] = new int[a.length];
    	for (int i=0;i<a.length;i++) 
    		b[i]=255-a[i];
    	return b;
    }
    protected ArrayList separarCarrera (String s, int ancho){
    	ArrayList lineas = new ArrayList();
    	char car;
    	int fin,inicio=0,nlineas=1;
    	if (s.length()>ancho){
    		fin = ancho-1;
    		while (fin>inicio){
    			if (fin > s.length()){ 
    				fin = s.length()-1;
    			}
	    		car = s.charAt(fin--);
	    		if (car == ' '){
	    			lineas.add(s.substring(inicio,fin+1));
	    			inicio=fin+2;
	    			fin=ancho*++nlineas;
	    		}
    		}
    		lineas.add(s.substring(inicio));
    	}else{
    		lineas.add(s);
    	}
    	return lineas;
    }
    public void agregarGS(String n,byte c[]){
    	comandos.write(27);
    	try {comandos.write("GS ".getBytes());} catch (IOException e1) {e1.printStackTrace();}
    	try {comandos.write(n.getBytes());} catch (IOException e1) {e1.printStackTrace();}
    	try {comandos.write(" 30 0 0 1029 645 ".getBytes());} catch (IOException e1) {e1.printStackTrace();}
    	try {comandos.write(c);} catch (IOException e1) {e1.printStackTrace();}
    	comandos.write(13);
    }
    public void agregarComando(String c){
    	comandos.write(27);
    	try {comandos.write(c.getBytes());} catch (IOException e1) {e1.printStackTrace();}
    	comandos.write(13);
    }
	public int comprobar(byte a[]){
		int bytes=0;
		for (int i=0;i<a.length;i++){ 
			if ((a[i] & 0x80) == 128){
				bytes += a[i] & 0x7F;
				i++;
			}else bytes++;
		}
		return bytes;
	}
}