package aca.web.convenio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import aca.convenio.spring.ConArchivo;
import aca.convenio.spring.ConArchivoDao;
import aca.convenio.spring.ConConvenio;
import aca.convenio.spring.ConConvenioDao;
import aca.convenio.spring.ConTipo;
import aca.convenio.spring.ConTipoDao;

@Controller
public class ContConvenioConvenio {	
	
	@Autowired
	ConConvenioDao conConvenioDao;
	
	@Autowired
	ConTipoDao conTipoDao;
	
	@Autowired
	ConArchivoDao conArchivoDao;
	
	@RequestMapping("/convenio/convenio/listado")
	public String convenioConvenioListado(HttpServletRequest request, Model modelo){
		String estado				= request.getParameter("Estado")==null?"0":request.getParameter("Estado");
		String tipo			    	= request.getParameter("Tipo")==null?"0":request.getParameter("Tipo");
		String filtro 				= "X";
		
		if (!estado.equals("0")) {
			filtro = " WHERE ESTADO = '"+estado+"'";
		}		
		if (!tipo.equals("0")) {
			if (filtro.equals("X")) 
				filtro = " WHERE TIPO = '"+tipo+"'";
			else
				filtro = filtro + " AND TIPO = '"+tipo+"'";
		}
		if (filtro.equals("X")) filtro="";		
		
		List<ConConvenio> lisConvenios 		= conConvenioDao.lisTodos(filtro+" ORDER BY FECHA_VIGENCIA DESC");		
		List<ConTipo> listaTipos 			= conTipoDao.lisTodos(" ORDER BY TIPO_NOMBRE");
		HashMap<String,ConArchivo> mapa 	= conArchivoDao.mapaConArchivo();
		HashMap<String,ConTipo> mapaConTipo	= conTipoDao.mapaConTipo();
		
		modelo.addAttribute("lisConvenios", lisConvenios);
		modelo.addAttribute("listaTipos", listaTipos);
		modelo.addAttribute("mapa", mapa);
		modelo.addAttribute("mapaConTipo", mapaConTipo);
		
		return "convenio/convenio/listado";
	}
	
	@RequestMapping("/convenio/convenio/editar")
	public String convenioConvenioEditar(HttpServletRequest request, Model modelo){		
		
		String id 				= (String)request.getParameter("Id")==null?"0":request.getParameter("Id");
		ConConvenio convenio 	= new ConConvenio();
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();            
	    if (sesion!=null){
			if (conConvenioDao.existeReg(id)) {
				convenio = conConvenioDao.mapeaRegId(id);				
			}
	    }
	    
	    List<ConTipo> listaTipos = conTipoDao.lisTodos(" ORDER BY TIPO_NOMBRE");	
	    
	    modelo.addAttribute("convenio", convenio);
	    modelo.addAttribute("listaTipos", listaTipos);
	    
	    return "convenio/convenio/editar";
	}
	
