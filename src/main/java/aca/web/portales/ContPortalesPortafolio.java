package aca.web.portales;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import aca.alumno.spring.AlumPersonal;
import aca.bec.spring.BecAcuerdo;
import aca.bec.spring.BecContrato;
import aca.bec.spring.BecPeriodo;
import aca.bec.spring.BecPuestoAlumno;
import aca.bec.spring.BecTipo;
import aca.catalogo.spring.CatCarrera;
import aca.financiero.spring.ContCcosto;
import aca.portafolio.spring.PorCompromiso;
import aca.portafolio.spring.PorCompromisoDao;
import aca.portafolio.spring.PorCosmovision;
import aca.portafolio.spring.PorCosmovisionDao;
import aca.portafolio.spring.PorEmpArchivo;
import aca.util.Fecha;

@Controller
public class ContPortalesPortafolio {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired
	aca.portafolio.spring.PorEmpArchivoDao porEmpArchivoDao;

	@Autowired
	aca.vista.spring.MaestrosDao maestrosDao;

	@Autowired
	aca.portafolio.spring.PorEstudioDao porEstudioDao;

	@Autowired
	aca.portafolio.spring.PorNivelDao porNivelDao;

	@Autowired
	aca.emp.spring.EmpleadoDao empleadoDao;

	@Autowired
	aca.portafolio.spring.PorEmpDoctoDao porEmpDoctoDao;

	@Autowired
	aca.portafolio.spring.PorDocumentoDao porDocumetnoDao;

	@Autowired
	aca.portafolio.spring.PorPeriodoDao porPeriodoDao;

	@Autowired
	aca.portafolio.spring.PorCosmovisionDao porCosmovisionDao;
	
	@Autowired
	PorCompromisoDao porCompromisoDao;
	
