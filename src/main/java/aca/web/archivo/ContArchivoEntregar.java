package aca.web.archivo;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.apache.commons.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.archivo.spring.ArchDocumentosDao;
import aca.archivo.spring.ArchEntrega;
import aca.archivo.spring.ArchEntregaDao;
import aca.emp.spring.EmpFoto;

@Controller
public class ContArchivoEntregar {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	ArchEntregaDao archEntregaDao;	
	
	@Autowired
	AlumPersonalDao alumPersonalDao;	 
	
	@Autowired
	ArchDocumentosDao archDocumentosDao;
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexiÃ³n
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){
			System.out.println(url+" "+ex);
		}
	}
	
	@RequestMapping("/archivo/entregar/firma")
	public String archivoEntregarFirma( HttpServletRequest request) {
		
		String folio   			= request.getParameter("Folio");
		String codigoAlumno		= "";		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion!=null) {
			codigoAlumno 		= (String) sesion.getAttribute("codigoAlumno");
			sesion.setAttribute("folio",folio);
		}		
		
		return "archivo/entregar/firma";
		
	}
	
	@ResponseBody
	@RequestMapping("/archivo/entregar/saveFirma")	
	public String archivoEntregarSaveFirma(HttpServletRequest request){		
		String firma 			= request.getParameter("Firma")==null?"":request.getParameter("Firma");	
		String mensaje			= "ok";
		String base64Image 		= firma.split(",")[1];
		byte[] decodedBytes 	= Base64.getDecoder().decode(base64Image);
		byte[] imageBytes 		= jakarta.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
		
		String codigoAlumno	= "";
		String folio   		= "";	
		
		HttpSession sesion = ((HttpServletRequest)request).getSession();
		if (sesion!=null) {
			codigoAlumno = (String) sesion.getAttribute("codigoAlumno");
			folio = (String) sesion.getAttribute("folio");
		}		

		System.out.println("DATA : "+codigoAlumno+" - "+folio+" - "+decodedBytes);
		
		archEntregaDao.updateFirma(decodedBytes, codigoAlumno, folio);
		
		return mensaje;
	}
	
	@PostMapping("/archivo/entregar/grabarFirma")
	public String archivoEntregarGrabarFirma(HttpServletRequest request, @RequestParam("imagen") MultipartFile firma) throws IOException, FileUploadException {
		
		String codigoAlumno	= "";
		
		HttpSession sesion = ((HttpServletRequest)request).getSession();
		if (sesion!=null) {
			codigoAlumno = (String) sesion.getAttribute("codigoAlumno");
		}		
		String folio   		= request.getParameter("Folio");	
		
		System.out.println("SAVE SIGNATURE : "+firma.getBytes()+" - "+codigoAlumno+" - "+folio);
		
//		archEntregaDao.updateFirma(firma.getBytes(), codigoAlumno, folio);
		
		return "redirect:/archivo/entregar/firma";
		
	}
	
	@RequestMapping("/archivo/entregar/accion")
	public String archivoEntregarAccion(HttpServletRequest request, @RequestParam("files[]") MultipartFile file) throws IOException {
		String codigoAlumno = request.getParameter("CodigoAlumno")==null?"0":request.getParameter("CodigoAlumno");
		String folio 		= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String imagen 		= request.getParameter("Imagen")==null?"0":request.getParameter("Imagen");
		String opcion 		= request.getParameter("Opc")==null?"0":request.getParameter("Opc");
		String mensaje		= "";
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	codigoAlumno		= (String)sesion.getAttribute("codigoAlumno");        	
        }

        if(!file.isEmpty()) {
			try {
				byte[] foto = file.getBytes();
				
				if(imagen.equals("Identificacion")){
	    			if(!archEntregaDao.updateIdentificacion(foto,codigoAlumno, folio)){ 
	    				mensaje = "Error";
	    			}else{
	    				mensaje = "1";
	    			}
	    		}else if(imagen.equals("Poder")){
	    			if(!archEntregaDao.updatePoder(foto,codigoAlumno, folio)){ 
	    				mensaje = "Error";
	    			}else{
	    				mensaje = "1";
	    			}
	    		}else if(imagen.equals("Envio")){
	    			if(!archEntregaDao.updateEnvio(foto,codigoAlumno, folio)){ 
	    				mensaje = "Error";
	    			}else{
	    				mensaje = "1";
	    			}
	    		}else if(imagen.equals("Firma")){
	    			if(!archEntregaDao.updateFirma(foto,codigoAlumno, folio)){ 
	    				mensaje = "Error";
	    			}else{
	    				mensaje = "1";
	    			}
	    		}else if(imagen.equals("Extra")){
	    			if(!archEntregaDao.updateExtra(foto,codigoAlumno, folio)){ 
	    				mensaje = "Error";
	    			}else{
	    				mensaje = "1";
	    			}
	    		}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			mensaje = "Choose an Image";
		}
        
		return "redirect:/archivo/entregar/subir?Folio="+folio+"&Imagen="+imagen+"&Opc="+opcion+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/archivo/entregar/borrar")
	public String archivoEntregarBorrar(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContArchivoEntregar|archivoEntregarBorrar");
		return "archivo/entregar/borrar";
	}
	
	@RequestMapping("/archivo/entregar/borrarImagen")
	public String archivoEntregarBorrarImagen(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContArchivoEntregar|archivoEntregarBorrarImagen");
		return "archivo/entregar/borrarImagen";
	}
	
	@RequestMapping("/archivo/entregar/documentos")
	public String archivoEntregarDocumentos(HttpServletRequest request, Model modelo){

		String codigoAlumno		= "0";
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	codigoAlumno		= (String)sesion.getAttribute("codigoAlumno");        	
        }
        AlumPersonal alumno = new AlumPersonal();
		if(alumPersonalDao.existeReg(codigoAlumno)) {
			alumno = alumPersonalDao.mapeaRegId(codigoAlumno);
		}
		List<ArchEntrega> lisEntregas				= archEntregaDao.getListaSinArchivos(codigoAlumno);
		HashMap<String,String> mapDescripcion		= archDocumentosDao.mapaTodos();
		HashMap<String,String> mapIdentificacion	= archEntregaDao.mapaIdentificacion(codigoAlumno);
		HashMap<String,String> mapPoder				= archEntregaDao.mapaPoder(codigoAlumno);
		HashMap<String,String> mapEnvio				= archEntregaDao.mapaEnvio(codigoAlumno);
		HashMap<String,String> mapExtra				= archEntregaDao.mapaExtra(codigoAlumno);
		
		modelo.addAttribute("codigoAlumno", codigoAlumno);
		modelo.addAttribute("alumno", alumno);
		modelo.addAttribute("lisEntregas", lisEntregas);
		modelo.addAttribute("mapDescripcion", mapDescripcion);
		modelo.addAttribute("mapIdentificacion", mapIdentificacion);
		modelo.addAttribute("mapPoder", mapPoder);
		modelo.addAttribute("mapEnvio", mapEnvio);
		modelo.addAttribute("mapExtra", mapExtra);
		
		return "archivo/entregar/documentos";
	}
	
	@RequestMapping("/archivo/entregar/camara")
	public String archivoEntregarCamara(HttpServletRequest request, Model modelo){
		
		String codigoAlumno		= "0";
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	codigoAlumno		= (String)sesion.getAttribute("codigoAlumno");        	
        }
        AlumPersonal alumno = new AlumPersonal();
		if(alumPersonalDao.existeReg(codigoAlumno)) {
			alumno = alumPersonalDao.mapeaRegId(codigoAlumno);
		} 
		
		modelo.addAttribute("codigoAlumno", codigoAlumno);
		modelo.addAttribute("alumno", alumno);
		
		return "archivo/entregar/camara";
	}
	
	@RequestMapping("/archivo/entregar/editarRetirar")
	public String archivoEntregarEditarRetirar(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContArchivoEntregar|archivoEntregarEditarRetirar");
		return "archivo/entregar/editarRetirar";
	}
	
	@RequestMapping("/archivo/entregar/imagen")
	public String archivoEntregarImagen(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContArchivoEntregar|archivoEntregarEditarRetirar");
		return "archivo/entregar/imagen";
	}
	
	@RequestMapping("/archivo/entregar/retirar")
	public String archivoEntregarRetirar(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContArchivoEntregar|archivoEntregarRetirar");
		return "archivo/entregar/retirar";
	}
	
	@RequestMapping("/archivo/entregar/subir")
	public String archivoEntregarSubir(HttpServletRequest request, Model modelo){
		String codigoAlumno	= "0";
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	codigoAlumno		= (String)sesion.getAttribute("codigoAlumno");        	
        }
		
		String folio 	= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String imagen 	= request.getParameter("Imagen")==null?"0":request.getParameter("Imagen");
		String opcion 	= request.getParameter("Opc")==null?"0":request.getParameter("Opc");
		String mensaje	= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
		
		ArchEntrega archEntrega	= new ArchEntrega();
		
		if(archEntregaDao.existeReg(codigoAlumno, folio)) {
			archEntrega = archEntregaDao.mapeaRegId(codigoAlumno, folio);
		}
		
		modelo.addAttribute("codigoAlumno", codigoAlumno);		
		modelo.addAttribute("folio", folio);		
		modelo.addAttribute("imagen", imagen);		
		modelo.addAttribute("opcion", opcion);		
		modelo.addAttribute("mensaje", mensaje);		
		modelo.addAttribute("archEntrega", archEntrega);		
		
		return "archivo/entregar/subir";
	}
	
	@RequestMapping("/archivo/entregar/ver")
	public String archivoEntregarVer(HttpServletRequest request, Model modelo){
		String folio 		= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String imagen 		= request.getParameter("Imagen")==null?"0":request.getParameter("Imagen");
		String codigoAlumno	= "0";
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoAlumno		= (String)sesion.getAttribute("codigoAlumno");        	
		}
		
		ArchEntrega archEntrega	= new ArchEntrega();
		
		if(archEntregaDao.existeReg(codigoAlumno, folio)) {
			archEntrega = archEntregaDao.mapeaRegId(codigoAlumno, folio);
		}
		
		modelo.addAttribute("archEntrega", archEntrega);		
		modelo.addAttribute("imagen", imagen);		

		return "archivo/entregar/ver";
	}
	
	@RequestMapping("/archivo/entregar/firmados")
	public String archivoFirmaDos(HttpServletRequest request, Model modelo){
		
		return "archivo/entregar/firmados";
	}
	
	@RequestMapping("/archivo/entregar/muestraFoto")
	public void archivoEntregarMuestraFoto(HttpServletRequest request, HttpServletResponse response){
		String folio 		= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String imagen 		= request.getParameter("Imagen")==null?"0":request.getParameter("Imagen");
		String codigoAlumno	= "0";
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	codigoAlumno		= (String)sesion.getAttribute("codigoAlumno");        	
        }
        
		ArchEntrega archEntrega	= new ArchEntrega();

		try{			
			// Busca la imagen
			if(archEntregaDao.existeReg(codigoAlumno, folio)) {
				archEntrega = archEntregaDao.getEntrega(codigoAlumno, folio);
			}
			OutputStream out = response.getOutputStream();			
			
			if(imagen.equals("Identificacion")){
				out.write(archEntrega.getIdentificacion());
    		}else if(imagen.equals("Poder")){
    			out.write(archEntrega.getPoder());
    		}else if(imagen.equals("Envio")){
    			out.write(archEntrega.getEnvio());
    		}else if(imagen.equals("Firma")){
    			out.write(archEntrega.getFirma());
    		}else if(imagen.equals("Extra")){
    			out.write(archEntrega.getExtra());
    		}
			
			out.close();
		}catch(Exception ex){
			System.out.println("Error /archivo/entregar/muestraFoto:"+ex);
		}
	}
}