	@RequestMapping("/convenio/convenio/grabar")
	public String convenioConvenioGrabar(HttpServletRequest request, Model modelo){
			
		String id 					= (String)request.getParameter("Id")==null?"0":request.getParameter("Id");
		String nombre 				= (String)request.getParameter("Nombre")==null?"-":request.getParameter("Nombre");
		String fechaFirma 			= (String)request.getParameter("FechaFirma")==null?"":request.getParameter("FechaFirma");
		String fechaVigencia 		= (String)request.getParameter("FechaVigencia")==null?"":request.getParameter("FechaVigencia");
		String programa				= (String)request.getParameter("Programa")==null?"-":request.getParameter("Programa");
		String objetivo				= (String)request.getParameter("Objetivo")==null?"-":request.getParameter("Objetivo");
		String estado				= (String)request.getParameter("Estado")==null?"1":request.getParameter("Estado");
		String tipo					= (String)request.getParameter("Tipo")==null?"1":request.getParameter("Tipo");
		String mensaje 				= "-";		
		
		ConConvenio convenio = new ConConvenio();
		convenio.setNombre(nombre);
		convenio.setFechaFirma(fechaFirma);
		if(estado.equals("2")) {
			fechaVigencia = "2100/12/31";
			convenio.setFechaVigencia(fechaVigencia);
		}else{
			convenio.setFechaVigencia(fechaVigencia);
		}
		convenio.setPrograma(programa);
		convenio.setObjetivo(objetivo);
		convenio.setEstado(estado);
		convenio.setTipoId(tipo);
		
		if (conConvenioDao.existeReg(id)) {			
			convenio.setId(id);
			// Update
			if (conConvenioDao.updateReg(convenio)) {
				mensaje = "Modificado";
			}else {
				mensaje = "Error al modificar...";
			}
		}else {
			// Insert			
			id = conConvenioDao.maximoReg();
			convenio.setId(id);			
			if (conConvenioDao.insertReg(convenio)) {
				mensaje = "Grabado";
			}else {
				mensaje = "Error al grabar...";
			}			
		}
		
		return "redirect:/convenio/convenio/editar?Id="+id+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/convenio/convenio/borrar")
	public String convenioConvenioBorrar(HttpServletRequest request){		
		
		String id 				= (String)request.getParameter("Id")==null?"0":request.getParameter("Id");
		if (conConvenioDao.existeReg(id)) {
			conConvenioDao.deleteReg(id);				
		}
		
	    return "redirect:/convenio/convenio/listado";
	}
	
	@RequestMapping("/convenio/convenio/imagen")
	public String convenioConvenioImagen(HttpServletRequest request, Model modelo){	
		
		String id 				= (String)request.getParameter("Id") == null ? "0" : request.getParameter("Id");
		String folio 			= request.getParameter("Folio") == null ? "0" : request.getParameter("Folio");
		String mensaje 			= request.getParameter("Mensaje") == null ? "0" : request.getParameter("Mensaje");
		
		ConConvenio convenio 	= new ConConvenio();
		ConArchivo conArchivo 	= new ConArchivo();
		
		if(conConvenioDao.existeReg(id)) {
			convenio = conConvenioDao.mapeaRegId(id);
		}
		
		if(conArchivoDao.existeReg(id, folio)){				
			conArchivo = conArchivoDao.mapeaRegId(id,folio);	
		}
		
		modelo.addAttribute("convenio", convenio);
		modelo.addAttribute("conArchivo", conArchivo);
		modelo.addAttribute("mensaje", mensaje);
		
	    return "convenio/convenio/imagen";
	}
	
	@PostMapping("/convenio/convenio/guardarImagen")
	public String convenioConvenioGuardarImagen(@RequestParam("archivo") MultipartFile file, HttpServletRequest request){
		
		ConArchivo conArchivo 	= new ConArchivo();
		
		String idConvenio	= request.getParameter("IdConvenio")==null?"0":request.getParameter("IdConvenio");
		String folio		= request.getParameter("Folio")==null?"0":request.getParameter("Folio");	
		String mensaje		= "";
		
		try {
			conArchivo.setConvenioId(idConvenio);
			conArchivo.setFolio(folio);			
			conArchivo.setNombre( file.getOriginalFilename() );
			conArchivo.setArchivo(file.getBytes());			
			if(conArchivoDao.existeReg(idConvenio, folio)){				
				conArchivoDao.updateReg(conArchivo);	
				mensaje = "1";
			}else{				
				conArchivoDao.insertReg(conArchivo);	
				mensaje = "1";
			}			
		}catch(Exception ex){		
			
		}		
		
		return "redirect:/convenio/convenio/imagen?Id="+idConvenio+"&Folio="+folio+"&Mensaje="+mensaje;
	}

	@RequestMapping("/convenio/convenio/borrarImagen")
	public String convenioConvenioBorrarImagen(HttpServletRequest request){
		
		String idConvenio		= request.getParameter("IdConvenio")==null?"0":request.getParameter("IdConvenio");
		String folio			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");		
		
		if(conArchivoDao.existeReg(idConvenio, folio)){				
			conArchivoDao.deleteReg(idConvenio, folio);	
		}			
		
		return "redirect:/convenio/convenio/imagen?Id="+idConvenio+"&Folio="+folio;
	}
	
	@RequestMapping(value = { "/convenio/convenio/descargaImagen" })
	public void convenioConvenioDescargarImagen(HttpServletResponse response, HttpServletRequest request) {
		ConArchivo conArchivo 	= new ConArchivo();
		
		String idConvenio		= request.getParameter("IdConvenio")==null?"0":request.getParameter("IdConvenio");
		String folio			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");	
		
		if(conArchivoDao.existeReg(idConvenio, folio)){				
			conArchivo = conArchivoDao.mapeaRegId(idConvenio, folio);	
			
			try {
				response.setContentType("application/octet-stream");
				response.setHeader("Content-Disposition","attachment; filename=\""+conArchivo.getNombre()+ "\"");
				response.getOutputStream().write(conArchivo.getArchivo());
				response.flushBuffer();
			} catch (IOException e) {
				System.out.println("ERROR");
				e.printStackTrace();
			}
		}
	}
	
	@RequestMapping("/convenio/convenio/ver")
	public String convenioConvenioVer(HttpServletRequest request, Model modelo){
		
		String convenioId					= request.getParameter("ConvenioId")==null?"0":request.getParameter("ConvenioId");
	    String folio			     		= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
	    String convenioNombre				= conConvenioDao.mapeaRegId(convenioId).getNombre();
	    
	    ConArchivo conArchivo 	= new ConArchivo();       
        if (conArchivoDao.existeReg(convenioId, folio)){
        	conArchivo = conArchivoDao.mapeaRegId(convenioId, folio);        	
        }
        
		List<ConArchivo> lisArchivos 		= conArchivoDao.lisPorConvenio(convenioId, " ORDER BY FOLIO");
		
		modelo.addAttribute("convenioNombre", convenioNombre);
		modelo.addAttribute("conArchivo", conArchivo);
		modelo.addAttribute("lisArchivos", lisArchivos);
		
		return "convenio/convenio/ver";
	}
	
}