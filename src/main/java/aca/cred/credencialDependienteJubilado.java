package aca.cred;

import java.applet.Applet;
import java.awt.Button;
import java.awt.Color;
import java.awt.Event;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.ArrayList;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.ServiceUI;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaSizeName;

@SuppressWarnings("removal")
public class credencialDependienteJubilado extends Applet{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Image imgCredencial,imgFoto,imgCredencialAtras,sello;
    String matricula,nombre,fnac,empleado,depto,nomina, institucion;
    FontMetrics fm;
    BufferedImage bs,bN,bsA,bsAN;
    
    Button credBoton;
	boolean recrear=true;
	ByteArrayOutputStream comandos;
	MediaTracker tracker;
    @SuppressWarnings("deprecation")
	public void init() {   	
    	setLayout(null);
    	matricula 	= getParameter("matricula");;
    	nombre 		= getParameter("nombre");;
    	fnac 		= getParameter("fnac");
    	empleado 	= getParameter("empleado");
    	depto 		= getParameter("depto");
    	nomina 		= getParameter("nomina");
		institucion = getParameter("institucion");
    	
    	credBoton = new Button("Imprimir Credencial");
    	credBoton.setBounds(0,0,400,30);
    	credBoton.setFont(new Font("Gill Sans MT",Font.BOLD,11));
    	add(credBoton);
    	sello = getImage(getDocumentBase(),"flama.jpg");
    	imgCredencial = getImage(getDocumentBase(),"credFrenteEmpleado.jpg");
    	imgCredencialAtras = getImage(getDocumentBase(),"credAtrasEmpleado.jpg");
    	imgFoto = getImage(getDocumentBase(),"foto.jsp?mat="+matricula);
    	//imgFoto = getImage(getDocumentBase(),matricula+".jpg");
    	bs = new BufferedImage(1029, 645, BufferedImage.TYPE_INT_RGB);
    	bN = new BufferedImage(1029, 645, BufferedImage.TYPE_INT_RGB);
    	bsA = new BufferedImage(1029, 645, BufferedImage.TYPE_INT_RGB);
    	bsAN = new BufferedImage(1029, 645, BufferedImage.TYPE_INT_RGB);
    	comandos = new ByteArrayOutputStream();
    	tracker = new MediaTracker(this);
    	setSize(1200,750);
    }
	public void start(){		 
    	tracker.addImage(imgCredencial,0);
    	tracker.addImage(imgCredencialAtras,1);
    	tracker.addImage(imgFoto,2);
    	tracker.addImage(sello,3);
    	try {tracker.waitForAll();} catch (InterruptedException e) {}
		Graphics2D gs = bs.createGraphics();
		Graphics2D gN = bN.createGraphics();
		Graphics2D gsA = bsA.createGraphics();
		Graphics2D gsAN = bsAN.createGraphics();

		gN.setColor(Color.WHITE);
        gN.fillRect(0,0,1029,645);
        gN.setColor(Color.BLACK);

        gsAN.setColor(Color.WHITE);
        gsAN.fillRect(0,0,1029,645);
        gsAN.setColor(Color.BLACK);

		
		gs.drawImage(imgCredencial, 0, 0,1029, 645,this);
		gs.drawImage(imgFoto, 35,240,290,380,this);		
		gs.setColor(Color.WHITE);
		gs.setFont(new Font("Gill Sans MT",Font.PLAIN,34));
		gs.drawString("Credencial de Dependiente",290,225);
		gs.setColor(Color.BLACK);
		
		gs.setFont(new Font("Gill Sans MT",Font.PLAIN,26));
		gs.drawString("Nombre",350,270);
		gs.drawString("Puesto",350,350);
		gs.drawString("Titular",350,425);
		gs.drawString("Departamento",350,505);
		gs.drawString("# Nomina",350,610);
		gs.setFont(new Font("Gill Sans MT",Font.PLAIN,29));
		gs.drawString(nombre.toUpperCase(),360,305);
		gs.drawString("Dependiente",360,385);
		gs.drawString(empleado,360,460);
		gs.drawString(depto,360,540);
		gs.setFont(new Font("Gill Sans MT",Font.PLAIN,32));
        gs.drawString(nomina,480,610);
		
		//--------
		gN.setFont(new Font("Gill Sans MT",Font.PLAIN,26));
		gN.drawString("Nombre",350,270);
		gN.drawString("Puesto",350,350);
		gN.drawString("Titular",350,425);
		gN.drawString("Departamento",350,505);
		gN.drawString("# Nomina",350,610);
		gN.setFont(new Font("Gill Sans MT",Font.PLAIN,29));
		gN.drawString(nombre.toUpperCase(),360,305);
		gN.drawString("Dependiente",360,385);
		gN.drawString(empleado,360,460);
		gN.drawString(depto,360,540);
		gN.setFont(new Font("Gill Sans MT",Font.PLAIN,32));
        gN.drawString(nomina,480,610);
        
        gs.dispose();
        gN.dispose();
        

        
        
        gsA.drawImage(imgCredencialAtras, 0, 0,1029, 645,this);
        gsA.fillRect(575,475,390,110);
       
        
        gsAN.setFont(new Font("Gill Sans MT",Font.BOLD,21));
        dibujarPeriodo(gsAN, gsA, 150,225,"Enero - Diciembre");
        gsAN.setFont(new Font("Gill Sans MT",Font.BOLD,20));
        gsAN.drawString("Esta credencial es propiedad de la",610,160);
        gsAN.drawString(institucion + " y es",610,190);
        gsAN.drawString("válida solamente por el periodo que",610,220);
        gsAN.drawString("se indica.",610,250);
        gsAN.setFont(new Font("Gill Sans MT",Font.BOLD,28));
        gsAN.drawString("www.um.edu.mx",610,340);
        gsAN.setColor(Color.WHITE);
        gsAN.dispose();
        gsA.dispose();
	}
    public void paint(Graphics g) {
    	g.drawImage(bs,0,30,400,250,null);
    	g.drawImage(bN,401,30,400,250,null);
    	g.drawImage(bsA,0,281,400,250,null);
    	g.drawImage(bsAN,401,281,400,250,null);
	}

