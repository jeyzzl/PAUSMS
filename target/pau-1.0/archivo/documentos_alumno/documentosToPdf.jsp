<%@ page import="java.io.*" %>
<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import = "java.awt.Color" %>
<%@ page import = "java.io.FileOutputStream" %>
<%@ page import = "java.io.File" %>
<%@ page import = "java.io.IOException" %>
<%@ page import = "com.itextpdf.text.*" %>
<%@ page import = "com.itextpdf.text.pdf.*" %>
<%@ page import = "com.itextpdf.text.pdf.events.*" %>
<%@ page import = "com.itextpdf.text.Document" %>
<%@ page import = "com.itextpdf.text.DocumentException" %>
<%@ page import = "com.itextpdf.text.Paragraph" %>
<%@ page import = "com.itextpdf.text.pdf.PdfWriter" %>
<%@ page import	= "java.io.OutputStream"%>
<%@ page import	= "jakarta.servlet.http.HttpServletResponse"%>

<%@ page import	= "javax.imageio.ImageIO"%>
<%@ page import	= "java.awt.image.BufferedImage"%>
<%@ page import	= "java.io.ByteArrayInputStream"%>

<%@ page import="aca.archivo.spring.ArchDocAlum"%>  <!-- Agregado Rpet -->
<%@ page import="aca.pg.archivo.spring.PosArchDocAlum"%>  <!-- Agregado Rpet -->
<%@ page import="aca.archivo.spring.ArchDocumentos"%>  <!-- Agregado Rpet -->
<%@ page import="aca.pg.archivo.spring.PosArchDocAlum"%> <!-- Agregado Rpet -->
<%@ page import="aca.alumno.spring.AlumPersonal"%>
<%@ page import="aca.residencia.spring.ResDatos"%>
<%


	String documentoNombre    		 = (String) request.getAttribute("documentoNombre");
    String documentoId		 		 = request.getParameter("DocumentoId")==null?"0":request.getParameter("DocumentoId");          
    AlumPersonal alumPersonal 		 = (AlumPersonal) request.getAttribute("alumPersonal");
    List<PosArchDocAlum> lisImagenes = (List<PosArchDocAlum>) request.getAttribute("lisImagenes");
 
    
	String carpeta 		= application.getRealPath("/WEB-INF/pdf/educacion/"); 
	String dir 			= "";
	
	Document document = new Document(PageSize.LETTER);
	document.setMargins(0,0,0,0);
	
	try{
		
		if(!new File(carpeta).exists()) new File(application.getRealPath(carpeta)).mkdirs();
		dir = carpeta+alumPersonal.getCodigoPersonal()+".pdf";
		PdfWriter pdf = PdfWriter.getInstance(document, new FileOutputStream(dir));
		
		document.addAuthor("UM");
        document.addSubject("Documentos Digitalizados");
		document.open();

		PdfContentByte canvas = pdf.getDirectContentUnder();
	
		for (PosArchDocAlum hojas : lisImagenes ){
			if (hojas.getIddocumento().equals(documentoId)) {
				ByteArrayInputStream fotoInput = new ByteArrayInputStream(hojas.getImagenByte());
				//String contentType = URLConnection.guessContentTypeFromStream(fotoInput);
			    BufferedImage imagenOriginal = ImageIO.read(fotoInput);			    
			    float altoCarta = imagenOriginal.getWidth() * 1.295f;
			    float porAlto	= (imagenOriginal.getHeight() * 100) / altoCarta;
			    float altoFinal = (755*porAlto) / 100;
			    float porAncho	= porAlto-100;
			  	Image imgHoja = null;
			  	imgHoja = Image.getInstance(hojas.getImagenByte());
			  	imgHoja.setAlignment(Image.ALIGN_JUSTIFIED | Image.UNDERLYING);			  	
			  	float altoImagen 	= imagenOriginal.getHeight()*1f;			  	
				if (porAlto > 100){		
					imgHoja.scaleAbsolute(575-(575*porAncho/180),755);
					imgHoja.setAbsolutePosition(20, 20);
			    }else{
			    	imgHoja.scaleAbsolute(575,altoFinal);			    	
			    	imgHoja.setAbsolutePosition(20, (100-porAlto)*755/100);
			    }		  	
			  	
			  	canvas.addImage(imgHoja);			
			  	document.newPage();	
			  }	
		}	
		
	}catch(Exception ex){
			System.err.println("Error generating PDF file "+ex.getMessage());
	}
	
	document.close();
	
	// Cambia la diagonal inversa por diagonal normal para que se pueda ver el archivo en windows(localhost)
	if (java.io.File.separator.equals("\\")){
		dir = dir.replace("\\", "/");
	}
			
	String nombreArchivo = alumPersonal.getCodigoPersonal()+" - "+documentoNombre+".pdf";
	response.sendRedirect("../../archivo.jsp?ruta="+dir+"&nombre="+nombreArchivo);
%>