	@Autowired
	ServletContext context;	
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
			//System.out.println("Abrir conEnoc"+url);
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}
	
	@RequestMapping("/portales/portafolio/datos")
	public String datos(HttpServletRequest request, Model modelo){
		String usuario		= "";
		
		String periodoId = request.getParameter("PeriodoId") == null ? "X" :request.getParameter("PeriodoId");

		HttpSession sesion	= null;
		if (request instanceof HttpServletRequest) {
            sesion = ((HttpServletRequest)request).getSession();   
        }
		
		if (sesion != null){
			usuario   = (String) sesion.getAttribute("codigoPersonal");
		}	
		
		if(!periodoId.equals("X")){
			sesion.setAttribute("porPeriodo", periodoId);
		}
		
		Map<String, aca.portafolio.spring.PorNivel> mapNiveles = new HashMap<String, aca.portafolio.spring.PorNivel>(); 
		mapNiveles.putAll(porNivelDao.getMapAll(""));
		
		modelo.addAttribute("lisEstudios", porEstudioDao.getListEmpleado(usuario, "ORDER BY NIVEL_ID"));
		modelo.addAttribute("lisPeriodos", porPeriodoDao.lisActivos(" ORDER BY PERIODO_ID DESC"));
		modelo.addAttribute("maestro", maestrosDao.mapeaRegId(usuario));
		modelo.addAttribute("paisOrigen", empleadoDao.getAronPais(usuario));
		modelo.addAttribute("curp", empleadoDao.getCurp(usuario));
		modelo.addAttribute("rfc", empleadoDao.getRFC(usuario));
		modelo.addAttribute("rfc", empleadoDao.getRFC(usuario));
		modelo.addAttribute("mapNiveles", mapNiveles);
		
		return "portales/portafolio/datos";
	}	
	
	@RequestMapping("/portales/portafolio/compromiso")
	public String compromiso(HttpServletRequest request, Model modelo){
		String periodoId 		= request.getParameter("PeriodoId") == null ? "X" :request.getParameter("PeriodoId");
		String mensaje	 		= request.getParameter("Mensaje") == null ? "0" :request.getParameter("Mensaje");
		String codigoPersonal 	= "0";
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	codigoPersonal 		= (String) sesion.getAttribute("codigoPersonal");
        }
        
        String fechaHoy 	= aca.util.Fecha.getHoy();
        
        if(periodoId.equals("X")) {
        	periodoId = fechaHoy.substring(6, 10);
        }
        
        PorCompromiso porCompromiso = new PorCompromiso();
        
        if(porCompromisoDao.existeReg(codigoPersonal, periodoId)) {
        	porCompromiso = porCompromisoDao.mapeaRegId(codigoPersonal, periodoId);
        }
        
        boolean existe = false;
        if(porCompromisoDao.existeReg(codigoPersonal, periodoId)) {
        	existe = true; 
        }
        
        String nombre = maestrosDao.getNombreMaestro(codigoPersonal, "NOMBRE");
		
        modelo.addAttribute("periodoId", periodoId);
		modelo.addAttribute("codigoPersonal", codigoPersonal);
		modelo.addAttribute("porCompromiso", porCompromiso);
		modelo.addAttribute("mensaje", mensaje);
		modelo.addAttribute("nombre", nombre);
		modelo.addAttribute("existe", existe);
		
		return "portales/portafolio/compromiso";
	}	
	
	@RequestMapping("/portales/portafolio/grabarCompromiso")
	public String grabarCompromiso(HttpServletRequest request, Model modelo){
		String periodoId 		= request.getParameter("PeriodoId") == null ? "X" :request.getParameter("PeriodoId");
		String codigoPersonal 	= "0";
		String departamento 	= request.getParameter("departamento") == null ? "-" :request.getParameter("departamento");
		String educar 			= request.getParameter("educar") == null ? "-" :request.getParameter("educar");
		String modelar 			= request.getParameter("modelar") == null ? "-" :request.getParameter("modelar");
		String investigar 		= request.getParameter("investigar") == null ? "-" :request.getParameter("investigar");
		String servir 			= request.getParameter("servir") == null ? "-" :request.getParameter("servir");
		String proclamar 		= request.getParameter("proclamar") == null ? "-" :request.getParameter("proclamar");
		String esperanza 		= request.getParameter("esperanza") == null ? "-" :request.getParameter("esperanza");
		String mensaje			= "0";
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoPersonal 		= (String) sesion.getAttribute("codigoEmpleado");
		}
		
		String fechaHoy 	= aca.util.Fecha.getHoy();
		
		if(periodoId.equals("X")) {
			periodoId = fechaHoy.substring(6, 10);
		}
		
		PorCompromiso porCompromiso = new PorCompromiso();
		
		porCompromiso.setCodigoPersonal(codigoPersonal);
		porCompromiso.setPeriodoId(periodoId);
		porCompromiso.setDepartamento(departamento);
		porCompromiso.setEducar(educar);
		porCompromiso.setModelar(modelar);
		porCompromiso.setInvestigar(investigar);
		porCompromiso.setServir(servir);
		porCompromiso.setProclamar(proclamar);
		porCompromiso.setEsperanza(esperanza);
		porCompromiso.setEstado("A");
		
		if(!porCompromisoDao.existeReg(codigoPersonal, periodoId)) {
			if(porCompromisoDao.insertReg(porCompromiso)) {
				mensaje = "1";
			}else {
				mensaje = "2";
			}
		}else {
			if(porCompromisoDao.updateReg(porCompromiso)) {
				mensaje = "1";
			}else {
				mensaje = "2";
			}
		}
		
		return "redirect:/portales/portafolio/compromiso?Mensaje="+mensaje;
	}	
	
	@RequestMapping("/portales/portafolio/enviarEstado")
	public String enviarEstado(HttpServletRequest request, Model modelo){
	
		String periodoId 		= request.getParameter("PeriodoId") == null ? "X" :request.getParameter("PeriodoId");
		String codigoPersonal	= request.getParameter("CodigoPersonal") == null ? "X" :request.getParameter("CodigoPersonal");

		if(porCompromisoDao.updateEstado(codigoPersonal, periodoId, "E")){
			
		}
		
		return "redirect:/portales/portafolio/compromiso?";
	}	
	
	@RequestMapping("/portales/portafolio/compromisoPdf")
	public void compromisoPdf(HttpServletResponse response, HttpServletRequest request) throws DocumentException, IOException{
		String codigoPersonal 	= request.getParameter("CodigoPersonal") == null ? "0" :request.getParameter("CodigoPersonal");
		String periodoId 		= request.getParameter("PeriodoId") == null ? "0" :request.getParameter("PeriodoId");
		String carpeta			= context.getRealPath("/WEB-INF/pdf/compromiso/");
		String dir 				= carpeta+codigoPersonal+periodoId+".pdf";
        
        PorCompromiso porCompromiso = new PorCompromiso();
        
        if(porCompromisoDao.existeReg(codigoPersonal, periodoId)) {
        	porCompromiso = porCompromisoDao.mapeaRegId(codigoPersonal, periodoId);
        }
        
        String nombre = maestrosDao.getNombreMaestro(codigoPersonal, "NOMBRE");
		
		String rutaCarpeta 	= context.getRealPath("/WEB-INF/pdf/compromiso/");
		if(!new java.io.File(rutaCarpeta).exists()) new java.io.File(rutaCarpeta).mkdirs();
		
		int posX 	= 0; 
		int posY	= 0;
		
		//Creación de la fuente
		BaseFont base = BaseFont.createFont("../../fonts/adventsanslogo.ttf", BaseFont.WINANSI,true);
		
		Document document = new Document(PageSize.LETTER); //Crea un objeto para el documento PDF
		document.setMargins(-30,-30,215,30);
		
		try{
			if(!new File(carpeta).exists()) new File(carpeta).mkdirs();
			PdfWriter pdf = PdfWriter.getInstance(document, new FileOutputStream(dir));
			document.addTitle("Compromiso");
			document.addAuthor("Sistema Académico");
	        document.addSubject("Compromiso de "+nombre);    
		    document.open();
		    
		    Image jpg = Image.getInstance(context.getRealPath("/imagenes/")+"/compromiso.jpg");
		    jpg.scaleAbsolute(530,200);
		    jpg.setAbsolutePosition(40, posY+582);
		    document.add(jpg);
		    
	    	// TABLA DE ACUERDOS
	    	float headerwidths[]= {100f};
	    	PdfPTable tableDatos = new PdfPTable(headerwidths);	    	
	    	tableDatos.getDefaultCell().setBorder(1);
	    	tableDatos.setTotalWidth(document.getPageSize().getWidth()-40);
	    	//tableDatos.setSpacingBefore(720);
	    	
	    	PdfPCell cell = null;
	    	
	    	//Datos empelado
	    	cell = new PdfPCell(new Phrase("Yo, "+nombre+", que trabajo en "+porCompromiso.getDepartamento()+" me sumo a la misión de la UM porque...",  new Font(base, 10F, Font.BOLD)));
	    	cell.setHorizontalAlignment(Element.ALIGN_LEFT);		
	    	cell.setBorder(0);   
	    	cell.setExtraParagraphSpace(4f);
	    	tableDatos.addCell(cell);

	    	//Pregunta 1
	    	cell = new PdfPCell(new Phrase("Educo integralmente", new Font(base, 11, Font.BOLD) ));
	    	cell.setHorizontalAlignment(Element.ALIGN_LEFT);		
	    	cell.setBorder(0);   
	    	cell.setExtraParagraphSpace(4f);
	    	tableDatos.addCell(cell);
	    	
	    	cell = new PdfPCell(new Phrase(porCompromiso.getEducar(), new Font(base, 10, Font.NORMAL) ));
	    	cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
	    	cell.setBorder(1);
	    	cell.setBorderWidth(3);
	    	cell.setFixedHeight(60f);
	    	cell.setBorder(Rectangle.BOX);
	    	tableDatos.addCell(cell);
	    	
	    	cell = new PdfPCell(new Phrase(" ", new Font(base, 5, Font.BOLD) ));
	    	cell.setBorder(0); 
	    	tableDatos.addCell(cell);
	    	
	    	
	    	//Pregunta 2
	    	cell = new PdfPCell(new Phrase("con un modelo educativo sustentable", new Font(base, 11, Font.BOLD) ));
	    	cell.setHorizontalAlignment(Element.ALIGN_LEFT);		
	    	cell.setBorder(0);
	    	cell.setExtraParagraphSpace(3f);
	    	tableDatos.addCell(cell);
	    	
	    	cell = new PdfPCell(new Phrase(porCompromiso.getModelar(), new Font(base, 9, Font.NORMAL) ));
	    	cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);		
	    	cell.setBorder(1);		
	    	cell.setBorderWidth(3);	    	
	    	cell.setFixedHeight(60f);
	    	cell.setBorder(Rectangle.BOX);	
	    	tableDatos.addCell(cell);
	    	
	    	cell = new PdfPCell(new Phrase(" ", new Font(base, 5, Font.BOLD) ));
	    	cell.setBorder(0); 
	    	tableDatos.addCell(cell);
	    	
	    	
	    	//Pregunta 3
	    	cell = new PdfPCell(new Phrase("en escenarios de investigación", new Font(base, 11, Font.BOLD) ));
	    	cell.setHorizontalAlignment(Element.ALIGN_LEFT);		
	    	cell.setBorder(0);    	
	    	cell.setExtraParagraphSpace(3f);
	    	tableDatos.addCell(cell);
	    	
	    	cell = new PdfPCell(new Phrase(porCompromiso.getInvestigar(), new Font(base, 9, Font.NORMAL) ));
	    	cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);		
	    	cell.setBorder(1);	
	    	cell.setBorderWidth(3);
	    	cell.setFixedHeight(60f);	    	
	    	cell.setBorder(Rectangle.BOX);	    	
	    	tableDatos.addCell(cell);
	    	
	    	cell = new PdfPCell(new Phrase(" ", new Font(base, 5, Font.BOLD) ));
	    	cell.setBorder(0); 
	    	tableDatos.addCell(cell);
	    	
	    	
	    	//Pregunta 4
	    	cell = new PdfPCell(new Phrase("y servicio abnegado", new Font(base, 11, Font.BOLD) ));
	    	cell.setHorizontalAlignment(Element.ALIGN_LEFT);		
	    	cell.setBorder(0);	    	
	    	cell.setExtraParagraphSpace(3f);
	    	tableDatos.addCell(cell);
	    	
	    	cell = new PdfPCell(new Phrase(porCompromiso.getServir(), new Font(base, 9, Font.NORMAL) ));
	    	cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);		
	    	cell.setBorder(1);
	    	cell.setBorderWidth(3);
	    	cell.setFixedHeight(60f);
	    	cell.setBorder(Rectangle.BOX);    	
	    	tableDatos.addCell(cell);
	    	
	    	cell = new PdfPCell(new Phrase(" ", new Font(base, 5, Font.BOLD) ));
	    	cell.setBorder(0); 
	    	tableDatos.addCell(cell);
	    	
	    	//Pregunta 5
	    	cell = new PdfPCell(new Phrase("que se unen a la proclamación bíblica global", new Font(base, 11, Font.BOLD) ));
	    	cell.setHorizontalAlignment(Element.ALIGN_LEFT);		
	    	cell.setBorder(0);
	    	cell.setExtraParagraphSpace(3f);
	    	tableDatos.addCell(cell);
	    	
	    	cell = new PdfPCell(new Phrase(porCompromiso.getProclamar(), new Font(base, 9, Font.NORMAL) ));
	    	cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);		
	    	cell.setBorder(1);
	    	cell.setBorderWidth(3);
	    	cell.setFixedHeight(60f);
	    	cell.setBorder(Rectangle.BOX);	    	
	    	tableDatos.addCell(cell);
	    	
	    	cell = new PdfPCell(new Phrase(" ", new Font(base, 5, Font.BOLD) ));
	    	cell.setBorder(0); 
	    	tableDatos.addCell(cell);
	    	
	    	
	    	//Pregunta 6
	    	cell = new PdfPCell(new Phrase("de la esperanza adventista de un mundo nuevo", new Font(base, 11, Font.BOLD) ));
	    	cell.setHorizontalAlignment(Element.ALIGN_LEFT);		
	    	cell.setBorder(0);
	    	cell.setExtraParagraphSpace(3f);
	    	tableDatos.addCell(cell);
	    	
	    	cell = new PdfPCell(new Phrase(porCompromiso.getEsperanza(), new Font(base, 9, Font.NORMAL) ));
	    	cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);		
	    	cell.setBorder(1);
	    	cell.setBorderWidth(3);
	    	cell.setFixedHeight(60f);
	    	cell.setBorder(Rectangle.BOX);    	
	    	tableDatos.addCell(cell);	    	
	    	
	    	document.add(tableDatos);
	    	
			document.close();
			
			// Cambia la diagonal inversa por diagonal normal para que se pueda ver el archivo en windows(localhost)
			if (java.io.File.separator.equals("\\")){
				dir = dir.replace("\\", "/");		
			}
			
			java.io.File f = new java.io.File(dir);	
			java.io.FileInputStream instream = null;	
			byte[] archivo = null;
			if(f.exists()){
				archivo = new byte[(int)f.length()];
				instream = new java.io.FileInputStream(dir);
			}
			instream.read(archivo,0,(int)f.length());
			instream.close();
			
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition","attachment; filename=\""+codigoPersonal+periodoId+".pdf"+ "\"");
			response.getOutputStream().write(archivo);
			response.flushBuffer();

		}catch(IOException ioe){
			System.err.println("Error certificado en PDF: "+ioe.getMessage());
		}
	}

	@RequestMapping("/portales/portafolio/documentos")
	public String documentos(HttpServletRequest request, Model modelo){
		String origen 		= request.getParameter("origen")!=null ? request.getParameter("origen") : "";
		String fechaHoy 	= aca.util.Fecha.getHoy();
		
		String usuario		= "";
		String periodoId 	= "-";
		
		HttpSession sesion	= null;
		if (request instanceof HttpServletRequest) {
			sesion = ((HttpServletRequest)request).getSession();   
		}
		
		if (sesion != null){
			usuario   = (String) sesion.getAttribute("codigoPersonal");
			periodoId = (String)sesion.getAttribute("porPeriodo");
		}	
		
		Map<String, aca.portafolio.spring.PorNivel> mapNiveles = new HashMap<String, aca.portafolio.spring.PorNivel>(); 
		mapNiveles.putAll(porNivelDao.getMapAll(""));
		
		modelo.addAttribute("codigoPersonal", usuario);
		modelo.addAttribute("periodoId", periodoId);
		modelo.addAttribute("fechaHoy", fechaHoy);
		modelo.addAttribute("origen", origen);
		modelo.addAttribute("mapArchivos", porEmpArchivoDao.mapaEmpArchivo(usuario, periodoId));
		modelo.addAttribute("lisDocumentos", porEmpDoctoDao.getListEmpleados(usuario, "ORDER BY DOCUMENTO_ID"));
		
		enviarConEnoc(request,"Error-aca.ContPortalesPortafolio|portalesPortafolioDocumentos");
		
		return "portales/portafolio/documentos";
	}	

	@RequestMapping("/portales/portafolio/subir")
	public String subir(HttpServletRequest request, HttpServletResponse response, Model modelo){
		String documentoId 		= request.getParameter("DocumentoId")!=null ? request.getParameter("DocumentoId") : "";
		String origen 			= request.getParameter("origen")!=null ? request.getParameter("origen") : "";
		String folio	 		= request.getParameter("Folio")!=null ? request.getParameter("Folio") : "";
		String usuario			= "";
		String periodoId 		= "-";
		String nombreArchivo 	= "-";
		
		HttpSession sesion	= null;
		if (request instanceof HttpServletRequest) {
			sesion = ((HttpServletRequest)request).getSession();   
		}
		
		if (sesion != null){
			usuario   = (String) sesion.getAttribute("codigoPersonal");
			periodoId = (String)sesion.getAttribute("porPeriodo");
		}	
		
		boolean tieneArchivo = false;
		
		PorEmpArchivo archivo =  new PorEmpArchivo();

		if(porEmpArchivoDao.existeReg(usuario, folio, documentoId, periodoId)) {
			archivo =  porEmpArchivoDao.mapeaRegId(usuario, folio, documentoId, periodoId);
			nombreArchivo = archivo.getNombre();
			if(archivo.getArchivo() != null) {
				tieneArchivo = true;
			}
		}
		
		modelo.addAttribute("codigoPersonal", usuario);
		modelo.addAttribute("documentoId", documentoId);
		modelo.addAttribute("periodoId", periodoId);
		modelo.addAttribute("origen", origen);
		modelo.addAttribute("folio", folio);
		modelo.addAttribute("tieneArchivo", tieneArchivo);
		modelo.addAttribute("nombreArchivo", nombreArchivo);
		
		return "portales/portafolio/subir";
	}
	
	@RequestMapping(value={"/portales/portafolio/imagenArchivo"})
	public void imagenArchivo(HttpServletRequest request, HttpServletResponse response){
		String documentoId 	= request.getParameter("DocumentoId")!=null ? request.getParameter("DocumentoId") : "";
		String folio	 	= request.getParameter("Folio")!=null ? request.getParameter("Folio") : "";
		String usuario		= "";
		String periodoId 	= "-";
		
		HttpSession sesion	= null;
		if (request instanceof HttpServletRequest) {
			sesion = ((HttpServletRequest)request).getSession();   
		}
		
		if (sesion != null){
			usuario   = (String) sesion.getAttribute("codigoPersonal");
			periodoId = (String)sesion.getAttribute("porPeriodo");
		}	
		
		PorEmpArchivo archivo = porEmpArchivoDao.mapeaRegId(usuario, folio, documentoId, periodoId);
		
		try {
			OutputStream out = response.getOutputStream();
			out.write(archivo.getArchivo());
			out.close();

		} catch (IOException e) {
			System.out.println("ERROR - Controller: ConPortalesPortafolio/imagenArchivo");
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/portales/portafolio/guardarArchivo")
	public String guardarArchivo(HttpServletRequest request, Model modelo, @RequestParam("archivo") MultipartFile archivo){		
		PorEmpArchivo porEmpArchivo = new PorEmpArchivo();
		
		String documentoId 		= request.getParameter("DocumentoId")==null?"0":request.getParameter("DocumentoId");
		String periodoId   		= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String origen 			= request.getParameter("origen")!=null ? request.getParameter("origen") : "";
		String folio	   		= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String nombre 			= archivo.getOriginalFilename();
		
		HttpSession sesion	= null;
		if (request instanceof HttpServletRequest) {
			sesion = ((HttpServletRequest)request).getSession();   
		}
		
		if (sesion!=null){
			String usuario  	= (String) sesion.getAttribute("codigoPersonal");
			try{				
				byte[] file = archivo.getBytes();
				
				porEmpArchivo.setCodigoPersonal(usuario);
				porEmpArchivo.setPeriodoId(periodoId);
				porEmpArchivo.setDocumentoId(documentoId);
				porEmpArchivo.setArchivo(file);
				porEmpArchivo.setNombre(nombre);
				porEmpArchivo.setFolio(folio);
				if (porEmpArchivoDao.existeReg(usuario,folio,documentoId,periodoId)){
					porEmpArchivoDao.updateReg(porEmpArchivo);
				}else{
					porEmpArchivoDao.insertReg(porEmpArchivo);
				}
				
			}catch( Exception e) {
				e.printStackTrace();
			}	
		}		
		
		return "redirect:/portales/portafolio/subir?DocumentoId="+documentoId+"&origen="+origen+"&Folio="+folio;
	}	
	
	@RequestMapping("/portales/portafolio/borrarArchivo")
	public String borrarArchivo(HttpServletRequest request, Model modelo){		
		String documentoId 	= request.getParameter("DocumentoId")!=null ? request.getParameter("DocumentoId") : "";
		String origen 		= request.getParameter("origen")!=null ? request.getParameter("origen") : "";
		String folio	 	= request.getParameter("Folio")!=null ? request.getParameter("Folio") : "";
		String usuario		= "";
		String periodoId 	= "-";
		
		HttpSession sesion	= null;
		if (request instanceof HttpServletRequest) {
			sesion = ((HttpServletRequest)request).getSession();   
			periodoId = (String)sesion.getAttribute("porPeriodo");
		}
		
		if (sesion != null){
			usuario   = (String) sesion.getAttribute("codigoPersonal");
		}	
		
		if (porEmpArchivoDao.existeReg(usuario,folio,documentoId,periodoId)){
			porEmpArchivoDao.deleteReg(usuario, folio, documentoId,periodoId);
		}
		
		return "redirect:/portales/portafolio/subir?DocumentoId="+documentoId+"&origen="+origen+"&Folio="+folio;
	}	
	
	@RequestMapping(value = {"/portales/portafolio/mostrarPdf"})
	public ResponseEntity<byte[]> mostrarPdf(HttpServletRequest request, HttpServletResponse respo) {
		String documentoId 	= request.getParameter("DocumentoId")!=null ? request.getParameter("DocumentoId") : "";
		String folio	 	= request.getParameter("Folio")!=null ? request.getParameter("Folio") : "";
		String usuario		= "";
		String periodoId 	= "-";
		
		HttpSession sesion	= null;
		if (request instanceof HttpServletRequest) {
			sesion = ((HttpServletRequest)request).getSession();   
		}
		
		if (sesion != null){
			usuario   = (String) sesion.getAttribute("codigoPersonal");
			periodoId = (String)sesion.getAttribute("porPeriodo");
		}	
		
		PorEmpArchivo archivo = porEmpArchivoDao.mapeaRegId(usuario, folio, documentoId, periodoId);
		
		try {
			OutputStream out = respo.getOutputStream();
			out.write(archivo.getArchivo());
			out.close();
			
		} catch (IOException e) {
			System.out.println("ERROR - Controller: ConPortalesPortafolio/mostrarPdf");
			e.printStackTrace();
		}
		
	    ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(HttpStatus.OK);
	    return response;
	}
	
	@RequestMapping(value = {"/portales/portafolio/descargaArchivo"}, method = RequestMethod.GET)
	public void descargaArcho(HttpServletRequest request, HttpServletResponse response) {
		String documentoId 	= request.getParameter("DocumentoId")!=null ? request.getParameter("DocumentoId") : "";
		String folio	 	= request.getParameter("Folio")!=null ? request.getParameter("Folio") : "";
		String usuario		= "";
		String periodoId 	= "-";
		
		HttpSession sesion	= null;
		if (request instanceof HttpServletRequest) {
			sesion = ((HttpServletRequest)request).getSession();   
		}
		
		if (sesion != null){
			usuario   = (String) sesion.getAttribute("codigoPersonal");
			periodoId = (String)sesion.getAttribute("porPeriodo");
		}	
		
		PorEmpArchivo archivo = porEmpArchivoDao.mapeaRegId(usuario, folio, documentoId, periodoId);
		
		try {
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition","attachment; filename=\""+archivo.getNombre()+ "\"");
			response.getOutputStream().write(archivo.getArchivo());
			response.flushBuffer();
		} catch (IOException e) {
			System.out.println("ERROR - Controller: ConPortalesPortafolio/descargaArchivo");
			e.printStackTrace();
		}

	}
	
	@RequestMapping("/portales/portafolio/accion_actividad")
	public String portalesPortafolioAccionActividad(HttpServletRequest request, Model modelo){
		enviarConEnoc(request,"Error-aca.ContPortalesPortafolio|portalesPortafolioAccionActividad");
		return "portales/portafolio/accion_actividad";
	}	
	
	@RequestMapping("/portales/portafolio/accion_informe")
	public String portalesPortafolioAccionInforme(HttpServletRequest request, Model modelo){
		enviarConEnoc(request,"Error-aca.ContPortalesPortafolio|portalesPortafolioAccionInforme");
		return "portales/portafolio/accion_informe";
	}
	
	@RequestMapping("/portales/portafolio/accion_proyecto")
	public String portalesPortafolioAccionProyecto(HttpServletRequest request, Model modelo){
		enviarConEnoc(request,"Error-aca.ContPortalesPortafolio|portalesPortafolioAccionProyecto");
		return "portales/portafolio/accion_proyecto";
	}
	
	@RequestMapping("/portales/portafolio/accion_respact")
	public String portalesPortafolioAccionRespact(HttpServletRequest request, Model modelo){
		enviarConEnoc(request,"Error-aca.ContPortalesPortafolio|portalesPortafolioAccionRespact");
		return "portales/portafolio/accion_respact";
	}
	
	@RequestMapping("/portales/portafolio/actividades")
	public String portalesPortafolioActividades(HttpServletRequest request, Model modelo){
		enviarConEnoc(request,"Error-aca.ContPortalesPortafolio|portalesPortafolioActividades");
		return "portales/portafolio/actividades";
	}
	
	@RequestMapping("/portales/portafolio/agregar")
	public String portalesPortafolioAgregar(HttpServletRequest request, Model modelo){	
		enviarConEnoc(request,"Error-aca.ContPortalesPortafolio|portalesPortafolioAgregar");
		return "portales/portafolio/agregar";
	}
	
	@RequestMapping("/portales/portafolio/agregarGrado")
	public String portalesPortafolioAgregarGrado(HttpServletRequest request, Model modelo){
		enviarConEnoc(request,"Error-aca.ContPortalesPortafolio|portalesPortafolioAgregarGrado");
		return "portales/portafolio/agregarGrado";
	}
	
	@RequestMapping("/portales/portafolio/archivo")
	public String portalesPortafolioArchivo(HttpServletRequest request, Model modelo){
		enviarConEnoc(request,"Error-aca.ContPortalesPortafolio|portalesPortafolioArchivo");
		return "portales/portafolio/archivo";
	}
	
	@RequestMapping("/portales/portafolio/asistencia")
	public String portalesPortafolioAsistencia(HttpServletRequest request, Model modelo){
		enviarConEnoc(request,"Error-aca.ContPortalesPortafolio|portalesPortafolioAsistencia");
		return "portales/portafolio/asistencia";
	}
	
	@RequestMapping("/portales/portafolio/borrar")
	public String portalesPortafolioBorrar(HttpServletRequest request, Model modelo){		
		return "portales/portafolio/borrar";
	}
	
	@RequestMapping("/portales/portafolio/cosmovision")
	public String portalesPortafolioCosmovision(HttpServletRequest request, Model modelo){
		enviarConEnoc(request,"Error-aca.ContPortalesPortafolio|portalesPortafolioCosmovision");
		
		String periodoId = request.getParameter("PeriodoId") == null ? "X" :request.getParameter("PeriodoId");
		
		HttpSession sesion	= null;
		if (request instanceof HttpServletRequest) {
            sesion = ((HttpServletRequest)request).getSession();   
        }
		
		if(!periodoId.equals("X")){
			sesion.setAttribute("porPeriodo", periodoId);
		}
		
		modelo.addAttribute("lisPeriodos", porPeriodoDao.getListAll(""));
		
		return "portales/portafolio/cosmovision";
	}

	@RequestMapping("/portales/portafolio/cosmovisionTraspasar")
	public String portalesPortafolioCosmovisionTraspasar(HttpServletRequest request, Model modelo){
		enviarConEnoc(request,"Error-aca.ContPortalesPortafolio|portalesPortafolioCosmovision");
		
		String periodoId 	= request.getParameter("PeriodoId") == null ? "-" :request.getParameter("PeriodoId");
		String filosofia 	= request.getParameter("Filosofia") == null ? "-" :request.getParameter("Filosofia");
		String mision 		= request.getParameter("Mision") == null ? "-" :request.getParameter("Mision");
		String vision 		= request.getParameter("Vision") == null ? "-" :request.getParameter("Vision");
		String valores 		= request.getParameter("Valores") == null ? "-" :request.getParameter("Valores");
		String reflexion 	= request.getParameter("Reflexion") == null ? "-" :request.getParameter("Reflexion");
		String usuario		= "";
		
		PorCosmovision cosmovision = new PorCosmovision();
		
		HttpSession sesion	= null;
		if (request instanceof HttpServletRequest) {
			sesion = ((HttpServletRequest)request).getSession();   
		}
		
		if (sesion != null){
			usuario   = (String) sesion.getAttribute("codigoPersonal");
		}	
		
		cosmovision.setCodigoPersonal(usuario);
		cosmovision.setPeriodoId(periodoId);
		cosmovision.setFilosofia(filosofia);
		cosmovision.setMision(mision);
		cosmovision.setVision(vision);
		cosmovision.setValores(valores);
		cosmovision.setReflexion(reflexion);
		
		if(porCosmovisionDao.existeReg(usuario, periodoId)) {
			porCosmovisionDao.updateReg(cosmovision);
		}else {
			porCosmovisionDao.insertReg(cosmovision);
		}
		
		modelo.addAttribute("lisPeriodos", porPeriodoDao.getListAll(""));
		
		return "portales/portafolio/cosmovision";
	}
	
	@RequestMapping("/portales/portafolio/evaluacion")
	public String portalesPortafolioEvaluacion(HttpServletRequest request, Model modelo){
		enviarConEnoc(request,"Error-aca.ContPortalesPortafolio|portalesPortafolioEvaluacion");
		return "portales/portafolio/evaluacion";
	}
	
	@RequestMapping("/portales/portafolio/formPuesto")
	public String portalesPortafolioFormPuesto(HttpServletRequest request, Model modelo){
		enviarConEnoc(request,"Error-aca.ContPortalesPortafolio|portalesPortafolioFormPuesto");
		return "portales/portafolio/formPuesto";
	}
	
	@RequestMapping("/portales/portafolio/guardar")
	public String portalesPortafolioGuardar(HttpServletRequest request, Model modelo){
		enviarConEnoc(request,"Error-aca.ContPortalesPortafolio|portalesPortafolioGuardar");
		return "portales/portafolio/guardar";
	}
	
	@RequestMapping("/portales/portafolio/imagen")
	public String portalesPortafolioImagen(HttpServletRequest request, Model modelo){
		enviarConEnoc(request,"Error-aca.ContPortalesPortafolio|portalesPortafolioImagen");
		return "portales/portafolio/imagen";
	}
	
	@RequestMapping("/portales/portafolio/modificaLogro")
	public String portalesPortafolioModificaLogro(HttpServletRequest request, Model modelo){
		enviarConEnoc(request,"Error-aca.ContPortalesPortafolio|portalesPortafolioModificaLogro");
		return "portales/portafolio/modificaLogro";
	}
	
	@RequestMapping("/portales/portafolio/periodo")
	public String portalesPortafolioPeriodo(HttpServletRequest request, Model modelo){
		enviarConEnoc(request,"Error-aca.ContPortalesPortafolio|portalesPortafolioPeriodo");
		return "portales/portafolio/periodo";
	}
	
	@RequestMapping("/portales/portafolio/puesto")
	public String portalesPortafolioPuesto(HttpServletRequest request, Model modelo){
		enviarConEnoc(request,"Error-aca.ContPortalesPortafolio|portalesPortafolioPuesto");
		return "portales/portafolio/puesto";
	}
	
	@RequestMapping("/portales/portafolio/puestoFront")
	public String portalesPortafolioPuestoFront(HttpServletRequest request, Model modelo){
		enviarConEnoc(request,"Error-aca.ContPortalesPortafolio|portalesPortafolioPuestoFront");
		return "portales/portafolio/puestoFront";
	}
	
	@RequestMapping("/portales/portafolio/puestoReferencia")
	public String portalesPortafolioPuestoRefrencia(HttpServletRequest request, Model modelo){
		enviarConEnoc(request,"Error-aca.ContPortalesPortafolio|portalesPortafolioPuestoReferencia");
		return "portales/portafolio/puestoReferencia";
	}	
	
}