	public void dibujarPeriodo(Graphics gs,Graphics gs2, int x,int y, String texto){
		int i,ano;
		Calendar ahora = Calendar.getInstance();
		ano = ahora.get(Calendar.YEAR);
		gs.drawImage(sello,x+20,y+8,null);
		for (i=0;i<2;i++)
			dibujarAno(gs,gs2,x+i*115,y,ano+i);
		for (i=0;i<2;i++)
			dibujarAno(gs,gs2,x+i*115,y+113,ano+i+2);
		gs.drawString(texto,x+12,y+253);
	}
	public void dibujarAno(Graphics gs,Graphics gs2, int x,int y, int ano){
		int ancho=115,alto=110;
		gs2.setColor(Color.WHITE);
		gs2.fillRect(x,y,ancho,alto);
		gs.setColor(Color.BLACK);
		gs.drawRect(x,y,ancho,alto);
		gs.drawRect(x-1,y-1,ancho+2,alto+2);
		gs.drawRect(x,y+84,ancho,1);
		gs.setFont(new Font("Gill Sans MT",Font.BOLD,24));
		gs.drawString(String.valueOf(ano),x+30,y+106);
	}

    public boolean action(Event x, Object y) {
    	Graphics2D gs = bs.createGraphics();
		gs.drawImage(imgCredencial, 0, 0,1029, 645,this);
		gs.drawImage(imgFoto, 35,240,290,380,this);		
		gs.setColor(Color.WHITE);
		gs.setFont(new Font("Gill Sans MT",Font.PLAIN,34));
		gs.drawString("Credencial de Dependiente",290,225);
		gs.setColor(Color.BLACK);
		
    	credBoton.setLabel("Preparando impresora...");
    	credBoton.setEnabled(false);
	   	AffineTransform afLeft = AffineTransform.getRotateInstance(Math.toRadians(270)); 
		afLeft.translate(bs.getWidth() * -1,0); 
		AffineTransformOp lOp = new AffineTransformOp(afLeft,null); 
		
		BufferedImage bsRot = lOp.filter(bs, null);
		BufferedImage bcmyk = new BufferedImage(645, 1029, BufferedImage.TYPE_INT_RGB);
		bcmyk.getRaster().setSamples(0,0,645, 1029,0,convertirCMY(bsRot.getRaster().getSamples(0,0,645, 1029,0,(int[])null)));
		bcmyk.getRaster().setSamples(0,0,645, 1029,1,convertirCMY(bsRot.getRaster().getSamples(0,0,645, 1029,1,(int[])null)));
		bcmyk.getRaster().setSamples(0,0,645, 1029,2,convertirCMY(bsRot.getRaster().getSamples(0,0,645, 1029,2,(int[])null)));

		byte C[] = new byte[1029*645];
		byte M[] = new byte[1029*645];
		byte Y[] = new byte[1029*645];
	
		credBoton.setLabel("Creando frente de credencial...");
		transformar31(C,bcmyk.getRaster().getSamples(0,0,645, 1029,0,(int[])null));
		transformar31(M,bcmyk.getRaster().getSamples(0,0,645, 1029,1,(int[])null));
		transformar31(Y,bcmyk.getRaster().getSamples(0,0,645, 1029,2,(int[])null));
		
		BufferedImage bNRot = lOp.filter(bN, null); 

		byte Ccomp[] = comprimirColor(C);
		byte Mcomp[] = comprimirColor(M);
		byte Ycomp[] = comprimirColor(Y);
		byte K[] = getK(bNRot);
		byte Kcomp[] = comprimirNegro(K);
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
		agregarG(Kcomp);
		agregarComando("M 1 [IS 0[IS 1[IS 2[I[IV 31");
		agregarComando("MF");
		credBoton.setLabel("Creando reverso de credencial...");
		BufferedImage bsARot = lOp.filter(bsA, null); 
		BufferedImage bsANRot = lOp.filter(bsAN, null); 
		bcmyk.getRaster().setSamples(0,0,645, 1029,0,convertirCMY(bsARot.getRaster().getSamples(0,0,645, 1029,0,(int[])null)));
		bcmyk.getRaster().setSamples(0,0,645, 1029,1,convertirCMY(bsARot.getRaster().getSamples(0,0,645, 1029,1,(int[])null)));
		bcmyk.getRaster().setSamples(0,0,645, 1029,2,convertirCMY(bsARot.getRaster().getSamples(0,0,645, 1029,2,(int[])null)));
		
		transformar31(C,bcmyk.getRaster().getSamples(0,0,645, 1029,0,(int[])null));
		transformar31(M,bcmyk.getRaster().getSamples(0,0,645, 1029,1,(int[])null));
		transformar31(Y,bcmyk.getRaster().getSamples(0,0,645, 1029,2,(int[])null));
		Ccomp = comprimirColor(C);
		Mcomp = comprimirColor(M);
		Ycomp = comprimirColor(Y);

		K = getK(bsANRot);
		Kcomp = comprimirNegro(K);

		agregarGS("0",Ycomp);
		agregarGS("1",Mcomp);
		agregarGS("2",Ccomp);
		agregarG(Kcomp);
		agregarComando("B 595 575 0 0 0 3 90 0 "+matricula.replaceAll("-",""));
		agregarComando("M 1 [IS 0[IS 1[IS 2[I[IV 31[MO");
		credBoton.setLabel("Enviando info a impresora...");
		PrintService ps = selectPrinter(getPrintService("Eltron P420 Card Printer"));
		if (ps!=null) try {print(comandos.toByteArray(), ps);} catch (Exception e) {e.printStackTrace();}
		credBoton.setLabel("Imprimir Credencial");
		credBoton.setEnabled(true);
		return true;
    }
    public void dibujar(byte dato, int x, int y){
    	Graphics gs = getGraphics();
    	if (dato!=0){
    		if ((dato & 128) == 128) 
    			gs.drawRect(x,y,0,0);
    		if ((dato & 64) == 64) 
    			gs.drawRect(x+1,y,0,0);
    		if ((dato & 32) == 32) 
    			gs.drawRect(x+2,y,0,0);
    		if ((dato & 16) == 16) 
    			gs.drawRect(x+3,y,0,0);
    		if ((dato & 8) == 8) 
    			gs.drawRect(x+4,y,0,0);
    		if ((dato & 4) == 4) 
    			gs.drawRect(x+5,y,0,0);
    		if ((dato & 2) == 2) 
    			gs.drawRect(x+6,y,0,0);
    		if ((dato & 1) == 1) 
    			gs.drawRect(x+7,y,0,0);
	    }
    }
    public byte[] getK(BufferedImage bi){
    	int bytes = (int)Math.ceil((double)bi.getWidth()/8);
    	byte K[] = new byte[bi.getHeight()*bytes];
    	int pb=0,nb=7,i,j;
    	for (i=0;i<bi.getHeight();i++,nb=7,pb++){
    		for (j=0;j<bi.getWidth();j++){
    			if (bi.getRGB(j,i)==Color.BLACK.getRGB())	
    				K[pb]= (byte) (K[pb] | (byte) Math.pow(2,nb));
    			if (nb--==0){
    				nb=7;
    				pb++;
    			}
    		}
    	}
    	return K;
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
    public byte[] comprimirColor(byte a[]) {
    	int inicio=0,iguales,diferentes;
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
    public byte[] comprimirNegro(byte a[]) {
    	int inicio=0,iguales,diferentes;
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
    			ba.write(diferentes);
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
    public ArrayList<String> separarCarrera (String s, int ancho){
    	ArrayList<String> lineas = new ArrayList<String>();
    	char car;
    	int fin,inicio=0;
    	if (s.length()>ancho){
    		fin = ancho-1;
    		while (fin>inicio){
    			if (fin > s.length()){ 
    				fin = 0;
    			}
	    		car = s.charAt(fin--);
	    		if (car == ' '){
	    			lineas.add(s.substring(inicio,fin+1));
	    			inicio = fin+2;
	    			fin = fin + ancho - 1;
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
    public void agregarG(byte c[]){
    	comandos.write(27);
    	try {comandos.write("G 0 0 12 645 1029 1".getBytes());} catch (IOException e1) {e1.printStackTrace();}
    	comandos.write(13);
    	comandos.write(27);
    	comandos.write(0x5A);
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
    static public PrintService getPrintService(String printerName) {
        PrintService result = null;
        if (printerName != null) {
            PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null); //all
            if (printServices != null) {
                int count = printServices.length;
                for (int i = 0; i < count; i++) {
                    if (printServices[i].getName().equalsIgnoreCase(printerName)) {
                        result = printServices[i];
                        break;
                    }
                }
            }
        }
        return result;
    }
    static public PrintService selectPrinter(PrintService defaultPrinter) {
        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
        if (defaultPrinter == null) defaultPrinter = PrintServiceLookup.lookupDefaultPrintService();
        PrintRequestAttributeSet attributes = new HashPrintRequestAttributeSet();
        attributes.add(MediaSizeName.NA_LETTER);
        attributes.add(new Copies(1));
        return ServiceUI.printDialog(null, 200, 200,  printServices, defaultPrinter, null, attributes);
    }
    static public void print(byte datos[], PrintService printService) throws PrintException {
        Doc doc = new SimpleDoc(datos, DocFlavor.BYTE_ARRAY.AUTOSENSE, null);
        PrintRequestAttributeSet attributes = new HashPrintRequestAttributeSet();
        attributes.add(MediaSizeName.NA_LETTER);
        attributes.add(new Copies(1));
        
        print(doc, attributes, printService);
    }
    static public void print(Doc doc, PrintRequestAttributeSet attributes, PrintService printService) throws PrintException {
        if (printService == null) printService = PrintServiceLookup.lookupDefaultPrintService();
        DocPrintJob docPrintJob = printService.createPrintJob();
        docPrintJob.print(doc, attributes);
    }
}